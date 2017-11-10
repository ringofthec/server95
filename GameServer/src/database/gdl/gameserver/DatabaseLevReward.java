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
//================奖励id===========================
@SuppressWarnings("unused")
public class DatabaseLevReward implements DatabaseTableDataBase<DatabaseLevReward>,Serializable {
    public static final String TableName = "LevReward";
    /** 奖励ID */
    @DatabaseFieldAttribute(fieldName = "levReward_id",fieldType = Long.class,arrayType = Byte.class)
    public Long levReward_id = null;
    public Long getLevReward_id() {return levReward_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 奖励表ID（table表ID） */
    @DatabaseFieldAttribute(fieldName = "table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer table_id = null;
    public Integer getTable_id() {return table_id;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseLevReward __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseLevReward();
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
    public DatabaseLevReward diff()
    {
        DatabaseLevReward ret = new DatabaseLevReward();
        if (this.levReward_id != null && (__self == null || !this.levReward_id.equals(__self.levReward_id)))
            ret.levReward_id = this.levReward_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.table_id != null && (__self == null || !this.table_id.equals(__self.table_id)))
            ret.table_id = this.table_id;
        return ret;
    }
    @Override
    public void set(DatabaseLevReward value) {
        this.levReward_id = value.levReward_id;
        this.player_id = value.player_id;
        this.table_id = value.table_id;
    }
    @Override
    public boolean isEmpty() {
        if (this.levReward_id != null) return false;
        if (this.player_id != null) return false;
        if (this.table_id != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.levReward_id = null;
        this.player_id = null;
        this.table_id = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.levReward_id != null)
            ret += ("levReward_id = " + this.levReward_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.table_id != null)
            ret += ("table_id = " + this.table_id.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "LevReward";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "levReward_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return levReward_id;
    }
}