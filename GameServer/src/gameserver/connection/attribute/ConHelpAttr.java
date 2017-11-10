package gameserver.connection.attribute;

import gameserver.connection.attribute.info.BuildInfo;
import gameserver.network.protos.common.ProLegion.Msg_G2S_DelSeekHelp;
import gameserver.network.protos.common.ProLegion.Proto_SeekHelp_BuildType;
import gameserver.network.server.connection.Connection;
import gameserver.network.server.connection.ConnectionAttribute;
import gameserver.share.ShareServerManager;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.DatabaseBuild;
import databaseshare.DatabaseRequestHelp;

public class ConHelpAttr extends ConnectionAttribute {
	protected static final Logger logger = LoggerFactory.getLogger(ConHelpAttr.class);
			
	//申请id : 申请对应的实体
	private Map<Integer,DatabaseRequestHelp> requests = new HashMap<Integer,DatabaseRequestHelp>();
	
	@Override
	protected void Initialize_impl() {
		List<DatabaseRequestHelp> requestList = DbMgr.getInstance().getShareDB().Select(DatabaseRequestHelp.class, " player_id = ? ", m_Con.getPlayerId());
		for (DatabaseRequestHelp requestHelp : requestList) 
			requests.put(requestHelp.help_id, requestHelp);
	}
	
	//添加一个帮助
	public BuildInfo addHelp(int help_id, long doPlayerId) {
		try{
			if (!requests.containsKey(help_id))
				return null;
			
			DatabaseRequestHelp help = requests.get(help_id);
			help.help_list.add(doPlayerId);
			
			BuildInfo build = m_Con.getBuild().getBuild(help.build_id);
			build.save();
			m_Con.getBuild().CheckData();
			
			return build;
		}catch (Exception e){
			logger.error("addHelp error:", e);
		}
		return null;
	}
	
	public int getHelpCount(DatabaseBuild build,Proto_SeekHelp_BuildType state) {
		DatabaseRequestHelp req = getRequest(build, state);
		if (req != null)
			return req.help_list.size();
		return 0;
	}
	
	//添加一个申请
	public void addRequest(DatabaseRequestHelp requestHelp){
		requests.put(requestHelp.help_id, requestHelp);
	}
		
	public void deleteHelp(DatabaseBuild build,Proto_SeekHelp_BuildType state) throws Exception {
		DatabaseRequestHelp req = getRequest(build, state);
		if (req != null){
			//删除本地内存
			delHelp(req.help_id);
			//发送消息删除share内存的帮助信息
			Msg_G2S_DelSeekHelp.Builder delMsg = Msg_G2S_DelSeekHelp.newBuilder();
			delMsg.setReqId(req.help_id);
			
			final int legion_id = req.legion_id;
			final Msg_G2S_DelSeekHelp helpmsg = delMsg.build();
			final Connection cn = m_Con;
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					try {
						ShareServerManager.getInstance().sendMsgLegion(cn, legion_id, helpmsg);
					} catch (GameException e) {
					}catch (Exception e) {
						logger.error("deleteHelp error:", e);
					}
				}
			}, 0);
		}
	}
	
	public boolean hasReq(DatabaseBuild build,Proto_SeekHelp_BuildType state) {
		return getRequest(build, state) != null;
	}
	
	//取得一个申请
	private DatabaseRequestHelp getRequest(DatabaseBuild build,Proto_SeekHelp_BuildType state){
		for (DatabaseRequestHelp requestHelp : requests.values()) {
			if (requestHelp.build_id.intValue()==build.build_id&&
					requestHelp.level.intValue()==build.level.intValue()&&
					requestHelp.player_id.longValue()==m_Con.getPlayerId()&&
					requestHelp.state == state)
					return  requestHelp;
		}
		return null;
	}
	
	//去除一个帮助
	private void delHelp(Integer requestId){
		requests.remove(requestId);
		save(requestId);
	}

	private void save(Integer requestId) {
		DbMgr.getInstance().getShareDB().Delete(DatabaseRequestHelp.class, "help_id = ? ", requestId);
	}
}
