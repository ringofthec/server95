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
public class DatabaseWhite_list implements DatabaseTableDataBase<DatabaseWhite_list>,Serializable {
    public static final String TableName = "white_list";
    /** id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** 帐号注册的设备唯一标识 */
    @DatabaseFieldAttribute(fieldName = "create_device_unique_identifier",fieldType = String.class,arrayType = Byte.class)
    public String create_device_unique_identifier = null;
    public String getCreate_device_unique_identifier() {return create_device_unique_identifier;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseWhite_list __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseWhite_list();
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
    public DatabaseWhite_list diff()
    {
        DatabaseWhite_list ret = new DatabaseWhite_list();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.create_device_unique_identifier != null && (__self == null || !this.create_device_unique_identifier.equals(__self.create_device_unique_identifier)))
            ret.create_device_unique_identifier = this.create_device_unique_identifier;
        return ret;
    }
    @Override
    public void set(DatabaseWhite_list value) {
        this.id = value.id;
        this.create_device_unique_identifier = value.create_device_unique_identifier;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.create_device_unique_identifier != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.create_device_unique_identifier = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.create_device_unique_identifier != null)
            ret += ("create_device_unique_identifier = " + this.create_device_unique_identifier.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "white_list";
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