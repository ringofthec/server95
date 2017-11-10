package commonality;
/** 游戏服务器发给分流服务器的包头 */
public class GameToShareHead {
    /**  */
    public Integer type;
    /**  */
    public String id;
    /**  */
    public String session;
    public GameToShareHead() {}
    public GameToShareHead( Integer type, String id, String session)
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