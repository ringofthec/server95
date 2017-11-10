package gameserver.connection.attribute;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.network.server.connection.Connection;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import table.MT_Data_LegionStore;
import table.MT_Data_legionConfig;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;

import commonality.Common;
import database.CustomLegionCommodity;
import databaseshare.CustomLegionMeber;
import databaseshare.DatabaseLegion;

public class LegionManager  {
	private final static LegionManager instance = new LegionManager();
	public static LegionManager getInstance() { return instance; }
	
	private Map<String,Integer> legionStoreRandMap  = new HashMap<String,Integer>();//军团商店 type随机值
	private Map<String,TreeMap<Integer,MT_Data_LegionStore>> treeMaps  = new HashMap<String,TreeMap<Integer,MT_Data_LegionStore>>();//军团商店 type:区间 如: A :0-10
	
	public void init() {
		initLegionStoreMap();
	}
	
	private void initLegionStoreMap() {
		// 1. 计算各组别的区间段
		HashMap<Integer,MT_Data_LegionStore> legionStores = TableManager.GetInstance().TableLegionStore().Datas();
		MT_Data_legionConfig legionConfig = TableManager.GetInstance().TablelegionConfig().GetElement(1);
		String types = legionConfig.curType().get(0);
		String[] strs = types.split(":");
		int len = strs.length;
		for (int i =0;i<len;i++){
			String type = strs[i];
			for (MT_Data_LegionStore legionStore : legionStores.values()){
				if (legionStore.Type().equals(type)&&!TABLE.IsInvalid(legionStore.typeRadom())){
					legionStoreRandMap.put(type, legionStore.typeRadom());
					break;
				}
			}
		}
		
		// 2. 
		HashMap<Integer,MT_Data_LegionStore> legionStoreMap = TableManager.GetInstance().TableLegionStore().Datas();
		TreeMap<Integer,MT_Data_LegionStore> treeMapA = new TreeMap<>();
		TreeMap<Integer,MT_Data_LegionStore> treeMapB = new TreeMap<>();
		TreeMap<Integer,MT_Data_LegionStore> treeMapC = new TreeMap<>();
		TreeMap<Integer,MT_Data_LegionStore> treeMapD = new TreeMap<>();
		TreeMap<Integer,MT_Data_LegionStore> treeMapE = new TreeMap<>();
		
		for (Map.Entry<Integer,MT_Data_LegionStore> legionStore : legionStoreMap.entrySet()) {
			if ("A".equals(legionStore.getValue().Type()))  {
				treeMapA.put(legionStore.getKey(), legionStore.getValue());
			}
			
			if ("B".equals(legionStore.getValue().Type())) 
				treeMapB.put(legionStore.getKey(), legionStore.getValue());
			
			if ("C".equals(legionStore.getValue().Type())) 
				treeMapC.put(legionStore.getKey(), legionStore.getValue());
			
			if ("D".equals(legionStore.getValue().Type())) 
				treeMapD.put(legionStore.getKey(), legionStore.getValue());
			
			if ("E".equals(legionStore.getValue().Type())) 
				treeMapE.put(legionStore.getKey(), legionStore.getValue());
			
		}
		treeMaps.put("A", treeMapA);
		treeMaps.put("B", treeMapB);
		treeMaps.put("C", treeMapC);
		treeMaps.put("D", treeMapD);
		treeMaps.put("E", treeMapE);
	}
	
	public List<CustomLegionCommodity> legionStoreList(Connection connection, int isHandleFush) throws ParseException {
		ConPlayerAttr playerAttribute = connection.getPlayer();
		// 手动刷新就刷一次
		if (isHandleFush==1)
			return refresh(isHandleFush,playerAttribute,connection);
		// 如果上次刷新后没有超时，直接返回.自动刷新把当前时间把大5毫秒保证肯定能刷新
		long now = TimeUtil.GetDateTime();
		if ((now - playerAttribute.getRefreshTime()) < 2 * Common.ONE_HOUR_MILLISECOND)
			return playerAttribute.getlegion_store_list();
		
		// 超过8小时了，刷一次
		updatePlayerRefreshTime(playerAttribute,now);
		return refresh(isHandleFush,playerAttribute,connection);
	}

	//刷出一个新的，数量为6的商品列表
	private List<CustomLegionCommodity> refresh(int isHandleFush, ConPlayerAttr playerAttribute,Connection connection) throws ParseException {
		List<MT_Data_LegionStore> selecteds = new ArrayList<MT_Data_LegionStore>();
		
		// 先计算ABCD类别商品的出现情况
		MT_Data_LegionStore selectedA = getLegionStoreByType("A",legionStoreRandMap,treeMaps.get("A"),false,connection);
		MT_Data_LegionStore selectedB = getLegionStoreByType("B",legionStoreRandMap,treeMaps.get("B"),false,connection);
		MT_Data_LegionStore selectedC = getLegionStoreByType("C",legionStoreRandMap,treeMaps.get("C"),false,connection);
		MT_Data_LegionStore selectedD = getLegionStoreByType("D",legionStoreRandMap,treeMaps.get("D"),false,connection);
	
		if (selectedA!=null)selecteds.add(selectedA);
		if (selectedB!=null)selecteds.add(selectedB);
		if (selectedC!=null)selecteds.add(selectedC);
		if (selectedD!=null)selecteds.add(selectedD);
		
		// 如果商品数量没有达到6个，就用E类型商品填充
		while(selecteds.size()<Common.LEGIONSTORESIZE) {
			MT_Data_LegionStore selectedE = getLegionStoreByType("E",legionStoreRandMap,treeMaps.get("E"),false,connection);
			if (selectedE != null && !isExist(selecteds, selectedE))
				selecteds.add(selectedE);
		}
		Collections.shuffle(selecteds);
		
		// 计算luck值
		//calcLuck(playerAttribute,isHandleFush,legionConfig);
		
		// 保存新的商品列表到玩家身上
		playerAttribute.fushLegion_store_list(selecteds);
		return playerAttribute.getlegion_store_list();
	}
	
