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
//================计划任务===========================
@SuppressWarnings("unused")
public class DatabasePlan_task implements DatabaseTableDataBase<DatabasePlan_task>,Serializable {
    public static final String TableName = "plan_task";
    /** 任务唯一key */
    @DatabaseFieldAttribute(fieldName = "plan_key",fieldType = String.class,arrayType = Byte.class)
    public String plan_key = null;
    public String getPlan_key() {return plan_key;}
    /** 执行时间 */
    @DatabaseFieldAttribute(fieldName = "enable_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long enable_time = null;
    public Long getEnable_time() {return enable_time;}
    /** 类型 */
    @DatabaseFieldAttribute(fieldName = "plan_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer plan_type = null;
    public Integer getPlan_type() {return plan_type;}
    /** 内容 */
    @DatabaseFieldAttribute(fieldName = "plan_context",fieldType = String.class,arrayType = Byte.class)
    public String plan_context = null;
    public String getPlan_context() {return plan_context;}
    /** 是否完成 */
    @DatabaseFieldAttribute(fieldName = "finish",fieldType = Integer.class,arrayType = Byte.class)
    public Integer finish = null;
    public Integer getFinish() {return finish;}
    /** 查询结果1 */
    @DatabaseFieldAttribute(fieldName = "res",fieldType = String.class,arrayType = Byte.class)
    public String res = null;
    public String getRes() {return res;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePlan_task __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePlan_task();
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
    public DatabasePlan_task diff()
    {
        DatabasePlan_task ret = new DatabasePlan_task();
        if (this.plan_key != null && (__self == null || !this.plan_key.equals(__self.plan_key)))
            ret.plan_key = this.plan_key;
        if (this.enable_time != null && (__self == null || !this.enable_time.equals(__self.enable_time)))
            ret.enable_time = this.enable_time;
        if (this.plan_type != null && (__self == null || !this.plan_type.equals(__self.plan_type)))
            ret.plan_type = this.plan_type;
        if (this.plan_context != null && (__self == null || !this.plan_context.equals(__self.plan_context)))
            ret.plan_context = this.plan_context;
        if (this.finish != null && (__self == null || !this.finish.equals(__self.finish)))
            ret.finish = this.finish;
        if (this.res != null && (__self == null || !this.res.equals(__self.res)))
            ret.res = this.res;
        return ret;
    }
    @Override
    public void set(DatabasePlan_task value) {
        this.plan_key = value.plan_key;
        this.enable_time = value.enable_time;
        this.plan_type = value.plan_type;
        this.plan_context = value.plan_context;
        this.finish = value.finish;
        this.res = value.res;
    }
    @Override
    public boolean isEmpty() {
        if (this.plan_key != null) return false;
        if (this.enable_time != null) return false;
        if (this.plan_type != null) return false;
        if (this.plan_context != null) return false;
        if (this.finish != null) return false;
        if (this.res != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.plan_key = null;
        this.enable_time = null;
        this.plan_type = null;
        this.plan_context = null;
        this.finish = null;
        this.res = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.plan_key != null)
            ret += ("plan_key = " + this.plan_key.toString() + " ");
        if (this.enable_time != null)
            ret += ("enable_time = " + this.enable_time.toString() + " ");
        if (this.plan_type != null)
            ret += ("plan_type = " + this.plan_type.toString() + " ");
        if (this.plan_context != null)
            ret += ("plan_context = " + this.plan_context.toString() + " ");
        if (this.finish != null)
            ret += ("finish = " + this.finish.toString() + " ");
        if (this.res != null)
            ret += ("res = " + this.res.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "plan_task";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "plan_key";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return plan_key;
    }
}