import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

public class SudoKopy implements ActionListener {
    
    // gui
    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[][] buttons = new JButton[9][9];

    // un array contenant le sudoku complet
    int[][] final_grid = new int[9][9];

    // un array contenant les possibilités de chaque bouton
    int[][][] logic_grid = new int[9][9][9];
    //ArrayList<Integer> test = new ArrayList<Integer>();

    SudoKopy() {

        // créer le cadre du jeu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Sudoku JAVA");
        
        // créer un layout de 81 boutons correspondant à la grille du sudoku
        panel.setLayout(new GridLayout(9,9));

        populateGrid();
        drawButton();
        consoleLog();
        frame.add(panel);
    }

    // rempli la grille de sudoku
    private void populateGrid() {
        // on commence par créer la grille logique comprenant 81 boutons et on rempli chaque entrée avec 9 possiblités 
        int[] possiblities_line1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int x=0; x<9; x++) {
            for (int y=0; y<9; y++) {
                for (int z=0; z<9; z++) {
                    logic_grid[x][y][z] = possiblities_line1[z];
                }
            }
        }

        // on tire 9 chiffre différents pour la 1ère ligne
        for (int x=0; x<9; x++) {
            int index = random.nextInt(possiblities_line1.length);
            final_grid[0][x] = possiblities_line1[index];
            possiblities_line1 = removeIndexFromArray(index, possiblities_line1);
        }
        
        // on update les possiblités de la 1ère ligne
        for (int x=0; x<9; x++) {
            int[] temp_array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            for (int arrayEntry : temp_array) {
                if (final_grid[0][x] != arrayEntry) {
                    logic_grid[0][x] = removeValueFromArray(arrayEntry, logic_grid[0][x]);
                }
            }
        }

        // on update les possibilités des colonnes et de la ligne 2 en fonction du tirage de la 1ère ligne
        for (int x=1; x<9; x++) {
              for (int y=0; y<9; y++) {
                logic_grid[x][y] = removeValueFromArray(final_grid[0][y], logic_grid[x][y]);
            }
        }

        // on effectue un tirage de la 2ème ligne
        int[] possibilites_line2 = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // nouveaux chiffres possibles
        for (int x=0; x<9; x++) { // itère la ligne 2
            System.out.println(" possibilites line 2 length est: " + possibilites_line2.length); // <= err: BOUND MUST BE POSITIVE ?
            int index = random.nextInt(possibilites_line2.length); // roll un nouvel index
            if (possibilites_line2[index] == final_grid[0][x]) { // si le chiffre est le même que la 1ère ligne
                boolean loop = true; // on reroll jusqu'à avoir un chiffre différent
                while (loop) {
                    System.out.println("line 2 loop");
                    index = random.nextInt(possibilites_line2.length);
                    if (possibilites_line2[index] != final_grid[0][x]) { // si le chiffre est différent de la ligne 1 on arrête la boucle
                        loop = false;
                    }
                    final_grid[1][x] = possibilites_line2[index]; // on update la ligne 2 de la grille finale
                    if (possibilites_line2.length >=1) {
                        possibilites_line2 = removeIndexFromArray(index, possibilites_line2); // on enlève la possibilité de cette ligne
                    }
                }
            } else { // sinon on update directement la grille finale et on supprime cette posibilité de la ligne
                final_grid[1][x] = possibilites_line2[index];
                if (possibilites_line2.length >=1) {
                    possibilites_line2 = removeIndexFromArray(index, possibilites_line2);
                }
            }
        }

        // on update les possibilités de la 2ème ligne
        for (int x=0; x<9; x++) {
            int[] temp_array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            for (int arrayEntry : temp_array) {
                if (final_grid[1][x] != arrayEntry) {
                    logic_grid[1][x] = removeValueFromArray(arrayEntry, logic_grid[1][x]);
                }
            }
        }

        // on update les possibilités des colonnes et de la ligne 3 en fonction du tirage de la 2ème ligne 
        for (int x=2; x<9; x++) {
            for (int y=0; y<9; y++) {
              logic_grid[x][y] = removeValueFromArray(final_grid[1][y], logic_grid[x][y]);
            }
        }

        // on effectue un tirage de la 3ème ligne
        int[] possibilites_line3 = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // nouveaux chiffres possibles
        for (int x=0; x<9; x++) { // itère la ligne 3
            System.out.println(" possibilites line 3 length est: " + possibilites_line3.length); // <= err: BOUND MUST BE POSITIVE ?
            int index = random.nextInt(possibilites_line3.length); // roll un nouvel index
            if (possibilites_line3[index] == final_grid[1][x]) { // si le chiffre est le même que la 2ème ligne
                boolean loop = true; // on reroll jusqu'à avoir un chiffre différent
                while (loop) {
                    System.out.println("line 3 loop");
                    index = random.nextInt(possibilites_line3.length);
                    if (possibilites_line3[index] != final_grid[1][x]) { // si le chiffre est différent de la ligne 2 on arrête la boucle
                        loop = false;
                    }
                    final_grid[2][x] = possibilites_line3[index]; // on update la ligne 3 de la grille finale
                    if (possibilites_line3.length >=1) {
                        possibilites_line3 = removeIndexFromArray(index, possibilites_line3); // on enlève la possibilité de cette ligne
                    }
                }
            } else { // sinon on update directement la grille finale et on supprime cette posibilité de la ligne
                final_grid[2][x] = possibilites_line3[index];
                if (possibilites_line3.length >=1) {
                    possibilites_line3 = removeIndexFromArray(index, possibilites_line3);
                }
            }
        }

