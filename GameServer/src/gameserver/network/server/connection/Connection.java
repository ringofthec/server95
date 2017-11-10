/**
 * This file is part of Aion Core <aioncore.com>
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */

package gameserver.network.server.connection;

import gameserver.cache.AccountCache;
import gameserver.config.ServerConfig;
import gameserver.connection.attribute.ConAccountAttr;
import gameserver.connection.attribute.ConActiveRewardAttr;
import gameserver.connection.attribute.ConAirSupportAttr;
import gameserver.connection.attribute.ConBuildAttr;
import gameserver.connection.attribute.ConCommonAttr;
import gameserver.connection.attribute.ConCorpsAttr;
import gameserver.connection.attribute.ConHelpAttr;
import gameserver.connection.attribute.ConHeroAttr;
import gameserver.connection.attribute.ConItemAttr;
import gameserver.connection.attribute.ConMailAttr;
import gameserver.connection.attribute.ConPassiveBuffAttr;
import gameserver.connection.attribute.ConPlayerAttr;
import gameserver.connection.attribute.ConTaskAttr;
import gameserver.connection.attribute.ConTechAttr;
import gameserver.fighting.AutoFighting;
import gameserver.http.HttpProcessManager;
import gameserver.ipo.IPOManagerDb;
import gameserver.logging.LogService;
import gameserver.network.IOServer;
import gameserver.network.protos.common.CommonMessage;
import gameserver.network.protos.common.ProPvpMatch;
import gameserver.network.protos.common.ProPvpMatch.ACCOUNT_OPER_TYPE;
import gameserver.network.protos.game.CommonProto.Proto_Off_Line;
import gameserver.network.protos.game.ProHint;
import gameserver.network.protos.game.ProHint.Msg_G2C_InvokeScript;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_SCENE;
import gameserver.network.protos.game.ProHint.Msg_G2C_Prompt.PROMPT_TYPE;
import gameserver.network.protos.game.ProPve;
import gameserver.network.protos.game.ProWanted.Msg_C2G_BeginWanted;
import gameserver.network.protos.management.GameServerMessage;
import gameserver.pushevent.PushEventService;
import gameserver.pvp.PvpManager;
import gameserver.share.ShareServerManager;
import gameserver.stat.StatManger;
import gameserver.taskmanager.FIFORunnableQueue;
import gameserver.thread.ThreadPoolManager;
import gameserver.utils.DbMgr;
import gameserver.utils.GameException;
import gameserver.utils.TransFormArgs;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.Deflater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import robot.RobotPlayer;
import table.MT_Data_Prompt;
import table.base.TableManager;

import com.commons.database.DatabaseTableDataBase;
import com.commons.network.Assertion;
import com.commons.util.BufferHelp;
import com.commons.util.DatabaseUtil;
import com.commons.util.NetUtil;
import com.commons.util.NewZipUtil;
import com.commons.util.TimeUtil;
import com.commons.util.string;
import com.google.protobuf.GeneratedMessage;

import commonality.Common;
import commonality.PromptType;
import database.DatabasePlayer_offline_val;
import databaseshare.DatabaseAccount;

/**
 * Object representing connection between LoginServer and GameServer.
 * 
 * @author -Nemesiss-
 */
public class Connection {
	/**
	 * Logger for this class.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(Connection.class);

	protected class Message {
		int type;
		Short msgId;
		byte[] bytes;
		List<GeneratedMessage> messages = new ArrayList<GeneratedMessage>();

		public Message(Short msgId, GeneratedMessage[] messages) {
			this(msgId, Arrays.asList(messages));
		}

		public Message(Short msgId, List<GeneratedMessage> messages) {
			type = 0;
			this.msgId = msgId;
			this.messages.clear();
			for (GeneratedMessage message : messages) {
				if (message != null) {
					this.messages.add(message);
				}
			}
		}

		public Message(Short msgId, byte[] bytes) {
			type = 1;
			this.msgId = msgId;
			this.bytes = bytes;
		}

		public final int getType() {
			return this.type;
		}

		public final Short getMsgId() {
			return this.msgId;
		}

		public final List<GeneratedMessage> getMessages() {
			return this.messages;
		}

		public final byte[] getBytes() {
			return this.bytes;
		}
	}

	// only reboto
	public String session;
	public RobotPlayer player;
	// end

	/**
	 * Server Packet "to send" Queue
	 */
	public static final int Msg_C2G_PvpMatching = 0x01;
	public static final int Msg_C2G_CreatePlayer = 0x02;
	public static final int Msg_C2G_join_Legion = 0x04;
	public static final int Msg_C2G_Signout_Legion = 0x08;
	public static final int Msg_C2G_Create_Legion = 0x10;
	public static final int Msg_C2G_BuildUpgradeOver = 0x20;
	public static final int Msg_C2G_Contribution_Item = 0x40;
	private int queueMask = 0;
	protected final Deque<Message> sendMsgQueue = new ArrayDeque<Message>();
	private long player_id;
	private String last_device_unique_identifier;
	/** 鏄惁宸茬粡鍒濆鍖杝ession淇℃伅 绗竴娆℃帴鏀跺埌session鐨勬秷鎭�鍒濆鍖栬鑹蹭俊鎭� */
	private boolean initializeSession = false;
	/** 鏄惁宸茬粡涓婄嚎 */
	private boolean initializeUpLine = false;
	private final int save_interval = 30 * 60 * 1000;
	private long nextSaveTime = System.currentTimeMillis() + save_interval;
	private String registration = null;
	private boolean isRecipePakage = false;
	private long lastkeeplive = 0;
	private boolean not_send_flag = false;
	private boolean need_recalc_fight_val = false;
	// 涓婃鍒嗕韩鎴樻姤鐨勬椂闂�
	private Long perTime = 0l;
	
