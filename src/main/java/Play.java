
/**
 * This class interacts takes input from the user and 
 * uses the GameEngine to play Klondike Solitaire.
 * 
 * How to play: 
 * BUTTONS USED: d/t/f/a/s/q/w
 * char d -> draw() stock -> waste
 * char t -> waste to tableau: which tableau? take number input
 * char f -> waste to foundation: 
 * char a -> tab to tab: which tabs? take number input
 * char w -> foundation -> tab: which tab? take number input
 * 
 * char q -> quit.
 * 
 */

import java.util.Scanner;

public class Play {

    public static String welcome() {
        return "Welcome to Klondike Solitaire!\n"
                + "1. Play Game\n"
                + "2. Controls\n"
                + "3. Quit\n";
    }

    /**
     * The select below functions are used when user is moving cards to or from
     * column.
     * 
     * @return a string display, when player selects t, a, or w
     */
    public static String selectCol() {
        return "Select Source Column: \n";
    }

    public static String selectRow() {
        return "Select Source Row: \n";
    }

    public static String selectDestination() {
        return "Select Destination Column: \n";
    }

    public static String howToPlay() {
        StringBuilder sb = new StringBuilder();

        sb.append("How To Play on Console: \n");
        sb.append("-----------------------------------\n");
        sb.append("d - draw card.\n");
        sb.append("t - play card waste to tableau.\n");
        sb.append("f - play card waste to foundation.\n");
        sb.append("a - play card from tableau to tableau\n");
        sb.append("w - play card foundation to tableau\n");
        sb.append("------------------------------------\n");
        sb.append("\n");

        sb.append("Tabelau selection, after selecting a tab move: \n");
        sb.append("Select the number column. \n");
        sb.append("Select the row. \n");
        sb.append("Select destination.\n");

        return sb.toString();

    }

    /**
     * Use to continously update board after each move
     * while in the loop (while game is NOT over)
     */
    public static String display(GameEngine game) {
        return game.toString();
    }

    public static void main(String[] args) {
        System.out.println(Play.welcome());

        Scanner s = new Scanner(System.in);
        GameEngine game = new GameEngine(true);
        int userInput;
        char move;
        boolean play = false;

        int source;
        int row;
        int dest;

        userInput = s.nextInt();
        switch (userInput) {
            case 1:
                play = true;
                break;
            case 2:
                System.out.println(Play.howToPlay());
                break;
            case 3:
                System.out.println("Bye for now!");
                break;
        }

        if (play == true) {
            game.dealNewGame();
        }

        while (!game.isGameOver() && play == true) {
            Play.display(game);

            System.out.println("Select a move: ");
            move = s.nextLine().charAt(0);
            move = Character.toLowerCase(move);

            if (move == 'd') { // draw
                game.draw();
            } else if (move == 't') { // waste to tableau
                Play.selectDestination();
                dest = s.nextInt();
                game.moveWasteToTableau(dest);
            } else if (move == 'f') { // waste to foundation
                game.moveWastetoFoundation();
            }

        }

        s.close();
    }

}
