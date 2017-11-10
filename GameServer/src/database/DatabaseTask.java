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
//================任务表===========================
@SuppressWarnings("unused")
public class DatabaseTask implements DatabaseTableDataBase<DatabaseTask>,Serializable {
    public static final String TableName = "task";
    /** 任务ID */
    @DatabaseFieldAttribute(fieldName = "task_id",fieldType = Long.class,arrayType = Byte.class)
    public Long task_id = null;
    public Long getTask_id() {return task_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 任务TableID */
    @DatabaseFieldAttribute(fieldName = "table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer table_id = null;
    public Integer getTable_id() {return table_id;}
    /** 任务需要的条件的数量或者次数 */
    @DatabaseFieldAttribute(fieldName = "number",fieldType = Integer.class,arrayType = Byte.class)
    public Integer number = null;
    public Integer getNumber() {return number;}
    /** 任务是否已完成 */
    @DatabaseFieldAttribute(fieldName = "complete",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean complete = null;
    public Boolean getComplete() {return complete;}
    /** 整条任务链是否已完成 */
    @DatabaseFieldAttribute(fieldName = "finished",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean finished = null;
    public Boolean getFinished() {return finished;}
    /** 接受任务链的时间 */
    @DatabaseFieldAttribute(fieldName = "accept_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long accept_time = null;
    public Long getAccept_time() {return accept_time;}
    /** 完成任务链的时间 */
    @DatabaseFieldAttribute(fieldName = "finshed_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long finshed_time = null;
    public Long getFinshed_time() {return finshed_time;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseTask __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseTask();
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
    public DatabaseTask diff()
    {
        DatabaseTask ret = new DatabaseTask();
        if (this.task_id != null && (__self == null || !this.task_id.equals(__self.task_id)))
            ret.task_id = this.task_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.table_id != null && (__self == null || !this.table_id.equals(__self.table_id)))
            ret.table_id = this.table_id;
        if (this.number != null && (__self == null || !this.number.equals(__self.number)))
            ret.number = this.number;
        if (this.complete != null && (__self == null || !this.complete.equals(__self.complete)))
            ret.complete = this.complete;
        if (this.finished != null && (__self == null || !this.finished.equals(__self.finished)))
            ret.finished = this.finished;
        if (this.accept_time != null && (__self == null || !this.accept_time.equals(__self.accept_time)))
            ret.accept_time = this.accept_time;
        if (this.finshed_time != null && (__self == null || !this.finshed_time.equals(__self.finshed_time)))
            ret.finshed_time = this.finshed_time;
        return ret;
    }
    @Override
    public void set(DatabaseTask value) {
        this.task_id = value.task_id;
        this.player_id = value.player_id;
        this.table_id = value.table_id;
        this.number = value.number;
        this.complete = value.complete;
        this.finished = value.finished;
        this.accept_time = value.accept_time;
        this.finshed_time = value.finshed_time;
    }
    @Override
    public boolean isEmpty() {
        if (this.task_id != null) return false;
        if (this.player_id != null) return false;
        if (this.table_id != null) return false;
        if (this.number != null) return false;
        if (this.complete != null) return false;
        if (this.finished != null) return false;
        if (this.accept_time != null) return false;
        if (this.finshed_time != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.task_id = null;
        this.player_id = null;
        this.table_id = null;
        this.number = null;
        this.complete = null;
        this.finished = null;
        this.accept_time = null;
        this.finshed_time = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.task_id != null)
            ret += ("task_id = " + this.task_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.table_id != null)
            ret += ("table_id = " + this.table_id.toString() + " ");
        if (this.number != null)
            ret += ("number = " + this.number.toString() + " ");
        if (this.complete != null)
            ret += ("complete = " + this.complete.toString() + " ");
        if (this.finished != null)
            ret += ("finished = " + this.finished.toString() + " ");
        if (this.accept_time != null)
            ret += ("accept_time = " + this.accept_time.toString() + " ");
        if (this.finshed_time != null)
            ret += ("finshed_time = " + this.finshed_time.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "task";
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