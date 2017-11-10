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
//================玩家军团信息===========================
@SuppressWarnings("unused")
public class DatabasePlayerLegion implements DatabaseTableDataBase<DatabasePlayerLegion>,Serializable {
    public static final String TableName = "playerLegion";
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 所属军团id */
    @DatabaseFieldAttribute(fieldName = "belong_legion",fieldType = Integer.class,arrayType = Byte.class)
    public Integer belong_legion = null;
    public Integer getBelong_legion() {return belong_legion;}
    /** 加入军团时间 */
    @DatabaseFieldAttribute(fieldName = "join_legion_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long join_legion_time = null;
    public Long getJoin_legion_time() {return join_legion_time;}
    /** 用来处理军团战体力恢复 */
    @DatabaseFieldAttribute(fieldName = "war_cp_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long war_cp_time = null;
    public Long getWar_cp_time() {return war_cp_time;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabasePlayerLegion __self = null;
    @Override
    public void sync()
    {
        __self = new DatabasePlayerLegion();
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
    public DatabasePlayerLegion diff()
    {
        DatabasePlayerLegion ret = new DatabasePlayerLegion();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.belong_legion != null && (__self == null || !this.belong_legion.equals(__self.belong_legion)))
            ret.belong_legion = this.belong_legion;
        if (this.join_legion_time != null && (__self == null || !this.join_legion_time.equals(__self.join_legion_time)))
            ret.join_legion_time = this.join_legion_time;
        if (this.war_cp_time != null && (__self == null || !this.war_cp_time.equals(__self.war_cp_time)))
            ret.war_cp_time = this.war_cp_time;
        return ret;
    }
    @Override
    public void set(DatabasePlayerLegion value) {
        this.player_id = value.player_id;
        this.belong_legion = value.belong_legion;
        this.join_legion_time = value.join_legion_time;
        this.war_cp_time = value.war_cp_time;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.belong_legion != null) return false;
        if (this.join_legion_time != null) return false;
        if (this.war_cp_time != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.belong_legion = null;
        this.join_legion_time = null;
        this.war_cp_time = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.belong_legion != null)
            ret += ("belong_legion = " + this.belong_legion.toString() + " ");
        if (this.join_legion_time != null)
            ret += ("join_legion_time = " + this.join_legion_time.toString() + " ");
        if (this.war_cp_time != null)
            ret += ("war_cp_time = " + this.war_cp_time.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "playerLegion";
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