import java.util.List;
import java.util.ArrayList;
import java.util.EnumMap;

/**
 * The Board class creates 7 tableau piles, 4 foundation piles, 1 stock and 1
 * waste.
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
     * 
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
     * 
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
     * Count the number of foundations complete
     * 
     * @return int value of foundations complete
     */
    public int foundationsComplete() {
        int count = 0;
        for (Foundation f : foundations.values()) {
            if (f.isFull()) {
                count++;
            }
        }

        return count;
    }

    /*
     * Width 3 characters wide left aligned
     */
    private static final String CELL_FORMAT = "[%-3s] ";

    /**
     * Helper function for board display. Takes the CELL_FORMAT variable and handles
     * nulls using tenary operator.
     */
    private static String cell(String str) {
        return String.format(CELL_FORMAT, (str == null || str.isEmpty()) ? "" : str);
    }

    /**
     * Board layout
     */
    @Override
    public String toString() {
        StringBuilder swPile = new StringBuilder();
        StringBuilder fPile = new StringBuilder();
        StringBuilder tPiles = new StringBuilder();
        StringBuilder sb = new StringBuilder(); // column counter

        // stock waste piles
        swPile.append(cell(stock.toDisplay()));
        swPile.append(cell(waste.toDisplay()));

        // foundation piles
        for (Foundation f : foundations.values()) {
            fPile.append(cell(f.toDisplay()));
        }

        // tableau columns
        int max = 0;
        for (Tableau t : tableaus) {
            if (t.size() > max) {
                max = t.size();
            }
        }

        // 1 -> 7
        for (int i = 1; i <= getColumns(); i++) {
            sb.append(i + "     ");
        }

        for (int row = 0; row < max; row++) {
            tPiles.append(row + " "); // print row num indexed 0-based
            for (int col = 0; col < getColumns(); col++) {

                Tableau t = tableaus.get(col);
                if (row < t.size()) {
                    tPiles.append(cell(t.getCard(row).toDisplay()));
                } else {
                    tPiles.append(cell(""));
                }
            }
            tPiles.append("\n"); // next row
        }

        // board layout
        return "-----------------------------------------\n"
                + "  " + swPile.toString() + "      " + fPile.toString() + "\n"
                + "   " + sb.toString() + "\n"
                + tPiles.toString();
    }

    public static void main(String[] args) {

        // test the board output, don't worry about logic, focus on display
        Board board = new Board();
        Deck deck = new Deck(true);
        System.out.println("Deck card count: " + deck.getSize());
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

        /////////////////////////////////////////////////////////

        System.out.println();
        System.out.println("///////////////////////////");
        System.out.println("Foundations complete Test");
        // foundations complete, fill a foundation
        Board b = new Board();
        ////////////// SPADES ///////////////
        Card s1 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card s2 = new Card(Card.Rank.TWO, Card.Suit.SPADES);
        Card s3 = new Card(Card.Rank.THREE, Card.Suit.SPADES);
        Card s4 = new Card(Card.Rank.FOUR, Card.Suit.SPADES);
        Card s5 = new Card(Card.Rank.FIVE, Card.Suit.SPADES);
        Card s6 = new Card(Card.Rank.SIX, Card.Suit.SPADES);
        Card s7 = new Card(Card.Rank.SEVEN, Card.Suit.SPADES);
        Card s8 = new Card(Card.Rank.EIGHT, Card.Suit.SPADES);
        Card s9 = new Card(Card.Rank.NINE, Card.Suit.SPADES);
        Card s10 = new Card(Card.Rank.TEN, Card.Suit.SPADES);
        Card sj = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        Card sq = new Card(Card.Rank.QUEEN, Card.Suit.SPADES);
        Card sk = new Card(Card.Rank.KING, Card.Suit.SPADES);
        b.getFoundation(Card.Suit.SPADES).push(s1);
        b.getFoundation(Card.Suit.SPADES).push(s2);
        b.getFoundation(Card.Suit.SPADES).push(s3);
        b.getFoundation(Card.Suit.SPADES).push(s4);
        b.getFoundation(Card.Suit.SPADES).push(s5);
        b.getFoundation(Card.Suit.SPADES).push(s6);
        b.getFoundation(Card.Suit.SPADES).push(s7);
        b.getFoundation(Card.Suit.SPADES).push(s8);
        b.getFoundation(Card.Suit.SPADES).push(s9);
        b.getFoundation(Card.Suit.SPADES).push(s10);
        b.getFoundation(Card.Suit.SPADES).push(sj);
        b.getFoundation(Card.Suit.SPADES).push(sq);
        b.getFoundation(Card.Suit.SPADES).push(sk);
        System.out.println(b.foundationsComplete());

        ///////////// CLUBS ////////////////
        Card u1 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card u2 = new Card(Card.Rank.TWO, Card.Suit.CLUBS);
        Card u3 = new Card(Card.Rank.THREE, Card.Suit.CLUBS);
        Card u4 = new Card(Card.Rank.FOUR, Card.Suit.CLUBS);
        Card u5 = new Card(Card.Rank.FIVE, Card.Suit.CLUBS);
        Card u6 = new Card(Card.Rank.SIX, Card.Suit.CLUBS);
        Card u7 = new Card(Card.Rank.SEVEN, Card.Suit.CLUBS);
        Card u8 = new Card(Card.Rank.EIGHT, Card.Suit.CLUBS);
        Card u9 = new Card(Card.Rank.NINE, Card.Suit.CLUBS);
        Card u10 = new Card(Card.Rank.TEN, Card.Suit.CLUBS);
        Card uj = new Card(Card.Rank.JACK, Card.Suit.CLUBS);
        Card uq = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
        Card uk = new Card(Card.Rank.KING, Card.Suit.CLUBS);
        b.getFoundation(Card.Suit.CLUBS).push(u1);
        b.getFoundation(Card.Suit.CLUBS).push(u2);
        b.getFoundation(Card.Suit.CLUBS).push(u3);
        b.getFoundation(Card.Suit.CLUBS).push(u4);
        b.getFoundation(Card.Suit.CLUBS).push(u5);
        b.getFoundation(Card.Suit.CLUBS).push(u6);
        b.getFoundation(Card.Suit.CLUBS).push(u7);
        b.getFoundation(Card.Suit.CLUBS).push(u8);
        b.getFoundation(Card.Suit.CLUBS).push(u9);
        b.getFoundation(Card.Suit.CLUBS).push(u10);
        b.getFoundation(Card.Suit.CLUBS).push(uj);
        b.getFoundation(Card.Suit.CLUBS).push(uq);
        b.getFoundation(Card.Suit.CLUBS).push(uk);
        System.out.println(b.foundationsComplete());

        ///////////////// DIAMONDS /////////////////
        Card d1 = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        Card d2 = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
        Card d3 = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS);
        Card d4 = new Card(Card.Rank.FOUR, Card.Suit.DIAMONDS);
        Card d5 = new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS);
        Card d6 = new Card(Card.Rank.SIX, Card.Suit.DIAMONDS);
        Card d7 = new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS);
        Card d8 = new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS);
        Card d9 = new Card(Card.Rank.NINE, Card.Suit.DIAMONDS);
        Card d10 = new Card(Card.Rank.TEN, Card.Suit.DIAMONDS);
        Card dj = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS);
        Card dq = new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS);
        Card dk = new Card(Card.Rank.KING, Card.Suit.DIAMONDS);
        b.getFoundation(Card.Suit.DIAMONDS).push(d1);
        b.getFoundation(Card.Suit.DIAMONDS).push(d2);
        b.getFoundation(Card.Suit.DIAMONDS).push(d3);
        b.getFoundation(Card.Suit.DIAMONDS).push(d4);
        b.getFoundation(Card.Suit.DIAMONDS).push(d5);
        b.getFoundation(Card.Suit.DIAMONDS).push(d6);
        b.getFoundation(Card.Suit.DIAMONDS).push(d7);
        b.getFoundation(Card.Suit.DIAMONDS).push(d8);
        b.getFoundation(Card.Suit.DIAMONDS).push(d9);
        b.getFoundation(Card.Suit.DIAMONDS).push(d10);
        b.getFoundation(Card.Suit.DIAMONDS).push(dj);
        b.getFoundation(Card.Suit.DIAMONDS).push(dq);
        b.getFoundation(Card.Suit.DIAMONDS).push(dk);

        //////////////////// HEARTS ///////////////////
        Card h1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card h2 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card h3 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        Card h4 = new Card(Card.Rank.FOUR, Card.Suit.HEARTS);
        Card h5 = new Card(Card.Rank.FIVE, Card.Suit.HEARTS);
        Card h6 = new Card(Card.Rank.SIX, Card.Suit.HEARTS);
        Card h7 = new Card(Card.Rank.SEVEN, Card.Suit.HEARTS);
        Card h8 = new Card(Card.Rank.EIGHT, Card.Suit.HEARTS);
        Card h9 = new Card(Card.Rank.NINE, Card.Suit.HEARTS);
        Card h10 = new Card(Card.Rank.TEN, Card.Suit.HEARTS);
        Card hj = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card hq = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS);
        Card hk = new Card(Card.Rank.KING, Card.Suit.HEARTS);
        b.getFoundation(Card.Suit.HEARTS).push(h1);
        b.getFoundation(Card.Suit.HEARTS).push(h2);
        b.getFoundation(Card.Suit.HEARTS).push(h3);
        b.getFoundation(Card.Suit.HEARTS).push(h4);
        b.getFoundation(Card.Suit.HEARTS).push(h5);
        b.getFoundation(Card.Suit.HEARTS).push(h6);
        b.getFoundation(Card.Suit.HEARTS).push(h7);
        b.getFoundation(Card.Suit.HEARTS).push(h8);
        b.getFoundation(Card.Suit.HEARTS).push(h9);
        b.getFoundation(Card.Suit.HEARTS).push(h10);
        b.getFoundation(Card.Suit.HEARTS).push(hj);
        b.getFoundation(Card.Suit.HEARTS).push(hq);
        b.getFoundation(Card.Suit.HEARTS).push(hk);

        System.out.println(b.foundationsComplete()); // 4
        System.out.println(b.isGameWon()); // true

    }

}
