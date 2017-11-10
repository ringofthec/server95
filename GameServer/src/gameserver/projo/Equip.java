package gameserver.projo;
public class Equip {
    
	/** 物品ID */
    public Integer item_id ;
	/** 物品表ID（table表ID） */
    public Integer table_id;
    /** 装备部位 */
    public int pos ;
    
    public Equip(Integer item_id, Integer table_id, int pos) {
		super();
		this.item_id = item_id;
		this.table_id = table_id;
		this.pos = pos;
	}
    
    public Integer getItem_id() {
		return item_id;
	}

	public void setItem_id(Integer item_id) {
		this.item_id = item_id;
	}

	public Integer getTable_id() {
		return table_id;
	}

	public void setTable_id(Integer table_id) {
		this.table_id = table_id;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
}