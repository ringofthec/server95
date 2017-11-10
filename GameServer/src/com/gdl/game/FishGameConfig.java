package com.gdl.game;

import gameserver.config.TableConfig;
import gameserver.jsonprotocol.Consts;
import gameserver.logging.LogService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javolution.util.FastList;
import javolution.util.FastMap;

import com.commons.util.JsonUtil;
import com.commons.util.RandomUtil;

public class FishGameConfig {
	private static FishGameConfig m_int = new FishGameConfig();
	private FishGameConfig() {}
	public static FishGameConfig getInstance() {return m_int;}
	
	public static class SceneTask {
		int id;
		int secene_id;
		int duration;
		int reward_coin;
		int reward_gold;
		List<Integer> fishes_type;
		List<Integer> fishes_count;
	}
	
	public static class SceneCon {
		List<Integer> time;
		List<Integer> ts;
		int duration;
		List<Integer> index; // 唯一标示
		int id;
		List<Integer> leave; // 离开时间
		int type;			 // 场景类型  0 普通 1 涨潮 2 奖池 3 副本 4 任务
		List<Integer> ids;   // 模板id
		List<Integer> gids;	 // 组id
		String seed;
		List<Integer> born;	// 出生时间
	}
	
	public static class GroupConInfo {
		int id;
		int type;
		int index;
		List<Integer> head;
		List<Integer> member;
	}
	
	public static class SceneConfig {
		SceneTask task;
		SceneCon scene;
		List<GroupConInfo> groups;
	}
	
	public static class DropRange {
		int id;
		int m;
		int x;
	}
	
	public static class KillRect {
		int m;
		int x;
	}
	
	public static class FishConfig{
		public int hp;
		public int t;  // 0  普通  1  召唤鱼   2  入侵boss 3  奖池boss(金龙鱼) 4  副本boss(魔鬼鱼)
		public int id;
		public int w;
		public boolean isKiller; // 
		public KillRect killRect; // 金钱掉落范围
		
		public boolean isNormal()   	{ return t == 0; }
		public boolean isSummon()   	{ return t == 1; }
		public boolean isBoss()     	{ return t == 2; }
		public boolean isPoolBoss() 	{ return t == 3; }
		public boolean isInstanceBoss() { return t == 4; }
		
		public int getAttackRate() { return w; }
		public int getRate() {
			for (DropRange o : drop) {
				if (o.id == Consts.getCOIN_ID())
					return o.m;
			}
			
			return 0;
		}
		
		public List<DropRange> drop;
	}
	
	public static class FishConfigs {
		List<FishConfig> fishes;
	}
	
	FishConfigs fish_config;
	FastList<Integer> summon_fish_list = new FastList<Integer>();
	FastMap<Integer, FishConfig> fish_config_map = new FastMap<Integer, FishGameConfig.FishConfig>();
	public FishConfig getFishConfig(int fid) {
		return fish_config_map.get(fid);
	}
	
	FastMap<Integer, SceneConfig> scene_config_map = new FastMap<Integer, FishGameConfig.SceneConfig>();
	public SceneConfig getSceneConfig(int fid) {
		return scene_config_map.get(fid);
	}
	
	public void init(boolean isboot) {
		loadSceneDir();
		loadFishJson();
		initScene();
		
		if (isboot)
			initTableTimeMap();
	}
	
	List<Integer> level1_normal = new ArrayList<Integer>();
	List<Integer> level1_special = new ArrayList<Integer>();
	
	List<Integer> level2_normal= new ArrayList<Integer>();
	List<Integer> level2_special= new ArrayList<Integer>();
	List<Integer> level2_task= new ArrayList<Integer>();
	
	List<Integer> level3_normal= new ArrayList<Integer>();
	List<Integer> level3_special= new ArrayList<Integer>();
	List<Integer> level3_task= new ArrayList<Integer>();
	List<Integer> level3_instance= new ArrayList<Integer>();
	
	public boolean isPoolScene(int sid) {
		if (sid == 84 || sid == 92 || sid == 9)
			return true;
		return false;
	}
	
	public int getPoolScene(int levelid) {
		if (levelid == 1)
			return 84;
		
		if (levelid == 2)
			return 92;
		
		return 9;
	}
	
