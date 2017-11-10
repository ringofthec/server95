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
//================军团帮助表===========================
@SuppressWarnings("unused")
public class DatabaseRequestHelp implements DatabaseTableDataBase<DatabaseRequestHelp>,Serializable {
    public static final String TableName = "requestHelp";
    /** 唯一ID */
    @DatabaseFieldAttribute(fieldName = "help_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer help_id = null;
    public Integer getHelp_id() {return help_id;}
    /** 请求帮助玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 建筑ID */
    @DatabaseFieldAttribute(fieldName = "build_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer build_id = null;
    public Integer getBuild_id() {return build_id;}
    /** 相关的table ID */
    @DatabaseFieldAttribute(fieldName = "table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer table_id = null;
    public Integer getTable_id() {return table_id;}
    /** 点击求助时候的等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 点击求助时候的建筑状态 */
    @DatabaseFieldAttribute(fieldName = "state",fieldType = Integer.class,arrayType = gameserver.network.protos.common.ProLegion.Proto_SeekHelp_BuildType.class)
    public gameserver.network.protos.common.ProLegion.Proto_SeekHelp_BuildType state = null;
    public gameserver.network.protos.common.ProLegion.Proto_SeekHelp_BuildType getState() {return state;}
    /** 军团id */
    @DatabaseFieldAttribute(fieldName = "legion_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer legion_id = null;
    public Integer getLegion_id() {return legion_id;}
    /** 帮助过的人 */
    @DatabaseFieldAttribute(fieldName = "help_list",fieldType = String.class,arrayType = Long.class)
    public List<Long> help_list = null;
    public List<Long> getHelp_list() {return help_list;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 最大帮助次数 */
    @DatabaseFieldAttribute(fieldName = "max_help",fieldType = Integer.class,arrayType = Byte.class)
    public Integer max_help = null;
    public Integer getMax_help() {return max_help;}
    /** 创建时间点 */
    @DatabaseFieldAttribute(fieldName = "time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long time = null;
    public Long getTime() {return time;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRequestHelp __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRequestHelp();
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
    public DatabaseRequestHelp diff()
    {
        DatabaseRequestHelp ret = new DatabaseRequestHelp();
        if (this.help_id != null && (__self == null || !this.help_id.equals(__self.help_id)))
            ret.help_id = this.help_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.build_id != null && (__self == null || !this.build_id.equals(__self.build_id)))
            ret.build_id = this.build_id;
        if (this.table_id != null && (__self == null || !this.table_id.equals(__self.table_id)))
            ret.table_id = this.table_id;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.state != null && (__self == null || !this.state.equals(__self.state)))
            ret.state = this.state;
        if (this.legion_id != null && (__self == null || !this.legion_id.equals(__self.legion_id)))
            ret.legion_id = this.legion_id;
        if (this.help_list != null && (__self == null || !this.help_list.equals(__self.help_list)))
            ret.help_list = this.help_list;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.max_help != null && (__self == null || !this.max_help.equals(__self.max_help)))
            ret.max_help = this.max_help;
        if (this.time != null && (__self == null || !this.time.equals(__self.time)))
            ret.time = this.time;
        return ret;
    }
    @Override
    public void set(DatabaseRequestHelp value) {
        this.help_id = value.help_id;
        this.player_id = value.player_id;
        this.build_id = value.build_id;
        this.table_id = value.table_id;
        this.level = value.level;
        this.state = value.state;
        this.legion_id = value.legion_id;
        this.help_list = Utility.clonePrimitiveList(value.help_list);
        this.name = value.name;
        this.max_help = value.max_help;
        this.time = value.time;
    }
    @Override
    public boolean isEmpty() {
        if (this.help_id != null) return false;
        if (this.player_id != null) return false;
        if (this.build_id != null) return false;
        if (this.table_id != null) return false;
        if (this.level != null) return false;
        if (this.state != null) return false;
        if (this.legion_id != null) return false;
        if (this.help_list != null) return false;
        if (this.name != null) return false;
        if (this.max_help != null) return false;
        if (this.time != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.help_id = null;
        this.player_id = null;
        this.build_id = null;
        this.table_id = null;
        this.level = null;
        this.state = null;
        this.legion_id = null;
        this.help_list = null;
        this.name = null;
        this.max_help = null;
        this.time = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.help_id != null)
            ret += ("help_id = " + this.help_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.build_id != null)
            ret += ("build_id = " + this.build_id.toString() + " ");
        if (this.table_id != null)
            ret += ("table_id = " + this.table_id.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.state != null)
            ret += ("state = " + this.state.toString() + " ");
        if (this.legion_id != null)
            ret += ("legion_id = " + this.legion_id.toString() + " ");
        if (this.help_list != null)
            ret += ("help_list = " + this.help_list.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.max_help != null)
            ret += ("max_help = " + this.max_help.toString() + " ");
        if (this.time != null)
            ret += ("time = " + this.time.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "requestHelp";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "help_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return help_id;
    }
}