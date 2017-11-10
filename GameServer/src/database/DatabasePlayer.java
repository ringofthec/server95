// 本文件是自动生成，不要手动修改
package database;
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
//================玩家信息表===========================
@SuppressWarnings("unused")
public class DatabasePlayer implements DatabaseTableDataBase<DatabasePlayer>,Serializable {
    public static final String TableName = "player";
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 玩家头像 */
    @DatabaseFieldAttribute(fieldName = "head",fieldType = Integer.class,arrayType = Byte.class)
    public Integer head = null;
    public Integer getHead() {return head;}
    /** 玩家等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** Vip等级 */
    @DatabaseFieldAttribute(fieldName = "vipLevel",fieldType = Integer.class,arrayType = Byte.class)
    public Integer vipLevel = null;
    public Integer getVipLevel() {return vipLevel;}
    /** VIP过期时间 */
    @DatabaseFieldAttribute(fieldName = "vipOverdueTime",fieldType = Timestamp.class,arrayType = Long.class)
    public Long vipOverdueTime = null;
    public Long getVipOverdueTime() {return vipOverdueTime;}
    /** 战斗总次数 */
    @DatabaseFieldAttribute(fieldName = "fightingcount",fieldType = Integer.class,arrayType = Byte.class)
    public Integer fightingcount = null;
    public Integer getFightingcount() {return fightingcount;}
    /** 战斗胜利总次数 */
    @DatabaseFieldAttribute(fieldName = "victorycount",fieldType = Integer.class,arrayType = Byte.class)
    public Integer victorycount = null;
    public Integer getVictorycount() {return victorycount;}
    /** 队形1 */
    @DatabaseFieldAttribute(fieldName = "formation1",fieldType = String.class,arrayType = CustomFormation.class)
    public List<CustomFormation> formation1 = null;
    public List<CustomFormation> getFormation1() {return formation1;}
    /** 队形2 */
    @DatabaseFieldAttribute(fieldName = "formation2",fieldType = String.class,arrayType = CustomFormation.class)
    public List<CustomFormation> formation2 = null;
    public List<CustomFormation> getFormation2() {return formation2;}
    /** 当前的最高Pve关数 */
    @DatabaseFieldAttribute(fieldName = "instance_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer instance_id = null;
    public Integer getInstance_id() {return instance_id;}
    /** 功勋 */
    @DatabaseFieldAttribute(fieldName = "feat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer feat = null;
    public Integer getFeat() {return feat;}
    /** 已打关的星数 */
    @DatabaseFieldAttribute(fieldName = "instance_star_array",fieldType = String.class,arrayType = CustomInstanceStar.class)
    public List<CustomInstanceStar> instance_star_array = null;
    public List<CustomInstanceStar> getInstance_star_array() {return instance_star_array;}
    /** 大关信息 */
    @DatabaseFieldAttribute(fieldName = "superiorInfo",fieldType = String.class,arrayType = CustomSuperior.class)
    public List<CustomSuperior> superiorInfo = null;
    public List<CustomSuperior> getSuperiorInfo() {return superiorInfo;}
    /** 用来处理体力恢复 */
    @DatabaseFieldAttribute(fieldName = "cp_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long cp_time = null;
    public Long getCp_time() {return cp_time;}
    /** 玩家是否在线 */
    @DatabaseFieldAttribute(fieldName = "online",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean online = null;
    public Boolean getOnline() {return online;}
    /** 领取那关英雄 */
    @DatabaseFieldAttribute(fieldName = "selhero",fieldType = Integer.class,arrayType = Byte.class)
    public Integer selhero = null;
    public Integer getSelhero() {return selhero;}
    /** 扩地次数 */
    @DatabaseFieldAttribute(fieldName = "extendNumber",fieldType = Integer.class,arrayType = Byte.class)
    public Integer extendNumber = null;
    public Integer getExtendNumber() {return extendNumber;}
    /** PVP战斗次数 */
    @DatabaseFieldAttribute(fieldName = "pvpNumber",fieldType = Integer.class,arrayType = Byte.class)
    public Integer pvpNumber = null;
    public Integer getPvpNumber() {return pvpNumber;}
    /** PVP最后战斗时间 */
    @DatabaseFieldAttribute(fieldName = "pvpTime",fieldType = Timestamp.class,arrayType = Long.class)
    public Long pvpTime = null;
    public Long getPvpTime() {return pvpTime;}
    /** 所属军团id */
    @DatabaseFieldAttribute(fieldName = "belong_legion",fieldType = Integer.class,arrayType = Byte.class)
    public Integer belong_legion = null;
    public Integer getBelong_legion() {return belong_legion;}
    /** 捐献次数(金币),捐献金币一天只能捐献一次 */
    @DatabaseFieldAttribute(fieldName = "contribution_money_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer contribution_money_num = null;
    public Integer getContribution_money_num() {return contribution_money_num;}
    /** 购买的物品列表 */
    @DatabaseFieldAttribute(fieldName = "commodity_list",fieldType = String.class,arrayType = CustomBuyCommodity.class)
    public List<CustomBuyCommodity> commodity_list = null;
    public List<CustomBuyCommodity> getCommodity_list() {return commodity_list;}
    /** 队列大小 */
    @DatabaseFieldAttribute(fieldName = "queue_size",fieldType = Integer.class,arrayType = Byte.class)
    public Integer queue_size = null;
    public Integer getQueue_size() {return queue_size;}
    /** 幸运值 */
    @DatabaseFieldAttribute(fieldName = "luck_val",fieldType = Integer.class,arrayType = Byte.class)
    public Integer luck_val = null;
    public Integer getLuck_val() {return luck_val;}
    /** 军团商店刷新时间 */
    @DatabaseFieldAttribute(fieldName = "refresh_time",fieldType = Long.class,arrayType = Byte.class)
    public Long refresh_time = null;
    public Long getRefresh_time() {return refresh_time;}
    /** 军团商店列表 */
    @DatabaseFieldAttribute(fieldName = "legion_store_list",fieldType = String.class,arrayType = CustomLegionCommodity.class)
    public List<CustomLegionCommodity> legion_store_list = null;
    public List<CustomLegionCommodity> getLegion_store_list() {return legion_store_list;}
    /** 国别 */
    @DatabaseFieldAttribute(fieldName = "nation",fieldType = String.class,arrayType = Byte.class)
    public String nation = null;
    public String getNation() {return nation;}
    /** 累计登陆天数 */
    @DatabaseFieldAttribute(fieldName = "total_login_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer total_login_num = null;
    public Integer getTotal_login_num() {return total_login_num;}
    /** 上次领取时长奖励时间 */
    @DatabaseFieldAttribute(fieldName = "previous_time",fieldType = Long.class,arrayType = Byte.class)
    public Long previous_time = null;
    public Long getPrevious_time() {return previous_time;}
    /** 在线时间抽奖次数 */
    @DatabaseFieldAttribute(fieldName = "luck_draw_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer luck_draw_num = null;
    public Integer getLuck_draw_num() {return luck_draw_num;}
    /** 勋章免费抽奖时间 */
    @DatabaseFieldAttribute(fieldName = "luck_draw_free_time",fieldType = Long.class,arrayType = Byte.class)
    public Long luck_draw_free_time = null;
    public Long getLuck_draw_free_time() {return luck_draw_free_time;}
    /** 勋章抽奖免费次数 */
    @DatabaseFieldAttribute(fieldName = "luck_draw_free_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer luck_draw_free_num = null;
    public Integer getLuck_draw_free_num() {return luck_draw_free_num;}
    /** 上次下线时间 */
    @DatabaseFieldAttribute(fieldName = "last_offline_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_offline_time = null;
    public Long getLast_offline_time() {return last_offline_time;}
    /** 完成的新手指导ID（指导链第一个） */
    @DatabaseFieldAttribute(fieldName = "finishedGuide",fieldType = String.class,arrayType = Integer.class)
    public List<Integer> finishedGuide = null;
    public List<Integer> getFinishedGuide() {return finishedGuide;}
    /** 转盘第一次抽取。0:一次都没有抽过，1：抽过一次 */
    @DatabaseFieldAttribute(fieldName = "luck_draw_first_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer luck_draw_first_num = null;
    public Integer getLuck_draw_first_num() {return luck_draw_first_num;}
    /** 任务计数 */
    @DatabaseFieldAttribute(fieldName = "taskcompletecount",fieldType = String.class,arrayType = CustomTaskCompleteCount.class)
    public List<CustomTaskCompleteCount> taskcompletecount = null;
    public List<CustomTaskCompleteCount> getTaskcompletecount() {return taskcompletecount;}
    /** 推送id */
    @DatabaseFieldAttribute(fieldName = "registration_id",fieldType = String.class,arrayType = Byte.class)
    public String registration_id = null;
    public String getRegistration_id() {return registration_id;}
    /** 计数 */
    @DatabaseFieldAttribute(fieldName = "count_info",fieldType = String.class,arrayType = CustomCountInfo.class)
    public List<CustomCountInfo> count_info = null;
    public List<CustomCountInfo> getCount_info() {return count_info;}
    /** 语言 */
    @DatabaseFieldAttribute(fieldName = "language",fieldType = Integer.class,arrayType = Byte.class)
    public Integer language = null;
    public Integer getLanguage() {return language;}
    /** 是否是机器人 1:是 0：否 */
    @DatabaseFieldAttribute(fieldName = "robot",fieldType = Integer.class,arrayType = Byte.class)
    public Integer robot = null;
    public Integer getRobot() {return robot;}
    /** 游戏状态码 */
    @DatabaseFieldAttribute(fieldName = "gamestatusmask",fieldType = Integer.class,arrayType = Byte.class)
    public Integer gamestatusmask = null;
    public Integer getGamestatusmask() {return gamestatusmask;}
    /** 帐号创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 是否被禁言 0:正常，其他：被禁言到的时间点 */
    @DatabaseFieldAttribute(fieldName = "refuse_chat_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long refuse_chat_time = null;
    public Long getRefuse_chat_time() {return refuse_chat_time;}
    /** 是否被禁言 0:正常，1：被禁言 */
    @DatabaseFieldAttribute(fieldName = "refuse_chat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer refuse_chat = null;
    public Integer getRefuse_chat() {return refuse_chat;}
    /** pvp随机总数 */
    @DatabaseFieldAttribute(fieldName = "pvp_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer pvp_count = null;
    public Integer getPvp_count() {return pvp_count;}
    /** 勋章免费金币抽奖次数 */
    @DatabaseFieldAttribute(fieldName = "free_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer free_num = null;
    public Integer getFree_num() {return free_num;}
    /** 上次免费金币抽取勋章的时间 */
    @DatabaseFieldAttribute(fieldName = "free_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long free_time = null;
    public Long getFree_time() {return free_time;}
    /** 上次免费金转抽取勋章的时间 */
    @DatabaseFieldAttribute(fieldName = "free_gold_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long free_gold_time = null;
    public Long getFree_gold_time() {return free_gold_time;}
    /** 上次刷新时间 */
    @DatabaseFieldAttribute(fieldName = "last_flush_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_flush_time = null;
    public Long getLast_flush_time() {return last_flush_time;}
    /** 已打关列表 */
    @DatabaseFieldAttribute(fieldName = "hero_path_already_points",fieldType = String.class,arrayType = CustomHeroPathAlreadyPoint.class)
    public List<CustomHeroPathAlreadyPoint> hero_path_already_points = null;
    public List<CustomHeroPathAlreadyPoint> getHero_path_already_points() {return hero_path_already_points;}
    /** 已经兵种的关卡数组tableIds */
    @DatabaseFieldAttribute(fieldName = "hero_path_recived_crop_table_ids",fieldType = String.class,arrayType = Integer.class)
    public List<Integer> hero_path_recived_crop_table_ids = null;
    public List<Integer> getHero_path_recived_crop_table_ids() {return hero_path_recived_crop_table_ids;}
    /** 本方兵种等级列表 */
    @DatabaseFieldAttribute(fieldName = "hero_path_crops",fieldType = String.class,arrayType = CustomHeroPathCrop.class)
    public List<CustomHeroPathCrop> hero_path_crops = null;
    public List<CustomHeroPathCrop> getHero_path_crops() {return hero_path_crops;}
    /** 敌方士兵等级列表 */
    @DatabaseFieldAttribute(fieldName = "hero_path_enemy_crops",fieldType = String.class,arrayType = CustomHeroPathCrop.class)
    public List<CustomHeroPathCrop> hero_path_enemy_crops = null;
    public List<CustomHeroPathCrop> getHero_path_enemy_crops() {return hero_path_enemy_crops;}
    /** 敌方阵型列表 */
    @DatabaseFieldAttribute(fieldName = "hero_path_formation",fieldType = String.class,arrayType = CustomFormation.class)
    public List<CustomFormation> hero_path_formation = null;
    public List<CustomFormation> getHero_path_formation() {return hero_path_formation;}
    /** 当前关卡信息,list里面只有当前关卡一个元素 */
    @DatabaseFieldAttribute(fieldName = "hero_path_curPoint",fieldType = String.class,arrayType = CustomHeroPathCurPoint.class)
    public List<CustomHeroPathCurPoint> hero_path_curPoint = null;
    public List<CustomHeroPathCurPoint> getHero_path_curPoint() {return hero_path_curPoint;}
    /** 当前关卡我方布阵列表 */
    @DatabaseFieldAttribute(fieldName = "hero_path_cur_self_formation",fieldType = String.class,arrayType = CustomFormation.class)
    public List<CustomFormation> hero_path_cur_self_formation = null;
    public List<CustomFormation> getHero_path_cur_self_formation() {return hero_path_cur_self_formation;}
    /** 已经开启的宝箱数组 */
    @DatabaseFieldAttribute(fieldName = "hero_path_recive_box",fieldType = String.class,arrayType = Integer.class)
    public List<Integer> hero_path_recive_box = null;
    public List<Integer> getHero_path_recive_box() {return hero_path_recive_box;}
    /** 英雄之路刷新次数 */
    @DatabaseFieldAttribute(fieldName = "hero_path_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hero_path_num = null;
    public Integer getHero_path_num() {return hero_path_num;}
    /** 返利基金 */
    @DatabaseFieldAttribute(fieldName = "fund_end_day",fieldType = Timestamp.class,arrayType = Long.class)
    public Long fund_end_day = null;
    public Long getFund_end_day() {return fund_end_day;}
    /** 上次领基金时间 */
    @DatabaseFieldAttribute(fieldName = "last_recv_fund_day",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_recv_fund_day = null;
    public Long getLast_recv_fund_day() {return last_recv_fund_day;}
    /** 玩家连续登陆天数 */
    @DatabaseFieldAttribute(fieldName = "continue_login_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer continue_login_num = null;
    public Integer getContinue_login_num() {return continue_login_num;}
    /** 玩家连续登陆奖励领取记录 */
    @DatabaseFieldAttribute(fieldName = "continue_login_reward",fieldType = Integer.class,arrayType = Byte.class)
    public Integer continue_login_reward = null;
    public Integer getContinue_login_reward() {return continue_login_reward;}
    /** 玩家今天被帮助总钱数 */
    @DatabaseFieldAttribute(fieldName = "today_help_money_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer today_help_money_count = null;
    public Integer getToday_help_money_count() {return today_help_money_count;}
    /** 玩家今天被帮助总士兵数 */
    @DatabaseFieldAttribute(fieldName = "today_help_corp_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer today_help_corp_count = null;
    public Integer getToday_help_corp_count() {return today_help_corp_count;}
    /** 玩家战斗力 */
    @DatabaseFieldAttribute(fieldName = "fight_value",fieldType = Integer.class,arrayType = Byte.class)
    public Integer fight_value = null;
    public Integer getFight_value() {return fight_value;}
    /** 悬赏关卡最后一次挑战时间 */
    @DatabaseFieldAttribute(fieldName = "reward_last_play_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long reward_last_play_time = null;
    public Long getReward_last_play_time() {return reward_last_play_time;}
    /** 悬赏关卡当前关卡号 */
    @DatabaseFieldAttribute(fieldName = "reward_cur_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer reward_cur_id = null;
    public Integer getReward_cur_id() {return reward_cur_id;}
    /** 悬赏关卡敌方士兵等级列表 */
    @DatabaseFieldAttribute(fieldName = "reward_enemy_crops",fieldType = String.class,arrayType = CustomFormationCrops.class)
    public List<CustomFormationCrops> reward_enemy_crops = null;
    public List<CustomFormationCrops> getReward_enemy_crops() {return reward_enemy_crops;}
    /** 悬赏关卡敌方阵型列表 */
    @DatabaseFieldAttribute(fieldName = "reward_enemy_formation",fieldType = String.class,arrayType = CustomFormation.class)
    public List<CustomFormation> reward_enemy_formation = null;
    public List<CustomFormation> getReward_enemy_formation() {return reward_enemy_formation;}
    /** 举报列表 */
    @DatabaseFieldAttribute(fieldName = "chatReport",fieldType = String.class,arrayType = CustomChatReport.class)
    public List<CustomChatReport> chatReport = null;
    public List<CustomChatReport> getChatReport() {return chatReport;}
    /** 支付次数 */
    @DatabaseFieldAttribute(fieldName = "pay_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer pay_count = null;
    public Integer getPay_count() {return pay_count;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePlayer __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePlayer();
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
    public DatabasePlayer diff()
    {
        DatabasePlayer ret = new DatabasePlayer();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.head != null && (__self == null || !this.head.equals(__self.head)))
            ret.head = this.head;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.vipLevel != null && (__self == null || !this.vipLevel.equals(__self.vipLevel)))
            ret.vipLevel = this.vipLevel;
        if (this.vipOverdueTime != null && (__self == null || !this.vipOverdueTime.equals(__self.vipOverdueTime)))
            ret.vipOverdueTime = this.vipOverdueTime;
        if (this.fightingcount != null && (__self == null || !this.fightingcount.equals(__self.fightingcount)))
            ret.fightingcount = this.fightingcount;
        if (this.victorycount != null && (__self == null || !this.victorycount.equals(__self.victorycount)))
            ret.victorycount = this.victorycount;
        if (this.formation1 != null && (__self == null || !this.formation1.equals(__self.formation1)))
            ret.formation1 = this.formation1;
        if (this.formation2 != null && (__self == null || !this.formation2.equals(__self.formation2)))
            ret.formation2 = this.formation2;
        if (this.instance_id != null && (__self == null || !this.instance_id.equals(__self.instance_id)))
            ret.instance_id = this.instance_id;
        if (this.feat != null && (__self == null || !this.feat.equals(__self.feat)))
            ret.feat = this.feat;
        if (this.instance_star_array != null && (__self == null || !this.instance_star_array.equals(__self.instance_star_array)))
            ret.instance_star_array = this.instance_star_array;
        if (this.superiorInfo != null && (__self == null || !this.superiorInfo.equals(__self.superiorInfo)))
            ret.superiorInfo = this.superiorInfo;
        if (this.cp_time != null && (__self == null || !this.cp_time.equals(__self.cp_time)))
            ret.cp_time = this.cp_time;
        if (this.online != null && (__self == null || !this.online.equals(__self.online)))
            ret.online = this.online;
        if (this.selhero != null && (__self == null || !this.selhero.equals(__self.selhero)))
            ret.selhero = this.selhero;
        if (this.extendNumber != null && (__self == null || !this.extendNumber.equals(__self.extendNumber)))
            ret.extendNumber = this.extendNumber;
        if (this.pvpNumber != null && (__self == null || !this.pvpNumber.equals(__self.pvpNumber)))
            ret.pvpNumber = this.pvpNumber;
        if (this.pvpTime != null && (__self == null || !this.pvpTime.equals(__self.pvpTime)))
            ret.pvpTime = this.pvpTime;
        if (this.belong_legion != null && (__self == null || !this.belong_legion.equals(__self.belong_legion)))
            ret.belong_legion = this.belong_legion;
        if (this.contribution_money_num != null && (__self == null || !this.contribution_money_num.equals(__self.contribution_money_num)))
            ret.contribution_money_num = this.contribution_money_num;
        if (this.commodity_list != null && (__self == null || !this.commodity_list.equals(__self.commodity_list)))
            ret.commodity_list = this.commodity_list;
        if (this.queue_size != null && (__self == null || !this.queue_size.equals(__self.queue_size)))
            ret.queue_size = this.queue_size;
        if (this.luck_val != null && (__self == null || !this.luck_val.equals(__self.luck_val)))
            ret.luck_val = this.luck_val;
        if (this.refresh_time != null && (__self == null || !this.refresh_time.equals(__self.refresh_time)))
            ret.refresh_time = this.refresh_time;
        if (this.legion_store_list != null && (__self == null || !this.legion_store_list.equals(__self.legion_store_list)))
            ret.legion_store_list = this.legion_store_list;
        if (this.nation != null && (__self == null || !this.nation.equals(__self.nation)))
            ret.nation = this.nation;
        if (this.total_login_num != null && (__self == null || !this.total_login_num.equals(__self.total_login_num)))
            ret.total_login_num = this.total_login_num;
        if (this.previous_time != null && (__self == null || !this.previous_time.equals(__self.previous_time)))
            ret.previous_time = this.previous_time;
        if (this.luck_draw_num != null && (__self == null || !this.luck_draw_num.equals(__self.luck_draw_num)))
            ret.luck_draw_num = this.luck_draw_num;
        if (this.luck_draw_free_time != null && (__self == null || !this.luck_draw_free_time.equals(__self.luck_draw_free_time)))
            ret.luck_draw_free_time = this.luck_draw_free_time;
        if (this.luck_draw_free_num != null && (__self == null || !this.luck_draw_free_num.equals(__self.luck_draw_free_num)))
            ret.luck_draw_free_num = this.luck_draw_free_num;
        if (this.last_offline_time != null && (__self == null || !this.last_offline_time.equals(__self.last_offline_time)))
            ret.last_offline_time = this.last_offline_time;
        if (this.finishedGuide != null && (__self == null || !this.finishedGuide.equals(__self.finishedGuide)))
            ret.finishedGuide = this.finishedGuide;
        if (this.luck_draw_first_num != null && (__self == null || !this.luck_draw_first_num.equals(__self.luck_draw_first_num)))
            ret.luck_draw_first_num = this.luck_draw_first_num;
        if (this.taskcompletecount != null && (__self == null || !this.taskcompletecount.equals(__self.taskcompletecount)))
            ret.taskcompletecount = this.taskcompletecount;
        if (this.registration_id != null && (__self == null || !this.registration_id.equals(__self.registration_id)))
            ret.registration_id = this.registration_id;
        if (this.count_info != null && (__self == null || !this.count_info.equals(__self.count_info)))
            ret.count_info = this.count_info;
        if (this.language != null && (__self == null || !this.language.equals(__self.language)))
            ret.language = this.language;
        if (this.robot != null && (__self == null || !this.robot.equals(__self.robot)))
            ret.robot = this.robot;
        if (this.gamestatusmask != null && (__self == null || !this.gamestatusmask.equals(__self.gamestatusmask)))
            ret.gamestatusmask = this.gamestatusmask;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.refuse_chat_time != null && (__self == null || !this.refuse_chat_time.equals(__self.refuse_chat_time)))
            ret.refuse_chat_time = this.refuse_chat_time;
        if (this.refuse_chat != null && (__self == null || !this.refuse_chat.equals(__self.refuse_chat)))
            ret.refuse_chat = this.refuse_chat;
        if (this.pvp_count != null && (__self == null || !this.pvp_count.equals(__self.pvp_count)))
            ret.pvp_count = this.pvp_count;
        if (this.free_num != null && (__self == null || !this.free_num.equals(__self.free_num)))
            ret.free_num = this.free_num;
        if (this.free_time != null && (__self == null || !this.free_time.equals(__self.free_time)))
            ret.free_time = this.free_time;
        if (this.free_gold_time != null && (__self == null || !this.free_gold_time.equals(__self.free_gold_time)))
            ret.free_gold_time = this.free_gold_time;
        if (this.last_flush_time != null && (__self == null || !this.last_flush_time.equals(__self.last_flush_time)))
            ret.last_flush_time = this.last_flush_time;
        if (this.hero_path_already_points != null && (__self == null || !this.hero_path_already_points.equals(__self.hero_path_already_points)))
            ret.hero_path_already_points = this.hero_path_already_points;
        if (this.hero_path_recived_crop_table_ids != null && (__self == null || !this.hero_path_recived_crop_table_ids.equals(__self.hero_path_recived_crop_table_ids)))
            ret.hero_path_recived_crop_table_ids = this.hero_path_recived_crop_table_ids;
        if (this.hero_path_crops != null && (__self == null || !this.hero_path_crops.equals(__self.hero_path_crops)))
            ret.hero_path_crops = this.hero_path_crops;
        if (this.hero_path_enemy_crops != null && (__self == null || !this.hero_path_enemy_crops.equals(__self.hero_path_enemy_crops)))
            ret.hero_path_enemy_crops = this.hero_path_enemy_crops;
        if (this.hero_path_formation != null && (__self == null || !this.hero_path_formation.equals(__self.hero_path_formation)))
            ret.hero_path_formation = this.hero_path_formation;
        if (this.hero_path_curPoint != null && (__self == null || !this.hero_path_curPoint.equals(__self.hero_path_curPoint)))
            ret.hero_path_curPoint = this.hero_path_curPoint;
        if (this.hero_path_cur_self_formation != null && (__self == null || !this.hero_path_cur_self_formation.equals(__self.hero_path_cur_self_formation)))
            ret.hero_path_cur_self_formation = this.hero_path_cur_self_formation;
        if (this.hero_path_recive_box != null && (__self == null || !this.hero_path_recive_box.equals(__self.hero_path_recive_box)))
            ret.hero_path_recive_box = this.hero_path_recive_box;
        if (this.hero_path_num != null && (__self == null || !this.hero_path_num.equals(__self.hero_path_num)))
            ret.hero_path_num = this.hero_path_num;
        if (this.fund_end_day != null && (__self == null || !this.fund_end_day.equals(__self.fund_end_day)))
            ret.fund_end_day = this.fund_end_day;
        if (this.last_recv_fund_day != null && (__self == null || !this.last_recv_fund_day.equals(__self.last_recv_fund_day)))
            ret.last_recv_fund_day = this.last_recv_fund_day;
        if (this.continue_login_num != null && (__self == null || !this.continue_login_num.equals(__self.continue_login_num)))
            ret.continue_login_num = this.continue_login_num;
        if (this.continue_login_reward != null && (__self == null || !this.continue_login_reward.equals(__self.continue_login_reward)))
            ret.continue_login_reward = this.continue_login_reward;
        if (this.today_help_money_count != null && (__self == null || !this.today_help_money_count.equals(__self.today_help_money_count)))
            ret.today_help_money_count = this.today_help_money_count;
        if (this.today_help_corp_count != null && (__self == null || !this.today_help_corp_count.equals(__self.today_help_corp_count)))
            ret.today_help_corp_count = this.today_help_corp_count;
        if (this.fight_value != null && (__self == null || !this.fight_value.equals(__self.fight_value)))
            ret.fight_value = this.fight_value;
        if (this.reward_last_play_time != null && (__self == null || !this.reward_last_play_time.equals(__self.reward_last_play_time)))
            ret.reward_last_play_time = this.reward_last_play_time;
        if (this.reward_cur_id != null && (__self == null || !this.reward_cur_id.equals(__self.reward_cur_id)))
            ret.reward_cur_id = this.reward_cur_id;
        if (this.reward_enemy_crops != null && (__self == null || !this.reward_enemy_crops.equals(__self.reward_enemy_crops)))
            ret.reward_enemy_crops = this.reward_enemy_crops;
        if (this.reward_enemy_formation != null && (__self == null || !this.reward_enemy_formation.equals(__self.reward_enemy_formation)))
            ret.reward_enemy_formation = this.reward_enemy_formation;
        if (this.chatReport != null && (__self == null || !this.chatReport.equals(__self.chatReport)))
            ret.chatReport = this.chatReport;
        if (this.pay_count != null && (__self == null || !this.pay_count.equals(__self.pay_count)))
            ret.pay_count = this.pay_count;
        return ret;
    }
    @Override
    public void set(DatabasePlayer value) {
        this.player_id = value.player_id;
        this.name = value.name;
        this.head = value.head;
        this.level = value.level;
        this.vipLevel = value.vipLevel;
        this.vipOverdueTime = value.vipOverdueTime;
        this.fightingcount = value.fightingcount;
        this.victorycount = value.victorycount;
        this.formation1 = Utility.cloneList(value.formation1);
        this.formation2 = Utility.cloneList(value.formation2);
        this.instance_id = value.instance_id;
        this.feat = value.feat;
        this.instance_star_array = Utility.cloneList(value.instance_star_array);
        this.superiorInfo = Utility.cloneList(value.superiorInfo);
        this.cp_time = value.cp_time;
        this.online = value.online;
        this.selhero = value.selhero;
        this.extendNumber = value.extendNumber;
        this.pvpNumber = value.pvpNumber;
        this.pvpTime = value.pvpTime;
        this.belong_legion = value.belong_legion;
        this.contribution_money_num = value.contribution_money_num;
        this.commodity_list = Utility.cloneList(value.commodity_list);
        this.queue_size = value.queue_size;
        this.luck_val = value.luck_val;
        this.refresh_time = value.refresh_time;
        this.legion_store_list = Utility.cloneList(value.legion_store_list);
        this.nation = value.nation;
        this.total_login_num = value.total_login_num;
        this.previous_time = value.previous_time;
        this.luck_draw_num = value.luck_draw_num;
        this.luck_draw_free_time = value.luck_draw_free_time;
        this.luck_draw_free_num = value.luck_draw_free_num;
        this.last_offline_time = value.last_offline_time;
        this.finishedGuide = Utility.clonePrimitiveList(value.finishedGuide);
        this.luck_draw_first_num = value.luck_draw_first_num;
        this.taskcompletecount = Utility.cloneList(value.taskcompletecount);
        this.registration_id = value.registration_id;
        this.count_info = Utility.cloneList(value.count_info);
        this.language = value.language;
        this.robot = value.robot;
        this.gamestatusmask = value.gamestatusmask;
        this.create_time = value.create_time;
        this.refuse_chat_time = value.refuse_chat_time;
        this.refuse_chat = value.refuse_chat;
        this.pvp_count = value.pvp_count;
        this.free_num = value.free_num;
        this.free_time = value.free_time;
        this.free_gold_time = value.free_gold_time;
        this.last_flush_time = value.last_flush_time;
        this.hero_path_already_points = Utility.cloneList(value.hero_path_already_points);
        this.hero_path_recived_crop_table_ids = Utility.clonePrimitiveList(value.hero_path_recived_crop_table_ids);
        this.hero_path_crops = Utility.cloneList(value.hero_path_crops);
        this.hero_path_enemy_crops = Utility.cloneList(value.hero_path_enemy_crops);
        this.hero_path_formation = Utility.cloneList(value.hero_path_formation);
        this.hero_path_curPoint = Utility.cloneList(value.hero_path_curPoint);
        this.hero_path_cur_self_formation = Utility.cloneList(value.hero_path_cur_self_formation);
        this.hero_path_recive_box = Utility.clonePrimitiveList(value.hero_path_recive_box);
        this.hero_path_num = value.hero_path_num;
        this.fund_end_day = value.fund_end_day;
        this.last_recv_fund_day = value.last_recv_fund_day;
        this.continue_login_num = value.continue_login_num;
        this.continue_login_reward = value.continue_login_reward;
        this.today_help_money_count = value.today_help_money_count;
        this.today_help_corp_count = value.today_help_corp_count;
        this.fight_value = value.fight_value;
        this.reward_last_play_time = value.reward_last_play_time;
        this.reward_cur_id = value.reward_cur_id;
        this.reward_enemy_crops = Utility.cloneList(value.reward_enemy_crops);
        this.reward_enemy_formation = Utility.cloneList(value.reward_enemy_formation);
        this.chatReport = Utility.cloneList(value.chatReport);
        this.pay_count = value.pay_count;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.name != null) return false;
        if (this.head != null) return false;
        if (this.level != null) return false;
        if (this.vipLevel != null) return false;
        if (this.vipOverdueTime != null) return false;
        if (this.fightingcount != null) return false;
        if (this.victorycount != null) return false;
        if (this.formation1 != null) return false;
        if (this.formation2 != null) return false;
        if (this.instance_id != null) return false;
        if (this.feat != null) return false;
        if (this.instance_star_array != null) return false;
        if (this.superiorInfo != null) return false;
        if (this.cp_time != null) return false;
        if (this.online != null) return false;
        if (this.selhero != null) return false;
        if (this.extendNumber != null) return false;
        if (this.pvpNumber != null) return false;
        if (this.pvpTime != null) return false;
        if (this.belong_legion != null) return false;
        if (this.contribution_money_num != null) return false;
        if (this.commodity_list != null) return false;
        if (this.queue_size != null) return false;
        if (this.luck_val != null) return false;
        if (this.refresh_time != null) return false;
        if (this.legion_store_list != null) return false;
        if (this.nation != null) return false;
        if (this.total_login_num != null) return false;
        if (this.previous_time != null) return false;
        if (this.luck_draw_num != null) return false;
        if (this.luck_draw_free_time != null) return false;
        if (this.luck_draw_free_num != null) return false;
        if (this.last_offline_time != null) return false;
        if (this.finishedGuide != null) return false;
        if (this.luck_draw_first_num != null) return false;
        if (this.taskcompletecount != null) return false;
        if (this.registration_id != null) return false;
        if (this.count_info != null) return false;
        if (this.language != null) return false;
        if (this.robot != null) return false;
        if (this.gamestatusmask != null) return false;
        if (this.create_time != null) return false;
        if (this.refuse_chat_time != null) return false;
        if (this.refuse_chat != null) return false;
        if (this.pvp_count != null) return false;
        if (this.free_num != null) return false;
        if (this.free_time != null) return false;
        if (this.free_gold_time != null) return false;
        if (this.last_flush_time != null) return false;
        if (this.hero_path_already_points != null) return false;
        if (this.hero_path_recived_crop_table_ids != null) return false;
        if (this.hero_path_crops != null) return false;
        if (this.hero_path_enemy_crops != null) return false;
        if (this.hero_path_formation != null) return false;
        if (this.hero_path_curPoint != null) return false;
        if (this.hero_path_cur_self_formation != null) return false;
        if (this.hero_path_recive_box != null) return false;
        if (this.hero_path_num != null) return false;
        if (this.fund_end_day != null) return false;
        if (this.last_recv_fund_day != null) return false;
        if (this.continue_login_num != null) return false;
        if (this.continue_login_reward != null) return false;
        if (this.today_help_money_count != null) return false;
        if (this.today_help_corp_count != null) return false;
        if (this.fight_value != null) return false;
        if (this.reward_last_play_time != null) return false;
        if (this.reward_cur_id != null) return false;
        if (this.reward_enemy_crops != null) return false;
        if (this.reward_enemy_formation != null) return false;
        if (this.chatReport != null) return false;
        if (this.pay_count != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.name = null;
        this.head = null;
        this.level = null;
        this.vipLevel = null;
        this.vipOverdueTime = null;
        this.fightingcount = null;
        this.victorycount = null;
        this.formation1 = null;
        this.formation2 = null;
        this.instance_id = null;
        this.feat = null;
        this.instance_star_array = null;
        this.superiorInfo = null;
        this.cp_time = null;
        this.online = null;
        this.selhero = null;
        this.extendNumber = null;
        this.pvpNumber = null;
        this.pvpTime = null;
        this.belong_legion = null;
        this.contribution_money_num = null;
        this.commodity_list = null;
        this.queue_size = null;
        this.luck_val = null;
        this.refresh_time = null;
        this.legion_store_list = null;
        this.nation = null;
        this.total_login_num = null;
        this.previous_time = null;
        this.luck_draw_num = null;
        this.luck_draw_free_time = null;
        this.luck_draw_free_num = null;
        this.last_offline_time = null;
        this.finishedGuide = null;
        this.luck_draw_first_num = null;
        this.taskcompletecount = null;
        this.registration_id = null;
        this.count_info = null;
        this.language = null;
        this.robot = null;
        this.gamestatusmask = null;
        this.create_time = null;
        this.refuse_chat_time = null;
        this.refuse_chat = null;
        this.pvp_count = null;
        this.free_num = null;
        this.free_time = null;
        this.free_gold_time = null;
        this.last_flush_time = null;
        this.hero_path_already_points = null;
        this.hero_path_recived_crop_table_ids = null;
        this.hero_path_crops = null;
        this.hero_path_enemy_crops = null;
        this.hero_path_formation = null;
        this.hero_path_curPoint = null;
        this.hero_path_cur_self_formation = null;
        this.hero_path_recive_box = null;
        this.hero_path_num = null;
        this.fund_end_day = null;
        this.last_recv_fund_day = null;
        this.continue_login_num = null;
        this.continue_login_reward = null;
        this.today_help_money_count = null;
        this.today_help_corp_count = null;
        this.fight_value = null;
        this.reward_last_play_time = null;
        this.reward_cur_id = null;
        this.reward_enemy_crops = null;
        this.reward_enemy_formation = null;
        this.chatReport = null;
        this.pay_count = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.head != null)
            ret += ("head = " + this.head.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.vipLevel != null)
            ret += ("vipLevel = " + this.vipLevel.toString() + " ");
        if (this.vipOverdueTime != null)
            ret += ("vipOverdueTime = " + this.vipOverdueTime.toString() + " ");
        if (this.fightingcount != null)
            ret += ("fightingcount = " + this.fightingcount.toString() + " ");
        if (this.victorycount != null)
            ret += ("victorycount = " + this.victorycount.toString() + " ");
        if (this.formation1 != null)
            ret += ("formation1 = " + this.formation1.toString() + " ");
        if (this.formation2 != null)
            ret += ("formation2 = " + this.formation2.toString() + " ");
        if (this.instance_id != null)
            ret += ("instance_id = " + this.instance_id.toString() + " ");
        if (this.feat != null)
            ret += ("feat = " + this.feat.toString() + " ");
        if (this.instance_star_array != null)
            ret += ("instance_star_array = " + this.instance_star_array.toString() + " ");
        if (this.superiorInfo != null)
            ret += ("superiorInfo = " + this.superiorInfo.toString() + " ");
        if (this.cp_time != null)
            ret += ("cp_time = " + this.cp_time.toString() + " ");
        if (this.online != null)
            ret += ("online = " + this.online.toString() + " ");
        if (this.selhero != null)
            ret += ("selhero = " + this.selhero.toString() + " ");
        if (this.extendNumber != null)
            ret += ("extendNumber = " + this.extendNumber.toString() + " ");
        if (this.pvpNumber != null)
            ret += ("pvpNumber = " + this.pvpNumber.toString() + " ");
        if (this.pvpTime != null)
            ret += ("pvpTime = " + this.pvpTime.toString() + " ");
        if (this.belong_legion != null)
            ret += ("belong_legion = " + this.belong_legion.toString() + " ");
        if (this.contribution_money_num != null)
            ret += ("contribution_money_num = " + this.contribution_money_num.toString() + " ");
        if (this.commodity_list != null)
            ret += ("commodity_list = " + this.commodity_list.toString() + " ");
        if (this.queue_size != null)
            ret += ("queue_size = " + this.queue_size.toString() + " ");
        if (this.luck_val != null)
            ret += ("luck_val = " + this.luck_val.toString() + " ");
        if (this.refresh_time != null)
            ret += ("refresh_time = " + this.refresh_time.toString() + " ");
        if (this.legion_store_list != null)
            ret += ("legion_store_list = " + this.legion_store_list.toString() + " ");
        if (this.nation != null)
            ret += ("nation = " + this.nation.toString() + " ");
        if (this.total_login_num != null)
            ret += ("total_login_num = " + this.total_login_num.toString() + " ");
        if (this.previous_time != null)
            ret += ("previous_time = " + this.previous_time.toString() + " ");
        if (this.luck_draw_num != null)
            ret += ("luck_draw_num = " + this.luck_draw_num.toString() + " ");
        if (this.luck_draw_free_time != null)
            ret += ("luck_draw_free_time = " + this.luck_draw_free_time.toString() + " ");
        if (this.luck_draw_free_num != null)
            ret += ("luck_draw_free_num = " + this.luck_draw_free_num.toString() + " ");
        if (this.last_offline_time != null)
            ret += ("last_offline_time = " + this.last_offline_time.toString() + " ");
        if (this.finishedGuide != null)
            ret += ("finishedGuide = " + this.finishedGuide.toString() + " ");
        if (this.luck_draw_first_num != null)
            ret += ("luck_draw_first_num = " + this.luck_draw_first_num.toString() + " ");
        if (this.taskcompletecount != null)
            ret += ("taskcompletecount = " + this.taskcompletecount.toString() + " ");
        if (this.registration_id != null)
            ret += ("registration_id = " + this.registration_id.toString() + " ");
        if (this.count_info != null)
            ret += ("count_info = " + this.count_info.toString() + " ");
        if (this.language != null)
            ret += ("language = " + this.language.toString() + " ");
        if (this.robot != null)
            ret += ("robot = " + this.robot.toString() + " ");
        if (this.gamestatusmask != null)
            ret += ("gamestatusmask = " + this.gamestatusmask.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.refuse_chat_time != null)
            ret += ("refuse_chat_time = " + this.refuse_chat_time.toString() + " ");
        if (this.refuse_chat != null)
            ret += ("refuse_chat = " + this.refuse_chat.toString() + " ");
        if (this.pvp_count != null)
            ret += ("pvp_count = " + this.pvp_count.toString() + " ");
        if (this.free_num != null)
            ret += ("free_num = " + this.free_num.toString() + " ");
        if (this.free_time != null)
            ret += ("free_time = " + this.free_time.toString() + " ");
        if (this.free_gold_time != null)
            ret += ("free_gold_time = " + this.free_gold_time.toString() + " ");
        if (this.last_flush_time != null)
            ret += ("last_flush_time = " + this.last_flush_time.toString() + " ");
        if (this.hero_path_already_points != null)
            ret += ("hero_path_already_points = " + this.hero_path_already_points.toString() + " ");
        if (this.hero_path_recived_crop_table_ids != null)
            ret += ("hero_path_recived_crop_table_ids = " + this.hero_path_recived_crop_table_ids.toString() + " ");
        if (this.hero_path_crops != null)
            ret += ("hero_path_crops = " + this.hero_path_crops.toString() + " ");
        if (this.hero_path_enemy_crops != null)
            ret += ("hero_path_enemy_crops = " + this.hero_path_enemy_crops.toString() + " ");
        if (this.hero_path_formation != null)
            ret += ("hero_path_formation = " + this.hero_path_formation.toString() + " ");
        if (this.hero_path_curPoint != null)
            ret += ("hero_path_curPoint = " + this.hero_path_curPoint.toString() + " ");
        if (this.hero_path_cur_self_formation != null)
            ret += ("hero_path_cur_self_formation = " + this.hero_path_cur_self_formation.toString() + " ");
        if (this.hero_path_recive_box != null)
            ret += ("hero_path_recive_box = " + this.hero_path_recive_box.toString() + " ");
        if (this.hero_path_num != null)
            ret += ("hero_path_num = " + this.hero_path_num.toString() + " ");
        if (this.fund_end_day != null)
            ret += ("fund_end_day = " + this.fund_end_day.toString() + " ");
        if (this.last_recv_fund_day != null)
            ret += ("last_recv_fund_day = " + this.last_recv_fund_day.toString() + " ");
        if (this.continue_login_num != null)
            ret += ("continue_login_num = " + this.continue_login_num.toString() + " ");
        if (this.continue_login_reward != null)
            ret += ("continue_login_reward = " + this.continue_login_reward.toString() + " ");
        if (this.today_help_money_count != null)
            ret += ("today_help_money_count = " + this.today_help_money_count.toString() + " ");
        if (this.today_help_corp_count != null)
            ret += ("today_help_corp_count = " + this.today_help_corp_count.toString() + " ");
        if (this.fight_value != null)
            ret += ("fight_value = " + this.fight_value.toString() + " ");
        if (this.reward_last_play_time != null)
            ret += ("reward_last_play_time = " + this.reward_last_play_time.toString() + " ");
        if (this.reward_cur_id != null)
            ret += ("reward_cur_id = " + this.reward_cur_id.toString() + " ");
        if (this.reward_enemy_crops != null)
            ret += ("reward_enemy_crops = " + this.reward_enemy_crops.toString() + " ");
        if (this.reward_enemy_formation != null)
            ret += ("reward_enemy_formation = " + this.reward_enemy_formation.toString() + " ");
        if (this.chatReport != null)
            ret += ("chatReport = " + this.chatReport.toString() + " ");
        if (this.pay_count != null)
            ret += ("pay_count = " + this.pay_count.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "player";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (formation1 != null && formation1.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : formation1) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    formation1.remove(data);
		    }
        }
        if (formation2 != null && formation2.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : formation2) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    formation2.remove(data);
		    }
        }
        if (instance_star_array != null && instance_star_array.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : instance_star_array) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    instance_star_array.remove(data);
		    }
        }
        if (superiorInfo != null && superiorInfo.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : superiorInfo) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    superiorInfo.remove(data);
		    }
        }
        if (commodity_list != null && commodity_list.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : commodity_list) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    commodity_list.remove(data);
		    }
        }
        if (legion_store_list != null && legion_store_list.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : legion_store_list) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    legion_store_list.remove(data);
		    }
        }
        if (taskcompletecount != null && taskcompletecount.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : taskcompletecount) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    taskcompletecount.remove(data);
		    }
        }
        if (count_info != null && count_info.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : count_info) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    count_info.remove(data);
		    }
        }
        if (hero_path_already_points != null && hero_path_already_points.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : hero_path_already_points) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    hero_path_already_points.remove(data);
		    }
        }
        if (hero_path_crops != null && hero_path_crops.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : hero_path_crops) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    hero_path_crops.remove(data);
		    }
        }
        if (hero_path_enemy_crops != null && hero_path_enemy_crops.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : hero_path_enemy_crops) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    hero_path_enemy_crops.remove(data);
		    }
        }
        if (hero_path_formation != null && hero_path_formation.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : hero_path_formation) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    hero_path_formation.remove(data);
		    }
        }
        if (hero_path_curPoint != null && hero_path_curPoint.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : hero_path_curPoint) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    hero_path_curPoint.remove(data);
		    }
        }
        if (hero_path_cur_self_formation != null && hero_path_cur_self_formation.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : hero_path_cur_self_formation) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    hero_path_cur_self_formation.remove(data);
		    }
        }
        if (reward_enemy_crops != null && reward_enemy_crops.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : reward_enemy_crops) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    reward_enemy_crops.remove(data);
		    }
        }
        if (reward_enemy_formation != null && reward_enemy_formation.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : reward_enemy_formation) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    reward_enemy_formation.remove(data);
		    }
        }
        if (chatReport != null && chatReport.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : chatReport) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    chatReport.remove(data);
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