package com.lineage.server.model.Instance;

import com.lineage.server.datatables.PetItemTable;
import com.lineage.server.templates.L1Item;
import com.lineage.server.templates.L1ItemPower_name;
import com.lineage.server.templates.L1PetItem;
import com.lineage.server.utils.BinaryOutputStream;

/**
 * 物品詳細資料
 * @author dexc
 *
 */
public class L1ItemStatus {

	private final L1ItemInstance _itemInstance;
	
	private final L1Item _item;
	
	private final BinaryOutputStream _os;
	
	private final L1ItemPower _itemPower;
	
	/**
	 * 物品詳細資料
	 * @param itemInstance L1ItemInstance
	 */
	public L1ItemStatus(final L1ItemInstance itemInstance) {
		this._itemInstance = itemInstance;
		this._item = itemInstance.getItem();
		this._os = new BinaryOutputStream();
		this._itemPower = new L1ItemPower(this._itemInstance);
	}
	
	/**
	 * 物品詳細資料
	 * @param template L1Item
	 */
	public L1ItemStatus(final L1Item template) {
		this._itemInstance = new L1ItemInstance();
		this._itemInstance.setItem(template);
		this._item = template;
		this._os = new BinaryOutputStream();
		this._itemPower = new L1ItemPower(this._itemInstance);
	}
	
	public BinaryOutputStream getStatusBytes() {
		// 分類
		final int use_type = this._item.getUseType();
		switch (use_type) {
		case -11: // 对读取方法调用无法分类的物品
		case -10: // 加速药水
		case -9: // 技术书
		case -8: // 料理书
		case -7: // 增HP道具
		case -6: // 增MP道具
		case -5: // 食人妖精競賽票
		case -4: // 項圈
		case -1: // 無法使用(材料等)
		case 0: // 一般物品
		case 3: // 創造怪物魔杖(無須選取目標 - 無數量:沒有任何事情發生)
		case 5: // 魔杖類型(須選取目標)
		case 6: // 瞬間移動卷軸
		case 7: // 鑑定卷軸
		case 9: // 傳送回家的卷軸
		case 8: // 復活卷軸
		case 12: // 信紙
		case 13: // 信紙(寄出)
		case 14: // 請選擇一個物品(道具欄位)
		case 15: // 哨子
		case 16: // 變形卷軸
		case 17: // 選取目標 (近距離)
		case 26: // 對武器施法的卷軸
		case 27: // 對盔甲施法的卷軸
		case 28: // 空的魔法卷軸
		case 29: // 瞬間移動卷軸(祝福)
		case 30: // 魔法卷軸選取目標 (遠距離 無XY座標傳回)
		case 31: // 聖誕卡片
		case 32: // 聖誕卡片(寄出)
		case 33: // 情人節卡片
		case 34: // 情人節卡片(寄出)
		case 35: // 白色情人節卡片
		case 36: // 白色情人節卡片(寄出)
		case 39: // 選取目標 (遠距離)
		case 42: // 釣魚杆
		case 46: // 飾品強化捲軸
		case 55: // 請選擇魔法娃娃
			return this.etcitem();

		case -12: // 寵物用具
			final L1PetItem petItem = PetItemTable.get().getTemplate(this._item.getItemId());
			// 武器
			if (petItem.isWeapom()) {
				return this.petweapon(petItem);
			// 防具
			} else {
				return this.petarmor(petItem);
			}

		case -3: // 飛刀
		case -2: // 箭
			return this.arrow();

		case 38: // 食物
			return this.fooditem();

		case 10: // 照明道具
			return this.lightitem();

		case 2: // 盔甲
		case 18: // T恤
		case 19: // 斗篷
		case 20: // 手套
		case 21: // 靴
		case 22: // 頭盔
		case 25: // 盾牌
			return this.armor();
			
		case 40: // 耳環
		case 23: // 戒指
		case 24: // 項鍊
		case 37: // 腰帶
			return this.accessories();

		case 43: // 副助道具右
		case 44: // 副助道具左
		case 45: // 副助道具中  
		case 48: // 副助道具右下
		case 47: // 副助道具左下
			return this.accessories2();
			
		case 1: // 武器
			return this.weapon();
		}
		return null;
	}

	/**
	 * 飛刀
	 * 箭
	 * @return
	 */
	private BinaryOutputStream arrow() {
		this._os.writeC(0x01); // 打撃値
		this._os.writeC(this._item.getDmgSmall());
		this._os.writeC(this._item.getDmgLarge());
		this._os.writeC(this._item.getMaterial());
		this._os.writeD(this._itemInstance.getWeight());
		return this._os;
	}

	/**
	 * 食物
	 * @return
	 */
	private BinaryOutputStream fooditem() {
		this._os.writeC(0x15);
		// 栄養
		this._os.writeH(this._item.getFoodVolume());
		this._os.writeC(this._item.getMaterial());
		this._os.writeD(this._itemInstance.getWeight());
		return this._os;
	}

	/**
	 * 照明道具
	 * @return
	 */
	private BinaryOutputStream lightitem() {
		this._os.writeC(0x16);
		this._os.writeH(this._item.getLightRange());
		this._os.writeC(this._item.getMaterial());
		this._os.writeD(this._itemInstance.getWeight());
		return this._os;
	}

