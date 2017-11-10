package gameserver.connection.attribute.info;

import gameserver.cache.HeroCache;
import gameserver.connection.attribute.ConHeroAttr;
import gameserver.network.server.connection.Connection;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Hero;
import table.MT_Data_HeroInforce;
import table.MT_Data_Item;
import table.MT_Data_ItemStrengthen;
import table.MT_Data_MedalStar;
import table.MT_Data_commodity;
import table.base.TableManager;

import com.commons.util.DatabaseInsertUpdateResult;
import com.commons.util.RandomUtil;

import commonality.Common;
import commonality.Common.BUILDTYPE;
import database.CustomHeroEquip;
import database.DatabaseHero;
import database.DatabaseMedal;

public class HeroInfo {
	private static final Logger logger = LoggerFactory.getLogger(HeroInfo.class);
			
	private int part = 5;
	private List<MedalInfo> m_medalList = new ArrayList<MedalInfo>();
	private Connection m_Connection;
	@SuppressWarnings("unused")
	private ConHeroAttr m_Attribute;
	private DatabaseHero m_Hero;

	public DatabaseHero getHero() {
		return m_Hero;
	}

	private MT_Data_Hero m_Data;

	public HeroInfo(Connection connection, ConHeroAttr attribute,
			DatabaseHero hero, List<MedalInfo> medalList) {
		m_Connection = connection;
		m_Attribute = attribute;
		m_Hero = hero;
		m_medalList = medalList;
		if (m_Hero.equips == null)
			m_Hero.equips = new ArrayList<CustomHeroEquip>();
		m_Hero.sync();
		m_Data = TableManager.GetInstance().TableHero()
				.GetElement(m_Hero.hero_table_id);

		// 初始化英雄技能
		if (m_Hero.skills.isEmpty()) {
			if (m_Data.InitSkill() > 0) {
				m_Hero.skills.add(m_Data.InitSkill());
				save();
			}
		}
	}

	public List<MedalInfo> getMedalList() {
		if (m_Connection.getBuild().getBuild(BUILDTYPE.MEDAL) == null)
			return new ArrayList<MedalInfo>();
		return m_medalList;
	}

	public int getHeroId() {
		return m_Hero.hero_id;
	}

	public int getTableId() {
		return m_Hero.hero_table_id;
	}

	public int getInstanceId() {
		return m_Hero.hero_instance_id;
	}

	public MT_Data_Hero getData() {
		return m_Data;
	}

	public int getPopulation() {
		return m_Data.Population();
	}

	public List<CustomHeroEquip> getEquips() {
		return m_Hero.equips;
	}

	public List<Integer> getSkills() {
		return m_Hero.skills;
	}

	public void addSkills(int skillid) {
		m_Hero.skills.add(skillid);
		save();
	}

	public boolean hasSkill(int skillid) {
		for (Integer sid : m_Hero.skills) {
			if (sid == skillid)
				return true;
		}
		return false;
	}

	public void addEquip(CustomHeroEquip equip) {
		m_Hero.equips.add(equip);
		save();
	}

	public void delEquip(CustomHeroEquip equip) {
		m_Hero.equips.remove(equip);
		save();
	}

	public void save() {
		m_Connection.pushSave(m_Hero);
		m_Connection.getHero().addCache(m_Hero);
	}
	
	public static long[] force_param = {0, 1000,3000,6000,10000,15000,15000};
	public static int[] star_param =     {0,1000,3000,6000,10000,15000};

	public void updateHeroFightVal() {
		// 1. 英雄所穿装备的战力值
		int equipVal = 0;
		List<CustomHeroEquip> equips = getEquips();
		for (CustomHeroEquip equip : equips) {
			ItemInfo itemInfo = m_Connection.getItem().getItemById(
					equip.equipId);
			MT_Data_ItemStrengthen is = TableManager.GetInstance()
					.TableItemStrengthen().GetElement(itemInfo.getLvl());
			equipVal += (itemInfo.getData().fightVal() * is.power() / 10000);
		}

		// 2. 英雄所有勋章的战力值
		int medalVal = 0;
		List<MedalInfo> medalInfos = getMedalList();
		for (MedalInfo medalInfo : medalInfos) {
			int star = medalInfo.getStar();
			MT_Data_MedalStar medalStar = TableManager.GetInstance()
					.TableMedalStar()
					.GetElement(medalInfo.getMedalTableId() + star);
			medalVal += medalInfo.getData().fightVal() * medalStar.fightVal();
		}
		
		// 3. 计算强化战斗力
		long force_val = 0;
		long star_val = 0;
		int base_fight = (int)(29000 / 1.5);
		MT_Data_HeroInforce heroinfoceconfig = TableManager.GetInstance()
				.TableHeroInforce().GetElement(getData().ID() * 10000 + getStar());
		if (getStar() == 5) {
			heroinfoceconfig = TableManager.GetInstance()
					.TableHeroInforce().GetElement(getData().ID() * 10000 + 4);
		}
		if (heroinfoceconfig != null) {
			star_val = base_fight * star_param[getStar()] / 10000;
			
			int maxLvl = TableManager.GetInstance().getMaxHeroLvl(heroinfoceconfig);
			force_val = base_fight * force_param[getStar() + 1] * getLvl() / maxLvl / 10000;
		}

		// 英雄战斗力 = 所穿装备的战力值 + 所有勋章的战力值 + 英雄本身的战斗力 + 星级战斗力 + 强化战斗力
		m_Hero.fight_val = equipVal + medalVal + m_Data.FightVal() + (int)star_val + (int)force_val;
		logger.info("fight_val={},equipVal={},medalVal={},base={},star_val={},force_val={}", 
				m_Hero.fight_val,equipVal,medalVal,m_Data.FightVal(),star_val,force_val);
		save();
	}
	