	private boolean isDestroy = false;
	private boolean isWorking = false;
	private int tryDestroyCount = 0;
	
	public boolean isDestroy() {
		return isDestroy;
	}

	public void setDestroy(boolean isDestroy) {
		if (this.isDestroy != isDestroy)
			tryDestroyCount = 0;
		this.isDestroy = isDestroy;
	}

	public boolean isWorking() {
		tryDestroyCount++;
		if (tryDestroyCount > 10)
			return false;
		return isWorking;
	}

	public boolean setWorking(boolean isWorking) {
		synchronized (this) {
			if (isWorking && this.isDestroy)
				return false;
			if (this.isWorking != isWorking)
				tryDestroyCount = 0;
			this.isWorking = isWorking;
		}
		return true;
	}
	
	public boolean isNeed_recalc_fight_val() {
		return need_recalc_fight_val;
	}

	public void setNeed_recalc_fight_val(boolean need_recalc_fight_val) {
		this.need_recalc_fight_val = need_recalc_fight_val;
	}

	public boolean isNot_send_flag() {
		return not_send_flag;
	}

	public void setNot_send_flag(boolean not_send_flag) {
		this.not_send_flag = not_send_flag;
	}

	public boolean isInState(int mask) {
		return (queueMask & mask) != 0;
	}

	public void setState(boolean set, int mask) {
		if (set) {
			queueMask |= mask;
		} else {
			queueMask &= ~mask;
		}
	}

	public long getLastkeeplive() {
		return lastkeeplive;
	}

	public void setLastkeeplive(long lastkeeplive) {
		this.lastkeeplive = lastkeeplive;
	}

	public Long getPerTime() {
		return perTime;
	}

	public void setPerTime(Long perTime) {
		this.perTime = perTime;
	}

	public boolean isRecipePakageSend() {
		return isRecipePakage;
	}

	public void setReceiptPakage(boolean isRecipePakage) {
		this.isRecipePakage = isRecipePakage;
	}

	private List<DatabaseTableDataBase<?>> saveQueue = new CopyOnWriteArrayList<DatabaseTableDataBase<?>>();

	public void pushSave(DatabaseTableDataBase<?> data) {
		if (data == null)
			return;

		if (!saveQueue.contains(data))
			saveQueue.add(data);
	}

	private FIFORunnableQueue<Runnable> _packetQueue;

	public FIFORunnableQueue<Runnable> getPacketQueue() {
		if (_packetQueue == null) {
			_packetQueue = new FIFORunnableQueue<Runnable>() {
			};
			_packetQueue.setThreadPoolManager(ThreadPoolManager.getInstance());
		}

		return _packetQueue;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String res) {
		registration = res;
	}

	public void save(boolean force) {
		if (force || nextSaveTime < System.currentTimeMillis()) {
			for (DatabaseTableDataBase<?> data : saveQueue) {
				try {
					data.check();
					data.save();
				} catch (Exception e) {
					logger.error("save error: ", e);
				}
			}
			saveQueue.clear();
			nextSaveTime = System.currentTimeMillis() + save_interval;
		}
	}

	public void UpLine() {
		initializeUpLine = true;
	}

	public boolean GetInitializeUpLine() {
		return initializeUpLine;
	}

	public DatabaseUtil getDb() {
		return DbMgr.getInstance().getDbByPlayerId(player_id);
	}

	public void setPlayerId(long player_id) {
		this.player_id = player_id;
	}

