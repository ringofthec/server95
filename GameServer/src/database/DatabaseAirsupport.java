// 本文件是自动生成，不要手动修改
package database;
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
//================空中支援表===========================
@SuppressWarnings("unused")
public class DatabaseAirsupport implements DatabaseTableDataBase<DatabaseAirsupport>,Serializable {
    public static final String TableName = "airsupport";
    /** 空中支援ID */
    @DatabaseFieldAttribute(fieldName = "air_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer air_id = null;
    public Integer getAir_id() {return air_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** table表ID */
    @DatabaseFieldAttribute(fieldName = "table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer table_id = null;
    public Integer getTable_id() {return table_id;}
    /** 等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 耐久 */
    @DatabaseFieldAttribute(fieldName = "durable",fieldType = Integer.class,arrayType = Byte.class)
    public Integer durable = null;
    public Integer getDurable() {return durable;}
    /** 是否出战 */
    @DatabaseFieldAttribute(fieldName = "out_fighting",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean out_fighting = null;
    public Boolean getOut_fighting() {return out_fighting;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseAirsupport __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseAirsupport();
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
    public DatabaseAirsupport diff()
    {
        DatabaseAirsupport ret = new DatabaseAirsupport();
        if (this.air_id != null && (__self == null || !this.air_id.equals(__self.air_id)))
            ret.air_id = this.air_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.table_id != null && (__self == null || !this.table_id.equals(__self.table_id)))
            ret.table_id = this.table_id;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.durable != null && (__self == null || !this.durable.equals(__self.durable)))
            ret.durable = this.durable;
        if (this.out_fighting != null && (__self == null || !this.out_fighting.equals(__self.out_fighting)))
            ret.out_fighting = this.out_fighting;
        return ret;
    }
    @Override
    public void set(DatabaseAirsupport value) {
        this.air_id = value.air_id;
        this.player_id = value.player_id;
        this.table_id = value.table_id;
        this.level = value.level;
        this.durable = value.durable;
        this.out_fighting = value.out_fighting;
    }
    @Override
    public boolean isEmpty() {
        if (this.air_id != null) return false;
        if (this.player_id != null) return false;
        if (this.table_id != null) return false;
        if (this.level != null) return false;
        if (this.durable != null) return false;
        if (this.out_fighting != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.air_id = null;
        this.player_id = null;
        this.table_id = null;
        this.level = null;
        this.durable = null;
        this.out_fighting = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.air_id != null)
            ret += ("air_id = " + this.air_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.table_id != null)
            ret += ("table_id = " + this.table_id.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.durable != null)
            ret += ("durable = " + this.durable.toString() + " ");
        if (this.out_fighting != null)
            ret += ("out_fighting = " + this.out_fighting.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "airsupport";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "air_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return air_id;
    }
}