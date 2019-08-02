package com.lineage.server.serverpackets;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.lineage.server.datatables.ShopTable;
import com.lineage.server.model.L1PcInventory;
import com.lineage.server.model.Instance.L1ItemInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.item.L1ItemId;

/**
 * 賣(回收商)
 * @author dexc
 *
 */
public class S_ShopBuyListAll extends ServerBasePacket {

	private byte[] _byte = null;

	public S_ShopBuyListAll(final L1PcInstance pc, final L1NpcInstance npc) {
		Map<L1ItemInstance, Integer> assessedItems = this.assessItems(pc.getInventory());
		
		assessedItems= sortMapByKey(assessedItems);	//進行排序
		
		if (assessedItems.isEmpty()) {
			// 你並沒有我需要的東西
			pc.sendPackets(new S_NoSell(npc));
			return;
		}

		if (assessedItems.size() <= 0) {
			// 你並沒有我需要的東西
			pc.sendPackets(new S_NoSell(npc));
			return;
		}

		this.writeC(S_OPCODE_SHOWSHOPSELLLIST);
		this.writeD(npc.getId());

		this.writeH(assessedItems.size());

		for (final L1ItemInstance key : assessedItems.keySet()) {
			this.writeD(key.getId());
			this.writeD(assessedItems.get(key));
		}
	}
	
	/**
	 * 傳回物品價格
	 * @param inv
	 * @return
	 */
	private Map<L1ItemInstance, Integer> assessItems(final L1PcInventory inv) {
		
		final Map<L1ItemInstance, Integer> result = new HashMap<L1ItemInstance, Integer>();
		
		for (final L1ItemInstance item : inv.getItems()) {
			
			switch (item.getItem().getItemId()) {
			case 40308: // 金幣
			case L1ItemId.GOLD_CN_ADENA: // 天寶				
			case 40314: // 項圈	
			case 40316: // 高等寵物項圈
			case 60221: // \f3神戒\f>任務區
			case 60225: // \f3神戒\f>掛賣區
			case 60226: // \f3神戒\f>市場
			case 60228: // \f3神戒\f>元寶街			
			case 30408://一轉耳環
			case 30409://二轉耳環
			case 30410://三轉耳環
			case 30411://四轉耳環
			case 30412://五轉耳環
			case 30413://六轉耳環
			case 30414://七轉耳環
			case 30415://八轉耳環
			case 30416://九轉耳環
			case 30417://十轉耳環	
			case 60244: //金條
				continue;
			}

			if (item.isEquipped()) {// 使用中
				continue;
			}
			final int key = item.getItemId();
			final int price = ShopTable.get().getPrice(key);
			if (price > 0) {
				result.put(item, price);
			}

		}
		return result;
	}
	
	/**
	 * 使用 Map按 L1ItemInstance getid 進行排序
	 * @param map
	 * @return
	 */
	public static Map<L1ItemInstance, Integer> sortMapByKey(Map<L1ItemInstance, Integer> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
 
		Map<L1ItemInstance, Integer> sortMap = new TreeMap<L1ItemInstance, Integer>(new MapKeyComparator());
 
		sortMap.putAll(map);
 
		return sortMap;
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


class MapKeyComparator implements Comparator< L1ItemInstance>{
 
	@Override
	public int compare(final L1ItemInstance item1 ,final  L1ItemInstance item2) {
	     
		  Integer obj1 = item1.getId();
	      Integer obj2 = item2.getId();
	      
		return obj2.compareTo(obj1);
	}
}
