import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;
import javax.swing.*;

public class Sudoku implements ActionListener {
    
    // gui
    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[][] buttons = new JButton[9][9];

    // var
    int[][] solution_grid = new int[9][9];
    boolean[][] logic_grid = new boolean[9][9];

    Sudoku() {

        // créer le cadre du jeu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,800);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Sudoku JAVA");
        
        // créer un layout de 81 boutons correspondant à la grille du sudoku
        // TODO: faire en sorte que les subgrid soient plus visible
        panel.setLayout(new GridLayout(9,9));

        int[][] sudoku_grid = generateSudoku();
        int[][] playable_grid = makeItPlayable(sudoku_grid);

        printSudoku(playable_grid);
        drawGrid(playable_grid);
        frame.add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        for (int x=0; x<buttons.length; x++) {
            for (int y=0; y<buttons[x].length; y++) {
                if (e.getSource() == buttons[x][y]) {
                  
                    // TODO: pouvoir rentrer un nouveau chiffre
                    if (logic_grid[x][y] == false) {
                        System.out.println("this button should be rewritable");
                    } else {
                        System.out.println("this button can't be rewrited");
                    }
                    
                }
            }
        }
    }

    // génère un sudoku
    public static int[][] generateSudoku() {
        int[][] sudoku_grid = new int[9][9];
        populateGrid(sudoku_grid);
        return sudoku_grid;
    }

    // méthode qui efface un nombre de case en fonction de la difficulté
    public int[][] makeItPlayable(int[][] sudoku_grid) {
        // TODO: il doit y avoir minimum 2 case par subgrid pour que ce soit jouable

        //boolean[][] logic_grid = new boolean[9][9];
        int[][] playable_grid = new int[9][9];
        int difficulty = 33;

        // on passe un nombre de case égale à la difficulté à true, elles ne seront pas rewritable
        for (int i=0; i<difficulty; i++) { 
            int random_x = random.nextInt(9);
            int random_y = random.nextInt(9);

            if (logic_grid[random_x][random_y] == true) { // si la case est déjà marquée comme true, recommence l'itération; sinon marque true
                i = i-1;
                continue;
            } else {
                logic_grid[random_x][random_y] = true;
            }
        }

        // on itère la grille playable et la grille logic, si logic = false alors playable est vide et rewritable
        // si true, alors playable possède est chiffre est n'est pas rewritable
        for (int row=0; row<9; row++) {
            for (int col=0; col<9; col++) {
                if (logic_grid[row][col] == true) {
                    playable_grid[row][col] = sudoku_grid[row][col];
                }
            }
        }

        return playable_grid;    
    }

    // TODO: méthode qui check si la grille de sudoku une fois finie est valide

    // TODO: méthode qui déclare la victoire du joueur si la grille est valide

    // rempli la grille de sudoku avec une méthode d'incrémentation et de backtracking (recursive)
    public static boolean populateGrid(int[][] sudoku_grid) {
        
        for (int row=0; row<9; row++) { // itère 9 lignes

            for (int col=0; col<9; col++) { // itère 9 colonnes

                if (sudoku_grid[row][col] == 0) { // case vide ?

                    for (int number=1; number<=9; number++) { // itère 9 possibilités

                        if (isValid(sudoku_grid, row, col, number)) { // chiffre possible ?

                            sudoku_grid[row][col] = number; // imprime le chiffre dans la case

                            if (populateGrid(sudoku_grid)) { // rappelle la fonction de manière recursive
                                return true;
                            }

                            sudoku_grid[row][col] = 0; // réinitialise si l'instance à renvoyer false
                        }
                    }
                    
                    return false; // pas de chiffre valide, renvoit false
                }
            }
        }
        return true;
    }

    // regarde si les règles du sudoku sont respectées
    public static boolean isValid(int[][] sudoku_grid, int row, int col, int number) {

        return  !usedInRow(sudoku_grid, row, number) &&
                !usedInCol(sudoku_grid, col, number) &&
                !usedInSubgrid(sudoku_grid, row - row % 3, col - col % 3, number);
    }
    
    // regarde si le chiffre est utilisé dans la ligne
    public static boolean usedInRow(int[][] sudoku_grid, int row, int number) {

        for (int col=0; col<9; col++) { // itère chaque case de la ligne

            if (sudoku_grid[row][col] == number) { // chiffre présent dans la case ?

                return true;

            }

        }

        return false;
    }

    // regarde si le chiffre est utilisé dans la colonne
    public static boolean usedInCol(int[][] sudoku_grid, int col, int number) {

        for (int row=0; row<9; row++) { // itère chaque case de la colonne 

            if (sudoku_grid[row][col] == number) { // chiffre présent dans la case ?

                return true;

            }
        }

        return false;
    }

    // regarde si le chiffre est utilisé dans la subgrid
    public static boolean usedInSubgrid(int[][] sudoku_grid, int start_row, int start_col, int number) {

        for (int row=0; row<3; row++) { // itère chaque ligne de la subgrid

            for (int col=0; col<3; col++) { // itère chaque colonne de la subgrid

                if (sudoku_grid[row + start_row][col + start_col] == number) { // chiffre présent dans la case ?

                    return true;

                }
            }
        }

        return false;
    }

    // ajoute les boutons dans jpanel et dessine la grille de sudoku dans jframe
    public void drawGrid(int[][] playable_grid) {
        
        for (int x=0; x<9; x++) {

            for (int y=0; y<9; y++) {

                // créer un bouton contenant le chiffre
                buttons[x][y] = new JButton();
                panel.add(buttons[x][y]);
                buttons[x][y].setFont(new Font("MV Boli",Font.BOLD,20));
                buttons[x][y].setFocusable(false);
                buttons[x][y].addActionListener(this);

                if (playable_grid[x][y] == 0) {
                    buttons[x][y].setText(" ");
                    buttons[x][y].setBackground(new Color(200,200,255));
                } else {
                    buttons[x][y].setText(String.valueOf(playable_grid[x][y]));
                }
            }        
        }
    }

    // affiche des éléments dans la console
    public static void printSudoku(int[][] sudoku) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(sudoku[row][col] + " ");
            }
            System.out.println();
        }
    }
}