	/**
	 * 防具類
	 * @return
	 */
	private BinaryOutputStream armor() {
		// AC
		this._os.writeC(0x13);
		int ac = this._item.get_ac();
		if (ac < 0) {
			ac = Math.abs(ac);
		}
		this._os.writeC(ac);

		this._os.writeC(this._item.getMaterial());
		this._os.writeC(this._item.get_greater());// CNOP修正
		this._os.writeD(this._itemInstance.getWeight());

		// 強化数
		if (this._itemInstance.getEnchantLevel() != 0) {
			this._os.writeC(0x02);
			this._os.writeC(this._itemInstance.getEnchantLevel());
		}
		// 損傷度
		if (this._itemInstance.get_durability() != 0) {
			this._os.writeC(0x03);
			this._os.writeC(this._itemInstance.get_durability());
		}
		
		int pw_s1 = this._item.get_addstr();// 力量
		int pw_s2 = this._item.get_adddex();// 敏捷
		int pw_s3 = this._item.get_addcon();// 體質
		int pw_s4 = this._item.get_addwis();// 精神
		int pw_s5 = this._item.get_addint();// 智力
		int pw_s6 = this._item.get_addcha();// 魅力
		
		int pw_sHp = this._item.get_addhp();// +HP
		int pw_sMp = this._item.get_addmp();// +MP
		int pw_sMr = this._itemPower.getMr();// MR(抗魔)
		int pw_sSp = this._item.get_addsp();// SP(魔攻)
		
		int pw_sDg = this._item.getDmgModifierByArmor();// DG(攻擊力)
		int pw_sHi = this._item.getHitModifierByArmor();// Hit(攻擊成功)

		int pw_d4_1 = this._item.get_defense_fire();// 火屬性
		int pw_d4_2 = this._item.get_defense_water();// 水屬性
		int pw_d4_3 = this._item.get_defense_wind();// 風屬性
		int pw_d4_4 = this._item.get_defense_earth();// 地屬性
		
		int pw_k6_1 = this._item.get_regist_freeze();// 寒冰耐性
		int pw_k6_2 = this._item.get_regist_stone();// 石化耐性
		int pw_k6_3 = this._item.get_regist_sleep();// 睡眠耐性
		int pw_k6_4 = this._item.get_regist_blind();// 暗黑耐性
		int pw_k6_5 = this._item.get_regist_stun();// 昏迷耐性
		int pw_k6_6 = this._item.get_regist_sustain();// 支撑耐性

		// 攻撃成功
		if (pw_sHi != 0) {
			this._os.writeC(0x05);
			this._os.writeC(pw_sHi);
		}
		
		// 追加打撃
		if (pw_sDg != 0) {
			this._os.writeC(0x06);
			this._os.writeC(pw_sDg);
		}
		
		// 使用可能
		int bit = 0;
		bit |= this._item.isUseRoyal() ? 1 : 0;
		bit |= this._item.isUseKnight() ? 2 : 0;
		bit |= this._item.isUseElf() ? 4 : 0;
		bit |= this._item.isUseMage() ? 8 : 0;
		bit |= this._item.isUseDarkelf() ? 16 : 0;
		bit |= this._item.isUseDragonknight() ? 32 : 0;
		bit |= this._item.isUseIllusionist() ? 64 : 0;
		this._os.writeC(0x07);
		this._os.writeC(bit);
		
		// 弓命中追加
		if (this._item.getBowHitModifierByArmor() != 0) {
			this._os.writeC(0x18);
			this._os.writeC(this._item.getBowHitModifierByArmor());
		}

		// 弓傷害追加
		if (this._item.getBowDmgModifierByArmor() != 0) {
			this._os.writeC(0x23);
			this._os.writeC(this._item.getBowDmgModifierByArmor());
		}

		// 特別定義套裝
		int s6_1 = 0;// 力量
		int s6_2 = 0;// 敏捷
		int s6_3 = 0;// 體質
		int s6_4 = 0;// 精神
		int s6_5 = 0;// 智力
		int s6_6 = 0;// 魅力
		int aH_1 = 0;// +HP
		int aM_1 = 0;// +MP
		int aMR_1 = 0;// MR(抗魔)
		int aSP_1 = 0;// SP(魔攻)
		int aSS_1 = 0;// 加速效果
		int d4_1 = 0;// 火屬性
		int d4_2 = 0;// 水屬性
		int d4_3 = 0;// 風屬性
		int d4_4 = 0;// 地屬性
		int k6_1 = 0;// 寒冰耐性
		int k6_2 = 0;// 石化耐性
		int k6_3 = 0;// 睡眠耐性
		int k6_4 = 0;// 暗黑耐性
		int k6_5 = 0;// 昏迷耐性
		int k6_6 = 0;// 支撑耐性
		if (_itemInstance.isMatch()) {// 完成套裝
			s6_1 = _item.get_mode()[0];// 力量
			s6_2 = _item.get_mode()[1];// 敏捷
			s6_3 = _item.get_mode()[2];// 體質
			s6_4 = _item.get_mode()[3];// 精神
			s6_5 = _item.get_mode()[4];// 智力
			s6_6 = _item.get_mode()[5];// 魅力
			aH_1 = _item.get_mode()[6];// +HP
			aM_1 = _item.get_mode()[7];// +MP
			aMR_1 = _item.get_mode()[8];// MR(抗魔)
			aSP_1 = _item.get_mode()[9];// SP(魔攻)
			aSS_1 = _item.get_mode()[10];// 加速效果
			d4_1 = _item.get_mode()[11];// 火屬性
			d4_2 = _item.get_mode()[12];// 水屬性
			d4_3 = _item.get_mode()[13];// 風屬性
			d4_4 = _item.get_mode()[14];// 地屬性
			k6_1 = _item.get_mode()[15];// 寒冰耐性
			k6_2 = _item.get_mode()[16];// 石化耐性
			k6_3 = _item.get_mode()[17];// 睡眠耐性
			k6_4 = _item.get_mode()[18];// 暗黑耐性
			k6_5 = _item.get_mode()[19];// 昏迷耐性
			k6_6 = _item.get_mode()[20];// 支撑耐性
		}

		if (_itemInstance.get_power_name() != null) {
			final L1ItemPower_name power = _itemInstance.get_power_name();
			switch (power.get_hole_1()) {
			case 1:// 力  力+1
				s6_1 += 1;
				break;
			case 2:// 敏  敏+1
				s6_2 += 1;
				break;
			case 3:// 體  體+1 血+15
				s6_3 += 1;
				aH_1 += 15;
				break;
			case 4:// 精  精+1 魔+15
				s6_4 += 1;
				aM_1 += 15;
				break;
			case 5:// 智  智力+1
				s6_5 += 1;
				break;
			case 6:// 魅  魅力+1
				s6_6 += 1;
				break;
			case 7:// 血  血+25
				aH_1 += 25;
				break;
			case 8:// 魔  魔+25
				aM_1 += 25;
				break;
			case 9:// 攻
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				aMR_1 += 3;
				break;
			}
			switch (power.get_hole_2()) {
			case 1:// 力  力+1
				s6_1 += 1;
				break;
			case 2:// 敏  敏+1
				s6_2 += 1;
				break;
			case 3:// 體  體+1 血+15
				s6_3 += 1;
				aH_1 += 15;
				break;
			case 4:// 精  精+1 魔+15
				s6_4 += 1;
				aM_1 += 15;
				break;
			case 5:// 智  智力+1
				s6_5 += 1;
				break;
			case 6:// 魅  魅力+1
				s6_6 += 1;
				break;
			case 7:// 血  血+25
				aH_1 += 25;
				break;
			case 8:// 魔  魔+25
				aM_1 += 25;
				break;
			case 9:// 攻
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				aMR_1 += 3;
				break;
			}
			switch (power.get_hole_3()) {
			case 1:// 力  力+1
				s6_1 += 1;
				break;
			case 2:// 敏  敏+1
				s6_2 += 1;
				break;
			case 3:// 體  體+1 血+15
				s6_3 += 1;
				aH_1 += 15;
				break;
			case 4:// 精  精+1 魔+15
				s6_4 += 1;
				aM_1 += 15;
				break;
			case 5:// 智  智力+1
				s6_5 += 1;
				break;
			case 6:// 魅  魅力+1
				s6_6 += 1;
				break;
			case 7:// 血  血+25
				aH_1 += 25;
				break;
			case 8:// 魔  魔+25
				aM_1 += 25;
				break;
			case 9:// 攻
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				aMR_1 += 3;
				break;
			}
			switch (power.get_hole_4()) {
			case 1:// 力  力+1
				s6_1 += 1;
				break;
			case 2:// 敏  敏+1
				s6_2 += 1;
				break;
			case 3:// 體  體+1 血+15
				s6_3 += 1;
				aH_1 += 15;
				break;
			case 4:// 精  精+1 魔+15
				s6_4 += 1;
				aM_1 += 15;
				break;
			case 5:// 智  智力+1
				s6_5 += 1;
				break;
			case 6:// 魅  魅力+1
				s6_6 += 1;
				break;
			case 7:// 血  血+25
				aH_1 += 25;
				break;
			case 8:// 魔  魔+25
				aM_1 += 25;
				break;
			case 9:// 攻
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				aMR_1 += 3;
				break;
			}
			switch (power.get_hole_5()) {
			case 1:// 力  力+1
				s6_1 += 1;
				break;
			case 2:// 敏  敏+1
				s6_2 += 1;
				break;
			case 3:// 體  體+1 血+15
				s6_3 += 1;
				aH_1 += 15;
				break;
			case 4:// 精  精+1 魔+15
				s6_4 += 1;
				aM_1 += 15;
				break;
			case 5:// 智  智力+1
				s6_5 += 1;
				break;
			case 6:// 魅  魅力+1
				s6_6 += 1;
				break;
			case 7:// 血  血+25
				aH_1 += 25;
				break;
			case 8:// 魔  魔+25
				aM_1 += 25;
				break;
			case 9:// 攻
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				aMR_1 += 3;
				break;
			}
		}

		// 力量
		final int addstr = 
			pw_s1 + s6_1;
		if (addstr != 0) {
			this._os.writeC(0x08);
			this._os.writeC(addstr);
		}
		// 敏捷
		final int adddex = 
			pw_s2 + s6_2;
		if (adddex != 0) {
			this._os.writeC(0x09);
			this._os.writeC(adddex);
		}
		// 體質
		final int addcon = 
			pw_s3 + s6_3;
		if (addcon != 0) {
			this._os.writeC(0x0a);
			this._os.writeC(addcon);
		}
		// 精神
		final int addwis = 
			pw_s4 + s6_4;
		if (addwis != 0) {
			this._os.writeC(0x0b);
			this._os.writeC(addwis);
		}
		// 智力
		final int addint = 
			pw_s5 + s6_5;
		if (addint != 0) {
			this._os.writeC(0x0c);
			this._os.writeC(addint);
		}
		// 魅力
		final int addcha = 
			pw_s6 + s6_6;
		if (addcha != 0) {
			this._os.writeC(0x0d);
			this._os.writeC(addcha);
		}
		// +HP
		final int addhp = 
			pw_sHp + aH_1;
		if (addhp != 0) {
			this._os.writeC(0x0e);
			this._os.writeH(addhp);
		}
		// +MP
		final int addmp = 
			pw_sMp + aM_1;
		if (addmp != 0) {
			this._os.writeC(0x20);
			this._os.writeC(addmp);
		}
		// MR(抗魔)
		final int addmr = 
			pw_sMr + aMR_1;
		if (addmr != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(addmr);
		}
		// SP(魔攻)
		final int addsp = pw_sSp + aSP_1;
		if (addsp != 0) {
			this._os.writeC(0x11);
			this._os.writeC(addsp);
		}
		// 具備加速效果
		boolean haste = this._item.isHasteItem();

		if (aSS_1 == 1) {
			haste = true;
		}
		if (haste) {
			this._os.writeC(0x12);
		}
		// 增加火屬性
		final int fire = 
			pw_d4_1 + d4_1;
		if (fire != 0) {
			this._os.writeC(0x1b);
			this._os.writeC(fire);
		}
		// 增加水屬性
		final int water = 
			pw_d4_2 + d4_2;
		if (water != 0) {
			this._os.writeC(0x1c);
			this._os.writeC(water);
		}
		// 增加風屬性
		final int wind = 
			pw_d4_3 + d4_3;
		if (wind != 0) {
			this._os.writeC(0x1d);
			this._os.writeC(wind);
		}
		// 增加地屬性
		final int earth = 
			pw_d4_4 + d4_4;
		if (earth != 0) {
			this._os.writeC(0x1e);
			this._os.writeC(earth);
		}
		
		boolean isOut = false;
		// 寒冰耐性
		final int freeze = 
			pw_k6_1 + k6_1;
		//System.out.println("寒冰耐性:"+freeze);
		if (freeze != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(freeze);
			this._os.writeC(0x21);
			this._os.writeC(0x01);
		}
		// 石化耐性
		final int stone = 
			pw_k6_2 + k6_2;
		//System.out.println("石化耐性:"+stone);
		if (stone != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(stone);
			this._os.writeC(0x21);
			this._os.writeC(0x02);
		}
		// 睡眠耐性
		final int sleep = 
			pw_k6_3 + k6_3;
		//System.out.println("睡眠耐性:"+sleep);
		if (sleep != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(sleep);
			this._os.writeC(0x21);
			this._os.writeC(0x03);
		}
		// 暗黑耐性
		final int blind = 
			pw_k6_4 + k6_4;
		//System.out.println("暗黑耐性:"+blind);
		if (blind != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(blind);
			this._os.writeC(0x21);
			this._os.writeC(0x04);
		}
		// 昏迷耐性
		final int stun = 
			pw_k6_5 + k6_5;
		//System.out.println("昏迷耐性:"+stun);
		if (stun != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(stun);
			this._os.writeC(0x21);
			this._os.writeC(0x05);
		}
		// 支撑耐性
		final int sustain = 
			pw_k6_6 + k6_6;
		//System.out.println("支撑耐性:"+sustain);
		if (sustain != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(sustain);
			this._os.writeC(0x21);
			this._os.writeC(0x06);
		}
		return this._os;
	}

