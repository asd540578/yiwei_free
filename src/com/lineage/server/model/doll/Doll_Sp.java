package com.lineage.server.model.doll;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_SPMR;

/**
 * 娃娃能力 模組:魔法攻擊<BR>
 * 魔法攻擊提高 參數：TYPE1(增加質)
 * @author dexc
 *
 */
public class Doll_Sp extends L1DollExecutor {

	private static final Log _log = LogFactory.getLog(Doll_Sp.class);
	
	private int _int1;// 值1

	/**
	 * 娃娃效果:魔法攻擊
	 */
	public Doll_Sp() {
	}

	public static L1DollExecutor get() {
		return new Doll_Sp();
	}

	@Override
	public void set_power(int int1, int int2, int int3) {
		try {
			_int1 = int1;
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void setDoll(L1PcInstance pc) {
		try {
			pc.addSp(_int1);
			pc.sendPackets(new S_SPMR(pc));
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void removeDoll(L1PcInstance pc) {
		try {
			pc.addSp(-_int1);
			pc.sendPackets(new S_SPMR(pc));
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public boolean is_reset() {
		return false;
	}
}
