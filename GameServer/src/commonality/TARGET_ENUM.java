package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 技能目标类型 */
public enum TARGET_ENUM {
    /** (0)敌方最前面敌人 */
    FRONT_TARGET(0),
    /** (1)敌方最前面敌人以及上下两个单位 */
    FRONT_TARGET_UPDOWN(1),
    /** (2)前方队友 */
    FRONT_TEAMMATE(2),
    /** (3)前方队友以及上下两个单位 */
    FRONT_TEAMMATE_UPDOWN(3),
    /** (4)敌方最前面所有敌人 */
    FRONT_ALL_TARGET(4),
    ;
    public static TARGET_ENUM valueOf(int value) {
        if (value == 0) return FRONT_TARGET;
        if (value == 1) return FRONT_TARGET_UPDOWN;
        if (value == 2) return FRONT_TEAMMATE;
        if (value == 3) return FRONT_TEAMMATE_UPDOWN;
        if (value == 4) return FRONT_ALL_TARGET;
        return null;
    }
    int number;
    TARGET_ENUM(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}