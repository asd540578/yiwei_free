package com.lineage.server.model.doll;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 娃娃能力 模組:MP回復相關(指定時間)<BR>
 * MP回復提高 參數：TYPE1(提高質) TYPE2(時間:秒)
 * @author dexc
 *
 */
public class Doll_MpTR extends L1DollExecutor {

	private static final Log _log = LogFactory.getLog(Doll_MpTR.class);
	
	private int _int1;// 值1
	
	private int _int2;// 值2

	/**
	 * 娃娃效果:MP增加(指定時間)
	 */
	public Doll_MpTR() {
	}

	public static L1DollExecutor get() {
		return new Doll_MpTR();
	}

	@Override
	public void set_power(int int1, int int2, int int3) {
		try {
			_int1 = int1;
			_int2 = int2;
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void setDoll(L1PcInstance pc) {
		try {
			pc.set_doll_mpr(_int1);
			pc.set_doll_mpr_time_src(_int2);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void removeDoll(L1PcInstance pc) {
		try {
			pc.set_doll_mpr(0);
			pc.set_doll_mpr_time_src(0);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public boolean is_reset() {
		return false;
	}
}
