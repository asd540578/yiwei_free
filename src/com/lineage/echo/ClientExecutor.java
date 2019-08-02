package com.lineage.echo;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.commons.system.IpAttackCheck;
import com.lineage.commons.system.LanSecurityManager;
import com.lineage.config.Config;
import com.lineage.config.ConfigIpCheck;
import com.lineage.echo.encryptions.Encryption;
import com.lineage.list.OnlineUser;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_Disconnect;
import com.lineage.server.templates.L1Account;
import com.lineage.server.thread.GeneralThreadPool;
import com.lineage.server.utils.StreamUtil;
import com.lineage.server.utils.SystemUtil;

/**
 * 客戶資料處理
 * @author dexc
 *
 */
public class ClientExecutor extends OpcodesClient implements Runnable {

	private static final Log _log = LogFactory.getLog(ClientExecutor.class);

	private Socket _csocket = null;

	private L1Account _account = null;// 連線帳戶資料

	private L1PcInstance _activeChar = null;// 登入人物資料

	private StringBuilder _ip = null;// 連線IP資料

	private StringBuilder _mac = null;// MAC資料

	private int _kick = 0;

	private boolean _isStrat = true;

	private EncryptExecutor _encrypt;// 封包加密管理

	private DecryptExecutor _decrypt;// 封包解密管理

	private PacketHandlerExecutor _handler;// 資料處理者

	private Encryption _keys;

	private int _error = -1;// 錯誤次數

	private static final int M = 3; // 移動最大封包處理量

	private static final int O = 2;// 人物其他動作最大封包處理量
	
	private int _saveInventory = 0;
	
	private int _savePc = 0;
	
	public int _xorByte = (byte) 0xf0;
	
	public long _authdata;

	/**
	 * 啟用設置
	 * @param socket
	 * @throws IOException
	 */
	public ClientExecutor(final Socket socket) throws IOException {
		_csocket = socket;
		//TODO 伺服器綑綁 
		if(Config.LOGINS_TO_AUTOENTICATION) {
 			int randomNumber = (int) (Math.random() * 900000000) + 255;
 			_xorByte = randomNumber % 255 + 1;
			_authdata = new BigInteger(Integer.toString(randomNumber)).modPow(new BigInteger(Config.RSA_KEY_E), new BigInteger(Config.RSA_KEY_N)).longValue();
		}

		_ip = new StringBuilder().append(socket.getInetAddress().getHostAddress());
		_handler = new PacketHandler(this);
		_keys = new Encryption();
		_decrypt = new DecryptExecutor(this, socket.getInputStream());
		_encrypt = new EncryptExecutor(this, socket.getOutputStream());
	}

	public void start() {
		
	}
	
	@Override
	public void run() {
		final PacketHc m = new PacketHc(this, M);
		GeneralThreadPool.get().schedule(m, 0);
		
		final PacketHc o = new PacketHc(this, O);
		GeneralThreadPool.get().schedule(o, 0);

		// 加入人物自動保存時間軸
		set_savePc(Config.AUTOSAVE_INTERVAL);
		// 加入背包物品自動保存時間軸
		set_saveInventory(Config.AUTOSAVE_INTERVAL_INVENTORY);
		
		try {
			_encrypt.satrt();// 開始處理封包輸出
			_encrypt.outStart();// 把第一個封包送出去
			
			boolean isEcho = false;// 完成要求接收伺服器版本(防止惡意封包發送)
			while (_isStrat) {
				byte[] decrypt = null;
				try {
					decrypt = readPacket();

				} catch (final Exception e) {
					break;
				}
				
				if (decrypt.length > 1440) {
					_log.warn("客戶端送出長度異常封包:" + _ip.toString() + " 帳號:" + (_account != null? _account.get_login():"未登入"));
					LanSecurityManager.BANIPMAP.put(_ip.toString(), 100);
					break;
				}
				if (_account != null) {
					if (!OnlineUser.get().isLan(_account.get_login())) {
						break;
					}
					if (!_account.is_isLoad()) {
						break;
					}
				}
				
				final int opcode = decrypt[0] & 0xFF;

				//_log.debug("opcode" + opcode);
				
				if (this._activeChar == null) {
					if (opcode == C_OPCODE_CLIENTVERSION) {// 要求接收伺服器版本
						if (ConfigIpCheck.IPCHECKPACK) {
							LanSecurityManager.BANIPPACK.remove(_ip.toString());
						}
						isEcho = true;
					}
					
					if (isEcho) {
						this._handler.handlePacket(decrypt);
					}
					continue;
				}
				
				if (!isEcho) {
					continue;
				}//*/

				switch (opcode) {
				case C_OPCODE_QUITGAME:// 要求離開遊戲
				case C_OPCODE_CHANGECHAR: // 要求切換角色
				case C_OPCODE_DROPITEM: // 要求丟棄物品(丟棄置地面)
				case C_OPCODE_DELETEINVENTORYITEM: // 要求刪除物品
					_handler.handlePacket(decrypt);
					break;

				case C_OPCODE_MOVECHAR: // 人物移動封包處理
					m.requestWork(decrypt);
					break;

				default: // 其他封包處理
					o.requestWork(decrypt);
					break;
				}
			}

			// 垃圾回收
			//finalize();

		} catch (Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
			
		//} catch (Throwable e) {
			//_log.error(e.getLocalizedMessage(), e);*/
			
		} finally {
			if (ConfigIpCheck.IPCHECK) {
				IpAttackCheck.SOCKETLIST.remove(this);
			}
			// 移出人物自動保存時間軸
			set_savePc(-1);
			// 移出背包物品自動保存時間軸
			set_saveInventory(-1);

			// 關閉IO
			close();
		}
		return;
	}
	
