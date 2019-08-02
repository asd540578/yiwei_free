package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * スキルアイコンや遮断リストの表示など複数の用途に使われるパケットのクラス
 */
public class S_PacketBox extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * writeByte(id) writeShort(?): <font color=#00800>(639) %s的攻城戰開始。 </font><BR>
	 * 1:Kent 2:Orc 3:WW 4:Giran 5:Heine 6:Dwarf 7:Aden 8:Diad 9:城名9 ...
	 */
	// public static final int MSG_WAR_BEGIN = 0;

	/**
	 * writeByte(id) writeShort(?): <font color=#00800>(640) %s的攻城戰結束。 </font>
	 */
	// public static final int MSG_WAR_END = 1;

	/**
	 * writeByte(id) writeShort(?): <font color=#00800>(641) %s的攻城戰正在進行中。 </font>
	 */
	// public static final int MSG_WAR_GOING = 2;

	/**
	 * <font color=#00800>(642) 已掌握了城堡的主導權。 </font>
	 */
	// public static final int MSG_WAR_INITIATIVE = 3;

	/**
	 * <font color=#00800>(643) 已佔領城堡。</font>
	 */
	// public static final int MSG_WAR_OCCUPY = 4;

	/**
	 * <font color=#00800>(646) 結束決鬥。 </font>
	 */
	public static final int MSG_DUEL = 5;

	/**
	 * writeByte(count): <font color=#00800>(648) 您沒有發送出任何簡訊。 </font>
	 */
	public static final int MSG_SMS_SENT = 6;

	/**
	 * <font color=#00800>(790) 倆人的結婚在所有人的祝福下完成 </font>
	 */
	public static final int MSG_MARRIED = 9;

	/**
	 * writeByte(weight): <font color=#00800>重量(30段階) </font>
	 */
	public static final int WEIGHT = 10;

	/**
	 * writeByte(food): <font color=#00800>満腹度(30段階) </font>
	 */
	public static final int FOOD = 11;

	/**
	 * <font color=#00800>UB情報HTML </font>
	 */
	public static final int HTML_UB = 14;

	/**
	 * writeByte(id)<br>
	 * <font color=#00800> 1: (978) 感覺到在身上有的精靈力量被空氣中融化。<br>
	 * 2: (679) 忽然全身充滿了%s的靈力。 680 火<br>
	 * 3: (679) 忽然全身充滿了%s的靈力。 681 水<br>
	 * 4: (679) 忽然全身充滿了%s的靈力。 682 風<br>
	 * 5: (679) 忽然全身充滿了%s的靈力。 683 地<br>
	 * </font>
	 */
	public static final int MSG_ELF = 15;

	/**
	 * writeByte(count) S(name)...: <font color=#00800>開啟拒絕名單 :</font>
	 */
	public static final int ADD_EXCLUDE2 = 17;// 17

	/**
	 * writeString(name): <font color=#00800>增加到拒絕名單</font>
	 */
	public static final int ADD_EXCLUDE = 18;// 18

	/**
	 * writeString(name): <font color=#00800>移除出拒絕名單</font>
	 */
	public static final int REM_EXCLUDE = 19;// 19

	/** 技能圖示 */
	public static final int ICONS1 = 20;// 0x14

	/** 技能圖示 */
	public static final int ICONS2 = 21;// 0x15

	/** 技能圖示 */
	public static final int ICON_AURA = 22;// 0x16

	/**
	 * writeString(name): <font color=#00800>(764) 新村長由%s選出</font>
	 */
	public static final int MSG_TOWN_LEADER = 23;

	/**
	 * writeByte(id): <font color=#00800>聯盟職位變更</font><br>
	 * id - 1:見習 2:一般 3:守護騎士
	 */
	public static final int MSG_RANK_CHANGED = 27;

	/**
	 * <font color=#00800>血盟線上人數(HTML)</font>
	 */
	public static final int MSG_CLANUSER = 29;

	/**
	 * writeInt(?) writeString(name) writeString(clanname):<br>
	 * <font color=#00800>(782) %s 血盟的 %s打敗了反王<br>
	 * (783) %s 血盟成為新主人。 </font>
	 */
	// public static final int MSG_WIN_LASTAVARD = 30;

	/**
	 * <font color=#00800>(77) \f1你覺得舒服多了。</font>
	 */
	public static final int MSG_FEEL_GOOD = 31;

	/** 不明。 客戶端會傳回一個封包 */
	// INFO - Not Set OP ID: 40
	// 0000: 28 58 02 00 00 fe b2 d4 c6 00 00 00 00 00 00 00 (X..............
	public static final int SOMETHING1 = 33;

	/**
	 * writeShort(time): <font color=#00800>藍水圖示</font>
	 */
	public static final int ICON_BLUEPOTION = 34;// 34

	/**
	 * writeShort(time): <font color=#00800>變身圖示</font>
	 */
	public static final int ICON_POLYMORPH = 35;// 35

	/**
	 * writeShort(time): <font color=#00800>禁言圖示 </font>
	 */
	public static final int ICON_CHATBAN = 36;// 36

	/** 不明。C_7パケットが飛ぶ。C_7はペットのメニューを開いたときにも飛ぶ。 */
	public static final int SOMETHING2 = 37;

	/**
	 * <font color=#00800>血盟成員清單(HTML)</font>
	 */
	public static final int HTML_CLAN1 = 38;

	/** writeShort(time): 聖結界圖示 */
	public static final int ICON_I2H = 40;

	/**
	 * <font color=#00800>更新角色使用的快速鍵</font>
	 */
	public static final int CHARACTER_CONFIG = 41;// 41

	/**
	 * <font color=#00800>角色選擇視窗</font> > 0000 : 39 2a e1 88 08 12 48 fa 9*....H.
	 */
	public static final int LOGOUT = 42;// 42

	/**
	 * <font color=#00800>(130) \f1戰鬥中，無法重新開始。</font>
	 */
	public static final int MSG_CANT_LOGOUT = 43;

	/**
	 * <font color=#00800>風之枷鎖</font>
	 */
	// public static final int WIND_SHACKLE = 44;

	/**
	 * writeByte(count) writeInt(time) writeString(name) writeString(info):<br>
	 * [CALL] ボタンのついたウィンドウが表示される。これはBOTなどの不正者チェックに
	 * 使われる機能らしい。名前をダブルクリックするとC_RequestWhoが飛び、クライアントの
	 * フォルダにbot_list.txtが生成される。名前を選択して+キーを押すと新しいウィンドウが開く。
	 */
	// public static final int CALL_SOMETHING = 45;

	/**
	 * <font color=#00800>writeByte(id): 大圓形競技場，混沌的大戰<br>
	 * id - 1:開始(1045) 2:取消(1047) 3:結束(1046)</font>
	 */
	public static final int MSG_COLOSSEUM = 49;

	/**
	 * <font color=#00800>血盟情報(HTML)</font>
	 */
	public static final int HTML_CLAN2 = 51;

	/**
	 * <font color=#00800>料理選單</font>
	 */
	// public static final int COOK_WINDOW = 52;

	/** writeByte(type) writeShort(time): 料理アイコンが表示される */
	// public static final int ICON_COOKING = 53;

	/** 魚上鉤的圖形表示 */
	public static final int FISHING = 55;

	/** 魔法娃娃圖示 */
	public static final int DOLL = 56;

	/** 慎重藥水 */
	public static final int WISDOM_POTION = 57;

	/** 同盟目錄 */
	public static final int CLAN = 62;

	/** 比賽視窗(倒數開始) */
	// public static final int GAMESTART = 64;

	/** 開始正向計時 */
	// public static final int TIMESTART = 65;

	/** 顯示資訊 */
	// public static final int GAMEINFO = 66;

	/** 比賽視窗(倒數結束/停止計時) */
	// public static final int GAMEOVER = 69;

	/** 移除比賽視窗 */
	// public static final int GAMECLEAR = 70;

	/** 開始反向計時 */
	// public static final int STARTTIME = 71;

	/** 移除開始反向計時視窗 */
	// public static final int STARTTIMECLEAR = 72;

	/** 手臂受傷攻擊力下降(350秒) */
	public static final int DMG = 74;

	/** 對方開始顯示弱點(16秒) */
	public static final int TGDMG = 75;

	/** 攻城戰結束訊息 */
	// public static final int MSG_WAR_OVER = 79;

	/** 1,550：受到殷海薩的祝福，增加了些許的狩獵經驗值。 */
	// public static final int LEAVES = 82;

	/** 經驗值加成（殷海薩的祝福） */
	public static final int EXPBLESS = 82;

	/** 不明 綠色葉子 */
	// public static final int LEAVES200 = 87;

	/** 顯示龍座標選單 */
	public static final int DRAGON = 101;

	/** 3.8血盟倉庫使用紀錄 */
	public static final int HTML_CLAN_WARHOUSE_RECORD = 117;

	/** 資安 */
	public static final int ICON_OS = 125;// 22;//0x16
	/** VIP */
	public static final int ICON_VIP = 127;// VIP

	/** DOTO 3.53 副本實裝↓ */
	public static final int MAP_TIMER = 153;
	public static final int DISPLAY_MAP_TIME = 159;
	/** DOTO 3.53 副本實裝↑ */

	/** 3.8 血盟查詢盟友 (顯示公告) */
	public static final int HTML_PLEDGE_ANNOUNCE = 167;

	/** 3.8 血盟查詢盟友 (寫入公告) */
	public static final int HTML_PLEDGE_REALEASE_ANNOUNCE = 168;

	/** 3.8 血盟查詢盟友 (寫入備註) */
	public static final int HTML_PLEDGE_WRITE_NOTES = 169;

	/** 3.8 血盟查詢盟友 (顯示盟友) */
	public static final int HTML_PLEDGE_MEMBERS = 170;

	/** 3.8 血盟查詢盟友 (顯示上線盟友) */
	public static final int HTML_PLEDGE_ONLINE_MEMBERS = 171;

	/** 3.8 血盟 識別盟徽狀態 */
	public static final int PLEDGE_EMBLEM_STATUS = 173;

	/** 3.8 村莊便利傳送 */
	public static final int TOWN_TELEPORT = 176;
	
	
	// 102 龍之棲息地 林德拜爾
	// 125 OS的資安水準是最新狀態.因此身體和心理上感覺變強
	// 127 (已使用) 獲得體力上限+50、魔力上限+30、體力恢復+1 、魔力恢復+1的效果
	// 146 解除師徒
	// 153 新版計時器
	// 147 師徒icon
	// 165 獎勵 +20%
	/**
	 * 殷海薩的祝福
	 * 
	 * @param subCode
	 * @param msg
	 * @param value
	 */
	public S_PacketBox(final int subCode, final String msg, final int value) {
		this.writeC(S_OPCODE_PACKETBOX);
		// TODO 修正->殷海薩的祝福
		this.writeC(subCode);
		// this.writeC(1);
		this.writeS(msg);
		// this.writeS(msg);
		// writeC(0x52);//82
		this.writeC(value);
	}

	public S_PacketBox(final int subCode) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(subCode);

		switch (subCode) {
		// case MSG_WAR_INITIATIVE:
		// case MSG_WAR_OCCUPY:
		case MSG_MARRIED:
		case MSG_FEEL_GOOD:
		case MSG_CANT_LOGOUT:
		case LOGOUT:
		case FISHING:
			break;

		/*
		 * case CALL_SOMETHING: this.callSomething();
		 */
		default:
			break;
		}
	}

	/**
	 * TEST
	 */
	public S_PacketBox(final int type, final byte time) {
		// final int type = 181;
		// final int time = 20;
		// final boolean second = false;
		this.writeC(S_OPCODE_PACKETBOX);

		this.writeC(type);
		this.writeC(time); // time >> 2

	}

	public S_PacketBox(final int subCode, final int value) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(subCode);

		switch (subCode) {
		case DMG:
		case TGDMG:
			final int time = value >> 2;
			this.writeC(time); // time >> 2
			break;

		case ICON_BLUEPOTION:
		case ICON_CHATBAN:
		case ICON_I2H:
		case ICON_POLYMORPH:
		case MAP_TIMER: // TODO 3.53C 計時器(副本)
		case ICON_OS:
			this.writeH(value); // time
			break;

		/*
		 * case MSG_WAR_BEGIN: case MSG_WAR_END: case MSG_WAR_GOING: this.writeC(value);
		 * // castle id this.writeH(0); // ? break;
		 */

		case WISDOM_POTION:
			this.writeC(0x2c);
			this.writeH(value); // time
			break;

		case MSG_SMS_SENT:
		case WEIGHT:
		case FOOD:
			this.writeC(value);
			break;

		case MSG_ELF:
		case MSG_RANK_CHANGED:
		case MSG_COLOSSEUM:
			this.writeC(value); // msg id
			break;

		/*
		 * case MSG_LEVEL_OVER: writeC(0); // ? writeC(value); // 0-49以外は表示されない break;
		 */

		/*
		 * case COOK_WINDOW: this.writeC(0xdb); // ? this.writeC(0x31);
		 * this.writeC(0xdf); this.writeC(0x02); this.writeC(0x01); this.writeC(value);
		 * // level break;
		 */

		case DOLL:
			this.writeH(value);
			break;

		case PLEDGE_EMBLEM_STATUS:
			this.writeC(1);
			this.writeC(value);
			this.writeD(0);
			break;

		default:
			// this.writeH(value); // time
			break;
		}
	}

	public S_PacketBox(final int subCode, final int type, final int time) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(subCode);

		switch (subCode) {
		/*
		 * case ICON_COOKING: if (type != 7) { this.writeC(0x0c); this.writeC(0x0c);
		 * this.writeC(0x0c); this.writeC(0x12); this.writeC(0x0c); this.writeC(0x09);
		 * this.writeC(0x00); this.writeC(0x00); this.writeC(type); this.writeC(0x24);
		 * this.writeH(time); this.writeH(0x00);
		 * 
		 * } else { this.writeC(0x0c); this.writeC(0x0c); this.writeC(0x0c);
		 * this.writeC(0x12); this.writeC(0x0c); this.writeC(0x09); this.writeC(0xc8);
		 * this.writeC(0x00); this.writeC(type); this.writeC(0x26); this.writeH(time);
		 * this.writeC(0x3e); this.writeC(0x87); } break;
		 */

		case MSG_DUEL:
			this.writeD(type); // 相手のオブジェクトID
			this.writeD(time); // 自分のオブジェクトID
			break;

		default:
			break;
		}
	}

	public S_PacketBox(final int subCode, final String name) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(subCode);

		switch (subCode) {
		case ADD_EXCLUDE:
		case REM_EXCLUDE:
		case MSG_TOWN_LEADER:
			this.writeS(name);
			break;
	    case HTML_PLEDGE_REALEASE_ANNOUNCE:
		      writeS(name);		
				break;
		default:
			break;
		}
	}

	public S_PacketBox(final int subCode, final Object[] names) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(subCode);

		switch (subCode) {
		case ADD_EXCLUDE2:
			this.writeC(names.length);
			for (final Object name : names) {
				this.writeS(name.toString());
			}
			break;
			
			//血盟查詢
		case HTML_PLEDGE_ONLINE_MEMBERS:
			writeH(names.length);
			for (Object name : names) {
				L1PcInstance pc = (L1PcInstance) name;
				writeS(pc.getName());
			   writeC(0); // 3.80 新增
			}
			break;
		default:
			break;
		}
	}
	
	/** DOTO 3.53 副本實裝↓ */
	public S_PacketBox(final int subCode, final int time1, final int time2, final int time3, final int time4) {
		this.writeC(S_OPCODE_PACKETBOX);
		this.writeC(subCode);
		switch (subCode) {
		case DISPLAY_MAP_TIME:
			this.writeD(4);
			this.writeD(1);
			this.writeS("$12125"); // 奇岩地監
			this.writeD(time1);
			this.writeD(2);
			this.writeS("$6081"); // 象牙塔地監
			this.writeD(time2);
			this.writeD(3);
			this.writeS("$12126"); // 拉斯塔巴德地監
			this.writeD(time3);
			this.writeD(6);
			this.writeS(""); //時空裂痕
			this.writeD(time4);
			break;
		default:
			break;
		}
	}

	/** DOTO 3.53 副本實裝↑ */
	@Override
	public byte[] getContent() {
		if (this._byte == null) {
			this._byte = this.getBytes();
		}
		return this._byte;
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
