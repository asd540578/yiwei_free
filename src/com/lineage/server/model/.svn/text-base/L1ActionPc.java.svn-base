package com.lineage.server.model;

import static com.lineage.server.model.skill.L1SkillId.AWAKEN_ANTHARAS;
import static com.lineage.server.model.skill.L1SkillId.AWAKEN_FAFURION;
import static com.lineage.server.model.skill.L1SkillId.AWAKEN_VALAKAS;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.data.QuestClass;
import com.lineage.server.datatables.QuestTable;
import com.lineage.server.datatables.lock.CharacterQuestReading;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.skill.skillmode.SUMMON_MONSTER;
import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.serverpackets.S_Bonusstats;
import com.lineage.server.serverpackets.S_Lock;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_ShopSellListCnX;
import com.lineage.server.templates.L1Quest;

/**
 * 對話命令來自PC的執行與判斷
 * @author daien
 *
 */
public class L1ActionPc {

	private static final Log _log = LogFactory.getLog(L1ActionPc.class);

	private final L1PcInstance _pc;

	/**
	 * 對話命令來自PC的執行與判斷
	 * @param pc 執行者
	 */
	public L1ActionPc(final L1PcInstance pc) {
		_pc = pc;
	}

	/**
	 * 傳回執行命令者
	 * @return
	 */
	public L1PcInstance get_pc() {
		return _pc;
	}

