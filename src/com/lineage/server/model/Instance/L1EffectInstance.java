package com.lineage.server.model.Instance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.serverpackets.S_NPCPack_Eff;
import com.lineage.server.serverpackets.S_RemoveObject;
import com.lineage.server.templates.L1Npc;
import com.lineage.server.world.World;

/**
 * 對象:效果專用 控制項
 * @author dexc
 *
 */
public class L1EffectInstance extends L1NpcInstance {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final Log _log = LogFactory.getLog(L1EffectInstance.class);

	public static final int FW_DAMAGE_INTERVAL = 1650;// 火牢傷害間隔時間(毫秒)
	
	public static final int CUBE_INTERVAL = 500; // 幻術師技能間隔時間(毫秒)
	
	public static final int CUBE_TIME = 8000; // 幻術師技能効果時間8秒(毫秒)
	
	public static final int OTHER = 500; // 其它(毫秒)

	private L1EffectType _effectType;
	
	public L1EffectInstance(final L1Npc template) {
		super(template);

		// 取回NPCID
		final int npcId = this.getNpcTemplate().get_npcId();
		switch (npcId) {
		case 81157:// 法師技能(火牢)
			this._effectType = L1EffectType.isFirewall;
			break;
			
		case 80149:// 幻術師技能(立方：燃燒)
			this._effectType = L1EffectType.isCubeBurn;
			break;
			
		case 80150:// 幻術師技能(立方：地裂)
			this._effectType = L1EffectType.isCubeEruption;
			break;
			
		case 80151:// 幻術師技能(立方：衝擊)
			this._effectType = L1EffectType.isCubeShock;
			break;
			
		case 80152:// 幻術師技能(立方：和諧)
			this._effectType = L1EffectType.isCubeHarmonize;
			break;
			
		default:
			this._effectType = L1EffectType.isOther;
			break;
		}
	}

	/**
	 * 技能NPC類型
	 * @return
	 */
	public L1EffectType effectType() {
		return this._effectType;
	}
	
	/**
	 * TODO 接觸資訊
	 */
	@Override
	public void onPerceive(final L1PcInstance perceivedFrom) {
		try {
			// 副本ID不相等 不相護顯示
			if (perceivedFrom.get_showId() != this.get_showId()) {
				return;
			}
			perceivedFrom.addKnownObject(this);
			perceivedFrom.sendPackets(new S_NPCPack_Eff(this));
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}
	
	@Override
	public void onAction(final L1PcInstance pc) {
		
	}

	@Override
	public void deleteMe() {
		try {			
			this._destroyed = true;
			if (this.getInventory() != null) {
				this.getInventory().clearItems();
			}
			this.allTargetClear();
			this._master = null;
			World.get().removeVisibleObject(this);
			World.get().removeObject(this);
			for (final L1PcInstance pc : World.get().getRecognizePlayer(this)) {
				pc.removeKnownObject(this);
				pc.sendPackets(new S_RemoveObject(this));
			}
			this.removeAllKnownObjects();
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	private int _skillId;// 引用技能編號

	/**
	 * 設置引用技能編號
	 * @param i
	 */
	public void setSkillId(final int i) {
		this._skillId = i;
	}

	/**
	 * 引用技能編號
	 * @return
	 */
	public int getSkillId() {
		return this._skillId;
	}

}