	public String getLastDeviceUniqueIdentifier() {
		return last_device_unique_identifier;
	}

	public void setLast_device_unique_identifier(
			String last_device_unique_identifier) {
		this.last_device_unique_identifier = last_device_unique_identifier;
	}

	private ConAccountAttr accountAttribute = new ConAccountAttr();

	public ConAccountAttr getAccount() {
		return accountAttribute;
	}

	private ConPlayerAttr playerAttribute = new ConPlayerAttr();

	public ConPlayerAttr getPlayer() {
		return playerAttribute;
	}

	private ConItemAttr itemAttribute = new ConItemAttr();

	public ConItemAttr getItem() {
		return itemAttribute;
	}

	private ConBuildAttr buildAttribute = new ConBuildAttr();

	public ConBuildAttr getBuild() {
		return buildAttribute;
	}

	private ConHeroAttr heroAttribute = new ConHeroAttr();

	public ConHeroAttr getHero() {
		return heroAttribute;
	}

	private ConCorpsAttr corpsAttribute = new ConCorpsAttr();

	public ConCorpsAttr getCorps() {
		return corpsAttribute;
	}

	private ConTechAttr techAttribute = new ConTechAttr();

	public ConTechAttr getTech() {
		return techAttribute;
	}

	private ConAirSupportAttr airSupportAttribute = new ConAirSupportAttr();

	public ConAirSupportAttr getAir() {
		return airSupportAttribute;
	}

	private ConPassiveBuffAttr passiveBuffAttribute = new ConPassiveBuffAttr();

	public ConPassiveBuffAttr getBuffs() {
		return passiveBuffAttribute;
	}

	private ConCommonAttr commonAttribute = new ConCommonAttr();

	public ConCommonAttr getCommon() {
		return commonAttribute;
	}

	private ConTaskAttr taskAttribute = new ConTaskAttr();

	public ConTaskAttr getTasks() {
		return taskAttribute;
	}

	private ConMailAttr mailAttribute = new ConMailAttr();

	public ConMailAttr getMails() {
		return mailAttribute;
	}

	private ConHelpAttr helpAttribute = new ConHelpAttr();

	public ConHelpAttr getHelp() {
		return helpAttribute;
	}

	private ConActiveRewardAttr rewardsAttribute = new ConActiveRewardAttr();

	public ConActiveRewardAttr getReward() {
		return rewardsAttribute;
	}

	private boolean messageProcessing = false;

	public boolean getMessageProcessing() {
		return messageProcessing;
	}

	public void setMessageProcessing(boolean value) {
		messageProcessing = value;
	}

	public String getSession() {
		return accountAttribute.getSession();
	}

	public String getUid() {
		return accountAttribute.getUid();
	}

	public long getPlayerId() {
		if (accountAttribute == null)
			return 0;
		return accountAttribute.getPlayerId();
	}

	public String getPlayerName() {
		return playerAttribute.getName();
	}

	public boolean isInit() {
		return initializeSession;
	}

