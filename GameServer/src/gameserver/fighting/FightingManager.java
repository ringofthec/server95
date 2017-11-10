package gameserver.fighting;

import gameserver.connection.attribute.info.AirSupportInfo;
import gameserver.connection.attribute.info.CorpsInfo;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.connection.attribute.info.ItemInfo;
import gameserver.connection.attribute.info.MedalInfo;
import gameserver.fighting.SkillEffectManager.SKILL_TARGET_TYPE;
import gameserver.fighting.clock.ClockManager;
import gameserver.fighting.clock.OnTimeCallBack;
import gameserver.fighting.clock.TimeCallBack;
import gameserver.fighting.creature.Creature;
import gameserver.fighting.creature.CreatureAirSupport;
import gameserver.fighting.creature.CreatureAirSupportHorizontal;
import gameserver.fighting.creature.CreatureAirSupportRegion;
import gameserver.fighting.creature.CreatureAirSupportVertical;
import gameserver.fighting.creature.CreatureCorps;
import gameserver.fighting.creature.CreatureHero;
import gameserver.fighting.creature.CreatureObject;
import gameserver.fighting.stats.FightUtil;
import gameserver.network.protos.game.ProPve;
import gameserver.network.protos.game.ProBuild.Proto_CorpsInfo;
import gameserver.network.protos.game.ProPve.Proto_CorpsType;
import gameserver.network.protos.game.ProPve.Proto_Formation;
import gameserver.network.protos.game.ProPvp.Msg_Item;
import gameserver.network.protos.game.ProPvp.Msg_Medal;
import gameserver.network.protos.game.ProPvp.Proto_CorpsInfoPvp;
import gameserver.network.protos.game.ProPvp.Proto_HeroInfoPvp;
import gameserver.network.protos.game.ProWanted.Msg_G2C_LoginWanted;
import gameserver.network.server.connection.Connection;
import gameserver.video.VideoManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_AirSupport;
import table.MT_Data_Head;
import table.MT_Data_IEffect;
import table.MT_Data_Item;
import table.MT_Data_PassiveBuff;
import table.MT_Data_WantedInstance;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.GridPoint;
import com.commons.util.GridSize;

import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;
import commonality.FIGHTING_STATE;
import commonality.FIGHTING_TYPE;
import database.CustomFormation;
import database.CustomFormationCrops;
import database.CustomHeroEquip;

public class FightingManager {
	private static Logger logger = LoggerFactory.getLogger(FightingManager.class);
	
    public static final int LEFT_MINX = 0;			//自己方最小X
    public static final int LEFT_WIDTH = 7;			//自己方的宽度
    public static final int RIGHT_MINX = 8;			//敌方最小X
    public static final int RIGHT_WIDTH = 7;		//地方的宽度
    /** 战斗类型 PVE PVP 布阵 */
    public FIGHTING_TYPE FightingTypeEnum;
    /** 战斗类型 PVE PVP 布阵 */
    public int FightingType() {
    	return FightingTypeEnum.Number();
    }
    /** 对手ID */
    public long EnemyPlayerId;
	/**
	 * 计时器
	 */
	public ClockManager ClockManager;
	/**
	 * 录像
	 */
	public VideoManager VideoManager;
	/**
	 * 更新管理器
	 */
	public UpdateComponentManager UpdateManager;
	/**
	 * 技能管理
	 */
	public SkillEffectManager SkillEffectManager;
    /**
     * 当前状态 是正在布兵 还是战斗 还是结束战斗
     */
	public FIGHTING_STATE State;
	/**
	 * 地形
	 */
	public GameTerrain Terrain = GameTerrain.GetTerrain(new Vector2(-474.4f, -212.16f), 15, 6, 63.2f, 70.4f);
	/*
	 * 战斗空中支援数据
	 */
	private HashMap<Integer, FightingAirSupportInfo> m_AirSupport = new HashMap<Integer, FightingAirSupportInfo>();
	public HashMap<Integer, FightingAirSupportInfo> AirSupport() { return m_AirSupport; }
	/**
	 * 自己士兵数组
	 */
	public CorpsSet LeftCorps = new CorpsSet();
	/**
	 * 敌方士兵数组
	 */
    public CorpsSet RightCorps = new CorpsSet();
    /**
     * 进攻方站的方位
     */
    public CAMP_ENUM AttackCamp;
    /**
     * 战斗是否胜利 
     */
    private boolean m_Win = false;
    /**
     * 攻击方是否胜利 
     */
    public boolean GetWin() {
    	return m_Win;
    }
    
