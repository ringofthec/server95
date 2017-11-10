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
//================shop表id===========================
@SuppressWarnings("unused")
public class DatabaseShop implements DatabaseTableDataBase<DatabaseShop>,Serializable {
    public static final String TableName = "shop";
    /** shop表id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer id = null;
    public Integer getId() {return id;}
    /** 剩余 */
    @DatabaseFieldAttribute(fieldName = "count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer count = null;
    public Integer getCount() {return count;}
    /** 总量 */
    @DatabaseFieldAttribute(fieldName = "total",fieldType = Integer.class,arrayType = Byte.class)
    public Integer total = null;
    public Integer getTotal() {return total;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseShop __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseShop();
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
    public DatabaseShop diff()
    {
        DatabaseShop ret = new DatabaseShop();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.count != null && (__self == null || !this.count.equals(__self.count)))
            ret.count = this.count;
        if (this.total != null && (__self == null || !this.total.equals(__self.total)))
            ret.total = this.total;
        return ret;
    }
    @Override
    public void set(DatabaseShop value) {
        this.id = value.id;
        this.count = value.count;
        this.total = value.total;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.count != null) return false;
        if (this.total != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.count = null;
        this.total = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.count != null)
            ret += ("count = " + this.count.toString() + " ");
        if (this.total != null)
            ret += ("total = " + this.total.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "shop";
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