package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_ChangeName;
import com.lineage.server.serverpackets.S_Exp;
import com.lineage.server.serverpackets.S_PacketBox;
import com.lineage.server.serverpackets.S_PacketBoxIcon1;
import com.lineage.server.serverpackets.S_PacketBoxSelect;

/** 3.63更新
 * 要求切換角色
 *
 * @author dexc
 *
 */
public class C_NewCharSelect extends ClientBasePacket {

	private static final Log _log = LogFactory.getLog(C_NewCharSelect.class);


	@Override
	public void start(final byte[] decrypt, final ClientExecutor client) {
		try {
			// 資料載入
			//this.read(decrypt);

			final L1PcInstance pc = client.getActiveChar();
			
			//Thread.sleep(5000);
			if (pc == null) {
				return;
			}
			pc.sendPackets(new S_PacketBoxIcon1(true, pc.get_dodge()));
			Thread.sleep(750);
			client.quitGame();
			
			// 返回人物選取畫面
			client.out().encrypt(new S_PacketBoxSelect());
			
			_log.info("角色切換: " + pc.getName());
			
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			this.over();
		}
	}

	@Override
	public String getType() {
		return this.getClass().getSimpleName();
	}
}
