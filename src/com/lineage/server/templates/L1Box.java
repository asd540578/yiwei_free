package com.lineage.server.templates;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 箱子開出物設置資料
 * @author dexc
 *
 */
public class L1Box {

	private static final Log _log = LogFactory.getLog(L1Box.class);

	private int _box_item_id;
	
	private int _get_item_id;

	private int _randomint;// 比對用機率
	
	private int _random;// 機率
	
	private int _min_count;
	
	private int _max_count;
	
	private int _use_type = 127;
	
	private boolean _out = false;

	public int get_box_item_id() {
		return _box_item_id;
	}

	public void set_box_item_id(int box_item_id) {
		_box_item_id = box_item_id;
	}

	public int get_item_id() {
		return _get_item_id;
	}

	public void set_get_item_id(int get_item_id) {
		_get_item_id = get_item_id;
	}

	/**
	 * 比對用機率
	 * @return
	 */
	public int get_randomint() {
		return _randomint;
	}

	/**
	 * 比對用機率
	 * @param randomint
	 */
	public void set_randomint(int randomint) {
		_randomint = randomint;
	}

	/**
	 * 機率
	 * @return
	 */
	public int get_random() {
		return _random;
	}

	/**
	 * 機率
	 * @param random
	 */
	public void set_random(int random) {
		_random = random;
	}

	public int get_min_count() {
		return _min_count;
	}

	public void set_min_count(int min_count) {
		_min_count = min_count;
	}

	public int get_max_count() {
		return _max_count;
	}

	public void set_max_count(int max_count) {
		_max_count = max_count;
	}

	public boolean is_out() {
		return _out;
	}

	public void set_out(boolean out) {
		_out = out;
	}

	public int get_use_type() {
		return _use_type;
	}

	public void set_use_type(int use_type) {
		this._use_type = use_type;
		if (use_type >= _int7) {
			use_type -= _int7;
			_isIllusionist = true;
		}
		if (use_type >= _int6) {
			use_type -= _int6;
			_isDragonKnight = true;
		}
		if (use_type >= _int5) {
			use_type -= _int5;
			_isDarkelf = true;
		}
		if (use_type >= _int4) {
			use_type -= _int4;
			_isWizard = true;
		}
		if (use_type >= _int3) {
			use_type -= _int3;
			_isElf = true;
		}
		if (use_type >= _int2) {
			use_type -= _int2;
			_isKnight = true;
		}
		if (use_type >= _int1) {
			use_type -= _int1;
			_isCrown = true;
		}

		if (use_type > 0) {
			_log.error("寶箱可用職業設定錯誤:餘數大於0 編號:" + _box_item_id + "/" + _get_item_id);
		}
	}

	private static final int _int7 = 64;// 幻術師可用:64
	private static final int _int6 = 32;// 龍騎士可用:32
	private static final int _int5 = 16;// 黑暗精靈可用:16
	private static final int _int4 = 8;// 法師可用:8
	private static final int _int3 = 4;// 精靈可用:4
	private static final int _int2 = 2;// 騎士可用:2
	private static final int _int1 = 1;// 王族可用:1

	private boolean _isCrown;// 王族可用:1

	private boolean _isKnight;// 騎士可用:2

	private boolean _isElf;// 精靈可用:4

	private boolean _isWizard;// 法師可用:8

	private boolean _isDarkelf;// 黑暗精靈可用:16

	private boolean _isDragonKnight;// 龍騎士可用:32

	private boolean _isIllusionist;// 幻術師可用:64

	public boolean is_use(L1PcInstance pc) {
		try {
			if (pc.isCrown() && _isCrown) {
				return true;
			}
			if (pc.isKnight() && _isKnight) {
				return true;
			}
			if (pc.isElf() && _isElf) {
				return true;
			}
			if (pc.isWizard() && _isWizard) {
				return true;
			}
			if (pc.isDarkelf() && _isDarkelf) {
				return true;
			}
			if (pc.isDragonKnight() && _isDragonKnight) {
				return true;
			}
			if (pc.isIllusionist() && _isIllusionist) {
				return true;
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		return false;
	}
}