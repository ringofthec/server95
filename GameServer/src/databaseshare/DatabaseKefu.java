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
//================唯一id===========================
@SuppressWarnings("unused")
public class DatabaseKefu implements DatabaseTableDataBase<DatabaseKefu>,Serializable {
    public static final String TableName = "kefu";
    /** 唯一id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家名字 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 消息 */
    @DatabaseFieldAttribute(fieldName = "msg",fieldType = String.class,arrayType = Byte.class)
    public String msg = null;
    public String getMsg() {return msg;}
    /** 状态  */
    @DatabaseFieldAttribute(fieldName = "status",fieldType = Integer.class,arrayType = Byte.class)
    public Integer status = null;
    public Integer getStatus() {return status;}
    /** 标志 0 玩家 1系统 */
    @DatabaseFieldAttribute(fieldName = "flag",fieldType = Integer.class,arrayType = Byte.class)
    public Integer flag = null;
    public Integer getFlag() {return flag;}
    /** 消息 */
    @DatabaseFieldAttribute(fieldName = "retmsg",fieldType = String.class,arrayType = Byte.class)
    public String retmsg = null;
    public String getRetmsg() {return retmsg;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseKefu __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseKefu();
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
    public DatabaseKefu diff()
    {
        DatabaseKefu ret = new DatabaseKefu();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.msg != null && (__self == null || !this.msg.equals(__self.msg)))
            ret.msg = this.msg;
        if (this.status != null && (__self == null || !this.status.equals(__self.status)))
            ret.status = this.status;
        if (this.flag != null && (__self == null || !this.flag.equals(__self.flag)))
            ret.flag = this.flag;
        if (this.retmsg != null && (__self == null || !this.retmsg.equals(__self.retmsg)))
            ret.retmsg = this.retmsg;
        return ret;
    }
    @Override
    public void set(DatabaseKefu value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.name = value.name;
        this.create_time = value.create_time;
        this.msg = value.msg;
        this.status = value.status;
        this.flag = value.flag;
        this.retmsg = value.retmsg;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.name != null) return false;
        if (this.create_time != null) return false;
        if (this.msg != null) return false;
        if (this.status != null) return false;
        if (this.flag != null) return false;
        if (this.retmsg != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.name = null;
        this.create_time = null;
        this.msg = null;
        this.status = null;
        this.flag = null;
        this.retmsg = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.msg != null)
            ret += ("msg = " + this.msg.toString() + " ");
        if (this.status != null)
            ret += ("status = " + this.status.toString() + " ");
        if (this.flag != null)
            ret += ("flag = " + this.flag.toString() + " ");
        if (this.retmsg != null)
            ret += ("retmsg = " + this.retmsg.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "kefu";
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