package commonality;
/** 游戏服务器从中心服务器注册 */
public class GameServerRegister {
    /** 操作类型 注册和退出 */
    public Integer type;
    /** 服务器磁盘路径 */
    public String path;
    /** 注册密码 */
    public String key;
    /** 本机内网IP */
    public String ip;
    /** 本机外网IP */
    public String netip;
    public GameServerRegister() {}
    public GameServerRegister( Integer type, String path, String key, String ip, String netip)
    {
        this.type = type;
        this.path = path;
        this.key = key;
        this.ip = ip;
        this.netip = netip;
    }
    public Integer getType()
    {
        return this.type;
    }
    public String getPath()
    {
        return this.path;
    }
    public String getKey()
    {
        return this.key;
    }
    public String getIp()
    {
        return this.ip;
    }
    public String getNetip()
    {
        return this.netip;
    }
}