    /*
     * 空中支援的公共CD
     */
    private int m_AirSupportCommonCoolDownKey;
    public TimeCallBack AirSupportCommonCoolDown() {
    	TimeCallBack timeCallBack = ClockManager.getTimeCallBack(m_AirSupportCommonCoolDownKey);
    	return timeCallBack;
    }
    
    private Connection m_Connection;
    
    public FightingManager(Connection connection)
    {
    	m_Connection = connection;
    	ClockManager = new ClockManager();
    	UpdateManager = new UpdateComponentManager();
    	SkillEffectManager = new SkillEffectManager(this);
    	VideoManager = new VideoManager(m_Connection, this);
    }
    public void Shutdown() {
    	ClockManager.Shutdown();
    	ClockManager = null;
    	
    	UpdateManager.Shutdown();
    	UpdateManager = null;
    	
    	SkillEffectManager.Shutdown();
    	SkillEffectManager = null;
    	
    	for (FightingAirSupportInfo info : m_AirSupport.values())
    		info.Shutdown();
    	m_AirSupport.clear();
    	m_AirSupport = null;
    	
    	LeftCorps.Shutdown();
    	LeftCorps = null;
    	RightCorps.Shutdown();
    	RightCorps = null;
    	
    	VideoManager = null;
    }
    public void InitializeFightingLeft() throws Exception
    {
        State = FIGHTING_STATE.LAYOUT;
        AttackCamp = CAMP_ENUM.LEFT;
        MT_Data_Head head = TableManager.GetInstance().TableHead().GetElement(m_Connection.getPlayer().getHead());
        
        // 初始化自己方数组
        LeftCorps.Initialize(LEFT_MINX, LEFT_WIDTH, CAMP_ENUM.LEFT, this, false);
        // 初始数组
        RightCorps.Initialize(RIGHT_MINX, RIGHT_WIDTH, CAMP_ENUM.RIGHT, this, true);
        // 初始化自己城墙
        int hp = m_Connection.getBuild().GetWallHp();
        int wallLayoutId = m_Connection.getBuild().GetWallLayout();
        LeftCorps.InitializeCastle(hp, wallLayoutId, m_Connection.getPlayerName(),
        		m_Connection.getPlayer().getLevel(),
        		m_Connection.getPlayer().getFeat(),
        		head != null ? head.Head() : "");
        // 初始化英雄信息以及兵种信息
        for (HeroInfo heroInfo : m_Connection.getHero().getHeros().values()) {
        	List<FightingEquip> equips = new ArrayList<FightingEquip>();
        	for (CustomHeroEquip equip : heroInfo.getHero().equips) {
        		ItemInfo item = m_Connection.getItem().getItemById(equip.equipId);
        		equips.add(new FightingEquip(item.getTableId(), item.getLvl()));
        	}
        	HashMap<Integer, FightingMedal> medals = new HashMap<Integer, FightingMedal>();
        	for (MedalInfo medal : heroInfo.getMedalList()) {
//        		medals.add(medal.getData());
        		if (medals.containsKey(medal.getData().index())) {
        			logger.error("英雄{0} 重复勋章 TableId:{1}  Level:{2}", heroInfo.getHeroId(), medal.getData().index(), medal.getLvl());
        			continue;
        		}
        		medals.put(medal.getData().index(), new FightingMedal(medal.getData().index(), medal.getLvl(), medal.getStar()));
        	}
        	LeftCorps.InsertHeroInfo(heroInfo.getTableId(), heroInfo.getStar(), heroInfo.getHpLvl(), heroInfo.getAttackLvl(), heroInfo.getDefenLvl(), equips, medals);
        }
        for (CorpsInfo corpsInfo : m_Connection.getCorps().getCorpsArray()) {
        	LeftCorps.InsertCorpsInfo(corpsInfo.getTableId(), corpsInfo.getLevel(), corpsInfo.getNumber());
        }
        // 初始化被动技能
        for (MT_Data_PassiveBuff pair : m_Connection.getBuffs().GetPassiveBuffs().values()) {
        	LeftCorps.AddPassiveBuff(pair);
        }
        // 初始化空中支援数据
        m_AirSupport.clear();
        for (AirSupportInfo pair : m_Connection.getAir().GetAirSupportArray()) {
        	m_AirSupport.put(pair.GetTableId(), new FightingAirSupportInfo(pair.GetTableId(), pair.GetLevel(), this));
        }
    }
    
