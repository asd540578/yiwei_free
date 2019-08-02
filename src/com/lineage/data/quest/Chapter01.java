package com.lineage.data.quest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lineage.data.executor.QuestExecutor;
import com.lineage.server.model.Instance.L1PcInstance;
import com.lineage.server.serverpackets.S_NPCTalkReturn;
import com.lineage.server.serverpackets.S_ServerMessage;
import com.lineage.server.templates.L1Quest;

/**
 * 說明:魔法師．哈汀(故事)
 * @author daien
 *
 */
public class Chapter01 extends QuestExecutor {

	private static final Log _log = LogFactory.getLog(Chapter01.class);

	/**
	 * 任務資料
	 */
	public static L1Quest QUEST;

	/**
	 * 從前的說話之島
	 */
	public static final int MAPID = 9000;

	/**
	 * 任務資料說明HTML
	 */
	private static final String _html = "q_cha1_1";
/*
<body>
<img src="#1210"></img> <font fg=66ff66>步驟.1　象牙塔的秘密研究室</font><BR>
請先參照Chapter.0：穿越時空的探險，抵達象牙塔的秘密研究室。<BR>
跟<font fg=0000ff>尤基</font>交談說想要回到從前的說話之島探討哈汀的秘密。<BR>
尤基 會要求玩家湊齊<font fg=0000ff>5~8個人</font>才能前往從前的說話之島。<BR>
之後他會幫玩家傳送到從前的秘密研究室。<BR>
<BR>
注意事項：<BR>
尤基 ：傳送到任務副本。<BR>
輔助研究員：傳送到說話之島。<BR>
紙人：販售消耗品。<BR>
<BR>
任務目標：<BR>
湊齊5~8人組隊和尤基 交談傳送到從前的秘密研究室<BR>
<BR>
<img src="#1210"></img> <font fg=66ff66>步驟.2　從前的秘密研究室</font><BR>
隨送到從前的秘密研究室，會先碰到哈汀。<BR>
並且隨著哈汀的說明之後會，按<font fg=0000ff>「alt + 2」</font>告別哈汀會出現要傳送到下一個任務地點的魔法陣。<BR>
隊長：傳送到從前的說話之島<BR>
隊友：傳送到從前的說話之島洞窟2樓<BR>
<BR>
注意事項：<BR>
隊長是扮演歐林<BR>
隊友則是扮演其他四色成員<BR>
<BR>
任務目標：<BR>
和哈汀進行互動之後，隊長和隊員各自傳送到下一個目的地<BR>
<BR>
<img src="#1210"></img> <font fg=66ff66>步驟.3　隊長篇</font><BR>
<BR>
隊長會被傳送到從前的說話之島，並且遇到<font fg=0000ff>賽尼斯</font>進行互動。<BR>
賽尼斯會要求玩家在他準備魔法的期間，幫他除掉身邊出現的怪物。<BR>
完成之後會傳送到惡魔召喚的房間和隊友們會合進行決戰。<BR>
<BR>
注意事項：<BR>
關於NPC的問答：<BR>
<font fg=0000ff>「alt + 2」</font>表示要<BR>
<font fg=0000ff>「alt + 4」</font>表示不要<BR>
該處為結局分歧點之一，玩家的抉擇會影響到結局的因素之一<BR>
分歧說明請參考步驟5<BR>
<BR>
任務目標：<BR>
守護賽尼斯完成魔法，之後會傳送到隊員身邊會合<BR>
<BR>
<img src="#1210"></img> <font fg=66ff66>步驟.4　隊友篇</font><BR>
<BR>
除了隊長，其他成員會被傳送到從前的說話之島洞窟2樓執行任務。<BR>
<BR>
從起點一路進行下去會遇到4階段的關卡。<BR>
關卡1~關卡3都是要玩家們將關卡內的<font fg=0000ff>四個光球地板</font>，同時有玩家站在上面，即可開啟通往下一關卡的門。<BR>
關卡4則是要將房間裡的<font fg=0000ff>怪物全數清除</font>，即可開啟通往最終關卡的門。<BR>
<BR>
注意事項：<BR>
關卡1~關卡4的完成方式，為結局分歧點之一<BR>
分歧說明請參考步驟5<BR>
如果在限定時間內無法完成關卡1~關卡4，則副本宣告失敗，全員傳送回說話之島<BR>
※地圖上5個特殊地點是有機會在地上撿到哈汀日記的場所<BR>
<BR>
任務目標：<BR>
在限定時間內完成關卡1~關卡4，來到最終關卡的房間<BR>
<BR>
<img src="#1210"></img> <font fg=66ff66>步驟.5　決戰篇</font><BR>
<BR>
來到最終關卡之後，隊長會被傳送過來和隊友們會合。<BR>
在最終關卡玩家們需要合力清除12回合出現的怪物們。<BR>
以下是各種分歧條件與結局：<BR>
<BR>
<font fg=ffff00>【分歧0】最終頭目-鐮刀死神的使者(秘譚)</font><BR>
達成條件：最終關卡時，各階段出現的怪物都殘留4隻以上<BR>
條件達成：哈汀會說出「邪惡氣息又擁擠過來了！請準備！」<BR>
<BR>
<font fg=ffff00>【分歧1】最終頭目-巴風特(秘譚)</font><BR>
達成條件：最終關卡1~11回合，迅速擊敗所有怪物<BR>
基本上要在哈汀說話之前(兩分鐘內)，至少怪物要清到剩3隻以下<BR>
條件達成：每回合兩分鐘內清掉怪物，並且哈汀沒有說出【分歧0】那句話<BR>
<BR>
<font fg=ffff00>【分歧2】賽尼斯(秘譚)不會出現</font><BR>
達成條件：隊長在「從前的說話之島」和賽尼斯在說話時，都不理會<BR>
直到賽尼斯說出「【歐林】，是在渺視我嗎?」，這時會按「alt + 2」回應她<BR>
條件達成：在最終關卡清怪慢【分歧0】，清怪快【分歧1】，但是賽尼斯(秘譚)不會出現<BR>
<BR>
<font fg=ffff00>【分歧3】最終頭目-鐮刀死神的使者(秘譚)、火焰之影(NPC)出現</font><BR>
達成條件：再進行關卡1~關卡3的時候，要踏上紅光球位置時，先踏上面三個<BR>
直到賽尼斯或哈汀說出「笨」、「慢」、「耐心」、「快一點」...等字眼，這時再踏上最後一個<BR>
之後趕進前往下一個關卡，再如法炮製通過關卡1~關卡3<BR>
最終關卡達成【分歧0】的條件<BR>
條件達成：再擊敗鐮刀死神的使者(秘譚)、賽尼斯(秘譚)之後，NPC火焰之影會出現<BR>
由於通關後，如果太慢通過右下角已打開的黑牆，會拿不到獎勵<BR>
因此建議先通過後再隔牆看NPC火焰之影的說明(如果你有興趣的話)<BR>
<BR>
<font fg=ffff00>【分歧4】最終頭目-巴風特(秘譚)、戰鬥結束NPC火焰之影出現</font><BR>
達成條件：再進行關卡1~關卡3的時候，要踏上紅光球位置時，先踏上面三個<BR>
直到賽尼斯或哈汀說出「笨」、「慢」、「耐心」、「快一點」...等字眼，這時再踏上最後一個<BR>
之後趕進前往下一個關卡，再如法炮製通過關卡1~關卡3<BR>
最終關卡達成【分歧1】的條件<BR>
條件達成：再擊敗巴風特(秘譚)、賽尼斯(秘譚)之後，NPC火焰之影會出現<BR>
由於通關後，如果太慢通過右下角已打開的黑牆，會拿不到獎勵<BR>
因此建議先通過後再隔牆看NPC火焰之影的說明(如果你有興趣的話)<BR>
<BR>
<font fg=ffff00>【分歧5】最終關卡怪物強度提升、戰鬥結束NPC火焰之影出現</font><BR>
達成條件：除了達成【分歧3】或【分歧4】的條件<BR>
在進行關卡4骷髏房時也等到賽尼斯或哈汀說出「笨」、「慢」、「耐心」、「快一點」...等字眼，這時在清掉怪物<BR>
條件達成：除了【分歧3】或【分歧4】的情況出現，最終關卡的怪物強度會提升<BR>
<BR>
<font fg=ffff00>【分歧6】NPC炎魔現身、最終頭目-鐮刀死神的使者(秘譚)、黑翼賽尼斯(秘譚)</font><BR>
達成條件：隊長要達成【分歧2】讓賽尼斯(秘譚)不出現<BR>
同時隊友要達成【分歧0】和【分歧3】讓NPC火焰之影現身、最終頭目-鐮刀死神的使者(秘譚)<BR>
條件達成：在最終關卡時，NPC炎魔現身的時候，黑翼賽尼斯(秘譚)也會跟著現身<BR>
<BR>
<font fg=ffff00>【分歧7】NPC炎魔現身、最終頭目-巴風特(秘譚)、黑翼賽尼斯(秘譚)</font><BR>
達成條件：隊長要達成【分歧2】讓賽尼斯(秘譚)不出現<BR>
同時隊友要達成【分歧1】和【分歧3】讓NPC火焰之影現身、最終頭目-巴風特(秘譚)<BR>
條件達成：在最終關卡時，NPC炎魔現身的時候，黑翼賽尼斯(秘譚)也會跟著現身<BR>
<BR>
<font fg=ffff00>【分歧8】大量葛林(秘譚)現身</font><BR>
達成條件：未知<BR>
條件達成：副本進行途中出現大量的葛林(秘譚)<BR>
最後副本宣告失敗，全員傳送回說話之島<BR>
<BR>
注意事項：<BR>
副本失敗條件：<BR>
<font fg=ffff00>1. 如果隊長斷線(隊長死亡，副本照樣進行)<BR>
2. 隊友闖關超過時間限制<BR>
3. 隊友死亡或斷線，造成在關卡內人數低於4人時(因為啟動機關需要至少4人)<BR>
4. 在最終關卡各階段怪物2分鐘內剩下4隻以上，並且出現「邪惡氣息又擁擠過來了！請準備！」<BR>
如果過了1分鐘後還剩下4隻以上<BR>
會出現「就算是因為照這樣做而任務失敗的話，別氣餒！讓我給你打打氣吧！」<BR>
之後如果還是來不及清怪，並出現「高估、逃跑、逃走」之類的字眼，則表示失敗</font><BR>
<BR>
任務目標：<BR>
進行最終關卡，連續12回合成功擊敗怪物<BR>
<BR>
相關怪物：<BR>
   <font fg=ffff00>Lv.4　	 葛林(秘譚1)</font><BR>
   <font fg=ffff00>Lv.45　	 鐮刀死神的使者(秘譚1)</font><BR>
   <font fg=ffff00>Lv.52　	 巴風特(秘譚1)</font><BR>
   <font fg=ffff00>Lv.64　	 黑翼賽尼斯(秘譚1)</font><BR>
   <font fg=ffff00>Lv.64　	 賽尼斯(秘譚1)</font><BR>
<BR>
<img src="#1210"></img> <font fg=66ff66>步驟.6　盡速離開現場</font><BR>
闖關完成後，最終關卡右下角的黑色牆壁會開啟<BR>
沿著這邊往右上走，來到一個地上有紅色光圈的房間<BR>
當每個玩家都站上紅色光圈後，中央會出現黑色光圈<BR>
這時踏上黑色光圈就會出現哈汀的秘密袋子，之後就把袋子撿起來<BR>
再來就直離開副本，回到說話之島吧！<BR>
<BR>
注意事項：<BR>
因此牆壁一開啟就要馬上離開前去領獎房間，否則牆壁很快就會再關閉<BR>
被關在最終關卡的房間，將會無法獲得獎勵<BR>
<BR>
任務目標：<BR>
前往領獎房間領取獎勵，離開副本<BR>
<BR>
相關物品：<BR>
   <font fg=ffff00> 哈汀的秘密袋子 x 1</font><BR>
<BR>
<br>
<img src="#331" action="index">
</body>
*/
	private Chapter01() {
		// TODO Auto-generated constructor stub
	}

	public static QuestExecutor get() {
		return new Chapter01();
	}
	
	@Override
	public void execute(L1Quest quest) {
		try {
			// 設置任務
			QUEST = quest;

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
			
		} finally {
			//_log.info("任務啟用:" + QUEST.get_note());
		}
	}

	@Override
	public void startQuest(L1PcInstance pc) {
		try {
			// 判斷職業
			if (QUEST.check(pc)) {
				// 判斷等級
				if (pc.getLevel() >= QUEST.get_questlevel()) {
					
				} else {
					// 該等級 無法執行此任務
					pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_not1"));
				}
				
			} else {
				// 該職業無法執行此任務
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "y_q_not2"));
			}

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void endQuest(L1PcInstance pc) {
		try {
			final String questName = QUEST.get_questname();
			pc.sendPackets(new S_ServerMessage("\\fT" + questName + "任務結束！"));

		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void showQuest(L1PcInstance pc) {
		try {
			// 展示任務說明
			if (_html != null) {
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), _html));
			}
			
		} catch (final Exception e) {
			_log.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void stopQuest(L1PcInstance pc) {
		// TODO Auto-generated method stub
		
	}
}
