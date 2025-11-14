/**
 * This class interacts takes input from the user and 
 * uses the GameEngine to play Klondike Solitaire.
 */

import java.util.HashMap;
import java.util.InputMismatchException;
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
        sb.append("x - draw card\n");
        sb.append("t - play card waste to tableau\n");
        sb.append("f - play card waste to foundation\n");
        sb.append("a - play card from tableau to tableau\n");
        sb.append("w - play card foundation to tableau\n");
        sb.append("e - tableau to foundation\n");
        sb.append("r - recycle -> push cards from waste back into stock\n");
        sb.append("q - quit\n");
        sb.append("------------------------------------\n");
        sb.append("\n");

        sb.append("Select a Foundation: \n");
        sb.append("d  - DIAMOND\n");
        sb.append("h - HEARTS\n");
        sb.append("c - CLUBS\n");
        sb.append("s - SPADES\n");


        return sb.toString();
    }   
    
    public static String rules() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rules to the game: \n");
        sb.append("Draw a card from stock to the waste pile.");
        sb.append("The card in the waste can be played into either the foundations, \n");
        sb.append("or the Tableau columns.\n");

        sb.append("Initially, cards on the tableau are randomly dealt, and your goal is to sort the tableau.");
        sb.append("Cards on the tableau are sorted from 1 lower rank, and alternating color. \n");
        sb.append("Between black (CLUBS AND SPADES) and red (HEARTS AND DIAMONDS)\n");
        sb.append("Cards on the foundation is where you sort cards in ascending order from ACE (1) -> KING (13)\n");
        sb.append("Foundations are sorted by SUITS. There is 4 foundations, one for each SUIT.\n");

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
        Time timer = new Time(); // our stopwatch 
        double minutes = 0.0;
        int countMoves = 0;

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
            timer.start();
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
                countMoves++;
            } else {
                if (move == 'q') {
                    play = false;
                    bye();
                    timer.stop();
                    minutes = timer.getMinutes();
                    System.out.println("Time Elapsed: " + minutes + " minutes.");
                    System.out.println("Move count: " + countMoves);
                } else {
                    System.out.println(printInvalid());
                }
            }
        }

        if (game.isGameOver()) {
            timer.stop();
            System.out.println("Congrats! You've beat the game!");
            System.out.println("Move count: " + countMoves);
            minutes = timer.getMinutes();
            System.out.printf("Time Elapsed: " + minutes + " minutes.");
        }
        s.close();
    }
}
