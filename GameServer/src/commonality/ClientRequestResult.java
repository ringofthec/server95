package commonality;
/** 客户端请求服务器地址返回 */
public class ClientRequestResult {
    /** 返回错误码 */
    public Integer code;
    /** 错误描述 */
    public String message;
    /** 游戏服务器内网IP */
    public String gameIP;
    /** 游戏服务器外网IP */
    public String gameNetIP;
    /** 游戏服务器外网IP */
    public String managementNetIP;
    /** 游戏服务器端口 */
    public Integer gamePort;
    /** 重新request地址 */
    public String new_request_addr;
    public ClientRequestResult() {}
    public ClientRequestResult( Integer code, String message, String gameIP, String gameNetIP, String managementNetIP, Integer gamePort, String new_request_addr)
    {
        this.code = code;
        this.message = message;
        this.gameIP = gameIP;
        this.gameNetIP = gameNetIP;
        this.managementNetIP = managementNetIP;
        this.gamePort = gamePort;
        this.new_request_addr = new_request_addr;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
    public String getGameIP()
    {
        return this.gameIP;
    }
    public String getGameNetIP()
    {
        return this.gameNetIP;
    }
    public String getManagementNetIP()
    {
        return this.managementNetIP;
    }
    public Integer getGamePort()
    {
        return this.gamePort;
    }
    public String getNew_request_addr()
    {
        return this.new_request_addr;
    }
}