	/**
	 * 楠岃瘉鐜╁session 姣忔鏀跺埌鍗忚閮戒細璋冪敤
	 * 
	 * @param session
	 * @param checkUpline
	 *            濡傛灉鏄鏋滄槸绗竴娆＄櫥褰�灏辨槸false
	 * @return
	 */
	public boolean CheckSession(String session, boolean checkUpline) {
		try {
			if (initializeSession == true)
				return getSession().equals(session);

			DatabaseAccount account = AccountCache.getAccount(session);
			if (account == null)
				account = DbMgr
						.getInstance()
						.getShareDB()
						.SelectOne(DatabaseAccount.class, "session = ?",
								session);
			if (account == null || account.isEmpty())
				return false;
			AccountCache.setAccountCache(session, account);
			account.sync();
			account.setDatabaseSimple(DbMgr.getInstance().getShareDB()
					.getM_Simple());
			accountAttribute.Initialize(this, account);
			long beginTime = System.currentTimeMillis();
			ConnectionManager.GetInstance().AddConnection(getPlayerId(), this);
			player_id = account.player_id;
			long loadPlayerBeginTime = System.currentTimeMillis();
			playerAttribute.Initialize(this);
			long loadBuildBeginTime = System.currentTimeMillis();
			buildAttribute.Initialize(this);
			long loadTechBeginTime = System.currentTimeMillis();
			techAttribute.Initialize(this);
			long loadCorpBeginTime = System.currentTimeMillis();
			corpsAttribute.Initialize(this);
			long loadItemBeginTime = System.currentTimeMillis();
			itemAttribute.Initialize(this);
			long loadAirBeginTime = System.currentTimeMillis();
			airSupportAttribute.Initialize(this);
			long loadHeroBeginTime = System.currentTimeMillis();
			heroAttribute.Initialize(this);
			long loadPassBeginTime = System.currentTimeMillis();
			passiveBuffAttribute.Initialize(this);
			long loadendTime = System.currentTimeMillis();
			commonAttribute.Initialize(this);
			taskAttribute.Initialize(this);
			mailAttribute.Initialize(this);
			helpAttribute.Initialize(this);
			rewardsAttribute.init(this);
			initializeSession = true;
			playerAttribute.calToTalFightVal();
			logger.info(
					"playerDBLoad cost player_id={},RepeatLogin={},PlayerLoad={},"
							+ "BuildLoad={},TechLoad={},CorpLoad={},ItemLoad={},AirLoad={},HeroLoad={},PassLoad={},InitCost={}",
					player_id, loadPlayerBeginTime - beginTime,
					loadBuildBeginTime - loadPlayerBeginTime, loadTechBeginTime
							- loadBuildBeginTime, loadCorpBeginTime
							- loadTechBeginTime, loadItemBeginTime
							- loadCorpBeginTime, loadAirBeginTime
							- loadItemBeginTime, loadHeroBeginTime
							- loadAirBeginTime, loadPassBeginTime
							- loadHeroBeginTime, loadendTime
							- loadPassBeginTime, System.currentTimeMillis()
							- loadendTime);
			if (checkUpline) {
				try {
					IPOManagerDb.getInstance().UserLogin(this);
				} catch (Exception e) {
					logger.error("register log is error : ", e);
				}
				this.UpLine();
				this.CheckUpline();
			}
			String nation = getPlayer().getNation();
			String cretateTime = getPlayer().getCreateTime();
			LogService.logLogInOut(getPlayerId(), 1, 0, nation, cretateTime,0,getPlayer().getLevel());
			StatManger.getInstance().onLogin();
			return true;
		} catch (Exception e) {
			logger.error("checkSession is error : ", e);
		}
		return false;
	}

	/**
	 * 鐜╁鍒氫笂绾夸細璋冪敤姝ゅ嚱鏁�
	 */
	public void CheckUpline() {
		try {
			if (initializeSession == false)
				return;
			accountAttribute.CheckUpline();
			techAttribute.CheckUpline();
			corpsAttribute.CheckUpline();
			itemAttribute.CheckUpline();
			airSupportAttribute.CheckUpline();
			heroAttribute.CheckUpline();
			commonAttribute.CheckUpline();
			passiveBuffAttribute.CheckUpline();
			taskAttribute.CheckUpline();
			buildAttribute.CheckUpline();
			playerAttribute.CheckUpline();
			PushEventService.getInstance().clearAllPush(this);
		} catch (Exception e) {
			logger.error("CheckUpline is error : ", e);
		}
	}

	/**
	 * 楠岃瘉鐜╁褰撳墠鐘舵� 濡傛灉鏄鏀绘墦鐘舵� 涓嶈兘鐧诲綍 杩斿洖棰勮闇�绛夊緟鐨勬渶澶ф椂闂�杩斿洖<=0 琛ㄧず鍙互鐧诲綍
	 * 姣忔澶勭悊鍗忚涔嬪墠閮戒細璋冪敤
	 */
	public long CheckState(String session) {
		return playerAttribute.CheckState();
	}

	/** 姣忔敹鍒颁竴涓秷鎭兘浼氳皟鐢ㄦ鍑芥暟 妫�祴浣撳姏绛夋暟鎹� 澶勭悊鐜╁崗璁互鍚庤皟鐢� */
	public void CheckData() {
		if (initializeSession == false)
			return;
		playerAttribute.CheckData();
		techAttribute.CheckData();
		corpsAttribute.CheckData();
		itemAttribute.CheckData();
		airSupportAttribute.CheckData();
		heroAttribute.CheckData();
		passiveBuffAttribute.CheckData();
		commonAttribute.CheckData();
		taskAttribute.CheckData();
		buildAttribute.CheckData();
		mailAttribute.CheckData();
		rewardsAttribute.CheckData();
		playerAttribute.tryRecalcFightVal();
		save(false);
	}

	/**
	 * Constructor.
	 * 
	 * @param sc
	 * @param d
	 * @throws IOException
	 */
	
