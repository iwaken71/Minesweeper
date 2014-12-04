package Minesweeper;
import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;

public class GameGUI extends JFrame implements ActionListener,MouseListener{
	private static final long serialVersionUID = 1L;//意味なし

	final static int CELL_SIZE = 48; //一つのマスの大きさ

	private JPanel Panel; //全体パネル
	private JPanel ButtonPanel; //ボタンパネル
	private JPanel SidePanel[]; //オプションパネル
	private JButton Button[][]; 
	private JLabel label[][];
	private JLabel display[];
	private Feild F;
	private boolean FIRST;
	long start,stop;
	ImageIcon FlagIcon,BomIcon;
	private double time;
	private int DISPLAYTIME;
	private int REGTIME=99999;
	ClassLoader cl;
	boolean STOP = false;
	String  LEVEL;
	Word W1;

	//コンストラクタ
	GameGUI(int Level,boolean Japanese){
		W1 = new Word();
		F = new Feild(Level);
		W1.SetJapan(Japanese);

		switch(Level){
		case 0:
			LEVEL = W1.toJ("Easy");break;
		case 1:
			LEVEL =  W1.toJ("Medium");break;
		case 2:
			LEVEL =  W1.toJ("Hard");break;
		default:
			break;

		}
		this.setTitle(LEVEL);

		FIRST = true;
		Button = new JButton[F.width][F.height];
		label = new JLabel[F.width][F.height];

		display = new JLabel[4];
		toString();
		display[0] = new JLabel(String.valueOf(F.GetFlagNum()));
		display[1] = new JLabel();
		display[2] = new JLabel();
		display[3] = new JLabel();
		for(int i = 0;i < F.width; i++){
			for(int j = 0;j < F.height; j++){
				label[i][j] = new JLabel();
				Button[i][j] = new JButton("");
				Button[i][j].add(label[i][j]);
				Button[i][j].addActionListener(this);
				Button[i][j].addMouseListener(new myListener());
				Button[i][j].setBackground(Color.lightGray);
			}
		}
		cl = this.getClass().getClassLoader();
		FlagIcon = new ImageIcon(cl.getResource("Flag.png")); 
		BomIcon = new ImageIcon(cl.getResource("Bom.png")); 

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(CELL_SIZE*F.width+50, CELL_SIZE*F.height+150);
		if(Level == 2)
			this.setSize(CELL_SIZE*F.height+50, CELL_SIZE*F.height+150);
		this.setLocationRelativeTo(null);
		Panel = new JPanel();
		ButtonPanel = new JPanel(new GridLayout(F.width,F.height));
		SidePanel = new JPanel[4];
		for(int i = 0; i < 4; i++){
			SidePanel[i] = new JPanel();
			SidePanel[i].setPreferredSize(new Dimension(100,25));
			SidePanel[i].setBackground(Color.YELLOW);
			getContentPane().add(SidePanel[i]);
			SidePanel[i].add(display[i]);
			Panel.add(SidePanel[i],BorderLayout. SOUTH);
		}
		SidePanel[0].setPreferredSize(new Dimension(50,25));
		SidePanel[3].setPreferredSize(new Dimension(150,25));
		Panel.add(SidePanel[3],BorderLayout. SOUTH);
		if(Level == 2)
			ButtonPanel.setPreferredSize(new Dimension(F.height*CELL_SIZE, F.width*CELL_SIZE));
		else
			ButtonPanel.setPreferredSize(new Dimension(F.width*CELL_SIZE, F.height*CELL_SIZE));
		getContentPane().add(ButtonPanel);
		for(int i = 0;i < F.width; i++){
			for(int j = 0;j < F.height; j++){
				ButtonPanel.add(Button[i][j]);
				Button[i][j].add(label[i][j]);
				Button[i][j].setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.CENTER_BASELINE, 16));
			}
		}
		Panel.add(ButtonPanel,BorderLayout.CENTER);
		getContentPane().add(Panel);

		JMenu menu1 = new JMenu(W1.toJ("Menu"));
		menu1.addActionListener(this);
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		JMenuItem menuitem[] = new JMenuItem[5];

		menubar.add(menu1);
		menuitem[0] = new JMenuItem(W1.toJ("New"));
		menuitem[1] = new JMenuItem(W1.toJ("Easy"));
		menuitem[2] = new JMenuItem(W1.toJ("Medium"));
		menuitem[3] = new JMenuItem(W1.toJ("Hard"));
		menuitem[4] = new JMenuItem(W1.toJ("Exit"));
		for(int i = 0; i < 5; i++){
			menuitem[i].addActionListener(this);
			menu1.add(menuitem[i]);
		}


	}
	public void run(){
		this.setVisible(true);
	}

	//ボタンが押された時の動作
	public void actionPerformed(ActionEvent e) {
		String push = ((AbstractButton)e.getSource()).getText();
		if(push.equals(W1.toJ("New"))){
			ReStart();
		}
		if(push.equals(W1.toJ("Exit"))){
			System.exit(0);
		}
		if(push.equals(W1.toJ("Easy"))){
			if(LEVEL == W1.toJ("Easy")){
				ReStart();
			}else{
				GameGUI g = new GameGUI(0,W1.GetJapan());
				g.run();
			}
		}if(push.equals(W1.toJ("Medium"))){
			if(LEVEL == W1.toJ("Medium")){
				ReStart();
			}else{
				GameGUI g = new GameGUI(1,W1.GetJapan());
				g.run();
			}
		}if(push.equals(W1.toJ("Hard"))){
			if(LEVEL == W1.toJ("Hard")){
				ReStart();
			}else{
				GameGUI g = new GameGUI(2,W1.GetJapan());
				g.run();
			}
		}
	}
	//マスに表示される文字
	private void Display(){
		for(int i = 0; i < F.width; i++){
			for(int j = 0; j < F.height; j++){
				if(!F.isFlag(i, j))
					Button[i][j].setText(F.CheckBlock(i, j));
				else{

				}
				if(F.B[i][j].GetNum()>=0){
					Button[i][j].setBackground(Color.white);
					switch(F.CheckBlock(i, j)){
					case "0":
						Button[i][j].setText("");break;
					case "1":
						Button[i][j].setForeground(Color.BLUE);break;
					case "2":
						Button[i][j].setForeground(Color.GREEN);break;
					case "3":
						Button[i][j].setForeground(Color.RED);break;
					case "4":
						Button[i][j].setForeground(Color.CYAN);break;
					case "5":
						Button[i][j].setForeground(Color.ORANGE);break;
					case "6":
						Button[i][j].setForeground(Color.magenta);break;
					case "7":
						Button[i][j].setForeground(Color.black);break;
					case "F":
						if(Button[i][j].getText()!="")
							label[i][j].setIcon(FlagIcon);
					}
				}
			}
			this.setVisible(true);
		}
	}

	//クリックされた時の処理
	public class myListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e){

			Object obj = e.getSource();
			try{
				if(!STOP){
					if(F.isGame()){
						for(int i = 0; i < F.width; i++){
							for(int j = 0; j < F.height; j++){
								if(obj == Button[i][j]){
									//左クリック
									if(e.getButton()==1){
										//ダブルクリック
										if (e.getClickCount() >= 2){
											F.AroundFlag(i, j);
										}
										if(FIRST){
											F.SetBlock(i, j);
											start = System.currentTimeMillis();
											FIRST = false;
										}
										F.ClickBlock(i, j);
									}
									//右クリック
									else if(e.getButton()==3){
										if(!F.B[i][j].isPressed()){
											F.PushFlag(i, j);
											if(F.isFlag(i, j)){
												label[i][j].setIcon(FlagIcon);
												F.DownFlag();
											}else{
												label[i][j].setIcon(null);
												F.UPFlag();
											}
											toString();
											display[0].setText(String.valueOf(F.GetFlagNum()));
										}
									}
								}
							}
						}
						Display();
					}
					//GAMEOVERのとき
					if(!F.isGame()){
						display[1].setText(W1.toJ("GAMEOVER"));
						stop = System.currentTimeMillis();
						time = (stop-start)/1000;
						DISPLAYTIME = (int)time;
						DISPLAYTIME(2,time);
						for(int i = 0; i < F.width; i++){
							for(int j = 0; j < F.height; j++){
								if(F.B[i][j].isBom()){
									label[i][j].setText("●");
									Button[i][j].setBackground(Color.RED);
								}
							}
						}
						STOP = true;
					}
					//GAMEをクリアした時
					if(F.isClear()){
						display[1].setText(W1.toJ("Clear!"));
						stop = System.currentTimeMillis();
						time = (stop-start)/1000;
						DISPLAYTIME = (int)time;
						DISPLAYTIME(2,DISPLAYTIME);
						if(REGTIME>(int)time){
							REGTIME = (int)time;
							DISPLAYTIME(3,REGTIME);
						}
						STOP = true;
					}
				}
			}
			catch(Exception E){
			}
		}
	}
	//再スタート
	private void ReStart(){
		start = 0;
		stop = 0;
		F.Start();
		STOP = false;
		FIRST = true;
		F.FlagNum = F.BomNum;
		toString();
		display[0].setText(String.valueOf(F.GetFlagNum()));
		display[1].setText("");
		display[2].setText("");
		for(int i = 0; i < F.width; i++){
			for(int j = 0; j < F.height; j++){
				F.B[i][j].SetNum(-1);
				Button[i][j].setText("");
				label[i][j].setText("");
				Button[i][j].setBackground(Color.lightGray);
				Button[i][j].setFont(new Font("HGP創英角ﾎﾟｯﾌﾟ体", Font.BOLD, 16));
				label[i][j].setIcon(null);
				label[i][j].setHorizontalAlignment(JLabel.CENTER);
				Button[i][j].setHorizontalAlignment(JLabel.CENTER);
			}
		}
		this.setVisible(true);
		this.run();

	}
	//Timeの表示
	private void DISPLAYTIME(int num,double TIME1){
		int time1 = (int)TIME1;
		if(num == 2){
			int Min,Sec;
			if(time1>=60){
				Min = time1/60;
				Sec = time1%60;
				display[num].setText(String.valueOf(Min)+W1.toJ("min")+String.valueOf(Sec)+W1.toJ("s"));
			}else
				display[num].setText(String.valueOf(time1)+W1.toJ("s"));
		}
		else if(num == 3){
			int Min,Sec;
			if(time1>=60){
				Min = time1/60;
				Sec = time1%60;
				display[num].setText(W1.toJ("Best Time:")+String.valueOf(Min)+W1.toJ("min")+String.valueOf(Sec)+W1.toJ("s"));
			}else
				display[num].setText(W1.toJ("Best Time:")+String.valueOf(time1)+W1.toJ("s"));
		}
	}

	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public static void main(String[] args){
		GameGUI g = new GameGUI(0,true);
		g.run();
	}
}






