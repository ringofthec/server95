// 本文件是自动生成，不要手动修改
package database;
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
//================兵种表===========================
@SuppressWarnings("unused")
public class DatabaseCorps implements DatabaseTableDataBase<DatabaseCorps>,Serializable {
    public static final String TableName = "corps";
    /** 士兵ID */
    @DatabaseFieldAttribute(fieldName = "corps_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer corps_id = null;
    public Integer getCorps_id() {return corps_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** table表ID */
    @DatabaseFieldAttribute(fieldName = "table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer table_id = null;
    public Integer getTable_id() {return table_id;}
    /** 数量 */
    @DatabaseFieldAttribute(fieldName = "number",fieldType = Integer.class,arrayType = Byte.class)
    public Integer number = null;
    public Integer getNumber() {return number;}
    /** 等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseCorps __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseCorps();
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
    public DatabaseCorps diff()
    {
        DatabaseCorps ret = new DatabaseCorps();
        if (this.corps_id != null && (__self == null || !this.corps_id.equals(__self.corps_id)))
            ret.corps_id = this.corps_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.table_id != null && (__self == null || !this.table_id.equals(__self.table_id)))
            ret.table_id = this.table_id;
        if (this.number != null && (__self == null || !this.number.equals(__self.number)))
            ret.number = this.number;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        return ret;
    }
    @Override
    public void set(DatabaseCorps value) {
        this.corps_id = value.corps_id;
        this.player_id = value.player_id;
        this.table_id = value.table_id;
        this.number = value.number;
        this.level = value.level;
    }
    @Override
    public boolean isEmpty() {
        if (this.corps_id != null) return false;
        if (this.player_id != null) return false;
        if (this.table_id != null) return false;
        if (this.number != null) return false;
        if (this.level != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.corps_id = null;
        this.player_id = null;
        this.table_id = null;
        this.number = null;
        this.level = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.corps_id != null)
            ret += ("corps_id = " + this.corps_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.table_id != null)
            ret += ("table_id = " + this.table_id.toString() + " ");
        if (this.number != null)
            ret += ("number = " + this.number.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "corps";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "corps_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return corps_id;
    }
}