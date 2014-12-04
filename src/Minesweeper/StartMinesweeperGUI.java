package Minesweeper;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

/*************
 * 
 * 最初のメニュー画面の実装
 *
 */

public class StartMinesweeperGUI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	public static final int N = 3;
	private DATA[] data = new DATA[N];
	private JPanel panel;	
	private JButton[] button = new JButton[N];
	private Word W = new Word();
	JMenu menu1,menu2;
	JMenuBar menubar;
	JMenuItem menuitem1,menuitem2,menuitem3;

	StartMinesweeperGUI(){
		this.setTitle(W.toJ("Minesweeper"));
		this.SetDATA();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(250, 250, 250, 200);
		panel = new JPanel();

		menu1 = new JMenu(W.toJ("Menu"));
		menu1.addActionListener(this);
		menu2 = new JMenu(W.toJ("Languages"));
		menu2.addActionListener(this);

		menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		menubar.add(menu1);
		menubar.add(menu2);
		menuitem1 = new JMenuItem(W.toJ("Exit"));
		menuitem1.addActionListener(this);
		menuitem2 = new JMenuItem("日本語");
		menuitem2.addActionListener(this);
		menuitem3 = new JMenuItem("English");
		menuitem3.addActionListener(this);
		menu1.add(menuitem1);
		menu2.add(menuitem2);
		menu2.add(menuitem3);
		for(int i = 0; i < N; i++){
			button[i] = new JButton(W.toJ(data[i].title));
			panel.add(button[i]);
			button[i].addActionListener(this);
		}
		this.add(panel);
		this.getContentPane().add(panel);
	}
	private void SetDATA(){
		data[0] = new DATA(W.toJ("Easy"),0);
		data[1] = new DATA(W.toJ("Medium"),1);
		data[2] = new DATA(W.toJ("Hard"),2);
	}

	public void actionPerformed(ActionEvent e) {
		try{
			String push = ((AbstractButton)e.getSource()).getText();
			if(push.equals(W.toJ("Exit"))){
				System.exit(0);
			}
			for(int i = 0; i< N; i++){
				if(push.equals(W.toJ(data[i].title))){
					GameGUI g = new GameGUI(data[i].NUM,W.GetJapan());
					g.run();
				}
			}
			if(push.equals("English")){
				W.SetJapan(false);
				this.Language();
			}if(push.equals("日本語")){
				W.SetJapan(true);
				this.Language();
			}
		}catch(Exception E){
		}
	}
	private class DATA{
		String title;
		int NUM;

		DATA(String title,int NUM){
			this.title = title;
			this.NUM = NUM;
		}
	}
	public void run(){
		this.setVisible(true);
	}
	public void Language(){
		this.setTitle(W.toJ("Minesweeper"));
		menu1.setText(W.toJ("Menu"));
		menu2.setText(W.toJ("Languages"));
		menuitem1.setText(W.toJ("Exit"));
		for(int i = 0; i < 3; i++){
			button[i].setText(W.toJ(data[i].title));
		}


	}
	public static void main(String[] agrs){
		StartMinesweeperGUI start = new StartMinesweeperGUI();
		start.run();
	}

}
