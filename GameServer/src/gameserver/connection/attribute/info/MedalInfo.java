package gameserver.connection.attribute.info;

import gameserver.cache.HeroCache;
import gameserver.connection.attribute.ConHeroAttr;
import gameserver.network.server.connection.Connection;
import table.MT_Data_MedalAttribute;
import table.base.TableManager;
import database.DatabaseMedal;

public class MedalInfo {
	@SuppressWarnings("unused")
	private ConHeroAttr m_Attribute;
	private Connection m_Connection;
	private DatabaseMedal m_Medal;
	private MT_Data_MedalAttribute m_Data_mAttribute;
	public DatabaseMedal getMedal(){
		return m_Medal;
	}
	public MT_Data_MedalAttribute getData() {
		return m_Data_mAttribute;
	}
	public MedalInfo(Connection connection,ConHeroAttr attribute,DatabaseMedal medal){
		m_Connection = connection;
		m_Attribute = attribute;
		m_Medal = medal;
		m_Data_mAttribute = TableManager.GetInstance().TableMedalAttribute().GetElement(medal.medal_table_id + medal.cur_level);
	}
	public void upStar() {
		m_Medal.cur_star++;
		save();
	}
	public int getStar() {
		return m_Medal.cur_star;
	}
	public int getLvl() {
		return m_Medal.cur_level;
	}
	public int getExp() {
		return m_Medal.cur_exp;
	}
	public Integer getMedalTableId() {
		return m_Medal.medal_table_id;
	}
	public void setLevel(int level) {
		m_Medal.cur_level = level;
		m_Medal.cur_exp = 0;
		save();
	}
	public void upLev(int allExp) {
		//加经验,算等级
		m_Medal.cur_exp += allExp;
		while (true) {
			int tableId = m_Medal.medal_table_id+m_Medal.cur_level;
			MT_Data_MedalAttribute medalAttribute = TableManager.GetInstance().TableMedalAttribute().GetElement(tableId);
			if (m_Medal.cur_exp < medalAttribute.needExp())
				break;
			m_Medal.cur_level++;
			m_Medal.cur_exp -=medalAttribute.needExp();
		}
		m_Data_mAttribute = TableManager.GetInstance().TableMedalAttribute().GetElement(m_Medal.medal_table_id + m_Medal.cur_level);
		save();
	}
	public void save() {
		m_Connection.pushSave(m_Medal);
		HeroCache.updateMedal(m_Medal.hero_id, m_Connection.getPlayerId(), m_Medal);
	}
}