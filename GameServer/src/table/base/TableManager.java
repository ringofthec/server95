package table.base;
import gameserver.config.TableConfig;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.utils.UtilItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.Int6;
import table.MT_Data_DropOut;
import table.MT_Data_Exp;
import table.MT_Data_GBJLStraight;
import table.MT_Data_GFSkill;
import table.MT_Data_GGiftPackageShop;
import table.MT_Data_GGiftShop;
import table.MT_Data_GNiuCardStraight;
import table.MT_Data_GRechargePolicy;
import table.MT_Data_GSlotsBackRate;
import table.MT_Data_GSlotsFriutActiveReward;
import table.MT_Data_GSlotsGuess;
import table.MT_Data_GSlotsLine;
import table.MT_Data_GSlotsLotty;
import table.MT_Data_GSlotsOpen;
import table.MT_Data_GSlotsRandom;
import table.MT_Data_GSlotsResult;
import table.MT_Data_GStoreItem;
import table.MT_Data_HeroCall;
import table.MT_Data_HeroInforce;
import table.MT_Data_Instance;
import table.MT_Data_Item;
import table.MT_Data_Language;
import table.MT_Data_Rank;
import table.MT_Data_Task;
import table.MT_TableManager;
import table.TableUtil;

import com.commons.util.RandomUtil;
import com.commons.util.string;

import commonality.ARMOR_TYPE;
import commonality.ATTACK_TYPE;

public class TableManager extends MT_TableManager {
	private static final Logger logger = LoggerFactory.getLogger(MT_TableManager.class);
	private final static TableManager instance = new TableManager();
	public static TableManager GetInstance() { return instance; }
	//玩家最大等级
	private int m_maxPlayerLevel;
	//最大军衔
	private int m_maxRank;
	//最大扩地次数
	private int m_maxLand;
	private int m_max_hero_path;
	private int m_max_login_reward;
	//最大强化数
	private int m_max_item_strengthen;
	//最大英雄星级
	private int m_max_hero_star;
	
	private int m_max_g_level;
	private int m_max_g_viplevel;
	private FastMap<Integer, List<Integer>> m_slot_lists 
					= new FastMap<Integer, List<Integer>>();
	
	private FastList<Integer> lines_1 = new FastList<Integer>();
	private FastList<Integer> lines_3 = new FastList<Integer>();
	private FastList<Integer> lines_10 = new FastList<Integer>();
	private FastList<Integer> lines_30 = new FastList<Integer>();
	
	private FastList<Integer> slot_lotty_games = new FastList<Integer>();
	
	private FastList< MT_Data_GSlotsOpen > slot_open_games 
					= new FastList< MT_Data_GSlotsOpen >();
	
	private FastList<Integer> slot_guess_games = new FastList<Integer>();
	
	private FastMap<Integer, Integer> skill_num_limit = new FastMap<Integer, Integer>();
	private FastMap<Integer, Integer> skill_cost = new FastMap<Integer, Integer>();
	
	private FastMap<Integer, Integer> gift_to_like = new FastMap<Integer, Integer>();
	
	private FastMap<Integer, ArrayList<Integer>> rate_to_id = new FastMap<Integer, ArrayList<Integer>>();
	
	private FastMap<Integer, ArrayList<Integer>> money_and_line_new_policy 
			= new FastMap<Integer, ArrayList<Integer>>();
	private ArrayList<Integer> money_and_line_new_policy_money = new ArrayList<Integer>();
	
	private List<Integer> all_v_items = new ArrayList<Integer>();
	
	private List<Integer> nn_card_rate = new ArrayList<Integer>();
	
	private FastMap<Integer, MT_Data_GRechargePolicy> all_recharge_policy = new FastMap<Integer, MT_Data_GRechargePolicy>();
	
	private FastMap<Integer, FastMap<Integer, Integer> > bjl_bupai_starge = new FastMap<Integer, FastMap<Integer,Integer>>();
	
	//任务链 任务链ID以任务链的第一个任务ID为Key
	private Map<Integer, List<Integer>> m_tasks = new HashMap<Integer, List<Integer>>();
	private Map<Integer, Integer> m_instanceLink = new FastMap<Integer, Integer>();
	private TableManager()
	{
		Initialize(true);
	}
	public int getMaxGLevel() {
		return m_max_g_level;
	}
	public int getMaxGVipLevel() {
		return m_max_g_viplevel;
	}
	public List<Integer> getGSlotsList(int policy_id) {
		return m_slot_lists.get(policy_id);
	}
	public List<Integer> getNiuniuCardRan() {
		return nn_card_rate;
	}

