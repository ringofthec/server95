package gameserver.connection.attribute.info;

import gameserver.cache.CorpsCache;
import gameserver.connection.attribute.ConCorpsAttr;
import gameserver.network.server.connection.Connection;
import table.MT_Data_Corps;
import table.base.TableManager;
import commonality.Common.SETNUMBER_TYPE;
import database.DatabaseCorps;

public class CorpsInfo {
	private Connection m_Connection;
	private ConCorpsAttr m_Attribute;
	private DatabaseCorps m_Corps;
	private MT_Data_Corps m_Data;
	public CorpsInfo(Connection connectioin,ConCorpsAttr attribute,DatabaseCorps corps) {
		m_Connection = connectioin;
		m_Attribute = attribute;
		m_Corps = corps;
		m_Corps.sync();
		m_Data = TableManager.GetInstance().TableCorps().GetElement(m_Corps.table_id + m_Corps.level);
	}
	public MT_Data_Corps getData() {
		return m_Data;
	}
	
	public DatabaseCorps getM_Corps() {
		return m_Corps;
	}
	public int getTableId() {
		return m_Corps.table_id;
	}
	public int getNumber() {
		return m_Corps.number;
	}
	public int getLevel() {
		return m_Corps.level;
	}
	public void levelUp() {
		++m_Corps.level;
		m_Data = TableManager.GetInstance().TableCorps().GetElement(m_Corps.table_id + m_Corps.level);
		save();
	}
	public int getPopulation() {
		return m_Data.Population() * m_Corps.number;
	}
	public void setNumber(int value,SETNUMBER_TYPE type) {
		int number = m_Corps.number;
		if (type == SETNUMBER_TYPE.SET)
			number = value;
		else if (type == SETNUMBER_TYPE.ADDITION)
			number += value;
		else if (type == SETNUMBER_TYPE.MINUS)
			number -= value;
		number = Math.max(number, 0);
		m_Corps.number = number;
		save();	
	}
	private void save() {
		m_Attribute.InsertNeedUpdate(m_Corps.table_id);
		m_Connection.pushSave(m_Corps);
		m_Connection.getCorps().addCache(m_Corps);
	}
	public Integer getId() {
		return m_Corps.corps_id;
	}
}