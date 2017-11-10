package gameserver.network.message.game;

import gameserver.ipo.IPOManager;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.game.ProFight;
import gameserver.network.protos.game.ProPve.Proto_CorpsType;
import gameserver.network.protos.game.ProPve.Proto_Formation;
import gameserver.network.protos.game.ProPve.Proto_Formation_Lv;
import gameserver.network.protos.game.ProWanted;
import gameserver.network.protos.game.ProWanted.Msg_G2C_BeginWanted;
import gameserver.network.protos.game.ProWanted.Msg_G2C_LoginWanted;
import gameserver.network.protos.game.ProWanted.Msg_G2C_OverFighting;
import gameserver.network.protos.game.ProWanted.Msg_G2C_OverFighting.Proto_FightReward;
import gameserver.network.server.connection.Connection;
import gameserver.utils.GameException;
import gameserver.utils.Item_Channel;
import gameserver.utils.UtilItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.Int2;
import table.MT_Data_Corps;
import table.MT_Data_WantedInstance;
import table.base.TableManager;

import com.commons.util.RandomUtil;

import commonality.Common;
import commonality.Common.SETNUMBER_TYPE;
import commonality.ProductChannel;
import commonality.PromptType;
import commonality.VmChannel;
import database.CustomFormation;
import database.CustomFormationCrops;

public class ClientMessageWanted {
	protected static final Logger logger = LoggerFactory.getLogger(ClientMessageWanted.class);
	private final static ClientMessageWanted instance = new ClientMessageWanted();
	public static ClientMessageWanted getInstance() { return instance; }
	
	public void initialize() {
		IOServer.getInstance().regMsgProcess(ProWanted.Msg_C2G_LoginWanted.class,this, "OnLoginWanted");//客户端请求进入战场
		IOServer.getInstance().regMsgProcess(ProWanted.Msg_C2G_BeginWanted.class,this, "OnBeginWanted");//开始战斗
		IOServer.getInstance().regMsgProcess(ProWanted.Msg_C2G_OverFighting.class,this, "OnOverFighting");//战斗结束
		IOServer.getInstance().regMsgProcess(ProWanted.Msg_C2G_OverTime.class,this, "OnOverTime");//快速结束冷却时间
	}
	
	public void OnLoginWanted(Connection connection, ProWanted.Msg_C2G_LoginWanted msg) throws GameException{
		if (!connection.getPlayer().isPlayRewardInstanceId(msg.getCurId()))
			return;
		
		Msg_G2C_LoginWanted.Builder builder = Msg_G2C_LoginWanted.newBuilder();
		builder.setCurId(msg.getCurId());
		//1:设置敌方阵型
		Proto_Formation.Builder fBuilder = Proto_Formation.newBuilder();
		for (database.CustomFormation customFormation :connection.getPlayer().getRewardEnemyFormation()) {
			fBuilder.setId(customFormation.id);
			fBuilder.setPosX(customFormation.x);
			fBuilder.setPosY(customFormation.y);
			fBuilder.setType(Proto_CorpsType.SOLIDER);
			builder.addFormationInfo(fBuilder);
		}
		//设置敌方士兵等级
		Proto_Formation_Lv.Builder lvbBuilder= Proto_Formation_Lv.newBuilder();
		for (CustomFormationCrops Crop : connection.getPlayer().getRewardEnemyCrops()) {
			lvbBuilder.setId(Crop.cropTableId);
			lvbBuilder.setLv(Crop.lv);
			builder.addFormationLv(lvbBuilder);
		}
		connection.sendPushMessage(builder.build());
	}
	
	public void OnBeginWanted(Connection connection, ProWanted.Msg_C2G_BeginWanted msg) throws GameException{
		connection.getPlayer().curRewardInstanceId = connection.getPlayer().getRewardCurId();
		connection.getPlayer().rewardInstanceBeginMsg = msg;
		Msg_G2C_BeginWanted.Builder builder = Msg_G2C_BeginWanted.newBuilder();
		builder.setError(0);
		connection.sendPushMessage(builder.build());
	}
	
