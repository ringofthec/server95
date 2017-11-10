package gameserver.video;

import gameserver.fighting.CorpsSet;
import gameserver.fighting.FightingCorpsInfo;
import gameserver.fighting.FightingEquip;
import gameserver.fighting.FightingHeroInfo;
import gameserver.fighting.FightingManager;
import gameserver.fighting.FightingMedal;
import gameserver.fighting.creature.Creature;
import gameserver.fighting.creature.CreatureCastle;
import gameserver.fighting.creature.CreatureCorps;
import gameserver.fighting.stats.FightUtil;
import gameserver.fighting.stats.STATS_ENUM;
import gameserver.network.protos.game.ProFight.Msg_G2C_AskAnchorData.AnchorInfo;
import gameserver.network.protos.game.ProPve.Proto_CorpsType;
import gameserver.network.server.connection.Connection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.commons.util.GZipUtil;
import com.commons.util.GridPoint;
import com.commons.util.JsonUtil;
import com.commons.util.NetUtil;

import table.MT_Data_Item;
import table.MT_Data_PassiveBuff;
import commonality.CAMP_ENUM;
import commonality.CREATURE_ENUM;
import commonality.FIGHTING_TYPE;

public class VideoManager {
	public enum ACTION_ENUM
	{
	    /// <summary> 士兵城墙攻击 </summary>
	    ATTACK,
	    /// <summary> 士兵城墙改变血量 </summary>
	    CHANGE_HP,
	    /// <summary> 释放空中支援 </summary>
	    FIRE_AIR_SUPPORT,
	    /// <summary> 兵种死亡 </summary>
	    CREATURE_DIE,
	}
	
	public VideoManager(Connection connection, FightingManager fightingManager) {
		this.m_FightingManager = fightingManager;
		this.m_Connection = connection;
	}
	
