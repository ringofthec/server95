package gameserver.connection.attribute.info;

import gameserver.connection.attribute.ConItemAttr;
import gameserver.ipo.IPOManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.message.game.ClientMessagePassiveBuff;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_SCENE;
import gameserver.network.server.connection.Connection;
import gameserver.stat.StatManger;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.TransFormArgs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Exp;
import table.MT_Data_Item;
import table.MT_Data_ItemStrengthen;
import table.MT_Data_Vip;
import table.base.TableManager;
import commonality.Common;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;
import database.DatabaseItem;

/** 物品信息*/
public class ItemInfo
{
	private static final Logger logger = LoggerFactory.getLogger(ItemInfo.class);
	private Connection m_Connection = null;
	private ConItemAttr m_Attribute = null;
	private DatabaseItem m_Item = null;
	private MT_Data_Item m_Data = null;
	public ItemInfo(Connection connection,ConItemAttr attribute,DatabaseItem item){
		m_Connection = connection;
		m_Attribute = attribute;
		m_Item = item;
		m_Data = TableManager.GetInstance().TableItem().GetElement(m_Item.table_id);
	}
	public boolean isEquip() {
		return m_Data.Type() == Common.ITEMTYPE.Equip.Number();
	}
	public boolean isEquipDebris() {
		return m_Data.Type() == Common.ITEMTYPE.Equip_Debris.Number();
	}
	
