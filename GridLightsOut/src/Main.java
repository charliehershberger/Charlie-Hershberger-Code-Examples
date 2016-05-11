import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {

	public static boolean gamewon = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final JFrame frame = new JFrame();
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(10,10,0,0));
		class numpad extends JButton{
			private int num = 0;
			public numpad(int a){
				this.num = a;
			}
			public int getNum(){
				return num;
			}
			public void addNum(int a){
				this.num+= a;
				this.setText(this.num+"");
			}
			public void invert(){
				if (this.getText().equals("X")){
					this.setText("O");
				}else{
					this.setText("X");
				}
			}
		}
		final ArrayList<numpad> buttons = new ArrayList<numpad>();
		for(int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++){
				final numpad a = new numpad(i*10+j+1);
				//a.setText(i*10+j+1+"");
				if (Math.random()>0.5){
					a.setText("O");
				}else{
					a.setText("X");
				}
				panel.add(a);
				buttons.add(a);
			}
		}
		final numpad button = new numpad(0);
		class bl implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource()!= button&&!frame.getTitle().equals("you won")){
					int num = 0;
					for (numpad pressed : buttons){
						if (pressed.equals(arg0.getSource())){
							pressed.invert();
							num = pressed.getNum();
							//button.addNum(pressed.getNum());
							break;
						}
					}
					for (numpad pressed : buttons){
						if (num%10 != 0){
							if (pressed.getNum() == num +1){
								pressed.invert();
							}
						}
						if (num%10 != 1){
							if (pressed.getNum() == num -1){
								pressed.invert();
							}
						}
						if (!(num <= 10)){
							if (pressed.getNum() == num -10){
								pressed.invert();
							}
						}
						if (!(num>90)){
							if (pressed.getNum() == num + 10){
								pressed.invert();
							}
						}
						if (!(num <= 10)&&num%10 != 0){
							if (pressed.getNum() == num -9){
								pressed.invert();
							}
						}
						if (!(num>90)&&num%10 != 0){
							if (pressed.getNum() == num + 11){
								pressed.invert();
							}
						}
						if (!(num <= 10)&&num%10 != 1){
							if (pressed.getNum() == num -11){
								pressed.invert();
							}
						}
						if (!(num>90)&&num%10 != 1){
							if (pressed.getNum() == num + 9){
								pressed.invert();
							}
						}
						}
					for (numpad target: buttons){
						if (target.getText().equals("O")){
							break;
						}
						if (target.getNum() == 100){
							frame.setTitle("you won");
							gamewon = true;
						}
					}
					}
				}
			}
		for (numpad pad : buttons){
			bl buttonlistener = new bl();
			pad.addActionListener(buttonlistener);
		}
		class gameReset implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.setTitle("Grid Lights Out");
				for (numpad pressed : buttons){
					if (Math.random()>0.5){
						pressed.setText("O");
					}else{
						pressed.setText("X");
					}
				}	
			}
		}
		gameReset reset = new gameReset();
		button.addActionListener(reset);
		button.setText("new game");
		frame.add(button, BorderLayout.SOUTH);
		frame.add(panel, BorderLayout.NORTH);
		frame.setVisible(true);
		frame.setTitle("Grid Lights Out");
		frame.setSize(500, 500);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}