package com.lineage.server.serverpackets;

import com.lineage.server.serverpackets.S_ServerMessage;

import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 物品名單(個人倉庫)
 * @author dexc
 *
 */
public class S_RetrieveList extends ServerBasePacket {

	private byte[] _byte = null;
	
	/**
	 * 物品名單(個人倉庫)
	 * @param objid
	 * @param pc
	 */
	public S_RetrieveList(final int objid, final L1PcInstance pc) {
		if (pc.getInventory().getSize() < 180) {
			final int size = pc.getDwarfInventory().getSize();
			if (size > 0) {
				this.writeC(S_OPCODE_SHOWRETRIEVELIST);
				this.writeD(objid);
				this.writeH(size);
				this.writeC(3); // 個人倉庫
				for (final Object itemObject : pc.getDwarfInventory().getItems()) {
					final L1ItemInstance item = (L1ItemInstance) itemObject;
					this.writeD(item.getId());
					int i = item.getItem().getUseType();
					if (i < 0) {
						i = 0;
					}
					this.writeC(i);// this.writeC(0x00);
					this.writeH(item.get_gfxid());
					this.writeC(item.getBless());
					this.writeD((int) Math.min(item.getCount(), 2000000000));
					this.writeC(item.isIdentified() ? 0x01 : 0x00);
					this.writeS(item.getViewName());
					
					
				}
				//TODO 修正3.53C血盟倉領取金額顯示異常
				writeD(30);
                writeD(0x00000000);
                writeH(0x00);
                //TODO 修正3.53C血盟倉領取金額顯示異常
			} else {
				pc.sendPackets(new S_ServerMessage(1625));
				//TODO 修正個人倉庫領出封包by99團隊
			}

		} else {
			pc.sendPackets(new S_ServerMessage(263)); // 263 \f1一個角色最多可攜帶180個道具。
		}
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
