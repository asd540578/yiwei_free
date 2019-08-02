package com.lineage.server.model.Instance;

import static com.lineage.server.model.skill.L1SkillId.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.Config;
import com.lineage.config.ConfigAlt;
import com.lineage.config.ConfigKill;
import com.lineage.config.ConfigOther;
import com.lineage.config.ConfigRate;
import com.lineage.data.event.OnlineGiftSet;
import com.lineage.data.event.QuestSet;
import com.lineage.data.quest.Chapter01R;
import com.lineage.echo.ClientExecutor;
import com.lineage.echo.EncryptExecutor;
import com.lineage.server.ActionCodes;
import com.lineage.server.clientpackets.AcceleratorChecker;
import com.lineage.server.datatables.ExpTable;
import com.lineage.server.datatables.MapLevelTable;
import com.lineage.server.datatables.QuestTable;
import com.lineage.server.datatables.lock.CharBuffReading;
import com.lineage.server.datatables.lock.CharOtherReading;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.datatables.sql.CharacterTable;
import com.lineage.server.model.L1ActionPet;
import com.lineage.server.model.L1ActionSummon;
import com.lineage.server.model.L1AttackMode;
import com.lineage.server.model.L1AttackPc;
import com.lineage.server.model.L1CastleLocation;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1ChatParty;
import com.lineage.server.model.L1Clan;
import com.lineage.server.model.L1DwarfForElfInventory;
import com.lineage.server.model.L1DwarfInventory;
import com.lineage.server.model.L1EquipmentSlot;
import com.lineage.server.model.L1ExcludingList;
import com.lineage.server.model.L1Karma;
import com.lineage.server.model.L1Magic;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.L1Party;
import com.lineage.server.model.L1ActionPc;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.L1PinkName;
import com.lineage.server.model.L1PcQuest;
import com.lineage.server.model.L1Teleport;
import com.lineage.server.model.L1TownLocation;
import com.lineage.server.model.L1War;
import com.lineage.server.model.classes.L1ClassFeature;
import com.lineage.server.model.monitor.L1PcInvisDelay;
import com.lineage.server.model.skill.L1SkillUse;
import com.lineage.server.serverpackets.S_BlueMessage;
import com.lineage.server.serverpackets.S_Bonusstats;
import com.lineage.server.serverpackets.S_ChangeShape;
import com.lineage.server.serverpackets.S_DelSkill;
import com.lineage.server.serverpackets.S_DoActionGFX;
import com.lineage.server.serverpackets.S_DoActionShop;
import com.lineage.server.serverpackets.S_EquipmentWindow;
import com.lineage.server.serverpackets.S_Exp;
import com.lineage.server.serverpackets.S_Fishing;
import com.lineage.server.serverpackets.S_HPMeter;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.serverpackets.S_KillMessage;
import com.lineage.server.serverpackets.S_Lawful;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_OtherCharPacks;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_PacketBoxParty;
import com.lineage.server.serverpackets.S_PacketBoxProtection;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillSound;
import com.lineage.server.serverpackets.ServerBasePacket;
import com.lineage.server.templates.L1PcOther;
import com.lineage.server.templates.L1PcOtherList;
import com.lineage.server.templates.L1PrivateShopBuyList;
import com.lineage.server.templates.L1PrivateShopSellList;
import com.lineage.server.templates.L1Skills;
import com.lineage.server.templates.L1TradeItem;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.timecontroller.server.ServerUseMapTimer;
import com.lineage.server.timecontroller.server.ServerWarExecutor;
import com.lineage.server.utils.CalcInitHpMp;
import com.lineage.server.utils.CalcStat;
import com.lineage.server.utils.ListMapUtil;
import com.lineage.server.world.World;
import com.lineage.server.world.WorldClan;
import com.lineage.server.world.WorldQuest;
import com.lineage.server.world.WorldWar;

import log.datatables.lock.LogDeathReading;

/**
 * 對象:PC 控制項
 * 
 * @author dexc
 *
 */
public class L1PcInstance extends L1Character {

	private static final Log _log = LogFactory.getLog(L1PcInstance.class);

	private static final long serialVersionUID = 1L;

	/** 騎士(男) */
	public static final int CLASSID_KNIGHT_MALE = 61;
	/** 騎士(女) */
	public static final int CLASSID_KNIGHT_FEMALE = 48;

	/** 精靈(男) */
	public static final int CLASSID_ELF_MALE = 138;
	/** 精靈(女) */
	public static final int CLASSID_ELF_FEMALE = 37;

	/** 法師(男) */
	public static final int CLASSID_WIZARD_MALE = 734;
	/** 法師(女) */
	public static final int CLASSID_WIZARD_FEMALE = 1186;

	/** 黑妖(男) */
	public static final int CLASSID_DARK_ELF_MALE = 2786;
	/** 黑妖(女) */
	public static final int CLASSID_DARK_ELF_FEMALE = 2796;

	/** 王族(男) */
	public static final int CLASSID_PRINCE = 0;
	/** 王族(女) */
	public static final int CLASSID_PRINCESS = 1;

	/** 龍騎(男) */
	public static final int CLASSID_DRAGON_KNIGHT_MALE = 6658;
	/** 龍騎(女) */
	public static final int CLASSID_DRAGON_KNIGHT_FEMALE = 6661;

	/** 幻術(男) */
	public static final int CLASSID_ILLUSIONIST_MALE = 6671;
	/** 幻術(女) */
	public static final int CLASSID_ILLUSIONIST_FEMALE = 6650;

	private static Random _random = new Random();

	private boolean _isKill = false;

	public boolean is_isKill() {
		return _isKill;
	}

	public void set_isKill(boolean _isKill) {
		this._isKill = _isKill;
	}

	private short _hpr = 0;

	private short _trueHpr = 0;

	public short getHpr() {
		return this._hpr;
	}

	/**
	 * 增加(減少)HP回復量
	 * 
	 * @param i
	 */
	public void addHpr(final int i) {
		this._trueHpr += i;
		this._hpr = (short) Math.max(0, this._trueHpr);
	}

	private short _mpr = 0;
	private short _trueMpr = 0;

	public short getMpr() {
		return this._mpr;
	}

	/**
	 * 增加(減少)MP回復量
	 * 
	 * @param i
	 */
	public void addMpr(final int i) {
		this._trueMpr += i;
		this._mpr = (short) Math.max(0, this._trueMpr);
	}

	public short _originalHpr = 0; // ● オリジナルCON HPR

	public short getOriginalHpr() {

		return this._originalHpr;
	}

	public short _originalMpr = 0; // ● オリジナルWIS MPR

	public short getOriginalMpr() {

		return this._originalMpr;
	}

	private boolean _mpRegenActive;
	private boolean _mpReductionActiveByAwake;
	private boolean _hpRegenActive;

	private int _hpRegenType = 0;
	private int _hpRegenState = 4;

	public int getHpRegenState() {
		return this._hpRegenState;
	}

	public void set_hpRegenType(final int hpRegenType) {
		this._hpRegenType = hpRegenType;
	}

	public int hpRegenType() {
		return this._hpRegenType;
	}

	private int regenMax() {
		final int lvlTable[] = new int[] { 30, 25, 20, 16, 14, 12, 11, 10, 9, 3, 2 };

		int regenLvl = Math.min(10, getLevel());
		if ((30 <= getLevel()) && isKnight()) {
			regenLvl = 11;
		}
		return lvlTable[regenLvl - 1] << 2;
	}

	/**
	 * HP回復成立
	 * 
	 * @return
	 */
	public boolean isRegenHp() {
		if (!_hpRegenActive) {
			return false;
		}
		if (hasSkillEffect(EXOTIC_VITALIZE) || hasSkillEffect(ADDITIONAL_FIRE)) {
			return _hpRegenType >= regenMax();
		}
		if (120 <= _inventory.getWeight240()) {
			return false;
		}
		if ((_food < 3)) {
			return false;
		}
		return _hpRegenType >= regenMax();
	}

	private int _mpRegenType = 0;
	private int _mpRegenState = 4;

	public int getMpRegenState() {
		return this._mpRegenState;
	}

	public void set_mpRegenType(final int hpmpRegenType) {
		this._mpRegenType = hpmpRegenType;
	}

	public int mpRegenType() {
		return this._mpRegenType;
	}

	/**
	 * MP回復成立
	 * 
	 * @return
	 */
	public boolean isRegenMp() {
		if (!this._mpRegenActive) {
			return false;
		}
		if (this.hasSkillEffect(EXOTIC_VITALIZE) || this.hasSkillEffect(ADDITIONAL_FIRE)) {
			return this._mpRegenType >= 64;
		}
		if (120 <= this._inventory.getWeight240()) {
			return false;
		}
		if ((this._food < 3)) {
			return false;
		}
		// 法師加速
		if (this.isWizard()) {
			return this._mpRegenType >= 40;
		}
		return this._mpRegenType >= 64;
	}

	// HP自然回復 MP自然回復

	/** 無動作 */
	public static final int REGENSTATE_NONE = 4;

	/** 移動中 */
	public static final int REGENSTATE_MOVE = 2;

	/** 攻擊中 */
	public static final int REGENSTATE_ATTACK = 1;

	public void setRegenState(final int state) {
		this._mpRegenState = state;
		this._hpRegenState = state;
	}

	/**
	 * HP自然回復啟用
	 */
	public void startHpRegeneration() {
		if (!this._hpRegenActive) {
			this._hpRegenActive = true;
		}
	}

	/**
	 * HP自然回復停止
	 */
	public void stopHpRegeneration() {
		if (this._hpRegenActive) {
			this._hpRegenActive = false;
		}
	}

	/**
	 * HP自然回復狀態
	 * 
	 * @return
	 */
	public boolean getHpRegeneration() {
		return _hpRegenActive;
	}

	/**
	 * MP自然回復啟用
	 */
	public void startMpRegeneration() {
		if (!this._mpRegenActive) {
			this._mpRegenActive = true;
		}
	}

	/**
	 * MP自然回復停止
	 */
	public void stopMpRegeneration() {
		if (this._mpRegenActive) {
			this._mpRegenActive = false;
		}
	}

	/**
	 * MP自然回復狀態
	 * 
	 * @return
	 */
	public boolean getMpRegeneration() {
		return _mpRegenActive;
	}

	// 龍騎士 覺醒技能 MP自然減少時間
	public static final int INTERVAL_BY_AWAKE = 4;// 秒

	// 龍騎士覺醒技能 MP自然減少計算時間
	private int _awakeMprTime = 0;// 秒

	/**
	 * 龍騎士覺醒技能 MP自然減少處理時間
	 * 
	 * @return
	 */
	public int get_awakeMprTime() {
		return _awakeMprTime;
	}

	/**
	 * 設置龍騎士覺醒技能 MP自然減少處理時間
	 * 
	 * @param awakeMprTime
	 */
	public void set_awakeMprTime(final int awakeMprTime) {
		this._awakeMprTime = awakeMprTime;
	}

	/**
	 * 龍騎士 覺醒技能 MP自然減少啟用
	 */
	public void startMpReductionByAwake() {
		if (!this._mpReductionActiveByAwake) {
			this.set_awakeMprTime(INTERVAL_BY_AWAKE);
			this._mpReductionActiveByAwake = true;
		}
	}

	/**
	 * 龍騎士 覺醒技能 MP自然減少停止
	 */
	public void stopMpReductionByAwake() {
		if (this._mpReductionActiveByAwake) {
			this.set_awakeMprTime(0);
			this._mpReductionActiveByAwake = false;
		}
	}

	/**
	 * 是否在龍騎士 覺醒技能 MP自然減少中
	 * 
	 * @return
	 */
	public boolean isMpReductionActiveByAwake() {
		return _mpReductionActiveByAwake;
	}

	private int _awakeSkillId = 0;// 龍騎士 覺醒技能編號

	/**
	 * 龍騎士 覺醒技能編號
	 * 
	 * @return
	 */
	public int getAwakeSkillId() {
		return this._awakeSkillId;
	}

	/**
	 * 龍騎士 覺醒技能編號
	 * 
	 * @param i
	 */
	public void setAwakeSkillId(final int i) {
		this._awakeSkillId = i;
	}

	/**
	 * 加入PC 可見物更新處理清單
	 */
	public void startObjectAutoUpdate() {
		this.removeAllKnownObjects();
	}

	/**
	 * 移出各種處理清單
	 */
	public void stopEtcMonitor() {
		// 移出PC 鬼魂模式處理清單
		this.set_ghostTime(-1);
		this.setGhost(false);
		this.setGhostCanTalk(true);
		this.setReserveGhost(false);

		// 移出龍騎士覺醒技能MP自然減少處理
		stopMpReductionByAwake();

		if (ServerUseMapTimer.MAP.get(this) != null) {
			// 移出計時地圖處理清單
			ServerUseMapTimer.MAP.remove(this);
		}

		// 移出在線獎勵清單
		OnlineGiftSet.remove(this);

		// 清空清單資料
		ListMapUtil.clear(_skillList);
		ListMapUtil.clear(_sellList);
		ListMapUtil.clear(_buyList);
		ListMapUtil.clear(_trade_items);
	}

	private int _old_lawful;

	/**
	 * 原始Lawful
	 * 
	 * @return
	 */
	public int getLawfulo() {
		return _old_lawful;
	}

	/**
	 * 更新Lawful
	 */
	public void onChangeLawful() {
		if (_old_lawful != getLawful()) {
			_old_lawful = getLawful();
			sendPacketsAll(new S_Lawful(this));
			// 戰鬥特化效果
			lawfulUpdate();
		}
	}

	private boolean _jl1 = false;// 正義的守護 Lv.1
	private boolean _jl2 = false;// 正義的守護 Lv.2
	private boolean _jl3 = false;// 正義的守護 Lv.3
	private boolean _el1 = false;// 邪惡的守護 Lv.1
	private boolean _el2 = false;// 邪惡的守護 Lv.2
	private boolean _el3 = false;// 邪惡的守護 Lv.3

