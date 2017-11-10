package gameserver.connection.attribute.info;

import gameserver.cache.TechCache;
import gameserver.connection.attribute.ConTechAttr;
import gameserver.network.server.connection.Connection;
import table.MT_Data_TechInfo;
import table.base.TableManager;
import database.DatabaseTech;

public class TechInfo
{
	private Connection m_Connection;
	private ConTechAttr m_Attribute;
	private DatabaseTech m_Tech;
	private MT_Data_TechInfo m_Data;
	public TechInfo(Connection connection,ConTechAttr attribute,DatabaseTech tech)
	{
		m_Connection = connection;
		m_Attribute = attribute;
		m_Tech = tech;
		m_Tech.sync();
		m_Data = TableManager.GetInstance().TableTechInfo().GetElement(m_Tech.table_id + m_Tech.level);
	}
	public int getTableId()
	{
		return m_Tech.table_id;
	}
	public int getLevel()
	{
		return m_Tech.level;
	}
	public int getBuffId() {
		return m_Data.Skill();
	}
	public MT_Data_TechInfo getData()
	{
		return m_Data;
	}
	public boolean isMaxLevel()
	{
		return false;
	}
	public void levelUp()
	{
		++m_Tech.level;
		m_Data = TableManager.GetInstance().TableTechInfo().GetElement(m_Tech.table_id + m_Tech.level);
		save();
	}
	private void save()
	{
		m_Attribute.InsertNeedUpdate(m_Tech.table_id);
		m_Connection.pushSave(m_Tech);
		m_Connection.getTech().addHeroCache(m_Tech);
	}
	public Integer getId() {
		return m_Tech.tech_id;
	}
	
	public DatabaseTech getM_Tech() {
		return m_Tech;
	}
}