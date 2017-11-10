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
public class DatabaseMoney_static implements DatabaseTableDataBase<DatabaseMoney_static>,Serializable {
    public static final String TableName = "money_static";
    /** 玩家id */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 单日最高 */
    @DatabaseFieldAttribute(fieldName = "max_day",fieldType = Long.class,arrayType = Byte.class)
    public Long max_day = null;
    public Long getMax_day() {return max_day;}
    /** 总赢取 */
    @DatabaseFieldAttribute(fieldName = "total",fieldType = Long.class,arrayType = Byte.class)
    public Long total = null;
    public Long getTotal() {return total;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "days7",fieldType = String.class,arrayType = CustomLong1Info.class)
    public List<CustomLong1Info> days7 = null;
    public List<CustomLong1Info> getDays7() {return days7;}
    /** 水果slots总赢取 */
    @DatabaseFieldAttribute(fieldName = "fruits_slot_total",fieldType = Long.class,arrayType = Byte.class)
    public Long fruits_slot_total = null;
    public Long getFruits_slot_total() {return fruits_slot_total;}
    /** 水果slots最高单次 */
    @DatabaseFieldAttribute(fieldName = "fruits_slot_max",fieldType = Long.class,arrayType = Byte.class)
    public Long fruits_slot_max = null;
    public Long getFruits_slot_max() {return fruits_slot_max;}
    /** 水果slots游戏局数 */
    @DatabaseFieldAttribute(fieldName = "fruits_slot_count",fieldType = Long.class,arrayType = Byte.class)
    public Long fruits_slot_count = null;
    public Long getFruits_slot_count() {return fruits_slot_count;}
    /** 水果slots玫瑰赛前三次数 */
    @DatabaseFieldAttribute(fieldName = "fruits_slot_rose_top3_count",fieldType = Long.class,arrayType = Byte.class)
    public Long fruits_slot_rose_top3_count = null;
    public Long getFruits_slot_rose_top3_count() {return fruits_slot_rose_top3_count;}
    /** 水果slots游戏奖池中中奖累计 */
    @DatabaseFieldAttribute(fieldName = "fruits_slot_pool_total",fieldType = Long.class,arrayType = Byte.class)
    public Long fruits_slot_pool_total = null;
    public Long getFruits_slot_pool_total() {return fruits_slot_pool_total;}
    /** 水果slots游戏奖池中中奖次数 */
    @DatabaseFieldAttribute(fieldName = "fruits_slot_pool_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer fruits_slot_pool_count = null;
    public Integer getFruits_slot_pool_count() {return fruits_slot_pool_count;}
    /** 金砖slots总赢取 */
    @DatabaseFieldAttribute(fieldName = "gold_slot_total",fieldType = Long.class,arrayType = Byte.class)
    public Long gold_slot_total = null;
    public Long getGold_slot_total() {return gold_slot_total;}
    /** 金砖slots最高单次 */
    @DatabaseFieldAttribute(fieldName = "gold_slot_max",fieldType = Long.class,arrayType = Byte.class)
    public Long gold_slot_max = null;
    public Long getGold_slot_max() {return gold_slot_max;}
    /** 金砖slots游戏局数 */
    @DatabaseFieldAttribute(fieldName = "gold_slot_count",fieldType = Long.class,arrayType = Byte.class)
    public Long gold_slot_count = null;
    public Long getGold_slot_count() {return gold_slot_count;}
    /** 金砖slots赢取金砖数 */
    @DatabaseFieldAttribute(fieldName = "gold_slot_gold",fieldType = Long.class,arrayType = Byte.class)
    public Long gold_slot_gold = null;
    public Long getGold_slot_gold() {return gold_slot_gold;}
    /** 金砖slots游戏奖池中中奖累计 */
    @DatabaseFieldAttribute(fieldName = "gold_slot_pool_total",fieldType = Long.class,arrayType = Byte.class)
    public Long gold_slot_pool_total = null;
    public Long getGold_slot_pool_total() {return gold_slot_pool_total;}
    /** 金砖slots游戏奖池中中奖次数 */
    @DatabaseFieldAttribute(fieldName = "gold_slot_pool_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer gold_slot_pool_count = null;
    public Integer getGold_slot_pool_count() {return gold_slot_pool_count;}
    /** 捕鱼游戏总赢取 */
    @DatabaseFieldAttribute(fieldName = "fish_total",fieldType = Long.class,arrayType = Byte.class)
    public Long fish_total = null;
    public Long getFish_total() {return fish_total;}
    /** 捕鱼游戏最高单次 */
    @DatabaseFieldAttribute(fieldName = "fish_max",fieldType = Long.class,arrayType = Byte.class)
    public Long fish_max = null;
    public Long getFish_max() {return fish_max;}
    /** 捕鱼游戏击杀金龙鱼次数 */
    @DatabaseFieldAttribute(fieldName = "fish_battle_kingfish",fieldType = Long.class,arrayType = Byte.class)
    public Long fish_battle_kingfish = null;
    public Long getFish_battle_kingfish() {return fish_battle_kingfish;}
    /** 捕鱼游戏击杀魔鬼鱼次数 */
    @DatabaseFieldAttribute(fieldName = "fish_battle_doomfish",fieldType = Long.class,arrayType = Byte.class)
    public Long fish_battle_doomfish = null;
    public Long getFish_battle_doomfish() {return fish_battle_doomfish;}
    /** 捕鱼游戏完成任务数 */
    @DatabaseFieldAttribute(fieldName = "fish_task_count",fieldType = Long.class,arrayType = Byte.class)
    public Long fish_task_count = null;
    public Long getFish_task_count() {return fish_task_count;}
    /** 捕鱼游戏捕鱼总条数 */
    @DatabaseFieldAttribute(fieldName = "fish_catch_fish_total",fieldType = Long.class,arrayType = Byte.class)
    public Long fish_catch_fish_total = null;
    public Long getFish_catch_fish_total() {return fish_catch_fish_total;}
    /**  */
    @DatabaseFieldAttribute(fieldName = "millstone",fieldType = String.class,arrayType = CustomMillStone.class)
    public List<CustomMillStone> millstone = null;
    public List<CustomMillStone> getMillstone() {return millstone;}
    /** 牛牛次数 */
    @DatabaseFieldAttribute(fieldName = "niuniu_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer niuniu_count = null;
    public Integer getNiuniu_count() {return niuniu_count;}
    /** 牛牛游戏总赢取(作为闲) */
    @DatabaseFieldAttribute(fieldName = "niuniu_x_win",fieldType = Long.class,arrayType = Byte.class)
    public Long niuniu_x_win = null;
    public Long getNiuniu_x_win() {return niuniu_x_win;}
    /** 牛牛游戏总输(作为闲) */
    @DatabaseFieldAttribute(fieldName = "niuniu_x_lose",fieldType = Long.class,arrayType = Byte.class)
    public Long niuniu_x_lose = null;
    public Long getNiuniu_x_lose() {return niuniu_x_lose;}
    /** 牛牛游戏总赢取(作为庄) */
    @DatabaseFieldAttribute(fieldName = "niuniu_z_win",fieldType = Long.class,arrayType = Byte.class)
    public Long niuniu_z_win = null;
    public Long getNiuniu_z_win() {return niuniu_z_win;}
    /** 牛牛游戏总输(作为庄) */
    @DatabaseFieldAttribute(fieldName = "niuniu_z_lose",fieldType = Long.class,arrayType = Byte.class)
    public Long niuniu_z_lose = null;
    public Long getNiuniu_z_lose() {return niuniu_z_lose;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseMoney_static __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseMoney_static();
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
    public DatabaseMoney_static diff()
    {
        DatabaseMoney_static ret = new DatabaseMoney_static();
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.max_day != null && (__self == null || !this.max_day.equals(__self.max_day)))
            ret.max_day = this.max_day;
        if (this.total != null && (__self == null || !this.total.equals(__self.total)))
            ret.total = this.total;
        if (this.days7 != null && (__self == null || !this.days7.equals(__self.days7)))
            ret.days7 = this.days7;
        if (this.fruits_slot_total != null && (__self == null || !this.fruits_slot_total.equals(__self.fruits_slot_total)))
            ret.fruits_slot_total = this.fruits_slot_total;
        if (this.fruits_slot_max != null && (__self == null || !this.fruits_slot_max.equals(__self.fruits_slot_max)))
            ret.fruits_slot_max = this.fruits_slot_max;
        if (this.fruits_slot_count != null && (__self == null || !this.fruits_slot_count.equals(__self.fruits_slot_count)))
            ret.fruits_slot_count = this.fruits_slot_count;
        if (this.fruits_slot_rose_top3_count != null && (__self == null || !this.fruits_slot_rose_top3_count.equals(__self.fruits_slot_rose_top3_count)))
            ret.fruits_slot_rose_top3_count = this.fruits_slot_rose_top3_count;
        if (this.fruits_slot_pool_total != null && (__self == null || !this.fruits_slot_pool_total.equals(__self.fruits_slot_pool_total)))
            ret.fruits_slot_pool_total = this.fruits_slot_pool_total;
        if (this.fruits_slot_pool_count != null && (__self == null || !this.fruits_slot_pool_count.equals(__self.fruits_slot_pool_count)))
            ret.fruits_slot_pool_count = this.fruits_slot_pool_count;
        if (this.gold_slot_total != null && (__self == null || !this.gold_slot_total.equals(__self.gold_slot_total)))
            ret.gold_slot_total = this.gold_slot_total;
        if (this.gold_slot_max != null && (__self == null || !this.gold_slot_max.equals(__self.gold_slot_max)))
            ret.gold_slot_max = this.gold_slot_max;
        if (this.gold_slot_count != null && (__self == null || !this.gold_slot_count.equals(__self.gold_slot_count)))
            ret.gold_slot_count = this.gold_slot_count;
        if (this.gold_slot_gold != null && (__self == null || !this.gold_slot_gold.equals(__self.gold_slot_gold)))
            ret.gold_slot_gold = this.gold_slot_gold;
        if (this.gold_slot_pool_total != null && (__self == null || !this.gold_slot_pool_total.equals(__self.gold_slot_pool_total)))
            ret.gold_slot_pool_total = this.gold_slot_pool_total;
        if (this.gold_slot_pool_count != null && (__self == null || !this.gold_slot_pool_count.equals(__self.gold_slot_pool_count)))
            ret.gold_slot_pool_count = this.gold_slot_pool_count;
        if (this.fish_total != null && (__self == null || !this.fish_total.equals(__self.fish_total)))
            ret.fish_total = this.fish_total;
        if (this.fish_max != null && (__self == null || !this.fish_max.equals(__self.fish_max)))
            ret.fish_max = this.fish_max;
        if (this.fish_battle_kingfish != null && (__self == null || !this.fish_battle_kingfish.equals(__self.fish_battle_kingfish)))
            ret.fish_battle_kingfish = this.fish_battle_kingfish;
        if (this.fish_battle_doomfish != null && (__self == null || !this.fish_battle_doomfish.equals(__self.fish_battle_doomfish)))
            ret.fish_battle_doomfish = this.fish_battle_doomfish;
        if (this.fish_task_count != null && (__self == null || !this.fish_task_count.equals(__self.fish_task_count)))
            ret.fish_task_count = this.fish_task_count;
        if (this.fish_catch_fish_total != null && (__self == null || !this.fish_catch_fish_total.equals(__self.fish_catch_fish_total)))
            ret.fish_catch_fish_total = this.fish_catch_fish_total;
        if (this.millstone != null && (__self == null || !this.millstone.equals(__self.millstone)))
            ret.millstone = this.millstone;
        if (this.niuniu_count != null && (__self == null || !this.niuniu_count.equals(__self.niuniu_count)))
            ret.niuniu_count = this.niuniu_count;
        if (this.niuniu_x_win != null && (__self == null || !this.niuniu_x_win.equals(__self.niuniu_x_win)))
            ret.niuniu_x_win = this.niuniu_x_win;
        if (this.niuniu_x_lose != null && (__self == null || !this.niuniu_x_lose.equals(__self.niuniu_x_lose)))
            ret.niuniu_x_lose = this.niuniu_x_lose;
        if (this.niuniu_z_win != null && (__self == null || !this.niuniu_z_win.equals(__self.niuniu_z_win)))
            ret.niuniu_z_win = this.niuniu_z_win;
        if (this.niuniu_z_lose != null && (__self == null || !this.niuniu_z_lose.equals(__self.niuniu_z_lose)))
            ret.niuniu_z_lose = this.niuniu_z_lose;
        return ret;
    }
    @Override
    public void set(DatabaseMoney_static value) {
        this.player_id = value.player_id;
        this.max_day = value.max_day;
        this.total = value.total;
        this.days7 = Utility.cloneList(value.days7);
        this.fruits_slot_total = value.fruits_slot_total;
        this.fruits_slot_max = value.fruits_slot_max;
        this.fruits_slot_count = value.fruits_slot_count;
        this.fruits_slot_rose_top3_count = value.fruits_slot_rose_top3_count;
        this.fruits_slot_pool_total = value.fruits_slot_pool_total;
        this.fruits_slot_pool_count = value.fruits_slot_pool_count;
        this.gold_slot_total = value.gold_slot_total;
        this.gold_slot_max = value.gold_slot_max;
        this.gold_slot_count = value.gold_slot_count;
        this.gold_slot_gold = value.gold_slot_gold;
        this.gold_slot_pool_total = value.gold_slot_pool_total;
        this.gold_slot_pool_count = value.gold_slot_pool_count;
        this.fish_total = value.fish_total;
        this.fish_max = value.fish_max;
        this.fish_battle_kingfish = value.fish_battle_kingfish;
        this.fish_battle_doomfish = value.fish_battle_doomfish;
        this.fish_task_count = value.fish_task_count;
        this.fish_catch_fish_total = value.fish_catch_fish_total;
        this.millstone = Utility.cloneList(value.millstone);
        this.niuniu_count = value.niuniu_count;
        this.niuniu_x_win = value.niuniu_x_win;
        this.niuniu_x_lose = value.niuniu_x_lose;
        this.niuniu_z_win = value.niuniu_z_win;
        this.niuniu_z_lose = value.niuniu_z_lose;
    }
    @Override
    public boolean isEmpty() {
        if (this.player_id != null) return false;
        if (this.max_day != null) return false;
        if (this.total != null) return false;
        if (this.days7 != null) return false;
        if (this.fruits_slot_total != null) return false;
        if (this.fruits_slot_max != null) return false;
        if (this.fruits_slot_count != null) return false;
        if (this.fruits_slot_rose_top3_count != null) return false;
        if (this.fruits_slot_pool_total != null) return false;
        if (this.fruits_slot_pool_count != null) return false;
        if (this.gold_slot_total != null) return false;
        if (this.gold_slot_max != null) return false;
        if (this.gold_slot_count != null) return false;
        if (this.gold_slot_gold != null) return false;
        if (this.gold_slot_pool_total != null) return false;
        if (this.gold_slot_pool_count != null) return false;
        if (this.fish_total != null) return false;
        if (this.fish_max != null) return false;
        if (this.fish_battle_kingfish != null) return false;
        if (this.fish_battle_doomfish != null) return false;
        if (this.fish_task_count != null) return false;
        if (this.fish_catch_fish_total != null) return false;
        if (this.millstone != null) return false;
        if (this.niuniu_count != null) return false;
        if (this.niuniu_x_win != null) return false;
        if (this.niuniu_x_lose != null) return false;
        if (this.niuniu_z_win != null) return false;
        if (this.niuniu_z_lose != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.player_id = null;
        this.max_day = null;
        this.total = null;
        this.days7 = null;
        this.fruits_slot_total = null;
        this.fruits_slot_max = null;
        this.fruits_slot_count = null;
        this.fruits_slot_rose_top3_count = null;
        this.fruits_slot_pool_total = null;
        this.fruits_slot_pool_count = null;
        this.gold_slot_total = null;
        this.gold_slot_max = null;
        this.gold_slot_count = null;
        this.gold_slot_gold = null;
        this.gold_slot_pool_total = null;
        this.gold_slot_pool_count = null;
        this.fish_total = null;
        this.fish_max = null;
        this.fish_battle_kingfish = null;
        this.fish_battle_doomfish = null;
        this.fish_task_count = null;
        this.fish_catch_fish_total = null;
        this.millstone = null;
        this.niuniu_count = null;
        this.niuniu_x_win = null;
        this.niuniu_x_lose = null;
        this.niuniu_z_win = null;
        this.niuniu_z_lose = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.max_day != null)
            ret += ("max_day = " + this.max_day.toString() + " ");
        if (this.total != null)
            ret += ("total = " + this.total.toString() + " ");
        if (this.days7 != null)
            ret += ("days7 = " + this.days7.toString() + " ");
        if (this.fruits_slot_total != null)
            ret += ("fruits_slot_total = " + this.fruits_slot_total.toString() + " ");
        if (this.fruits_slot_max != null)
            ret += ("fruits_slot_max = " + this.fruits_slot_max.toString() + " ");
        if (this.fruits_slot_count != null)
            ret += ("fruits_slot_count = " + this.fruits_slot_count.toString() + " ");
        if (this.fruits_slot_rose_top3_count != null)
            ret += ("fruits_slot_rose_top3_count = " + this.fruits_slot_rose_top3_count.toString() + " ");
        if (this.fruits_slot_pool_total != null)
            ret += ("fruits_slot_pool_total = " + this.fruits_slot_pool_total.toString() + " ");
        if (this.fruits_slot_pool_count != null)
            ret += ("fruits_slot_pool_count = " + this.fruits_slot_pool_count.toString() + " ");
        if (this.gold_slot_total != null)
            ret += ("gold_slot_total = " + this.gold_slot_total.toString() + " ");
        if (this.gold_slot_max != null)
            ret += ("gold_slot_max = " + this.gold_slot_max.toString() + " ");
        if (this.gold_slot_count != null)
            ret += ("gold_slot_count = " + this.gold_slot_count.toString() + " ");
        if (this.gold_slot_gold != null)
            ret += ("gold_slot_gold = " + this.gold_slot_gold.toString() + " ");
        if (this.gold_slot_pool_total != null)
            ret += ("gold_slot_pool_total = " + this.gold_slot_pool_total.toString() + " ");
        if (this.gold_slot_pool_count != null)
            ret += ("gold_slot_pool_count = " + this.gold_slot_pool_count.toString() + " ");
        if (this.fish_total != null)
            ret += ("fish_total = " + this.fish_total.toString() + " ");
        if (this.fish_max != null)
            ret += ("fish_max = " + this.fish_max.toString() + " ");
        if (this.fish_battle_kingfish != null)
            ret += ("fish_battle_kingfish = " + this.fish_battle_kingfish.toString() + " ");
        if (this.fish_battle_doomfish != null)
            ret += ("fish_battle_doomfish = " + this.fish_battle_doomfish.toString() + " ");
        if (this.fish_task_count != null)
            ret += ("fish_task_count = " + this.fish_task_count.toString() + " ");
        if (this.fish_catch_fish_total != null)
            ret += ("fish_catch_fish_total = " + this.fish_catch_fish_total.toString() + " ");
        if (this.millstone != null)
            ret += ("millstone = " + this.millstone.toString() + " ");
        if (this.niuniu_count != null)
            ret += ("niuniu_count = " + this.niuniu_count.toString() + " ");
        if (this.niuniu_x_win != null)
            ret += ("niuniu_x_win = " + this.niuniu_x_win.toString() + " ");
        if (this.niuniu_x_lose != null)
            ret += ("niuniu_x_lose = " + this.niuniu_x_lose.toString() + " ");
        if (this.niuniu_z_win != null)
            ret += ("niuniu_z_win = " + this.niuniu_z_win.toString() + " ");
        if (this.niuniu_z_lose != null)
            ret += ("niuniu_z_lose = " + this.niuniu_z_lose.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "money_static";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (days7 != null && days7.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : days7) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    days7.remove(data);
		    }
        }
        if (millstone != null && millstone.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : millstone) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    millstone.remove(data);
		    }
        }
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