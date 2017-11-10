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
//================全局邮件===========================
@SuppressWarnings("unused")
public class DatabaseGlob_mail implements DatabaseTableDataBase<DatabaseGlob_mail>,Serializable {
    public static final String TableName = "glob_mail";
    /** 邮件唯一id */
    @DatabaseFieldAttribute(fieldName = "mail_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer mail_id = null;
    public Integer getMail_id() {return mail_id;}
    /** 邮件发送者 */
    @DatabaseFieldAttribute(fieldName = "mail_sender",fieldType = String.class,arrayType = Byte.class)
    public String mail_sender = null;
    public String getMail_sender() {return mail_sender;}
    /** 邮件标题 */
    @DatabaseFieldAttribute(fieldName = "mail_title",fieldType = String.class,arrayType = Byte.class)
    public String mail_title = null;
    public String getMail_title() {return mail_title;}
    /** 邮件内容 */
    @DatabaseFieldAttribute(fieldName = "mail_comment",fieldType = String.class,arrayType = Byte.class)
    public String mail_comment = null;
    public String getMail_comment() {return mail_comment;}
    /** 物品信息 */
    @DatabaseFieldAttribute(fieldName = "item_info",fieldType = String.class,arrayType = CustomGlobMailItemInfo.class)
    public List<CustomGlobMailItemInfo> item_info = null;
    public List<CustomGlobMailItemInfo> getItem_info() {return item_info;}
    /** 邮件创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 邮件过期时间 */
    @DatabaseFieldAttribute(fieldName = "expire_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long expire_time = null;
    public Long getExpire_time() {return expire_time;}
    /** 筛选条件 */
    @DatabaseFieldAttribute(fieldName = "filter_value",fieldType = String.class,arrayType = Byte.class)
    public String filter_value = null;
    public String getFilter_value() {return filter_value;}
    /** 参数1 */
    @DatabaseFieldAttribute(fieldName = "param1",fieldType = Long.class,arrayType = Byte.class)
    public Long param1 = null;
    public Long getParam1() {return param1;}
    /** 参数2 */
    @DatabaseFieldAttribute(fieldName = "param2",fieldType = Long.class,arrayType = Byte.class)
    public Long param2 = null;
    public Long getParam2() {return param2;}
    /** 参数3 */
    @DatabaseFieldAttribute(fieldName = "param3",fieldType = String.class,arrayType = Byte.class)
    public String param3 = null;
    public String getParam3() {return param3;}
    /** 类型 */
    @DatabaseFieldAttribute(fieldName = "mail_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer mail_type = null;
    public Integer getMail_type() {return mail_type;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseGlob_mail __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseGlob_mail();
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
    public DatabaseGlob_mail diff()
    {
        DatabaseGlob_mail ret = new DatabaseGlob_mail();
        if (this.mail_id != null && (__self == null || !this.mail_id.equals(__self.mail_id)))
            ret.mail_id = this.mail_id;
        if (this.mail_sender != null && (__self == null || !this.mail_sender.equals(__self.mail_sender)))
            ret.mail_sender = this.mail_sender;
        if (this.mail_title != null && (__self == null || !this.mail_title.equals(__self.mail_title)))
            ret.mail_title = this.mail_title;
        if (this.mail_comment != null && (__self == null || !this.mail_comment.equals(__self.mail_comment)))
            ret.mail_comment = this.mail_comment;
        if (this.item_info != null && (__self == null || !this.item_info.equals(__self.item_info)))
            ret.item_info = this.item_info;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.expire_time != null && (__self == null || !this.expire_time.equals(__self.expire_time)))
            ret.expire_time = this.expire_time;
        if (this.filter_value != null && (__self == null || !this.filter_value.equals(__self.filter_value)))
            ret.filter_value = this.filter_value;
        if (this.param1 != null && (__self == null || !this.param1.equals(__self.param1)))
            ret.param1 = this.param1;
        if (this.param2 != null && (__self == null || !this.param2.equals(__self.param2)))
            ret.param2 = this.param2;
        if (this.param3 != null && (__self == null || !this.param3.equals(__self.param3)))
            ret.param3 = this.param3;
        if (this.mail_type != null && (__self == null || !this.mail_type.equals(__self.mail_type)))
            ret.mail_type = this.mail_type;
        return ret;
    }
    @Override
    public void set(DatabaseGlob_mail value) {
        this.mail_id = value.mail_id;
        this.mail_sender = value.mail_sender;
        this.mail_title = value.mail_title;
        this.mail_comment = value.mail_comment;
        this.item_info = Utility.cloneList(value.item_info);
        this.create_time = value.create_time;
        this.expire_time = value.expire_time;
        this.filter_value = value.filter_value;
        this.param1 = value.param1;
        this.param2 = value.param2;
        this.param3 = value.param3;
        this.mail_type = value.mail_type;
    }
    @Override
    public boolean isEmpty() {
        if (this.mail_id != null) return false;
        if (this.mail_sender != null) return false;
        if (this.mail_title != null) return false;
        if (this.mail_comment != null) return false;
        if (this.item_info != null) return false;
        if (this.create_time != null) return false;
        if (this.expire_time != null) return false;
        if (this.filter_value != null) return false;
        if (this.param1 != null) return false;
        if (this.param2 != null) return false;
        if (this.param3 != null) return false;
        if (this.mail_type != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.mail_id = null;
        this.mail_sender = null;
        this.mail_title = null;
        this.mail_comment = null;
        this.item_info = null;
        this.create_time = null;
        this.expire_time = null;
        this.filter_value = null;
        this.param1 = null;
        this.param2 = null;
        this.param3 = null;
        this.mail_type = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.mail_id != null)
            ret += ("mail_id = " + this.mail_id.toString() + " ");
        if (this.mail_sender != null)
            ret += ("mail_sender = " + this.mail_sender.toString() + " ");
        if (this.mail_title != null)
            ret += ("mail_title = " + this.mail_title.toString() + " ");
        if (this.mail_comment != null)
            ret += ("mail_comment = " + this.mail_comment.toString() + " ");
        if (this.item_info != null)
            ret += ("item_info = " + this.item_info.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.expire_time != null)
            ret += ("expire_time = " + this.expire_time.toString() + " ");
        if (this.filter_value != null)
            ret += ("filter_value = " + this.filter_value.toString() + " ");
        if (this.param1 != null)
            ret += ("param1 = " + this.param1.toString() + " ");
        if (this.param2 != null)
            ret += ("param2 = " + this.param2.toString() + " ");
        if (this.param3 != null)
            ret += ("param3 = " + this.param3.toString() + " ");
        if (this.mail_type != null)
            ret += ("mail_type = " + this.mail_type.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "glob_mail";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (item_info != null && item_info.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : item_info) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    item_info.remove(data);
		    }
        }
    }
    @Override
    public String getPrimaryKeyName() {
        return "mail_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return mail_id;
    }
}