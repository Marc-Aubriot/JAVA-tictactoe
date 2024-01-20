import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame implements ActionListener {

    JFrame frame = new JFrame();
    JPanel button_panel = new JPanel();
    JButton[] buttons = new JButton[3];

    GameFrame() {

        frame.setSize(800,800);
        frame.setTitle("Game Hub");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        button_panel.setLayout(new GridLayout(3, 1));
		button_panel.setBackground(new Color(255,0,0));


        buttons[0] = new JButton();
        button_panel.add(buttons[0]);
        buttons[0].setFont(new Font("MV Boli",Font.BOLD,40));
		buttons[0].setFocusable(false);
        buttons[0].setForeground(new Color(255,0,0));
		buttons[0].setText("Sudoku");
        buttons[0].addActionListener(this);

        buttons[1] = new JButton();
        button_panel.add(buttons[1]);
        buttons[1].setFont(new Font("MV Boli",Font.BOLD,40));
		buttons[1].setFocusable(false);
        buttons[1].setForeground(new Color(255,0,0));
		buttons[1].setText("Snake");
        buttons[1].addActionListener(this);

        buttons[2] = new JButton();
        button_panel.add(buttons[2]);
        buttons[2].setFont(new Font("MV Boli",Font.BOLD,40));
		buttons[2].setFocusable(false);
        buttons[2].setForeground(new Color(255,0,0));
		buttons[2].setText("TicTacToe");
        buttons[2].addActionListener(this);

        frame.add(button_panel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]) {
            frame.dispose();
            new Sudoku();
        } else if (e.getSource() == buttons[1]) {
            frame.dispose();
            new SnakeGameFrame();
        } else if (e.getSource() == buttons[2]) {
            frame.dispose();
            new TicTacToe();
        }
    }

}
