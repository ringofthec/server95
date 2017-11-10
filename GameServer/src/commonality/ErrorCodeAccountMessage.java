package commonality;
import java.util.HashMap;
import com.commons.util.string;
public class ErrorCodeAccountMessage {
    private static HashMap<ErrorCodeAccount,String> messages = new HashMap<>();
    private static boolean _initialize = false;
    public static String getMessage(ErrorCodeAccount code,Object... args)
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
        messages.put(ErrorCodeAccount.SUCCEED,"成功");
        messages.put(ErrorCodeAccount.FAIL,"未知错误");
        messages.put(ErrorCodeAccount.ACCOUNT_INVALID,"无效的邮箱地址");
        messages.put(ErrorCodeAccount.PASSWORD_INVALID,"无效的密码密码长度为6-16个英文字母和数字");
        messages.put(ErrorCodeAccount.USER_EXIST,"用户名已存在");
        messages.put(ErrorCodeAccount.APP_NOT_EXIST,"AppID不存在或者AppKey错误");
        messages.put(ErrorCodeAccount.USER_NOT_EXIST,"用户名不存在");
        messages.put(ErrorCodeAccount.PASSWORD_FAIL,"密码错误");
    }
}