	// 在指定类别的商品中随机一个商品，可能发生必出或者没有随到的情况
	private MT_Data_LegionStore getLegionStoreByType(String type, Map<String,Integer> storeTypeMap, TreeMap<Integer, MT_Data_LegionStore> treeMap, boolean b,Connection connection) {
		int[] typeQujian;
		if (("A".equals(type)&&b)||("B".equals(type)&&b)||("E".equals(type))){
			typeQujian = getEquipByType(type,treeMap,connection);
			return getSelected(treeMap,typeQujian, 0,connection);
		}
		
		int rand = storeTypeMap.get(type);
		int rnd  = new Random().nextInt(10000);
		if(rnd < rand){
			typeQujian = getEquipByType(type,treeMap,connection);
			return getSelected(treeMap,typeQujian,0,connection);
		}
		return null;
	}
	
	private void calcLuck(ConPlayerAttr playerAttribute,int isHandleFush,MT_Data_legionConfig legionConfig) {
		//无论手动还是自动都要加luck值
	    playerAttribute.icLuckVal();
		if (playerAttribute.getluck_val()> legionConfig.luckVal().field3())
			playerAttribute.setLuckVal(1);
	}
	
	private boolean isLuckA(MT_Data_legionConfig legionConfig,Connection connection) {
		return connection.getPlayer().getluck_val() == legionConfig.luckVal().field3();
	}
	
	private boolean isLuckB(MT_Data_legionConfig legionConfig,Connection connection) {
		return connection.getPlayer().getluck_val() == legionConfig.luckVal().field1() ||
				connection.getPlayer().getluck_val() == legionConfig.luckVal().field2();
	}
	
	private int[] getEquipByType(String type,TreeMap<Integer, MT_Data_LegionStore> treeMap,Connection connection) {
		//取得玩家自己所有的英雄，随机选出对应的装备
		int sum = 0;
		int[] arr = new int[2];
		//自己拥有的英雄tableIds
		List<Integer> heroIds = new ArrayList<>();
		heroIds.add(0);
		Map<Integer, HeroInfo> heroInfos = connection.getHero().getHeros();
		for (HeroInfo heroInfo : heroInfos.values()) 
			heroIds.add( heroInfo.getHero().hero_table_id);
		
		//如果装备是通用装备或者适合我的英雄
		for (MT_Data_LegionStore mt_Data_LegionStore : treeMap.values()) {
			if (heroIds.contains(mt_Data_LegionStore.belong())) 
				sum = sum + mt_Data_LegionStore.probability();
		}
		arr[0] = 0;
		arr[1] = sum;
		return arr;
	}
	
	private MT_Data_LegionStore getSelected(TreeMap<Integer, MT_Data_LegionStore> treeMap, int[] arr, int random,Connection connection) {
		List<Integer> heroIds = new ArrayList<>();
		heroIds.add(0);
		Map<Integer, HeroInfo> heroInfos = connection.getHero().getHeros();
		for (HeroInfo heroInfo : heroInfos.values()) 
			heroIds.add( heroInfo.getHero().hero_table_id);
		
		int begin = arr[0];
		int end = arr[1];
		int sum = 0;
		if (random == 0) random = RandomUtil.RangeRandom(begin, end);
		for (Map.Entry<Integer,MT_Data_LegionStore> entry : treeMap.entrySet()){
			if (!heroIds.contains(entry.getValue().belong())) 
				continue;
			
			Integer r = entry.getValue().probability();
			sum = r+begin;
			if (random>=begin && random<=sum) {
				return entry.getValue();
			}
			begin = sum;
		}
		return null;
	}
	private boolean isExist(List<MT_Data_LegionStore> lsList,MT_Data_LegionStore oneObj) {
		for (MT_Data_LegionStore mt_Data_LegionStore:lsList) {
			if (mt_Data_LegionStore.ID().intValue()==oneObj.ID()) {
				return true;
			}
		}
		return false;
	}
	public Long getTime() throws ParseException {
		Calendar cal = TimeUtil.GetCalendar();
		int hour1 = cal.get(Calendar.HOUR_OF_DAY);
		int hour = hour1 + ((hour1 % 2)==0? 2 : 1);
		hour %= 24;
		if (hour < hour1)
			cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (cal.getTimeInMillis() - TimeUtil.GetDateTime()) / 1000;
	}
	private void updatePlayerRefreshTime(ConPlayerAttr playerAttribute, long now) {
		Calendar cal = TimeUtil.GetCalendar();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		hour -= (hour % 2)==0? 0 : 1;
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		playerAttribute.setrefresh_time(cal.getTimeInMillis());
	}
	
	/**获取指定滴成员列表*/
	public  List<CustomLegionMeber> getMembers(DatabaseLegion legion,int type)
	{
		return null;
	}
	
}