	public void OnOverFighting(Connection connection, ProWanted.Msg_C2G_OverFighting msg) throws Exception{
		if (connection.getPlayer().curRewardInstanceId != msg.getId())
			return;
		
		if (connection.getPlayer().getLevel() >= Common.NO_DIE_CORPS) {
			for (int i=0;i<msg.getCorpsCount();++i){
				ProFight.Proto_LossCorps corps = msg.getCorps(i);
				if (corps.getType() == Proto_CorpsType.SOLIDER)
					connection.getCorps().setCorpsNumber(corps.getCorpsId(), corps.getNumber(), SETNUMBER_TYPE.MINUS);
			}
		}
		
		long costId = IPOManager.getInstance().getNextId();
		LogService.logEvent(connection.getPlayerId(), costId, 38, msg.getId(), msg.getWin() ? 1:0);
		
		Msg_G2C_OverFighting.Builder builder = Msg_G2C_OverFighting.newBuilder();
		builder.setWin(msg.getWin());
		if (msg.getWin())
		{
			List<UtilItem> list = connection.getPlayer().randomRewardItem();
			if (list != null)
			{
				connection.getItem().setItemNumberArrayByUtilItem(list, SETNUMBER_TYPE.ADDITION,
						VmChannel.InGameDrop, ProductChannel.InGameDrop, costId,"",Item_Channel.REWARD_INSTANCE_DROP);

				for (UtilItem item : list){
					Proto_FightReward.Builder itemInfo = Proto_FightReward.newBuilder();
					itemInfo.setTableId(item.GetItemId());
					itemInfo.setNum(item.GetCount());
					builder.addItems(itemInfo);
				}
			}
			connection.getPlayer().addRewardInstanceId();
			connection.getPlayer().sendWantedData(false);
		}
		connection.sendPushMessage(builder.build());
		
		connection.getPlayer().curRewardInstanceId = 0;
	}
	
	public void OnOverTime(Connection connection, ProWanted.Msg_C2G_OverTime msg) throws GameException{
		
		int cd = connection.getPlayer().getRewardPlayCD();
		if (cd > 0)
		{
			long speedUp = connection.getCommon().speedUp(cd, Item_Channel.CLEAR_REWARD_INSTANCE_CD);
			if (speedUp < 0)
				return;
			connection.getPlayer().clearRewardInstanceCD();
			connection.ShowPrompt(PromptType.CLEAR_REWARD_INSTANCE_CD);
		}
	}
	
	//随机阵型
	public void randomEnemy(Integer playerlevel,
													  Integer curId,
													  List<CustomFormation> fmList,
													  List<CustomFormationCrops> CropList) {
		if (fmList == null)
			fmList = new ArrayList<CustomFormation>();
		else
			fmList.clear();
			
		if (CropList == null)
			CropList = new ArrayList<CustomFormationCrops>();
		else
			CropList.clear();
		
		MT_Data_WantedInstance wantedInstance = TableManager.GetInstance().TableWantedInstance().GetElement(curId);
		Int2 radomType = wantedInstance.radomType();
		
		//如果为0,只放在3,4个位置
		if (radomType.field1() == 0) {
			int cropTableId = wantedInstance.cropType().get(RandomUtil.RangeRandom(0, wantedInstance.cropType().size() - 1));// 随机选出一个兵种
			int level = RandomUtil.RangeRandom(wantedInstance.cropLv().field1(), wantedInstance.cropLv().field2());// 兵种等级
			//随机出一列
			int randomColNum = RandomUtil.RangeRandom(0, 6);
			for(int i=0;i<4;i++)
			{
				CustomFormation customFormation = new CustomFormation();
				customFormation.id = cropTableId;
				customFormation.x = randomColNum;
				customFormation.y = i+1;
				customFormation.type = 0;
				fmList.add(customFormation);
			}
			
			CustomFormationCrops crop = new CustomFormationCrops();
			crop.cropTableId = cropTableId;
			crop.lv = level;
			CropList.add(crop);
		}else {
			//每次随机出来一个兵种，都放置一列
			//随机出一列
			// usedRow 记录了阵列中士兵占用的列数
			// 注意，生成兵的列数 != 阵列中士兵占用的列数，因为有的兵会占1列以上
			HashMap<Integer, Integer> map = new HashMap<>();//临时存放兵种的tableID
			int usedRow = 7;
			int corpRowNum = radomType.field2();//排数
			for (int j = corpRowNum; j > 0; j--) {
				if (usedRow <= 0)
					break;
				int cropTableId = wantedInstance.cropType().get(RandomUtil.RangeRandom(0, wantedInstance.cropType().size() - 1));// 随机选出一个兵种
				int level = RandomUtil.RangeRandom(wantedInstance.cropLv().field1(), wantedInstance.cropLv().field2());// 兵种等级
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
					
				}
				
				if (map.containsKey(cropTableId))
					continue;
				
				CustomFormationCrops crop = new CustomFormationCrops();
				crop.cropTableId = cropTableId;
				crop.lv = level;
				CropList.add(crop);
				map.put(cropTableId, 0);
				
			}
		}
		
		if (!wantedInstance.HeroType().isEmpty()) {
			int nHeroNum = 1;
			if (playerlevel >= Common.REWARD_INSTANCE_ENEMY_OPEN_LEVEL.intValue())
				nHeroNum = 2;
			int random = RandomUtil.RangeRandom(1, 3);
			for(int i=0;i<nHeroNum;i++)
			{
				CustomFormation customFormation = new CustomFormation();
				customFormation.type = 1;
				int heroCorpId = wantedInstance.HeroType().get(RandomUtil.RangeRandom(0, wantedInstance.HeroType().size() - 1));
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
				
				CustomFormationCrops crop = new CustomFormationCrops();
				crop.cropTableId = heroCorpId;
				crop.lv = 0;
				CropList.add(crop);
			}
		}

	}
}
