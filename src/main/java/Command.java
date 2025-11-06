import java.util.Scanner;

/**
 * Defines Command.
 * 
 * Something that can be executed with access to gameengine and a scanner. 
 * Allows maps to associate a key with a action, from the gameengine.
 * ex) 'x' -> game.draw()
 * 
 * Can use lambdas expression since its a functional interface(only one abstract method).
 * Lambdas expression allows exclusion of this boilerplate. 
 * 
 * Map<Character, Command> links each key to an executable command. When player presses a key
 * that is in the map, the map will call execute() to perform the action
 */
@FunctionalInterface
public interface Command {
    void execute(GameEngine game, Scanner s);
}