    public boolean InitializeFightingRight(int instanceId,List<CustomFormation> list,List<CustomFormationCrops> corps) throws Exception {
    	MT_Data_WantedInstance wantedInstance = TableManager.GetInstance()
				.TableWantedInstance().GetElement(instanceId);
    	if (wantedInstance == null)
    		return false;
    	 // 初始化自己城墙
        RightCorps.InitializeCastle(wantedInstance.getM_nHp(), 
        		wantedInstance.getM_nwall(), 
        		wantedInstance.getM_sname(), 
        		wantedInstance.getM_nlevel(), 
        		0, 
        		wantedInstance.getM_shead());
        
        for (CustomFormationCrops corpsInfo : corps) {
        	RightCorps.InsertCorpsInfo(corpsInfo.cropTableId, corpsInfo.lv, corpsInfo.num);
        }
        
        List<Proto_Formation> fmList = new ArrayList<ProPve.Proto_Formation>();
        Proto_Formation.Builder fBuilder = Proto_Formation.newBuilder();
		for (database.CustomFormation customFormation :list) {
			fBuilder.setId(customFormation.id);
			fBuilder.setPosX(customFormation.x);
			fBuilder.setPosY(customFormation.y);
			fBuilder.setType(Proto_CorpsType.SOLIDER);
			fmList.add(fBuilder.build());
		}
        // 对方阵型
        RightCorps.InitializeFormation(fmList, false);
    	return true;
    }
    
    
    public void InitializeFightingRight(int hp, int wallLayoutId, List<Proto_HeroInfoPvp> heros, List<Integer> passiveBuffId,
    		List<Proto_CorpsInfoPvp> corps, List<Proto_Formation> formation, String name, int level, int feat, String head) throws Exception {
        // 初始化自己城墙
        RightCorps.InitializeCastle(hp, wallLayoutId, name, level, feat, head);
        // 初始化英雄信息以及兵种信息
        for (Proto_HeroInfoPvp pair : heros) {
        	List<FightingEquip> equips = new ArrayList<FightingEquip>();
        	for (Msg_Item equip : pair.getEquipsList()) {
        		equips.add(new FightingEquip(equip.getTableid(), equip.getLvl()));
        	}
        	HashMap<Integer, FightingMedal> medals = new HashMap<Integer, FightingMedal>();
        	for (Msg_Medal medal : pair.getMedalsList()) {
                if (medals.containsKey(medal.getTableid())) {
                    logger.error("英雄{0} 重复勋章 TableId:{1}  Level:{2}", pair.getHeroId(), medal.getTableid(), medal.getLvl());
                    continue;
                }
                
                medals.put(medal.getTableid(), new FightingMedal(medal.getTableid(), medal.getLvl(), medal.getStar()));
        	}
        	RightCorps.InsertHeroInfo(pair.getHeroId(), pair.getStar(), pair.getHpLv(), pair.getAtkLv(), pair.getDefLv(), equips, medals);
        }
        for (Proto_CorpsInfoPvp corpsInfo : corps) {
        	RightCorps.InsertCorpsInfo(corpsInfo.getCorpsId(), corpsInfo.getCorpsLevel(), corpsInfo.getCorpsNum());
        }
        // 初始化被动技能
        for (int Id : passiveBuffId) {
        	RightCorps.AddPassiveBuff(Id);
        }
        // 对方阵型
        RightCorps.InitializeFormation(formation, false);
        // 初始化空中支援数据
//        m_AirSupport.clear();
//        for (AirSupportInfo pair : m_Connection.getAir().GetAirSupportArray()) {
//        	m_AirSupport.put(pair.GetTableId(), new FightingAirSupportInfo(pair.GetTableId(), pair.GetLevel(), this));
//        }
    }
    public void StartFighting() throws Exception
    {
        State = FIGHTING_STATE.FIGHTING;
        LeftCorps.StartFighting();
        RightCorps.StartFighting();
        UpdateManager.StartFighting();
        for (Map.Entry<Integer, FightingAirSupportInfo> pair : m_AirSupport.entrySet())
        	pair.getValue().StartFighting();
        OnAutoFireAirSupport();
        
        VideoManager.StartFighting();
    }
  //自动释放空中支援
    public void OnAutoFireAirSupport() throws Exception
    {
        if (State != FIGHTING_STATE.FIGHTING) return;
        if (m_AirSupport.size() <= 0) return;
        if (AirSupportCommonCoolDown() != null && AirSupportCommonCoolDown().SurplusTime() > 0) return;
        FightingAirSupportInfo info = null;

        int maxLevel = -1;
        for (FightingAirSupportInfo temp : m_AirSupport.values()) {
        	if (!m_Connection.getAir().GetAirSupportByTableId(temp.TableId).IsOutFighting())
        		continue;
        	
            if (temp.CoolDown().SurplusTime() <= 0 && temp.Level > maxLevel) {
                maxLevel = temp.Level;
                info = temp;
            }
        }
        if (info != null) {
	        GridPoint point = RightCorps.GetPriorityPoint(info.Data, info.Data.Type());
	        if (point.x == -1) return;
	        FireAirSupport(info, point);
        }
    }
    /// <summary> 释放空中支援 </summary>
    public void FireAirSupport(FightingAirSupportInfo AirInfo, GridPoint pos)
    {
        if (AirInfo == null || !Terrain.IsValidRanks(pos)) return;
        AirInfo.Use();
        m_AirSupportCommonCoolDownKey = ClockManager.addGameTimeCallBack(2, new OnTimeCallBack() {
			@Override
			public void run(TimeCallBack timeCallBack, Object[] args) {
				OnAirCoolDownTimeOver(timeCallBack, args);
			}
        }, this);
        FireAirSupport_impl(CAMP_ENUM.LEFT, AirInfo.Data, pos);
        VideoManager.AddFireSupportAction(AirInfo.TableId, pos);
    }
    //空中支援公共CD时间到
    void OnAirCoolDownTimeOver(TimeCallBack call, Object... args)
    {
        try {
        	OnAutoFireAirSupport();
		} catch (Exception e) {
			logger.error("OnAirCoolDownTimeOver:" + e.toString());
		}
    }
    private void FireAirSupport_impl(CAMP_ENUM camp, MT_Data_AirSupport airData, GridPoint pos)
    {
        CreatureAirSupport creature = null;
        if (airData.Type() == TABLE.AIR_SUPPORT_TARGET.HORIZONTAL.ordinal()) {
            creature = new CreatureAirSupportHorizontal();
        } else if (airData.Type() == TABLE.AIR_SUPPORT_TARGET.VERTICAL.ordinal()) {
            creature = new CreatureAirSupportVertical();
        } else if (airData.Type() == TABLE.AIR_SUPPORT_TARGET.REGION.ordinal()) {
            creature = new CreatureAirSupportRegion();
        } else {
            return;
        }
        creature.Initialize(camp, airData, pos, this);
    }
    //快速结束战斗
    public void FastOverFighting()
    {
        float deltaTime = 0.016f * 8;
        float time = 120;
        while (State == FIGHTING_STATE.FIGHTING && time > 0)
        {
        	ClockManager.OnUpdate(deltaTime);
            UpdateManager.OnUpdate_impl(deltaTime);
            
            time -= deltaTime;
        }
    }
    public void OverFighting(CAMP_ENUM camp)
    {
    	UpdateManager.OverFighting();
    	State = FIGHTING_STATE.FIGHTING_OVER;
    	m_Win = (camp == CAMP_ENUM.RIGHT);
        LeftCorps.OverFighting(camp);
        RightCorps.OverFighting(camp);
    }
    public void CheckFinghtingOver(Creature creature)
    {
        CorpsSet set = creature.Camp == CAMP_ENUM.LEFT ? LeftCorps : RightCorps;
        if (set.IsNone()) OverFighting(creature.Camp);
    }
    
    
    
    
    ////////////////////////////FightingManager_Fighting///////////////////////
    public List<Creature> GetFrontCreature(Creature creature, boolean upDown)
    {
    	List<Creature> targets = new ArrayList<Creature>();
    	List<CreatureCorps> creatures = creature.Camp == CAMP_ENUM.LEFT ? LeftCorps.Creatures() : RightCorps.Creatures();
        int y1Min = upDown ? creature.BeginY() - 1 : creature.BeginY();
        int y1Max = upDown ? creature.Rect().bottom() + 1 : creature.Rect().bottom();
        for (int i = y1Min; i <= y1Max; ++i)
        {
            Creature ret = GetNearCreature(i, creature, creatures);
            if (ret != null && !targets.contains(ret))
                targets.add(ret);
        }
        return targets;
    }
    public List<Creature> GetTargetCreature(Creature creature, boolean upDown)
    {
    	List<Creature> targets = new ArrayList<Creature>();
    	List<CreatureCorps> creatures = creature.Camp == CAMP_ENUM.LEFT ? RightCorps.Creatures() : LeftCorps.Creatures();
    	Creature castle = creature.Camp == CAMP_ENUM.LEFT ? RightCorps.Castle : LeftCorps.Castle;
        int y1Min = upDown ? creature.BeginY() - 1 : creature.BeginY();
        int y1Max = upDown ? creature.Rect().bottom() + 1 : creature.Rect().bottom();
        for (int i = y1Min;i <= y1Max; ++i)
        {
            Creature ret = GetNearCreature(i, creature, creatures);
            if (ret == null) ret = castle;
            if (!targets.contains(ret)) targets.add(ret);
        }
        return targets;
    }
    /// <summary> 获得移动前面的单位 </summary>
    public List<Creature> GetMoveCreature(Creature creature)
    {
        List<Creature> targets = new ArrayList<Creature>();
        List<CreatureCorps> leftCreatures = LeftCorps.Creatures();
        List<CreatureCorps> rightCreatures = RightCorps.Creatures();
        Creature castle = creature.Camp == CAMP_ENUM.LEFT ? RightCorps.Castle : LeftCorps.Castle;
        int y1Min = creature.BeginY();
        int y1Max = creature.Rect().bottom();
        int count = leftCreatures.size() + rightCreatures.size();
        for (int i = y1Min; i <= y1Max; ++i)
        {
            Creature ret = null;
            int minDistance = 99999;
            for (int j = 0; j < count; ++j)
            {
                Creature target = j < leftCreatures.size() ? leftCreatures.get(j) : rightCreatures.get(j - leftCreatures.size());
                //判断是不是在自己的碰撞范围
                if (target.IsDie || target == creature) continue;
                int y2Min = target.BeginY();
                int y2Max = target.Rect().bottom();
                if (i >= y2Min && i <= y2Max)
                {
                    int distance = 999999;
                    if (creature.Camp == CAMP_ENUM.LEFT)
                    {
                        if (target.BeginX() > creature.EndX())
                            distance = target.BeginX() - creature.EndX();
                    }
                    else if (creature.Camp == CAMP_ENUM.RIGHT)
                    {
                        if (creature.BeginX() > target.EndX())
                            distance = creature.BeginX() - target.EndX();
                    }
                    if (distance < minDistance)
                    {
                        minDistance = distance;
                        ret = target;
                    }
                }
            }
            if (ret == null) ret = castle;
            if (!targets.contains(ret))
                targets.add(ret);
        }
        return targets;
    }
    private Creature GetNearCreature(int y, Creature creature, List<CreatureCorps> creatures)
    {
        float minDistance = 99999;
        Creature ret = null;
        int count = creatures.size();
        for (int i = 0; i < count; ++i)
        {
            Creature target = creatures.get(i);
            //判断是不是在自己的碰撞范围
            if (target.IsDie || target == creature || !FightUtil.IsValidCreature(creature, target)) continue;
            int y2Min = target.BeginY();
            int y2Max = target.Rect().bottom();
            if (y >= y2Min && y <= y2Max)
            {
                float distance = FightUtil.GetDistance(creature, target);
                if (distance < minDistance)
                {
                    minDistance = distance;
                    ret = target;
                }
            }
        }
        return ret;
    }
    private List<Creature> GetRangeCreature(Creature creature, GridSize range)
    {
        if (creature == null) return null;
        return GetRangeCreature(creature.Camp, creature.CurrentPosition(), range);
    }
    private List<Creature> GetRangeCreature(CAMP_ENUM Camp, GridPoint position, GridSize range)
    {
        List<GridPoint> positions = GetRangePosition(Camp, position, range);
        CorpsSet corpsSet = Camp == CAMP_ENUM.LEFT ? LeftCorps : RightCorps;
        List<Creature> ret = new ArrayList<Creature>();
        for (GridPoint pos : positions)
        {
            Creature target = corpsSet.GetElementByCurrent(pos.x, pos.y);
            if (target != null && !target.IsDie && !ret.contains(target) && target.Type != CREATURE_ENUM.TOWER)
            {
                ret.add(target);
            }
            else
            {
                if (Camp == CAMP_ENUM.LEFT && !ret.contains(LeftCorps.Castle) && LeftCorps.Castle.ContainsCurrent(pos.x, pos.y))
                    ret.add(LeftCorps.Castle);
                else if (Camp == CAMP_ENUM.RIGHT && !ret.contains(RightCorps.Castle) && RightCorps.Castle.ContainsCurrent(pos.x, pos.y))
                    ret.add(RightCorps.Castle);
            }
        }
        return ret;
    }
    private List<GridPoint> GetRangePosition(CAMP_ENUM Camp, GridPoint position, GridSize range)
    {
        int miny = position.y - range.height / 2;
        int maxy = miny + range.height - 1;
        int minx, maxx;
        if (Camp == CAMP_ENUM.LEFT)
        {
            minx = position.x - range.width / 2;
            maxx = minx + range.width - 1;
        }
        else
        {
            maxx = position.x + range.width / 2;
            minx = maxx - range.width + 1;
        }
        List<GridPoint> ret = new ArrayList<GridPoint>();
        for (int x = minx; x <= maxx; ++x)
        {
            for (int y = miny; y <= maxy; ++y)
            {
                ret.add(new GridPoint(x, y));
            }
        }
        return ret;
    }
    public void AddBeginBuff(Creature target, FightData data)
    {
        if (target == null || data.effect.BeginTargetBuff().size() <= 0) return;
        List<Creature> creatures = GetRangeCreature(target, new GridSize(data.skill.Range().field1(),data.skill.Range().field2()));
        if (creatures != null)
        {
            for (Creature creature : creatures) {
                if (creature != null && !creature.IsDie)
                    creature.AddBuffArray(data.effect.BeginTargetBuff());
            }
        }
    }
    
