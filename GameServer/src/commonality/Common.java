package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/**  */
public class Common {
    /** 英雄上阵引导ID */
    public static final Integer HERO_UP_BATTLE_GUIDE_ID = 1000000;
    /** 转轮赠送的特殊病种ID段 */
    public static final Integer ZHUANLUN_CORPS_ID_MIN = 5000000;
    /** 转轮赠送的特殊病种ID段 */
    public static final Integer ZHUANLUN_CORPS_ID_MAX = 6000000;
    /** 自动飞机按钮显示等级 */
    public static final Integer AUTO_PLANE_OPEN_LEVEL = 6;
    /** 世界聊天开启等级 */
    public static final Integer WORLD_CHAR_OPEN_LEVEL = 1;
    /** 金币免费抽勋章次数 */
    public static final Integer max_money_free_luck_draw = 5;
    /** 金砖免费抽勋章次数 */
    public static final Integer max_gold_free_luck_draw = 1;
    /** 当前最大对列数 */
    public static final Integer maxQueue = 5;
    /** 军团捐钱捐兵单次上限 */
    public static final Integer legion_money_corp_help_limit = 10;
    /** 体力恢复速度 30秒(单位:毫秒) */
    public static final Integer CP_RESTORE_SPEED = 1800000;
    /** 军团战体力恢复速度 30秒(单位:毫秒) */
    public static final Integer WAR_CP_RESTORE_SPEED = 300000;
    /** 非阻塞的消息ID */
    public static final Short NON_BREAKING_MESSAGE_ID = 0;
    /** 阻塞的消息ID */
    public static final Short BREAKING_MESSAGE_ID = 1;
    /** 城镇地形初始大小（是地垫的大小 一个地垫是5*5的格子） */
    public static final Integer MAP_START_SIZE = 5;
    /** 一块地垫的地形的宽高 */
    public static final Integer LAND_WH = 3;
    /** 士兵初始等级 */
    public static final Integer CORPS_START_LEVEL = 1;
    /** 空中支援初始最大出战个数 */
    public static final Integer AIRSUPPORT_OUT_FIGHTING_MAX_NUMBER = 3;
    /** PVP挑战次数 */
    public static final Integer PVP_MAX_NUM = 10;
    /** 最多升级个数 */
    public static final Integer UPDATENUM = 2;
    /** 军团商店显示商品个数 */
    public static final Integer LEGIONSTORESIZE = 6;
    /** 军团商店模块，人物最大幸运值 */
    public static final Integer MAXLUCKVAL = 50;
    /** 抽取一次勋章对应掉落组的tableid */
    public static final Integer LUCKDRAWDROPOUTONE = 8;
    /** 抽取十次勋章对应掉落组的tableid */
    public static final Integer LUCKDRAWDROPOUTTEN = 9;
    /** 抽取一次勋章对应的tableid */
    public static final Integer LUCKDRAWDONE = 1;
    /** 抽取十次勋章对应的tableid */
    public static final Integer LUCKDRAWDTEN = 10;
    /** 战斗加速 X2 开放等级 */
    public static final Integer OPENLEVEL_X2 = 9;
    /** 开始自动释放空中支援的等级 */
    public static final Integer OPEN_AUTO_FIRE_AIRSUPPORT = 20;
    /** 每小时秒数 */
    public static final Integer ONE_HOUR_SECOND = 3600;
    /** 每小时毫秒数 */
    public static final Integer ONE_HOUR_MILLISECOND = 3600000;
    /** 12小时毫秒数 */
    public static final Integer TWELVE_HOUR_MILLISECOND = 43200000;
    /** 军团列表个数 */
    public static final Integer QUERY_LEGION_COUNT = 10;
    /** 资源收益最大时间(秒) 8小时 */
    public static final Integer ASSET_MAX_TIME = 28800;
    /** 一个加速道具加速的时间(秒) 5分钟 */
    public static final Integer SPPED_ITEM_TIME = 300;
    /** 一分钟的毫秒数 */
    public static final Long MINUTE_MILLISECOND = 60000L;
    /** 一小时的毫秒数 */
    public static final Long HOUR_MILLISECOND = 3600000L;
    /** 一天的毫秒数 */
    public static final Long DAY_MILLISECOND = 86400000L;
    /** 一周的毫秒数 */
    public static final Long WEEK_MILLISECOND = 604800000L;
    /** 一个月的毫秒数 */
    public static final Long MONTH_MILLISECOND = 2592000000L;
    /** 一天的秒数 */
    public static final Integer DAY_SECOND = 86400;
    /** 一小时的秒数 */
    public static final Integer HOUR_SECOND = 3600;
    /** 一分钟的秒数 */
    public static final Integer MINUTE_SECOND = 60;
    /**  */
    public static final Integer DEFAULT_EXPIRE_TIME = 5000;
    /** 扫荡开启的任务等级 */
    public static final Integer FAST_FIGHTING_LEVEL = 20;
    /** 登录奖励开启等级 */
    public static final Integer REWARD_LOGIN_LEVEL = 0;
    /** 等级奖励开启等级 */
    public static final Integer REWARD_LEVEL_LEVEL = 5;
    /** 推荐人奖励开启等级 */
    public static final Integer RECOMMEND_LEVEL = 5;
    /** 幸运抽奖开启等级 */
    public static final Integer LUCKY_LOTTERY_LEVEL = 7;
    /** 目标关闭等级 */
    public static final Integer GROWGUIDE_CLOSE_LEVEL = 20;
    /** 布防开启等级 */
    public static final Integer DEPLOYMENT_LEVEL = 10;
    /** 初始的队列数量 */
    public static final Integer QUEUE_SIZE = 2;
    /** 一次军团金钱捐献的金币数 */
    public static final Integer LEGION_HELP_MONEY_NUM = 1000;
    /** 奖励界面展示的勋章ID  Item表中的ID */
    public static final Integer SHOW_MEDAL_ID = 40008;
    /** 本地读取当前所使用的语言的Key */
    public static final String LANGUAGE_KEY = "Language";
    /** IPO */
    public static final String IPO_KEY = "IPO";
    /** IPO */
    public static final Long IPO_VAL = 10000000000L;
    /** 公告开启等级 */
    public static final Integer NOTICE_OPEN_LEVEL = 1;
    /** 英雄之路开启等级 */
    public static final Integer HEROPATH_OPEN_LEVEL = 17;
    /** 充值返利开启等级 */
    public static final Integer ACTIVE_OPEN_LEVEL = 5;
    /** 不死兵等级等级 */
    public static final Integer NO_DIE_CORPS = 10;
    /** 军团资助开启等级 */
    public static final Integer OPEN_LEGION_MONEY_ASSIST = 3;
    /** 军团士兵资助按钮出现等级 */
    public static final Integer SHOW_LEGION_CORPS_ASSIST = 10;
    /** 军团士兵资助开启等级 */
    public static final Integer OPEN_LEGION_CORPS_ASSIST = 13;
    /** 英雄强化开启等级 */
    public static final Integer HERO_UPGRADE_OPEN_LEVEL = 17;
    /** 装备强化开启等级 */
    public static final Integer EQUIP_INTENSIFY_OPEN_LEVEL = 19;
    /** 英雄之路加强敌方开启等级 */
    public static final Integer HERO_PATH_STRENG_ENEMY_OPEN_LEVEL = 99;
    /** PVP加强敌方开启等级 */
    public static final Integer PVP_STRENG_ENEMY_OPEN_LEVEL = 99;
    /** 拆分装备需要消耗滴金币倍数(获得材料滴倍数) */
    public static final Integer SPLIT_EQU_TIMES = 500;
    /** 悬赏关卡CD */
    public static final Integer REWARD_INSTANCE_CD = 1800;
    /** 悬赏关卡加强敌方开启等级 */
    public static final Integer REWARD_INSTANCE_ENEMY_OPEN_LEVEL = 99;
    /** 悬赏关卡开启等级 */
    public static final Integer WANTED_OPEN_LEVEL = 19;
    /** 军团建筑物防守格子数 */
    public static final Integer LEGION_BUILD_DEFEN_NUM = 10;
    /** 军团建筑物防守士兵数 */
    public static final Integer LEGION_BUILD_ENEMY_MAX_NUM = 12;
    /** 军团战进攻胜利积分 */
    public static final Integer LEGION_BATTLE_ATTACK_WIN_POINT = 3;
    /** 军团战进攻失败积分 */
    public static final Integer LEGION_BATTLE_ATTACK_FAIL_POINT = 1;
    /** 军团战防守胜利积分 */
    public static final Integer LEGION_BATTLE_DEFEN_WIN_POINT = 0;
    /** 军团战防守失败积分 */
    public static final Integer LEGION_BATTLE_DEFEN_FAIL_POINT = 0;
    /** 军团战战备状态CD（秒） */
    public static final Integer LEGION_BATTLE_READY_CD = 86400;
    /** 军团战战斗状态CD（秒） */
    public static final Integer LEGION_BATTLE_FIGHT_CD = 86400;
    /** 军团战胜利休整状态CD（秒） */
    public static final Integer LEGION_BATTLE_WIN_REST_CD = 172800;
    /** 军团战失败休整状态CD（秒） */
    public static final Integer LEGION_BATTLE_FAIL_REST_CD = 165600;
    /** 军团战建筑恢复CD（秒） */
    public static final Integer LEGION_BATTLE_BUILD_REST_CD = 7200;
    /** 单场军团战获得令牌最大个数 */
    public static final Integer LEGION_BATTLE_GET_TOKEN_MAX = 50;
    /** 军团战进攻胜利获得令牌 */
    public static final Integer LEGION_BATTLE_ATTACK_WIN_TOKEN = 2;
    /** 军团战进攻失败获得令牌 */
    public static final Integer LEGION_BATTLE_ATTACK_FAIL_TOKEN = 1;
    /** 军团战战报最大条数 */
    public static final Integer LEGION_BATTLE_REPORT_MAX = 10;
    /** 军团战进攻失败cd */
    public static final Integer LEGION_BATTLE_FAIL_CD = 0;
    /** 军团战开启等级 */
    public static final Integer LEGION_BATTLE_OPEN_FUNC_LEVEL = 3;
    /** 军团战进攻胜利获得资金 */
    public static final Integer LEGION_BATTLE_ATTACK_WIN_MONEY = 10;
    /** 军团战人数限制 */
    public static final Integer LEGION_BATTLE_MEMBER_COUNT_LIMIT = 20;
    /** 军团战建筑物摧毁获得积分 */
    public static final Integer LEGION_WAR_BUILD_DAMAGE_POINT = 1;
    /** 军团战建筑物摧毁获得积分时间(秒) */
    public static final Integer LEGION_WAR_BUILD_DAMAGE_POINT_TIME = 60;
    /** 军团战体力上限 */
    public static final Integer LEGION_WAR_CP_MAX = 10;
    /** 军团战胜利获得资金 */
    public static final Integer LEGION_WAR_WIN_MONEY = 100000;
    /** 军团战失败获得资金 */
    public static final Integer LEGION_WAR_LOSE_MONEY = 80000;
    /** 军团战单场战斗超时时间(秒) */
    public static final Integer LEGION_BATTLE_TIMEOUT = 300;
    /** 军团战战报提示cd(秒) */
    public static final Integer LEGION_WAR_REPORT_PROMPT_CD = 30;
    /** pvp活动军衔限制 */
    public static final Integer PVP_ACTIVITY_FEAT_MIN = 400;
    /** 军团战玩家开启等级 */
    public static final Integer LEGION_WAR_PLAYER_OPEN_FUNC_LEVEL = 20;
    /** pvp可以抢夺身上金钱的最大值 */
    public static final Integer PVP_CAN_LOOT_MONEY_MAX = 200000;
    /** 邮件标题最大长度 */
    public static final Integer MAIL_TITLE_MAX_LENGTH = 25;
    /** 邮件内容最大长度 */
    public static final Integer MAIL_TEXT_MAX_LENGTH = 300;
    /** 首冲加成 */
    public static final Integer FIRST_PAY_ADDITION = 2;
    /** 玩家最高等级 */
    public static final Integer PLAYER_MAX_LEVEL = 70;
    /** vip最高等级 */
    public static final Integer VIP_MAX_LEVEL = 15;
    /**  */
    public static final Integer CHAT_REPORT_PERSON_NUM = 5;
    /** 账号尝试登录最大次数 */
    public static final Integer ACCOUNT_LOGIN_TRY_MAX = 3;
    /** 屏蔽上限 */
    public static final Integer HIDE_MAX = 20;
    /** 军衔上线值 */
    public static final Integer FEAT_MAX = 1000000;
    /** 捐献上限次数 */
    public static final Integer CONTRIBUTION_MAX = 3;
    /** 每日获得贡献上限 */
    public static final Integer CONTRIBUTION_MAXNUM = 200;
    /** 军团战斗类科技开放等级 */
    public static final Integer LEGION_FIGHT_TECH_OPENLV = 10;
    /** 邀请玩家开放等级 */
    public static final Integer INVITE_OPEN_LV = 5;
    /** 邀请玩家数量上限 */
    public static final Integer INVITE_MAX = 10;
    /** 通告邀请价格 */
    public static final Integer NOTICE_INVITE_PRICE = 100;
    /** 时间任务开启等级 */
    public static final Integer TIMETASK_OPEN_FUNC_LEVEL = 13;
    /** pvp开启等级 */
    public static final Integer PVP_OPEN_FUNC_LEVEL = 1;
    /** pvp战斗开启等级 */
    public static final Integer PVP_OPEN_ATTACK_LEVEL = 10;
    /** pvp最多匹配玩家数量 */
    public static final Integer PVP_MATCH_PLAYER_MAX = 15;
    /** pvp工厂开启等级 */
    public static final Integer PVP_FACTORY_OPEN_FUNC_LEVEL = 20;
    /** pvp匹配等级浮动 */
    public static final Integer PVP_MATCH_RAND_LEVEL = 4;
    /** 国王战战斗时间 */
    public static final Long KING_WAR_TIME = 86400000L;
    /** 国王战休息时间 */
    public static final Long KING_RESET_TIME = 604800000L;
    /** 国王战支援CD时间 */
    public static final Long KING_DONATE_TIME = 600000L;
    /** 国王战攻击CD时间 */
    public static final Long KING_ATTACK_TIME = 600000L;
    /** 国王战金币奖赏 */
    public static final Integer KING_REWARD_MONEY_NUM = 100000;
    /** 国王战金砖奖赏 */
    public static final Integer KING_REWARD_GOLD_NUM = 10;
    /** 国王战防御值初始值 */
    public static final Integer KING_DEFVAL_INIT = 1000000;
    /** 国王战忠诚度初始值 */
    public static final Integer KING_LOYALTYVAL_INIT = 100;
    /** 国王战开启等级 */
    public static final Integer KING_OPEN_FUNC_LEVEL = 20;
    /** 国王战任命提示起始值 */
    public static final Integer KING_TAKEOFFICE_PROMPT_INDEX = 102;
    /** 国王战攻击防御值倍数 */
    public static final Integer KING_ATTACK_DEFVAL = 10;
    /** 国王战玩家捐献最大值 */
    public static final Integer KING_PLAYER_DONATE_MAX = 200;
    /** 国王战每次自动改变防御值 */
    public static final Integer KING_AUTO_DEFVAL_ONCE = 1000;
    /** 弹珠普通抽取花费 */
    public static final Integer HOODLE_NORMAL_DRAW = 50000;
    /** 弹珠幸运抽取花费 */
    public static final Integer HOODLE_LUCKY_DRAW = 100;
    /** 弹珠背包最大个数 */
    public static final Integer HOODLE_PACKET_MAX = 50;
    /** 弹珠开启等级 */
    public static final Integer HOODLE_OPEN_FUNC_LEVEL = 28;
    /** 士兵解锁弹珠等级-1 */
    public static final Integer HOODLE_CORP_UNLOCK_LEVEL = 2;
    /** 英雄传承开启等级 */
    public static final Integer HERO_HERITAGE_OPEN_LV = 30;
    /** 英雄传承金砖数量 */
    public static final Integer HERO_HERITAGE_GOLD_NUM = 2000;
    /** 同时上阵英雄数量 */
    public static final Integer BATTLE_HERO_MAX_NUM = 3;
    /** 金币免费弹珠抽取次数 */
    public static final Integer max_money_free_hoodle_draw = 3;
    /** 金砖免费弹珠抽取次数 */
    public static final Integer max_gold_free_hoodle_draw = 1;
    /** 月卡商品名字 */
    public static final String MONTH_CARD_NAME = "fund";
    /** 充值礼包名字 */
    public static final String RECHARGE_GIFT = "rechargegift";
    /** Common 构造函数 */
    static { 
    }
    /** 商城物品类型 */
    public static enum COMMODITY_TYPE {
        /** (0) -_-  */
        NONE(0),
        /** (1)建筑队列 */
        QUEUE(1),
        /** (2)普通物品 */
        ITEM(2),
        /** (3)购买金币 */
        MONEY(3),
        /** (4)护盾 */
        SHIELD(4),
        ;
        public static COMMODITY_TYPE valueOf(int value) {
            if (value == 0) return NONE;
            if (value == 1) return QUEUE;
            if (value == 2) return ITEM;
            if (value == 3) return MONEY;
            if (value == 4) return SHIELD;
            return null;
        }
        int number;
        COMMODITY_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 排行榜类型（取自ProRanking文件 Proto_RankingType  如果proto改变  请手动修改此枚举） */
    public static enum RANKING_TYPE {
        /** (1)玩家排行榜 */
        PLAYER_RANKING(1),
        /** (2)军团排行榜 */
        LEGION_RANKING(2),
        /** (3)英雄排行榜 */
        HERO_RANKING(3),
        /** (4)军衔排行榜 */
        FEAT_RANKING(4),
        ;
        public static RANKING_TYPE valueOf(int value) {
            if (value == 1) return PLAYER_RANKING;
            if (value == 2) return LEGION_RANKING;
            if (value == 3) return HERO_RANKING;
            if (value == 4) return FEAT_RANKING;
            return null;
        }
        int number;
        RANKING_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 排行榜区域（取自ProRanking文件 Proto_RankingForm  如果proto改变  请手动修改此枚举） */
    public static enum RANKING_FORM {
        /** (1)全球排行 */
        GLOBAL1(1),
        /** (2)地区排行 */
        REGION(2),
        ;
        public static RANKING_FORM valueOf(int value) {
            if (value == 1) return GLOBAL1;
            if (value == 2) return REGION;
            return null;
        }
        int number;
        RANKING_FORM(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 角色属性上限类型 */
    public static enum LIMIT_TYPE {
        /** (0)人口上限 */
        PEOPLE(0),
        /** (1)PVP次数 */
        PVP_NUMBER(1),
        /** (2)体力上限 */
        CP(2),
        /** (3)背包上限 */
        BAG_COUNT(3),
        /** (4)队列上限 */
        QUEUE(4),
        /** (5)使用体力物品次数 */
        USE_CP_ITEM(5),
        /** (6)使用PVP物品次数 */
        USE_PVP_ITEM(6),
        /** (7)军团战体力上限 */
        WAR_CP(7),
        /** (8)使用军团战体力物品次数 */
        USE_WAR_CP_ITEM(8),
        /** (9)购买经验次数 */
        BUY_EXP_COUNT(9),
        ;
        public static LIMIT_TYPE valueOf(int value) {
            if (value == 0) return PEOPLE;
            if (value == 1) return PVP_NUMBER;
            if (value == 2) return CP;
            if (value == 3) return BAG_COUNT;
            if (value == 4) return QUEUE;
            if (value == 5) return USE_CP_ITEM;
            if (value == 6) return USE_PVP_ITEM;
            if (value == 7) return WAR_CP;
            if (value == 8) return USE_WAR_CP_ITEM;
            if (value == 9) return BUY_EXP_COUNT;
            return null;
        }
        int number;
        LIMIT_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 军团职位 */
    public static enum LEGION_JOB {
        /** (0)普通成员 */
        MEMBER(0),
        /** (1)官员 */
        OFFICER(1),
        /** (2)军团长 */
        BOSS(2),
        ;
        public static LEGION_JOB valueOf(int value) {
            if (value == 0) return MEMBER;
            if (value == 1) return OFFICER;
            if (value == 2) return BOSS;
            return null;
        }
        int number;
        LEGION_JOB(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 道具枚举 */
    public static enum ITEMTYPE {
        /** (0)虚拟货币 */
        Virtual(0),
        /** (1)一般物品 */
        Item(1),
        /** (2)装备 */
        Equip(2),
        /** (3)勋章 */
        Medal(3),
        /** (4)礼包 */
        Gift(4),
        /** (5)时间加速 */
        Timer(5),
        /** (6)转轮使用的特殊宝箱 */
        TimerRewardBox(6),
        /** (7)装备碎片 */
        Equip_Debris(7),
        /** (8)VIP卡牌 */
        Vip_Card(8),
        /** (9)军团资源 */
        Legion_Item(9),
        /** (11)时限buff */
        TIME_LIMIT_ITEM(11),
        /** (12)护盾 */
        Shield(12),
        ;
        public static ITEMTYPE valueOf(int value) {
            if (value == 0) return Virtual;
            if (value == 1) return Item;
            if (value == 2) return Equip;
            if (value == 3) return Medal;
            if (value == 4) return Gift;
            if (value == 5) return Timer;
            if (value == 6) return TimerRewardBox;
            if (value == 7) return Equip_Debris;
            if (value == 8) return Vip_Card;
            if (value == 9) return Legion_Item;
            if (value == 11) return TIME_LIMIT_ITEM;
            if (value == 12) return Shield;
            return null;
        }
        int number;
        ITEMTYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 装备部位枚举 */
    public static enum EQUIPPART {
        /** (0)武器 */
        Weapon(0),
        /** (1)帽子 */
        Hat(1),
        /** (2)上衣 */
        Jacket(2),
        /** (3)裤子 */
        Trousers(3),
        /** (4)鞋子 */
        Shoe(4),
        /** (5)饰品 */
        Accessories(5),
        /** (6)时装 */
        Avatar(6),
        ;
        public static EQUIPPART valueOf(int value) {
            if (value == 0) return Weapon;
            if (value == 1) return Hat;
            if (value == 2) return Jacket;
            if (value == 3) return Trousers;
            if (value == 4) return Shoe;
            if (value == 5) return Accessories;
            if (value == 6) return Avatar;
            return null;
        }
        int number;
        EQUIPPART(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 属性枚举 */
    public static enum ATTRENUM {
        /** (0)攻击 */
        Attack(0),
        /** (1)防御 */
        Defense(1),
        /** (2)血量 */
        Hp(2),
        /** (3)暴击 */
        Critical(3),
        /** (4)免爆 */
        ImmuneCrit(4),
        /** (5)命中 */
        Hit(5),
        /** (6)闪避 */
        Dodge(6),
        /** (7)吸血 */
        Vampire(7),
        /** (8)攻击速度 */
        AttackSpeed(8),
        /** (9)个数 */
        Count(9),
        ;
        public static ATTRENUM valueOf(int value) {
            if (value == 0) return Attack;
            if (value == 1) return Defense;
            if (value == 2) return Hp;
            if (value == 3) return Critical;
            if (value == 4) return ImmuneCrit;
            if (value == 5) return Hit;
            if (value == 6) return Dodge;
            if (value == 7) return Vampire;
            if (value == 8) return AttackSpeed;
            if (value == 9) return Count;
            return null;
        }
        int number;
        ATTRENUM(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 建筑类枚举 */
    public static enum BUILDTYPE {
        /** (0)主城 */
        MainCity(0),
        /** (1)生产类(产兵操作) */
        Factory(1),
        /** (2)资源类(收取金币等资源) */
        Assets(2),
        /** (3)装饰类 */
        Decorate(3),
        /** (4)仓库类(物品上限) */
        Depot(4),
        /** (5)研究类(兵种升级) */
        Institute(5),
        /** (6)科技（科技升级） */
        Tech(6),
        /** (7)空中支援（技能系统） */
        AirSupport(7),
        /** (8)城墙(战斗是城堡血量) */
        Wall(8),
        /** (9)勋章 */
        MEDAL(9),
        /** (10)军团驻地 */
        LEGION(10),
        /** (11)旗帜 */
        FLAG(11),
        /** (12)英雄驻地 */
        HeroHome(12),
        /** (13)建筑队列 */
        Queue(13),
        ;
        public static BUILDTYPE valueOf(int value) {
            if (value == 0) return MainCity;
            if (value == 1) return Factory;
            if (value == 2) return Assets;
            if (value == 3) return Decorate;
            if (value == 4) return Depot;
            if (value == 5) return Institute;
            if (value == 6) return Tech;
            if (value == 7) return AirSupport;
            if (value == 8) return Wall;
            if (value == 9) return MEDAL;
            if (value == 10) return LEGION;
            if (value == 11) return FLAG;
            if (value == 12) return HeroHome;
            if (value == 13) return Queue;
            return null;
        }
        int number;
        BUILDTYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 设置value类型 */
    public static enum SETNUMBER_TYPE {
        /** (0)直接设置值 */
        SET(0),
        /** (1)原有基础上加上此值 */
        ADDITION(1),
        /** (2)原有基础上减去此值 */
        MINUS(2),
        ;
        public static SETNUMBER_TYPE valueOf(int value) {
            if (value == 0) return SET;
            if (value == 1) return ADDITION;
            if (value == 2) return MINUS;
            return null;
        }
        int number;
        SETNUMBER_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 帐号的等级(GM 非GM) */
    public static enum ACCOUNT_TYPE {
        /** (0)非GM帐号 */
        NORMAL(0),
        /** (1)GM帐号 */
        GM(1),
        ;
        public static ACCOUNT_TYPE valueOf(int value) {
            if (value == 0) return NORMAL;
            if (value == 1) return GM;
            return null;
        }
        int number;
        ACCOUNT_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 玩家当前状态 */
    public static enum PLAYER_STATE {
        /** (0)正常状态 */
        NONE(0),
        /** (1)被匹配到的状态 */
        MATCHED(1),
        /** (2)正在被攻打状态 */
        DEFENSE(2),
        ;
        public static PLAYER_STATE valueOf(int value) {
            if (value == 0) return NONE;
            if (value == 1) return MATCHED;
            if (value == 2) return DEFENSE;
            return null;
        }
        int number;
        PLAYER_STATE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** pvp活动类型 */
    public static enum PVP_ACTIVITY_TYPE {
        /** (0)无 */
        NONE(0),
        /** (1)百战之王 */
        KING_OF_KILL_SOLDIERS(1),
        /** (2)功勋卓著 */
        KING_OF_ROB_FEATS(2),
        /** (3)贪婪之主 */
        KING_OF_ROB_GOLDS(3),
        ;
        public static PVP_ACTIVITY_TYPE valueOf(int value) {
            if (value == 0) return NONE;
            if (value == 1) return KING_OF_KILL_SOLDIERS;
            if (value == 2) return KING_OF_ROB_FEATS;
            if (value == 3) return KING_OF_ROB_GOLDS;
            return null;
        }
        int number;
        PVP_ACTIVITY_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** pvp活动分组类型 */
    public static enum PVP_ACTIVITY_GROUP {
        /** (0)无 */
        NONE(0),
        /** (1)精英分组 */
        GROUP_ELITE(1),
        /** (2)普通分组 */
        GROUP_NORMAL(2),
        ;
        public static PVP_ACTIVITY_GROUP valueOf(int value) {
            if (value == 0) return NONE;
            if (value == 1) return GROUP_ELITE;
            if (value == 2) return GROUP_NORMAL;
            return null;
        }
        int number;
        PVP_ACTIVITY_GROUP(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 被动技能目标类型 */
    public static enum PASSIVEBUFF_OBJECT_TYPE {
        /** (0)目标自己,只有自己享受 0 */
        SELF(0),
        /** (1)敌方英雄 1 */
        ENEMY_HERO(1),
        /** (2)我方英雄 2 */
        HERO(2),
        /** (3)我方兵种 3 */
        OWNCORPS(3),
        /** (4)敌方兵种 4 */
        ENEMYCORPS(4),
        /** (5)我方护甲类型 5 */
        OWNARMORTYPE(5),
        /** (6)敌方护甲类型 6 */
        ENEMYARMORTYPE(6),
        /** (7)我方攻击类型 7 */
        OWNATTACKTYPE(7),
        /** (8)敌方攻击类型 8 */
        ENEMYATTACKTYPE(8),
        /** (9)我方城墙 9 */
        OWNWALL(9),
        /** (10)敌方城墙 10 */
        ENEMYWALL(10),
        /** (11)敌方飞机 11 */
        ENEMYAIRSUPPORT(11),
        /** (12)我方飞机 12 */
        OWNAIRSUPPORT(12),
        ;
        public static PASSIVEBUFF_OBJECT_TYPE valueOf(int value) {
            if (value == 0) return SELF;
            if (value == 1) return ENEMY_HERO;
            if (value == 2) return HERO;
            if (value == 3) return OWNCORPS;
            if (value == 4) return ENEMYCORPS;
            if (value == 5) return OWNARMORTYPE;
            if (value == 6) return ENEMYARMORTYPE;
            if (value == 7) return OWNATTACKTYPE;
            if (value == 8) return ENEMYATTACKTYPE;
            if (value == 9) return OWNWALL;
            if (value == 10) return ENEMYWALL;
            if (value == 11) return ENEMYAIRSUPPORT;
            if (value == 12) return OWNAIRSUPPORT;
            return null;
        }
        int number;
        PASSIVEBUFF_OBJECT_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 被动技能类型 */
    public static enum PASSIVEBUFF_TYPE {
        /** (0)站位 0 */
        NONE(0),
        /** (1)攻击 1 */
        ATTACK(1),
        /** (2)防御 2 */
        DEFEND(2),
        /** (3)血量 3 */
        HP(3),
        /** (4)命中 4 */
        Hit(4),
        /** (5)闪避 5 */
        DODGE(5),
        /** (6)暴击 6 */
        CRIT(6),
        /** (7)抗暴击 7 */
        RESIST_CRIT(7),
        /** (8)攻速 8 */
        ATTACK_SPEED(8),
        /** (9)移动速度 9 */
        MOVE_SPEED(9),
        /** (10)站位 10 */
        NONE2(10),
        /** (11)人口上限 11 */
        PEOPLEMAX(11),
        /** (12)竞技场次数 12 */
        PVPCOUNT(12),
        /** (13)体力上限 13 */
        CPMAX(13),
        /** (14)背包上限 14 */
        BAGMAX(14),
        /** (15)队列上限 15 */
        QUEUEMAX(15),
        /** (16)缩短建筑升级时间 16 */
        BUILD_UP_TIME(16),
        /** (17)减少建筑升级花费 17 */
        BUILD_UP_MONEY(17),
        /** (18)缩短士兵产出时间 18 */
        CORPS_OUT_TIME(18),
        /** (19)缩短士兵产出花费 19 */
        CORPS_OUT_MONEY(19),
        /** (20)缩短士兵升级时间 20 */
        CORPS_UP_TIME(20),
        /** (21)减少士兵升级花费 21 */
        CORPS_UP_MONEY(21),
        /** (22)增加产出队列 22 */
        ADD_MARKNUM(22),
        /** (23)减少PVP被抢数额 23 */
        LESSEN_PVP_LOOT(23),
        /** (24)战斗加速开关 24 */
        FIGHT_ADDSPEED_ON_OFF(24),
        /** (25)战斗跳过开关 25 */
        FIGHT_SKIP_ON_OFF(25),
        /** (26)增加PVP资源抢夺数额 26 */
        INC_PVP_LOOT(26),
        /** (27)缩短建筑建造时间 27 */
        BUILD_CREATE_TIME(27),
        /** (28)减少建筑建造花费 28 */
        BUILD_CREATE_MONEY(28),
        /** (29)增加军团帮助次数上限 29 */
        INC_LEGION_HELP_COUNT(29),
        /** (30)减少科技升级的金币消耗 30 */
        DEC_TECH_MONEY_COST(30),
        /** (31)减少科技升级的花费时间 31 */
        DEC_TECH_TIME_COST(31),
        /** (32)自动飞机开关 32 */
        AUTO_PLANE(32),
        /** (33)扫荡开关 33 */
        FAST_FIGHT(33),
        /** (34)提升铸币厂产量 34 */
        INC_GATHER_MONEY(34),
        /** (35)减少收取钻石时间 35 */
        DECR_GATHER_RARE_TIME(35),
        /** (36)PVE经验加成 36 */
        PVE_EXP_ADD(36),
        ;
        public static PASSIVEBUFF_TYPE valueOf(int value) {
            if (value == 0) return NONE;
            if (value == 1) return ATTACK;
            if (value == 2) return DEFEND;
            if (value == 3) return HP;
            if (value == 4) return Hit;
            if (value == 5) return DODGE;
            if (value == 6) return CRIT;
            if (value == 7) return RESIST_CRIT;
            if (value == 8) return ATTACK_SPEED;
            if (value == 9) return MOVE_SPEED;
            if (value == 10) return NONE2;
            if (value == 11) return PEOPLEMAX;
            if (value == 12) return PVPCOUNT;
            if (value == 13) return CPMAX;
            if (value == 14) return BAGMAX;
            if (value == 15) return QUEUEMAX;
            if (value == 16) return BUILD_UP_TIME;
            if (value == 17) return BUILD_UP_MONEY;
            if (value == 18) return CORPS_OUT_TIME;
            if (value == 19) return CORPS_OUT_MONEY;
            if (value == 20) return CORPS_UP_TIME;
            if (value == 21) return CORPS_UP_MONEY;
            if (value == 22) return ADD_MARKNUM;
            if (value == 23) return LESSEN_PVP_LOOT;
            if (value == 24) return FIGHT_ADDSPEED_ON_OFF;
            if (value == 25) return FIGHT_SKIP_ON_OFF;
            if (value == 26) return INC_PVP_LOOT;
            if (value == 27) return BUILD_CREATE_TIME;
            if (value == 28) return BUILD_CREATE_MONEY;
            if (value == 29) return INC_LEGION_HELP_COUNT;
            if (value == 30) return DEC_TECH_MONEY_COST;
            if (value == 31) return DEC_TECH_TIME_COST;
            if (value == 32) return AUTO_PLANE;
            if (value == 33) return FAST_FIGHT;
            if (value == 34) return INC_GATHER_MONEY;
            if (value == 35) return DECR_GATHER_RARE_TIME;
            if (value == 36) return PVE_EXP_ADD;
            return null;
        }
        int number;
        PASSIVEBUFF_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 勋章花色枚举 */
    public static enum MEDAL_CARDCOLOR {
        /** (0)-_- */
        NONE(0),
        /** (1)黑桃 */
        Spades(1),
        /** (2)梅花 */
        Titoni(2),
        /** (3)红桃 */
        Hearts(3),
        /** (4)方片 */
        Diamond(4),
        ;
        public static MEDAL_CARDCOLOR valueOf(int value) {
            if (value == 0) return NONE;
            if (value == 1) return Spades;
            if (value == 2) return Titoni;
            if (value == 3) return Hearts;
            if (value == 4) return Diamond;
            return null;
        }
        int number;
        MEDAL_CARDCOLOR(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 军团建筑类枚举 */
    public static enum LEGIONBUILDTYPE {
        /** (1)军团基地 */
        LegionWar_HQ(1),
        /** (2)兵营 */
        LegionWar_Barracks(2),
        /** (3)车间 */
        LegionWar_Factory(3),
        /** (4)特战兵营 */
        LegionWar_Specical(4),
        /** (5)英雄殿堂 */
        LegionWar_Hero(5),
        /** (6)空军指挥部 */
        LegionWar_Airsupport(6),
        /** (7)城防中心 */
        LegionWar_Wall(7),
        ;
        public static LEGIONBUILDTYPE valueOf(int value) {
            if (value == 1) return LegionWar_HQ;
            if (value == 2) return LegionWar_Barracks;
            if (value == 3) return LegionWar_Factory;
            if (value == 4) return LegionWar_Specical;
            if (value == 5) return LegionWar_Hero;
            if (value == 6) return LegionWar_Airsupport;
            if (value == 7) return LegionWar_Wall;
            return null;
        }
        int number;
        LEGIONBUILDTYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 活动类型枚举 */
    public static enum ACTIVETYPE {
        /** (10000)充值返利活动 */
        ACTIVE_RECHARGE(10000),
        /** (10001)经验活动 */
        ACTIVE_EXP(10001),
        /** (10002)充值活动 */
        ACTIVE_PAY(10002),
        /** (10003)商店物品限时打折活动 */
        ACTIVE_SHOP_OFF_SALE(10003),
        /** (10004)限时、限量商品 */
        ACTIVE_LIMITED_SALE(10004),
        /** (10005)抽奖活动 */
        ACTIVE_DRAW(10005),
        /** (10006)七日礼 */
        ACTIVE_SEVEN_DAY(10006),
        /** (10007)首充送礼 */
        ACTIVE_FIRST_PAY(10007),
        /** (10008)累计充值 */
        ACTIVE_MASS_RECHARGE(10008),
        /** (10009)物品兑换 */
        ACTIVE_EXCHANGE_ITEM(10009),
        /** (20001)宣传画 */
        ACTIVE_PIC(20001),
        /** (11003)英雄礼包 */
        HERO_PACKET(11003),
        ;
        public static ACTIVETYPE valueOf(int value) {
            if (value == 10000) return ACTIVE_RECHARGE;
            if (value == 10001) return ACTIVE_EXP;
            if (value == 10002) return ACTIVE_PAY;
            if (value == 10003) return ACTIVE_SHOP_OFF_SALE;
            if (value == 10004) return ACTIVE_LIMITED_SALE;
            if (value == 10005) return ACTIVE_DRAW;
            if (value == 10006) return ACTIVE_SEVEN_DAY;
            if (value == 10007) return ACTIVE_FIRST_PAY;
            if (value == 10008) return ACTIVE_MASS_RECHARGE;
            if (value == 10009) return ACTIVE_EXCHANGE_ITEM;
            if (value == 20001) return ACTIVE_PIC;
            if (value == 11003) return HERO_PACKET;
            return null;
        }
        int number;
        ACTIVETYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 全局邮件类型 */
    public static enum GLOBMAILTYPE {
        /** (0)GM-无条件判断 */
        MAIL_GM(0),
        /** (1)军团战 */
        MAIL_LEGIONWAR(1),
        /** (2)军团群发 */
        MAIL_LEGIONALL(2),
        /** (3)军团boss */
        MAIL_LEGIONBOSS(3),
        /** (1001)GM-条件判断 */
        MAIL_GM_FILTER(1001),
        /** (1002)GM-集合判断 */
        MAIL_GM_SET(1002),
        /** (1003)GM-计划任务 */
        MAIL_GM_PLAN(1003),
        /** (2001)军团boss */
        MAIL_GIFT_BOSS(2001),
        /** (2002)军团充值返礼 */
        MAIL_GIFT_RECHARGE(2002),
        ;
        public static GLOBMAILTYPE valueOf(int value) {
            if (value == 0) return MAIL_GM;
            if (value == 1) return MAIL_LEGIONWAR;
            if (value == 2) return MAIL_LEGIONALL;
            if (value == 3) return MAIL_LEGIONBOSS;
            if (value == 1001) return MAIL_GM_FILTER;
            if (value == 1002) return MAIL_GM_SET;
            if (value == 1003) return MAIL_GM_PLAN;
            if (value == 2001) return MAIL_GIFT_BOSS;
            if (value == 2002) return MAIL_GIFT_RECHARGE;
            return null;
        }
        int number;
        GLOBMAILTYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 军团buff类型 */
    public static enum LEGIONBUFFERTYPE {
        /** (1)增加军团人数上限 */
        ADD_PEOPLE(1),
        ;
        public static LEGIONBUFFERTYPE valueOf(int value) {
            if (value == 1) return ADD_PEOPLE;
            return null;
        }
        int number;
        LEGIONBUFFERTYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 任务链类型 */
    public static enum TASK_LINK_TYPE {
        /** (1)主线 */
        MAIN(1),
        /** (2)支线 */
        BRANCH(2),
        /** (3)日常 */
        EVERY_DAY(3),
        ;
        public static TASK_LINK_TYPE valueOf(int value) {
            if (value == 1) return MAIN;
            if (value == 2) return BRANCH;
            if (value == 3) return EVERY_DAY;
            return null;
        }
        int number;
        TASK_LINK_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 任务类型 */
    public static enum TASK_TYPE {
        /** (1)把指定的xx建筑升级到xx级 */
        BUILD(1),
        /** (2)训练指定的兵种XX个 */
        CORPS(2),
        /** (3)升级xx科技到xx级 */
        TECH(3),
        /** (4)通过第XX关战役 */
        INSTANCE(4),
        /** (5)以X星评价通过第XX关战役X次 */
        CLEAN_INSTANCE(5),
        /** (6)在竞技场中XX对手XX次 */
        PVP(6),
        /** (7)军衔升级到XX级 */
        RANK(7),
        /** (8)进行xx次XX加速 */
        SPEED_UP(8),
        /** (9)使用XX物品X次 */
        USE_ITEM(9),
        /** (10)获得xx物品  XX个 */
        GATHER_ITEM(10),
        /** (11)获得XX品质XX类物品XX个 */
        GATHER_QUALITY_ITEM(11),
        /** (12)勋章升级 */
        MEDAL_UPGRADE(12),
        /** (13)勋章升星 */
        MEDAL_UPSTAR(13),
        /** (14)勋章升级到指定等级 */
        MEDAL_TO_LEVEL(14),
        /** (15)勋章升级到指定星级 */
        MEDAL_TO_STAR(15),
        /** (16)英雄穿装备 */
        HERO_DRESS_EQUIP(16),
        /** (17)空中支援操作 */
        AIRSUPPORT_OPERATE(17),
        /** (18)获得某个英雄 */
        GET_HERO(18),
        /** (19)获得XX个英雄 */
        GET_HERO_NUMBER(19),
        /** (20)加入一个军团 */
        JOIN_LEGION(20),
        /** (21)玩家升级到XX级 */
        PLAYER_LEVEL(21),
        /** (22)XX建筑建造XX个 */
        BUILDING_NUMBER(22),
        /** (23)XX士兵升级到XX级 */
        SOLIDER_LEVEL(23),
        /** (24)扩地XX次 */
        EXPAND_LAND(24),
        /** (25)布防XX次 */
        SET_DEFENCE(25),
        /** (26)VIP提升到XX级 */
        VIP_LEVEL(26),
        /** (27)登陆奖励 */
        GET_LOGIN_REWARD(27),
        /** (28)等级奖励 */
        GET_LEVEL_REWARD(28),
        /** (29)大转盘奖励 */
        GET_ONLINE_REWARD(29),
        /** (30)星级通关奖励 */
        GET_STAR_PVE_REWARD(30),
        /** (31)通关教学 */
        COMPLETE_TEACH(31),
        /** (32)公会商店购买东东 */
        BUY_FROM_LEGION_STORE(32),
        /** (33)发起公会帮助 */
        HELP_LEGION_MEMBER(33),
        /** (34)开通VIP */
        OPEN_VIP(34),
        /** (35)强化英雄 */
        FORCE_HERO(35),
        /** (36)英雄之路 */
        HERO_PATH(36),
        /** (37)抽勋章 */
        DRAW_MEDAL(37),
        /** (38)装备强化 */
        EQUIP_FORCE(38),
        /** (39)装备分解 */
        EQUIP_SPLITE(39),
        /** (40)飞机升级到指定等级 */
        AIRSUPPORT_UP_LV(40),
        /** (41)通关指定滴英雄之路 */
        HERO_PATH_PASS(41),
        /** (42)通关指定滴悬赏关卡 */
        WANTED_PASS(42),
        /** (43)游戏评分 */
        GAME_SHARE(43),
        /** (44)账号绑定 */
        GAME_ACCOUNT_BIND(44),
        /** (45)游戏分享 */
        GAME_COMMUNION(45),
        /** (46)聊天 */
        CHAT(46),
        /** (47)完成指定的新手引导 */
        FINISH_GUIDE(47),
        /** (48)购买经验 */
        BUY_EXP(48),
        ;
        public static TASK_TYPE valueOf(int value) {
            if (value == 1) return BUILD;
            if (value == 2) return CORPS;
            if (value == 3) return TECH;
            if (value == 4) return INSTANCE;
            if (value == 5) return CLEAN_INSTANCE;
            if (value == 6) return PVP;
            if (value == 7) return RANK;
            if (value == 8) return SPEED_UP;
            if (value == 9) return USE_ITEM;
            if (value == 10) return GATHER_ITEM;
            if (value == 11) return GATHER_QUALITY_ITEM;
            if (value == 12) return MEDAL_UPGRADE;
            if (value == 13) return MEDAL_UPSTAR;
            if (value == 14) return MEDAL_TO_LEVEL;
            if (value == 15) return MEDAL_TO_STAR;
            if (value == 16) return HERO_DRESS_EQUIP;
            if (value == 17) return AIRSUPPORT_OPERATE;
            if (value == 18) return GET_HERO;
            if (value == 19) return GET_HERO_NUMBER;
            if (value == 20) return JOIN_LEGION;
            if (value == 21) return PLAYER_LEVEL;
            if (value == 22) return BUILDING_NUMBER;
            if (value == 23) return SOLIDER_LEVEL;
            if (value == 24) return EXPAND_LAND;
            if (value == 25) return SET_DEFENCE;
            if (value == 26) return VIP_LEVEL;
            if (value == 27) return GET_LOGIN_REWARD;
            if (value == 28) return GET_LEVEL_REWARD;
            if (value == 29) return GET_ONLINE_REWARD;
            if (value == 30) return GET_STAR_PVE_REWARD;
            if (value == 31) return COMPLETE_TEACH;
            if (value == 32) return BUY_FROM_LEGION_STORE;
            if (value == 33) return HELP_LEGION_MEMBER;
            if (value == 34) return OPEN_VIP;
            if (value == 35) return FORCE_HERO;
            if (value == 36) return HERO_PATH;
            if (value == 37) return DRAW_MEDAL;
            if (value == 38) return EQUIP_FORCE;
            if (value == 39) return EQUIP_SPLITE;
            if (value == 40) return AIRSUPPORT_UP_LV;
            if (value == 41) return HERO_PATH_PASS;
            if (value == 42) return WANTED_PASS;
            if (value == 43) return GAME_SHARE;
            if (value == 44) return GAME_ACCOUNT_BIND;
            if (value == 45) return GAME_COMMUNION;
            if (value == 46) return CHAT;
            if (value == 47) return FINISH_GUIDE;
            if (value == 48) return BUY_EXP;
            return null;
        }
        int number;
        TASK_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 领取奖励的类型 */
    public static enum REWARD_TYPE {
        /** (1)登陆奖励 */
        LOGIN(1),
        /** (2)等级奖励 */
        LEVEL(2),
        /** (3)在线时长奖励 */
        ONLINE(3),
        /** (4)PVE 关卡星级奖励 */
        PVE_STAR(4),
        ;
        public static REWARD_TYPE valueOf(int value) {
            if (value == 1) return LOGIN;
            if (value == 2) return LEVEL;
            if (value == 3) return ONLINE;
            if (value == 4) return PVE_STAR;
            return null;
        }
        int number;
        REWARD_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 加速类型 */
    public static enum SPEEDUP_TYPE {
        /** (1)训练加速 */
        MAKE_CORPS(1),
        /** (2)科技升级加速 */
        TECH_UPGRADE(2),
        /** (3)建筑升级加速 */
        BUILD_UPGRADE(3),
        /** (4)士兵升级加速 */
        CORPS_UPGRADE(4),
        /** (5)飞机升级加速 */
        AIR_UPGRADE(5),
        /** (6)扩地加速 */
        EXTEND_LAND(6),
        /** (7)时间任务 */
        TIME_TASK(7),
        ;
        public static SPEEDUP_TYPE valueOf(int value) {
            if (value == 1) return MAKE_CORPS;
            if (value == 2) return TECH_UPGRADE;
            if (value == 3) return BUILD_UPGRADE;
            if (value == 4) return CORPS_UPGRADE;
            if (value == 5) return AIR_UPGRADE;
            if (value == 6) return EXTEND_LAND;
            if (value == 7) return TIME_TASK;
            return null;
        }
        int number;
        SPEEDUP_TYPE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
    /** 空中支援操作 */
    public static enum AIRSUPPORT_OPERATE {
        /** (0)出战 */
        OUT_FIGHTING(0),
        /** (1)收回 */
        TAKE_BACK(1),
        /** (2)施放 */
        FIRE(2),
        /** (3)修理 */
        REPAIR(3),
        /** (4)升级 */
        LEVEL_UP(4),
        ;
        public static AIRSUPPORT_OPERATE valueOf(int value) {
            if (value == 0) return OUT_FIGHTING;
            if (value == 1) return TAKE_BACK;
            if (value == 2) return FIRE;
            if (value == 3) return REPAIR;
            if (value == 4) return LEVEL_UP;
            return null;
        }
        int number;
        AIRSUPPORT_OPERATE(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
}