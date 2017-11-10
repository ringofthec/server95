// 本文件是自动生成，不要手动修改
package databaseshare;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.sql.Date;
import java.io.Serializable;
import com.commons.util.Utility;
import com.commons.database.DatabaseSimple;
import com.commons.database.DatabaseFieldAttribute;
import com.commons.database.DatabaseTableDataBase;
import com.commons.database.DatabaseFieldDataBase;
//===========================================
@SuppressWarnings("unused")
public class DatabasePvp_match implements DatabaseTableDataBase<DatabasePvp_match>,Serializable {
    public static final String TableName = "pvp_match";
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家是否在线 */
    @DatabaseFieldAttribute(fieldName = "online",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean online = null;
    public Boolean getOnline() {return online;}
    /** 是否为机器人数据 */
    @DatabaseFieldAttribute(fieldName = "robot",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean robot = null;
    public Boolean getRobot() {return robot;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 上次被攻击的时间 */
    @DatabaseFieldAttribute(fieldName = "by_attack_time",fieldType = Long.class,arrayType = Byte.class)
    public Long by_attack_time = null;
    public Long getBy_attack_time() {return by_attack_time;}
    /** 护盾结束时间 */
    @DatabaseFieldAttribute(fieldName = "shield_end_time",fieldType = Long.class,arrayType = Byte.class)
    public Long shield_end_time = null;
    public Long getShield_end_time() {return shield_end_time;}
    /** 玩家当前的状态 */
    @DatabaseFieldAttribute(fieldName = "state",fieldType = Integer.class,arrayType = commonality.Common.PLAYER_STATE.class)
    public commonality.Common.PLAYER_STATE state = null;
    public commonality.Common.PLAYER_STATE getState() {return state;}
    /** 被匹配到的时间 */
    @DatabaseFieldAttribute(fieldName = "matchtime",fieldType = Long.class,arrayType = Byte.class)
    public Long matchtime = null;
    public Long getMatchtime() {return matchtime;}
    /** 开始战斗时间 */
    @DatabaseFieldAttribute(fieldName = "fightingtime",fieldType = Long.class,arrayType = Byte.class)
    public Long fightingtime = null;
    public Long getFightingtime() {return fightingtime;}
    /** 防御阵形 */
    @DatabaseFieldAttribute(fieldName = "defenseformation",fieldType = String.class,arrayType = CustomFormation.class)
    public List<CustomFormation> defenseformation = null;
    public List<CustomFormation> getDefenseformation() {return defenseformation;}
    /** 功勋 */
    @DatabaseFieldAttribute(fieldName = "feat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer feat = null;
    public Integer getFeat() {return feat;}
    /** 匹配到的玩家ID */
    @DatabaseFieldAttribute(fieldName = "match_player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long match_player_id = null;
    public Long getMatch_player_id() {return match_player_id;}
    /** 玩家等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 玩家vip等级 */
    @DatabaseFieldAttribute(fieldName = "viplevel",fieldType = Integer.class,arrayType = Byte.class)
    public Integer viplevel = null;
    public Integer getViplevel() {return viplevel;}
    /** 玩家钱币数 */
    @DatabaseFieldAttribute(fieldName = "money",fieldType = Long.class,arrayType = Byte.class)
    public Long money = null;
    public Long getMoney() {return money;}
    /** 被打了 */
    @DatabaseFieldAttribute(fieldName = "beattacked",fieldType = Integer.class,arrayType = Byte.class)
    public Integer beattacked = null;
    public Integer getBeattacked() {return beattacked;}
    /** 所属军团id(不再使用) */
    @DatabaseFieldAttribute(fieldName = "belong_legion",fieldType = Integer.class,arrayType = Byte.class)
    public Integer belong_legion = null;
    public Integer getBelong_legion() {return belong_legion;}
    /** 玩家经验 */
    @DatabaseFieldAttribute(fieldName = "exp",fieldType = Integer.class,arrayType = Byte.class)
    public Integer exp = null;
    public Integer getExp() {return exp;}
    /** 玩家账户创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Byte.class)
    public Timestamp create_time = null;
    public Timestamp getCreate_time() {return create_time;}
    /** 国别 */
    @DatabaseFieldAttribute(fieldName = "nation",fieldType = String.class,arrayType = Byte.class)
    public String nation = null;
    public String getNation() {return nation;}
    /** 机器人英雄属性加成万分比 */
    @DatabaseFieldAttribute(fieldName = "hero_attr_plus",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hero_attr_plus = null;
    public Integer getHero_attr_plus() {return hero_attr_plus;}
    /** 中和战斗力 */
    @DatabaseFieldAttribute(fieldName = "fightVal",fieldType = Integer.class,arrayType = Byte.class)
    public Integer fightVal = null;
    public Integer getFightVal() {return fightVal;}
    /** 当前人口 */
    @DatabaseFieldAttribute(fieldName = "cur_population",fieldType = Integer.class,arrayType = Byte.class)
    public Integer cur_population = null;
    public Integer getCur_population() {return cur_population;}
    /** 总人口 */
    @DatabaseFieldAttribute(fieldName = "totle_population",fieldType = Integer.class,arrayType = Byte.class)
    public Integer totle_population = null;
    public Integer getTotle_population() {return totle_population;}
    /** 战斗总次数 */
    @DatabaseFieldAttribute(fieldName = "fightingcount",fieldType = Integer.class,arrayType = Byte.class)
    public Integer fightingcount = null;
    public Integer getFightingcount() {return fightingcount;}
    /** 战斗胜利总次数 */
    @DatabaseFieldAttribute(fieldName = "victorycount",fieldType = Integer.class,arrayType = Byte.class)
    public Integer victorycount = null;
    public Integer getVictorycount() {return victorycount;}
    /** 匹配列表 */
    @DatabaseFieldAttribute(fieldName = "match_list",fieldType = String.class,arrayType = CustomPvpPlayerInfo.class)
    public List<CustomPvpPlayerInfo> match_list = null;
    public List<CustomPvpPlayerInfo> getMatch_list() {return match_list;}
    /** 自己工厂被占领开始时间 */
    @DatabaseFieldAttribute(fieldName = "self_factory_start_time",fieldType = Long.class,arrayType = Byte.class)
    public Long self_factory_start_time = null;
    public Long getSelf_factory_start_time() {return self_factory_start_time;}
    /** 自己工厂被占领玩家id */
    @DatabaseFieldAttribute(fieldName = "self_factory_playerId",fieldType = Long.class,arrayType = Byte.class)
    public Long self_factory_playerId = null;
    public Long getSelf_factory_playerId() {return self_factory_playerId;}
    /** 别人工厂被占领开始时间 */
    @DatabaseFieldAttribute(fieldName = "other_factory_start_time",fieldType = Long.class,arrayType = Byte.class)
    public Long other_factory_start_time = null;
    public Long getOther_factory_start_time() {return other_factory_start_time;}
    /** 别人工厂被占领玩家id */
    @DatabaseFieldAttribute(fieldName = "other_factory_playerId",fieldType = Long.class,arrayType = Byte.class)
    public Long other_factory_playerId = null;
    public Long getOther_factory_playerId() {return other_factory_playerId;}
    /** 别人工厂被占领玩家名称 */
    @DatabaseFieldAttribute(fieldName = "other_factory_playerName",fieldType = String.class,arrayType = Byte.class)
    public String other_factory_playerName = null;
    public String getOther_factory_playerName() {return other_factory_playerName;}
    /** 别人工厂被占领玩家功勋 */
    @DatabaseFieldAttribute(fieldName = "other_factory_playerFeat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer other_factory_playerFeat = null;
    public Integer getOther_factory_playerFeat() {return other_factory_playerFeat;}
    /** 最近一次登录时间 */
    @DatabaseFieldAttribute(fieldName = "last_login_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_login_time = null;
    public Long getLast_login_time() {return last_login_time;}
    /** 是否有战火 */
    @DatabaseFieldAttribute(fieldName = "fire_stat",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean fire_stat = null;
    public Boolean getFire_stat() {return fire_stat;}
    /** 战火自动熄灭时间 */
    @DatabaseFieldAttribute(fieldName = "fire_end_time",fieldType = Long.class,arrayType = Byte.class)
    public Long fire_end_time = null;
    public Long getFire_end_time() {return fire_end_time;}
    /** 参加活动积分等级 */
    @DatabaseFieldAttribute(fieldName = "join_activity_point_level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer join_activity_point_level = null;
    public Integer getJoin_activity_point_level() {return join_activity_point_level;}
    /** 参加活动玩家的功勋 */
    @DatabaseFieldAttribute(fieldName = "join_activity_feat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer join_activity_feat = null;
    public Integer getJoin_activity_feat() {return join_activity_feat;}
    /** 参加活动时间 */
    @DatabaseFieldAttribute(fieldName = "join_activity_time",fieldType = Long.class,arrayType = Byte.class)
    public Long join_activity_time = null;
    public Long getJoin_activity_time() {return join_activity_time;}
    /** 活动类型 */
    @DatabaseFieldAttribute(fieldName = "activity_type",fieldType = Integer.class,arrayType = commonality.Common.PVP_ACTIVITY_TYPE.class)
    public commonality.Common.PVP_ACTIVITY_TYPE activity_type = null;
    public commonality.Common.PVP_ACTIVITY_TYPE getActivity_type() {return activity_type;}
    /** 活动分数 */
    @DatabaseFieldAttribute(fieldName = "activity_point",fieldType = Integer.class,arrayType = Byte.class)
    public Integer activity_point = null;
    public Integer getActivity_point() {return activity_point;}
    /** 活动分组类型 */
    @DatabaseFieldAttribute(fieldName = "activity_group_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer activity_group_type = null;
    public Integer getActivity_group_type() {return activity_group_type;}
    /** 活动分组玩家信息 */
    @DatabaseFieldAttribute(fieldName = "activity_group",fieldType = String.class,arrayType = CustomPVPActivityGroupPlayerInfo.class)
    public List<CustomPVPActivityGroupPlayerInfo> activity_group = null;
    public List<CustomPVPActivityGroupPlayerInfo> getActivity_group() {return activity_group;}
    /** 上次活动排名 */
    @DatabaseFieldAttribute(fieldName = "last_activity_rank",fieldType = Integer.class,arrayType = Byte.class)
    public Integer last_activity_rank = null;
    public Integer getLast_activity_rank() {return last_activity_rank;}
    /** 上次活动分数 */
    @DatabaseFieldAttribute(fieldName = "last_activity_point",fieldType = Integer.class,arrayType = Byte.class)
    public Integer last_activity_point = null;
    public Integer getLast_activity_point() {return last_activity_point;}
    /** 上次参加活动时间 */
    @DatabaseFieldAttribute(fieldName = "last_join_activity_time",fieldType = Long.class,arrayType = Byte.class)
    public Long last_join_activity_time = null;
    public Long getLast_join_activity_time() {return last_join_activity_time;}
    /** 上次活动分组类型 */
    @DatabaseFieldAttribute(fieldName = "last_activity_group_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer last_activity_group_type = null;
    public Integer getLast_activity_group_type() {return last_activity_group_type;}
    /** 是否领取积分奖励档次标示 */
    @DatabaseFieldAttribute(fieldName = "get_activity_point_gift",fieldType = Integer.class,arrayType = Byte.class)
    public Integer get_activity_point_gift = null;
    public Integer getGet_activity_point_gift() {return get_activity_point_gift;}
    /** 是否领取排名奖励标示 */
    @DatabaseFieldAttribute(fieldName = "get_activity_rank_gift",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean get_activity_rank_gift = null;
    public Boolean getGet_activity_rank_gift() {return get_activity_rank_gift;}
    /** 玩家头像 */
    @DatabaseFieldAttribute(fieldName = "head",fieldType = Integer.class,arrayType = Byte.class)
    public Integer head = null;
    public Integer getHead() {return head;}
    /** facebook头像URL */
    @DatabaseFieldAttribute(fieldName = "head_url",fieldType = String.class,arrayType = String.class)
    public String head_url = null;
    public String getHead_url() {return head_url;}
    /** 国王战最后一次开始时间 */
    @DatabaseFieldAttribute(fieldName = "lastKingStartTime",fieldType = Long.class,arrayType = Byte.class)
    public Long lastKingStartTime = null;
    public Long getLastKingStartTime() {return lastKingStartTime;}
    /** 国王战福利列表 */
    @DatabaseFieldAttribute(fieldName = "kingWelfareList",fieldType = String.class,arrayType = CustomKingWelfareInfo.class)
    public List<CustomKingWelfareInfo> kingWelfareList = null;
    public List<CustomKingWelfareInfo> getKingWelfareList() {return kingWelfareList;}
    /** 国王战最后一次支援时间 */
    @DatabaseFieldAttribute(fieldName = "lastKingDonateTime",fieldType = Long.class,arrayType = Byte.class)
    public Long lastKingDonateTime = null;
    public Long getLastKingDonateTime() {return lastKingDonateTime;}
    /** 国王战最后一次攻击时间 */
    @DatabaseFieldAttribute(fieldName = "lastKingAttackTime",fieldType = Long.class,arrayType = Byte.class)
    public Long lastKingAttackTime = null;
    public Long getLastKingAttackTime() {return lastKingAttackTime;}
    /** 国王战捐献士兵值 */
    @DatabaseFieldAttribute(fieldName = "kingSoldierVal",fieldType = Integer.class,arrayType = Byte.class)
    public Integer kingSoldierVal = null;
    public Integer getKingSoldierVal() {return kingSoldierVal;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePvp_match __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePvp_match();
        __self.set(this);
    }
    /** DatabaseSimple对象  用于获取获得此对象的 数据库对象 */
    private DatabaseSimple __simple = null;
    @Override
    public void setDatabaseSimple(DatabaseSimple value) {
        __simple = value;
    }
    @Override
    public DatabaseSimple getDatabaseSimple() {
        return __simple;
    }
    @Override
    public void save() {
        if (__simple != null && getPrimaryKeyName() != null && getPrimaryKeyValue() != null)
            __simple.Update(this);
    }
    @Override
    public DatabasePvp_match diff()
    {
        DatabasePvp_match ret = new DatabasePvp_match();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.online != null && (__self == null || !this.online.equals(__self.online)))
            ret.online = this.online;
        if (this.robot != null && (__self == null || !this.robot.equals(__self.robot)))
            ret.robot = this.robot;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.by_attack_time != null && (__self == null || !this.by_attack_time.equals(__self.by_attack_time)))
            ret.by_attack_time = this.by_attack_time;
        if (this.shield_end_time != null && (__self == null || !this.shield_end_time.equals(__self.shield_end_time)))
            ret.shield_end_time = this.shield_end_time;
        if (this.state != null && (__self == null || !this.state.equals(__self.state)))
            ret.state = this.state;
        if (this.matchtime != null && (__self == null || !this.matchtime.equals(__self.matchtime)))
            ret.matchtime = this.matchtime;
        if (this.fightingtime != null && (__self == null || !this.fightingtime.equals(__self.fightingtime)))
            ret.fightingtime = this.fightingtime;
        if (this.defenseformation != null && (__self == null || !this.defenseformation.equals(__self.defenseformation)))
            ret.defenseformation = this.defenseformation;
        if (this.feat != null && (__self == null || !this.feat.equals(__self.feat)))
            ret.feat = this.feat;
        if (this.match_player_id != null && (__self == null || !this.match_player_id.equals(__self.match_player_id)))
            ret.match_player_id = this.match_player_id;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.viplevel != null && (__self == null || !this.viplevel.equals(__self.viplevel)))
            ret.viplevel = this.viplevel;
        if (this.money != null && (__self == null || !this.money.equals(__self.money)))
            ret.money = this.money;
        if (this.beattacked != null && (__self == null || !this.beattacked.equals(__self.beattacked)))
            ret.beattacked = this.beattacked;
        if (this.belong_legion != null && (__self == null || !this.belong_legion.equals(__self.belong_legion)))
            ret.belong_legion = this.belong_legion;
        if (this.exp != null && (__self == null || !this.exp.equals(__self.exp)))
            ret.exp = this.exp;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.nation != null && (__self == null || !this.nation.equals(__self.nation)))
            ret.nation = this.nation;
        if (this.hero_attr_plus != null && (__self == null || !this.hero_attr_plus.equals(__self.hero_attr_plus)))
            ret.hero_attr_plus = this.hero_attr_plus;
        if (this.fightVal != null && (__self == null || !this.fightVal.equals(__self.fightVal)))
            ret.fightVal = this.fightVal;
        if (this.cur_population != null && (__self == null || !this.cur_population.equals(__self.cur_population)))
            ret.cur_population = this.cur_population;
        if (this.totle_population != null && (__self == null || !this.totle_population.equals(__self.totle_population)))
            ret.totle_population = this.totle_population;
        if (this.fightingcount != null && (__self == null || !this.fightingcount.equals(__self.fightingcount)))
            ret.fightingcount = this.fightingcount;
        if (this.victorycount != null && (__self == null || !this.victorycount.equals(__self.victorycount)))
            ret.victorycount = this.victorycount;
        if (this.match_list != null && (__self == null || !this.match_list.equals(__self.match_list)))
            ret.match_list = this.match_list;
        if (this.self_factory_start_time != null && (__self == null || !this.self_factory_start_time.equals(__self.self_factory_start_time)))
            ret.self_factory_start_time = this.self_factory_start_time;
        if (this.self_factory_playerId != null && (__self == null || !this.self_factory_playerId.equals(__self.self_factory_playerId)))
            ret.self_factory_playerId = this.self_factory_playerId;
        if (this.other_factory_start_time != null && (__self == null || !this.other_factory_start_time.equals(__self.other_factory_start_time)))
            ret.other_factory_start_time = this.other_factory_start_time;
        if (this.other_factory_playerId != null && (__self == null || !this.other_factory_playerId.equals(__self.other_factory_playerId)))
            ret.other_factory_playerId = this.other_factory_playerId;
        if (this.other_factory_playerName != null && (__self == null || !this.other_factory_playerName.equals(__self.other_factory_playerName)))
            ret.other_factory_playerName = this.other_factory_playerName;
        if (this.other_factory_playerFeat != null && (__self == null || !this.other_factory_playerFeat.equals(__self.other_factory_playerFeat)))
            ret.other_factory_playerFeat = this.other_factory_playerFeat;
        if (this.last_login_time != null && (__self == null || !this.last_login_time.equals(__self.last_login_time)))
            ret.last_login_time = this.last_login_time;
        if (this.fire_stat != null && (__self == null || !this.fire_stat.equals(__self.fire_stat)))
            ret.fire_stat = this.fire_stat;
        if (this.fire_end_time != null && (__self == null || !this.fire_end_time.equals(__self.fire_end_time)))
            ret.fire_end_time = this.fire_end_time;
        if (this.join_activity_point_level != null && (__self == null || !this.join_activity_point_level.equals(__self.join_activity_point_level)))
            ret.join_activity_point_level = this.join_activity_point_level;
        if (this.join_activity_feat != null && (__self == null || !this.join_activity_feat.equals(__self.join_activity_feat)))
            ret.join_activity_feat = this.join_activity_feat;
        if (this.join_activity_time != null && (__self == null || !this.join_activity_time.equals(__self.join_activity_time)))
            ret.join_activity_time = this.join_activity_time;
        if (this.activity_type != null && (__self == null || !this.activity_type.equals(__self.activity_type)))
            ret.activity_type = this.activity_type;
        if (this.activity_point != null && (__self == null || !this.activity_point.equals(__self.activity_point)))
            ret.activity_point = this.activity_point;
        if (this.activity_group_type != null && (__self == null || !this.activity_group_type.equals(__self.activity_group_type)))
            ret.activity_group_type = this.activity_group_type;
        if (this.activity_group != null && (__self == null || !this.activity_group.equals(__self.activity_group)))
            ret.activity_group = this.activity_group;
        if (this.last_activity_rank != null && (__self == null || !this.last_activity_rank.equals(__self.last_activity_rank)))
            ret.last_activity_rank = this.last_activity_rank;
        if (this.last_activity_point != null && (__self == null || !this.last_activity_point.equals(__self.last_activity_point)))
            ret.last_activity_point = this.last_activity_point;
        if (this.last_join_activity_time != null && (__self == null || !this.last_join_activity_time.equals(__self.last_join_activity_time)))
            ret.last_join_activity_time = this.last_join_activity_time;
        if (this.last_activity_group_type != null && (__self == null || !this.last_activity_group_type.equals(__self.last_activity_group_type)))
            ret.last_activity_group_type = this.last_activity_group_type;
        if (this.get_activity_point_gift != null && (__self == null || !this.get_activity_point_gift.equals(__self.get_activity_point_gift)))
            ret.get_activity_point_gift = this.get_activity_point_gift;
        if (this.get_activity_rank_gift != null && (__self == null || !this.get_activity_rank_gift.equals(__self.get_activity_rank_gift)))
            ret.get_activity_rank_gift = this.get_activity_rank_gift;
        if (this.head != null && (__self == null || !this.head.equals(__self.head)))
            ret.head = this.head;
        if (this.head_url != null && (__self == null || !this.head_url.equals(__self.head_url)))
            ret.head_url = this.head_url;
        if (this.lastKingStartTime != null && (__self == null || !this.lastKingStartTime.equals(__self.lastKingStartTime)))
            ret.lastKingStartTime = this.lastKingStartTime;
        if (this.kingWelfareList != null && (__self == null || !this.kingWelfareList.equals(__self.kingWelfareList)))
            ret.kingWelfareList = this.kingWelfareList;
        if (this.lastKingDonateTime != null && (__self == null || !this.lastKingDonateTime.equals(__self.lastKingDonateTime)))
            ret.lastKingDonateTime = this.lastKingDonateTime;
        if (this.lastKingAttackTime != null && (__self == null || !this.lastKingAttackTime.equals(__self.lastKingAttackTime)))
            ret.lastKingAttackTime = this.lastKingAttackTime;
        if (this.kingSoldierVal != null && (__self == null || !this.kingSoldierVal.equals(__self.kingSoldierVal)))
            ret.kingSoldierVal = this.kingSoldierVal;
        return ret;
    }
    @Override
    public void set(DatabasePvp_match value) {
        this.player_id = value.player_id;
        this.online = value.online;
        this.robot = value.robot;
        this.name = value.name;
        this.by_attack_time = value.by_attack_time;
        this.shield_end_time = value.shield_end_time;
        this.state = value.state;
        this.matchtime = value.matchtime;
        this.fightingtime = value.fightingtime;
        this.defenseformation = Utility.cloneList(value.defenseformation);
        this.feat = value.feat;
        this.match_player_id = value.match_player_id;
        this.level = value.level;
        this.viplevel = value.viplevel;
        this.money = value.money;
        this.beattacked = value.beattacked;
        this.belong_legion = value.belong_legion;
        this.exp = value.exp;
        this.create_time = value.create_time;
        this.nation = value.nation;
        this.hero_attr_plus = value.hero_attr_plus;
        this.fightVal = value.fightVal;
        this.cur_population = value.cur_population;
        this.totle_population = value.totle_population;
        this.fightingcount = value.fightingcount;
        this.victorycount = value.victorycount;
        this.match_list = Utility.cloneList(value.match_list);
        this.self_factory_start_time = value.self_factory_start_time;
        this.self_factory_playerId = value.self_factory_playerId;
        this.other_factory_start_time = value.other_factory_start_time;
        this.other_factory_playerId = value.other_factory_playerId;
        this.other_factory_playerName = value.other_factory_playerName;
        this.other_factory_playerFeat = value.other_factory_playerFeat;
        this.last_login_time = value.last_login_time;
        this.fire_stat = value.fire_stat;
        this.fire_end_time = value.fire_end_time;
        this.join_activity_point_level = value.join_activity_point_level;
        this.join_activity_feat = value.join_activity_feat;
        this.join_activity_time = value.join_activity_time;
        this.activity_type = value.activity_type;
        this.activity_point = value.activity_point;
        this.activity_group_type = value.activity_group_type;
        this.activity_group = Utility.cloneList(value.activity_group);
        this.last_activity_rank = value.last_activity_rank;
        this.last_activity_point = value.last_activity_point;
        this.last_join_activity_time = value.last_join_activity_time;
        this.last_activity_group_type = value.last_activity_group_type;
        this.get_activity_point_gift = value.get_activity_point_gift;
        this.get_activity_rank_gift = value.get_activity_rank_gift;
        this.head = value.head;
        this.head_url = value.head_url;
        this.lastKingStartTime = value.lastKingStartTime;
        this.kingWelfareList = Utility.cloneList(value.kingWelfareList);
        this.lastKingDonateTime = value.lastKingDonateTime;
        this.lastKingAttackTime = value.lastKingAttackTime;
        this.kingSoldierVal = value.kingSoldierVal;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.online != null) return false;
        if (this.robot != null) return false;
        if (this.name != null) return false;
        if (this.by_attack_time != null) return false;
        if (this.shield_end_time != null) return false;
        if (this.state != null) return false;
        if (this.matchtime != null) return false;
        if (this.fightingtime != null) return false;
        if (this.defenseformation != null) return false;
        if (this.feat != null) return false;
        if (this.match_player_id != null) return false;
        if (this.level != null) return false;
        if (this.viplevel != null) return false;
        if (this.money != null) return false;
        if (this.beattacked != null) return false;
        if (this.belong_legion != null) return false;
        if (this.exp != null) return false;
        if (this.create_time != null) return false;
        if (this.nation != null) return false;
        if (this.hero_attr_plus != null) return false;
        if (this.fightVal != null) return false;
        if (this.cur_population != null) return false;
        if (this.totle_population != null) return false;
        if (this.fightingcount != null) return false;
        if (this.victorycount != null) return false;
        if (this.match_list != null) return false;
        if (this.self_factory_start_time != null) return false;
        if (this.self_factory_playerId != null) return false;
        if (this.other_factory_start_time != null) return false;
        if (this.other_factory_playerId != null) return false;
        if (this.other_factory_playerName != null) return false;
        if (this.other_factory_playerFeat != null) return false;
        if (this.last_login_time != null) return false;
        if (this.fire_stat != null) return false;
        if (this.fire_end_time != null) return false;
        if (this.join_activity_point_level != null) return false;
        if (this.join_activity_feat != null) return false;
        if (this.join_activity_time != null) return false;
        if (this.activity_type != null) return false;
        if (this.activity_point != null) return false;
        if (this.activity_group_type != null) return false;
        if (this.activity_group != null) return false;
        if (this.last_activity_rank != null) return false;
        if (this.last_activity_point != null) return false;
        if (this.last_join_activity_time != null) return false;
        if (this.last_activity_group_type != null) return false;
        if (this.get_activity_point_gift != null) return false;
        if (this.get_activity_rank_gift != null) return false;
        if (this.head != null) return false;
        if (this.head_url != null) return false;
        if (this.lastKingStartTime != null) return false;
        if (this.kingWelfareList != null) return false;
        if (this.lastKingDonateTime != null) return false;
        if (this.lastKingAttackTime != null) return false;
        if (this.kingSoldierVal != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.online = null;
        this.robot = null;
        this.name = null;
        this.by_attack_time = null;
        this.shield_end_time = null;
        this.state = null;
        this.matchtime = null;
        this.fightingtime = null;
        this.defenseformation = null;
        this.feat = null;
        this.match_player_id = null;
        this.level = null;
        this.viplevel = null;
        this.money = null;
        this.beattacked = null;
        this.belong_legion = null;
        this.exp = null;
        this.create_time = null;
        this.nation = null;
        this.hero_attr_plus = null;
        this.fightVal = null;
        this.cur_population = null;
        this.totle_population = null;
        this.fightingcount = null;
        this.victorycount = null;
        this.match_list = null;
        this.self_factory_start_time = null;
        this.self_factory_playerId = null;
        this.other_factory_start_time = null;
        this.other_factory_playerId = null;
        this.other_factory_playerName = null;
        this.other_factory_playerFeat = null;
        this.last_login_time = null;
        this.fire_stat = null;
        this.fire_end_time = null;
        this.join_activity_point_level = null;
        this.join_activity_feat = null;
        this.join_activity_time = null;
        this.activity_type = null;
        this.activity_point = null;
        this.activity_group_type = null;
        this.activity_group = null;
        this.last_activity_rank = null;
        this.last_activity_point = null;
        this.last_join_activity_time = null;
        this.last_activity_group_type = null;
        this.get_activity_point_gift = null;
        this.get_activity_rank_gift = null;
        this.head = null;
        this.head_url = null;
        this.lastKingStartTime = null;
        this.kingWelfareList = null;
        this.lastKingDonateTime = null;
        this.lastKingAttackTime = null;
        this.kingSoldierVal = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.online != null)
            ret += ("online = " + this.online.toString() + " ");
        if (this.robot != null)
            ret += ("robot = " + this.robot.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.by_attack_time != null)
            ret += ("by_attack_time = " + this.by_attack_time.toString() + " ");
        if (this.shield_end_time != null)
            ret += ("shield_end_time = " + this.shield_end_time.toString() + " ");
        if (this.state != null)
            ret += ("state = " + this.state.toString() + " ");
        if (this.matchtime != null)
            ret += ("matchtime = " + this.matchtime.toString() + " ");
        if (this.fightingtime != null)
            ret += ("fightingtime = " + this.fightingtime.toString() + " ");
        if (this.defenseformation != null)
            ret += ("defenseformation = " + this.defenseformation.toString() + " ");
        if (this.feat != null)
            ret += ("feat = " + this.feat.toString() + " ");
        if (this.match_player_id != null)
            ret += ("match_player_id = " + this.match_player_id.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.viplevel != null)
            ret += ("viplevel = " + this.viplevel.toString() + " ");
        if (this.money != null)
            ret += ("money = " + this.money.toString() + " ");
        if (this.beattacked != null)
            ret += ("beattacked = " + this.beattacked.toString() + " ");
        if (this.belong_legion != null)
            ret += ("belong_legion = " + this.belong_legion.toString() + " ");
        if (this.exp != null)
            ret += ("exp = " + this.exp.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.nation != null)
            ret += ("nation = " + this.nation.toString() + " ");
        if (this.hero_attr_plus != null)
            ret += ("hero_attr_plus = " + this.hero_attr_plus.toString() + " ");
        if (this.fightVal != null)
            ret += ("fightVal = " + this.fightVal.toString() + " ");
        if (this.cur_population != null)
            ret += ("cur_population = " + this.cur_population.toString() + " ");
        if (this.totle_population != null)
            ret += ("totle_population = " + this.totle_population.toString() + " ");
        if (this.fightingcount != null)
            ret += ("fightingcount = " + this.fightingcount.toString() + " ");
        if (this.victorycount != null)
            ret += ("victorycount = " + this.victorycount.toString() + " ");
        if (this.match_list != null)
            ret += ("match_list = " + this.match_list.toString() + " ");
        if (this.self_factory_start_time != null)
            ret += ("self_factory_start_time = " + this.self_factory_start_time.toString() + " ");
        if (this.self_factory_playerId != null)
            ret += ("self_factory_playerId = " + this.self_factory_playerId.toString() + " ");
        if (this.other_factory_start_time != null)
            ret += ("other_factory_start_time = " + this.other_factory_start_time.toString() + " ");
        if (this.other_factory_playerId != null)
            ret += ("other_factory_playerId = " + this.other_factory_playerId.toString() + " ");
        if (this.other_factory_playerName != null)
            ret += ("other_factory_playerName = " + this.other_factory_playerName.toString() + " ");
        if (this.other_factory_playerFeat != null)
            ret += ("other_factory_playerFeat = " + this.other_factory_playerFeat.toString() + " ");
        if (this.last_login_time != null)
            ret += ("last_login_time = " + this.last_login_time.toString() + " ");
        if (this.fire_stat != null)
            ret += ("fire_stat = " + this.fire_stat.toString() + " ");
        if (this.fire_end_time != null)
            ret += ("fire_end_time = " + this.fire_end_time.toString() + " ");
        if (this.join_activity_point_level != null)
            ret += ("join_activity_point_level = " + this.join_activity_point_level.toString() + " ");
        if (this.join_activity_feat != null)
            ret += ("join_activity_feat = " + this.join_activity_feat.toString() + " ");
        if (this.join_activity_time != null)
            ret += ("join_activity_time = " + this.join_activity_time.toString() + " ");
        if (this.activity_type != null)
            ret += ("activity_type = " + this.activity_type.toString() + " ");
        if (this.activity_point != null)
            ret += ("activity_point = " + this.activity_point.toString() + " ");
        if (this.activity_group_type != null)
            ret += ("activity_group_type = " + this.activity_group_type.toString() + " ");
        if (this.activity_group != null)
            ret += ("activity_group = " + this.activity_group.toString() + " ");
        if (this.last_activity_rank != null)
            ret += ("last_activity_rank = " + this.last_activity_rank.toString() + " ");
        if (this.last_activity_point != null)
            ret += ("last_activity_point = " + this.last_activity_point.toString() + " ");
        if (this.last_join_activity_time != null)
            ret += ("last_join_activity_time = " + this.last_join_activity_time.toString() + " ");
        if (this.last_activity_group_type != null)
            ret += ("last_activity_group_type = " + this.last_activity_group_type.toString() + " ");
        if (this.get_activity_point_gift != null)
            ret += ("get_activity_point_gift = " + this.get_activity_point_gift.toString() + " ");
        if (this.get_activity_rank_gift != null)
            ret += ("get_activity_rank_gift = " + this.get_activity_rank_gift.toString() + " ");
        if (this.head != null)
            ret += ("head = " + this.head.toString() + " ");
        if (this.head_url != null)
            ret += ("head_url = " + this.head_url.toString() + " ");
        if (this.lastKingStartTime != null)
            ret += ("lastKingStartTime = " + this.lastKingStartTime.toString() + " ");
        if (this.kingWelfareList != null)
            ret += ("kingWelfareList = " + this.kingWelfareList.toString() + " ");
        if (this.lastKingDonateTime != null)
            ret += ("lastKingDonateTime = " + this.lastKingDonateTime.toString() + " ");
        if (this.lastKingAttackTime != null)
            ret += ("lastKingAttackTime = " + this.lastKingAttackTime.toString() + " ");
        if (this.kingSoldierVal != null)
            ret += ("kingSoldierVal = " + this.kingSoldierVal.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "pvp_match";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (defenseformation != null && defenseformation.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : defenseformation) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    defenseformation.remove(data);
		    }
        }
        if (match_list != null && match_list.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : match_list) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    match_list.remove(data);
		    }
        }
        if (activity_group != null && activity_group.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : activity_group) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    activity_group.remove(data);
		    }
        }
        if (kingWelfareList != null && kingWelfareList.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : kingWelfareList) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    kingWelfareList.remove(data);
		    }
        }
    }
    @Override
    public String getPrimaryKeyName() {
        return "player_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return player_id;
    }
}