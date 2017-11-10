package commonality;
/** 客户端请求服务器地址 */
public class ClientRequest {
    /** 客户端外网IP */
    public String ip;
    /** 是否需要公网IP地址 */
    public Integer needNetIp;
    /** 账号 */
    public String temp_uid;
    public ClientRequest() {}
    public ClientRequest( String ip, Integer needNetIp, String temp_uid)
    {
        this.ip = ip;
        this.needNetIp = needNetIp;
        this.temp_uid = temp_uid;
    }
    public String getIp()
    {
        return this.ip;
    }
    public Integer getNeedNetIp()
    {
        return this.needNetIp;
    }
    public String getTemp_uid()
    {
        return this.temp_uid;
    }
}