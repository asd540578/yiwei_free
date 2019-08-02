package com.lineage.server.model;

import static com.lineage.server.model.skill.L1SkillId.BLIND_HIDING;
import static com.lineage.server.model.skill.L1SkillId.COUNTER_BARRIER;
import static com.lineage.server.model.skill.L1SkillId.DETECTION;
import static com.lineage.server.model.skill.L1SkillId.ENCHANT_WEAPON;
import static com.lineage.server.model.skill.L1SkillId.EXTRA_HEAL;
import static com.lineage.server.model.skill.L1SkillId.GREATER_HASTE;
import static com.lineage.server.model.skill.L1SkillId.HASTE;
import static com.lineage.server.model.skill.L1SkillId.HEAL;
import static com.lineage.server.model.skill.L1SkillId.INVISIBILITY;
import static com.lineage.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static com.lineage.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;
import static com.lineage.server.model.skill.L1SkillId.STATUS_BRAVE;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.data.ItemClass;
import com.lineage.data.item_armor.set.ArmorSet;
import com.lineage.server.datatables.ItemTimeTable;
import com.lineage.server.datatables.lock.CharItemsTimeReading;
import com.lineage.server.datatables.lock.CharSkillReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Ability;
import com.lineage.server.serverpackets.S_AddSkill;
import com.lineage.server.serverpackets.S_DelSkill;
import com.lineage.server.serverpackets.S_Invis;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.templates.L1Item;
import com.lineage.server.templates.L1ItemPower_name;

/**
 * 防具武器的使用
 * @author dexc
 *
 */
public class L1EquipmentSlot {

	public static final Log _log = LogFactory.getLog(L1EquipmentSlot.class);

	// 執行人物
	private L1PcInstance _owner;

	// 作用中套裝
	private ArrayList<ArmorSet> _currentArmorSet;
	
	// 使用中防具
	private ArrayList<L1ItemInstance> _armors;

	// 作用中武器
	private L1ItemInstance _weapon;

	/**
	 * 防具武器的使用
	 * @param owner 執行人物
	 */
	public L1EquipmentSlot(final L1PcInstance owner) {
		this._owner = owner;

		this._armors = new ArrayList<L1ItemInstance>();
		this._currentArmorSet = new ArrayList<ArmorSet>();
	}

	/**
	 * 使用中武器
	 * @return
	 */
	public L1ItemInstance getWeapon() {
		return this._weapon;
	}
	
	/**
	 * 使用中防具清單
	 * @return
	 */
	public ArrayList<L1ItemInstance> getArmors() {
		return this._armors;
	}

	/**
	 * 武器裝備
	 * @param weapon
	 */
	private void setWeapon(final L1ItemInstance weapon) {
		this._owner.setWeapon(weapon);
		this._owner.setCurrentWeapon(weapon.getItem().getType1());
		weapon.startEquipmentTimer(this._owner);
		this._weapon = weapon;
	}

	/**
	 * 武器解除
	 * @param weapon
	 */
	private void removeWeapon(final L1ItemInstance weapon) {
		//final int itemId = weapon.getItem().getItemId();
		this._owner.setWeapon(null);
		this._owner.setCurrentWeapon(0);
		weapon.stopEquipmentTimer(this._owner);

		this._weapon = null;
		if (this._owner.hasSkillEffect(COUNTER_BARRIER)) {
			this._owner.removeSkillEffect(COUNTER_BARRIER);
		}
	}

