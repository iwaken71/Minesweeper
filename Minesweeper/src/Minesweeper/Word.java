package Minesweeper;

public class Word {
	int Language;
	private boolean Japanese;

	String[] language = new String[2];
	String[][] word = new String[2][];

	Word(){
		language[0] = "Japanese";
		language[1] = "English";
		Japanese = false;
	}
	public void SetJapan(boolean a){
		this.Japanese = a;
	}
	public boolean GetJapan(){
		return this.Japanese;
	}
	public String toJ(String s){
		if(!Japanese) return s;
		switch(s){
		case "Japanese":
			return "日本語";
		case "Easy":
			return "初級";
		case "Medium":
			return "中級";
		case "Hard":
			return "上級";
		case "Menu":
			return "メニュー";
		case "Exit":
			return "終了";
		case "s":
			return "秒";
		case "Minesweeper":
			return "マインスイーパー";
		case "Best Time:":
			return "最高記録";
		case "English":
			return "英語";
		case "Languages":
			return "言語";
		case "GAMEOVER":
			return "ゲームオーバー";
		case "Clear!":
			return "クリア";
		case "New":
			return "新規";
		case "min":
			return "分";
			
			
		default:
			break;
		}
		return s;



	}
}
