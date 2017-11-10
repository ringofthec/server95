package commonality;
/** ss/gl to gs */
public class HttpToGameHeadRe {
    /**  */
    public Integer code;
    /**  */
    public String codeMessage;
    public HttpToGameHeadRe() {}
    public HttpToGameHeadRe( Integer code, String codeMessage)
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