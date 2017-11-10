package gameserver.connection.attribute.info;
import gameserver.connection.attribute.ConAirSupportAttr;
import gameserver.network.server.connection.Connection;
import table.MT_Data_AirSupport;
import table.base.TableManager;
import commonality.Common.SETNUMBER_TYPE;
import database.DatabaseAirsupport;
public class AirSupportInfo
{
	private DatabaseAirsupport m_AirSupport;
	private MT_Data_AirSupport m_Data;
	private ConAirSupportAttr m_Attribute;
	private Connection m_Connection;
	public AirSupportInfo(Connection connection,ConAirSupportAttr attribute, DatabaseAirsupport airSupport) {
		m_Connection = connection;
		m_Attribute = attribute;
		m_AirSupport = airSupport;
		m_AirSupport.sync();
		m_Data = TableManager.GetInstance().TableAirSupport().GetElement(m_AirSupport.table_id + m_AirSupport.level);
	}
	public int GetTableId() {
		return m_AirSupport.table_id;
	}
	public int GetLevel() {
		return m_AirSupport.level;
	}
	public void UpgradePlane() {
		m_AirSupport.level++;
		m_Data = TableManager.GetInstance().TableAirSupport().GetElement(m_AirSupport.table_id + m_AirSupport.level);
		Save();
	}
	public boolean IsOutFighting() {
		if (m_AirSupport.out_fighting == null)
			return false;
		return m_AirSupport.out_fighting;
	}
	public MT_Data_AirSupport GetData() {
		return m_Data;
	}
	public int GetDurable() {
		return m_AirSupport.durable;
	}
	/** 收回空中支援（空中支援休息 不出战 ）*/
	public void TakeBack() {
		m_AirSupport.out_fighting = false;
		Save();	
	}
	
	public DatabaseAirsupport getM_AirSupport() {
		return m_AirSupport;
	}
	/** 收回空中支援（空中支援出战 ）*/
	public void OutFighting() {
		m_AirSupport.out_fighting = true;
		Save();
	}
	public void SetDurable(int value, SETNUMBER_TYPE type) {
//		int durable = m_AirSupport.durable;
//		if (type == SETNUMBER_TYPE.SET)
//			durable = value;
//		else if (type == SETNUMBER_TYPE.ADDITION)
//			durable += value;
//		else if (type == SETNUMBER_TYPE.MINUS)
//			durable -= value;
//		durable = Math.min(durable, m_Data.MaxDurable());
//		durable = Math.max(durable, 0);
//		m_AirSupport.durable = durable;
//		m_AirSupport.durable = m_Data.MaxDurable();
//		Save();
	}
	public void Repair() {
		SetDurable(m_Data.MaxDurable(), SETNUMBER_TYPE.SET);
	}
	public void Save() {
		m_Attribute.InsertNeedUpdate(GetTableId());
		m_Connection.pushSave(m_AirSupport);
		m_Connection.getAir().addCache(m_AirSupport);
	}
}