
/**
 * This class interacts takes input from the user and 
 * uses the GameEngine to play Klondike Solitaire.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Play {
    private static final Map<Character, Command> COMMAND_MAP = createMap();
    private static final Map<Character, Card.Suit> FOUNDATION_MAP = foundationKey();

    // controls for selecting foundation, returns map
    public static Map<Character, Card.Suit> foundationKey() {
        HashMap<Character, Card.Suit> map = new HashMap<>();
        map.put('d', Card.Suit.DIAMONDS);
        map.put('h', Card.Suit.HEARTS);
        map.put('c', Card.Suit.CLUBS);
        map.put('s', Card.Suit.SPADES);

        return map;
    }

    /**
     * My control system for userInput and controls to move cards around
     * @return Map and stored in static final field COMMAND_MAP
     */
    public static Map<Character, Command> createMap() {
        Map<Character, Command> map = new HashMap<>();
        map.put('x', (game, s) -> game.draw()); // draw card
        map.put('t', (game, s) -> { // to tableau
            int dest = askForTableauDestination(s);
            game.moveWasteToTableau(dest);
        });
        map.put('f', (game, s) -> { // to foundation
            char move = askForFoundationDestination(s);
            if (FOUNDATION_MAP.get(move) != null) {
                game.moveWastetoFoundation(FOUNDATION_MAP.get(move));
            } else {
                printInvalid();
            }
        });
        map.put('a', (game, s) -> { // tab to tab
            int source = askForTableauSource(s);
            int row = askForTableauRow(s);
            int dest = askForTableauDestination(s);

            game. move(source, row, dest);
        });
        map.put('w', (game, s) -> { // foundation to tableau
            char move = askForFoundationDestination(s);
            int dest = askForTableauDestination(s);

            if (FOUNDATION_MAP.get(move) != null) {
                game.moveFoundationToTableau(FOUNDATION_MAP.get(move), dest);
            } else {
                printInvalid();
            }
        });
        map.put('e', (game, s) -> { // tableau to foundation
            int source = askForTableauSource(s);
            char move = askForFoundationDestination(s);

            if (FOUNDATION_MAP.get(move) != null) {
                game.moveTableauToFoundation(source, FOUNDATION_MAP.get(move));
            } else {
                printInvalid();
            }
        });
        map.put('r', (game, s) -> game.recycle()); // recycle

        return map;
    }

    public static int askForTableauDestination(Scanner s) {
        System.out.println(selectDestination());
        return s.nextInt();
    }

    public static char askForFoundationDestination(Scanner s) {
        System.out.println(selectFoundation());
        return Character.toLowerCase(s.next().charAt(0));
    }

    public static int askForTableauRow(Scanner s) {
        System.out.println(selectRow());
        return s.nextInt();
    }

    public static int askForTableauSource(Scanner s) {
        System.out.println(selectCol());
        return s.nextInt();
    }

    // public access to commands
    public static Map<Character, Command> getCommands() {
        return COMMAND_MAP;
    }

    public static String printInvalid() {
        return "Invalid input.";
    }

    public static String welcomeMenu() {
        return "Welcome to Klondike Solitaire!\n"
                + "1. Play Game\n"
                + "2. Controls\n"
                + "3. Quit";
    }

    /**
     * The select below functions are used when user is moving cards to or from
     * column.
     * 
     * @return a string display, when player selects t, a, or w
     */
    public static String selectCol() {
        return "Select Source Column: ";
    }

    public static String selectRow() {
        return "Select Source Row: ";
    }

    public static String selectDestination() {
        return "Select Destination Column: ";
    }

    public static String selectFoundation() {
        return "Select Foundation: ";
    }

    public static void selectMove() {
       System.out.println("Select a move: ");
    }

    public static void bye() {
        System.out.println("Bye for now!");
    }

    public static void controls() {
        System.out.println("x - draw | t - tab | f - found | a - tab to tab |" + 
        "w - found to tab | e - tab to f | r - recycle\n" + 
        "q - quit");
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

        sb.append("Select a Foundation: \n");
        sb.append("d  - DIAMOND\n");
        sb.append("h - HEARTS\n");
        sb.append("c - CLUBS\n");
        sb.append("s - SPADES\n");


        return sb.toString();

    }    

    /**
     * Use to continously update board after each move
     * while in the loop (while game is NOT over)
     */
    public static void display(GameEngine game) {
        System.out.print(game.toString()); 
    }

    public static void main(String[] args) {
        System.out.println(Play.welcomeMenu());

        Scanner s = new Scanner(System.in);
        GameEngine game = new GameEngine(true);
        final Map<Character, Command> commandMap = COMMAND_MAP;

        boolean play = false;
        char move;
        int userInput;
        userInput = s.nextInt();

        switch (userInput) {
            case 1: // play
                play = true;
                break;
            case 2:
                System.out.println(Play.howToPlay());
                break;
            case 3:
                bye();
                break;
        }

        if (play == true) {
            game.dealNewGame();
        }

        while (!game.isGameOver() && play == true) {
            controls();
            display(game);
            selectMove();

            move = s.next().charAt(0);     
            move = Character.toLowerCase(move);       
            Command cmd = commandMap.get(move);
            if (cmd != null) {
                cmd.execute(game, s);
            } else {
                System.out.println(printInvalid());
            }

            if (move == 'q') {
                play = false;
                bye();
            }
        }
        s.close();
    }
}
