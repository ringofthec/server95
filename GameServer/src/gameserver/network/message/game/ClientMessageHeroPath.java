package gameserver.network.message.game;

import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProFight;
import gameserver.network.protos.game.ProHero;
import gameserver.network.protos.game.ProHero.ProtoHeroInfo;
import gameserver.network.protos.game.ProHeroPath;
import gameserver.network.protos.game.ProHeroPath.Msg_C2G_HeroPath_OverFight;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroList;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroList.Proto_HeroPathInfo;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroPath;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroPathData;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroPathData.Proto_Formation;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroPathData.Proto_Formation_Lv;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroPathData.Proto_Recruit_Corps;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroPathData.Proto_Self_CorpsInfo;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_AskHeroPathData.Proto_Star;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_HeroPath_OverFight;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_HeroPath_Recive_Crop;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_InviteHero;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_OpenBox;
import gameserver.network.protos.game.ProHeroPath.Msg_G2C_StartHeroPath;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_SCENE;
import gameserver.network.protos.game.ProPve.Proto_CorpsType;
import gameserver.network.server.connection.Connection;
import gameserver.projo.HeroInfoPoJo;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.TransFormArgs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.Int6;
import table.MT_Data_Corps;
import table.MT_Data_DropOut;
import table.MT_Data_Hero;
import table.MT_Data_HeroPath;
import table.MT_Data_HeroPathConfig;
import table.base.TABLE;
import table.base.TableManager;

import com.commons.util.DatabaseUtil;
import com.commons.util.RandomUtil;

import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.Common.TASK_TYPE;
import commonality.ITEM_ID;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;
import database.CustomFormation;
import database.CustomFormationCrops;
import database.CustomHeroPathAlreadyPoint;
import database.CustomHeroPathCrop;
import database.DatabaseHero;
import database.DatabaseItem;
import database.DatabaseMedal;

