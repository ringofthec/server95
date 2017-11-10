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
//================货币类型===========================
@SuppressWarnings("unused")
public class DatabaseItem implements DatabaseTableDataBase<DatabaseItem>,Serializable {
    public static final String TableName = "item";
    /** 物品ID */
    @DatabaseFieldAttribute(fieldName = "item_id",fieldType = Long.class,arrayType = Byte.class)
    public Long item_id = null;
    public Long getItem_id() {return item_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 物品表ID（table表ID） */
    @DatabaseFieldAttribute(fieldName = "table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer table_id = null;
    public Integer getTable_id() {return table_id;}
    /** 数量 */
    @DatabaseFieldAttribute(fieldName = "number",fieldType = Long.class,arrayType = Byte.class)
    public Long number = null;
    public Long getNumber() {return number;}
    /** 装备等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseItem __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseItem();
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
    public DatabaseItem diff()
    {
        DatabaseItem ret = new DatabaseItem();
        if (this.item_id != null && (__self == null || !this.item_id.equals(__self.item_id)))
            ret.item_id = this.item_id;
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
    public void set(DatabaseItem value) {
        this.item_id = value.item_id;
        this.player_id = value.player_id;
        this.table_id = value.table_id;
        this.number = value.number;
        this.level = value.level;
    }
    @Override
    public boolean isEmpty() {
        if (this.item_id != null) return false;
        if (this.player_id != null) return false;
        if (this.table_id != null) return false;
        if (this.number != null) return false;
        if (this.level != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.item_id = null;
        this.player_id = null;
        this.table_id = null;
        this.number = null;
        this.level = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.item_id != null)
            ret += ("item_id = " + this.item_id.toString() + " ");
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
        return "item";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "item_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return item_id;
    }
}