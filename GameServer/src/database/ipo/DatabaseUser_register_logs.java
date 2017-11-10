// 本文件是自动生成，不要手动修改
package database.ipo;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.sql.Date;
import java.io.Serializable;
import com.commons.util.Utility;
import com.commons.database.DatabaseSimple;
import com.commons.database.DatabaseFieldAttribute;
import com.commons.database.DatabaseTableDataBase;
import com.commons.database.DatabaseFieldDataBase;
//================玩家注册日志===========================
@SuppressWarnings("unused")
public class DatabaseUser_register_logs implements DatabaseTableDataBase<DatabaseUser_register_logs>,Serializable {
    public static final String TableName = "user_register_logs";
    /** 项目id */
    @DatabaseFieldAttribute(fieldName = "project_id",fieldType = String.class,arrayType = Byte.class)
    public String project_id = null;
    public String getProject_id() {return project_id;}
    /** 服务器ID */
    @DatabaseFieldAttribute(fieldName = "server_id",fieldType = String.class,arrayType = Byte.class)
    public String server_id = null;
    public String getServer_id() {return server_id;}
    /** 注册设备标示 */
    @DatabaseFieldAttribute(fieldName = "open_udid",fieldType = String.class,arrayType = Byte.class)
    public String open_udid = null;
    public String getOpen_udid() {return open_udid;}
    /** 注册IP */
    @DatabaseFieldAttribute(fieldName = "ip",fieldType = String.class,arrayType = Byte.class)
    public String ip = null;
    public String getIp() {return ip;}
    /** 用户id */
    @DatabaseFieldAttribute(fieldName = "user_id",fieldType = Long.class,arrayType = Byte.class)
    public Long user_id = null;
    public Long getUser_id() {return user_id;}
    /** 账户名称 */
    @DatabaseFieldAttribute(fieldName = "user_name",fieldType = String.class,arrayType = Byte.class)
    public String user_name = null;
    public String getUser_name() {return user_name;}
    /** 注册时间点 */
    @DatabaseFieldAttribute(fieldName = "created_at",fieldType = Timestamp.class,arrayType = Long.class)
    public Long created_at = null;
    public Long getCreated_at() {return created_at;}
    /** 游戏版本 */
    @DatabaseFieldAttribute(fieldName = "app_id",fieldType = String.class,arrayType = Byte.class)
    public String app_id = null;
    public String getApp_id() {return app_id;}
    /** 系统版本 */
    @DatabaseFieldAttribute(fieldName = "os_version",fieldType = String.class,arrayType = Byte.class)
    public String os_version = null;
    public String getOs_version() {return os_version;}
    /** 设备类型 */
    @DatabaseFieldAttribute(fieldName = "device_name",fieldType = String.class,arrayType = Byte.class)
    public String device_name = null;
    public String getDevice_name() {return device_name;}
    /** 设备id */
    @DatabaseFieldAttribute(fieldName = "device_id",fieldType = String.class,arrayType = Byte.class)
    public String device_id = null;
    public String getDevice_id() {return device_id;}
    /** 设备id类型 */
    @DatabaseFieldAttribute(fieldName = "device_id_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer device_id_type = null;
    public Integer getDevice_id_type() {return device_id_type;}
    /** 本地语言 */
    @DatabaseFieldAttribute(fieldName = "locale",fieldType = String.class,arrayType = Byte.class)
    public String locale = null;
    public String getLocale() {return locale;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseUser_register_logs __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseUser_register_logs();
        __self.set(this);
    }
    /** DatabaseSimple对象  用于获取获得此对象的 数据库对象 */
    private DatabaseSimple __simple = null;
    @Override
    public void setDatabaseSimple(DatabaseSimple value) {
        __simple = value;
    }
    @Override
    public DatabaseSimple getDatabaseSimple() {
        return __simple;
    }
    @Override
    public void save() {
        if (__simple != null && getPrimaryKeyName() != null && getPrimaryKeyValue() != null)
            __simple.Update(this);
    }
    @Override
    public DatabaseUser_register_logs diff()
    {
        DatabaseUser_register_logs ret = new DatabaseUser_register_logs();
        if (this.project_id != null && (__self == null || !this.project_id.equals(__self.project_id)))
            ret.project_id = this.project_id;
        if (this.server_id != null && (__self == null || !this.server_id.equals(__self.server_id)))
            ret.server_id = this.server_id;
        if (this.open_udid != null && (__self == null || !this.open_udid.equals(__self.open_udid)))
            ret.open_udid = this.open_udid;
        if (this.ip != null && (__self == null || !this.ip.equals(__self.ip)))
            ret.ip = this.ip;
        if (this.user_id != null && (__self == null || !this.user_id.equals(__self.user_id)))
            ret.user_id = this.user_id;
        if (this.user_name != null && (__self == null || !this.user_name.equals(__self.user_name)))
            ret.user_name = this.user_name;
        if (this.created_at != null && (__self == null || !this.created_at.equals(__self.created_at)))
            ret.created_at = this.created_at;
        if (this.app_id != null && (__self == null || !this.app_id.equals(__self.app_id)))
            ret.app_id = this.app_id;
        if (this.os_version != null && (__self == null || !this.os_version.equals(__self.os_version)))
            ret.os_version = this.os_version;
        if (this.device_name != null && (__self == null || !this.device_name.equals(__self.device_name)))
            ret.device_name = this.device_name;
        if (this.device_id != null && (__self == null || !this.device_id.equals(__self.device_id)))
            ret.device_id = this.device_id;
        if (this.device_id_type != null && (__self == null || !this.device_id_type.equals(__self.device_id_type)))
            ret.device_id_type = this.device_id_type;
        if (this.locale != null && (__self == null || !this.locale.equals(__self.locale)))
            ret.locale = this.locale;
        return ret;
    }
    @Override
    public void set(DatabaseUser_register_logs value) {
        this.project_id = value.project_id;
        this.server_id = value.server_id;
        this.open_udid = value.open_udid;
        this.ip = value.ip;
        this.user_id = value.user_id;
        this.user_name = value.user_name;
        this.created_at = value.created_at;
        this.app_id = value.app_id;
        this.os_version = value.os_version;
        this.device_name = value.device_name;
        this.device_id = value.device_id;
        this.device_id_type = value.device_id_type;
        this.locale = value.locale;
    }
    @Override
    public boolean isEmpty() {
        if (this.project_id != null) return false;
        if (this.server_id != null) return false;
        if (this.open_udid != null) return false;
        if (this.ip != null) return false;
        if (this.user_id != null) return false;
        if (this.user_name != null) return false;
        if (this.created_at != null) return false;
        if (this.app_id != null) return false;
        if (this.os_version != null) return false;
        if (this.device_name != null) return false;
        if (this.device_id != null) return false;
        if (this.device_id_type != null) return false;
        if (this.locale != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.project_id = null;
        this.server_id = null;
        this.open_udid = null;
        this.ip = null;
        this.user_id = null;
        this.user_name = null;
        this.created_at = null;
        this.app_id = null;
        this.os_version = null;
        this.device_name = null;
        this.device_id = null;
        this.device_id_type = null;
        this.locale = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.project_id != null)
            ret += ("project_id = " + this.project_id.toString() + " ");
        if (this.server_id != null)
            ret += ("server_id = " + this.server_id.toString() + " ");
        if (this.open_udid != null)
            ret += ("open_udid = " + this.open_udid.toString() + " ");
        if (this.ip != null)
            ret += ("ip = " + this.ip.toString() + " ");
        if (this.user_id != null)
            ret += ("user_id = " + this.user_id.toString() + " ");
        if (this.user_name != null)
            ret += ("user_name = " + this.user_name.toString() + " ");
        if (this.created_at != null)
            ret += ("created_at = " + this.created_at.toString() + " ");
        if (this.app_id != null)
            ret += ("app_id = " + this.app_id.toString() + " ");
        if (this.os_version != null)
            ret += ("os_version = " + this.os_version.toString() + " ");
        if (this.device_name != null)
            ret += ("device_name = " + this.device_name.toString() + " ");
        if (this.device_id != null)
            ret += ("device_id = " + this.device_id.toString() + " ");
        if (this.device_id_type != null)
            ret += ("device_id_type = " + this.device_id_type.toString() + " ");
        if (this.locale != null)
            ret += ("locale = " + this.locale.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "user_register_logs";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return null;
    }
    @Override
    public Object getPrimaryKeyValue() {
        return null;
    }
}