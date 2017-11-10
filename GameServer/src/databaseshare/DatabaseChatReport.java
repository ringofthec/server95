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
//================举报玩家===========================
@SuppressWarnings("unused")
public class DatabaseChatReport implements DatabaseTableDataBase<DatabaseChatReport>,Serializable {
    public static final String TableName = "chatReport";
    /** 唯一ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** 时间 */
    @DatabaseFieldAttribute(fieldName = "time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long time = null;
    public Long getTime() {return time;}
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "player_name",fieldType = String.class,arrayType = Byte.class)
    public String player_name = null;
    public String getPlayer_name() {return player_name;}
    /** 被举报玩家id */
    @DatabaseFieldAttribute(fieldName = "reported_player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long reported_player_id = null;
    public Long getReported_player_id() {return reported_player_id;}
    /** 被举报玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "reported_player_name",fieldType = String.class,arrayType = Byte.class)
    public String reported_player_name = null;
    public String getReported_player_name() {return reported_player_name;}
    /** 当前有效累计举报 */
    @DatabaseFieldAttribute(fieldName = "cur_reported_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer cur_reported_count = null;
    public Integer getCur_reported_count() {return cur_reported_count;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseChatReport __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseChatReport();
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
    public DatabaseChatReport diff()
    {
        DatabaseChatReport ret = new DatabaseChatReport();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.time != null && (__self == null || !this.time.equals(__self.time)))
            ret.time = this.time;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.player_name != null && (__self == null || !this.player_name.equals(__self.player_name)))
            ret.player_name = this.player_name;
        if (this.reported_player_id != null && (__self == null || !this.reported_player_id.equals(__self.reported_player_id)))
            ret.reported_player_id = this.reported_player_id;
        if (this.reported_player_name != null && (__self == null || !this.reported_player_name.equals(__self.reported_player_name)))
            ret.reported_player_name = this.reported_player_name;
        if (this.cur_reported_count != null && (__self == null || !this.cur_reported_count.equals(__self.cur_reported_count)))
            ret.cur_reported_count = this.cur_reported_count;
        return ret;
    }
    @Override
    public void set(DatabaseChatReport value) {
        this.id = value.id;
        this.time = value.time;
        this.player_id = value.player_id;
        this.player_name = value.player_name;
        this.reported_player_id = value.reported_player_id;
        this.reported_player_name = value.reported_player_name;
        this.cur_reported_count = value.cur_reported_count;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.time != null) return false;
        if (this.player_id != null) return false;
        if (this.player_name != null) return false;
        if (this.reported_player_id != null) return false;
        if (this.reported_player_name != null) return false;
        if (this.cur_reported_count != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.time = null;
        this.player_id = null;
        this.player_name = null;
        this.reported_player_id = null;
        this.reported_player_name = null;
        this.cur_reported_count = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.time != null)
            ret += ("time = " + this.time.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.player_name != null)
            ret += ("player_name = " + this.player_name.toString() + " ");
        if (this.reported_player_id != null)
            ret += ("reported_player_id = " + this.reported_player_id.toString() + " ");
        if (this.reported_player_name != null)
            ret += ("reported_player_name = " + this.reported_player_name.toString() + " ");
        if (this.cur_reported_count != null)
            ret += ("cur_reported_count = " + this.cur_reported_count.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "chatReport";
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