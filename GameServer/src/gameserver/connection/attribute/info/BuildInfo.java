package gameserver.connection.attribute.info;

import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.ipo.IPOManager;
import gameserver.logging.LogService;
import gameserver.network.message.game.ClientMessageCommon;
import gameserver.network.protos.common.ProLegion.Proto_SeekHelp_BuildType;
import gameserver.network.protos.game.CommonProto.Proto_BuildState;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.Util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_DataBase;
import table.MT_Data_Building;
import table.MT_Data_assets;
import table.MT_Data_depot;
import table.MT_Data_factory;
import table.MT_Data_institute;
import table.MT_Data_legionConfig;
import table.MT_Data_main;
import table.MT_Data_tech;
import table.MT_TableEnum;
import table.base.TableManager;

import com.commons.util.GridRect;
import com.commons.util.GridSize;
import com.commons.util.TimeUtil;

import commonality.Common;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;
import commonality.Common.LIMIT_TYPE;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ITEM_ID;
import database.CustomPossessions;
import database.DatabaseBuild;

public class BuildInfo{
	protected static final Logger logger = LoggerFactory.getLogger(BuildInfo.class);
	protected Connection m_Connection;
	protected ConPlayerAttr m_player;
	protected ConBuildAttr m_Attribute;
	protected DatabaseBuild m_Build;
	public DatabaseBuild getM_Build() {
		return m_Build;
	}

	protected MT_Data_Building m_DataBuilding;		//build表的配置信息
	protected MT_DataBase m_DataBase;				//根据建筑的等级取出的配置信息
	protected GridSize m_Size;
	protected double m_EarningsTime;				//收集一个单位的资源需要的时间（秒）
	
	public BuildInfo(Connection connection,ConBuildAttr attribute,DatabaseBuild build) throws Exception{
		m_Connection = connection;
		m_player = connection.getPlayer();
		m_Attribute = attribute;
		m_Build = build;
		if (m_Build.corpsArray == null)
			m_Build.corpsArray = new ArrayList<CustomPossessions>();
		m_DataBuilding = TableManager.GetInstance().TableBuilding().GetElement(m_Build.table_id);
		m_DataBase = Util.GetDataBaseByData(m_DataBuilding, m_Build.level);
		Int2 volume = (Int2)m_DataBase.GetDataByString(MT_TableEnum.Volume);
		m_Size = new GridSize(volume.field1(),volume.field2());
	}
	
