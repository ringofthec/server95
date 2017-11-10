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
//================防守战报表===========================
@SuppressWarnings("unused")
public class DatabaseDefend_report implements DatabaseTableDataBase<DatabaseDefend_report>,Serializable {
    public static final String TableName = "defend_report";
    /** 防守战报ID */
    @DatabaseFieldAttribute(fieldName = "defend_report_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer defend_report_id = null;
    public Integer getDefend_report_id() {return defend_report_id;}
    /** 本方玩家ID */
    @DatabaseFieldAttribute(fieldName = "owen_id",fieldType = Long.class,arrayType = Byte.class)
    public Long owen_id = null;
    public Long getOwen_id() {return owen_id;}
    /** 对手玩家ID */
    @DatabaseFieldAttribute(fieldName = "target_id",fieldType = Long.class,arrayType = Byte.class)
    public Long target_id = null;
    public Long getTarget_id() {return target_id;}
    /** 防守时间点 */
    @DatabaseFieldAttribute(fieldName = "time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long time = null;
    public Long getTime() {return time;}
    /** 对手名字 */
    @DatabaseFieldAttribute(fieldName = "target_name",fieldType = String.class,arrayType = Byte.class)
    public String target_name = null;
    public String getTarget_name() {return target_name;}
    /** 对手等级 */
    @DatabaseFieldAttribute(fieldName = "target_level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer target_level = null;
    public Integer getTarget_level() {return target_level;}
    /** 对方功勋值 */
    @DatabaseFieldAttribute(fieldName = "target_now_feat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer target_now_feat = null;
    public Integer getTarget_now_feat() {return target_now_feat;}
    /** 对方军团名字 */
    @DatabaseFieldAttribute(fieldName = "target_group",fieldType = String.class,arrayType = Byte.class)
    public String target_group = null;
    public String getTarget_group() {return target_group;}
    /** 成功还是失败 */
    @DatabaseFieldAttribute(fieldName = "win",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean win = null;
    public Boolean getWin() {return win;}
    /** 获得的资源数量 */
    @DatabaseFieldAttribute(fieldName = "asset",fieldType = Integer.class,arrayType = Byte.class)
    public Integer asset = null;
    public Integer getAsset() {return asset;}
    /** 自己获得的功勋值 负值表示失去功勋 */
    @DatabaseFieldAttribute(fieldName = "owen_feat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer owen_feat = null;
    public Integer getOwen_feat() {return owen_feat;}
    /** 录像ID */
    @DatabaseFieldAttribute(fieldName = "video_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer video_id = null;
    public Integer getVideo_id() {return video_id;}
    /** 是否已查看 */
    @DatabaseFieldAttribute(fieldName = "islook",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean islook = null;
    public Boolean getIslook() {return islook;}
    /** 是否已复仇 */
    @DatabaseFieldAttribute(fieldName = "isfuckback",fieldType = Integer.class,arrayType = Byte.class)
    public Integer isfuckback = null;
    public Integer getIsfuckback() {return isfuckback;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseDefend_report __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseDefend_report();
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
    public DatabaseDefend_report diff()
    {
        DatabaseDefend_report ret = new DatabaseDefend_report();
        if (this.defend_report_id != null && (__self == null || !this.defend_report_id.equals(__self.defend_report_id)))
            ret.defend_report_id = this.defend_report_id;
        if (this.owen_id != null && (__self == null || !this.owen_id.equals(__self.owen_id)))
            ret.owen_id = this.owen_id;
        if (this.target_id != null && (__self == null || !this.target_id.equals(__self.target_id)))
            ret.target_id = this.target_id;
        if (this.time != null && (__self == null || !this.time.equals(__self.time)))
            ret.time = this.time;
        if (this.target_name != null && (__self == null || !this.target_name.equals(__self.target_name)))
            ret.target_name = this.target_name;
        if (this.target_level != null && (__self == null || !this.target_level.equals(__self.target_level)))
            ret.target_level = this.target_level;
        if (this.target_now_feat != null && (__self == null || !this.target_now_feat.equals(__self.target_now_feat)))
            ret.target_now_feat = this.target_now_feat;
        if (this.target_group != null && (__self == null || !this.target_group.equals(__self.target_group)))
            ret.target_group = this.target_group;
        if (this.win != null && (__self == null || !this.win.equals(__self.win)))
            ret.win = this.win;
        if (this.asset != null && (__self == null || !this.asset.equals(__self.asset)))
            ret.asset = this.asset;
        if (this.owen_feat != null && (__self == null || !this.owen_feat.equals(__self.owen_feat)))
            ret.owen_feat = this.owen_feat;
        if (this.video_id != null && (__self == null || !this.video_id.equals(__self.video_id)))
            ret.video_id = this.video_id;
        if (this.islook != null && (__self == null || !this.islook.equals(__self.islook)))
            ret.islook = this.islook;
        if (this.isfuckback != null && (__self == null || !this.isfuckback.equals(__self.isfuckback)))
            ret.isfuckback = this.isfuckback;
        return ret;
    }
    @Override
    public void set(DatabaseDefend_report value) {
        this.defend_report_id = value.defend_report_id;
        this.owen_id = value.owen_id;
        this.target_id = value.target_id;
        this.time = value.time;
        this.target_name = value.target_name;
        this.target_level = value.target_level;
        this.target_now_feat = value.target_now_feat;
        this.target_group = value.target_group;
        this.win = value.win;
        this.asset = value.asset;
        this.owen_feat = value.owen_feat;
        this.video_id = value.video_id;
        this.islook = value.islook;
        this.isfuckback = value.isfuckback;
    }
    @Override
    public boolean isEmpty() {
        if (this.defend_report_id != null) return false;
        if (this.owen_id != null) return false;
        if (this.target_id != null) return false;
        if (this.time != null) return false;
        if (this.target_name != null) return false;
        if (this.target_level != null) return false;
        if (this.target_now_feat != null) return false;
        if (this.target_group != null) return false;
        if (this.win != null) return false;
        if (this.asset != null) return false;
        if (this.owen_feat != null) return false;
        if (this.video_id != null) return false;
        if (this.islook != null) return false;
        if (this.isfuckback != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.defend_report_id = null;
        this.owen_id = null;
        this.target_id = null;
        this.time = null;
        this.target_name = null;
        this.target_level = null;
        this.target_now_feat = null;
        this.target_group = null;
        this.win = null;
        this.asset = null;
        this.owen_feat = null;
        this.video_id = null;
        this.islook = null;
        this.isfuckback = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.defend_report_id != null)
            ret += ("defend_report_id = " + this.defend_report_id.toString() + " ");
        if (this.owen_id != null)
            ret += ("owen_id = " + this.owen_id.toString() + " ");
        if (this.target_id != null)
            ret += ("target_id = " + this.target_id.toString() + " ");
        if (this.time != null)
            ret += ("time = " + this.time.toString() + " ");
        if (this.target_name != null)
            ret += ("target_name = " + this.target_name.toString() + " ");
        if (this.target_level != null)
            ret += ("target_level = " + this.target_level.toString() + " ");
        if (this.target_now_feat != null)
            ret += ("target_now_feat = " + this.target_now_feat.toString() + " ");
        if (this.target_group != null)
            ret += ("target_group = " + this.target_group.toString() + " ");
        if (this.win != null)
            ret += ("win = " + this.win.toString() + " ");
        if (this.asset != null)
            ret += ("asset = " + this.asset.toString() + " ");
        if (this.owen_feat != null)
            ret += ("owen_feat = " + this.owen_feat.toString() + " ");
        if (this.video_id != null)
            ret += ("video_id = " + this.video_id.toString() + " ");
        if (this.islook != null)
            ret += ("islook = " + this.islook.toString() + " ");
        if (this.isfuckback != null)
            ret += ("isfuckback = " + this.isfuckback.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "defend_report";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "defend_report_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return defend_report_id;
    }
}