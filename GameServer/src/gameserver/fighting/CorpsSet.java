package gameserver.fighting;

import gameserver.fighting.creature.Creature;
import gameserver.fighting.creature.CreatureCastle;
import gameserver.fighting.creature.CreatureCorps;
import gameserver.fighting.creature.CreatureSolider;
import gameserver.fighting.creature.CreatureWall;
import gameserver.fighting.stats.FightUtil;
import gameserver.fighting.stats.INSERT_CORPS_ERROR;
import gameserver.network.protos.game.ProPve.Proto_CorpsType;
import gameserver.network.protos.game.ProPve.Proto_Formation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_DataBase;
import table.MT_Data_AirSupport;
import table.MT_Data_Corps;
import table.MT_Data_Hero;
import table.MT_Data_Instance;
import table.MT_Data_Item;
import table.MT_Data_Spawn;
import table.MT_Data_WallLayout;
import table.MT_Table_Spawn;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.GridPoint;
import com.commons.util.GridRect;
import com.commons.util.GridSize;
import com.commons.util.RandomUtil;
import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;
import commonality.Common;

public class CorpsSet extends FightingBuff {
	private static Logger logger = LoggerFactory.getLogger(CorpsSet.class);
	
	private class PriorityPoint
    {
        public int key = 0;
        public int restrain = 0;        //克制的兵种数量
        public int creature = 0;        //其他兵种数量
    }
	
