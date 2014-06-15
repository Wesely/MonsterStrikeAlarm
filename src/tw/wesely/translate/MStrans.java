package tw.wesely.translate;

import java.io.IOException;

public class MStrans {
	public static String getTranslated(String orgText) throws IOException {
		String translated = orgText;
		translated = translated.replace("仲間を弾いて敵へ撃ちこむ", "將隊友彈出以攻擊敵人（被彈出者無視毒牆）");
		translated = translated.replace("仲間にふれると", "撞擊到隊友的話");
		translated = translated.replace("自分を中心に無属性の", "以自己為中心無屬性的");
		translated = translated.replace("敵にバウンドする毎に", "每次撞到敵人或是邊線時");
		translated = translated.replace("ふれた最初の敵を乱打し", "對第一個接觸到的敵人進行連擊");
		translated = translated.replace("3発の無属性衝撃波で攻撃", "以三發無屬性的衝擊波攻擊");
		translated = translated.replace("ふれた仲間を一定期間", "接觸到的隊友在一定期間");
		translated = translated.replace("ふれた最初の敵を乱打し", "對接觸到的第一個敵方進行連擊");
		translated = translated.replace("敵の弱点にヒットした際に超ダメージを与える",
				"擊中敵人弱點時給予強大傷害");
		translated = translated.replace("天下一のカリスマで、仲間を導いて敵へ撃ちこむ",
				"以天下第一的魅力，引導隊友擊向敵人");
		translated = translated.replace("合体した闇武で、味方のHPが少ないほど強力な連撃を放つ",
				"以合體後的闇武，我方HP越低將釋放越強力的連擊");
		translated = translated.replace("水の精霊の力で、ヒットしたボスの弱点を全て出現させる",
				"以水精靈之力，將其擊中的頭目之所有弱點全部顯現");
		translated = translated.replace("自身のスピードとパワーが大アップ＆ビットンに大ダメージ",
				"將自身速度和力量大大提升，並能對比特盾造成強大傷害");
		translated = translated.replace("レーザーで追い討ち", "並以雷射追擊");
		translated = translated.replace("アップ", "提昇");
		translated = translated.replace("パワー", "力量");
		translated = translated.replace("ターン数", " CD");
		translated = translated.replace("スピード", "速度");
		translated = translated.replace("ストライクショット", "StrikeShot主動技");
		translated = translated.replace("友情コンボ", "友情技");
		translated = translated.replace("ステータス", "基本資料");
		translated = translated.replace("アビリティ", "特殊能力");
		translated = translated.replace("アンチダメージウォール ", "毒牆免疫");
		translated = translated.replace("アンチ重力バリア", "重力場免疫");
		translated = translated.replace("クロスレーザー", "X字雷射");
		translated = translated.replace("レーザー", "雷射");
		translated = translated.replace("ホーミング", "追蹤彈");
		translated = translated.replace("キラー", "殺手");
		translated = translated.replace("拡散弾", "擴散彈");
		translated = translated.replace("シールドブレーカー", "破盾");
		translated = translated.replace("アンチダメージウォール", "防傷害牆壁");
		translated = translated.replace("アンチワープ", "無視黑洞");
		translated = translated.replace("魔王", "魔王");
		translated = translated.replace("神", "神");
		translated = translated.replace("魔封じ", "對魔族﹑魔人族傷害提升");
		translated = translated.replace("ロボット", "機械");
		translated = translated.replace("獣", "獸");
		translated = translated.replace("ヒューマン", "人類");
		translated = translated.replace("鉱物", "礦物");
		translated = translated.replace("ドラゴン", "龍族");
		translated = translated.replace("妖精", "妖精");
		translated = translated.replace("ユニバース", "宇宙族");
		translated = translated.replace("幻獣", "幻獸");
		translated = translated.replace("ゲージ", "蓄力攻擊");
		translated = translated.replace("アクシス", "軸心國");
		translated = translated.replace("ユニオン", "同盟國");
		translated = translated.replace("キラー", "殺手");
		translated = translated.replace("サムライ", "武士");
		translated = translated.replace("バランス", "均衡");

		return translated;
	}
}
