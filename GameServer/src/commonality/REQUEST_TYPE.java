package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 请求类型 */
public enum REQUEST_TYPE {
    /** (0)客户端请求游戏服务器地址 */
    CLIENT_REQUEST_GAMESERVER(0),
    /** (1)游戏服务器注册到中心服务器 */
    GAMESERVER_REGISTER(1),
    /** (2)游戏服务器启动完成 */
    GAMESERVER_FINISHED(2),
    /** (3)游戏服务器关闭 */
    GAMESERVER_QUIT(3),
    /** (4)游戏服务器请求分流服务器地址 */
    GAMESERVER_REQUEST_SHARESERVER(4),
    /** (5)分流服务器注册到中心服务器 */
    SHARESERVER_REGISTER(5),
    /** (6)分流服务器启动完成 */
    SHARESERVER_FINISHED(6),
    /** (7)分流服务器关闭 */
    SHARESERVER_QUIT(7),
    ;
    public static REQUEST_TYPE valueOf(int value) {
        if (value == 0) return CLIENT_REQUEST_GAMESERVER;
        if (value == 1) return GAMESERVER_REGISTER;
        if (value == 2) return GAMESERVER_FINISHED;
        if (value == 3) return GAMESERVER_QUIT;
        if (value == 4) return GAMESERVER_REQUEST_SHARESERVER;
        if (value == 5) return SHARESERVER_REGISTER;
        if (value == 6) return SHARESERVER_FINISHED;
        if (value == 7) return SHARESERVER_QUIT;
        return null;
    }
    int number;
    REQUEST_TYPE(int number) { 
        this.number = number; 
    }
    public final int Number() { 
        return this.number; 
    }
}