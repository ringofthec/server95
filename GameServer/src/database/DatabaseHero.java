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
//================英雄表===========================
@SuppressWarnings("unused")
public class DatabaseHero implements DatabaseTableDataBase<DatabaseHero>,Serializable {
    public static final String TableName = "Hero";
    /** 英雄唯一ID */
    @DatabaseFieldAttribute(fieldName = "hero_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hero_id = null;
    public Integer getHero_id() {return hero_id;}
    /** 英雄表中ID */
    @DatabaseFieldAttribute(fieldName = "hero_table_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hero_table_id = null;
    public Integer getHero_table_id() {return hero_table_id;}
    /** 选中英雄的关卡ID */
    @DatabaseFieldAttribute(fieldName = "hero_instance_id",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hero_instance_id = null;
    public Integer getHero_instance_id() {return hero_instance_id;}
    /** 玩家ID */
    @DatabaseFieldAttribute(fieldName = "player_id",fieldType = Long.class,arrayType = Byte.class)
    public Long player_id = null;
    public Long getPlayer_id() {return player_id;}
    /** 英雄身上的装备 */
    @DatabaseFieldAttribute(fieldName = "equips",fieldType = String.class,arrayType = CustomHeroEquip.class)
    public List<CustomHeroEquip> equips = null;
    public List<CustomHeroEquip> getEquips() {return equips;}
    /** 战斗力 */
    @DatabaseFieldAttribute(fieldName = "fight_val",fieldType = Integer.class,arrayType = Byte.class)
    public Integer fight_val = null;
    public Integer getFight_val() {return fight_val;}
    /** 星级 */
    @DatabaseFieldAttribute(fieldName = "star",fieldType = Integer.class,arrayType = Byte.class)
    public Integer star = null;
    public Integer getStar() {return star;}
    /** 血量等级 */
    @DatabaseFieldAttribute(fieldName = "hp_lvl",fieldType = Integer.class,arrayType = Byte.class)
    public Integer hp_lvl = null;
    public Integer getHp_lvl() {return hp_lvl;}
    /** 攻击等级 */
    @DatabaseFieldAttribute(fieldName = "attack_lvl",fieldType = Integer.class,arrayType = Byte.class)
    public Integer attack_lvl = null;
    public Integer getAttack_lvl() {return attack_lvl;}
    /** 防御等级 */
    @DatabaseFieldAttribute(fieldName = "defen_lvl",fieldType = Integer.class,arrayType = Byte.class)
    public Integer defen_lvl = null;
    public Integer getDefen_lvl() {return defen_lvl;}
    /** 技能列表 */
    @DatabaseFieldAttribute(fieldName = "skills",fieldType = String.class,arrayType = Integer.class)
    public List<Integer> skills = null;
    public List<Integer> getSkills() {return skills;}
    /** 升星的次数 */
    @DatabaseFieldAttribute(fieldName = "star_up_count",fieldType = Integer.class,arrayType = Byte.class)
    public Integer star_up_count = null;
    public Integer getStar_up_count() {return star_up_count;}
    /** 幸运值 */
    @DatabaseFieldAttribute(fieldName = "luck_val",fieldType = Integer.class,arrayType = Byte.class)
    public Integer luck_val = null;
    public Integer getLuck_val() {return luck_val;}
    /** 是否已初始化幸运值 */
    @DatabaseFieldAttribute(fieldName = "init_luck_val",fieldType = Integer.class,arrayType = Boolean.class)
    public Boolean init_luck_val = null;
    public Boolean getInit_luck_val() {return init_luck_val;}
    /** 克隆对象 用于获取改变的字段 */
    private DatabaseHero __self = null;
    @Override
    public void sync()
    {
        __self = new DatabaseHero();
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
    public DatabaseHero diff()
    {
        DatabaseHero ret = new DatabaseHero();
        if (this.hero_id != null && (__self == null || !this.hero_id.equals(__self.hero_id)))
            ret.hero_id = this.hero_id;
        if (this.hero_table_id != null && (__self == null || !this.hero_table_id.equals(__self.hero_table_id)))
            ret.hero_table_id = this.hero_table_id;
        if (this.hero_instance_id != null && (__self == null || !this.hero_instance_id.equals(__self.hero_instance_id)))
            ret.hero_instance_id = this.hero_instance_id;
        if (this.player_id != null && (__self == null || !this.player_id.equals(__self.player_id)))
            ret.player_id = this.player_id;
        if (this.equips != null && (__self == null || !this.equips.equals(__self.equips)))
            ret.equips = this.equips;
        if (this.fight_val != null && (__self == null || !this.fight_val.equals(__self.fight_val)))
            ret.fight_val = this.fight_val;
        if (this.star != null && (__self == null || !this.star.equals(__self.star)))
            ret.star = this.star;
        if (this.hp_lvl != null && (__self == null || !this.hp_lvl.equals(__self.hp_lvl)))
            ret.hp_lvl = this.hp_lvl;
        if (this.attack_lvl != null && (__self == null || !this.attack_lvl.equals(__self.attack_lvl)))
            ret.attack_lvl = this.attack_lvl;
        if (this.defen_lvl != null && (__self == null || !this.defen_lvl.equals(__self.defen_lvl)))
            ret.defen_lvl = this.defen_lvl;
        if (this.skills != null && (__self == null || !this.skills.equals(__self.skills)))
            ret.skills = this.skills;
        if (this.star_up_count != null && (__self == null || !this.star_up_count.equals(__self.star_up_count)))
            ret.star_up_count = this.star_up_count;
        if (this.luck_val != null && (__self == null || !this.luck_val.equals(__self.luck_val)))
            ret.luck_val = this.luck_val;
        if (this.init_luck_val != null && (__self == null || !this.init_luck_val.equals(__self.init_luck_val)))
            ret.init_luck_val = this.init_luck_val;
        return ret;
    }
    @Override
    public void set(DatabaseHero value) {
        this.hero_id = value.hero_id;
        this.hero_table_id = value.hero_table_id;
        this.hero_instance_id = value.hero_instance_id;
        this.player_id = value.player_id;
        this.equips = Utility.cloneList(value.equips);
        this.fight_val = value.fight_val;
        this.star = value.star;
        this.hp_lvl = value.hp_lvl;
        this.attack_lvl = value.attack_lvl;
        this.defen_lvl = value.defen_lvl;
        this.skills = Utility.clonePrimitiveList(value.skills);
        this.star_up_count = value.star_up_count;
        this.luck_val = value.luck_val;
        this.init_luck_val = value.init_luck_val;
    }
    @Override
    public boolean isEmpty() {
        if (this.hero_id != null) return false;
        if (this.hero_table_id != null) return false;
        if (this.hero_instance_id != null) return false;
        if (this.player_id != null) return false;
        if (this.equips != null) return false;
        if (this.fight_val != null) return false;
        if (this.star != null) return false;
        if (this.hp_lvl != null) return false;
        if (this.attack_lvl != null) return false;
        if (this.defen_lvl != null) return false;
        if (this.skills != null) return false;
        if (this.star_up_count != null) return false;
        if (this.luck_val != null) return false;
        if (this.init_luck_val != null) return false;
        return true;
    }
    @Override
    public void clear() {
        this.hero_id = null;
        this.hero_table_id = null;
        this.hero_instance_id = null;
        this.player_id = null;
        this.equips = null;
        this.fight_val = null;
        this.star = null;
        this.hp_lvl = null;
        this.attack_lvl = null;
        this.defen_lvl = null;
        this.skills = null;
        this.star_up_count = null;
        this.luck_val = null;
        this.init_luck_val = null;
    }
    @Override
    public String toString() {
        String ret = "";
        if (this.hero_id != null)
            ret += ("hero_id = " + this.hero_id.toString() + " ");
        if (this.hero_table_id != null)
            ret += ("hero_table_id = " + this.hero_table_id.toString() + " ");
        if (this.hero_instance_id != null)
            ret += ("hero_instance_id = " + this.hero_instance_id.toString() + " ");
        if (this.player_id != null)
            ret += ("player_id = " + this.player_id.toString() + " ");
        if (this.equips != null)
            ret += ("equips = " + this.equips.toString() + " ");
        if (this.fight_val != null)
            ret += ("fight_val = " + this.fight_val.toString() + " ");
        if (this.star != null)
            ret += ("star = " + this.star.toString() + " ");
        if (this.hp_lvl != null)
            ret += ("hp_lvl = " + this.hp_lvl.toString() + " ");
        if (this.attack_lvl != null)
            ret += ("attack_lvl = " + this.attack_lvl.toString() + " ");
        if (this.defen_lvl != null)
            ret += ("defen_lvl = " + this.defen_lvl.toString() + " ");
        if (this.skills != null)
            ret += ("skills = " + this.skills.toString() + " ");
        if (this.star_up_count != null)
            ret += ("star_up_count = " + this.star_up_count.toString() + " ");
        if (this.luck_val != null)
            ret += ("luck_val = " + this.luck_val.toString() + " ");
        if (this.init_luck_val != null)
            ret += ("init_luck_val = " + this.init_luck_val.toString() + " ");
        return ret;
    }
    @Override
    public String getTableName() {
        return "Hero";
    }
    @Override
    public void check() {
        List<DatabaseFieldDataBase<?>> invalid = new ArrayList<>();
        if (equips != null && equips.size() > 0)
        {
            invalid.clear();
            for (DatabaseFieldDataBase<?> data : equips) {
                if (data.isValid() == false)
				    invalid.add(data);
            }
            for (DatabaseFieldDataBase<?> data : invalid) {
			    equips.remove(data);
		    }
        }
    }
    @Override
    public String getPrimaryKeyName() {
        return "hero_id";
    }
    @Override
    public Object getPrimaryKeyValue() {
        return hero_id;
    }
}