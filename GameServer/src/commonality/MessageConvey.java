package commonality;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
@SuppressWarnings("unused")
/** 服务器消息转发 */
public class MessageConvey {
    /** 消息转发参数关键字type */
    public static final String type = "type";
    /** id */
    public static final String id = "id";
    /** 转发前提条件 */
    public static final String condition = "condition";
    /** 转发条件参数 */
    public static final String conditionargs = "conditionargs";
    /** 服务器类型 */
    public static final String servertype = "servertype";
    /** sharserver类型 */
    public static final String ShareServerType = "fewafaewt6ya23w6165";
    /** gamerserver类型 */
    public static final String GameServerType = "t56j3io25906u2395u";
    /** MessageConvey 构造函数 */
    static { 
    }
    /** 服务器中转消息类型 */
    public static enum ConveyMessageType {
        /** (0)无效类型 */
        NONE(0),
        /** (1)发送给指定UID的Game Server */
        TO_SERVER(1),
        /** (2)发送给指定PlayerId的Player的Game Server */
        TO_PLAYER_PLAYERID(2),
        /** (3)发送给指定PlayerId的Player 服务器直接转发 自己不处理 */
        TO_PLAYER_PLAYERID_CLIENT(3),
        /** (4)发送给所有Game Server */
        TO_ALL_SERVER(4),
        /** (5)发送给所有玩家 服务器收到直接转发给所有客户端 */
        TO_ALL_PLAYER(5),
        /** (6)类型数量 */
        COUNT(6),
        ;
        public static ConveyMessageType valueOf(int value) {
            if (value == 0) return NONE;
            if (value == 1) return TO_SERVER;
            if (value == 2) return TO_PLAYER_PLAYERID;
            if (value == 3) return TO_PLAYER_PLAYERID_CLIENT;
            if (value == 4) return TO_ALL_SERVER;
            if (value == 5) return TO_ALL_PLAYER;
            if (value == 6) return COUNT;
            return null;
        }
        int number;
        ConveyMessageType(int number) { 
            this.number = number; 
        }
        public final int Number() { 
            return this.number; 
        }
    }
}