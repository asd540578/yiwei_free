package com.lineage.server.clientpackets;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.echo.ClientExecutor;
import com.lineage.server.model.Instance.L1PcInstance;

/**
 * 要求選取觀看頻道
 *
 * @author daien
 *
 */

	public class C_LoginToServerOK extends ClientBasePacket {

		private static final Log _log = LogFactory.getLog(C_LoginToServerOK.class);

		/*public C_LoginToServerOK() {
		}

		public C_LoginToServerOK(final byte[] abyte0, final ClientExecutor client) {
			super(abyte0);
			try {
				this.start(abyte0, client);
				
			} catch (final Exception e) {
				_log.error(e.getLocalizedMessage(), e);
			}
		}*/

		@Override
		public void start(final byte[] decrypt, final ClientExecutor client) {
			try {
				// 資料載入
				this.read(decrypt);

				final int type = this.readC();
				final int button = this.readC();

				final L1PcInstance pc = client.getActiveChar();
				
				//System.out.println("type:"+type+"\nbutton:"+button);
				
				switch (type) {
				case 255: // 全體 && Whisper
					switch (button) {
					case 95:
					case 127:
						pc.setShowWorldChat(true); // open
						pc.setCanWhisper(true); // open
						break;
						
					case 91:
					case 123:
						pc.setShowWorldChat(true); // open
						pc.setCanWhisper(false); // close
						break;
						
					case 94:
					case 126:
						pc.setShowWorldChat(false); // close
						pc.setCanWhisper(true); // open
						break;
						
					case 90:
					case 122:
						pc.setShowWorldChat(false); // close
						pc.setCanWhisper(false); // close
						break;
						
					default:
						System.out.println((new StringBuilder("C_LoginToServerOK-255-未知:")).append(button).toString());
						break;
					}
					break;
					
				case 0: // 全體聊天
					if (button == 0) { // close
						pc.setShowWorldChat(false);
						
					} else if (button == 1) { // open
						pc.setShowWorldChat(true);
					}
					break;
					
				case 2: // 密語頻道
					if (button == 0) { // close
						pc.setCanWhisper(false);
						
					} else if (button == 1) { // open
						pc.setCanWhisper(true);
					}
					break;
					
				case 6: // 買賣頻道
					if (button == 0) { // close
						pc.setShowTradeChat(false);
						
					} else if (button == 1) { // open
						pc.setShowTradeChat(true);
					}
					break;
					// 3.63更新
				case 9: // '\t'
					if (button == 0)
					{
						pc.setShowClanChat(true);
						break;
					}
					if (button == 1)
						pc.setShowClanChat(false);
					break;

				case 10: // '\n'
					if (button == 0)
					{
						pc.setShowPartyChat(false);
						break;
					}
					if (button == 1)
						pc.setShowPartyChat(true);
					break;
					//3.63更新 結
				default:
					System.out.println((new StringBuilder("C_LoginToServerOK-未知:")).append(type).toString());
					break;
				}
				
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

	