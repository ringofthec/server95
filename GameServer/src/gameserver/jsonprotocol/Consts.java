// 本文件是由脚本生成的，请不要手动修改
//因为这样并没有什么卵用，等会儿已生成，就把你手动修改的地方全部覆盖掉了........


package gameserver.jsonprotocol;
public class Consts {
//解锁的最高子弹类型
public static Integer getMaxBullet() { return 61; }

//切牌
public static Integer getBJLS7() { return 17000; }

//当前经验id，或者叫当前获取id
public static Integer getCEXP_ID() { return 8; }

//百人牛牛坐庄最大金币要求
public static Integer getNiuniu_ZZ_Max_Coin() { return 30000000; }

//炮台2
public static Integer getBattery2() { return 42; }

//捕鱼xp技能
public static Integer getFishXPSkillPoint() { return 30; }

//炮台3
public static Integer getBattery3() { return 43; }

//登录时大转轮
public static Integer getItemEventRotaryReward() { return 1; }

//捕鱼激光累计
public static Integer getFishLaserCoinCost(int idx) { Integer[] temp = {0,10000,100000,1000000};return temp[idx]; }

//结算
public static Integer getBJLS6() { return 9000; }

//金猪取钱
public static Integer getItemEventPigGet() { return 17; }

//GM指令
public static Integer getItemEventGM() { return 10; }

//slot奖池最大值
public static Long getGoldSlotsPoolInitCoins(int idx) { Long[] temp = {0L, 1000000L, 7500000L, 25000000L};return temp[idx]; }

//下注时间
public static Integer getBJLS1() { return 15000; }

//今日充值数
public static Integer getDay_Recharge_Value() { return 110; }

//牛牛庄家输
public static Integer getItemEventNNzhuangshu() { return 63; }

//捕鱼技能
public static Integer getFISH_SKILL_NUCLEAR() { return 14; }

//闲家咪牌时间
public static Integer getBJLS2() { return 20000; }

//话费券
public static Integer getBill_ID() { return 10; }

//游戏消耗金币计数器
public static Integer getGame_Cost_Count() { return 101; }

//首冲标致
public static Integer getIs_First_Charge() { return 78; }

//牛牛下注时间
public static Integer getNiuniuBetTime() { return 15000; }

//牛牛休息时间
public static Integer getNiuniuFreeTime() { return 10000; }

//捕鱼锁定技能
public static Integer getItemEventLocking() { return 32; }

//结算
public static Integer getBJLS5_2() { return 2000; }

//人气榜领取奖励情况
public static Integer getIsLikeRankRewardGet() { return 25; }

//今日总赢取数
public static Integer getDay_Earn_Count() { return 111; }

//捕鱼获得killer
public static Integer getItemEventFishCatchGetKill() { return 31; }

//当前选中的炮台
public static Integer getCurBattery() { return 60; }

//当前选择的子弹类型
public static Integer getCurBullet() { return 62; }

//捕鱼技能
public static Integer getFISH_SKILL_FREEZ() { return 13; }

//金砖slot活动id
public static Integer getGold_Slot_ActiveId() { return 106; }

//庄家咪牌时间
public static Integer getBJLS2_2() { return 5000; }

//金砖slot游戏
public static Integer getGoldSlot() { return 2; }

//vip升级
public static Integer getItemEventVipLevelUp() { return 8; }

//炮台7
public static Integer getBattery7() { return 47; }

//金砖slot连输次数
public static Integer getGold_Slot_Fall_Count() { return 105; }

//牛牛闲家输
public static Integer getItemEventNNxianshu() { return 62; }

//赠送礼物
public static Integer getItemEventGiveGift() { return 3; }

//vip等级id
public static Integer getVIP_LVL_ID() { return 3; }

//水果slot游戏
public static Integer getFriutSlot() { return 1; }

//当前子弹等级
public static Integer getCurrentBullet() { return 62; }

//财富榜领取奖励情况
public static Integer getIsMoneyRankRewardGet() { return 23; }

//百人牛牛下注上限比例(身上携带钱数)
public static Double getNiuniu_Bet_Coin_Rate() { return 0.3; }

//非凡
public static Integer getGreate() { return 500; }

//捕鱼奖池最大值
public static Long getFishPoolInitCoins(int idx) { Long[] temp = {0L, 2000000L, 15000000L, 50000000L};return temp[idx]; }

//庄家补牌时间
public static Integer getBJLS4_2() { return 2000; }

//vip经验id
public static Integer getVIP_EXP_ID() { return 4; }

//当前炮塔类型
public static Integer getCurrentBattery() { return 60; }

//炮台5
public static Integer getBattery5() { return 45; }

//捕鱼门厅核弹击杀倍率
public static Integer getFishMenTingNulearRate(int idx) { Integer[] temp = {0, 100, 500, 1000};return temp[idx]; }

//等级榜领取奖励情况
public static Integer getIsLevelRankRewardGet() { return 24; }

//闲家补牌时间
public static Integer getBJLS4() { return 11000; }

//捕鱼召唤技能
public static Integer getItemEventSummon() { return 35; }

//水果slot连输次数
public static Integer getFriut_Slot_Fall_Count() { return 104; }

//slot奖池最大值
public static Long getSlotsPoolCoins(int idx) { Long[] temp = {0L, 5000000L, 50000000L, 200000000L};return temp[idx]; }

//救济金领取次数
public static Integer getRelief_ID() { return 20; }

//兑奖
public static Integer getItemEventCash() { return 22; }

//商场/礼品
public static Integer getItemEventGiftShop() { return 13; }

//捕鱼游戏ID
public static Integer getGAMEID_FISHER() { return 10; }

//商场/等级
public static Integer getItemEventLevelShop() { return 12; }

//捕鱼任务完成
public static Integer getItemEventFishTaskComplete() { return 26; }

//金猪转换
public static Integer getItemEventPigConvert() { return 19; }

//被点赞
public static Integer getItemEventLiked() { return 5; }

//炮台8
public static Integer getBattery8() { return 48; }

//百人牛牛
public static Integer getNiuNiu() { return 15; }

//激光累计值的 id
public static Integer getFISH_LASER_ID() { return 30; }

//捕鱼狂怒技能
public static Integer getItemEventRage() { return 37; }

//使用物品
public static Integer getItemEventUseItem() { return 6; }

//金猪储蓄量
public static Integer getPIG_COIN_ID() { return 11; }

//金猪金币奖励
public static Integer getItemEventPigReward() { return 23; }

//百人牛牛坐下最小金币要求
public static Integer getNiuniu_Sit_Min_Coin() { return 0; }

//炮台9
public static Integer getBattery9() { return 49; }

//五星好评记录
public static Integer getRate_Star_ID() { return 22; }

//捕鱼消耗
public static Integer getItemEventFishCatchCost() { return 27; }

//炮台1
public static Integer getBattery1() { return 41; }

//炮台6
public static Integer getBattery6() { return 46; }

//牛牛庄家赢取
public static Integer getItemEventNNzhuangyin() { return 61; }

//捕鱼极速技能
public static Integer getItemEventSpeed() { return 36; }

//等级id
public static Integer getLVL_ID() { return 6; }

//领取邮件
public static Integer getItemEventMail() { return 9; }

//金币id
public static Integer getCOIN_ID() { return 1; }

//礼包购买标志的起始位置
public static Integer getGiftPackegeStartId() { return 70; }

//礼品展示
public static Integer getItemEventGiftShow() { return 45; }

//百家乐
public static Integer getBaijiale() { return 20; }

//当天slot总次数
public static Integer getDay_Slot_Count() { return 100; }

//庄家补牌时间
public static Integer getBJLS5() { return 11000; }

//炮台10
public static Integer getBattery10() { return 50; }

//奖池浮动最低百分比
public static Double getPoolRate() { return 0.75; }

//捕鱼冰冻技能
public static Integer getItemEventFrozen() { return 33; }

//金猪存钱
public static Integer getItemEventPigSave() { return 18; }

//升级
public static Integer getItemEventLevelUp() { return 7; }

//牛牛闲家赢取
public static Integer getItemEventNNxianyin() { return 60; }

//游戏获得金币计数器
public static Integer getGame_Earn_Count() { return 102; }

//捕鱼技能
public static Integer getFISH_SKILL_RAGE() { return 17; }

//捕鱼技能
public static Integer getFISH_SKILL_SPEED() { return 16; }

//捕鱼技能
public static Integer getFISH_SKILL_ALADIN() { return 15; }

//捕鱼技能
public static Integer getFISH_SKILL_LOCK() { return 12; }

//比赛时间
public static String getGoldGameTime() { return "5分钟,"; }

//丢弃物品
public static Integer getItemEventDrop() { return 11; }

//救济金领取把数
public static Integer getRelief_Slot_Count() { return 103; }

//救济金领取最大次数
public static Integer getRelief_Max() { return 3; }

//水果slot押注
public static Integer getItemEventBeginFriutSlot() { return 50; }

//不可思议
public static Integer getUnbelievable() { return 1000; }

//优异
public static Integer getNice() { return 200; }

//牛牛开牌时间
public static Integer getNiuniuOpenTime() { return 15000; }

//闲家补牌时间
public static Integer getBJLS3_2() { return 2000; }

//百家乐闲家输
public static Integer getItemEventBJLxianshu() { return 72; }

//捕鱼奖池最大值
public static Long getFishPoolCoins(int idx) { Long[] temp = {0L, 5000000L, 5000000L, 200000000L};return temp[idx]; }

//水果slot赢取
public static Integer getItemEventFriutSlotEarn() { return 51; }

//百家乐庄家赢取
public static Integer getItemEventBJLzhuangyin() { return 71; }

//百家乐闲家赢取
public static Integer getItemEventBJLxianyin() { return 70; }

//水果slot奖池
public static Integer getItemEventFriutSlotPool() { return 52; }

//slot奖池最大值
public static Long getGoldSlotsPoolCoins(int idx) { Long[] temp = {0L, 2500000L, 25000000L, 100000000L};return temp[idx]; }

//捕鱼获得xp技能
public static Integer getItemEventFishCatchGetXP() { return 29; }

//救济金
public static Integer getItemEventRelief() { return 14; }

//当前vip经验id
public static Integer getCVIP_EXP_ID() { return 5; }

//更新昵称头像的奖励
public static Integer getUpdate_Head_ID() { return 21; }

//兑换码礼包
public static Integer getItemEventExchangeCode() { return 4; }

//百人牛牛坐庄最小金币要求
public static Integer getNiuniu_ZZ_Min_Coin() { return 10000000; }

//人气id
public static Integer getLIKEID() { return 9; }

//金猪赠送金砖
public static Integer getItemEventPigGive() { return 20; }

//百家乐庄家输
public static Integer getItemEventBJLzhuangshu() { return 73; }

//捕鱼中奖池
public static Integer getItemEventFishPoolBoss() { return 25; }

//捕鱼获得核弹技能
public static Integer getItemEventFishCatchGetNnclear() { return 30; }

//捕鱼门厅激光击杀倍率
public static Integer getFishMenTingLasrRate(int idx) { Integer[] temp = {0, 100, 500, 1000};return temp[idx]; }

//休息时间
public static Integer getBJLS0() { return 1000; }

//捕鱼获得
public static Integer getItemEventFishCatchGet() { return 28; }

//排行榜奖励
public static Integer getItemEventRankReward() { return 24; }

//金币对金砖的汇率
public static Integer getCoin2Gold() { return 1000; }

//炮台4
public static Integer getBattery4() { return 44; }

//抽奖
public static Integer getItemEventLotty() { return 21; }

//总经验id，或者叫总获取id
public static Integer getEXP_ID() { return 7; }

//红包
public static Integer getItemEventBouns() { return 16; }

//评分奖励
public static Integer getItemEventRate() { return 15; }

//捕鱼游戏
public static Integer getFisher() { return 10; }

//捕鱼核弹技能
public static Integer getItemEventNuclearBomb() { return 34; }

//slot奖池最大值
public static Long getSlotsPoolInitCoins(int idx) { Long[] temp = {0L, 2000000L, 15000000L, 50000000L};return temp[idx]; }

//金砖id
public static Integer getGOLD_ID() { return 2; }

//充值
public static Integer getItemEventRecharge() { return 46; }

//连续登录奖励
public static Integer getItemEventLoginReward() { return 2; }

//庄家咪牌时间
public static Integer getBJLS3() { return 18000; }

}

 // over