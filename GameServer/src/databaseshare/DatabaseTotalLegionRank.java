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
public class DatabaseTotalLegionRank implements DatabaseTableDataBase<DatabaseTotalLegionRank>,Serializable {
    public static final String TableName = "totalLegionRank";
    /**  */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer id = null;
    public Integer getId() {return id;}
    /** 军团ID */
    @DatabaseFieldAttribute(fieldName = "legion_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer legion_id = null;
    public Integer getLegion_id() {return legion_id;}
    /** 军团等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 军团名 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 军团贡献 */
    @DatabaseFieldAttribute(fieldName = "total_contribution",fieldType = Integer.class,arrayType = Byte.class)
    public Integer total_contribution = null;
    public Integer getTotal_contribution() {return total_contribution;}
    /** 军团人数 */
    @DatabaseFieldAttribute(fieldName = "cur_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer cur_num = null;
    public Integer getCur_num() {return cur_num;}
    /** 军团长国别 */
    @DatabaseFieldAttribute(fieldName = "nation",fieldType = String.class,arrayType = Byte.class)
    public String nation = null;
    public String getNation() {return nation;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseTotalLegionRank __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseTotalLegionRank();
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
    public DatabaseTotalLegionRank diff()
    {
        DatabaseTotalLegionRank ret = new DatabaseTotalLegionRank();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.legion_id != null && (__self == null || !this.legion_id.equals(__self.legion_id)))
            ret.legion_id = this.legion_id;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.total_contribution != null && (__self == null || !this.total_contribution.equals(__self.total_contribution)))
            ret.total_contribution = this.total_contribution;
        if (this.cur_num != null && (__self == null || !this.cur_num.equals(__self.cur_num)))
            ret.cur_num = this.cur_num;
        if (this.nation != null && (__self == null || !this.nation.equals(__self.nation)))
            ret.nation = this.nation;
        return ret;
    }
    @Override
    public void set(DatabaseTotalLegionRank value) {
        this.id = value.id;
        this.legion_id = value.legion_id;
        this.level = value.level;
        this.name = value.name;
        this.total_contribution = value.total_contribution;
        this.cur_num = value.cur_num;
        this.nation = value.nation;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.legion_id != null) return false;
        if (this.level != null) return false;
        if (this.name != null) return false;
        if (this.total_contribution != null) return false;
        if (this.cur_num != null) return false;
        if (this.nation != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.legion_id = null;
        this.level = null;
        this.name = null;
        this.total_contribution = null;
        this.cur_num = null;
        this.nation = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.legion_id != null)
            ret += ("legion_id = " + this.legion_id.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.total_contribution != null)
            ret += ("total_contribution = " + this.total_contribution.toString() + " ");
        if (this.cur_num != null)
            ret += ("cur_num = " + this.cur_num.toString() + " ");
        if (this.nation != null)
            ret += ("nation = " + this.nation.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "totalLegionRank";
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