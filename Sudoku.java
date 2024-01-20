import java.awt.*;
import java.awt.event.*;
//import java.util.*;
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
    int[][] game_grid = new int[9][9];

    // difficulty setting (EASY, MEDIUM, HARD, TEST (1 case))
    String difficulty = "TEST";

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
        game_grid = playable_grid;
        printSudoku(playable_grid);
        drawGrid(playable_grid);
        frame.add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        for (int x=0; x<buttons.length; x++) {
            for (int y=0; y<buttons[x].length; y++) {
                if (e.getSource() == buttons[x][y]) {
                    
                    
                    // incrémente la case quand on clique dessus si la case n'est pas inrewritable (false dans logic_grid)
                    if (!logic_grid[x][y]) {

                        if (game_grid[x][y]<9) { // incrémente la case de 1 à 9 puis reset à 1
                            game_grid[x][y] = game_grid[x][y] + 1;
                        } else {
                            game_grid[x][y] = 1;
                        }

                        buttons[x][y].setText(String.valueOf(game_grid[x][y]));

                    } else if (logic_grid[x][y]) {
                        return;
                    }
                    
                    // si la ligne est finie, la paint en vert
                    if (isValidRow(x)) {
                        int[] green_color = {200, 255, 200};
                        paintRow(x, green_color);
                    } else {
                        int[] blue_color = {200, 200, 255};
                        paintRow(x, blue_color);
                    }

                    // si la colonne est finie, la paint en vert
                    if (isValidCol(y)) {
                        int[] green_color = {200, 255, 200};
                        paintCol(y, green_color);
                    } else {
                        int[] blue_color = {200, 200, 255};
                        paintCol(y, blue_color);
                    }

                    // si la subgrid est finie, la paint en vert
                    if (isValidSubgrid(x - x % 3 , y - y % 3)) {

                    } else {

                    }

                    // check si la grille est finie
                    if (gridFinished()) {
                        endGame();
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
        int case_number = 0;

        if (difficulty == "EASY") {
            case_number = 60;
        } else if (difficulty == "MEDIUM") {
            case_number = 45;
        } else if (difficulty == "HARD") {
            case_number = 30;
        } else if (difficulty == "TEST") {
            case_number = 80;
        }

        //int difficulty = 33;

        // on passe un nombre de case égale à la difficulté à true, elles ne seront pas rewritable
        for (int i=0; i<case_number; i++) { 
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

    // TODO: paint la ligne 
    // incomplet pour le moment
    public void paintRow(int row, int[] color) {

        for (int y=0; y<9; y++) {
            Color color_green = new Color(200,255,0);
            //Color color_blue = new Color(200,200,255); 

            if (buttons[row][y].getBackground() == color_green) {
                
            }

            if (color[2] == 255 && logic_grid[row][y] != true) { // peint les cases rewritable en bleu
                buttons[row][y].setBackground(new Color(color[0],color[1],color[2]));
            } else if (color[2] == 255 && logic_grid[row][y]) { // peint les cases non rewritable en couleur par défaut
                buttons[row][y].setBackground(null);
            } else if (color[1] == 255) { // peint les cases en vert
                buttons[row][y].setBackground(new Color(color[0],color[1],color[2]));
            }
        }
    }

    // TODO: paint la colonne en vert si elle est valide
    // incomplet pour le moment
    public void paintCol(int col, int[] color) {

        for (int x=0; x<9; x++) {
            if (color[2] == 255 && logic_grid[x][col] != true) { // peint les cases rewritable en bleu
                buttons[x][col].setBackground(new Color(color[0],color[1],color[2]));
            } else if (color[2] == 255 && logic_grid[x][col]) { // peint les cases non rewritable en couleur par défaut
                buttons[x][col].setBackground(null);
            } else if (color[1] == 255) { // peint les cases en vert
                buttons[x][col].setBackground(new Color(color[0],color[1],color[2]));
            }
        }
    }

    // TODO: paint la subgrid en vert si elle est valide
    public void paintSubgrid(int row, int col, int[] color) {

    }

    // méthode qui check si la grille de sudoku une fois finie est valide
    public boolean gridFinished() {

        // vérification des lignes
        for (int x=0; x<9; x++) {

            if (!isValidRow(x)) {  
                return false; 
            }

        }

        // vérification des colonnes
        for (int y=0; y<9; y++) {

            if (!isValidCol(y)) { 
                return false;
            }

        }

        // vérification des subgrids, on injecte 9 coordonnées qui représentent les subgrids
        boolean[] subgrid_valid = new boolean[9];
        int[][] coordinates = { {0,0}, {0,3}, {0,6}, {3,0}, {3,3}, {3,6}, {6,0}, {6,3}, {6,6}};

        for (int i=0; i<coordinates.length; i++) {
            subgrid_valid[i] = isValidSubgrid(coordinates[i][0], coordinates[i][1]);

            if(!subgrid_valid[i]) {
                return false;
            } 
        }

        return true;
    }

    // méthode qui vérifie la validité de la ligne
    public boolean isValidRow(int row) {
        boolean[] numbers = new boolean[9];

        // on itère la ligne pour valider les chiffres
        for (int col=0; col<9; col++) {
            if (game_grid[row][col] == 1) { numbers[0] = true; }
            if (game_grid[row][col] == 2) { numbers[1] = true; }
            if (game_grid[row][col] == 3) { numbers[2] = true; }
            if (game_grid[row][col] == 4) { numbers[3] = true; }
            if (game_grid[row][col] == 5) { numbers[4] = true; }
            if (game_grid[row][col] == 6) { numbers[5] = true; }
            if (game_grid[row][col] == 7) { numbers[6] = true; }
            if (game_grid[row][col] == 8) { numbers[7] = true; }
            if (game_grid[row][col] == 9) { numbers[8] = true; }
        }

        // on itère le tableau des nombres, si on trouve un seul false on return false
        for (boolean b : numbers) {
            if (!b) {
                return false;
            }
        }

        return true;
    }

    // méthode qui vérifie la validité de la colonne
    public boolean isValidCol(int col) {
        boolean[] numbers = new boolean[9];

        // on itère la colonne pour valider les chiffres
        for (int row=0; row<9; row++) {
            if (game_grid[row][col] == 1) { numbers[0] = true; }
            if (game_grid[row][col] == 2) { numbers[1] = true; }
            if (game_grid[row][col] == 3) { numbers[2] = true; }
            if (game_grid[row][col] == 4) { numbers[3] = true; }
            if (game_grid[row][col] == 5) { numbers[4] = true; }
            if (game_grid[row][col] == 6) { numbers[5] = true; }
            if (game_grid[row][col] == 7) { numbers[6] = true; }
            if (game_grid[row][col] == 8) { numbers[7] = true; }
            if (game_grid[row][col] == 9) { numbers[8] = true; }
        }

        // on itère le tableau des nombres, si on trouve un seul false on return false
        for (boolean b : numbers) {
            if (!b) {
                return false;
            }
        }

        return true;
    }

    // méthode qui vérifie la validité de la subgrid
    public boolean isValidSubgrid(int start_row, int start_col) {

        boolean[] numbers = new boolean[9];

        // on itère la colonne pour valider les chiffres
        for (int row=0; row<3; row++) {
            for (int col=0; col<3; col++) {
                if (game_grid[start_row + row][start_col + col] == 1) { numbers[0] = true; }
                if (game_grid[start_row + row][start_col + col] == 2) { numbers[1] = true; }
                if (game_grid[start_row + row][start_col + col] == 3) { numbers[2] = true; }
                if (game_grid[start_row + row][start_col + col] == 4) { numbers[3] = true; }
                if (game_grid[start_row + row][start_col + col] == 5) { numbers[4] = true; }
                if (game_grid[start_row + row][start_col + col] == 6) { numbers[5] = true; }
                if (game_grid[start_row + row][start_col + col] == 7) { numbers[6] = true; }
                if (game_grid[start_row + row][start_col + col] == 8) { numbers[7] = true; }
                if (game_grid[start_row + row][start_col + col] == 9) { numbers[8] = true; }
            }
        }

        // on itère le tableau des nombres, si on trouve un seul false on return false
        for (boolean b : numbers) {
            if (!b) {
                return false;
            }
        }

        return true;
    }

    // méthode qui déclare la victoire du joueur si la grille est valide
    public void endGame() {
        System.out.println("ENDGAME");
        frame.remove(panel);

        JPanel panel2 = new JPanel();
        JLabel textfield = new JLabel();

        textfield.setBackground(new Color(25,25,25));
		textfield.setForeground(new Color(25,255,0));
		textfield.setFont(new Font("Ink Free",Font.BOLD,75));
		textfield.setHorizontalAlignment(JLabel.CENTER);
		textfield.setText("YOU WIN :D");
		textfield.setOpaque(true);
		
		panel2.setLayout(new BorderLayout());
        panel2.add(textfield);

        frame.add(panel2,BorderLayout.NORTH);
    }

    // rempli la grille de sudoku avec une méthode d'incrémentation et de backtracking (recursive)
    public static boolean populateGrid(int[][] sudoku_grid) {
        
        for (int row=0; row<9; row++) { // itère 9 lignes

            for (int col=0; col<9; col++) { // itère 9 colonnes

                if (sudoku_grid[row][col] == 0) { // case vide ?

                    for (int number=1; number<=9; number++) { // itère 9 possibilités

                        if (isValidMove(sudoku_grid, row, col, number)) { // chiffre possible ?

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
    public static boolean isValidMove(int[][] sudoku_grid, int row, int col, int number) {

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