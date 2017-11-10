package commonality;
public enum ErrorCode {
    /** succeed */
    SUCCEED,
    /** fail */
    FAIL,
    /** 请求数据不是POST */
    REGISTER_NOT_POST,
    /** 请求的数据不全 */
    REGISTER_INVALID_DATA,
    /** 连接数据库失败 {0} */
    REGISTER_CON_SQL_FAIL,
    /** 验证信息失败 path : {0}  key : {1} */
    REGISTER_KEY_INVALID,
    /** 执行SQL语句失败 {0}   {1} */
    REGISTER_EXEC_SQL_FAIL,
    /** 请求数据不是POST */
    REQUEST_NOT_POST,
    /** 执行SQL语句失败 {0}   {1} */
    REQUEST_EXEC_SQL_FAIL,
    /** 不存在有效的服务器 */
    REQUEST_NOT_EXIST_SERVER,
    /** 请求数据不是POST {0} */
    SERVER_NOT_POST,
    /** 消息数据太短 数据长度 {0} */
    SERVER_TOO_SHORT,
    /** 消息格式不对 */
    SERVER_FORMAT_ERROR,
    /** session验证失败 {0} */
    SERVER_SESSION_FAIL,
    /** 服务器当前未开启或者服务器不存在 */
    SERVER_NOT_ONLINE,
    /** 未知错误 {0}:{1} - {2} */
    SERVER_FAIL,
    /** 请求数据不是POST {0} */
    CLIENT_NOT_POST,
    /** 消息数据太短 数据长度 {0} */
    CLIENT_TOO_SHORT,
    /** 消息格式不对 */
    CLIENT_FORMAT_ERROR,
    /** session验证失败 {0} */
    CLIENT_SESSION_FAIL,
    /** 未知错误 {0}:{1} - {2} */
    CLIENT_FAIL,
    /** 请求数据不是POST {0} */
    CENTER_NOT_POST,
    /** 消息数据太短 数据长度 {0} */
    CENTER_TOO_SHORT,
    /** 消息格式不对 */
    CENTER_FORMAT_ERROR,
    /** session验证失败 {0} */
    CENTER_SESSION_FAIL,
    /** 未知错误 {0}:{1} - {2} */
    CENTER_FAIL,
    /** 成功 */
    SHARE_SUCCEED,
    /** 失败 */
    SHARE_FAIL,
    /** session验证失败 */
    SHARE_SESSION_FAIL,
    /** shareserver返回正常的错误码 */
    SHARE_SEND_PROMPT,
}