	/**
	 * 防具穿著裝備
	 * @param armor
	 */
	private void setArmor(final L1ItemInstance armor) {
		final L1Item item = armor.getItem();
		final int itemId = armor.getItem().getItemId();

		// 取得物件觸發事件
		final int use_type = armor.getItem().getUseType();
		final int addac = addac_armor(armor);

		switch (use_type) {
		case 2:// 盔甲
			this._owner.addAc(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac);
			break;
			
		case 22:// 頭盔
			this._owner.addAc(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac);
			break;
			
		case 20:// 手套
			this._owner.addAc(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac);
			break;
			
		case 21:// 長靴
			this._owner.addAc(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac);
			break;

		case 18:// T恤
		case 19:// 斗篷
		case 25:// 盾牌
			this._owner.addAc(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac);
			break;
			
		case 23:// 戒指
		case 24:// 項鍊
		case 37:// 腰帶
		case 40:// 耳環
			if (item.get_ac() != 0) {
				this._owner.addAc(item.get_ac());
			}
			if (armor.getItem().get_greater() != 3) {
				armor.greater(this._owner, true);
			}
			break;

		case 43:// 副助道具-右
		case 44:// 副助道具-左
		case 45:// 副助道具-中 
		case 48:// 副助道具-右下
		case 47:// 副助道具-左下
			if (item.get_ac() != 0) {
				this._owner.addAc(item.get_ac());
			}
			break;
			
		default:
			break;
		}
		set_time_item(armor);
		
		this._owner.addDamageReductionByArmor(item.getDamageReduction());
		this._owner.addWeightReduction(item.getWeightReduction());
		
		int hit = item.getHitModifierByArmor();// Hit(攻擊成功)
		int dmg = item.getDmgModifierByArmor();// DG(攻擊力)
		
		this._owner.addHitModifierByArmor(hit);
		this._owner.addDmgModifierByArmor(dmg);
		
		this._owner.addBowHitModifierByArmor(item.getBowHitModifierByArmor());
		this._owner.addBowDmgModifierByArmor(item.getBowDmgModifierByArmor());

		final int addFire = 
			item.get_defense_fire();
		this._owner.addFire(addFire);// 增加火屬性
		final int addWater = 
			item.get_defense_water();
		this._owner.addWater(addWater);// 增加水屬性
		final int addWind = 
			item.get_defense_wind();
		this._owner.addWind(addWind);// 增加風屬性
		final int addEarth = 
			item.get_defense_earth();
		this._owner.addEarth(addEarth);// 增加地屬性

		final int add_regist_freeze = 
			item.get_regist_freeze();
		this._owner.add_regist_freeze(add_regist_freeze);// 寒冰耐性
		final int addRegistStone = 
			item.get_regist_stone();
		this._owner.addRegistStone(addRegistStone);// 石化耐性
		final int addRegistSleep = 
			item.get_regist_sleep();
		this._owner.addRegistSleep(addRegistSleep);// 睡眠耐性
		final int addRegistBlind = 
			item.get_regist_blind();
		this._owner.addRegistBlind(addRegistBlind);// 暗黑耐性
		final int addRegistStun = 
			item.get_regist_stun();
		this._owner.addRegistStun(addRegistStun);// 昏迷耐性
		final int addRegistSustain = 
			item.get_regist_sustain();
		this._owner.addRegistSustain(addRegistSustain);// 支撑耐性

		this._armors.add(armor);

		// 取回全部套裝
		for (final Integer key : ArmorSet.getAllSet().keySet()) {
			// 套裝資料
			final ArmorSet armorSet = ArmorSet.getAllSet().get(key);
			// 套裝中組件 並且 完成套裝
			if (armorSet.isPartOfSet(itemId) && armorSet.isValid(this._owner)) {
				if (armor.getItem().getUseType() == 23) {// 戒指
					if (!armorSet.isEquippedRingOfArmorSet(this._owner)) {
						armorSet.giveEffect(this._owner);
						this._currentArmorSet.add(armorSet);
						this._owner.getInventory().setPartMode(armorSet, true);
					}
					
				} else {
					armorSet.giveEffect(this._owner);
					this._currentArmorSet.add(armorSet);
					this._owner.getInventory().setPartMode(armorSet, true);
				}
			}
		}
		// 計時物件啟用
		armor.startEquipmentTimer(this._owner);
	}

	private int addac_armor(L1ItemInstance armor) {
		int addac = 0;
		if (armor.get_power_name() != null) {
			final L1ItemPower_name power = armor.get_power_name();
			switch (power.get_hole_1()) {
			case 10:// 防  防禦-2
				addac += 2;
				break;
			}
			switch (power.get_hole_2()) {
			case 10:// 防  防禦-2
				addac += 2;
				break;
			}
			switch (power.get_hole_3()) {
			case 10:// 防  防禦-2
				addac += 2;
				break;
			}
			switch (power.get_hole_4()) {
			case 10:// 防  防禦-2
				addac += 2;
				break;
			}
			switch (power.get_hole_5()) {
			case 10:// 防  防禦-2
				addac += 2;
				break;
			}
		}
		return addac;
	}

