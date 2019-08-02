package com.lineage.server.serverpackets;

import java.util.List;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PetInstance;

/**
 * 物品名單(寵物背包)
 * @author dexc
 *
 */
public class S_PetInventory extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 物品名單(寵物背包)
	 * @param pet
	 */
	/*public S_PetInventory(final L1PetInstance pet) {
		final List<L1ItemInstance> itemList = pet.getInventory().getItems();

		this.writeC(S_OPCODE_SHOWRETRIEVELIST);
		this.writeD(pet.getId());
		this.writeH(itemList.size());
		this.writeC(0x0b);
		for (final L1ItemInstance item : itemList) {
			if (item != null) {
				this.writeD(item.getId());
				this.writeC(0x13);
				this.writeH(item.get_gfxid());
				this.writeC(item.getBless());
				this.writeD((int) Math.min(item.getCount(), 2000000000));
				this.writeC(item.isIdentified() ? 1 : 0);
				this.writeS(item.getViewName());
			}
		}
		this.writeC(0x0a);
	}*/

	/**
	 * 物品名單(寵物背包)
	 * @param pet
	 * @param b 寵物是否剛進入
	 */
	public S_PetInventory(final L1PetInstance pet, boolean b) {
		isTrue(pet);
	}
	
	private void isTrue(final L1PetInstance pet) {
		final List<L1ItemInstance> itemList = pet.getInventory().getItems();

		this.writeC(S_OPCODE_SHOWRETRIEVELIST);
		this.writeD(pet.getId());
		this.writeH(itemList.size());
		this.writeC(0x0b);
		for (final L1ItemInstance item : itemList) {
			if (item != null) {
				this.writeD(item.getId());
				this.writeC(0x02);
				this.writeH(item.get_gfxid());
				this.writeC(item.getBless());
				this.writeD((int) Math.min(item.getCount(), 2000000000));
				//this.writeC(item.isIdentified() ? 1 : 0);
				// 顯示裝備中的寵物裝備
				if (item.getItem().getType2() == 0 
						&& item.getItem().getType() == 11
						&& item.isEquipped()) {
					this.writeC(item.isIdentified() ? 3 : 2);
				} else {
					this.writeC(item.isIdentified() ? 1 : 0);
				}
				this.writeS(item.getViewName());
			}
		}
		this.writeC(pet.getAc()); // 寵物防禦
	}

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
