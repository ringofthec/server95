package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 战斗时候的阵营  左边还是右边 */
public enum CAMP_ENUM {
    /** (0)左边阵营 */
    LEFT(0),
    /** (1)右边阵营 */
    RIGHT(1),
    ;
    public static CAMP_ENUM valueOf(int value) {
        if (value == 0) return LEFT;
        if (value == 1) return RIGHT;
        return null;
    }
    int number;
    CAMP_ENUM(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}