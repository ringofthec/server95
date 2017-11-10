// 本文件是自动生成，不要手动修改
package database;
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
//================建筑表===========================
@SuppressWarnings("unused")
public class DatabaseBuild implements DatabaseTableDataBase<DatabaseBuild>,Serializable {
    public static final String TableName = "build";
    /** 建筑唯一ID */
    @DatabaseFieldAttribute(fieldName = "build_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer build_id = null;
    public Integer getBuild_id() {return build_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 建筑类型ID （table表ID） */
    @DatabaseFieldAttribute(fieldName = "table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer table_id = null;
    public Integer getTable_id() {return table_id;}
    /** 建筑坐标X */
    @DatabaseFieldAttribute(fieldName = "posx",fieldType = Integer.class,arrayType = Byte.class)
    public Integer posx = null;
    public Integer getPosx() {return posx;}
    /** 建筑坐标Y */
    @DatabaseFieldAttribute(fieldName = "posy",fieldType = Integer.class,arrayType = Byte.class)
    public Integer posy = null;
    public Integer getPosy() {return posy;}
    /** 建筑等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 当前建筑状态 */
    @DatabaseFieldAttribute(fieldName = "state",fieldType = Integer.class,arrayType = gameserver.network.protos.game.CommonProto.Proto_BuildState.class)
    public gameserver.network.protos.game.CommonProto.Proto_BuildState state = null;
    public gameserver.network.protos.game.CommonProto.Proto_BuildState getState() {return state;}
    /** 建筑建造或升级开始时间 */
    @DatabaseFieldAttribute(fieldName = "upgradetime",fieldType = Long.class,arrayType = Byte.class)
    public Long upgradetime = null;
    public Long getUpgradetime() {return upgradetime;}
    /** 建筑特有操作的最后一次的更新时间 产一个兵更新一次 */
    @DatabaseFieldAttribute(fieldName = "lastoperatetime",fieldType = Long.class,arrayType = Byte.class)
    public Long lastoperatetime = null;
    public Long getLastoperatetime() {return lastoperatetime;}
    /** 上一次资源收取时间 */
    @DatabaseFieldAttribute(fieldName = "gathertime",fieldType = Long.class,arrayType = Byte.class)
    public Long gathertime = null;
    public Long getGathertime() {return gathertime;}
    /** 产出的剩余兵种数组 格式 : 兵种1ID:兵种1数量,兵种2ID:兵种2数量;保存待升级的列表 */
    @DatabaseFieldAttribute(fieldName = "corpsArray",fieldType = String.class,arrayType = CustomPossessions.class)
    public List<CustomPossessions> corpsArray = null;
    public List<CustomPossessions> getCorpsArray() {return corpsArray;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseBuild __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseBuild();
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
    public DatabaseBuild diff()
    {
        DatabaseBuild ret = new DatabaseBuild();
        if (this.build_id != null && (__self == null || !this.build_id.equals(__self.build_id)))
            ret.build_id = this.build_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.table_id != null && (__self == null || !this.table_id.equals(__self.table_id)))
            ret.table_id = this.table_id;
        if (this.posx != null && (__self == null || !this.posx.equals(__self.posx)))
            ret.posx = this.posx;
        if (this.posy != null && (__self == null || !this.posy.equals(__self.posy)))
            ret.posy = this.posy;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.state != null && (__self == null || !this.state.equals(__self.state)))
            ret.state = this.state;
        if (this.upgradetime != null && (__self == null || !this.upgradetime.equals(__self.upgradetime)))
            ret.upgradetime = this.upgradetime;
        if (this.lastoperatetime != null && (__self == null || !this.lastoperatetime.equals(__self.lastoperatetime)))
            ret.lastoperatetime = this.lastoperatetime;
        if (this.gathertime != null && (__self == null || !this.gathertime.equals(__self.gathertime)))
            ret.gathertime = this.gathertime;
        if (this.corpsArray != null && (__self == null || !this.corpsArray.equals(__self.corpsArray)))
            ret.corpsArray = this.corpsArray;
        return ret;
    }
    @Override
    public void set(DatabaseBuild value) {
        this.build_id = value.build_id;
        this.player_id = value.player_id;
        this.table_id = value.table_id;
        this.posx = value.posx;
        this.posy = value.posy;
        this.level = value.level;
        this.state = value.state;
        this.upgradetime = value.upgradetime;
        this.lastoperatetime = value.lastoperatetime;
        this.gathertime = value.gathertime;
        this.corpsArray = Utility.cloneList(value.corpsArray);
    }
    @Override
    public boolean isEmpty() {
        if (this.build_id != null) return false;
        if (this.player_id != null) return false;
        if (this.table_id != null) return false;
        if (this.posx != null) return false;
        if (this.posy != null) return false;
        if (this.level != null) return false;
        if (this.state != null) return false;
        if (this.upgradetime != null) return false;
        if (this.lastoperatetime != null) return false;
        if (this.gathertime != null) return false;
        if (this.corpsArray != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.build_id = null;
        this.player_id = null;
        this.table_id = null;
        this.posx = null;
        this.posy = null;
        this.level = null;
        this.state = null;
        this.upgradetime = null;
        this.lastoperatetime = null;
        this.gathertime = null;
        this.corpsArray = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.build_id != null)
            ret += ("build_id = " + this.build_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.table_id != null)
            ret += ("table_id = " + this.table_id.toString() + " ");
        if (this.posx != null)
            ret += ("posx = " + this.posx.toString() + " ");
        if (this.posy != null)
            ret += ("posy = " + this.posy.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.state != null)
            ret += ("state = " + this.state.toString() + " ");
        if (this.upgradetime != null)
            ret += ("upgradetime = " + this.upgradetime.toString() + " ");
        if (this.lastoperatetime != null)
            ret += ("lastoperatetime = " + this.lastoperatetime.toString() + " ");
        if (this.gathertime != null)
            ret += ("gathertime = " + this.gathertime.toString() + " ");
        if (this.corpsArray != null)
            ret += ("corpsArray = " + this.corpsArray.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "build";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (corpsArray != null && corpsArray.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : corpsArray) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    corpsArray.remove(data);
		    }
        }
    }
    @Override
    public String getPrimaryKeyName() {
        return "build_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return build_id;
    }
}