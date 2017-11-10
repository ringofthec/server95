package commonality;
/** 帐号系统返回 */
public class AccountResult {
    /** 返回编码 */
    public Integer code;
    /** 返回描述 */
    public String message;
    public AccountResult() {}
    public AccountResult( Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
}