	/**
	 * 飾品類
	 * @return
	 */
	private BinaryOutputStream accessories() {
		// AC
		this._os.writeC(0x13);
		int ac = this._item.get_ac();
		if (ac < 0) {
			ac = Math.abs(ac);
		}
		this._os.writeC(ac);

		this._os.writeC(this._item.getMaterial());
		this._os.writeC(this._item.get_greater());// 飾品等級
		this._os.writeD(this._itemInstance.getWeight());
		
		int pw_s1 = this._item.get_addstr();// 力量
		int pw_s2 = this._item.get_adddex();// 敏捷
		int pw_s3 = this._item.get_addcon();// 體質
		int pw_s4 = this._item.get_addwis();// 精神
		int pw_s5 = this._item.get_addint();// 智力
		int pw_s6 = this._item.get_addcha();// 魅力
		
		int pw_sHp = this._item.get_addhp();// +HP
		int pw_sMp = this._item.get_addmp();// +MP
		int pw_sMr = this._itemPower.getMr();// MR(抗魔)
		int pw_sSp = this._item.get_addsp();// SP(魔攻)
		
		int pw_sDg = this._item.getDmgModifierByArmor();// DG(攻擊力)
		int pw_sHi = this._item.getHitModifierByArmor();// Hit(攻擊成功)

		int pw_d4_1 = this._item.get_defense_fire();// 火屬性
		int pw_d4_2 = this._item.get_defense_water();// 水屬性
		int pw_d4_3 = this._item.get_defense_wind();// 風屬性
		int pw_d4_4 = this._item.get_defense_earth();// 地屬性
		
		int pw_k6_1 = this._item.get_regist_freeze();// 寒冰耐性
		int pw_k6_2 = this._item.get_regist_stone();// 石化耐性
		int pw_k6_3 = this._item.get_regist_sleep();// 睡眠耐性
		int pw_k6_4 = this._item.get_regist_blind();// 暗黑耐性
		int pw_k6_5 = this._item.get_regist_stun();// 昏迷耐性
		int pw_k6_6 = this._item.get_regist_sustain();// 支撑耐性

		// 攻撃成功
		if (pw_sHi != 0) {
			this._os.writeC(0x05);
			this._os.writeC(pw_sHi);
		}
		
		// 追加打撃
		if (pw_sDg != 0) {
			this._os.writeC(0x06);
			this._os.writeC(pw_sDg);
		}
		
		// 使用可能
		int bit = 0;
		bit |= this._item.isUseRoyal() ? 1 : 0;
		bit |= this._item.isUseKnight() ? 2 : 0;
		bit |= this._item.isUseElf() ? 4 : 0;
		bit |= this._item.isUseMage() ? 8 : 0;
		bit |= this._item.isUseDarkelf() ? 16 : 0;
		bit |= this._item.isUseDragonknight() ? 32 : 0;
		bit |= this._item.isUseIllusionist() ? 64 : 0;
		this._os.writeC(0x07);
		this._os.writeC(bit);
		
		// 弓命中追加
		if (this._item.getBowHitModifierByArmor() != 0) {
			this._os.writeC(0x18);
			this._os.writeC(this._item.getBowHitModifierByArmor());
		}
		// 弓傷害追加
		if (this._item.getBowDmgModifierByArmor() != 0) {
			this._os.writeC(0x23);
			this._os.writeC(this._item.getBowDmgModifierByArmor());
		}

		// 特別定義套裝
		int s6_1 = 0;// 力量
		int s6_2 = 0;// 敏捷
		int s6_3 = 0;// 體質
		int s6_4 = 0;// 精神
		int s6_5 = 0;// 智力
		int s6_6 = 0;// 魅力
		int aH_1 = 0;// +HP
		int aM_1 = 0;// +MP
		int aMR_1 = 0;// MR(抗魔)
		int aSP_1 = 0;// SP(魔攻)
		int aSS_1 = 0;// 加速效果
		int d4_1 = 0;// 火屬性
		int d4_2 = 0;// 水屬性
		int d4_3 = 0;// 風屬性
		int d4_4 = 0;// 地屬性
		int k6_1 = 0;// 寒冰耐性
		int k6_2 = 0;// 石化耐性
		int k6_3 = 0;// 睡眠耐性
		int k6_4 = 0;// 暗黑耐性
		int k6_5 = 0;// 昏迷耐性
		int k6_6 = 0;// 支撑耐性
		
		if (this._itemInstance.isMatch()) {// 完成套裝
			s6_1 = this._item.get_mode()[0];// 力量
			s6_2 = this._item.get_mode()[1];// 敏捷
			s6_3 = this._item.get_mode()[2];// 體質
			s6_4 = this._item.get_mode()[3];// 精神
			s6_5 = this._item.get_mode()[4];// 智力
			s6_6 = this._item.get_mode()[5];// 魅力
			aH_1 = this._item.get_mode()[6];// +HP
			aM_1 = this._item.get_mode()[7];// +MP
			aMR_1 = this._item.get_mode()[8];// MR(抗魔)
			aSP_1 = this._item.get_mode()[9];// SP(魔攻)
			aSS_1 = this._item.get_mode()[10];// 加速效果
			d4_1 = this._item.get_mode()[11];// 火屬性
			d4_2 = this._item.get_mode()[12];// 水屬性
			d4_3 = this._item.get_mode()[13];// 風屬性
			d4_4 = this._item.get_mode()[14];// 地屬性
			k6_1 = this._item.get_mode()[15];// 寒冰耐性
			k6_2 = this._item.get_mode()[16];// 石化耐性
			k6_3 = this._item.get_mode()[17];// 睡眠耐性
			k6_4 = this._item.get_mode()[18];// 暗黑耐性
			k6_5 = this._item.get_mode()[19];// 昏迷耐性
			k6_6 = this._item.get_mode()[20];// 支撑耐性
		}

		// 力量
		final int addstr = pw_s1 + s6_1;
		if (addstr != 0) {
			this._os.writeC(0x08);
			this._os.writeC(addstr);
		}
		// 敏捷
		final int adddex = pw_s2 + s6_2;
		if (adddex != 0) {
			this._os.writeC(0x09);
			this._os.writeC(adddex);
		}
		// 體質
		final int addcon = pw_s3 + s6_3;
		if (addcon != 0) {
			this._os.writeC(0x0a);
			this._os.writeC(addcon);
		}
		// 精神
		final int addwis = pw_s4 + s6_4;
		if (addwis != 0) {
			this._os.writeC(0x0b);
			this._os.writeC(addwis);
		}
		// 智力
		final int addint = pw_s5 + s6_5;
		if (addint != 0) {
			this._os.writeC(0x0c);
			this._os.writeC(addint);
		}
		// 魅力
		final int addcha = pw_s6 + s6_6;
		if (addcha != 0) {
			this._os.writeC(0x0d);
			this._os.writeC(addcha);
		}

		// +HP MR 火 水 風 地 HP MP MR SP HPR MPR
		final int addhp = pw_sHp + this.greater()[4] + aH_1;
		if (addhp != 0) {
			this._os.writeC(0x0e);
			this._os.writeH(addhp);
		}
		
		// +MP MR 火 水 風 地 HP MP MR SP HPR MPR
		final int addmp = pw_sMp + this.greater()[5] + aM_1;
		if (addmp != 0) {
			this._os.writeC(0x20);
			this._os.writeC(addmp);
		}

		// MR(抗魔) MR 火 水 風 地 HP MP MR SP HPR MPR
		final int addmr = pw_sMr + this.greater()[6] + aMR_1;
		if (addmr != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(addmr);
		}
		// SP(魔攻)火 水 風 地 HP MP MR SP HPR MPR
		final int addsp = pw_sSp + this.greater()[7] + aSP_1;
		if (addsp != 0) {
			this._os.writeC(0x11);
			this._os.writeC(addsp);
		}

		// 具備加速效果
		boolean haste = this._item.isHasteItem();
		if (aSS_1 == 1) {
			haste = true;
		}
		if (haste) {
			this._os.writeC(0x12);
		}

		// 增加火屬性
		final int defense_fire = pw_d4_1 + this.greater()[0] + d4_1;
		if (defense_fire != 0) {
			this._os.writeC(0x1b);
			this._os.writeC(defense_fire);
		}

		// 增加水屬性
		final int defense_water = pw_d4_2 + this.greater()[1] + d4_2;
		if (defense_water != 0) {
			this._os.writeC(0x1c);
			this._os.writeC(defense_water);
		}

		// 增加風屬性
		final int defense_wind = pw_d4_3 + this.greater()[2] + d4_3;
		if (defense_wind != 0) {
			this._os.writeC(0x1d);
			this._os.writeC(defense_wind);
		}

		// 增加地屬性
		final int defense_earth = pw_d4_4 + this.greater()[3] + d4_4;
		if (defense_earth != 0) {
			this._os.writeC(0x1e);
			this._os.writeC(defense_earth);
		}

		boolean isOut = false;
		// 寒冰耐性
		final int freeze = pw_k6_1 + k6_1;
		if (freeze != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(freeze);
			this._os.writeC(0x21);
			this._os.writeC(0x01);
		}

		// 石化耐性
		final int stone = pw_k6_2 + k6_2;
		if (stone != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(stone);
			this._os.writeC(0x21);
			this._os.writeC(0x02);
		}

		// 睡眠耐性
		final int sleep = pw_k6_3 + k6_3;
		if (sleep != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(sleep);
			this._os.writeC(0x21);
			this._os.writeC(0x03);
		}

		// 暗黑耐性
		final int blind = pw_k6_4 + k6_4;
		if (blind != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(blind);
			this._os.writeC(0x21);
			this._os.writeC(0x04);
		}

		// 昏迷耐性
		final int stun = pw_k6_5 + k6_5;
		if (stun != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(stun);
			this._os.writeC(0x21);
			this._os.writeC(0x05);
		}

		// 支撑耐性
		final int sustain = pw_k6_6 + k6_6;
		if (sustain != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(sustain);
			this._os.writeC(0x21);
			this._os.writeC(0x06);
		}
		return this._os;
	}

