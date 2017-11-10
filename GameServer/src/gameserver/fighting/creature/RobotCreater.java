package gameserver.fighting.creature;

import gameserver.http.HttpProcessManager;
import gameserver.utils.DbMgr;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import table.MT_Data_Corps;
import table.MT_Data_PvpVirtual;
import table.base.TableManager;

import com.commons.util.HttpProcessResult;
import com.commons.util.HttpUtil;
import com.commons.util.JsonUtil;
import com.commons.util.RandomUtil;
import com.commons.util.TimeUtil;
import commonality.Common;
import commonality.ErrorCode;
import commonality.ITEM_ID;
import commonality.QueryPlayerDBRequest;
import commonality.QueryPlayerDBResult;

import database.DatabaseBuild;
import database.DatabaseCorps;
import database.DatabaseHero;
import database.DatabaseItem;
import database.DatabasePlayer;
import databaseshare.CustomFormation;
import databaseshare.DatabasePvp_match;

public class RobotCreater {
	
	private final static Logger logger = LoggerFactory.getLogger(RobotCreater.class);

	private static String[] littlelist = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	private static String[] uplist = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public static String newName() {
		StringBuffer name = new StringBuffer().append(uplist[RandomUtil.RangeRandom(0, uplist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)])
							.append(littlelist[RandomUtil.RangeRandom(0, littlelist.length - 1)]);
		return name.toString();
	}
	
