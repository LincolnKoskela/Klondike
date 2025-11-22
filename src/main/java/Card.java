/**
 * This class constructs a playing card 
 * using enumeration values. Card implements
 * Comparable to use the compareTo() method
 */

public class Card implements Comparable <Card> {

    /**
     * Mini enum class for Suit's
     */
    public enum Suit {
        
        // enum constants 
        SPADES("black"),
        DIAMONDS("red"),
        CLUBS("black"), 
        HEARTS("red");

        // private field
        private final String color;

        // construct an enum Suit obj
        private Suit(String color) {
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    };

    public enum Rank {
        ACE(1), TWO(2), 
        THREE(3), FOUR(4), 
        FIVE(5), SIX(6), 
        SEVEN(7), EIGHT(8), 
        NINE(9), TEN(10),
        JACK(11), QUEEN(12), 
        KING(13);

        private final int value;

        private Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Rank rank;
    private Suit suit;
    private boolean faceUp;

    // construct a playing card
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        faceUp = false;
    }

    // copy constructor
    public Card(Card card) {
        this(card.rank, card.suit);
        faceUp = card.faceUp;
    }

    // getters
    public Rank getRank() {
        return rank;
    }
    public Suit getSuit() {
        return suit;
    }

    // initially face down (false)
    public boolean isFaceUp() {
        return faceUp;
    }
    public void flip() {
        faceUp = !faceUp;
    }

    /**
     * Define logical equality of two card objects
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        
        Card card = (Card) obj;
        return this.getRank() == card.getRank()
        && this.getSuit() == card.getSuit();
    }
    
    /**
     * Check if two cards have the same rank
     */
    public boolean hasSameRank(Card other) {
        return other != null &&
        this.getRank() == other.getRank();
    }

    /**
     * Check if two cards have the same suit
     */
    public boolean hasSameSuit(Card other) {
        return other != null &&
        this.getSuit() == other.getSuit();
    }

    /**
     * defines how Card objects will be compared
     * @param other -- Card object being compared to
     * @return int value
     */
    public int compareTo(Card other) {
        if (this.getRank().getValue() > other.getRank().getValue()) {
            return 1;
        } else if (this.getRank().getValue() < other.getRank().getValue()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * String representation of a Card object
     */
    @Override
    public String toString() {
        if (faceUp) {
            return rank.getValue() + "" + suit.name().substring(0, 1);
        } else {
            return rank.getValue() + "" + suit.name().substring(0, 1) + ":X";
        }
    }

    /**
     * To represent cards from a players perspective.
     * @return -- face of cards if face up but xx if face down
     */
    public String toDisplay() {
        if (faceUp) return rank.getValue() + suit.name().substring(0, 1);
        else return "XX";
    }

    public static void main(String[] args) {

        /////////////////// Cards /////////////////////
        Card c1 = new Card(Rank.ACE, Suit.SPADES);
        Card c2 = new Card(Rank.KING, Suit.HEARTS);
        Card c3 = new Card(Rank.JACK, Suit.HEARTS);
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);

        ////////////// compareTo ///////////////////
        System.out.println(c1.compareTo(c2));
        System.out.println(c2.compareTo(c3));
        System.out.println(c2.compareTo(c3));

        //////////////// flip ////////////////////
        c1.flip();
        System.out.println(c1.isFaceUp());
        System.out.println(c2.isFaceUp());

        /////////////// sameRank //////////////////
        System.out.println(c1.hasSameRank(c3));
        Card c4 = new Card(Rank.ACE, Suit.CLUBS);
        System.out.println(c1.hasSameRank(c4));

        /////////////// equals ////////////////////
        System.out.println(c1.equals(c4));
        Card c5 = new Card(Rank.ACE, Suit.SPADES);
        System.out.println(c1.equals(c5)); 
        
        ////////////// sameSuit ///////////////////
        System.out.println();
        System.out.println("Testing hasSameSuit");
        Card c6 = new Card(Rank.TEN, Suit.SPADES);
        Card c7 = new Card(Rank.FOUR, Suit.SPADES);
        Card c8 = new Card(Rank.EIGHT, Suit.CLUBS);

        System.out.println(c6.hasSameSuit(c8)); // false
        System.out.println(c7.hasSameSuit(c8)); // false
        System.out.println(c6.hasSameSuit(c7)); // true


        //////////// get suit etc ///////////////
        System.out.println();
        
        Card x1 = new Card(Rank.ACE, Suit.SPADES);
        Card x2 = new Card(Rank.JACK, Suit.SPADES);

        System.out.println(x1.getSuit());
        System.out.println(x2.getSuit());
        System.out.println(x1.getRank());
        System.out.println(x1.getRank().getValue());
        System.out.println(x2.getRank().getValue());
        System.out.println(x1.getSuit().getColor());
        System.out.println(x2.getSuit().getColor());


        // toDisplay //

        System.out.println();
        Card a1 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card a2 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);

        System.out.println(a1.toDisplay());
        System.out.println(a2.toDisplay());
        a1.flip();
        a2.flip();
        System.out.println(a1.toDisplay());
        System.out.println(a2.toDisplay());
    }
}