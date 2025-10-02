import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * The Board class creates 7 tableau piles, 4 foundation piles, 1 stock and 1 waste. 
 * This class will also handle the initial deal onto the board
 */

public class Board {
    
    // private fields -> no outside class can directly change 
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

    /*
     * public getters allow access to private fields
     */
    public Stock getStock() {
        return stock;
    }

    public Waste getWaste() {
        return waste;
    }

    public Foundation getFoundation(Card.Suit suit) {
        if (suit == null) {
            throw new IllegalArgumentException("Suit cannot be null.");
        }
        return foundations.get(suit);
    }

    public Tableau getTableau(int column) {
        if (column > 7 || column < 1) {
            throw new IllegalArgumentException("Invalid Column Input.");
        }
        int index = column - 1;
        return tableaus.get(index);
    }

    /**
     * Theres 7 tableau columns
     * @return -- 7
     */
    public int getTableauColumns() {
        return 7;
    }

    /**
     * This function empties all the piles and sets up
     * a redeal. Clean slate. This function can be called
     * instead of making a new board object each time.
     * 
     * Looping through the enum, the values() function gives you 
     * all the foundation objects in the enummap. 
     */
    public void clear() {
        stock.clear();
        waste.clear();

        for (Foundation f : foundations.values()) {
            f.clear();
        }

        for (int i = 0; i < getTableauColumns(); i++) {
            tableaus.get(i).clear();
        }
    }
    
    @Override
    public String toString() {

    }
}
