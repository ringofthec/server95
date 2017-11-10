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
//================科技表===========================
@SuppressWarnings("unused")
public class DatabaseTech implements DatabaseTableDataBase<DatabaseTech>,Serializable {
    public static final String TableName = "tech";
    /** 科技ID */
    @DatabaseFieldAttribute(fieldName = "tech_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer tech_id = null;
    public Integer getTech_id() {return tech_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** table表ID,例如：炮弹id，导弹id */
    @DatabaseFieldAttribute(fieldName = "table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer table_id = null;
    public Integer getTable_id() {return table_id;}
    /** 等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 默认关闭 */
    @DatabaseFieldAttribute(fieldName = "is_open",fieldType = Integer.class,arrayType = Byte.class)
    public Integer is_open = null;
    public Integer getIs_open() {return is_open;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseTech __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseTech();
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
    public DatabaseTech diff()
    {
        DatabaseTech ret = new DatabaseTech();
        if (this.tech_id != null && (__self == null || !this.tech_id.equals(__self.tech_id)))
            ret.tech_id = this.tech_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.table_id != null && (__self == null || !this.table_id.equals(__self.table_id)))
            ret.table_id = this.table_id;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.is_open != null && (__self == null || !this.is_open.equals(__self.is_open)))
            ret.is_open = this.is_open;
        return ret;
    }
    @Override
    public void set(DatabaseTech value) {
        this.tech_id = value.tech_id;
        this.player_id = value.player_id;
        this.table_id = value.table_id;
        this.level = value.level;
        this.is_open = value.is_open;
    }
    @Override
    public boolean isEmpty() {
        if (this.tech_id != null) return false;
        if (this.player_id != null) return false;
        if (this.table_id != null) return false;
        if (this.level != null) return false;
        if (this.is_open != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.tech_id = null;
        this.player_id = null;
        this.table_id = null;
        this.level = null;
        this.is_open = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.tech_id != null)
            ret += ("tech_id = " + this.tech_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.table_id != null)
            ret += ("table_id = " + this.table_id.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.is_open != null)
            ret += ("is_open = " + this.is_open.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "tech";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "tech_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return tech_id;
    }
}