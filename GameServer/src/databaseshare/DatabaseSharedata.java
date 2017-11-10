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
//===========================================
@SuppressWarnings("unused")
public class DatabaseSharedata implements DatabaseTableDataBase<DatabaseSharedata>,Serializable {
    public static final String TableName = "sharedata";
    /** ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = String.class,arrayType = Byte.class)
    public String id = null;
    public String getId() {return id;}
    /** 类型 */
    @DatabaseFieldAttribute(fieldName = "type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer type = null;
    public Integer getType() {return type;}
    /** 对应的分流服务器ID */
    @DatabaseFieldAttribute(fieldName = "serverUid",fieldType = String.class,arrayType = Byte.class)
    public String serverUid = null;
    public String getServerUid() {return serverUid;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseSharedata __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseSharedata();
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
    public DatabaseSharedata diff()
    {
        DatabaseSharedata ret = new DatabaseSharedata();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.type != null && (__self == null || !this.type.equals(__self.type)))
            ret.type = this.type;
        if (this.serverUid != null && (__self == null || !this.serverUid.equals(__self.serverUid)))
            ret.serverUid = this.serverUid;
        return ret;
    }
    @Override
    public void set(DatabaseSharedata value) {
        this.id = value.id;
        this.type = value.type;
        this.serverUid = value.serverUid;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.type != null) return false;
        if (this.serverUid != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.type = null;
        this.serverUid = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.type != null)
            ret += ("type = " + this.type.toString() + " ");
        if (this.serverUid != null)
            ret += ("serverUid = " + this.serverUid.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "sharedata";
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