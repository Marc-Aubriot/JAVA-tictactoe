import javax.swing.JFrame;

public class SnakeGameFrame extends JFrame {

	SnakeGameFrame(){
			
		this.add(new Snake());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
}