	//
	private FightingManager m_FightingManager;
	//
	private Connection m_Connection;
	//视频所属战报
    public AnchorInfo m_AnchorInfo = null;
    //攻击者
    public Player m_Attacker = new Player();
    //防守者
    public Player m_Defense = new Player();
    //行动队列
    private List<Action> m_listActions = new ArrayList<Action>();
    //请求数据回调
//    private RequestVideoFinshed m_callBack = null;
    //以什么视角查看录像
    private long m_ViewPlayerId = 0;
    //播放录像时是否已防御方视角
    public boolean DefenseView;
    //失败方
    public CAMP_ENUM Loser;
    //死亡的兵种数量
    private HashMap<Integer, Integer> m_DieSoliders = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> DieSoliders() {
    	return m_DieSoliders;
    }
    public void StartFighting()
    {
        if (m_FightingManager.FightingTypeEnum != FIGHTING_TYPE.PVP) return;
        m_listActions.clear();
        SetPlayer(m_Attacker, m_Connection.getPlayerId(), m_FightingManager.LeftCorps, false);
        SetPlayer(m_Defense, m_FightingManager.EnemyPlayerId, m_FightingManager.RightCorps, true);
    }
    private void SetPlayer(Player player, long playerId, CorpsSet corpsSet, boolean reverse)
    {
        CreatureCastle castle = corpsSet.Castle;
        player.m_PlayerId = playerId;
        player.m_Name = castle.Name;
        player.m_Level = castle.Level;
        player.m_Feat = castle.Feat;
        player.m_Head = castle.Head;
        player.m_WallLayoutId = corpsSet.WallLayoutId;
        player.m_WallHp = (Integer)castle.GetValue(STATS_ENUM.MAXHP);
        player.m_HeroInfo.clear();
        HashMap<Integer, FightingHeroInfo> heroInfo = corpsSet.HeroInfos();
        for (Entry<Integer, FightingHeroInfo> pair : heroInfo.entrySet()) {
            List<Player.EquipInfo> equips = new ArrayList<Player.EquipInfo>();
            for (FightingEquip equip : pair.getValue().Equips())
                equips.add(new Player.EquipInfo(equip.TableId, equip.Level));
            List<Player.MedalInfo> medals = new ArrayList<Player.MedalInfo>();
            for (FightingMedal medal : pair.getValue().Medals().values())
                medals.add(new Player.MedalInfo(medal.TableId, medal.Level, medal.StarLv));
            player.m_HeroInfo.add(new Player.HeroInfo(pair.getKey(), pair.getValue().Star, pair.getValue().HpLevel, pair.getValue().AtkLevel, pair.getValue().DefLevel, equips, medals));
        }
        player.m_CorpsInfo.clear();
        HashMap<Integer, FightingCorpsInfo> corpsInfo = corpsSet.CorpsInfos();
        for (Entry<Integer, FightingCorpsInfo> pair : corpsInfo.entrySet()) {
            player.m_CorpsInfo.add(new Player.CorpsInfo(pair.getKey(), pair.getValue().Level));
        }
        player.m_PassiveBuff.clear();
        HashMap<Integer, MT_Data_PassiveBuff> passiveBuff = corpsSet.PassiveBuffs();
        for (MT_Data_PassiveBuff pair : passiveBuff.values()) {
            player.m_PassiveBuff.add(pair.ID());
        }
        player.m_Formations.clear();
        List<CreatureCorps> creatures = corpsSet.Creatures();
        for (CreatureCorps pair : creatures) {
            VideoFormation info = new VideoFormation();
            info.Id = pair.CorpsId;
            info.posX = (reverse ? FightUtil.GetLeftXByRightX(pair.BeginX(), pair.Size().width, m_FightingManager) : pair.BeginX());
            info.posY = pair.BeginY();
            info.type = (pair.Type == CREATURE_ENUM.HERO ? Proto_CorpsType.HERO.getNumber() : Proto_CorpsType.SOLIDER.getNumber());
            player.m_Formations.add(info);
        }
    }
    public void OverFighting()
    {
        
    }
    public void AddAttackAction(Creature creature, Creature target)
    {
        if (m_FightingManager.FightingTypeEnum != FIGHTING_TYPE.PVP) return;
        Action action = new Action(m_FightingManager, creature);
        action.action = ACTION_ENUM.ATTACK.ordinal();

        action.targetType = target.Type.Number();
        action.targetCamp = target.Camp.Number();
        action.targetWidth = target.Size().width;
        action.targetX = (action.targetCamp == CAMP_ENUM.LEFT.Number() ? target.BeginX() : FightUtil.GetLeftXByRightX(target.BeginX(), target.Size().width, m_FightingManager));
        action.targetY = target.BeginY();

        m_listActions.add(action);
    }
    public void AddChangeHpAction(Creature creature, int value)
    {
        if (m_FightingManager.FightingTypeEnum != FIGHTING_TYPE.PVP) return;
        Action action = new Action(m_FightingManager, creature);
        action.action = ACTION_ENUM.CHANGE_HP.ordinal();
        action.hp = value;
        m_listActions.add(action);
    }
    public void AddFireSupportAction(int airId, GridPoint point)
    {
        if (m_FightingManager.FightingTypeEnum != FIGHTING_TYPE.PVP) return;
        Action action = new Action(m_FightingManager);
        action.action = ACTION_ENUM.FIRE_AIR_SUPPORT.ordinal();
        action.airId = airId;
        action.fireX = point.x;
        action.fireY = point.y;
        m_listActions.add(action);
    }
    public void AddCreatureDieAction(Creature creature)
    {
        if (m_FightingManager.FightingTypeEnum != FIGHTING_TYPE.PVP) return;
        Action action = new Action(m_FightingManager, creature);
        action.timeLine = m_FightingManager.UpdateManager.GetTimeLine();
        action.action = ACTION_ENUM.CREATURE_DIE.ordinal();
        m_listActions.add(action);
    }
    public byte[] Save() throws IOException
    {
        ByteBuffer stream = ByteBuffer.allocate(1024 * 1024).order(ByteOrder.LITTLE_ENDIAN);
        String attacker = JsonUtil.ObjectToJson(m_Attacker);
        NetUtil.WriteString(stream, attacker);
        String defense = JsonUtil.ObjectToJson(m_Defense);
        NetUtil.WriteString(stream, defense);
        int count = m_listActions.size();
        stream.putInt(count);
        for (int i = 0; i < count;++i ) {
            String action = JsonUtil.ObjectToJson(m_listActions.get(i));
            NetUtil.WriteString(stream, action);
        }

        byte[] bytes = Arrays.copyOf(stream.array(), stream.position());
        stream.clear();
        return GZipUtil.Compress(bytes);
    }
//    public void Load(long playerId, byte[] bytes)
//    {
//        ByteBuffer stream = ByteBuffer.allocate(8192 * 2).order(ByteOrder.LITTLE_ENDIAN);
//        String attacker = NetUtil.ReadString(stream);
//        m_Attacker = JsonUtil.JsonToObject(attacker, Player.class);
//        String defense = NetUtil.ReadString(stream);
//        m_Defense = JsonUtil.JsonToObject(defense, Player.class);
//        m_listActions.clear();
//        DefenseView = (m_Defense.m_PlayerId == playerId);
//        m_DieSoliders.clear();
//        int count = stream.getInt();
//        for (int i = 0; i < count;++i ) {
//            Action action = JsonUtil.JsonToObject(NetUtil.ReadString(stream), Action.class);
//            m_listActions.add(action);
//            boolean castleLose = false;
//            if (action.action == ACTION_ENUM.CREATURE_DIE)
//            {
//                if (action.creatureType == CREATURE_ENUM.CASTLE)
//                {
//                    castleLose = true;
//                    if (DefenseView)
//                        Loser = action.creatureCamp == CAMP_ENUM.LEFT ? CAMP_ENUM.RIGHT : CAMP_ENUM.LEFT;
//                    else
//                        Loser = action.creatureCamp;
//                }
//                else if (action.creatureType == CREATURE_ENUM.SOLIDER)
//                {
//                    if ((DefenseView && action.creatureCamp == CAMP_ENUM.RIGHT) || 
//                        (!DefenseView && action.creatureCamp == CAMP_ENUM.LEFT))
//                    {
//                        List<Proto_Formation> formations = DefenseView ? m_Defense.m_Formations : m_Attacker.m_Formations;
//                        for (Proto_Formation formation : formations)
//                        {
//                            if (formation.getPosX() == action.creatureX && formation.getPosY() == action.creatureY)
//                                Util.AddValue(m_DieSoliders, formation.getId());
//                        }
//                    }
//                }
//            }
//            //证明不是城堡死亡失败的 那肯定是进攻方失败
//            if (!castleLose)
//            {
//                Loser = DefenseView ? CAMP_ENUM.RIGHT : CAMP_ENUM.LEFT;
//            }
//        }
//    }
}