	public DatabaseItem getM_Item() {
		return m_Item;
	}
	public int getTableId() {
		return m_Item.table_id;
	}
	public int getItemId() {
		return m_Item.item_id;
	}
	public int getItemColor() {
		return m_Data.Quality();
	}
	public boolean isWeapon() {
		return m_Data.ItemPart() == Common.EQUIPPART.Weapon.Number();
	}
	public int getNumber() {
		if (isEquip()) {
			if (m_Item.number <= 0)
				return 0;
			return 1;
		}
		return m_Item.number;
	}
	public MT_Data_Item getData() {
		return m_Data;
	}
	public int getStar() {
		return 1;
	}
	public int getLvl() {
		return m_Item.level;
	}
	public void setOwner(int heroId) {
		m_Item.is_on = heroId;
		Save();
	}
	public int getOwner() {
		return m_Item.is_on;
	}
	public int getExp() {
		return m_Item.exp;
	}
	public void ItemIncStrengthen() {
		m_Item.level += 1;
		m_Item.exp = 0;
		Save();
	}
	public void ItemIncStrengthenExp(int exp) {
		m_Item.exp += exp;
		Save();
	}
	public void ItemDecStrengthen() {
		m_Item.level -= 1;
		m_Item.level = Math.max(0, m_Item.level);
		Save();
	}
	/**设置物品数量 如果是装备的话 <=0则为删除该装备 在数据库标识为-1 表示已删除*/
	public void setItemNumber(int number, SETNUMBER_TYPE type, VmChannel vmChannel, 
			ProductChannel productChannel, long serial_num, String chain,boolean isShowPoto, Item_Channel itemch)
	{
		try {
			if (number < 0)
				throw new GameException("企图设置物品{} 的数量为 {}", m_Data.ID(), number);
			
			final int oldNumber = m_Item.number;
			// 1. 修改数量
			int num = m_Item.number;
			if (type == SETNUMBER_TYPE.SET)
				num = number;
			else if (type == SETNUMBER_TYPE.ADDITION) {
				if (num + number < num)
					throw new GameException("物品{}数量{}，添加{}个后会溢出", m_Data.ID(), num, number);
					
				num += number;
			}
			else if (type == SETNUMBER_TYPE.MINUS) {
				if (num - number > num)
					throw new GameException("物品{}数量{}，减去{}个后会溢出", m_Data.ID(), num, number);
					
				num -= number;
			}
			if (isEquip()) {
				num = (num <= 0 ? -1 : 1);
			} else {
				num = Math.max(0, num);	
			}
			m_Item.number = num;
			
			// 2. 处理特殊的物品变化
			if (getTableId() == ITEM_ID.EXP) {
				UpdateExp();
			}
			else if (getTableId() == ITEM_ID.VIP_EXP) {
				UpdateVipExp();
			}
			else if (getTableId() == ITEM_ID.MONEY) {
				UpdateMoney();
			} else if (getTableId() == ITEM_ID.CP) {
				int maxCp = m_Connection.getCommon().GetValue(LIMIT_TYPE.CP);
				if (oldNumber == maxCp && num < maxCp)
					m_Connection.getPlayer().UpdatePlayerCpTime();
			}
			
			// 3. 保存
			Save();
			
			// 4. 记录日志
			int log_type = 0;
			long change_num = 0;
			int off = oldNumber - m_Item.number;
			if (off > 0) {
				log_type = 2;
				change_num = (long) off;
			} else {
				log_type = 1;
				change_num = (long) Math.abs(off);
			}
			
			if (getTableId() != ITEM_ID.EXP && 
				getTableId() != ITEM_ID.VIP_EXP && 
				getTableId() != ITEM_ID.CP &&
				getTableId() != ITEM_ID.GOLD) {
				IPOManagerDb.getInstance().UserProductLog(m_Connection, serial_num, log_type, 
						change_num, 0, chain, productChannel, 
						"item_" + getTableId(), getNumber());
			} else if (getTableId() == ITEM_ID.GOLD) {
				IPOManagerDb.getInstance().UserMoneyLog(m_Connection, serial_num, log_type, 
						change_num, chain, vmChannel, 
						2, 0.0, "toy_war.2000.gold", getNumber());
			}
			
			String nation = m_Connection.getPlayer().getNation();
			
			LogService.logItem(m_Connection.getPlayerId(), serial_num, 
					type.Number(), getTableId(), number, oldNumber, m_Item.number, itemch.getNum(),nation
					,m_Connection.getPlayer().getLevel()
					,m_Connection.getPlayer().getVipLevel());
			
			if (getTableId() == ITEM_ID.MONEY) {
				StatManger.getInstance().onMoneyChange(type, number, itemch);
			} else if (getTableId() == ITEM_ID.GOLD) {
				StatManger.getInstance().onGoldChange(type, number, itemch);
			}
			
			// 5. 客户端提示
			if (isShowPoto) {
				if (type == SETNUMBER_TYPE.ADDITION &&
						getTableId() != ITEM_ID.EXP &&
						getTableId() != ITEM_ID.CP &&
						getTableId() != ITEM_ID.MONEY)
					{
						m_Connection.ShowPrompt(PromptType.RECIVE_ITEM, PROMPT_SCENE.CITY, TransFormArgs.CreateItemArgs(getTableId()), number);
					}
			}
		}
		catch (Exception e) {
			logger.error("setItemNumber is error : ",e);
		}
	}
	/**
	 * 处理玩家经验
	 */
	void UpdateExp() {	
		int exp = m_Item.number;
		boolean isUp=true;
		while(true) {
			MT_Data_Exp dataExp = m_Connection.getPlayer().GetPlayerExpData();
			if (dataExp == null)
				break;

			if (exp < dataExp.exp())
				break;

			isUp=m_Connection.getPlayer().LevelUp();
			if(!isUp)
			{
				exp=dataExp.exp();
				break;
			}
			 exp -= dataExp.exp();
		}
		m_Item.number = exp;
		
		//更新pvpMatch
		m_Connection.getPlayer().setPvpMatchExp(exp);
	}
	/** 处理玩家金钱 */
	void UpdateMoney() {
		try {
			int maxNumber = m_Connection.getBuild().GetMaxMoneyCount();
			m_Item.number = Math.min(maxNumber, m_Item.number);
		}
		catch (Exception e) {
			logger.error("UpdateMoney is error : ", e);
		}
	}
	/** 处理玩家VIP经验 */
	void UpdateVipExp() {
		int exp = m_Item.number;
		int oldVipLv = m_Connection.getPlayer().getVipLevel();
		boolean isUp=true;
		while(true) {
			MT_Data_Vip dataVip = m_Connection.getPlayer().GetPlayerVipData();
			if (dataVip == null)
				break;

			if (exp < dataVip.Exp())
				break;

			isUp=m_Connection.getPlayer().VipLevelUp();
			if(!isUp)
			{
				exp= dataVip.Exp();
				break;
			}
			exp -= dataVip.Exp();
		}
		m_Item.number = exp;
		if (m_Connection.getPlayer().IsVipValid() == false)
			return;
		int newVipLv = m_Connection.getPlayer().getVipLevel();
		if (oldVipLv >= newVipLv)
			return;
		
		ClientMessagePassiveBuff.getInstance().UpdatePassiveBuffByVip(m_Connection, oldVipLv, newVipLv);
		//VIP升级记录日志
		long s_num = IPOManager.getInstance().getNextId();
		LogService.logEvent(m_Connection.getPlayerId(), s_num, 21, oldVipLv, newVipLv);
	}
	public void CombineDebris()
	{
		if (m_Data.Type() != Common.ITEMTYPE.Equip_Debris.Number())
			return;
		if (getNumber() < m_Data.Debris().field2())
			return;
		long s_num = IPOManagerDb.getInstance().getNextId();
		setItemNumber(m_Data.Debris().field2(), SETNUMBER_TYPE.MINUS, VmChannel.CompoundFromOtherVm, 
				ProductChannel.CompoundFromOtherGoods, s_num, "", true, Item_Channel.COMBONE_DEBRIS);
		m_Attribute.setItemNumber(m_Data.Debris().field1(), 1, SETNUMBER_TYPE.ADDITION, VmChannel.CompoundFromOtherVm, 
				ProductChannel.CompoundFromOtherGoods, s_num, "", true, Item_Channel.COMBONE_DEBRIS);
		LogService.logEvent(m_Attribute.getCon().getPlayerId(), s_num, 26);
		Save();
	}
	void Save() {
		m_Attribute.InsertNeedUpdate(m_Item.item_id);
		m_Connection.pushSave(m_Item);
		m_Connection.getItem().addCache(m_Item);
	}
	
	public int getTotalExp() {
		int totalExp = 0;
		for(int i=0;i<getLvl();i++)
		{
			MT_Data_ItemStrengthen itemData = TableManager.GetInstance().TableItemStrengthen().GetElement(i);
			totalExp += itemData.getM_arrayexpmax().get(getItemColor() - 1).field2();
		}
		totalExp += getExp();
		
		return totalExp;
	}
}
