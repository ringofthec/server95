package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 推送消息类型 */
public class PUSH_EVENT_TYPE {
    /** 体力恢复满了 */
    public static final Integer CP_RECOVE_FULL = 1;
    /** 被人在PVP干了 */
    public static final Integer FUCK_WITH_PVP = 2;
    /** 全部士兵训练完成 */
    public static final Integer ALL_CORP_READY = 3;
    /** 超过3天未登录 */
    public static final Integer OVRE_3_DAY_NOT_LOGIN = 4;
    /** 建筑升级完成 */
    public static final Integer BUILD_UPGRADE_IS_READY = 5;
    /** 建筑建筑完成 */
    public static final Integer BUILD_IS_READY = 6;
    /** 士兵升级完成 */
    public static final Integer CORP_UPGRADE_IS_READY = 7;
    /** 科技升级完成 */
    public static final Integer TECH_UPGRADE_IS_READY = 8;
    /** 军团商店自动刷新 */
    public static final Integer LEGION_STORE_FLUSH = 9;
    /** 军团战宣战 */
    public static final Integer LEGION_WAR_DECLAREWAR = 10;
    /** 军团战被宣战 */
    public static final Integer LEGION_WAR_BE_DECLAREWAR = 11;
    /** 军团战开战 */
    public static final Integer LEGION_WAR_START = 12;
    /** 飞机升级完成 */
    public static final Integer AIRSUPPORT_UPGRADE_IS_READY = 13;
    /** PUSH_EVENT_TYPE 构造函数 */
    static { 
    }
}