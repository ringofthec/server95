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
//================玩家提交bug===========================
@SuppressWarnings("unused")
public class DatabaseBug_report implements DatabaseTableDataBase<DatabaseBug_report>,Serializable {
    public static final String TableName = "bug_report";
    /** bug id */
    @DatabaseFieldAttribute(fieldName = "bug_id",fieldType = Long.class,arrayType = Byte.class)
    public Long bug_id = null;
    public Long getBug_id() {return bug_id;}
    /** ip */
    @DatabaseFieldAttribute(fieldName = "ip",fieldType = String.class,arrayType = Byte.class)
    public String ip = null;
    public String getIp() {return ip;}
    /** bug发生时间 */
    @DatabaseFieldAttribute(fieldName = "bug_time",fieldType = Timestamp.class,arrayType = Byte.class)
    public Timestamp bug_time = null;
    public Timestamp getBug_time() {return bug_time;}
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家名字 */
    @DatabaseFieldAttribute(fieldName = "player_name",fieldType = String.class,arrayType = Byte.class)
    public String player_name = null;
    public String getPlayer_name() {return player_name;}
    /** bug内容 */
    @DatabaseFieldAttribute(fieldName = "bug",fieldType = String.class,arrayType = Byte.class)
    public String bug = null;
    public String getBug() {return bug;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseBug_report __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseBug_report();
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
    public DatabaseBug_report diff()
    {
        DatabaseBug_report ret = new DatabaseBug_report();
        if (this.bug_id != null && (__self == null || !this.bug_id.equals(__self.bug_id)))
            ret.bug_id = this.bug_id;
        if (this.ip != null && (__self == null || !this.ip.equals(__self.ip)))
            ret.ip = this.ip;
        if (this.bug_time != null && (__self == null || !this.bug_time.equals(__self.bug_time)))
            ret.bug_time = this.bug_time;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.player_name != null && (__self == null || !this.player_name.equals(__self.player_name)))
            ret.player_name = this.player_name;
        if (this.bug != null && (__self == null || !this.bug.equals(__self.bug)))
            ret.bug = this.bug;
        return ret;
    }
    @Override
    public void set(DatabaseBug_report value) {
        this.bug_id = value.bug_id;
        this.ip = value.ip;
        this.bug_time = value.bug_time;
        this.player_id = value.player_id;
        this.player_name = value.player_name;
        this.bug = value.bug;
    }
    @Override
    public boolean isEmpty() {
        if (this.bug_id != null) return false;
        if (this.ip != null) return false;
        if (this.bug_time != null) return false;
        if (this.player_id != null) return false;
        if (this.player_name != null) return false;
        if (this.bug != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.bug_id = null;
        this.ip = null;
        this.bug_time = null;
        this.player_id = null;
        this.player_name = null;
        this.bug = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.bug_id != null)
            ret += ("bug_id = " + this.bug_id.toString() + " ");
        if (this.ip != null)
            ret += ("ip = " + this.ip.toString() + " ");
        if (this.bug_time != null)
            ret += ("bug_time = " + this.bug_time.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.player_name != null)
            ret += ("player_name = " + this.player_name.toString() + " ");
        if (this.bug != null)
            ret += ("bug = " + this.bug.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "bug_report";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "bug_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return bug_id;
    }
}