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

    public static void welcome() {
        System.out.println("Welcome to Klondike Solitaire!");
    }

    public String howToPlay() {
        StringBuilder sb = new StringBuilder();

        sb.append("How To Play on Console: \n");
        sb.append("d - draw card.\n");
        sb.append("t - play card waste to tableau.\n");
        sb.append("f - play card waste to foundation.\n");
        sb.append("a - play card from tableau to tableau");
        sb.append("w - play card foundation to tableau");

    }

    /**
     * Use to continously update board after each move
     * while in the loop (while game is NOT over)
     */
    public String display(GameEngine game) {
        return game.toString();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        GameEngine game = new GameEngine(true);
        int n = s.nextInt(); // user number input
        char move = s.next().charAt(0); // make moves
        move = Character.toLowerCase(move);

        Play.welcome();

        while (!game.isGameOver()) {

        }








    }
    
}
