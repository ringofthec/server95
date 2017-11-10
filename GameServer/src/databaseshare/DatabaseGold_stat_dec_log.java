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
//================channel_id===========================
@SuppressWarnings("unused")
public class DatabaseGold_stat_dec_log implements DatabaseTableDataBase<DatabaseGold_stat_dec_log>,Serializable {
    public static final String TableName = "gold_stat_dec_log";
    /** channel_id */
    @DatabaseFieldAttribute(fieldName = "channel_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer channel_id = null;
    public Integer getChannel_id() {return channel_id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "count",fieldType = Long.class,arrayType = Byte.class)
    public Long count = null;
    public Long getCount() {return count;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseGold_stat_dec_log __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseGold_stat_dec_log();
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
    public DatabaseGold_stat_dec_log diff()
    {
        DatabaseGold_stat_dec_log ret = new DatabaseGold_stat_dec_log();
        if (this.channel_id != null && (__self == null || !this.channel_id.equals(__self.channel_id)))
            ret.channel_id = this.channel_id;
        if (this.count != null && (__self == null || !this.count.equals(__self.count)))
            ret.count = this.count;
        return ret;
    }
    @Override
    public void set(DatabaseGold_stat_dec_log value) {
        this.channel_id = value.channel_id;
        this.count = value.count;
    }
    @Override
    public boolean isEmpty() {
        if (this.channel_id != null) return false;
        if (this.count != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.channel_id = null;
        this.count = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.channel_id != null)
            ret += ("channel_id = " + this.channel_id.toString() + " ");
        if (this.count != null)
            ret += ("count = " + this.count.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "gold_stat_dec_log";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "channel_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return channel_id;
    }
}