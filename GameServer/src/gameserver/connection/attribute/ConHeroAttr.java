package gameserver.connection.attribute;

import gameserver.cache.HeroCache;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.connection.attribute.info.ItemInfo;
import gameserver.connection.attribute.info.MedalInfo;
import gameserver.logging.LogService;
import gameserver.network.message.game.ClientMessageHero;
import gameserver.network.protos.game.ProHero;
import gameserver.network.server.connection.ConnectionAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Hero;
import table.MT_Data_MedalAttribute;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.RandomUtil;

import commonality.Common.BUILDTYPE;
import database.CustomHeroEquip;
import database.DatabaseHero;
import database.DatabaseMedal;

public class ConHeroAttr extends ConnectionAttribute {
	private static final Logger logger = LoggerFactory
			.getLogger(ConHeroAttr.class);

	// <hero_id, HeroInfo>
	private Map<Integer, HeroInfo> m_Heros = new HashMap<Integer, HeroInfo>();

	@Override
	protected void Initialize_impl() {
		m_Heros.clear();
		boolean isLoadDb = false;
		HashMap<Object, Object> herosMap = HeroCache.getAllHero(m_Con
				.getPlayerId());
		if (herosMap == null) {
			isLoadDb = true;
			herosMap = new HashMap<Object, Object>();
		}
		if (isLoadDb) {

			List<DatabaseHero> heros = getDb().Select(DatabaseHero.class,
					"player_id = ?", m_Con.getPlayerId());
			for (DatabaseHero hero : heros) {
				List<MedalInfo> medalInfos = initMedal(hero);
				m_Heros.put(hero.hero_id, new HeroInfo(m_Con, this, hero,
						medalInfos));

				DatabaseHero hero1 = new DatabaseHero();
				hero1.set(hero);
				herosMap.put(hero.hero_id, hero1);
			}
		} else {

			Set<Object> keys = herosMap.keySet();
			for (Object key : keys) {
				DatabaseHero hero = (DatabaseHero) herosMap.get(key);
				hero.sync();
				hero.setDatabaseSimple(getDb().getM_Simple());
				List<MedalInfo> medalInfos = initMedal(hero);
				m_Heros.put(hero.hero_id, new HeroInfo(m_Con, this, hero,
						medalInfos));
			}
		}
		// 讲数据设置会缓存 如果是从缓存加载也需要设置一次 因为 缓存设置过定时清除 需要将设置的时间取消掉
		HeroCache.setHeros(m_Con.getPlayerId(), herosMap);
	}

	private List<MedalInfo> initMedal(DatabaseHero hero) {
		List<MedalInfo> medalInfos = new ArrayList<>();
		boolean isLoadDb = false;
		HashMap<Object, Object> medals = HeroCache.getMedals(hero.hero_id,
				m_Con.getPlayerId());
		if (medals == null) {
			isLoadDb = true;
			medals = new HashMap<Object, Object>();
		}
		if (isLoadDb) {
			List<DatabaseMedal> medalList = getDb().Select(DatabaseMedal.class,
					"hero_id = ? ", hero.hero_id);// 一个英雄下面所有的勋章
			for (DatabaseMedal databaseMedal : medalList) {
				MedalInfo medalInfo = new MedalInfo(m_Con, this, databaseMedal);
				medalInfos.add(medalInfo);
				DatabaseMedal medal = new DatabaseMedal();
				medal.set(databaseMedal);
				medals.put(medal.medal_id, medal);
			}
		} else {
			Set<Object> keys = medals.keySet();
			for (Object key : keys) {
				DatabaseMedal medal = (DatabaseMedal) medals.get(key);
				medal.sync();
				medal.setDatabaseSimple(getDb().getM_Simple());
				MedalInfo medalInfo = new MedalInfo(m_Con, this, medal);
				medalInfos.add(medalInfo);
			}
		}
		HeroCache.setMedals(hero.hero_id, m_Con.getPlayerId(), medals);
		return medalInfos;
	}

