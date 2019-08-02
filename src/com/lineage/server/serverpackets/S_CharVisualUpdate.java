package com.lineage.server.serverpackets;

import com.lineage.server.model.L1Character;
import com.lineage.server.model.Instance.L1PcInstance;


/**
 * 物件動作種類(長時間)
 * @author dexc
 *
 */
public class S_CharVisualUpdate extends ServerBasePacket {

	private byte[] _byte = null;
	
	/**
	 * 物件動作種類(長時間)
	 * @param objid 物件OBJID
	 * @param weaponType 武器型態代號(TYPE)
	 */
	public S_CharVisualUpdate(final int objid, final int weaponType) {
		this.writeC(S_OPCODE_CHARVISUALUPDATE);
		this.writeD(objid);
		this.writeC(weaponType);
		this.writeC(0xff);
		this.writeC(0xff);
	}
	
	/**
	 * 物件動作種類(長時間)
	 * @param cha 使用者
	 * @param status 動作
	 */
	public S_CharVisualUpdate(final L1Character cha,final int status) {
		this.writeC(S_OPCODE_CHARVISUALUPDATE);
		this.writeD(cha.getId());
		this.writeC(status);
		this.writeC(0xff);
		this.writeC(0xff);
	}

	/**
	 * 物件動作種類(長時間)
	 * @param cha
	 */
	public S_CharVisualUpdate(final L1PcInstance cha) {
		this.writeC(S_OPCODE_CHARVISUALUPDATE);
		this.writeD(cha.getId());
		this.writeC(cha.getCurrentWeapon());
		this.writeC(0xff);
		this.writeC(0xff);
	}

	@Override
	public byte[] getContent() {
		if (this._byte == null) {
			this._byte = this.getBytes();
		}
		return this._byte;
	}
}