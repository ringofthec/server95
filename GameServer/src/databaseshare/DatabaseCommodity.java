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
//================商品id===========================
@SuppressWarnings("unused")
public class DatabaseCommodity implements DatabaseTableDataBase<DatabaseCommodity>,Serializable {
    public static final String TableName = "commodity";
    /** 物品ID */
    @DatabaseFieldAttribute(fieldName = "commodity_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer commodity_id = null;
    public Integer getCommodity_id() {return commodity_id;}
    /** commodity表的tableID */
    @DatabaseFieldAttribute(fieldName = "commodity_table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer commodity_table_id = null;
    public Integer getCommodity_table_id() {return commodity_table_id;}
    /** 物品表ID（table表ID） */
    @DatabaseFieldAttribute(fieldName = "item_table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer item_table_id = null;
    public Integer getItem_table_id() {return item_table_id;}
    /** 当前全服玩家购买总数 */
    @DatabaseFieldAttribute(fieldName = "total_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer total_num = null;
    public Integer getTotal_num() {return total_num;}
    /** 更新时间 */
    @DatabaseFieldAttribute(fieldName = "updatetime",fieldType = Long.class,arrayType = Byte.class)
    public Long updatetime = null;
    public Long getUpdatetime() {return updatetime;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseCommodity __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseCommodity();
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
    public DatabaseCommodity diff()
    {
        DatabaseCommodity ret = new DatabaseCommodity();
        if (this.commodity_id != null && (__self == null || !this.commodity_id.equals(__self.commodity_id)))
            ret.commodity_id = this.commodity_id;
        if (this.commodity_table_id != null && (__self == null || !this.commodity_table_id.equals(__self.commodity_table_id)))
            ret.commodity_table_id = this.commodity_table_id;
        if (this.item_table_id != null && (__self == null || !this.item_table_id.equals(__self.item_table_id)))
            ret.item_table_id = this.item_table_id;
        if (this.total_num != null && (__self == null || !this.total_num.equals(__self.total_num)))
            ret.total_num = this.total_num;
        if (this.updatetime != null && (__self == null || !this.updatetime.equals(__self.updatetime)))
            ret.updatetime = this.updatetime;
        return ret;
    }
    @Override
    public void set(DatabaseCommodity value) {
        this.commodity_id = value.commodity_id;
        this.commodity_table_id = value.commodity_table_id;
        this.item_table_id = value.item_table_id;
        this.total_num = value.total_num;
        this.updatetime = value.updatetime;
    }
    @Override
    public boolean isEmpty() {
        if (this.commodity_id != null) return false;
        if (this.commodity_table_id != null) return false;
        if (this.item_table_id != null) return false;
        if (this.total_num != null) return false;
        if (this.updatetime != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.commodity_id = null;
        this.commodity_table_id = null;
        this.item_table_id = null;
        this.total_num = null;
        this.updatetime = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.commodity_id != null)
            ret += ("commodity_id = " + this.commodity_id.toString() + " ");
        if (this.commodity_table_id != null)
            ret += ("commodity_table_id = " + this.commodity_table_id.toString() + " ");
        if (this.item_table_id != null)
            ret += ("item_table_id = " + this.item_table_id.toString() + " ");
        if (this.total_num != null)
            ret += ("total_num = " + this.total_num.toString() + " ");
        if (this.updatetime != null)
            ret += ("updatetime = " + this.updatetime.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "commodity";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "commodity_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return commodity_id;
    }
}