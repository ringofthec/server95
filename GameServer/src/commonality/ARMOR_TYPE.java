package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 护甲类型 */
public enum ARMOR_TYPE {
    /** (1)轻甲 */
    LIGHT(1),
    /** (2)中甲 */
    STRONG(2),
    /** (3)重甲 */
    COMPLEX(3),
    /** (4)英雄护甲 */
    HERO(4),
    ;
    public static ARMOR_TYPE valueOf(int value) {
        if (value == 1) return LIGHT;
        if (value == 2) return STRONG;
        if (value == 3) return COMPLEX;
        if (value == 4) return HERO;
        return null;
    }
    int number;
    ARMOR_TYPE(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}