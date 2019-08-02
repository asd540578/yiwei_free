/*
Navicat MySQL Data Transfer
Source Host     : localhost:25382
Source Database : yiwei_01
Target Host     : localhost:25382
Target Database : yiwei_01
Date: 2019-08-02 15:17:43
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for character_bank
-- ----------------------------
DROP TABLE IF EXISTS `character_bank`;
CREATE TABLE `character_bank` (
  `account_name` varchar(45) NOT NULL,
  `adena_count` bigint(10) NOT NULL DEFAULT '0',
  `pass` varchar(10) NOT NULL,
  `settime` datetime NOT NULL,
  PRIMARY KEY (`account_name`),
  KEY `char_id` (`account_name`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_bank
-- ----------------------------

-- ----------------------------
-- Table structure for character_buddys
-- ----------------------------
DROP TABLE IF EXISTS `character_buddys`;
CREATE TABLE `character_buddys` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `char_id` int(10) NOT NULL DEFAULT '0',
  `buddy_id` int(10) unsigned NOT NULL DEFAULT '0',
  `buddy_name` varchar(45) NOT NULL,
  PRIMARY KEY (`char_id`,`buddy_id`),
  KEY `key_id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25548 DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB';

-- ----------------------------
-- Records of character_buddys
-- ----------------------------

-- ----------------------------
-- Table structure for character_buff
-- ----------------------------
DROP TABLE IF EXISTS `character_buff`;
CREATE TABLE `character_buff` (
  `char_obj_id` int(10) NOT NULL DEFAULT '0',
  `skill_id` int(10) unsigned NOT NULL DEFAULT '0',
  `remaining_time` int(10) NOT NULL DEFAULT '0',
  `poly_id` int(10) DEFAULT '0',
  PRIMARY KEY (`char_obj_id`,`skill_id`),
  KEY `char_obj_id` (`char_obj_id`,`skill_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB';

-- ----------------------------
-- Records of character_buff
-- ----------------------------
INSERT INTO `character_buff` VALUES ('300477', '1001', '282', '-1');
INSERT INTO `character_buff` VALUES ('301047', '1000', '3148', '-1');
INSERT INTO `character_buff` VALUES ('301047', '1001', '3148', '-1');

-- ----------------------------
-- Table structure for character_config
-- ----------------------------
DROP TABLE IF EXISTS `character_config`;
CREATE TABLE `character_config` (
  `object_id` int(10) NOT NULL DEFAULT '0',
  `length` int(10) unsigned NOT NULL DEFAULT '0',
  `data` blob,
  PRIMARY KEY (`object_id`),
  KEY `object_id` (`object_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_config
-- ----------------------------
INSERT INTO `character_config` VALUES ('300185', '300', 0x4B174686400300320000FFFFFFFF0F0F0F00D5FFD5FFD5FF00000000000000000000000000000000000000000000000000000000000203040000000000000000000000000000000000020304000000000000000000000000000000000002030422D9C6C7006CA47F145AA9B42C0683A12F0683A02FE8095A0ED85DF334A0A7B03AAE108910DE0F9E003A56C92A788F0A0D46B28C0C51DF8C2A3A1F712B16BBF70F39A8BB15C259EB2A189B313CBADEC42BC97F0A017F44F1165A70DD083A019D1B12E8232E12E8232E71FA241A5983073F06294838D4F56008F753393B48F5CB35315C1D21C0DDE321000000000000000000FFFFFF000000000000000000FEFEFEFEFEFEFEFEFEFEFEFE0300000003000000016C02D101F9092A5DFFFFFFFF01010000000000000000000000000140);
INSERT INTO `character_config` VALUES ('300477', '284', 0x4B4B4686400300320000EA00D0010F0F0F00D5FFD5FFD5FF070707000000000000000000D9C6C600D9C6C600D9C6C6000000000000020304D9C6C600D9C6C600D9C6C6000000000000020304D9C6C600D9C6C600D9C6C60000000000000203041ED9C6C7006CA47F145AA9B42C0683A12F0683A02F51DF8C2A3A1F712B16BBF70FC259EB2A189B313CBADEC42BC97F0A0164CECE275A70DD083A019D1C12E8232E12E8232E71FA241B5983074006294839D4F56009F753393B48F5CB35C0DDE321290B9F263EF9E3087C4BDA39DF1A3208604E0B3611B2862C000000000000000000FFFFFF000000000000000000FEFEFEFEFEFEFEFEFEFEFEFE0300000003000000016C02D101F89B2A5DFFFFFFFF00010000000000000000000000000140);
INSERT INTO `character_config` VALUES ('301047', '304', 0x4B384686400300320000010001000F0F0F0055FFD5FFD5FF0F0707000000000000000000D9C6C600D9C6C600D9C6C60081B21F1400020304D9C6C600D9C6C600D9C6C6000000000000020304D9C6C600D9C6C600D9C6C600000000000002030423D9C6C7006CA47F145AA9B42C0683A12F0683A02FE8095A0EE349AD30AB93EF3EB97CC714C5C5872E71A0C6085C4BC62E450B302F2127BA0B4494FA11AD3BB12EC38A7038A5CA862F74EE44058A30AF1205DFA30C5DD65D2A5DD65D2A7C6A621E64EF4C3B11950A3CF753393B48F5CB35315C1D21C0DDE3212B0C4F0081B22014256DE31F3A019D1BA238A028000000000000000000FFFFFF000000000000000000FEFEFEFEFEFEFEFEFEFEFEFE0300000003000000016C02D10100000000FFFFFFFF01010000000000000000000000000100);

-- ----------------------------
-- Table structure for character_elf_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `character_elf_warehouse`;
CREATE TABLE `character_elf_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(13) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `count` bigint(11) DEFAULT NULL,
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
  PRIMARY KEY (`id`),
  KEY `key_id` (`account_name`)
) ENGINE=MyISAM AUTO_INCREMENT=1497070490 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_elf_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for character_furniture
-- ----------------------------
DROP TABLE IF EXISTS `character_furniture`;
CREATE TABLE `character_furniture` (
  `item_obj_id` int(10) unsigned NOT NULL DEFAULT '0',
  `npcid` int(10) unsigned NOT NULL DEFAULT '0',
  `locx` int(10) NOT NULL DEFAULT '0',
  `locy` int(10) NOT NULL DEFAULT '0',
  `mapid` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_obj_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_furniture
-- ----------------------------

-- ----------------------------
-- Table structure for character_gambling
-- ----------------------------
DROP TABLE IF EXISTS `character_gambling`;
CREATE TABLE `character_gambling` (
  `id` int(10) NOT NULL DEFAULT '0',
  `adena` bigint(10) unsigned NOT NULL DEFAULT '0',
  `rate` double(5,2) NOT NULL DEFAULT '0.00',
  `gamblingno` varchar(50) NOT NULL,
  `outcount` int(10) NOT NULL DEFAULT '0',
  `r` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_gambling
-- ----------------------------

-- ----------------------------
-- Table structure for character_item_power
-- ----------------------------
DROP TABLE IF EXISTS `character_item_power`;
CREATE TABLE `character_item_power` (
  `item_obj_id` int(10) unsigned NOT NULL,
  `hole_count` int(10) unsigned NOT NULL DEFAULT '0',
  `hole_1` int(10) NOT NULL DEFAULT '0',
  `hole_2` int(10) NOT NULL DEFAULT '0',
  `hole_3` int(10) NOT NULL DEFAULT '0',
  `hole_4` int(10) NOT NULL DEFAULT '0',
  `hole_5` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_obj_id`),
  KEY `char_id` (`item_obj_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_item_power
-- ----------------------------

-- ----------------------------
-- Table structure for character_items
-- ----------------------------
DROP TABLE IF EXISTS `character_items`;
CREATE TABLE `character_items` (
  `id` int(11) NOT NULL DEFAULT '0',
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
  PRIMARY KEY (`id`),
  KEY `key_id` (`char_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_items
-- ----------------------------
INSERT INTO `character_items` VALUES ('300203', '20085', '300185', 'T恤', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300202', '20095', '300185', '古老的金屬盔甲', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300201', '20094', '300185', '古老的鱗甲', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300200', '20093', '300185', '古老的長袍', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300199', '20092', '300185', '古老的皮盔甲', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300198', '271', '300185', '黑曜石奇古獸', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300197', '273', '300185', '破滅者鎖鏈劍', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300196', '117', '300185', '蕾雅魔杖', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300195', '84', '300185', '暗黑雙刀', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300194', '68', '300185', '古老的劍', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300193', '67', '300185', '古老的巨劍', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300192', '182', '300185', '古老的弩槍', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300191', '44156', '300185', '經驗值 魔法卷軸', '1', '0', '0', '0', '0', '0', '0', null, '0', '0', '0', null);
INSERT INTO `character_items` VALUES ('300190', '140100', '300185', '瞬間移動卷軸(祝福)', '100', '0', '0', '0', '0', '0', '0', null, '0', '0', '0', null);
INSERT INTO `character_items` VALUES ('300189', '40100', '300185', '瞬間移動卷軸', '93', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300188', '40079', '300185', '傳送回家的卷軸', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300187', '40117', '300185', '銀騎士村莊指定傳送卷軸', '98', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300186', '40308', '300185', '金幣', '10000000', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300204', '20044', '300185', '藍海賊頭巾', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300205', '20182', '300185', '手套', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300206', '20073', '300185', '精靈斗篷', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300207', '20192', '300185', '皮長靴', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300208', '20236', '300185', '精靈盾牌', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300209', '20264', '300185', '力量項鏈', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300210', '20280', '300185', '滅魔戒指', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300211', '20280', '300185', '滅魔戒指', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300212', '20281', '300185', '變形控制戒指', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300213', '20284', '300185', '召喚控制戒指', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300214', '20314', '300185', '古代巨人戒指', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300215', '21013', '300185', '寬容耳環', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300216', '40024', '300185', '古代終極體力恢復劑', '500', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300217', '40018', '300185', '強化綠色藥水', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300218', '40733', '300185', '名譽貨幣', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300219', '40088', '300185', '變形卷軸', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300478', '40308', '300477', '金幣', '10015870', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300479', '40117', '300477', '銀騎士村莊指定傳送卷軸', '99', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300480', '40079', '300477', '傳送回家的卷軸', '98', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300481', '40100', '300477', '瞬間移動卷軸', '93', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300482', '140100', '300477', '瞬間移動卷軸(祝福)', '101', '0', '0', '0', '0', '0', '0', null, '0', '0', '0', null);
INSERT INTO `character_items` VALUES ('300548', '20288', '300477', '傳送控制戒指', '1', '0', '0', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300779', '44070', '300477', '元寶', '1600', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300600', '40013', '300477', '自我加速藥水', '1', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300752', '1', '300477', '歐西斯匕首', '1', '1', '10000', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300491', '20092', '300477', '古老的皮盔甲', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300492', '20093', '300477', '古老的長袍', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300493', '20094', '300477', '古老的鱗甲', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300798', '40084', '300477', '狄亞得移動卷軸', '50', '0', '0', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300495', '20085', '300477', 'T恤', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300496', '20044', '300477', '藍海賊頭巾', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300497', '20182', '300477', '手套', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300498', '20073', '300477', '精靈斗篷', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300500', '20236', '300477', '精靈盾牌', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300501', '20264', '300477', '力量項鏈', '1', '1', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300502', '20280', '300477', '滅魔戒指', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300503', '20280', '300477', '滅魔戒指', '1', '0', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300504', '20281', '300477', '變形控制戒指', '1', '1', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300505', '20284', '300477', '召喚控制戒指', '1', '1', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300506', '20314', '300477', '古代巨人戒指', '1', '1', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300507', '21013', '300477', '寬容耳環', '1', '1', '10', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300508', '40024', '300477', '古代終極體力恢復劑', '500', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300509', '40018', '300477', '強化綠色藥水', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300511', '40088', '300477', '變形卷軸', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300574', '40318', '300477', '魔法寶石', '48', '0', '0', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('300801', '40074', '300477', '對盔甲施法的卷軸', '49', '0', '0', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301081', '40088', '301047', '變形卷軸', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301080', '40733', '301047', '名譽貨幣', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301079', '40018', '301047', '強化綠色藥水', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301078', '40024', '301047', '古代終極體力恢復劑', '500', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301236', '40126', '301047', '鑒定卷軸', '31', '0', '0', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301076', '20314', '301047', '古代巨人戒指', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301075', '20284', '301047', '召喚控制戒指', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301064', '20095', '301047', '古老的金屬盔甲', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301065', '20085', '301047', 'T恤', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301066', '20044', '301047', '藍海賊頭巾', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301067', '20182', '301047', '手套', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301068', '20073', '301047', '精靈斗篷', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301069', '20192', '301047', '皮長靴', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301070', '20236', '301047', '精靈盾牌', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301071', '20264', '301047', '力量項鏈', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301072', '20280', '301047', '滅魔戒指', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301073', '20280', '301047', '滅魔戒指', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301074', '20281', '301047', '變形控制戒指', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301063', '20094', '301047', '古老的鱗甲', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301061', '20092', '301047', '古老的皮盔甲', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301062', '20093', '301047', '古老的長袍', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301060', '271', '301047', '黑曜石奇古獸', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301242', '20264', '301047', '力量項鏈', '1', '0', '0', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301058', '117', '301047', '蕾雅魔杖', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301123', '40087', '301047', '對武器施法的卷軸', '49', '0', '0', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301055', '67', '301047', '古老的巨劍', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301056', '68', '301047', '古老的劍', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301054', '182', '301047', '古老的弩槍', '1', '0', '10', '1', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301053', '44156', '301047', '經驗值 魔法卷軸', '1', '0', '0', '0', '0', '0', '0', null, '0', '0', '0', null);
INSERT INTO `character_items` VALUES ('301052', '140100', '301047', '瞬間移動卷軸(祝福)', '100', '0', '0', '0', '0', '0', '0', null, '0', '0', '0', null);
INSERT INTO `character_items` VALUES ('301051', '40100', '301047', '瞬間移動卷軸', '100', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301050', '40079', '301047', '傳送回家的卷軸', '99', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301049', '40117', '301047', '銀騎士村莊指定傳送卷軸', '98', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301048', '40308', '301047', '金幣', '10006810', '0', '0', '0', '0', '0', '0', null, '1', '0', '0', null);
INSERT INTO `character_items` VALUES ('301243', '126', '301047', '瑪那魔杖', '1', '0', '0', '1', '0', '0', '0', null, '1', '0', '0', null);

-- ----------------------------
-- Table structure for character_items_time
-- ----------------------------
DROP TABLE IF EXISTS `character_items_time`;
CREATE TABLE `character_items_time` (
  `itemr_obj_id` int(11) NOT NULL DEFAULT '0',
  `usertime` datetime NOT NULL,
  PRIMARY KEY (`itemr_obj_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_items_time
-- ----------------------------

-- ----------------------------
-- Table structure for character_mail
-- ----------------------------
DROP TABLE IF EXISTS `character_mail`;
CREATE TABLE `character_mail` (
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `type` int(10) unsigned NOT NULL DEFAULT '0',
  `sender` varchar(16) DEFAULT NULL,
  `receiver` varchar(16) DEFAULT NULL,
  `date` varchar(16) DEFAULT NULL,
  `read_status` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `subject` blob,
  `content` blob,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_mail
-- ----------------------------

-- ----------------------------
-- Table structure for character_other
-- ----------------------------
DROP TABLE IF EXISTS `character_other`;
CREATE TABLE `character_other` (
  `char_obj_id` int(10) NOT NULL DEFAULT '0',
  `hpup` int(10) unsigned NOT NULL DEFAULT '0',
  `mpup` int(10) unsigned NOT NULL DEFAULT '0',
  `score` int(10) NOT NULL DEFAULT '0',
  `title` int(10) NOT NULL DEFAULT '0',
  `color` int(10) NOT NULL DEFAULT '0',
  `usemap` int(11) NOT NULL DEFAULT '-1',
  `usemaptime` int(11) NOT NULL DEFAULT '0',
  `uplevel` int(10) NOT NULL DEFAULT '0',
  `clanskill` int(10) NOT NULL DEFAULT '0',
  `deathCount` int(10) NOT NULL DEFAULT '0',
  `killCount` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`char_obj_id`),
  KEY `char_obj_id` (`char_obj_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB';

-- ----------------------------
-- Records of character_other
-- ----------------------------
INSERT INTO `character_other` VALUES ('300185', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0');
INSERT INTO `character_other` VALUES ('300477', '0', '0', '0', '0', '1', '-1', '0', '0', '0', '0', '0');
INSERT INTO `character_other` VALUES ('301047', '0', '0', '0', '0', '1', '0', '0', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for character_pets
-- ----------------------------
DROP TABLE IF EXISTS `character_pets`;
CREATE TABLE `character_pets` (
  `item_obj_id` int(10) unsigned NOT NULL DEFAULT '0',
  `objid` int(10) unsigned NOT NULL DEFAULT '0',
  `npcid` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `lvl` int(10) unsigned NOT NULL DEFAULT '0',
  `hp` int(10) unsigned NOT NULL DEFAULT '0',
  `mp` int(10) unsigned NOT NULL DEFAULT '0',
  `exp` int(10) unsigned NOT NULL DEFAULT '0',
  `lawful` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_obj_id`),
  KEY `item_obj_id` (`item_obj_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_pets
-- ----------------------------

-- ----------------------------
-- Table structure for character_quests
-- ----------------------------
DROP TABLE IF EXISTS `character_quests`;
CREATE TABLE `character_quests` (
  `char_id` int(10) unsigned NOT NULL,
  `quest_id` int(10) unsigned NOT NULL DEFAULT '0',
  `quest_step` int(10) unsigned NOT NULL DEFAULT '0',
  `note` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`char_id`,`quest_id`),
  KEY `char_id` (`char_id`,`quest_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_quests
-- ----------------------------

-- ----------------------------
-- Table structure for character_shop
-- ----------------------------
DROP TABLE IF EXISTS `character_shop`;
CREATE TABLE `character_shop` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `item_obj_id` int(11) NOT NULL DEFAULT '0',
  `user_obj_id` int(10) unsigned NOT NULL DEFAULT '0',
  `adena` int(10) unsigned NOT NULL DEFAULT '0',
  `overtime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  `end` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '結束模式 0:出售中 1:已售出元寶未領回 2:已售出元寶領回 3:未售出道具未領回 4:未售出道具以領回',
  `none` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `item_obj_id` (`item_obj_id`)
) ENGINE=MyISAM AUTO_INCREMENT=17300 DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB';

-- ----------------------------
-- Records of character_shop
-- ----------------------------

-- ----------------------------
-- Table structure for character_shopinfo
-- ----------------------------
DROP TABLE IF EXISTS `character_shopinfo`;
CREATE TABLE `character_shopinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `count` bigint(11) DEFAULT NULL,
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
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1497762057 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_shopinfo
-- ----------------------------

-- ----------------------------
-- Table structure for character_skills
-- ----------------------------
DROP TABLE IF EXISTS `character_skills`;
CREATE TABLE `character_skills` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `char_obj_id` int(10) NOT NULL DEFAULT '0',
  `skill_id` int(10) unsigned NOT NULL DEFAULT '0',
  `skill_name` varchar(45) NOT NULL DEFAULT '',
  `is_active` int(10) DEFAULT NULL,
  `activetimeleft` int(10) DEFAULT NULL,
  PRIMARY KEY (`char_obj_id`,`skill_id`),
  KEY `key_id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=552294 DEFAULT CHARSET=utf8 COMMENT='MyISAM free: 10240 kB; MyISAM free: 10240 kB';

-- ----------------------------
-- Records of character_skills
-- ----------------------------
INSERT INTO `character_skills` VALUES ('552215', '300477', '4', '光箭', '0', '0');
INSERT INTO `character_skills` VALUES ('552216', '300477', '1', '初級治癒術', '0', '0');
INSERT INTO `character_skills` VALUES ('552217', '300477', '2', '日光術', '0', '0');
INSERT INTO `character_skills` VALUES ('552218', '300477', '3', '保護罩', '0', '0');
INSERT INTO `character_skills` VALUES ('552219', '300477', '5', '指定傳送', '0', '0');
INSERT INTO `character_skills` VALUES ('552220', '300477', '6', '冰箭', '0', '0');
INSERT INTO `character_skills` VALUES ('552221', '300477', '7', '風刃', '0', '0');
INSERT INTO `character_skills` VALUES ('552222', '300477', '8', '神聖武器', '0', '0');
INSERT INTO `character_skills` VALUES ('552223', '300477', '9', '解毒術', '0', '0');
INSERT INTO `character_skills` VALUES ('552224', '300477', '10', '寒冷戰慄', '0', '0');
INSERT INTO `character_skills` VALUES ('552225', '300477', '11', '毒咒', '0', '0');
INSERT INTO `character_skills` VALUES ('552226', '300477', '12', '擬似魔法武器', '0', '0');
INSERT INTO `character_skills` VALUES ('552227', '300477', '13', '無所遁形術', '0', '0');
INSERT INTO `character_skills` VALUES ('552228', '300477', '14', '負重強化', '0', '0');
INSERT INTO `character_skills` VALUES ('552229', '300477', '15', '火箭', '0', '0');
INSERT INTO `character_skills` VALUES ('552230', '300477', '16', '地獄之牙', '0', '0');
INSERT INTO `character_skills` VALUES ('552231', '300477', '17', '極光雷電', '0', '0');
INSERT INTO `character_skills` VALUES ('552232', '300477', '18', '起死回生術', '0', '0');
INSERT INTO `character_skills` VALUES ('552233', '300477', '19', '中級治癒術', '0', '0');
INSERT INTO `character_skills` VALUES ('552234', '300477', '20', '闇盲咒術', '0', '0');
INSERT INTO `character_skills` VALUES ('552235', '300477', '21', '鎧甲護持', '0', '0');
INSERT INTO `character_skills` VALUES ('552236', '300477', '22', '寒冰氣息', '0', '0');
INSERT INTO `character_skills` VALUES ('552237', '300477', '23', '能量感測', '0', '0');
INSERT INTO `character_skills` VALUES ('552238', '300477', '25', '燃燒的火球', '0', '0');
INSERT INTO `character_skills` VALUES ('552239', '300477', '26', '通暢氣脈術', '0', '0');
INSERT INTO `character_skills` VALUES ('552240', '300477', '27', '壞物術', '0', '0');
INSERT INTO `character_skills` VALUES ('552241', '300477', '28', '吸血鬼之吻', '0', '0');
INSERT INTO `character_skills` VALUES ('552242', '300477', '29', '緩速術', '0', '0');
INSERT INTO `character_skills` VALUES ('552243', '300477', '30', '巖牢', '0', '0');
INSERT INTO `character_skills` VALUES ('552244', '300477', '31', '魔法屏障', '0', '0');
INSERT INTO `character_skills` VALUES ('552245', '300477', '32', '冥想術', '0', '0');
INSERT INTO `character_skills` VALUES ('552246', '300477', '33', '木乃伊的詛咒', '0', '0');
INSERT INTO `character_skills` VALUES ('552247', '300477', '34', '極道落雷', '0', '0');
INSERT INTO `character_skills` VALUES ('552248', '300477', '35', '高級治癒術', '0', '0');
INSERT INTO `character_skills` VALUES ('552249', '300477', '36', '迷魅術', '0', '0');
INSERT INTO `character_skills` VALUES ('552250', '300477', '37', '聖潔之光', '0', '0');
INSERT INTO `character_skills` VALUES ('552251', '300477', '38', '冰錐', '0', '0');
INSERT INTO `character_skills` VALUES ('552252', '300477', '39', '魔力奪取', '0', '0');
INSERT INTO `character_skills` VALUES ('552253', '300477', '40', '黑闇之影', '0', '0');
INSERT INTO `character_skills` VALUES ('552254', '300477', '41', '造屍術', '0', '0');
INSERT INTO `character_skills` VALUES ('552255', '300477', '42', '體魄強健術', '0', '0');
INSERT INTO `character_skills` VALUES ('552256', '300477', '43', '加速術', '0', '0');
INSERT INTO `character_skills` VALUES ('552257', '300477', '44', '魔法相消術', '0', '0');
INSERT INTO `character_skills` VALUES ('552258', '300477', '45', '地裂術', '0', '0');
INSERT INTO `character_skills` VALUES ('552259', '300477', '46', '烈炎術', '0', '0');
INSERT INTO `character_skills` VALUES ('552260', '300477', '47', '弱化術', '0', '0');
INSERT INTO `character_skills` VALUES ('552261', '300477', '48', '祝福魔法武器', '0', '0');
INSERT INTO `character_skills` VALUES ('552262', '300477', '49', '體力回復術', '0', '0');
INSERT INTO `character_skills` VALUES ('552263', '300477', '50', '冰矛圍籬', '0', '0');
INSERT INTO `character_skills` VALUES ('552264', '300477', '51', '召喚術', '0', '0');
INSERT INTO `character_skills` VALUES ('552265', '300477', '52', '神聖疾走', '0', '0');
INSERT INTO `character_skills` VALUES ('552266', '300477', '53', '龍捲風', '0', '0');
INSERT INTO `character_skills` VALUES ('552267', '300477', '54', '強力加速術', '0', '0');
INSERT INTO `character_skills` VALUES ('552268', '300477', '55', '狂暴術', '0', '0');
INSERT INTO `character_skills` VALUES ('552269', '300477', '56', '疾病術', '0', '0');
INSERT INTO `character_skills` VALUES ('552270', '300477', '57', '全部治癒術', '0', '0');
INSERT INTO `character_skills` VALUES ('552271', '300477', '58', '火牢', '0', '0');
INSERT INTO `character_skills` VALUES ('552272', '300477', '59', '冰雪暴', '0', '0');
INSERT INTO `character_skills` VALUES ('552273', '300477', '60', '隱身術', '0', '0');
INSERT INTO `character_skills` VALUES ('552274', '300477', '61', '返生術', '0', '0');
INSERT INTO `character_skills` VALUES ('552275', '300477', '62', '震裂術', '0', '0');
INSERT INTO `character_skills` VALUES ('552276', '300477', '63', '治癒能量風暴', '0', '0');
INSERT INTO `character_skills` VALUES ('552277', '300477', '64', '魔法封印', '0', '0');
INSERT INTO `character_skills` VALUES ('552278', '300477', '65', '雷霆風暴', '0', '0');
INSERT INTO `character_skills` VALUES ('552279', '300477', '66', '沉睡之霧', '0', '0');
INSERT INTO `character_skills` VALUES ('552280', '300477', '67', '變形術', '0', '0');
INSERT INTO `character_skills` VALUES ('552281', '300477', '68', '聖結界', '0', '0');
INSERT INTO `character_skills` VALUES ('552282', '300477', '69', '集體傳送術', '0', '0');
INSERT INTO `character_skills` VALUES ('552283', '300477', '70', '火風暴', '0', '0');
INSERT INTO `character_skills` VALUES ('552284', '300477', '71', '藥水霜化術', '0', '0');
INSERT INTO `character_skills` VALUES ('552285', '300477', '72', '強力無所遁形術', '0', '0');
INSERT INTO `character_skills` VALUES ('552286', '300477', '73', '創造魔法武器', '0', '0');
INSERT INTO `character_skills` VALUES ('552287', '300477', '74', '流星雨', '0', '0');
INSERT INTO `character_skills` VALUES ('552288', '300477', '75', '終極返生術', '0', '0');
INSERT INTO `character_skills` VALUES ('552289', '300477', '76', '集體緩速術', '0', '0');
INSERT INTO `character_skills` VALUES ('552290', '300477', '77', '究極光裂術', '0', '0');
INSERT INTO `character_skills` VALUES ('552291', '300477', '78', '絕對屏障', '0', '0');
INSERT INTO `character_skills` VALUES ('552292', '300477', '79', '靈魂昇華', '0', '0');
INSERT INTO `character_skills` VALUES ('552293', '300477', '80', '冰雪颶風', '0', '0');

-- ----------------------------
-- Table structure for character_teleport
-- ----------------------------
DROP TABLE IF EXISTS `character_teleport`;
CREATE TABLE `character_teleport` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `char_id` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `locx` int(10) unsigned NOT NULL DEFAULT '0',
  `locy` int(10) unsigned NOT NULL DEFAULT '0',
  `mapid` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `key_id` (`char_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1497842697 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_teleport
-- ----------------------------
INSERT INTO `character_teleport` VALUES ('300541', '300477', '\\aF@@@', '32938', '33201', '4');
INSERT INTO `character_teleport` VALUES ('300587', '300477', '\\aB哈嚕', '32938', '33200', '4');
INSERT INTO `character_teleport` VALUES ('300606', '300477', '\\aK蕭蕭', '32934', '33205', '4');

-- ----------------------------
-- Table structure for character_teleport_config
-- ----------------------------
DROP TABLE IF EXISTS `character_teleport_config`;
CREATE TABLE `character_teleport_config` (
  `object_id` int(10) NOT NULL DEFAULT '0',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0',
  `data` blob,
  PRIMARY KEY (`object_id`),
  KEY `object_id` (`object_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_teleport_config
-- ----------------------------
INSERT INTO `character_teleport_config` VALUES ('300477', '0', 0x0200010202);

-- ----------------------------
-- Table structure for character_vip
-- ----------------------------
DROP TABLE IF EXISTS `character_vip`;
CREATE TABLE `character_vip` (
  `char_obj_id` int(10) unsigned NOT NULL,
  `overtime` datetime NOT NULL DEFAULT '1970-01-01 00:00:00',
  PRIMARY KEY (`char_obj_id`),
  KEY `char_id` (`char_obj_id`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_vip
-- ----------------------------

-- ----------------------------
-- Table structure for character_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `character_warehouse`;
CREATE TABLE `character_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(13) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `count` bigint(11) DEFAULT NULL,
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
  PRIMARY KEY (`id`),
  KEY `key_id` (`account_name`)
) ENGINE=MyISAM AUTO_INCREMENT=1497857891 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of character_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for characters
-- ----------------------------
DROP TABLE IF EXISTS `characters`;
CREATE TABLE `characters` (
  `account_name` varchar(13) NOT NULL DEFAULT '0',
  `objid` int(11) unsigned NOT NULL DEFAULT '0',
  `char_name` varchar(45) NOT NULL DEFAULT '',
  `level` int(11) unsigned NOT NULL DEFAULT '0',
  `HighLevel` int(11) unsigned NOT NULL DEFAULT '0',
  `Exp` bigint(10) unsigned NOT NULL DEFAULT '0',
  `MaxHp` int(10) unsigned NOT NULL DEFAULT '0',
  `CurHp` int(10) unsigned NOT NULL DEFAULT '0',
  `MaxMp` int(10) NOT NULL DEFAULT '0',
  `CurMp` int(10) NOT NULL DEFAULT '0',
  `Ac` int(10) NOT NULL DEFAULT '0',
  `Str` int(3) NOT NULL DEFAULT '0',
  `Con` int(3) NOT NULL DEFAULT '0',
  `Dex` int(3) NOT NULL DEFAULT '0',
  `Cha` int(3) NOT NULL DEFAULT '0',
  `Intel` int(3) NOT NULL DEFAULT '0',
  `Wis` int(3) NOT NULL DEFAULT '0',
  `Status` int(10) unsigned NOT NULL DEFAULT '0',
  `Class` int(10) unsigned NOT NULL DEFAULT '0',
  `Sex` int(10) unsigned NOT NULL DEFAULT '0',
  `Type` int(10) unsigned NOT NULL DEFAULT '0',
  `Heading` int(10) unsigned NOT NULL DEFAULT '0',
  `LocX` int(11) unsigned NOT NULL DEFAULT '0',
  `LocY` int(11) unsigned NOT NULL DEFAULT '0',
  `MapID` int(10) unsigned NOT NULL DEFAULT '0',
  `Food` int(10) unsigned NOT NULL DEFAULT '0',
  `Lawful` int(10) NOT NULL DEFAULT '0',
  `Title` varchar(35) NOT NULL DEFAULT '',
  `ClanID` int(10) unsigned NOT NULL DEFAULT '0',
  `Clanname` varchar(45) NOT NULL DEFAULT '',
  `ClanRank` int(3) NOT NULL DEFAULT '0',
  `BonusStatus` int(10) NOT NULL DEFAULT '0',
  `ElixirStatus` int(10) NOT NULL DEFAULT '0',
  `ElfAttr` int(10) NOT NULL DEFAULT '0',
  `PKcount` int(10) NOT NULL DEFAULT '0',
  `PkCountForElf` int(10) NOT NULL DEFAULT '0',
  `ExpRes` int(10) NOT NULL DEFAULT '0',
  `PartnerID` int(10) NOT NULL DEFAULT '0',
  `AccessLevel` int(10) unsigned NOT NULL DEFAULT '0',
  `OnlineStatus` int(10) unsigned NOT NULL DEFAULT '0',
  `HomeTownID` int(10) NOT NULL DEFAULT '0',
  `Contribution` int(10) NOT NULL DEFAULT '0',
  `Pay` int(10) NOT NULL DEFAULT '0',
  `HellTime` int(10) unsigned NOT NULL DEFAULT '0',
  `Banned` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `Karma` int(10) NOT NULL DEFAULT '0',
  `LastPk` datetime DEFAULT NULL,
  `LastPkForElf` datetime DEFAULT NULL,
  `DeleteTime` datetime DEFAULT NULL,
  `OriginalStr` int(3) NOT NULL DEFAULT '0',
  `OriginalCon` int(3) NOT NULL DEFAULT '0',
  `OriginalDex` int(3) NOT NULL DEFAULT '0',
  `OriginalCha` int(3) NOT NULL DEFAULT '0',
  `OriginalInt` int(3) NOT NULL DEFAULT '0',
  `OriginalWis` int(3) NOT NULL DEFAULT '0',
  `CreateTime` int(10) NOT NULL DEFAULT '20090901',
  PRIMARY KEY (`objid`),
  KEY `key_id` (`account_name`,`char_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of characters
-- ----------------------------
INSERT INTO `characters` VALUES ('1111', '300185', '效果', '1', '0', '0', '16', '1', '1', '1', '7', '16', '14', '16', '12', '8', '9', '0', '61', '0', '1', '6', '33079', '33392', '4', '40', '0', '', '0', '', '0', '0', '0', '0', '0', '0', '1', '0', '200', '0', '0', '0', '0', '0', '0', '0', null, null, null, '16', '14', '16', '12', '8', '9', '20190714');
INSERT INTO `characters` VALUES ('1111', '300477', '大法師', '61', '61', '452863174', '466', '398', '368', '68', '-9', '12', '13', '18', '8', '12', '12', '46', '734', '0', '3', '4', '33081', '33400', '4', '50', '-127', '', '0', '', '0', '0', '0', '0', '0', '0', '0', '0', '200', '0', '0', '0', '0', '0', '0', '0', null, null, null, '12', '13', '18', '8', '12', '12', '20190714');

-- ----------------------------
-- Table structure for clan_data
-- ----------------------------
DROP TABLE IF EXISTS `clan_data`;
CREATE TABLE `clan_data` (
  `clan_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `clan_name` varchar(45) NOT NULL DEFAULT '',
  `leader_id` int(10) unsigned NOT NULL DEFAULT '0',
  `leader_name` varchar(45) NOT NULL DEFAULT '',
  `hascastle` int(10) unsigned NOT NULL DEFAULT '0',
  `hashouse` int(10) unsigned NOT NULL DEFAULT '0',
  `clanskill` tinyint(1) NOT NULL DEFAULT '0',
  `skilltime` datetime DEFAULT NULL,
  PRIMARY KEY (`clan_id`),
  KEY `clan_id` (`clan_id`)
) ENGINE=MyISAM AUTO_INCREMENT=1497859423 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clan_data
-- ----------------------------

-- ----------------------------
-- Table structure for clan_emblem
-- ----------------------------
DROP TABLE IF EXISTS `clan_emblem`;
CREATE TABLE `clan_emblem` (
  `clan_id` int(10) NOT NULL DEFAULT '0',
  `emblemicon` blob NOT NULL,
  `update` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`clan_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clan_emblem
-- ----------------------------

-- ----------------------------
-- Table structure for clan_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `clan_warehouse`;
CREATE TABLE `clan_warehouse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clan_name` varchar(45) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `count` bigint(11) DEFAULT NULL,
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
  PRIMARY KEY (`id`),
  KEY `key_id` (`clan_name`)
) ENGINE=MyISAM AUTO_INCREMENT=1497865667 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clan_warehouse
-- ----------------------------

-- ----------------------------
-- Table structure for server_board
-- ----------------------------
DROP TABLE IF EXISTS `server_board`;
CREATE TABLE `server_board` (
  `id` int(10) NOT NULL DEFAULT '0',
  `name` varchar(16) DEFAULT NULL,
  `date` varchar(16) DEFAULT NULL,
  `title` varchar(16) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_board
-- ----------------------------

-- ----------------------------
-- Table structure for server_board_auction
-- ----------------------------
DROP TABLE IF EXISTS `server_board_auction`;
CREATE TABLE `server_board_auction` (
  `house_id` int(10) unsigned NOT NULL DEFAULT '0',
  `house_name` varchar(45) NOT NULL DEFAULT '',
  `house_area` int(10) unsigned NOT NULL DEFAULT '0',
  `deadline` datetime DEFAULT NULL,
  `price` int(10) unsigned NOT NULL DEFAULT '0',
  `location` varchar(45) NOT NULL DEFAULT '',
  `old_owner` varchar(45) NOT NULL DEFAULT '',
  `old_owner_id` int(10) unsigned NOT NULL DEFAULT '0',
  `bidder` varchar(45) NOT NULL DEFAULT '',
  `bidder_id` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`house_id`),
  KEY `house_id` (`house_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_board_auction
-- ----------------------------
INSERT INTO `server_board_auction` VALUES ('262145', '$1242 1$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  1', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262146', '$1242 2$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  2', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262147', '$1242 3$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  3', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262148', '$1242 4$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  4', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262149', '$1242 5$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  5', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262150', '$1242 6$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  6', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262151', '$1242 7$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  7', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262152', '$1242 8$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  8', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262153', '$1242 9$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  9', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262154', '$1242 10$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  10', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262155', '$1242 11$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  11', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262156', '$1242 12$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  12', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262157', '$1242 13$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  13', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262158', '$1242 14$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  14', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262159', '$1242 15$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  15', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262160', '$1242 16$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  16', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262161', '$1242 17$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  17', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262162', '$1242 18$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  18', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262163', '$1242 19$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  19', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262164', '$1242 20$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  20', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262165', '$1242 21$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  21', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262166', '$1242 22$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  22', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262167', '$1242 23$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  23', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262168', '$1242 24$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  24', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262169', '$1242 25$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  25', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262170', '$1242 26$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  26', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262171', '$1242 27$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  27', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262172', '$1242 28$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  28', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262173', '$1242 29$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  29', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262175', '$1242 31$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  31', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262176', '$1242 32$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  32', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262177', '$1242 33$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  33', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262178', '$1242 34$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  34', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262179', '$1242 35$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  35', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262180', '$1242 36$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  36', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262181', '$1242 37$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  37', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262182', '$1242 38$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  38', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262184', '$1242 40$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  40', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262185', '$1242 41$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  41', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262186', '$1242 42$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  42', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262187', '$1242 43$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  43', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262188', '$1242 44$1195', '120', '2019-08-06 20:00:00', '100000', '$1242  44', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262189', '$1242 45$1195', '78', '2019-08-06 20:00:00', '100000', '$1242  45', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327681', '$1513 1$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  1', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327682', '$1513 2$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  2', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327683', '$1513 3$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  3', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327684', '$1513 4$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  4', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327685', '$1513 5$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  5', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327686', '$1513 6$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  6', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327687', '$1513 7$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  7', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327688', '$1513 8$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  8', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327689', '$1513 9$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  9', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327690', '$1513 10$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  10', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('327691', '$1513 11$1195', '0', '2019-08-06 20:00:00', '100000', '$1513  11', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('524289', '$381 1$1195', '48', '2019-08-06 20:00:00', '100000', '$381  1', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('524290', '$381 2$1195', '71', '2019-08-06 20:00:00', '100000', '$381  2', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('524291', '$381 3$1195', '48', '2019-08-06 20:00:00', '100000', '$381  3', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('524292', '$381 4$1195', '48', '2019-08-06 20:00:00', '100000', '$381  4', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('524293', '$381 5$1195', '82', '2019-08-06 20:00:00', '100000', '$381  5', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('524294', '$381 6$1195', '40', '2019-08-06 20:00:00', '100000', '$381  6', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262174', '$1242 29$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  30', '', '0', '', '0');
INSERT INTO `server_board_auction` VALUES ('262183', '$1242 39$1195', '45', '2019-08-06 20:00:00', '100000', '$1242  39', '', '0', '', '0');

-- ----------------------------
-- Table structure for server_boss
-- ----------------------------
DROP TABLE IF EXISTS `server_boss`;
CREATE TABLE `server_boss` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `location` varchar(45) NOT NULL DEFAULT '',
  `count` int(10) unsigned NOT NULL DEFAULT '0',
  `npc_templateid` int(10) unsigned NOT NULL DEFAULT '0',
  `group_id` int(10) unsigned NOT NULL DEFAULT '0',
  `locx1` int(10) unsigned NOT NULL DEFAULT '0',
  `locy1` int(10) unsigned NOT NULL DEFAULT '0',
  `locx2` int(10) unsigned NOT NULL DEFAULT '0',
  `locy2` int(10) unsigned NOT NULL DEFAULT '0',
  `heading` int(10) unsigned NOT NULL DEFAULT '0',
  `mapid` int(10) unsigned NOT NULL DEFAULT '0',
  `next_spawn_time` datetime DEFAULT NULL,
  `spawn_interval` int(10) DEFAULT '60000',
  `exist_time` int(10) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=201002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_boss
-- ----------------------------
INSERT INTO `server_boss` VALUES ('20000', '巨大鱷魚', '1', '45338', '0', '33485', '33185', '33522', '33227', '5', '4', '2011-11-26 00:34:54', '60', '0');
INSERT INTO `server_boss` VALUES ('20001', '古代巨人', '1', '45610', '0', '34238', '33383', '34254', '33406', '5', '4', '2011-11-26 13:05:14', '15', '0');
INSERT INTO `server_boss` VALUES ('20002', '飛龍', '1', '45529', '17', '33398', '32403', '33410', '32415', '5', '4', '2011-11-26 00:56:10', '15', '0');
INSERT INTO `server_boss` VALUES ('20003', '飛龍', '1', '45529', '17', '33339', '32348', '33354', '32363', '5', '4', '2011-11-26 13:10:13', '15', '0');
INSERT INTO `server_boss` VALUES ('20004', '飛龍', '1', '45529', '0', '33382', '32330', '33397', '32345', '5', '4', '2011-11-26 13:14:21', '15', '0');
INSERT INTO `server_boss` VALUES ('20005', '飛龍', '1', '45529', '0', '33356', '32375', '33371', '32390', '5', '4', '2011-11-26 13:13:11', '15', '0');
INSERT INTO `server_boss` VALUES ('20006', '黑長者', '1', '45545', '16', '33271', '32349', '33385', '32394', '5', '4', '2011-11-25 18:20:27', '15', '0');
INSERT INTO `server_boss` VALUES ('20007', '不死鳥', '1', '45617', '0', '33718', '32240', '33725', '32268', '5', '4', '2011-11-26 13:03:25', '15', '0');
INSERT INTO `server_boss` VALUES ('20008', '林德拜爾', '1', '45681', '0', '34041', '33007', '0', '0', '0', '4', '2011-11-18 11:31:27', '60', '0');
INSERT INTO `server_boss` VALUES ('20009', '突擊旅長．闇黑劍士', '1', '45577', '0', '32783', '32837', '0', '0', '5', '452', '2011-11-23 13:10:47', '120', '0');
INSERT INTO `server_boss` VALUES ('20010', '巴蘭卡', '1', '45844', '0', '32838', '32758', '0', '0', '5', '453', '2011-11-26 04:35:05', '15', '0');
INSERT INTO `server_boss` VALUES ('20011', '魔獸團長．凱巴勒', '1', '45607', '0', '32758', '32823', '0', '0', '5', '455', '2011-11-26 11:53:46', '120', '0');
INSERT INTO `server_boss` VALUES ('20012', '魔獸師長．辛克萊', '1', '45588', '0', '32745', '32823', '0', '0', '5', '456', '2011-11-26 10:39:54', '120', '0');
INSERT INTO `server_boss` VALUES ('20013', '魔法團長．卡勒米爾', '1', '45602', '0', '32842', '32821', '0', '0', '5', '461', '2011-06-21 01:23:17', '120', '0');
INSERT INTO `server_boss` VALUES ('20014', '法令軍王．蕾雅', '1', '45863', '0', '32805', '32839', '0', '0', '5', '462', '2011-11-26 01:57:03', '25', '0');
INSERT INTO `server_boss` VALUES ('20015', '神官長．邦妮', '1', '45612', '0', '32770', '32829', '0', '0', '5', '466', '2011-11-18 20:39:33', '120', '0');
INSERT INTO `server_boss` VALUES ('20016', '傭兵隊長．麥帕斯托', '1', '45608', '0', '32791', '32815', '0', '0', '5', '471', '2011-07-06 03:07:16', '120', '0');
INSERT INTO `server_boss` VALUES ('20017', '冥法團長．可利波斯', '1', '45615', '0', '32922', '32846', '0', '0', '5', '473', '2011-06-24 10:14:09', '120', '0');
INSERT INTO `server_boss` VALUES ('20018', '冥法軍王．海露拜', '1', '45676', '0', '32785', '32844', '0', '0', '5', '475', '2011-11-26 05:11:29', '25', '0');
INSERT INTO `server_boss` VALUES ('20019', '暗殺軍王．史雷佛', '1', '45648', '0', '32853', '32863', '0', '0', '5', '492', '2011-11-22 23:36:41', '15', '0');
INSERT INTO `server_boss` VALUES ('20020', '親衛隊長．凱特', '1', '45574', '0', '32831', '32762', '0', '0', '5', '494', '2011-11-17 23:59:18', '120', '0');
INSERT INTO `server_boss` VALUES ('20021', '暗殺團長．佈雷哲', '1', '45585', '0', '32785', '32837', '0', '0', '5', '495', '2011-08-26 11:49:46', '120', '0');
INSERT INTO `server_boss` VALUES ('20022', '混沌', '1', '45625', '0', '32734', '32895', '0', '0', '5', '522', '2011-11-26 00:56:57', '15', '0');
INSERT INTO `server_boss` VALUES ('20023', '死亡', '1', '45674', '0', '32746', '32897', '0', '0', '5', '523', '2011-11-26 01:11:20', '25', '0');
INSERT INTO `server_boss` VALUES ('20024', '火焰之影', '1', '45675', '0', '32733', '32894', '0', '0', '5', '524', '2011-11-18 15:46:11', '25', '0');
INSERT INTO `server_boss` VALUES ('20025', '長老．琪娜', '1', '45955', '0', '32862', '32840', '0', '0', '5', '530', '2011-11-18 02:14:07', '45', '0');
INSERT INTO `server_boss` VALUES ('20026', '長老．巴塔斯', '1', '45956', '0', '32757', '32744', '0', '0', '5', '531', '2011-11-26 02:42:13', '25', '0');
INSERT INTO `server_boss` VALUES ('20027', '長老．巴塔斯', '1', '45957', '0', '32791', '32786', '0', '0', '5', '531', '2011-11-22 19:26:06', '25', '0');
INSERT INTO `server_boss` VALUES ('20028', '長老．安迪斯', '1', '45958', '0', '32845', '32857', '0', '0', '5', '531', '2011-11-26 02:42:07', '25', '0');
INSERT INTO `server_boss` VALUES ('20029', '長老．艾迪爾', '1', '45959', '0', '32789', '32812', '0', '0', '5', '532', '2011-11-26 07:16:10', '15', '0');
INSERT INTO `server_boss` VALUES ('20030', '長老．泰瑪斯', '1', '45960', '0', '32859', '32897', '0', '0', '5', '533', '2011-11-26 02:35:18', '25', '0');
INSERT INTO `server_boss` VALUES ('20031', '長老．拉曼斯', '1', '45961', '0', '32789', '32891', '0', '0', '5', '533', '2011-11-26 02:37:26', '25', '0');
INSERT INTO `server_boss` VALUES ('20032', '長老．巴陸德', '1', '45962', '0', '32753', '32811', '0', '0', '5', '533', '2011-11-26 02:41:02', '25', '0');
INSERT INTO `server_boss` VALUES ('20033', '副神官．卡山德拉', '1', '45963', '0', '32858', '32821', '0', '0', '5', '534', '2011-11-25 04:28:05', '25', '0');
INSERT INTO `server_boss` VALUES ('20034', '吉爾塔斯', '1', '81163', '0', '32868', '32862', '0', '0', '0', '535', '2011-11-26 10:06:43', '60', '0');
INSERT INTO `server_boss` VALUES ('20035', '巨蟻女皇', '1', '45614', '0', '32742', '32856', '0', '0', '4', '543', '2011-11-26 13:07:27', '15', '0');
INSERT INTO `server_boss` VALUES ('20036', '底比斯 賀洛斯', '1', '46123', '0', '32782', '32836', '0', '0', '2', '782', '2011-11-21 16:35:08', '25', '0');
INSERT INTO `server_boss` VALUES ('20037', '底比斯 阿努比斯', '1', '46124', '0', '32782', '32827', '0', '0', '2', '782', '2011-11-21 16:31:35', '25', '0');
INSERT INTO `server_boss` VALUES ('20038', '死亡騎士', '1', '45601', '0', '32734', '32724', '32767', '32812', '5', '11', '2011-11-26 13:20:05', '15', '0');
INSERT INTO `server_boss` VALUES ('20039', '巴列斯', '1', '45583', '0', '32758', '32758', '32777', '32777', '5', '24', '2011-11-26 13:08:18', '15', '0');
INSERT INTO `server_boss` VALUES ('20040', '安塔瑞斯', '1', '45682', '0', '32697', '32823', '0', '0', '0', '37', '2011-11-17 02:17:20', '60', '0');
INSERT INTO `server_boss` VALUES ('20041', '水之精靈', '1', '45931', '0', '32611', '32688', '32702', '32912', '5', '63', '2011-11-26 00:08:56', '180', '0');
INSERT INTO `server_boss` VALUES ('20042', '受詛咒的 水精靈王', '1', '45942', '0', '32665', '32805', '32696', '32873', '5', '61', '2011-11-25 12:04:04', '180', '0');
INSERT INTO `server_boss` VALUES ('20043', '受詛咒的巫女莎爾', '1', '45941', '0', '32611', '32688', '32702', '32912', '5', '63', '2011-05-15 18:58:53', '180', '0');
INSERT INTO `server_boss` VALUES ('20044', '卡普', '1', '45943', '0', '32611', '32688', '32702', '32912', '5', '63', '2011-11-26 00:27:38', '180', '0');
INSERT INTO `server_boss` VALUES ('20045', '巨大蜈蚣', '1', '45944', '0', '32611', '32688', '32702', '32912', '5', '63', '2011-11-26 00:37:56', '180', '0');
INSERT INTO `server_boss` VALUES ('20046', '法利昂', '1', '45683', '0', '32771', '32831', '0', '0', '0', '65', '2011-11-13 21:32:30', '60', '0');
INSERT INTO `server_boss` VALUES ('20047', '巴拉卡斯', '1', '45684', '0', '32725', '32800', '0', '0', '0', '67', '2011-11-24 15:08:54', '60', '0');
INSERT INTO `server_boss` VALUES ('20048', '飛龍', '1', '45578', '0', '32650', '32853', '32730', '32920', '5', '70', '2011-11-25 23:57:46', '25', '0');
INSERT INTO `server_boss` VALUES ('20049', '飛龍', '1', '45578', '0', '32687', '32938', '32758', '32965', '5', '70', '2011-11-26 00:01:40', '25', '0');
INSERT INTO `server_boss` VALUES ('20050', '飛龍', '1', '45578', '0', '32758', '32965', '32821', '33000', '5', '70', '2011-11-25 22:06:02', '25', '0');
INSERT INTO `server_boss` VALUES ('20051', '飛龍', '1', '45578', '0', '32797', '32711', '32826', '32769', '5', '70', '2011-11-26 03:34:12', '25', '0');
INSERT INTO `server_boss` VALUES ('20052', '飛龍', '1', '45578', '0', '32636', '32702', '32705', '32790', '5', '70', '2011-11-25 17:50:43', '25', '0');
INSERT INTO `server_boss` VALUES ('20053', '巨大牛人', '1', '45584', '0', '32640', '32713', '32676', '32761', '5', '70', '2011-11-26 01:14:02', '15', '0');
INSERT INTO `server_boss` VALUES ('20054', '冰人惡魔', '1', '46142', '0', '32753', '32776', '0', '0', '5', '73', '2011-11-25 08:43:29', '60', '0');
INSERT INTO `server_boss` VALUES ('20055', '冰之女王', '1', '45609', '0', '32767', '32900', '0', '0', '5', '74', '2011-11-26 01:08:03', '15', '0');
INSERT INTO `server_boss` VALUES ('20056', '冰之女王', '1', '46141', '0', '32842', '32921', '0', '0', '5', '74', '2011-11-26 05:14:57', '360', '0');
INSERT INTO `server_boss` VALUES ('20057', '惡魔', '1', '45649', '0', '32668', '32793', '32735', '32868', '5', '82', '2011-11-26 12:56:51', '15', '0');
INSERT INTO `server_boss` VALUES ('20058', '潔尼斯女王', '1', '45513', '0', '32735', '32746', '32827', '32866', '6', '110', '2011-11-26 08:28:53', '15', '0');
INSERT INTO `server_boss` VALUES ('20059', '幻象眼魔', '1', '45547', '0', '32735', '32746', '32827', '32866', '5', '120', '2011-11-20 09:57:48', '15', '0');
INSERT INTO `server_boss` VALUES ('20060', '吸血鬼', '1', '45606', '0', '32735', '32746', '32827', '32866', '6', '130', '2011-11-26 02:27:36', '15', '0');
INSERT INTO `server_boss` VALUES ('20061', '殭屍王', '1', '45650', '0', '32735', '32746', '32827', '32866', '5', '140', '2011-11-25 01:20:28', '15', '0');
INSERT INTO `server_boss` VALUES ('20062', '木乃伊王', '1', '45653', '0', '32735', '32746', '32827', '32866', '3', '160', '2011-11-25 19:31:51', '15', '0');
INSERT INTO `server_boss` VALUES ('20063', '艾莉絲', '1', '45654', '0', '32735', '32746', '32827', '32866', '5', '170', '2011-11-26 06:11:10', '15', '0');
INSERT INTO `server_boss` VALUES ('20064', '騎士范德', '1', '45618', '0', '32576', '32768', '32767', '32959', '5', '180', '2011-11-14 20:35:34', '25', '0');
INSERT INTO `server_boss` VALUES ('20065', '巫妖', '1', '45672', '0', '32735', '32746', '32827', '32866', '5', '190', '2011-11-25 20:31:35', '25', '0');
INSERT INTO `server_boss` VALUES ('20066', '鐮刀死神', '1', '45673', '0', '32603', '32791', '32745', '32993', '6', '200', '2011-11-21 14:32:17', '25', '0');
INSERT INTO `server_boss` VALUES ('20071', '阿利歐克', '1', '45671', '0', '32678', '32937', '32692', '32948', '5', '243', '2011-11-25 00:07:17', '25', '0');
INSERT INTO `server_boss` VALUES ('20073', '樹精', '1', '45795', '0', '32841', '32913', '32880', '32947', '5', '244', '2011-11-05 02:20:50', '25', '0');
INSERT INTO `server_boss` VALUES ('20074', '塔洛斯伯爵', '1', '46025', '0', '32809', '32796', '0', '0', '5', '251', '2011-11-17 23:40:32', '30', '0');
INSERT INTO `server_boss` VALUES ('20075', '伯爵的親衛隊', '1', '46024', '51', '32737', '32827', '0', '0', '5', '250', '2011-11-04 21:14:39', '30', '0');
INSERT INTO `server_boss` VALUES ('20076', '曼孟', '1', '46026', '0', '32797', '32790', '0', '0', '5', '251', '2011-11-17 11:02:21', '30', '0');
INSERT INTO `server_boss` VALUES ('20077', '變形怪首領', '1', '45828', '0', '32597', '32854', '32604', '32875', '5', '252', '2011-11-17 09:27:21', '30', '0');
INSERT INTO `server_boss` VALUES ('20078', '巴貝多', '1', '45829', '0', '32654', '32838', '32679', '32884', '5', '254', '2011-11-26 02:49:24', '25', '0');
INSERT INTO `server_boss` VALUES ('20079', '瑪依奴夏門的鑽石高侖', '1', '45801', '0', '32778', '32797', '0', '0', '5', '255', '2011-11-26 12:41:59', '15', '0');
INSERT INTO `server_boss` VALUES ('20080', '瑪依奴夏門', '1', '45802', '0', '32696', '32799', '32699', '32911', '5', '256', '2011-11-23 15:39:42', '25', '0');
INSERT INTO `server_boss` VALUES ('20081', '瑪雅', '1', '46037', '0', '32796', '33072', '0', '0', '6', '258', '2011-10-28 10:04:16', '25', '0');
INSERT INTO `server_boss` VALUES ('20082', '土精靈王', '1', '45642', '0', '32692', '32713', '32767', '32747', '4', '303', '2011-11-26 09:26:43', '60', '0');
INSERT INTO `server_boss` VALUES ('20083', '水精靈王', '1', '45643', '0', '32715', '32623', '32727', '32659', '4', '303', '2011-11-26 02:13:14', '60', '0');
INSERT INTO `server_boss` VALUES ('20084', '風精靈王', '1', '45644', '0', '32618', '32733', '32623', '32795', '4', '303', '2011-11-25 18:52:01', '60', '0');
INSERT INTO `server_boss` VALUES ('20085', '火精靈王', '1', '45645', '0', '32793', '32706', '32804', '32734', '4', '303', '2011-11-26 02:06:29', '60', '0');
INSERT INTO `server_boss` VALUES ('20086', '巴風特', '1', '45573', '0', '32704', '32842', '32711', '32849', '5', '2', '2011-11-26 13:09:12', '15', '0');
INSERT INTO `server_boss` VALUES ('20093', '墮落', '1', '45685', '0', '32904', '32801', '0', '0', '5', '410', '2011-11-26 01:16:12', '25', '0');
INSERT INTO `server_boss` VALUES ('20094', '深淵之主', '1', '45646', '22', '32752', '32830', '32777', '32862', '5', '430', '2011-11-26 02:25:46', '180', '0');
INSERT INTO `server_boss` VALUES ('20095', '庫曼', '1', '45492', '49', '32671', '32810', '32688', '32911', '5', '480', '2011-11-26 01:39:54', '10', '0');
INSERT INTO `server_boss` VALUES ('20096', '豪勢', '1', '45548', '0', '32733', '32793', '32772', '32850', '5', '484', '2011-02-13 10:33:07', '90', '0');
INSERT INTO `server_boss` VALUES ('20097', '拉斯塔巴德近衛隊隊長', '1', '46013', '0', '32733', '32803', '32768', '32853', '5', '530', '2011-11-22 17:18:03', '90', '0');
INSERT INTO `server_boss` VALUES ('20098', '半魚人首領', '1', '45735', '0', '33160', '33213', '33173', '33250', '5', '558', '2011-08-05 06:01:32', '90', '0');
INSERT INTO `server_boss` VALUES ('20099', '大王烏賊', '1', '45734', '0', '33160', '33213', '33173', '33250', '5', '558', '2011-08-05 06:01:17', '90', '0');
INSERT INTO `server_boss` VALUES ('20100', '炎魔', '1', '45752', '0', '32726', '32832', '0', '0', '5', '603', '2011-11-04 11:25:31', '25', '0');
INSERT INTO `server_boss` VALUES ('20101', '獨角獸', '1', '91123', '0', '32719', '32717', '32752', '32753', '4', '303', '2011-11-26 11:54:18', '15', '0');
INSERT INTO `server_boss` VALUES ('20102', '杰弗雷庫(雌)', '1', '92000', '0', '32750', '32860', '0', '0', '0', '784', '2011-11-26 01:58:17', '90', '0');
INSERT INTO `server_boss` VALUES ('20103', '杰弗雷庫(雄)', '1', '92001', '0', '32751', '32871', '0', '0', '0', '784', '2011-11-26 01:58:17', '90', '0');
INSERT INTO `server_boss` VALUES ('20104', '克特', '1', '45600', '13', '32633', '32961', '0', '0', '5', '0', '2011-11-26 12:29:45', '15', '0');
INSERT INTO `server_boss` VALUES ('20106', '卡士伯', '1', '45488', '14', '32734', '32724', '32775', '32813', '5', '9', '2011-11-26 13:20:56', '20', '0');
INSERT INTO `server_boss` VALUES ('20107', '魔法師', '1', '45478', '7', '32734', '32724', '32767', '32812', '5', '11', '2011-11-26 14:01:43', '60', '0');
INSERT INTO `server_boss` VALUES ('20109', '飛龍', '1', '45578', '0', '32650', '32853', '32730', '32920', '5', '70', '2011-11-26 03:56:27', '30', '0');
INSERT INTO `server_boss` VALUES ('20110', '飛龍', '1', '45578', '0', '32687', '32938', '32758', '32965', '5', '70', '2011-11-26 03:53:47', '30', '0');
INSERT INTO `server_boss` VALUES ('20111', '飛龍', '1', '45578', '0', '32758', '32965', '32821', '33000', '5', '70', '2011-11-26 03:51:49', '30', '0');
INSERT INTO `server_boss` VALUES ('20112', '飛龍', '1', '45578', '0', '32797', '32711', '32826', '32769', '5', '70', '2011-11-26 03:40:16', '30', '0');
INSERT INTO `server_boss` VALUES ('20113', '飛龍', '1', '45578', '0', '32636', '32702', '32705', '32790', '5', '70', '2011-11-26 01:26:48', '30', '0');
INSERT INTO `server_boss` VALUES ('20114', '飛龍', '1', '45578', '0', '32650', '32853', '32730', '32920', '5', '70', '2011-11-26 03:42:51', '15', '0');
INSERT INTO `server_boss` VALUES ('20115', '飛龍', '1', '45578', '0', '32687', '32938', '32758', '32965', '5', '70', '2011-11-26 03:39:13', '15', '0');
INSERT INTO `server_boss` VALUES ('20116', '飛龍', '1', '45578', '0', '32758', '32965', '32821', '33000', '5', '70', '2011-11-26 03:36:50', '15', '0');
INSERT INTO `server_boss` VALUES ('20117', '飛龍', '1', '45578', '0', '32797', '32711', '32826', '32769', '5', '70', '2011-11-26 03:44:14', '15', '0');
INSERT INTO `server_boss` VALUES ('20118', '飛龍', '1', '45578', '0', '32636', '32702', '32705', '32790', '5', '70', '2011-11-26 01:12:45', '15', '0');
INSERT INTO `server_boss` VALUES ('20119', '伊弗利特', '1', '45516', '0', '33529', '32217', '33765', '32370', '5', '4', '2011-11-26 00:36:11', '60', '0');
INSERT INTO `server_boss` VALUES ('20120', '伊弗利特', '0', '45516', '0', '33535', '32202', '33765', '32370', '5', '4', '2009-12-10 01:57:42', '55', '0');
INSERT INTO `server_boss` VALUES ('20126', '變形怪首領', '1', '45546', '0', '33709', '33307', '0', '0', '5', '4', '2011-11-26 13:06:14', '15', '0');
INSERT INTO `server_boss` VALUES ('20125', '古代巨人', '1', '45610', '0', '34230', '33369', '0', '0', '5', '4', '2011-11-25 17:50:22', '15', '0');
INSERT INTO `server_boss` VALUES ('20124', '黑豹', '1', '45652', '0', '32722', '32722', '32877', '32877', '5', '150', '2011-11-26 06:32:10', '15', '0');
INSERT INTO `server_boss` VALUES ('20122', '曼波兔2', '1', '45535', '0', '32723', '32787', '32875', '32892', '5', '430', '2011-11-23 00:22:58', '15', '0');
INSERT INTO `server_boss` VALUES ('20121', '曼波兔1', '1', '45534', '0', '32581', '32993', '32654', '33044', '5', '440', '2011-11-26 01:48:29', '15', '0');
INSERT INTO `server_boss` VALUES ('20141', '安塔瑞斯', '1', '71014', '0', '32686', '32849', '0', '0', '0', '202', '2010-12-11 18:05:26', '120', '0');
INSERT INTO `server_boss` VALUES ('25005', '魔界的不死鳥(複本用BOSS加入)', '0', '91293', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');
INSERT INTO `server_boss` VALUES ('25004', '魔界的巨大牛人(複本用BOSS加入)', '0', '91292', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');
INSERT INTO `server_boss` VALUES ('25003', '魔界的幻象眼魔(複本用BOSS加入)', '0', '91289', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');
INSERT INTO `server_boss` VALUES ('25002', '魔界的伊弗利特(複本用BOSS加入)', '0', '91285', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');
INSERT INTO `server_boss` VALUES ('25001', '新法利昂(複本用BOSS加入)', '0', '71026', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');
INSERT INTO `server_boss` VALUES ('25000', '新安塔瑞斯(複本用BOSS加入)', '0', '71014', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');
INSERT INTO `server_boss` VALUES ('25006', '巴風特(複本用BOSS加入)', '0', '91294', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');
INSERT INTO `server_boss` VALUES ('25007', '黑翼賽尼斯(複本用BOSS加入)', '0', '91295', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');
INSERT INTO `server_boss` VALUES ('25008', '賽尼斯(複本用BOSS加入)', '0', '91296', '0', '0', '0', '0', '0', '0', '0', null, '60000', '0');

-- ----------------------------
-- Table structure for server_castle
-- ----------------------------
DROP TABLE IF EXISTS `server_castle`;
CREATE TABLE `server_castle` (
  `castle_id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `war_time` datetime DEFAULT NULL,
  `tax_rate` int(11) NOT NULL DEFAULT '0',
  `public_money` bigint(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`castle_id`),
  KEY `castle_id` (`castle_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_castle
-- ----------------------------
INSERT INTO `server_castle` VALUES ('1', '肯特城', '2008-09-15 00:00:00', '0', '0');
INSERT INTO `server_castle` VALUES ('2', '妖魔城', '2008-09-15 00:00:00', '0', '0');
INSERT INTO `server_castle` VALUES ('3', '風木城', '2008-09-15 00:00:00', '0', '0');
INSERT INTO `server_castle` VALUES ('4', '奇巖城', '2008-09-15 00:00:00', '0', '0');
INSERT INTO `server_castle` VALUES ('5', '海音城', '2008-09-15 00:00:00', '0', '0');
INSERT INTO `server_castle` VALUES ('6', '侏儒城', '2008-09-15 00:00:00', '0', '0');
INSERT INTO `server_castle` VALUES ('7', '亞丁城', '2008-09-15 00:00:00', '0', '0');
INSERT INTO `server_castle` VALUES ('8', '狄亞得要塞', '2008-09-15 00:00:00', '0', '1281');

-- ----------------------------
-- Table structure for server_event
-- ----------------------------
DROP TABLE IF EXISTS `server_event`;
CREATE TABLE `server_event` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `eventname` varchar(45) NOT NULL DEFAULT '0',
  `eventclass` varchar(45) NOT NULL DEFAULT '0',
  `eventstart` tinyint(1) NOT NULL DEFAULT '0',
  `eventother` varchar(512) NOT NULL DEFAULT '0',
  `note` varchar(255) DEFAULT '說明:',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_event
-- ----------------------------
INSERT INTO `server_event` VALUES ('1', '奇岩賭場系統', 'GamblingSet', '0', '60,1,44070', '說明:每場比賽間隔時間(分鐘),奇岩賭場 下注額,下注物品');
INSERT INTO `server_event` VALUES ('53', '回憶蠟燭系統', 'BaseResetSet', '1', '30', '說明:回憶蠟燭系統 使用後HP/MP保留百分比(1/100)');
INSERT INTO `server_event` VALUES ('3', '無線大賽系統', 'UbSet', '0', '0', '說明:無');
INSERT INTO `server_event` VALUES ('4', '傳送師', 'SpawnOtherSet', '1', '0', '說明:用來召喚傳送師');
INSERT INTO `server_event` VALUES ('6', '新手支援npc系統', 'SpawnOtherSet', '1', '0', '說明:無');
INSERT INTO `server_event` VALUES ('8', '亞丁商團系統', 'SpawnOtherSet', '1', '0', '說明:無');
INSERT INTO `server_event` VALUES ('9', '血盟技能系統', 'ClanSkillSet', '1', '0', '說明:武器幻化(光環)開關 0:關 1:開');
INSERT INTO `server_event` VALUES ('10', '全職技能導師系統', 'SkillTeacherSet', '1', '44,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,87,91,191,218,132,146,164,153,173,175,174,157,134,105,102', '說明:不開放學習的技能編號 使用\",\"分隔');
INSERT INTO `server_event` VALUES ('16', '服務器輔助說明', 'NewServerSet', '1', '10', '說明:無');
INSERT INTO `server_event` VALUES ('22', '風雲榜系統', 'Ranking4', '1', '0', '說明:無');
INSERT INTO `server_event` VALUES ('31', '拍賣管理者', 'ShopXSet', '1', '10,3,50,50000', '說明:手續費,寄售時間(天),最低出售價,最高出售價');
INSERT INTO `server_event` VALUES ('47', '任務/副本系統', 'QuestSet', '1', '0', '說明:');
INSERT INTO `server_event` VALUES ('11', '連線獎勵系統', 'OnlineGiftSet', '1', '20, 3#44070#100, 1#44070#150', '說明:連線獎勵系統');
INSERT INTO `server_event` VALUES ('52', '銀行管理員系統', 'BankSet', '1', '500000000,600000000,40308,5,0.05', '說明:銀行儲蓄上限,銀行存款上限(加入利息),發放利息時間 (單位:分),銀行利率(%)');
INSERT INTO `server_event` VALUES ('40', '天上聖母系統', 'MazuSet', '1', '0', '說明:啟動天上聖母系統');
INSERT INTO `server_event` VALUES ('39', '凹槽系統', 'PowerItemSet', '1', '10,2,3', '說明:強化成功機率(1/1000),防具最大凹槽數量,武器最大凹槽數量(最大凹槽數量不能超過5)');
INSERT INTO `server_event` VALUES ('44', '特殊屬性', 'FeatureItemSet', '1', 'true', '說明:啟動特殊屬性攻擊');
INSERT INTO `server_event` VALUES ('37', '升級裝備系統', 'ItemUpdateSet', '1', 'true', '說明:是否提供原始裝備附加屬性保留 true:是 false:否(不提供可堆疊物件交換)');

-- ----------------------------
-- Table structure for server_house
-- ----------------------------
DROP TABLE IF EXISTS `server_house`;
CREATE TABLE `server_house` (
  `house_id` int(10) unsigned NOT NULL DEFAULT '0',
  `house_name` varchar(45) NOT NULL DEFAULT '',
  `house_area` int(10) unsigned NOT NULL DEFAULT '0',
  `location` varchar(45) NOT NULL DEFAULT '',
  `keeper_id` int(10) unsigned NOT NULL DEFAULT '0',
  `is_on_sale` int(10) unsigned NOT NULL DEFAULT '0',
  `is_purchase_basement` int(10) unsigned NOT NULL DEFAULT '0',
  `tax_deadline` datetime NOT NULL,
  `locx1` int(10) NOT NULL DEFAULT '0' COMMENT '座標x1',
  `locx2` int(10) NOT NULL DEFAULT '0' COMMENT '座標x2',
  `locy1` int(10) NOT NULL DEFAULT '0' COMMENT '座標y1',
  `locy2` int(10) NOT NULL DEFAULT '0' COMMENT '座標y2',
  `locx3` int(10) NOT NULL DEFAULT '0' COMMENT '座標x3(用於不規則型小屋第2座標)',
  `locx4` int(10) NOT NULL DEFAULT '0' COMMENT '座標x4(用於不規則型小屋第2座標)',
  `locy3` int(10) NOT NULL DEFAULT '0' COMMENT '座標y3(用於不規則型小屋第2座標)',
  `locy4` int(10) NOT NULL DEFAULT '0' COMMENT '座標y4(用於不規則型小屋第2座標)',
  `mapid` int(10) NOT NULL DEFAULT '0' COMMENT '小屋地圖編號',
  `basement` int(10) NOT NULL DEFAULT '0' COMMENT '地下盟屋地圖編號',
  `homelocx` int(10) NOT NULL,
  `homelocy` int(10) NOT NULL,
  PRIMARY KEY (`house_id`),
  KEY `house_id` (`house_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_house
-- ----------------------------
INSERT INTO `server_house` VALUES ('262145', '奇巖血盟小屋1', '78', '1', '50527', '1', '0', '2010-09-01 00:00:00', '33368', '33375', '32651', '32654', '33373', '33375', '32655', '32657', '4', '5068', '33374', '32657');
INSERT INTO `server_house` VALUES ('262146', '奇巖血盟小屋2', '45', '2', '50505', '1', '0', '2010-09-01 00:00:00', '33381', '33387', '32653', '32656', '0', '0', '0', '0', '4', '5069', '33384', '32655');
INSERT INTO `server_house` VALUES ('262147', '奇巖血盟小屋3', '120', '3', '50519', '1', '0', '2010-09-01 00:00:00', '33392', '33404', '32650', '32656', '0', '0', '0', '0', '4', '5070', '33395', '32656');
INSERT INTO `server_house` VALUES ('262148', '奇巖血盟小屋4', '45', '4', '50545', '1', '0', '2010-09-01 00:00:00', '33427', '33430', '32656', '32662', '0', '0', '0', '0', '4', '5071', '33428', '32659');
INSERT INTO `server_house` VALUES ('262149', '奇巖血盟小屋5', '78', '5', '50531', '1', '0', '2010-09-01 00:00:00', '33439', '33445', '32665', '32667', '33442', '33445', '32668', '32672', '4', '5072', '33439', '32666');
INSERT INTO `server_house` VALUES ('262150', '奇巖血盟小屋6', '120', '6', '50529', '1', '0', '2010-09-01 00:00:00', '33454', '33466', '32648', '32654', '0', '0', '0', '0', '4', '5073', '33457', '32654');
INSERT INTO `server_house` VALUES ('262151', '奇巖血盟小屋7', '45', '7', '50516', '1', '0', '2010-09-01 00:00:00', '33476', '33479', '32665', '32671', '0', '0', '0', '0', '4', '5074', '33477', '32668');
INSERT INTO `server_house` VALUES ('262152', '奇巖血盟小屋8', '78', '8', '50538', '1', '0', '2010-09-01 00:00:00', '33471', '33477', '32678', '32680', '33474', '33477', '32681', '32685', '4', '5075', '33471', '32679');
INSERT INTO `server_house` VALUES ('262153', '奇巖血盟小屋9', '78', '9', '50518', '1', '0', '2010-09-01 00:00:00', '33453', '33460', '32694', '32697', '33458', '33460', '32698', '32700', '4', '5076', '33459', '32700');
INSERT INTO `server_house` VALUES ('262154', '奇巖血盟小屋10', '120', '10', '50509', '1', '0', '2010-09-01 00:00:00', '33421', '33433', '32685', '32691', '0', '0', '0', '0', '4', '5077', '33424', '32691');
INSERT INTO `server_house` VALUES ('262155', '奇巖血盟小屋11', '78', '11', '50536', '1', '0', '2010-09-01 00:00:00', '33409', '33415', '32674', '32676', '33412', '33415', '32677', '32681', '4', '5078', '33409', '32675');
INSERT INTO `server_house` VALUES ('262156', '奇巖血盟小屋12', '78', '12', '50520', '1', '0', '2010-09-01 00:00:00', '33414', '33421', '32703', '32706', '33419', '33421', '32707', '32709', '4', '5079', '33420', '32709');
INSERT INTO `server_house` VALUES ('262157', '奇巖血盟小屋13', '120', '13', '50543', '1', '0', '2010-09-01 00:00:00', '33372', '33384', '32692', '32698', '0', '0', '0', '0', '4', '5080', '33375', '32698');
INSERT INTO `server_house` VALUES ('262158', '奇巖血盟小屋14', '78', '14', '50526', '1', '0', '2010-09-01 00:00:00', '33362', '33365', '32681', '32687', '0', '0', '0', '0', '4', '5081', '33363', '32684');
INSERT INTO `server_house` VALUES ('262159', '奇巖血盟小屋15', '45', '15', '50512', '1', '0', '2010-09-01 00:00:00', '33360', '33366', '32669', '32671', '33363', '33366', '32672', '32676', '4', '5082', '33360', '32670');
INSERT INTO `server_house` VALUES ('262160', '奇巖血盟小屋16', '78', '16', '50510', '1', '0', '2010-09-01 00:00:00', '33341', '33347', '32660', '32662', '33344', '33347', '32663', '32667', '4', '5083', '33341', '32661');
INSERT INTO `server_house` VALUES ('262161', '奇巖血盟小屋17', '45', '17', '50504', '1', '0', '2010-09-01 00:00:00', '33345', '33348', '32672', '32678', '0', '0', '0', '0', '4', '5084', '33346', '32675');
INSERT INTO `server_house` VALUES ('262162', '奇巖血盟小屋18', '120', '18', '50525', '1', '0', '2010-09-01 00:00:00', '33338', '33350', '32704', '32711', '0', '0', '0', '0', '4', '5085', '33341', '32710');
INSERT INTO `server_house` VALUES ('262163', '奇巖血盟小屋19', '78', '19', '50534', '1', '0', '2010-09-01 00:00:00', '33349', '33356', '32728', '32731', '33354', '33356', '32732', '32734', '4', '5086', '33355', '32734');
INSERT INTO `server_house` VALUES ('262164', '奇巖血盟小屋20', '78', '20', '50540', '1', '0', '2010-09-01 00:00:00', '33366', '33372', '32713', '32715', '33369', '33372', '32716', '32720', '4', '5087', '33366', '32714');
INSERT INTO `server_house` VALUES ('262165', '奇巖血盟小屋21', '45', '21', '50515', '1', '0', '2010-09-01 00:00:00', '33380', '33383', '32712', '32718', '0', '0', '0', '0', '4', '5088', '33381', '32715');
INSERT INTO `server_house` VALUES ('262166', '奇巖血盟小屋22', '120', '22', '50513', '1', '0', '2010-09-01 00:00:00', '33401', '33413', '32733', '32739', '0', '0', '0', '0', '4', '5089', '33404', '32739');
INSERT INTO `server_house` VALUES ('262167', '奇巖血盟小屋23', '78', '23', '50528', '1', '0', '2010-09-01 00:00:00', '33424', '33430', '32717', '32719', '33427', '33430', '32720', '32724', '4', '5090', '33424', '32718');
INSERT INTO `server_house` VALUES ('262168', '奇巖血盟小屋24', '45', '24', '50533', '1', '0', '2010-09-01 00:00:00', '33448', '33451', '32729', '32735', '0', '0', '0', '0', '4', '5091', '33449', '32732');
INSERT INTO `server_house` VALUES ('262169', '奇巖血盟小屋25', '45', '25', '50542', '1', '0', '2010-09-01 00:00:00', '33404', '33407', '32754', '32760', '0', '0', '0', '0', '4', '5092', '33405', '32757');
INSERT INTO `server_house` VALUES ('262170', '奇巖血盟小屋26', '120', '26', '50511', '1', '0', '2010-09-01 00:00:00', '33363', '33375', '32755', '32761', '0', '0', '0', '0', '4', '5093', '33366', '32761');
INSERT INTO `server_house` VALUES ('262171', '奇巖血盟小屋27', '78', '27', '50501', '1', '0', '2010-09-01 00:00:00', '33351', '33357', '32774', '32776', '33354', '33357', '32777', '32781', '4', '5094', '33351', '32775');
INSERT INTO `server_house` VALUES ('262172', '奇巖血盟小屋28', '45', '28', '50503', '1', '0', '2010-09-01 00:00:00', '33355', '33361', '32787', '32790', '0', '0', '0', '0', '4', '5095', '33358', '32789');
INSERT INTO `server_house` VALUES ('262173', '奇巖血盟小屋29', '78', '29', '50508', '1', '0', '2010-09-01 05:00:00', '33366', '33373', '32786', '32789', '33371', '33373', '32790', '32792', '4', '5096', '33372', '32792');
INSERT INTO `server_house` VALUES ('262174', '奇巖血盟小屋30', '45', '30', '50514', '1', '0', '2010-09-01 00:00:00', '33383', '33386', '32773', '32779', '0', '0', '0', '0', '4', '5097', '33384', '32776');
INSERT INTO `server_house` VALUES ('262175', '奇巖血盟小屋31', '78', '31', '50532', '1', '0', '2010-09-01 00:00:00', '33397', '33404', '32788', '32791', '33402', '33404', '32792', '32794', '4', '5098', '33403', '32794');
INSERT INTO `server_house` VALUES ('262176', '奇巖血盟小屋32', '78', '32', '50544', '1', '0', '2010-09-01 00:00:00', '33479', '33486', '32788', '32791', '33484', '33486', '32792', '32794', '4', '5099', '33485', '32794');
INSERT INTO `server_house` VALUES ('262177', '奇巖血盟小屋33', '45', '33', '50524', '1', '0', '2010-09-01 00:00:00', '33498', '33501', '32801', '32807', '0', '0', '0', '0', '4', '5100', '33499', '32804');
INSERT INTO `server_house` VALUES ('262178', '奇巖血盟小屋34', '45', '34', '50535', '1', '0', '2010-09-01 00:00:00', '33379', '33385', '32802', '32805', '0', '0', '0', '0', '4', '5101', '33382', '32804');
INSERT INTO `server_house` VALUES ('262179', '奇巖血盟小屋35', '120', '35', '50521', '1', '0', '2010-09-01 00:00:00', '33373', '33385', '32822', '32829', '0', '0', '0', '0', '4', '5102', '33376', '32828');
INSERT INTO `server_house` VALUES ('262180', '奇巖血盟小屋36', '45', '36', '50517', '1', '0', '2010-09-01 00:00:00', '33398', '33401', '32810', '32816', '0', '0', '0', '0', '4', '5103', '33399', '32813');
INSERT INTO `server_house` VALUES ('262181', '奇巖血盟小屋37', '78', '37', '50537', '1', '0', '2010-09-01 00:00:00', '33397', '33403', '32821', '32823', '33400', '33403', '32824', '32828', '4', '5104', '33397', '32822');
INSERT INTO `server_house` VALUES ('262182', '奇巖血盟小屋38', '78', '38', '50539', '1', '0', '2010-09-01 00:00:00', '33431', '33438', '32838', '32841', '33436', '33438', '32842', '32844', '4', '5105', '33437', '32844');
INSERT INTO `server_house` VALUES ('262183', '奇巖血盟小屋39', '45', '39', '50507', '1', '1', '2010-09-01 00:00:00', '33456', '33462', '32838', '32841', '0', '0', '0', '0', '4', '5106', '33459', '32840');
INSERT INTO `server_house` VALUES ('262184', '奇巖血盟小屋40', '78', '40', '50530', '1', '0', '2010-09-01 00:00:00', '33385', '33392', '32845', '32848', '33390', '33392', '32849', '32851', '4', '5107', '33391', '32851');
INSERT INTO `server_house` VALUES ('262185', '奇巖血盟小屋41', '78', '41', '50502', '1', '0', '2010-09-01 00:00:00', '33399', '33405', '32859', '32861', '33402', '33405', '32862', '32866', '4', '5108', '33399', '32860');
INSERT INTO `server_house` VALUES ('262186', '奇巖血盟小屋42', '45', '42', '50506', '1', '0', '2010-09-01 00:00:00', '33414', '33417', '32850', '32856', '0', '0', '0', '0', '4', '5109', '33415', '32853');
INSERT INTO `server_house` VALUES ('262187', '奇巖血盟小屋43', '120', '43', '50522', '1', '0', '2010-09-01 00:00:00', '33372', '33384', '32867', '32873', '0', '0', '0', '0', '4', '5110', '33375', '32873');
INSERT INTO `server_house` VALUES ('262188', '奇巖血盟小屋44', '120', '44', '50541', '1', '0', '2010-09-01 00:00:00', '33425', '33437', '32865', '32871', '0', '0', '0', '0', '4', '5111', '33428', '32871');
INSERT INTO `server_house` VALUES ('262189', '奇巖血盟小屋45', '78', '45', '50523', '1', '0', '2010-09-01 00:00:00', '33443', '33449', '32869', '32871', '33446', '33449', '32872', '32876', '4', '5112', '33443', '32870');
INSERT INTO `server_house` VALUES ('327681', '海音血盟小屋1', '0', '1', '50620', '1', '0', '2010-09-01 00:00:00', '33599', '33601', '33213', '33214', '33602', '33610', '33213', '33218', '4', '5113', '33609', '33217');
INSERT INTO `server_house` VALUES ('327682', '海音血盟小屋2', '0', '2', '50623', '1', '0', '2010-09-01 00:00:00', '33627', '33632', '33206', '33209', '0', '0', '0', '0', '4', '5114', '33630', '33209');
INSERT INTO `server_house` VALUES ('327683', '海音血盟小屋3', '0', '3', '50619', '1', '0', '2010-09-01 00:00:00', '33626', '33627', '33225', '33227', '33628', '33632', '33221', '33230', '4', '5115', '33628', '33226');
INSERT INTO `server_house` VALUES ('327684', '海音血盟小屋4', '0', '4', '50621', '1', '0', '2010-09-01 00:00:00', '33628', '33636', '33241', '33244', '33632', '33635', '33245', '33250', '4', '5116', '33633', '33248');
INSERT INTO `server_house` VALUES ('327685', '海音血盟小屋5', '0', '5', '50622', '1', '0', '2010-09-01 00:00:00', '33616', '33621', '33262', '33265', '0', '0', '0', '0', '4', '5117', '33619', '33265');
INSERT INTO `server_house` VALUES ('327686', '海音血盟小屋6', '0', '6', '50624', '1', '0', '2010-09-01 00:00:00', '33570', '33580', '33228', '33232', '33574', '33576', '33233', '33234', '4', '5118', '33575', '33233');
INSERT INTO `server_house` VALUES ('327687', '海音血盟小屋7', '0', '7', '50617', '1', '0', '2010-09-01 00:00:00', '33583', '33588', '33305', '33314', '33587', '33588', '33315', '33316', '4', '5119', '33584', '33306');
INSERT INTO `server_house` VALUES ('327688', '海音血盟小屋8', '0', '8', '50614', '1', '0', '2010-09-01 00:00:00', '33577', '33578', '33337', '33337', '33579', '33588', '33335', '33339', '4', '5120', '33581', '33338');
INSERT INTO `server_house` VALUES ('327689', '海音血盟小屋9', '0', '9', '50618', '1', '0', '2010-09-01 00:00:00', '33615', '33623', '33374', '33377', '33619', '33622', '33378', '33383', '4', '5121', '33620', '33381');
INSERT INTO `server_house` VALUES ('327690', '海音血盟小屋10', '0', '10', '50616', '1', '0', '2010-09-01 00:00:00', '33624', '33625', '33397', '33399', '33626', '33630', '33393', '33403', '4', '5122', '33625', '33398');
INSERT INTO `server_house` VALUES ('327691', '海音血盟小屋11', '0', '11', '50615', '1', '0', '2010-09-01 21:00:00', '33621', '33622', '33444', '33444', '33622', '33632', '33442', '33446', '4', '5123', '33625', '33445');
INSERT INTO `server_house` VALUES ('458753', '亞丁血盟小屋1', '51', '1', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458754', '亞丁血盟小屋2', '24', '2', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458755', '亞丁血盟小屋3', '24', '3', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458756', '亞丁血盟小屋4', '56', '4', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458757', '亞丁血盟小屋5', '24', '5', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458758', '亞丁血盟小屋6', '63', '6', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458759', '亞丁血盟小屋7', '24', '7', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458760', '亞丁血盟小屋8', '63', '8', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458761', '亞丁血盟小屋9', '24', '9', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458762', '亞丁血盟小屋10', '24', '10', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458763', '亞丁血盟小屋11', '24', '11', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458764', '亞丁血盟小屋12', '24', '12', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458765', '亞丁血盟小屋13', '24', '13', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458766', '亞丁血盟小屋14', '63', '14', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458767', '亞丁血盟小屋15', '24', '15', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458768', '亞丁血盟小屋16', '56', '16', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458769', '亞丁血盟小屋17', '63', '17', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458770', '亞丁血盟小屋18', '24', '18', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458771', '亞丁血盟小屋19', '32', '19', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458772', '亞丁血盟小屋20', '64', '20', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458773', '亞丁血盟小屋21', '24', '21', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458774', '亞丁血盟小屋22', '20', '22', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458775', '亞丁血盟小屋23', '24', '23', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458776', '亞丁血盟小屋24', '56', '24', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458777', '亞丁血盟小屋25', '24', '25', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458778', '亞丁血盟小屋26', '20', '26', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458779', '亞丁血盟小屋27', '42', '27', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458780', '亞丁血盟小屋28', '15', '28', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458781', '亞丁血盟小屋29', '24', '29', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458782', '亞丁血盟小屋30', '24', '30', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458783', '亞丁血盟小屋31', '42', '31', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458784', '亞丁血盟小屋32', '48', '32', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458785', '亞丁血盟小屋33', '24', '33', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458786', '亞丁血盟小屋34', '15', '34', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458787', '亞丁血盟小屋35', '24', '35', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458788', '亞丁血盟小屋36', '24', '36', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458789', '亞丁血盟小屋37', '64', '37', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458790', '亞丁血盟小屋38', '15', '38', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458791', '亞丁血盟小屋39', '15', '39', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458792', '亞丁血盟小屋40', '24', '40', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458793', '亞丁血盟小屋41', '24', '41', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458794', '亞丁血盟小屋42', '29', '42', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458795', '亞丁血盟小屋43', '24', '43', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458796', '亞丁血盟小屋44', '15', '44', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458797', '亞丁血盟小屋45', '56', '45', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458798', '亞丁血盟小屋46', '15', '46', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458799', '亞丁血盟小屋47', '24', '47', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458800', '亞丁血盟小屋48', '24', '48', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458801', '亞丁血盟小屋49', '24', '49', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458802', '亞丁血盟小屋50', '48', '50', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458803', '亞丁血盟小屋51', '24', '51', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458804', '亞丁血盟小屋52', '24', '52', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458805', '亞丁血盟小屋53', '54', '53', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458806', '亞丁血盟小屋54', '24', '54', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458807', '亞丁血盟小屋55', '63', '55', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458808', '亞丁血盟小屋56', '63', '56', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458809', '亞丁血盟小屋57', '42', '57', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458810', '亞丁血盟小屋58', '24', '58', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458811', '亞丁血盟小屋59', '24', '59', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458812', '亞丁血盟小屋60', '63', '60', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458813', '亞丁血盟小屋61', '24', '61', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458814', '亞丁血盟小屋62', '21', '62', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458815', '亞丁血盟小屋63', '63', '63', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458816', '亞丁血盟小屋64', '24', '64', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458817', '亞丁血盟小屋65', '24', '65', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458818', '亞丁血盟小屋66', '48', '66', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('458819', '亞丁血盟小屋67', '24', '67', '0', '1', '0', '2010-09-01 00:00:00', '0', '0', '0', '0', '0', '0', '0', '0', '4', '0', '0', '0');
INSERT INTO `server_house` VALUES ('524289', '古魯丁血盟小屋1', '48', '1', '50626', '1', '0', '2010-03-01 00:00:00', '32559', '32566', '32669', '32676', '0', '0', '0', '0', '4', '0', '32564', '32675');
INSERT INTO `server_house` VALUES ('524290', '古魯丁血盟小屋2', '71', '2', '50627', '1', '0', '2010-09-01 00:00:00', '32548', '32556', '32705', '32716', '32547', '32547', '32710', '32716', '4', '0', '32549', '32707');
INSERT INTO `server_house` VALUES ('524291', '古魯丁血盟小屋3', '48', '3', '50628', '1', '0', '2010-09-01 00:00:00', '32537', '32544', '32781', '32791', '0', '0', '0', '0', '4', '0', '32538', '32782');
INSERT INTO `server_house` VALUES ('524292', '古魯丁血盟小屋4', '48', '4', '50629', '1', '0', '2010-09-01 00:00:00', '32550', '32560', '32780', '32787', '0', '0', '0', '0', '4', '0', '32558', '32786');
INSERT INTO `server_house` VALUES ('524293', '古魯丁血盟小屋5', '82', '5', '50630', '1', '0', '2010-09-01 00:00:00', '32535', '32543', '32807', '32818', '32534', '32534', '32812', '32818', '4', '0', '32536', '32809');
INSERT INTO `server_house` VALUES ('524294', '古魯丁血盟小屋6', '40', '6', '50631', '1', '0', '2010-09-01 00:00:00', '32553', '32560', '32814', '32821', '0', '0', '0', '0', '4', '0', '32554', '32819');

-- ----------------------------
-- Table structure for server_town
-- ----------------------------
DROP TABLE IF EXISTS `server_town`;
CREATE TABLE `server_town` (
  `town_id` int(10) unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL DEFAULT '',
  `leader_id` int(10) unsigned NOT NULL DEFAULT '0',
  `leader_name` varchar(45) DEFAULT NULL,
  `tax_rate` int(10) unsigned NOT NULL DEFAULT '0',
  `tax_rate_reserved` int(10) unsigned NOT NULL DEFAULT '0',
  `sales_money` int(10) unsigned NOT NULL DEFAULT '0',
  `sales_money_yesterday` int(10) unsigned NOT NULL DEFAULT '0',
  `town_tax` int(10) unsigned NOT NULL DEFAULT '0',
  `town_fix_tax` int(10) unsigned NOT NULL DEFAULT '0',
  `none` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`town_id`),
  KEY `town_id` (`town_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of server_town
-- ----------------------------
INSERT INTO `server_town` VALUES ('1', '說話之島村莊', '0', null, '0', '0', '0', '0', '0', '0', null);
INSERT INTO `server_town` VALUES ('2', '銀騎士村莊', '0', null, '0', '0', '3000', '0', '0', '58', null);
INSERT INTO `server_town` VALUES ('3', '古魯丁村莊', '0', null, '0', '0', '0', '0', '0', '0', null);
INSERT INTO `server_town` VALUES ('4', '燃柳村莊', '0', null, '0', '0', '0', '0', '0', '0', null);
INSERT INTO `server_town` VALUES ('5', '風木村莊', '0', null, '0', '0', '0', '0', '0', '0', null);
INSERT INTO `server_town` VALUES ('6', '肯特村莊', '0', null, '0', '0', '0', '0', '0', '0', null);
INSERT INTO `server_town` VALUES ('7', '奇岩村莊', '0', null, '0', '0', '0', '0', '0', '0', null);
INSERT INTO `server_town` VALUES ('8', '海音村', '0', null, '0', '0', '0', '0', '0', '0', null);
INSERT INTO `server_town` VALUES ('9', '威頓村', '0', null, '0', '0', '0', '0', '0', '0', null);
INSERT INTO `server_town` VALUES ('10', '歐瑞村', '0', null, '0', '0', '0', '0', '0', '0', null);
