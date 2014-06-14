package tw.wesely.translate;

public class MStrans {


	public String getTranslated(String orgText) {
		String translated = orgText;
		translated = translated.replace("ターン数", " CD");
		translated = translated.replace("スピード", "速度");
		translated = translated.replace("ストライクショット", "StrikeShot主動技");
		translated = translated.replace("友情コンボ", "友情技");
		translated = translated.replace("ステータス", "基本資料");
		translated = translated.replace("アビリティ", "特殊能力");
		translated = translated.replace("アンチダメージウォール ", "毒牆免疫");
		translated = translated.replace("アンチ重力バリア", "重力盾免疫");
		translated = translated.replace("クロスレーザー", "X字雷射");
		translated = translated.replace("レーザー", "雷射");
		translated = translated.replace("ホーミング", "追蹤彈");
		translated = translated.replace("キラー", "殺手");
		translated = translated.replace("拡散弾", "擴散彈");

		return translated;
	}
}