	/**
	 * 副助道具
	 * @return
	 */
	private BinaryOutputStream accessories2() {
		// AC
		this._os.writeC(0x13);
		int ac = this._item.get_ac();
		if (ac < 0) {
			ac = Math.abs(ac);
		}
		this._os.writeC(ac);

		this._os.writeC(this._item.getMaterial());
		this._os.writeC(this._item.get_greater());// 飾品等級
		this._os.writeD(this._itemInstance.getWeight());
		
		int pw_s1 = this._item.get_addstr();// 力量
		int pw_s2 = this._item.get_adddex();// 敏捷
		int pw_s3 = this._item.get_addcon();// 體質
		int pw_s4 = this._item.get_addwis();// 精神
		int pw_s5 = this._item.get_addint();// 智力
		int pw_s6 = this._item.get_addcha();// 魅力
		
		int pw_sHp = this._item.get_addhp();// +HP
		int pw_sMp = this._item.get_addmp();// +MP
		int pw_sMr = this._itemPower.getMr();// MR(抗魔)
		int pw_sSp = this._item.get_addsp();// SP(魔攻)
		
		int pw_sDg = this._item.getDmgModifierByArmor();// DG(攻擊力)
		int pw_sHi = this._item.getHitModifierByArmor();// Hit(攻擊成功)

		int pw_d4_1 = this._item.get_defense_fire();// 火屬性
		int pw_d4_2 = this._item.get_defense_water();// 水屬性
		int pw_d4_3 = this._item.get_defense_wind();// 風屬性
		int pw_d4_4 = this._item.get_defense_earth();// 地屬性
		
		int pw_k6_1 = this._item.get_regist_freeze();// 寒冰耐性
		int pw_k6_2 = this._item.get_regist_stone();// 石化耐性
		int pw_k6_3 = this._item.get_regist_sleep();// 睡眠耐性
		int pw_k6_4 = this._item.get_regist_blind();// 暗黑耐性
		int pw_k6_5 = this._item.get_regist_stun();// 昏迷耐性
		int pw_k6_6 = this._item.get_regist_sustain();// 支撑耐性

		// 攻撃成功
		if (pw_sHi != 0) {
			this._os.writeC(0x05);
			this._os.writeC(pw_sHi);
		}
		
		// 追加打撃
		if (pw_sDg != 0) {
			this._os.writeC(0x06);
			this._os.writeC(pw_sDg);
		}
		
		// 使用可能
		int bit = 0;
		bit |= this._item.isUseRoyal() ? 1 : 0;
		bit |= this._item.isUseKnight() ? 2 : 0;
		bit |= this._item.isUseElf() ? 4 : 0;
		bit |= this._item.isUseMage() ? 8 : 0;
		bit |= this._item.isUseDarkelf() ? 16 : 0;
		bit |= this._item.isUseDragonknight() ? 32 : 0;
		bit |= this._item.isUseIllusionist() ? 64 : 0;
		this._os.writeC(0x07);
		this._os.writeC(bit);
		
		// 弓命中追加
		if (this._item.getBowHitModifierByArmor() != 0) {
			this._os.writeC(0x18);
			this._os.writeC(this._item.getBowHitModifierByArmor());
		}
		// 弓傷害追加
		if (this._item.getBowDmgModifierByArmor() != 0) {
			this._os.writeC(0x23);
			this._os.writeC(this._item.getBowDmgModifierByArmor());
		}

		// 力量
		final int addstr = pw_s1;
		if (addstr != 0) {
			this._os.writeC(0x08);
			this._os.writeC(addstr);
		}
		// 敏捷
		final int adddex = pw_s2;
		if (adddex != 0) {
			this._os.writeC(0x09);
			this._os.writeC(adddex);
		}
		// 體質
		final int addcon = pw_s3;
		if (addcon != 0) {
			this._os.writeC(0x0a);
			this._os.writeC(addcon);
		}
		// 精神
		final int addwis = pw_s4;
		if (addwis != 0) {
			this._os.writeC(0x0b);
			this._os.writeC(addwis);
		}
		// 智力
		final int addint = pw_s5;
		if (addint != 0) {
			this._os.writeC(0x0c);
			this._os.writeC(addint);
		}
		// 魅力
		final int addcha = pw_s6;
		if (addcha != 0) {
			this._os.writeC(0x0d);
			this._os.writeC(addcha);
		}

		// +HP MR 火 水 風 地 HP MP MR SP HPR MPR
		final int addhp = pw_sHp;
		if (addhp != 0) {
			this._os.writeC(0x0e);
			this._os.writeH(addhp);
		}
		
		// +MP MR 火 水 風 地 HP MP MR SP HPR MPR
		final int addmp = pw_sMp;
		if (addmp != 0) {
			this._os.writeC(0x20);
			this._os.writeC(addmp);
		}

		// MR(抗魔) MR 火 水 風 地 HP MP MR SP HPR MPR
		final int addmr = pw_sMr;
		if (addmr != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(addmr);
		}
		// SP(魔攻)火 水 風 地 HP MP MR SP HPR MPR
		final int addsp = pw_sSp;
		if (addsp != 0) {
			this._os.writeC(0x11);
			this._os.writeC(addsp);
		}

		// 具備加速效果
		boolean haste = this._item.isHasteItem();
		if (haste) {
			this._os.writeC(0x12);
		}

		// 增加火屬性
		final int defense_fire = pw_d4_1;
		if (defense_fire != 0) {
			this._os.writeC(0x1b);
			this._os.writeC(defense_fire);
		}

		// 增加水屬性
		final int defense_water = pw_d4_2;
		if (defense_water != 0) {
			this._os.writeC(0x1c);
			this._os.writeC(defense_water);
		}

		// 增加風屬性
		final int defense_wind = pw_d4_3;
		if (defense_wind != 0) {
			this._os.writeC(0x1d);
			this._os.writeC(defense_wind);
		}

		// 增加地屬性
		final int defense_earth = pw_d4_4;
		if (defense_earth != 0) {
			this._os.writeC(0x1e);
			this._os.writeC(defense_earth);
		}

		boolean isOut = false;
		// 寒冰耐性
		final int freeze = pw_k6_1;
		if (freeze != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(freeze);
			this._os.writeC(0x21);
			this._os.writeC(0x01);
		}

		// 石化耐性
		final int stone = pw_k6_2;
		if (stone != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(stone);
			this._os.writeC(0x21);
			this._os.writeC(0x02);
		}

		// 睡眠耐性
		final int sleep = pw_k6_3;
		if (sleep != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(sleep);
			this._os.writeC(0x21);
			this._os.writeC(0x03);
		}

		// 暗黑耐性
		final int blind = pw_k6_4;
		if (blind != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(blind);
			this._os.writeC(0x21);
			this._os.writeC(0x04);
		}

		// 昏迷耐性
		final int stun = pw_k6_5;
		if (stun != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(stun);
			this._os.writeC(0x21);
			this._os.writeC(0x05);
		}

		// 支撑耐性
		final int sustain = pw_k6_6;
		if (sustain != 0) {
			if (addmr != 0 && !isOut) {
				this._os.writeC(0x21);
				this._os.writeC(0xd6);
				isOut = true;
			}
			this._os.writeC(0x0f);
			this._os.writeH(sustain);
			this._os.writeC(0x21);
			this._os.writeC(0x06);
		}
		return this._os;
	}
	
