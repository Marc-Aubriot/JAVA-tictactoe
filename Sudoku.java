import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Sudoku implements ActionListener {
    
    // gui
    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[] buttons = new JButton[81];

    // logic
    String[][] sudoku_horizontal_grid = { 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
    };

    String[][] sudoku_vertical_grid = { 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
        {null, null, null, null, null, null, null, null, null}, 
    };

    //String[][] sudoku_chunk_grid = { {}, {}, {}, {}, {}, {}, {}, {}, {}};
    String[] sudoku_key_value = {};
    int z;

    Sudoku() {
        
        // créer le cadre du jeu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Sudoku JAVA");
        
        // créer un layout de 81 boutons correspondant à la grille du sudoku
        panel.setLayout(new GridLayout(9,9));

        // populate la grille
        for(int y=0;y<9;y++) {

            // créer une liste de chiffre disponible à dispatcher dans cette ligne
            String[] int_table = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

            for(int i=0;i<9;i++) {
                
                // réduit la taille de la liste de 1 à chaque itération
                int integer = random.nextInt(int_table.length-i); 

                // créer un bouton contenant le chiffre
                buttons[i] = new JButton();
                panel.add(buttons[i]);
                buttons[i].setFont(new Font("MV Boli",Font.BOLD,20));
                buttons[i].setFocusable(false);
                buttons[i].addActionListener(this);
                buttons[i].setText(int_table[integer]);

                // enregistre le chiffre dans la ligne
                sudoku_horizontal_grid[y][i] = int_table[integer];

                // enregistre le chiffre dans la colonne
                sudoku_vertical_grid[i][y] = int_table[integer];

                // enlève le chiffre de la liste des chiffres possibles en décalant les index de 1 vers la gauche
                for (int k = integer; k < int_table.length - 1; k++) {
                    int_table[k] = int_table[k + 1];
                }

                ++z;
            }
        }

        frame.add(panel);

        consoleLog();
    }

    // affiche des éléments dans la console
    public void consoleLog() {

        // code de vérification console
        System.out.println("---------------------------------");
        System.out.println("sudoku_horizontal_grid");
        for(int y=0;y<9;y++) {
            System.out.println("");
            for(int i=0;i<9;i++) {
                System.out.print(sudoku_horizontal_grid[y][i] + " ");
            }
        }

        System.out.println("");

        System.out.println("---------------------------------");
        System.out.println("sudoku_vertical_grid");
        for(int y=0;y<9;y++) {
            System.out.println("");
            for(int i=0;i<9;i++) {
                System.out.print(sudoku_vertical_grid[y][i] + " ");
            }
        }

        System.out.println("");
        System.out.println("---------------------------------");
        // fin de la zone de code console
    }

    public void actionPerformed(ActionEvent e) {

    }

    /*private static boolean inArray(int value, int[] array) {

        for (int arrayEntry : array) {
          if (value == arrayEntry) {
            return true;
          }
        }
        return false;
    }*/
}
