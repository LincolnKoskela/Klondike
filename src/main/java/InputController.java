import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;


public class InputController {
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
        map.put('u', (game, s) -> game.undo()); // 

        return map;
    }

    public static int askForTableauSource(Scanner s) {
        int n;
        while (true) { // forever loopy
            try {
                System.out.println(selectCol());
                n = s.nextInt();
                if (n <= 7 && n >= 1) break;
                
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer. " + e);
                s.nextLine(); // clear scanner
            }            
        }
        return n;
    }

    public static int askForTableauDestination(Scanner s) {
        int n;

        while (true) {
            try {
                System.out.println(selectDestination());
                n = s.nextInt(); // may encounter input mismatch
                if (n <= 7 && n >= 1) break;

            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer. " + e);
                s.nextLine(); // clear scanner
            } 
        }
        return n;
    }

    public static char askForFoundationDestination(Scanner s) {
        char c;
        while (true) {
            try {
                System.out.println(selectFoundation());
                c = Character.toLowerCase(s.next().charAt(0));
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a char. " + e);
                s.nextLine(); // clear scanner
            }
        }
        return c;
    }

    public static int askForTableauRow(Scanner s) {
        int n;
        while (true) {
            try {
                System.out.println(selectRow());
                n = s.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer. " + e);
                s.nextLine(); // clear scanner
            }
        }
        return n;
    }

    // public access to commands
    public static Map<Character, Command> getCommands() {
        return COMMAND_MAP;
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

    public static void printInvalid() {
        System.out.println("Invalid input");
    }

    public static void controls() {
        System.out.println("x - draw | t - tab | f - found | a - tab to tab |" + 
        "w - found to tab | e - tab to f | r - recycle\n" + 
        "u - undo | q - quit");
    }
}
