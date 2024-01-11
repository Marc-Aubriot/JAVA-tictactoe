public class Main {

    static void methodBlako() {
        // je suis un commentaire c'est blako qui l'a écrit 

	    // Afficher "Bonjour" à l'écran
        System.out.println("Bonjour");

        // Créer un tableau de fruits
        String[] fruits = {"Pomme", "Banane", "Orange", "Fraise"};

        // Afficher les fruits du tableau
        System.out.println("Liste de fruits :");
        for (String fruit : fruits) {
            System.out.println(fruit);
	    }
    }

    public static void main(String[] args) {
        //methodBlako();
        Game tictactoe = new Game();
    }
}