	ChannelHandlerContext sock_ctx;
	public Connection(ChannelHandlerContext ctx) {
		String netaddress = ctx.channel().remoteAddress().toString();
		int activeConnections = IOServer.getInstance().getActiveConnections();
		if (Math.abs(ConnectionManager.last_update_connection_num
				- activeConnections) > 10) {
			HttpProcessManager.getInstance().sendMessageSync(
					GameServerMessage.Msg_S_UpdatePlayerNumber.newBuilder()
							.setNumber(activeConnections).build());
			ConnectionManager.last_update_connection_num = activeConnections;
		}
		
		sock_ctx = ctx;
		
		writeBuffer = ByteBuffer.allocate(NetUtil.MAX_WRITE_LENGTH.intValue());
        writeBuffer.flip();
        writeBuffer.order(ByteOrder.LITTLE_ENDIAN);
        readBuffer = ByteBuffer.allocate(NetUtil.MAX_READ_LENGTH.intValue());
        readBuffer.order(ByteOrder.LITTLE_ENDIAN);
        
		logger.info("connection from: {} 当前服务器总人数是: {}", netaddress,
				activeConnections);
	}

	public final ByteBuffer writeBuffer;
    public final ByteBuffer readBuffer;
	public void readData(ByteBuf bbf) {
		int total_buffer_len = bbf.capacity();
		
		if (total_buffer_len == 0)
			return ;
		
		byte[] uuu = new byte[total_buffer_len];
		bbf.getBytes(0, uuu);
		
		assert readBuffer.hasRemaining();
		readBuffer.put(uuu);
		
		readBuffer.flip();
        while (readBuffer.remaining() > 2 && readBuffer.remaining() >= readBuffer.getShort(readBuffer.position())) {
        	parse();
        }
        
        if (readBuffer.hasRemaining()) {
        	readBuffer.compact();

            /**
             * Test if this build should use assertion. If NetworkAssertion == false javac will remove this code block
             */
            if (Assertion.NetworkAssertion) {
                assert readBuffer.hasRemaining();
            }
        } else {
        	readBuffer.clear();
        }
	}
	
	private boolean parse() {
        short sz = 0;
        try {
            sz = readBuffer.getShort();
            if (sz > 1)
                sz -= 2;
            ByteBuffer b = (ByteBuffer) readBuffer.slice().limit(sz);
            //b.order(ByteOrder.LITTLE_ENDIAN);
            /** read message fully */
            readBuffer.position(readBuffer.position() + sz);

            return processData(sz,b);
        }
        catch (IllegalArgumentException e) {
            logger.warn("Error on parsing input from client - account: " + this + " packet size: " + sz + " real size:"
                    + readBuffer.remaining(), e);
            return false;
        }
    }
	