	/**
	 * TODO 戰鬥特化<BR>
	 */
	private void lawfulUpdate() {
		int l = getLawful();

		if (l >= 10000 && l <= 19999) {
			if (!_jl1) {
				overUpdate();
				_jl1 = true;
				sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.JUSTICE_L1, 1));
				sendPackets(new S_OwnCharAttrDef(this));
				sendPackets(new S_SPMR(this));
			}

		} else if (l >= 20000 && l <= 29999) {
			if (!_jl2) {
				overUpdate();
				_jl2 = true;
				sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.JUSTICE_L2, 1));
				sendPackets(new S_OwnCharAttrDef(this));
				sendPackets(new S_SPMR(this));
			}

		} else if (l >= 30000 && l <= 39999) {
			if (!_jl3) {
				overUpdate();
				_jl3 = true;
				sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.JUSTICE_L3, 1));
				sendPackets(new S_OwnCharAttrDef(this));
				sendPackets(new S_SPMR(this));
			}

		} else if (l >= -19999 && l <= -10000) {
			if (!_el1) {
				overUpdate();
				_el1 = true;
				sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.EVIL_L1, 1));
				sendPackets(new S_SPMR(this));
			}

		} else if (l >= -29999 && l <= -20000) {
			if (!_el2) {
				overUpdate();
				_el2 = true;
				sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.EVIL_L2, 1));
				sendPackets(new S_SPMR(this));
			}

		} else if (l >= -39999 && l <= -30000) {
			if (!_el3) {
				overUpdate();
				_el3 = true;
				sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.EVIL_L3, 1));
				sendPackets(new S_SPMR(this));
			}

		} else {
			if (overUpdate()) {
				sendPackets(new S_OwnCharAttrDef(this));
				sendPackets(new S_SPMR(this));
			}
		}
	}

	/**
	 * TODO 戰鬥特化<BR>
	 * 
	 * @return
	 */
	private boolean overUpdate() {
		if (_jl1) {
			_jl1 = false;
			sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.JUSTICE_L1, 0));
			return true;

		} else if (_jl2) {
			_jl2 = false;
			sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.JUSTICE_L2, 0));
			return true;

		} else if (_jl3) {
			_jl3 = false;
			sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.JUSTICE_L3, 0));
			return true;

		} else if (_el1) {
			_el1 = false;
			sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.EVIL_L1, 0));
			return true;

		} else if (_el2) {
			_el2 = false;
			sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.EVIL_L2, 0));
			return true;

		} else if (_el3) {
			_el3 = false;
			sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.EVIL_L3, 0));
			return true;
		}
		return false;
	}

	/**
	 * TODO 戰鬥特化<BR>
	 * <FONT COLOR="#0000ff">遭遇的守護 </FONT>20級以下角色
	 * 被超過10級以上的玩家攻擊而死亡時，不會失去經驗值，也不會掉落物品<BR>
	 * 
	 * @return
	 */
	private boolean isEncounter() {
		if (getLevel() <= ConfigOther.ENCOUNTER_LV) {
			return true;
		}
		return false;
	}

	/**
	 * TODO 戰鬥特化<BR>
	 * <FONT COLOR="#0000ff">正義的守護 Lv.1 </FONT>善惡值 10,000 ~ 19,999 (防御：-2 /
	 * 魔防+3)<BR>
	 * <FONT COLOR="#0000ff">正義的守護 Lv.2 </FONT>善惡值 20,000 ~ 29,999 (防御：-4 /
	 * 魔防+6)<BR>
	 * <FONT COLOR="#0000ff">正義的守護 Lv.3 </FONT>善惡值 30,000 ~ 32,767 (防御：-6 /
	 * 魔防+9)<BR>
	 * <FONT COLOR="#0000ff">邪惡的守護 Lv.1 </FONT>善惡值 -10,000 ~ -19,999 (近/遠距離攻擊力+1 /
	 * 魔攻+1)<BR>
	 * <FONT COLOR="#0000ff">邪惡的守護 Lv.2 </FONT>善惡值 -20,000 ~ -29,999 (近/遠距離攻擊力+3 /
	 * 魔攻+2)<BR>
	 * <FONT COLOR="#0000ff">邪惡的守護 Lv.3 </FONT>善惡值 -30,000 ~ -32,767 (近/遠距離攻擊力+5 /
	 * 魔攻+3)<BR>
	 * <FONT COLOR="#0000ff">遭遇的守護 </FONT>20級以下角色
	 * 被超過10級以上的玩家攻擊而死亡時，不會失去經驗值，也不會掉落物品<BR>
	 */
	public int guardianEncounter() {
		if (_jl1) {
			return 0;

		} else if (_jl2) {
			return 1;

		} else if (_jl3) {
			return 2;

		} else if (_el1) {
			return 3;

		} else if (_el2) {
			return 4;

		} else if (_el3) {
			return 5;
		}
		return -1;
	}

	private long _old_exp;

	/**
	 * 原始Lawful
	 * 
	 * @return
	 */
	public long getExpo() {
		return _old_exp;
	}

	/**
	 * 更新EXP
	 */
	public void onChangeExp() {
		if (_old_exp != getExp()) {
			_old_exp = getExp();

			final int level = ExpTable.getLevelByExp(getExp());
			final int char_level = getLevel();
			final int gap = level - char_level;

			if (gap == 0) {
				if (level <= 127) {
					sendPackets(new S_Exp(this));
				} else {
					sendPackets(new S_OwnCharStatus(this));
				}
				return;
			}

			if (gap > 0) {
				levelUp(gap);

			} else if (gap < 0) {
				levelDown(gap);
			}

			if (getLevel() > 20) {// LOLI 戰鬥特化
				sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.ENCOUNTER, 0));

			} else {
				sendPackets(new S_PacketBoxProtection(S_PacketBoxProtection.ENCOUNTER, 1));
			}
		}
	}

	/**
	 * TODO 接觸資訊
	 */
	@Override
	public void onPerceive(final L1PcInstance perceivedFrom) {
		try {
			if (this.isGmInvis() || this.isGhost() || this.isInvisble()) {
				return;
			}

			// 副本ID不相等 不相護顯示
			if (perceivedFrom.get_showId() != this.get_showId()) {
				return;
			}

			perceivedFrom.addKnownObject(this);
			// 發送自身資訊給予接觸人物
			perceivedFrom.sendPackets(new S_OtherCharPacks(this));

			// 隊伍成員HP狀態發送
			if (this.isInParty() && this.getParty().isMember(perceivedFrom)) {
				perceivedFrom.sendPackets(new S_HPMeter(this));
			}

			if (_isFishing) {
				perceivedFrom.sendPackets(new S_Fishing(getId(), ActionCodes.ACTION_Fishing, get_fishX(), get_fishY()));
			}

			if (this.isPrivateShop()) {
				final int mapId = this.getMapId();
				if ((mapId != 340) && (mapId != 350) && (mapId != 360) && (mapId != 370)) {
					this.getSellList().clear();
					this.getBuyList().clear();

					this.setPrivateShop(false);
					this.sendPacketsAll(new S_DoActionGFX(this.getId(), ActionCodes.ACTION_Idle));

				} else {
					perceivedFrom.sendPackets(new S_DoActionShop(this.getId(), this.getShopChat()));
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 清除離開可視範圍物件
	 */
	private void removeOutOfRangeObjects() {
		for (final L1Object known : getKnownObjects()) {
			if (known == null) {
				continue;
			}

			if (Config.PC_RECOGNIZE_RANGE == -1) {
				if (!getLocation().isInScreen(known.getLocation())) { // 画面外
					removeKnownObject(known);
					sendPackets(new S_RemoveObject(known));
				}

			} else {
				if (getLocation().getTileLineDistance(known.getLocation()) > Config.PC_RECOGNIZE_RANGE) {
					removeKnownObject(known);
					sendPackets(new S_RemoveObject(known));
				}
			}
		}
	}

	/**
	 * 可見物更新處理
	 */
	public void updateObject() {
		if (getOnlineStatus() != 1) {
			return;
		}
		removeOutOfRangeObjects();

		// 指定可視範圍資料更新
		for (final L1Object visible : World.get().getVisibleObjects(this, Config.PC_RECOGNIZE_RANGE)) {
			if (visible instanceof L1MerchantInstance) {// 對話NPC
				if (!knownsObject(visible)) {
					final L1MerchantInstance npc = (L1MerchantInstance) visible;
					// 未認知物件 執行物件封包發送
					npc.onPerceive(this);
				}
				continue;
			}

			if (visible instanceof L1DwarfInstance) {// 倉庫NPC
				if (!knownsObject(visible)) {
					final L1DwarfInstance npc = (L1DwarfInstance) visible;
					// 未認知物件 執行物件封包發送
					npc.onPerceive(this);
				}
				continue;
			}

			if (visible instanceof L1FieldObjectInstance) {// 景觀
				if (!knownsObject(visible)) {
					final L1FieldObjectInstance npc = (L1FieldObjectInstance) visible;
					// 未認知物件 執行物件封包發送
					npc.onPerceive(this);
				}
				continue;
			}

			// 副本ID不相等 不相護顯示
			if (visible.get_showId() != get_showId()) {
				continue;
			}

			if (!knownsObject(visible)) {
				// 未認知物件 執行物件封包發送
				visible.onPerceive(this);

			} else {
				if (visible instanceof L1NpcInstance) {
					final L1NpcInstance npc = (L1NpcInstance) visible;
					if (getLocation().isInScreen(npc.getLocation()) && (npc.getHiddenStatus() != 0)) {
						npc.approachPlayer(this);
					}
				}
			}

			// 一般人物 HP可見設置
			if (isHpBarTarget(visible)) {
				final L1Character cha = (L1Character) visible;
				cha.broadcastPacketHP(this);
			}

			// GM HP 查看設置
			if (hasSkillEffect(GMSTATUS_HPBAR)) {
				if (isGmHpBarTarget(visible)) {
					final L1Character cha = (L1Character) visible;
					cha.broadcastPacketHP(this);
				}
			}
		}
	}

	/**
	 * 可以觀看HP的對象(特別定義)
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isHpBarTarget(final L1Object obj) {
		// 所在地圖位置
		switch (this.getMapId()) {
		case 400:// 大洞穴/大洞穴抵抗軍/隱遁者地區
			if (obj instanceof L1FollowerInstance) {
				final L1FollowerInstance follower = (L1FollowerInstance) obj;
				if (follower.getMaster().equals(this)) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	/**
	 * GM HPBAR 可以觀看HP的對象
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isGmHpBarTarget(final L1Object obj) {
		if (obj instanceof L1MonsterInstance) {
			return true;
		}
		if (obj instanceof L1PcInstance) {
			return true;
		}
		if (obj instanceof L1SummonInstance) {
			return true;
		}
		if (obj instanceof L1PetInstance) {
			return true;
		}
		if (obj instanceof L1FollowerInstance) {
			return true;
		}
		return false;
	}

	private void sendVisualEffect() {
		int poisonId = 0;
		if (this.getPoison() != null) { // 毒状態
			poisonId = this.getPoison().getEffectId();
		}
		if (this.getParalysis() != null) { // 麻痺状態
			// 麻痺エフェクトを優先して送りたい為、poisonIdを上書き。
			poisonId = this.getParalysis().getEffectId();
		}
		if (poisonId != 0) { // このifはいらないかもしれない
			this.sendPacketsAll(new S_Poison(this.getId(), poisonId));
		}
	}

	public void sendVisualEffectAtLogin() {
		this.sendVisualEffect();
	}

	private boolean _isCHAOTIC = false;

	public boolean isCHAOTIC() {
		return this._isCHAOTIC;
	}

	public void setCHAOTIC(final boolean flag) {
		this._isCHAOTIC = flag;
	}

	public void sendVisualEffectAtTeleport() {
		if (this.isDrink()) { // 醉酒效果
			this.sendPackets(new S_Liquor(this.getId()));
		}
		if (this.isCHAOTIC()) { // 混亂效果
			this.sendPackets(new S_Liquor(this.getId(), 2));
		}
		this.sendVisualEffect();
	}

	// 可用技能編號列表
	private ArrayList<Integer> _skillList = new ArrayList<Integer>();

	/**
	 * 加入技能編號列表
	 * 
	 * @param skillid
	 */
	public void setSkillMastery(final int skillid) {
		if (!this._skillList.contains(new Integer(skillid))) {
			this._skillList.add(new Integer(skillid));
		}
	}

	/**
	 * 移出技能編號列表
	 * 
	 * @param skillid
	 */
	public void removeSkillMastery(final int skillid) {
		if (this._skillList.contains(new Integer(skillid))) {
			this._skillList.remove(new Integer(skillid));
		}
	}

	/**
	 * 傳回是否具有該技能使用權
	 * 
	 * @param skillid
	 * @return
	 */
	public boolean isSkillMastery(final int skillid) {
		return this._skillList.contains(new Integer(skillid));
	}

	/**
	 * 清空
	 */
	public void clearSkillMastery() {
		this._skillList.clear();
	}

	/**
	 * TODO 起始設置
	 */
	public L1PcInstance() {
		_accessLevel = 0;
		_currentWeapon = 0;
		_inventory = new L1PcInventory(this);
		_dwarf = new L1DwarfInventory(this);
		_dwarfForElf = new L1DwarfForElfInventory(this);
		_quest = new L1PcQuest(this);
		_action = new L1ActionPc(this);
		_actionPet = new L1ActionPet(this);
		_actionSummon = new L1ActionSummon(this);
		_equipSlot = new L1EquipmentSlot(this);
	}

	/**
	 * 娃娃跟隨主人變更移動/速度狀態
	 */
	public void setNpcSpeed() {
		try {
			// 取回娃娃
			if (!getDolls().isEmpty()) {
				for (final Object obj : getDolls().values().toArray()) {
					final L1DollInstance doll = (L1DollInstance) obj;
					if (doll != null) {
						doll.setNpcMoveSpeed();
					}
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void setCurrentHp(final int i) {
		int currentHp = Math.min(i, this.getMaxHp());

		if (this.getCurrentHp() == currentHp) {
			return;
		}

		if (currentHp <= 0) {
			if (this.isGm()) {
				currentHp = this.getMaxHp();

			} else {
				if (!this.isDead()) {
					this.death(null); // HP小於1死亡
				}
			}
		}

		this.setCurrentHpDirect(currentHp);
		this.sendPackets(new S_HPUpdate(currentHp, this.getMaxHp()));
		if (this.isInParty()) { // 隊伍狀態
			this.getParty().updateMiniHP(this);
		}
	}

	@Override
	public void setCurrentMp(final int i) {
		int currentMp = Math.min(i, this.getMaxMp());

		if (this.getCurrentMp() == currentMp) {
			return;
		}

		this.setCurrentMpDirect(currentMp);

		this.sendPackets(new S_MPUpdate(currentMp, this.getMaxMp()));
	}

	@Override
	public L1PcInventory getInventory() {
		return this._inventory;
	}

	public L1DwarfInventory getDwarfInventory() {
		return this._dwarf;
	}

	public L1DwarfForElfInventory getDwarfForElfInventory() {
		return this._dwarfForElf;
	}

	public boolean isGmInvis() {
		return this._gmInvis;
	}

	public void setGmInvis(final boolean flag) {
		this._gmInvis = flag;
	}

	public int getCurrentWeapon() {
		return this._currentWeapon;
	}

	public void setCurrentWeapon(final int i) {
		this._currentWeapon = i;
	}

	/**
	 * 0:王族 1:騎士 2:精靈 3:法師 4:黑妖 5:龍騎 6:幻術
	 * 
	 * @return
	 */
	public int getType() {
		return this._type;
	}

	/**
	 * 0:王族 1:騎士 2:精靈 3:法師 4:黑妖 5:龍騎 6:幻術
	 * 
	 * @param i
	 */
	public void setType(final int i) {
		this._type = i;
	}

	public short getAccessLevel() {
		return this._accessLevel;
	}

	public void setAccessLevel(final short i) {
		this._accessLevel = i;
	}

	public int getClassId() {
		return this._classId;
	}

	public void setClassId(final int i) {
		this._classId = i;
		this._classFeature = L1ClassFeature.newClassFeature(i);
	}

	private L1ClassFeature _classFeature = null;

	public L1ClassFeature getClassFeature() {
		return _classFeature;
	}

	@Override
	public synchronized long getExp() {
		return _exp;
	}

	@Override
	public synchronized void setExp(final long i) {
		_exp = i;
	}

	private int _PKcount; // ● PKカウント

	public int get_PKcount() {
		return this._PKcount;
	}

	public void set_PKcount(final int i) {
		this._PKcount = i;
	}

	private int _PkCountForElf; // ● PKカウント(エルフ用)

	public int getPkCountForElf() {
		return this._PkCountForElf;
	}

	public void setPkCountForElf(final int i) {
		this._PkCountForElf = i;
	}

	private int _clanid; // 血盟ID

	public int getClanid() {
		return this._clanid;
	}

	public void setClanid(final int i) {
		this._clanid = i;
	}

	private String clanname; // 血盟名稱

	public String getClanname() {
		return this.clanname;
	}

	public void setClanname(final String s) {
		this.clanname = s;
	}

	/**
	 * 血盟資料
	 * 
	 * @return
	 */
	public L1Clan getClan() {
		return WorldClan.get().getClan(this.getClanname());
	}

	private int _clanRank; // ● クラン内のランク(血盟君主、ガーディアン、一般、見習い)

	/**
	 * 血盟階級
	 * 
	 * @return
	 */
	public int getClanRank() {
		return this._clanRank;
	}

	public void setClanRank(final int i) {
		this._clanRank = i;
	}

	private byte _sex; // ● 性別

	/**
	 * 性別
	 * 
	 * @return
	 */
	public byte get_sex() {
		return this._sex;
	}

	/**
	 * 性別
	 * 
	 * @param i
	 */
	public void set_sex(final int i) {
		this._sex = (byte) i;
	}

	public boolean isGm() {
		return this._gm;
	}

	public void setGm(final boolean flag) {
		this._gm = flag;
	}

	public boolean isMonitor() {
		return this._monitor;
	}

	public void setMonitor(final boolean flag) {
		this._monitor = flag;
	}

	private L1PcInstance getStat() {
		return null;
	}

	public void reduceCurrentHp(final double d, final L1Character l1character) {
		this.getStat().reduceCurrentHp(d, l1character);
	}

	/**
	 * 指定されたプレイヤー群にログアウトしたことを通知する
	 *
	 * @param playersList 通知するプレイヤーの配列
	 */
	private void notifyPlayersLogout(final List<L1PcInstance> playersArray) {
		for (final L1PcInstance player : playersArray) {
			if (player.knownsObject(this)) {
				player.removeKnownObject(this);
				player.sendPackets(new S_RemoveObject(this));
			}
		}
	}

	public void logout() {
		// 保留技能紀錄
		CharBuffReading.get().deleteBuff(this);
		CharBuffReading.get().saveBuff(this);

		// 解除舊座標障礙宣告
		this.getMap().setPassable(this.getLocation(), true);

		if (this.getClanid() != 0) {
			final L1Clan clan = WorldClan.get().getClan(this.getClanname());
			if (clan != null) {
				if (clan.getWarehouseUsingChar() == this.getId()) {
					clan.setWarehouseUsingChar(0); // 解除血盟倉庫目前使用者
				}
			}
		}
		this.notifyPlayersLogout(this.getKnownPlayers());

		// 正在參加副本
		if (this.get_showId() != -1) {
			// 副本編號 是執行中副本
			if (WorldQuest.get().isQuest(this.get_showId())) {
				// 移出副本
				WorldQuest.get().remove(this.get_showId(), this);
			}
		}
		// 重置副本編號
		this.set_showId(-1);

		World.get().removeVisibleObject(this);
		World.get().removeObject(this);
		this.notifyPlayersLogout(World.get().getRecognizePlayer(this));

		// this._inventory.clearItems();
		// this._dwarf.clearItems();

		this.removeAllKnownObjects();
		this.stopHpRegeneration();
		this.stopMpRegeneration();
		this.setDead(true); // 使い方おかしいかもしれないけど、ＮＰＣに消滅したことをわからせるため
		this.setNetConnection(null);
		this.setPacketOutput(null);
	}

	public ClientExecutor getNetConnection() {
		return this._netConnection;
	}

	public void setNetConnection(final ClientExecutor clientthread) {
		this._netConnection = clientthread;
	}

	/**
	 * 是否再隊伍中
	 * 
	 * @return
	 */
	public boolean isInParty() {
		return this.getParty() != null;
	}

	/**
	 * 傳回隊伍
	 * 
	 * @return
	 */
	public L1Party getParty() {
		return this._party;
	}

	/**
	 * 設置隊伍
	 * 
	 * @param p
	 */
	public void setParty(final L1Party p) {
		this._party = p;
	}

	public boolean isInChatParty() {
		return this.getChatParty() != null;
	}

	public L1ChatParty getChatParty() {
		return this._chatParty;
	}

	public void setChatParty(final L1ChatParty cp) {
		this._chatParty = cp;
	}

	public int getPartyID() {
		return this._partyID;
	}

	public void setPartyID(final int partyID) {
		this._partyID = partyID;
	}

	public int getTradeID() {
		return this._tradeID;
	}

	public void setTradeID(final int tradeID) {
		this._tradeID = tradeID;
	}

	public void setTradeOk(final boolean tradeOk) {
		this._tradeOk = tradeOk;
	}

	public boolean getTradeOk() {
		return this._tradeOk;
	}

	/**
	 * 傳回暫時紀錄的objid
	 * 
	 * @return
	 */
	public int getTempID() {
		return this._tempID;
	}

	/**
	 * 設置暫時紀錄的objid
	 * 
	 * @param tempID
	 */
	public void setTempID(final int tempID) {
		this._tempID = tempID;
	}

	/**
	 * 是否為傳送狀態中
	 * 
	 * @return
	 */
	public boolean isTeleport() {
		return this._isTeleport;
	}

	/**
	 * 設置傳送狀態中
	 * 
	 * @param flag
	 */
	public void setTeleport(final boolean flag) {
		if (flag) {
			this.setNowTarget(null);// 解除目前攻擊目標設置
		}
		this._isTeleport = flag;
	}

	/**
	 * 醉酒狀態
	 * 
	 * @return
	 */
	public boolean isDrink() {
		return this._isDrink;
	}

	/**
	 * 醉酒狀態
	 * 
	 * @param flag
	 */
	public void setDrink(final boolean flag) {
		this._isDrink = flag;
	}

	public boolean isGres() {
		return this._isGres;
	}

	public void setGres(final boolean flag) {
		this._isGres = flag;
	}

	/**
	 * 紅名狀態
	 * 
	 * @return
	 */
	public boolean isPinkName() {
		return this._isPinkName;
	}

	/**
	 * 紅名狀態
	 * 
	 * @param flag
	 */
	public void setPinkName(final boolean flag) {
		this._isPinkName = flag;
	}

	// 賣出物品清單
	private ArrayList<L1PrivateShopSellList> _sellList = new ArrayList<L1PrivateShopSellList>();

	/**
	 * 傳回賣出物品清單
	 * 
	 * @return
	 */
	public ArrayList<L1PrivateShopSellList> getSellList() {
		return this._sellList;
	}

	// 回收物品清單
	private ArrayList<L1PrivateShopBuyList> _buyList = new ArrayList<L1PrivateShopBuyList>();

	/**
	 * 傳回回收物品清單
	 * 
	 * @return
	 */
	public ArrayList<L1PrivateShopBuyList> getBuyList() {
		return this._buyList;
	}

	private byte[] _shopChat;

	public void setShopChat(final byte[] chat) {
		this._shopChat = chat;
	}

	public byte[] getShopChat() {
		return this._shopChat;
	}

	private boolean _isPrivateShop = false;

	/**
	 * 傳回商店模式
	 * 
	 * @return
	 */
	public boolean isPrivateShop() {
		return this._isPrivateShop;
	}

	/**
	 * 設置商店模式
	 * 
	 * @param flag
	 */
	public void setPrivateShop(final boolean flag) {
		this._isPrivateShop = flag;
	}

	// 正在執行個人商店交易
	private boolean _isTradingInPrivateShop = false;

	/**
	 * 正在執行個人商店交易
	 * 
	 * @return
	 */
	public boolean isTradingInPrivateShop() {
		return this._isTradingInPrivateShop;
	}

	/**
	 * 正在執行個人商店交易
	 * 
	 * @param flag
	 */
	public void setTradingInPrivateShop(final boolean flag) {
		this._isTradingInPrivateShop = flag;
	}

	private int _partnersPrivateShopItemCount = 0; // 出售物品種類數量

	/**
	 * 傳回出售物品種類數量
	 * 
	 * @return
	 */
	public int getPartnersPrivateShopItemCount() {
		return this._partnersPrivateShopItemCount;
	}

	/**
	 * 設置出售物品種類數量
	 * 
	 * @param i
	 */
	public void setPartnersPrivateShopItemCount(final int i) {
		this._partnersPrivateShopItemCount = i;
	}

	private EncryptExecutor _out;// 封包加密管理

	/**
	 * 設置封包加密管理
	 * 
	 * @param out
	 */
	public void setPacketOutput(final EncryptExecutor out) {
		this._out = out;
	}

	/**
	 * 發送單體封包
	 * 
	 * @param packet 封包
	 */
	public void sendPackets(final ServerBasePacket packet) {
		if (this._out == null) {
			return;
		}
		// System.out.println(packet.toString());
		try {
			this._out.encrypt(packet);

		} catch (final Exception e) {
			this.logout();
			this.close();
		}
	}

	/**
	 * 發送單體封包 與可見範圍發送封包
	 * 
	 * @param packet 封包
	 */
	public void sendPacketsAll(final ServerBasePacket packet) {
		if (this._out == null) {
			return;
		}

		try {
			// 自己
			this._out.encrypt(packet);
			if (!this.isGmInvis() && !this.isInvisble()) {
				this.broadcastPacketAll(packet);
			}

		} catch (final Exception e) {
			this.logout();
			this.close();
		}
	}

	/**
	 * 發送單體封包 與指定範圍發送封包(範圍8)
	 * 
	 * @param packet 封包
	 */
	public void sendPacketsX8(final ServerBasePacket packet) {
		if (this._out == null) {
			return;
		}

		try {
			// 自己
			this._out.encrypt(packet);
			if (!this.isGmInvis() && !this.isInvisble()) {
				this.broadcastPacketX8(packet);
			}

		} catch (final Exception e) {
			this.logout();
			this.close();
		}
	}

	/**
	 * 發送單體封包 與指定範圍發送封包(範圍10)
	 * 
	 * @param packet 封包
	 */
	public void sendPacketsX10(final ServerBasePacket packet) {
		if (this._out == null) {
			return;
		}

		try {
			// 自己
			this._out.encrypt(packet);
			if (!this.isGmInvis() && !this.isInvisble()) {
				this.broadcastPacketX10(packet);
			}

		} catch (final Exception e) {
			this.logout();
			this.close();
		}
	}

	/**
	 * 發送單體封包 與可見指定範圍發送封包
	 * 
	 * @param packet 封包
	 * @param r      範圍
	 */
	public void sendPacketsXR(final ServerBasePacket packet, final int r) {
		if (this._out == null) {
			return;
		}

		try {
			// 自己
			this._out.encrypt(packet);
			if (!this.isGmInvis() && !this.isInvisble()) {
				this.broadcastPacketXR(packet, r);
			}

		} catch (final Exception e) {
			this.logout();
			this.close();
		}
	}

	/**
	 * 關閉連線線程
	 */
	private void close() {
		try {
			this.getNetConnection().close();
		} catch (final Exception e) {

		}
	}

	/**
	 * 對該物件攻擊的調用
	 * 
	 * @param attacker 攻擊方
	 */
	@Override
	public void onAction(final L1PcInstance attacker) {
		// NullPointerException回避。onActionの引数の型はL1Characterのほうが良い？
		if (attacker == null) {
			return;
		}
		// テレポート処理中
		if (this.isTeleport()) {
			return;
		}

		// 雙方之一 位於安全區域 僅送出動作資訊
		if (this.isSafetyZone() || attacker.isSafetyZone()) {
			// 攻撃モーション送信
			final L1AttackMode attack_mortion = new L1AttackPc(attacker, this);
			attack_mortion.action();
			return;
		}

		// 禁止PK服務器 僅送出動作資訊
		if (this.checkNonPvP(this, attacker) == true) {
			final L1AttackMode attack_mortion = new L1AttackPc(attacker, this);
			attack_mortion.action();
			return;
		}

		if ((this.getCurrentHp() > 0) && !this.isDead()) {
			// 攻擊行為產生解除隱身
			attacker.delInvis();

			boolean isCounterBarrier = false;
			// 開始計算攻擊
			final L1AttackMode attack = new L1AttackPc(attacker, this);
			if (attack.calcHit()) {
				if (this.hasSkillEffect(COUNTER_BARRIER)) {
					final L1Magic magic = new L1Magic(this, attacker);
					final boolean isProbability = magic.calcProbabilityMagic(COUNTER_BARRIER);
					final boolean isShortDistance = attack.isShortDistance();
					if (isProbability && isShortDistance) {
						isCounterBarrier = true;
					}
				}
				if (!isCounterBarrier) {
					attacker.setPetTarget(this);

					attack.calcDamage();
					attack.calcStaffOfMana();
					attack.addChaserAttack();
				}
			}
			if (isCounterBarrier) {
				// attack.actionCounterBarrier();
				attack.commitCounterBarrier();

			} else {
				attack.action();
				attack.commit();
			}
		}
	}

	/**
	 * 檢查是否可以攻擊
	 * 
	 * @param pc
	 * @param target
	 * @return
	 */
	public boolean checkNonPvP(final L1PcInstance pc, final L1Character target) {
		L1PcInstance targetpc = null;
		if (target instanceof L1PcInstance) {
			targetpc = (L1PcInstance) target;

		} else if (target instanceof L1PetInstance) {
			targetpc = (L1PcInstance) ((L1PetInstance) target).getMaster();

		} else if (target instanceof L1SummonInstance) {
			targetpc = (L1PcInstance) ((L1SummonInstance) target).getMaster();
		}
		if (targetpc == null) {
			return false; // 相手がPC、サモン、ペット以外
		}

		if (!ConfigAlt.ALT_NONPVP) { // Non-PvP設定
			if (this.getMap().isCombatZone(this.getLocation())) {
				return false;
			}

			// 取回全部戰爭清單
			for (final L1War war : WorldWar.get().getWarList()) {
				if ((pc.getClanid() != 0) && (targetpc.getClanid() != 0)) { // 共にクラン所属中
					final boolean same_war = war.checkClanInSameWar(pc.getClanname(), targetpc.getClanname());
					if (same_war == true) { // 同じ戦争に参加中
						return false;
					}
				}
			}
			// Non-PvP設定でも戦争中は布告なしで攻撃可能
			if (target instanceof L1PcInstance) {
				final L1PcInstance targetPc = (L1PcInstance) target;
				if (this.isInWarAreaAndWarTime(pc, targetPc)) {
					return false;
				}
			}
			return true;

		} else {
			return false;
		}
	}

	/**
	 * 戰爭旗幟座標內
	 * 
	 * @param pc
	 * @param target
	 * @return
	 */
	private boolean isInWarAreaAndWarTime(final L1PcInstance pc, final L1PcInstance target) {
		// pcとtargetが戦争中に戦争エリアに居るか
		final int castleId = L1CastleLocation.getCastleIdByArea(pc);
		final int targetCastleId = L1CastleLocation.getCastleIdByArea(target);
		if ((castleId != 0) && (targetCastleId != 0) && (castleId == targetCastleId)) {
			if (ServerWarExecutor.get().isNowWar(castleId)) {
				return true;
			}
		}
		return false;
	}

	private static boolean _debug = Config.DEBUG;

	/**
	 * 設置 寵物/召換獸/分身/護衛 攻擊目標
	 * 
	 * @param target
	 */
	public void setPetTarget(final L1Character target) {
		if (target == null) {
			return;
		}
		if (target.isDead()) {
			return;
		}
		final Map<Integer, L1NpcInstance> petList = this.getPetList();

		// 有寵物元素
		try {
			if (!petList.isEmpty()) {// 有寵物元素
				for (final Iterator<L1NpcInstance> iter = petList.values().iterator(); iter.hasNext();) {
					final L1NpcInstance pet = iter.next();
					if (pet != null) {
						if (pet instanceof L1PetInstance) {// 寵物
							final L1PetInstance pets = (L1PetInstance) pet;
							pets.setMasterTarget(target);

						} else if (pet instanceof L1SummonInstance) {// 召換獸
							final L1SummonInstance summon = (L1SummonInstance) pet;
							summon.setMasterTarget(target);
						}
					}
				}
			}

		} catch (final Exception e) {
			if (_debug) {
				_log.error(e.getLocalizedMessage(), e);
			}
		}

		final Map<Integer, L1IllusoryInstance> illList = this.get_otherList().get_illusoryList();

		// 有分身元素
		try {
			if (!illList.isEmpty()) {// 有分身元素
				// 控制分身攻擊
				if (this.getId() != target.getId()) {
					for (final Iterator<L1IllusoryInstance> iter = illList.values().iterator(); iter.hasNext();) {
						final L1IllusoryInstance ill = iter.next();
						if (ill != null) {
							ill.setLink(target);
						}
					}
				}
			}

		} catch (final Exception e) {
			if (_debug) {
				_log.error(e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 * 解除隱身術/暗隱術
	 */
	public void delInvis() {
		if (this.hasSkillEffect(INVISIBILITY)) { // 隱身術
			this.killSkillEffectTimer(INVISIBILITY);
			this.sendPackets(new S_Invis(this.getId(), 0));
			this.broadcastPacketAll(new S_OtherCharPacks(this));
		}
		if (this.hasSkillEffect(BLIND_HIDING)) { // 暗隱術
			this.killSkillEffectTimer(BLIND_HIDING);
			this.sendPackets(new S_Invis(this.getId(), 0));
			this.broadcastPacketAll(new S_OtherCharPacks(this));
		}
	}

	/**
	 * 解除暗隱術
	 */
	public void delBlindHiding() {
		this.killSkillEffectTimer(BLIND_HIDING);
		this.sendPackets(new S_Invis(this.getId(), 0));
		this.broadcastPacketAll(new S_OtherCharPacks(this));
	}

	/**
	 * 魔法具有属性傷害使用 (魔法抗性處理) attr:0.無属性魔法,1.地魔法,2.火魔法,4.水魔法,8.風魔法 (武器技能使用)
	 * 
	 * @param attacker
	 * @param damage
	 * @param attr
	 */
	public void receiveDamage(final L1Character attacker, double damage, final int attr) {
		final int player_mr = this.getMr();
		final int rnd = _random.nextInt(300) + 1;
		if (player_mr >= rnd) {
			damage /= 2.0;
		}

		int resist = 0;
		switch (attr) {
		case L1Skills.ATTR_EARTH:
			resist = this.getEarth();
			break;

		case L1Skills.ATTR_FIRE:
			resist = this.getFire();
			break;

		case L1Skills.ATTR_WATER:
			resist = this.getWater();
			break;

		case L1Skills.ATTR_WIND:
			resist = this.getWind();
			break;
		}

		int resistFloor = (int) (0.32 * Math.abs(resist));
		if (resist >= 0) {
			resistFloor *= 1;

		} else {
			resistFloor *= -1;
		}

		final double attrDeffence = resistFloor / 32.0;

		double coefficient = (1.0 - attrDeffence + 3.0 / 32.0);// 0.09375

		if (coefficient > 0) {
			damage *= coefficient;
		}
		this.receiveDamage(attacker, damage, false, false);
	}

	/**
	 * 受攻擊mp減少計算
	 * 
	 * @param attacker
	 * @param mpDamage
	 */
	public void receiveManaDamage(final L1Character attacker, final int mpDamage) {
		if ((mpDamage > 0) && !this.isDead()) {
			this.delInvis();
			if (attacker instanceof L1PcInstance) {
				L1PinkName.onAction(this, attacker);
			}
			if ((attacker instanceof L1PcInstance) && ((L1PcInstance) attacker).isPinkName()) {
				// ガードが画面内にいれば、攻撃者をガードのターゲットに設定する
				for (final L1Object object : World.get().getVisibleObjects(attacker)) {
					if (object instanceof L1GuardInstance) {
						final L1GuardInstance guard = (L1GuardInstance) object;
						guard.setTarget(((L1PcInstance) attacker));
					}
				}
			}

			int newMp = this.getCurrentMp() - mpDamage;
			if (newMp > this.getMaxMp()) {
				newMp = this.getMaxMp();
			}
			newMp = Math.max(newMp, 0);

			this.setCurrentMp(newMp);
		}
	}

	public long _oldTime = 0; // 連續魔法減低損傷使用

	private static final Map<Long, Double> _magicDamagerList = new HashMap<Long, Double>();

	/**
	 * 連續魔法減低損傷質預先載入 特殊定義道具 預先載入
	 */
	public static void load() {
		double newdmg = 100.00;
		for (long i = 2000; i > 0; i--) {
			if (i % 100 == 0) {
				newdmg -= 3.33;
			}
			_magicDamagerList.put(i, newdmg);
		}
	}

	/**
	 * 連續魔法減低損傷
	 * 
	 * @param damage
	 * @return
	 */
	public double isMagicDamager(final double damage) {
		final long nowTime = System.currentTimeMillis();
		final long interval = nowTime - this._oldTime;

		double newdmg = 0;
		if (damage < 0) {
			newdmg = damage;

		} else {
			Double tmpnewdmg = _magicDamagerList.get(interval);
			if (tmpnewdmg != null) {
				newdmg = (damage * tmpnewdmg) / 100;

			} else {
				newdmg = damage;
			}
			newdmg = Math.max(newdmg, 0);

			this._oldTime = nowTime; // 次回時間紀錄
		}
		return newdmg;
	}

	/**
	 * 受攻擊hp減少計算
	 * 
	 * @param attacker         攻擊者
	 * @param damage           傷害
	 * @param isMagicDamage    連續魔法傷害減低
	 * @param isCounterBarrier 這個傷害是否不執行反饋 true:不執行反饋 false:執行反饋
	 */
	public void receiveDamage(final L1Character attacker, double damage, final boolean isMagicDamage,
			final boolean isCounterBarrier) {
		if ((this.getCurrentHp() > 0) && !this.isDead()) {

			if (attacker != null) {
				if (attacker != this) {
					if (!(attacker instanceof L1EffectInstance) && !this.knownsObject(attacker)
							&& attacker.getMapId() == this.getMapId()) {
						attacker.onPerceive(this);
					}
				}

				// 連續魔法傷害減低
				if (isMagicDamage == true) {
					damage = this.isMagicDamager(damage);
				}

				// 攻擊者定義
				L1PcInstance attackPc = null;
				L1NpcInstance attackNpc = null;

				if (attacker instanceof L1PcInstance) {
					attackPc = (L1PcInstance) attacker;// 攻擊者為PC

				} else if (attacker instanceof L1NpcInstance) {
					attackNpc = (L1NpcInstance) attacker;// 攻擊者為NPC
				}

				// 傷害大於等於0(小於0回復HP)
				if (damage > 0) {
					// 解除隱身
					this.delInvis();
					// 解除沉睡之霧
					this.removeSkillEffect(FOG_OF_SLEEPING);

					if (attackPc != null) {
						L1PinkName.onAction(this, attackPc);
						if (attackPc.isPinkName()) {
							// 警衛對攻擊者的處分
							for (final L1Object object : World.get().getVisibleObjects(attacker)) {
								if (object instanceof L1GuardInstance) {
									final L1GuardInstance guard = (L1GuardInstance) object;
									guard.setTarget(((L1PcInstance) attacker));
								}
							}
						}
					}
				}

				if (!isCounterBarrier) {// false:執行反饋
					// 致命身軀(自身具有效果)
					if (this.hasSkillEffect(MORTAL_BODY)) {
						if (this.getId() != attacker.getId()) {
							final int rnd = _random.nextInt(100) + 1;
							if ((damage > 0) && (rnd <= 18)) {// 2011-11-26 0-15
								final int dmg = attacker.getLevel();
								// SRC DMG = 50
								if (attackPc != null) {
									attackPc.sendPacketsX10(
											new S_DoActionGFX(attackPc.getId(), ActionCodes.ACTION_Damage));
									attackPc.receiveDamage(this, dmg, false, true);

								} else if (attackNpc != null) {
									attackNpc.broadcastPacketX10(
											new S_DoActionGFX(attackNpc.getId(), ActionCodes.ACTION_Damage));
									attackNpc.receiveDamage(this, dmg);
								}
							}
						}
					}

					// 攻擊者被施放疼痛的歡愉
					if (attacker.hasSkillEffect(JOY_OF_PAIN)) {
						if (this.getId() != attacker.getId()) {
							attacker.killSkillEffectTimer(JOY_OF_PAIN);
							// 攻擊者藥物提高的HP
							int hpup = this.get_other().get_addhp();

							// (>> 1: 除) (<< 1: 乘)
							final int nowDamage = (getMaxHp() - getCurrentHp() - hpup) / 4;// / 2012-06-18

							if (nowDamage > 0) {
								// 給予傷害質
								if (attackPc != null) {
									attackPc.sendPacketsX10(
											new S_DoActionGFX(attackPc.getId(), ActionCodes.ACTION_Damage));
									attackPc.receiveDamage(this, nowDamage, false, true);

								} else if (attackNpc != null) {
									attackNpc.broadcastPacketX10(
											new S_DoActionGFX(attackNpc.getId(), ActionCodes.ACTION_Damage));
									attackNpc.receiveDamage(this, nowDamage);
								}
							}
						}
					}
				}
			}

			// 裝備使自己傷害加深的裝備
			if (this.getInventory().checkEquipped(145) // 狂戰士斧
					|| this.getInventory().checkEquipped(149)) { // 牛人斧頭
				damage *= 1.5; // 傷害提高1.5倍
			}

			// 幻覺：化身219
			if (this.hasSkillEffect(ILLUSION_AVATAR)) {
				damage *= 1.5; // 傷害提高1.5倍
			}

			int addhp = 0;
			if (_elitePlateMail_Fafurion > 0) {
				if (_random.nextInt(1000) <= _elitePlateMail_Fafurion) {
					this.sendPacketsX8(new S_SkillSound(this.getId(), 2187));
					addhp = _random.nextInt(_fafurion_hpmax - _fafurion_hpmin + 1) + _fafurion_hpmin;// 受到攻擊時，4%的機率會恢復體力72~86點。
				}
			}

			int newHp = this.getCurrentHp() - (int) (damage) + addhp;
			if (newHp > this.getMaxHp()) {
				newHp = this.getMaxHp();
			}
			if (newHp <= 0) {
				if (!this.isGm()) {
					this.death(attacker);
				}
			}

			this.setCurrentHp(newHp);

		} else if (!this.isDead()) {
			_log.error("人物hp減少處理失敗 可能原因: 初始hp為0");
			this.death(attacker);
		}
	}

	/**
	 * 死亡的處理
	 * 
	 * @param lastAttacker 攻擊致死的攻擊者
	 */
	public void death(final L1Character lastAttacker) {
		synchronized (this) {
			if (this.isDead()) {
				return;
			}
			this.setNowTarget(null);// 解除目前攻擊目標設置
			this.setDead(true);
			this.setStatus(ActionCodes.ACTION_Die);
		}
		GeneralThreadPool.get().execute(new Death(lastAttacker));

	}

	/**
	 * 人物死亡的處理
	 * 
	 * @author dexc
	 *
	 */
	private class Death implements Runnable {

		private L1Character _lastAttacker;

		private Death(final L1Character cha) {
			this._lastAttacker = cha;
		}

		@Override
		public void run() {
			final L1Character lastAttacker = this._lastAttacker;
			this._lastAttacker = null;
			L1PcInstance.this.setCurrentHp(0);
			L1PcInstance.this.setGresValid(false); // EXPロストするまでG-RES無効

			while (L1PcInstance.this.isTeleport()) { // 傳送狀態中延遲
				try {
					Thread.sleep(300);

				} catch (final Exception e) {
				}
			}
			if (L1PcInstance.this.isInParty()) {// 隊伍中
				for (final L1PcInstance member : L1PcInstance.this.getParty().partyUsers().values()) {
					member.sendPackets(new S_PacketBoxParty(getParty(), L1PcInstance.this));
				}
			}
			// 加入死亡清單
			set_delete_time(300);

			// 娃娃刪除
			if (!getDolls().isEmpty()) {
				for (Object obj : getDolls().values().toArray()) {
					final L1DollInstance doll = (L1DollInstance) obj;
					doll.deleteDoll();
				}
			}

			L1PcInstance.this.stopHpRegeneration();
			L1PcInstance.this.stopMpRegeneration();

			final int targetobjid = L1PcInstance.this.getId();
			L1PcInstance.this.getMap().setPassable(L1PcInstance.this.getLocation(), true);

			// 死亡時具有變身狀態
			int tempchargfx = 0;
			if (L1PcInstance.this.hasSkillEffect(SHAPE_CHANGE)) {
				tempchargfx = L1PcInstance.this.getTempCharGfx();
				L1PcInstance.this.setTempCharGfxAtDead(tempchargfx);

			} else {
				L1PcInstance.this.setTempCharGfxAtDead(L1PcInstance.this.getClassId());
			}

			// 死亡時 現有技能消除
			final L1SkillUse l1skilluse = new L1SkillUse();
			l1skilluse.handleCommands(L1PcInstance.this, CANCELLATION, L1PcInstance.this.getId(),
					L1PcInstance.this.getX(), L1PcInstance.this.getY(), 0, L1SkillUse.TYPE_LOGIN);

			// シャドウ系変身中に死亡するとクライアントが落ちるため暫定対応
			if ((tempchargfx == 5727) || (tempchargfx == 5730) || (tempchargfx == 5733) || (tempchargfx == 5736)) {
				tempchargfx = 0;
			}

			if (tempchargfx == 7351) {
				tempchargfx = L1PcInstance.this.getClassId();
				L1PcInstance.this.setTempCharGfx(tempchargfx);
			}

			if (tempchargfx != 0) {
				// System.out.println("tempchargfx: " + tempchargfx);
				L1PcInstance.this.sendPacketsAll(new S_ChangeShape(L1PcInstance.this, tempchargfx));

			} else {
				// シャドウ系変身中に攻撃しながら死亡するとクライアントが落ちるためディレイを入れる
				try {
					Thread.sleep(1000);
				} catch (final Exception e) {
				}
			}

			boolean isSafetyZone = false;// 是否為安全區中

			boolean isCombatZone = false;// 是否為戰鬥區中

			boolean isWar = false;// 是否參戰

			if (L1PcInstance.this.isSafetyZone()) {
				isSafetyZone = true;
			}
			if (L1PcInstance.this.isCombatZone()) {
				isCombatZone = true;
			}

			// 殺人次數的減少
			if (lastAttacker instanceof L1GuardInstance) {
				if (L1PcInstance.this.get_PKcount() > 0) {
					L1PcInstance.this.set_PKcount(L1PcInstance.this.get_PKcount() - 1);
				}
				L1PcInstance.this.setLastPk(null);
			}

			if (lastAttacker instanceof L1GuardianInstance) {
				if (L1PcInstance.this.getPkCountForElf() > 0) {
					L1PcInstance.this.setPkCountForElf(L1PcInstance.this.getPkCountForElf() - 1);
				}
				L1PcInstance.this.setLastPkForElf(null);
			}

			// 檢查攻擊者是否為PC(寵物 定義為主人)
			L1PcInstance fightPc = null;

			if (lastAttacker instanceof L1PcInstance) {// 攻擊者是玩家
				fightPc = (L1PcInstance) lastAttacker;

			} else if (lastAttacker instanceof L1PetInstance) {// 攻擊者是寵物
				final L1PetInstance npc = (L1PetInstance) lastAttacker;
				if (npc.getMaster() != null) {
					fightPc = (L1PcInstance) npc.getMaster();
				}

			} else if (lastAttacker instanceof L1SummonInstance) {// 攻擊者是 召換獸
				final L1SummonInstance npc = (L1SummonInstance) lastAttacker;
				if (npc.getMaster() != null) {
					fightPc = (L1PcInstance) npc.getMaster();
				}

			} else if (lastAttacker instanceof L1IllusoryInstance) {// 攻擊者是 分身
				final L1IllusoryInstance npc = (L1IllusoryInstance) lastAttacker;
				if (npc.getMaster() != null) {
					fightPc = (L1PcInstance) npc.getMaster();
				}

			} else if (lastAttacker instanceof L1EffectInstance) {// 攻擊者是 技能物件
				final L1EffectInstance npc = (L1EffectInstance) lastAttacker;
				if (npc.getMaster() != null) {
					fightPc = (L1PcInstance) npc.getMaster();
				}
			}

			L1PcInstance.this.sendPacketsAll(new S_DoActionGFX(targetobjid, ActionCodes.ACTION_Die));

			if (fightPc != null) {
				// 決鬥中
				if ((L1PcInstance.this.getFightId() == fightPc.getId())
						&& (fightPc.getFightId() == L1PcInstance.this.getId())) {
					L1PcInstance.this.setFightId(0);
					L1PcInstance.this.sendPackets(new S_PacketBox(S_PacketBox.MSG_DUEL, 0, 0));
					fightPc.setFightId(0);
					fightPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_DUEL, 0, 0));
					return;
				}

				// 效果: 被超過10級以上的玩家攻擊而死亡時，不會失去經驗值，也不會掉落物品
				if (isEncounter()) {// 遭遇的守護
					if (fightPc.getLevel() > getLevel()) {
						if ((fightPc.getLevel() - getLevel()) >= 10) {
							return;
						}
					}
				}

				// 攻城戰爭進行狀態
				if (L1PcInstance.this.castleWarResult()) {
					isWar = true;
				}

				// 血盟戰爭進行狀態
				if (L1PcInstance.this.simWarResult(lastAttacker)) {
					isWar = true;
				}

				// 攻城戰進行狀態
				if (L1PcInstance.this.isInWarAreaAndWarTime(L1PcInstance.this, fightPc)) {
					isWar = true;
				}

				// 死亡公告
				if (L1PcInstance.this.getLevel() >= ConfigKill.KILLLEVEL) {
					if (!fightPc.isGm()) {
						boolean isShow = false;// 是否公告
						if (isWar) {// 戰爭中
							isShow = true;

						} else {// 非戰爭中
							// 非戰鬥區
							if (!isCombatZone) {
								isShow = true;
							}
						}
						if (isShow) {
							// 殺人公告
							World.get().broadcastPacketToAll(
									new S_KillMessage(fightPc.getName(), L1PcInstance.this.getName()));
							fightPc.get_other().add_killCount(1);
							L1PcInstance.this.get_other().add_deathCount(1);
						}
					}
				}
			}

			// 安全區中
			if (isSafetyZone) {
				return;
			}
			// 戰鬥區中
			if (isCombatZone) {
				return;
			}
			// 死亡逞罰
			if (!L1PcInstance.this.getMap().isEnabledDeathPenalty()) {
				return;
			}

			final boolean castle_area = L1CastleLocation.checkInAllWarArea(getX(), getY(), getMapId());
			if (castle_area) {// 戰爭旗中
				return;
			}

			// 掉落 log 紀錄暫存
			ArrayList<L1ItemInstance> punish_items = null; // 噴裝備 紀錄
			ArrayList<Integer> skills = null; // 噴魔法 紀錄
			long remExp = 0;
			// 正義質未滿
			if (L1PcInstance.this.getLawful() < 32767) {
				// 物品掉落判斷
				punish_items = this.lostRate();
				// 技能掉落的判斷
				skills = this.lostSkillRate();
			}
			// 經驗值掉落的判斷
			remExp = this.expRate();

			LogDeathReading.get().logDeathArchive(L1PcInstance.this, punish_items, skills, remExp); // 死亡紀錄

			// 參戰中
			if (isWar) {
				return;
			}

			if (fightPc != null) {
				if (fightPc.getClan() != null && getClan() != null) {
					if (WorldWar.get().isWar(fightPc.getClan().getClanName(), getClan().getClanName())) {
						return;
					}
				}
				if (fightPc.isSafetyZone()) {
					return;
				}
				if (fightPc.isCombatZone()) {
					return;
				}
				if ((L1PcInstance.this.getLawful() >= 0) && (L1PcInstance.this.isPinkName() == false)) {
					boolean isChangePkCount = false;
					// boolean isChangePkCountForElf = false;
					// アライメントが30000未満の場合はPKカウント増加
					if (fightPc.getLawful() < 30000) {
						fightPc.set_PKcount(fightPc.get_PKcount() + 1);
						isChangePkCount = true;
						if (fightPc.isElf() && L1PcInstance.this.isElf()) {
							fightPc.setPkCountForElf(fightPc.getPkCountForElf() + 1);
							// isChangePkCountForElf = true;
						}
					}
					fightPc.setLastPk();
					if (fightPc.isElf() && L1PcInstance.this.isElf()) {
						fightPc.setLastPkForElf();
					}

					// アライメント処理
					// 公式の発表および各LVでのPKからつじつまの合うように変更
					// （PK側のLVに依存し、高LVほどリスクも高い）
					// 48あたりで-8kほど DKの時点で10k強
					// 60で約20k強 65で30k弱
					int lawful;

					if (fightPc.getLevel() < 50) {
						// lawful = -1 * (int) ((Math.pow(fightPc.getLevel(), 2) * 4));
						lawful = -1 * (((int) Math.pow(fightPc.getLevel(), 2)) << 2);

					} else {
						lawful = -1 * (int) ((Math.pow(fightPc.getLevel(), 3) * 0.08));
					}
					// もし(元々のアライメント-1000)が計算後より低い場合
					// 元々のアライメント-1000をアライメント値とする
					// （連続でPKしたときにほとんど値が変わらなかった記憶より）
					// これは上の式よりも自信度が低いうろ覚えですので
					// 明らかにこうならない！という場合は修正お願いします
					if ((fightPc.getLawful() - 1000) < lawful) {
						lawful = fightPc.getLawful() - 1000;
					}

					if (lawful <= -32768) {
						lawful = -32768;
					}
					fightPc.setLawful(lawful);

					fightPc.sendPacketsAll(new S_Lawful(fightPc));

					if (ConfigAlt.ALT_PUNISHMENT) {
						if (isChangePkCount && (fightPc.get_PKcount() >= 5) && (fightPc.get_PKcount() < 100)) {
							// あなたのPK回数が%0になりました。回数が%1になると地獄行きです。
							fightPc.sendPackets(new S_BlueMessage(551, String.valueOf(fightPc.get_PKcount()), "100"));

						} else if (isChangePkCount && (fightPc.get_PKcount() >= 100)) {
							fightPc.beginHell(true);
						}
					}

				} else {
					setPinkName(false);
				}
			}

			/*
			 * if (PcDeleteList.get(L1PcInstance.this) == null) {
			 * PcDeleteList.put(L1PcInstance.this);// 5M }
			 */
		}

		/**
		 * <FONT COLOR="#0000ff">經驗值掉落判斷</FONT>
		 */
		private long expRate() {
			
			long remExp = 0;
			
			
			final L1ItemInstance item1 = getInventory().checkItemX(44060, 1);
			if (item1 != null) {
				getInventory().removeItem(item1, 1);// 删除1个药水
				sendPackets(new S_ServerMessage("\\fU你身上帶有" + item1.getName() + "，剛剛死掉沒有掉％！"));
				return 0;
			}

			remExp = deathPenalty(); // 經驗質逞罰

			setGresValid(true); // EXPロストしたらG-RES有効

			if (getExpRes() == 0) {
				setExpRes(1);
			}

			onChangeExp();
			
			return remExp;
		}

		/**
		 * <FONT COLOR="#0000ff">物品掉落判斷</FONT>
		 */
		private ArrayList<L1ItemInstance> lostRate() {
			final L1ItemInstance item2 = L1PcInstance.this.getInventory().checkItemX(44061, 1);
			if (item2 != null) {
				L1PcInstance.this.getInventory().removeItem(item2, 1);// 删除1个
				sendPackets(new S_ServerMessage("\\fU你身上帶有" + item2.getName() + "，剛剛死掉沒有噴裝！"));
				return null;
			}

			ArrayList<L1ItemInstance> itemRate = null; // 物品掉落清單

			// 產生物品掉落機率
			// 正義質32000以上0%、每-1000增加0.4%
			// 正義質小於0 每-1000增加0.8%
			// 正義質-32000以下 最高51.2%掉落率
			int lostRate = ((int) ((L1PcInstance.this.getLawful() + 32768D) / 1000D - 65D)) << 2;

			if (lostRate < 0) {
				lostRate *= -1;
				if (L1PcInstance.this.getLawful() < 0) {
					// lostRate *= 2;
					lostRate = lostRate << 1;
				}
				final int rnd = _random.nextInt(1000) + 1;
				if (rnd <= lostRate) {
					int count = 0;
					int lawful = L1PcInstance.this.getLawful();
					if (lawful <= -32768) {// 小於-30000掉落1~5件
						count = _random.nextInt(5) + 1;

					} else if (lawful > -32768 && lawful <= -30000) {// 小於-30000掉落1~3件
						count = _random.nextInt(4) + 1;

					} else if (lawful > -30000 && lawful <= -20000) {// 小於-20000掉落1~3件
						count = _random.nextInt(3) + 1;

					} else if (lawful > -20000 && lawful <= -10000) {// 小於-10000掉落1~2件
						count = _random.nextInt(2) + 1;

					} else if (lawful > -10000 && lawful <= -0) {// 小於0掉落1件
						count = _random.nextInt(1) + 1;
					}

					if (count > 0) {
						itemRate = L1PcInstance.this.caoPenaltyResult(count);
					}
				}
			}
			return itemRate;
		}

		/**
		 * <FONT COLOR="#0000ff">死亡技能遺失判斷</FONT>
		 */
		private ArrayList<Integer> lostSkillRate() {

			ArrayList<Integer> skills = null;

			// 人物擁有技能數量
			int skillCount = _skillList.size();

			// 技能數量大於0
			if (skillCount > 0) {
				// 預計掉落技能數量
				int count = 0;
				// 人物正義質
				int lawful = getLawful();

				// 引用隨機質 0 ~ 199
				int random = _random.nextInt(200);

				if (lawful <= -32768) {
					count = _random.nextInt(4) + 1;// 隨機質 小於 技能數量

				} else if (lawful > -32768 && lawful <= -30000) {
					if (random <= (skillCount + 1)) {
						count = _random.nextInt(3) + 1;// 隨機質 小於 技能數量
					}

				} else if (lawful > -30000 && lawful <= -20000) {
					if (random <= ((skillCount >> 1) + 1)) {// 隨機質 小於 (技能數量 / 2)
						count = _random.nextInt(2) + 1;
					}

				} else if (lawful > -20000 && lawful <= -10000) {
					if (random <= ((skillCount >> 2) + 1)) {// 隨機質 小於 (技能數量 / 4)
						count = 1;
					}
				}

				if (count > 0) {
					skills = delSkill(count);
				}
			}
			return skills;
		}
	}

	/**
	 * <FONT COLOR="#0000ff">死亡掉落物品</FONT>
	 * 
	 * @param count 掉落數量
	 */
	private ArrayList<L1ItemInstance> caoPenaltyResult(final int count) {

		ArrayList<L1ItemInstance> items = new ArrayList<L1ItemInstance>();

		for (int i = 0; i < count; i++) {
			final L1ItemInstance item = getInventory().caoPenalty();
			if (item != null) {
				item.set_showId(get_showId());

				final int x = getX();
				final int y = getY();
				final short m = getMapId();
				getInventory().tradeItem(item, item.isStackable() ? item.getCount() : 1, // 物件不可堆疊 數量:1 可堆疊 數量:全部
						World.get().getInventory(x, y, m));
				// 638 您損失了 %0。
				sendPackets(new S_ServerMessage(638, item.getLogName()));

				items.add(item);
			}
		}
		return items;
	}

	/**
	 * <FONT COLOR="#0000ff">死亡技能遺失</FONT>
	 * 
	 * @param count 掉落數量
	 */
	private ArrayList<Integer> delSkill(final int count) {

		ArrayList<Integer> skills = new ArrayList<Integer>();

		for (int i = 0; i < count; i++) {
			// 隨機取得 INDEX 位置點
			int index = _random.nextInt(this._skillList.size());
			// 取回隨機位置點技能編號
			Integer skillid = _skillList.get(index);

			if (this._skillList.remove(skillid)) {
				this.sendPackets(new S_DelSkill(this, skillid));
				CharSkillReading.get().spellLost(this.getId(), skillid);
				skills.add(skillid);

			}
		}
		return skills;
	}

	/**
	 * <FONT COLOR="#0000ff">復活移出死亡清單</FONT>
	 */
	public void stopPcDeleteTimer() {
		this.setDead(false);
		// 加入死亡清單
		set_delete_time(0);
	}

	/**
	 * <FONT COLOR="#0000ff">是否在參加攻城戰中</FONT>
	 * 
	 * @return true:是 false:不是
	 */
	public boolean castleWarResult() {
		if ((this.getClanid() != 0) && this.isCrown()) { // 具有血盟的王族
			final L1Clan clan = WorldClan.get().getClan(this.getClanname());
			if (clan.getCastleId() == 0) {
				// 取回全部戰爭清單
				for (final L1War war : WorldWar.get().getWarList()) {
					final int warType = war.getWarType();
					final boolean isInWar = war.checkClanInWar(this.getClanname());
					final boolean isAttackClan = war.checkAttackClan(this.getClanname());
					if ((this.getId() == clan.getLeaderId()) && // 攻城戰中 攻擊方盟主死亡 退出戰爭
							(warType == 1) && isInWar && isAttackClan) {
						final String enemyClanName = war.getEnemyClanName(this.getClanname());
						if (enemyClanName != null) {
							war.ceaseWar(this.getClanname(), enemyClanName); // 結束
						}
						break;
					}
				}
			}
		}

		int castleId = 0;
		boolean isNowWar = false;
		castleId = L1CastleLocation.getCastleIdByArea(this);
		if (castleId != 0) { // 戰爭範圍旗幟內城堡ID
			isNowWar = ServerWarExecutor.get().isNowWar(castleId);
		}
		return isNowWar;
	}

	/**
	 * <FONT COLOR="#0000ff">是否在參加血盟戰爭中</FONT>
	 * 
	 * @param lastAttacker
	 * @return true:是 false:不是
	 */
	public boolean simWarResult(final L1Character lastAttacker) {
		if (this.getClanid() == 0) { // クラン所属していない
			return false;
		}

		L1PcInstance attacker = null;
		String enemyClanName = null;
		boolean sameWar = false;

		// 判斷主要攻擊者
		if (lastAttacker instanceof L1PcInstance) {// 攻擊者是玩家
			attacker = (L1PcInstance) lastAttacker;

		} else if (lastAttacker instanceof L1PetInstance) {// 攻擊者是寵物
			attacker = (L1PcInstance) ((L1PetInstance) lastAttacker).getMaster();

		} else if (lastAttacker instanceof L1SummonInstance) {// 攻擊者是 召換獸
			attacker = (L1PcInstance) ((L1SummonInstance) lastAttacker).getMaster();

		} else if (lastAttacker instanceof L1IllusoryInstance) {// 攻擊者是 分身
			attacker = (L1PcInstance) ((L1IllusoryInstance) lastAttacker).getMaster();

		} else if (lastAttacker instanceof L1EffectInstance) {// 攻擊者是 技能物件(火牢)
			attacker = (L1PcInstance) ((L1EffectInstance) lastAttacker).getMaster();

		} else {
			return false;
		}

		// 取回全部戰爭清單
		for (final L1War war : WorldWar.get().getWarList()) {
			final L1Clan clan = WorldClan.get().getClan(this.getClanname());

			final int warType = war.getWarType();
			final boolean isInWar = war.checkClanInWar(this.getClanname());
			if ((attacker != null) && (attacker.getClanid() != 0)) { // lastAttackerがPC、サモン、ペットでクラン所属中
				sameWar = war.checkClanInSameWar(this.getClanname(), attacker.getClanname());
			}

			if ((this.getId() == clan.getLeaderId()) && // 血盟主で模擬戦中
					(warType == 2) && (isInWar == true)) {
				enemyClanName = war.getEnemyClanName(this.getClanname());
				if (enemyClanName != null) {
					war.ceaseWar(this.getClanname(), enemyClanName); // 結束
				}
			}

			if ((warType == 2) && sameWar) {// 模擬戦で同じ戦争に参加中の場合、ペナルティなし
				return true;
			}
		}
		return false;
	}

	/**
	 * 經驗質恢復
	 */
	public void resExp() {
		final int oldLevel = this.getLevel();
		final long needExp = ExpTable.getNeedExpNextLevel(oldLevel);
		long exp = 0;
		switch (oldLevel) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
		case 27:
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		case 40:
		case 41:
		case 42:
		case 43:
		case 44:
			exp = (long) (needExp * 0.05);
			break;

		case 45:
			exp = (long) (needExp * 0.045);
			break;

		case 46:
			exp = (long) (needExp * 0.04);
			break;

		case 47:
			exp = (long) (needExp * 0.035);
			break;

		case 48:
			exp = (long) (needExp * 0.03);
			break;

		case 49:
			exp = (long) (needExp * 0.025);
			break;

		default:
			exp = (long) (needExp * 0.025);
			break;
		}

		if (exp == 0) {
			return;
		}
		this.addExp(exp);
	}

	/**
	 * 經驗質逞罰
	 * 
	 * @return
	 */
	private long deathPenalty() {
		final int oldLevel = this.getLevel();
		final long needExp = ExpTable.getNeedExpNextLevel(oldLevel);
		long exp = 0;
		switch (oldLevel) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
			exp = 0;
			break;

		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
		case 27:
		case 28:
		case 29:
		case 30:
		case 31:
		case 32:
		case 33:
		case 34:
		case 35:
		case 36:
		case 37:
		case 38:
		case 39:
		case 40:
		case 41:
		case 42:
		case 43:
		case 44:
			exp = (long) (needExp * 0.1);
			break;

		case 45:
			exp = (long) (needExp * 0.09);
			break;

		case 46:
			exp = (long) (needExp * 0.08);
			break;

		case 47:
			exp = (long) (needExp * 0.07);
			break;

		case 48:
			exp = (long) (needExp * 0.06);
			break;

		case 49:
			exp = (long) (needExp * 0.05);
			break;

		default:
			exp = (long) (needExp * 0.05);
			break;
		}

		if (exp == 0) {
			return 0;
		}
		this.addExp(-exp);
		return exp;
	}

	private int _originalEr = 0; // ● オリジナルDEX ER補正

	public int getOriginalEr() {

		return this._originalEr;
	}

	public int getEr() {
		if (this.hasSkillEffect(STRIKER_GALE)) {
			return 0;
		}

		int er = 0;
		if (this.isKnight()) {
			er = this.getLevel() >> 2;// /4 // ナイト

		} else if (this.isCrown() || this.isElf()) {
			er = this.getLevel() >> 3;// / 8; // 君主・エルフ

		} else if (this.isDarkelf()) {
			er = this.getLevel() / 6; // ダークエルフ

		} else if (this.isWizard()) {
			er = this.getLevel() / 10; // ウィザード

		} else if (this.isDragonKnight()) {
			er = this.getLevel() / 7; // ドラゴンナイト

		} else if (this.isIllusionist()) {
			er = this.getLevel() / 9; // イリュージョニスト
		}

		er += (this.getDex() - 8) >> 1;/// 2;

		er += this.getOriginalEr();

		if (this.hasSkillEffect(DRESS_EVASION)) {// 閃避提升
			er += 12;
		}

		if (this.hasSkillEffect(SOLID_CARRIAGE)) {// 堅固防護
			er += 15;
		}

		if (this.hasSkillEffect(ADLV80_1)) {// 卡瑞的祝福(地龍副本)
			er += 30;
		}

		if (this.hasSkillEffect(ADLV80_2)) {// 莎爾的祝福(水龍副本)
			er += 15;
		}
		return er;
	}

	/**
	 * 使用的武器
	 * 
	 * @return
	 */
	public L1ItemInstance getWeapon() {
		return this._weapon;
	}

	/**
	 * 使用的武器
	 * 
	 * @param weapon
	 */
	public void setWeapon(final L1ItemInstance weapon) {
		this._weapon = weapon;
	}

	/**
	 * 傳回任務狀態類
	 * 
	 * @return
	 */
	public L1PcQuest getQuest() {
		return this._quest;
	}

	/**
	 * 傳回選單命令執行類
	 * 
	 * @return
	 */
	public L1ActionPc getAction() {
		return this._action;
	}

	/**
	 * 傳回寵物選單命令執行類
	 * 
	 * @return
	 */
	public L1ActionPet getActionPet() {
		return this._actionPet;
	}

	/**
	 * 傳回召喚獸選單命令執行類
	 * 
	 * @return
	 */
	public L1ActionSummon getActionSummon() {
		return this._actionSummon;
	}

	/**
	 * 王族
	 * 
	 * @return
	 */
	public boolean isCrown() {
		return ((this.getClassId() == CLASSID_PRINCE) || (this.getClassId() == CLASSID_PRINCESS));
	}

	/**
	 * 騎士
	 * 
	 * @return
	 */
	public boolean isKnight() {
		return ((this.getClassId() == CLASSID_KNIGHT_MALE) || (this.getClassId() == CLASSID_KNIGHT_FEMALE));
	}

	/**
	 * 精靈
	 * 
	 * @return
	 */
	public boolean isElf() {
		return ((this.getClassId() == CLASSID_ELF_MALE) || (this.getClassId() == CLASSID_ELF_FEMALE));
	}

	/**
	 * 法師
	 * 
	 * @return
	 */
	public boolean isWizard() {
		return ((this.getClassId() == CLASSID_WIZARD_MALE) || (this.getClassId() == CLASSID_WIZARD_FEMALE));
	}

	/**
	 * 黑暗精靈
	 * 
	 * @return
	 */
	public boolean isDarkelf() {
		return ((this.getClassId() == CLASSID_DARK_ELF_MALE) || (this.getClassId() == CLASSID_DARK_ELF_FEMALE));
	}

	/**
	 * 龍騎士
	 * 
	 * @return
	 */
	public boolean isDragonKnight() {
		return ((this.getClassId() == CLASSID_DRAGON_KNIGHT_MALE)
				|| (this.getClassId() == CLASSID_DRAGON_KNIGHT_FEMALE));
	}

	/**
	 * 幻術師
	 * 
	 * @return
	 */
	public boolean isIllusionist() {
		return ((this.getClassId() == CLASSID_ILLUSIONIST_MALE) || (this.getClassId() == CLASSID_ILLUSIONIST_FEMALE));
	}

	private ClientExecutor _netConnection = null;
	private int _classId;
	private int _type;
	private long _exp;
	private final L1Karma _karma = new L1Karma();
	private boolean _gm;
	private boolean _monitor;
	private boolean _gmInvis;
	private short _accessLevel;
	private int _currentWeapon;
	private final L1PcInventory _inventory;
	private final L1DwarfInventory _dwarf;
	private final L1DwarfForElfInventory _dwarfForElf;
	private L1ItemInstance _weapon;
	private L1Party _party;
	private L1ChatParty _chatParty;
	private int _partyID;
	private int _tradeID;
	private boolean _tradeOk;
	private int _tempID;
	private boolean _isTeleport = false;
	private boolean _isDrink = false;
	private boolean _isGres = false;
	private boolean _isPinkName = false;
	private L1PcQuest _quest;
	private L1ActionPc _action;
	private L1ActionPet _actionPet;
	private L1ActionSummon _actionSummon;

	private L1EquipmentSlot _equipSlot;

	private String _accountName; // ● アカウントネーム

	public String getAccountName() {
		return this._accountName;
	}

	public void setAccountName(final String s) {
		this._accountName = s;
	}

	private short _baseMaxHp = 0; // ● ＭＡＸＨＰベース（1〜32767）

	/**
	 * 基礎HP
	 * 
	 * @return
	 */
	public short getBaseMaxHp() {
		return this._baseMaxHp;
	}

	/**
	 * 基礎HP
	 * 
	 * @param i
	 */
	public void addBaseMaxHp(short i) {
		i += this._baseMaxHp;
		if (i >= 32767) {
			i = 32767;

		} else if (i < 1) {
			i = 1;
		}
		this.addMaxHp(i - this._baseMaxHp);
		this._baseMaxHp = i;
	}

	private short _baseMaxMp = 0; // ● ＭＡＸＭＰベース（0〜32767）

	/**
	 * 基礎MP
	 * 
	 * @return
	 */
	public short getBaseMaxMp() {
		return this._baseMaxMp;
	}

	/**
	 * 基礎MP
	 * 
	 * @param i
	 */
	public void addBaseMaxMp(short i) {
		i += this._baseMaxMp;
		if (i >= 32767) {
			i = 32767;

		} else if (i < 1) {
			i = 1;
		}
		this.addMaxMp(i - this._baseMaxMp);
		this._baseMaxMp = i;
	}

	private int _baseAc = 0; // ● ＡＣベース（-128〜127）

	public int getBaseAc() {
		return this._baseAc;
	}

	private int _originalAc = 0; // ● オリジナルDEX ＡＣ補正

	public int getOriginalAc() {
		return this._originalAc;
	}

	private int _baseStr = 0; // ● ＳＴＲベース（1〜127）

	/**
	 * 原始力量(內含素質提升/萬能藥)
	 * 
	 * @return
	 */
	public int getBaseStr() {
		return this._baseStr;
	}

	/**
	 * 原始力量(內含素質提升/萬能藥)
	 * 
	 * @param i
	 */
	public void addBaseStr(int i) {
		i += this._baseStr;
		if (i >= 254) {
			i = 254;

		} else if (i < 1) {
			i = 1;
		}
		this.addStr((i - this._baseStr));
		this._baseStr = i;
	}

	private int _baseCon = 0; // ● ＣＯＮベース（1〜127）

	/**
	 * 原始體質(內含素質提升/萬能藥)
	 * 
	 * @return
	 */
	public int getBaseCon() {
		return this._baseCon;
	}

	/**
	 * 原始體質(內含素質提升/萬能藥)
	 * 
	 * @param i
	 */
	public void addBaseCon(int i) {
		i += this._baseCon;
		if (i >= 254) {
			i = 254;

		} else if (i < 1) {
			i = 1;
		}
		this.addCon((i - this._baseCon));
		this._baseCon = i;
	}

	private int _baseDex = 0; // ● ＤＥＸベース（1〜127）

	/**
	 * 原始敏捷(內含素質提升/萬能藥)
	 * 
	 * @return
	 */
	public int getBaseDex() {
		return this._baseDex;
	}

	/**
	 * 原始敏捷(內含素質提升/萬能藥)
	 * 
	 * @param i
	 */
	public void addBaseDex(int i) {
		i += this._baseDex;
		if (i >= 254) {
			i = 254;

		} else if (i < 1) {
			i = 1;
		}
		this.addDex((i - this._baseDex));
		this._baseDex = i;
	}

	private int _baseCha = 0; // ● ＣＨＡベース（1〜127）

	/**
	 * 原始魅力(內含素質提升/萬能藥)
	 * 
	 * @return
	 */
	public int getBaseCha() {
		return this._baseCha;
	}

	/**
	 * 原始魅力(內含素質提升/萬能藥)
	 * 
	 * @param i
	 */
	public void addBaseCha(int i) {
		i += this._baseCha;
		if (i >= 254) {
			i = 254;

		} else if (i < 1) {
			i = 1;
		}
		this.addCha((i - this._baseCha));
		this._baseCha = i;
	}

	private int _baseInt = 0; // ● ＩＮＴベース（1〜127）

	/**
	 * 原始智力(內含素質提升/萬能藥)
	 * 
	 * @return
	 */
	public int getBaseInt() {
		return this._baseInt;
	}

	/**
	 * 原始智力(內含素質提升/萬能藥)
	 * 
	 * @param i
	 */
	public void addBaseInt(int i) {
		i += this._baseInt;
		if (i >= 254) {
			i = 254;

		} else if (i < 1) {
			i = 1;
		}
		this.addInt((i - this._baseInt));
		this._baseInt = i;
	}

	private int _baseWis = 0; // ● ＷＩＳベース（1〜127）

	/**
	 * 原始精神(內含素質提升/萬能藥)
	 * 
	 * @return
	 */
	public int getBaseWis() {
		return this._baseWis;
	}

	/**
	 * 原始精神(內含素質提升/萬能藥)
	 * 
	 * @param i
	 */
	public void addBaseWis(int i) {
		i += this._baseWis;
		if (i >= 254) {
			i = 254;

		} else if (i < 1) {
			i = 1;
		}
		this.addWis((i - this._baseWis));
		this._baseWis = i;
	}

	////////////////////////////////////////////////////////////////////////////////////////

	private int _originalStr = 0; // ● オリジナル STR

	/**
	 * 原始力量(人物出生)
	 * 
	 * @return
	 */
	public int getOriginalStr() {
		return this._originalStr;
	}

	/**
	 * 原始力量(人物出生)
	 * 
	 * @param i
	 */
	public void setOriginalStr(final int i) {
		this._originalStr = i;
	}

	private int _originalCon = 0; // ● オリジナル CON

	/**
	 * 原始體質(人物出生)
	 * 
	 * @return
	 */
	public int getOriginalCon() {
		return this._originalCon;
	}

	/**
	 * 原始體質(人物出生)
	 * 
	 * @param i
	 */
	public void setOriginalCon(final int i) {
		this._originalCon = i;
	}

	private int _originalDex = 0; // ● オリジナル DEX

	/**
	 * 原始敏捷(人物出生)
	 * 
	 * @return
	 */
	public int getOriginalDex() {
		return this._originalDex;
	}

	/**
	 * 原始敏捷(人物出生)
	 * 
	 * @param i
	 */
	public void setOriginalDex(final int i) {
		this._originalDex = i;
	}

	private int _originalCha = 0; // ● オリジナル CHA

	/**
	 * 原始魅力(人物出生)
	 * 
	 * @return
	 */
	public int getOriginalCha() {
		return this._originalCha;
	}

	/**
	 * 原始魅力(人物出生)
	 * 
	 * @param i
	 */
	public void setOriginalCha(final int i) {
		this._originalCha = i;
	}

	private int _originalInt = 0; // ● オリジナル INT

	/**
	 * 原始智力(人物出生)
	 * 
	 * @return
	 */
	public int getOriginalInt() {
		return this._originalInt;
	}

	/**
	 * 原始智力(人物出生)
	 * 
	 * @param i
	 */
	public void setOriginalInt(final int i) {
		this._originalInt = i;
	}

	private int _originalWis = 0; // ● オリジナル WIS

	/**
	 * 原始精神(人物出生)
	 * 
	 * @return
	 */
	public int getOriginalWis() {
		return this._originalWis;
	}

	/**
	 * 原始精神(人物出生)
	 * 
	 * @param i
	 */
	public void setOriginalWis(final int i) {
		this._originalWis = i;
	}

	private int _originalDmgup = 0; // ● オリジナルSTR ダメージ補正

	public int getOriginalDmgup() {
		return this._originalDmgup;
	}

	private int _originalBowDmgup = 0; // ● オリジナルDEX 弓ダメージ補正

	public int getOriginalBowDmgup() {
		return this._originalBowDmgup;
	}

	private int _originalHitup = 0; // ● オリジナルSTR 命中補正

	public int getOriginalHitup() {
		return this._originalHitup;
	}

	private int _originalBowHitup = 0; // ● オリジナルDEX 命中補正

	public int getOriginalBowHitup() {
		return this._originalHitup + this._originalBowHitup;
	}

	private int _originalMr = 0; // ● オリジナルWIS 魔法防御

	public int getOriginalMr() {
		return this._originalMr;
	}

	private int _originalMagicHit = 0; // ● オリジナルINT 魔法命中

	/**
	 * 智力(依職業)附加魔法命中
	 * 
	 * @return
	 */
	public int getOriginalMagicHit() {
		return this._originalMagicHit;
	}

	private int _originalMagicCritical = 0; // ● オリジナルINT 魔法クリティカル

	public int getOriginalMagicCritical() {
		return this._originalMagicCritical;
	}

	private int _originalMagicConsumeReduction = 0; // ● オリジナルINT 消費MP軽減

	public int getOriginalMagicConsumeReduction() {
		return this._originalMagicConsumeReduction;
	}

	private int _originalMagicDamage = 0; // ● オリジナルINT 魔法ダメージ

	/**
	 * 魔攻
	 * 
	 * @return
	 */
	public int getOriginalMagicDamage() {
		return this._originalMagicDamage;
	}

	private int _originalHpup = 0; // ● オリジナルCON HP上昇値補正

	/**
	 * 體質 HP上昇値補正
	 * 
	 * @return
	 */
	public int getOriginalHpup() {
		return this._originalHpup;
	}

	private int _originalMpup = 0; // ● オリジナルWIS MP上昇値補正

	/**
	 * 精神 MP上昇値補正
	 * 
	 * @return
	 */
	public int getOriginalMpup() {
		return this._originalMpup;
	}

	private int _baseDmgup = 0; // ● ダメージ補正ベース（-128〜127）

	public int getBaseDmgup() {
		return this._baseDmgup;
	}

	private int _baseBowDmgup = 0; // ● 弓ダメージ補正ベース（-128〜127）

	public int getBaseBowDmgup() {
		return this._baseBowDmgup;
	}

	private int _baseHitup = 0; // ● 命中補正ベース（-128〜127）

	/**
	 * 命中補正
	 * 
	 * @return
	 */
	public int getBaseHitup() {
		return this._baseHitup;
	}

	private int _baseBowHitup = 0; // ● 弓命中補正ベース（-128〜127）

	/**
	 * 弓命中補正
	 * 
	 * @return
	 */
	public int getBaseBowHitup() {
		return this._baseBowHitup;
	}

	private int _baseMr = 0; // ● 魔法防御ベース（0〜）

	/**
	 * 魔法防御
	 * 
	 * @return
	 */
	public int getBaseMr() {
		return this._baseMr;
	}

	private int _advenHp; // 暫時增加的HP

	/**
	 * 暫時增加的HP
	 * 
	 * @return
	 */
	public int getAdvenHp() {
		return this._advenHp;
	}

	/**
	 * 暫時增加的HP
	 * 
	 * @param i
	 */
	public void setAdvenHp(final int i) {
		this._advenHp = i;
	}

	private int _advenMp; // 暫時增加的MP

	/**
	 * 暫時增加的MP
	 * 
	 * @return
	 */
	public int getAdvenMp() {
		return this._advenMp;
	}

	/**
	 * 暫時增加的MP
	 * 
	 * @param i
	 */
	public void setAdvenMp(final int i) {
		this._advenMp = i;
	}

	private int _highLevel; // ● 過去最高レベル

	public int getHighLevel() {
		return this._highLevel;
	}

	public void setHighLevel(final int i) {
		this._highLevel = i;
	}

	private int _bonusStats; // 升級點數使用次數

	/**
	 * 升級點數使用次數
	 * 
	 * @return
	 */
	public int getBonusStats() {
		return this._bonusStats;
	}

	/**
	 * 設置升級點數使用次數
	 * 
	 * @param i
	 */
	public void setBonusStats(final int i) {
		this._bonusStats = i;
	}

	private int _elixirStats; // 萬能藥使用次數

	/**
	 * 萬能藥使用次數
	 * 
	 * @return
	 */
	public int getElixirStats() {
		return this._elixirStats;
	}

	/**
	 * 設置萬能藥使用次數
	 * 
	 * @param i
	 */
	public void setElixirStats(final int i) {
		this._elixirStats = i;
	}

	private int _elfAttr; // ● エルフの属性

	/**
	 * 精靈屬性
	 * 
	 * @return
	 */
	public int getElfAttr() {
		return this._elfAttr;
	}

	public void setElfAttr(final int i) {
		this._elfAttr = i;
	}

	private int _expRes; // ● EXP復旧

	public int getExpRes() {
		return this._expRes;
	}

	public void setExpRes(final int i) {
		this._expRes = i;
	}

	private int _partnerId; // ● 結婚相手

	public int getPartnerId() {
		return this._partnerId;
	}

	public void setPartnerId(final int i) {
		_partnerId = i;
	}

	private int _onlineStatus; // 人物連線狀態

	/**
	 * 人物連線狀態
	 * 
	 * @return
	 */
	public int getOnlineStatus() {
		return _onlineStatus;
	}

	/**
	 * 設置人物連線狀態
	 * 
	 * @param i
	 */
	public void setOnlineStatus(final int i) {
		_onlineStatus = i;
	}

	private int _homeTownId; // ● ホームタウン

	public int getHomeTownId() {
		return _homeTownId;
	}

	public void setHomeTownId(final int i) {
		_homeTownId = i;
	}

	private int _contribution; // 貢獻度

	/**
	 * 貢獻度
	 * 
	 * @return
	 */
	public int getContribution() {
		return this._contribution;
	}

	/**
	 * 貢獻度
	 * 
	 * @param i
	 */
	public void setContribution(final int i) {
		this._contribution = i;
	}

	private int _hellTime;// 地獄滯留時間

	/**
	 * 地獄滯留時間
	 * 
	 * @return
	 */
	public int getHellTime() {
		return this._hellTime;
	}

	/**
	 * 地獄滯留時間
	 * 
	 * @param i
	 */
	public void setHellTime(final int i) {
		this._hellTime = i;
	}

	private boolean _banned; // ● 凍結

	public boolean isBanned() {
		return this._banned;
	}

	public void setBanned(final boolean flag) {
		this._banned = flag;
	}

	private int _food; // ● 満腹度

	public int get_food() {
		return _food;
	}

	public void set_food(int i) {
		if (i > 225) {
			i = 225;
		}
		_food = i;
		if (_food == 225) {// LOLI 生存吶喊
			final Calendar cal = Calendar.getInstance();
			long h_time = cal.getTimeInMillis() / 1000;// 換算為秒
			set_h_time(h_time);// 紀錄吃飽時間

		} else {
			set_h_time(-1);// 紀錄吃飽時間
		}
	}

	public L1EquipmentSlot getEquipSlot() {
		return this._equipSlot;
	}

	/**
	 * 加載指定PC資料
	 * 
	 * @param charName PC名稱
	 * @return
	 */
	public static L1PcInstance load(final String charName) {
		L1PcInstance result = null;
		try {
			result = CharacterTable.get().loadCharacter(charName);

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		return result;
	}

	/**
	 * 人物資料存檔
	 *
	 * @throws Exception
	 */
	public void save() throws Exception {
		if (isGhost()) {
			return;
		}

		if (isInCharReset()) {
			return;
		}

		// 其它事件紀錄
		if (_other != null) {
			CharOtherReading.get().storeOther(getId(), _other);
		}

		CharacterTable.get().storeCharacter(this);
	}

	/**
	 * 背包資料存檔
	 */
	public void saveInventory() {
		for (final L1ItemInstance item : getInventory().getItems()) {
			getInventory().saveItem(item, item.getRecordingColumns());
		}
	}

	public double getMaxWeight() {
		final int str = getStr();
		final int con = getCon();
		double maxWeight = (150 * (Math.floor(0.6 * str + 0.4 * con + 1))) * get_weightUP();

		double weightReductionByArmor = getWeightReduction(); // 減重設置
		weightReductionByArmor /= 100;

		int weightReductionByMagic = 0;
		if (hasSkillEffect(DECREASE_WEIGHT)) { // ディクリースウェイト
			weightReductionByMagic = 180;
		}

		double originalWeightReduction = 0; // オリジナルステータスによる重量軽減
		originalWeightReduction += 0.04 * (getOriginalStrWeightReduction() + getOriginalConWeightReduction());

		final double weightReduction = 1 + weightReductionByArmor + originalWeightReduction;

		maxWeight *= weightReduction;

		maxWeight += weightReductionByMagic;

		maxWeight *= ConfigRate.RATE_WEIGHT_LIMIT; // 服務器提高設置

		return maxWeight;
	}

	/**
	 * 神聖疾走效果 行走加速效果 風之疾走效果 生命之樹果實效果
	 * 
	 * @return
	 */
	public boolean isFastMovable() {
		return (this.hasSkillEffect(HOLY_WALK) || this.hasSkillEffect(MOVING_ACCELERATION)
				|| this.hasSkillEffect(WIND_WALK) || this.hasSkillEffect(STATUS_RIBRAVE));
	}

	/**
	 * 血之渴望效果
	 * 
	 * @return
	 */
	public boolean isFastAttackable() {
		return this.hasSkillEffect(BLOODLUST);
	}

	/**
	 * 勇敢藥水效果
	 * 
	 * @return
	 */
	public boolean isBrave() {
		return this.hasSkillEffect(STATUS_BRAVE);
	}

	/**
	 * 精靈餅乾效果
	 * 
	 * @return
	 */
	public boolean isElfBrave() {
		return this.hasSkillEffect(STATUS_ELFBRAVE);
	}

	/**
	 * 巧克力蛋糕效果
	 * 
	 * @return
	 */
	public boolean isBraveX() {
		return this.hasSkillEffect(STATUS_BRAVE3);
	}

	/**
	 * 加速效果
	 * 
	 * @return
	 */
	public boolean isHaste() {
		return (this.hasSkillEffect(STATUS_HASTE) || this.hasSkillEffect(HASTE) || this.hasSkillEffect(GREATER_HASTE)
				|| (this.getMoveSpeed() == 1));
	}

	private int invisDelayCounter = 0;

	public boolean isInvisDelay() {
		return (this.invisDelayCounter > 0);
	}

	private Object _invisTimerMonitor = new Object();

	public void addInvisDelayCounter(final int counter) {
		synchronized (this._invisTimerMonitor) {
			this.invisDelayCounter += counter;
		}
	}

	private static final long DELAY_INVIS = 3000L;

	/**
	 * 啟用隱身時間軸設置
	 */
	public void beginInvisTimer() {
		this.addInvisDelayCounter(1);
		GeneralThreadPool.get().pcSchedule(new L1PcInvisDelay(this.getId()), DELAY_INVIS);
	}

	public synchronized void addExp(final long exp) {
		final long newexp = _exp + exp;
		_exp = newexp;
	}

	/**
	 * 增加貢獻度
	 * 
	 * @param contribution
	 */
	public synchronized void addContribution(final int contribution) {
		_contribution += contribution;
	}

	/**
	 * 等級提升的判斷
	 * 
	 * @param gap
	 */
	private void levelUp(final int gap) {
		resetLevel();
		for (int i = 0; i < gap; i++) {
			final short randomHp = CalcStat.calcStatHp(getType(), getBaseMaxHp(), getBaseCon(), getOriginalHpup());
			final short randomMp = CalcStat.calcStatMp(getType(), getBaseMaxMp(), getBaseWis(), getOriginalMpup());
			addBaseMaxHp(randomHp);
			addBaseMaxMp(randomMp);
		}

		resetBaseHitup();
		resetBaseDmgup();
		resetBaseAc();
		resetBaseMr();
		if (getLevel() > getHighLevel()) {
			setHighLevel(getLevel());
		}

		setCurrentHp(getMaxHp());
		setCurrentMp(getMaxMp());

		try {
			// 人物資料存檔
			save();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			// 更新人物資訊
			sendPackets(new S_OwnCharStatus(this));

			// 地圖等級限制判斷
			MapLevelTable.get().get_level(getMapId(), this);
			showWindows();
		}
	}

	/**
	 * 判斷是否展示視窗<BR>
	 * 能力質/任務
	 */
	public void showWindows() {
		// 任務/副本系統啟動
		if (QuestSet.ISQUEST) {
			// 判斷是否出現任務提示視窗
			int quest = QuestTable.get().levelQuest(this, this.getLevel());
			if (quest > 0) {
				// 展示任務室窗
				isWindows();

			} else {
				// 判斷是否出現能力選取視窗
				if (power()) {
					this.sendPackets(new S_Bonusstats(this.getId()));
				}
			}

		} else {
			// 判斷是否出現能力選取視窗
			if (power()) {
				this.sendPackets(new S_Bonusstats(this.getId()));
			}
		}
	}

	/**
	 * 展示任務室窗
	 */
	public void isWindows() {
		// 判斷是否出現能力選取視窗
		if (power()) {// 是
			this.sendPackets(new S_NPCTalkReturn(this.getId(), "y_qs_10"));

		} else {// 不是
			this.sendPackets(new S_NPCTalkReturn(this.getId(), "y_qs_00"));
		}
	}

	/**
	 * 判斷是否出現能力選取視窗
	 * 
	 * @return
	 */
	public boolean power() {
		if (this.getLevel() >= 51) {
			if (this.getLevel() - 50 > this.getBonusStats()) {
				int power = getBaseStr() + getBaseDex() + getBaseCon() + getBaseInt() + getBaseWis() + getBaseCha();
				if (power < ConfigAlt.POWER * 6) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 等級下降
	 * 
	 * @param gap
	 */
	private void levelDown(final int gap) {
		this.resetLevel();

		for (int i = 0; i > gap; i--) {
			// レベルダウン時はランダム値をそのままマイナスする為に、base値に0を設定
			final short randomHp = CalcStat.calcStatHp(this.getType(), 0, this.getBaseCon(), this.getOriginalHpup());
			final short randomMp = CalcStat.calcStatMp(this.getType(), 0, this.getBaseWis(), this.getOriginalMpup());
			this.addBaseMaxHp((short) -randomHp);
			this.addBaseMaxMp((short) -randomMp);
		}

		if (this.getLevel() == 1) {
			final int initHp = CalcInitHpMp.calcInitHp(this);
			final int initMp = CalcInitHpMp.calcInitMp(this);
			this.addBaseMaxHp((short) -this.getBaseMaxHp());
			this.addBaseMaxHp((short) initHp);
			this.setCurrentHp((short) initHp);
			this.addBaseMaxMp((short) -this.getBaseMaxMp());
			this.addBaseMaxMp((short) initMp);
			this.setCurrentMp((short) initMp);
		}

		this.resetBaseHitup();
		this.resetBaseDmgup();
		this.resetBaseAc();
		this.resetBaseMr();

		try {
			// 存入資料
			this.save();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);

		} finally {
			// 更新人物資訊
			sendPackets(new S_OwnCharStatus(this));

			// 地圖等級限制判斷
			MapLevelTable.get().get_level(getMapId(), this);
		}
	}

	private boolean _ghost = false; // 鬼魂狀態

	/**
	 * 鬼魂狀態
	 * 
	 * @return
	 */
	public boolean isGhost() {
		return this._ghost;
	}

	/**
	 * 設置鬼魂狀態
	 * 
	 * @param flag
	 */
	private void setGhost(final boolean flag) {
		this._ghost = flag;
	}

	private int _ghostTime = -1; // 鬼魂狀態時間

	/**
	 * 鬼魂狀態時間
	 * 
	 * @return
	 */
	public int get_ghostTime() {
		return this._ghostTime;
	}

	/**
	 * 設置鬼魂狀態時間
	 * 
	 * @param ghostTime
	 */
	public void set_ghostTime(final int ghostTime) {
		this._ghostTime = ghostTime;
	}

	private boolean _ghostCanTalk = true; // 鬼魂狀態NPC對話允許

	/**
	 * 鬼魂狀態NPC對話允許
	 * 
	 * @return
	 */
	public boolean isGhostCanTalk() {
		return this._ghostCanTalk;
	}

	/**
	 * 設置鬼魂狀態NPC對話允許
	 * 
	 * @param flag
	 */
	private void setGhostCanTalk(final boolean flag) {
		this._ghostCanTalk = flag;
	}

	private boolean _isReserveGhost = false; // 準備鬼魂狀態解除

	/**
	 * 準備鬼魂狀態解除
	 * 
	 * @return
	 */
	public boolean isReserveGhost() {
		return this._isReserveGhost;
	}

	/**
	 * 準備鬼魂狀態解除
	 * 
	 * @param flag
	 */
	private void setReserveGhost(final boolean flag) {
		this._isReserveGhost = flag;
	}

	/**
	 * 鬼魂模式傳送
	 * 
	 * @param locx
	 * @param locy
	 * @param mapid
	 * @param canTalk
	 */
	public void beginGhost(final int locx, final int locy, final short mapid, final boolean canTalk) {
		this.beginGhost(locx, locy, mapid, canTalk, 0);
	}

	/**
	 * 鬼魂模式傳送
	 * 
	 * @param locx
	 * @param locy
	 * @param mapid
	 * @param canTalk
	 * @param sec
	 */
	public void beginGhost(final int locx, final int locy, final short mapid, final boolean canTalk, final int sec) {
		if (this.isGhost()) {
			return;
		}
		this.setGhost(true);
		this._ghostSaveLocX = this.getX();
		this._ghostSaveLocY = this.getY();
		this._ghostSaveMapId = this.getMapId();
		this._ghostSaveHeading = this.getHeading();
		this.setGhostCanTalk(canTalk);
		L1Teleport.teleport(this, locx, locy, mapid, 5, true);
		if (sec > 0) {
			this.set_ghostTime(sec);
		}
	}

	/**
	 * 離開鬼魂模式(傳送回出發點)
	 */
	public void makeReadyEndGhost() {
		this.setReserveGhost(true);
		L1Teleport.teleport(this, this._ghostSaveLocX, this._ghostSaveLocY, this._ghostSaveMapId,
				this._ghostSaveHeading, true);
	}

	/**
	 * 結束鬼魂模式
	 */
	public void endGhost() {
		this.set_ghostTime(-1);
		this.setGhost(false);
		this.setGhostCanTalk(true);
		this.setReserveGhost(false);
	}

	private int _ghostSaveLocX = 0;
	private int _ghostSaveLocY = 0;
	private short _ghostSaveMapId = 0;
	private int _ghostSaveHeading = 0;

	/**
	 * 地獄以外に居るときは地獄へ強制移動
	 * 
	 * @param isFirst
	 */
	public void beginHell(final boolean isFirst) {
		// 地獄以外に居るときは地獄へ強制移動
		if (this.getMapId() != 666) {
			final int locx = 32701;
			final int locy = 32777;
			final short mapid = 666;
			L1Teleport.teleport(this, locx, locy, mapid, 5, false);
		}

		if (isFirst) {
			if (this.get_PKcount() <= 10) {
				this.setHellTime(300);

			} else {
				this.setHellTime(300 * (this.get_PKcount() - 10) + 300);
			}
			// 552 因為你已經殺了 %0 人所以被打入地獄。 你將在這裡停留 %1 分鐘。
			this.sendPackets(new S_BlueMessage(552, String.valueOf(this.get_PKcount()),
					String.valueOf(this.getHellTime() / 60)));

		} else {
			// 637 你必須在此地停留 %0 秒。
			this.sendPackets(new S_BlueMessage(637, String.valueOf(this.getHellTime())));
		}
	}

	/**
	 * 地獄時間終止
	 */
	public void endHell() {
		// 地獄時間終止 返回然柳村
		final int[] loc = L1TownLocation.getGetBackLoc(L1TownLocation.TOWNID_ORCISH_FOREST);
		L1Teleport.teleport(this, loc[0], loc[1], (short) loc[2], 5, true);

		try {
			this.save();

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void setPoisonEffect(final int effectId) {
		this.sendPackets(new S_Poison(this.getId(), effectId));

		if (!this.isGmInvis() && !this.isGhost() && !this.isInvisble()) {
			this.broadcastPacketAll(new S_Poison(this.getId(), effectId));
		}
	}

	@Override
	public void healHp(final int pt) {
		super.healHp(pt);

		this.sendPackets(new S_HPUpdate(this));
	}

	@Override
	public int getKarma() {
		return this._karma.get();
	}

	@Override
	public void setKarma(final int i) {
		this._karma.set(i);
	}

	public void addKarma(final int i) {
		synchronized (this._karma) {
			this._karma.add(i);
		}
	}

	public int getKarmaLevel() {
		return this._karma.getLevel();
	}

	public int getKarmaPercent() {
		return this._karma.getPercent();
	}

	private Timestamp _lastPk;

	/**
	 * プレイヤーの最終PK時間を返す。
	 *
	 * @return _lastPk
	 *
	 */
	public Timestamp getLastPk() {
		return this._lastPk;
	}

	/**
	 * プレイヤーの最終PK時間を設定する。
	 *
	 * @param time 最終PK時間（Timestamp型） 解除する場合はnullを代入
	 */
	public void setLastPk(final Timestamp time) {
		this._lastPk = time;
	}

	/**
	 * プレイヤーの最終PK時間を現在の時刻に設定する。
	 */
	public void setLastPk() {
		this._lastPk = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * プレイヤーが手配中であるかを返す。
	 *
	 * @return 手配中であれば、true
	 */
	public boolean isWanted() {
		if (this._lastPk == null) {
			return false;

			// 距離PK時間超過1小時
		} else if (System.currentTimeMillis() - this._lastPk.getTime() > 1 * 3600 * 1000) {
			this.setLastPk(null);
			return false;
		}
		return true;
	}

	private Timestamp _lastPkForElf;

	public Timestamp getLastPkForElf() {
		return this._lastPkForElf;
	}

	public void setLastPkForElf(final Timestamp time) {
		this._lastPkForElf = time;
	}

	public void setLastPkForElf() {
		this._lastPkForElf = new Timestamp(System.currentTimeMillis());
	}

	public boolean isWantedForElf() {
		if (this._lastPkForElf == null) {
			return false;

		} else if (System.currentTimeMillis() - this._lastPkForElf.getTime() > 24 * 3600 * 1000) {
			this.setLastPkForElf(null);
			return false;
		}
		return true;
	}

	private Timestamp _deleteTime; // キャラクター削除までの時間

	public Timestamp getDeleteTime() {
		return this._deleteTime;
	}

	public void setDeleteTime(final Timestamp time) {
		this._deleteTime = time;
	}

	/**
	 * 職業魔法等級
	 */
	@Override
	public int getMagicLevel() {
		return this.getClassFeature().getMagicLevel(this.getLevel());
	}

	private double _weightUP = 1.0D;// 負重提高%

	/**
	 * 負重提高%
	 * 
	 * @return
	 */
	public double get_weightUP() {
		return _weightUP;
	}

	/**
	 * 負重提高%
	 * 
	 * @param i
	 */
	public void add_weightUP(final int i) {
		_weightUP += (i / 100D);
	}

	private int _weightReduction = 0;// 減重

	/**
	 * 減重
	 * 
	 * @return
	 */
	public int getWeightReduction() {
		return this._weightReduction;
	}

	/**
	 * 減重
	 * 
	 * @param i
	 */
	public void addWeightReduction(final int i) {
		this._weightReduction += i;
	}

	private int _originalStrWeightReduction = 0; // ● オリジナルSTR 重量軽減

	public int getOriginalStrWeightReduction() {
		return this._originalStrWeightReduction;
	}

	private int _originalConWeightReduction = 0; // ● オリジナルCON 重量軽減

	public int getOriginalConWeightReduction() {
		return this._originalConWeightReduction;
	}

	private int _hasteItemEquipped = 0;// 裝備有加速能力裝備(裝備數量)

	/**
	 * 裝備有加速能力裝備(裝備數量)
	 * 
	 * @return
	 */
	public int getHasteItemEquipped() {
		return this._hasteItemEquipped;
	}

	/**
	 * 裝備有加速能力裝備(裝備數量)
	 * 
	 * @param i
	 */
	public void addHasteItemEquipped(final int i) {
		this._hasteItemEquipped += i;
	}

	public void removeHasteSkillEffect() {
		if (this.hasSkillEffect(SLOW)) {
			this.removeSkillEffect(SLOW);
		}

		if (this.hasSkillEffect(MASS_SLOW)) {
			this.removeSkillEffect(MASS_SLOW);
		}

		if (this.hasSkillEffect(ENTANGLE)) {
			this.removeSkillEffect(ENTANGLE);
		}

		if (this.hasSkillEffect(HASTE)) {
			this.removeSkillEffect(HASTE);
		}

		if (this.hasSkillEffect(GREATER_HASTE)) {
			this.removeSkillEffect(GREATER_HASTE);
		}

		if (this.hasSkillEffect(STATUS_HASTE)) {
			this.removeSkillEffect(STATUS_HASTE);
		}
	}

	private int _damageReductionByArmor = 0; // 防具增加傷害減免

	public int getDamageReductionByArmor() {
		int damageReduction = 0;
		if (_damageReductionByArmor > 10) {
			damageReduction = 10 + (_random.nextInt((_damageReductionByArmor - 10)) + 1);

		} else {
			damageReduction = _damageReductionByArmor;
		}
		return damageReduction;
	}

	public void addDamageReductionByArmor(final int i) {
		this._damageReductionByArmor += i;
	}

	private int _hitModifierByArmor = 0; // 防具增加物理命中

	public int getHitModifierByArmor() {
		return this._hitModifierByArmor;
	}

	public void addHitModifierByArmor(final int i) {
		this._hitModifierByArmor += i;
	}

	private int _dmgModifierByArmor = 0; // 防具增加物理傷害

	public int getDmgModifierByArmor() {
		return this._dmgModifierByArmor;
	}

	public void addDmgModifierByArmor(final int i) {
		this._dmgModifierByArmor += i;
	}

	private int _bowHitModifierByArmor = 0; // 防具增加遠距離物理命中

	public int getBowHitModifierByArmor() {
		return this._bowHitModifierByArmor;
	}

	public void addBowHitModifierByArmor(final int i) {
		this._bowHitModifierByArmor += i;
	}

	private int _bowDmgModifierByArmor = 0; // 防具增加遠距離物理傷害

	public int getBowDmgModifierByArmor() {
		return this._bowDmgModifierByArmor;
	}

	public void addBowDmgModifierByArmor(final int i) {
		this._bowDmgModifierByArmor += i;
	}

	private boolean _gresValid; // G-RESが有効か

	private void setGresValid(final boolean valid) {
		this._gresValid = valid;
	}

	public boolean isGresValid() {
		return this._gresValid;
	}

	private boolean _isFishing = false;// 釣魚狀態

	/**
	 * 釣魚狀態
	 * 
	 * @return
	 */
	public boolean isFishing() {
		return this._isFishing;
	}

	private int _fishX = -1;

	public int get_fishX() {
		return _fishX;
	}

	private int _fishY = -1;

	public int get_fishY() {
		return _fishY;
	}

	/**
	 * 釣魚狀態
	 * 
	 * @param flag
	 * @param fishY
	 * @param fishX
	 */
	public void setFishing(final boolean flag, int fishX, int fishY) {
		this._isFishing = flag;
		_fishX = fishX;
		_fishY = fishY;
	}

	private int _cookingId = 0;

	public int getCookingId() {
		return this._cookingId;
	}

	public void setCookingId(final int i) {
		this._cookingId = i;
	}

	private int _dessertId = 0;

	public int getDessertId() {
		return this._dessertId;
	}

	public void setDessertId(final int i) {
		this._dessertId = i;
	}

	/**
	 * LVによる命中ボーナスを設定する LVが変動した場合などに呼び出せば再計算される
	 *
	 * @return
	 */
	public void resetBaseDmgup() {
		int newBaseDmgup = 0;
		int newBaseBowDmgup = 0;
		if (this.isKnight() || this.isDarkelf() || this.isDragonKnight()) { // ナイト、ダークエルフ、ドラゴンナイト
			newBaseDmgup = this.getLevel() / 10;
			newBaseBowDmgup = 0;

		} else if (this.isElf()) { // エルフ
			newBaseDmgup = 0;
			newBaseBowDmgup = this.getLevel() / 10;
		}
		this.addDmgup(newBaseDmgup - this._baseDmgup);
		this.addBowDmgup(newBaseBowDmgup - this._baseBowDmgup);
		this._baseDmgup = newBaseDmgup;
		this._baseBowDmgup = newBaseBowDmgup;
	}

	/**
	 * LVによる命中ボーナスを設定する LVが変動した場合などに呼び出せば再計算される
	 *
	 * @return
	 */
	public void resetBaseHitup() {
		int newBaseHitup = 0;
		int newBaseBowHitup = 0;
		if (this.isCrown()) { // プリ
			newBaseHitup = this.getLevel() / 5;
			newBaseBowHitup = this.getLevel() / 5;

		} else if (this.isKnight()) { // ナイト
			newBaseHitup = this.getLevel() / 3;
			newBaseBowHitup = this.getLevel() / 3;

		} else if (this.isElf()) { // エルフ
			newBaseHitup = this.getLevel() / 5;
			newBaseBowHitup = this.getLevel() / 5;

		} else if (this.isDarkelf()) { // ダークエルフ
			newBaseHitup = this.getLevel() / 3;
			newBaseBowHitup = this.getLevel() / 3;

		} else if (this.isDragonKnight()) { // ドラゴンナイト
			newBaseHitup = this.getLevel() / 3;
			newBaseBowHitup = this.getLevel() / 3;

		} else if (this.isIllusionist()) { // イリュージョニスト
			newBaseHitup = this.getLevel() / 5;
			newBaseBowHitup = this.getLevel() / 5;
		}

		this.addHitup(newBaseHitup - this._baseHitup);
		this.addBowHitup(newBaseBowHitup - this._baseBowHitup);
		this._baseHitup = newBaseHitup;
		this._baseBowHitup = newBaseBowHitup;
	}

	/**
	 * キャラクターステータスからACを再計算して設定する 初期設定時、LVUP,LVDown時などに呼び出す
	 */
	public void resetBaseAc() {
		final int newAc = CalcStat.calcAc(this.getLevel(), this.getBaseDex());
		this.addAc(newAc - this._baseAc);
		this._baseAc = newAc;
	}

	/**
	 * キャラクターステータスから素のMRを再計算して設定する 初期設定時、スキル使用時やLVUP,LVDown時に呼び出す
	 */
	public void resetBaseMr() {
		int newMr = 0;
		if (this.isCrown()) { // プリ
			newMr = 10;

		} else if (this.isElf()) { // エルフ
			newMr = 25;

		} else if (this.isWizard()) { // ウィザード
			newMr = 15;

		} else if (this.isDarkelf()) { // ダークエルフ
			newMr = 10;

		} else if (this.isDragonKnight()) { // ドラゴンナイト
			newMr = 18;

		} else if (this.isIllusionist()) { // イリュージョニスト
			newMr = 20;
		}
		newMr += CalcStat.calcStatMr(this.getWis()); // WIS分のMRボーナス
		newMr += this.getLevel() / 2; // LVの半分だけ追加
		this.addMr(newMr - this._baseMr);
		this._baseMr = newMr;
	}

	/**
	 * 重新設置等級為目前經驗質所屬
	 */
	public void resetLevel() {
		this.setLevel(ExpTable.getLevelByExp(this._exp));
	}

	/**
	 * 初期ステータスから現在のボーナスを再計算して設定する 初期設定時、再配分時に呼び出す
	 */
	public void resetOriginalHpup() {
		this._originalHpup = L1PcOriginal.resetOriginalHpup(this);
	}

	public void resetOriginalMpup() {
		this._originalMpup = L1PcOriginal.resetOriginalMpup(this);
	}

	public void resetOriginalStrWeightReduction() {
		this._originalStrWeightReduction = L1PcOriginal.resetOriginalStrWeightReduction(this);
	}

	public void resetOriginalDmgup() {
		this._originalDmgup = L1PcOriginal.resetOriginalDmgup(this);
	}

	public void resetOriginalConWeightReduction() {
		this._originalConWeightReduction = L1PcOriginal.resetOriginalConWeightReduction(this);
	}

	public void resetOriginalBowDmgup() {
		this._originalBowDmgup = L1PcOriginal.resetOriginalBowDmgup(this);
	}

	public void resetOriginalHitup() {
		this._originalHitup = L1PcOriginal.resetOriginalHitup(this);
	}

	public void resetOriginalBowHitup() {
		this._originalBowHitup = L1PcOriginal.resetOriginalBowHitup(this);
	}

	public void resetOriginalMr() {
		this._originalMr = L1PcOriginal.resetOriginalMr(this);
		this.addMr(this._originalMr);
	}

	public void resetOriginalMagicHit() {
		this._originalMagicHit = L1PcOriginal.resetOriginalMagicHit(this);
	}

	public void resetOriginalMagicCritical() {
		this._originalMagicCritical = L1PcOriginal.resetOriginalMagicCritical(this);
	}

	public void resetOriginalMagicConsumeReduction() {
		this._originalMagicConsumeReduction = L1PcOriginal.resetOriginalMagicConsumeReduction(this);
	}

	public void resetOriginalMagicDamage() {
		this._originalMagicDamage = L1PcOriginal.resetOriginalMagicDamage(this);
	}

	public void resetOriginalAc() {
		this._originalAc = L1PcOriginal.resetOriginalAc(this);
		// System.out.println("_originalAc:"+_originalAc);
		this.addAc(0 - this._originalAc);
	}

	public void resetOriginalEr() {
		this._originalEr = L1PcOriginal.resetOriginalEr(this);
	}

	public void resetOriginalHpr() {
		this._originalHpr = L1PcOriginal.resetOriginalHpr(this);
	}

	public void resetOriginalMpr() {
		this._originalMpr = L1PcOriginal.resetOriginalMpr(this);
	}

	/**
	 * 全屬性重置
	 */
	public void refresh() {
		this.resetLevel();
		this.resetBaseHitup();
		this.resetBaseDmgup();
		this.resetBaseMr();
		this.resetBaseAc();
		this.resetOriginalHpup();
		this.resetOriginalMpup();
		this.resetOriginalDmgup();
		this.resetOriginalBowDmgup();
		this.resetOriginalHitup();
		this.resetOriginalBowHitup();
		this.resetOriginalMr();
		this.resetOriginalMagicHit();
		this.resetOriginalMagicCritical();
		this.resetOriginalMagicConsumeReduction();
		this.resetOriginalMagicDamage();
		this.resetOriginalAc();
		this.resetOriginalEr();
		this.resetOriginalHpr();
		this.resetOriginalMpr();
		this.resetOriginalStrWeightReduction();
		this.resetOriginalConWeightReduction();
	}

	// 人物訊息拒絕清單
	private final L1ExcludingList _excludingList = new L1ExcludingList();

	/**
	 * 人物訊息拒絕清單
	 * 
	 * @return
	 */
	public L1ExcludingList getExcludingList() {
		return this._excludingList;
	}

	private int _teleportX = 0;// 傳送目的座標X

	/**
	 * 傳送目的座標X
	 * 
	 * @return
	 */
	public int getTeleportX() {
		return this._teleportX;
	}

	/**
	 * 傳送目的座標X
	 * 
	 * @param i
	 */
	public void setTeleportX(final int i) {
		this._teleportX = i;
	}

	private int _teleportY = 0;// 傳送目的座標Y

	/**
	 * 傳送目的座標Y
	 * 
	 * @return
	 */
	public int getTeleportY() {
		return this._teleportY;
	}

	/**
	 * 傳送目的座標Y
	 * 
	 * @param i
	 */
	public void setTeleportY(final int i) {
		this._teleportY = i;
	}

	private short _teleportMapId = 0;// 傳送目的座標MAP

	/**
	 * 傳送目的座標MAP
	 * 
	 * @return
	 */
	public short getTeleportMapId() {
		return this._teleportMapId;
	}

	/**
	 * 傳送目的座標MAP
	 * 
	 * @param i
	 */
	public void setTeleportMapId(final short i) {
		this._teleportMapId = i;
	}

	private int _teleportHeading = 0;// 傳送後面向

	/**
	 * 傳送後面向
	 * 
	 * @return
	 */
	public int getTeleportHeading() {
		return this._teleportHeading;
	}

	/**
	 * 傳送後面向
	 * 
	 * @param i
	 */
	public void setTeleportHeading(final int i) {
		this._teleportHeading = i;
	}

	private int _tempCharGfxAtDead;// 死亡時外型代號

	/**
	 * 死亡時外型代號
	 * 
	 * @return
	 */
	public int getTempCharGfxAtDead() {
		return this._tempCharGfxAtDead;
	}

	/**
	 * 死亡時外型代號
	 * 
	 * @param i
	 */
	private void setTempCharGfxAtDead(final int i) {
		this._tempCharGfxAtDead = i;
	}

	private boolean _isCanWhisper = true;// 全秘密語(收聽)

	/**
	 * 全秘密語(收聽)
	 * 
	 * @return flag true:接收 false:拒絕
	 */
	public boolean isCanWhisper() {
		return this._isCanWhisper;
	}

	/**
	 * 全秘密語(收聽)
	 * 
	 * @param flag flag true:接收 false:拒絕
	 */
	public void setCanWhisper(final boolean flag) {
		this._isCanWhisper = flag;
	}

	private boolean _isShowTradeChat = true;// 買賣頻道(收聽)

	/**
	 * 買賣頻道(收聽)
	 * 
	 * @return flag true:接收 false:拒絕
	 */
	public boolean isShowTradeChat() {
		return this._isShowTradeChat;
	}

	/**
	 * 買賣頻道(收聽)
	 * 
	 * @param flag true:接收 false:拒絕
	 */
	public void setShowTradeChat(final boolean flag) {
		this._isShowTradeChat = flag;
	}

	private boolean _isShowWorldChat = true;// 全體聊天(收聽)

	/**
	 * 全體聊天(收聽)
	 * 
	 * @return flag true:接收 false:拒絕
	 */
	public boolean isShowWorldChat() {
		return this._isShowWorldChat;
	}

	/**
	 * 全體聊天(收聽)
	 * 
	 * @param flag flag true:接收 false:拒絕
	 */
	public void setShowWorldChat(final boolean flag) {
		this._isShowWorldChat = flag;
	}

	private int _fightId;// 決鬥對象OBJID

	/**
	 * 決鬥對象OBJID
	 * 
	 * @return
	 */
	public int getFightId() {
		return this._fightId;
	}

	/**
	 * 決鬥對象OBJID
	 * 
	 * @param i
	 */
	public void setFightId(final int i) {
		this._fightId = i;
	}

	private byte _chatCount = 0;// 對話檢查次數

	private long _oldChatTimeInMillis = 0L;// 對話檢查毫秒差

	/**
	 * 對話檢查(洗畫面)
	 */
	public void checkChatInterval() {
		final long nowChatTimeInMillis = System.currentTimeMillis();
		if (this._chatCount == 0) {
			this._chatCount++;
			this._oldChatTimeInMillis = nowChatTimeInMillis;
			return;
		}

		final long chatInterval = nowChatTimeInMillis - this._oldChatTimeInMillis;
		// 時間差異2秒以上
		if (chatInterval > 2000) {
			this._chatCount = 0;
			this._oldChatTimeInMillis = 0;

		} else {
			if (this._chatCount >= 3) {
				this.setSkillEffect(STATUS_CHAT_PROHIBITED, 120 * 1000);
				this.sendPackets(new S_PacketBox(S_PacketBox.ICON_CHATBAN, 120));
				// \f3因洗畫面的關係，2分鐘之內無法聊天。
				this.sendPackets(new S_ServerMessage(153));
				this._chatCount = 0;
				this._oldChatTimeInMillis = 0;
			}
			this._chatCount++;
		}
	}

	private int _callClanId;// 呼喚盟友(對象OBJID)

	/**
	 * 傳回呼喚盟友(對象OBJID)
	 * 
	 * @return
	 */
	public int getCallClanId() {
		return this._callClanId;
	}

	/**
	 * 設置呼喚盟友(對象OBJID)
	 * 
	 * @param i
	 */
	public void setCallClanId(final int i) {
		this._callClanId = i;
	}

	private int _callClanHeading;// 設置呼喚盟友(自己的面向)

	/**
	 * 設置呼喚盟友(自己的面向)
	 * 
	 * @return
	 */
	public int getCallClanHeading() {
		return this._callClanHeading;
	}

	/**
	 * 傳回呼喚盟友(自己的面向)
	 * 
	 * @return
	 */
	public void setCallClanHeading(final int i) {
		this._callClanHeading = i;
	}

	private boolean _isInCharReset = false;// 執行人物重設狀態

	/**
	 * 傳回執行人物重設狀態
	 * 
	 * @return
	 */
	public boolean isInCharReset() {
		return this._isInCharReset;
	}

	/**
	 * 設置執行人物重設狀態
	 * 
	 * @param flag
	 */
	public void setInCharReset(final boolean flag) {
		this._isInCharReset = flag;
	}

	private int _tempLevel = 1;// 人物重置等級暫存(最低)

	/**
	 * 人物重置等級暫存(最低)
	 * 
	 * @return
	 */
	public int getTempLevel() {
		return this._tempLevel;
	}

	/**
	 * 人物重置等級暫存(最低)
	 * 
	 * @param i
	 */
	public void setTempLevel(final int i) {
		this._tempLevel = i;
	}

	private int _tempMaxLevel = 1;// 人物重置等級暫存(最高)

	/**
	 * 人物重置等級暫存(最高)
	 * 
	 * @return
	 */
	public int getTempMaxLevel() {
		return this._tempMaxLevel;
	}

	/**
	 * 人物重置等級暫存(最高)
	 * 
	 * @param i
	 */
	public void setTempMaxLevel(final int i) {
		this._tempMaxLevel = i;
	}

	private boolean _isSummonMonster = false;// 是否展開召喚控制選單

	/**
	 * 設置是否展開召喚控制選單
	 * 
	 * @param SummonMonster
	 */
	public void setSummonMonster(final boolean SummonMonster) {
		this._isSummonMonster = SummonMonster;
	}

	/**
	 * 是否展開召喚控制選單
	 * 
	 * @return
	 */
	public boolean isSummonMonster() {
		return this._isSummonMonster;
	}

	private boolean _isShapeChange = false;// 是否展開變身控制選單

	/**
	 * 設置是否展開變身控制選單
	 * 
	 * @param isShapeChange
	 */
	public void setShapeChange(final boolean isShapeChange) {
		this._isShapeChange = isShapeChange;
	}

	/**
	 * 是否展開變身控制選單
	 * 
	 * @return
	 */
	public boolean isShapeChange() {
		return this._isShapeChange;
	}

	private String _text;// 暫存文字串

	/**
	 * 設置暫存文字串(收件者)
	 *
	 * @param text
	 */
	public void setText(final String text) {
		this._text = text;
	}

	/**
	 * 傳回暫存文字串(收件者)
	 *
	 * @return
	 */
	public String getText() {
		return this._text;
	}

	private byte[] _textByte = null;// 暫存byte[]陣列

	/**
	 * 設定暫存byte[]陣列
	 *
	 * @param textByte
	 */
	public void setTextByte(final byte[] textByte) {
		this._textByte = textByte;
	}

	/**
	 * 傳回暫存byte[]陣列
	 *
	 * @return
	 */
	public byte[] getTextByte() {
		return this._textByte;
	}

	private L1PcOther _other;// 額外紀錄資料

	/**
	 * 額外紀錄資料
	 * 
	 * @param other
	 */
	public void set_other(final L1PcOther other) {
		this._other = other;
	}

	/**
	 * 額外紀錄資料
	 * 
	 * @return
	 */
	public L1PcOther get_other() {
		return this._other;
	}

	private L1PcOtherList _otherList;// 額外清單紀錄資料

	/**
	 * 額外清單紀錄資料
	 * 
	 * @param other
	 */
	public void set_otherList(final L1PcOtherList other) {
		_otherList = other;
	}

	/**
	 * 額外清單紀錄資料
	 * 
	 * @return
	 */
	public L1PcOtherList get_otherList() {
		return _otherList;
	}

	private int _oleLocX;// 移動前座標暫存X

	/**
	 * 移動前座標暫存X
	 * 
	 * @param oleLocx
	 */
	public void setOleLocX(final int oleLocx) {
		this._oleLocX = oleLocx;
	}

	/**
	 * 移動前座標暫存X
	 * 
	 * @return
	 */
	public int getOleLocX() {
		return this._oleLocX;
	}

	private int _oleLocY;// 移動前座標暫存Y

	/**
	 * 移動前座標暫存Y
	 * 
	 * @param oleLocy
	 */
	public void setOleLocY(final int oleLocy) {
		this._oleLocY = oleLocy;
	}

	/**
	 * 移動前座標暫存Y
	 * 
	 * @return
	 */
	public int getOleLocY() {
		return this._oleLocY;
	}

	private L1PcInstance _target = null;

	/**
	 * 設置目前攻擊對象
	 * 
	 * @param target
	 */
	public void setNowTarget(final L1PcInstance target) {
		this._target = target;
	}

	/**
	 * 傳回目前攻擊對象
	 */
	public L1PcInstance getNowTarget() {
		return this._target;
	}

	private int _dmgDown = 0;

	/**
	 * 副助道具傷害減免
	 * 
	 * @param dmgDown
	 */
	public void set_dmgDown(int dmgDown) {
		_dmgDown = dmgDown;
	}

	/**
	 * 副助道具傷害減免
	 * 
	 * @return
	 */
	public int get_dmgDown() {
		return _dmgDown;
	}

	/**
	 * 保存寵物目前模式
	 * 
	 * @param pc
	 */
	public void setPetModel() {
		try {
			// 寵物的跟隨移動
			for (final L1NpcInstance petNpc : getPetList().values()) {
				if (petNpc != null) {
					if (petNpc instanceof L1SummonInstance) { // 召喚獸的跟隨移動
						final L1SummonInstance summon = (L1SummonInstance) petNpc;
						summon.set_tempModel();

					} else if (petNpc instanceof L1PetInstance) { // 寵物的跟隨移動
						final L1PetInstance pet = (L1PetInstance) petNpc;
						pet.set_tempModel();
					}
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 恢復寵物目前模式
	 * 
	 * @param pc
	 */
	public void getPetModel() {
		try {
			// 寵物的跟隨移動
			for (final L1NpcInstance petNpc : getPetList().values()) {
				if (petNpc != null) {
					if (petNpc instanceof L1SummonInstance) { // 召喚獸的跟隨移動
						final L1SummonInstance summon = (L1SummonInstance) petNpc;
						summon.get_tempModel();

					} else if (petNpc instanceof L1PetInstance) { // 寵物的跟隨移動
						final L1PetInstance pet = (L1PetInstance) petNpc;
						pet.get_tempModel();
					}
				}
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	private long _h_time;// 生存吶喊時間

	/**
	 * 生存吶喊時間
	 * 
	 * @return
	 */
	public long get_h_time() {
		return _h_time;
	}

	/**
	 * 生存吶喊時間
	 * 
	 * @param h_time
	 */
	public void set_h_time(long time) {
		_h_time = time;
	}

	private boolean _mazu = false;// 媽祖祝福

	/**
	 * 媽祖祝福
	 * 
	 * @param b
	 */
	public void set_mazu(boolean b) {
		_mazu = b;
	}

	/**
	 * 媽祖祝福
	 * 
	 * @return
	 */
	public boolean is_mazu() {
		return _mazu;
	}

	private long _mazu_time = 0;// 媽祖祝福時間

	/**
	 * 媽祖祝福時間
	 * 
	 * @return
	 */
	public long get_mazu_time() {
		return _mazu_time;
	}

	/**
	 * 媽祖祝福時間
	 * 
	 * @param time
	 */
	public void set_mazu_time(long time) {
		_mazu_time = time;
	}

	private int _int1;// 機率增加攻擊力
	private int _int2;// 機率(1/100)

	/**
	 * 機率增加攻擊力
	 * 
	 * @param int1
	 * @param int2
	 */
	public void set_dmgAdd(int int1, int int2) {
		_int1 += int1;
		_int2 += int2;
	}

	/**
	 * 傳回機率增加的攻擊力
	 * 
	 * @return
	 */
	public int dmgAdd() {
		if (_int2 == 0) {
			return 0;
		}
		if ((_random.nextInt(100) + 1) <= _int2) {
			if (!getDolls().isEmpty()) {
				for (L1DollInstance doll : getDolls().values()) {
					doll.show_action(1);
				}
			}
			return _int1;
		}
		return 0;
	}

	private int _evasion;// 迴避機率(1/1000)

	/**
	 * 迴避機率
	 * 
	 * @param int1
	 */
	public void set_evasion(int int1) {
		_evasion += int1;
	}

	/**
	 * 傳回迴避機率
	 * 
	 * @return
	 */
	public int get_evasion() {
		return _evasion;
	}

	private double _expadd = 0.0D;// 經驗值增加

	/**
	 * 經驗值增加
	 * 
	 * @param int1
	 */
	public void set_expadd(int int1) {
		_expadd += (int1 / 100D);
	}

	/**
	 * 經驗值增加
	 * 
	 * @return
	 */
	public double getExpAdd() {
		return _expadd;
	}

	private int _dd1;// 機率傷害減免
	private int _dd2;// 機率(1/100)

	/**
	 * 機率傷害減免
	 * 
	 * @param int1
	 * @param int2
	 */
	public void set_dmgDowe(int int1, int int2) {
		_dd1 += int1;
		_dd2 += int2;
	}

	/**
	 * 傳回機率傷害減免
	 * 
	 * @return
	 */
	public int dmgDowe() {
		if (_dd2 == 0) {
			return 0;
		}
		if ((_random.nextInt(100) + 1) <= _dd2) {
			if (!getDolls().isEmpty()) {
				for (L1DollInstance doll : getDolls().values()) {
					doll.show_action(2);
				}
			}
			return _dd1;
		}
		return 0;
	}

	private boolean _isFoeSlayer = false;

	/**
	 * 是否使用屠宰者
	 * 
	 * @return
	 */
	public boolean isFoeSlayer() {
		return _isFoeSlayer;
	}

	/**
	 * 是否使用屠宰者
	 */
	public void isFoeSlayer(boolean isFoeSlayer) {
		_isFoeSlayer = isFoeSlayer;
	}

	private int _weaknss = 0;
	private long _weaknss_t = 0;// 時間

	/**
	 * 弱點曝光時間
	 * 
	 * @return
	 */
	public long get_weaknss_t() {
		return _weaknss_t;
	}

	/**
	 * 弱點曝光階段
	 * 
	 * @return
	 */
	public int get_weaknss() {
		return _weaknss;
	}

	/**
	 * 弱點曝光階段
	 * 
	 * @param lv
	 */
	public void set_weaknss(int lv, long t) {
		_weaknss = lv;
		_weaknss_t = t;
	}

	private int _actionId = -1;// 角色表情動作代號

	/**
	 * 角色表情動作代號
	 * 
	 * @param actionId
	 */
	public void set_actionId(int actionId) {
		_actionId = actionId;
	}

	/**
	 * 角色表情動作代號
	 * 
	 * @return
	 */
	public int get_actionId() {
		return _actionId;
	}

	private Chapter01R _hardin = null;// 哈汀副本線程

	/**
	 * 哈汀副本線程
	 * 
	 * @param hardin
	 */
	public void set_hardinR(Chapter01R hardin) {
		_hardin = hardin;
	}

	/**
	 * 哈汀副本線程
	 * 
	 * @return
	 */
	public Chapter01R get_hardinR() {
		return _hardin;
	}

	private int _unfreezingTime = 0;// 解除人物卡點

	public void set_unfreezingTime(int i) {
		_unfreezingTime = i;
	}

	public int get_unfreezingTime() {
		return _unfreezingTime;
	}

	private int _magic_modifier_dmg = 0;// 套裝增加魔法傷害

	public void add_magic_modifier_dmg(int add) {
		_magic_modifier_dmg += add;
	}

	public int get_magic_modifier_dmg() {
		return _magic_modifier_dmg;
	}

	private int _magic_reduction_dmg = 0;// 套裝減免魔法傷害

	public void add_magic_reduction_dmg(int add) {
		_magic_reduction_dmg += add;
	}

	public int get_magic_reduction_dmg() {
		return _magic_reduction_dmg;
	}

	private boolean _rname = false;// 重設名稱

	/**
	 * 重設名稱
	 * 
	 * @param b
	 */
	public void rename(boolean b) {
		_rname = b;
	}

	/**
	 * 重設名稱
	 * 
	 * @return
	 */
	public boolean is_rname() {
		return _rname;
	}

	private boolean _retitle = false;// 重設封號

	/**
	 * 重設封號
	 * 
	 * @return
	 */
	public boolean is_retitle() {
		return _retitle;
	}

	/**
	 * 重設封號
	 * 
	 * @param b
	 */
	public void retitle(boolean b) {
		_retitle = b;
	}

	private int _repass = 0;// 重設密碼

	/**
	 * 重設密碼
	 * 
	 * @return
	 */
	public int is_repass() {
		return _repass;
	}

	/**
	 * 重設密碼
	 * 
	 * @param b
	 */
	public void repass(int b) {
		_repass = b;
	}

	// 交易物品暫存
	private ArrayList<L1TradeItem> _trade_items = new ArrayList<L1TradeItem>();

	/**
	 * 加入交易物品暫存
	 * 
	 * @param info
	 */
	public void add_trade_item(L1TradeItem info) {
		if (_trade_items.size() == 16) {
			return;
		}
		_trade_items.add(info);
	}

	/**
	 * 交易物品暫存
	 * 
	 * @return
	 */
	public ArrayList<L1TradeItem> get_trade_items() {
		return _trade_items;
	}

	/**
	 * 清空交易物品暫存
	 */
	public void get_trade_clear() {
		_tradeID = 0;
		_trade_items.clear();
	}

	private int _mode_id = 0;// 記錄選取位置

	/**
	 * 記錄選取位置
	 * 
	 * @param mode
	 */
	public void set_mode_id(int mode) {
		_mode_id = mode;
	}

	/**
	 * 記錄選取位置
	 * 
	 * @return
	 */
	public int get_mode_id() {
		return _mode_id;
	}

	private boolean _check_item = false;

	public void set_check_item(boolean b) {
		_check_item = b;
	}

	public boolean get_check_item() {
		return _check_item;
	}

	private long _global_time = 0;

	public long get_global_time() {
		return _global_time;
	}

	public void set_global_time(final long global_time) {
		_global_time = global_time;
	}

	// DOLL 指定時間HP恢復

	private int _doll_hpr = 0;

	public int get_doll_hpr() {
		return _doll_hpr;
	}

	public void set_doll_hpr(int hpr) {
		_doll_hpr = hpr;
	}

	private int _doll_hpr_time = 0;// 計算用時間(秒)

	public int get_doll_hpr_time() {
		return _doll_hpr_time;
	}

	public void set_doll_hpr_time(int time) {
		_doll_hpr_time = time;
	}

	private int _doll_hpr_time_src = 0;// 恢復時間(秒)

	public int get_doll_hpr_time_src() {
		return _doll_hpr_time_src;
	}

	public void set_doll_hpr_time_src(int time) {
		_doll_hpr_time_src = time;
	}

	// DOLL 指定時間MP恢復

	private int _doll_mpr = 0;

	public int get_doll_mpr() {
		return _doll_mpr;
	}

	public void set_doll_mpr(int mpr) {
		_doll_mpr = mpr;
	}

	private int _doll_mpr_time = 0;// 計算用時間(秒)

	public int get_doll_mpr_time() {
		return _doll_mpr_time;
	}

	public void set_doll_mpr_time(int time) {
		_doll_mpr_time = time;
	}

	private int _doll_mpr_time_src = 0;// 恢復時間(秒)

	public int get_doll_mpr_time_src() {
		return _doll_mpr_time_src;
	}

	public void set_doll_mpr_time_src(int time) {
		_doll_mpr_time_src = time;
	}

	// DOLL 指定時間給予物品

	private int[] _doll_get = new int[2];

	public int[] get_doll_get() {
		return _doll_get;
	}

	public void set_doll_get(int itemid, int count) {
		_doll_get[0] = itemid;
		_doll_get[1] = count;
	}

	private int _doll_get_time = 0;// 計算用時間(秒)

	public int get_doll_get_time() {
		return _doll_get_time;
	}

	public void set_doll_get_time(int time) {
		_doll_get_time = time;
	}

	private int _doll_get_time_src = 0;// 給予時間(秒)

	public int get_doll_get_time_src() {
		return _doll_get_time_src;
	}

	public void set_doll_get_time_src(int time) {
		_doll_get_time_src = time;
	}

	// 留言版使用
	private String _board_title;// 暫存文字串

	public void set_board_title(final String text) {
		this._board_title = text;
	}

	public String get_board_title() {
		return this._board_title;
	}

	private String _board_content;// 暫存文字串

	public void set_board_content(final String text) {
		this._board_content = text;
	}

	public String get_board_content() {
		return this._board_content;
	}

	private int _killMonCount;

	public int getMonsterKill() {
		return _killMonCount;
	}

	public void addKillMonCount() {
		_killMonCount++;
	}

	public void resetKillMonCount() {
		_killMonCount = 0;
	}

	// 封包接收速度紀錄
	private long _spr_move_time = 0;// 移動

	public void set_spr_move_time(final long spr_time) {
		_spr_move_time = spr_time;
	}

	public long get_spr_move_time() {
		return this._spr_move_time;
	}

	private long _spr_attack_time = 0;// 攻擊

	public void set_spr_attack_time(final long spr_time) {
		_spr_attack_time = spr_time;
	}

	public long get_spr_attack_time() {
		return this._spr_attack_time;
	}

	private long _spr_skill_time = 0;// 技能

	public void set_spr_skill_time(final long spr_time) {
		_spr_skill_time = spr_time;
	}

	public long get_spr_skill_time() {
		return this._spr_skill_time;
	}

	// 死亡相關

	private int _delete_time = 0;// 死亡時間

	public void set_delete_time(final int time) {
		_delete_time = time;
	}

	public int get_delete_time() {
		return _delete_time;
	}

	// 藥水使用HP恢復增加

	private int _up_hp_potion = 0;

	/**
	 * 藥水使用HP恢復增加(1/100)
	 * 
	 * @param up_hp_potion
	 */
	public void set_up_hp_potion(final int up_hp_potion) {
		_up_hp_potion = up_hp_potion;
	}

	/**
	 * 藥水使用HP恢復增加(1/100)
	 * 
	 * @return
	 */
	public int get_up_hp_potion() {
		return _up_hp_potion;
	}

	// 法利昂的治癒守護(1/1000)

	private int _elitePlateMail_Fafurion = 0;
	private int _fafurion_hpmin = 0;
	private int _fafurion_hpmax = 0;

	/**
	 * 法利昂的治癒守護(1/1000)
	 * 
	 * @param r
	 */
	public void set_elitePlateMail_Fafurion(final int r, final int hpmin, final int hpmax) {
		_elitePlateMail_Fafurion = r;
		_fafurion_hpmin = hpmin;
		_fafurion_hpmax = hpmax;
	}

	// 毒性抵抗效果
	int _venom_resist = 0;

	/**
	 * 毒性抵抗效果(裝備)
	 * 
	 * @param i 裝備數量
	 */
	public void set_venom_resist(int i) {
		_venom_resist += i;
	}

	/**
	 * 毒性抵抗效果(裝備)
	 * 
	 * @return
	 */
	public int get_venom_resist() {
		return _venom_resist;
	}

	// 加速檢測器
	private AcceleratorChecker _speed = null;

	/**
	 * 加速檢測器
	 * 
	 * @return
	 */
	public AcceleratorChecker speed_Attack() {
		if (_speed == null) {
			_speed = new AcceleratorChecker(this);
		}
		return _speed;
	}

	private boolean _isShowClanChat = true;// 買賣頻道(收聽)

	public boolean isShowClanChat() {
		return this._isShowClanChat;
	}

	public void setShowClanChat(final boolean flag) {
		this._isShowClanChat = flag;
	}

	private boolean _isShowPartyChat = true;// 買賣頻道(收聽)

	public boolean isShowPartyChat() {
		return this._isShowPartyChat;
	}

	public void setShowPartyChat(final boolean flag) {
		this._isShowPartyChat = flag;
	}

	private byte _ringsExpansion;

	public final byte getRingsExpansion() {
		return this._ringsExpansion;
	}

	/**
	 * 戒指欄位擴充紀錄 3.63
	 * 
	 * @param i
	 */
	public final void setRingsExpansion(final byte i) {
		this._ringsExpansion = i;
	}
	
	/**
	 * 防止黑客 - 限制錯誤次數 ： 3
	 */
	private byte anti_hacking_count = 3;

	/**
	 * 取得 防止黑客 (剩餘次數)
	 */
	public byte get_anti_hacking_count() {
		return anti_hacking_count;
	}

	/**
	 * 設置 防止黑客 (剩餘次數)
	 */
	public void set_anti_hacking_count(byte anti_hacking_count) {
		this.anti_hacking_count = anti_hacking_count;
	}

}
