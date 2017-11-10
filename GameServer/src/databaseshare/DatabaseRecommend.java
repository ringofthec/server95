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
//===========================================
@SuppressWarnings("unused")
public class DatabaseRecommend implements DatabaseTableDataBase<DatabaseRecommend>,Serializable {
    public static final String TableName = "recommend";
    /** 唯一ID */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 被推荐的facebook uid */
    @DatabaseFieldAttribute(fieldName = "uid",fieldType = String.class,arrayType = Byte.class)
    public String uid = null;
    public String getUid() {return uid;}
    /** 是否已经推荐成功, 成功就是对方的playerid */
    @DatabaseFieldAttribute(fieldName = "target_player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long target_player_id = null;
    public Long getTarget_player_id() {return target_player_id;}
    /** 充值贡献 */
    @DatabaseFieldAttribute(fieldName = "recharge",fieldType = Integer.class,arrayType = Byte.class)
    public Integer recharge = null;
    public Integer getRecharge() {return recharge;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseRecommend __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseRecommend();
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
    public DatabaseRecommend diff()
    {
        DatabaseRecommend ret = new DatabaseRecommend();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.uid != null && (__self == null || !this.uid.equals(__self.uid)))
            ret.uid = this.uid;
        if (this.target_player_id != null && (__self == null || !this.target_player_id.equals(__self.target_player_id)))
            ret.target_player_id = this.target_player_id;
        if (this.recharge != null && (__self == null || !this.recharge.equals(__self.recharge)))
            ret.recharge = this.recharge;
        return ret;
    }
    @Override
    public void set(DatabaseRecommend value) {
        this.id = value.id;
        this.player_id = value.player_id;
        this.uid = value.uid;
        this.target_player_id = value.target_player_id;
        this.recharge = value.recharge;
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.player_id != null) return false;
        if (this.uid != null) return false;
        if (this.target_player_id != null) return false;
        if (this.recharge != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.player_id = null;
        this.uid = null;
        this.target_player_id = null;
        this.recharge = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.uid != null)
            ret += ("uid = " + this.uid.toString() + " ");
        if (this.target_player_id != null)
            ret += ("target_player_id = " + this.target_player_id.toString() + " ");
        if (this.recharge != null)
            ret += ("recharge = " + this.recharge.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "recommend";
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