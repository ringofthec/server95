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
//================id===========================
@SuppressWarnings("unused")
public class DatabaseActive implements DatabaseTableDataBase<DatabaseActive>,Serializable {
    public static final String TableName = "active";
    /** id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** active_id */
    @DatabaseFieldAttribute(fieldName = "active_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer active_id = null;
    public Integer getActive_id() {return active_id;}
    /** 开始时间 */
    @DatabaseFieldAttribute(fieldName = "begin_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long begin_time = null;
    public Long getBegin_time() {return begin_time;}
    /** 结束时间 */
    @DatabaseFieldAttribute(fieldName = "end_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long end_time = null;
    public Long getEnd_time() {return end_time;}
    /** 有效 */
    @DatabaseFieldAttribute(fieldName = "enable",fieldType = Integer.class,arrayType = Byte.class)
    public Integer enable = null;
    public Integer getEnable() {return enable;}
    /** 注释 */
    @DatabaseFieldAttribute(fieldName = "comment",fieldType = String.class,arrayType = Byte.class)
    public String comment = null;
    public String getComment() {return comment;}
    /** 参数1 */
    @DatabaseFieldAttribute(fieldName = "param1",fieldType = Integer.class,arrayType = Byte.class)
    public Integer param1 = null;
    public Integer getParam1() {return param1;}
    /** 参数2 */
    @DatabaseFieldAttribute(fieldName = "param2",fieldType = Integer.class,arrayType = Byte.class)
    public Integer param2 = null;
    public Integer getParam2() {return param2;}
    /** 附件信息 */
    @DatabaseFieldAttribute(fieldName = "active_values",fieldType = String.class,arrayType = CustomActiveValues.class)
    public List<CustomActiveValues> active_values = null;
    public List<CustomActiveValues> getActive_values() {return active_values;}
    /** 英语 */
    @DatabaseFieldAttribute(fieldName = "active_english",fieldType = String.class,arrayType = CustomActiveContext.class)
    public List<CustomActiveContext> active_english = null;
    public List<CustomActiveContext> getActive_english() {return active_english;}
    /** 中文 */
    @DatabaseFieldAttribute(fieldName = "active_chinese",fieldType = String.class,arrayType = CustomActiveContext.class)
    public List<CustomActiveContext> active_chinese = null;
    public List<CustomActiveContext> getActive_chinese() {return active_chinese;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseActive __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseActive();
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
    public DatabaseActive diff()
    {
        DatabaseActive ret = new DatabaseActive();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.active_id != null && (__self == null || !this.active_id.equals(__self.active_id)))
            ret.active_id = this.active_id;
        if (this.begin_time != null && (__self == null || !this.begin_time.equals(__self.begin_time)))
            ret.begin_time = this.begin_time;
        if (this.end_time != null && (__self == null || !this.end_time.equals(__self.end_time)))
            ret.end_time = this.end_time;
        if (this.enable != null && (__self == null || !this.enable.equals(__self.enable)))
            ret.enable = this.enable;
        if (this.comment != null && (__self == null || !this.comment.equals(__self.comment)))
            ret.comment = this.comment;
        if (this.param1 != null && (__self == null || !this.param1.equals(__self.param1)))
            ret.param1 = this.param1;
        if (this.param2 != null && (__self == null || !this.param2.equals(__self.param2)))
            ret.param2 = this.param2;
        if (this.active_values != null && (__self == null || !this.active_values.equals(__self.active_values)))
            ret.active_values = this.active_values;
        if (this.active_english != null && (__self == null || !this.active_english.equals(__self.active_english)))
            ret.active_english = this.active_english;
        if (this.active_chinese != null && (__self == null || !this.active_chinese.equals(__self.active_chinese)))
            ret.active_chinese = this.active_chinese;
        return ret;
    }
    @Override
    public void set(DatabaseActive value) {
        this.id = value.id;
        this.active_id = value.active_id;
        this.begin_time = value.begin_time;
        this.end_time = value.end_time;
        this.enable = value.enable;
        this.comment = value.comment;
        this.param1 = value.param1;
        this.param2 = value.param2;
        this.active_values = Utility.cloneList(value.active_values);
        this.active_english = Utility.cloneList(value.active_english);
        this.active_chinese = Utility.cloneList(value.active_chinese);
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.active_id != null) return false;
        if (this.begin_time != null) return false;
        if (this.end_time != null) return false;
        if (this.enable != null) return false;
        if (this.comment != null) return false;
        if (this.param1 != null) return false;
        if (this.param2 != null) return false;
        if (this.active_values != null) return false;
        if (this.active_english != null) return false;
        if (this.active_chinese != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.active_id = null;
        this.begin_time = null;
        this.end_time = null;
        this.enable = null;
        this.comment = null;
        this.param1 = null;
        this.param2 = null;
        this.active_values = null;
        this.active_english = null;
        this.active_chinese = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.active_id != null)
            ret += ("active_id = " + this.active_id.toString() + " ");
        if (this.begin_time != null)
            ret += ("begin_time = " + this.begin_time.toString() + " ");
        if (this.end_time != null)
            ret += ("end_time = " + this.end_time.toString() + " ");
        if (this.enable != null)
            ret += ("enable = " + this.enable.toString() + " ");
        if (this.comment != null)
            ret += ("comment = " + this.comment.toString() + " ");
        if (this.param1 != null)
            ret += ("param1 = " + this.param1.toString() + " ");
        if (this.param2 != null)
            ret += ("param2 = " + this.param2.toString() + " ");
        if (this.active_values != null)
            ret += ("active_values = " + this.active_values.toString() + " ");
        if (this.active_english != null)
            ret += ("active_english = " + this.active_english.toString() + " ");
        if (this.active_chinese != null)
            ret += ("active_chinese = " + this.active_chinese.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "active";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (active_values != null && active_values.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : active_values) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    active_values.remove(data);
		    }
        }
        if (active_english != null && active_english.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : active_english) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    active_english.remove(data);
		    }
        }
        if (active_chinese != null && active_chinese.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : active_chinese) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    active_chinese.remove(data);
		    }
        }
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