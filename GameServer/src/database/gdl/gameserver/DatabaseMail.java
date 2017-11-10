// 本文件是自动生成，不要手动修改
package database.gdl.gameserver;
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
//================邮件===========================
@SuppressWarnings("unused")
public class DatabaseMail implements DatabaseTableDataBase<DatabaseMail>,Serializable {
    public static final String TableName = "mail";
    /** 邮件唯一id */
    @DatabaseFieldAttribute(fieldName = "mail_id",fieldType = Long.class,arrayType = Byte.class)
    public Long mail_id = null;
    public Long getMail_id() {return mail_id;}
    /** 邮件的所有者 */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
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
    @DatabaseFieldAttribute(fieldName = "item_info",fieldType = String.class,arrayType = CustomMailItemInfo.class)
    public List<CustomMailItemInfo> item_info = null;
    public List<CustomMailItemInfo> getItem_info() {return item_info;}
    /** 是否领取，1:领取，0：没有领取 */
    @DatabaseFieldAttribute(fieldName = "is_recv",fieldType = Integer.class,arrayType = Byte.class)
    public Integer is_recv = null;
    public Integer getIs_recv() {return is_recv;}
    /** 是否读取 */
    @DatabaseFieldAttribute(fieldName = "is_read",fieldType = Integer.class,arrayType = Byte.class)
    public Integer is_read = null;
    public Integer getIs_read() {return is_read;}
    /** 邮件创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 邮件过期时间 */
    @DatabaseFieldAttribute(fieldName = "expire_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long expire_time = null;
    public Long getExpire_time() {return expire_time;}
    /** 是否删除 */
    @DatabaseFieldAttribute(fieldName = "is_delete",fieldType = Integer.class,arrayType = Byte.class)
    public Integer is_delete = null;
    public Integer getIs_delete() {return is_delete;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseMail __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseMail();
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
    public DatabaseMail diff()
    {
        DatabaseMail ret = new DatabaseMail();
        if (this.mail_id != null && (__self == null || !this.mail_id.equals(__self.mail_id)))
            ret.mail_id = this.mail_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.mail_sender != null && (__self == null || !this.mail_sender.equals(__self.mail_sender)))
            ret.mail_sender = this.mail_sender;
        if (this.mail_title != null && (__self == null || !this.mail_title.equals(__self.mail_title)))
            ret.mail_title = this.mail_title;
        if (this.mail_comment != null && (__self == null || !this.mail_comment.equals(__self.mail_comment)))
            ret.mail_comment = this.mail_comment;
        if (this.item_info != null && (__self == null || !this.item_info.equals(__self.item_info)))
            ret.item_info = this.item_info;
        if (this.is_recv != null && (__self == null || !this.is_recv.equals(__self.is_recv)))
            ret.is_recv = this.is_recv;
        if (this.is_read != null && (__self == null || !this.is_read.equals(__self.is_read)))
            ret.is_read = this.is_read;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.expire_time != null && (__self == null || !this.expire_time.equals(__self.expire_time)))
            ret.expire_time = this.expire_time;
        if (this.is_delete != null && (__self == null || !this.is_delete.equals(__self.is_delete)))
            ret.is_delete = this.is_delete;
        return ret;
    }
    @Override
    public void set(DatabaseMail value) {
        this.mail_id = value.mail_id;
        this.player_id = value.player_id;
        this.mail_sender = value.mail_sender;
        this.mail_title = value.mail_title;
        this.mail_comment = value.mail_comment;
        this.item_info = Utility.cloneList(value.item_info);
        this.is_recv = value.is_recv;
        this.is_read = value.is_read;
        this.create_time = value.create_time;
        this.expire_time = value.expire_time;
        this.is_delete = value.is_delete;
    }
    @Override
    public boolean isEmpty() {
        if (this.mail_id != null) return false;
        if (this.player_id != null) return false;
        if (this.mail_sender != null) return false;
        if (this.mail_title != null) return false;
        if (this.mail_comment != null) return false;
        if (this.item_info != null) return false;
        if (this.is_recv != null) return false;
        if (this.is_read != null) return false;
        if (this.create_time != null) return false;
        if (this.expire_time != null) return false;
        if (this.is_delete != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.mail_id = null;
        this.player_id = null;
        this.mail_sender = null;
        this.mail_title = null;
        this.mail_comment = null;
        this.item_info = null;
        this.is_recv = null;
        this.is_read = null;
        this.create_time = null;
        this.expire_time = null;
        this.is_delete = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.mail_id != null)
            ret += ("mail_id = " + this.mail_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.mail_sender != null)
            ret += ("mail_sender = " + this.mail_sender.toString() + " ");
        if (this.mail_title != null)
            ret += ("mail_title = " + this.mail_title.toString() + " ");
        if (this.mail_comment != null)
            ret += ("mail_comment = " + this.mail_comment.toString() + " ");
        if (this.item_info != null)
            ret += ("item_info = " + this.item_info.toString() + " ");
        if (this.is_recv != null)
            ret += ("is_recv = " + this.is_recv.toString() + " ");
        if (this.is_read != null)
            ret += ("is_read = " + this.is_read.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.expire_time != null)
            ret += ("expire_time = " + this.expire_time.toString() + " ");
        if (this.is_delete != null)
            ret += ("is_delete = " + this.is_delete.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "mail";
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