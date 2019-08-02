package com.lineage.server.serverpackets;

import static com.lineage.server.model.skill.L1SkillId.STATUS_BRAVE3;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 物件封包 - 其他人物
 * @author dexc
 *
 */
public class S_OtherCharPacks extends ServerBasePacket {

	private static final int STATUS_POISON = 1;
	private static final int STATUS_INVISIBLE = 2;
	private static final int STATUS_PC = 4;
	//private static final int STATUS_FREEZE = 8;
	private static final int STATUS_BRAVE = 16;
	private static final int STATUS_ELFBRAVE = 32;
	private static final int STATUS_FASTMOVABLE = 64;
	//private static final int STATUS_GHOST = 128;

	private byte[] _byte = null;

	/**
	 * 物件封包 - 其他人物
	 * @param pc
	 */
	public S_OtherCharPacks(final L1PcInstance pc) {
		int status = STATUS_PC;
		if (pc.getPoison() != null) { // 毒状態
			if (pc.getPoison().getEffectId() == 1) {
				status |= STATUS_POISON;
			}
		}
		if (pc.isInvisble()) {
			status |= STATUS_INVISIBLE;
		}
		if (pc.isBrave()) {
			status |= STATUS_BRAVE;
		}
		if (pc.isElfBrave()) {
			// エルヴンワッフルの場合は、STATUS_BRAVEとSTATUS_ELFBRAVEを立てる。
			// STATUS_ELFBRAVEのみでは効果が無い？
			status |= STATUS_BRAVE;
			status |= STATUS_ELFBRAVE;
		}
		if (pc.isFastMovable()) {
			status |= STATUS_FASTMOVABLE;
		}

		this.writeC(S_OPCODE_CHARPACK);
		this.writeH(pc.getX());
		this.writeH(pc.getY());
		this.writeD(pc.getId());

		if (pc.isDead()) {
			this.writeH(pc.getTempCharGfxAtDead());

		} else {
			this.writeH(pc.getTempCharGfx());
		}

		if (pc.isDead()) {
			this.writeC(pc.getStatus());
		} else {
			//TODO 3.81 商店斷線修正
			if(pc.isPrivateShop()){ //商店中 不顯示 武器
				this.writeC(0x00);
			} else {
				this.writeC(pc.getCurrentWeapon());
			}
		}

		this.writeC(pc.getHeading());
		// writeC(0); // makes char invis (0x01), cannot move. spells display
		this.writeC(pc.getChaLightSize());
		this.writeC(pc.getMoveSpeed());
		this.writeD(0x00000000); // exp
		this.writeH(pc.getLawful());

		final StringBuilder stringBuilder = new StringBuilder();
		if (pc.get_other().get_color() != 0) {
			stringBuilder.append(pc.get_other().color());
		}
		stringBuilder.append(pc.getName());	
		//stringBuilder.append(title);

		this.writeS(stringBuilder.toString());
		this.writeS(pc.getTitle());
		this.writeC(status); // 狀態
		this.writeD(pc.getClanid());
		this.writeS(pc.getClanname()); // 血盟名稱
		this.writeS(null); // 主人名稱
		
		this.writeC(0);// ?

		this.writeC(0xff); // HP顯示
		if (pc.hasSkillEffect(STATUS_BRAVE3)) {
			this.writeC(0x08); // 巧克力蛋糕
		} else {
			this.writeC(0x00); // タルクック距離(通り)Q
		}
		writeC(0); // PC = 0, Mon = Lv
		
		if(pc.isPrivateShop()){
			
		} else {
			writeS(null);
		}
		writeC(0xFF);
		writeC(0xFF);
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