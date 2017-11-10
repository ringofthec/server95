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
//================id===========================
@SuppressWarnings("unused")
public class DatabaseRecharge_cache implements DatabaseTableDataBase<DatabaseRecharge_cache>,Serializable {
    public static final String TableName = "recharge_cache";
    /** id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** player_id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 人民币分 */
    @DatabaseFieldAttribute(fieldName = "amount",fieldType = Integer.class,arrayType = Byte.class)
    public Integer amount = null;
    public Integer getAmount() {return amount;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "item_id",fieldType = String.class,arrayType = Byte.class)
    public String item_id = null;
    public String getItem_id() {return item_id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "pay_order",fieldType = String.class,arrayType = Byte.class)
    public String pay_order = null;
    public String getPay_order() {return pay_order;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "recharge_str",fieldType = String.class,arrayType = Byte.class)
    public String recharge_str = null;
    public String getRecharge_str() {return recharge_str;}
    /** 0是没有领过，1是已经领取 */
    @DatabaseFieldAttribute(fieldName = "mask",fieldType = Integer.class,arrayType = Byte.class)
    public Integer mask = null;
    public Integer getMask() {return mask;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRecharge_cache __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRecharge_cache();
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
    public DatabaseRecharge_cache diff()
    {
        DatabaseRecharge_cache ret = new DatabaseRecharge_cache();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.amount != null && (__self == null || !this.amount.equals(__self.amount)))
            ret.amount = this.amount;
        if (this.item_id != null && (__self == null || !this.item_id.equals(__self.item_id)))
            ret.item_id = this.item_id;
        if (this.pay_order != null && (__self == null || !this.pay_order.equals(__self.pay_order)))
            ret.pay_order = this.pay_order;
        if (this.recharge_str != null && (__self == null || !this.recharge_str.equals(__self.recharge_str)))
            ret.recharge_str = this.recharge_str;
        if (this.mask != null && (__self == null || !this.mask.equals(__self.mask)))
            ret.mask = this.mask;
        return ret;
    }
    @Override
    public void set(DatabaseRecharge_cache value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.amount = value.amount;
        this.item_id = value.item_id;
        this.pay_order = value.pay_order;
        this.recharge_str = value.recharge_str;
        this.mask = value.mask;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.amount != null) return false;
        if (this.item_id != null) return false;
        if (this.pay_order != null) return false;
        if (this.recharge_str != null) return false;
        if (this.mask != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.amount = null;
        this.item_id = null;
        this.pay_order = null;
        this.recharge_str = null;
        this.mask = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.amount != null)
            ret += ("amount = " + this.amount.toString() + " ");
        if (this.item_id != null)
            ret += ("item_id = " + this.item_id.toString() + " ");
        if (this.pay_order != null)
            ret += ("pay_order = " + this.pay_order.toString() + " ");
        if (this.recharge_str != null)
            ret += ("recharge_str = " + this.recharge_str.toString() + " ");
        if (this.mask != null)
            ret += ("mask = " + this.mask.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "recharge_cache";
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