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
//================玩家兑奖等级表===========================
@SuppressWarnings("unused")
public class DatabaseCashs implements DatabaseTableDataBase<DatabaseCashs>,Serializable {
    public static final String TableName = "cashs";
    /** 数据库ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer id = null;
    public Integer getId() {return id;}
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 奖品id */
    @DatabaseFieldAttribute(fieldName = "rid",fieldType = Integer.class,arrayType = Byte.class)
    public Integer rid = null;
    public Integer getRid() {return rid;}
    /** 玩家名字 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 玩家电话 */
    @DatabaseFieldAttribute(fieldName = "phone",fieldType = String.class,arrayType = Byte.class)
    public String phone = null;
    public String getPhone() {return phone;}
    /** 玩家地址 */
    @DatabaseFieldAttribute(fieldName = "address",fieldType = String.class,arrayType = Byte.class)
    public String address = null;
    public String getAddress() {return address;}
    /** 微信 */
    @DatabaseFieldAttribute(fieldName = "weixin",fieldType = String.class,arrayType = Byte.class)
    public String weixin = null;
    public String getWeixin() {return weixin;}
    /** 状态 */
    @DatabaseFieldAttribute(fieldName = "status",fieldType = Integer.class,arrayType = Byte.class)
    public Integer status = null;
    public Integer getStatus() {return status;}
    /** 时间 */
    @DatabaseFieldAttribute(fieldName = "ctime",fieldType = Timestamp.class,arrayType = Long.class)
    public Long ctime = null;
    public Long getCtime() {return ctime;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseCashs __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseCashs();
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
    public DatabaseCashs diff()
    {
        DatabaseCashs ret = new DatabaseCashs();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.rid != null && (__self == null || !this.rid.equals(__self.rid)))
            ret.rid = this.rid;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.phone != null && (__self == null || !this.phone.equals(__self.phone)))
            ret.phone = this.phone;
        if (this.address != null && (__self == null || !this.address.equals(__self.address)))
            ret.address = this.address;
        if (this.weixin != null && (__self == null || !this.weixin.equals(__self.weixin)))
            ret.weixin = this.weixin;
        if (this.status != null && (__self == null || !this.status.equals(__self.status)))
            ret.status = this.status;
        if (this.ctime != null && (__self == null || !this.ctime.equals(__self.ctime)))
            ret.ctime = this.ctime;
        return ret;
    }
    @Override
    public void set(DatabaseCashs value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.rid = value.rid;
        this.name = value.name;
        this.phone = value.phone;
        this.address = value.address;
        this.weixin = value.weixin;
        this.status = value.status;
        this.ctime = value.ctime;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.rid != null) return false;
        if (this.name != null) return false;
        if (this.phone != null) return false;
        if (this.address != null) return false;
        if (this.weixin != null) return false;
        if (this.status != null) return false;
        if (this.ctime != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.rid = null;
        this.name = null;
        this.phone = null;
        this.address = null;
        this.weixin = null;
        this.status = null;
        this.ctime = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.rid != null)
            ret += ("rid = " + this.rid.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.phone != null)
            ret += ("phone = " + this.phone.toString() + " ");
        if (this.address != null)
            ret += ("address = " + this.address.toString() + " ");
        if (this.weixin != null)
            ret += ("weixin = " + this.weixin.toString() + " ");
        if (this.status != null)
            ret += ("status = " + this.status.toString() + " ");
        if (this.ctime != null)
            ret += ("ctime = " + this.ctime.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "cashs";
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