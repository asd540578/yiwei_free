package com.lineage.server.model.Instance;

import static com.lineage.server.model.skill.L1SkillId.GMSTATUS_SHOWTRAPS;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.L1Location;
import com.lineage.server.model.L1Object;
import com.lineage.server.model.map.L1Map;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.serverpackets.S_Trap;
import com.lineage.server.templates.L1Trap;
import com.lineage.server.types.Point;

/**
 * 陷阱控制項
 * @author daien
 *
 */
public class L1TrapInstance extends L1Object {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Log _log = LogFactory.getLog(L1TowerInstance.class);

	private final L1Trap _trap;// 陷阱資料
	private final Point _baseLoc = new Point();// 原始座標位置
	private final Point _rndPt = new Point();// 召喚範圍點資料
	private int _span;// 陷阱重新動作時間(秒)
	
	private int _stop = 0;// 陷阱停止動作時間
	private boolean _isEnable = true;// 陷阱啟動狀態

	private List<L1PcInstance> _knownPlayers = new CopyOnWriteArrayList<L1PcInstance>();

	private static final Random _random = new Random();

	/**
	 * 陷阱
	 * @param id 陷阱編號
	 * @param trap 陷阱資料
	 * @param loc 原始座標位置
	 * @param rndPt 召喚範圍點資料
	 * @param span 陷阱重新動作時間(毫秒)
	 */
	public L1TrapInstance(final int id, final L1Trap trap, final L1Location loc, final Point rndPt,
			final int span) {
		this.setId(id);
		this._trap = trap;
		this.getLocation().set(loc);
		this._baseLoc.set(loc);
		this._rndPt.set(rndPt);
		
		if (span > 0) {
			// 迎合資料庫毫秒設置 / 1000
			this._span = span / 1000;
		}
		

		this.resetLocation();
	}
	
	public L1Trap get_trap() {
		return _trap;
	}

	/**
	 * 陷阱停止動作時間
	 * @param _stop
	 */
	public void set_stop(int _stop) {
		this._stop = _stop;
	}

	/**
	 * 陷阱停止動作時間
	 * @return
	 */
	public int get_stop() {
		return _stop;
	}

	/**
	 * 重新設置陷阱啟動與位置
	 */
	public void resetLocation() {
		try {
			if ((this._rndPt.getX() == 0) && (this._rndPt.getY() == 0)) {
				return;
			}
			this.enableTrap();
			// 迴圈50次
			for (int i = 0; i < 50; i++) {
				// 取得新座標位置
				int rndX = 
					_random.nextInt(this._rndPt.getX() + 1) * (_random.nextBoolean() ? 1 : -1);
				int rndY = 
					_random.nextInt(this._rndPt.getY() + 1) * (_random.nextBoolean() ? 1 : -1);

				rndX += this._baseLoc.getX();
				rndY += this._baseLoc.getY();

				final L1Map map = this.getLocation().getMap();
				// 座標設置判斷
				if (map.isInMap(rndX, rndY) && map.isPassable(rndX, rndY, null)) {
					this.getLocation().set(rndX, rndY);
					break;
				}
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 陷阱重新動作時間
	 * @return
	 */
	public int getSpan() {
		return this._span;
	}

	/**
	 * 啟用陷阱
	 */
	public void enableTrap() {
		this.set_stop(0);
		this._isEnable = true;
	}

	/**
	 * 解除陷阱
	 */
	public void disableTrap() {
		this._isEnable = false;

		for (final L1PcInstance pc : this._knownPlayers) {
			pc.removeKnownObject(this);
			pc.sendPackets(new S_RemoveObject(this));
		}
		this._knownPlayers.clear();
	}

	/**
	 * 陷阱是否啟用
	 * @return
	 */
	public boolean isEnable() {
		return this._isEnable;
	}

	/**
	 * 踩到陷阱的處理
	 * @param trodFrom
	 */
	public void onTrod(final L1PcInstance trodFrom) {
		this._trap.onTrod(trodFrom, this);
	}

	/**
	 * 偵測陷阱的處理
	 * @param caster
	 */
	public void onDetection(final L1PcInstance caster) {
		this._trap.onDetection(caster, this);
	}

	@Override
	public void onPerceive(final L1PcInstance perceivedFrom) {
		try {
			if (perceivedFrom.hasSkillEffect(GMSTATUS_SHOWTRAPS)) {
				perceivedFrom.addKnownObject(this);
				perceivedFrom.sendPackets(new S_Trap(this, this._trap.getType()));
				this._knownPlayers.add(perceivedFrom);
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
}
