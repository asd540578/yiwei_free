package com.lineage.server.model.doll;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 娃娃能力:睡眠耐性增加
 * 睡眠耐性提高 參數：TYPE1(增加質)
 * @author daien
 *
 */
public class Doll_Regist_Sleep extends L1DollExecutor {

	private static final Log _log = LogFactory.getLog(Doll_Regist_Sleep.class);
	
	private int _int1;// 值1

	/**
	 * 娃娃能力:睡眠耐性增加
	 */
	public Doll_Regist_Sleep() {
	}

	public static L1DollExecutor get() {
		return new Doll_Regist_Sleep();
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
			pc.addRegistSleep(_int1);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void removeDoll(L1PcInstance pc) {
		try {
			pc.addRegistSleep(-_int1);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public boolean is_reset() {
		return false;
	}
}
