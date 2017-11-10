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
//================玩家设备推送列表 推送唯一标识===========================
@SuppressWarnings("unused")
public class DatabaseDeviceToken implements DatabaseTableDataBase<DatabaseDeviceToken>,Serializable {
    public static final String TableName = "deviceToken";
    /** 设备ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** 设备唯一标识 */
    @DatabaseFieldAttribute(fieldName = "deviceUniqueIdentifier",fieldType = String.class,arrayType = Byte.class)
    public String deviceUniqueIdentifier = null;
    public String getDeviceUniqueIdentifier() {return deviceUniqueIdentifier;}
    /** 设备操作系统 */
    @DatabaseFieldAttribute(fieldName = "platform",fieldType = String.class,arrayType = gameserver.network.protos.game.CommonProto.PLATFORM_TYPE.class)
    public gameserver.network.protos.game.CommonProto.PLATFORM_TYPE platform = null;
    public gameserver.network.protos.game.CommonProto.PLATFORM_TYPE getPlatform() {return platform;}
    /** 设备deviceToken 消息推送使用 */
    @DatabaseFieldAttribute(fieldName = "device_token",fieldType = String.class,arrayType = Byte.class)
    public String device_token = null;
    public String getDevice_token() {return device_token;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseDeviceToken __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseDeviceToken();
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
    public DatabaseDeviceToken diff()
    {
        DatabaseDeviceToken ret = new DatabaseDeviceToken();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.deviceUniqueIdentifier != null && (__self == null || !this.deviceUniqueIdentifier.equals(__self.deviceUniqueIdentifier)))
            ret.deviceUniqueIdentifier = this.deviceUniqueIdentifier;
        if (this.platform != null && (__self == null || !this.platform.equals(__self.platform)))
            ret.platform = this.platform;
        if (this.device_token != null && (__self == null || !this.device_token.equals(__self.device_token)))
            ret.device_token = this.device_token;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        return ret;
    }
    @Override
    public void set(DatabaseDeviceToken value) {
        this.id = value.id;
        this.deviceUniqueIdentifier = value.deviceUniqueIdentifier;
        this.platform = value.platform;
        this.device_token = value.device_token;
        this.player_id = value.player_id;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.deviceUniqueIdentifier != null) return false;
        if (this.platform != null) return false;
        if (this.device_token != null) return false;
        if (this.player_id != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.deviceUniqueIdentifier = null;
        this.platform = null;
        this.device_token = null;
        this.player_id = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.deviceUniqueIdentifier != null)
            ret += ("deviceUniqueIdentifier = " + this.deviceUniqueIdentifier.toString() + " ");
        if (this.platform != null)
            ret += ("platform = " + this.platform.toString() + " ");
        if (this.device_token != null)
            ret += ("device_token = " + this.device_token.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "deviceToken";
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