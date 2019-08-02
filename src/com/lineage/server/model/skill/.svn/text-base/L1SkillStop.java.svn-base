package com.lineage.server.model.skill;

import static com.lineage.server.model.skill.L1SkillId.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.server.datatables.SkillsTable;
import com.lineage.server.model.L1Character;
import com.lineage.server.model.L1PolyMorph;
import com.lineage.server.model.Instance.L1MonsterInstance;
import com.lineage.server.model.Instance.L1NpcInstance;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.model.Instance.L1PetInstance;
import com.lineage.server.model.Instance.L1SummonInstance;
import com.lineage.server.model.skill.skillmode.SkillMode;
import com.lineage.server.serverpackets.S_Dexup;
import com.lineage.server.serverpackets.S_HPUpdate;
import com.lineage.server.serverpackets.S_Liquor;
import com.lineage.server.serverpackets.S_MPUpdate;
import com.lineage.server.serverpackets.S_OwnCharAttrDef;
import com.lineage.server.serverpackets.S_OwnCharStatus;
import com.lineage.server.serverpackets.S_PacketBoxCooking;
import com.lineage.server.serverpackets.S_PacketBoxWaterLife;
import com.lineage.server.serverpackets.S_Paralysis;
import com.lineage.server.serverpackets.S_Poison;
import com.lineage.server.serverpackets.S_SPMR;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.serverpackets.S_SkillBrave;
import com.lineage.server.serverpackets.S_SkillHaste;
import com.lineage.server.serverpackets.S_PacketBoxIconAura;
import com.lineage.server.serverpackets.S_SkillIconBlessOfEva;
import com.lineage.server.serverpackets.S_SkillIconShield;
import com.lineage.server.serverpackets.S_PacketBoxWisdomPotion;
import com.lineage.server.serverpackets.S_Strup;
import com.lineage.server.templates.L1Skills;

/**
 * 技能停止
 * @author dexc
 *
 */
public class L1SkillStop {

	private static final Log _log = LogFactory.getLog(L1SkillStop.class);

	public static void stopSkill(final L1Character cha, final int skillId) {
		try {
			//System.out.println("技能停止:"+skillId);
			// TODO SKILL移轉
			final SkillMode mode = L1SkillMode.get().getSkill(skillId);
			if (mode != null) {
				mode.stop(cha);
				
			} else {
				switch (skillId) {
				/*case IMMUNE_TO_HARM:
				case BOUNCE_ATTACK:
				case SHIELD:*/
				case LIGHT: // ライト
					if (cha instanceof L1PcInstance) {
						if (!cha.isInvisble()) {
							final L1PcInstance pc = (L1PcInstance) cha;
							pc.turnOnOffLight();
						}
					}
					break;

				case GLOWING_AURA: // グローウィング オーラ
					cha.addHitup(-5);
					cha.addBowHitup(-5);
					cha.addMr(-20);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SPMR(pc));
						pc.sendPackets(new S_PacketBoxIconAura(113, 0));
					}
					break;

				case SHINING_AURA: // シャイニング オーラ
					cha.addAc(8);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxIconAura(114, 0));
					}
					break;

