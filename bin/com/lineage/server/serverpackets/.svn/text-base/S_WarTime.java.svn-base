package com.lineage.server.serverpackets;

import java.util.Calendar;

import com.lineage.config.Config;
import com.lineage.server.datatables.lock.CastleReading;
import com.lineage.server.templates.L1Castle;

/**
 * 圍城時間設定
 * @author dexc
 *
 */
public class S_WarTime extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * 圍城時間設定
	 * @param cal
	 */
	public S_WarTime(final Calendar cal) {
		// 1997/01/01 17:00を基点としている
		final Calendar base_cal = Calendar.getInstance();
		base_cal.set(1997, 0, 1, 17, 0);
		final long base_millis = base_cal.getTimeInMillis();
		final long millis = cal.getTimeInMillis();
		long diff = millis - base_millis;
		diff -= 1200 * 60 * 1000; // 誤差修正
		diff = diff / 60000; // 分以下切捨て
		// timeは1加算すると3:02（182分）進む
		final int time = (int) (diff / 182);

		// writeDの直前のwriteCで時間の調節ができる
		// 0.7倍した時間だけ縮まるが
		// 1つ調整するとその次の時間が広がる？
		this.writeC(S_OPCODE_WARTIME);
		this.writeH(0x0006); // リストの数（6以上は無効）
		this.writeS(Config.TIME_ZONE); // 時間の後ろの（）内に表示される文字列
		this.writeC(0x00); // ?
		this.writeC(0x00); // ?
		this.writeC(0x00);
		this.writeD(time);
		this.writeC(0x00);
		this.writeD(time - 1);
		this.writeC(0x00);
		this.writeD(time - 2);
		this.writeC(0x00);
		this.writeD(time - 3);
		this.writeC(0x00);
		this.writeD(time - 4);
		this.writeC(0x00);
		this.writeD(time - 5);
		this.writeC(0x00);
	}

	/**
	 * 圍城時間設定 - 測試
	 * @param cal
	 */
	public S_WarTime(int op) {
		final L1Castle l1castle = CastleReading.get().getCastleTable(5);// 5 海音城
		final Calendar cal = l1castle.getWarTime();
		// 1997/01/01 17:00を基点としている
		final Calendar base_cal = Calendar.getInstance();
		base_cal.set(1997, 0, 1, 17, 0);
		final long base_millis = base_cal.getTimeInMillis();
		final long millis = cal.getTimeInMillis();
		long diff = millis - base_millis;
		diff -= 1200 * 60 * 1000; // 誤差修正
		diff = diff / 60000; // 分以下切捨て
		// timeは1加算すると3:02（182分）進む
		final int time = (int) (diff / 182);

		this.writeC(op);
		this.writeH(6); // リストの数（6以上は無効）
		this.writeS(Config.TIME_ZONE); // 時間の後ろの（）内に表示される文字列
		this.writeC(0); // ?
		this.writeC(0); // ?
		this.writeC(0);
		this.writeD(time);
		this.writeC(0);
		this.writeD(time - 1);
		this.writeC(0);
		this.writeD(time - 2);
		this.writeC(0);
		this.writeD(time - 3);
		this.writeC(0);
		this.writeD(time - 4);
		this.writeC(0);
		this.writeD(time - 5);
		this.writeC(0);
		
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
