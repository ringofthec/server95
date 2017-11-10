package gameserver.utils;

public enum Item_Channel {

	SPEED_UPGRADER(1000, "建筑升级加速"),  
	SPEED_CORP(1001, "造兵加速"),	   
	SPEED_INS_UP(1002,"兵种升级加速"),   
	SPEED_TEC_UP(1003,"科技升级加速"),    
	MAIL(1004,"邮件"),			   // 邮件
	LOGIN_REWARD(1005,"登陆奖励"),    // 登陆奖励
	LVL_REWARD(1006,"等级奖励"),	   // 等级奖励
	CP_INC(1007,"体力恢复"),		   // 体力恢复
	RECHARGE(1008,"充值获得"),		   // 充值获得
	AUTO_INC(1009,"每天自动增加vip经验"),		   // 每天自动增加vip经验
	TASK_REC(1010,"任务奖励"),		   // 任务奖励
	GATHER(1011,"采集收获"),		   // 采集收获
	CACLE_MAKE_CORP(1012,"取消造兵"), // 取消造兵
	PLANE_UP(1013,"飞机升级"),        // 飞机升级
	BUY_PLANE(1014,"购买飞机"),	   // 购买飞机
	REPIAR_PLANE(1015,"修复飞机"),    // 修复飞机
	CREATE_ROLE(1016,"创建角色"),     // 创建角色
	EXTREN_LAND(1017,"扩地"),	   // 扩地
	CREATE_BUILD(1018,"造建筑"),		// 造建筑
	BUILD_UPGRADE(1019,"建筑升级"),   // 建筑升级
	BUY(1020,"商店购买"),				// 商店购买
	MAKE_CORP(1021,"招兵"),		// 招兵
	TECH_UP(1022,"科技升级"),			// 科技升级
	CORP_UP(1023,"兵种升级"),			// 兵种升级
	PVE_DROP(1024,"pve掉落"),			// pve 掉落
	FAST_FIGHT(1025,"扫荡"),		// 扫荡
	GM(1026,"gm"),				// gm
	SEL_HERO(1027,"选择英雄"),			// 选择英雄
	BUY_MEDAL(1028,"购买勋章"),		// 购买勋章
	SELL_ITEM(1029,"出售物品"),		// 出售物品
	USE_ITEM(1030,"使用物品"),			// 使用物品
	UP_MEDAL(1031,"勋章升级"),			// 勋章升级
	UP_MEDAL_STAR(1032,"勋章升星"),	// 勋章升星
	LUCK_DRAW_MEDAL_MONEY_1(1033,"金币抽取勋章1次"),	// 金币抽取勋章1次
	LUCK_DRAW_MEDAL_MONEY_10(1034,"金币抽取勋章10次"),	// 金币抽取勋章10次
	LUCK_DRAW_MEDAL_GOLD_1(1035,"金砖抽取勋章1次"),	// 金砖抽取勋章1次
	LUCK_DRAW_MEDAL_GOLD_10(1036,"金砖抽取勋章10次"),	// 金砖抽取勋章10次
	CREATE_LEGION(1037,"创建军团"),			// 创建军团
	LEGION_CONTRBUTE(1038,"军团贡献"),			// 军团贡献
	BUY_LEGION_SHOP(1039,"军团商店购买"),			// 军团商店购买
	FLUSH_LEGION_SHOP(1040,"刷新军团商店"),		// 刷新军团商店
	START_PVE(1041,"开始pve扣除"),				// 开始pve扣除
	PVE_STAR_REWARD(1042,"pve星级奖励"),			// pve星级奖励
	PVP_MATCH(1043,"pvp匹配"),				// pvp匹配
	ONLINE_REWARD(1044,"大转轮获得"),			// 大转轮获得
	OPEN_REWARD(1045,"开宝箱"),				// 开宝箱
	OPEN_VIP(1046,"开启vip"),					// 开启vip
	PVP_REWARD(1047,"pvp获得"),				// pvp获得
	ACTIVE_REWARD(1048,"活动奖励")	,		// 活动奖励
	COMBONE_DEBRIS(1049,"碎片合成"),		// 碎片合成
	ITEM_STERNGTHEN(1050,"装备强化"), // 装备强化
	ITEM_SPLITE(1051, "装备拆分"), // 装备拆分
	ITEM_HEROPATH(1052, "英雄之路"), // 英雄之路
	FUND_GOLD(1053, "基金返利"), //基金返利
	HERO_LVL_UP(1054, "英雄升级"), // 英雄升级
	HERO_STAR_UP(1055, "英雄升星"), // 英雄升星
	LVL_UP_CP(1056, "升级体力奖励"), // 升级体力奖励
	HELP_MONEY(1057, "军团帮助"), // 军团帮助
	VIP_REPAIRE(1058, "vip修复"), // vip修复
	REWARD_INSTANCE_DROP(1059, "悬赏掉落"), // 悬赏掉落
	CLEAR_REWARD_INSTANCE_CD(1060, "清除悬赏关卡cd"), // 清除悬赏关卡cd
	;
	
	
	private Item_Channel(int n, String name) {
		num = n;
		this.name = name;
	}
	
	int num;
	String name;
	
	public int getNum() {
		return num;
	}
	
	public String getName() {
		return name;
	}
	
	public Item_Channel[] getAll() {
		return values();
	}
}
