package commonality;
/** http发送消息返回 */
public class ProcessMessageResult {
    /**  */
    public Integer code;
    /**  */
    public String message;
    /**  */
    public String messageResult;
    public ProcessMessageResult() {}
    public ProcessMessageResult( Integer code, String message, String messageResult)
    {
        this.code = code;
        this.message = message;
        this.messageResult = messageResult;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
    public String getMessageResult()
    {
        return this.messageResult;
    }
}