	private HashMap<String, Long> m_TableTimeMap = new HashMap<>();

	private long getTableLastModTime(String str) {
		String name = TableConfig.tablePath + File.separatorChar + str
				+ TableConfig.tableSuffix;
		File file = new File(name);
		return file.lastModified();
	}

	private void setTableTime(String str) {
		long lastModTime = getTableLastModTime(str);
		if (lastModTime == 0)
			return;
		m_TableTimeMap.put(str, lastModTime);
	}

	public void initTableTimeMap() {
		File file = new File(TableConfig.tablePath);
		for (String fn : file.list()) {
			File f2 = new File(file.getAbsolutePath(), fn);
			String name = f2.getName();
			setTableTime(name.substring(0, name.lastIndexOf(".")));
		}
	}

	public void checkTableTime() {
		Iterator<Entry<String, Long>> iter = m_TableTimeMap.entrySet()
				.iterator();
		boolean ischange = false;
		while (iter.hasNext()) {
			Entry<String, Long> entry = iter.next();
			String name = entry.getKey();
			long oldFileTime = entry.getValue();
			long nowFileTime = getTableLastModTime(name);
			if (nowFileTime == oldFileTime)
				continue;
			pushModifiedTable(name);
			m_TableTimeMap.put(name, nowFileTime);
			ischange =true;
		}
		
		if (ischange)
			Initialize(false);
	}
	
	public List<Integer> getLineInfo(int linenum) {
		if (linenum == 1)
			return lines_1;
		else if (linenum == 3)
			return lines_3;
		else if (linenum == 10)
			return lines_10;
		else if (linenum == 30)
			return lines_30;
		
		return lines_1;
	}
	public List<Integer> getSlotLottryRateList() {
		return slot_lotty_games;
	}
	public List<Integer> getSlotGuessRateList() {
		return slot_guess_games;
	}
	public FastList< MT_Data_GSlotsOpen > getSlotOpenRateLists() {
		return slot_open_games;
	}
	public MT_Data_GSlotsFriutActiveReward getFriutActiveRewardReward(int lvl, int order) {
		for (MT_Data_GSlotsFriutActiveReward ddd : TableGSlotsFriutActiveReward().Datas().values()) {
			if (ddd.level() == lvl && ddd.order() == order)
				return ddd;
		}
		
		return null;
	}
	public static class AppStoreInfo {
		public MT_Data_GGiftPackageShop m_giftShop;
		public MT_Data_GStoreItem m_storeItem;
	}
	public AppStoreInfo getConfigByItemInfo(String iteminfo) {
		AppStoreInfo appp = new AppStoreInfo();
		
		for (MT_Data_GGiftPackageShop gd : TableGGiftPackageShop().Datas().values()) {
			if (iteminfo.indexOf(gd.app_item()) != -1) {
				appp.m_giftShop = gd;
				return appp;
			}
		}
		
		for (MT_Data_GStoreItem gd : TableGStoreItem().Datas().values()) {
			if (iteminfo.indexOf(gd.app_item()) != -1) {
				appp.m_storeItem = gd;
				return appp;
			}
		}
		
		return null;
	}
	public List<Integer> getAllVItemId() {
		return all_v_items;
	}
	public int getSkillLimit(int skillid) {
		if (skill_num_limit.containsKey(skillid))
			return skill_num_limit.get(skillid);
		
		return -1;
	}
	public int getSkillCost(int skillid) {
		if (skill_cost.containsKey(skillid))
			return skill_cost.get(skillid);
		
		return -1;
	}
	public int getGiftFan(int itemTempid) {
		if (gift_to_like.containsKey(itemTempid))
			return gift_to_like.get(itemTempid);
		return 0;
	}
	public MT_Data_GRechargePolicy getTargetPolicy(int gameid, int preMoney) {
		int mask = gameid * 1000 * 10000 + preMoney;
		return all_recharge_policy.get(mask);
	}
	public int getMaxLoginReward() {
		return m_max_login_reward;
	}
	public int getBJLaddCardStrage(int z, int x) {
		return bjl_bupai_starge.get(x).get(z);
	}
	public ArrayList<Integer> getRateRes(int rate) {
		if (rate_to_id.containsKey(rate)) {
			int id = rate_to_id.get(rate).get(RandomUtil.RangeRandom(0, rate_to_id.get(rate).size() - 1));
			return TableGSlotsResult().GetElement(id).res();
		}
		
		return null;
	}
	
