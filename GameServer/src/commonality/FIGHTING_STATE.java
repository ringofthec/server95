package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 战斗当前状态 */
public enum FIGHTING_STATE {
    /** (0)正在布置兵种 */
    LAYOUT(0),
    /** (1)正在战斗 */
    FIGHTING(1),
    /** (2)战斗结束 */
    FIGHTING_OVER(2),
    ;
    public static FIGHTING_STATE valueOf(int value) {
        if (value == 0) return LAYOUT;
        if (value == 1) return FIGHTING;
        if (value == 2) return FIGHTING_OVER;
        return null;
    }
    int number;
    FIGHTING_STATE(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}