    public void ByAttack(CreatureObject attack, GridPoint target, FightData data)
    {
        ByAttack_impl(attack, SKILL_TARGET_TYPE.POSITION, null, target, data);
    }
    public void ByAttack(CreatureObject attack, CreatureObject target, FightData data)
    {
        ByAttack_impl(attack, SKILL_TARGET_TYPE.CREATURE, target, null, data);
    }
    private void ByAttack_impl(CreatureObject attack, SKILL_TARGET_TYPE type, CreatureObject targetCreature, GridPoint targetPoint, FightData data)
    {
    	if (State != FIGHTING_STATE.FIGHTING) return;
        MT_Data_AirSupport airData = null;
        if (attack instanceof CreatureAirSupport)
        	airData = ((CreatureAirSupport)attack).AirData;
        GridSize range = new GridSize(data.skill.Range().field1(), data.skill.Range().field2());
        if (airData != null)
        {
            if (airData.Type() == TABLE.AIR_SUPPORT_TARGET.HORIZONTAL.ordinal() || airData.Type() == TABLE.AIR_SUPPORT_TARGET.VERTICAL.ordinal())
                range = new GridSize(1, 1);
            else
                range = new GridSize(airData.Range().field1(), airData.Range().field2());
        }
        List<Creature> creatures = (type == SKILL_TARGET_TYPE.POSITION ? GetRangeCreature(CAMP_ENUM.RIGHT, targetPoint, range) : GetRangeCreature((Creature)targetCreature, range));
        for (Creature creature : creatures)
        {
            if (creature != null && !creature.IsDie && creature.Type != CREATURE_ENUM.TOWER)
            {
                if (!(type == SKILL_TARGET_TYPE.POSITION && creature.Type == CREATURE_ENUM.CASTLE))
                {
                    if (airData != null)
                    {
                        creature.Hurt(airData.Attack(), airData.AttackType(), 0);
                    }
                    else
                    {
                        creature.Hurt((Creature)attack, data);
                    }
                    creature.AddBuffArray(data.effect.HitTargetBuff());
                    if (attack.Type == CREATURE_ENUM.HERO)
                        AddHeroEquipBuff(attack, creature, 2);
                }
            }
        }
    }

    
  
