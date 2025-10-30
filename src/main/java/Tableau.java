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

    /**
     * Empties the pile. Removes all cards
     */
    @Override
    public void clear() {
        tableau.clear();
    }

    public boolean isEmpty() {
        if (size() == 0) return true;
        else return false;
    }

    /**
     * @return Head of the tableau
     * will be used to validate moves and 
     * few other functions to help compare cards
     */
    public Card head() {
        if (size() > 0) {
            return tableau.get(size()-1);
        } else return null;
    }

    /**
     * Due to the nature of the game, the topcard for a tableau 
     * can be viewed differently than other piles, becuase the columns
     * reveal each card, the top card would be the 0 index in a tab. 
     * The head() function above acts as how you'd normally think of the topcard
     * This function will rarely be used.
     */
    @Override
    public Card topCard() {
        if (!isEmpty()) {
            return tableau.get(0);
        } else return null;
    }

    /**
     * This function can be used to get a specific card at a given index. 
     * @param index of the card in a given row or column on the tableau. 
     * @return card type
     */
    public Card getCard(int index) {
        if (tableau.get(index) != null) {
            return tableau.get(index);
        } else {
            return null;
        }
        
    }

    /**
     * What can a tableau pile accept? 
     * Alternating color
     * One rank lower than currentCard
     * 
     * If the column is empty, only accept king
     */
    @Override
    public boolean canAccept(Card card) {
        Card currentCard = head(); // card being compared
        if (card == null) {
            return false;
        }

        // first card played down must be a king on empty column
        if (isEmpty()) {
            if (card.getRank().getValue() != 13) {
                return false;
            }
        } else {
            // card can't have same color, must alternate colors between red and black
            if (currentCard != null) {
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
        }
        return true;
    }

    /**
     * Add single playing card to a tableau
     */
    @Override
    public void push(Card card) {
        if (card != null) {
            tableau.add(card);
        }
    }

    public void push(List<Card> list) {
        if (list != null) {
            for (Card card : list) {
                tableau.add(card);
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
            nextCard = head();
            tableau.remove(head());
            return nextCard;
        }
        return null;
    }

    /**
     * This function is similar to draw() except it does NOT return any value, 
     * it just simply removes the card from the tableau. This function will be used
     * to remove from one tableau and go to another tableau. Helpful if looping through 
     * cards and removing them from a source column. 
     * @param card being removed
     */
    public void remove(Card card) {
        tableau.remove(card);
    }

    public void remove(List<Card> list) {
        if (list != null) {
            for (Card card : list) {
                remove(card);
            }
        }
    }

    /**
     * This function shallow copies moving cards between piles, 
     * Use this function to move cards around in tableaus getting sublist of cards
     * from the tableau arraylist
     * @param fromIndex inclusive
     * @param toIndex inclusive
     * @return an independent list with no shared structure from original tableua
     * List can be modified throughout the GameEngine
     */
    public List<Card> sublist(int fromIndex, int toIndex) {
        List <Card> list = new ArrayList<>(tableau.subList(fromIndex, toIndex + 1));
        return list;
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

    /**
     * Represents displaying one tableau
     * @return -- players perspective view
     */
    public String toDisplay() {
        StringBuilder sb = new StringBuilder();
        if (size() == 0) return "[ ]";
        else {
            for (Card card : tableau) {
                sb.append("[" + card.toDisplay() + "]\n");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        ////////////////////// TESTING tableau //////////////////
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
        /// game engine will handle when/if push is allowed
        /// here we just care if the push action is working as it should
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
        System.out.println(tab.canAccept(x1)); // false -- not king
        tab.push(x1);
        System.out.print(tab); // jd
        System.out.println(tab.canAccept(x2)); // true -- 1 less rank and alt color
        tab.push(x2);
        System.out.println(tab.canAccept(x3)); // true
        System.out.println(tab.canAccept(x4)); // false
        System.out.println(tab.canAccept(cNullCard)); // false
        System.out.println();

        Tableau tab2 = new Tableau();
        System.out.println(tab2.isEmpty());
        System.out.println(tab2.canAccept(x6)); // true
        tab2.push(x6);
        System.out.println(tab2.canAccept(x7)); // false
        System.out.println(tab2.canAccept(x8)); // false
        System.out.println(tab2.canAccept(x9)); // true
        System.out.println();

        //////// test the draw function ///////////
        /// when you draw you're removing from the tableau
        /// within same col it's suppose to flip the topcard
        /// game engine will handle this, we just want to test
        /// removing a card from the tableau column

        System.out.println("TESTING DRAW / REMOVE function");
        System.out.print(tab2);
        tab2.draw();
        System.out.println(tab2.size()); // 0

        Card next = tab2.draw();
        System.out.println(next); // null

        tab2.push(x6);
        tab2.push(x7);
        tab2.push(x5);
        System.out.print(tab2);

        next = tab2.draw();
        System.out.println("Draw top card -> " + next); // 7h
        System.out.print(tab2);
        System.out.println(tab2.size()); // 2
        System.out.println(tab2.isEmpty()); // false

        next = tab2.draw();
        System.out.println("Draw top card -> " + next); // QC
        System.out.print(tab2); // just KS left
        System.out.println(tab2.size()); // 1
        System.out.println(tab2.isEmpty()); // false

        next = tab2.draw();
        System.out.println("Draw top card -> " + next); // KS
        System.out.println(tab2); // **blank**
        System.out.println(tab2.size()); // 0
        System.out.println(tab2.isEmpty()); // true
        System.out.println(tab2.canAccept(x6)); // true
        System.out.println(tab2.canAccept(cNullCard)); // false
        System.out.println(tab2.canAccept(x7)); // false

        System.out.println(next); // KS

        if (tab2.canAccept(next)) {
            tab2.push(next);
        }

        System.out.print(tab2); // KS
        next = tab2.draw();
        System.out.println(tab2.isEmpty()); // true

        if (tab2.canAccept(x7)) {
            tab2.push(x7);
        }

        System.out.println(tab2.isEmpty()); // true
        System.out.println();

        System.out.println("Testing toDisplay() ");
        Tableau t = new Tableau();
        t.push(c1);
        t.push(c2);
        t.push(c3);
        t.push(c4);
        c5.flip();
        t.push(c5);

        System.out.println(t.toDisplay());

        System.out.println();


        /////////// remove //////////////////
        System.out.println("Test remove Function");
        System.out.println(t.toDisplay());
        t.remove(c5);
        System.out.println(t.toDisplay());
        t.remove(c4);
        System.out.println(t.toDisplay());


        


        


    }
}