	// 屬性武器
	private static final String[][] _attrEnchantString = new String[][]{
			new String[]{"1%束縛敵人0.8秒",		"2%束縛敵人1.0秒",	"3%束縛敵人1.5秒"},// 地之, 崩裂, 地靈
			new String[]{"1%發動1.2倍傷害",		"2%發動1.4倍傷害",	"3%發動1.6倍傷害"},// 火之, 烈焰, 火靈
			new String[]{"1%吸血吸魔",			"2%吸血吸魔",		"3%吸血吸魔"},// 水之, 海嘯, 水靈
			new String[]{"1%發動1格範圍傷害",	"2%發動2格範圍傷害",	"3%發動3格範圍傷害"},// 風之, 暴風, 風靈
			// ADD LOLI
			new String[]{"1%召喚光裂", 	"2%召喚光裂", 	"3%召喚光裂"},// "光之 ", "閃耀 ", "光靈 "
			new String[]{"1%施展闇盲", 	"2%施展闇盲", 	"3%施展闇盲"},// "暗之 ", "陰影 ", "暗靈 "
			new String[]{"1%施展魔封", 	"2%施展魔封", 	"3%施展魔封"},// "聖之 ", "神聖 ", "聖靈 "
			new String[]{"1%施展變形術", "2%施展變形術", "3%施展變形術"},// "邪之 ", "邪惡 ", "邪靈 "
	};