	// 根据每线押注数 和 充值返利 获取返回的倍率
	public int getBackRate(int money, long backmoney) {
		if (money < 100)
			money = 100;
		ArrayList<Integer> list = money_and_line_new_policy.get(money);
		int count = money_and_line_new_policy_money.size();
		for (int i = count - 1; i >= 0; ++i) {
			if (backmoney >= money_and_line_new_policy_money.get(i)) {
				return list.get(i);
			}
		}
		
		return -1;
	}
	private void Initialize(boolean isboot)
	{
		TableUtil.SetUtil(new TableDispose());
		 {
			 m_max_g_level = TableGExp().Count() + 1;
	         m_max_g_viplevel = TableGVip().Count();
	         m_max_login_reward = TableGCLoginReward().Count();
	     }
		 
		 List<Integer> all_v_items_t = new ArrayList<Integer>();
		 for (int i = 0; i < 200; ++i) {
			 if (TableGItem().Contains(i))
				 all_v_items_t.add(i);
		 }
		 all_v_items = all_v_items_t;
		 
		 FastMap<Integer, Integer> skill_num_limit_t = new FastMap<Integer, Integer>();
		 FastMap<Integer, Integer> skill_cost_t = new FastMap<Integer, Integer>();
		 for (MT_Data_GFSkill sk : TableGFSkill().Datas().values()) {
			 skill_num_limit_t.put(sk.id(), sk.maxCount());
			 skill_cost_t.put(sk.id(), sk.costId());
		 }
		 skill_num_limit = skill_num_limit_t;
		 skill_cost = skill_cost_t;
		 
		 FastMap<Integer, Integer> gift_to_like_t = new FastMap<Integer, Integer>();
		 for (MT_Data_GGiftShop cofig : TableGGiftShop().Datas().values()) {
			 gift_to_like_t.put(cofig.itemTempId(), cofig.fans());
		 }
		 gift_to_like = gift_to_like_t;
		 
		 FastMap<Integer, ArrayList<Integer>> rate_to_id_t = new FastMap<Integer, ArrayList<Integer>>();
		 for (MT_Data_GSlotsResult re : TableGSlotsResult().Datas().values()) {
			 if (!rate_to_id_t.containsKey(re.rate())) {
				 rate_to_id_t.put(re.rate(), new ArrayList<Integer>());
			 }
			 
			 ArrayList<Integer> l = rate_to_id_t.get(re.rate());
			 l.add(re.id());
		 }
		 rate_to_id = rate_to_id_t;
		 
		 FastMap<Integer, ArrayList<Integer>> money_and_line_new_policy_t 
			= new FastMap<Integer, ArrayList<Integer>>();
		 ArrayList<Integer> money_and_line_new_policy_money_t = new ArrayList<Integer>();
		 int[] all_line_money = {100, 200, 500, 1000, 2000, 5000, 10000};
		 for (int mmmmmm : all_line_money) {
			 money_and_line_new_policy_t.put(mmmmmm, new ArrayList<Integer>());
		 }
		 for (MT_Data_GSlotsBackRate baccc : TableGSlotsBackRate().Datas().values()) {
			 money_and_line_new_policy_money_t.add(baccc.backmoney());
			 
			 money_and_line_new_policy_t.get(100).add(baccc.money100());
			 money_and_line_new_policy_t.get(200).add(baccc.money200());
			 money_and_line_new_policy_t.get(500).add(baccc.money500());
			 money_and_line_new_policy_t.get(1000).add(baccc.money1000());
			 money_and_line_new_policy_t.get(2000).add(baccc.money2000());
			 money_and_line_new_policy_t.get(5000).add(baccc.money5000());
			 money_and_line_new_policy_t.get(10000).add(baccc.money10000());
			 
		 }
		 money_and_line_new_policy = money_and_line_new_policy_t;
		 money_and_line_new_policy_money = money_and_line_new_policy_money_t;
		 
		 FastMap<Integer, List<Integer>> m_slot_lists_t = new FastMap<Integer, List<Integer>>();
		 for ( MT_Data_GSlotsRandom slot_policy_config : TableGSlotsRandom().Datas().values() ) {
			 List<Integer> li = new ArrayList<Integer>();
			 
			 li.add(slot_policy_config.item1());
			 li.add(slot_policy_config.item2());
			 li.add(slot_policy_config.item3());
			 li.add(slot_policy_config.item4());
			 li.add(slot_policy_config.item5());
			 li.add(slot_policy_config.item6());
			 li.add(slot_policy_config.item7());
			 li.add(slot_policy_config.item8());
			 li.add(slot_policy_config.item9());
			 li.add(slot_policy_config.item10());
			 li.add(slot_policy_config.item11());
			 li.add(slot_policy_config.item12());
			 
			 m_slot_lists_t.put(slot_policy_config.id(), li);
		 }
		 m_slot_lists = m_slot_lists_t;
		 
		 FastList<Integer> lines_1_t = new FastList<Integer>();
		 FastList<Integer> lines_3_t = new FastList<Integer>();
		 FastList<Integer> lines_10_t = new FastList<Integer>();
		 FastList<Integer> lines_30_t = new FastList<Integer>();
		 for (MT_Data_GSlotsLine line : TableGSlotsLine().Datas().values()) {
			 if (line.group().contains(1))
				 lines_1_t.add(line.lineid());
			 
			 if (line.group().contains(3))
				 lines_3_t.add(line.lineid());
			 
			 if (line.group().contains(10))
				 lines_10_t.add(line.lineid());
			 
			 if (line.group().contains(30))
				 lines_30_t.add(line.lineid());
		 }
		 lines_1 = lines_1_t;
		 lines_3 = lines_3_t;
		 lines_10 = lines_10_t;
		 lines_30 = lines_30_t;
		 
		 FastList<Integer> slot_lotty_games_t = new FastList<Integer>();
		 for (MT_Data_GSlotsLotty lo : TableGSlotsLotty().Datas().values()) {
			 slot_lotty_games_t.add(lo.svr_rate());
		 }
		 slot_lotty_games = slot_lotty_games_t;
		 
		 FastList<Integer> slot_guess_games_t = new FastList<Integer>();
		 for (MT_Data_GSlotsGuess lo : TableGSlotsGuess().Datas().values()) {
			 slot_guess_games_t.add(lo.svr_rate());
		 }
		 slot_guess_games = slot_guess_games_t;
		 
		 FastList< MT_Data_GSlotsOpen > slot_open_games_t = new FastList< MT_Data_GSlotsOpen >();
		 for (MT_Data_GSlotsOpen lo : TableGSlotsOpen().Datas().values()) {
			 slot_open_games_t.add(lo);
		 }
		 slot_open_games = slot_open_games_t;
		 
		 FastMap<Integer, MT_Data_GRechargePolicy> all_recharge_policy_t = new FastMap<Integer, MT_Data_GRechargePolicy>();
		 for (MT_Data_GRechargePolicy lo: TableGRechargePolicy().Datas().values()) {
			 int mask = lo.gameid() * 1000 * 10000 + lo.pre_money();
			 all_recharge_policy_t.put(mask, lo);
		 }
		 all_recharge_policy = all_recharge_policy_t;
		 
		 List<Integer> nn_card_rate_t = new ArrayList<Integer>();
		 for (MT_Data_GNiuCardStraight gns : TableGNiuCardStraight().Datas().values()) {
			 nn_card_rate_t.add(gns.ran());
		 }
		 nn_card_rate = nn_card_rate_t;
		 
		 
		 FastMap<Integer, FastMap<Integer,Integer>> bjl_bupai_starge_t = new FastMap<Integer, FastMap<Integer,Integer>>();
		 for (MT_Data_GBJLStraight gslll : TableGBJLStraight().Datas().values()) {
			 FastMap<Integer, Integer> ttt = new FastMap<Integer, Integer>();
			 
			 ttt.put(0, gslll.z0());
			 ttt.put(1, gslll.z1());
			 ttt.put(2, gslll.z2());
			 ttt.put(3, gslll.z3());
			 ttt.put(4, gslll.z4());
			 ttt.put(5, gslll.z5());
			 ttt.put(6, gslll.z6());
			 ttt.put(7, gslll.z7());
			 ttt.put(8, gslll.z8());
			 ttt.put(9, gslll.z9());
			 
			 bjl_bupai_starge_t.put(gslll.id(), ttt);
		 }
		 bjl_bupai_starge = bjl_bupai_starge_t;
		 
		 if (isboot)
			 initTableTimeMap();
	}
	private void Initialize1()
	{
		TableUtil.SetUtil(new TableDispose());
		{
			int level = 1;
			while (true)
			{
				MT_Data_Exp data = TableExp().GetElement(level);
				if (data == null || TABLE.IsInvalid(data.exp()))
					break;
				++level;
			}
			m_maxPlayerLevel = level;
			logger.info("角色最大等级:{}", m_maxPlayerLevel);
		}
		{
			int rank = 0;
			while (true)
			{
				MT_Data_Rank data = TableRank().GetElement(rank);
				if (data == null || TABLE.IsInvalid(data.Exp()))
					break;
				++rank;
			}
			m_maxRank = rank;
			logger.info("角色最高军衔:{}", m_maxRank);
		}
        {
            m_maxLand = TableLand().Count();
            logger.info("最大扩地次数:{}", m_maxLand);
        }
        {
        	m_max_item_strengthen = TableItemStrengthen().Count() - 1;
        	logger.info("最大强化等级:{}", m_max_item_strengthen);
        }
        {
        	m_max_hero_path = TableHeroPath().Count() - 1;
        }
        {
        	m_max_hero_star = TableHeroInforce().Count() / 3;
        	logger.info("最大英雄星级:{}", m_max_hero_star);
        }
        {
        	List<Integer> usedTaskID = new ArrayList<>();
        	Collection<MT_Data_Task> set = TableTask().Datas().values();
        	for (MT_Data_Task task : set)
        	{
        		List<Integer> link = new ArrayList<>();
        		if (task.IsFirst() == true)
        		{
        			if (usedTaskID.contains(task.ID()))
        			{
        				logger.error("此任务ID:{} 已经在其他任务链中使用过 是不是填错了 亲！！！",task.ID());
        				continue;
        			}
        			link.add(task.ID());
        			MT_Data_Task linkData = task;
        			while (linkData != null && !TABLE.IsInvalid(linkData.NextID()))
					{
        				MT_Data_Task nextTask = TableTask().GetElement(linkData.NextID());
        				if (nextTask != null)
        				{
        					if (usedTaskID.contains(nextTask.ID()))
                			{
                				logger.error("此任务ID:{} 已经在其他任务链中使用过 是不是填错了 亲！！！",nextTask.ID());
                				break;
                			}
        					link.add(linkData.NextID());
        				}
        				else
        				{
        					logger.error("任务 {} 的后续任务ID  {} 不存在",linkData.ID(),linkData.NextID());
        					break;
        				}
        				linkData = nextTask;
					}
        			usedTaskID.addAll(link);
        			logger.info("添加任务链:{}  任务个数:{}",task.ID(),link.size());
        			m_tasks.put(task.ID(), link);
        		}
        	}
        }
        
        {
        	m_max_g_level = TableGExp().Count() + 1;
        	m_max_g_viplevel = TableGVip().Count();
        }
        
        {
        	boolean exit = false;
        	for (MT_Data_Item item : TableItem().Datas().values()) {
        		int heroMask = 0;
        		for (Int2 dropconf : item.DropOut()) {
        			if (dropconf.field1() != 0) {
        				heroMask |= 1 << dropconf.field1();
        			}
        		}
        		if (heroMask != 0 && heroMask != 0x0e) {
        			logger.error("Item表中的id为{}的掉落配置不对", item.ID());
        			exit = true;
        		}
        	}
        	
        	for (MT_Data_Instance item : TableInstance().Datas().values()) {
        		int heroMask = 0;
        		for (Int2 dropconf : item.DropOut()) {
        			if (dropconf.field1() != 0) {
        				heroMask |= 1 << dropconf.field1();
        			}
        		}
        		if (heroMask != 0 && heroMask != 0x0e) {
        			logger.error("Instance表中的id为{}的掉落配置不对", item.ID());
        			exit = true;
        		}
        	}
        	
        	if (exit)
        		System.exit(-1);
        }
        
        {
        	HashMap<Integer, MT_Data_Instance> datas = TableInstance().Datas();
        	TreeMap<Integer, MT_Data_Instance> treeMap = new TreeMap<Integer, MT_Data_Instance>();
        	for (Entry<Integer, MT_Data_Instance> pair : datas.entrySet()) 
        		treeMap.put(pair.getKey(), pair.getValue());

        	Iterator<Integer> iter = treeMap.keySet().iterator();
        	int curId = iter.next();
        	int nextId = curId;
        	while (iter.hasNext()) {
        		nextId = iter.next();
        		m_instanceLink.put(curId, nextId);
        		curId = nextId;
        	}
        }
        
        // 初始化多国语言
        InitializeLanguage();
        
        initMap();
	}
	public int getMaxHeroPath() {
		return m_max_hero_path;
	}
	public int getNextInstanceId(int curId) {
		if (m_instanceLink.containsKey(curId))
			return m_instanceLink.get(curId);
		return curId;
	}
	/** 获得玩家的最大等级 */
	public int getMaxPlayerLevel()
	{
		return m_maxPlayerLevel;
	}
	/** 获得玩家的最大军衔 */
	public int getMaxRank()
	{
		return m_maxRank;
	}
	/** 获得最大扩地次数 */
	public int getMaxLand()
	{
		return m_maxLand;
	}
	public int getMaxItemStrengthen() {
		return m_max_item_strengthen;
	}
	public int getHeroMaxStar() {
		return m_max_hero_star;
	}
	/** 获得所有任务链 */
	public Map<Integer, List<Integer>> getTasks()
	{
		return m_tasks;
	}
	public int getMaxHeroLvl(MT_Data_HeroInforce hi) {
		return hi.attacklimit() + hi.defenlimit() + hi.hplimit();
	}
	/** 获得任务ID的任务链 */
	public List<Integer> getTaskLink(int taskID)
	{
		Collection<List<Integer>> task = m_tasks.values();
		for (List<Integer> pair : task)
		{
			if (pair.contains(taskID))
				return pair;
		}
		return null;
	}
	/** 是否包含任务链ID */
	public boolean containLink(int taskID)
	{
		return m_tasks.containsKey(taskID);
	}
	/**
	 * 根据DropOut的index获得物品的id和数量
	 * @param dropId
	 * @return
	 * @throws Exception 
	 */

