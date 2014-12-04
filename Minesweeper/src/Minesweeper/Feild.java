package Minesweeper;

public class Feild {
	int width,height;//���ƍ���
	boolean GAME = true;//GAME�����s���Ă�����
	protected Block[][] B;
	int Level;
	int BomNum;//the number of Boms
	int FlagNum;//the number of Flags

	//�R���X�g���N�^
	public Feild(int Level){
		//Level�ɂ���ē�Փx��ς���
		this.Level = Level;
		switch(Level){
		case 0:
			this.width = 9;
			this.height = 9;
			this.BomNum = 10;
			break;
		case 1:
			this.width = 16;
			this.height = 16;
			this.BomNum = 40;
			break;
		case 2:
			this.width = 16;
			this.height = 30;
			this.BomNum = 99;
			break;
		default:
			this.width = 9;
			this.height = 9;
			this.BomNum = 10;
			break;
		}
		//Block
		B = new Block[width][height];
		this.FlagNum = this.BomNum;
	}
	public Feild(int width,int height,int BomNum){
		this.width = height;
		this.height = width;
		this.BomNum = BomNum;
		B = new Block[width][height];
		this.FlagNum = this.BomNum;
	}

	//�ĊJ
	public void Start(){
		GAME = true;
		int count = 0;
		for(int i = 0;i < width; i++){
			for(int j = 0; j < height ;j++){
				B[i][j] = new Block(false);
				B[i][j].SetPressed(false);
			}
		}
		int i = 0;
		int j = 0;
		while(true){
			double ran = Math.random();
			if(ran <= 0.95||B[i][j].isBom()){
			}else{
				B[i][j].SetBom();
				count++;
				if(count == BomNum) break;
			}
			j++;
			if(j == height){
				i++;
				j = 0;
				if(i == width)
					i = 0;
			}
		}
	}

	//����ݒ�
	public void SetBlock(int x,int y){
		int count = 0;
		for(int i = 0;i < width; i++){
			for(int j = 0; j < height ;j++){
				B[i][j] = new Block(false);
			}
		}

		//���e�Z�b�g
		int i = 0;
		int j = 0;
		while(true){
			double ran = Math.random();
			if(ran <= 0.95||BlockCCC(i,j,x,y)||B[i][j].isBom()){
			}else{
				B[i][j].SetBom();
				count++;
				if(count == BomNum) break;
			}
			j++;
			if(j == height){
				i++;
				j = 0;
				if(i == width)
					i = 0;
			}
		}
	}
	private boolean BlockCCC(int i,int j,int x,int y){
		for(int k = x-1;k <= x+1;k++){
			for(int h = y - 1; h <= y + 1; h++){
				if(k == i&&h == j) return true;
			}
		}
		return false;
		
	}
	//���N���b�N���ꂽ���̏���
	public void ClickBlock(int x,int y){
		//��̎�
		if(B[x][y].isFlag()){

		}
		//���e�̎�
		else if(B[x][y].isBom()){
			this.SetGame(false);
		}
		else{
			B[x][y].SetPressed(true);
			//���̔��e�̐����Z�b�g
			this.SetNumBlock(x, y);
			//���̔��e�̐����Z�b�g
			if(SearchBlock(x,y)==0){
				SpreadBlock(x,y);
			}
		}

	}

	public String CheckBlock(int x,int y){
		if(B[x][y].isFlag()) return "F";
		toString();
		String s = String.valueOf(B[x][y].GetNum());
		if(B[x][y].GetNum() == -1) return "";
		else return s;
	}
	public void PushFlag(int x,int y){
		if(B[x][y].isFlag())
			B[x][y].SetFlag(false);
		else 
			B[x][y].SetFlag(true);
	}
	public boolean isFlag(int x,int y){
		return B[x][y].isFlag();
	}
	public boolean isBom(int x,int y){
		return B[x][y].isBom();
	}
	private void SetNumBlock(int x,int y){
		B[x][y].SetNum(SearchBlock(x,y));
	}
	private int SearchBlock(int x,int y){
		int i,j;
		int count = 0;
		for(i = x - 1; i <= x + 1; i++){
			for(j = y - 1; j <= y + 1; j++){
				if(!((i == x&&j == y)||i < 0||i>= width||j < 0||j >= height)){
					if(B[i][j].isBom()) count++;
				}
			}
		}
		return count;

	}
	private void SpreadBlock(int x,int y){
		int i,j;
		for(i = x - 1; i <= x + 1; i++){
			for(j = y - 1; j <= y + 1; j++){
				if(!((i == x&&j == y)||i < 0||i>= width||j < 0||j >= height)){
					this.SetNumBlock(i, j);
					if(SearchBlock(i,j)==0&&!B[i][j].isPressed()){
						this.ClickBlock(i, j);
					}
					B[i][j].SetPressed(true);
				}
			}
		}
	}
	public boolean isClear(){
		boolean t = true;
		for(int i = 0;i < width; i++){
			for(int j = 0; j < height ;j++){
				if(!B[i][j].isPressed()){
					if(!B[i][j].isBom()){
						t = false;
					}
				}
			}
		}return t;
	}
	public void SpreadBlock2(int x,int y){
		int i,j;
		for(i = x - 1; i <= x + 1; i++){
			for(j = y - 1; j <= y + 1; j++){
				if(!((i == x&&j == y)||i < 0||i>= width||j < 0||j >= height)){
					this.SetNumBlock(i, j);
					B[i][j].SetPressed(true);
					if(SearchBlock(i,j)==0&&!B[i][j].isPressed()){
						SpreadBlock(i,j);
					}
				}
			}
		}
	}
	public void AroundBlock(int x,int y){
		int i,j;
		int count = 0;
		for(i = x - 1; i <= x + 1; i++){
			for(j = y - 1; j <= y + 1; j++){
				if(!((i == x&&j == y)||i < 0||i>= width||j < 0||j >= height)){
					if(B[i][j].isFlag()){
						count++;
					}
				}
			}
		}if(count == SearchBlock(x,y)){
			for(i = x - 1; i <= x + 1; i++){
				for(j = y - 1; j <= y + 1; j++){
					if(!((i == x&&j == y)||i < 0||i>= width||j < 0||j >= height)){
						if(!B[i][j].isPressed())
							this.ClickBlock(i, j);
					}
				}
			}
		}
	}
	public void UPFlag(){
		this.FlagNum++;
	}
	public void DownFlag(){
		this.FlagNum--;
	}
	public int GetFlagNum(){
		return this.FlagNum;
	}

	public void SetGame(boolean t){
		this.GAME = t;
	}
	public boolean isGame(){
		return this.GAME;
	}

}
