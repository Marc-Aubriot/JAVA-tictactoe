import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

public class Sudoku implements ActionListener {
    
    // utilitaires de programmation
    boolean debug = true;

    // gui
    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[][] buttons = new JButton[9][9];

    // un array contenant le sudoku complet
    int[][] final_grid = new int[9][9];

    // un array contenant les possibilités de chaque bouton, chaque ligne, chaque colonne et chaque sous grille
    int[][] line_grid = new int[9][9];
    int[][] col_grid = new int[9][9];
    int[][] sub_grid = new int[9][9];

    Sudoku() {

        // créer le cadre du jeu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Sudoku JAVA");
        
        // créer un layout de 81 boutons correspondant à la grille du sudoku
        panel.setLayout(new GridLayout(9,9));

        //createGrid();
        populateGrid();
        drawButton();
        consoleLog();
        frame.add(panel);
    }

    // rempli la grille de sudoku
    private void populateGrid() {

        // system: evite les stack overflow
        int max_loop = 40;
        int reroll_count = 0;

        // attribue les 9 chiffres possibles à chaque ligne, colonne, sub grid et case de la grille logique
        for (int x=0; x<9; x++) {  
            for (int y=0; y<9; y++) {

                int[] possibilites = {1,2,3,4,5,6,7,8,9};
                line_grid[x][y] = possibilites[y];
                col_grid[x][y] = possibilites[y];
                sub_grid[x][y] = possibilites[y];
            }      
        }

        // itère chaque case et check les possiblités
        remplissage:for (int x=0; x<6; x++) { // ligne
            
            // system: contrôle le stack overflow
            max_loop--;
            if (max_loop <= 0) {
                System.out.println("break du system");
                break;
            }

            // system: debug
            if (debug && x!=0) {
                for (int a=0; a<9; a++) {
                    System.out.println("final grid x:" + x + ",y:" + a + " - entry: " + final_grid[x-1][a]);
                }
            }

            for (int y=0; y<9; y++) { // colonne
                
                // on détermine de quelle subgrid il s'agit
                int sub_grid_index = 0;

                if (y<3 && x<3) { // subgrid 1
                    sub_grid_index = 0;
                } else if ( y>2 && x<3 && y<6) { // subgrid 2
                    sub_grid_index = 1;
                } else if (y>5 && x<3) { // subgrid 3
                    sub_grid_index = 2;
                } else if (y<3 && x>2 && x<6) { // subgrid 4
                    sub_grid_index = 3;
                } else if (y>2 && y<6 && x>2 && x<6) { // subgrid 5
                    sub_grid_index = 4;
                } else if (y>5 && x>2 && x<6) { // subgrid 6
                    sub_grid_index = 5;
                } else if (y<3 && x>5) { // subgrid 7
                    sub_grid_index = 6;
                } else if (y>2 && y<6 && x>5) { // subgrid 8
                    sub_grid_index = 7;
                } else if (y>5 && x>5) { // subgrid 9
                    sub_grid_index = 8;
                }

                // TODO: IMPORTANT piocher les possibilités de la case dans la subgrid (après faudra verif ligne / colonne)
                int[] possibilites = sub_grid[sub_grid_index];
                for (int a=0; a<possibilites.length; a++) {
                    if (possibilites[a] == 0) {
                        possibilites = removeIndexFromArray(a, possibilites);
                    }
                }

                // on tire un chiffre dans la liste des possibilité et on va le vérifier
                int index = random.nextInt(possibilites.length);
                int chiffre_candidat = possibilites[index];

                // check si le chiffre est possible pour cette ligne/colonne/subgrid, sinon le reroll
                boolean chiffre_a_verifier = true;
                while (chiffre_a_verifier) {
                    
                    // check si le chiffre est possible, d'abord dans la subgrid, puis dans la ligne et la colonne
                    boolean sub_grid_true = false;
                    sub_grid_true = inArray(chiffre_candidat, sub_grid[sub_grid_index]);

                    if (inArray(chiffre_candidat, line_grid[x]) && inArray(chiffre_candidat, col_grid[y]) && sub_grid_true && chiffre_candidat != 0) {
                        
                        // system: debug
                        if (debug) { 
                            // ligne
                            System.out.print("\n les possiblités pour cette ligne sont: ");
                            for (int z=0; z<line_grid[x].length; z++) {
                                System.out.print(line_grid[x][z]);
                            }

                            // colonne
                            System.out.print("\n les possiblités pour cette colonne sont: ");
                            for (int z=0; z<col_grid[y].length; z++) {
                                System.out.print(col_grid[y][z]);
                            }

                            // subgrid
                            System.out.print("\n les possiblités pour cette subgrid sont: ");
                            for (int z=0; z<sub_grid[sub_grid_index].length; z++) {
                                System.out.print(sub_grid[sub_grid_index][z]);
                            }

                            System.out.println("\n le chiffre candidat " + chiffre_candidat + ": OK pour case [" + x + "," + y + "]"); 
                        }

                        // marque le chiffre candidat comme bon
                        chiffre_a_verifier = false;

                       // enlève le chiffre des possibilités des lignes, colonnes et sub grids
                        line_grid[x] = removeValueFromArray(chiffre_candidat, line_grid[x]);
                        col_grid[y] = removeValueFromArray(chiffre_candidat, col_grid[y]);
                        sub_grid[sub_grid_index] = removeValueFromArray(chiffre_candidat, sub_grid[sub_grid_index]);    

                    } else { // si le chiffre est mauvais on le reroll

                        // system: debug
                        if (debug) { 
                            System.out.println("\n le chiffre candidat " + chiffre_candidat + ": IMPOSSIBLE pour case [" + x + "," + y + "]"); 
                        }

                        // marque le chiffre candidat comme mauvais
                        chiffre_a_verifier = true;

                        // enlève le chiffre des possiblités de cette itération
                        possibilites = removeIndexFromArray(index, possibilites);

                        // reroll le chiffre si il reste des possibilités, sinon recommence la portion (3 subgrid) du sudoku
                        if (possibilites.length> 0) {
                            index = random.nextInt(possibilites.length);
                            chiffre_candidat = possibilites[index];
                            reroll_count++;
                            System.out.print("reroll => le nouveau chiffre est: " + chiffre_candidat);
                        } else { // le remplissage de cette portion de subgrid à échouer, il faut recommencer
                            if (x<3) {
                                x = 0;
                                continue remplissage;
                            } else if (x>2 && x<6) {
                                x=3;
                                continue remplissage;
                            } else if (x<5) {
                                x=5;
                                continue remplissage;
                            }
                        }
                     
                    }
                } 

                // le chiffre est bon on peut donc l'imprimer dans la grille finale
                if (!chiffre_a_verifier) {
                    final_grid[x][y] = chiffre_candidat;
                }

                // on update les possibilités des colonnes/subgrid
                if (x!=0) {
                    col_grid[y] = removeValueFromArray(final_grid[x][y], col_grid[y]);
                    sub_grid[sub_grid_index] = removeValueFromArray(final_grid[x][y], sub_grid[sub_grid_index]);          
                }

            } // fin de la boucle y
        } // fin de la boucle [x,y]

        System.out.println("\n nombre de reroll: " + reroll_count);
    } // fin de populateGrid()

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

                // TODO: un test pour dupplication qui affiche la case en rouge dans drawButton
            }        
        }
    }

    // affiche des éléments dans la console
    private void consoleLog() { 

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

                    // TODO: imprimer les possibilités quand on clique sur une case dans action event
                }
            }
        }
    }


    // METHODES UTILITAIRES de manipulation de tableau
    // trouve un integer dans un tableau et retourne true
    private static boolean inArray(int value, int[] array) {
        for (int arrayEntry : array) {
          if (value == arrayEntry) {
            return true;
          }
        }
        return false;
    }

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