	/** 插入一个英雄 */
	public HeroInfo insertHero(int tableId) {
		DatabaseInsertUpdateResult result = null;
		try {
			DatabaseHero hero = new DatabaseHero();
			hero.hero_table_id = tableId;
			hero.player_id = m_Con.getPlayerId();
			hero.equips = new ArrayList<CustomHeroEquip>();
			hero.skills = new ArrayList<Integer>();
			hero.star = 0;
			hero.hp_lvl = 0;
			hero.attack_lvl = 0;
			hero.defen_lvl = 0;
			hero.star_up_count = 0;
			hero.luck_val = 0;
			hero.init_luck_val = true;
			result = getDb().Insert(hero);
			hero.hero_id = result.identity.intValue();

			List<MedalInfo> medalInfos = addHeroMedal(hero.hero_id, null);
			HeroInfo heroInfo = new HeroInfo(m_Con, this, hero, medalInfos);
			heroInfo.updateHeroFightVal();
			m_Heros.put(hero.hero_id, heroInfo);
			addCache(hero);
			return heroInfo;
		} catch (Exception e) {
			logger.error("insertHero is error : ", e);
		}
		return null;
	}

	public void addCache(DatabaseHero hero) {
		if (HeroCache.isNeedAllIncr(hero.player_id))
			HeroCache.setHeros(hero.player_id, m_Heros);
		else
			HeroCache.addHero(hero.player_id, hero);
	}

	/** 初始化英雄的勋章 */
	public List<MedalInfo> addHeroMedal(int heroId, HeroInfo hero) {
		List<MedalInfo> medalInfos = new ArrayList<MedalInfo>();
		ConPlayerAttr curPlayer = m_Con.getPlayer();
		HashMap<Integer, MT_Data_MedalAttribute> medalAttributeMap = TableManager
				.GetInstance().TableMedalAttribute().Datas();
		for (MT_Data_MedalAttribute medalattr : medalAttributeMap.values()) {
			// 1. 没有配置开启等级
			// 2. 玩家等级不足开启等级
			// 3. 配置了购买金钱时
			// 以上3中情况都不创建勋章
			if (TABLE.IsInvalid(medalattr.openLev())
					|| !curPlayer.CheckPlayerLevel(medalattr.openLev(), false)
					|| !TABLE.IsInvalid(medalattr.OpenNeedMoney()))
				continue;

			// 过滤英雄已经有的勋章
			if (hero != null
					&& hero.getMedalByTableId(medalattr.ID()) != null)
				continue;

			MedalInfo medalInfo = HeroInfo.createMedalByTableId(m_Con, heroId,
					medalattr.ID());
			medalInfos.add(medalInfo);
			LogService.logEvent(curPlayer.getPlayerId(), -1, 20,
					medalattr.ID(), heroId);
		}
		return medalInfos;
	}

	// 等玩家等级变化时，检查玩家的全部英雄的勋章，是否可以开启并开启
	public void onPlayerLevelUp(boolean isSync) {
		if (m_Con.getBuild().getBuild(BUILDTYPE.MEDAL) == null)
			return;

		for (HeroInfo heroInfo : m_Heros.values()) {
			List<MedalInfo> medalInfos = addHeroMedal(heroInfo.getHeroId(),
					heroInfo);
			heroInfo.getMedalList().addAll(medalInfos);
			if (isSync)
				ClientMessageHero.getInstance().OnHeroData(m_Con,
						heroInfo.getHeroId());
		}
	}

	// 玩家上线的时候，需要检查一次勋章的开启情况(原因是可能策划在勋章表中加入了玩家现在能开起的新的勋章)
	@Override
	public void CheckUpline() {
		onPlayerLevelUp(false);
	}

	public HeroInfo getHeroByTableId(int tableId) {
		for (HeroInfo hero : m_Heros.values()) {
			if (hero.getTableId() == tableId)
				return hero;
		}
		return null;
	}

	public HeroInfo getHeroById(int heroId) {
		return m_Heros.get(heroId);
	}

	public Map<Integer, HeroInfo> getHeros() {
		return m_Heros;
	}

	public int getFightVal() {
		int fightVal = 0;
		for (HeroInfo he : m_Heros.values())
			fightVal += he.getHero().fight_val;
		return fightVal;
	}

	public HeroInfo getRandomHero() {
		if (m_Heros.isEmpty())
			return null;

		int randomHeroIdx = RandomUtil.RangeRandom(0, m_Heros.size() - 1);
		for (HeroInfo he : m_Heros.values()) {
			if (randomHeroIdx == 0)
				return he;
			--randomHeroIdx;
		}

		return null;
	}

	public int getHeroCount() {
		return m_Heros.size();
	}

	public boolean isHaveHeroByTableId(int tableId) {
		return (getHeroByTableId(tableId) != null);
	}

