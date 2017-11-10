package commonality;
/** Manager返回gamedb的链接信息 */
public class QueryDBInfoResult {
    /** 返回错误码 */
    public Integer code;
    /** 返回错误描述 */
    public String message;
    /** 数据库id */
    public Integer db_id;
    /** 数据库名称 */
    public String db_name;
    /** 数据库ip */
    public String address;
    /** 数据库端口 */
    public Integer port;
    /** 数据库链接参数 */
    public String parameters;
    /** 用户名 */
    public String username;
    /** 密码 */
    public String password;
    public QueryDBInfoResult() {}
    public QueryDBInfoResult( Integer code, String message, Integer db_id, String db_name, String address, Integer port, String parameters, String username, String password)
    {
        this.code = code;
        this.message = message;
        this.db_id = db_id;
        this.db_name = db_name;
        this.address = address;
        this.port = port;
        this.parameters = parameters;
        this.username = username;
        this.password = password;
    }
    public Integer getCode()
    {
        return this.code;
    }
    public String getMessage()
    {
        return this.message;
    }
    public Integer getDb_id()
    {
        return this.db_id;
    }
    public String getDb_name()
    {
        return this.db_name;
    }
    public String getAddress()
    {
        return this.address;
    }
    public Integer getPort()
    {
        return this.port;
    }
    public String getParameters()
    {
        return this.parameters;
    }
    public String getUsername()
    {
        return this.username;
    }
    public String getPassword()
    {
        return this.password;
    }
}