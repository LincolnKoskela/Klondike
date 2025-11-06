
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

import java.util.HashMap;
import java.util.Map;
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

    public static String selectFoundation() {
        return "Select Foundation: ";
    }

    public static String howToPlay() {
        StringBuilder sb = new StringBuilder();

        sb.append("How To Play on Console: \n");
        sb.append("-----------------------------------\n");
        sb.append("x - draw card.\n");
        sb.append("t - play card waste to tableau.\n");
        sb.append("f - play card waste to foundation.\n");
        sb.append("a - play card from tableau to tableau\n");
        sb.append("w - play card foundation to tableau\n");
        sb.append("e - tableau to foundation\n");
        sb.append("r - recycle -> push cards from waste back into stock\n");
        sb.append("------------------------------------\n");
        sb.append("\n");

        sb.append("Tabelau selection, after selecting a tab move: \n");
        sb.append("Select the number column. \n");
        sb.append("Select the row. \n");
        sb.append("Select destination.\n");
        sb.append("-----------------------------------\n");

        sb.append("Select a Foundation: \n");
        sb.append("d  - DIAMOND\n");
        sb.append("h - HEARTS\n");
        sb.append("c - CLUBS\n");
        sb.append("s - SPADES\n");


        return sb.toString();

    }

    /**
     * My control system for userInput and controls to move cards around
     * @return HashMap<Character, Command> (interface)
     */
    public static HashMap<Character, Command> createMap() {
        HashMap<Character, Command> map = new HashMap<>();
        map.put('x', (game, s) -> game.draw()); // draw card
        map.put('t', (game, s) -> { // to tableau
            System.out.println(selectDestination());
            int dest = s.nextInt();
            game.moveWasteToTableau(dest);
        });
        map.put('f', (game, s) -> { // to foundation, using foundationKey() function
            System.out.println(selectFoundation());
            char move = s.next().charAt(0);
            move = Character.toLowerCase(move);
            if (foundationKey().get(move) != null) {
                game.moveWastetoFoundation(foundationKey().get(move));
            } else {
                System.out.println("Invalid key.");
            }
        });
    }


    // controls for selecting foundation, returns map
    public static HashMap<Character, Card.Suit> foundationKey() {
        HashMap<Character, Card.Suit> map = new HashMap<>();
        map.put('d', Card.Suit.DIAMONDS);
        map.put('h', Card.Suit.HEARTS);
        map.put('c', Card.Suit.CLUBS);
        map.put('s', Card.Suit.SPADES);

        return map;
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
        boolean play = false;

        int source;
        int row;
        int dest;
        char foundation;

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
            System.out.println(Play.display(game)); 

            System.out.print("Select a move: ");
            char move = s.next().charAt(0);
            move = Character.toLowerCase(move);

            if (move == 'x') { // draw
                game.draw();
            } else if (move == 't') { // waste to tableau
                System.out.print(Play.selectDestination());  
                dest = s.nextInt();
                game.moveWasteToTableau(dest);
            } else if (move == 'f') { // waste to foundation
                System.out.print(Play.selectFoundation()); 
                foundation = s.next().charAt(0);
                foundation = Character.toLowerCase(foundation);
                if (foundation == 'd') {
                    game.moveWastetoFoundation(Card.Suit.DIAMONDS);
                } else if (foundation == 'h') {
                    game.moveWastetoFoundation(Card.Suit.HEARTS);
                } else if (foundation == 'c') {
                    game.moveWastetoFoundation(Card.Suit.CLUBS);
                } else if (foundation == 's') {
                    game.moveWastetoFoundation(Card.Suit.SPADES);
                } else {
                    continue;
                }
            } else if (move == 'a') { // tableau to tableau
                System.out.print(Play.selectCol()); 
                source = s.nextInt();
                System.out.print(Play.selectRow()); 
                row = s.nextInt();
                System.out.print(Play.selectDestination()); 
                dest = s.nextInt();
                game.move(source, row, dest);
            } else if (move == 'w') { // foundation to tableau
                System.out.print(Play.selectFoundation());
                foundation = s.next().charAt(0);
                foundation = Character.toLowerCase(foundation);
                System.out.print(Play.selectDestination());
                dest = s.nextInt();

                if (foundation == 'd') {
                    game.moveFoundationToTableau(Card.Suit.DIAMONDS, dest);
                } else if (foundation == 'h') {
                    game.moveFoundationToTableau(Card.Suit.HEARTS, dest);
                } else if (foundation == 'c') {
                    game.moveFoundationToTableau(Card.Suit.CLUBS, dest);
                } else if (foundation == 's') {
                    game.moveFoundationToTableau(Card.Suit.SPADES, dest);
                } else {
                    continue;
                }
            } else if (move == 'e') { // tab to foundation
                System.out.print(selectCol());
                source = s.nextInt();
                System.out.print(selectRow());
                row = s.nextInt();
                System.out.print(selectFoundation());
                foundation = s.next().charAt(0);
                foundation = Character.toLowerCase(foundation);

                if (foundation == 'd') {
                    game.moveTableauToFoundation(source, row, Card.Suit.DIAMONDS);
                } else if (foundation == 'h') {
                    game.moveTableauToFoundation(source, row, Card.Suit.HEARTS);
                } else if (foundation == 'c') {
                    game.moveTableauToFoundation(source, row, Card.Suit.CLUBS);
                } else if (foundation == 's') {
                    game.moveTableauToFoundation(source, row, Card.Suit.SPADES);
                } else {
                    continue;
                }
            } else if (move == 'r') {
                game.recycle();
            } else if (move == 'q') { // quit
                System.out.println("You've quit the game, bye now!");
                play = false;
            } else {
                System.out.println("Invalid input.");
            }

            // if you beat the game
            if (game.isGameOver()) {
                System.out.println("Congrats! You've successfully beat the game!");
                play = false;
            }
        }
        s.close();
    }
}
