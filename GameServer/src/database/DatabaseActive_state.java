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
//================活动状态===========================
@SuppressWarnings("unused")
public class DatabaseActive_state implements DatabaseTableDataBase<DatabaseActive_state>,Serializable {
    public static final String TableName = "active_state";
    /** id */
    @DatabaseFieldAttribute(fieldName = "id",fieldType = Long.class,arrayType = Byte.class)
    public Long id = null;
    public Long getId() {return id;}
    /** 活动id */
    @DatabaseFieldAttribute(fieldName = "active_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer active_id = null;
    public Integer getActive_id() {return active_id;}
    /** 已经领奖的idx */
    @DatabaseFieldAttribute(fieldName = "reward_idx",fieldType = Integer.class,arrayType = Byte.class)
    public Integer reward_idx = null;
    public Integer getReward_idx() {return reward_idx;}
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 参数1 */
    @DatabaseFieldAttribute(fieldName = "param1",fieldType = Integer.class,arrayType = Byte.class)
    public Integer param1 = null;
    public Integer getParam1() {return param1;}
    /** 附加信息 */
    @DatabaseFieldAttribute(fieldName = "state_values",fieldType = String.class,arrayType = CustomActiveStateValues.class)
    public List<CustomActiveStateValues> state_values = null;
    public List<CustomActiveStateValues> getState_values() {return state_values;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseActive_state __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseActive_state();
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
    public DatabaseActive_state diff()
    {
        DatabaseActive_state ret = new DatabaseActive_state();
        if (this.id != null && (__self == null || !this.id.equals(__self.id)))
            ret.id = this.id;
        if (this.active_id != null && (__self == null || !this.active_id.equals(__self.active_id)))
            ret.active_id = this.active_id;
        if (this.reward_idx != null && (__self == null || !this.reward_idx.equals(__self.reward_idx)))
            ret.reward_idx = this.reward_idx;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.param1 != null && (__self == null || !this.param1.equals(__self.param1)))
            ret.param1 = this.param1;
        if (this.state_values != null && (__self == null || !this.state_values.equals(__self.state_values)))
            ret.state_values = this.state_values;
        return ret;
    }
    @Override
    public void set(DatabaseActive_state value) {
        this.id = value.id;
        this.active_id = value.active_id;
        this.reward_idx = value.reward_idx;
        this.player_id = value.player_id;
        this.param1 = value.param1;
        this.state_values = Utility.cloneList(value.state_values);
    }
    @Override
    public boolean isEmpty() {
        if (this.id != null) return false;
        if (this.active_id != null) return false;
        if (this.reward_idx != null) return false;
        if (this.player_id != null) return false;
        if (this.param1 != null) return false;
        if (this.state_values != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.id = null;
        this.active_id = null;
        this.reward_idx = null;
        this.player_id = null;
        this.param1 = null;
        this.state_values = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.id != null)
            ret += ("id = " + this.id.toString() + " ");
        if (this.active_id != null)
            ret += ("active_id = " + this.active_id.toString() + " ");
        if (this.reward_idx != null)
            ret += ("reward_idx = " + this.reward_idx.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.param1 != null)
            ret += ("param1 = " + this.param1.toString() + " ");
        if (this.state_values != null)
            ret += ("state_values = " + this.state_values.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "active_state";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (state_values != null && state_values.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : state_values) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    state_values.remove(data);
		    }
        }
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