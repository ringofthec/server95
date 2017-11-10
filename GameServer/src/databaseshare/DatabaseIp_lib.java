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
public class DatabaseIp_lib implements DatabaseTableDataBase<DatabaseIp_lib>,Serializable {
    public static final String TableName = "ip_lib";
    /** id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** beginip */
    @DatabaseFieldAttribute(fieldName = "beginip",fieldType = Long.class,arrayType = Byte.class)
    public Long beginip = null;
    public Long getBeginip() {return beginip;}
    /** endip */
    @DatabaseFieldAttribute(fieldName = "endip",fieldType = Long.class,arrayType = Byte.class)
    public Long endip = null;
    public Long getEndip() {return endip;}
    /** addr */
    @DatabaseFieldAttribute(fieldName = "addr",fieldType = String.class,arrayType = Byte.class)
    public String addr = null;
    public String getAddr() {return addr;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseIp_lib __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseIp_lib();
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
    public DatabaseIp_lib diff()
    {
        DatabaseIp_lib ret = new DatabaseIp_lib();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.beginip != null && (__self == null || !this.beginip.equals(__self.beginip)))
            ret.beginip = this.beginip;
        if (this.endip != null && (__self == null || !this.endip.equals(__self.endip)))
            ret.endip = this.endip;
        if (this.addr != null && (__self == null || !this.addr.equals(__self.addr)))
            ret.addr = this.addr;
        return ret;
    }
    @Override
    public void set(DatabaseIp_lib value) {
        this.id = value.id;
        this.beginip = value.beginip;
        this.endip = value.endip;
        this.addr = value.addr;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.beginip != null) return false;
        if (this.endip != null) return false;
        if (this.addr != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.beginip = null;
        this.endip = null;
        this.addr = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.beginip != null)
            ret += ("beginip = " + this.beginip.toString() + " ");
        if (this.endip != null)
            ret += ("endip = " + this.endip.toString() + " ");
        if (this.addr != null)
            ret += ("addr = " + this.addr.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "ip_lib";
    }
    @Override
    public void check() {
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