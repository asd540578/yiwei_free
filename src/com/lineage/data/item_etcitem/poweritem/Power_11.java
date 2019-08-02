package com.lineage.data.item_etcitem.poweritem;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.data.event.PowerItemSet;
import com.lineage.data.executor.ItemExecutor;
import com.lineage.server.datatables.lock.CharItemPowerReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ItemName;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1ItemPower_name;

/**
 * 56067 抗魔(抗魔+3)
 *
 * @author dexc
 *
 */
public class Power_11 extends ItemExecutor {

	private static final Log _log = LogFactory.getLog(Power_11.class);

	private static  final Random _random = new Random();

	/**
	 *
	 */
	private Power_11() {
		// TODO Auto-generated constructor stub
	}

	public static ItemExecutor get() {
		return new Power_11();
	}

	/**
	 * 道具物件執行
	 * @param data 參數
	 * @param pc 執行者
	 * @param item 物件
	 */
	@Override
	public void execute(final int[] data, final L1PcInstance pc, final L1ItemInstance item) {
		try {
			// 對象OBJID
			final int targObjId = data[0];

			// 目標物品
			final L1ItemInstance tgItem = pc.getInventory().getItem(targObjId);

			if (tgItem == null) {
				return;
			}
			if (tgItem.get_power_name() == null) {
				pc.sendPackets(new S_ServerMessage("\\fT這個物品沒有凹槽!"));
				return;
			}
			if (tgItem.getItem().getType2() != 2) {// 修正 YiWei thatmystyle (UID: 3602)
				pc.sendPackets(new S_ServerMessage("\\fT這只能使用在防具上"));
				return;
			}
			if (tgItem.isEquipped()) {
				pc.sendPackets(new S_ServerMessage("\\fR你必須先解除物品裝備!"));
				return;
			}
			int random = PowerItemSet.HOLER;
			if (pc.isGm()) {
				random = 1000;
			}
			final L1ItemPower_name power = tgItem.get_power_name();
			if (power.get_hole_1() == 0 && power.get_hole_count() >= 1) {
				pc.getInventory().removeItem(item, 1);
				if (_random.nextInt(1000) > random) {
					pc.sendPackets(new S_ServerMessage("\\fT凹槽置入魔法物品失敗!"));
					return;
				}
				power.set_hole_1(11);
				pc.sendPackets(new S_ItemStatus(tgItem));
				pc.sendPackets(new S_ItemName(tgItem));
				CharItemPowerReading.get().updateItem(tgItem.getId(), tgItem.get_power_name());
				
			} else if (power.get_hole_2() == 0 && power.get_hole_count() >= 2) {
				pc.getInventory().removeItem(item, 1);
				if (_random.nextInt(1000) > random) {
					pc.sendPackets(new S_ServerMessage("\\fT凹槽置入魔法物品失敗!"));
					return;
				}
				power.set_hole_2(11);
				pc.sendPackets(new S_ItemStatus(tgItem));
				pc.sendPackets(new S_ItemName(tgItem));
				CharItemPowerReading.get().updateItem(tgItem.getId(), tgItem.get_power_name());
				
			} else if (power.get_hole_3() == 0 && power.get_hole_count() >= 3) {
				pc.getInventory().removeItem(item, 1);
				if (_random.nextInt(1000) > random) {
					pc.sendPackets(new S_ServerMessage("\\fT凹槽置入魔法物品失敗!"));
					return;
				}
				power.set_hole_3(11);
				pc.sendPackets(new S_ItemStatus(tgItem));
				pc.sendPackets(new S_ItemName(tgItem));
				CharItemPowerReading.get().updateItem(tgItem.getId(), tgItem.get_power_name());
				
			} else if (power.get_hole_4() == 0 && power.get_hole_count() >= 4) {
				pc.getInventory().removeItem(item, 1);
				if (_random.nextInt(1000) > random) {
					pc.sendPackets(new S_ServerMessage("\\fT凹槽置入魔法物品失敗!"));
					return;
				}
				power.set_hole_4(11);
				pc.sendPackets(new S_ItemStatus(tgItem));
				pc.sendPackets(new S_ItemName(tgItem));
				CharItemPowerReading.get().updateItem(tgItem.getId(), tgItem.get_power_name());
				
			} else if (power.get_hole_5() == 0 && power.get_hole_count() >= 5) {
				pc.getInventory().removeItem(item, 1);
				if (_random.nextInt(1000) > random) {
					pc.sendPackets(new S_ServerMessage("\\fT凹槽置入魔法物品失敗!"));
					return;
				}
				power.set_hole_5(11);
				pc.sendPackets(new S_ItemStatus(tgItem));
				pc.sendPackets(new S_ItemName(tgItem));
				CharItemPowerReading.get().updateItem(tgItem.getId(), tgItem.get_power_name());
				
			} else {
				pc.sendPackets(new S_ServerMessage("\\fT這個物品沒有足夠凹槽!"));
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
}
