alter table Hero add `luck_val` int(4) DEFAULT '0';
alter table Hero add `init_luck_val` bit(1) DEFAULT b'0';

alter table item add `exp` int(4) DEFAULT '0';

alter table player add `fight_value` int(6) DEFAULT '0';
alter table player add `reward_last_play_time` timestamp NOT NULL DEFAULT '1980-11-11 00:00:00';
alter table player add `reward_cur_id` int(3) DEFAULT '0';
alter table player add `reward_enemy_crops` text ;
alter table player add `reward_enemy_formation` text ;

CREATE TABLE `glob_mail` (
  `mail_id` int(10) NOT NULL AUTO_INCREMENT,
  `mail_sender` varchar(20) DEFAULT NULL,
  `mail_title` varchar(30) DEFAULT NULL,
  `mail_comment` varchar(500) DEFAULT NULL,
  `item_info` varchar(200) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '1980-11-11 00:00:00',
  `expire_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`mail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

CREATE TABLE `recharge_cache` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `player_id` bigint(8) DEFAULT NULL COMMENT 'player_id',
  `amount` int(8) DEFAULT NULL COMMENT '人民币分',
  `item_id` varchar(40) DEFAULT NULL,
  `pay_order` varchar(60) DEFAULT NULL,
  `recharge_str` text,
  `mask` int(4) DEFAULT NULL COMMENT '0是没有领过，1是已经领取',
  PRIMARY KEY (`id`)
  KEY `player_id_idx` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='id';

alter table glob_mail add `filter_value` text DEFAULT '' COMMENT '筛选条件';
alter table glob_mail add `param2` bigint(20) DEFAULT NULL COMMENT '参数2';
alter table glob_mail add `param3` varchar(50) DEFAULT NULL COMMENT '参数3';
alter table glob_mail AUTO_INCREMENT=100000;
alter table glob_mail add `mail_type` int(3) DEFAULT '0' COMMENT '类型';
#首冲
alter table player add `pay_count` int(4) DEFAULT '0' COMMENT '支付次数';

#聊天举报字段增加
alter table player add `chatReport` varchar(1024) DEFAULT NULL COMMENT '聊天举报列表';

#facebook
alter table account add login_info varchar(200);
alter table account change uid uid varchar(100);

#举报日志表
CREATE TABLE `chatreport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一ID',
  `time` timestamp NOT NULL DEFAULT '1980-11-11 00:00:00' COMMENT '时间',
  `player_id` bigint(20) DEFAULT NULL COMMENT '玩家id',
  `param1` bigint(20) DEFAULT NULL COMMENT '被举报玩家ID',
  `player_name` char(32) DEFAULT NULL COMMENT '玩家姓名',
  `reported_player_id` bigint(20) DEFAULT NULL COMMENT '被举报玩家id',
  `reported_player_name` char(32) DEFAULT NULL COMMENT '被举报玩家姓名',
  `cur_reported_count` int(11) DEFAULT NULL COMMENT '当前有效累计举报',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='举报玩家';


#上下线异常处理增加字段
alter table account add `online` bit(1) DEFAULT b'0' COMMENT '是否在线';
alter table account add `try_count` int(4) DEFAULT '0' COMMENT '尝试登录次数';