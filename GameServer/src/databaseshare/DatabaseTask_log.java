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
//================任务id===========================
@SuppressWarnings("unused")
public class DatabaseTask_log implements DatabaseTableDataBase<DatabaseTask_log>,Serializable {
    public static final String TableName = "task_log";
    /** task_id */
    @DatabaseFieldAttribute(fieldName = "task_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer task_id = null;
    public Integer getTask_id() {return task_id;}
    /** 完成次数 */
    @DatabaseFieldAttribute(fieldName = "count",fieldType = Long.class,arrayType = Byte.class)
    public Long count = null;
    public Long getCount() {return count;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseTask_log __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseTask_log();
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
    public DatabaseTask_log diff()
    {
        DatabaseTask_log ret = new DatabaseTask_log();
        if (this.task_id != null && (__self == null || !this.task_id.equals(__self.task_id)))
            ret.task_id = this.task_id;
        if (this.count != null && (__self == null || !this.count.equals(__self.count)))
            ret.count = this.count;
        return ret;
    }
    @Override
    public void set(DatabaseTask_log value) {
        this.task_id = value.task_id;
        this.count = value.count;
    }
    @Override
    public boolean isEmpty() {
        if (this.task_id != null) return false;
        if (this.count != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.task_id = null;
        this.count = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.task_id != null)
            ret += ("task_id = " + this.task_id.toString() + " ");
        if (this.count != null)
            ret += ("count = " + this.count.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "task_log";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "task_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return task_id;
    }
}