	/**
	 * 給予時間限制物品
	 * @param item
	 */
	private void set_time_item(final L1ItemInstance item) {
		if (item.get_time() == null) {
			int date = -1;
			if (ItemTimeTable.TIME.get(item.getItemId()) != null) {
				date = ItemTimeTable.TIME.get(item.getItemId()).intValue();
			}

			if (date != -1) {
				long time = System.currentTimeMillis();// 目前時間豪秒
				long x1 = date * 60 * 60;// 指定小時耗用秒數
				long x2 = x1 * 1000;// 轉為豪秒
				long upTime = x2 + time;// 目前時間 加上指定天數耗用秒數
				
				// 時間數據
				final Timestamp ts = new Timestamp(upTime);
				item.set_time(ts);
				
				// 人物背包物品使用期限資料
				CharItemsTimeReading.get().addTime(item.getId(), ts);
				_owner.sendPackets(new S_ItemName(item));
			}
		}
	}
	
	/**
	 * 解除裝備
	 * @param armor
	 */
	private void removeArmor(final L1ItemInstance armor) {
		final L1Item item = armor.getItem();
		final int itemId = armor.getItem().getItemId();
		// 取得物件觸發事件
		final int use_type = armor.getItem().getUseType();
		final int addac = addac_armor(armor);
		
		switch (use_type) {
		case 2:// 盔甲
			this._owner.addAc(-(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac));
			break;
			
		case 22:// 頭盔
			this._owner.addAc(-(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac));
			break;
			
		case 20:// 手套
			this._owner.addAc(-(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac));
			break;
			
		case 21:// 長靴
			this._owner.addAc(-(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac));
			break;
			
		case 18:// T恤
		case 19:// 斗篷
		case 25:// 盾牌
			this._owner.addAc(-(item.get_ac() - armor.getEnchantLevel() - armor.getAcByMagic() - addac));
			break;
			
		case 23:// 戒指
		case 24:// 項鍊
		case 37:// 腰帶
		case 40:// 耳環
			if (item.get_ac() != 0) {
				this._owner.addAc(-item.get_ac());
			}
			if (armor.getItem().get_greater() != 3) {
				armor.greater(this._owner, false);
			}
			break;

		case 43:// 副助道具-右
		case 44:// 副助道具-左
		case 45:// 副助道具-中 
		case 48:// 副助道具-右下
		case 47:// 副助道具-左下
			if (item.get_ac() != 0) {
				this._owner.addAc(-item.get_ac());
			}
			break;
			
		default:
			break;
		}
		
		this._owner.addDamageReductionByArmor(-item.getDamageReduction());
		this._owner.addWeightReduction(-item.getWeightReduction());
		
		int hit = item.getHitModifierByArmor();// Hit(攻擊成功)
		int dmg = item.getDmgModifierByArmor();// DG(攻擊力)
		
		this._owner.addHitModifierByArmor(-hit);
		this._owner.addDmgModifierByArmor(-dmg);
		
		this._owner.addBowHitModifierByArmor(-item.getBowHitModifierByArmor());
		this._owner.addBowDmgModifierByArmor(-item.getBowDmgModifierByArmor());

		final int addFire = 
			item.get_defense_fire();
		this._owner.addFire(-addFire);// 增加火屬性
		final int addWater = 
			item.get_defense_water();
		this._owner.addWater(-addWater);// 增加水屬性
		final int addWind = 
			item.get_defense_wind();
		this._owner.addWind(-addWind);// 增加風屬性
		final int addEarth = 
			item.get_defense_earth();
		this._owner.addEarth(-addEarth);// 增加地屬性

		final int add_regist_freeze = 
			item.get_regist_freeze();
		this._owner.add_regist_freeze(-add_regist_freeze);// 寒冰耐性
		final int addRegistStone = 
			item.get_regist_stone();
		this._owner.addRegistStone(-addRegistStone);// 石化耐性
		final int addRegistSleep = 
			item.get_regist_sleep();
		this._owner.addRegistSleep(-addRegistSleep);// 睡眠耐性
		final int addRegistBlind = 
			item.get_regist_blind();
		this._owner.addRegistBlind(-addRegistBlind);// 暗黑耐性
		final int addRegistStun = 
			item.get_regist_stun();
		this._owner.addRegistStun(-addRegistStun);// 昏迷耐性
		final int addRegistSustain = 
			item.get_regist_sustain();
		this._owner.addRegistSustain(-addRegistSustain);// 支撑耐性

		// 取回全部套裝
		for (final Integer key : ArmorSet.getAllSet().keySet()) {
			// 套裝資料
			final ArmorSet armorSet = ArmorSet.getAllSet().get(key);
			// 套裝中組件 作用中套裝具有該套裝資料 並且套裝未完成
			if (armorSet.isPartOfSet(itemId)
					&& this._currentArmorSet.contains(armorSet)
					&& !armorSet.isValid(this._owner)) {
				armorSet.cancelEffect(this._owner);
				// 移除作用中套裝
				this._currentArmorSet.remove(armorSet);
				this._owner.getInventory().setPartMode(armorSet, false);
			}
		}
		
		// 計時物件停止計時
		armor.stopEquipmentTimer(this._owner);

		this._armors.remove(armor);
	}

