/*
Navicat MySQL Data Transfer
Source Host     : localhost:25382
Source Database : yiwei_log
Target Host     : localhost:25382
Target Database : yiwei_log
Date: 2019-08-02 14:58:02
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for other_chat
-- ----------------------------
DROP TABLE IF EXISTS `other_chat`;
CREATE TABLE `other_chat` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `account_name` varchar(50) NOT NULL,
  `char_id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `clan_id` int(10) NOT NULL,
  `clan_name` varchar(50) DEFAULT NULL,
  `locx` int(10) NOT NULL,
  `locy` int(10) NOT NULL,
  `mapid` int(10) NOT NULL,
  `type` int(10) NOT NULL,
  `target_account_name` varchar(50) DEFAULT NULL,
  `target_id` int(10) DEFAULT '0',
  `target_name` varchar(50) DEFAULT NULL,
  `target_clan_id` int(10) DEFAULT NULL,
  `target_clan_name` varchar(50) DEFAULT NULL,
  `target_locx` int(10) DEFAULT NULL,
  `target_locy` int(10) DEFAULT NULL,
  `target_mapid` int(10) DEFAULT NULL,
  `content` varchar(256) DEFAULT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `char_id` (`char_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of other_chat
-- ----------------------------

-- ----------------------------
-- Table structure for other_get_package
-- ----------------------------
DROP TABLE IF EXISTS `other_get_package`;
CREATE TABLE `other_get_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT NULL,
  `object` bigint(50) DEFAULT NULL,
  `key` int(10) DEFAULT NULL,
  `itemid` int(10) DEFAULT NULL,
  `itemName` varchar(20) DEFAULT NULL,
  `itemCount` int(10) DEFAULT NULL,
  `datetime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2440 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of other_get_package
-- ----------------------------

-- ----------------------------
-- Table structure for other_item_del
-- ----------------------------
DROP TABLE IF EXISTS `other_item_del`;
CREATE TABLE `other_item_del` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '道具刪除紀錄',
  `item_objid` bigint(20) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `item_name` varchar(20) DEFAULT 'itemName',
  `character_items` text COMMENT '物品道具',
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `out` int(2) DEFAULT '0' COMMENT '歸還',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=86736 DEFAULT CHARSET=utf8 COMMENT='刪除紀錄';

-- ----------------------------
-- Records of other_item_del
-- ----------------------------

-- ----------------------------
-- Table structure for other_item_enchant
-- ----------------------------
DROP TABLE IF EXISTS `other_item_enchant`;
CREATE TABLE `other_item_enchant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_objid` bigint(20) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `item_name` varchar(20) DEFAULT '',
  `character_items` text COMMENT '物品道具',
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `out` int(2) DEFAULT '0' COMMENT '歸還',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=20630 DEFAULT CHARSET=utf8 COMMENT='爆裝紀錄';

-- ----------------------------
-- Records of other_item_enchant
-- ----------------------------

-- ----------------------------
-- Table structure for other_item_offline_trade
-- ----------------------------
DROP TABLE IF EXISTS `other_item_offline_trade`;
CREATE TABLE `other_item_offline_trade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `char_ip` varchar(50) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `charName` varchar(15) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL COMMENT '託售的道具ID',
  `itemName` varchar(50) DEFAULT NULL COMMENT '託售的道具名稱',
  `itemCount` bigint(20) DEFAULT NULL COMMENT '託售的道具數量',
  `adena` int(10) DEFAULT NULL COMMENT '託售金額',
  `end` bigint(20) DEFAULT NULL COMMENT '結束模式 0:出售中 1:已售出元寶未領回 2:已售出元寶領回 3:未售出道具未領回 4:未售出道具以領回',
  `datetime` datetime DEFAULT NULL,
  `none` varchar(15) DEFAULT NULL COMMENT 'LogOtherItemOfflineTrade',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='交易紀錄';

-- ----------------------------
-- Records of other_item_offline_trade
-- ----------------------------

-- ----------------------------
-- Table structure for other_item_sellnpc
-- ----------------------------
DROP TABLE IF EXISTS `other_item_sellnpc`;
CREATE TABLE `other_item_sellnpc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_objid` bigint(20) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `item_name` varchar(20) DEFAULT 'itemName',
  `character_items` text COMMENT '物品道具',
  `sellcount` bigint(20) DEFAULT NULL COMMENT '賣的數量',
  `sellprice` bigint(20) DEFAULT NULL COMMENT '賣的金額',
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `out` int(2) DEFAULT '0' COMMENT '歸還',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=852981 DEFAULT CHARSET=utf8 COMMENT='賣NPC紀錄';

-- ----------------------------
-- Records of other_item_sellnpc
-- ----------------------------

-- ----------------------------
-- Table structure for other_item_trade
-- ----------------------------
DROP TABLE IF EXISTS `other_item_trade`;
CREATE TABLE `other_item_trade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `char_ip` varchar(50) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `charName` varchar(15) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `itemName` varchar(50) DEFAULT NULL,
  `itemCount` bigint(20) DEFAULT NULL,
  `target_ip` varchar(50) DEFAULT NULL,
  `targetId` bigint(20) DEFAULT NULL,
  `targetName` varchar(15) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=12206 DEFAULT CHARSET=utf8 COMMENT='交易紀錄';

-- ----------------------------
-- Records of other_item_trade
-- ----------------------------

-- ----------------------------
-- Table structure for other_item_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `other_item_warehouse`;
CREATE TABLE `other_item_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_objid` bigint(20) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `itemName` varchar(50) DEFAULT NULL,
  `itemCount` bigint(20) DEFAULT NULL,
  `type` int(2) DEFAULT NULL COMMENT '倉庫存取類型 0 存  ; 1 取',
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=111036 DEFAULT CHARSET=utf8 COMMENT='倉庫使用紀錄';

-- ----------------------------
-- Records of other_item_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for other_item_warehouse_clan
-- ----------------------------
DROP TABLE IF EXISTS `other_item_warehouse_clan`;
CREATE TABLE `other_item_warehouse_clan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_objid` bigint(20) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `itemName` varchar(50) DEFAULT NULL,
  `itemCount` bigint(20) DEFAULT NULL,
  `type` int(2) DEFAULT NULL COMMENT '倉庫存取類型',
  `clanname` varchar(50) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=24303 DEFAULT CHARSET=utf8 COMMENT='血盟倉庫使用紀錄';

-- ----------------------------
-- Records of other_item_warehouse_clan
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_change
-- ----------------------------
DROP TABLE IF EXISTS `other_log_change`;
CREATE TABLE `other_log_change` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `olddata` varchar(25) DEFAULT NULL,
  `newdata` varchar(25) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='變更紀錄';

-- ----------------------------
-- Records of other_log_change
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_death
-- ----------------------------
DROP TABLE IF EXISTS `other_log_death`;
CREATE TABLE `other_log_death` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '死亡紀錄',
  `char_id` bigint(20) DEFAULT NULL,
  `deathMapid` int(10) DEFAULT NULL,
  `delExp` bigint(20) DEFAULT NULL,
  `delItem` text,
  `delSkill` text,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1518 DEFAULT CHARSET=utf8 COMMENT='角色死亡紀錄';

-- ----------------------------
-- Records of other_log_death
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_dissolve
-- ----------------------------
DROP TABLE IF EXISTS `other_log_dissolve`;
CREATE TABLE `other_log_dissolve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `char_id` bigint(20) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_name` varchar(20) DEFAULT 'itemName',
  `character_items` varchar(200) DEFAULT NULL,
  `getcount` bigint(20) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23060 DEFAULT CHARSET=utf8 COMMENT='溶解紀錄';

-- ----------------------------
-- Records of other_log_dissolve
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_store
-- ----------------------------
DROP TABLE IF EXISTS `other_log_store`;
CREATE TABLE `other_log_store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `storeType` int(2) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_name` varchar(20) DEFAULT 'itemName',
  `character_items` varchar(200) DEFAULT NULL,
  `itemCount` bigint(20) DEFAULT NULL,
  `goldCount` bigint(20) DEFAULT NULL,
  `target_id` bigint(20) DEFAULT NULL,
  `target` varchar(25) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='掛賣紀錄';

-- ----------------------------
-- Records of other_log_store
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_discard
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_discard`;
CREATE TABLE `other_log_world_discard` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_name` varchar(25) DEFAULT NULL,
  `discard_count` int(10) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14371 DEFAULT CHARSET=utf8 COMMENT='丟地上紀錄';

-- ----------------------------
-- Records of other_log_world_discard
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_disconnected
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_disconnected`;
CREATE TABLE `other_log_world_disconnected` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `type` int(2) DEFAULT NULL COMMENT '類型',
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=233 DEFAULT CHARSET=utf8 COMMENT='斷線紀錄';

-- ----------------------------
-- Records of other_log_world_disconnected
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_enchant_ok
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_enchant_ok`;
CREATE TABLE `other_log_world_enchant_ok` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_id` int(10) DEFAULT NULL,
  `item_name` varchar(30) DEFAULT NULL,
  `oldEnchantLvl` int(3) DEFAULT NULL,
  `newEnchantLvl` int(3) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6090 DEFAULT CHARSET=utf8 COMMENT='強化成功紀錄';

-- ----------------------------
-- Records of other_log_world_enchant_ok
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_explosion_proof
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_explosion_proof`;
CREATE TABLE `other_log_world_explosion_proof` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `type` int(3) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_name` varchar(25) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of other_log_world_explosion_proof
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_gm_command
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_gm_command`;
CREATE TABLE `other_log_world_gm_command` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `Ip` varchar(25) DEFAULT NULL,
  `char_acc` varchar(25) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `command` varchar(25) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2907 DEFAULT CHARSET=utf8 COMMENT='GM指令紀錄';

-- ----------------------------
-- Records of other_log_world_gm_command
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_pick_up
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_pick_up`;
CREATE TABLE `other_log_world_pick_up` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `mapid` int(10) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_name` varchar(25) DEFAULT NULL,
  `pickup_count` bigint(20) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=9886 DEFAULT CHARSET=utf8 COMMENT='撿起紀錄';

-- ----------------------------
-- Records of other_log_world_pick_up
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_special_buy
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_special_buy`;
CREATE TABLE `other_log_world_special_buy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `gold_id` int(10) DEFAULT NULL,
  `gold_count` bigint(20) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_id` int(10) DEFAULT NULL,
  `item_enchantlvl` int(10) DEFAULT '0',
  `itemName` varchar(25) DEFAULT NULL,
  `itemCount` bigint(20) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3570 DEFAULT CHARSET=utf8 COMMENT='特殊購買';

-- ----------------------------
-- Records of other_log_world_special_buy
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_special_receive
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_special_receive`;
CREATE TABLE `other_log_world_special_receive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `type` int(3) DEFAULT NULL,
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `clan_id` bigint(2) DEFAULT NULL,
  `clan_name` varchar(25) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_name` varchar(25) DEFAULT NULL,
  `item_count` bigint(20) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='特殊領取';

-- ----------------------------
-- Records of other_log_world_special_receive
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_throw_npc
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_throw_npc`;
CREATE TABLE `other_log_world_throw_npc` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `npc_id` int(10) DEFAULT NULL,
  `npc_name` varchar(25) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_name` varchar(25) DEFAULT NULL,
  `throw_count` int(10) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6013 DEFAULT CHARSET=utf8 COMMENT='給NPC紀錄';

-- ----------------------------
-- Records of other_log_world_throw_npc
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_treasure
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_treasure`;
CREATE TABLE `other_log_world_treasure` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `map_id` int(5) DEFAULT '0' COMMENT '地圖',
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `item_objid` bigint(20) DEFAULT NULL,
  `item_id` int(10) DEFAULT NULL,
  `itemName` varchar(25) DEFAULT NULL,
  `itemCount` bigint(20) DEFAULT NULL,
  `npc_id` int(10) DEFAULT NULL,
  `npc_name` varchar(25) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3032 DEFAULT CHARSET=utf8 COMMENT='打寶紀錄';

-- ----------------------------
-- Records of other_log_world_treasure
-- ----------------------------

-- ----------------------------
-- Table structure for other_log_world_useitem
-- ----------------------------
DROP TABLE IF EXISTS `other_log_world_useitem`;
CREATE TABLE `other_log_world_useitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_no` smallint(2) DEFAULT '0' COMMENT '伺服器編號',
  `char_id` bigint(20) DEFAULT NULL,
  `char_name` varchar(25) DEFAULT NULL,
  `item_id` int(10) DEFAULT NULL,
  `item_name` varchar(25) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `ip` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5543 DEFAULT CHARSET=utf8 COMMENT='丟地上紀錄';

-- ----------------------------
-- Records of other_log_world_useitem
-- ----------------------------