	public static void createPvpVirtualPerson() {
		long time = TimeUtil.GetDateTime();
		
		int robotCount = DbMgr.getInstance().getShareDB().Count(DatabasePvp_match.class, " robot = ?", 1);
		if (robotCount != 0)
			return ;
		
		HashMap<Integer, MT_Data_PvpVirtual> pvpVirtual = TableManager.GetInstance().TablePvpVirtual().Datas();
		for (MT_Data_PvpVirtual mt_Data_PvpVirtual : pvpVirtual.values()) {
			for (int i = 0; i < 0; i++) {
				long t = time++;
				try {
					// 创建账户
					QueryPlayerDBRequest query = new QueryPlayerDBRequest();
					query.type = 1;
					query.channel = 0;
					query.uid = String.valueOf(t);
					query.player_id = 0L;
					query.session = "";
					query.last_login_ip = "";
					query.last_login_platform = "WINDOWS";
					query.last_login_time = TimeUtil.GetDateString();
					query.last_device_unique_identifier = String.valueOf(t);
	
					HttpProcessResult httpResult = HttpProcessManager.getInstance().httpPostAskLogin(query);
					if (httpResult.succeed == false)
						System.out.println("出现错误");
					String strResult = HttpUtil.urldecode(HttpUtil.toString(httpResult));
					QueryPlayerDBResult registerResult = JsonUtil.JsonToObject(strResult, QueryPlayerDBResult.class);
					if (registerResult== null || registerResult.code != ErrorCode.SUCCEED.ordinal()) {
						System.out.println("出现错误");
						continue;
					}
					
					
					if (registerResult.is_create == 1) {
						DatabasePlayer player = new DatabasePlayer();
						player.robot =1;//是机器人
						player.name = newName();
						player.player_id = registerResult.id;
						player.level = RandomUtil.RangeRandom(mt_Data_PvpVirtual.lv().field1(), mt_Data_PvpVirtual.lv().field2());
						player.feat = (int)(mt_Data_PvpVirtual.feat() * RandomUtil.RangeRandom(0.9f, 1.1f));
						player.vipLevel = 1;
						player.total_login_num = 0;
						player.queue_size = Common.QUEUE_SIZE;
						player.cp_time = TimeUtil.GetDateTime();
						player.head = RandomUtil.RangeRandom(0, 4);
	
						// 初始化城防中心
						DatabaseBuild build = new DatabaseBuild();
						build.table_id = 4;
						build.player_id = player.player_id;
						build.level = RandomUtil.RangeRandom(mt_Data_PvpVirtual.bulidLv().field1(), mt_Data_PvpVirtual.bulidLv().field2());
						DbMgr.getInstance().getDbByPlayerId(player.player_id).Insert(build);
	
						// 初始化主城
						DatabaseBuild b = new DatabaseBuild();
						b.table_id = 1;
						b.player_id = player.player_id;
						b.level = 1;
						DbMgr.getInstance().getDbByPlayerId(player.player_id).Insert(b);
	
						// 初始化金币
						DatabaseItem item = new DatabaseItem();
						item.player_id = player.player_id;
						item.table_id = ITEM_ID.MONEY;
						item.number = RandomUtil.RangeRandom(
								mt_Data_PvpVirtual.minNum(),
								mt_Data_PvpVirtual.maxNum());
						DbMgr.getInstance().getDbByPlayerId(player.player_id).Insert(item);
						
						logger.error("player_id={},orgfeat={},genfeat={},money={}", player.getPlayer_id(),
								mt_Data_PvpVirtual.feat(), player.feat, item.number);
	
						// 初始化玩家
						DbMgr.getInstance().getDbByPlayerId(player.player_id).Insert(player);
	
						// 阵型兵种初始化
						DatabasePvp_match pvp_match = new DatabasePvp_match();
						pvp_match.robot = true;
						pvp_match.feat = player.feat;
						pvp_match.defenseformation = new ArrayList<CustomFormation>();
						// 随机生成兵种排数
						int corpRowNum = RandomUtil.RangeRandom(mt_Data_PvpVirtual
								.cropNum().field1(), mt_Data_PvpVirtual.cropNum()
								.field2());
						ArrayList<Integer> cropsTypes = mt_Data_PvpVirtual.cropType();// 兵种配置10000,20000,50000
						
						// usedRow 记录了阵列中士兵占用的列数
						// 注意，生成兵的列数 != 阵列中士兵占用的列数，因为有的兵会占1列以上
						int usedRow = 7;
						for (int j = corpRowNum; j > 0; j--) {
							if (usedRow <= 0)
								break;
								
							int cropType = cropsTypes.get(RandomUtil.RangeRandom(0, cropsTypes.size() - 1));// 随机选出一个兵种
							MT_Data_Corps corpConfig = TableManager.GetInstance().TableCorps().GetElement(cropType);
							if (corpConfig == null)
								continue;
							
							if (usedRow - corpConfig.Size().field1() < 0)
								break;
							
							DatabaseCorps corp = new DatabaseCorps();
							corp.table_id = cropType;
							corp.level = RandomUtil.RangeRandom(mt_Data_PvpVirtual
									.cropLv().field1(), mt_Data_PvpVirtual.cropLv()
									.field2());// 兵种等级
							corp.number = 100;
							corp.player_id = player.player_id;
	
							DbMgr.getInstance().getDbByPlayerId(player.player_id)
									.Insert(corp);// 保存兵种
							
							usedRow -= corpConfig.Size().field1();
							
							// 生成防御阵型 竖排 0-5
							int usedColumn = 0;
							for (int k = 0; k < 6; k++) {
								if (usedColumn >= 6)
									break;
								
								if (usedColumn + corpConfig.Size().field2() > 6)
									break;
								
								CustomFormation formation = new CustomFormation();
								formation.type = 0;
								formation.id = cropType;// 兵种id
								formation.x = usedRow;
								formation.y = usedColumn;
								pvp_match.defenseformation.add(formation);// 兵种
								
								usedColumn += corpConfig.Size().field2();
							}
						}
	
						// 初始化英雄
						DatabaseHero hero = new DatabaseHero();
						hero.hero_table_id = RandomUtil.RangeRandom(1, 3);
						hero.player_id = player.player_id;
						DbMgr.getInstance().getDbByPlayerId(player.player_id).Insert(hero);
	
						// 初始化阵型
						pvp_match.player_id = player.player_id;
						pvp_match.name = player.name;
						pvp_match.level = player.level;
						CustomFormation customFormation = new CustomFormation();
						customFormation.type = 1;
						customFormation.id = hero.hero_table_id;
	
						int random = RandomUtil.RangeRandom(1, 3);
						if (hero.hero_table_id == 2) {
							// 女爵
							if (random == 1) {
								customFormation.x = 0;
								customFormation.y = 1;
							} else if (random == 2) {
								customFormation.x = 0;
								customFormation.y = 3;
							} else {
								customFormation.x = 0;
								customFormation.y = 4;
							}
						} else {
							// 大壮、军官
							if (random == 1) {
								customFormation.x = 5;
								customFormation.y = 1;
							} else if (random == 2) {
								customFormation.x = 5;
								customFormation.y = 3;
							} else {
								customFormation.x = 5;
								customFormation.y = 4;
							}
						}
						pvp_match.defenseformation.add(customFormation);// 英雄
	
						DbMgr.getInstance().getShareDB().Insert(pvp_match);
					}
				} catch (Exception e ) { 
					
				}
			}
		}
	}
}
