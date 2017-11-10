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
//================录像回放表===========================
@SuppressWarnings("unused")
public class DatabaseVideo implements DatabaseTableDataBase<DatabaseVideo>,Serializable {
    public static final String TableName = "Video";
    /** 录像唯一ID */
    @DatabaseFieldAttribute(fieldName = "video_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer video_id = null;
    public Integer getVideo_id() {return video_id;}
    /** 获得的资源数量 */
    @DatabaseFieldAttribute(fieldName = "win_asset",fieldType = Integer.class,arrayType = Byte.class)
    public Integer win_asset = null;
    public Integer getWin_asset() {return win_asset;}
    /** 获得的功勋值 负值表示失去功勋 */
    @DatabaseFieldAttribute(fieldName = "win_feat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer win_feat = null;
    public Integer getWin_feat() {return win_feat;}
    /** 获得的资源数量 */
    @DatabaseFieldAttribute(fieldName = "lose_asset",fieldType = Integer.class,arrayType = Byte.class)
    public Integer lose_asset = null;
    public Integer getLose_asset() {return lose_asset;}
    /** 获得的功勋值 负值表示失去功勋 */
    @DatabaseFieldAttribute(fieldName = "lose_feat",fieldType = Integer.class,arrayType = Byte.class)
    public Integer lose_feat = null;
    public Integer getLose_feat() {return lose_feat;}
    /** 创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 录像的数据 */
    @DatabaseFieldAttribute(fieldName = "video_data",fieldType = byte[].class,arrayType = Byte.class)
    public byte[] video_data = null;
    public byte[] getVideo_data() {return video_data;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseVideo __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseVideo();
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
    public DatabaseVideo diff()
    {
        DatabaseVideo ret = new DatabaseVideo();
        if (this.video_id != null && (__self == null || !this.video_id.equals(__self.video_id)))
            ret.video_id = this.video_id;
        if (this.win_asset != null && (__self == null || !this.win_asset.equals(__self.win_asset)))
            ret.win_asset = this.win_asset;
        if (this.win_feat != null && (__self == null || !this.win_feat.equals(__self.win_feat)))
            ret.win_feat = this.win_feat;
        if (this.lose_asset != null && (__self == null || !this.lose_asset.equals(__self.lose_asset)))
            ret.lose_asset = this.lose_asset;
        if (this.lose_feat != null && (__self == null || !this.lose_feat.equals(__self.lose_feat)))
            ret.lose_feat = this.lose_feat;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.video_data != null && (__self == null || !Arrays.equals(this.video_data,__self.video_data)))
            ret.video_data = this.video_data;
        return ret;
    }
    @Override
    public void set(DatabaseVideo value) {
        this.video_id = value.video_id;
        this.win_asset = value.win_asset;
        this.win_feat = value.win_feat;
        this.lose_asset = value.lose_asset;
        this.lose_feat = value.lose_feat;
        this.create_time = value.create_time;
        this.video_data = value.video_data;
    }
    @Override
    public boolean isEmpty() {
        if (this.video_id != null) return false;
        if (this.win_asset != null) return false;
        if (this.win_feat != null) return false;
        if (this.lose_asset != null) return false;
        if (this.lose_feat != null) return false;
        if (this.create_time != null) return false;
        if (this.video_data != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.video_id = null;
        this.win_asset = null;
        this.win_feat = null;
        this.lose_asset = null;
        this.lose_feat = null;
        this.create_time = null;
        this.video_data = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.video_id != null)
            ret += ("video_id = " + this.video_id.toString() + " ");
        if (this.win_asset != null)
            ret += ("win_asset = " + this.win_asset.toString() + " ");
        if (this.win_feat != null)
            ret += ("win_feat = " + this.win_feat.toString() + " ");
        if (this.lose_asset != null)
            ret += ("lose_asset = " + this.lose_asset.toString() + " ");
        if (this.lose_feat != null)
            ret += ("lose_feat = " + this.lose_feat.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.video_data != null)
            ret += ("video_data = " + this.video_data.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "Video";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "video_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return video_id;
    }
}