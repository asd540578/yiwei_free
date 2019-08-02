package com.lineage.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.ItemTable;
import com.lineage.server.datatables.lock.CharItemsReading;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_AddItem;
import com.lineage.server.serverpackets.S_DeleteInventoryItem;
import com.lineage.server.serverpackets.S_ItemStatus;
import com.lineage.server.serverpackets.S_TradeAddItem;
import com.lineage.server.serverpackets.S_TradeStatus;
import com.lineage.server.templates.L1TradeItem;
import com.lineage.server.world.World;

import log.datatables.lock.LogItemTradeReading;

/**
 * 玩家相互交易判斷類
 * @author dexc
 *
 */
public class L1Trade {

	private static final Log _log = LogFactory.getLog(L1Trade.class);
	
	/**
	 * 加入交易物品
	 * @param pc
	 * @param itemid
	 * @param itemcount
	 */
	public void tradeAddItem(final L1PcInstance pc, final int itemObjid, long itemcount) {
		L1PcInstance trading_partner = null;
		try {
			// 取回交易對象
			trading_partner = (L1PcInstance) World.get().findObject(pc.getTradeID());
			// 取回要加入交易的物品
			final L1ItemInstance item = pc.getInventory().getItem(itemObjid);
			if (item == null) {
				return;
			}
			
			if (trading_partner == null) {
				return;
			}

			if (item.isEquipped()) {
				return;
			}
			
			final ArrayList<L1TradeItem> map = pc.get_trade_items();
			if (map.size() >= 16) {
				return;
			}

			long count = 0;
			
			for (final Iterator<L1TradeItem> iter = map.iterator(); iter.hasNext();) {
				final L1TradeItem tg = iter.next();
				if (tg.get_objid() == item.getId()) {
					count += tg.get_count();
				}
			}
			itemcount = Math.max(0, itemcount);
			final long now_count = itemcount + count;// 本次物件數量 + 已輸出相同物件數量
			
			// 檢查數量
			final boolean checkItem = pc.getInventory().checkItem(item.getItemId(), now_count);
			if (checkItem) {
				// 建立交易物件資訊
				final L1TradeItem info = new L1TradeItem();
				info.set_objid(item.getId());
				info.set_item_id(item.getItemId());
				info.set_item(item);
				info.set_count(itemcount);
				
				// 加入暫存清單
				pc.add_trade_item(info);

				// 輸出新數量給客戶端
				final long out_count = item.getCount() - now_count;
				if (out_count <= 0) {
					// 數量為0移除顯示
					pc.sendPackets(new S_DeleteInventoryItem(item.getId()));
					
				} else {
					// 數量不為0更新顯示
					pc.sendPackets(new S_ItemStatus(item, out_count));
				}
				
				// 交易內容新增
				pc.sendPackets(new S_TradeAddItem(item, itemcount, 0));
				trading_partner.sendPackets(new S_TradeAddItem(item, itemcount, 1));
				
			} else {
				pc.sendPackets(new S_TradeStatus(1));
				trading_partner.sendPackets(new S_TradeStatus(1));
				
				pc.setTradeOk(false);
				trading_partner.setTradeOk(false);
				
				pc.setTradeID(0);
				trading_partner.setTradeID(0);
				return;
			}
			
		} catch (Exception e) {
			if (pc != null) {
				pc.get_trade_clear();
			}
			if (trading_partner != null) {
				trading_partner.get_trade_clear();
			}
			_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			
		}
	}