	public int getFightVal() {
		return m_Hero.fight_val;
	}

	// 通过tableId查询某个英雄下的勋章，此tableID不包含等级
	public MedalInfo getMedalByTableId(int tableId) {
		for (MedalInfo medalInfo : m_medalList) {
			if (medalInfo.getMedalTableId() == tableId)
				return medalInfo;
		}
		return null;
	}

	public void addCache(DatabaseMedal medal) {
		long pid = m_Connection.getPlayerId();
		int hero_id = m_Hero.getHero_id();
		if (HeroCache.isNeedIncrMedal(pid, hero_id))
			HeroCache.setMedals(hero_id, pid, m_medalList);
		else
			HeroCache.addMedal(hero_id, pid, medal);
	}

	// 添加一个勋章
	public void addMedal(int tableId) {
		MedalInfo medalInfo = createMedalByTableId(m_Connection,
				m_Hero.hero_id, tableId);
		// 加入英雄的勋章列表中
		m_medalList.add(medalInfo);
		addCache(medalInfo.getMedal());
	}

	public static MedalInfo createMedalByTableId(Connection connection,
			int heroid, int tableId) {
		DatabaseMedal medal = new DatabaseMedal();
		medal.hero_id = heroid;
		medal.cur_level = 1;
		medal.cur_star = 1;
		medal.cur_exp = 0;
		medal.medal_table_id = tableId;
		DatabaseInsertUpdateResult result = connection.getDb().Insert(medal);
		medal.medal_id = result.identity.intValue();
		MedalInfo medalInfo = new MedalInfo(connection, connection.getHero(),
				medal);
		return medalInfo;
	}

	// 穿饰品,如果有相同的类型不管数量是否穿戴满先换下同类型的饰品；如果没有相同类型的饰品：如果饰品数量还没有穿满，就创建一个新饰品；如果饰品数量已经穿满，就换第一个饰品下来
	public int onShipin(ItemInfo item) {
		int oldEquip = 0;
		CustomHeroEquip old = getConflictShiPin(item);
		if (old != null) {
			oldEquip = old.equipId;
			flushCustomEquip(old, item.getItemId());
			m_Connection.getItem().setItemOwner(oldEquip, 0);
		} else {
			if (getShiPinNum() < 3) {
				addEquip(item.getItemId(), getAccessoriesPart());
			} else {
				CustomHeroEquip equip = getShipin();
				oldEquip = equip.equipId;
				flushCustomEquip(equip, item.getItemId());
				m_Connection.getItem().setItemOwner(oldEquip, 0);
			}
		}
		item.setOwner(getHeroId());
		return oldEquip;
	}

	// 返回一个饰品
	private CustomHeroEquip getShipin() {
		for (CustomHeroEquip equip : getEquips()) {
			if (isShipin(equip.itemPart) && equip.itemPart == part) {
				part = part + 10;
				if (part > 25)
					part = 5;
				return equip;
			}
		}
		return null;
	}

	// 得到饰品可用的位置
	private int getAccessoriesPart() {
		List<Integer> allParts = new ArrayList<>();
		allParts.add(5);
		allParts.add(15);
		allParts.add(25);

		List<CustomHeroEquip> equips = getEquips();
		for (CustomHeroEquip pair : equips) {
			if (isShipin(pair.itemPart)) {
				allParts.remove((Integer) pair.itemPart);
			}
		}
		return allParts.get(0);
	}

	// 检查英雄是否已经装备了同一个tableId的饰品，或者是否装备了一个同一类型的饰品,如果有就返回，否则返回空
	private CustomHeroEquip getConflictShiPin(ItemInfo item) {
		MT_Data_Item itemData = item.getData();
		List<CustomHeroEquip> equips = getEquips();
		for (CustomHeroEquip equip : equips) {
			if (isShipin(equip.itemPart)) {
				ItemInfo tempItem = m_Connection.getItem().getItemById(
						equip.equipId);
				MT_Data_Item tempItemData = tempItem.getData();
				if (tempItem.getTableId() == item.getTableId())
					return equip;
				if (tempItemData.Jewelry().intValue() == itemData.Jewelry()
						.intValue())
					return equip;
			}
		}
		return null;
	}

