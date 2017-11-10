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
//================notice_id===========================
@SuppressWarnings("unused")
public class DatabaseNotice implements DatabaseTableDataBase<DatabaseNotice>,Serializable {
    public static final String TableName = "notice";
    /** notice_id */
    @DatabaseFieldAttribute(fieldName = "notice_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer notice_id = null;
    public Integer getNotice_id() {return notice_id;}
    /** 标题 */
    @DatabaseFieldAttribute(fieldName = "title_en",fieldType = String.class,arrayType = Byte.class)
    public String title_en = null;
    public String getTitle_en() {return title_en;}
    /** 公告内容 */
    @DatabaseFieldAttribute(fieldName = "content_en",fieldType = String.class,arrayType = Byte.class)
    public String content_en = null;
    public String getContent_en() {return content_en;}
    /** 时间 */
    @DatabaseFieldAttribute(fieldName = "time",fieldType = Timestamp.class,arrayType = Byte.class)
    public Timestamp time = null;
    public Timestamp getTime() {return time;}
    /** 标题 */
    @DatabaseFieldAttribute(fieldName = "title_cn",fieldType = String.class,arrayType = Byte.class)
    public String title_cn = null;
    public String getTitle_cn() {return title_cn;}
    /** 公告内容 */
    @DatabaseFieldAttribute(fieldName = "content_cn",fieldType = String.class,arrayType = Byte.class)
    public String content_cn = null;
    public String getContent_cn() {return content_cn;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseNotice __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseNotice();
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
    public DatabaseNotice diff()
    {
        DatabaseNotice ret = new DatabaseNotice();
        if (this.notice_id != null && (__self == null || !this.notice_id.equals(__self.notice_id)))
            ret.notice_id = this.notice_id;
        if (this.title_en != null && (__self == null || !this.title_en.equals(__self.title_en)))
            ret.title_en = this.title_en;
        if (this.content_en != null && (__self == null || !this.content_en.equals(__self.content_en)))
            ret.content_en = this.content_en;
        if (this.time != null && (__self == null || !this.time.equals(__self.time)))
            ret.time = this.time;
        if (this.title_cn != null && (__self == null || !this.title_cn.equals(__self.title_cn)))
            ret.title_cn = this.title_cn;
        if (this.content_cn != null && (__self == null || !this.content_cn.equals(__self.content_cn)))
            ret.content_cn = this.content_cn;
        return ret;
    }
    @Override
    public void set(DatabaseNotice value) {
        this.notice_id = value.notice_id;
        this.title_en = value.title_en;
        this.content_en = value.content_en;
        this.time = value.time;
        this.title_cn = value.title_cn;
        this.content_cn = value.content_cn;
    }
    @Override
    public boolean isEmpty() {
        if (this.notice_id != null) return false;
        if (this.title_en != null) return false;
        if (this.content_en != null) return false;
        if (this.time != null) return false;
        if (this.title_cn != null) return false;
        if (this.content_cn != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.notice_id = null;
        this.title_en = null;
        this.content_en = null;
        this.time = null;
        this.title_cn = null;
        this.content_cn = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.notice_id != null)
            ret += ("notice_id = " + this.notice_id.toString() + " ");
        if (this.title_en != null)
            ret += ("title_en = " + this.title_en.toString() + " ");
        if (this.content_en != null)
            ret += ("content_en = " + this.content_en.toString() + " ");
        if (this.time != null)
            ret += ("time = " + this.time.toString() + " ");
        if (this.title_cn != null)
            ret += ("title_cn = " + this.title_cn.toString() + " ");
        if (this.content_cn != null)
            ret += ("content_cn = " + this.content_cn.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "notice";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "notice_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return notice_id;
    }
}