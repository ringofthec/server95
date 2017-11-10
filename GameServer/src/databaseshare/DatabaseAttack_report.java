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
//================进攻战报表===========================
@SuppressWarnings("unused")
public class DatabaseAttack_report implements DatabaseTableDataBase<DatabaseAttack_report>,Serializable {
    public static final String TableName = "attack_report";
    /** 进攻战报ID */
    @DatabaseFieldAttribute(fieldName = "attack_report_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer attack_report_id = null;
    public Integer getAttack_report_id() {return attack_report_id;}
    /** 本方玩家ID */
    @DatabaseFieldAttribute(fieldName = "owen_id",fieldType = Long.class,arrayType = Byte.class)
    public Long owen_id = null;
    public Long getOwen_id() {return owen_id;}
    /** 对手玩家ID */
    @DatabaseFieldAttribute(fieldName = "target_id",fieldType = Long.class,arrayType = Byte.class)
    public Long target_id = null;
    public Long getTarget_id() {return target_id;}
    /** 进攻时间点 */
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
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseAttack_report __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseAttack_report();
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
    public DatabaseAttack_report diff()
    {
        DatabaseAttack_report ret = new DatabaseAttack_report();
        if (this.attack_report_id != null && (__self == null || !this.attack_report_id.equals(__self.attack_report_id)))
            ret.attack_report_id = this.attack_report_id;
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
        return ret;
    }
    @Override
    public void set(DatabaseAttack_report value) {
        this.attack_report_id = value.attack_report_id;
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
    }
    @Override
    public boolean isEmpty() {
        if (this.attack_report_id != null) return false;
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
        return true;
    }
    @Override
    public void clear() {
        this.attack_report_id = null;
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
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.attack_report_id != null)
            ret += ("attack_report_id = " + this.attack_report_id.toString() + " ");
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
        return ret;
    }
    @Override
    public String getTableName() {
        return "attack_report";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "attack_report_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return attack_report_id;
    }
}