package com.lineage.server.serverpackets;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.gametime.L1GameTimeClock;

/**
 * 角色資訊
 * @author dexc
 *
 */
public class S_OwnCharStatus extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * (下方對話框)角色資訊
	 * @param pc
	 */
	public S_OwnCharStatus(final L1PcInstance pc) {
		int time = L1GameTimeClock.getInstance().currentTime().getSeconds();
		time = time - (time % 300);
		// _log.warning((new
		// StringBuilder()).append("送信時間:").append(i).toString());
		this.writeC(S_OPCODE_OWNCHARSTATUS);
		this.writeD(pc.getId());

		this.writeC(pc.getLevel());
		
		this.writeExp(pc.getExp());

		this.writeC(pc.getStr());
		this.writeC(pc.getInt());
		this.writeC(pc.getWis());
		this.writeC(pc.getDex());
		this.writeC(pc.getCon());
		this.writeC(pc.getCha());
		this.writeH(pc.getCurrentHp());
		this.writeH(pc.getMaxHp());
		this.writeH(pc.getCurrentMp());
		this.writeH(pc.getMaxMp());
		this.writeC(pc.getAc());
		this.writeD(time);
		this.writeC(pc.get_food());
		this.writeC(pc.getInventory().getWeight240());
		this.writeH(pc.getLawful());
		this.writeH(pc.getFire());
		this.writeH(pc.getWater());
		this.writeH(pc.getWind());
		this.writeH(pc.getEarth());
		this.writeD(pc.getMonsterKill()); // TODO 3.53C モンスター討伐数
	}
	
	/**
     * 角色資訊(重登專用，等級顯示127)
     * @param pc
     */
    public S_OwnCharStatus(final L1PcInstance pc , final boolean check) {
            int time = L1GameTimeClock.getInstance().currentTime().getSeconds();
            time = time - (time % 300);
            this.writeC(S_OPCODE_OWNCHARSTATUS);
            this.writeD(pc.getId());
            this.writeC(127);// 強制設定等級為 127
            this.writeExp(pc.getExp());
            this.writeC(pc.getStr());
            this.writeC(pc.getInt());
            this.writeC(pc.getWis());
            this.writeC(pc.getDex());
            this.writeC(pc.getCon());
            this.writeC(pc.getCha());
            this.writeH(pc.getCurrentHp());
            this.writeH(pc.getMaxHp());
            this.writeH(pc.getCurrentMp());
            this.writeH(pc.getMaxMp());
            this.writeC(pc.getAc());
            this.writeD(time);
            this.writeC(pc.get_food());
            this.writeC(pc.getInventory().getWeight240());
            this.writeH(pc.getLawful());
    		this.writeH(pc.getFire());
    		this.writeH(pc.getWater());
    		this.writeH(pc.getWind());
    		this.writeH(pc.getEarth());
    }
	/**
	 * 角色資訊 測試用
	 * @param pc 測試GM
	 * @param str 力量
	 */
	public S_OwnCharStatus(final L1PcInstance pc, int str) {
		int time = L1GameTimeClock.getInstance().currentTime().getSeconds();
		time = time - (time % 300);

		this.writeC(S_OPCODE_OWNCHARSTATUS);
		this.writeD(pc.getId());

		this.writeC(pc.getLevel());
		
		this.writeExp(pc.getExp());

		this.writeC(str);
		this.writeC(pc.getInt());
		this.writeC(pc.getWis());
		this.writeC(pc.getDex());
		this.writeC(pc.getCon());
		this.writeC(pc.getCha());
		this.writeH(pc.getCurrentHp());
		this.writeH(pc.getMaxHp());
		this.writeH(pc.getCurrentMp());
		this.writeH(pc.getMaxMp());
		this.writeC(pc.getAc());
		this.writeD(time);
		this.writeC(pc.get_food());
		this.writeC(pc.getInventory().getWeight240());
		this.writeH(pc.getLawful());
		this.writeC(pc.getFire());
		this.writeC(pc.getWater());
		this.writeC(pc.getWind());
		this.writeC(pc.getEarth());
		this.writeD(pc.getMonsterKill()); // TODO 3.53C モンスター討伐数
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