	/**
	 * 交易完成
	 * @param pc
	 */
	public void tradeOK(final L1PcInstance pc) {
		L1PcInstance trading_partner = null;
		try {
			// 取回交易對象
			trading_partner = (L1PcInstance) World.get().findObject(pc.getTradeID());
			if (trading_partner != null) {
				// 取回自己的交易物品
				final ArrayList<L1TradeItem> map_1 = pc.get_trade_items();
				// 取回對象交易物品
				final ArrayList<L1TradeItem> map_2 = trading_partner.get_trade_items();

				// 物品轉移清單
				final HashMap<L1ItemInstance, Long> temp1 = new HashMap<L1ItemInstance, Long>();
				final HashMap<L1ItemInstance, Long> temp2 = new HashMap<L1ItemInstance, Long>();
				
				if (!map_1.isEmpty()) {
					for (final Iterator<L1TradeItem> iter = map_1.iterator(); iter.hasNext();) {
						final L1TradeItem tg = iter.next();
						if (!CharItemsReading.get().getUserItems(pc.getId(), tg.get_objid(), tg.get_count())) {
							_log.error("人物交易異常(指定數據數量有誤): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
							pc.getNetConnection().kick();
							trading_partner.getNetConnection().kick();
							continue;
						}
						
						final L1ItemInstance tg_item = pc.getInventory().getItem(tg.get_objid());
						if (tg_item == null) {
							_log.error("人物交易異常(物品為空): " + pc.getName() + "/" + pc.getNetConnection().hashCode());
							pc.getNetConnection().kick();
							trading_partner.getNetConnection().kick();
							continue;
						}

						final Long count = temp1.get(tg_item);
						if (count == null) {
							temp1.put(tg_item, tg.get_count());
						} else {
							temp1.put(tg_item, tg.get_count() + count);
						}
						// 移除物品
						pc.getInventory().removeItem(tg.get_objid(), tg.get_count());
					}
				}

				if (!map_2.isEmpty()) {
					for (final Iterator<L1TradeItem> iter = map_2.iterator(); iter.hasNext();) {
						final L1TradeItem tg = iter.next();
						if (!CharItemsReading.get().getUserItems(trading_partner.getId(), tg.get_objid(), tg.get_count())) {
							_log.error("人物交易異常(指定數據數量有誤): " + trading_partner.getName() + "/" + trading_partner.getNetConnection().hashCode());
							pc.getNetConnection().kick();
							trading_partner.getNetConnection().kick();
							continue;
						}
						
						final L1ItemInstance tg_item = trading_partner.getInventory().getItem(tg.get_objid());
						if (tg_item == null) {
							_log.error("人物交易異常(物品為空): " + trading_partner.getName() + "/" + trading_partner.getNetConnection().hashCode());
							pc.getNetConnection().kick();
							trading_partner.getNetConnection().kick();
							continue;
						}

						final Long count = temp2.get(tg_item);
						if (count == null) {
							temp2.put(tg_item, tg.get_count());
						} else {
							temp2.put(tg_item, tg.get_count() + count);
						}
						// 移除物品
						trading_partner.getInventory().removeItem(tg.get_objid(), tg.get_count());
					}
				}
				
				// 交換物品
				if (!temp1.isEmpty()) {// pc
					for (L1ItemInstance item : temp1.keySet()) {
						final long count = temp1.get(item);
						if (item.isStackable()) {
							final L1ItemInstance tgItem = ItemTable.get().createItem(item.getItemId());
							tgItem.setCount(count);
							trading_partner.getInventory().storeItem(tgItem);
							
						} else {
							trading_partner.getInventory().storeItem(item);
						}
						// 交易記錄 (主動找交易的玩家)
						LogItemTradeReading.get().logArchive(pc, trading_partner ,item , count );
					}
				}
				
				if (!temp2.isEmpty()) {// trading_partner
					for (L1ItemInstance item : temp2.keySet()) {
						final long count = temp2.get(item);
						if (item.isStackable()) {
							final L1ItemInstance tgItem = ItemTable.get().createItem(item.getItemId());
							tgItem.setCount(count);
							pc.getInventory().storeItem(tgItem);
							
						} else {
							pc.getInventory().storeItem(item);
						}
						// 交易記錄 (被邀請交易的玩家)
						LogItemTradeReading.get().logArchive(trading_partner , pc , item , count );
					}
				}
				
				temp1.clear();
				temp2.clear();
				
				pc.sendPackets(new S_TradeStatus(0));
				trading_partner.sendPackets(new S_TradeStatus(0));
				
				pc.setTradeOk(false);
				trading_partner.setTradeOk(false);
				
				pc.setTradeID(0);
				trading_partner.setTradeID(0);
				
				//pc.turnOnOffLight();
				//trading_partner.turnOnOffLight();
			}
			
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			if (pc != null) {
				pc.get_trade_clear();
			}
			if (trading_partner != null) {
				trading_partner.get_trade_clear();
			}
		}
	}
	
	/**
	 * 交易取消
	 * @param pc
	 */
	public void tradeCancel(final L1PcInstance pc) {
		L1PcInstance trading_partner = null;
		try {
			trading_partner = (L1PcInstance) World.get().findObject(pc.getTradeID());
			if (trading_partner != null) {
				// 取回自己的交易物品
				final ArrayList<L1TradeItem> map_1 = pc.get_trade_items();
				// 取回對象交易物品
				final ArrayList<L1TradeItem> map_2 = trading_partner.get_trade_items();
				
				// 物品還原清單
				final HashMap<Integer, Long> temp1 = new HashMap<Integer, Long>();
				final HashMap<Integer, Long> temp2 = new HashMap<Integer, Long>();

				if (!map_1.isEmpty()) {// pc
					for (final Iterator<L1TradeItem> iter = map_1.iterator(); iter.hasNext();) {
						final L1TradeItem tg = iter.next();
						final Long count = temp1.get(tg.get_objid());
						if (count == null) {
							temp1.put(tg.get_objid(), tg.get_count());
						} else {
							temp1.put(tg.get_objid(), tg.get_count() + count);
						}
					}
				}

				if (!map_2.isEmpty()) {// trading_partner
					for (final Iterator<L1TradeItem> iter = map_2.iterator(); iter.hasNext();) {
						final L1TradeItem tg = iter.next();
						final Long count = temp2.get(tg.get_objid());
						if (count == null) {
							temp2.put(tg.get_objid(), tg.get_count());
						} else {
							temp2.put(tg.get_objid(), tg.get_count() + count);
						}
					}
				}
				
				if (!temp1.isEmpty()) {// pc
					for (Integer key : temp1.keySet()) {
						final long count = temp1.get(key);
						final L1ItemInstance tg_item = pc.getInventory().getItem(key);
						if (tg_item != null) {
							if (count == tg_item.getCount()) {
								pc.sendPackets(new S_AddItem(tg_item));// 取回刪除物件
							} else {
								pc.sendPackets(new S_ItemStatus(tg_item, tg_item.getCount()));// 還原物品數量
							}
						}
					}
				}
				
				if (!temp2.isEmpty()) {// trading_partner
					for (Integer key : temp2.keySet()) {
						final long count = temp2.get(key);
						final L1ItemInstance tg_item = trading_partner.getInventory().getItem(key);
						if (count == tg_item.getCount()) {
							trading_partner.sendPackets(new S_AddItem(tg_item));// 取回刪除物件
						} else {
							trading_partner.sendPackets(new S_ItemStatus(tg_item, tg_item.getCount()));// 還原物品數量
						}
					}
				}
				
				temp1.clear();
				temp2.clear();
				
				pc.sendPackets(new S_TradeStatus(1));
				trading_partner.sendPackets(new S_TradeStatus(1));
				
				pc.setTradeOk(false);
				trading_partner.setTradeOk(false);
				
				pc.setTradeID(0);
				trading_partner.setTradeID(0);
			}
			
		} catch (Exception e) {
			_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			if (pc != null) {
				pc.get_trade_clear();
			}
			if (trading_partner != null) {
				trading_partner.get_trade_clear();
			}
		}
	}
}
