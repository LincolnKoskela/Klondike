import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single tableau pile current state, and defines 
 * its rules on how it acts, and what it can accept in Klondike.
 */

public class Tableau implements Pile {
    
    private List<Card> tableau;

    public Tableau() {
        this.tableau = new ArrayList<>();
    }

    /**
     * Return size of tableau pile
     */
    @Override
    public int size() {
        return tableau.size();
    }

    public boolean isEmpty() {
        if (size() == 0) return true;
        else return false;
    }

    /**
     * Top card on the pile
     * @return -- Card obj
     */
    public Card topCard() {
        if (size() > 0) {
            return tableau.get(size()-1);
        } else return null;
        
    }

    /**
     * What can a tableau pile accept? 
     * Alternating color
     * One rank lower than currentCard
     */
    @Override
    public boolean canAccept(Card card) {
        Card currentCard = topCard(); // card being compared
        if (card == null) {
            return false;
        }

        // card can't have same color, must alternate colors between red and black
        if(currentCard != null) {
            if (currentCard.getSuit().getColor() == card.getSuit().getColor()) {
            return false;
            }
        }
        

        // top card should be one rank higher than card being played, so a difference of 1
        if (currentCard != null) {
            int difference = 0;
            difference = currentCard.getRank().getValue() - card.getRank().getValue(); 
            if (difference != 1) { // if not differnce of 1, return false
                return false;
            }
        }

        return true;
    }

    @Override
    public void push(Card card) {
        if (card != null) {
            tableau.add(card);
            if (!card.isFaceUp()) {
                card.flip();
            }
        }
    }

    /**
     * Think of this as removing the card from tableau
     * like a pop() function
     * In the tableau
     */
    @Override 
    public Card draw() {
        Card nextCard = null;
        if (size() > 0) {
            nextCard = topCard();
            tableau.remove(topCard());
            if (topCard() != null) {
                if (!topCard().isFaceUp()) { // if the card is face down flip it
                topCard().flip();
                }
            }
            
            return nextCard;
        }
        return null;
    }

    /**
     * String representation of a tableau pile
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableau.size(); i++) {
            sb.append(tableau.get(i) + "\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ////////////////////// TESTING Foundation //////////////////
        ///                                                 ///
        ///                                                 ///
        //////////////////////// CARDS ////////////////////////
        Card c1 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card c2 = new Card(Card.Rank.KING, Card.Suit.HEARTS);
        Card c3 = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card c4 = new Card(Card.Rank.SIX, Card.Suit.SPADES);
        Card c5 = new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS);
        Card c6 = new Card(Card.Rank.SIX, Card.Suit.DIAMONDS);
        Card c7 = new Card(Card.Rank.FOUR, Card.Suit.CLUBS);
        Card c8 = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        Card c9 = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS);
        Card c10 = new Card(Card.Rank.EIGHT, Card.Suit.SPADES);
        Card cNullCard = null; // shouldn't push this card into pile


        //////////////////// push! //////////////////////////
        System.out.println("Testing push");
        Tableau t1 = new Tableau();
        t1.push(c1);
        t1.push(c2);
        t1.push(c3);
        t1.push(c4);
        t1.push(c5);
        t1.push(c6);
        t1.push(c7);
        t1.push(c8);
        t1.push(c9);
        t1.push(c10);
        t1.push(cNullCard);
        System.out.println(t1); 
        System.out.println("push method working");
        System.out.println();


        ////////// canaccept/ /////////
        System.out.println("TESTING CANACCEPT");

        Tableau tab = new Tableau();
        // alterating color and -1 rank 
        Card x1 = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS);
        Card x2 = new Card(Card.Rank.TEN, Card.Suit.SPADES);
        Card x3 = new Card(Card.Rank.NINE, Card.Suit.HEARTS);
        Card x4 = new Card(Card.Rank.EIGHT, Card.Suit.CLUBS);
        Card x5 = new Card(Card.Rank.SEVEN, Card.Suit.HEARTS);

        // same colors and correct rank change
        Card x6 = new Card(Card.Rank.KING, Card.Suit.SPADES);
        Card x7 = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
        Card x8 = new Card(Card.Rank.QUEEN, Card.Suit.SPADES);
        Card x9 = new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS);
        System.out.println(tab.isEmpty()); // true
        System.out.println(tab.canAccept(x1)); // true
        tab.push(x1);
        System.out.print(tab); // jd
        System.out.println(tab.canAccept(x2)); // true
        tab.push(x2);
        System.out.println(tab.canAccept(x3)); // true
        System.out.println(tab.canAccept(x4)); // false
        System.out.println(tab.canAccept(cNullCard)); // false

        Tableau tab2 = new Tableau();
        System.out.println(tab2.canAccept(x6)); // true
        tab2.push(x6);
        System.out.println(tab2.canAccept(x7)); // false
        System.out.println(tab2.canAccept(x8)); // false
        System.out.println(tab2.canAccept(x9)); // true



        //////// test the draw function ///////////

        tab2.draw();
        System.out.println(tab2.size()); // 0

        Card next = tab2.draw();
        System.out.println(next); // null

        


        


    }
}
