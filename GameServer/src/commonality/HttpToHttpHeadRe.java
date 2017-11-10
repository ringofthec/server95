package commonality;
/** ss/gl to ss/gl */
public class HttpToHttpHeadRe {
    /**  */
    public Integer code;
    /**  */
    public String codeMessage;
    public HttpToHttpHeadRe() {}
    public HttpToHttpHeadRe( Integer code, String codeMessage)
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