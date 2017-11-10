package commonality;
/** ss/gl to ss/gl */
public class HttpToHttpHead {
    /**  */
    public String id;
    /**  */
    public String session;
    public HttpToHttpHead() {}
    public HttpToHttpHead( String id, String session)
    {
        this.id = id;
        this.session = session;
    }
    public String getId()
    {
        return this.id;
    }
    public String getSession()
    {
        return this.session;
    }
}