	public boolean processData(final short length, ByteBuffer data) {
		final ByteBuffer bb = ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN).put(data);
		bb.position(0);
		final Connection con = this;
		if (con.setWorking(true)) {
			if (ServerConfig.thread_pool_mode) {
				getPacketQueue().execute(new Runnable() {
					@Override
					public void run() {
						IOServer.getInstance().Handle(con, length, bb);
						con.setWorking(false);
					}
				});
			} else {
				IOServer.getInstance().Handle(con, length, bb);
				con.setWorking(false);
			}
		}
		return true;
	}
	
	public void sendMsg(GeneratedMessage msg) {
		short msgid = 25;
		byte[] data = msg.toByteArray();
		System.err.println(data.length);
		
		ByteBuf fff = new PooledByteBufAllocator().buffer();
		fff.writeShort(25);
		fff.writeBytes(data, 0, data.length);
		BinaryWebSocketFrame bwsf = new BinaryWebSocketFrame(fff); 
		//TextWebSocketFrame bwsf = new TextWebSocketFrame(fff);
		sock_ctx.writeAndFlush(bwsf);
	}

	// 鍘嬬缉鐩稿叧
	protected Deflater decompresser = new Deflater();
	protected ByteBuffer buffer = ByteBuffer.allocate(8192 * 2).order(
			ByteOrder.LITTLE_ENDIAN);
	protected BufferHelp compressHelper = new BufferHelp(8192 * 3);

	/**
	 * This method will be called by Dispatcher, and will be repeated till
	 * return false.
	 * 
	 * @param data
	 * @return True if data was written to buffer, False indicating that there
	 *         are not any more data to write.
	 */
	protected boolean writeData(ByteBuffer data) {
		if (sendMsgQueue.isEmpty())
			return false;

		Message message = sendMsgQueue.removeFirst();
		if (message == null)
			return false;

		byte[] bytes = null;
		int bytelen = 0;
		if (message.getType() == 0) {
			String strMessage = "";
			for (GeneratedMessage msg : message.getMessages())
				strMessage += (msg.getClass().getSimpleName() + " ");
			bytes = NetUtil.GetByeBufferByMessageEx(buffer, message.getMsgId(),
					message.getMessages());
			if (!strMessage.equals(""))
				logger.info(
						"SendMessage [0x{}] length [{}] to [{}] messages [{}]",
						Integer.toHexString(message.msgId), buffer.limit(),
						"local", strMessage);
		} else {
			bytes = NetUtil.GetByeBufferByBytesEx(buffer, message.getMsgId(),
					"", message.getBytes());
		}

		if (bytes == null || bytes.length <= 0)
			return false;

		bytelen = buffer.limit();
		try {
			boolean isCompress = false;
			byte[] hehe = bytes;
			int len = bytelen;
			if (bytelen > ServerConfig.send_compress_len) {
				isCompress = NewZipUtil.compressBytes(decompresser, bytes,
						compressHelper, bytelen);
				if (isCompress) {
					hehe = compressHelper.data;
					len = compressHelper.size;
				}
			}

			data.putShort((short) (len + 3));
			data.put((byte) (isCompress ? 1 : 0));
			data.put(hehe, 0, len);
			data.flip();
			data.rewind();
			return true;
		} catch (Exception e) {
			logger.error("writeData is error : ", e);
		}
		return false;
	}
	
	public String getIP() {
		return "localhost";
	}

	/**
	 * This method is called by Dispatcher when connection is ready to be
	 * closed.
	 * 
	 * @return time in ms after witch onDisconnect() method will be called.
	 *         Always return 0.
	 */
	protected final long getDisconnectionDelay() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public final void onDisconnect() {
		try {
			long playerId = 0;
			if (getAccountTemp().playerId != 0)
				playerId = getAccountTemp().playerId;
			else if (getAccount().getDBAccount() != null)
				playerId = getPlayerId();
			if (playerId > 0)
				ConnectionManager.GetInstance().destroyConnect(playerId,null);
		} catch (Exception e) {
			logger.error("save player error, ", e);
		}
		int activeConnections = IOServer.getInstance().getActiveConnections();
		logger.info("onDisconnect {}  褰撳墠鍦ㄧ嚎浜烘暟 : {}", getIP(),
				activeConnections);
		HttpProcessManager.getInstance().sendMessageSync(
				GameServerMessage.Msg_S_UpdatePlayerNumber.newBuilder()
						.setNumber(activeConnections).build());
	}

	private void offlineSave() {
		if (initializeSession) {
			initializeSession = false;
			try {
				long start = TimeUtil.GetDateTime();
				// 淇濆瓨鏈涓嬬嚎鏃跺�鏌愪簺鐘舵�
				saveFeatVal();
				saveLegionLvVal();
				PushEventService.getInstance().pushAllEvent(this);

				String nation = getPlayer().getNation();
				String createTime = getPlayer().getCreateTime();
				LogService.logLogInOut(getPlayerId(), 0,
						playerAttribute.getOnlineTime(), nation, createTime, playerAttribute.getActiveTime(),
						playerAttribute.getLevel());

				// 鎺夌嚎鑷姩鎴樻枟
				if (getPlayer().isPve == true) {
					AutoFighting fighting = new AutoFighting(this);

					ProPve.Msg_C2G_StartAttackPve msg = getPlayer().pveMessage;
					try {
						fighting.AutoFighingPVE(msg.getID(), msg.getInfoList());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (getPlayer().curRewardInstanceId != 0) {
					AutoFighting fighting = new AutoFighting(this);
					Msg_C2G_BeginWanted msg = getPlayer().rewardInstanceBeginMsg;
					try {
						fighting.AutoFighingRewardInstance(msg.getInfoList());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (PvpManager.getInstance().IsFighting(getPlayerId())) {
					AutoFighting fighting = new AutoFighting(this);
					try {
						fighting.AutoFighingPVP(getPlayer().pvpMessage,
								getPlayer().pvpFormation);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				PvpManager.getInstance().Offline(getPlayerId());
				getPlayer().Offline();
				logger.debug("Offline:"+(TimeUtil.GetDateTime()-start)+"ms");
			} catch (Exception e) {
				logger.error("offlineSave error", e);
			}

			ConnectionManager.GetInstance().DelConnection(getPlayerId());
			save(true);
			sendAccountStateToShare(getPlayerId(), ACCOUNT_OPER_TYPE.OFFLINE);
		}
	}

	private void saveLegionLvVal() {
		DatabasePlayer_offline_val offline_val = new DatabasePlayer_offline_val();
		offline_val.player_id = getPlayerId();
		offline_val.type = Proto_Off_Line.LEGION_LV;
		offline_val.value = getPlayer().getLegionLv();
		if (offline_val.value == 0)
			return;
		getDb().Insert(offline_val);
	}

	private void saveFeatVal() {
		DatabasePlayer_offline_val offline_val = new DatabasePlayer_offline_val();
		offline_val.player_id = getPlayerId();
		offline_val.type = Proto_Off_Line.FEAT_VAL;
		offline_val.value = getPlayer().getFeat();
		getDb().Insert(offline_val);
	}

	/**
	 * {@inheritDoc}
	 */
	protected final void onServerClose() {
		// TODO mb some packet should be send to gameserver before closing?
		//close(/* packet, */true);
	}

	public final void sendReceiptMessage(GeneratedMessage... messages) {
		sendMessage(Common.BREAKING_MESSAGE_ID, messages);
		setReceiptPakage(true);
	}

	public final void sendPushMessage(GeneratedMessage... messages) {
		sendMessage(Common.NON_BREAKING_MESSAGE_ID, messages);
	}

	public final void sendPushMessage(byte[] bytes) {
		sendMessage(Common.NON_BREAKING_MESSAGE_ID, bytes);
	}

	/**
	 * Sends GsServerPacket to this client.
	 * 
	 * @param bp
	 *            GsServerPacket to be sent.
	 */
	private final void sendMessage(Short msgId, GeneratedMessage... messages) {
		if (not_send_flag)
			return;
		/**
		 * Connection is already closed or waiting for last (close packet) to be
		 * sent
		 */
	//	if (isWriteDisabled())
	//		return;
		sendMsgQueue.addLast(new Message(msgId, messages));
	//	enableWriteInterest();
	}

	private final void sendMessage(Short msgId, byte[] bytes) {
	//	if (isWriteDisabled())
	//		return;
		sendMsgQueue.addLast(new Message(msgId, bytes));
	//	enableWriteInterest();
	}

	/**
	 * Its guaranted that closePacket will be sent before closing connection,
	 * but all past and future packets wont. Connection will be closed [by
	 * Dispatcher Thread], and onDisconnect() method will be called to clear all
	 * other things. forced means that server shouldn't wait with removing this
	 * connection.
	 * 
	 * @param closePacket
	 *            Packet that will be send before closing.
	 * @param forced
	 *            have no effect in this implementation.
	 */
	public final void close(boolean forced, Short msgId,
			GeneratedMessage... messages) {
		offlineSave();

		/*
		synchronized (guard) {
			if (isWriteDisabled())
				return;
			pendingClose = true;
			isForcedClosing = forced;
			sendMsgQueue.clear();
			if (messages != null)
				sendMsgQueue.addLast(new Message(msgId, messages));
			enableWriteInterest();

		}
		*/
	}

	public final void ShowPrompt(PromptType promptId, Object... args) {
		ShowPrompt(promptId, PROMPT_SCENE.NONE, new String[0], args);
	}

	public final void ShowPrompt(PromptType promptId, PROMPT_SCENE type,
			Object... args) {
		ShowPrompt(promptId, type, null, args);
	}

	public final void ShowPrompt(PromptType promptId, PROMPT_SCENE promptType,
			String[] scripts, Object... args) {
		if (promptId == PromptType.NONE)
			return;

		try {
			MT_Data_Prompt dataPrompt = TableManager.GetInstance()
					.TablePrompt().GetElement(promptId.Number());
			if (dataPrompt != null) {
				logger.info("showPrompt:" + dataPrompt.text());
				try {
					logger.info("showPrompt(" + promptId + ")"
							+ string.Format(dataPrompt.text(), args));
				} catch (Exception e) {
					logger.info("showPrompterr:", e);
				}
			}

			Msg_G2C_Prompt msg = buildPromptMsg(promptId, promptType, scripts,
					args);
			sendPushMessage(msg);
		} catch (Exception e) {
			logger.info("showPrompterr:", e);
		}
	}

	public static Msg_G2C_Prompt buildPromptMsg(PromptType promptId,
			PROMPT_SCENE promptType, String[] scripts, Object... args) {
		Msg_G2C_Prompt.Builder builder = Msg_G2C_Prompt.newBuilder();
		builder.setPromptId(promptId.Number());
		builder.setPromptScene(promptType);
		if (scripts != null && scripts.length > 0) {
			int length = scripts.length;
			for (int i = 0; i < length; ++i)
				builder.addCallBack(scripts[i]);
		}
		int count = args.length;
		for (int i = 0; i < count; ++i) {
			Object value = args[i];
			if (value == null)
				continue;
			Class<?> type = value.getClass();
			if (type == Boolean.class || type == Boolean.TYPE) {
				builder.addArgsList(PROMPT_TYPE.BOOL);
				builder.addBoolArgs((Boolean) value);
			} else if (type == Byte.class || type == Byte.TYPE
					|| type == Short.class || type == Short.TYPE
					|| type == Integer.class || type == Integer.TYPE) {
				builder.addArgsList(PROMPT_TYPE.INT32);
				builder.addIntArgs((Integer) value);
			} else if (type == Long.class || type == Long.TYPE) {
				builder.addArgsList(PROMPT_TYPE.LONG);
				builder.addLongArgs((Long) value);
			} else if (type == String.class) {
				builder.addArgsList(PROMPT_TYPE.STRING);
				builder.addStringArgs((String) value);
			} else if (type == TransFormArgs.class) {
				builder.addArgsList(PROMPT_TYPE.TRANSFORM);
				TransFormArgs tfa = (TransFormArgs) value;

				ProHint.Msg_G2C_Prompt.TransInfo.Builder trans = ProHint.Msg_G2C_Prompt.TransInfo
						.newBuilder();
				trans.setTablekey(tfa.tablekey);
				trans.setTablename(tfa.tablename);
				trans.setId(tfa.id);
				trans.setColumnkey(tfa.columnkey);
				trans.setTransfromType(tfa.type);

				builder.addTransArgs(trans.build());
			}
		}
		return builder.build();
	}

	public final void InvokeScript(String data) {
		Msg_G2C_InvokeScript.Builder builder = Msg_G2C_InvokeScript
				.newBuilder();
		builder.setData(data);
		sendPushMessage(builder.build());
	}
	
	public void doKickOutPlayer(long playerId) {
		if (ConnectionManager.GetInstance().GetConnection(playerId) != null) {
			ConnectionManager.GetInstance().destroyConnect(playerId,
					CommonMessage.Msg_S_RepeatLogin.newBuilder().build());
		}
		else {
			HttpProcessManager.getInstance().sendMessageToPlayer(
					playerId,
					CommonMessage.Msg_S_RepeatLogin.newBuilder()
					.setPlayerId(playerId).build());
		}
	}
	
	@SuppressWarnings("unused")
	public class AccountTemp {
		private boolean passer = false; 
		private long playerId = 0;
		private String uid = null;
		private String token = null;
		private String session = null;
		private String loginInfo = null;
		
		public boolean isPasser() {
			return passer;
		}
		public void setPasser(boolean passer) {
			this.passer = passer;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public long getPlayerId() {
			return playerId;
		}
		public void setPlayerId(long playerId) {
			this.playerId = playerId;
		}
		public String getSession() {
			return session;
		}
		public void setSession(String session) {
			this.session = session;
		}
		public String getLoginInfo() {
			return loginInfo;
		}
		public void setLoginInfo(String loginInfo) {
			this.loginInfo = loginInfo;
		}
		
	}
	
	public AccountTemp m_accountTemp = new AccountTemp();

	public AccountTemp getAccountTemp() {
		return m_accountTemp;
	}

	public void setAccountTemp(boolean passer,
			long playerId,
			String uid,String token,
			String session,String loginInfo) {
		m_accountTemp.passer = passer;
		m_accountTemp.playerId = playerId;
		if (uid != null)
			m_accountTemp.uid = uid;
		if (token != null)
			m_accountTemp.token = token;
		if (session != null)
			m_accountTemp.session = session;
		if (loginInfo != null)
			m_accountTemp.loginInfo = loginInfo;
	}
	
	public void sendAccountStateToShare(long playerId,ACCOUNT_OPER_TYPE type) {
		ProPvpMatch.Msg_G2S_AccountOnline.Builder message = ProPvpMatch.Msg_G2S_AccountOnline.newBuilder();
		message.setOnlineType(type);
		message.setPlayerId(playerId);
		
		final Connection cn = this;
		final ProPvpMatch.Msg_G2S_AccountOnline sendmsg = message.build();
		
		if (ThreadPoolManager.getInstance().isShutdown()) {
			try {
				ShareServerManager.getInstance().sendMsgAccount(cn, sendmsg);
			} catch (GameException e) {
			}catch (Exception e) {
				logger.error("sendAccountStateToShare, error:", e);
			}
		}
		else {
			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					try {
						ShareServerManager.getInstance().sendMsgAccount(cn, sendmsg);
					} catch (GameException e) {
					}catch (Exception e) {
						logger.error("sendAccountStateToShare, error:", e);
					}
				}
			}, 0);
		}
		
	}
	
}
