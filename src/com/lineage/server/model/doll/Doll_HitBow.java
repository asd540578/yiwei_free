package com.lineage.server.model.doll;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 娃娃能力 模組:遠距離命中相關<BR>
 * 遠距離命中提高 參數：TYPE1(遠距離命中+)
 * @author dexc
 *
 */
public class Doll_HitBow extends L1DollExecutor {

	private static final Log _log = LogFactory.getLog(Doll_HitBow.class);
	
	private int _int1;// 值1

	/**
	 * 娃娃效果:遠距離命中增加
	 */
	public Doll_HitBow() {
	}

	public static L1DollExecutor get() {
		return new Doll_HitBow();
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
			pc.addBowHitup(_int1);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void removeDoll(L1PcInstance pc) {
		try {
			pc.addBowHitup(-_int1);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public boolean is_reset() {
		return false;
	}
}