	public void initScene() {
		level1_normal.clear();
		level1_normal.add(85);
		level1_normal.add(86);
		level1_normal.add(87);
		level1_normal.add(88);
		
		level1_special.clear();
		level1_special.add(89);
		level1_special.add(90);
		level1_special.add(91);
		
		level2_normal.clear();
		level2_normal.add(61);
		level2_normal.add(68);
		level2_normal.add(69);
		level2_normal.add(70);
		level2_normal.add(71);
		level2_normal.add(72);
		
		level2_special.clear();
		level2_special.add(76);
		level2_special.add(77);
		level2_special.add(78);
		level2_special.add(79);
		level2_special.add(80);
		level2_special.add(81);
		level2_special.add(82);
		level2_special.add(83);
		
		level2_task.clear();
		level2_task.add(73);
		level2_task.add(74);
		level2_task.add(75);
		
		level3_normal.clear();
		level3_normal.add(48);
		level3_normal.add(49);
		level3_normal.add(50);
		level3_normal.add(51);
		level3_normal.add(52);
		level3_normal.add(53);
		level3_normal.add(54);
		level3_normal.add(55);
		level3_normal.add(56);
		level3_normal.add(57);
		
		level3_special.clear();
		level3_special.add(4);
		level3_special.add(5);
		level3_special.add(6);
		level3_special.add(18);
		level3_special.add(19);
		level3_special.add(20);
		level3_special.add(58);
		level3_special.add(59);
		level3_special.add(60);
		
		level3_task.clear();
		level3_task.add(2);
		level3_task.add(7);
		level3_task.add(21);
		
		level3_instance.clear();
		level3_instance.add(3);
	}
	
	public int getNextScene(int levelId, int step) {
		if (levelId == 1) {
			step = step % 2;
			if (step == 0) {
				int poolid = FishGameMrg.getInstance().enterPoolScene(levelId);
				LogService.sysErr(step, "check poolid " + poolid, levelId);
				if (poolid <= 0)
					return level1_normal.get(RandomUtil.RangeRandom(0, level1_normal.size() - 1));
				else
					return poolid;
			}
			else
				return level1_special.get(RandomUtil.RangeRandom(0, level1_special.size() - 1));
		} else if (levelId == 2) {
			step = step % 6;
			switch (step) {
			case 0:
			case 2:
			case 4: {
				int poolid = FishGameMrg.getInstance().enterPoolScene(levelId);
				LogService.sysErr(step, "check poolid " + poolid, levelId);
				if (poolid <= 0)
					return level2_normal.get(RandomUtil.RangeRandom(0, level2_normal.size() - 1));
				else
					return poolid;
			}
			case 1:
			case 3:
				return level2_special.get(RandomUtil.RangeRandom(0, level2_special.size() - 1));
			case 5:
				return level2_task.get(RandomUtil.RangeRandom(0, level2_task.size() - 1));
			}
		} else if (levelId == 3) {
			step = step % 8;
			switch (step) {
			case 0:
			case 2:
			case 4:
			case 6: {
				int poolid = FishGameMrg.getInstance().enterPoolScene(levelId);
				LogService.sysErr(step, "check poolid " + poolid, levelId);
				if (poolid <= 0)
					return level3_normal.get(RandomUtil.RangeRandom(0, level3_normal.size() - 1));
				return poolid;
			}
			case 1:
			case 5:
				return level3_special.get(RandomUtil.RangeRandom(0, level3_special.size() - 1));
			case 3:
				return level3_task.get(RandomUtil.RangeRandom(0, level3_task.size() - 1));
			case 7:
				return level3_instance.get(RandomUtil.RangeRandom(0, level3_instance.size() - 1));
			}
		}
		
		return 0;
	}
	
	private SceneConfig loadSceneJson(File jsonFile) {
		BufferedReader reader = null;
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(jsonFile));
			reader = new BufferedReader(read);
			long len = jsonFile.length();
			char[] json_file = new char[(int)len];
			reader.read(json_file);
			String jjj = new String(json_file);
			SceneConfig scene_config = JsonUtil.JsonToObject(jjj, SceneConfig.class);
			return scene_config;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	private void loadSceneDir() {
		File dir = new File("data/sceneDatas");

		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.getName().startsWith("fishinfo"))
				continue;
			
			SceneConfig sc = loadSceneJson(f);
			scene_config_map.put(sc.scene.id, sc);
		}
	}
	public int getSummoneId() {
		if (summon_fish_list.isEmpty())
			return -1;
		
		int r = RandomUtil.RangeRandom(0, summon_fish_list.size() - 1);
		return summon_fish_list.get(r);
	}
	private void loadFishJson() {
		File file = new File("data/sceneDatas/fishinfo.json");
		if (!file.isFile() || !file.exists())
			return ;
		
		BufferedReader reader = null;
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file));
			reader = new BufferedReader(read);
			long len = file.length();
			char[] json_file = new char[(int)len];
			reader.read(json_file);
			String jjj = new String(json_file);
			fish_config = JsonUtil.JsonToObject(jjj, FishConfigs.class);
			
			for (FishConfig fc : fish_config.fishes) {
				fish_config_map.put(fc.id, fc);
				
				if ( fc.isSummon() )
					summon_fish_list.add(fc.id);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private HashMap<String, Long> m_TableTimeMap = new HashMap<>();

	private long getTableLastModTime(String str) {
		String name = TableConfig.fishPath + File.separatorChar + str
				+ TableConfig.fishSuffix;
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
		File file = new File(TableConfig.fishPath);
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
			
			m_TableTimeMap.put(name, nowFileTime);
			ischange =true;
		}
		
		if (ischange)
			init(false);
	}
}