	/**
	 * 關閉連線線程
	 * @throws IOException
	 */
	public void close() {
		try {
			String mac = null;
			if (_mac != null) {
				mac = _mac.toString();
			}
			
			if (_csocket == null) {
				return;
			}

			_kick = 0;

			if (_account != null) {
				OnlineUser.get().remove(_account.get_login());
			}
			
			if (_activeChar != null) {
				quitGame();
			}

			String ipAddr = _ip.toString();
			String account = null;
			
			if (_kick < 1) {
				if (_account != null) {
					account = _account.get_login();
				}
			}

			_decrypt.stop();
			_encrypt.stop();
			
			StreamUtil.close(_csocket);

			if (ConfigIpCheck.ISONEIP) {
				LanSecurityManager.ONEIPMAP.remove(ipAddr);
			}
			
			_handler = null;
			_mac = null;// MAC資料
			_ip = null;// 連線IP資料
			_activeChar = null;// 登入人物資料
			_account = null;// 連線帳戶資料

			_decrypt = null;
			_encrypt = null;
			_csocket = null;
			_keys = null;
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("\n--------------------------------------------------");
			stringBuilder.append("\n       客戶端 離線: (");
			if (account != null) {
				stringBuilder.append(account + " ");
			}
			if (mac != null) {
				stringBuilder.append(" " + mac + " / ");
			}
			stringBuilder.append(ipAddr + ") 完成連線中斷!!");
			stringBuilder.append("\n--------------------------------------------------");
			_log.info(stringBuilder.toString());
			SystemUtil.printMemoryUsage(_log);
			
			//System.gc();
			
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 傳回帳號暫存資料
	 * @return
	 */
	public L1Account getAccount() {
		return _account;
	}

	/**
	 * 設置登入帳號
	 * @param account
	 */
	public void setAccount(final L1Account account) {
		_account = account;
	}

	/**
	 * 傳回登入帳號
	 * @return
	 */
	public String getAccountName() {
		if (_account == null) {
			return null;
		}
		return _account.get_login();
	}

	/**
	 * 傳回目前登入人物
	 * @return
	 */
	public L1PcInstance getActiveChar() {
		return _activeChar;
	}

	/**
	 * 設置目前登入人物
	 * @param pc
	 */
	public void setActiveChar(final L1PcInstance pc) {
		_activeChar = pc;
	}

	/**
	 * 傳回IP位置
	 * @return
	 */
	public StringBuilder getIp() {
		return _ip;
	}

	/**
	 * 傳回MAC位置
	 * @return
	 */
	public StringBuilder getMac() {
		return _mac;
	}

	/**
	 * 設置MAC位置
	 * @param mac
	 * @return true:允許登入 false:禁止登入
	 */
	public boolean setMac(final StringBuilder mac) {
		_mac = mac;
		return true;
	}

	/**
	 * 傳回 Socket
	 * @return
	 */
	public Socket get_socket() {
		return _csocket;
	}

	/**
	 * 中斷連線
	 * @return 
	 */
	public boolean kick() {
		try {
			_encrypt.encrypt(new S_Disconnect());
		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
		}
		quitGame();

		_kick = 1;
		_isStrat = false;
		close();
		return true;
	}

	/**
	 * 人物離開遊戲的處理
	 * @param pc
	 */
	public void quitGame() {
		try {
			if (_activeChar == null) {
				return;
			}
			synchronized (_activeChar) {
				QuitGame.quitGame(_activeChar);
				_activeChar = null;
			}

		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 封包解密
	 * @return
	 * @throws Exception
	 */
	private byte[] readPacket() {
		try {
			byte[] data = null;
			data = _decrypt.decrypt();
			return data;

		} catch (final Exception e) {
			//_log.error(e.getLocalizedMessage(), e);
		}
		return null;
	}

	/**
	 * 傳回封包加密解密管理接口
	 */
	public EncryptExecutor out() {
		return _encrypt;
	}

	/**
	 * 加密與解密金鑰
	 */
	public void set_keys(Encryption keys) {
		//System.out.println("加密與解密金鑰:"+keys);
		_keys = keys;
	}

	/**
	 * 傳回加密與解密金鑰
	 * @return the _keys
	 */
	public Encryption get_keys() {
		return _keys;
	}

	/**
	 * 傳回錯誤次數
	 * @return
	 */
	public int get_error() {
		return _error;
	}

	/**
	 * 設置錯誤次數
	 * @param error
	 */
	public void set_error(final int error) {
		_error = error;
		if (error >= 2) {
			kick();
		}
	}

	/**
	 * 設置自動存檔背包物件時間
	 * @param _saveInventory
	 */
	public void set_saveInventory(final int saveInventory) {
		_saveInventory = saveInventory;
	}

	/**
	 * 傳回自動存檔背包物件時間
	 * @return
	 */
	public int get_saveInventory() {
		return _saveInventory;
	}

	/**
	 * 設置自動存檔人物資料時間
	 * @param _saveInventory
	 */
	public void set_savePc(final int savePc) {
		_savePc = savePc;
	}

	/**
	 * 傳回自動存檔人物資料時間
	 * @return
	 */
	public int get_savePc() {
		return _savePc;
	}
}
