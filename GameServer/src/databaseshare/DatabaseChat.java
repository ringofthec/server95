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
//================chat_id===========================
@SuppressWarnings("unused")
public class DatabaseChat implements DatabaseTableDataBase<DatabaseChat>,Serializable {
    public static final String TableName = "chat";
    /** chat_id */
    @DatabaseFieldAttribute(fieldName = "chat_id",fieldType = Long.class,arrayType = Byte.class)
    public Long chat_id = null;
    public Long getChat_id() {return chat_id;}
    /** 时间 */
    @DatabaseFieldAttribute(fieldName = "time",fieldType = Timestamp.class,arrayType = Byte.class)
    public Timestamp time = null;
    public Timestamp getTime() {return time;}
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家名称 */
    @DatabaseFieldAttribute(fieldName = "player_name",fieldType = String.class,arrayType = Byte.class)
    public String player_name = null;
    public String getPlayer_name() {return player_name;}
    /** 聊天内容 */
    @DatabaseFieldAttribute(fieldName = "content",fieldType = String.class,arrayType = Byte.class)
    public String content = null;
    public String getContent() {return content;}
    /** 聊天类型，0：世界聊天，1：军团聊天 */
    @DatabaseFieldAttribute(fieldName = "type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer type = null;
    public Integer getType() {return type;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseChat __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseChat();
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
    public DatabaseChat diff()
    {
        DatabaseChat ret = new DatabaseChat();
        if (this.chat_id != null && (__self == null || !this.chat_id.equals(__self.chat_id)))
            ret.chat_id = this.chat_id;
        if (this.time != null && (__self == null || !this.time.equals(__self.time)))
            ret.time = this.time;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.player_name != null && (__self == null || !this.player_name.equals(__self.player_name)))
            ret.player_name = this.player_name;
        if (this.content != null && (__self == null || !this.content.equals(__self.content)))
            ret.content = this.content;
        if (this.type != null && (__self == null || !this.type.equals(__self.type)))
            ret.type = this.type;
        return ret;
    }
    @Override
    public void set(DatabaseChat value) {
        this.chat_id = value.chat_id;
        this.time = value.time;
        this.player_id = value.player_id;
        this.player_name = value.player_name;
        this.content = value.content;
        this.type = value.type;
    }
    @Override
    public boolean isEmpty() {
        if (this.chat_id != null) return false;
        if (this.time != null) return false;
        if (this.player_id != null) return false;
        if (this.player_name != null) return false;
        if (this.content != null) return false;
        if (this.type != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.chat_id = null;
        this.time = null;
        this.player_id = null;
        this.player_name = null;
        this.content = null;
        this.type = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.chat_id != null)
            ret += ("chat_id = " + this.chat_id.toString() + " ");
        if (this.time != null)
            ret += ("time = " + this.time.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.player_name != null)
            ret += ("player_name = " + this.player_name.toString() + " ");
        if (this.content != null)
            ret += ("content = " + this.content.toString() + " ");
        if (this.type != null)
            ret += ("type = " + this.type.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "chat";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "chat_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return chat_id;
    }
}