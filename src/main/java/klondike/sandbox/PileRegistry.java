package klondike.sandbox;

import java.util.HashMap;
import java.util.Map;

import klondike.*;

/**
 * Help BoardView and Controller understand GameEngine to perform moves
 * 
 * The engine is designed to track int sources, int destinations, and int columns.
 * The GUI talks in terms of Pile objects. This class is the klondike translator.
 */
public class PileRegistry {
    private final Map<Pile, Integer> tableauColumn = new HashMap<>();

    private final Map<Pile, Card.Suit> foundationSuit = new HashMap<>();

    /**
     * Register a tableau to a specific pile number
     */
    public void registerTableau(Pile pile, int col) {
        tableauColumn.put(pile, col);
    }

    /**
     * Register a foundation to a specific suit
     */
    public void registerFoundation(Pile pile, Card.Suit suit) {
        foundationSuit.put(pile, suit);
    }

    /**
     * Given a pile -> get the key Map<K, V>
     * return the value of pile (the tableau col)
     * @param pile is the pile object in the tableaucolumn map 
     * that is being searched and scanned for. 
     * @return return the keys value of the given pile. 
     * Will return null if not registered
     */
    public Integer getTableauCol(Pile pile) {
        return tableauColumn.get(pile);
    }

    public Card.Suit getFoundationSuit(Pile pile) {
        return foundationSuit.get(pile);
    }

    /*
    put(pile, 3) -> this pile is tableau col 3
    get(pile) -> tell me what this pile is
    */
}
