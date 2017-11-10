package commonality;
/** 分流服务器返给游戏服务器的包头 */
public class ShareToGameHead {
    /**  */
    public Integer code;
    /**  */
    public String codeMessage;
    public ShareToGameHead() {}
    public ShareToGameHead( Integer code, String codeMessage)
    {
        this.code = code;
        this.codeMessage = codeMessage;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getCodeMessage()
    {
        return this.codeMessage;
    }
}