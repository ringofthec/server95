package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 服务器给所有玩家的玩家判断条件 */
public enum ConveyCondition {
    /** (0)无条件 全部转发 */
    NONE(0),
    /** (1)需要军团 */
    NEED_LEGION(1),
    /** (2)需要等级 */
    NEED_LEVEL(2),
    ;
    public static ConveyCondition valueOf(int value) {
        if (value == 0) return NONE;
        if (value == 1) return NEED_LEGION;
        if (value == 2) return NEED_LEVEL;
        return null;
    }
    int number;
    ConveyCondition(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}