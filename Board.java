import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * The Board class creates 7 tableau piles, 4 foundation piles, 1 stock and 1 waste. 
 * This class will also handle the initial deal onto the board
 */

public class Board {
    
    private Stock stock;
    private Waste waste;
    private EnumMap<Card.Suit, Foundation> foundations; // for each suit, associate 1 foundation
    private List<Tableau> tableaus;

    /**
     * Create a new Board object
     */
    public Board() {
        this.stock = new Stock();
        this.waste = new Waste();

        /*
         * key is Card.suit 
         * value is corresponding foundation pile object
         * now there's 4 foundations, 1 for each suit
         */
        this.foundations = new EnumMap<>(Card.Suit.class);
        foundations.put(Card.Suit.CLUBS, new Foundation());
        foundations.put(Card.Suit.DIAMONDS, new Foundation());
        foundations.put(Card.Suit.HEARTS, new Foundation());
        foundations.put(Card.Suit.SPADES, new Foundation());

        this.tableaus = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            this.tableaus.add(new Tableau());
        }
        
    }

    // Initialization
}
