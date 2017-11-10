package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 兵种类型 */
public enum CREATURE_ENUM {
    /** (0)空中支援 */
    AIRSUPPORT(0),
    /** (1)城堡 */
    CASTLE(1),
    /** (2)炮台 箭塔 堡垒 */
    TOWER(2),
    /** (3)英雄 */
    HERO(3),
    /** (4)普通士兵 */
    SOLIDER(4),
    /** (5)陷阱 */
    TRAP(5),
    ;
    public static CREATURE_ENUM valueOf(int value) {
        if (value == 0) return AIRSUPPORT;
        if (value == 1) return CASTLE;
        if (value == 2) return TOWER;
        if (value == 3) return HERO;
        if (value == 4) return SOLIDER;
        if (value == 5) return TRAP;
        return null;
    }
    int number;
    CREATURE_ENUM(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}