package commonality;
public enum ErrorCodeAccount {
    /** 成功 */
    SUCCEED,
    /** 未知错误 */
    FAIL,
    /** 无效的邮箱地址 */
    ACCOUNT_INVALID,
    /** 无效的密码密码长度为6-16个英文字母和数字 */
    PASSWORD_INVALID,
    /** 用户名已存在 */
    USER_EXIST,
    /** AppID不存在或者AppKey错误 */
    APP_NOT_EXIST,
    /** 用户名不存在 */
    USER_NOT_EXIST,
    /** 密码错误 */
    PASSWORD_FAIL,
}