import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class Snake implements ActionListener{
    
    // frame var
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;

    // panel var
    Color BACKGROUND_COLOR = new Color(0, 0, 0); 
    static final int UNIT_SIZE = 40;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;

    // snake var
    Color SNAKE_COLOR = Color.GREEN;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int body_parts = 6;
    int apples_count;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer; // clock du jeu
    Random random;
    JFrame frame3 = new JFrame();
    JPanel panel = new JPanel();

    Snake(){
        
        // initialisation du plateau de jeu
        frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame3.setVisible(true);
        frame3.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        frame3.setTitle("JAVA Snake");
        frame3.setResizable(false);
        frame3.setLocationRelativeTo(null); 

        panel.setBackground(BACKGROUND_COLOR);
        //panel.addKeyListener(new MyKeyAdapter());

        frame3.add(panel);
        
        //frame.setBackground(new Color(0, 0, 0));
    }

    // récupère les inputs du joueurs
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
    
    }

    
    // dessine le plateau
    public void draw(Graphics canva) {

        //this.panel.paintComponent(canva);
        draw(canva);
    }

    // modifie les coordoonées du snake en fonction de la direction
    public void move() {

    }


    // check si le snake à toucher une pomme ou un mur
    public void check_collision() {

    }

    // pop une pomme dans le jeu
    public void pop_pomme() {

    }

    // game over
    public void game_over() {

    }

    // le snake mange une pomme et le score augmente
    public void snack_pomme() {

    }
}