	/**
	 * 武器
	 * @return
	 */
	private BinaryOutputStream weapon() {
		// 打撃値
		this._os.writeC(0x01);
		this._os.writeC(this._item.getDmgSmall());
		this._os.writeC(this._item.getDmgLarge());

		this._os.writeC(this._item.getMaterial());
		this._os.writeD(this._itemInstance.getWeight());
		
		// 強化数
		if (this._itemInstance.getEnchantLevel() != 0) {
			this._os.writeC(0x02);
			this._os.writeC(this._itemInstance.getEnchantLevel());
		}
		// 損傷度
		if (this._itemInstance.get_durability() != 0) {
			this._os.writeC(0x03);
			this._os.writeC(this._itemInstance.get_durability());
		}
		// 両手武器
		if (this._item.isTwohandedWeapon()) {
			this._os.writeC(0x04);
		}

		int get_addstr = this._item.get_addstr();// 力量
		int get_adddex = this._item.get_adddex();// 敏捷
		int get_addcon = this._item.get_addcon();// 體質
		int get_addwis = this._item.get_addwis();// 精神
		int get_addint = this._item.get_addint();// 智力
		int get_addcha = this._item.get_addcha();// 魅力
		
		int get_addhp = this._item.get_addhp();// +HP
		int get_addmp = this._item.get_addmp();// +MP
		int mr = this._itemPower.getMr();// MR(抗魔)
		
		int addWeaponSp = this._item.get_addsp();// SP(魔攻)
		int addDmgModifier = this._item.getDmgModifier();// DG(攻擊力)
		int addHitModifier = this._item.getHitModifier();// Hit(攻擊成功)

		int pw_d4_1 = this._item.get_defense_fire();// 火屬性
		int pw_d4_2 = this._item.get_defense_water();// 水屬性
		int pw_d4_3 = this._item.get_defense_wind();// 風屬性
		int pw_d4_4 = this._item.get_defense_earth();// 地屬性
		
		int pw_k6_1 = this._item.get_regist_freeze();// 寒冰耐性
		int pw_k6_2 = this._item.get_regist_stone();// 石化耐性
		int pw_k6_3 = this._item.get_regist_sleep();// 睡眠耐性
		int pw_k6_4 = this._item.get_regist_blind();// 暗黑耐性
		int pw_k6_5 = this._item.get_regist_stun();// 昏迷耐性
		int pw_k6_6 = this._item.get_regist_sustain();// 支撑耐性

		if (_itemInstance.get_power_name() != null) {
			final L1ItemPower_name power = _itemInstance.get_power_name();
			switch (power.get_hole_1()) {
			case 1:// 力  力+1
				get_addstr += 1;
				break;
			case 2:// 敏  敏+1
				get_adddex += 1;
				break;
			case 3:// 體  體+1 血+15
				get_addcon += 1;
				get_addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				get_addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				get_addhp += 25;
				break;
			case 8:// 魔  魔+25
				get_addmp += 25;
				break;
			case 9:// 攻  額外攻擊+3
				addDmgModifier += 3;
				break;
			}
			switch (power.get_hole_2()) {
			case 1:// 力  力+1
				get_addstr += 1;
				break;
			case 2:// 敏  敏+1
				get_adddex += 1;
				break;
			case 3:// 體  體+1 血+15
				get_addcon += 1;
				get_addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				get_addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				get_addhp += 25;
				break;
			case 8:// 魔  魔+25
				get_addmp += 25;
				break;
			case 9:// 攻  額外攻擊+3
				addDmgModifier += 3;
				break;
			}
			switch (power.get_hole_3()) {
			case 1:// 力  力+1
				get_addstr += 1;
				break;
			case 2:// 敏  敏+1
				get_adddex += 1;
				break;
			case 3:// 體  體+1 血+15
				get_addcon += 1;
				get_addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				get_addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				get_addhp += 25;
				break;
			case 8:// 魔  魔+25
				get_addmp += 25;
				break;
			case 9:// 攻  額外攻擊+3
				addDmgModifier += 3;
				break;
			}
			switch (power.get_hole_4()) {
			case 1:// 力  力+1
				get_addstr += 1;
				break;
			case 2:// 敏  敏+1
				get_adddex += 1;
				break;
			case 3:// 體  體+1 血+15
				get_addcon += 1;
				get_addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				get_addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				get_addhp += 25;
				break;
			case 8:// 魔  魔+25
				get_addmp += 25;
				break;
			case 9:// 攻  額外攻擊+3
				addDmgModifier += 3;
				break;
			}
			switch (power.get_hole_5()) {
			case 1:// 力  力+1
				get_addstr += 1;
				break;
			case 2:// 敏  敏+1
				get_adddex += 1;
				break;
			case 3:// 體  體+1 血+15
				get_addcon += 1;
				get_addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				get_addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				get_addhp += 25;
				break;
			case 8:// 魔  魔+25
				get_addmp += 25;
				break;
			case 9:// 攻  額外攻擊+3
				addDmgModifier += 3;
				break;
			}
		}

		// 攻撃成功
		//int addHitModifier = this._item.getHitModifier() + pw_sHi;
		if (addHitModifier != 0) {
			this._os.writeC(0x05);
			this._os.writeC(addHitModifier);
		}
		
		// 追加打撃
		//int addDmgModifier = this._item.getDmgModifier() + pw_sDg;
		if (addDmgModifier != 0) {
			this._os.writeC(0x06);
			this._os.writeC(addDmgModifier);
		}
		
		// 使用可能
		int bit = 0;
		bit |= this._item.isUseRoyal() ? 1 : 0;
		bit |= this._item.isUseKnight() ? 2 : 0;
		bit |= this._item.isUseElf() ? 4 : 0;
		bit |= this._item.isUseMage() ? 8 : 0;
		bit |= this._item.isUseDarkelf() ? 16 : 0;
		bit |= this._item.isUseDragonknight() ? 32 : 0;
		bit |= this._item.isUseIllusionist() ? 64 : 0;
		this._os.writeC(0x07);
		this._os.writeC(bit);
		
		// 弓命中追加
		/*if (_item.getBowHitModifierByArmor() != 0) {
			os.writeC(24);
			os.writeC(_item.getBowHitModifierByArmor());
		}
		// 弓傷害追加
		if (_item.getBowDmgModifierByArmor() != 0) {
			os.writeC(35);
			os.writeC(_item.getBowDmgModifierByArmor());
		}*/
		// MP吸収
		if ((this._itemInstance.getItemId() == 126) || (this._itemInstance.getItemId() == 127)) {
			this._os.writeC(0x10);
		}
		// HP吸収
		if (this._itemInstance.getItemId() == 262) {
			this._os.writeC(0x22);
		}

		//int get_addstr = this._item.get_addstr();
		// STR~CHA
		if (get_addstr != 0) {
			this._os.writeC(0x08);
			this._os.writeC(get_addstr);
		}
		
		//int get_adddex = this._item.get_adddex();
		if (get_adddex != 0) {
			this._os.writeC(0x09);
			this._os.writeC(get_adddex);
		}
		
		//int get_addcon = this._item.get_addcon();
		if (get_addcon != 0) {
			this._os.writeC(0x0a);
			this._os.writeC(get_addcon);
		}
		
		//int get_addwis = this._item.get_addwis();
		if (get_addwis != 0) {
			this._os.writeC(0x0b);
			this._os.writeC(get_addwis);
		}
		
		//int get_addint = this._item.get_addint();
		if (get_addint != 0) {
			this._os.writeC(0x0c);
			this._os.writeC(get_addint);
		}
		
		//int get_addcha = this._item.get_addcha();
		if (get_addcha != 0) {
			this._os.writeC(0x0d);
			this._os.writeC(get_addcha);
		}
		
		// HP, MP
		
		//int get_addhp = this._item.get_addhp();
		if (get_addhp != 0) {
			this._os.writeC(0x0e);
			this._os.writeH(get_addhp);
		}
		
		//int get_addmp = this._item.get_addmp();
		if (get_addmp != 0) {
			this._os.writeC(0x20);
			this._os.writeC(get_addmp);
		}
		
		// MR
		//final int mr = this._itemPower.getMr();
		if (mr != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(mr);
		}
		// SP(魔法攻擊力)
		//int addWeaponSp = this._item.get_addsp() + pw_sSp;
		if (addWeaponSp != 0) {
			this._os.writeC(0x11);
			this._os.writeC(addWeaponSp);
		}
		// 具備加速效果
		if (this._item.isHasteItem()) {
			this._os.writeC(0x12);
		}
		// 增加火屬性
		if (pw_d4_1 != 0) {
			this._os.writeC(0x1b);
			this._os.writeC(pw_d4_1);
		}
		// 增加水屬性
		if (pw_d4_2!= 0) {
			this._os.writeC(0x1c);
			this._os.writeC(pw_d4_2);
		}
		// 增加風屬性
		if (pw_d4_3 != 0) {
			this._os.writeC(0x1d);
			this._os.writeC(pw_d4_3);
		}
		// 增加地屬性
		if (pw_d4_4 != 0) {
			this._os.writeC(0x1e);
			this._os.writeC(pw_d4_4);
		}

		// 凍結耐性
		if (pw_k6_1 != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(pw_k6_1);
			this._os.writeC(0x21);
			this._os.writeC(0x01);
		}
		// 石化耐性
		if (pw_k6_2 != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(pw_k6_2);
			this._os.writeC(0x21);
			this._os.writeC(0x02);
		}
		// 睡眠耐性
		if (pw_k6_3 != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(pw_k6_3);
			this._os.writeC(0x21);
			this._os.writeC(0x03);
		}
		// 暗闇耐性
		if (pw_k6_4 != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(pw_k6_4);
			this._os.writeC(0x21);
			this._os.writeC(0x04);
		}
		// 昏迷耐性
		if (pw_k6_5 != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(pw_k6_5);
			this._os.writeC(0x21);
			this._os.writeC(0x05);
		}
		// 支撑耐性
		if (pw_k6_6 != 0) {
			this._os.writeC(0x0f);
			this._os.writeH(pw_k6_6);
			this._os.writeC(0x21);
			this._os.writeC(0x06);
		}
		return this._os;
	}

