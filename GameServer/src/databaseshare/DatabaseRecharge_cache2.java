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
public class DatabaseRecharge_cache2 implements DatabaseTableDataBase<DatabaseRecharge_cache2>,Serializable {
    public static final String TableName = "recharge_cache2";
    /** id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** player_id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 人民币分 */
    @DatabaseFieldAttribute(fieldName = "amount",fieldType = Float.class,arrayType = Byte.class)
    public Float amount = null;
    public Float getAmount() {return amount;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "shop_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer shop_type = null;
    public Integer getShop_type() {return shop_type;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "tid",fieldType = Integer.class,arrayType = Byte.class)
    public Integer tid = null;
    public Integer getTid() {return tid;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "pay_order",fieldType = String.class,arrayType = Byte.class)
    public String pay_order = null;
    public String getPay_order() {return pay_order;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "recharge_str",fieldType = String.class,arrayType = Byte.class)
    public String recharge_str = null;
    public String getRecharge_str() {return recharge_str;}
    /** 创建时间 */
    @DatabaseFieldAttribute(fieldName = "get_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long get_time = null;
    public Long getGet_time() {return get_time;}
    /** 0是没有领过，1是已经领取 */
    @DatabaseFieldAttribute(fieldName = "mask",fieldType = Integer.class,arrayType = Byte.class)
    public Integer mask = null;
    public Integer getMask() {return mask;}
    /** 0 app 1 支付宝 2 微信 */
    @DatabaseFieldAttribute(fieldName = "paytype",fieldType = Integer.class,arrayType = Byte.class)
    public Integer paytype = null;
    public Integer getPaytype() {return paytype;}
    /** 1 金砖大富翁 */
    @DatabaseFieldAttribute(fieldName = "channel",fieldType = Integer.class,arrayType = Byte.class)
    public Integer channel = null;
    public Integer getChannel() {return channel;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRecharge_cache2 __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRecharge_cache2();
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
    public DatabaseRecharge_cache2 diff()
    {
        DatabaseRecharge_cache2 ret = new DatabaseRecharge_cache2();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.amount != null && (__self == null || !this.amount.equals(__self.amount)))
            ret.amount = this.amount;
        if (this.shop_type != null && (__self == null || !this.shop_type.equals(__self.shop_type)))
            ret.shop_type = this.shop_type;
        if (this.tid != null && (__self == null || !this.tid.equals(__self.tid)))
            ret.tid = this.tid;
        if (this.pay_order != null && (__self == null || !this.pay_order.equals(__self.pay_order)))
            ret.pay_order = this.pay_order;
        if (this.recharge_str != null && (__self == null || !this.recharge_str.equals(__self.recharge_str)))
            ret.recharge_str = this.recharge_str;
        if (this.get_time != null && (__self == null || !this.get_time.equals(__self.get_time)))
            ret.get_time = this.get_time;
        if (this.mask != null && (__self == null || !this.mask.equals(__self.mask)))
            ret.mask = this.mask;
        if (this.paytype != null && (__self == null || !this.paytype.equals(__self.paytype)))
            ret.paytype = this.paytype;
        if (this.channel != null && (__self == null || !this.channel.equals(__self.channel)))
            ret.channel = this.channel;
        return ret;
    }
    @Override
    public void set(DatabaseRecharge_cache2 value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.create_time = value.create_time;
        this.amount = value.amount;
        this.shop_type = value.shop_type;
        this.tid = value.tid;
        this.pay_order = value.pay_order;
        this.recharge_str = value.recharge_str;
        this.get_time = value.get_time;
        this.mask = value.mask;
        this.paytype = value.paytype;
        this.channel = value.channel;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.create_time != null) return false;
        if (this.amount != null) return false;
        if (this.shop_type != null) return false;
        if (this.tid != null) return false;
        if (this.pay_order != null) return false;
        if (this.recharge_str != null) return false;
        if (this.get_time != null) return false;
        if (this.mask != null) return false;
        if (this.paytype != null) return false;
        if (this.channel != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.create_time = null;
        this.amount = null;
        this.shop_type = null;
        this.tid = null;
        this.pay_order = null;
        this.recharge_str = null;
        this.get_time = null;
        this.mask = null;
        this.paytype = null;
        this.channel = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.amount != null)
            ret += ("amount = " + this.amount.toString() + " ");
        if (this.shop_type != null)
            ret += ("shop_type = " + this.shop_type.toString() + " ");
        if (this.tid != null)
            ret += ("tid = " + this.tid.toString() + " ");
        if (this.pay_order != null)
            ret += ("pay_order = " + this.pay_order.toString() + " ");
        if (this.recharge_str != null)
            ret += ("recharge_str = " + this.recharge_str.toString() + " ");
        if (this.get_time != null)
            ret += ("get_time = " + this.get_time.toString() + " ");
        if (this.mask != null)
            ret += ("mask = " + this.mask.toString() + " ");
        if (this.paytype != null)
            ret += ("paytype = " + this.paytype.toString() + " ");
        if (this.channel != null)
            ret += ("channel = " + this.channel.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "recharge_cache2";
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