	// 获得英雄的饰品的数量
	private int getShiPinNum() {
		int count = 0;
		for (CustomHeroEquip equip : getEquips()) {
			if (isShipin(equip.itemPart))
				count++;
		}
		return count;
	}

	private boolean isShipin(int itemPart) {
		return (itemPart == 5 || itemPart == 15 || itemPart == 25);
	}

	private void flushCustomEquip(CustomHeroEquip equip, int itemid) {
		equip.equipId = itemid;
		save();
	}

	private void addEquip(int itemid, int itempart) {
		CustomHeroEquip equip = new CustomHeroEquip();
		equip.equipId = itemid;
		equip.itemPart = itempart;
		addEquip(equip);
	}

	private void removeEquip(CustomHeroEquip oldEquip) {
		getEquips().remove(oldEquip);
		save();
	}

	// 穿普通装备
	public int onEquip(ItemInfo item) {
		MT_Data_Item itemData = item.getData();
		int oldItemId = 0;
		CustomHeroEquip oldEquip = getEquipByItemPart(item.getData().ItemPart());
		if (oldEquip == null) {
			addEquip(item.getItemId(), itemData.ItemPart());
		} else {
			m_Connection.getItem().setItemOwner(oldEquip.equipId, 0);
			oldItemId = oldEquip.equipId;
			flushCustomEquip(oldEquip, item.getItemId());
		}
		item.setOwner(getHeroId());
		return oldItemId;
	}

	// 查询指定位置上是否有装备
	private CustomHeroEquip getEquipByItemPart(int part) {
		List<CustomHeroEquip> equips = getEquips();
		for (CustomHeroEquip equip : equips) {
			if (equip.itemPart == part)
				return equip;
		}
		return null;
	}

	// 穿装备
	public int equip(ItemInfo item) {
		if (item.getData().ItemPart() == Common.EQUIPPART.Accessories.Number())
			return onShipin(item);
		else
			return onEquip(item);
	}

	// 脱装备
	public int unEquip(ItemInfo item) {
		CustomHeroEquip oldEquip = getByItemId(item.getItemId());
		if (oldEquip == null)
			return 0;
		removeEquip(oldEquip);
		item.setOwner(0);
		return oldEquip.equipId;
	}

	public CustomHeroEquip getByItemId(int itemid) {
		for (CustomHeroEquip temp : getEquips()) {
			if (temp.equipId == itemid)
				return temp;
		}
		return null;
	}

	// 取得物品是否已经被装备
	public boolean isUse(int item_Id) {
		List<CustomHeroEquip> equips = getEquips();
		for (CustomHeroEquip equip : equips) {
			if (equip.equipId == item_Id)
				return true;
		}
		return false;
	}

	public int getStar() {
		return m_Hero.star;
	}
	
	public int getLuckVal() {
		return m_Hero.luck_val;
	}
	
	public int addLuckVal() {
		int addVal = 1;
		m_Hero.luck_val += addVal;
		int index = getData().ID() * 10000 + getStar();
		MT_Data_HeroInforce conf = TableManager.GetInstance().TableHeroInforce().GetElement(index);
		if (m_Hero.luck_val >= conf.getM_nluckmax())
			m_Hero.luck_val = conf.getM_nluckmax();
		save();
		return addVal;
	}
	
	public void initLuckValByStarUpCount() {
		if (m_Hero.init_luck_val)
			return;
		m_Hero.init_luck_val = true;
		if (m_Hero.star_up_count > 0)
		{
			m_Hero.luck_val += m_Hero.star_up_count;
			int index = getData().ID() * 10000 + getStar();
			MT_Data_HeroInforce conf = TableManager.GetInstance().TableHeroInforce().GetElement(index);
			if (m_Hero.luck_val >= conf.getM_nluckmax())
				m_Hero.luck_val = conf.getM_nluckmax();
		}
		save();
	}
	
	public void addStarCount() {
		m_Hero.star_up_count++;
		save();
	}
	
	public int getStarCount() {
		return m_Hero.star_up_count;
	}

	public int starUp() {
		m_Hero.star++;
		m_Hero.star_up_count = 0;
		m_Hero.luck_val = 0;
		save();
		return m_Hero.star;
	}

	public int getLvl() {
		return m_Hero.hp_lvl + m_Hero.attack_lvl + m_Hero.defen_lvl;
	}

	public int getHpLvl() {
		return m_Hero.hp_lvl;
	}

	public int hpLvlUp() {
		m_Hero.hp_lvl++;
		save();
		return m_Hero.hp_lvl;
	}

	public int getAttackLvl() {
		return m_Hero.attack_lvl;
	}

	public int attackLvlUp() {
		m_Hero.attack_lvl++;
		save();
		return m_Hero.attack_lvl;
	}

	public int getDefenLvl() {
		return m_Hero.defen_lvl;
	}

	public int defenLvlUp() {
		m_Hero.defen_lvl++;
		save();
		return m_Hero.defen_lvl;
	}
}