	/**
	 * 額外文字顯示
	 * @param hole 類型
	 * @param type 1:武器 2:防具
	 * @return
	 */
	private String set_other_msg(final int hole, final int type) {
		String otherMsg = null;
		switch (hole) {
		case 1:// 力  力+1
			otherMsg = "[力]";
			break;
		case 2:// 敏  敏+1
			otherMsg = "[敏]";
			break;
		case 3:// 體  體+1 血+15
			otherMsg = "[體]";
			break;
		case 4:// 精  精+1 魔+15
			otherMsg = "[精]";
			break;
		case 5:// 智  智力+1
			otherMsg = "[智]";
			break;
		case 6:// 魅  魅力+1
			otherMsg = "[魅]";
			break;
		case 7:// 血  血+25
			otherMsg = "[生]";
			break;
		case 8:// 魔  魔+25
			otherMsg = "[魔]";
			break;
		case 9:// 攻  額外攻擊+3
			otherMsg = "[攻]";
			break;
		case 10:// 防  防禦-23
			otherMsg = "[防]";
			break;
		case 11:// 抗  抗魔+3
			otherMsg = "[抗]";
			break;
		}
		return otherMsg;
	}

	/**
	 * 一般道具
	 * @return
	 */
	private BinaryOutputStream etcitem() {
		this._os.writeC(0x17); // 材質
		this._os.writeC(this._item.getMaterial());
		this._os.writeD(this._itemInstance.getWeight());
		return this._os;
	}