				case BRAVE_AURA: // ブレイブ オーラ
					cha.addDmgup(-5);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxIconAura(116, 0));
					}
					break;
					
				case SHIELD: // シールド
					cha.addAc(2);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillIconShield(5, 0));
					}
					break;

				case BLIND_HIDING: // ブラインドハイディング
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.delBlindHiding();
					}
					break;

				case SHADOW_ARMOR: // シャドウ アーマー
					cha.addAc(3);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillIconShield(3, 0));
					}
					break;

				case DRESS_DEXTERITY: // ドレス デクスタリティー
					cha.addDex((byte) -2);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Dexup(pc, 2, 0));
					}
					break;

				case DRESS_MIGHTY:  // ドレス マイティー
					cha.addStr((byte) -2);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Strup(pc, 2, 0));
					}
					break;

				case SHADOW_FANG: // シャドウ ファング
					cha.addDmgup(-5);
					break;

				case ENCHANT_WEAPON: // エンチャント ウェポン
					cha.addDmgup(-2);
					break;

				case BLESSED_ARMOR: // ブレスド アーマー
					cha.addAc(3);
					break;

				case EARTH_BLESS: // アース ブレス
					cha.addAc(7);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillIconShield(7, 0));
					}
					break;

				case RESIST_MAGIC: // レジスト マジック
					cha.addMr(-10);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SPMR(pc));
					}
					break;

				case CLEAR_MIND: // クリアー マインド
					cha.addWis((byte) -3);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.resetBaseMr();
					}
					break;

				case RESIST_ELEMENTAL: // レジスト エレメント
					cha.addWind(-10);
					cha.addWater(-10);
					cha.addFire(-10);
					cha.addEarth(-10);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_OwnCharAttrDef(pc));
					}
					break;

				case ELEMENTAL_PROTECTION: // エレメンタルプロテクション
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						final int attr = pc.getElfAttr();
						if (attr == 1) {
							cha.addEarth(-50);
						} else if (attr == 2) {
							cha.addFire(-50);
						} else if (attr == 4) {
							cha.addWater(-50);
						} else if (attr == 8) {
							cha.addWind(-50);
						}
						pc.sendPackets(new S_OwnCharAttrDef(pc));
					}
					break;

				case WATER_LIFE: // 水之元氣
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxWaterLife());
					}
					break;

				/*case ELEMENTAL_FALL_DOWN: // エレメンタルフォールダウン
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						final int attr = pc.getAddAttrKind();
						final int i = 50;
						switch (attr) {
						case 1:
							pc.addEarth(i);
							break;
						case 2:
							pc.addFire(i);
							break;
						case 4:
							pc.addWater(i);
							break;
						case 8:
							pc.addWind(i);
							break;
						default:
							break;
						}
						pc.setAddAttrKind(0);
						pc.sendPackets(new S_OwnCharAttrDef(pc));
					} else if (cha instanceof L1NpcInstance) {
						final L1NpcInstance npc = (L1NpcInstance) cha;
						final int attr = npc.getAddAttrKind();
						final int i = 50;
						switch (attr) {
						case 1:
							npc.addEarth(i);
							break;
						case 2:
							npc.addFire(i);
							break;
						case 4:
							npc.addWater(i);
							break;
						case 8:
							npc.addWind(i);
							break;
						default:
							break;
						}
						npc.setAddAttrKind(0);
					}
					break;*/

				case IRON_SKIN: // アイアン スキン
					cha.addAc(10);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillIconShield(10, 0));
					}
					break;

				case EARTH_SKIN: // アース スキン
					cha.addAc(6);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillIconShield(6, 0));
					}
					break;

				case PHYSICAL_ENCHANT_STR: // フィジカル エンチャント：STR
					cha.addStr((byte) -5);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Strup(pc, 5, 0));
					}
					break;

				case PHYSICAL_ENCHANT_DEX: // フィジカル エンチャント：DEX
					cha.addDex((byte) -5);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Dexup(pc, 5, 0));
					}
					break;

				case FIRE_WEAPON: // ファイアー ウェポン
					cha.addDmgup(-4);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxIconAura(147, 0));
					}
					break;

				case FIRE_BLESS: // ファイアー ブレス
					cha.addDmgup(-4);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxIconAura(154, 0));
					}
					break;

				case BURNING_WEAPON: // バーニング ウェポン
					cha.addDmgup(-6);
					cha.addHitup(-3);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxIconAura(162, 0));
					}
					break;

				case BLESS_WEAPON: // ブレス ウェポン
					cha.addDmgup(-2);
					cha.addHitup(-2);
					cha.addBowHitup(-2);
					break;

				case WIND_SHOT: // ウィンド ショット
					cha.addBowHitup(-6);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxIconAura(148, 0));
					}
					break;

				case STORM_EYE: // ストーム アイ
					cha.addBowHitup(-2);
					cha.addBowDmgup(-3);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxIconAura(155, 0));
					}
					break;

				case STORM_SHOT: // ストーム ショット
					cha.addBowDmgup(-5);
					cha.addBowHitup(1);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxIconAura(165, 0));
					}
					break;

				case BERSERKERS: // バーサーカー
					cha.addAc(-10);
					cha.addDmgup(-5);
					cha.addHitup(-2);
					break;

				case SHAPE_CHANGE: // シェイプ チェンジ
					L1PolyMorph.undoPoly(cha);
					break;

				/*case ADVANCE_SPIRIT: // アドバンスド スピリッツ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMaxHp(-pc.getAdvenHp());
						pc.addMaxMp(-pc.getAdvenMp());
						pc.setAdvenHp(0);
						pc.setAdvenMp(0);
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
						if (pc.isInParty()) { // パーティー中
							pc.getParty().updateMiniHP(pc);
						}
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					}
					break;*/

				//case HASTE:
				case GREATER_HASTE:  // ヘイスト、グレーターヘイスト
					cha.setMoveSpeed(0);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
					}
					break;

				case HOLY_WALK:
				case MOVING_ACCELERATION:
				case WIND_WALK:
				//case BLOODLUST: // ホーリーウォーク、ムービングアクセレーション、ウィンドウォーク、ブラッドラスト
					cha.setBraveSpeed(0);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
					}
					break;

				case ILLUSION_OGRE: // イリュージョン：オーガ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(-4);
						pc.addHitup(-4);
					}
					break;

				case ILLUSION_LICH: // イリュージョン：リッチ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addSp(-2);
						pc.sendPackets(new S_SPMR(pc));
					}
					break;

				case ILLUSION_DIA_GOLEM: // イリュージョン：ダイアモンドゴーレム
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addAc(20);
					}
					break;

				/*case ILLUSION_AVATAR: // イリュージョン：アバター
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(-10);
					}
					break;*/

				case INSIGHT: // インサイト
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addStr((byte) -1);
						pc.addCon((byte) -1);
						pc.addDex((byte) -1);
						pc.addWis((byte) -1);
						pc.addInt((byte) -1);
					}
					break;

				/*case CURSE_BLIND:
				case DARKNESS:
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_CurseBlind(0));
					}
					break;*/

				/*case CURSE_PARALYZE: // カーズ パラライズ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_Poison(pc.getId(), 0));
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_PARALYSIS, false));
					}
					break;*/

				case WEAKNESS: // ウィークネス
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(5);
						pc.addHitup(1);
					}
					break;

				case DISEASE: // ディジーズ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(6);
						pc.addAc(-12);
					}
					break;

				case ICE_LANCE: // アイスランス
				case FREEZING_BLIZZARD: // フリージングブリザード
				case FREEZING_BREATH: // フリージングブレス
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_Poison(pc.getId(), 0));
						
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, false));
					} else if ((cha instanceof L1MonsterInstance)
							|| (cha instanceof L1SummonInstance)
							|| (cha instanceof L1PetInstance)) {
						final L1NpcInstance npc = (L1NpcInstance) cha;
						npc.broadcastPacketAll(new S_Poison(npc.getId(), 0));
						npc.setParalyzed(false);
					}
					break;

				case EARTH_BIND: // アースバインド
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_Poison(pc.getId(), 0));
						
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, false));
					} else if ((cha instanceof L1MonsterInstance)
							|| (cha instanceof L1SummonInstance)
							|| (cha instanceof L1PetInstance)) {
						final L1NpcInstance npc = (L1NpcInstance) cha;
						npc.broadcastPacketAll(new S_Poison(npc.getId(), 0));
						npc.setParalyzed(false);
					}
					break;

				/*case SHOCK_STUN: // ショック スタン
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_STUN, false));
					} else if ((cha instanceof L1MonsterInstance)
							|| (cha instanceof L1SummonInstance)
							|| (cha instanceof L1PetInstance)) {
						final L1NpcInstance npc = (L1NpcInstance) cha;
						npc.setParalyzed(false);
					}
					break;*/

				case FOG_OF_SLEEPING: // フォグ オブ スリーピング
					cha.setSleeped(false);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_SLEEP, false));
						pc.sendPackets(new S_OwnCharStatus(pc));
					}
					break;

				case ABSOLUTE_BARRIER: // アブソルート バリア
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.startHpRegeneration();
						pc.startMpRegeneration();
					}
					break;

				/*case WIND_SHACKLE: // ウィンド シャックル
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxWindShackle(pc.getId(), 0));
					}
					break;*/

				case SLOW:
				case ENTANGLE:
				case MASS_SLOW: // スロー、エンタングル、マススロー
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
					}
					cha.setMoveSpeed(0);
					break;

				/*case STATUS_FREEZE: // Freeze
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, false));
						
					} else if ((cha instanceof L1MonsterInstance)
							|| (cha instanceof L1SummonInstance)
							|| (cha instanceof L1PetInstance)) {
						final L1NpcInstance npc = (L1NpcInstance) cha;
						npc.setParalyzed(false);
					}
					break;*/

				case GUARD_BRAKE: // ガードブレイク
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addAc(-15);
					}
					break;

				case HORROR_OF_DEATH: // ホラーオブデス
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addStr(5);
						pc.addInt(5);
					}
					break;

				case STATUS_CUBE_IGNITION_TO_ALLY: // キューブ[イグニション]：味方
					cha.addFire(-30);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_OwnCharAttrDef(pc));
					}
					break;

				case STATUS_CUBE_QUAKE_TO_ALLY: // キューブ[クエイク]：味方
					cha.addEarth(-30);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_OwnCharAttrDef(pc));
					}
					break;

				case STATUS_CUBE_SHOCK_TO_ALLY: // キューブ[ショック]：味方
					cha.addWind(-30);
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_OwnCharAttrDef(pc));
					}
					break;

				case STATUS_CUBE_IGNITION_TO_ENEMY: // キューブ[イグニション]：敵
				case STATUS_CUBE_QUAKE_TO_ENEMY: // キューブ[クエイク]：敵
				case STATUS_CUBE_SHOCK_TO_ENEMY: // キューブ[ショック]：敵
				case STATUS_MR_REDUCTION_BY_CUBE_SHOCK: // キューブ[ショック]によるMR減少
				case STATUS_CUBE_BALANCE: // キューブ[バランス]
					break;

				case STATUS_BRAVE: // 勇敢藥水效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
					}
					cha.setBraveSpeed(0);
					break;

				case STATUS_BRAVE3: // 巧克力蛋糕
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_Liquor(pc.getId(), 0x00));
					}
					break;

				case EXP13: // 第一段經驗加倍效果
				case EXP15: // 第一段經驗加倍效果
				case EXP17: // 第一段經驗加倍效果
				case EXP20: // 第一段經驗加倍效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						// 2402 經驗直加倍效果消失！
						pc.sendPackets(new S_ServerMessage("經驗加倍效果消失！"));
						pc.sendPackets(new S_PacketBoxCooking(pc, 32, 0));
					}
					break;

				case SEXP13: // 第二段經驗加倍效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						// 3077 第二段經驗1.3倍效果結束。
						pc.sendPackets(new S_ServerMessage("第二段經驗1.3倍效果結束。"));
					}
					break;
					
				case SEXP15: // 第二段經驗加倍效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						// 3079 第二段經驗1.5倍效果結束。
						pc.sendPackets(new S_ServerMessage("第二段經驗1.5倍效果結束。"));
					}
					break;
					
				case SEXP17: // 第二段經驗加倍效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						// 3081 第二段經驗1.7倍效果結束。
						pc.sendPackets(new S_ServerMessage("第二段經驗1.7倍效果結束"));
					}
					break;
					
				case SEXP20: // 第二段經驗加倍效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						// 3075 第二段經驗雙倍效果結束。
						pc.sendPackets(new S_ServerMessage("第二段經驗雙倍效果結束。"));
					}
					break;
					
				case REEXP20: // 第三段經驗加倍效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						// 3073 第三段經驗雙倍效果結束。
						pc.sendPackets(new S_ServerMessage("第三段經驗雙倍效果結束。"));
					}
					break;

				case STATUS_ELFBRAVE: // 精靈餅乾效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_SkillBrave(pc.getId(), 0, 0));
					}
					cha.setBraveSpeed(0);
					break;

				case STATUS_RIBRAVE: // 生命之樹果實效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.setBraveSpeed(0);
						pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
						// XXX ユグドラの実のアイコンを消す方法が不明
						
					} else {
						cha.setBraveSpeed(0);
					}
					break;

				case STATUS_HASTE: // 加速藥水效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPacketsAll(new S_SkillHaste(pc.getId(), 0, 0));
					}
					cha.setMoveSpeed(0);
					break;

				case STATUS_BLUE_POTION: // 魔力回復藥水效果
					break;

				case STATUS_UNDERWATER_BREATH: // 伊娃的祝福藥水效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), 0));
					}
					break;

				case STATUS_WISDOM_POTION: // 慎重藥水效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						cha.addSp(-2);
						pc.sendPackets(new S_PacketBoxWisdomPotion(0));
					}
					break;

				case STATUS_CHAT_PROHIBITED: // 禁言效果
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_ServerMessage(288)); // チャットができるようになりました。
					}
					break;

				case STATUS_POISON: // 毒素效果
					cha.curePoison();
					break;

				case COOKING_1_0_N:
				case COOKING_1_0_S: // フローティングアイステーキ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addWind(-10);
						pc.addWater(-10);
						pc.addFire(-10);
						pc.addEarth(-10);
						pc.sendPackets(new S_OwnCharAttrDef(pc));
						pc.sendPackets(new S_PacketBoxCooking(pc, 0, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_1_1_N:
				case COOKING_1_1_S: // ベアーステーキ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMaxHp(-30);
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
						if (pc.isInParty()) { // パーティー中
							pc.getParty().updateMiniHP(pc);
						}
						pc.sendPackets(new S_PacketBoxCooking(pc, 1, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_1_2_N:
				case COOKING_1_2_S: // ナッツ餅
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 2, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_1_3_N:
				case COOKING_1_3_S: // 蟻脚のチーズ焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addAc(1);
						pc.sendPackets(new S_PacketBoxCooking(pc, 3, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_1_4_N:
				case COOKING_1_4_S: // フルーツサラダ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMaxMp(-20);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
						pc.sendPackets(new S_PacketBoxCooking(pc, 4, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_1_5_N:
				case COOKING_1_5_S: // フルーツ甘酢あんかけ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 5, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_1_6_N:
				case COOKING_1_6_S: // 猪肉の串焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMr(-5);
						pc.sendPackets(new S_SPMR(pc));
						pc.sendPackets(new S_PacketBoxCooking(pc, 6, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_1_7_N:
				case COOKING_1_7_S: // キノコスープ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 7, 0));
						pc.setDessertId(0);
					}
					break;

				case COOKING_2_0_N:
				case COOKING_2_0_S: // キャビアカナッペ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 8, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_2_1_N:
				case COOKING_2_1_S: // アリゲーターステーキ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMaxHp(-30);
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
						if (pc.isInParty()) { // パーティー中
							pc.getParty().updateMiniHP(pc);
						}
						pc.addMaxMp(-30);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
						pc.sendPackets(new S_PacketBoxCooking(pc, 9, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_2_2_N:
				case COOKING_2_2_S: // タートルドラゴンの菓子
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addAc(2);
						pc.sendPackets(new S_PacketBoxCooking(pc, 10, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_2_3_N:
				case COOKING_2_3_S: // キウィパロット焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 11, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_2_4_N:
				case COOKING_2_4_S: // スコーピオン焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 12, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_2_5_N:
				case COOKING_2_5_S: // イレッカドムシチュー
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMr(-10);
						pc.sendPackets(new S_SPMR(pc));
						pc.sendPackets(new S_PacketBoxCooking(pc, 13, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_2_6_N:
				case COOKING_2_6_S: // クモ脚の串焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addSp(-1);
						pc.sendPackets(new S_SPMR(pc));
						pc.sendPackets(new S_PacketBoxCooking(pc, 14, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_2_7_N:
				case COOKING_2_7_S: // クラブスープ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 15, 0));
						pc.setDessertId(0);
					}
					break;

				case COOKING_3_0_N:
				case COOKING_3_0_S: // クラスタシアンのハサミ焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 16, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_3_1_N:
				case COOKING_3_1_S:  // グリフォン焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMaxHp(-50);
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
						if (pc.isInParty()) { // パーティー中
							pc.getParty().updateMiniHP(pc);
						}
						pc.addMaxMp(-50);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
						pc.sendPackets(new S_PacketBoxCooking(pc, 17, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_3_2_N:
				case COOKING_3_2_S: // コカトリスステーキ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 18, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_3_3_N:
				case COOKING_3_3_S: // タートルドラゴン焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addAc(3);
						pc.sendPackets(new S_PacketBoxCooking(pc, 19, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_3_4_N:
				case COOKING_3_4_S:  // レッサードラゴンの手羽先
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMr(-15);
						pc.sendPackets(new S_SPMR(pc));
						pc.addWind(-10);
						pc.addWater(-10);
						pc.addFire(-10);
						pc.addEarth(-10);
						pc.sendPackets(new S_OwnCharAttrDef(pc));
						pc.sendPackets(new S_PacketBoxCooking(pc, 20, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_3_5_N:
				case COOKING_3_5_S: // ドレイク焼き
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addSp(-2);
						pc.sendPackets(new S_SPMR(pc));
						pc.sendPackets(new S_PacketBoxCooking(pc, 21, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_3_6_N:
				case COOKING_3_6_S: // 深海魚のシチュー
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.addMaxHp(-30);
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
						if (pc.isInParty()) { // パーティー中
							pc.getParty().updateMiniHP(pc);
						}
						pc.sendPackets(new S_PacketBoxCooking(pc, 22, 0));
						pc.setCookingId(0);
					}
					break;

				case COOKING_3_7_N:
				case COOKING_3_7_S: // バシリスクの卵スープ
					if (cha instanceof L1PcInstance) {
						final L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_PacketBoxCooking(pc, 23, 0));
						pc.setDessertId(0);
					}
					break;
				}
			}
			//cha.removeSkillEffect(skillId);
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
		
		if (cha instanceof L1PcInstance) {
			final L1PcInstance pc = (L1PcInstance) cha;
			sendStopMessage(pc, skillId);
			pc.sendPackets(new S_OwnCharStatus(pc));
		}
	}

	// メッセージの表示（終了するとき）
	private static void sendStopMessage(final L1PcInstance charaPc, final int skillid) {
		final L1Skills l1skills = SkillsTable.get().getTemplate(skillid);
		if ((l1skills == null) || (charaPc == null)) {
			return;
		}

		final int msgID = l1skills.getSysmsgIdStop();
		if (msgID > 0) {
			charaPc.sendPackets(new S_ServerMessage(msgID));
		}
	}
}