	/**
	 * 設置物品裝備
	 * @param eq
	 */
	public void set(final L1ItemInstance eq) {
		final L1Item item = eq.getItem();
		if (item.getType2() == 0) {
			return;
		}

		int addhp = item.get_addhp();
		int addmp = item.get_addmp();
		int get_addstr = item.get_addstr();
		int get_adddex = item.get_adddex();
		int get_addcon = item.get_addcon();
		int get_addwis = item.get_addwis();
		int get_addint = item.get_addint();
		int get_addcha = item.get_addcha();
		int addMr = 0;

		if (eq.get_power_name() != null) {
			final L1ItemPower_name power = eq.get_power_name();
			switch (power.get_hole_1()) {
			case 1:// 力  力+1
				get_addstr += 1;
				break;
			case 2:// 敏  敏+1
				get_adddex += 1;
				break;
			case 3:// 體  體+1 血+15
				get_addcon += 1;
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
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
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
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
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
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
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
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
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
				break;
			}
		}
		
		if (addhp != 0) {
			this._owner.addMaxHp(addhp);// +HP
		}
		if (addmp != 0) {
			this._owner.addMaxMp(addmp);// +MP
		}

		this._owner.addStr(get_addstr);// 力量
		this._owner.addDex(get_adddex);// 敏捷
		this._owner.addCon(get_addcon);// 體質
		this._owner.addWis(get_addwis);// 精神
		if (get_addwis != 0) {
			this._owner.resetBaseMr();
		}
		this._owner.addInt(get_addint);// 智力
		this._owner.addCha(get_addcha);// 魅力

		addMr += eq.getMr();
		// 精靈盾牌
		if (eq.getName().equals("$187") && this._owner.isElf()) {
			addMr += 5;
		}
		if (addMr != 0) {
			this._owner.addMr(addMr);
			this._owner.sendPackets(new S_SPMR(this._owner));
		}
		
		int addSp = 0;
		addSp += item.get_addsp();
		if (addSp != 0) {
			this._owner.addSp(addSp);
			this._owner.sendPackets(new S_SPMR(this._owner));
		}
		
		// 具備加速
		boolean isHasteItem = item.isHasteItem();
		if (isHasteItem) {
			this._owner.addHasteItemEquipped(1);
			this._owner.removeHasteSkillEffect();
			if (this._owner.getMoveSpeed() != 1) {
				this._owner.setMoveSpeed(1);
				this._owner.sendPackets(new S_SkillHaste(this._owner.getId(), 1, -1));
				this._owner.broadcastPacketAll(new S_SkillHaste(this._owner.getId(), 1, 0));
			}
		}

		switch (item.getType2() ) {
		case 1:// 武器
			this.setWeapon(eq);
			ItemClass.get().item_weapon(true, this._owner, eq);
			break;
			
		case 2:// 防具
			this.setArmor(eq);
			this.setMagic(eq);
			ItemClass.get().item_armor(true, this._owner, eq);
			break;
		}
	}

