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
//================gamedb汇总表===========================
@SuppressWarnings("unused")
public class DatabaseGamedb implements DatabaseTableDataBase<DatabaseGamedb>,Serializable {
    public static final String TableName = "gamedb";
    /** 数据库ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer id = null;
    public Integer getId() {return id;}
    /** db名字 */
    @DatabaseFieldAttribute(fieldName = "db_name",fieldType = String.class,arrayType = Byte.class)
    public String db_name = null;
    public String getDb_name() {return db_name;}
    /** db ip */
    @DatabaseFieldAttribute(fieldName = "address",fieldType = String.class,arrayType = Byte.class)
    public String address = null;
    public String getAddress() {return address;}
    /** db 端口 */
    @DatabaseFieldAttribute(fieldName = "port",fieldType = String.class,arrayType = Byte.class)
    public String port = null;
    public String getPort() {return port;}
    /** db 链接参数 */
    @DatabaseFieldAttribute(fieldName = "parameters",fieldType = String.class,arrayType = Byte.class)
    public String parameters = null;
    public String getParameters() {return parameters;}
    /** db 用户名 */
    @DatabaseFieldAttribute(fieldName = "username",fieldType = String.class,arrayType = Byte.class)
    public String username = null;
    public String getUsername() {return username;}
    /** db 密码 */
    @DatabaseFieldAttribute(fieldName = "password",fieldType = String.class,arrayType = Byte.class)
    public String password = null;
    public String getPassword() {return password;}
    /** db 负载 */
    @DatabaseFieldAttribute(fieldName = "playercount",fieldType = Integer.class,arrayType = Byte.class)
    public Integer playercount = null;
    public Integer getPlayercount() {return playercount;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseGamedb __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseGamedb();
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
    public DatabaseGamedb diff()
    {
        DatabaseGamedb ret = new DatabaseGamedb();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.db_name != null && (__self == null || !this.db_name.equals(__self.db_name)))
            ret.db_name = this.db_name;
        if (this.address != null && (__self == null || !this.address.equals(__self.address)))
            ret.address = this.address;
        if (this.port != null && (__self == null || !this.port.equals(__self.port)))
            ret.port = this.port;
        if (this.parameters != null && (__self == null || !this.parameters.equals(__self.parameters)))
            ret.parameters = this.parameters;
        if (this.username != null && (__self == null || !this.username.equals(__self.username)))
            ret.username = this.username;
        if (this.password != null && (__self == null || !this.password.equals(__self.password)))
            ret.password = this.password;
        if (this.playercount != null && (__self == null || !this.playercount.equals(__self.playercount)))
            ret.playercount = this.playercount;
        return ret;
    }
    @Override
    public void set(DatabaseGamedb value) {
        this.id = value.id;
        this.db_name = value.db_name;
        this.address = value.address;
        this.port = value.port;
        this.parameters = value.parameters;
        this.username = value.username;
        this.password = value.password;
        this.playercount = value.playercount;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.db_name != null) return false;
        if (this.address != null) return false;
        if (this.port != null) return false;
        if (this.parameters != null) return false;
        if (this.username != null) return false;
        if (this.password != null) return false;
        if (this.playercount != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.db_name = null;
        this.address = null;
        this.port = null;
        this.parameters = null;
        this.username = null;
        this.password = null;
        this.playercount = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.db_name != null)
            ret += ("db_name = " + this.db_name.toString() + " ");
        if (this.address != null)
            ret += ("address = " + this.address.toString() + " ");
        if (this.port != null)
            ret += ("port = " + this.port.toString() + " ");
        if (this.parameters != null)
            ret += ("parameters = " + this.parameters.toString() + " ");
        if (this.username != null)
            ret += ("username = " + this.username.toString() + " ");
        if (this.password != null)
            ret += ("password = " + this.password.toString() + " ");
        if (this.playercount != null)
            ret += ("playercount = " + this.playercount.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "gamedb";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return id;
    }
}