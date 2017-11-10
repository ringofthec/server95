package commonality;
/** gs to ss/gl */
public class GameToHttpHead {
    /**  */
    public Integer type;
    /**  */
    public String id;
    /**  */
    public String session;
    public GameToHttpHead() {}
    public GameToHttpHead( Integer type, String id, String session)
    {
        this.type = type;
        this.id = id;
        this.session = session;
    }
    public Integer getType()
    {
        return this.type;
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