	private GridRect m_Rect;                                                    							// 本阵营的矩形
	public GridRect Rect() { return new GridRect(m_Rect); }               									//返回矩形 New一个返回 以免外部修改影响数据
	private CAMP_ENUM m_Camp;                                                   							// 阵营
    private HashMap<Integer, FightingHeroInfo> m_HeroInfos = new HashMap<Integer, FightingHeroInfo>();		// 英雄数组
    public HashMap<Integer, FightingHeroInfo> HeroInfos() { return m_HeroInfos; }							// 英雄数组
    private HashMap<Integer, FightingCorpsInfo> m_CorpsInfos = new HashMap<Integer, FightingCorpsInfo>();	// 兵种数组
    public HashMap<Integer, FightingCorpsInfo> CorpsInfos() { return m_CorpsInfos; }						// 兵种数组
    private List<CreatureCorps> m_Creatures = new ArrayList<CreatureCorps>();   							// 兵种数组 兵种 英雄
    public List<CreatureCorps> Creatures() { return m_Creatures; }											// 兵种数组 兵种 英雄
    private List<CreatureCorps> m_Walls = new ArrayList<CreatureCorps>();       							// 城墙数组
    public CreatureCastle Castle;       							            							//城堡
    private FightingManager m_FightingManager;								                                //战斗管理
    public int WallLayoutId;																				//城墙布阵ID
    private int m_WallX;                                                        							//城墙的X轴固定坐标
    private boolean m_Instance = false;                                            							//是否是副本（副本的话 不判断数量）
    public void Initialize(int minX, int width, CAMP_ENUM camp, FightingManager fightingManager, boolean instance)
    {
        m_Creatures.clear();
        m_Walls.clear();
        m_HeroInfos.clear();
        m_CorpsInfos.clear();
        m_PassiveBuffs.clear();
        m_FightingManager = fightingManager;
        m_Camp = camp;
        m_Instance = instance;
        m_Rect = new GridRect(minX, 0, width, m_FightingManager.Terrain.GetMaxY());
        m_WallX = (camp == CAMP_ENUM.LEFT ? m_Rect.left() - 1 : m_Rect.right() + 2);
        Castle = null;
    }
    public void Shutdown() {
    	m_FightingManager = null;
    	
    	m_Creatures.clear();
    	m_Creatures = null;
        m_Walls.clear();
        m_Walls = null;
        m_HeroInfos.clear();
        m_HeroInfos = null;
        m_CorpsInfos.clear();
        m_CorpsInfos = null;
        m_PassiveBuffs.clear();
        m_PassiveBuffs = null;
    }
    public void InsertHeroInfo(int tableId, int star, int hpLv, int atkLv, int defLv, List<FightingEquip> equips, HashMap<Integer, FightingMedal> medals)
    {
    	m_HeroInfos.put(tableId, new FightingHeroInfo(tableId, star, hpLv, atkLv, defLv, equips, medals));
    }
    public void InsertCorpsInfo(int tableId, int level, int count)
    {
        if (count <= 0)
            return;
        if (m_CorpsInfos.containsKey(tableId))
        	m_CorpsInfos.get(tableId).repairCount();
        else
        	m_CorpsInfos.put(tableId, new FightingCorpsInfo(tableId, level, count));
    }
    public MT_Data_Hero GetHeroData(int tableId)
    {
        return m_HeroInfos.containsKey(tableId) ? m_HeroInfos.get(tableId).Data : null;
    }
    public MT_Data_Corps GetCorpsData(int tableId)
    {
        return m_CorpsInfos.containsKey(tableId) ? m_CorpsInfos.get(tableId).Data : null;
    }
    public void InitializeCastle(int hp, int wallLayoutId, String name, int level, int feat, String herd) throws Exception
    {
    	Castle = new CreatureCastle();
    	Castle.Initialize(m_Camp == CAMP_ENUM.LEFT ? m_Rect.left() - 1 : m_Rect.right() + 1, 0, hp, m_Camp, m_FightingManager,
    			name, level, feat, herd);
    	Castle.MakePerfect();
    	MT_Data_WallLayout wallLayout = TableManager.GetInstance().TableWallLayout().GetElement(wallLayoutId);
    	if (wallLayout == null)
    		return;
    	WallLayoutId = wallLayoutId;
    	int height = m_Rect.height;
    	for (int i = 0; i < height; ++i) {
    		Int2 info = (Int2)wallLayout.GetDataByString("Wall" + (i + 1));
    		if (!TABLE.IsInvalid(info)) AddWall(i, info.field1(), info.field2());
    	}
    }
    public void InitializeWall(int wallLayoutId)
    {
        MT_Data_WallLayout wallLayout = TableManager.GetInstance().TableWallLayout().GetElement(wallLayoutId);
        if (wallLayout == null) return;
        int height = m_Rect.height;
        for (int i = 0; i < height; ++i)
        {
        	try {
        		Int2 info = (Int2)wallLayout.GetDataByString("Wall" + (i + 1));
        		if (!TABLE.IsInvalid(info))  AddWall(i, info.field1(), info.field2());
        	}
        	catch (Exception e) { }
        }
    }
    public INSERT_CORPS_ERROR InitializeFormation(List<Proto_Formation> formationArray, boolean cease) throws Exception
    {
    	INSERT_CORPS_ERROR result = INSERT_CORPS_ERROR.NONE;
        int count = formationArray.size();
        for (int i = 0; i < count;++i )
        {
            Proto_Formation formation = formationArray.get(i);
            if (formation.getType() == Proto_CorpsType.HERO)
            {
                if (m_HeroInfos.containsKey(formation.getId()) && m_HeroInfos.get(formation.getId()).CanPlace()) {
                	if (m_Camp == CAMP_ENUM.RIGHT ? 
                        AddRightHero(formation.getPosX(), formation.getPosY(), formation.getId()) : 
                        AddHero(formation.getPosX(), formation.getPosY(), formation.getId()))
                    {  
                		
                    }
                } else {
                	result = INSERT_CORPS_ERROR.HERO_ERROR;
                    if (cease) break;
                }
            } else {
                if (m_CorpsInfos.containsKey(formation.getId()) && m_CorpsInfos.get(formation.getId()).CanPlace()) {
                	if (m_Camp == CAMP_ENUM.RIGHT ?
                        AddRightCorps(formation.getPosX(), formation.getPosY(), formation.getId()) :
                        AddCorps(formation.getPosX(), formation.getPosY(), formation.getId()))
                    { 
                		
                    }
                } else {
                	result = INSERT_CORPS_ERROR.CORPS_ERROR;
                    if (cease) break;
                }
            }
        }
        return result;
    }
    /// <summary> 根据副本布局初始化阵形 </summary>
    public void InitializeInstance(int instanceID) throws Exception
    {
        MT_Data_Instance instanceData = TableManager.GetInstance().TableInstance().GetElement(instanceID);
        if (instanceData == null)
        {
            logger.error("场景ID为空   {0}", instanceID);
            return;
        }
        InitializeCastle(instanceData.Hp(), instanceData.WallLayout(), instanceData.Name(), instanceData.Level(), 0, instanceData.Head());
        InitializeSpawn(instanceData.ID());
    }
    private void InitializeSpawn(Integer spawnID) throws Exception
    {
        MT_Table_Spawn spawn = TableManager.GetInstance().getSpawns_Spawn(spawnID.toString());
        for (Map.Entry<Integer, MT_Data_Spawn> pair : spawn.Datas().entrySet())
        {
            MT_Data_Spawn corps = pair.getValue();
            AddCreature_impl(corps.Position().field1() + m_Rect.left() - 1, corps.Position().field2() - 1, 
                corps.CorpsID(), corps.Level(), CREATURE_ENUM.SOLIDER, false);
        }
    }
    /**
     * 开始战斗
     */
    public void StartFighting()
    {
        int count = m_Creatures.size();
        for (int i = 0; i < count; ++i)
        {
            m_Creatures.get(i).StartFighting();
        }
        count = m_Walls.size();
        for (int i = 0; i < count; ++i)
        {
            m_Walls.get(i).StartFighting();
        }
        Castle.StartFighting();
    }
    /**
     * 结束战斗
     */
    public void OverFighting(CAMP_ENUM camp)
    {
        int count = m_Creatures.size();
        for (int i = 0; i < count; ++i)
        {
            m_Creatures.get(i).OverFighting(camp);
        }
        count = m_Walls.size();
        for (int i = 0; i < count; ++i)
        {
            m_Walls.get(i).OverFighting(camp);
        }
        Castle.OverFighting(camp);
    }
    public Creature GetCorpsByBegin(int x, int y)
    {
        int count = m_Creatures.size();
        for (int i = 0; i < count; ++i)
        {
            Creature creature = m_Creatures.get(i);
            if (creature.BeginX() == x && creature.BeginY() == y) return creature;
        }
        count = m_Walls.size();
        for (int i = 0; i < count; ++i)
        {
            Creature creature = m_Walls.get(i);
            if (creature.BeginX() == x && creature.BeginY() == y) return creature;
        }
        if (Castle.BeginX() == x && Castle.BeginY() == y)
            return Castle;
        return null;
    }
    public Creature GetWallByBeginY(int y)
    {
        int count = m_Walls.size();
        for (int i = 0; i < count; ++i)
        {
            Creature creature = m_Walls.get(i);
            if (creature.BeginY() == y) return creature;
        }
        return null;
    }
    // 根据兵种开始位置获得一个兵
    public CreatureCorps GetElementByBegin(int x, int y)
    {
        int count = m_Creatures.size();
        for (int i = 0; i < count; ++i)
        {
            CreatureCorps creature = m_Creatures.get(i);
            if (creature.ContainsBegin(x, y)) return creature;
        }
        return null;
    }
    // 根据兵种当前位置获得一个兵
    public CreatureCorps GetElementByCurrent(int x, int y)
    {
        int count = m_Creatures.size();
        for (int i = 0; i < count; ++i)
        {
            CreatureCorps creature = m_Creatures.get(i);
            if (creature.ContainsCurrent(x, y) && !creature.IsDie) return creature;
        }
        return null;
    }
    public boolean AddCorps(int x, int y, int corpsId) throws Exception
    {
        return AddCreature_impl(x, y, corpsId, 0, CREATURE_ENUM.SOLIDER, false);
    }
    public boolean AddRightCorps(int x, int y, int corpsId) throws Exception
    {
        return AddCreature_impl(x, y, corpsId, 0, CREATURE_ENUM.SOLIDER, true);
    }
    public boolean AddHero(int x, int y, int heroId) throws Exception
    {
        return AddCreature_impl(x, y, heroId, 0, CREATURE_ENUM.HERO, false);
    }
    public boolean AddRightHero(int x, int y, int heroId) throws Exception
    {
        return AddCreature_impl(x, y, heroId, 0, CREATURE_ENUM.HERO, true);
    }
    public boolean AddWall(int y, int corpsId, int level) throws Exception
    {
        return AddCreature_impl(m_WallX, y, corpsId, level, CREATURE_ENUM.TOWER, false);
    }
    private boolean AddCreature_impl(int x, int y, int id, int level, CREATURE_ENUM type, boolean right) throws Exception
    {
        GridSize size = null;
        MT_DataBase database = null;
        if (type == CREATURE_ENUM.HERO)
        {
            MT_Data_Hero heroData = GetHeroData(id);
            if (heroData == null) return false;
            size = new GridSize(heroData.Size().field1(),heroData.Size().field2());
            database = heroData;
        }
        else
        {
            MT_Data_Corps corpsData = ((!m_Instance && type == CREATURE_ENUM.SOLIDER) ? GetCorpsData(id) : TableManager.GetInstance().TableCorps().GetElement(id + level));
            if (corpsData == null) return false;
            size = (type == CREATURE_ENUM.TOWER ? new GridSize(1,1) : new GridSize(corpsData.Size().field1(),corpsData.Size().field2()));
            database = corpsData;
        }
        if (right) x = FightUtil.GetRightXByLeftX(x, size.width, m_FightingManager);
        if (type == CREATURE_ENUM.HERO || type == CREATURE_ENUM.SOLIDER)
        {
            if (!m_Rect.Contains(new GridRect(x, y, size.width, size.height)))
                return false;
        }
        else
        {
            if (!m_Rect.ContainsY(y) || x != m_WallX)
                return false;
        }
        int width = size.width + x;
        int height = size.height + y;
        for (int i = x; i < width; ++i)
        {
            for (int j = y; j < height; ++j)
                RemoveCreature(i, j);
        }
        CreatureCorps creatureCorps = null;
        if (type == CREATURE_ENUM.SOLIDER)
        {
            CreatureSolider solider = new CreatureSolider();
            solider.Initialize(x, y, id, database, m_Camp, CREATURE_ENUM.SOLIDER, m_FightingManager);
            creatureCorps = solider;
        }
        else if (type == CREATURE_ENUM.TOWER)
        {
            CreatureWall wall = new CreatureWall();
            wall.Initialize(x, y, id, database, m_Camp, m_FightingManager);
            creatureCorps = wall;
        }
        else if (type == CREATURE_ENUM.HERO)
        {
            CreatureSolider hero = new CreatureSolider();
            hero.Initialize(x, y, id, database, m_Camp, CREATURE_ENUM.HERO, m_FightingManager);
            creatureCorps = hero;
        }
        creatureCorps.MakePerfect();
        if (type == CREATURE_ENUM.TOWER) {
            m_Walls.add(creatureCorps);
        } else {
            m_Creatures.add(creatureCorps);
            if (!m_Instance) {
                if (type == CREATURE_ENUM.HERO) {
                    m_HeroInfos.get(id).Place();
                } else if (type == CREATURE_ENUM.SOLIDER) {
                    m_CorpsInfos.get(id).Place();
                }
            }
        }
        return true;
    }
    public void RemoveCreature(int x, int y)
    {
        CreatureCorps creature = GetElementByBegin(x, y);
        if (creature != null) RemoveCreature(creature);
    }
    public void RemoveCreature(CreatureCorps creature)
    {
        if (creature == null || creature.Type == CREATURE_ENUM.TOWER) return;
        m_Creatures.remove(creature);
    }
    public boolean IsNone()
    {
        //如果不是进攻方 要打烂城墙才算赢
        if (m_Camp != m_FightingManager.AttackCamp) return false;
        //如果是进攻方 没有兵就算输
        int Count = m_Creatures.size();
        for (int i = 0; i < Count; ++i)
        {
            if (!m_Creatures.get(i).IsDie) return false;
        }
        return true;
    }
    public Map<Integer, Integer> GetDieSoliders()
    {
        Map<Integer, Integer> soliders = new HashMap<Integer,Integer>();
        int Count = m_Creatures.size();
        for (int i = 0; i < Count; ++i)
        {
        	CreatureCorps creature = m_Creatures.get(i);
        	if (creature.IsDie && creature.Type == CREATURE_ENUM.SOLIDER)
        	{
        		int id = creature.CorpsId;
        		int number = soliders.containsKey(id) ? soliders.get(id) : 0;
        		soliders.put(id, number + 1);
        	}
        }
        return soliders;
    }
    public List<Integer> GetDieHeros()
    {
        List<Integer> heros = new ArrayList<Integer>();
        for (CreatureCorps creature : m_Creatures)
        {
            if (creature.IsDie && creature.Type == CREATURE_ENUM.HERO)
            {
                int id = creature.CorpsId;
                if (!heros.contains(id))
                    heros.add(id);
            }
        }
        return heros;
    }
    public int2 GetHeroMedalAttr(CREATURE_ENUM creatureType, int id, Common.ATTRENUM attrType) throws Exception
    {
        int2 value = new int2(0, 0);
        if (creatureType != CREATURE_ENUM.HERO)
            return value;
        if (!m_HeroInfos.containsKey(id))
        	return value;
        return m_HeroInfos.get(id).GetMedalAttribute(attrType);
    }
    public int2 GetHeroEquipAttr(CREATURE_ENUM creatureType, int id, Common.ATTRENUM attrType)
    {
        int2 value = new int2(0, 0);
        if (creatureType != CREATURE_ENUM.HERO)
            return value;
        if (!m_HeroInfos.containsKey(id))
        	return value;
        return m_HeroInfos.get(id).GetEquipAttribute(attrType);
    }
    
