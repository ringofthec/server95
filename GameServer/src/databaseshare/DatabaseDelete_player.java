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
//================任务id===========================
@SuppressWarnings("unused")
public class DatabaseDelete_player implements DatabaseTableDataBase<DatabaseDelete_player>,Serializable {
    public static final String TableName = "delete_player";
    /**  */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseDelete_player __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseDelete_player();
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
    public DatabaseDelete_player diff()
    {
        DatabaseDelete_player ret = new DatabaseDelete_player();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        return ret;
    }
    @Override
    public void set(DatabaseDelete_player value) {
        this.player_id = value.player_id;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "delete_player";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "player_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return player_id;
    }
}