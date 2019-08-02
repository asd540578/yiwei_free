/*
Navicat MySQL Data Transfer
Source Host     : localhost:25382
Source Database : yiwei
Target Host     : localhost:25382
Target Database : yiwei
Date: 2019-08-02 14:57:24
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for accounts
-- ----------------------------
DROP TABLE IF EXISTS `accounts`;
CREATE TABLE `accounts` (
  `login` varchar(50) NOT NULL DEFAULT '',
  `password` varchar(50) DEFAULT NULL,
  `lastactive` datetime DEFAULT NULL,
  `access_level` int(11) DEFAULT NULL,
  `ip` varchar(20) NOT NULL DEFAULT '',
  `host` varchar(255) DEFAULT NULL,
  `banned` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否連線服務器',
  `character_slot` int(2) unsigned NOT NULL DEFAULT '0',
  `spw` varchar(12) NOT NULL,
  `warehouse` int(10) NOT NULL DEFAULT '-256',
  `server_no` int(10) NOT NULL DEFAULT '0' COMMENT '登入服務器編號',
  PRIMARY KEY (`login`),
  KEY `login` (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 3072 kB';

-- ----------------------------
-- Records of accounts
-- ----------------------------
INSERT INTO `accounts` VALUES ('2222', '2222', '2019-08-01 20:28:57', '200', '127.0.0.1', '127.0.0.1', '0', '0', '未建立超級密碼', '-256', '0');
INSERT INTO `accounts` VALUES ('1111', '1111', '2019-08-01 20:26:38', '200', '127.0.0.1', '127.0.0.1', '0', '0', '未建立超級密碼', '-256', '0');

-- ----------------------------
-- Table structure for ban_ip
-- ----------------------------
DROP TABLE IF EXISTS `ban_ip`;
CREATE TABLE `ban_ip` (
  `ip` varchar(255) NOT NULL DEFAULT '',
  `info` varchar(255) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`ip`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 3072 kB';

-- ----------------------------
-- Records of ban_ip
-- ----------------------------

-- ----------------------------
-- Table structure for commands
-- ----------------------------
DROP TABLE IF EXISTS `commands`;
CREATE TABLE `commands` (
  `name` varchar(255) NOT NULL,
  `access_level` int(10) NOT NULL DEFAULT '200',
  `class_name` varchar(255) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `system` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of commands
-- ----------------------------
INSERT INTO `commands` VALUES ('echo', '200', 'L1Echo', '重新啟用監聽', '1');
INSERT INTO `commands` VALUES ('setting', '200', 'L1Status', '重置指定人物屬性(參數:對象 - 屬性(參考說明) - 變更質)', '1');
INSERT INTO `commands` VALUES ('summon', '100', 'L1Summon', '召喚寵物(參數:npcid))', '0');
INSERT INTO `commands` VALUES ('cleaning', '100', 'L1DeleteGroundItem', '刪除地面物品', '0');
INSERT INTO `commands` VALUES ('addskill', '100', 'L1AddSkill', '賦予該GM職業所有技能', '0');
INSERT INTO `commands` VALUES ('level', '100', 'L1Level', '變更該GM人物等級', '0');
INSERT INTO `commands` VALUES ('loc', '100', 'L1Loc', '取回目前座標資料', '0');
INSERT INTO `commands` VALUES ('desc', '100', 'L1Describe', '顯示人物附加屬性(參數:人物名稱)', '1');
INSERT INTO `commands` VALUES ('who', '100', 'L1Who', '顯示線上實際人數', '1');
INSERT INTO `commands` VALUES ('allbuff', '200', 'L1AllBuff', '全技能狀態(參數:對象(gm,all))', '0');
INSERT INTO `commands` VALUES ('speed', '200', 'L1Speed', '賦予GM加速狀態', '0');
INSERT INTO `commands` VALUES ('adena', '200', 'L1Adena', '創造金幣(參數:數量)', '0');
INSERT INTO `commands` VALUES ('hpbar', '200', 'L1HpBar', '顯示物件血條(參數:ON顯示/關閉OFF)', '0');
INSERT INTO `commands` VALUES ('resettrap', '200', 'L1ResetTrap', '重新配置陷阱', '0');
INSERT INTO `commands` VALUES ('reloadtrap', '200', 'L1ReloadTrap', '重新載入陷阱', '0');
INSERT INTO `commands` VALUES ('showtrap', '200', 'L1ShowTrap', '顯示陷阱位置(參數:on顯示/off關閉顯示)', '0');
INSERT INTO `commands` VALUES ('gfxid', '200', 'L1GfxId', '顯示指定圖形(參數:圖形編號 - 延伸數量)', '0');
INSERT INTO `commands` VALUES ('hometown', '200', 'L1HomeTown', '啟用貢獻度系統(參數:daily(日處理)/monthly(月處理))', '1');
INSERT INTO `commands` VALUES ('gm', '200', 'L1GM', '隱藏顯示GM屬性', '0');
INSERT INTO `commands` VALUES ('shutdown', '200', 'L1Shutdown', '關機指令(參數:now()/abort()/倒數秒數)', '1');
INSERT INTO `commands` VALUES ('item', '200', 'L1CreateItem', '創造物品(參數:物品編號 - 數量 - 追加質)', '0');
INSERT INTO `commands` VALUES ('itemset', '200', 'L1CreateItemSet', '取得預設套件(參數:套件編號)', '0');
INSERT INTO `commands` VALUES ('buff', '200', 'L1Buff', '附加給予指定狀態(參數:對象(me,all) - 技能編號)', '0');
INSERT INTO `commands` VALUES ('chat', '200', 'L1Chat', '廣播限制(參數:on(取消廣播限制)/off(設置廣播限制))', '1');
INSERT INTO `commands` VALUES ('chatng', '200', 'L1ChatNG', '禁言(參數:人物名稱 - 秒數)', '1');
INSERT INTO `commands` VALUES ('skick', '200', 'L1SKick', '解除卡點(參數:人物名稱)', '1');
INSERT INTO `commands` VALUES ('kick', '200', 'L1Kick', '踢除下線(參數:人物名稱/選單)', '1');
INSERT INTO `commands` VALUES ('powerkick', '200', 'L1PowerKick', '封鎖IP/MAC(參數:人物名稱/選單)', '1');
INSERT INTO `commands` VALUES ('accbankick', '200', 'L1AccountBanKick', '帳號封鎖(參數:帳號)', '1');
INSERT INTO `commands` VALUES ('poly', '200', 'L1Poly', '變身指令(參數:人物名稱 - 變身代號)', '0');
INSERT INTO `commands` VALUES ('ress', '200', 'L1Ress', 'GM治療結界', '0');
INSERT INTO `commands` VALUES ('death', '200', 'L1Kill', '殺死指定人物(參數:人物名稱/選單)', '1');
INSERT INTO `commands` VALUES ('gmroom', '200', 'L1GMRoom', 'GM指定移動座標(參數:座標編號)', '0');
INSERT INTO `commands` VALUES ('topc', '200', 'L1ToPC', '移動座標至指定人物身邊(參數:人物名稱/選單)', '0');
INSERT INTO `commands` VALUES ('move', '200', 'L1Move', '移動至指定座標邊(參數:LOCX - LOCY - MAPID)', '0');
INSERT INTO `commands` VALUES ('weather', '200', 'L1ChangeWeather', '遊戲天氣控制(參數:控制代號)', '1');
INSERT INTO `commands` VALUES ('tospawn', '200', 'L1ToSpawn', '移動往指定召喚編號的NPC所在位置(參數:召喚表編號)', '0');
INSERT INTO `commands` VALUES ('f', '200', 'L1Favorite', '快速指令紀錄(參數:set(設置)/show(目前設置))', '0');
INSERT INTO `commands` VALUES ('recall', '200', 'L1Recall', '召喚玩家(參數:ALL(全部玩家)/人物名稱/選單)', '0');
INSERT INTO `commands` VALUES ('partyrecall', '200', 'L1PartyRecall', '召喚隊伍(參數:人物名稱/選單)', '0');
INSERT INTO `commands` VALUES ('invisible', '200', 'L1Invisible', '啟用/取消GM隱身', '0');
INSERT INTO `commands` VALUES ('spawn', '200', 'L1SpawnCmd', '召喚NPC(參數:NPCID - 數量 - 範圍)', '0');
INSERT INTO `commands` VALUES ('help', '100', 'L1CommandHelp', '顯示管理指令群', '1');
INSERT INTO `commands` VALUES ('color', '200', 'L1Color', 'GM名稱變色', '0');
INSERT INTO `commands` VALUES ('buffkick', '200', 'L1BuffKick', '刪除已存人物保留技能(參數:人物objid/選單)', '0');
INSERT INTO `commands` VALUES ('spr', '200', 'L1Spr', '取回指定圖形速度設置(參數:圖形編號)', '1');
INSERT INTO `commands` VALUES ('gfxidpc', '200', 'L1GfxIdInPc', '產生動畫物件(參數:動畫編號)', '0');
INSERT INTO `commands` VALUES ('acc', '200', 'L1Acc', '取回指定帳號資料(參數:帳號)', '1');
INSERT INTO `commands` VALUES ('db', '200', 'L1Db', '變更經驗直倍率(參數:倍率)', '1');
INSERT INTO `commands` VALUES ('IL', '200', 'L1ILL', '取回GM分身', '0');
INSERT INTO `commands` VALUES ('killnpc', '200', 'L1KillNpc', '殺死畫面中 NPC', '0');
INSERT INTO `commands` VALUES ('msg', '200', 'L1AtkMsg', '開啟/關閉 GM攻擊訊息', '0');
INSERT INTO `commands` VALUES ('port', '200', 'L1Port', '關閉/開啟指定監聽端口(參數:stop/start 端口編號)', '1');
INSERT INTO `commands` VALUES ('movex', '200', 'L1MoveX', '測試未知地圖', '0');
INSERT INTO `commands` VALUES ('tomap', '200', 'L1ToMap', '指定地圖傳送(參數:地圖編號)', '0');
INSERT INTO `commands` VALUES ('polyme', '200', 'L1PolyMe', 'GM變身指令(參數:變身代號)', '0');
INSERT INTO `commands` VALUES ('html', '200', 'L1Html', '顯示指定HTML', '0');
INSERT INTO `commands` VALUES ('pcm', '200', 'L1pcm', 'PC清單', '0');
INSERT INTO `commands` VALUES ('ipm', '200', 'L1ipm', '封鎖清單', '0');
INSERT INTO `commands` VALUES ('delgfxid', '200', 'L1GfxIdDel', '刪除產生動畫物件', '0');
INSERT INTO `commands` VALUES ('bn', '200', 'L1BanUser', '外掛處分(參數:人物名稱 - 分鐘)', '0');
INSERT INTO `commands` VALUES ('gc', '200', 'L1GC', '核心資源回收', '1');
INSERT INTO `commands` VALUES ('npc', '200', 'L1NpcSet', '設置定點NPC(參數:NPC編號)', '0');
INSERT INTO `commands` VALUES ('id', '200', 'L1ShowNpcid', '顯示物件資訊', '0');
INSERT INTO `commands` VALUES ('cn', '200', 'L1CN', '指定帳號加入指定物品(參數:帳號 - 物品編號 - 數量)', '1');
INSERT INTO `commands` VALUES ('ipmx', '200', 'L1ipmx', '暫時封鎖清單', '0');

-- ----------------------------
-- Table structure for server_activity_rank_config
-- ----------------------------
DROP TABLE IF EXISTS `server_activity_rank_config`;
CREATE TABLE `server_activity_rank_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` int(11) DEFAULT NULL COMMENT '活動伺服器',
  `server_name` varchar(10) DEFAULT NULL COMMENT '伺服器名稱',
  `item` int(10) DEFAULT NULL COMMENT '活動送道具',
  `name` varchar(30) DEFAULT NULL COMMENT '送的道具名稱',
  `count` int(10) DEFAULT NULL COMMENT '送道具數量',
  `NewActivityEndDate` datetime DEFAULT NULL COMMENT '結束時間',
  `start` int(2) DEFAULT '0' COMMENT '啟動開關',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_activity_rank_config
-- ----------------------------
INSERT INTO `server_activity_rank_config` VALUES ('1', '1', 'Yiwei', '60011', '高級內掛輔助月卡', '1', '2019-07-31 14:40:00', '0');

-- ----------------------------
-- Table structure for server_get_character_items
-- ----------------------------
DROP TABLE IF EXISTS `server_get_character_items`;
CREATE TABLE `server_get_character_items` (
  `key` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) DEFAULT '0',
  `item_id` int(11) DEFAULT NULL,
  `char_id` int(11) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `count` bigint(10) DEFAULT NULL,
  `is_equipped` int(11) DEFAULT NULL,
  `enchantlvl` int(11) DEFAULT NULL,
  `is_id` int(11) DEFAULT NULL,
  `durability` int(11) DEFAULT NULL,
  `charge_count` int(11) DEFAULT NULL,
  `remaining_time` int(11) DEFAULT NULL,
  `last_used` datetime DEFAULT NULL,
  `bless` int(11) DEFAULT NULL,
  `attr_enchant_kind` int(11) DEFAULT NULL,
  `attr_enchant_level` int(11) DEFAULT NULL,
  `gamno` varchar(50) DEFAULT NULL,
  `out` tinyint(1) DEFAULT '0',
  `server_no` int(2) DEFAULT NULL,
  `deldate` datetime DEFAULT NULL,
  PRIMARY KEY (`key`),
  KEY `key_id` (`char_id`)
) ENGINE=MyISAM AUTO_INCREMENT=100000030 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_get_character_items
-- ----------------------------

-- ----------------------------
-- Table structure for server_get_sponsor_package
-- ----------------------------
DROP TABLE IF EXISTS `server_get_sponsor_package`;
CREATE TABLE `server_get_sponsor_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水號',
  `vipDay` int(11) NOT NULL DEFAULT '0',
  `p_id` int(10) NOT NULL DEFAULT '0',
  `p_name` varchar(50) DEFAULT NULL,
  `count` int(10) NOT NULL DEFAULT '1',
  `server_no` int(10) NOT NULL DEFAULT '0',
  `account` varchar(50) NOT NULL,
  `out` tinyint(1) NOT NULL DEFAULT '0',
  `play` varchar(45) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `ip` varchar(45) DEFAULT NULL,
  `order_items_id` int(10) DEFAULT NULL,
  `note` varchar(50) DEFAULT NULL,
  `deldate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_get_sponsor_package
-- ----------------------------

-- ----------------------------
-- Table structure for server_get_user_package
-- ----------------------------
DROP TABLE IF EXISTS `server_get_user_package`;
CREATE TABLE `server_get_user_package` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `p_id` int(10) DEFAULT NULL,
  `p_name` varchar(255) DEFAULT NULL,
  `count` int(10) DEFAULT NULL,
  `server_no` int(2) DEFAULT NULL,
  `char_account` varchar(30) DEFAULT NULL,
  `char_objid` bigint(50) DEFAULT NULL,
  `out` int(1) DEFAULT '0',
  `play` varchar(45) DEFAULT NULL,
  `note` varchar(50) DEFAULT NULL,
  `ip` varchar(45) DEFAULT NULL,
  `deldate` datetime DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=200000000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_get_user_package
-- ----------------------------

-- ----------------------------
-- Table structure for server_info
-- ----------------------------
DROP TABLE IF EXISTS `server_info`;
CREATE TABLE `server_info` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `server_name` varchar(50) DEFAULT NULL,
  `minid` int(10) DEFAULT '1000000' COMMENT '預設最小ID編號',
  `maxid` int(10) DEFAULT NULL COMMENT '目前已用最大ID編號',
  `start` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '服務器 啟動狀態',
  `ip` varchar(50) DEFAULT '0.0.0.0',
  `port` varchar(50) DEFAULT '2000',
  `open` int(10) DEFAULT NULL COMMENT '0',
  `sponsor` tinyint(2) NOT NULL DEFAULT '0' COMMENT '贊助系統開關',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 3072 kB';

-- ----------------------------
-- Records of server_info
-- ----------------------------
INSERT INTO `server_info` VALUES ('1', '測試', '300000', '0', '1', '127.0.0.1', '2000', '0', '0');
INSERT INTO `server_info` VALUES ('2', '測試2', '1000000', null, '0', '0.0.0.0', '2000', '0', '0');
