package eugene;

import org.json.JSONException;
import org.json.JSONObject;

import com.lineage.server.model.Instance.L1ItemInstance;

/**
 *  管理器 Json
 * @author Eugene
 *
 */
public class JsonManagement {

	
	/**
	 * 打包 character_Items
	 * @param item
	 * @return
	 */
	public static JSONObject Bale_Character_Items ( final L1ItemInstance item  ) {
		
		JSONObject json = new JSONObject();
		
		try {
			
			json.put( "item_id" ,   item.getItemId()); //編號
			json.put( "item_name" ,   item.getItem().getName());//名稱
			json.put( "count" ,   item.getCount());//數量
			json.put( "is_equipped" ,  0); //是否裝備
			json.put( "enchantlvl" ,   item.getEnchantLevel()); //強化等級
			json.put( "is_id" ,   item.isIdentified()); //是否鑑定
			json.put( "durability" ,  item.get_durability()); //損壞
			json.put( "charge_count" ,  item.getChargeCount()); //可用次數
			json.put( "bless" ,  item.getBless()); //屬性
			json.put( "attr_enchant_kind" ,   item.getAttrEnchantKind());//屬性類型
			json.put( "attr_enchant_level" ,   item.getAttrEnchantLevel());//屬性等級
	
		} catch (JSONException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		
		return json;
	}
}