    /// <summary>
    /// 添加英雄装备提供的Buff
    /// </summary>
    /// <param name="attackObj"></param>
    /// <param name="targetObj"></param>
    /// <param name="type">1:释放技能 2:命中后目标增加BUFF 3:暴击后</param>
    public void AddHeroEquipBuff(CreatureObject attackObject, CreatureObject targetObject, int type)
    {
        if (attackObject instanceof CreatureHero && targetObject instanceof Creature)
        {
            CreatureHero attack = (CreatureHero)attackObject;
            Creature target = (Creature)targetObject;
            HashMap<Integer, FightingHeroInfo> array = new HashMap<Integer, FightingHeroInfo>();
            if (attack.Camp == CAMP_ENUM.LEFT)
                array = this.LeftCorps.HeroInfos();
            else
                array = this.RightCorps.HeroInfos();
            FightingHeroInfo heroInfo = array.get(attack.CorpsId);
            if (heroInfo == null) return;
            for (MT_Data_IEffect effect : heroInfo.Skills())
            {
                if (type == 3)
                {
                    if (effect.CritBuff().size() > 0)
                        attack.AddBuffArray(effect.CritBuff());
                    if (effect.CritTargetBuff().size() > 0)
                        target.AddBuffArray(effect.CritTargetBuff());
                }
                else if (type == 2)
                {
                    if (effect.HitTargetBuff().size() > 0)
                        target.AddBuffArray(effect.HitTargetBuff());
                }
                else if (type == 1)
                {
                    if (effect.BeginAttackerBuff().size() > 0)
                        attack.AddBuffArray(effect.BeginAttackerBuff());
                    if (effect.BeginTargetBuff().size() > 0)
                        target.AddBuffArray(effect.BeginTargetBuff());
                }
            }
        }
    }
}
