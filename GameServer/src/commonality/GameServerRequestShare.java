package commonality;
/** 游戏服务器请求分流服务器返回 */
public class GameServerRequestShare {
    /** id类型 */
    public Integer type;
    /** id */
    public String id;
    /** 服务器外网IP */
    public String ip;
    public GameServerRequestShare() {}
    public GameServerRequestShare( Integer type, String id, String ip)
    {
        this.type = type;
        this.id = id;
        this.ip = ip;
    }
    public Integer getType()
    {
        return this.type;
    }
    public String getId()
    {
        return this.id;
    }
    public String getIp()
    {
        return this.ip;
    }
}