public class ClientMessageHeroPath {
	protected static final Logger logger = LoggerFactory.getLogger(ClientMessageHeroPath.class);
	private final static ClientMessageHeroPath instance = new ClientMessageHeroPath();
	public static ClientMessageHeroPath getInstance() { return instance; }
	
	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_AskHeroPathData.class,this, "OnAskHeroPathData");//请求英雄之路相关数据
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_AskHeroList.class,this, "OnAskHeroList");//请求英雄列表
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_OpenBox.class,this, "OnOpenHeroPathBox");//开启宝箱
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_InviteHero.class,this, "OnRequestHero");//邀请英雄
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_HeroPath_OverFight.class,this, "OnEndHeroPath");//战斗结束
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_HeroPath_Recive_Crop.class,this, "OnReciveCrop");//领取士兵
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_Hero_Path_Refush.class,this, "OnRefush");//重置
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_AskHeroPath.class,this, "OnAskHeroPath");//请求战斗
		IOServer.getInstance().regMsgProcess(ProHeroPath.Msg_C2G_StartHeroPath.class,this, "OnStartHeroPath");//开始战斗
	}
	
	//英雄之路重置
	public void OnRefush(Connection connection, ProHeroPath.Msg_C2G_Hero_Path_Refush msg) throws GameException{
		if (!connection.getPlayer().canFush()) {
			return;
		}
		
		int curPoint = connection.getPlayer().getCurHeroPathPointId();
		connection.getPlayer().initHeroPathData();
		connection.getPlayer().incrHeroPathNum();
		sendAskHeroPathMsgToClint(connection);
		connection.getPlayer().sendHeroPathNum();
		LogService.logEvent(connection.getPlayerId(), -1, 35, curPoint);
	}
	
	//英雄开始战斗
	public void OnStartHeroPath(Connection connection, ProHeroPath.Msg_C2G_StartHeroPath msg) throws GameException{
		List<CustomFormation> customFormations = new ArrayList<>();
		
		//取得我方阵型
		List<gameserver.network.protos.game.ProPve.Proto_Formation> formation = msg.getInfoList();
		for (gameserver.network.protos.game.ProPve.Proto_Formation forma : formation) {
			CustomFormation custFormation = new CustomFormation();
			custFormation.id = forma.getId();//士兵id
			custFormation.x = forma.getPosX();//士兵位置
			custFormation.y = forma.getPosY();
			custFormation.type = forma.getType().getNumber();//士兵类型
			customFormations.add(custFormation);
		}
		//更新玩家当前关卡阵型
		connection.getPlayer().updateCurPointForMation(customFormations);
		
		//0：请求开始战斗正常 1：请求战斗出错
		connection.sendPushMessage(Msg_G2C_StartHeroPath.newBuilder().setError(0).build());
	}
	
	//请求本军团的英雄列表
	public void OnAskHeroList(Connection connection, ProHeroPath.Msg_C2G_AskHeroList msg) throws GameException {
		//当前英雄的战斗力
		int fightVal = connection.getHero().getFightVal();
		int legionId = connection.getPlayer().getBelegionId();
		List<HeroInfoPoJo> heros = new ArrayList<>();
		
		// 先去库里查自己帮会的英雄
		if (legionId != 0) {
			try {
				int max = (int) (fightVal+fightVal*0.2);
				for (DatabaseUtil util : DbMgr.getInstance().getGSDb().values()) {
					List<HeroInfoPoJo> others = util.SelectOrige(HeroInfoPoJo.class, "select hero_id,hero_table_id,name,fight_val,player.player_id,feat from Hero ,player where fight_val <= "+max+" and fight_val >=2000 and Hero.player_id <> "+connection.getPlayerId()+" and belong_legion = "+legionId+" and Hero.player_id = player.player_id");
					if (others != null && !others.isEmpty()) 
						heros.addAll(others);
				}
			} catch (Exception e) {
				logger.error("请求英雄列表出错 ,",e);
			}
		}
		
		// 没有可选英雄的话就去加一些机器人英雄
		if (heros.isEmpty()) {
			HashMap<Integer,MT_Data_Hero> heroMap = TableManager.GetInstance().TableHero().Datas();
			for (MT_Data_Hero hero : heroMap.values()) {
				HeroInfoPoJo heroInfoPoJo = new HeroInfoPoJo();
				heroInfoPoJo.setHero_id(hero.index()*(-1));//数据库的heroId,如果是默认的三个英雄，就设置为tableId的相反数
				heroInfoPoJo.setHero_table_id(hero.index());
				heroInfoPoJo.setFight_val(hero.FightVal());
				heroInfoPoJo.setName("default");//英雄所属玩家的名称
				heroInfoPoJo.setFeat(0);
				heros.add(heroInfoPoJo);
			}
		}
		
		Collections.sort(heros, new Comparator<HeroInfoPoJo>() {
			@Override
			public int compare(HeroInfoPoJo o1, HeroInfoPoJo o2) {
				if (o1.fight_val > o2.fight_val)
					return -1;
				else if (o1.fight_val < o2.fight_val)
					return 1;
				else
					return 0;
			}
		});
		
		sendHeroList(connection, heros);
	}
	
	//邀请英雄
	public void OnRequestHero(Connection connection, ProHeroPath.Msg_C2G_InviteHero msg) throws GameException {
		if (!connection.getPlayer().isFirstPoint()) 
			return;
		
		sendPlayerRequestHero(connection, msg.getId(), msg.getPlayerId());
		
		//设置英雄id到关卡信息中
		connection.getPlayer().setRequestHeroId(msg.getId(), msg.getPlayerId());
		
		//邀请完英雄直接进入战斗
		sendClickPointMsg(connection);
	}

	private void sendPlayerRequestHero(Connection connection,
			int heroid, long player_id) {
		DatabaseHero hero = null;
		List<DatabaseMedal> medals = new ArrayList<>();
		List<DatabaseItem> items = new ArrayList<>();
		if (heroid < 0) {
			//选择的是系统默认的三个英雄里面的一个, id是表里的id的负值
			hero = new DatabaseHero();
			hero.hero_id = heroid;
			hero.hero_table_id = -heroid;
			hero.skills = new ArrayList<Integer>();
			hero.star = 0;
			hero.hp_lvl = 0;
			hero.attack_lvl = 0;
			hero.defen_lvl = 0;
			hero.luck_val = 0;
		}else {
			//选择的是玩家的英雄
			hero = DbMgr.getInstance().getDbByPlayerId(player_id)
					.SelectOne(DatabaseHero.class, "hero_id = ? and player_id = ? ",heroid, player_id);
			medals = DbMgr.getInstance().getDbByPlayerId(player_id)
					.Select(DatabaseMedal.class,"hero_id = ?",heroid);
			items = DbMgr.getInstance().getDbByPlayerId(player_id)
					.Select(DatabaseItem.class, "is_on =? and player_id = ?",heroid, player_id);
		}
		
		//发送英雄数据
		sendRequestHeroInfo(connection, hero, medals, items);
	}

	private void sendRequestHeroInfo(Connection connection, DatabaseHero hero,List<DatabaseMedal> medals, List<DatabaseItem> items) {
		Msg_G2C_InviteHero.Builder mes = Msg_G2C_InviteHero.newBuilder();
		ProtoHeroInfo.Builder info = heroMessage(hero, medals, items);
		mes.setInfo(info);
		connection.sendPushMessage(mes.build());
	}
	
	public ProHero.ProtoHeroInfo.Builder heroMessage(DatabaseHero hero,List<DatabaseMedal> medals,List<DatabaseItem> items) {
		ProHero.ProtoHeroInfo.Builder info = ProHero.ProtoHeroInfo.newBuilder();
		info.setId(hero.hero_id);
		info.setTableId(hero.hero_table_id);
		info.setStar(hero.star);
		info.setHpLvl(hero.hp_lvl);
		info.setAttackLvl(hero.attack_lvl);
		info.setDefenLvl(hero.defen_lvl);
		info.setLuckVal(hero.luck_val);
		
		//装备列表
		for (int i = 0;i<items.size();i++){
			DatabaseItem item = items.get(i);
			ProHero.ProtoHeroInfo.ProtoEquipInfo.Builder equipInfo = ProHero.ProtoHeroInfo.ProtoEquipInfo.newBuilder();
			equipInfo.setItemId(item.item_id);
			equipInfo.setTableId(item.table_id);
			equipInfo.setPart(i);//客户端占时不用显示英雄的详细信息
			equipInfo.setStarLv(1);
			equipInfo.setLevel(item.level);
			info.addEquips(equipInfo);
		}
		
		//英雄勋章列表
		for (DatabaseMedal medalIf : medals){
			ProHero.ProtoHeroInfo.ProtoMedalInfo.Builder medalInfo = ProHero.ProtoHeroInfo.ProtoMedalInfo.newBuilder();
			medalInfo.setTableId(medalIf.medal_table_id);
			medalInfo.setLevel(medalIf.cur_level);
			medalInfo.setStarLv(medalIf.cur_star);
			medalInfo.setExp(medalIf.cur_exp);
			info.addMedals(medalInfo);
		}
		
		// 技能列表
		for (int skillid : hero.skills) {
			info.addSkills(skillid);
		}
		return info;
	}
	
	//请求英雄之路的数据
	public void OnAskHeroPathData(Connection connection, ProHeroPath.Msg_C2G_AskHeroPathData msg) throws GameException {
		//英雄之路还没有开启
	//	if (connection.getPlayer().getLevel() < Common.OPEN_HERO_PATH) 
	//		return;
		
		//1：英雄之路消息
		sendAskHeroPathMsgToClint(connection);
		
		//2：友放英雄信息
		int heroId = connection.getPlayer().getRequestHeroId();
		if (heroId == 0) 
			return;
		
		sendPlayerRequestHero(connection, heroId, connection.getPlayer().getRequestPlayerId());
	}
	
	//领取士兵
	public void OnReciveCrop(Connection connection, ProHeroPath.Msg_C2G_HeroPath_Recive_Crop msg) throws GameException {
		//如果当前关卡已经领取士兵
		if (connection.getPlayer().thisPointIsRecived()) 
			return;
		
		Integer cropTableId = msg.getCropId();
		if (cropTableId == null || cropTableId == 0) 
			return;
		
			//如果有这个兵种
		if (connection.getPlayer().haveThisCrop(cropTableId)) 
			connection.getPlayer().addCropNum(cropTableId);
		else 
			//无这个兵种
			connection.getPlayer().addCrop(cropTableId);
		
		//设置本关卡已经领取过士兵
		connection.getPlayer().addThisPointToRecived();
		
		//设置本方兵种等级
		Integer totalStar =connection.getPlayer().getStarFromAlreadyPoints();
		//判断士兵是否升级  2:通过星级判断拥有的士兵是否可以升级
		connection.getPlayer().heroPathCropLvUp(totalStar);
		
		//领取士兵返回
		Msg_G2C_HeroPath_Recive_Crop.Builder mesBuilder = Msg_G2C_HeroPath_Recive_Crop.newBuilder();
		mesBuilder.setCropId(cropTableId);
		mesBuilder.setLevel(connection.getPlayer().getSelfCropLv());
		mesBuilder.setNum(connection.getPlayer().getSelfCropNumByTableId(cropTableId));
		connection.sendPushMessage(mesBuilder.build());
	}
	
	//开启英雄之路宝箱
	public void OnOpenHeroPathBox(Connection connection, ProHeroPath.Msg_C2G_OpenBox msg) throws Exception {
		//宝箱ID
		ConPlayerAttr player = connection.getPlayer();
		
		Integer boxId = msg.getId();
		if (!player.isLastPoint() &&
				(boxId > player.getCurHeroPathPointId() - 1))
			return ;
		
		//宝箱已经开启过
		if (player.isRecivedThisBox(boxId)) 
			return ;
		
		MT_Data_HeroPath heroPath = TableManager.GetInstance().TableHeroPath().GetElement(boxId);
		if (heroPath == null)
			return ;
		
		//1：开宝箱给玩家加金币
		CustomHeroPathAlreadyPoint alreadyPoint = connection.getPlayer().getPreviousPointStar(boxId);
		long s_num = IPOManagerDb.getInstance().getNextId();
		int pid = (alreadyPoint.id +1)/2;
		int temp = connection.getPlayer().getLevel() - 10;
		if (temp < 0) temp = 1;
		int money = temp * 30 * pid * alreadyPoint.starCount;
		
		if (connection.getItem().getItemCountByTableId(ITEM_ID.MONEY) + money > connection
				.getBuild().GetMaxMoneyCount()) {
			connection.ShowPrompt(PromptType.MONEY_FULL);
			return;
		}
		connection.getItem().setItemNumber(ITEM_ID.MONEY, money, 
				SETNUMBER_TYPE.ADDITION,VmChannel.PurchaseGoods,ProductChannel.PurchaseGoods, 
				s_num, "", Item_Channel.ITEM_HEROPATH);
		connection.ShowPrompt(PromptType.RECIVE_ITEM, PROMPT_SCENE.CITY, TransFormArgs.CreateItemArgs(ITEM_ID.MONEY), money);
		
		//2：掉落租随机物品
		//根据dropId取得所以租类别
		List<Integer> types = getAllTypes(heroPath.dropOut());
		//根据租类别id随机物品
		for (Integer type : types) {
			Map<Integer,Integer> map = getOneTypeDropOut(heroPath.dropOut(), type);
			//保存物品
			for (Map.Entry<Integer,Integer> entry : map.entrySet())
				connection.getItem().setItemNumber(entry.getKey(), entry.getValue(),SETNUMBER_TYPE.ADDITION, VmChannel.PurchaseGoods,ProductChannel.PurchaseGoods, s_num, "", Item_Channel.ITEM_HEROPATH);
		}
		//修改已经领取宝箱数组
		player.updateReciveBosex(boxId);
		LogService.logEvent(connection.getPlayerId(), s_num, 34, boxId, player.getCurHeroPathPointId());
		connection.sendPushMessage(Msg_G2C_OpenBox.newBuilder().setId(msg.getId()).build());
	}

	//根据dropOId取得所以组类别
	private List<Integer> getAllTypes(Integer dropId) {
		List<Integer> types = new ArrayList<>();
		// 可能真的不存在掉落
		if (TABLE.IsInvalid(dropId))
			return types;
		
		if (!TableManager.GetInstance().TableDropOut().Contains(dropId)) {
			logger.error("getAllTypes, not exist dropid id={}", dropId);
			return types;
		}
		
		MT_Data_DropOut mt_Data_DropOut = TableManager.GetInstance().TableDropOut().GetElement(dropId);
		if (mt_Data_DropOut == null)
			return types;
		ArrayList<Int6> allDrops = mt_Data_DropOut.Arrays();
		for (Int6 dropInfo : allDrops){
			if (TABLE.IsInvalid(dropInfo)) 
				continue;
			if (types.contains(dropInfo.field4()))
				continue;
			types.add(dropInfo.field4());
		}
		return types;
	}
	
	public void OnAskHeroPath(Connection connection, ProHeroPath.Msg_C2G_AskHeroPath msg) throws GameException {
		//关卡ID
		Integer pointId = msg.getId();
		Integer curId = connection.getPlayer().getCurHeroPathPointId();
		if (curId != pointId) 
			return;
		
		//本关卡是否已经打过
		boolean isFuck = connection.getPlayer().isAladyFuck(pointId);
		if (isFuck) 
			return;
		
		//必须领取宝箱才能进入下一关
		int boxId = curId -1;
		if (curId !=1 && !connection.getPlayer().isRecivedThisBox(boxId)) 
			return;
		
		sendClickPointMsg(connection);
	}

	private void sendClickPointMsg(Connection connection) {
		Msg_G2C_AskHeroPath.Builder messgage = Msg_G2C_AskHeroPath.newBuilder();
		connection.sendPushMessage(messgage.build());
	}
	
	//战斗结束
	public void OnEndHeroPath(Connection connection, ProHeroPath.Msg_C2G_HeroPath_OverFight msg) throws GameException {
		//关卡ID
		Integer pointId = msg.getId();
		Integer curId = connection.getPlayer().getCurHeroPathPointId();
		if (curId != pointId) 
			return;
		
		int star = 0;
		if (msg.getWin()) 
			//战斗胜利
			star = win(msg,connection);
		else 
			//战斗失败
			fail(msg,connection);

		connection.sendPushMessage(Msg_G2C_HeroPath_OverFight.newBuilder().setStar(star).setWin(msg.getWin()).build());
		sendAskHeroPathMsgToClint(connection);
	}
	
	private void fail(Msg_C2G_HeroPath_OverFight msg, Connection connection) {
	}
	
	private int win(Msg_C2G_HeroPath_OverFight msg, Connection connection) {
		int curPointId = connection.getPlayer().getCurHeroPathPointId();//已经通过关卡的ID
		int playerLevel = connection.getPlayer().getLevel();
		
		MT_Data_HeroPath heroPath = TableManager.GetInstance().TableHeroPath()
				.GetElement(curPointId);
		if (heroPath == null)
			return 0;
		
		// 计算本方死亡人口
		int selfDiePop = calSelfDiePop(msg,connection);
		//计算星级
		int star = calcStar(selfDiePop,connection);
		//加入已打关列表
		connection.getPlayer().addAlreadyPoints(star);
		//判断是否还有下一个关，即 是不是最后一关
		if (connection.getPlayer().isHaveNextPoint(heroPath.nextId())) {
			//更新当前关卡信息
			connection.getPlayer().updateCurPoint(heroPath.nextId());
			//更新当前关卡敌方兵种信息(当前关卡已经更新为下一个关卡)
			//更新当前敌方相关信息
			connection.getPlayer().updateHeroPathEnemy();
		} else {
			connection.getPlayer().updateCurPoint(-1);
		}
		//更新奖励信息
//		long s_num = updateItem(star,msg.getId(),connection);
		//判断士兵是否升级  1:取得所有已经通过关卡的星级
		Integer totalStar =connection.getPlayer().getStarFromAlreadyPoints();
		//判断士兵是否升级  2:通过星级判断拥有的士兵是否可以升级
		connection.getPlayer().heroPathCropLvUp(totalStar);
		//记录通关日志
		long s_num = IPOManagerDb.getInstance().getNextId();
		int nextPointId = connection.getPlayer().getCurHeroPathPointId();//已经通过关卡的ID
		LogService.logEvent(connection.getPlayerId(), s_num, 31, curPointId, nextPointId);
		connection.getTasks().AddTaskNumber(TASK_TYPE.HERO_PATH, 0, 1, 0);
		connection.getTasks().AddTaskNumber(TASK_TYPE.HERO_PATH_PASS, curPointId, 1, 0);
		return star;
	}
	
	private  int  calcStar(int selfDiePop, Connection connection) {
		int oneStar = 1;
		//计算敌方人口
		int enemyPoP = calcEnemyPopulation(connection);
		int twoStar = (int) (enemyPoP * 0.5);
		int threeStar = (int) (enemyPoP * 0.2);
		if (selfDiePop <= twoStar)
			oneStar ++;
		if (selfDiePop <= threeStar)
			oneStar ++;
		return oneStar;
	}
	private int calSelfDiePop(Msg_C2G_HeroPath_OverFight msg,Connection connection) {
		int selfDiePopu = 0;
		for (int i=0; i<msg.getCorpsCount();++i){
			ProFight.Proto_LossCorps corp = msg.getCorps(i);
			if (corp.getType() == Proto_CorpsType.SOLIDER)
				selfDiePopu += connection.getCorps().GetAllPopulation(corp.getCorpsId(),corp.getNumber());
			else
				selfDiePopu += connection.getHero().getHeroPopulation(corp.getCorpsId());
		}
		return selfDiePopu;
	}
	
	public void sendAskHeroPathMsgToClint(Connection connection) {
		Msg_G2C_AskHeroPathData.Builder msg = Msg_G2C_AskHeroPathData.newBuilder();
		//1:设置敌方阵型
		Proto_Formation.Builder fBuilder = Proto_Formation.newBuilder();
		for (database.CustomFormation customFormation :connection.getPlayer().getEnemyFom()) {
			fBuilder.setId(customFormation.id);
			fBuilder.setPosX(customFormation.x);
			fBuilder.setPosY(customFormation.y);
			fBuilder.setType(Proto_CorpsType.SOLIDER);
			msg.addFoms(fBuilder);
		}
		//设置敌方士兵等级
		Proto_Formation_Lv.Builder lvbBuilder= Proto_Formation_Lv.newBuilder();
		for (CustomHeroPathCrop heroPathCrop : connection.getPlayer().getEnemyHeroPathCrops()) {
			lvbBuilder.setId(heroPathCrop.cropTableId);
			lvbBuilder.setLv(heroPathCrop.lv);
			msg.addLvs(lvbBuilder);
		}
		//设置已打关卡星数
		Proto_Star.Builder starBuilder =Proto_Star.newBuilder();
		for (CustomHeroPathAlreadyPoint customHeroPathAlreadyPoint: connection.getPlayer().getAlreadyPoints()) {
			starBuilder.setId(customHeroPathAlreadyPoint.id);
			starBuilder.setStar(customHeroPathAlreadyPoint.starCount);
			msg.addStars(starBuilder);
		}
		//设置本方士兵等级信息
		int lv = connection.getPlayer().getSelfCropLv();//当前玩家兵种的等级
		Proto_Self_CorpsInfo.Builder selfBuilder = Proto_Self_CorpsInfo.newBuilder();
		for (CustomHeroPathCrop crop : connection.getPlayer().getSelfHeroPathCrops()) {
			selfBuilder.setTableId(crop.cropTableId);
			selfBuilder.setLevel(lv);
			selfBuilder.setNum(crop.num);
			msg.addSelfCrops(selfBuilder);
		}
		//可以招募兵种信息.当前关卡可以领取士兵，但是没有领取
		if (!connection.getPlayer().thisPointIsRecived()) {
			List<Int2> crops = randomSelfCrops(connection.getPlayer().getCurHeroPathPointId());
			Proto_Recruit_Corps.Builder messs = Proto_Recruit_Corps.newBuilder();
			for (Int2 int2 : crops) {
				messs.setTableId(int2.field1());
				messs.setNum(int2.field2());
				messs.setLevel(lv);
				msg.addRecruitCorps(messs);
			}
		}
		
		//已经开启的宝箱数组
		List<Integer> bosIds = connection.getPlayer().getReciveBoxes();
		for (int i =0 ;i<bosIds.size();i++) 
			msg.addHaveBox(bosIds.get(i));
		
		//当前关卡
		msg.setId(connection.getPlayer().getCurHeroPathPointId());
		
		//2:升级兵种星级相关
		//当前还没有兵种
		int index = 1;int stsrLv = 1;
		if (connection.getPlayer().getSelfCropLv() != 0) 
			index = connection.getPlayer().getSelfCropLv();
		MT_Data_HeroPathConfig config = TableManager.GetInstance().TableHeroPathConfig().GetElement(index);
		if (config != null )
			stsrLv = config.starLv();
		
		msg.setNeedStar(stsrLv);
		
		//发消息
		connection.sendPushMessage(msg.build());
	}
	
	//计算敌方人口
	private Integer calcEnemyPopulation(Connection connection) {
		int enemyPopulation = 0;
		for(CustomFormation corp :connection.getPlayer().getEnemyFom()) {
			if (corp.type == 0)
				enemyPopulation += connection.getCorps().GetAllPopulation(corp.id,1);
			else
				enemyPopulation += 4;
		}
		return enemyPopulation;
	}
	
	//随机阵型
	public void randomEnemy(Integer playerlevel,Integer curId,List<CustomFormation> fmList,List<CustomHeroPathCrop> CropList) {
		if (fmList == null)
			fmList = new ArrayList<CustomFormation>();
		else
			fmList.clear();
		
		if (CropList == null)
			CropList = new ArrayList<CustomHeroPathCrop>();
		else
			CropList.clear();
		
		MT_Data_HeroPath heroPath = TableManager.GetInstance().TableHeroPath().GetElement(curId);
		Int2 radomType = heroPath.radomType();
		
		//如果为0,只放在3,4个位置
		if (radomType.field1() == 0) {
			int cropTableId = heroPath.cropType().get(RandomUtil.RangeRandom(0, heroPath.cropType().size() - 1));// 随机选出一个兵种
			//随机出一列
			int randomColNum = RandomUtil.RangeRandom(0, 6);
			int level = RandomUtil.RangeRandom(heroPath.cropLv().field1(), heroPath.cropLv().field2());// 兵种等级
			for(int i=0;i<4;i++)
			{
				CustomFormation customFormation = new CustomFormation();
				customFormation.id = cropTableId;
				customFormation.x = randomColNum;
				customFormation.y = i+1;
				customFormation.type = 0;
				fmList.add(customFormation);
				
			}
			
			CustomHeroPathCrop crop = new CustomHeroPathCrop();
			crop.cropTableId = cropTableId;
			crop.lv = level;
			CropList.add(crop);

		}else {
			//每次随机出来一个兵种，都放置一列
			//随机出一列
			// usedRow 记录了阵列中士兵占用的列数
			// 注意，生成兵的列数 != 阵列中士兵占用的列数，因为有的兵会占1列以上
			HashMap<Integer, Integer> map= new HashMap<>();//临时存放兵种的tableID
			int usedRow = 7;
			int corpRowNum = radomType.field2();//排数
			for (int j = corpRowNum; j > 0; j--) {
				if (usedRow <= 0)
					break;
				int cropTableId = heroPath.cropType().get(RandomUtil.RangeRandom(0, heroPath.cropType().size() - 1));// 随机选出一个兵种
				int level = RandomUtil.RangeRandom(heroPath.cropLv().field1(), heroPath.cropLv().field2());// 兵种等级
				MT_Data_Corps corpConfig = TableManager.GetInstance().TableCorps().GetElement(cropTableId+level);
				if (corpConfig == null)
					continue;
				if (usedRow - corpConfig.Size().field1() < 0)
					break;				
				usedRow -= corpConfig.Size().field1();
				
				// 生成防御阵型 竖排 0-5
				int usedColumn = 0;
				for (int k = 0; k < 6; k++) {
					if (usedColumn >= 6)
						break;
					if (usedColumn + corpConfig.Size().field2() > 6)
						break;
					CustomFormation customFormation = new CustomFormation();
					customFormation.type = 0;
					customFormation.id = cropTableId;// 兵种id
					customFormation.x = usedRow;
					customFormation.y = usedColumn;
					usedColumn += corpConfig.Size().field2();
					
					fmList.add(customFormation);
					
					if (map.containsKey(cropTableId))
						continue;
					
					CustomHeroPathCrop crop = new CustomHeroPathCrop();
					crop.cropTableId = cropTableId;
					crop.lv = level;
					CropList.add(crop);
					
					map.put(cropTableId, 0);
				}
			}
		}
		
		if (!heroPath.HeroType().isEmpty()) {
			int nHeroNum = 1;
			if (playerlevel >= Common.HERO_PATH_STRENG_ENEMY_OPEN_LEVEL.intValue())
				nHeroNum = 2;
			int random = RandomUtil.RangeRandom(1, 3);
			for(int i=0;i<nHeroNum;i++)
			{
				CustomFormation customFormation = new CustomFormation();
				customFormation.type = 1;
				int heroCorpId = heroPath.HeroType().get(RandomUtil.RangeRandom(0, heroPath.HeroType().size() - 1));
				customFormation.id = heroCorpId;
				if (heroCorpId >= 2200 && heroCorpId < 2300) {
					// 女爵
					customFormation.x = 0;
					if (random == 1) {
						if (i == 0)
							customFormation.y = 1;
						else
							customFormation.y = 4;
					} else if (random == 2) {
						if (i == 0)
							customFormation.y = 3;
						else
							customFormation.y = 1;
					} else {
						if (i == 0)
							customFormation.y = 4;
						else
							customFormation.y = 1;
					}
				} else {
					// 大壮、军官
					customFormation.x = 5;
					if (random == 1) {
						if (i == 0)
							customFormation.y = 1;
						else
							customFormation.y = 4;
					} else if (random == 2) {
						if (i == 0)
							customFormation.y = 3;
						else
							customFormation.y = 1;
					} else {
						if (i == 0)
							customFormation.y = 4;
						else
							customFormation.y = 1;
					}
				}

				fmList.add(customFormation);
				
				CustomHeroPathCrop crop = new CustomHeroPathCrop();
				crop.cropTableId = heroCorpId;
				crop.lv = 0;
				CropList.add(crop);
			}
		}
	}
	
	//根据关卡id随机出本方士兵，返回一个随机士兵列表，数量为3
	public List<Int2> randomSelfCrops(int cardId) {
		List<Int2> totalCrops = new ArrayList<>();
		MT_Data_HeroPath heroPath = TableManager.GetInstance().TableHeroPath().GetElement(cardId);
		if (heroPath == null)
			return totalCrops;
		ArrayList<Int2>  allCrops = heroPath.selfCrop();
		if (allCrops == null || allCrops.isEmpty()) 
			return totalCrops;

		while (true) {
			if (totalCrops.size() >=3)
				break;
			//随机出一个兵种
			Int2 cropInfo = allCrops.get(RandomUtil.RangeRandom(0, allCrops.size() - 1));
			if (!isExisted(totalCrops,cropInfo)) 
				totalCrops.add(cropInfo);
		}
		return totalCrops;
	}
	
	private boolean isExisted(List<Int2> totalCrops, Int2 cropInfo) {
		for (Int2 int2 :totalCrops) {
			if (int2.field1() == cropInfo.field1()) 
				return true;
		}
		return false;
	}
	//根据某一个组随机出物品id和数量
	private Map<Integer, Integer> getOneTypeDropOut(int dropOutTableId,int type) {
		HashMap<Integer,Integer> map = new HashMap<>();//被选中的Tableid,数量num
		
		if (!TableManager.GetInstance().TableDropOut().Contains(dropOutTableId)) {
			logger.error("getOneTypeDropOut, not exist dropid id={}", dropOutTableId);
			return map;
		}
		
		Integer begin = 0;
		Integer sum = 0;
		int random = RandomUtil.RangeRandom(1, 10000);//随机数
		MT_Data_DropOut mt_Data_DropOut = TableManager.GetInstance().TableDropOut().GetElement(dropOutTableId);
		ArrayList<Int6> allDrops = mt_Data_DropOut.Arrays();
		for (Int6 dropInfo : allDrops) {
			if (TABLE.IsInvalid(dropInfo)) 
				continue;
			if (type == dropInfo.field4()){
				Integer r = dropInfo.field3();//某一行的概率值
				sum = r+begin;
				if (random>=begin&&random<=sum) {
					map.put(dropInfo.field1(), dropInfo.field2());
					break;
				}
				begin = sum;
			}
		}
		return map;
	}
	
	private void sendHeroList(Connection connection, List<HeroInfoPoJo> heros) {
		Msg_G2C_AskHeroList.Builder mesg = Msg_G2C_AskHeroList.newBuilder();
		for (HeroInfoPoJo heroInfoPoJo :heros) {
			if (heroInfoPoJo == null ) 
				continue;
			Proto_HeroPathInfo.Builder iBuilder = Proto_HeroPathInfo.newBuilder();
			iBuilder.setHeroId(heroInfoPoJo.getHero_id());
			iBuilder.setHeroTableId(heroInfoPoJo.getHero_table_id());
			iBuilder.setPlayerName(heroInfoPoJo.getName());
			iBuilder.setFightVal(heroInfoPoJo.getFight_val());
			iBuilder.setPlayerId(heroInfoPoJo.getPlayer_id());
			iBuilder.setFeat(heroInfoPoJo.getFeat());
			mesg.addHeroInfos(iBuilder);
		}
		connection.sendPushMessage(mesg.build());
	}
}
