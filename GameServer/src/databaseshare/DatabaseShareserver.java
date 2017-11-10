// 本文件是自动生成，不要手动修改
package databaseshare;
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
//================转发服务器注册表 转发服务器分流===========================
@SuppressWarnings("unused")
public class DatabaseShareserver implements DatabaseTableDataBase<DatabaseShareserver>,Serializable {
    public static final String TableName = "shareserver";
    /** 服务器UID md5(服务器磁盘路径 + 密码 + IP) */
    @DatabaseFieldAttribute(fieldName = "uid",fieldType = String.class,arrayType = Byte.class)
    public String uid = null;
    public String getUid() {return uid;}
    /** 服务器外网IP地址 */
    @DatabaseFieldAttribute(fieldName = "netip",fieldType = String.class,arrayType = Byte.class)
    public String netip = null;
    public String getNetip() {return netip;}
    /** 服务器内网IP地址 */
    @DatabaseFieldAttribute(fieldName = "ip",fieldType = String.class,arrayType = Byte.class)
    public String ip = null;
    public String getIp() {return ip;}
    /** 服务器HttpServer端口 */
    @DatabaseFieldAttribute(fieldName = "port",fieldType = Integer.class,arrayType = Byte.class)
    public Integer port = null;
    public Integer getPort() {return port;}
    /** 服务器操作session */
    @DatabaseFieldAttribute(fieldName = "session",fieldType = String.class,arrayType = Byte.class)
    public String session = null;
    public String getSession() {return session;}
    /** 当前服务器是否在线(不是时时的，客户端请求到此服务器会检测一次) */
    @DatabaseFieldAttribute(fieldName = "online",fieldType = Integer.class,arrayType = Byte.class)
    public Integer online = null;
    public Integer getOnline() {return online;}
    /** 服务器是否启动完成 */
    @DatabaseFieldAttribute(fieldName = "finshed",fieldType = Integer.class,arrayType = Byte.class)
    public Integer finshed = null;
    public Integer getFinshed() {return finshed;}
    /** 服务器启动时间 */
    @DatabaseFieldAttribute(fieldName = "starttime",fieldType = Timestamp.class,arrayType = Byte.class)
    public Timestamp starttime = null;
    public Timestamp getStarttime() {return starttime;}
    /** 服务器在线人数 */
    @DatabaseFieldAttribute(fieldName = "number",fieldType = Integer.class,arrayType = Byte.class)
    public Integer number = null;
    public Integer getNumber() {return number;}
    /** 服务器磁盘路径 */
    @DatabaseFieldAttribute(fieldName = "path",fieldType = String.class,arrayType = Byte.class)
    public String path = null;
    public String getPath() {return path;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseShareserver __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseShareserver();
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
    public DatabaseShareserver diff()
    {
        DatabaseShareserver ret = new DatabaseShareserver();
        if (this.uid != null && (__self == null || !this.uid.equals(__self.uid)))
            ret.uid = this.uid;
        if (this.netip != null && (__self == null || !this.netip.equals(__self.netip)))
            ret.netip = this.netip;
        if (this.ip != null && (__self == null || !this.ip.equals(__self.ip)))
            ret.ip = this.ip;
        if (this.port != null && (__self == null || !this.port.equals(__self.port)))
            ret.port = this.port;
        if (this.session != null && (__self == null || !this.session.equals(__self.session)))
            ret.session = this.session;
        if (this.online != null && (__self == null || !this.online.equals(__self.online)))
            ret.online = this.online;
        if (this.finshed != null && (__self == null || !this.finshed.equals(__self.finshed)))
            ret.finshed = this.finshed;
        if (this.starttime != null && (__self == null || !this.starttime.equals(__self.starttime)))
            ret.starttime = this.starttime;
        if (this.number != null && (__self == null || !this.number.equals(__self.number)))
            ret.number = this.number;
        if (this.path != null && (__self == null || !this.path.equals(__self.path)))
            ret.path = this.path;
        return ret;
    }
    @Override
    public void set(DatabaseShareserver value) {
        this.uid = value.uid;
        this.netip = value.netip;
        this.ip = value.ip;
        this.port = value.port;
        this.session = value.session;
        this.online = value.online;
        this.finshed = value.finshed;
        this.starttime = value.starttime;
        this.number = value.number;
        this.path = value.path;
    }
    @Override
    public boolean isEmpty() {
        if (this.uid != null) return false;
        if (this.netip != null) return false;
        if (this.ip != null) return false;
        if (this.port != null) return false;
        if (this.session != null) return false;
        if (this.online != null) return false;
        if (this.finshed != null) return false;
        if (this.starttime != null) return false;
        if (this.number != null) return false;
        if (this.path != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.uid = null;
        this.netip = null;
        this.ip = null;
        this.port = null;
        this.session = null;
        this.online = null;
        this.finshed = null;
        this.starttime = null;
        this.number = null;
        this.path = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.uid != null)
            ret += ("uid = " + this.uid.toString() + " ");
        if (this.netip != null)
            ret += ("netip = " + this.netip.toString() + " ");
        if (this.ip != null)
            ret += ("ip = " + this.ip.toString() + " ");
        if (this.port != null)
            ret += ("port = " + this.port.toString() + " ");
        if (this.session != null)
            ret += ("session = " + this.session.toString() + " ");
        if (this.online != null)
            ret += ("online = " + this.online.toString() + " ");
        if (this.finshed != null)
            ret += ("finshed = " + this.finshed.toString() + " ");
        if (this.starttime != null)
            ret += ("starttime = " + this.starttime.toString() + " ");
        if (this.number != null)
            ret += ("number = " + this.number.toString() + " ");
        if (this.path != null)
            ret += ("path = " + this.path.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "shareserver";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "uid";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return uid;
    }
}