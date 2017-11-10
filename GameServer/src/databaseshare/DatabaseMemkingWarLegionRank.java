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
//===========================================
@SuppressWarnings("unused")
public class DatabaseMemkingWarLegionRank implements DatabaseTableDataBase<DatabaseMemkingWarLegionRank>,Serializable {
    public static final String TableName = "memkingWarLegionRank";
    /** 军团ID */
    @DatabaseFieldAttribute(fieldName = "legion_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer legion_id = null;
    public Integer getLegion_id() {return legion_id;}
    /** 军团名 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 积分 */
    @DatabaseFieldAttribute(fieldName = "point",fieldType = Integer.class,arrayType = Byte.class)
    public Integer point = null;
    public Integer getPoint() {return point;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseMemkingWarLegionRank __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseMemkingWarLegionRank();
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
    public DatabaseMemkingWarLegionRank diff()
    {
        DatabaseMemkingWarLegionRank ret = new DatabaseMemkingWarLegionRank();
        if (this.legion_id != null && (__self == null || !this.legion_id.equals(__self.legion_id)))
            ret.legion_id = this.legion_id;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.point != null && (__self == null || !this.point.equals(__self.point)))
            ret.point = this.point;
        return ret;
    }
    @Override
    public void set(DatabaseMemkingWarLegionRank value) {
        this.legion_id = value.legion_id;
        this.name = value.name;
        this.point = value.point;
    }
    @Override
    public boolean isEmpty() {
        if (this.legion_id != null) return false;
        if (this.name != null) return false;
        if (this.point != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.legion_id = null;
        this.name = null;
        this.point = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.legion_id != null)
            ret += ("legion_id = " + this.legion_id.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.point != null)
            ret += ("point = " + this.point.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "memkingWarLegionRank";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "legion_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return legion_id;
    }
}