	//掉落组id ： 所有掉落组列表.某个类型对应一个掉落组列表
//	private Map<Integer, List<Int6>> map =  new HashMap<>();//普通关卡
//	private Map<Integer, List<Int6>> bossMap =  new HashMap<>();//boss关卡
	public List<UtilItem> getDropOut(ConPlayerAttr player, ArrayList<Int2> dropId) throws Exception{
		int playerLevel = player.getLevel();
		HeroInfo hi = player.getCon().getHero().getRandomHero();
		int heroTableId = -1;
		if (hi != null) heroTableId = hi.getTableId();
		
		//初始化掉落组map
		List<UtilItem> dropOuts = new ArrayList<UtilItem>();
		for (Int2 dropconf : dropId) {
			if (dropconf.field1() == 0 ||  // 无视英雄的掉落
					dropconf.field1() == heroTableId) {  // 英雄必须匹配
				dropOuts.addAll(getDropOut(playerLevel, dropconf.field2()));
			}
		}
		return dropOuts;
	}
	public List<UtilItem> getDropOut(int playerLevel, int dropId) throws Exception{
		List<UtilItem> dropOuts = new ArrayList<UtilItem>();
		Map<Integer, List<Int6>> curMap = getDropMapByDropId(dropId);
		//遍历map里面所有掉落组的类型
		for (Map.Entry<Integer, List<Int6>> entry : curMap.entrySet()) {
			List<Int6> drpOutsInt6s = entry.getValue();
			List<Int6> lvEnough = new ArrayList<>();
			Integer totalRandom = 0;
			for (Int6 drpOut : drpOutsInt6s) {
				if (playerLevel >= drpOut.field5() && playerLevel <= drpOut.field6()) {
					lvEnough.add(drpOut);
					totalRandom += drpOut.field3();
				}
			}
			if (totalRandom == 0) 
				continue;
			//随机数
			int random = new Random().nextInt(10000);
			//随机数落在概率总和的范围里面--说明已经随机到这个类型的一个物品
			if (random<=totalRandom) {
				Integer selectedtableId = 0;
				Integer selectedNum = 0;
				Integer begin = 0;
				Integer sum = 0;
				for (int k =0;k<drpOutsInt6s.size();k++){
					Int6 int6 = drpOutsInt6s.get(k);
					Integer r = int6.field3();//某一行的概率值
					sum = r+begin;
					if (random>=begin&&random<=sum) {
						selectedtableId = int6.field1();
						selectedNum = int6.field2();
						break;
					}
					begin = sum;
				}
				UtilItem utilItem = new UtilItem(selectedtableId,selectedNum);
				dropOuts.add(utilItem);
			}
		}
		return dropOuts;
	}
	private FastMap<Integer, FastMap<Integer, List<Int6>>> m_dropMap = new FastMap<Integer, FastMap<Integer,List<Int6>>>();  // <dropId， <组id，组列表>>
	private FastMap<Integer, List<Int6>> getDropMapByDropId(int dropId) {
		return m_dropMap.get(dropId);
	}
	private void initMap() {
		for (MT_Data_DropOut dropOutData : TableDropOut().Datas().values()) {
			FastMap<Integer, List<Int6>> dropMap = new FastMap<>();
			
	        ArrayList<Int6> int6s = dropOutData.Arrays();
	  		for (Int6 oneDropOut : int6s) {
	  			if (TABLE.IsInvalid(oneDropOut))
					continue;
	  			if (!dropMap.containsKey(oneDropOut.field4())) {
	  				List<Int6> oneTypeInt6s = new ArrayList<>();
	  				oneTypeInt6s.add(oneDropOut);
	  				dropMap.put(oneDropOut.field4(), oneTypeInt6s);
	  			}else {
	  				dropMap.get(oneDropOut.field4()).add(oneDropOut);
	  			}
	  		}
	  		m_dropMap.put(dropOutData.ID(), dropMap);
		}
	}

