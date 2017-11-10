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
public class DatabasePigop implements DatabaseTableDataBase<DatabasePigop>,Serializable {
    public static final String TableName = "pigop";
    /**  */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** = */
    @DatabaseFieldAttribute(fieldName = "op_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long op_time = null;
    public Long getOp_time() {return op_time;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "optype",fieldType = Integer.class,arrayType = Byte.class)
    public Integer optype = null;
    public Integer getOptype() {return optype;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "number",fieldType = Long.class,arrayType = Byte.class)
    public Long number = null;
    public Long getNumber() {return number;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "otherid",fieldType = Long.class,arrayType = Byte.class)
    public Long otherid = null;
    public Long getOtherid() {return otherid;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "othername",fieldType = String.class,arrayType = Byte.class)
    public String othername = null;
    public String getOthername() {return othername;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "pigcoin",fieldType = Long.class,arrayType = Byte.class)
    public Long pigcoin = null;
    public Long getPigcoin() {return pigcoin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "money",fieldType = Long.class,arrayType = Byte.class)
    public Long money = null;
    public Long getMoney() {return money;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "gold",fieldType = Long.class,arrayType = Byte.class)
    public Long gold = null;
    public Long getGold() {return gold;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePigop __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePigop();
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
    public DatabasePigop diff()
    {
        DatabasePigop ret = new DatabasePigop();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.op_time != null && (__self == null || !this.op_time.equals(__self.op_time)))
            ret.op_time = this.op_time;
        if (this.optype != null && (__self == null || !this.optype.equals(__self.optype)))
            ret.optype = this.optype;
        if (this.number != null && (__self == null || !this.number.equals(__self.number)))
            ret.number = this.number;
        if (this.otherid != null && (__self == null || !this.otherid.equals(__self.otherid)))
            ret.otherid = this.otherid;
        if (this.othername != null && (__self == null || !this.othername.equals(__self.othername)))
            ret.othername = this.othername;
        if (this.pigcoin != null && (__self == null || !this.pigcoin.equals(__self.pigcoin)))
            ret.pigcoin = this.pigcoin;
        if (this.money != null && (__self == null || !this.money.equals(__self.money)))
            ret.money = this.money;
        if (this.gold != null && (__self == null || !this.gold.equals(__self.gold)))
            ret.gold = this.gold;
        return ret;
    }
    @Override
    public void set(DatabasePigop value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.op_time = value.op_time;
        this.optype = value.optype;
        this.number = value.number;
        this.otherid = value.otherid;
        this.othername = value.othername;
        this.pigcoin = value.pigcoin;
        this.money = value.money;
        this.gold = value.gold;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.op_time != null) return false;
        if (this.optype != null) return false;
        if (this.number != null) return false;
        if (this.otherid != null) return false;
        if (this.othername != null) return false;
        if (this.pigcoin != null) return false;
        if (this.money != null) return false;
        if (this.gold != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.op_time = null;
        this.optype = null;
        this.number = null;
        this.otherid = null;
        this.othername = null;
        this.pigcoin = null;
        this.money = null;
        this.gold = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.op_time != null)
            ret += ("op_time = " + this.op_time.toString() + " ");
        if (this.optype != null)
            ret += ("optype = " + this.optype.toString() + " ");
        if (this.number != null)
            ret += ("number = " + this.number.toString() + " ");
        if (this.otherid != null)
            ret += ("otherid = " + this.otherid.toString() + " ");
        if (this.othername != null)
            ret += ("othername = " + this.othername.toString() + " ");
        if (this.pigcoin != null)
            ret += ("pigcoin = " + this.pigcoin.toString() + " ");
        if (this.money != null)
            ret += ("money = " + this.money.toString() + " ");
        if (this.gold != null)
            ret += ("gold = " + this.gold.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "pigop";
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