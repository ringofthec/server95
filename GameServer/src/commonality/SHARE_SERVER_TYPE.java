package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 分流服务器ID类型 */
public enum SHARE_SERVER_TYPE {
    /** (0)全局服 */
    GLO_BAL(0),
    /** (1)军团子服 */
    LEGION(1),
    ;
    public static SHARE_SERVER_TYPE valueOf(int value) {
        if (value == 0) return GLO_BAL;
        if (value == 1) return LEGION;
        return null;
    }
    int number;
    SHARE_SERVER_TYPE(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}