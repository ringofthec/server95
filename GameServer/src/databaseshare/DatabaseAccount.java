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
//================帐号信息表===========================
@SuppressWarnings("unused")
public class DatabaseAccount implements DatabaseTableDataBase<DatabaseAccount>,Serializable {
    public static final String TableName = "account";
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 帐号UID */
    @DatabaseFieldAttribute(fieldName = "uid",fieldType = String.class,arrayType = Byte.class)
    public String uid = null;
    public String getUid() {return uid;}
    /** 是否是游客帐号 */
    @DatabaseFieldAttribute(fieldName = "passer",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean passer = null;
    public Boolean getPasser() {return passer;}
    /** 临时UID */
    @DatabaseFieldAttribute(fieldName = "temp_uid",fieldType = String.class,arrayType = Byte.class)
    public String temp_uid = null;
    public String getTemp_uid() {return temp_uid;}
    /** 渠道 */
    @DatabaseFieldAttribute(fieldName = "channel",fieldType = Integer.class,arrayType = Byte.class)
    public Integer channel = null;
    public Integer getChannel() {return channel;}
    /** 帐号创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 帐号创建IP */
    @DatabaseFieldAttribute(fieldName = "create_ip",fieldType = String.class,arrayType = Byte.class)
    public String create_ip = null;
    public String getCreate_ip() {return create_ip;}
    /** 帐号注册的设备操作系统 */
    @DatabaseFieldAttribute(fieldName = "create_platform",fieldType = String.class,arrayType = gameserver.network.protos.game.CommonProto.PLATFORM_TYPE.class)
    public gameserver.network.protos.game.CommonProto.PLATFORM_TYPE create_platform = null;
    public gameserver.network.protos.game.CommonProto.PLATFORM_TYPE getCreate_platform() {return create_platform;}
    /** 帐号注册的设备唯一标识 */
    @DatabaseFieldAttribute(fieldName = "create_device_unique_identifier",fieldType = String.class,arrayType = Byte.class)
    public String create_device_unique_identifier = null;
    public String getCreate_device_unique_identifier() {return create_device_unique_identifier;}
    /** 最后登录时间 */
    @DatabaseFieldAttribute(fieldName = "last_login_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_login_time = null;
    public Long getLast_login_time() {return last_login_time;}
    /** 最后登录IP */
    @DatabaseFieldAttribute(fieldName = "last_login_ip",fieldType = String.class,arrayType = Byte.class)
    public String last_login_ip = null;
    public String getLast_login_ip() {return last_login_ip;}
    /** 最后登录的设备操作系统 */
    @DatabaseFieldAttribute(fieldName = "last_login_platform",fieldType = String.class,arrayType = gameserver.network.protos.game.CommonProto.PLATFORM_TYPE.class)
    public gameserver.network.protos.game.CommonProto.PLATFORM_TYPE last_login_platform = null;
    public gameserver.network.protos.game.CommonProto.PLATFORM_TYPE getLast_login_platform() {return last_login_platform;}
    /** 最后登录设备的唯一标识 */
    @DatabaseFieldAttribute(fieldName = "last_device_unique_identifier",fieldType = String.class,arrayType = Byte.class)
    public String last_device_unique_identifier = null;
    public String getLast_device_unique_identifier() {return last_device_unique_identifier;}
    /** 帐号操作session */
    @DatabaseFieldAttribute(fieldName = "session",fieldType = String.class,arrayType = Byte.class)
    public String session = null;
    public String getSession() {return session;}
    /** 角色所在服务器 */
    @DatabaseFieldAttribute(fieldName = "serveruid",fieldType = String.class,arrayType = Byte.class)
    public String serveruid = null;
    public String getServeruid() {return serveruid;}
    /** 帐号类型GM 非GM */
    @DatabaseFieldAttribute(fieldName = "type",fieldType = Integer.class,arrayType = commonality.Common.ACCOUNT_TYPE.class)
    public commonality.Common.ACCOUNT_TYPE type = null;
    public commonality.Common.ACCOUNT_TYPE getType() {return type;}
    /** 角色所在的gamedb id */
    @DatabaseFieldAttribute(fieldName = "db_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer db_id = null;
    public Integer getDb_id() {return db_id;}
    /** 是否被封禁，1：被封号 0：正常 */
    @DatabaseFieldAttribute(fieldName = "banned",fieldType = Integer.class,arrayType = Byte.class)
    public Integer banned = null;
    public Integer getBanned() {return banned;}
    /** 被封号时长 */
    @DatabaseFieldAttribute(fieldName = "banned_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long banned_time = null;
    public Long getBanned_time() {return banned_time;}
    /** 国别 */
    @DatabaseFieldAttribute(fieldName = "nation",fieldType = String.class,arrayType = Byte.class)
    public String nation = null;
    public String getNation() {return nation;}
    /** 上次登录机型 */
    @DatabaseFieldAttribute(fieldName = "last_model",fieldType = String.class,arrayType = Byte.class)
    public String last_model = null;
    public String getLast_model() {return last_model;}
    /** 上次登录语言 */
    @DatabaseFieldAttribute(fieldName = "last_lang",fieldType = String.class,arrayType = Byte.class)
    public String last_lang = null;
    public String getLast_lang() {return last_lang;}
    /** 上次登录客户端版本 */
    @DatabaseFieldAttribute(fieldName = "last_version",fieldType = String.class,arrayType = Byte.class)
    public String last_version = null;
    public String getLast_version() {return last_version;}
    /** 上次登录操作系统 */
    @DatabaseFieldAttribute(fieldName = "last_opsys",fieldType = String.class,arrayType = Byte.class)
    public String last_opsys = null;
    public String getLast_opsys() {return last_opsys;}
    /** 上次登录操作系统语言 */
    @DatabaseFieldAttribute(fieldName = "last_opsys_lang",fieldType = String.class,arrayType = Byte.class)
    public String last_opsys_lang = null;
    public String getLast_opsys_lang() {return last_opsys_lang;}
    /** 绑定账号时的账号信息 */
    @DatabaseFieldAttribute(fieldName = "login_info",fieldType = String.class,arrayType = Byte.class)
    public String login_info = null;
    public String getLogin_info() {return login_info;}
    /** 是否在线 */
    @DatabaseFieldAttribute(fieldName = "online",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean online = null;
    public Boolean getOnline() {return online;}
    /** 尝试登录次数 */
    @DatabaseFieldAttribute(fieldName = "try_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer try_count = null;
    public Integer getTry_count() {return try_count;}
    /** 是否绑定 */
    @DatabaseFieldAttribute(fieldName = "isBind",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean isBind = null;
    public Boolean getIsBind() {return isBind;}
    /** 全局唯一ID */
    @DatabaseFieldAttribute(fieldName = "glob_id",fieldType = Long.class,arrayType = Byte.class)
    public Long glob_id = null;
    public Long getGlob_id() {return glob_id;}
    /** 临时UID */
    @DatabaseFieldAttribute(fieldName = "old_temp_uid",fieldType = String.class,arrayType = Byte.class)
    public String old_temp_uid = null;
    public String getOld_temp_uid() {return old_temp_uid;}
    /** 切换手机编码 */
    @DatabaseFieldAttribute(fieldName = "change_phone_code",fieldType = String.class,arrayType = Byte.class)
    public String change_phone_code = null;
    public String getChange_phone_code() {return change_phone_code;}
    /** 切换手机编码过期时间 */
    @DatabaseFieldAttribute(fieldName = "change_phone_code_expire_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long change_phone_code_expire_time = null;
    public Long getChange_phone_code_expire_time() {return change_phone_code_expire_time;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseAccount __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseAccount();
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
    public DatabaseAccount diff()
    {
        DatabaseAccount ret = new DatabaseAccount();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.uid != null && (__self == null || !this.uid.equals(__self.uid)))
            ret.uid = this.uid;
        if (this.passer != null && (__self == null || !this.passer.equals(__self.passer)))
            ret.passer = this.passer;
        if (this.temp_uid != null && (__self == null || !this.temp_uid.equals(__self.temp_uid)))
            ret.temp_uid = this.temp_uid;
        if (this.channel != null && (__self == null || !this.channel.equals(__self.channel)))
            ret.channel = this.channel;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.create_ip != null && (__self == null || !this.create_ip.equals(__self.create_ip)))
            ret.create_ip = this.create_ip;
        if (this.create_platform != null && (__self == null || !this.create_platform.equals(__self.create_platform)))
            ret.create_platform = this.create_platform;
        if (this.create_device_unique_identifier != null && (__self == null || !this.create_device_unique_identifier.equals(__self.create_device_unique_identifier)))
            ret.create_device_unique_identifier = this.create_device_unique_identifier;
        if (this.last_login_time != null && (__self == null || !this.last_login_time.equals(__self.last_login_time)))
            ret.last_login_time = this.last_login_time;
        if (this.last_login_ip != null && (__self == null || !this.last_login_ip.equals(__self.last_login_ip)))
            ret.last_login_ip = this.last_login_ip;
        if (this.last_login_platform != null && (__self == null || !this.last_login_platform.equals(__self.last_login_platform)))
            ret.last_login_platform = this.last_login_platform;
        if (this.last_device_unique_identifier != null && (__self == null || !this.last_device_unique_identifier.equals(__self.last_device_unique_identifier)))
            ret.last_device_unique_identifier = this.last_device_unique_identifier;
        if (this.session != null && (__self == null || !this.session.equals(__self.session)))
            ret.session = this.session;
        if (this.serveruid != null && (__self == null || !this.serveruid.equals(__self.serveruid)))
            ret.serveruid = this.serveruid;
        if (this.type != null && (__self == null || !this.type.equals(__self.type)))
            ret.type = this.type;
        if (this.db_id != null && (__self == null || !this.db_id.equals(__self.db_id)))
            ret.db_id = this.db_id;
        if (this.banned != null && (__self == null || !this.banned.equals(__self.banned)))
            ret.banned = this.banned;
        if (this.banned_time != null && (__self == null || !this.banned_time.equals(__self.banned_time)))
            ret.banned_time = this.banned_time;
        if (this.nation != null && (__self == null || !this.nation.equals(__self.nation)))
            ret.nation = this.nation;
        if (this.last_model != null && (__self == null || !this.last_model.equals(__self.last_model)))
            ret.last_model = this.last_model;
        if (this.last_lang != null && (__self == null || !this.last_lang.equals(__self.last_lang)))
            ret.last_lang = this.last_lang;
        if (this.last_version != null && (__self == null || !this.last_version.equals(__self.last_version)))
            ret.last_version = this.last_version;
        if (this.last_opsys != null && (__self == null || !this.last_opsys.equals(__self.last_opsys)))
            ret.last_opsys = this.last_opsys;
        if (this.last_opsys_lang != null && (__self == null || !this.last_opsys_lang.equals(__self.last_opsys_lang)))
            ret.last_opsys_lang = this.last_opsys_lang;
        if (this.login_info != null && (__self == null || !this.login_info.equals(__self.login_info)))
            ret.login_info = this.login_info;
        if (this.online != null && (__self == null || !this.online.equals(__self.online)))
            ret.online = this.online;
        if (this.try_count != null && (__self == null || !this.try_count.equals(__self.try_count)))
            ret.try_count = this.try_count;
        if (this.isBind != null && (__self == null || !this.isBind.equals(__self.isBind)))
            ret.isBind = this.isBind;
        if (this.glob_id != null && (__self == null || !this.glob_id.equals(__self.glob_id)))
            ret.glob_id = this.glob_id;
        if (this.old_temp_uid != null && (__self == null || !this.old_temp_uid.equals(__self.old_temp_uid)))
            ret.old_temp_uid = this.old_temp_uid;
        if (this.change_phone_code != null && (__self == null || !this.change_phone_code.equals(__self.change_phone_code)))
            ret.change_phone_code = this.change_phone_code;
        if (this.change_phone_code_expire_time != null && (__self == null || !this.change_phone_code_expire_time.equals(__self.change_phone_code_expire_time)))
            ret.change_phone_code_expire_time = this.change_phone_code_expire_time;
        return ret;
    }
    @Override
    public void set(DatabaseAccount value) {
        this.player_id = value.player_id;
        this.uid = value.uid;
        this.passer = value.passer;
        this.temp_uid = value.temp_uid;
        this.channel = value.channel;
        this.create_time = value.create_time;
        this.create_ip = value.create_ip;
        this.create_platform = value.create_platform;
        this.create_device_unique_identifier = value.create_device_unique_identifier;
        this.last_login_time = value.last_login_time;
        this.last_login_ip = value.last_login_ip;
        this.last_login_platform = value.last_login_platform;
        this.last_device_unique_identifier = value.last_device_unique_identifier;
        this.session = value.session;
        this.serveruid = value.serveruid;
        this.type = value.type;
        this.db_id = value.db_id;
        this.banned = value.banned;
        this.banned_time = value.banned_time;
        this.nation = value.nation;
        this.last_model = value.last_model;
        this.last_lang = value.last_lang;
        this.last_version = value.last_version;
        this.last_opsys = value.last_opsys;
        this.last_opsys_lang = value.last_opsys_lang;
        this.login_info = value.login_info;
        this.online = value.online;
        this.try_count = value.try_count;
        this.isBind = value.isBind;
        this.glob_id = value.glob_id;
        this.old_temp_uid = value.old_temp_uid;
        this.change_phone_code = value.change_phone_code;
        this.change_phone_code_expire_time = value.change_phone_code_expire_time;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.uid != null) return false;
        if (this.passer != null) return false;
        if (this.temp_uid != null) return false;
        if (this.channel != null) return false;
        if (this.create_time != null) return false;
        if (this.create_ip != null) return false;
        if (this.create_platform != null) return false;
        if (this.create_device_unique_identifier != null) return false;
        if (this.last_login_time != null) return false;
        if (this.last_login_ip != null) return false;
        if (this.last_login_platform != null) return false;
        if (this.last_device_unique_identifier != null) return false;
        if (this.session != null) return false;
        if (this.serveruid != null) return false;
        if (this.type != null) return false;
        if (this.db_id != null) return false;
        if (this.banned != null) return false;
        if (this.banned_time != null) return false;
        if (this.nation != null) return false;
        if (this.last_model != null) return false;
        if (this.last_lang != null) return false;
        if (this.last_version != null) return false;
        if (this.last_opsys != null) return false;
        if (this.last_opsys_lang != null) return false;
        if (this.login_info != null) return false;
        if (this.online != null) return false;
        if (this.try_count != null) return false;
        if (this.isBind != null) return false;
        if (this.glob_id != null) return false;
        if (this.old_temp_uid != null) return false;
        if (this.change_phone_code != null) return false;
        if (this.change_phone_code_expire_time != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.uid = null;
        this.passer = null;
        this.temp_uid = null;
        this.channel = null;
        this.create_time = null;
        this.create_ip = null;
        this.create_platform = null;
        this.create_device_unique_identifier = null;
        this.last_login_time = null;
        this.last_login_ip = null;
        this.last_login_platform = null;
        this.last_device_unique_identifier = null;
        this.session = null;
        this.serveruid = null;
        this.type = null;
        this.db_id = null;
        this.banned = null;
        this.banned_time = null;
        this.nation = null;
        this.last_model = null;
        this.last_lang = null;
        this.last_version = null;
        this.last_opsys = null;
        this.last_opsys_lang = null;
        this.login_info = null;
        this.online = null;
        this.try_count = null;
        this.isBind = null;
        this.glob_id = null;
        this.old_temp_uid = null;
        this.change_phone_code = null;
        this.change_phone_code_expire_time = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.uid != null)
            ret += ("uid = " + this.uid.toString() + " ");
        if (this.passer != null)
            ret += ("passer = " + this.passer.toString() + " ");
        if (this.temp_uid != null)
            ret += ("temp_uid = " + this.temp_uid.toString() + " ");
        if (this.channel != null)
            ret += ("channel = " + this.channel.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.create_ip != null)
            ret += ("create_ip = " + this.create_ip.toString() + " ");
        if (this.create_platform != null)
            ret += ("create_platform = " + this.create_platform.toString() + " ");
        if (this.create_device_unique_identifier != null)
            ret += ("create_device_unique_identifier = " + this.create_device_unique_identifier.toString() + " ");
        if (this.last_login_time != null)
            ret += ("last_login_time = " + this.last_login_time.toString() + " ");
        if (this.last_login_ip != null)
            ret += ("last_login_ip = " + this.last_login_ip.toString() + " ");
        if (this.last_login_platform != null)
            ret += ("last_login_platform = " + this.last_login_platform.toString() + " ");
        if (this.last_device_unique_identifier != null)
            ret += ("last_device_unique_identifier = " + this.last_device_unique_identifier.toString() + " ");
        if (this.session != null)
            ret += ("session = " + this.session.toString() + " ");
        if (this.serveruid != null)
            ret += ("serveruid = " + this.serveruid.toString() + " ");
        if (this.type != null)
            ret += ("type = " + this.type.toString() + " ");
        if (this.db_id != null)
            ret += ("db_id = " + this.db_id.toString() + " ");
        if (this.banned != null)
            ret += ("banned = " + this.banned.toString() + " ");
        if (this.banned_time != null)
            ret += ("banned_time = " + this.banned_time.toString() + " ");
        if (this.nation != null)
            ret += ("nation = " + this.nation.toString() + " ");
        if (this.last_model != null)
            ret += ("last_model = " + this.last_model.toString() + " ");
        if (this.last_lang != null)
            ret += ("last_lang = " + this.last_lang.toString() + " ");
        if (this.last_version != null)
            ret += ("last_version = " + this.last_version.toString() + " ");
        if (this.last_opsys != null)
            ret += ("last_opsys = " + this.last_opsys.toString() + " ");
        if (this.last_opsys_lang != null)
            ret += ("last_opsys_lang = " + this.last_opsys_lang.toString() + " ");
        if (this.login_info != null)
            ret += ("login_info = " + this.login_info.toString() + " ");
        if (this.online != null)
            ret += ("online = " + this.online.toString() + " ");
        if (this.try_count != null)
            ret += ("try_count = " + this.try_count.toString() + " ");
        if (this.isBind != null)
            ret += ("isBind = " + this.isBind.toString() + " ");
        if (this.glob_id != null)
            ret += ("glob_id = " + this.glob_id.toString() + " ");
        if (this.old_temp_uid != null)
            ret += ("old_temp_uid = " + this.old_temp_uid.toString() + " ");
        if (this.change_phone_code != null)
            ret += ("change_phone_code = " + this.change_phone_code.toString() + " ");
        if (this.change_phone_code_expire_time != null)
            ret += ("change_phone_code_expire_time = " + this.change_phone_code_expire_time.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "account";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "player_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return player_id;
    }
}