        // on update les possibilités de la 3ème ligne
        for (int x=0; x<9; x++) {
            int[] temp_array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            for (int arrayEntry : temp_array) {
                if (final_grid[2][x] != arrayEntry) {
                    logic_grid[2][x] = removeValueFromArray(arrayEntry, logic_grid[2][x]);
                }
            }
        }

        // on update les possibilités des colonnes et de la ligne 3 en fonction du tirage de la 2ème ligne 
        for (int x=3; x<9; x++) {
            for (int y=0; y<9; y++) {
              logic_grid[x][y] = removeValueFromArray(final_grid[2][y], logic_grid[x][y]);
            }
        }

        // on effectue un tirage de la 4ème ligne
        int[] possibilites_line4 = {1, 2, 3, 4, 5, 6, 7, 8, 9}; // nouveaux chiffres possibles
        for (int x=0; x<9; x++) { // itère la ligne 4
            System.out.println(" possibilites line 4 length est: " + possibilites_line4.length); // <= err: BOUND MUST BE POSITIVE ?
            int index = random.nextInt(possibilites_line4.length); // roll un nouvel index
            if (possibilites_line4[index] == final_grid[2][x]) { // si le chiffre est le même que la 3ème ligne
                boolean loop = true; // on reroll jusqu'à avoir un chiffre différent
                while (loop) {
                    System.out.println("line 4 loop");
                    index = random.nextInt(possibilites_line4.length);
                    if (possibilites_line4[index] != final_grid[2][x]) { // si le chiffre est différent de la ligne 3 on arrête la boucle
                        loop = false;
                    }
                    final_grid[3][x] = possibilites_line4[index]; // on update la ligne 4 de la grille finale
                    if (possibilites_line4.length >=1) {
                        possibilites_line4 = removeIndexFromArray(index, possibilites_line4); // on enlève la possibilité de cette ligne
                    }
                }
            } else { // sinon on update directement la grille finale et on supprime cette posibilité de la ligne
                final_grid[3][x] = possibilites_line4[index];
                if (possibilites_line4.length >=1) {
                    possibilites_line4 = removeIndexFromArray(index, possibilites_line4);
                }
            }
        }

        // on update les possibilités de la 3ème ligne
        for (int x=0; x<9; x++) {
            int[] temp_array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            for (int arrayEntry : temp_array) {
                if (final_grid[3][x] != arrayEntry) {
                    logic_grid[3][x] = removeValueFromArray(arrayEntry, logic_grid[3][x]);
                }
            }
        }

        // on update les possibilités des colonnes et de la ligne 4 en fonction du tirage de la 3ème ligne 
        for (int x=3; x<9; x++) {
            for (int y=0; y<9; y++) {
              logic_grid[x][y] = removeValueFromArray(final_grid[3][y], logic_grid[x][y]);
            }
        }

    }

    // prépare le tableau logique
    private void drawButton() {

        for (int x=0; x<buttons.length; x++) {

            for (int y=0; y<final_grid[x].length; y++) {

                // créer un bouton contenant le chiffre
                buttons[x][y] = new JButton();
                panel.add(buttons[x][y]);
                buttons[x][y].setFont(new Font("MV Boli",Font.BOLD,20));
                buttons[x][y].setFocusable(false);
                buttons[x][y].addActionListener(this);
                buttons[x][y].setText(String.valueOf(final_grid[x][y]));
            }        
        }
    }

    // affiche des éléments dans la console
    private void consoleLog() { 

        // affiche les possiblités de chaque case
        /*for (int y=0; y<logic_grid.length; y++) {
            for (int x=0; x<logic_grid[0].length; x++) {
                System.out.println();
                System.out.print("button x:" + y + " - y:" + x + " - possiblités: ");
                for (int z=0; z<9; z++) {
                    System.err.print(logic_grid[0][x][z] + " ");
                }
            }
        }*/

        // affiche la grid de sudoku
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("final_grid");
        for(int y=0;y<9;y++) {
            System.out.println("");
            for(int i=0;i<9;i++) {
                System.out.print(final_grid[y][i] + " ");
            }
        }
        System.out.println();


    }

    public void actionPerformed(ActionEvent e) {
        for (int x=0; x<buttons.length; x++) {
            for (int y=0; y<buttons[x].length; y++) {
                if (e.getSource() == buttons[x][y]) {
                    String text = buttons[x][y].getText();
                    System.out.println();
                    System.out.println("bouton x:" + x + " y:" + y);
                    System.out.println("value: " + text);
                    System.out.print("possibilites: ");
                    for (int z=0; z<logic_grid.length; z++) {
                        System.out.print(logic_grid[x][y][z]);
                    }
                }
            }
        }
    }

    // trouve un integer dans un tableau et retourne true
    /*private static boolean inArray(int value, int[] array) {
        for (int arrayEntry : array) {
          if (value == arrayEntry) {
            return true;
          }
        }
        return false;
    }*/

    // efface un index d'un tableau et retourne le nouveau tableau
    private static int[] removeIndexFromArray(int index, int[] array) {
        int[] new_array = new int[array.length-1];

        for (int i=0, k=0; i<array.length; i++) {
            if(i!=index) {
                new_array[k] = array[i];
                k++;
            }
        }

        return new_array;
    }

    // efface une value d'un tableau et retourne le nouveau tableau
    private static int[] removeValueFromArray(int value, int[] array) {
        int[] new_array = new int[array.length];

        for (int i=0, k=0; i<array.length; i++) {
            if (array[i] != value) {
                new_array[k] = array[i];
                k++;
            }
        }

        return new_array;
    }

    // Implementing Fisher–Yates shuffle src stack overflow
    static void shuffleArray(int[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
        int index = rnd.nextInt(i + 1);
        // Simple swap
        int a = ar[index];
        ar[index] = ar[i];
        ar[i] = a;
        }
    }

}