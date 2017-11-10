package gameserver.utils;

import table.Int2;

public class UtilItem {
	private int m_ItemId;
	public void setM_ItemId(int m_ItemId) {
		this.m_ItemId = m_ItemId;
	}
	public void setM_Count(int m_Count) {
		this.m_Count = m_Count;
	}
	private int m_Count;
	public int GetItemId() {
		return m_ItemId;
	}
	public int GetCount() {
		return m_Count;
	}
	public UtilItem(int itemId,int count)
	{
		m_ItemId = itemId;
		m_Count = count;
	}
	public UtilItem(Int2 item)
	{
		m_ItemId = item.field1();
		m_Count = item.field2();
	}
}
