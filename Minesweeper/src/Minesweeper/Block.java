package Minesweeper;

public class Block {
	private boolean Bom;
	private int BomNumber;
	private boolean Flag;
	//private boolean help;
	private boolean Pressed;
	
	public Block(boolean Bom){
		this.Bom = Bom;
		this.Flag = false;
		//this.help = false;
		this.Pressed = false;
		this.BomNumber = -1;
	}
	public void SetBom(){
		this.Bom = true;
	}
	public void SetNum(int Num){
		this.BomNumber = Num;
	}
	public void SetFlag(boolean a){
		this.Flag = a;
	}
	public void SetPressed(boolean a){
		this.Pressed = a;
	}
	public int GetNum(){
		return this.BomNumber;
	}
	public boolean isFlag(){
		return this.Flag;
	}
	public boolean isBom(){
		return this.Bom;
	}
	public boolean isPressed(){
		return this.Pressed;
	}
	

}
