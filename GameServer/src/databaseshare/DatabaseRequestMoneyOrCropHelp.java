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
public class DatabaseRequestMoneyOrCropHelp implements DatabaseTableDataBase<DatabaseRequestMoneyOrCropHelp>,Serializable {
    public static final String TableName = "requestMoneyOrCropHelp";
    /** 唯一ID */
    @DatabaseFieldAttribute(fieldName = "help_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer help_id = null;
    public Integer getHelp_id() {return help_id;}
    /** 请求玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 玩家姓名 */
    @DatabaseFieldAttribute(fieldName = "player_name",fieldType = String.class,arrayType = Byte.class)
    public String player_name = null;
    public String getPlayer_name() {return player_name;}
    /** 金币求助/士兵求助 */
    @DatabaseFieldAttribute(fieldName = "type",fieldType = Integer.class,arrayType = gameserver.network.protos.common.ProLegionHelp.Proto_SeekHelp_Type.class)
    public gameserver.network.protos.common.ProLegionHelp.Proto_SeekHelp_Type type = null;
    public gameserver.network.protos.common.ProLegionHelp.Proto_SeekHelp_Type getType() {return type;}
    /** 士兵id,如果是求助士兵；如果是求助资源，为0 */
    @DatabaseFieldAttribute(fieldName = "crop_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer crop_id = null;
    public Integer getCrop_id() {return crop_id;}
    /** 军团id */
    @DatabaseFieldAttribute(fieldName = "legion_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer legion_id = null;
    public Integer getLegion_id() {return legion_id;}
    /** 帮助过我的人 */
    @DatabaseFieldAttribute(fieldName = "money_crop_help_list",fieldType = String.class,arrayType = Long.class)
    public List<Long> money_crop_help_list = null;
    public List<Long> getMoney_crop_help_list() {return money_crop_help_list;}
    /** 当前被帮助次数 */
    @DatabaseFieldAttribute(fieldName = "cur_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer cur_num = null;
    public Integer getCur_num() {return cur_num;}
    /** 最大帮助次数 */
    @DatabaseFieldAttribute(fieldName = "max_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer max_num = null;
    public Integer getMax_num() {return max_num;}
    /** 求助内容 */
    @DatabaseFieldAttribute(fieldName = "text",fieldType = String.class,arrayType = Byte.class)
    public String text = null;
    public String getText() {return text;}
    /** 创建时间点 */
    @DatabaseFieldAttribute(fieldName = "time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long time = null;
    public Long getTime() {return time;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRequestMoneyOrCropHelp __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRequestMoneyOrCropHelp();
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
    public DatabaseRequestMoneyOrCropHelp diff()
    {
        DatabaseRequestMoneyOrCropHelp ret = new DatabaseRequestMoneyOrCropHelp();
        if (this.help_id != null && (__self == null || !this.help_id.equals(__self.help_id)))
            ret.help_id = this.help_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.player_name != null && (__self == null || !this.player_name.equals(__self.player_name)))
            ret.player_name = this.player_name;
        if (this.type != null && (__self == null || !this.type.equals(__self.type)))
            ret.type = this.type;
        if (this.crop_id != null && (__self == null || !this.crop_id.equals(__self.crop_id)))
            ret.crop_id = this.crop_id;
        if (this.legion_id != null && (__self == null || !this.legion_id.equals(__self.legion_id)))
            ret.legion_id = this.legion_id;
        if (this.money_crop_help_list != null && (__self == null || !this.money_crop_help_list.equals(__self.money_crop_help_list)))
            ret.money_crop_help_list = this.money_crop_help_list;
        if (this.cur_num != null && (__self == null || !this.cur_num.equals(__self.cur_num)))
            ret.cur_num = this.cur_num;
        if (this.max_num != null && (__self == null || !this.max_num.equals(__self.max_num)))
            ret.max_num = this.max_num;
        if (this.text != null && (__self == null || !this.text.equals(__self.text)))
            ret.text = this.text;
        if (this.time != null && (__self == null || !this.time.equals(__self.time)))
            ret.time = this.time;
        return ret;
    }
    @Override
    public void set(DatabaseRequestMoneyOrCropHelp value) {
        this.help_id = value.help_id;
        this.player_id = value.player_id;
        this.player_name = value.player_name;
        this.type = value.type;
        this.crop_id = value.crop_id;
        this.legion_id = value.legion_id;
        this.money_crop_help_list = Utility.clonePrimitiveList(value.money_crop_help_list);
        this.cur_num = value.cur_num;
        this.max_num = value.max_num;
        this.text = value.text;
        this.time = value.time;
    }
    @Override
    public boolean isEmpty() {
        if (this.help_id != null) return false;
        if (this.player_id != null) return false;
        if (this.player_name != null) return false;
        if (this.type != null) return false;
        if (this.crop_id != null) return false;
        if (this.legion_id != null) return false;
        if (this.money_crop_help_list != null) return false;
        if (this.cur_num != null) return false;
        if (this.max_num != null) return false;
        if (this.text != null) return false;
        if (this.time != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.help_id = null;
        this.player_id = null;
        this.player_name = null;
        this.type = null;
        this.crop_id = null;
        this.legion_id = null;
        this.money_crop_help_list = null;
        this.cur_num = null;
        this.max_num = null;
        this.text = null;
        this.time = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.help_id != null)
            ret += ("help_id = " + this.help_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.player_name != null)
            ret += ("player_name = " + this.player_name.toString() + " ");
        if (this.type != null)
            ret += ("type = " + this.type.toString() + " ");
        if (this.crop_id != null)
            ret += ("crop_id = " + this.crop_id.toString() + " ");
        if (this.legion_id != null)
            ret += ("legion_id = " + this.legion_id.toString() + " ");
        if (this.money_crop_help_list != null)
            ret += ("money_crop_help_list = " + this.money_crop_help_list.toString() + " ");
        if (this.cur_num != null)
            ret += ("cur_num = " + this.cur_num.toString() + " ");
        if (this.max_num != null)
            ret += ("max_num = " + this.max_num.toString() + " ");
        if (this.text != null)
            ret += ("text = " + this.text.toString() + " ");
        if (this.time != null)
            ret += ("time = " + this.time.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "requestMoneyOrCropHelp";
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