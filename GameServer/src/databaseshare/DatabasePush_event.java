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
//================推送消息===========================
@SuppressWarnings("unused")
public class DatabasePush_event implements DatabaseTableDataBase<DatabasePush_event>,Serializable {
    public static final String TableName = "push_event";
    /** id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 消息类型 */
    @DatabaseFieldAttribute(fieldName = "event_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer event_type = null;
    public Integer getEvent_type() {return event_type;}
    /** 通知时间 */
    @DatabaseFieldAttribute(fieldName = "notify_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long notify_time = null;
    public Long getNotify_time() {return notify_time;}
    /** 推送id */
    @DatabaseFieldAttribute(fieldName = "registration_id",fieldType = String.class,arrayType = Byte.class)
    public String registration_id = null;
    public String getRegistration_id() {return registration_id;}
    /** 语言 */
    @DatabaseFieldAttribute(fieldName = "language",fieldType = Integer.class,arrayType = Byte.class)
    public Integer language = null;
    public Integer getLanguage() {return language;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePush_event __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePush_event();
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
    public DatabasePush_event diff()
    {
        DatabasePush_event ret = new DatabasePush_event();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.event_type != null && (__self == null || !this.event_type.equals(__self.event_type)))
            ret.event_type = this.event_type;
        if (this.notify_time != null && (__self == null || !this.notify_time.equals(__self.notify_time)))
            ret.notify_time = this.notify_time;
        if (this.registration_id != null && (__self == null || !this.registration_id.equals(__self.registration_id)))
            ret.registration_id = this.registration_id;
        if (this.language != null && (__self == null || !this.language.equals(__self.language)))
            ret.language = this.language;
        return ret;
    }
    @Override
    public void set(DatabasePush_event value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.event_type = value.event_type;
        this.notify_time = value.notify_time;
        this.registration_id = value.registration_id;
        this.language = value.language;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.event_type != null) return false;
        if (this.notify_time != null) return false;
        if (this.registration_id != null) return false;
        if (this.language != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.event_type = null;
        this.notify_time = null;
        this.registration_id = null;
        this.language = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.event_type != null)
            ret += ("event_type = " + this.event_type.toString() + " ");
        if (this.notify_time != null)
            ret += ("notify_time = " + this.notify_time.toString() + " ");
        if (this.registration_id != null)
            ret += ("registration_id = " + this.registration_id.toString() + " ");
        if (this.language != null)
            ret += ("language = " + this.language.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "push_event";
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