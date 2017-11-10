package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 渠道列表 */
public enum CHANNEL_TYPE {
    /** (1)googleplay(world war toy) */
    GooglePlayWar(1),
    /** (2)googleplay(bedroom) */
    GooglePlayBedroom(2),
    /** (3)360平台 */
    Q360(3),
    /** (4)AppStore(World War Toy) */
    AppStoreWWT(4),
    /** (5)AppStore(Toy Warfare) */
    AppStoreTW(5),
    /** (6)Toy Warfare(Android) */
    ANDROID_TOY_WARFARE(6),
    /** (7)Clash of Toys(Android) */
    ANDROID_CLASH_OF_TOYS(7),
    /** (8)Clash of Toys(App Store) */
    APPSTORE_CLASH_OF_TOYS(8),
    /** (9)Army Man - Toy Wars(Android) */
    ANDROID_ARMY_MAN(9),
    /** (10)Toy Brigade(Android) */
    ANDROID_TOY_BRIGADE(10),
    /** (11)Toy Brigade(IOS) */
    IOS_TOY_BRIGADE(11),
    /** (12)War of Toys(Android) */
    ANDROID_WAR_OF_TOYS(12),
    /** (13)Toybox Wars(Android) */
    ANDROID_TOYBOX_WARS(13),
    /** (14)Toy Soldiers(Android) */
    ANDROID_TOY_SOLDIERS(14),
    /** (15)Война армий(Android) */
    ANDROID_RU(15),
    /** (16)Army of Toys(Android) */
    ANDROID_ARMY_OF_TOYS(16),
    /** (17)Soldier of Toys(Android) */
    ANDROID_SOLDIER_OF_TOYS(17),
    /** (18)阿拉伯版本(Android) */
    ANDROID_ARAB(18),
    /** (19)Soldiers Of Toy(Android) */
    ANDROID_SOLDIERS_OF_TOY(19),
    /** (20)玩具兵大戰(Android) */
    ANDROID_CHT(20),
    /** (21)World War Toys(Android) */
    ANDROID_WORLD_WAR_TOYS(21),
    /** (22)阿拉伯版本(IOS) */
    IOS_ARAB(22),
    /** (23)Toy Army(IOS) */
    IOS_TOY_ARMY(23),
    /** (24)德语版(Android) */
    ANDROID_GER(24),
    /** (25)葡萄牙语(Android) */
    ANDROID_POR(25),
    /** (26)西班牙语(Android) */
    ANDROID_ES(26),
    /** (27)Toy Army(安卓) */
    ANDROID_TOY_ARMY(27),
    /** (28)WOPS(安卓) */
    ANDROID_WOPS(28),
    /** (29)Война армий(IOS) */
    IOS_RU(29),
    /** (30)德语版(IOS) */
    IOS_GER(30),
    /** (31)繁体(IOS) */
    IOS_CHT(31),
    /** (32)葡萄牙语(IOS) */
    IOS_POR(32),
    /** (33)Battlefield of Toys(Android) */
    ANDROID_BATTLEFIELD_OF_TOYS(33),
    /** (34)Toy defense online(Android) */
    ANDROID_TOY_DEFENSE_ONLINE(34),
    /** (35)COT BBI(Android) */
    ANDROID_COT_BBI(35),
    ;
    public static CHANNEL_TYPE valueOf(int value) {
        if (value == 1) return GooglePlayWar;
        if (value == 2) return GooglePlayBedroom;
        if (value == 3) return Q360;
        if (value == 4) return AppStoreWWT;
        if (value == 5) return AppStoreTW;
        if (value == 6) return ANDROID_TOY_WARFARE;
        if (value == 7) return ANDROID_CLASH_OF_TOYS;
        if (value == 8) return APPSTORE_CLASH_OF_TOYS;
        if (value == 9) return ANDROID_ARMY_MAN;
        if (value == 10) return ANDROID_TOY_BRIGADE;
        if (value == 11) return IOS_TOY_BRIGADE;
        if (value == 12) return ANDROID_WAR_OF_TOYS;
        if (value == 13) return ANDROID_TOYBOX_WARS;
        if (value == 14) return ANDROID_TOY_SOLDIERS;
        if (value == 15) return ANDROID_RU;
        if (value == 16) return ANDROID_ARMY_OF_TOYS;
        if (value == 17) return ANDROID_SOLDIER_OF_TOYS;
        if (value == 18) return ANDROID_ARAB;
        if (value == 19) return ANDROID_SOLDIERS_OF_TOY;
        if (value == 20) return ANDROID_CHT;
        if (value == 21) return ANDROID_WORLD_WAR_TOYS;
        if (value == 22) return IOS_ARAB;
        if (value == 23) return IOS_TOY_ARMY;
        if (value == 24) return ANDROID_GER;
        if (value == 25) return ANDROID_POR;
        if (value == 26) return ANDROID_ES;
        if (value == 27) return ANDROID_TOY_ARMY;
        if (value == 28) return ANDROID_WOPS;
        if (value == 29) return IOS_RU;
        if (value == 30) return IOS_GER;
        if (value == 31) return IOS_CHT;
        if (value == 32) return IOS_POR;
        if (value == 33) return ANDROID_BATTLEFIELD_OF_TOYS;
        if (value == 34) return ANDROID_TOY_DEFENSE_ONLINE;
        if (value == 35) return ANDROID_COT_BBI;
        return null;
    }
    int number;
    CHANNEL_TYPE(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}