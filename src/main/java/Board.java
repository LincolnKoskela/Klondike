import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * The Board class creates 7 tableau piles, 4 foundation piles, 1 stock and 1 waste. 
 */

public class Board {
    
    // boards private fields
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
        for (int i = 0; i < getColumns(); i++) {
            this.tableaus.add(new Tableau());
        }
        
    }

    // get me
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
     * Theres 7 tableau columns in Klondikes Tableau section 
     * of the board. This function is used for looping through
     * the columns instead of hardcoding to make more understandable. 
     * @return 7
     */
    public int getColumns() {
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

        for (int i = 0; i < getColumns(); i++) {
            tableaus.get(i).clear();
        }
    }

    /**
     * Check if one of the piles is not full. Full means to have 
     * size of 13 cards and king on top, ranked from Ace up to King
     * @return false if one pile is not full
     */
    public boolean isGameWon() {
        for (Foundation f : foundations.values()) {
            if (!f.isFull()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return Total cards on the board, should be 52
     */
    public int totalCardCount() {
        int size = foundations.size() + stock.size() 
        + waste.size() + tableaus.size(); 
        return size;
    }

    public int foundationsComplete() {
        int count = 0;
        for (Foundation f : foundations.values()) {
            if (f.isFull()) {
                count++;
            }
        }

        return count;
    }
    
    @Override
    public String toString() {
        StringBuilder swPile = new StringBuilder(); 
        StringBuilder fPile = new StringBuilder(); 
        StringBuilder tPiles = new StringBuilder(); 

        // stock waste piles
        swPile.append(stock.toDisplay()); 
        swPile.append(" "); 
        swPile.append(waste.toDisplay()); 
        swPile.append(" ");

        // foundation piles
        for (Foundation f : foundations.values()) {
            fPile.append(f.toDisplay());
        }

        // tableau columns 
        int max = 0;
        for (Tableau t : tableaus) {
            if (t.size() > max) {
                max = t.size();
            }
        }

        for (int row = 0; row < max; row++) {
            for (int col = 0; col < getColumns(); col++) {
                Tableau t = tableaus.get(col);
                if (row < t.size()) {
                    tPiles.append("[").append(t.getCard(row).toDisplay()).append("]");
                } else {
                    tPiles.append("[  ]");
                }
                tPiles.append(" ");
            }
            tPiles.append("\n"); // next row
        }

        // board layout
        return swPile.toString() + "       " + fPile.toString() + "\n"
        + tPiles.toString();
    }

    public static void main(String[] args) {

        // test the board output, don't worry about logic, focus on display
        Board board = new Board();
        Deck deck = new Deck();
        Card c1 = deck.draw();
        Card c2 = deck.draw();
        Card c3 = deck.draw();
        Card c4 = deck.draw();
        Card c5 = deck.draw();
        Card c6 = deck.draw();
        Card c7 = deck.draw();
        Card c8 = deck.draw();
        Card c9 = deck.draw();
        Card c10 = deck.draw();

        board.getStock().push(c1);
        board.getStock().push(c2);
        board.getFoundation(Card.Suit.SPADES).push(c3);
        board.getFoundation(Card.Suit.DIAMONDS).push(c4);
        board.getFoundation(Card.Suit.SPADES).push(c4);
        board.getFoundation(Card.Suit.SPADES).push(c5);
        board.getFoundation(Card.Suit.CLUBS).push(c6);
        board.getFoundation(Card.Suit.HEARTS).push(c7);
        board.getWaste().push(c8);
        board.getTableau(1).push(c9);
        board.getTableau(1).push(c10);
        board.getTableau(1).push(c9);

        Card c11 = deck.draw();
        Card c12 = deck.draw();
        Card c13 = deck.draw();
        Card c14 = deck.draw();
        Card c15 = deck.draw();
        Card c16 = deck.draw();
        Card c17 = deck.draw();
        Card c18 = deck.draw();
        Card c19 = deck.draw();
        Card c20 = deck.draw();

        board.getTableau(1).push(c11);
        board.getTableau(2).push(c12);
        board.getTableau(2).push(c13);
        board.getTableau(3).push(c14);
        board.getTableau(4).push(c15);
        board.getTableau(5).push(c16);
        board.getTableau(6).push(c17);
        board.getTableau(7).push(c18);
        board.getTableau(5).push(c19);
        board.getTableau(3).push(c20);

        System.out.print(board);

        /*
         * col 1 = 4 cards
         * 2 - 2
         * 3 - 2
         * 4 - 1
         * 5 - 2
         * 6 - 1
         * 7 - 1
         */

        Board b = new Board();
    }

}
