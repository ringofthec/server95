package gameserver.network.message.game;

import gameserver.config.PvpConfig;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.network.IOServer;
import gameserver.network.protos.common.ProPvpMatch;
import gameserver.network.protos.game.ProFight;
import gameserver.network.protos.game.ProPve;
import gameserver.network.protos.game.ProPvp;
import gameserver.network.server.connection.Connection;
import gameserver.pvp.PvpManager;
import gameserver.share.ShareServerManager;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commonality.ITEM_ID;
import commonality.PromptType;
import commonality.SHARE_SERVER_TYPE;

import databaseshare.DatabaseDefend_report;
import databaseshare.DatabasePvp_match;

public class ClientMessagePvp {
	private final Logger logger = LoggerFactory.getLogger(ClientMessagePvp.class);
	
	private final static ClientMessagePvp instance = new ClientMessagePvp();
	public static ClientMessagePvp getInstance() { return instance; }

	public void initialize()
	{
		IOServer.getInstance().regMsgProcess(ProPvp.Msg_C2G_PvpMatching.class,this, "OnPvpMatching");
		IOServer.getInstance().regMsgProcess(ProPvp.Msg_C2G_AskDefFormation.class,this, "OnDefFormation");
		IOServer.getInstance().regMsgProcess(ProPvp.Msg_C2G_StartAttackPvp.class,this, "OnStartAttack");
		IOServer.getInstance().regMsgProcess(ProFight.Msg_C2G_Pvp_OverFight.class, this, "OnPvpOverFight");
		
		ShareServerManager.getInstance().regMsgProcess(ProPvpMatch.Msg_S2G_PvpMatchRes.class,this, "OnPvpMatchRes");
	}
	
	public void OnPvpMatchRes(Connection connect,SHARE_SERVER_TYPE type,int id,ProPvpMatch.Msg_S2G_PvpMatchRes msg) throws Exception{
		
		PvpManager.getInstance().Match(connect, msg.getTargetPlayerId(), msg.getReportId(), msg.getNeedMoney());
		connect.setState(false, Connection.Msg_C2G_PvpMatching);
	}
	
	private static long targetRobotPlayerId = 0;
	//PVP开始匹配
	public void OnPvpMatching(Connection connection,ProPvp.Msg_C2G_PvpMatching message) {
		try {
			if (connection.isInState(Connection.Msg_C2G_PvpMatching))
				return ;

			ConPlayerAttr player = connection.getPlayer();
			if (player.getPvpNumber() <= 0){
				connection.ShowPrompt(PromptType.PVP_COUNT);
				connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(200).build());
				return;
			}

			int needMoney = 0;
			if (message.getMatchCount() > 0) {
				needMoney = player.getLevel() * PvpConfig.pvp_match_cost_level_param;
				if (PvpConfig.pvp_match_cost_count_param > 0)
					needMoney = needMoney * message.getMatchCount();

				if (!connection.getItem().checkItemEnough(ITEM_ID.MONEY, needMoney)) {
					connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(200).build());
					return ;
				}
			}

			long targetplayerid = 0;
			if (message.getReportId() > 0) {
				DatabaseDefend_report databaseDefend_reports = DbMgr.getInstance().getShareDB()
						.SelectOne(DatabaseDefend_report.class, "defend_report_id = ?", message.getReportId());
				if (databaseDefend_reports == null) {
					logger.info("player will find not exist targetplayer, reportid={}", message.getReportId());
					connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(200).build());
					throw new GameException(PromptType.FUCK_PLAYER_ERROR);
				}

				if (databaseDefend_reports.isfuckback == 0) {
					logger.info("player already fuck player!");
					connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(200).build());
					throw new GameException(PromptType.FUCK_PLAYER_ERROR);
				}

				targetplayerid = databaseDefend_reports.target_id;
			} else {
				if (player.isFirstPvp()) {
						PvpManager.getInstance().Match(connection, targetRobotPlayerId, 0, 0);
						return ;
				}
			}


			ProPvpMatch.Msg_G2S_PvpMatchReq.Builder req = ProPvpMatch.Msg_G2S_PvpMatchReq.newBuilder();
			req.setPlayerId(connection.getPlayerId());
			req.setMyRank(Util.getRank(player.getFeat()));
			req.setLegionId(player.getBelegionId());
			req.setTargetPlayerId(targetplayerid);
			req.setReportId(message.getReportId());
			req.setNeedMoney(needMoney);
			req.setMyLevel(player.getLevel());
			int forceRobot = 0;
			if (targetplayerid == 0)
				forceRobot = player.getPvpCount() % 4 == 0 ? 1 : 0;
			req.setForceRobot(forceRobot);
			final ProPvpMatch.Msg_G2S_PvpMatchReq tmsg = req.build();
			final Connection cn = connection;
			cn.setState(true, Connection.Msg_C2G_PvpMatching);

			if (forceRobot == 0)
			{
				ThreadPoolManager.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						try {
							ShareServerManager.getInstance().sendMsgPVP(cn, tmsg);
						}  catch (GameException e) {
							cn.ShowPrompt(e.getId());
						}
						catch (Exception e) {
							logger.error("OnPvpMatching error,", e);
						} finally {
							cn.setState(false, Connection.Msg_C2G_PvpMatching);
							cn.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(200).build());
						}
					}
				}, 0);
			}
			else
			{
				PvpManager.getInstance().Match(connection, targetRobotPlayerId, 0, 0);
				cn.setState(false, Connection.Msg_C2G_PvpMatching);
			}
		} catch (GameException e) {
			connection.ShowPrompt(e.getId());
			connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(200).build());
		}
		catch (Exception e) {
			logger.error("pvpmatch error:", e);
			connection.sendReceiptMessage(ProPvp.Msg_G2C_PvpOver.newBuilder().setErrorCode(200).build());
		}
	}
	//请求防御阵形
	public void OnDefFormation(Connection connection,ProPvp.Msg_C2G_AskDefFormation message){
		ConPlayerAttr player = connection.getPlayer();
		ProPvp.Msg_G2C_AskDefFormation.Builder msg = ProPvp.Msg_G2C_AskDefFormation.newBuilder();
		
		for (databaseshare.CustomFormation pair : player.getdefenseformation()) {
			ProPve.Proto_Formation.Builder info = ProPve.Proto_Formation.newBuilder();
			info.setId(pair.id);
			info.setPosX(pair.x);
			info.setPosY(pair.y);
			info.setType(ProPve.Proto_CorpsType.valueOf(pair.type));
			msg.addFormationInfo(info);
		}
		connection.sendReceiptMessage(msg.build());
	}
	//PVP开始战斗
	public void OnStartAttack(Connection connection,ProPvp.Msg_C2G_StartAttackPvp message) throws GameException {
		if (connection.isInState(Connection.Msg_C2G_PvpMatching))
			return ;
		
		PvpManager.getInstance().StartFighting(connection);
		connection.getPlayer().pvpFormation = message.getInfoList();
	}
	//PVP结束战斗
	public void OnPvpOverFight(Connection connection,ProFight.Msg_C2G_Pvp_OverFight message) throws GameException {
		PvpManager.getInstance().OverFighting(connection, message);
	}
}
