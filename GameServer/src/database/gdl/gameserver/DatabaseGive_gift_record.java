// 本文件是自动生成，不要手动修改
package database.gdl.gameserver;
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
//================推送消息===========================
@SuppressWarnings("unused")
public class DatabaseGive_gift_record implements DatabaseTableDataBase<DatabaseGive_gift_record>,Serializable {
    public static final String TableName = "give_gift_record";
    /** id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 类型 */
    @DatabaseFieldAttribute(fieldName = "type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer type = null;
    public Integer getType() {return type;}
    /** 时间 */
    @DatabaseFieldAttribute(fieldName = "re_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long re_time = null;
    public Long getRe_time() {return re_time;}
    /** 消息 */
    @DatabaseFieldAttribute(fieldName = "msg",fieldType = String.class,arrayType = Byte.class)
    public String msg = null;
    public String getMsg() {return msg;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseGive_gift_record __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseGive_gift_record();
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
    public DatabaseGive_gift_record diff()
    {
        DatabaseGive_gift_record ret = new DatabaseGive_gift_record();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.type != null && (__self == null || !this.type.equals(__self.type)))
            ret.type = this.type;
        if (this.re_time != null && (__self == null || !this.re_time.equals(__self.re_time)))
            ret.re_time = this.re_time;
        if (this.msg != null && (__self == null || !this.msg.equals(__self.msg)))
            ret.msg = this.msg;
        return ret;
    }
    @Override
    public void set(DatabaseGive_gift_record value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.type = value.type;
        this.re_time = value.re_time;
        this.msg = value.msg;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.type != null) return false;
        if (this.re_time != null) return false;
        if (this.msg != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.type = null;
        this.re_time = null;
        this.msg = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.type != null)
            ret += ("type = " + this.type.toString() + " ");
        if (this.re_time != null)
            ret += ("re_time = " + this.re_time.toString() + " ");
        if (this.msg != null)
            ret += ("msg = " + this.msg.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "give_gift_record";
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