package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.serverpackets.S_ServerMessage;

import log.datatables.lock.LogItemDeleteReading;

/**
 * 要求刪除物品
 *
 * @author daien
 *
 */
public class C_DeleteInventoryItem extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_DeleteInventoryItem.class);

	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			this.read(decrypt);
			
			final L1PcInstance pc = client.getActiveChar();

			final int itemObjectId = this.readD();
			
			final L1ItemInstance item = pc.getInventory().getItem(itemObjectId);

			// 物品為空
			if (item == null) {
				return;
			}
			if (item.getCount() <= 0) {
				return;
			}

			// 執行人物不是GM
			if (!pc.isGm()) {
				if (item.getItem().isCantDelete()) {
					// 125 \f1你不能夠放棄此樣物品。
					pc.sendPackets(new S_ServerMessage(125));
					return;
				}
			}

			// 寵物
			final Object[] petlist = pc.getPetList().values().toArray();
			for (final Object petObject : petlist) {
				if (petObject instanceof L1PetInstance) {
					final L1PetInstance pet = (L1PetInstance) petObject;
					if (item.getId() == pet.getItemObjId()) {
						// 125 \f1你不能夠放棄此樣物品。
						pc.sendPackets(new S_ServerMessage(125));
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
				// 125 \f1你不能夠放棄此樣物品。
				pc.sendPackets(new S_ServerMessage(125));
				return;
			}
			if (item.getBless() >= 128) { // 封印的装備
				// 210 \f1%0%d是不可轉移的…
				pc.sendPackets(new S_ServerMessage(210, item.getItem().getNameId()));
				return;
			}
			
			final long count = item.getCount();
			_log.info("人物:" + pc.getName() + "刪除物品" + item.getItem().getName() + " 物品OBJID:" + item.getId());
			pc.getInventory().removeItem(item , count );
			LogItemDeleteReading.get().logArchive( pc ,  item ,   count );//刪除紀錄
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
