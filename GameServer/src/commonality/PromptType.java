package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** Prompt */
public enum PromptType {
    /** (-1) */
    NONE(-1),
    /** (100)国王战攻击胜利 */
    KINGWAR_ATTACK_WIN(100),
    /** (101)国王战成为国王 */
    KINGWAR_BECOMES_KING(101),
    /** (102)国王战防御胜利 */
    KINGWAR_DEFENSE_WIN(102),
    /** (103)国王战系统防御胜利 */
    KINGWAR_SYSTEM_DEFENSE_WIN(103),
    /** (104)国王战任命国务卿 */
    KINGWAR_TAKEOFFICE_SOS(104),
    /** (105)国王战任命国防部长 */
    KINGWAR_TAKEOFFICE_DM(105),
    /** (106)国王战任命元帅 */
    KINGWAR_TAKEOFFICE_MAR(106),
    /** (107)国王战任命突击者 */
    KINGWAR_TAKEOFFICE_RAI(107),
    /** (108)国王战任命守护者 */
    KINGWAR_TAKEOFFICE_GUAR(108),
    /** (109)国王战任命大学者 */
    KINGWAR_TAKEOFFICE_SCHO(109),
    /** (110)国王战任命后勤部长 */
    KINGWAR_TAKEOFFICE_HOL(110),
    /** (111)国王战任命训练教官 */
    KINGWAR_TAKEOFFICE_INST(111),
    /** (112)国王战防御值通知 */
    KINGWAR_DEFVAL(112),
    /** (113)国王战发放福利 */
    KINGWAR_PUBLISH_WELFARE(113),
    /** (114)国王战国王上线 */
    KINGWAR_KING_ONLINE(114),
    /** (120)国王战不能攻击 */
    KINGWAR_CANNOT_ATTACK(120),
    /** (121)国王战不能援助 */
    KINGWAR_CANNOT_DONATE(121),
    /** (146)国王战攻击获得积分 */
    KINGWAR_ATTACK_GET_POINT(146),
    /** (150)国王战即将开始 */
    KINGWAR_WILL_START(150),
    /** (151)国王战已开始 */
    KINGWAR_START(151),
    /** (147)国王战自动减少防御值通知 */
    KINGWAR_DEFVAL_AUTO(147),
    /** (148)国王战不能领取官员工资 */
    KINGWAR_NOTGET_OFFICER_WAGE(148),
    /** (149)国王战不能领取荣誉奖励 */
    KINGWAR_NOTGET_HONOR_GIFT(149),
    /** (153)国王战限制等级 */
    KINGWAR_LIMIT_LEVEL(153),
    /** (130)获得弹珠提示 */
    HOODLE_GET(130),
    /** (135)士兵弹珠格数已满 */
    CORPS_HOODLE_FULL(135),
    /** (136)弹珠背包已满 */
    HOODLE_PACKET_FULL(136),
    /** (10021) */
    BUY_EXP_COUNT_NOT_ENOUGH(10021),
    /** (10022) */
    GET_EXP_BY_BUY(10022),
    /** (100000) */
    SEESION_ERROR(100000),
    /** (100001) */
    LOGIN_STATE_ERROR(100001),
    /** (100002) */
    SERVER_UPDATE(100002),
    /** (100003) */
    SERVER_WILL_EXIT(100003),
    /** (100004) */
    PLAYER_NAME_INVAILD(100004),
    /** (100005) */
    LEGION_NAME_INVAILD(100005),
    /** (100014) */
    WAR_CP_NOT_ENOUGH(100014),
    /** (100016) */
    BIND_ALREAY(100016),
    /** (110000) */
    MONEY_NOT_ENOUGH(110000),
    /** (110001) */
    GOLD_NOT_ENOUGH(110001),
    /** (110002) */
    RARE_NOT_ENOUGH(110002),
    /** (110003) */
    ITEM_NOT_ENOUGH(110003),
    /** (110004) */
    CP_NOT_ENOUGH(110004),
    /** (110005) */
    PVP_BUY_ENOUGH(110005),
    /** (110006) */
    CP_BUY_ENOUGH(110006),
    /** (110007) */
    QUEUE_ENOUGH(110007),
    /** (110008) */
    MEDAL_LEVEL_NOT_ENOUGH(110008),
    /** (110009) */
    NEED_HAVE_LEGION(110009),
    /** (110010) */
    FACTORY_ENOUGH(110010),
    /** (110011)仓库溢出 */
    DEPOT_OVERFLOW(110011),
    /** (110012)主动PVP 护盾消失警告 */
    SHIELD_DESTROY(110012),
    /** (110013) */
    EQUIP_BAG_IS_FULL(110013),
    /** (110014) */
    CHECK_FIGHTING_CORPS(110014),
    /** (110015) */
    ALREADY_IN_SHIELD(110015),
    /** (110018) */
    WAR_CP_BUY_ENOUGH(110018),
    /** (110100) */
    NEED_PLAYER_LEVEL(110100),
    /** (110101) */
    NEED_BUILD_LEVEL(110101),
    /** (110102) */
    CORP_UP_NEED_BUILD(110102),
    /** (110201) */
    BUILD_QUEUE_NOT_ENOUGH(110201),
    /** (110202) */
    BUILD_IS_FULL(110202),
    /** (110203) */
    BUILD_STATE_ERR(110203),
    /** (110204) */
    BUILD_MAX_LEVEL(110204),
    /** (110205) */
    ITEM_BUY_LIMIT(110205),
    /** (110206) */
    CORP_QUEUE_NOT_ENOUGH(110206),
    /** (110207) */
    POPULATION_FULL(110207),
    /** (110208) */
    TECH_IS_FULL(110208),
    /** (110209) */
    CORP_LEVEL_MAX(110209),
    /** (110210) */
    CONTRBUTE_IS_FULL(110210),
    /** (110211) */
    INSTANCE_COUNT_LIMIT(110211),
    /** (110212) */
    DEFENCE_TIMEOUT(110212),
    /** (110213) */
    FIGHT_TIMEOUT(110213),
    /** (110214) */
    PVP_NOT_MATCH(110214),
    /** (110215) */
    DEFENSE_FORMATION_SAVE(110215),
    /** (110216)PVP结果出现错误 */
    PVP_RESULT_ERROR(110216),
    /** (110217)PVP战斗超时 */
    PVP_FIGHTING_TIMEOUT(110217),
    /** (110218)PVP布阵时间超时 */
    PVP_LAYOUT_TIMEOUT(110218),
    /** (110220)玩家不在线 */
    PLAYER_NOT_ONLINE(110220),
    /** (110221)大转轮次数用完 */
    TIMES_USED_OUT(110221),
    /** (120001) */
    TARGET_LEVEL_UP(120001),
    /** (120002) */
    RECIVE_ITEM(120002),
    /** (120003) */
    BUILD_LEVEL_UP(120003),
    /** (120004) */
    BUILD_CREATE(120004),
    /** (120005) */
    USE_ITEM_COUNT(120005),
    /** (120006) */
    MODITY_LEGION_PUBLIC(120006),
    /** (130000) */
    SEND_HELP(130000),
    /** (130001) */
    LEGION_MONEY_CONTRBUTE(130001),
    /** (130002) */
    LEGION_ITEM_CONTRBUTE(130002),
    /** (130003) */
    LEGION_CONTRBUTE_NOTIFY(130003),
    /** (130004) */
    BUG_SUPPORT(130004),
    /** (130005) */
    LEGION_HELP(130005),
    /** (130006) */
    LEGION_JOIN(130006),
    /** (130007) */
    LEGION_OUT(130007),
    /** (130008) */
    LEGION_KIKOUT(130008),
    /** (130009) */
    LEGION_OFFICAL(130009),
    /** (130010) */
    LEGION_ASSi(130010),
    /** (130011) */
    LEGION_DEMO(130011),
    /** (130012) */
    LEGION_HELP_YOU(130012),
    /** (130013) */
    LEGION_OFFIA_SIZE(130013),
    /** (130014) */
    LEGION_WUZI_HECI(130014),
    /** (130018)玩家没有军团 */
    PLAYER_NO_LEGION(130018),
    /** (130020)军团权限不足 */
    LEGION_NO_CAN_IT(130020),
    /** (130021)该位置没人 */
    LEGION_POS_NO_USE(130021),
    /** (130022)该位置已被人占用 */
    LEGION_POS_USED(130022),
    /** (130023)已有敌对军团 */
    LEGION_HAS_OTHER_LEGION(130023),
    /** (130024)该期间无法执行该操作 */
    LEGION_STATE_INCORRECT(130024),
    /** (130026)该军团已被宣战 */
    LEGION_DECLARATION_WAR(130026),
    /** (130027)请先宣战 */
    LEGION_FIRST_DECLARATION_WAR(130027),
    /** (130028)军团战备资金不足 */
    LEGION_BATTLE_MONEY_NOT_ENOUGH(130028),
    /** (130029)军团建筑物不能升级 */
    LEGION_BUILD_LEVEL_LIMITED(130029),
    /** (130030)军团建筑物守卫已死亡 */
    LEGION_BUILD_PLAYER_DEAD(130030),
    /** (130031)加入军团cd未到不能参加军团战 */
    LEGION_WAR_PLAYER_JOIN_CD(130031),
    /** (130032)军团长上线了 */
    LEGION_BOSS_LOGIN_BROADCAST(130032),
    /** (130033)军团官员上线了 */
    LEGION_OFFICICAL_LOGIN_BROADCAST(130033),
    /** (130034)该位置已被人攻击 */
    LEGION_POS_ATTACK(130034),
    /** (130035)我们军团已经向军团xx宣战。 */
    LEGION_ATTACK_OTHER(130035),
    /** (130036)我们军团已经被军团xx宣战。 */
    LEGION_ATTACK_ME(130036),
    /** (130037)军团战进入正式战斗阶段，请各位成员去军团战界面参加战斗！ */
    LEGION_START_FIGHT(130037),
    /** (130038)敌方的xx已经被我方攻破，请各位成员继续努力！ */
    LEGION_BUILD_BROKEN(130038),
    /** (130039)军团战结束 */
    LEGION_WAR_OVER(130039),
    /** (130044)军团战战报提示 */
    LEGION_WAR_REPORT_PROMPT(130044),
    /** (130046)军团战等级限制提示 */
    LEGION_WAR_LEVEL_PROMPT(130046),
    /** (160001)PVP活动军衔限制 */
    PVP_ACTIVITY_FEAT_LIMIT(160001),
    /** (160002)获取pvp活动排行奖励限制 */
    GET_PVP_ACTIVITY_RANK_GIFT_LIMIT(160002),
    /** (160003)pvp活动尚未开启 */
    PVP_ACTIVITY_NOT_OPEN(160003),
    /** (180000) */
    MONEY_FULL(180000),
    /** (140000) */
    GOLD_NOT_ENOUGH_BOX(140000),
    /** (140001) */
    IS_LEGION_DONATE(140001),
    /** (150000) */
    IS_CLOSURE(150000),
    /** (160000) */
    PVP_COUNT(160000),
    /** (190000)战报分享成功 */
    ANCHOR_SHARE_SUCCEED(190000),
    /** (190001)战报分享失败 */
    ANCHOR_SHARE_FAIL(190001),
    /** (190002)世界聊天太频繁 */
    WORLD_SEND_TIME(190002),
    /** (190003)军团聊天太频繁 */
    LEGION_SEND_TIME(190003),
    /** (190004)暂时不能PVP复仇 */
    FUCK_PLAYER_ERROR(190004),
    /** (190005)该军团名称已经存在 */
    LEGION_NAME_FAIL(190005),
    /** (190006)请先选择私聊对象 */
    PRIVATE_TARGET_FAIL(190006),
    /** (190010)屏蔽了XX玩家 */
    HIDE_PLAYER(190010),
    /** (190011)已经在屏蔽列表中 */
    IN_HIDE_LIST(190011),
    /** (190012)被屏蔽了，无法对其私聊 */
    HIDE_DONOT_PRIVATE(190012),
    /** (190013)被屏蔽了，无法对其发送邮件 */
    HIDE_DONOT_MAIL(190013),
    /** (190014)屏蔽列表满了 */
    HIDE_FULL(190014),
    /** (190015)屏蔽列表满了 */
    DELETE_HIDE(190015),
    /** (190016)PVP玩家在线，不能被打 */
    PVP_PLAYER_ONLINE(190016),
    /** (190017)PVP玩家保护，不能被打 */
    PVP_PLAYER_SHIELD(190017),
    /** (200000) */
    TASK_COMPLETE(200000),
    /** (300000) */
    BELONG_LEGION(300000),
    /** (400000) */
    BUY_BUILD_QUEUE(400000),
    /** (400001)购买护盾 */
    BUY_SHIELD(400001),
    /** (41) */
    REC_EXP(41),
    /** (84) */
    REFUSE_CHAT(84),
    /** (85) */
    REFUSE_CHAT_TIME(85),
    /** (92) */
    BANE_TIME(92),
    /** (500000) */
    DEFAULT_NAME(500000),
    /** (600000) */
    HERO_PATH_CROP_LVUP(600000),
    /** (700000) */
    HERO_PATH_FUSH_NUM(700000),
    /** (600101)英雄升级成功 */
    HERO_LVL_SUCCESS(600101),
    /** (600102)英雄升级失败 */
    HERO_LVL_FAIL(600102),
    /** (600103)英雄升星成功 */
    HERO_STAR_SUCCESS(600103),
    /** (600104)英雄升星失败 */
    HERO_STAR_FAIL(600104),
    /** (600201)装备升级成功 */
    ITEM_LVLUP_SUCCESS(600201),
    /** (600202)装备强化成功 */
    ITEM_STRENGTHEN_SUCCESS(600202),
    /** (600203)装备强化成功并暴击 */
    ITEM_STRENGTHEN_GOODLUCK(600203),
    /** (600210)装备分解成功 */
    ITEM_SPLITE_SUCCESS(600210),
    /** (601001)军团帮助金币提示 */
    LEGION_HELP_MONEY_HIT(601001),
    /** (601002)军团帮助兵种提示 */
    LEGION_HELP_CORP_HIT(601002),
    /** (601003)人口即将满了 */
    LEGION_HELP_CORP_POPU_FULL(601003),
    /** (600106)该功能未开放 */
    FUNCTION_NOT_OPEN(600106),
    /** (700005)清除悬赏关卡CD */
    CLEAR_REWARD_INSTANCE_CD(700005),
    /** (701000)聊天举报 */
    CHAT_REPORT(701000),
    /** (702000)军团升职 */
    UP_OFFICIAL(702000),
    /** (702002)军团升职为官员 */
    UP_OFFICIAL2(702002),
    /** (702003)军团升职为精英成员 */
    UP_OFFICIAL3(702003),
    /** (702010)军团降职 */
    DEMOTION(702010),
    /** (702013)军团降职精英成员 */
    DEMOTION3(702013),
    /** (702014)军团降职为普通成员 */
    DEMOTION4(702014),
    /** (703001)军团建筑物升级 */
    LEGION_BUILD_LEVELUP(703001),
    /** (703002)军团建筑物升级 */
    LEGION_BUILD_LEVELUP_PROMPT(703002),
    /** (8)开箱子获得金币 */
    OPEN_BOX_GET_MONEY(8),
    /** (9)获得体力 */
    GET_CP(9),
    /** (10)获得军团战体力 */
    GET_WAR_CP(10),
    /** (93)军团人员已满 */
    LEGION_MEMBER_LIST_FULL(93),
    /** (94)军团人员已满 */
    LEGION_MEMBER_FULL_FOR_PASS(94),
    /** (800010)军团战宣战 */
    LEGION_WAR_DECLAREWAR(800010),
    /** (800011)军团战被宣战 */
    LEGION_WAR_BE_DECLAREWAR(800011),
    /** (800013)军团战进攻失败cd未到，不能进攻 */
    LEGION_BATTLE_FAIL_CD_NO_ATTACK(800013),
    /** (130040)获得军团经验 */
    LEGION_GET_EXP(130040),
    /** (130041)获得军团资金 */
    LEGION_GET_MONEY(130041),
    /** (130042)获得军团经验 */
    LEGION_GET_EXP_TO_ALL(130042),
    /** (130043)获得军团资金 */
    LEGION_GET_MONEY_TO_ALL(130043),
    /** (10016)获得军团经验 */
    MAIL_TITLE_INCORRT(10016),
    /** (10017)获得军团资金 */
    MAIL_TEXT_INCORRT(10017),
    /** (801000)军团boss被攻击中 */
    LEGION_BOSS_IN_ATTACK(801000),
    /** (801001)军团BOSS攻击次数上限 */
    LEGION_BOSS_ATTACK_MAX(801001),
    /** (801002)兵力不足，不能攻击军团boss */
    NO_ENOUGH_SOLDIER(801002),
    /** (801003)补充士兵科技继续捐赠 */
    NO_ENOUGH_CORPS(801003),
    /** (801004)军团BOSS权限不足 */
    LEGION_BOSS_NO_CAN_OPERATE(801004),
    /** (802000)军团科技升级中 */
    LEGION_TECH_IN_UPGRADE(802000),
    /** (802001)战斗类科技等级上限不能继续升级 */
    FIGHT_TECH_LV_MAX(802001),
    /** (802002)战斗类科技未开启 */
    FIGHT_TECH_NO_OPEN(802002),
    /** (802003)军团资金不足 */
    LEGION_MONEY_NO_ENOUGH(802003),
    /** (802004)军团权限不足 */
    LEGION_TECH_NO_CAN_OPERATE(802004),
    /** (10028)英雄传承成功 */
    HERO_HERITAGE_SUCCESS(10028),
    /** (10029)兑换英雄已存在 */
    EXCHANGE_HERO_HAS_EXIST(10029),
    /** (10030)兑换物品已存在 */
    EXCHANGE_ITEM_HAS_EXIST(10030),
    ;
    public static PromptType valueOf(int value) {
        if (value == -1) return NONE;
        if (value == 100) return KINGWAR_ATTACK_WIN;
        if (value == 101) return KINGWAR_BECOMES_KING;
        if (value == 102) return KINGWAR_DEFENSE_WIN;
        if (value == 103) return KINGWAR_SYSTEM_DEFENSE_WIN;
        if (value == 104) return KINGWAR_TAKEOFFICE_SOS;
        if (value == 105) return KINGWAR_TAKEOFFICE_DM;
        if (value == 106) return KINGWAR_TAKEOFFICE_MAR;
        if (value == 107) return KINGWAR_TAKEOFFICE_RAI;
        if (value == 108) return KINGWAR_TAKEOFFICE_GUAR;
        if (value == 109) return KINGWAR_TAKEOFFICE_SCHO;
        if (value == 110) return KINGWAR_TAKEOFFICE_HOL;
        if (value == 111) return KINGWAR_TAKEOFFICE_INST;
        if (value == 112) return KINGWAR_DEFVAL;
        if (value == 113) return KINGWAR_PUBLISH_WELFARE;
        if (value == 114) return KINGWAR_KING_ONLINE;
        if (value == 120) return KINGWAR_CANNOT_ATTACK;
        if (value == 121) return KINGWAR_CANNOT_DONATE;
        if (value == 146) return KINGWAR_ATTACK_GET_POINT;
        if (value == 150) return KINGWAR_WILL_START;
        if (value == 151) return KINGWAR_START;
        if (value == 147) return KINGWAR_DEFVAL_AUTO;
        if (value == 148) return KINGWAR_NOTGET_OFFICER_WAGE;
        if (value == 149) return KINGWAR_NOTGET_HONOR_GIFT;
        if (value == 153) return KINGWAR_LIMIT_LEVEL;
        if (value == 130) return HOODLE_GET;
        if (value == 135) return CORPS_HOODLE_FULL;
        if (value == 136) return HOODLE_PACKET_FULL;
        if (value == 10021) return BUY_EXP_COUNT_NOT_ENOUGH;
        if (value == 10022) return GET_EXP_BY_BUY;
        if (value == 100000) return SEESION_ERROR;
        if (value == 100001) return LOGIN_STATE_ERROR;
        if (value == 100002) return SERVER_UPDATE;
        if (value == 100003) return SERVER_WILL_EXIT;
        if (value == 100004) return PLAYER_NAME_INVAILD;
        if (value == 100005) return LEGION_NAME_INVAILD;
        if (value == 100014) return WAR_CP_NOT_ENOUGH;
        if (value == 100016) return BIND_ALREAY;
        if (value == 110000) return MONEY_NOT_ENOUGH;
        if (value == 110001) return GOLD_NOT_ENOUGH;
        if (value == 110002) return RARE_NOT_ENOUGH;
        if (value == 110003) return ITEM_NOT_ENOUGH;
        if (value == 110004) return CP_NOT_ENOUGH;
        if (value == 110005) return PVP_BUY_ENOUGH;
        if (value == 110006) return CP_BUY_ENOUGH;
        if (value == 110007) return QUEUE_ENOUGH;
        if (value == 110008) return MEDAL_LEVEL_NOT_ENOUGH;
        if (value == 110009) return NEED_HAVE_LEGION;
        if (value == 110010) return FACTORY_ENOUGH;
        if (value == 110011) return DEPOT_OVERFLOW;
        if (value == 110012) return SHIELD_DESTROY;
        if (value == 110013) return EQUIP_BAG_IS_FULL;
        if (value == 110014) return CHECK_FIGHTING_CORPS;
        if (value == 110015) return ALREADY_IN_SHIELD;
        if (value == 110018) return WAR_CP_BUY_ENOUGH;
        if (value == 110100) return NEED_PLAYER_LEVEL;
        if (value == 110101) return NEED_BUILD_LEVEL;
        if (value == 110102) return CORP_UP_NEED_BUILD;
        if (value == 110201) return BUILD_QUEUE_NOT_ENOUGH;
        if (value == 110202) return BUILD_IS_FULL;
        if (value == 110203) return BUILD_STATE_ERR;
        if (value == 110204) return BUILD_MAX_LEVEL;
        if (value == 110205) return ITEM_BUY_LIMIT;
        if (value == 110206) return CORP_QUEUE_NOT_ENOUGH;
        if (value == 110207) return POPULATION_FULL;
        if (value == 110208) return TECH_IS_FULL;
        if (value == 110209) return CORP_LEVEL_MAX;
        if (value == 110210) return CONTRBUTE_IS_FULL;
        if (value == 110211) return INSTANCE_COUNT_LIMIT;
        if (value == 110212) return DEFENCE_TIMEOUT;
        if (value == 110213) return FIGHT_TIMEOUT;
        if (value == 110214) return PVP_NOT_MATCH;
        if (value == 110215) return DEFENSE_FORMATION_SAVE;
        if (value == 110216) return PVP_RESULT_ERROR;
        if (value == 110217) return PVP_FIGHTING_TIMEOUT;
        if (value == 110218) return PVP_LAYOUT_TIMEOUT;
        if (value == 110220) return PLAYER_NOT_ONLINE;
        if (value == 110221) return TIMES_USED_OUT;
        if (value == 120001) return TARGET_LEVEL_UP;
        if (value == 120002) return RECIVE_ITEM;
        if (value == 120003) return BUILD_LEVEL_UP;
        if (value == 120004) return BUILD_CREATE;
        if (value == 120005) return USE_ITEM_COUNT;
        if (value == 120006) return MODITY_LEGION_PUBLIC;
        if (value == 130000) return SEND_HELP;
        if (value == 130001) return LEGION_MONEY_CONTRBUTE;
        if (value == 130002) return LEGION_ITEM_CONTRBUTE;
        if (value == 130003) return LEGION_CONTRBUTE_NOTIFY;
        if (value == 130004) return BUG_SUPPORT;
        if (value == 130005) return LEGION_HELP;
        if (value == 130006) return LEGION_JOIN;
        if (value == 130007) return LEGION_OUT;
        if (value == 130008) return LEGION_KIKOUT;
        if (value == 130009) return LEGION_OFFICAL;
        if (value == 130010) return LEGION_ASSi;
        if (value == 130011) return LEGION_DEMO;
        if (value == 130012) return LEGION_HELP_YOU;
        if (value == 130013) return LEGION_OFFIA_SIZE;
        if (value == 130014) return LEGION_WUZI_HECI;
        if (value == 130018) return PLAYER_NO_LEGION;
        if (value == 130020) return LEGION_NO_CAN_IT;
        if (value == 130021) return LEGION_POS_NO_USE;
        if (value == 130022) return LEGION_POS_USED;
        if (value == 130023) return LEGION_HAS_OTHER_LEGION;
        if (value == 130024) return LEGION_STATE_INCORRECT;
        if (value == 130026) return LEGION_DECLARATION_WAR;
        if (value == 130027) return LEGION_FIRST_DECLARATION_WAR;
        if (value == 130028) return LEGION_BATTLE_MONEY_NOT_ENOUGH;
        if (value == 130029) return LEGION_BUILD_LEVEL_LIMITED;
        if (value == 130030) return LEGION_BUILD_PLAYER_DEAD;
        if (value == 130031) return LEGION_WAR_PLAYER_JOIN_CD;
        if (value == 130032) return LEGION_BOSS_LOGIN_BROADCAST;
        if (value == 130033) return LEGION_OFFICICAL_LOGIN_BROADCAST;
        if (value == 130034) return LEGION_POS_ATTACK;
        if (value == 130035) return LEGION_ATTACK_OTHER;
        if (value == 130036) return LEGION_ATTACK_ME;
        if (value == 130037) return LEGION_START_FIGHT;
        if (value == 130038) return LEGION_BUILD_BROKEN;
        if (value == 130039) return LEGION_WAR_OVER;
        if (value == 130044) return LEGION_WAR_REPORT_PROMPT;
        if (value == 130046) return LEGION_WAR_LEVEL_PROMPT;
        if (value == 160001) return PVP_ACTIVITY_FEAT_LIMIT;
        if (value == 160002) return GET_PVP_ACTIVITY_RANK_GIFT_LIMIT;
        if (value == 160003) return PVP_ACTIVITY_NOT_OPEN;
        if (value == 180000) return MONEY_FULL;
        if (value == 140000) return GOLD_NOT_ENOUGH_BOX;
        if (value == 140001) return IS_LEGION_DONATE;
        if (value == 150000) return IS_CLOSURE;
        if (value == 160000) return PVP_COUNT;
        if (value == 190000) return ANCHOR_SHARE_SUCCEED;
        if (value == 190001) return ANCHOR_SHARE_FAIL;
        if (value == 190002) return WORLD_SEND_TIME;
        if (value == 190003) return LEGION_SEND_TIME;
        if (value == 190004) return FUCK_PLAYER_ERROR;
        if (value == 190005) return LEGION_NAME_FAIL;
        if (value == 190006) return PRIVATE_TARGET_FAIL;
        if (value == 190010) return HIDE_PLAYER;
        if (value == 190011) return IN_HIDE_LIST;
        if (value == 190012) return HIDE_DONOT_PRIVATE;
        if (value == 190013) return HIDE_DONOT_MAIL;
        if (value == 190014) return HIDE_FULL;
        if (value == 190015) return DELETE_HIDE;
        if (value == 190016) return PVP_PLAYER_ONLINE;
        if (value == 190017) return PVP_PLAYER_SHIELD;
        if (value == 200000) return TASK_COMPLETE;
        if (value == 300000) return BELONG_LEGION;
        if (value == 400000) return BUY_BUILD_QUEUE;
        if (value == 400001) return BUY_SHIELD;
        if (value == 41) return REC_EXP;
        if (value == 84) return REFUSE_CHAT;
        if (value == 85) return REFUSE_CHAT_TIME;
        if (value == 92) return BANE_TIME;
        if (value == 500000) return DEFAULT_NAME;
        if (value == 600000) return HERO_PATH_CROP_LVUP;
        if (value == 700000) return HERO_PATH_FUSH_NUM;
        if (value == 600101) return HERO_LVL_SUCCESS;
        if (value == 600102) return HERO_LVL_FAIL;
        if (value == 600103) return HERO_STAR_SUCCESS;
        if (value == 600104) return HERO_STAR_FAIL;
        if (value == 600201) return ITEM_LVLUP_SUCCESS;
        if (value == 600202) return ITEM_STRENGTHEN_SUCCESS;
        if (value == 600203) return ITEM_STRENGTHEN_GOODLUCK;
        if (value == 600210) return ITEM_SPLITE_SUCCESS;
        if (value == 601001) return LEGION_HELP_MONEY_HIT;
        if (value == 601002) return LEGION_HELP_CORP_HIT;
        if (value == 601003) return LEGION_HELP_CORP_POPU_FULL;
        if (value == 600106) return FUNCTION_NOT_OPEN;
        if (value == 700005) return CLEAR_REWARD_INSTANCE_CD;
        if (value == 701000) return CHAT_REPORT;
        if (value == 702000) return UP_OFFICIAL;
        if (value == 702002) return UP_OFFICIAL2;
        if (value == 702003) return UP_OFFICIAL3;
        if (value == 702010) return DEMOTION;
        if (value == 702013) return DEMOTION3;
        if (value == 702014) return DEMOTION4;
        if (value == 703001) return LEGION_BUILD_LEVELUP;
        if (value == 703002) return LEGION_BUILD_LEVELUP_PROMPT;
        if (value == 8) return OPEN_BOX_GET_MONEY;
        if (value == 9) return GET_CP;
        if (value == 10) return GET_WAR_CP;
        if (value == 93) return LEGION_MEMBER_LIST_FULL;
        if (value == 94) return LEGION_MEMBER_FULL_FOR_PASS;
        if (value == 800010) return LEGION_WAR_DECLAREWAR;
        if (value == 800011) return LEGION_WAR_BE_DECLAREWAR;
        if (value == 800013) return LEGION_BATTLE_FAIL_CD_NO_ATTACK;
        if (value == 130040) return LEGION_GET_EXP;
        if (value == 130041) return LEGION_GET_MONEY;
        if (value == 130042) return LEGION_GET_EXP_TO_ALL;
        if (value == 130043) return LEGION_GET_MONEY_TO_ALL;
        if (value == 10016) return MAIL_TITLE_INCORRT;
        if (value == 10017) return MAIL_TEXT_INCORRT;
        if (value == 801000) return LEGION_BOSS_IN_ATTACK;
        if (value == 801001) return LEGION_BOSS_ATTACK_MAX;
        if (value == 801002) return NO_ENOUGH_SOLDIER;
        if (value == 801003) return NO_ENOUGH_CORPS;
        if (value == 801004) return LEGION_BOSS_NO_CAN_OPERATE;
        if (value == 802000) return LEGION_TECH_IN_UPGRADE;
        if (value == 802001) return FIGHT_TECH_LV_MAX;
        if (value == 802002) return FIGHT_TECH_NO_OPEN;
        if (value == 802003) return LEGION_MONEY_NO_ENOUGH;
        if (value == 802004) return LEGION_TECH_NO_CAN_OPERATE;
        if (value == 10028) return HERO_HERITAGE_SUCCESS;
        if (value == 10029) return EXCHANGE_HERO_HAS_EXIST;
        if (value == 10030) return EXCHANGE_ITEM_HAS_EXIST;
        return null;
    }
    int number;
    PromptType(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}