package gameserver.projo;

import com.commons.database.DatabaseFieldAttribute;
import com.commons.database.DatabaseSimple;
import com.commons.database.DatabaseTableDataBase;

public class HeroInfoPoJo implements DatabaseTableDataBase<HeroInfoPoJo>{
//	h.hero_id,h.table_id,h.fight_val,p.name
	@DatabaseFieldAttribute(fieldName = "hero_id",fieldType = Integer.class,arrayType = Byte.class)
	public int hero_id;
	@DatabaseFieldAttribute(fieldName = "hero_table_id",fieldType = Integer.class,arrayType = Byte.class)
	public int hero_table_id;
	@DatabaseFieldAttribute(fieldName = "name",fieldType = String.class,arrayType = Byte.class)
	public String name;
	@DatabaseFieldAttribute(fieldName = "fight_val",fieldType = Long.class,arrayType = Byte.class)
	public int fight_val;
	@DatabaseFieldAttribute(fieldName = "feat",fieldType = Integer.class,arrayType = Byte.class)
	public int feat;
	
	@DatabaseFieldAttribute(fieldName = "player.player_id",fieldType = Long.class,arrayType = Byte.class)
	public int player_id;
	
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getFeat() {
		return feat;
	}
	public void setFeat(int feat) {
		this.feat = feat;
	}
	public int getHero_table_id() {
		return hero_table_id;
	}
	public void setHero_table_id(int hero_table_id) {
		this.hero_table_id = hero_table_id;
	}
	public int getHero_id() {
		return hero_id;
	}
	public void setHero_id(int hero_id) {
		this.hero_id = hero_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFight_val() {
		return fight_val;
	}
	public void setFight_val(int fight_val) {
		this.fight_val = fight_val;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void set(HeroInfoPoJo val) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public HeroInfoPoJo diff() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sync() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void check() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getPrimaryKeyName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getPrimaryKeyValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setDatabaseSimple(DatabaseSimple value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public DatabaseSimple getDatabaseSimple() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}
}
