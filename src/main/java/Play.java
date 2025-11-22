/**
 * This class interacts takes input from the user and 
 * uses the GameEngine and InputController to play Klondike Solitaire.
 */

import java.util.*;

public class Play {

    /**
     * Use to continously update board after each move
     * while in the loop (while game is NOT over)
     */
    public static void display(GameEngine game) {
        System.out.print(game.toString()); 
    }

    public static void dealGame(GameEngine game, boolean playMe, Time timer) {
        if (playMe == true) {
            game.dealNewGame();
            timer.start();
        }
        
    }

    /**
     * Print bye, Play == false, stop the timer
     */
    public static void endGame(boolean play, Time timer) {
        TextViews.bye();
        play = false;
        timer.stop();
    }

    public static double minutes(Time timer) {
        return timer.getMinutes();
    }

    public static void printTime(double minutes) {
        System.out.printf("Time Elapsed: %0.2f minutes.", minutes);
    }

    public static void wonGame(GameEngine game, Time timer, double minutes) {
        timer.stop();
        System.out.println("Congrats! You've beat Klondike Solitaire!");
        minutes = minutes(timer);
        printTime(minutes);
    }


    public static final Map<Character, Command> commandMap() {
        return Collections.unmodifiableMap(InputController.getCommands());
    } 

    public static void gameLoop(GameEngine game, boolean play,
    Scanner s, Map<Character, Command> commandMap, Time timer, double minutes) {

        while (!game.isGameOver() && play == true) {
            char move;
            InputController.controls();
            display(game);
            InputController.selectMove();

            move = s.next().charAt(0);
            move = Character.toLowerCase(move);
            Command cmd = commandMap.get(move);
            if (cmd != null) {
                cmd.execute(game, s);
            } else {
                if (move == 'q') {
                    play = false;
                    endGame(play, timer);
                    minutes = minutes(timer);
                    printTime(minutes);
                } else {
                    TextViews.printInvalid();
                }
            }
        }
    }

    public static void play() {
        
        System.out.println(TextViews.welcomeMenu());
        Scanner s = new Scanner(System.in);
        GameEngine game = new GameEngine(true);
        int userInput = s.nextInt();

        boolean playMe = false;
        Time timer = new Time();
        double minutes = 0.0;

        switch (userInput) {
            case 1:
                playMe = true;
                break;
            case 2:
                System.out.println(TextViews.howToPlay());
                break;
            case 3:
                TextViews.bye();
                break;
            default:
                TextViews.printInvalid();
        }

        Map<Character, Command> controller = commandMap();
        dealGame(game, playMe, timer);
        gameLoop(game, playMe, s, controller, timer, minutes);
        if (game.isGameOver()) {
            wonGame(game, timer, minutes);
        }
        s.close();
    }

    public static void main(String[] args) {
        play();
    }
}