	public boolean isQueue() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.Queue.Number());
	}
	public boolean isFactory() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.Factory.Number());
	}
	public boolean isAssets() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.Assets.Number());
	}
	public boolean isInstitute() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.Institute.Number());
	}
	public boolean isTech() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.Tech.Number());
	}
	public boolean isMainCity() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.MainCity.Number());
	}
	public boolean isDepot() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.Depot.Number());
	}
	public boolean isWall() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.Wall.Number());
	}
	public boolean isFlag() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.FLAG.Number());
	}
	public boolean isMedal() {
		return m_DataBuilding.Type().equals(Common.BUILDTYPE.MEDAL.Number());
	}
	public double getEarningsTime() {
		return m_EarningsTime;
	}
	public int getTableId(){
		return m_Build.table_id;
	}
	public int getPosX() {
		return m_Build.posx;
	}
	public int getPosY() {
		return m_Build.posy;
	}
	public int getBuildId() {
		return m_Build.build_id;
	}
	public MT_Data_Building getDataBuilding() {
		return m_DataBuilding;
	}
	public MT_DataBase getDataBase() {
		return m_DataBase;
	}
	public int getLevel() {
		return m_Build.level;
	}
	public Proto_BuildState getState() {
		return m_Build.state;
	}
	public boolean isNormalState() {
		return m_Build.state == Proto_BuildState.NONE_BUILD;
	}
	public boolean isUpgradState() {
		return m_Build.state == Proto_BuildState.UPGRADE;
	}
	public boolean isOperateState() {
		return m_Build.state == Proto_BuildState.OPERATE;
	}
	public boolean IsUpgrading() {
		return (m_Build.state == Proto_BuildState.UPGRADE || 
				m_Build.state == Proto_BuildState.OPERATE_UPGRADE);
	}
	public long getupgradeTime() {
		return m_Build.upgradetime;
	}
	public long getLastOpTime() {
		return m_Build.lastoperatetime;
	}
	public long getGratherTime() {
		return m_Build.gathertime;
	}
	public int getType(){
		return m_DataBuilding.Type();
	}
	public GridSize getSize(){
		return m_Size;
	}
	public GridRect getRect(){
		return new GridRect(m_Build.posx,m_Build.posy,m_Size.width,m_Size.height);
	}
	public void setPosition(int x,int y) {
		m_Build.posx = x;
		m_Build.posy = y;
		save();
	}
	public List<CustomPossessions> getCorpsArray() {
		return m_Build.corpsArray;
	}
	private void BuildLevelUp() throws Exception {
		++ m_Build.level;
		m_DataBase = Util.GetDataBaseByData(m_DataBuilding, m_Build.level);
		UpdateEarningsTime();
	}
	public int getCurWorkTableId() throws Exception {
		if (m_Build.corpsArray.isEmpty())
			throw new Exception("根本没有正在升级的啊！！！！！！！！");
		
		return m_Build.corpsArray.get(0).id;
	}
	
	public void buildUpgrade() throws GameException {
		long nowTime = TimeUtil.GetDateTime();
		if (isNormalState()){
			if (isAssets()) 
				gatherResource();
			m_Build.state = Proto_BuildState.UPGRADE;
		}else if (isOperateState()){
			m_Build.state = Proto_BuildState.OPERATE_UPGRADE;
		}
		m_Build.upgradetime = nowTime;
		
		//建筑升级给玩家增加经验，去掉建筑建造
		if(m_Build.level !=0){
			long sum = IPOManager.getInstance().getNextId();
			MT_DataBase mt_DataBase = m_Connection.getBuild().getBuildDataBase(m_Build.build_id);
			if (isMainCity()) {
				MT_Data_main mainCity = (MT_Data_main)mt_DataBase;
				if(mainCity.LevelUpExp() != 0){
					m_Connection.getItem().setItemNumber(ITEM_ID.EXP, mainCity.LevelUpExp(),SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, sum, "", Item_Channel.BUILD_UPGRADE);
					m_Connection.ShowPrompt(PromptType.REC_EXP,mainCity.LevelUpExp());
				}
			}else if(isAssets()){
				MT_Data_assets assets = (MT_Data_assets)mt_DataBase;
				if(assets.LevelUpExp() != 0){
					m_Connection.getItem().setItemNumber(ITEM_ID.EXP, assets.LevelUpExp(),SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, sum, "", Item_Channel.BUILD_UPGRADE);
					m_Connection.ShowPrompt(PromptType.REC_EXP,assets.LevelUpExp());
				}
			}else if(isDepot()){
				MT_Data_depot depot = (MT_Data_depot)mt_DataBase;
				if(depot.LevelUpExp() != 0){
					m_Connection.getItem().setItemNumber(ITEM_ID.EXP, depot.LevelUpExp(),SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, sum, "", Item_Channel.BUILD_UPGRADE);
					m_Connection.ShowPrompt(PromptType.REC_EXP,depot.LevelUpExp());
				}
			}else if(isInstitute()){
				MT_Data_institute institute = (MT_Data_institute)mt_DataBase;
				if(institute.LevelUpExp() != 0){
					m_Connection.getItem().setItemNumber(ITEM_ID.EXP, institute.LevelUpExp(),SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, sum, "", Item_Channel.BUILD_UPGRADE);
					m_Connection.ShowPrompt(PromptType.REC_EXP,institute.LevelUpExp());
				}
				
			}else if(isTech()){
				MT_Data_tech tech = (MT_Data_tech)mt_DataBase;
				if(tech.LevelUpExp() != 0){
					m_Connection.getItem().setItemNumber(ITEM_ID.EXP, tech.LevelUpExp(),SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, sum, "", Item_Channel.BUILD_UPGRADE);
					m_Connection.ShowPrompt(PromptType.REC_EXP,tech.LevelUpExp());
				}
			}else if(isFactory()){
				MT_Data_factory factory = (MT_Data_factory)mt_DataBase;
				if(factory.LevelUpExp()!=0){
					m_Connection.getItem().setItemNumber(ITEM_ID.EXP, factory.LevelUpExp(),SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods, ProductChannel.PurchaseGoods, sum, "", Item_Channel.BUILD_UPGRADE);
					m_Connection.ShowPrompt(PromptType.REC_EXP,factory.LevelUpExp());
				}
			}
		}
		save();
	}
	
	public void checkBuild() throws Exception{
		try {
			long nowTime = TimeUtil.GetDateTime();
			switch (m_Build.state){
			// 正在升级
			case UPGRADE: {
				long needtimeread = calcUpgradeLeftNeedTimeBySecond(nowTime);
				//需求时间到了更新等级 否则无操作
				if (needtimeread <= 0)
					upgradeChangetoNone(nowTime, needtimeread);
				break;
			}
			// 正在进行建筑特有操作
			case OPERATE: {
				checkBuild_impl(nowTime);
				break;
			}
			// 产兵过程中升级
			case OPERATE_UPGRADE: {
				int needtimereal = calcUpgradeLeftNeedTimeBySecond(nowTime);
				if (needtimereal <= 0) {
					upgradeChangtoOperate(nowTime, needtimereal, getUpgradeNeedTimeByMillisSecond());
				}
				break;
			}
			case NONE_BUILD : {
				if (!m_Build.corpsArray.isEmpty()) {
					logger.error("playerid={}, buildid={}, tableid={}, corparray={}", m_Connection.getPlayerId(),
									m_Build.build_id, m_Build.table_id, m_Build.corpsArray.toString());
					m_Build.corpsArray.clear();
					save();
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("checkDatabaseBuild is error : ",e);
		}
	}

	private void upgradeChangtoOperate(long nowTime, int needtimereal, long incUpgradetime)
			throws Exception {
		m_Build.state = Proto_BuildState.OPERATE;
		deleteHelp();
		m_Build.lastoperatetime += incUpgradetime;
		BuildLevelUp();
		checkBuild_impl(nowTime);
		save();
	}

	private void upgradeChangetoNone(long nowTime,long needtimeread) throws Exception {
		deleteHelp();
		m_Build.state = Proto_BuildState.NONE_BUILD;
		BuildLevelUp();
		m_Build.gathertime = nowTime + needtimeread;
		if (isFlag()) {
			m_Connection.getCommon().UpdatePeople();
			ClientMessageCommon.getInstance().UpdateCountInfo(m_Connection, ProPvp.Proto_ActionType.PEOPLE_MAX, 0, m_Connection.getCommon().GetValue(LIMIT_TYPE.PEOPLE));
		}
		save();
	}

	protected void deleteHelp() throws Exception {
		if (m_player.getBelegionId()!=0){
			Proto_SeekHelp_BuildType state = getWorkState();
			m_Connection.getHelp().deleteHelp(m_Build, state);
		}
	}
	
	// 请求帮助次数
	protected int queryHelp(int needTime) throws Exception {
		Proto_SeekHelp_BuildType state = getWorkState();
		int helpNum = m_Connection.getHelp().getHelpCount(m_Build, state);
		MT_Data_legionConfig legionConfig = TableManager.GetInstance().TablelegionConfig().GetElement(1);
		if (needTime<=legionConfig.LessTime().field1()*60){
			int freeTime = helpNum*legionConfig.LessTime().field2()*60;//当求助操作总耗时低于2小时时，每次求组会减少1分钟的耗时;
			needTime = needTime - freeTime;
		}else{
			int freeTime = (int)helpNum*((legionConfig.LessTime().field3()*needTime)/100);//总耗时大于2小时，每次帮助都可以减少该操作1%的时间消耗
			needTime = needTime - freeTime;
		}
		return needTime;
	}

	public Proto_SeekHelp_BuildType getWorkState() {
		if (m_Build.state == Proto_BuildState.UPGRADE || m_Build.state == Proto_BuildState.OPERATE_UPGRADE){
			if (m_Build.level==0)
				return Proto_SeekHelp_BuildType.Build_create;
			else 
				return Proto_SeekHelp_BuildType.Build_Upgrade;
		}else if (m_Build.state.equals(Proto_BuildState.OPERATE)){
			if (isInstitute())
				return Proto_SeekHelp_BuildType.Corps_Upgrade;
			else if (isTech())
				return Proto_SeekHelp_BuildType.Tech_Upgrade;
		}
		
		return Proto_SeekHelp_BuildType.Build_none;
	}
	
	public boolean buildSpeedUp() throws Exception {
		checkBuild();
		if (m_Build.state != Proto_BuildState.UPGRADE && m_Build.state != Proto_BuildState.OPERATE_UPGRADE) 
			return false;
		int oldLevel = m_Build.level;
		long nowTime = TimeUtil.GetDateTime();
		int needtimeread = calcUpgradeLeftNeedTimeBySecond(nowTime);
		long speedUp = m_Connection.getCommon().speedUp(needtimeread, Item_Channel.SPEED_UPGRADER);
		if (speedUp!=-1) {
			if (m_Build.state == Proto_BuildState.UPGRADE)
				upgradeChangetoNone(nowTime, 0);
			else if (m_Build.state == Proto_BuildState.OPERATE_UPGRADE) 
				upgradeChangtoOperate(nowTime, 0, TimeUtil.GetDateTime() - m_Build.upgradetime);
		}
		LogService.logBuildingUpgrade(m_Build.build_id,m_Build.table_id,oldLevel,m_Build.level, speedUp, m_Connection.getPlayerId(), 1, m_Connection.getPlayer().getLevel());
		return speedUp==-1?false:true;
	}
	
	// 计算建筑升级需要时间
	public long getUpgradeNeedTimeByMillisSecond() throws Exception {
		int needTime = (int)m_DataBase.GetDataByString(MT_TableEnum.UpgradeTimer);
		if (m_Build.level == 0)
			needTime = m_Connection.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.BUILD_CREATE_TIME, needTime);
		else
			needTime = m_Connection.getBuffs().getValueByDecPassiveBuff(Common.PASSIVEBUFF_TYPE.BUILD_UP_TIME, needTime);
		if (m_player.getBelegionId()!=0)
			needTime = queryHelp(needTime);//请求帮助次数
		return needTime * 1000;
	}
	
	// 计算建筑升级剩余时间
	public int calcUpgradeLeftNeedTimeBySecond(long nowTime) throws Exception {
		long needTime = getUpgradeNeedTimeByMillisSecond();
		long passTime = nowTime - m_Build.upgradetime;	//过去的时间
		int leftTime = (int)((needTime-passTime)/1000);
		return leftTime < 0 ? 0 : leftTime;
	}
	
	public boolean operateSpeedUp() throws Exception {
		if (getState() != Proto_BuildState.OPERATE)
			throw new GameException("建筑状态不对");
		
		return speedup_imp(); 
	}
	
	public boolean isContainsCorp(int id) {
		if (m_Build.corpsArray != null) {
			for (CustomPossessions tempCorps : m_Build.corpsArray) {
				if (tempCorps.id == id)
					return true;
			}
		}
		return false;
	}
	
	protected void UpdateEarningsTime() throws Exception {
	}
	
	public int getMakingPopulation() {
		return 0;
	}
	
	public boolean IsCorpsLegal(int corpsId) throws Exception{
		return false;
	}
	
	public boolean IsTechLegal(int techId) throws Exception{
		return false;
	}
	
	public long getCorpFinishNeedTime() throws Exception {
		return 0;
	}
	
	public long getTechLeftNeedTimeByMilliSecond() throws Exception {
		return 0;
	}
	
	public long getInstituteFinishLeftTimeByMilliSecond() throws Exception {
		return 0;
	}

	protected void checkBuild_impl(long nowTime) throws Exception {
	}
	
	public void techUpgrade(int tech_table_id) throws GameException{
		throw new GameException("该建筑不能进行科技升级");
	}
	
	// 产出兵种 
	public void addMakeCorps(int corps_table_id, int count) throws GameException {
		throw new GameException("该建筑不能进行产兵操作");
	}
	
	/** 删除一个指定类型的正在制造中的兵种 */	
	public void delMakeCorps(int corps_table_id, int count) throws GameException {
		throw new GameException("该建筑不能进行产兵操作");
	}
	
	// 兵种升级 
	public void corpsUpgrade(int corps_table_id) throws GameException {
		throw new GameException("该建筑不能进行兵种升级操作");
	}
	
	/* 收取资源
	 */
	public void gatherResource() throws GameException {
		throw new GameException("该建筑不能进行收取金币操作");
	}
	
	protected boolean speedup_imp() throws Exception {
		return false;
	}
	
	// 获取当前建筑正在造兵的总个数
	public int getCurMakeCorpNum() {
		return 0;
	}
	
	// 获取当前建筑能够造兵的最大个数
	public int getCurBuildMaxCorp()
			throws Exception {
		return 0;
	}
	
	public boolean isMaxOut() throws Exception {
		return true;
	}
	
	public void save() {
		save(true);
	}
	
	protected void save(boolean isSync) {
		if (isSync == true)
			m_Attribute.InsertNeedUpdate(m_Build.build_id);
		m_Connection.pushSave(m_Build);
		m_Connection.getBuild().addCache(m_Build);
	}
}