	/**
	 * 寵物防具
	 * @return
	 */
	private BinaryOutputStream petarmor(final L1PetItem petItem) {
		this._os.writeC(0x13);
		int ac = petItem.getAddAc();
		if (ac < 0) {
			ac = Math.abs(ac);
		}
		this._os.writeC(ac);
		this._os.writeC(this._item.getMaterial());
		this._os.writeD(this._itemInstance.getWeight());

		if (petItem.getHitModifier() != 0) {
			this._os.writeC(5);
			this._os.writeC(petItem.getHitModifier());
		}

		if (petItem.getDamageModifier() != 0) {
			this._os.writeC(6);
			this._os.writeC(petItem.getDamageModifier());
		}

		if (petItem.isHigher()) {
			this._os.writeC(7);
			this._os.writeC(128);
		}

		if (petItem.getAddStr() != 0) {
			this._os.writeC(8);
			this._os.writeC(petItem.getAddStr());
		}
		if (petItem.getAddDex() != 0) {
			this._os.writeC(9);
			this._os.writeC(petItem.getAddDex());
		}
		if (petItem.getAddCon() != 0) {
			this._os.writeC(10);
			this._os.writeC(petItem.getAddCon());
		}
		if (petItem.getAddWis() != 0) {
			this._os.writeC(11);
			this._os.writeC(petItem.getAddWis());
		}
		if (petItem.getAddInt() != 0) {
			this._os.writeC(12);
			this._os.writeC(petItem.getAddInt());
		}

		// HP, MP
		if (petItem.getAddHp() != 0) {
			this._os.writeC(14);
			this._os.writeH(petItem.getAddHp());
		}
		if (petItem.getAddMp() != 0) {
			this._os.writeC(32);
			this._os.writeC(petItem.getAddMp());
		}
		// MR
		if (petItem.getAddMr() != 0) {
			this._os.writeC(15);
			this._os.writeH(petItem.getAddMr());
		}
		// SP(魔力)
		if (petItem.getAddSp() != 0) {
			this._os.writeC(17);
			this._os.writeC(petItem.getAddSp());
		}
		return this._os;
	}
	
	/**
	 * 寵物武器
	 * @return
	 */
	private BinaryOutputStream petweapon(final L1PetItem petItem) {
		this._os.writeC(0x01); // 打撃値
		this._os.writeC(0x00);
		this._os.writeC(0x00);
		this._os.writeC(this._item.getMaterial());
		this._os.writeD(this._itemInstance.getWeight());

		if (petItem.isHigher()) {
			this._os.writeC(7);
			this._os.writeC(128);
		}

		if (petItem.getAddStr() != 0) {
			this._os.writeC(8);
			this._os.writeC(petItem.getAddStr());
		}
		if (petItem.getAddDex() != 0) {
			this._os.writeC(9);
			this._os.writeC(petItem.getAddDex());
		}
		if (petItem.getAddCon() != 0) {
			this._os.writeC(10);
			this._os.writeC(petItem.getAddCon());
		}
		if (petItem.getAddWis() != 0) {
			this._os.writeC(11);
			this._os.writeC(petItem.getAddWis());
		}
		if (petItem.getAddInt() != 0) {
			this._os.writeC(12);
			this._os.writeC(petItem.getAddInt());
		}

		// HP, MP
		if (petItem.getAddHp() != 0) {
			this._os.writeC(14);
			this._os.writeH(petItem.getAddHp());
		}
		if (petItem.getAddMp() != 0) {
			this._os.writeC(32);
			this._os.writeC(petItem.getAddMp());
		}
		// MR
		if (petItem.getAddMr() != 0) {
			this._os.writeC(15);
			this._os.writeH(petItem.getAddMr());
		}
		return this._os;
	}

	/**
	 * 飾品能力顯示
	 *  火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
	 */
	private int[] greater() {
		final int level = this._itemInstance.getEnchantLevel();

		int[] rint = new int[10];
		switch (this._itemInstance.getItem().get_greater()) {
		case 0:// 高等
			switch (level) {
			case 0:
				break;
			case 1:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{1,1,1,1,0,0,0,0,0,0};
				break;
			case 2:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{2,2,2,2,0,0,0,0,0,0};
				break;
			case 3:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{3,3,3,3,0,0,0,0,0,0};
				break;
			case 4:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{4,4,4,4,0,0,0,0,0,0};
				break;
			case 5:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{5,5,5,5,0,0,0,0,0,0};
				break;
			case 6:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{6,6,6,6,0,0,0,0,1,1};
				break;
			case 7:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{10,10,10,10,0,0,0,0,3,3};
				break;
			default:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{15,15,15,15,0,0,0,0,3,3};
				break;
			}
			break;
			
		case 1:// 中等
			switch (level) {
			case 0:
				break;
			case 1:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,5,0,0,0,0,0};
				break;
			case 2:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,10,0,0,0,0,0};
				break;
			case 3:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,15,0,0,0,0,0};
				break;
			case 4:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,20,0,0,0,0,0};
				break;
			case 5:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,25,0,0,0,0,0};
				break;
			case 6:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,30,0,2,0,0,0};
				break;
			case 7:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,40,0,7,0,0,0};
				break;
			default:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,40,0,12,0,0,0};
				break;
			}
			break;
			
		case 2:// 初等
			switch (level) {
			case 0:
				break;
			case 1:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,0,3,0,0,0,0};
				break;
			case 2:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,0,6,0,0,0,0};
				break;
			case 3:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,0,9,0,0,0,0};
				break;
			case 4:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,0,12,0,0,0,0};
				break;
			case 5:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,0,15,0,0,0,0};
				break;
			case 6:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,0,25,0,1,0,0};
				break;
			case 7:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,0,40,0,2,0,0};
				break;
			default:
				// 火, 水, 風, 地, HP, MP, MR, SP, HPR, MPR
				rint = new int[]{0,0,0,0,0,40,0,3,0,0};
				break;
			}
			break;
			
		default:
			break;
		}
		return rint;
	}
}
