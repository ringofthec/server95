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
//================服务器变量表===========================
@SuppressWarnings("unused")
public class DatabaseGs_server_var implements DatabaseTableDataBase<DatabaseGs_server_var>,Serializable {
    public static final String TableName = "gs_server_var";
    /** 变量名称 */
    @DatabaseFieldAttribute(fieldName = "var_key",fieldType = String.class,arrayType = Byte.class)
    public String var_key = null;
    public String getVar_key() {return var_key;}
    /** 变量值 */
    @DatabaseFieldAttribute(fieldName = "var_value",fieldType = String.class,arrayType = Byte.class)
    public String var_value = null;
    public String getVar_value() {return var_value;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseGs_server_var __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseGs_server_var();
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
    public DatabaseGs_server_var diff()
    {
        DatabaseGs_server_var ret = new DatabaseGs_server_var();
        if (this.var_key != null && (__self == null || !this.var_key.equals(__self.var_key)))
            ret.var_key = this.var_key;
        if (this.var_value != null && (__self == null || !this.var_value.equals(__self.var_value)))
            ret.var_value = this.var_value;
        return ret;
    }
    @Override
    public void set(DatabaseGs_server_var value) {
        this.var_key = value.var_key;
        this.var_value = value.var_value;
    }
    @Override
    public boolean isEmpty() {
        if (this.var_key != null) return false;
        if (this.var_value != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.var_key = null;
        this.var_value = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.var_key != null)
            ret += ("var_key = " + this.var_key.toString() + " ");
        if (this.var_value != null)
            ret += ("var_value = " + this.var_value.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "gs_server_var";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "var_key";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return var_key;
    }
}