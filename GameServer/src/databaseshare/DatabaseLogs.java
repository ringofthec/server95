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
//================日志变量表===========================
@SuppressWarnings("unused")
public class DatabaseLogs implements DatabaseTableDataBase<DatabaseLogs>,Serializable {
    public static final String TableName = "logs";
    /** 记录的日期 */
    @DatabaseFieldAttribute(fieldName = "log_day",fieldType = String.class,arrayType = Byte.class)
    public String log_day = null;
    public String getLog_day() {return log_day;}
    /** 新玩家数 */
    @DatabaseFieldAttribute(fieldName = "new_player",fieldType = Integer.class,arrayType = Byte.class)
    public Integer new_player = null;
    public Integer getNew_player() {return new_player;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "login_player",fieldType = Integer.class,arrayType = Byte.class)
    public Integer login_player = null;
    public Integer getLogin_player() {return login_player;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "login_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer login_count = null;
    public Integer getLogin_count() {return login_count;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "recharge_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer recharge_num = null;
    public Integer getRecharge_num() {return recharge_num;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "recharge",fieldType = Integer.class,arrayType = Byte.class)
    public Integer recharge = null;
    public Integer getRecharge() {return recharge;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d2daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d2daylogin = null;
    public Integer getD2daylogin() {return d2daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d3daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d3daylogin = null;
    public Integer getD3daylogin() {return d3daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d4daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d4daylogin = null;
    public Integer getD4daylogin() {return d4daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d5daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d5daylogin = null;
    public Integer getD5daylogin() {return d5daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d6daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d6daylogin = null;
    public Integer getD6daylogin() {return d6daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d7daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d7daylogin = null;
    public Integer getD7daylogin() {return d7daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d8daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d8daylogin = null;
    public Integer getD8daylogin() {return d8daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d9daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d9daylogin = null;
    public Integer getD9daylogin() {return d9daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d10daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d10daylogin = null;
    public Integer getD10daylogin() {return d10daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d15daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d15daylogin = null;
    public Integer getD15daylogin() {return d15daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d20daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d20daylogin = null;
    public Integer getD20daylogin() {return d20daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d25daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d25daylogin = null;
    public Integer getD25daylogin() {return d25daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d30daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d30daylogin = null;
    public Integer getD30daylogin() {return d30daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d40daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d40daylogin = null;
    public Integer getD40daylogin() {return d40daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d50daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d50daylogin = null;
    public Integer getD50daylogin() {return d50daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d60daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d60daylogin = null;
    public Integer getD60daylogin() {return d60daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "d120daylogin",fieldType = Integer.class,arrayType = Byte.class)
    public Integer d120daylogin = null;
    public Integer getD120daylogin() {return d120daylogin;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "money_gen",fieldType = Long.class,arrayType = Byte.class)
    public Long money_gen = null;
    public Long getMoney_gen() {return money_gen;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "money_cost",fieldType = Long.class,arrayType = Byte.class)
    public Long money_cost = null;
    public Long getMoney_cost() {return money_cost;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "gold_gen",fieldType = Long.class,arrayType = Byte.class)
    public Long gold_gen = null;
    public Long getGold_gen() {return gold_gen;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "gold_cost",fieldType = Long.class,arrayType = Byte.class)
    public Long gold_cost = null;
    public Long getGold_cost() {return gold_cost;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "recharge_doller",fieldType = Float.class,arrayType = Byte.class)
    public Float recharge_doller = null;
    public Float getRecharge_doller() {return recharge_doller;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseLogs __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseLogs();
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
    public DatabaseLogs diff()
    {
        DatabaseLogs ret = new DatabaseLogs();
        if (this.log_day != null && (__self == null || !this.log_day.equals(__self.log_day)))
            ret.log_day = this.log_day;
        if (this.new_player != null && (__self == null || !this.new_player.equals(__self.new_player)))
            ret.new_player = this.new_player;
        if (this.login_player != null && (__self == null || !this.login_player.equals(__self.login_player)))
            ret.login_player = this.login_player;
        if (this.login_count != null && (__self == null || !this.login_count.equals(__self.login_count)))
            ret.login_count = this.login_count;
        if (this.recharge_num != null && (__self == null || !this.recharge_num.equals(__self.recharge_num)))
            ret.recharge_num = this.recharge_num;
        if (this.recharge != null && (__self == null || !this.recharge.equals(__self.recharge)))
            ret.recharge = this.recharge;
        if (this.d2daylogin != null && (__self == null || !this.d2daylogin.equals(__self.d2daylogin)))
            ret.d2daylogin = this.d2daylogin;
        if (this.d3daylogin != null && (__self == null || !this.d3daylogin.equals(__self.d3daylogin)))
            ret.d3daylogin = this.d3daylogin;
        if (this.d4daylogin != null && (__self == null || !this.d4daylogin.equals(__self.d4daylogin)))
            ret.d4daylogin = this.d4daylogin;
        if (this.d5daylogin != null && (__self == null || !this.d5daylogin.equals(__self.d5daylogin)))
            ret.d5daylogin = this.d5daylogin;
        if (this.d6daylogin != null && (__self == null || !this.d6daylogin.equals(__self.d6daylogin)))
            ret.d6daylogin = this.d6daylogin;
        if (this.d7daylogin != null && (__self == null || !this.d7daylogin.equals(__self.d7daylogin)))
            ret.d7daylogin = this.d7daylogin;
        if (this.d8daylogin != null && (__self == null || !this.d8daylogin.equals(__self.d8daylogin)))
            ret.d8daylogin = this.d8daylogin;
        if (this.d9daylogin != null && (__self == null || !this.d9daylogin.equals(__self.d9daylogin)))
            ret.d9daylogin = this.d9daylogin;
        if (this.d10daylogin != null && (__self == null || !this.d10daylogin.equals(__self.d10daylogin)))
            ret.d10daylogin = this.d10daylogin;
        if (this.d15daylogin != null && (__self == null || !this.d15daylogin.equals(__self.d15daylogin)))
            ret.d15daylogin = this.d15daylogin;
        if (this.d20daylogin != null && (__self == null || !this.d20daylogin.equals(__self.d20daylogin)))
            ret.d20daylogin = this.d20daylogin;
        if (this.d25daylogin != null && (__self == null || !this.d25daylogin.equals(__self.d25daylogin)))
            ret.d25daylogin = this.d25daylogin;
        if (this.d30daylogin != null && (__self == null || !this.d30daylogin.equals(__self.d30daylogin)))
            ret.d30daylogin = this.d30daylogin;
        if (this.d40daylogin != null && (__self == null || !this.d40daylogin.equals(__self.d40daylogin)))
            ret.d40daylogin = this.d40daylogin;
        if (this.d50daylogin != null && (__self == null || !this.d50daylogin.equals(__self.d50daylogin)))
            ret.d50daylogin = this.d50daylogin;
        if (this.d60daylogin != null && (__self == null || !this.d60daylogin.equals(__self.d60daylogin)))
            ret.d60daylogin = this.d60daylogin;
        if (this.d120daylogin != null && (__self == null || !this.d120daylogin.equals(__self.d120daylogin)))
            ret.d120daylogin = this.d120daylogin;
        if (this.money_gen != null && (__self == null || !this.money_gen.equals(__self.money_gen)))
            ret.money_gen = this.money_gen;
        if (this.money_cost != null && (__self == null || !this.money_cost.equals(__self.money_cost)))
            ret.money_cost = this.money_cost;
        if (this.gold_gen != null && (__self == null || !this.gold_gen.equals(__self.gold_gen)))
            ret.gold_gen = this.gold_gen;
        if (this.gold_cost != null && (__self == null || !this.gold_cost.equals(__self.gold_cost)))
            ret.gold_cost = this.gold_cost;
        if (this.recharge_doller != null && (__self == null || !this.recharge_doller.equals(__self.recharge_doller)))
            ret.recharge_doller = this.recharge_doller;
        return ret;
    }
    @Override
    public void set(DatabaseLogs value) {
        this.log_day = value.log_day;
        this.new_player = value.new_player;
        this.login_player = value.login_player;
        this.login_count = value.login_count;
        this.recharge_num = value.recharge_num;
        this.recharge = value.recharge;
        this.d2daylogin = value.d2daylogin;
        this.d3daylogin = value.d3daylogin;
        this.d4daylogin = value.d4daylogin;
        this.d5daylogin = value.d5daylogin;
        this.d6daylogin = value.d6daylogin;
        this.d7daylogin = value.d7daylogin;
        this.d8daylogin = value.d8daylogin;
        this.d9daylogin = value.d9daylogin;
        this.d10daylogin = value.d10daylogin;
        this.d15daylogin = value.d15daylogin;
        this.d20daylogin = value.d20daylogin;
        this.d25daylogin = value.d25daylogin;
        this.d30daylogin = value.d30daylogin;
        this.d40daylogin = value.d40daylogin;
        this.d50daylogin = value.d50daylogin;
        this.d60daylogin = value.d60daylogin;
        this.d120daylogin = value.d120daylogin;
        this.money_gen = value.money_gen;
        this.money_cost = value.money_cost;
        this.gold_gen = value.gold_gen;
        this.gold_cost = value.gold_cost;
        this.recharge_doller = value.recharge_doller;
    }
    @Override
    public boolean isEmpty() {
        if (this.log_day != null) return false;
        if (this.new_player != null) return false;
        if (this.login_player != null) return false;
        if (this.login_count != null) return false;
        if (this.recharge_num != null) return false;
        if (this.recharge != null) return false;
        if (this.d2daylogin != null) return false;
        if (this.d3daylogin != null) return false;
        if (this.d4daylogin != null) return false;
        if (this.d5daylogin != null) return false;
        if (this.d6daylogin != null) return false;
        if (this.d7daylogin != null) return false;
        if (this.d8daylogin != null) return false;
        if (this.d9daylogin != null) return false;
        if (this.d10daylogin != null) return false;
        if (this.d15daylogin != null) return false;
        if (this.d20daylogin != null) return false;
        if (this.d25daylogin != null) return false;
        if (this.d30daylogin != null) return false;
        if (this.d40daylogin != null) return false;
        if (this.d50daylogin != null) return false;
        if (this.d60daylogin != null) return false;
        if (this.d120daylogin != null) return false;
        if (this.money_gen != null) return false;
        if (this.money_cost != null) return false;
        if (this.gold_gen != null) return false;
        if (this.gold_cost != null) return false;
        if (this.recharge_doller != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.log_day = null;
        this.new_player = null;
        this.login_player = null;
        this.login_count = null;
        this.recharge_num = null;
        this.recharge = null;
        this.d2daylogin = null;
        this.d3daylogin = null;
        this.d4daylogin = null;
        this.d5daylogin = null;
        this.d6daylogin = null;
        this.d7daylogin = null;
        this.d8daylogin = null;
        this.d9daylogin = null;
        this.d10daylogin = null;
        this.d15daylogin = null;
        this.d20daylogin = null;
        this.d25daylogin = null;
        this.d30daylogin = null;
        this.d40daylogin = null;
        this.d50daylogin = null;
        this.d60daylogin = null;
        this.d120daylogin = null;
        this.money_gen = null;
        this.money_cost = null;
        this.gold_gen = null;
        this.gold_cost = null;
        this.recharge_doller = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.log_day != null)
            ret += ("log_day = " + this.log_day.toString() + " ");
        if (this.new_player != null)
            ret += ("new_player = " + this.new_player.toString() + " ");
        if (this.login_player != null)
            ret += ("login_player = " + this.login_player.toString() + " ");
        if (this.login_count != null)
            ret += ("login_count = " + this.login_count.toString() + " ");
        if (this.recharge_num != null)
            ret += ("recharge_num = " + this.recharge_num.toString() + " ");
        if (this.recharge != null)
            ret += ("recharge = " + this.recharge.toString() + " ");
        if (this.d2daylogin != null)
            ret += ("d2daylogin = " + this.d2daylogin.toString() + " ");
        if (this.d3daylogin != null)
            ret += ("d3daylogin = " + this.d3daylogin.toString() + " ");
        if (this.d4daylogin != null)
            ret += ("d4daylogin = " + this.d4daylogin.toString() + " ");
        if (this.d5daylogin != null)
            ret += ("d5daylogin = " + this.d5daylogin.toString() + " ");
        if (this.d6daylogin != null)
            ret += ("d6daylogin = " + this.d6daylogin.toString() + " ");
        if (this.d7daylogin != null)
            ret += ("d7daylogin = " + this.d7daylogin.toString() + " ");
        if (this.d8daylogin != null)
            ret += ("d8daylogin = " + this.d8daylogin.toString() + " ");
        if (this.d9daylogin != null)
            ret += ("d9daylogin = " + this.d9daylogin.toString() + " ");
        if (this.d10daylogin != null)
            ret += ("d10daylogin = " + this.d10daylogin.toString() + " ");
        if (this.d15daylogin != null)
            ret += ("d15daylogin = " + this.d15daylogin.toString() + " ");
        if (this.d20daylogin != null)
            ret += ("d20daylogin = " + this.d20daylogin.toString() + " ");
        if (this.d25daylogin != null)
            ret += ("d25daylogin = " + this.d25daylogin.toString() + " ");
        if (this.d30daylogin != null)
            ret += ("d30daylogin = " + this.d30daylogin.toString() + " ");
        if (this.d40daylogin != null)
            ret += ("d40daylogin = " + this.d40daylogin.toString() + " ");
        if (this.d50daylogin != null)
            ret += ("d50daylogin = " + this.d50daylogin.toString() + " ");
        if (this.d60daylogin != null)
            ret += ("d60daylogin = " + this.d60daylogin.toString() + " ");
        if (this.d120daylogin != null)
            ret += ("d120daylogin = " + this.d120daylogin.toString() + " ");
        if (this.money_gen != null)
            ret += ("money_gen = " + this.money_gen.toString() + " ");
        if (this.money_cost != null)
            ret += ("money_cost = " + this.money_cost.toString() + " ");
        if (this.gold_gen != null)
            ret += ("gold_gen = " + this.gold_gen.toString() + " ");
        if (this.gold_cost != null)
            ret += ("gold_cost = " + this.gold_cost.toString() + " ");
        if (this.recharge_doller != null)
            ret += ("recharge_doller = " + this.recharge_doller.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "logs";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "log_day";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return log_day;
    }
}