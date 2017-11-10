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
//================player_id===========================
@SuppressWarnings("unused")
public class DatabaseRecharge_gold_back implements DatabaseTableDataBase<DatabaseRecharge_gold_back>,Serializable {
    public static final String TableName = "recharge_gold_back";
    /** player_id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 累计充值统计 */
    @DatabaseFieldAttribute(fieldName = "recharge_gold_sum",fieldType = Integer.class,arrayType = Byte.class)
    public Integer recharge_gold_sum = null;
    public Integer getRecharge_gold_sum() {return recharge_gold_sum;}
    /** 0是没有领过，1是已经领取 */
    @DatabaseFieldAttribute(fieldName = "mask",fieldType = Integer.class,arrayType = Byte.class)
    public Integer mask = null;
    public Integer getMask() {return mask;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRecharge_gold_back __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRecharge_gold_back();
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
    public DatabaseRecharge_gold_back diff()
    {
        DatabaseRecharge_gold_back ret = new DatabaseRecharge_gold_back();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.recharge_gold_sum != null && (__self == null || !this.recharge_gold_sum.equals(__self.recharge_gold_sum)))
            ret.recharge_gold_sum = this.recharge_gold_sum;
        if (this.mask != null && (__self == null || !this.mask.equals(__self.mask)))
            ret.mask = this.mask;
        return ret;
    }
    @Override
    public void set(DatabaseRecharge_gold_back value) {
        this.player_id = value.player_id;
        this.recharge_gold_sum = value.recharge_gold_sum;
        this.mask = value.mask;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.recharge_gold_sum != null) return false;
        if (this.mask != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.recharge_gold_sum = null;
        this.mask = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.recharge_gold_sum != null)
            ret += ("recharge_gold_sum = " + this.recharge_gold_sum.toString() + " ");
        if (this.mask != null)
            ret += ("mask = " + this.mask.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "recharge_gold_back";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "player_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return player_id;
    }
}