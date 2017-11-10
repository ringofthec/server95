package gameserver.ranking;

import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.info.HeroInfo;
import gameserver.network.IOServer;
import gameserver.network.message.game.ClientMessageChat;
import gameserver.network.protos.common.ProRanking;
import gameserver.network.protos.common.ProRanking.Proto_RankingType;
import gameserver.network.server.connection.Connection;
import gameserver.share.ShareServerManager;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.commons.util.DatabaseUtil;

import commonality.SHARE_SERVER_TYPE;
import database.CustomHeroEquip;
import database.DatabaseHero;

public class ClientMessageRanking{   
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(ClientMessageRanking.class);
	private final static ClientMessageRanking instance = new ClientMessageRanking();
	public static ClientMessageRanking getInstance() {return instance;}   
    public void initialize() {
		IOServer.getInstance().regMsgProcess(ProRanking.Msg_C2G_Ranking.class,this, "OnComputer");
		IOServer.getInstance().regMsgProcess(ProRanking.Msg_C2G_AskLegionInfo.class,this, "OnLegion");
		IOServer.getInstance().regMsgProcess(ProRanking.Msg_C2G_AskHeroInfo.class,this, "OnHero");
	}
    public void init() {
    	ShareServerManager.getInstance().regMsgProcess(ProRanking.Msg_G2C_Ranking.class,this, "OnFsRanking");
    	ShareServerManager.getInstance().regMsgProcess(ProRanking.Msg_G2S_Rank_MAP.class,this, "OnInitRankMap");
    }

    private DatabaseHero queryMaxFightValHero(Map<Integer, HeroInfo> heroMap) {
		DatabaseHero max = null;
		int max_cont = 0;
		for (Map.Entry<Integer, HeroInfo> entry:heroMap.entrySet()) {
			int fightVal = entry.getValue().getHero().fight_val;
			if (fightVal > max_cont) {
				max_cont = fightVal;
				max = entry.getValue().getHero();
			}
		}
		return max;
	}
    
	//发送请求排行榜消息 G-S
	public void OnComputer(Connection connection, ProRanking.Msg_C2G_Ranking message) throws Exception {
		ConPlayerAttr player = connection.getPlayer();
		ProRanking.Msg_G2S_Ranking.Builder msg = ProRanking.Msg_G2S_Ranking.newBuilder();
		msg.setType(message.getType());//排行榜类型
		msg.setNation(message.getForm());//排行榜国别
		msg.setPlayerNation(connection.getPlayer().getNation());
		if (message.getType()==Proto_RankingType.LEGION_RANKING)
			msg.setId(player.getBelegionId());
		if (message.getType()==Proto_RankingType.PLAYER_RANKING || message.getType()==Proto_RankingType.HERO_RANKING || message.getType()==Proto_RankingType.TOTAL_FINGTH )
			msg.setId(player.getPlayerId());	
		msg.setPlayerId(player.getPlayerId());
		
		final Connection cn = connection;
		final ProRanking.Msg_G2S_Ranking rmsg = msg.build();
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			public void run() {
				try {
					ShareServerManager.getInstance().sendMsgRank(cn, rmsg);
				} catch (GameException e) {
				}catch (Exception e) {
					logger.error("OnComputer, err:", e);
				}
			}
		}, 0);
	}
    
  	//发送请求排行榜消息,share返回的消息返回给客户端
    public void OnFsRanking(Connection connection,SHARE_SERVER_TYPE type,int id,ProRanking.Msg_G2C_Ranking message) throws Exception {
    		connection.sendReceiptMessage(message);
    }
    
    public void OnInitRankMap(Connection connection,SHARE_SERVER_TYPE type,int id,ProRanking.Msg_G2S_Rank_MAP message) throws Exception {
    	ClientMessageChat.getInstance().OnInitRankMap(message);
    }
    
  //军团详细信息G-S
  public void OnLegion(Connection connection, ProRanking.Msg_C2G_AskLegionInfo message) throws Exception{
  }
  
  //英雄详细信息 G-S
  public void OnHero(Connection connection, ProRanking.Msg_C2G_AskHeroInfo message) throws Exception{
	  	DatabaseHero hero = null;
	  	//self
	  	HeroInfo heroInfo  = connection.getHero().getHeroById(message.getHeroId());
	  	//other
	  	if (heroInfo == null) {
	  		DatabaseUtil databaseUtil = DbMgr.getInstance().getDbByPlayerId(message.getPlayerId());
		  	hero = databaseUtil.SelectOne(DatabaseHero.class, "hero_id = ? ", message.getHeroId());
		}else {
			hero = heroInfo.getHero();
		}
	  	List<CustomHeroEquip> equips = hero.equips;
  		ProRanking.Msg_G2C_AskHeroInfo.Builder result = ProRanking.Msg_G2C_AskHeroInfo.newBuilder();
  		result.setTableId(hero.hero_table_id);
  		for (CustomHeroEquip equip:equips){
	  		ProRanking.Msg_G2C_AskHeroInfo.ProtoEquipInfo.Builder equipBuilder = ProRanking.Msg_G2C_AskHeroInfo.ProtoEquipInfo.newBuilder();
	  		equipBuilder.setItemId(equip.equipId);
	  		equipBuilder.setLevel(0);
	  		equipBuilder.setPart(equip.itemPart);
	  		equipBuilder.setStarLv(0);
	  		
	  		result.addEquips(equipBuilder.build());
		}
  		connection.sendReceiptMessage(result.build());
  } 
  
}  