	private HashMap<String, HashMap<String, String>> m_Languages = new HashMap<String, HashMap<String, String>>();
    private String[] m_Language = {"CN","EN"};
    private void InitializeLanguage()
    {
        m_Languages.clear();
        
        for (String language : m_Language) {
        	HashMap<String, String> curLang = new HashMap<String, String>();
        	Collection<MT_Data_Language> spawns = getSpawns_Language(language).Datas().values();
        	for (MT_Data_Language pair : spawns)
        	{
        		for (String key : pair.Key())
        			curLang.put(key, pair.Text());
        	}
        	m_Languages.put(language, curLang);
        }
    }
    public int getLanguageId(String language) {
    	for (int i = 0; i < m_Language.length; ++i) {
    		if (m_Language[i].equals(language))
    			return i;
    	}
    	
    	return 0;
    }
    public String GetLanguageText(String lang, String key) {
    	//if (m_Languages.get(lang).containsKey(key))
        //    return m_Languages.get(lang).get(key);
        return "";
    }
    public String GetLanguageText(String key) {
    	return GetLanguageText("CN", key);
    }
    public String ReadLString(String lang, String fileName,String lineName, int id) {
		return GetLanguageText(lang, string.Format("{}_{}_{}", fileName, lineName, id));
	}
    public String ReadLString(int langId, String fileName,String lineName, int id) {
		return GetLanguageText(m_Language[langId], string.Format("{}_{}_{}", fileName, lineName, id));
	}
    public boolean isCanSelectNewHero(int playerlevel) {
    	for (MT_Data_HeroCall hc : TableManager.GetInstance().TableHeroCall().Datas().values()) {
    		if (hc.levelneed() == playerlevel)
    			return true;
    	}
    	return false;
    }
    /// <summary>
    /// 根据攻击类型获得克制的防御类型
    /// </summary>
    /// <param name="type"></param>
    /// <returns></returns>
    public int GetRestrainByAtkType(int type)
    {
    	if (type == ATTACK_TYPE.IMPALE.Number())
    		return ARMOR_TYPE.STRONG.Number();
    	else if (type == ATTACK_TYPE.POWER.Number())
    		return ARMOR_TYPE.COMPLEX.Number();
    	else if (type == ATTACK_TYPE.HERO.Number())
    		return -1;
    	else if (type == ATTACK_TYPE.BIGBANG.Number())
    		return ARMOR_TYPE.LIGHT.Number();

        return -1;
    }
}
