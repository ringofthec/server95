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
public class DatabaseExchangecodegift implements DatabaseTableDataBase<DatabaseExchangecodegift>,Serializable {
    public static final String TableName = "exchangecodegift";
    /**  */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "patch_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer patch_id = null;
    public Integer getPatch_id() {return patch_id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "exchangecode",fieldType = String.class,arrayType = Byte.class)
    public String exchangecode = null;
    public String getExchangecode() {return exchangecode;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "item_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer item_id = null;
    public Integer getItem_id() {return item_id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "item_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer item_num = null;
    public Integer getItem_num() {return item_num;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseExchangecodegift __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseExchangecodegift();
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
    public DatabaseExchangecodegift diff()
    {
        DatabaseExchangecodegift ret = new DatabaseExchangecodegift();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.patch_id != null && (__self == null || !this.patch_id.equals(__self.patch_id)))
            ret.patch_id = this.patch_id;
        if (this.exchangecode != null && (__self == null || !this.exchangecode.equals(__self.exchangecode)))
            ret.exchangecode = this.exchangecode;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.item_id != null && (__self == null || !this.item_id.equals(__self.item_id)))
            ret.item_id = this.item_id;
        if (this.item_num != null && (__self == null || !this.item_num.equals(__self.item_num)))
            ret.item_num = this.item_num;
        return ret;
    }
    @Override
    public void set(DatabaseExchangecodegift value) {
        this.id = value.id;
        this.patch_id = value.patch_id;
        this.exchangecode = value.exchangecode;
        this.player_id = value.player_id;
        this.item_id = value.item_id;
        this.item_num = value.item_num;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.patch_id != null) return false;
        if (this.exchangecode != null) return false;
        if (this.player_id != null) return false;
        if (this.item_id != null) return false;
        if (this.item_num != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.patch_id = null;
        this.exchangecode = null;
        this.player_id = null;
        this.item_id = null;
        this.item_num = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.patch_id != null)
            ret += ("patch_id = " + this.patch_id.toString() + " ");
        if (this.exchangecode != null)
            ret += ("exchangecode = " + this.exchangecode.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.item_id != null)
            ret += ("item_id = " + this.item_id.toString() + " ");
        if (this.item_num != null)
            ret += ("item_num = " + this.item_num.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "exchangecodegift";
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