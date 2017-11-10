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
//================统计客户端事件===========================
@SuppressWarnings("unused")
public class DatabaseStatistics_event implements DatabaseTableDataBase<DatabaseStatistics_event>,Serializable {
    public static final String TableName = "statistics_event";
    /** 事件名称 */
    @DatabaseFieldAttribute(fieldName = "event_name",fieldType = String.class,arrayType = Byte.class)
    public String event_name = null;
    public String getEvent_name() {return event_name;}
    /** 事件时间 */
    @DatabaseFieldAttribute(fieldName = "event_date",fieldType = Timestamp.class,arrayType = Byte.class)
    public Timestamp event_date = null;
    public Timestamp getEvent_date() {return event_date;}
    /** 事件次数 */
    @DatabaseFieldAttribute(fieldName = "count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer count = null;
    public Integer getCount() {return count;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseStatistics_event __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseStatistics_event();
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
    public DatabaseStatistics_event diff()
    {
        DatabaseStatistics_event ret = new DatabaseStatistics_event();
        if (this.event_name != null && (__self == null || !this.event_name.equals(__self.event_name)))
            ret.event_name = this.event_name;
        if (this.event_date != null && (__self == null || !this.event_date.equals(__self.event_date)))
            ret.event_date = this.event_date;
        if (this.count != null && (__self == null || !this.count.equals(__self.count)))
            ret.count = this.count;
        return ret;
    }
    @Override
    public void set(DatabaseStatistics_event value) {
        this.event_name = value.event_name;
        this.event_date = value.event_date;
        this.count = value.count;
    }
    @Override
    public boolean isEmpty() {
        if (this.event_name != null) return false;
        if (this.event_date != null) return false;
        if (this.count != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.event_name = null;
        this.event_date = null;
        this.count = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.event_name != null)
            ret += ("event_name = " + this.event_name.toString() + " ");
        if (this.event_date != null)
            ret += ("event_date = " + this.event_date.toString() + " ");
        if (this.count != null)
            ret += ("count = " + this.count.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "statistics_event";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return null;
    }
    @Override
    public Object getPrimaryKeyValue() {
        return null;
    }
}