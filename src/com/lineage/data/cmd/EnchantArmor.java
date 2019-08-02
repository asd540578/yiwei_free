package com.lineage.data.cmd;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1ItemPower;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_HelpMessage;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;

import log.datatables.lock.LogItemEnchantReading;
import log.datatables.lock.LogWorldEnchanOkReading;

/**
 * 
 * @author daien
 *
 */
public class EnchantArmor extends EnchantExecutor {

	private static final Log _log = LogFactory.getLog(EnchantArmor.class);

	/**
	 * 強化失敗
	 * @param pc 執行者
	 * @param item 對象物件
	 */
	@Override
	public void failureEnchant(final L1PcInstance pc, final L1ItemInstance item) {
		final StringBuilder s = new StringBuilder();
		
		// 未鑑定
		if (!item.isIdentified()) {
			s.append(item.getName());
			
		} else {
			s.append(item.getLogName());
		}
		// 164 \f1%0%s 产生激烈的 %1 光芒，一会儿后就消失了。銀色的
		pc.sendPackets(new S_ServerMessage(164, s.toString(), "$252"));
		pc.getInventory().removeItem(item, item.getCount());
		_log.info("人物:" + pc.getName() + "點爆物品" + item.getItem().getName() + " 物品OBJID:" + item.getId());
		LogItemEnchantReading.get().logArchive(pc, item, 1); // 暴裝紀錄
	}

	/**
	 * 強化成功
	 * @param pc 執行者
	 * @param item 對象物件
	 * @param i 強化質
	 */
	@Override
	public void successEnchant(final L1PcInstance pc, final L1ItemInstance item, final int i) {
		final StringBuilder s = new StringBuilder();
		final StringBuilder sa = new StringBuilder();
		final StringBuilder sb = new StringBuilder();
		
		// 未鑑定
		if (!item.isIdentified()) {
			s.append(item.getName());
			
		} else {
			if (item.getEnchantLevel() > 0) {
				s.append("+" + item.getEnchantLevel() + " " + item.getName());
				
			} else if (item.getEnchantLevel() < 0) {
				s.append(item.getEnchantLevel() + " " + item.getName());
				
			} else {
				s.append(item.getName());
			}
		}

		switch (i) {
		case 0:
			// \f1%0%s %2 产生激烈的 %1 光芒，但是没有任何事情发生。
			pc.sendPackets(new S_ServerMessage(160, s.toString(), "$252", "$248"));
			return;
			
		case -1:
			sa.append("$246");// 黑色的
			sb.append("$247");// 一瞬間發出
			break;
			
		case 1: // '\001'
			sa.append("$252");// 銀色的
			sb.append("$247");// 一瞬間發出
			break;

		case 2: // '\002'
		case 3: // '\003'
			sa.append("$252");// 銀色的
			sb.append("$248");// 持續發出
			break;
		}
		
		// 161 \f1%0%s %2 %1 光芒。
		pc.sendPackets(new S_ServerMessage(161, s.toString(), sa.toString(), sb.toString()));

		final int oldEnchantLvl = item.getEnchantLevel();// 原追加值
		final int newEnchantLvl = oldEnchantLvl + i;// 新追加值

		

		
		if (oldEnchantLvl != newEnchantLvl) {
			
			final int safeenchant = item.getItem().get_safeenchant();
			
			item.setEnchantLevel(newEnchantLvl);
			pc.getInventory().updateItem(item, L1PcInventory.COL_ENCHANTLVL);
			pc.getInventory().saveItem(item, L1PcInventory.COL_ENCHANTLVL);
			if (newEnchantLvl > safeenchant) {// 強化值等於或超過9
				// 1,652：強化  
				// 產生訊息封包 (強化成功)
				World.get().broadcastPacketToAll(
						new S_HelpMessage(pc.getName(), 
								s.toString() + " " +sb.toString() + " " + sa.toString() + " $251"));
				
				LogWorldEnchanOkReading.get().logArchive(pc, item, oldEnchantLvl, newEnchantLvl);// log 成功紀錄;
			}
			
			if (item.isEquipped()) {
				// 取得物件觸發事件
				final int use_type = item.getItem().getUseType();
				switch (use_type) {
				case 2:// 盔甲
				case 22:// 頭盔
				case 19:// 斗篷
				case 18:// T恤
				case 20:// 手套
				case 21:// 靴
				case 25:// 盾牌
					pc.addAc(-i);
					final int mr = item.getMr();
					if (mr != 0) {
						final Integer integer = L1ItemPower.MR2.get(item.getItemId());
						if (integer != null) {
							pc.addMr(i * integer);
							pc.sendPackets(new S_SPMR(pc));
						}
					}
					break;

				case 23:// 戒指
				case 24:// 項鍊
				case 37:// 腰帶
				case 40:// 耳環
					if (item.getItem().get_greater() != 3) {
						item.greater(pc, true);
					}
					break;
					
				default:
					break;
				}
				pc.sendPackets(new S_OwnCharStatus(pc));
			}
		}
	}
}