	/**
	 * 解除物品裝備
	 * @param eq
	 */
	public void remove(final L1ItemInstance eq) {
		final L1Item item = eq.getItem();
		if (item.getType2() == 0) {
			return;
		}

		int addhp = item.get_addhp();
		int addmp = item.get_addmp();
		
		int get_addstr = item.get_addstr();
		int get_adddex = item.get_adddex();
		int get_addcon = item.get_addcon();
		int get_addwis = item.get_addwis();
		int get_addint = item.get_addint();
		int get_addcha = item.get_addcha();
		int addMr = 0;

		if (eq.get_power_name() != null) {
			final L1ItemPower_name power = eq.get_power_name();
			switch (power.get_hole_1()) {
			case 1:// 力  力+1
				get_addstr += 1;
				break;
			case 2:// 敏  敏+1
				get_adddex += 1;
				break;
			case 3:// 體  體+1 血+15
				get_addcon += 1;
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
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
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
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
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
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
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
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
				addhp += 15;
				break;
			case 4:// 精  精+1 魔+15
				get_addwis += 1;
				addmp += 15;
				break;
			case 5:// 智  智力+1
				get_addint += 1;
				break;
			case 6:// 魅  魅力+1 
				get_addcha += 1;
				break;
			case 7:// 血  血+25
				addhp += 25;
				break;
			case 8:// 魔  魔+25
				addmp += 25;
				break;
			case 9:// 額 額外攻擊+3
				break;
			case 10:// 防  防禦-2
				break;
			case 11:// 抗  抗魔+3
				addMr += 3;
				break;
			}
		}
		
		if (addmp != 0) {
			this._owner.addMaxMp(-addmp);// +MP
		}
		if (addhp != 0) {
			this._owner.addMaxHp(-addhp);// +HP
		}
		
		this._owner.addStr((byte) -get_addstr);// 力量
		this._owner.addDex((byte) -get_adddex);// 敏捷
		this._owner.addCon((byte) -get_addcon);// 體質
		this._owner.addWis((byte) -get_addwis);// 精神
		if (get_addwis != 0) {
			this._owner.resetBaseMr();
		}
		this._owner.addInt((byte) -get_addint);// 智力
		this._owner.addCha((byte) -get_addcha);// 魅力

		addMr += eq.getMr();
		// 精靈盾牌
		if (eq.getName().equals("$187") && this._owner.isElf()) {
			addMr += 5;
		}
		if (addMr != 0) {
			this._owner.addMr(-addMr);
			this._owner.sendPackets(new S_SPMR(this._owner));
		}
		
		int addSp = 0;
		addSp -= item.get_addsp();
		if (addSp != 0) {
			this._owner.addSp(addSp);
			this._owner.sendPackets(new S_SPMR(this._owner));
		}
		
		// 具備加速
		boolean isHasteItem = item.isHasteItem();

		if (isHasteItem) {
			this._owner.addHasteItemEquipped(-1);
			if (this._owner.getHasteItemEquipped() == 0) {
				this._owner.setMoveSpeed(0);
				this._owner.sendPacketsAll(new S_SkillHaste(this._owner.getId(), 0, 0));
			}
		}
		switch (item.getType2()) {
		case 1:// 武器
			this.removeWeapon(eq);
			ItemClass.get().item_weapon(false, this._owner, eq);
			break;
			
		case 2:// 防具
			this.removeMagic(this._owner.getId(), eq);
			this.removeArmor(eq);
			ItemClass.get().item_armor(false, this._owner, eq);
			break;
		}
	}