	// 检测是否已选英雄(通过比instanceId)
	public boolean isHaveHeroByInstanceId(int instanceId) {
		for (HeroInfo hero : m_Heros.values()) {
			if (hero.getInstanceId() == instanceId)
				return true;
		}
		return false;
	}

	// 获得英雄人口
	public int getHeroPopulation(int tableId) {
		MT_Data_Hero data = TableManager.GetInstance().TableHero()
				.GetElement(tableId);
		if (data != null)
			return data.Population();
		return 0;
	}

	// 获得指定勋章的最高等级, 如果为非法值 则找所有勋章的最高等级勋章
	public int GetMedalMaxLevel(int medalId) {
		int max = 0;
		for (HeroInfo hero : m_Heros.values()) {
			List<MedalInfo> medalInfos = hero.getMedalList();
			for (MedalInfo medalInfo : medalInfos) {
				if (TABLE.IsInvalid(medalId)
						|| medalId == medalInfo.getMedal().medal_table_id) {
					if (medalInfo.getMedal().cur_level > max)
						max = medalInfo.getMedal().cur_level;
				}
			}
		}
		return max;
	}

	// 获得指定勋章的最高星级 如果为非法值 则找最高等级星级
	public int GetMedalMaxStar(int medalId) {
		int max = 0;
		for (HeroInfo hero : m_Heros.values()) {
			List<MedalInfo> medalInfos = hero.getMedalList();
			for (MedalInfo medalInfo : medalInfos) {
				if (TABLE.IsInvalid(medalId)
						|| medalId == medalInfo.getMedal().medal_table_id) {
					if (medalInfo.getMedal().cur_star > max)
						max = medalInfo.getMedal().cur_star;
				}
			}
		}
		return max;
	}

	public void updateAllHeroFightVal() {
		for (HeroInfo hero : m_Heros.values()) {
			hero.updateHeroFightVal();
		}
	}

	public ProHero.ProtoHeroInfo.Builder buildHeroMessage(HeroInfo heroinfo) {
		ProHero.ProtoHeroInfo.Builder info = ProHero.ProtoHeroInfo.newBuilder();
		info.setId(heroinfo.getHeroId());
		info.setTableId(heroinfo.getTableId());
		info.setStar(heroinfo.getStar());
		info.setHpLvl(heroinfo.getHpLvl());
		info.setAttackLvl(heroinfo.getAttackLvl());
		info.setDefenLvl(heroinfo.getDefenLvl());
		heroinfo.initLuckValByStarUpCount();
		info.setLuckVal(heroinfo.getLuckVal());

		// 装备
		List<CustomHeroEquip> equips = heroinfo.getEquips();
		for (CustomHeroEquip equip : equips) {
			ItemInfo item = m_Con.getItem().getItemById(equip.equipId);
			if (item != null) {
				ProHero.ProtoHeroInfo.ProtoEquipInfo.Builder equipInfo = ProHero.ProtoHeroInfo.ProtoEquipInfo
						.newBuilder();
				equipInfo.setItemId(equip.equipId);
				equipInfo.setTableId(item.getTableId());
				equipInfo.setPart(equip.itemPart);
				equipInfo.setStarLv(item.getStar());
				equipInfo.setLevel(item.getLvl());
				info.addEquips(equipInfo);
			} else {
				logger.error("buildHeroMessage error-------playerid={},heroId={},equipId={}",
						m_Con.getPlayerId(),heroinfo.getHeroId(),equip.equipId);
			}
		}

		// 勋章
		List<MedalInfo> medalInfoList = heroinfo.getMedalList();
		for (MedalInfo medalIf : medalInfoList) {
			ProHero.ProtoHeroInfo.ProtoMedalInfo.Builder medalInfo = ProHero.ProtoHeroInfo.ProtoMedalInfo
					.newBuilder();
			medalInfo.setTableId(medalIf.getMedal().medal_table_id);
			medalInfo.setLevel(medalIf.getMedal().cur_level);
			medalInfo.setStarLv(medalIf.getMedal().cur_star);
			medalInfo.setExp(medalIf.getMedal().cur_exp);
			info.addMedals(medalInfo);
		}

		// 技能
		List<Integer> skillsList = heroinfo.getSkills();
		for (int skillid : skillsList) {
			info.addSkills(skillid);
		}
		return info;
	}
}
