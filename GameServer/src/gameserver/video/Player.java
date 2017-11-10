package gameserver.video;

import java.util.ArrayList;
import java.util.List;

public class Player {
	public static class CorpsInfo
    {
        public int TableId;
        public int Level;
        public CorpsInfo() { }
        public CorpsInfo(int tableId, int level) {
            this.TableId = tableId;
            this.Level = level;
        }
    }
    public static class EquipInfo
    {
        public int TableId;
        public int Level;
        public EquipInfo() { }
        public EquipInfo(int tableId, int level) {
            this.TableId = tableId;
            this.Level = level;
        }
    }
    public static class MedalInfo
    {
        public int TableId;
        public int Level;
        public int StarLv;
        public MedalInfo() { }
        public MedalInfo(int tableId, int level, int starLv) {
            this.TableId = tableId;
            this.Level = level;
            this.StarLv = starLv;
        }
    }
    public static class HeroInfo
    {
        public int TableId;
        public int Star;
        public int HpLevel;
        public int AtkLevel;
        public int DefLevel;
        public List<Integer> Equips = new ArrayList<Integer>();
        public List<Integer> Medals = new ArrayList<Integer>();;
        public List<EquipInfo> EquipInfos = new ArrayList<EquipInfo>();
        public List<MedalInfo> MedalInfos = new ArrayList<MedalInfo>();
        public HeroInfo() { }
        public HeroInfo(int tableId, int star, int hpLv, int atkLv, int defLv, List<EquipInfo> equips, List<MedalInfo> medals) {
            this.TableId = tableId;
            this.EquipInfos = equips;
            this.MedalInfos = medals;
            this.Star = star;
            this.AtkLevel = atkLv;
            this.DefLevel = defLv;
            this.HpLevel = hpLv;
        }
    }
    public List<VideoFormation> m_Formations = new ArrayList<VideoFormation>();        //阵型
    public long m_PlayerId = 0;         //玩家ID
    public String m_Name = "";          //姓名
    public int m_Level = 0;             //等级
    public int m_Feat = 0;              //功勋
    public int m_WallLayoutId = 0;      //城墙布阵Id
    public int m_WallHp = 0;            //城墙血量
    public String m_Head = "";          //城堡头像
    public List<CorpsInfo> m_CorpsInfo = new ArrayList<CorpsInfo>();   //士兵信息
    public List<HeroInfo> m_HeroInfo = new ArrayList<HeroInfo>();      //英雄信息
    public List<Integer> m_PassiveBuff = new ArrayList<Integer>();             //被动技能
}
