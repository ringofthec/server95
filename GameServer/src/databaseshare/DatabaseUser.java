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
//================玩家信息表===========================
@SuppressWarnings("unused")
public class DatabaseUser implements DatabaseTableDataBase<DatabaseUser>,Serializable {
    public static final String TableName = "user";
    /** 账号 */
    @DatabaseFieldAttribute(fieldName = "account",fieldType = String.class,arrayType = Byte.class)
    public String account = null;
    public String getAccount() {return account;}
    /** 密码 */
    @DatabaseFieldAttribute(fieldName = "password",fieldType = String.class,arrayType = Byte.class)
    public String password = null;
    public String getPassword() {return password;}
    /** 权限，第一位：修改玩家属性，第二位：发送邮件,第三位：封号 */
    @DatabaseFieldAttribute(fieldName = "rool",fieldType = String.class,arrayType = Byte.class)
    public String rool = null;
    public String getRool() {return rool;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseUser __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseUser();
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
    public DatabaseUser diff()
    {
        DatabaseUser ret = new DatabaseUser();
        if (this.account != null && (__self == null || !this.account.equals(__self.account)))
            ret.account = this.account;
        if (this.password != null && (__self == null || !this.password.equals(__self.password)))
            ret.password = this.password;
        if (this.rool != null && (__self == null || !this.rool.equals(__self.rool)))
            ret.rool = this.rool;
        return ret;
    }
    @Override
    public void set(DatabaseUser value) {
        this.account = value.account;
        this.password = value.password;
        this.rool = value.rool;
    }
    @Override
    public boolean isEmpty() {
        if (this.account != null) return false;
        if (this.password != null) return false;
        if (this.rool != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.account = null;
        this.password = null;
        this.rool = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.account != null)
            ret += ("account = " + this.account.toString() + " ");
        if (this.password != null)
            ret += ("password = " + this.password.toString() + " ");
        if (this.rool != null)
            ret += ("rool = " + this.rool.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "user";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "account";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return account;
    }
}