	/**
	 * 選單命令執行
	 * @param cmd
	 * @param amount
	 */
	public void action(final String cmd, final long amount) {
		try {
			if (cmd.matches("[0-9]+")) {
				// 解除GM管理狀態
				_pc.get_other().set_gmHtml(null);
				// 展開召喚控制選單
				if (_pc.isSummonMonster()) {
					summonMonster(_pc, cmd);
					_pc.setShapeChange(false);
					_pc.setSummonMonster(false);
				}
				return;
			}
			
			// 展開變身控制選單
			if (_pc.isShapeChange()) {
				// 解除GM管理狀態
				_pc.get_other().set_gmHtml(null);
				final int awakeSkillId = _pc.getAwakeSkillId();
				if ((awakeSkillId == AWAKEN_ANTHARAS)
						|| (awakeSkillId == AWAKEN_FAFURION)
						|| (awakeSkillId == AWAKEN_VALAKAS)) {
					// 目前狀態中無法變身。
					_pc.sendPackets(new S_ServerMessage(1384));
					return;
				}
				L1PolyMorph.handleCommands(_pc, cmd);
				_pc.setShapeChange(false);
				_pc.setSummonMonster(false);
				return;
			}
			
			// GM選單不為空
			if (_pc.get_other().get_gmHtml() != null) {
				_pc.get_other().get_gmHtml().action(cmd);
				return;
			}
			
			// 解除GM管理狀態
			_pc.get_other().set_gmHtml(null);
			
			// 任務選單 FIXME
			if (cmd.equalsIgnoreCase("power")) {// 能力選取視窗
				// 判斷是否出現能力選取視窗
				if (_pc.power()) {
					_pc.sendPackets(new S_Bonusstats(_pc.getId()));
				}
				
			} else if (cmd.equalsIgnoreCase("shop")) {// 道具商城
				_pc.sendPackets(new S_ShopSellListCnX(_pc, _pc.getId()));
				
			} else if (cmd.equalsIgnoreCase("index")) {// 任務查詢系統
				_pc.isWindows();
				
			} else if (cmd.equalsIgnoreCase("locerr1")) {// 解除人物卡點
				_pc.set_unfreezingTime(10);
				
			} else if (cmd.equalsIgnoreCase("locerr2")) {// 修正人物錯位
				_pc.sendPackets(new S_Lock());
				
			} else if (cmd.equalsIgnoreCase("qt")) {// 查看執行中任務
				showStartQuest(_pc, _pc.getId());
				
			} else if (cmd.equalsIgnoreCase("quest")) {// 查看可執行任務
				showQuest(_pc, _pc.getId());

			} else if (cmd.equalsIgnoreCase("questa")) {// 查看全部任務
				showQuestAll(_pc, _pc.getId());
				
			} else if (cmd.equalsIgnoreCase("i")) {// 任務介紹
				final L1Quest quest = QuestTable.get().getTemplate(_pc.getTempID());
				_pc.setTempID(0);
				// 確認該任務存在
				if (quest == null) {
					return;
				}
				QuestClass.get().showQuest(_pc, quest.get_id());
				
			} else if (cmd.equalsIgnoreCase("d")) {// 任務回收
				final L1Quest quest = QuestTable.get().getTemplate(_pc.getTempID());
				_pc.setTempID(0);
				// 確認該任務存在
				if (quest == null) {
					return;
				}
				// 任務已經完成
				if (_pc.getQuest().isEnd(quest.get_id())) {
					questDel(quest);
					return;
				}
				// 任務尚未開始
				if (!_pc.getQuest().isStart(quest.get_id())) {
					// 很抱歉!!你並未開始執行這個任務!
					_pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "y_q_not6"));
					return;
				}
				// 執行中 未完成任務
				questDel(quest);
				
			} else if (cmd.equalsIgnoreCase("dy")) {// 任務移除
				final L1Quest quest = QuestTable.get().getTemplate(_pc.getTempID());
				_pc.setTempID(0);
				// 確認該任務存在
				if (quest == null) {
					return;
				}
				// 任務已經完成
				if (_pc.getQuest().isEnd(quest.get_id())) {
					isDel(quest);
					return;
				}
				// 任務尚未開始
				if (!_pc.getQuest().isStart(quest.get_id())) {
					// 很抱歉!!你並未開始執行這個任務!
					_pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "y_q_not6"));
					return;
				}
				// 執行中 未完成任務
				isDel(quest);
				
			} else if (cmd.equalsIgnoreCase("up")) {// 上一頁(管理)
				final int page = _pc.get_other().get_page() - 1;
				final L1ActionShowHtml show = new L1ActionShowHtml(_pc);
				show.showQuestMap(page);
				
			} else if (cmd.equalsIgnoreCase("dn")) {// 下一頁(管理)
				final int page = _pc.get_other().get_page() + 1;
				final L1ActionShowHtml show = new L1ActionShowHtml(_pc);
				show.showQuestMap(page);
				
			} else if (cmd.equalsIgnoreCase("q0")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 0;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q1")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 1;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q2")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 2;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q3")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 3;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q4")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 4;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q5")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 5;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q6")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 6;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q7")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 7;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q8")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 8;
				showPage(key);
				
			} else if (cmd.equalsIgnoreCase("q9")) {// 頁面內指定位置
				final int key = (_pc.get_other().get_page() * 10) + 9;
				showPage(key);
			}
			
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 任務解除執行
	 * @param quest
	 */
	private void questDel(final L1Quest quest) {
		try {
			if (quest.is_del()) {
				_pc.setTempID(quest.get_id());
				String over = null;
				// 該任務完成
				if (_pc.getQuest().isEnd(quest.get_id())) {
					over = "完成任務";// 完成任務!
				} else {
					over = _pc.getQuest().get_step(quest.get_id()) + " / " + quest.get_difficulty();
				}
				
				final String[] info = new String[]{
						quest.get_questname(), // 任務名稱
						Integer.toString(quest.get_questlevel()), // 任務等級
						over, // 任務進度
						// 額外說明
						};
				_pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "y_qi2", info));

			} else {
				// 任務不可刪除
				_pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "y_q_not5"));
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 確定解除任務執行
	 * @param quest
	 */
	private void isDel(final L1Quest quest) {
		try {
			if (quest.is_del()) {
				// 任務終止
				QuestClass.get().stopQuest(_pc, quest.get_id());
				
				CharacterQuestReading.get().delQuest(_pc.getId(), quest.get_id());
				final String[] info = new String[]{
						quest.get_questname(), // 任務名稱
						Integer.toString(quest.get_questlevel()), // 任務等級
						};
				// 刪除任務
				_pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "y_qi3", info));
				
			} else {
				// 任務不可刪除
				_pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "y_q_not5"));
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 查看執行中任務
	 * @param pc
	 * @param id
	 */
	public static void showStartQuest(L1PcInstance pc, int objid) {
		try {
			// 清空暫存任務清單
			pc.get_otherList().QUESTMAP.clear();
			
			int key = 0;
			for (int i = QuestTable.MINQID ; i <= QuestTable.MAXQID ; i++) {
				final L1Quest value = QuestTable.get().getTemplate(i);
				if (value != null) {
					// 該任務已經結束
					if (pc.getQuest().isEnd(value.get_id())) {
						continue;
					}
					// 執行中任務判斷
					if (pc.getQuest().isStart(value.get_id())) {
						pc.get_otherList().QUESTMAP.put(key++, value);
					}
				}
			}

			if (pc.get_otherList().QUESTMAP.size() <= 0) {
				// 很抱歉!!你並沒有任何執行中的任務!
				pc.sendPackets(new S_NPCTalkReturn(objid, "y_q_not7"));
				
			} else {
				final L1ActionShowHtml show = new L1ActionShowHtml(pc);
				show.showQuestMap(0);
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 可執行任務
	 * @param pc
	 * @param objid
	 */
	public static void showQuest(L1PcInstance pc, int objid) {
		try {
			// 清空暫存任務清單
			pc.get_otherList().QUESTMAP.clear();
			
			int key = 0;

			for (int i = QuestTable.MINQID ; i <= QuestTable.MAXQID ; i++) {
				final L1Quest value = QuestTable.get().getTemplate(i);
				if (value != null) {
					// 大於可執行等級
					if (pc.getLevel() >= value.get_questlevel()) {
						// 該任務已經結束
						if (pc.getQuest().isEnd(value.get_id())) {
							continue;
						}
						// 該任務已經開始
						if (pc.getQuest().isStart(value.get_id())) {
							continue;
						}
						// 可執行職業判斷
						if (value.check(pc)) {
							pc.get_otherList().QUESTMAP.put(key++, value);
						}
					}
				}
			}

			if (pc.get_otherList().QUESTMAP.size() <= 0) {
				// 很抱歉!!目前你的任務已經全部完成!
				pc.sendPackets(new S_NPCTalkReturn(objid, "y_q_not4"));
				
			} else {
				final L1ActionShowHtml show = new L1ActionShowHtml(pc);
				show.showQuestMap(0);
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
	
	/**
	 * 全部任務
	 * @param pc
	 * @param objid
	 */
	public static void showQuestAll(L1PcInstance pc, int objid) {
		try {
			// 清空暫存任務清單
			pc.get_otherList().QUESTMAP.clear();
			
			int key = 0;
			for (int i = QuestTable.MINQID ; i <= QuestTable.MAXQID ; i++) {
				final L1Quest value = QuestTable.get().getTemplate(i);
				if (value != null) {
					// 可執行職業判斷
					if (value.check(pc)) {
						pc.get_otherList().QUESTMAP.put(key++, value);
					}
				}
			}
			final L1ActionShowHtml show = new L1ActionShowHtml(pc);
			show.showQuestMap(0);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 展示指定任務進度資料
	 * @param key
	 */
	private void showPage(int key) {
		try {
			final L1Quest quest = _pc.get_otherList().QUESTMAP.get(key);
			_pc.setTempID(quest.get_id());
			String over = null;
			// 該任務完成
			if (_pc.getQuest().isEnd(quest.get_id())) {
				over = "完成任務";// 完成任務!
			} else {
				over = _pc.getQuest().get_step(quest.get_id()) + " / " + quest.get_difficulty();
			}
			
			final String[] info = new String[]{
					quest.get_questname(), // 任務名稱
					Integer.toString(quest.get_questlevel()), // 任務等級
					over, // 任務進度
					""// 額外說明
					};
			_pc.sendPackets(new S_NPCTalkReturn(_pc.getId(), "y_qi1", info));
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 展開召喚控制選單
	 * @param pc
	 * @param s
	 */
	private void summonMonster(final L1PcInstance pc, final String s) {
		try {
			final SkillMode skillMode = new SUMMON_MONSTER();
			skillMode.start(pc, s);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
}
