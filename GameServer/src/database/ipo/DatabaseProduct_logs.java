// 本文件是自动生成，不要手动修改
package database.ipo;
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
//================产品表===========================
@SuppressWarnings("unused")
public class DatabaseProduct_logs implements DatabaseTableDataBase<DatabaseProduct_logs>,Serializable {
    public static final String TableName = "product_logs";
    /** 项目id */
    @DatabaseFieldAttribute(fieldName = "project_id",fieldType = String.class,arrayType = Byte.class)
    public String project_id = null;
    public String getProject_id() {return project_id;}
    /** 服务器ID */
    @DatabaseFieldAttribute(fieldName = "server_id",fieldType = String.class,arrayType = Byte.class)
    public String server_id = null;
    public String getServer_id() {return server_id;}
    /** OPEN UDID */
    @DatabaseFieldAttribute(fieldName = "open_udid",fieldType = String.class,arrayType = Byte.class)
    public String open_udid = null;
    public String getOpen_udid() {return open_udid;}
    /** 用户ip */
    @DatabaseFieldAttribute(fieldName = "ip",fieldType = String.class,arrayType = Byte.class)
    public String ip = null;
    public String getIp() {return ip;}
    /** 用户id */
    @DatabaseFieldAttribute(fieldName = "user_id",fieldType = Long.class,arrayType = Byte.class)
    public Long user_id = null;
    public Long getUser_id() {return user_id;}
    /** 用户名 */
    @DatabaseFieldAttribute(fieldName = "user_name",fieldType = String.class,arrayType = Byte.class)
    public String user_name = null;
    public String getUser_name() {return user_name;}
    /** 事件时间 */
    @DatabaseFieldAttribute(fieldName = "created_at",fieldType = Timestamp.class,arrayType = Long.class)
    public Long created_at = null;
    public Long getCreated_at() {return created_at;}
    /** 插入的时间戳（单位是10微妙） */
    @DatabaseFieldAttribute(fieldName = "created_ts",fieldType = Long.class,arrayType = Byte.class)
    public Long created_ts = null;
    public Long getCreated_ts() {return created_ts;}
    /** 流水号 */
    @DatabaseFieldAttribute(fieldName = "serial_no",fieldType = Long.class,arrayType = Byte.class)
    public Long serial_no = null;
    public Long getSerial_no() {return serial_no;}
    /** 增加还是减少1: add 2:reduce */
    @DatabaseFieldAttribute(fieldName = "log_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer log_type = null;
    public Integer getLog_type() {return log_type;}
    /** 虚拟货币类型，只在log_type为1（增加）的时候有意义 */
    @DatabaseFieldAttribute(fieldName = "vm_type",fieldType = Integer.class,arrayType = Byte.class)
    public Integer vm_type = null;
    public Integer getVm_type() {return vm_type;}
    /** 虚拟货币数量。 */
    @DatabaseFieldAttribute(fieldName = "vm_num",fieldType = Long.class,arrayType = Byte.class)
    public Long vm_num = null;
    public Long getVm_num() {return vm_num;}
    /** 关联项目 */
    @DatabaseFieldAttribute(fieldName = "chain",fieldType = String.class,arrayType = Byte.class)
    public String chain = null;
    public String getChain() {return chain;}
    /** 增加或减少的渠道，详见channel定义表 */
    @DatabaseFieldAttribute(fieldName = "channel",fieldType = Integer.class,arrayType = Byte.class)
    public Integer channel = null;
    public Integer getChannel() {return channel;}
    /** 事件完成之后产品的余额 */
    @DatabaseFieldAttribute(fieldName = "balance",fieldType = Long.class,arrayType = Byte.class)
    public Long balance = null;
    public Long getBalance() {return balance;}
    /** 产品id，可以唯一标识产品的字符串。如果是道具，则和原来的item_name一样。如果是服务，则以service_为前缀加上服务的名字。 */
    @DatabaseFieldAttribute(fieldName = "product_id",fieldType = String.class,arrayType = Byte.class)
    public String product_id = null;
    public String getProduct_id() {return product_id;}
    /** 产品数量 */
    @DatabaseFieldAttribute(fieldName = "product_num",fieldType = Long.class,arrayType = Byte.class)
    public Long product_num = null;
    public Long getProduct_num() {return product_num;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseProduct_logs __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseProduct_logs();
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
    public DatabaseProduct_logs diff()
    {
        DatabaseProduct_logs ret = new DatabaseProduct_logs();
        if (this.project_id != null && (__self == null || !this.project_id.equals(__self.project_id)))
            ret.project_id = this.project_id;
        if (this.server_id != null && (__self == null || !this.server_id.equals(__self.server_id)))
            ret.server_id = this.server_id;
        if (this.open_udid != null && (__self == null || !this.open_udid.equals(__self.open_udid)))
            ret.open_udid = this.open_udid;
        if (this.ip != null && (__self == null || !this.ip.equals(__self.ip)))
            ret.ip = this.ip;
        if (this.user_id != null && (__self == null || !this.user_id.equals(__self.user_id)))
            ret.user_id = this.user_id;
        if (this.user_name != null && (__self == null || !this.user_name.equals(__self.user_name)))
            ret.user_name = this.user_name;
        if (this.created_at != null && (__self == null || !this.created_at.equals(__self.created_at)))
            ret.created_at = this.created_at;
        if (this.created_ts != null && (__self == null || !this.created_ts.equals(__self.created_ts)))
            ret.created_ts = this.created_ts;
        if (this.serial_no != null && (__self == null || !this.serial_no.equals(__self.serial_no)))
            ret.serial_no = this.serial_no;
        if (this.log_type != null && (__self == null || !this.log_type.equals(__self.log_type)))
            ret.log_type = this.log_type;
        if (this.vm_type != null && (__self == null || !this.vm_type.equals(__self.vm_type)))
            ret.vm_type = this.vm_type;
        if (this.vm_num != null && (__self == null || !this.vm_num.equals(__self.vm_num)))
            ret.vm_num = this.vm_num;
        if (this.chain != null && (__self == null || !this.chain.equals(__self.chain)))
            ret.chain = this.chain;
        if (this.channel != null && (__self == null || !this.channel.equals(__self.channel)))
            ret.channel = this.channel;
        if (this.balance != null && (__self == null || !this.balance.equals(__self.balance)))
            ret.balance = this.balance;
        if (this.product_id != null && (__self == null || !this.product_id.equals(__self.product_id)))
            ret.product_id = this.product_id;
        if (this.product_num != null && (__self == null || !this.product_num.equals(__self.product_num)))
            ret.product_num = this.product_num;
        return ret;
    }
    @Override
    public void set(DatabaseProduct_logs value) {
        this.project_id = value.project_id;
        this.server_id = value.server_id;
        this.open_udid = value.open_udid;
        this.ip = value.ip;
        this.user_id = value.user_id;
        this.user_name = value.user_name;
        this.created_at = value.created_at;
        this.created_ts = value.created_ts;
        this.serial_no = value.serial_no;
        this.log_type = value.log_type;
        this.vm_type = value.vm_type;
        this.vm_num = value.vm_num;
        this.chain = value.chain;
        this.channel = value.channel;
        this.balance = value.balance;
        this.product_id = value.product_id;
        this.product_num = value.product_num;
    }
    @Override
    public boolean isEmpty() {
        if (this.project_id != null) return false;
        if (this.server_id != null) return false;
        if (this.open_udid != null) return false;
        if (this.ip != null) return false;
        if (this.user_id != null) return false;
        if (this.user_name != null) return false;
        if (this.created_at != null) return false;
        if (this.created_ts != null) return false;
        if (this.serial_no != null) return false;
        if (this.log_type != null) return false;
        if (this.vm_type != null) return false;
        if (this.vm_num != null) return false;
        if (this.chain != null) return false;
        if (this.channel != null) return false;
        if (this.balance != null) return false;
        if (this.product_id != null) return false;
        if (this.product_num != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.project_id = null;
        this.server_id = null;
        this.open_udid = null;
        this.ip = null;
        this.user_id = null;
        this.user_name = null;
        this.created_at = null;
        this.created_ts = null;
        this.serial_no = null;
        this.log_type = null;
        this.vm_type = null;
        this.vm_num = null;
        this.chain = null;
        this.channel = null;
        this.balance = null;
        this.product_id = null;
        this.product_num = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.project_id != null)
            ret += ("project_id = " + this.project_id.toString() + " ");
        if (this.server_id != null)
            ret += ("server_id = " + this.server_id.toString() + " ");
        if (this.open_udid != null)
            ret += ("open_udid = " + this.open_udid.toString() + " ");
        if (this.ip != null)
            ret += ("ip = " + this.ip.toString() + " ");
        if (this.user_id != null)
            ret += ("user_id = " + this.user_id.toString() + " ");
        if (this.user_name != null)
            ret += ("user_name = " + this.user_name.toString() + " ");
        if (this.created_at != null)
            ret += ("created_at = " + this.created_at.toString() + " ");
        if (this.created_ts != null)
            ret += ("created_ts = " + this.created_ts.toString() + " ");
        if (this.serial_no != null)
            ret += ("serial_no = " + this.serial_no.toString() + " ");
        if (this.log_type != null)
            ret += ("log_type = " + this.log_type.toString() + " ");
        if (this.vm_type != null)
            ret += ("vm_type = " + this.vm_type.toString() + " ");
        if (this.vm_num != null)
            ret += ("vm_num = " + this.vm_num.toString() + " ");
        if (this.chain != null)
            ret += ("chain = " + this.chain.toString() + " ");
        if (this.channel != null)
            ret += ("channel = " + this.channel.toString() + " ");
        if (this.balance != null)
            ret += ("balance = " + this.balance.toString() + " ");
        if (this.product_id != null)
            ret += ("product_id = " + this.product_id.toString() + " ");
        if (this.product_num != null)
            ret += ("product_num = " + this.product_num.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "product_logs";
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