    public GridPoint GetPriorityPoint(MT_Data_AirSupport data, int target) throws Exception
    {
        int attackType = data.AttackType();
        if (TABLE.AIR_SUPPORT_TARGET.REGION.ordinal() != target)
        {
            List<PriorityPoint> points = new ArrayList<PriorityPoint>();
            HashMap<Integer, PriorityPoint> keys = new HashMap<Integer, PriorityPoint>();
            for (CreatureCorps creature : m_Creatures)
            {
                if (creature.IsDie)
                    continue;
                int key = (TABLE.AIR_SUPPORT_TARGET.HORIZONTAL.ordinal() == target ? creature.BeginY() : creature.CurrentX());
                PriorityPoint pro = keys.containsKey(key) ? keys.get(key) : new PriorityPoint();
                pro.key = key;
                if (TableManager.GetInstance().GetRestrainByAtkType(attackType) == creature.GetArmorType()) {
                    ++pro.restrain;
                } else {
                    ++pro.creature;
                }
                if (!keys.containsKey(key)) {
                	keys.put(key, pro);
                    points.add(pro);
                }
            }
            if (points.size() > 0) {
            	Collections.sort(points, new Comparator<PriorityPoint>() {
					@Override
					public int compare(PriorityPoint o1, PriorityPoint o2) {
						if (o2.restrain > o1.restrain)
							return 1;
						if (o2.restrain < o1.restrain)
							return -1;
						if (o2.creature > o1.creature)
							return 1;
						if (o2.creature < o1.creature)
							return -1;
						return 0;
					}
            	});
            	
                return (TABLE.AIR_SUPPORT_TARGET.HORIZONTAL.ordinal() == target ? new GridPoint(0, points.get(0).key) : new GridPoint(points.get(0).key, 0));
            }
        }
        else
        {
            List<GridPoint> restrains = new ArrayList<GridPoint>();
            List<GridPoint> creatures = new ArrayList<GridPoint>();
            for (CreatureCorps creature : m_Creatures)
            {
                if (creature.IsDie)
                    continue;
                if (TableManager.GetInstance().GetRestrainByAtkType(attackType) == creature.GetArmorType()) {
                    restrains.add(creature.CurrentPosition());
                } else {
                    creatures.add(creature.CurrentPosition());
                }
            }
            if (restrains.size() > 0) {
                return restrains.get(RandomUtil.RangeRandom(0, restrains.size() - 1));
            }
            if (creatures.size() > 0)            {
                return creatures.get(RandomUtil.RangeRandom(0, creatures.size() - 1));
            }
        }
        return new GridPoint(-1, -1);
    }
}
