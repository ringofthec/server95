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
//================充值id===========================
@SuppressWarnings("unused")
public class DatabaseRecharge_log implements DatabaseTableDataBase<DatabaseRecharge_log>,Serializable {
    public static final String TableName = "recharge_log";
    /** recharge_id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** 充值时间 */
    @DatabaseFieldAttribute(fieldName = "recharge_time",fieldType = Timestamp.class,arrayType = Byte.class)
    public Timestamp recharge_time = null;
    public Timestamp getRecharge_time() {return recharge_time;}
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家名称 */
    @DatabaseFieldAttribute(fieldName = "player_name",fieldType = String.class,arrayType = Byte.class)
    public String player_name = null;
    public String getPlayer_name() {return player_name;}
    /** 流水号 */
    @DatabaseFieldAttribute(fieldName = "pay_order",fieldType = String.class,arrayType = Byte.class)
    public String pay_order = null;
    public String getPay_order() {return pay_order;}
    /** 商品名称 */
    @DatabaseFieldAttribute(fieldName = "item_id",fieldType = String.class,arrayType = Byte.class)
    public String item_id = null;
    public String getItem_id() {return item_id;}
    /** 获得金砖数 */
    @DatabaseFieldAttribute(fieldName = "goldnum",fieldType = Integer.class,arrayType = Byte.class)
    public Integer goldnum = null;
    public Integer getGoldnum() {return goldnum;}
    /** 国别 */
    @DatabaseFieldAttribute(fieldName = "nation",fieldType = String.class,arrayType = Byte.class)
    public String nation = null;
    public String getNation() {return nation;}
    /** 花得钱 */
    @DatabaseFieldAttribute(fieldName = "cost_num",fieldType = Float.class,arrayType = Byte.class)
    public Float cost_num = null;
    public Float getCost_num() {return cost_num;}
    /** 账户创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Byte.class)
    public Timestamp create_time = null;
    public Timestamp getCreate_time() {return create_time;}
    /** 渠道 */
    @DatabaseFieldAttribute(fieldName = "channel",fieldType = Integer.class,arrayType = Byte.class)
    public Integer channel = null;
    public Integer getChannel() {return channel;}
    /** 等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRecharge_log __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRecharge_log();
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
    public DatabaseRecharge_log diff()
    {
        DatabaseRecharge_log ret = new DatabaseRecharge_log();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.recharge_time != null && (__self == null || !this.recharge_time.equals(__self.recharge_time)))
            ret.recharge_time = this.recharge_time;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.player_name != null && (__self == null || !this.player_name.equals(__self.player_name)))
            ret.player_name = this.player_name;
        if (this.pay_order != null && (__self == null || !this.pay_order.equals(__self.pay_order)))
            ret.pay_order = this.pay_order;
        if (this.item_id != null && (__self == null || !this.item_id.equals(__self.item_id)))
            ret.item_id = this.item_id;
        if (this.goldnum != null && (__self == null || !this.goldnum.equals(__self.goldnum)))
            ret.goldnum = this.goldnum;
        if (this.nation != null && (__self == null || !this.nation.equals(__self.nation)))
            ret.nation = this.nation;
        if (this.cost_num != null && (__self == null || !this.cost_num.equals(__self.cost_num)))
            ret.cost_num = this.cost_num;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.channel != null && (__self == null || !this.channel.equals(__self.channel)))
            ret.channel = this.channel;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        return ret;
    }
    @Override
    public void set(DatabaseRecharge_log value) {
        this.id = value.id;
        this.recharge_time = value.recharge_time;
        this.player_id = value.player_id;
        this.player_name = value.player_name;
        this.pay_order = value.pay_order;
        this.item_id = value.item_id;
        this.goldnum = value.goldnum;
        this.nation = value.nation;
        this.cost_num = value.cost_num;
        this.create_time = value.create_time;
        this.channel = value.channel;
        this.level = value.level;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.recharge_time != null) return false;
        if (this.player_id != null) return false;
        if (this.player_name != null) return false;
        if (this.pay_order != null) return false;
        if (this.item_id != null) return false;
        if (this.goldnum != null) return false;
        if (this.nation != null) return false;
        if (this.cost_num != null) return false;
        if (this.create_time != null) return false;
        if (this.channel != null) return false;
        if (this.level != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.recharge_time = null;
        this.player_id = null;
        this.player_name = null;
        this.pay_order = null;
        this.item_id = null;
        this.goldnum = null;
        this.nation = null;
        this.cost_num = null;
        this.create_time = null;
        this.channel = null;
        this.level = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.recharge_time != null)
            ret += ("recharge_time = " + this.recharge_time.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.player_name != null)
            ret += ("player_name = " + this.player_name.toString() + " ");
        if (this.pay_order != null)
            ret += ("pay_order = " + this.pay_order.toString() + " ");
        if (this.item_id != null)
            ret += ("item_id = " + this.item_id.toString() + " ");
        if (this.goldnum != null)
            ret += ("goldnum = " + this.goldnum.toString() + " ");
        if (this.nation != null)
            ret += ("nation = " + this.nation.toString() + " ");
        if (this.cost_num != null)
            ret += ("cost_num = " + this.cost_num.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.channel != null)
            ret += ("channel = " + this.channel.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "recharge_log";
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