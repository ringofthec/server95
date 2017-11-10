package commonality;
/** 帐号注册结构体 */
public class AccountRegister {
    /** 帐号 邮箱 */
    public String user_name;
    /** 密码 */
    public String password;
    public AccountRegister() {}
    public AccountRegister( String user_name, String password)
    {
        this.user_name = user_name;
        this.password = password;
    }
    public String getUser_name()
    {
        return this.user_name;
    }
    public String getPassword()
    {
        return this.password;
    }
}