package commonality;
/** 请求gamedb的链接信息 */
public class QueryDBInfoRequest {
    /** 数据库名称 */
    public Integer db_id;
    public QueryDBInfoRequest() {}
    public QueryDBInfoRequest( Integer db_id)
    {
        this.db_id = db_id;
    }
    public Integer getDb_id()
    {
        return this.db_id;
    }
}