/**
 * Utility class that uses deep copies in each indivdual piles class
 * to make a snapshot of the current game state. 
 */

import java.util.*;

public class Snapshot {
    
    /**
     * This function creates a frozen snapshot of the game. 
     * Using this function will enable a undo feature.
     * @param board gives this function access to board commands 
     * @return gamestate object (frozen snapshot)
     */
    public GameState createSnapshot(Board board) {
        int col = 7;
        Stock stock = board.getStock().copy();
        Waste waste = board.getWaste().copy();
        EnumMap<Card.Suit, Foundation> foundations = new EnumMap<>(Card.Suit.class);
        List<Tableau> tableaus = new ArrayList<>();

        for (Card.Suit suit : Card.Suit.values()) {
            foundations.put(suit, board.getFoundation(suit).copy());
        }

        for (int i = 1; i <= col; i++) {
            tableaus.add(board.getTableau(i).copy());
        }
        return new GameState(stock, waste, foundations, tableaus);
    }
}
