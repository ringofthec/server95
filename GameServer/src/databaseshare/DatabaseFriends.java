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
//================好友列表===========================
@SuppressWarnings("unused")
public class DatabaseFriends implements DatabaseTableDataBase<DatabaseFriends>,Serializable {
    public static final String TableName = "friends";
    /** 自己id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 好友id */
    @DatabaseFieldAttribute(fieldName = "friend_id",fieldType = Long.class,arrayType = Byte.class)
    public Long friend_id = null;
    public Long getFriend_id() {return friend_id;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseFriends __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseFriends();
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
    public DatabaseFriends diff()
    {
        DatabaseFriends ret = new DatabaseFriends();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.friend_id != null && (__self == null || !this.friend_id.equals(__self.friend_id)))
            ret.friend_id = this.friend_id;
        return ret;
    }
    @Override
    public void set(DatabaseFriends value) {
        this.player_id = value.player_id;
        this.friend_id = value.friend_id;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.friend_id != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.friend_id = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.friend_id != null)
            ret += ("friend_id = " + this.friend_id.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "friends";
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