package com.lineage.server.timecontroller.pet;

import java.util.Collection;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.world.World;

/**
 * 魔法娃娃處理時間軸(娃娃效果:HP恢復(指定時間))
 * @author dexc
 *
 */
public class DollHprTimer extends TimerTask {

	private static final Log _log = LogFactory.getLog(DollHprTimer.class);

	private ScheduledFuture<?> _timer;

	public void start() {
		final int timeMillis = 1000;// 1秒
		_timer = GeneralThreadPool.get().scheduleAtFixedRate(this, timeMillis, timeMillis);
	}

	@Override
	public void run() {
		try {
			final Collection<L1PcInstance> all = World.get().getAllPlayers();
			// 不包含元素
			if (all.isEmpty()) {
				return;
			}
			
			for (final Iterator<L1PcInstance> iter = all.iterator(); iter.hasNext();) {
				final L1PcInstance tgpc = iter.next();
				if (tgpc.get_doll_hpr_time_src() <= 0) {
					continue;
				}
				if (!checkErr(tgpc)) {
					continue;
				}
				final int newHp = tgpc.getCurrentHp() + tgpc.get_doll_hpr();
				tgpc.setCurrentHp(newHp);
				Thread.sleep(50);
			}

		} catch (final Exception e) {
			_log.error("魔法娃娃處理時間軸(HPR)異常重啟", e);
			GeneralThreadPool.get().cancel(_timer, false);
			final DollHprTimer dollTimer = new DollHprTimer();
			dollTimer.start();
		}
	}

	/**
	 * 該PC是否執行恢復
	 * @param tgpc
	 * @return true:正常 false:異常
	 */
	private static boolean checkErr(final L1PcInstance tgpc) {
		try {
			if (tgpc == null) {
				return false;
			}
			if (tgpc.getOnlineStatus() == 0) {
				return false;
			}
			if (tgpc.getNetConnection() == null) {
				return false;
			}
			if (tgpc.isTeleport()) {
				return false;
			}
			if (!tgpc.getHpRegeneration()) {
				return false;
			}
			// HP已滿
			if (tgpc.getCurrentHp() >= tgpc.getMaxHp()) {
				return false;
			}
			
			final int newtime = tgpc.get_doll_hpr_time() - 1;
			tgpc.set_doll_hpr_time(newtime);
			if (newtime <= 0) {
				tgpc.set_doll_hpr_time(tgpc.get_doll_hpr_time_src());
				return true;
			}

		} catch (final Exception e) {
			return false;
		}
		return false;
	}
}
