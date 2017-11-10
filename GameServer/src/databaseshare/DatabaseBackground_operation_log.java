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
//================后台操作日志===========================
@SuppressWarnings("unused")
public class DatabaseBackground_operation_log implements DatabaseTableDataBase<DatabaseBackground_operation_log>,Serializable {
    public static final String TableName = "background_operation_log";
    /** 日志id */
    @DatabaseFieldAttribute(fieldName = "log_id",fieldType = Long.class,arrayType = Byte.class)
    public Long log_id = null;
    public Long getLog_id() {return log_id;}
    /** 操作人账号 */
    @DatabaseFieldAttribute(fieldName = "operate_person",fieldType = String.class,arrayType = Byte.class)
    public String operate_person = null;
    public String getOperate_person() {return operate_person;}
    /** 操作的时间 */
    @DatabaseFieldAttribute(fieldName = "operation_time",fieldType = String.class,arrayType = Byte.class)
    public String operation_time = null;
    public String getOperation_time() {return operation_time;}
    /** 做了什么操作 */
    @DatabaseFieldAttribute(fieldName = "operation",fieldType = String.class,arrayType = Byte.class)
    public String operation = null;
    public String getOperation() {return operation;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseBackground_operation_log __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseBackground_operation_log();
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
    public DatabaseBackground_operation_log diff()
    {
        DatabaseBackground_operation_log ret = new DatabaseBackground_operation_log();
        if (this.log_id != null && (__self == null || !this.log_id.equals(__self.log_id)))
            ret.log_id = this.log_id;
        if (this.operate_person != null && (__self == null || !this.operate_person.equals(__self.operate_person)))
            ret.operate_person = this.operate_person;
        if (this.operation_time != null && (__self == null || !this.operation_time.equals(__self.operation_time)))
            ret.operation_time = this.operation_time;
        if (this.operation != null && (__self == null || !this.operation.equals(__self.operation)))
            ret.operation = this.operation;
        return ret;
    }
    @Override
    public void set(DatabaseBackground_operation_log value) {
        this.log_id = value.log_id;
        this.operate_person = value.operate_person;
        this.operation_time = value.operation_time;
        this.operation = value.operation;
    }
    @Override
    public boolean isEmpty() {
        if (this.log_id != null) return false;
        if (this.operate_person != null) return false;
        if (this.operation_time != null) return false;
        if (this.operation != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.log_id = null;
        this.operate_person = null;
        this.operation_time = null;
        this.operation = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.log_id != null)
            ret += ("log_id = " + this.log_id.toString() + " ");
        if (this.operate_person != null)
            ret += ("operate_person = " + this.operate_person.toString() + " ");
        if (this.operation_time != null)
            ret += ("operation_time = " + this.operation_time.toString() + " ");
        if (this.operation != null)
            ret += ("operation = " + this.operation.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "background_operation_log";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "log_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return log_id;
    }
}