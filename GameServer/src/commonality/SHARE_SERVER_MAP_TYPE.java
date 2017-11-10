package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 分流服务器排行榜类型 */
public enum SHARE_SERVER_MAP_TYPE {
    /** (0)排行榜 */
    RANK(0),
    ;
    public static SHARE_SERVER_MAP_TYPE valueOf(int value) {
        if (value == 0) return RANK;
        return null;
    }
    int number;
    SHARE_SERVER_MAP_TYPE(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}