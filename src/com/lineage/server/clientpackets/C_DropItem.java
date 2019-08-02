package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.config.ConfigAlt;
import com.lineage.echo.ClientExecutor;
import com.lineage.server.datatables.ItemRestrictionsTable;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.world.World;

import log.datatables.lock.LogWorldDiscardReading;

/**
 * 要求丟棄物品
 *
 * @author daien
 *
 */
public class C_DropItem extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_DropItem.class);

	/*public C_DropItem() {
	}

	public C_DropItem(final byte[] abyte0, final ClientExecutor client) {
		super(abyte0);
		try {
			this.start(abyte0, client);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}*/

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			this.read(decrypt);

			final int x = this.readH();
			final int y = this.readH();
			final int objectId = this.readD();
			int count = this.readD();
			if (count > Integer.MAX_VALUE) {
				count = Integer.MAX_VALUE;
			}
			count = Math.max(0, count);
			final L1PcInstance pc = client.getActiveChar();
			if (pc.isGhost()) {
				return;
			}

			// 執行人物不是GM
			if(!ConfigAlt.DORP_ITEM && !pc.isGm()) {
				// \f1你不能夠放棄此樣物品。
				pc.sendPackets(new S_ServerMessage(125));
				return;
			}

			final L1ItemInstance item = pc.getInventory().getItem(objectId);

			// 物品為空
			if (item == null) {
				return;
			}
			
			if (item.getCount() <= 0) {
				return;
			}
			
			// 執行人物不是GM
			if (!pc.isGm()) {
				if (!item.getItem().isTradable()) {
					// \f1%0%d是不可轉移的…
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
					return;
				}

				if (item.get_time() != null) {
					// \f1%0%d是不可轉移的…
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
					return;
				}
				if (ItemRestrictionsTable.RESTRICTIONS.contains(item.getItemId())) {
					// \f1%0%d是不可轉移的…
					pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
					return;
				}
			}

			// 寵物
			final Object[] petlist = pc.getPetList().values().toArray();
			for (final Object petObject : petlist) {
				if (petObject instanceof L1PetInstance) {
					final L1PetInstance pet = (L1PetInstance) petObject;
					if (item.getId() == pet.getItemObjId()) {
						// \f1%0%d是不可轉移的…
						pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
						return;
					}
				}
			}

			// 取回娃娃
			if (pc.getDoll(item.getId()) != null) {
				// 1,181：這個魔法娃娃目前正在使用中。  
				pc.sendPackets(new S_ServerMessage(1181));
				return;
			}

			if (item.isEquipped()) {
				// \f1你不能夠放棄此樣物品。
				pc.sendPackets(new S_ServerMessage(125));
				return;
			}
			if (item.getBless() >= 128) { // 封印装備
				// \f1%0%d是不可轉移的…
				pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
				return;
			}
			
			LogWorldDiscardReading.get().logArchive(pc , item , count);
			pc.getInventory().tradeItem(item, count, pc.get_showId(),
					World.get().getInventory(x, y, pc.getMapId()));
			/*L1ItemInstance newItem = pc.getInventory().tradeItem(item, count, 
					World.get().getInventory(x, y, pc.getMapId()));
			newItem.set_showId(pc.get_showId());*/
			
			pc.turnOnOffLight();
			
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
