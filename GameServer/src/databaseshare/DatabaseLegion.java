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
//================军团表===========================
@SuppressWarnings("unused")
public class DatabaseLegion implements DatabaseTableDataBase<DatabaseLegion>,Serializable {
    public static final String TableName = "legion";
    /** 军团ID */
    @DatabaseFieldAttribute(fieldName = "legion_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer legion_id = null;
    public Integer getLegion_id() {return legion_id;}
    /** 军团长ID */
    @DatabaseFieldAttribute(fieldName = "boss_id",fieldType = Long.class,arrayType = Byte.class)
    public Long boss_id = null;
    public Long getBoss_id() {return boss_id;}
    /** 军团名称 */
    @DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
    public String name = null;
    public String getName() {return name;}
    /** 军团等级 */
    @DatabaseFieldAttribute(fieldName = "level",fieldType = Integer.class,arrayType = Byte.class)
    public Integer level = null;
    public Integer getLevel() {return level;}
    /** 入团是否需要验证 */
    @DatabaseFieldAttribute(fieldName = "is_need_validate",fieldType = Integer.class,arrayType = Byte.class)
    public Integer is_need_validate = null;
    public Integer getIs_need_validate() {return is_need_validate;}
    /** 军团总贡献值 */
    @DatabaseFieldAttribute(fieldName = "total_contribution",fieldType = Integer.class,arrayType = Byte.class)
    public Integer total_contribution = null;
    public Integer getTotal_contribution() {return total_contribution;}
    /** 公告 */
    @DatabaseFieldAttribute(fieldName = "notice",fieldType = String.class,arrayType = Byte.class)
    public String notice = null;
    public String getNotice() {return notice;}
    /** 图标索引 */
    @DatabaseFieldAttribute(fieldName = "imageIndex",fieldType = Integer.class,arrayType = Byte.class)
    public Integer imageIndex = null;
    public Integer getImageIndex() {return imageIndex;}
    /** 是否有效,1:有效,0 无效 */
    @DatabaseFieldAttribute(fieldName = "is_enable",fieldType = Integer.class,arrayType = Byte.class)
    public Integer is_enable = null;
    public Integer getIs_enable() {return is_enable;}
    /** 战斗状态,0:活跃期 1:战备期 2:战斗期 3:休整期 */
    @DatabaseFieldAttribute(fieldName = "battle_state",fieldType = Integer.class,arrayType = Byte.class)
    public Integer battle_state = null;
    public Integer getBattle_state() {return battle_state;}
    /** 资金 */
    @DatabaseFieldAttribute(fieldName = "battle_money",fieldType = Integer.class,arrayType = Byte.class)
    public Integer battle_money = null;
    public Integer getBattle_money() {return battle_money;}
    /** 军团物资 */
    @DatabaseFieldAttribute(fieldName = "legion_money",fieldType = Integer.class,arrayType = Byte.class)
    public Integer legion_money = null;
    public Integer getLegion_money() {return legion_money;}
    /** 战斗状态开始时间 */
    @DatabaseFieldAttribute(fieldName = "battle_state_starttime",fieldType = Timestamp.class,arrayType = Long.class)
    public Long battle_state_starttime = null;
    public Long getBattle_state_starttime() {return battle_state_starttime;}
    /** 战斗积分 */
    @DatabaseFieldAttribute(fieldName = "battle_points",fieldType = Integer.class,arrayType = Byte.class)
    public Integer battle_points = null;
    public Integer getBattle_points() {return battle_points;}
    /** 对战军团ID */
    @DatabaseFieldAttribute(fieldName = "battle_legion_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer battle_legion_id = null;
    public Integer getBattle_legion_id() {return battle_legion_id;}
    /** 是否胜利 */
    @DatabaseFieldAttribute(fieldName = "is_win",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean is_win = null;
    public Boolean getIs_win() {return is_win;}
    /** 是否为进攻方 */
    @DatabaseFieldAttribute(fieldName = "is_attack",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean is_attack = null;
    public Boolean getIs_attack() {return is_attack;}
    /** 军团长国别 */
    @DatabaseFieldAttribute(fieldName = "nation",fieldType = String.class,arrayType = Byte.class)
    public String nation = null;
    public String getNation() {return nation;}
    /** 军团创建时间 */
    @DatabaseFieldAttribute(fieldName = "create_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long create_time = null;
    public Long getCreate_time() {return create_time;}
    /** 当前人数缓存，精确 */
    @DatabaseFieldAttribute(fieldName = "cur_num_cache",fieldType = Integer.class,arrayType = Byte.class)
    public Integer cur_num_cache = null;
    public Integer getCur_num_cache() {return cur_num_cache;}
    /** 最大人数 */
    @DatabaseFieldAttribute(fieldName = "max_member_num",fieldType = Integer.class,arrayType = Byte.class)
    public Integer max_member_num = null;
    public Integer getMax_member_num() {return max_member_num;}
    /** 军团最后活跃时间 */
    @DatabaseFieldAttribute(fieldName = "last_active_time",fieldType = Timestamp.class,arrayType = Long.class)
    public Long last_active_time = null;
    public Long getLast_active_time() {return last_active_time;}
    /** 所在数据库 */
    @DatabaseFieldAttribute(fieldName = "db_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer db_id = null;
    public Integer getDb_id() {return db_id;}
    /** 总战力 */
    @DatabaseFieldAttribute(fieldName = "total_fight_val",fieldType = Integer.class,arrayType = Byte.class)
    public Integer total_fight_val = null;
    public Integer getTotal_fight_val() {return total_fight_val;}
    /** 最后一次刷新战斗力时间 */
    @DatabaseFieldAttribute(fieldName = "last_flush_fight_time",fieldType = Long.class,arrayType = Byte.class)
    public Long last_flush_fight_time = null;
    public Long getLast_flush_fight_time() {return last_flush_fight_time;}
    /** 最后一次刷新军团时间 */
    @DatabaseFieldAttribute(fieldName = "last_flush_legion_time",fieldType = Long.class,arrayType = Byte.class)
    public Long last_flush_legion_time = null;
    public Long getLast_flush_legion_time() {return last_flush_legion_time;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseLegion __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseLegion();
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
    public DatabaseLegion diff()
    {
        DatabaseLegion ret = new DatabaseLegion();
        if (this.legion_id != null && (__self == null || !this.legion_id.equals(__self.legion_id)))
            ret.legion_id = this.legion_id;
        if (this.boss_id != null && (__self == null || !this.boss_id.equals(__self.boss_id)))
            ret.boss_id = this.boss_id;
        if (this.name != null && (__self == null || !this.name.equals(__self.name)))
            ret.name = this.name;
        if (this.level != null && (__self == null || !this.level.equals(__self.level)))
            ret.level = this.level;
        if (this.is_need_validate != null && (__self == null || !this.is_need_validate.equals(__self.is_need_validate)))
            ret.is_need_validate = this.is_need_validate;
        if (this.total_contribution != null && (__self == null || !this.total_contribution.equals(__self.total_contribution)))
            ret.total_contribution = this.total_contribution;
        if (this.notice != null && (__self == null || !this.notice.equals(__self.notice)))
            ret.notice = this.notice;
        if (this.imageIndex != null && (__self == null || !this.imageIndex.equals(__self.imageIndex)))
            ret.imageIndex = this.imageIndex;
        if (this.is_enable != null && (__self == null || !this.is_enable.equals(__self.is_enable)))
            ret.is_enable = this.is_enable;
        if (this.battle_state != null && (__self == null || !this.battle_state.equals(__self.battle_state)))
            ret.battle_state = this.battle_state;
        if (this.battle_money != null && (__self == null || !this.battle_money.equals(__self.battle_money)))
            ret.battle_money = this.battle_money;
        if (this.legion_money != null && (__self == null || !this.legion_money.equals(__self.legion_money)))
            ret.legion_money = this.legion_money;
        if (this.battle_state_starttime != null && (__self == null || !this.battle_state_starttime.equals(__self.battle_state_starttime)))
            ret.battle_state_starttime = this.battle_state_starttime;
        if (this.battle_points != null && (__self == null || !this.battle_points.equals(__self.battle_points)))
            ret.battle_points = this.battle_points;
        if (this.battle_legion_id != null && (__self == null || !this.battle_legion_id.equals(__self.battle_legion_id)))
            ret.battle_legion_id = this.battle_legion_id;
        if (this.is_win != null && (__self == null || !this.is_win.equals(__self.is_win)))
            ret.is_win = this.is_win;
        if (this.is_attack != null && (__self == null || !this.is_attack.equals(__self.is_attack)))
            ret.is_attack = this.is_attack;
        if (this.nation != null && (__self == null || !this.nation.equals(__self.nation)))
            ret.nation = this.nation;
        if (this.create_time != null && (__self == null || !this.create_time.equals(__self.create_time)))
            ret.create_time = this.create_time;
        if (this.cur_num_cache != null && (__self == null || !this.cur_num_cache.equals(__self.cur_num_cache)))
            ret.cur_num_cache = this.cur_num_cache;
        if (this.max_member_num != null && (__self == null || !this.max_member_num.equals(__self.max_member_num)))
            ret.max_member_num = this.max_member_num;
        if (this.last_active_time != null && (__self == null || !this.last_active_time.equals(__self.last_active_time)))
            ret.last_active_time = this.last_active_time;
        if (this.db_id != null && (__self == null || !this.db_id.equals(__self.db_id)))
            ret.db_id = this.db_id;
        if (this.total_fight_val != null && (__self == null || !this.total_fight_val.equals(__self.total_fight_val)))
            ret.total_fight_val = this.total_fight_val;
        if (this.last_flush_fight_time != null && (__self == null || !this.last_flush_fight_time.equals(__self.last_flush_fight_time)))
            ret.last_flush_fight_time = this.last_flush_fight_time;
        if (this.last_flush_legion_time != null && (__self == null || !this.last_flush_legion_time.equals(__self.last_flush_legion_time)))
            ret.last_flush_legion_time = this.last_flush_legion_time;
        return ret;
    }
    @Override
    public void set(DatabaseLegion value) {
        this.legion_id = value.legion_id;
        this.boss_id = value.boss_id;
        this.name = value.name;
        this.level = value.level;
        this.is_need_validate = value.is_need_validate;
        this.total_contribution = value.total_contribution;
        this.notice = value.notice;
        this.imageIndex = value.imageIndex;
        this.is_enable = value.is_enable;
        this.battle_state = value.battle_state;
        this.battle_money = value.battle_money;
        this.legion_money = value.legion_money;
        this.battle_state_starttime = value.battle_state_starttime;
        this.battle_points = value.battle_points;
        this.battle_legion_id = value.battle_legion_id;
        this.is_win = value.is_win;
        this.is_attack = value.is_attack;
        this.nation = value.nation;
        this.create_time = value.create_time;
        this.cur_num_cache = value.cur_num_cache;
        this.max_member_num = value.max_member_num;
        this.last_active_time = value.last_active_time;
        this.db_id = value.db_id;
        this.total_fight_val = value.total_fight_val;
        this.last_flush_fight_time = value.last_flush_fight_time;
        this.last_flush_legion_time = value.last_flush_legion_time;
    }
    @Override
    public boolean isEmpty() {
        if (this.legion_id != null) return false;
        if (this.boss_id != null) return false;
        if (this.name != null) return false;
        if (this.level != null) return false;
        if (this.is_need_validate != null) return false;
        if (this.total_contribution != null) return false;
        if (this.notice != null) return false;
        if (this.imageIndex != null) return false;
        if (this.is_enable != null) return false;
        if (this.battle_state != null) return false;
        if (this.battle_money != null) return false;
        if (this.legion_money != null) return false;
        if (this.battle_state_starttime != null) return false;
        if (this.battle_points != null) return false;
        if (this.battle_legion_id != null) return false;
        if (this.is_win != null) return false;
        if (this.is_attack != null) return false;
        if (this.nation != null) return false;
        if (this.create_time != null) return false;
        if (this.cur_num_cache != null) return false;
        if (this.max_member_num != null) return false;
        if (this.last_active_time != null) return false;
        if (this.db_id != null) return false;
        if (this.total_fight_val != null) return false;
        if (this.last_flush_fight_time != null) return false;
        if (this.last_flush_legion_time != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.legion_id = null;
        this.boss_id = null;
        this.name = null;
        this.level = null;
        this.is_need_validate = null;
        this.total_contribution = null;
        this.notice = null;
        this.imageIndex = null;
        this.is_enable = null;
        this.battle_state = null;
        this.battle_money = null;
        this.legion_money = null;
        this.battle_state_starttime = null;
        this.battle_points = null;
        this.battle_legion_id = null;
        this.is_win = null;
        this.is_attack = null;
        this.nation = null;
        this.create_time = null;
        this.cur_num_cache = null;
        this.max_member_num = null;
        this.last_active_time = null;
        this.db_id = null;
        this.total_fight_val = null;
        this.last_flush_fight_time = null;
        this.last_flush_legion_time = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.legion_id != null)
            ret += ("legion_id = " + this.legion_id.toString() + " ");
        if (this.boss_id != null)
            ret += ("boss_id = " + this.boss_id.toString() + " ");
        if (this.name != null)
            ret += ("name = " + this.name.toString() + " ");
        if (this.level != null)
            ret += ("level = " + this.level.toString() + " ");
        if (this.is_need_validate != null)
            ret += ("is_need_validate = " + this.is_need_validate.toString() + " ");
        if (this.total_contribution != null)
            ret += ("total_contribution = " + this.total_contribution.toString() + " ");
        if (this.notice != null)
            ret += ("notice = " + this.notice.toString() + " ");
        if (this.imageIndex != null)
            ret += ("imageIndex = " + this.imageIndex.toString() + " ");
        if (this.is_enable != null)
            ret += ("is_enable = " + this.is_enable.toString() + " ");
        if (this.battle_state != null)
            ret += ("battle_state = " + this.battle_state.toString() + " ");
        if (this.battle_money != null)
            ret += ("battle_money = " + this.battle_money.toString() + " ");
        if (this.legion_money != null)
            ret += ("legion_money = " + this.legion_money.toString() + " ");
        if (this.battle_state_starttime != null)
            ret += ("battle_state_starttime = " + this.battle_state_starttime.toString() + " ");
        if (this.battle_points != null)
            ret += ("battle_points = " + this.battle_points.toString() + " ");
        if (this.battle_legion_id != null)
            ret += ("battle_legion_id = " + this.battle_legion_id.toString() + " ");
        if (this.is_win != null)
            ret += ("is_win = " + this.is_win.toString() + " ");
        if (this.is_attack != null)
            ret += ("is_attack = " + this.is_attack.toString() + " ");
        if (this.nation != null)
            ret += ("nation = " + this.nation.toString() + " ");
        if (this.create_time != null)
            ret += ("create_time = " + this.create_time.toString() + " ");
        if (this.cur_num_cache != null)
            ret += ("cur_num_cache = " + this.cur_num_cache.toString() + " ");
        if (this.max_member_num != null)
            ret += ("max_member_num = " + this.max_member_num.toString() + " ");
        if (this.last_active_time != null)
            ret += ("last_active_time = " + this.last_active_time.toString() + " ");
        if (this.db_id != null)
            ret += ("db_id = " + this.db_id.toString() + " ");
        if (this.total_fight_val != null)
            ret += ("total_fight_val = " + this.total_fight_val.toString() + " ");
        if (this.last_flush_fight_time != null)
            ret += ("last_flush_fight_time = " + this.last_flush_fight_time.toString() + " ");
        if (this.last_flush_legion_time != null)
            ret += ("last_flush_legion_time = " + this.last_flush_legion_time.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "legion";
    }
    @Override
    public void check() {
    }
    @Override
    public String getPrimaryKeyName() {
        return "legion_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return legion_id;
    }
}