	/**
	 * 設置(魔法道具)
	 * @param item
	 */
	private void setMagic(final L1ItemInstance item) {
		switch (item.getItemId()) {
		case 20077: // 隱身斗篷
		case 120077: // 隱身斗篷
		case 20062: // 炎魔的血光斗篷
			if (!this._owner.hasSkillEffect(INVISIBILITY)) {
				this._owner.killSkillEffectTimer(BLIND_HIDING);
				this._owner.setSkillEffect(INVISIBILITY, 0);
				this._owner.sendPackets(new S_Invis(this._owner.getId(), 1));
				this._owner.broadcastPacketAll(new S_RemoveObject(this._owner));
			}
			break;
			
		case 20288: // 傳送控制戒指
			this._owner.sendPackets(new S_Ability(1, true));
			break;
			
		case 20281: // 變形控制戒指
			//this._owner.sendPackets(new S_Ability(2, true));
			break;
			
		case 20383: // 軍馬頭盔
			if (item.getChargeCount() != 0) {
				// 可用次數 -1
				item.setChargeCount(item.getChargeCount() - 1);
				this._owner.getInventory().updateItem(item, L1PcInventory.COL_CHARGE_COUNT);
			}
			if (this._owner.hasSkillEffect(STATUS_BRAVE)) {
				this._owner.killSkillEffectTimer(STATUS_BRAVE);
				this._owner.sendPacketsAll(new S_SkillBrave(this._owner.getId(), 0, 0));
				this._owner.setBraveSpeed(0);
			}
			break;
			
		case 20013: // 敏捷魔法頭盔
			if (!this._owner.isSkillMastery(PHYSICAL_ENCHANT_DEX)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, PHYSICAL_ENCHANT_DEX));
			}
			if (!this._owner.isSkillMastery(HASTE)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, HASTE));
			}
			break;
			
		case 20014: // 治癒魔法頭盔
			if (!this._owner.isSkillMastery(HEAL)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, HEAL));
			}
			if (!this._owner.isSkillMastery(EXTRA_HEAL)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, EXTRA_HEAL));
			}
			break;
			
		case 20015: // 力量魔法頭盔
			if (!this._owner.isSkillMastery(ENCHANT_WEAPON)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, ENCHANT_WEAPON));
			}
			if (!this._owner.isSkillMastery(DETECTION)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, DETECTION));
			}
			if (!this._owner.isSkillMastery(PHYSICAL_ENCHANT_STR)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, PHYSICAL_ENCHANT_STR));
			}
			break;
			
		case 20008: // 小型風之頭盔
			if (!this._owner.isSkillMastery(HASTE)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, HASTE));
			}
			break;
			
		case 20023: // 風之頭盔
			if (!this._owner.isSkillMastery(GREATER_HASTE)) {
				this._owner.sendPackets(new S_AddSkill(this._owner, GREATER_HASTE));
			}
			break;
			
		default:// 其他道具
			break;
		}
	}

	/**
	 * 解除(魔法道具)
	 * @param objectId
	 * @param item
	 */
	private void removeMagic(final int objectId, final L1ItemInstance item) {
		switch (item.getItemId()) {
		case 20077: // 隱身斗篷
		case 120077: // 隱身斗篷
		case 20062: // 炎魔的血光斗篷
			this._owner.delInvis(); // 隱身解除
			break;
			
		case 20288: // 傳送控制戒指
			this._owner.sendPackets(new S_Ability(1, false));
			break;
			
		case 20281: // 變形控制戒指
			//this._owner.sendPackets(new S_Ability(2, false));
			break;
			
		case 20383: // 軍馬頭盔
			break;
			
		case 20013: // 敏捷魔法頭盔
			if (!CharSkillReading.get().spellCheck(objectId, PHYSICAL_ENCHANT_DEX)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, PHYSICAL_ENCHANT_DEX));
			}
			if (!CharSkillReading.get().spellCheck(objectId, HASTE)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, HASTE));
			}
			break;
			
		case 20014: // 治癒魔法頭盔
			if (!CharSkillReading.get().spellCheck(objectId, HEAL)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, HEAL));
			}
			if (!CharSkillReading.get().spellCheck(objectId, EXTRA_HEAL)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, EXTRA_HEAL));
			}
			break;
			
		case 20015: // 力量魔法頭盔
			if (!CharSkillReading.get().spellCheck(objectId, ENCHANT_WEAPON)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, ENCHANT_WEAPON));
			}
			if (!CharSkillReading.get().spellCheck(objectId, DETECTION)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, DETECTION));
			}
			if (!CharSkillReading.get().spellCheck(objectId, PHYSICAL_ENCHANT_STR)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, PHYSICAL_ENCHANT_STR));
			}
			break;
			
		case 20008: // 小型風之頭盔
			if (!CharSkillReading.get().spellCheck(objectId, HASTE)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, HASTE));
			}
			break;
			
		case 20023: // 風之頭盔
			if (!CharSkillReading.get().spellCheck(objectId, GREATER_HASTE)) {
				this._owner.sendPackets(new S_DelSkill(this._owner, GREATER_HASTE));
			}
			break;
			
		default:// 其他道具
			break;
		}
	}
}
