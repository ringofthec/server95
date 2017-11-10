package commonality;
import java.util.HashMap;
import com.commons.util.string;
public class ErrorCodeMessage {
    private static HashMap<ErrorCode,String> messages = new HashMap<>();
    private static boolean _initialize = false;
    public static String getMessage(ErrorCode code,Object... args)
    {
        initialize();
        if (messages.containsKey(code))
            return string.Format(messages.get(code),args);
        return "";
    }
    private static void initialize()
    {
        if (_initialize == true)
            return;
        _initialize = true;
        messages.clear();
        messages.put(ErrorCode.SUCCEED,"succeed");
        messages.put(ErrorCode.FAIL,"fail");
        messages.put(ErrorCode.REGISTER_NOT_POST,"请求数据不是POST");
        messages.put(ErrorCode.REGISTER_INVALID_DATA,"请求的数据不全");
        messages.put(ErrorCode.REGISTER_CON_SQL_FAIL,"连接数据库失败 {}");
        messages.put(ErrorCode.REGISTER_KEY_INVALID,"验证信息失败 path : {}  key : {}");
        messages.put(ErrorCode.REGISTER_EXEC_SQL_FAIL,"执行SQL语句失败 {}   {}");
        messages.put(ErrorCode.REQUEST_NOT_POST,"请求数据不是POST");
        messages.put(ErrorCode.REQUEST_EXEC_SQL_FAIL,"执行SQL语句失败 {}   {}");
        messages.put(ErrorCode.REQUEST_NOT_EXIST_SERVER,"不存在有效的服务器");
        messages.put(ErrorCode.SERVER_NOT_POST,"请求数据不是POST {}");
        messages.put(ErrorCode.SERVER_TOO_SHORT,"消息数据太短 数据长度 {}");
        messages.put(ErrorCode.SERVER_FORMAT_ERROR,"消息格式不对");
        messages.put(ErrorCode.SERVER_SESSION_FAIL,"session验证失败 {}");
        messages.put(ErrorCode.SERVER_NOT_ONLINE,"服务器当前未开启或者服务器不存在");
        messages.put(ErrorCode.SERVER_FAIL,"未知错误 {}:{} - {}");
        messages.put(ErrorCode.CLIENT_NOT_POST,"请求数据不是POST {}");
        messages.put(ErrorCode.CLIENT_TOO_SHORT,"消息数据太短 数据长度 {}");
        messages.put(ErrorCode.CLIENT_FORMAT_ERROR,"消息格式不对");
        messages.put(ErrorCode.CLIENT_SESSION_FAIL,"session验证失败 {}");
        messages.put(ErrorCode.CLIENT_FAIL,"未知错误 {}:{} - {}");
        messages.put(ErrorCode.CENTER_NOT_POST,"请求数据不是POST {}");
        messages.put(ErrorCode.CENTER_TOO_SHORT,"消息数据太短 数据长度 {}");
        messages.put(ErrorCode.CENTER_FORMAT_ERROR,"消息格式不对");
        messages.put(ErrorCode.CENTER_SESSION_FAIL,"session验证失败 {}");
        messages.put(ErrorCode.CENTER_FAIL,"未知错误 {}:{} - {}");
        messages.put(ErrorCode.SHARE_SUCCEED,"成功");
        messages.put(ErrorCode.SHARE_FAIL,"失败");
        messages.put(ErrorCode.SHARE_SESSION_FAIL,"session验证失败");
        messages.put(ErrorCode.SHARE_SEND_PROMPT,"shareserver返回正常的错误码");
    }
}