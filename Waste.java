/**
 * The waste pile is where cards go from the stock if not played
 * on the tableau or the foundation
 * 
 * This class provides the basic functions of a waste pile.
 * Cards can be recycled from waste back into Stock pile in
 * preserved order.
 */

import java.util.*;

public class Waste implements Pile {
    
    private List<Card> waste;

    public Waste() {
        this.waste = new ArrayList<>();
    }

    @Override
    public int size() {
        return waste.size();
    }

    /**
     * Empties the pile. Removes all cards
     */
    @Override
    public void clear() {
        waste.clear();
    }

    @Override
    public boolean isEmpty() {
        if (size() == 0) return true;
        else return false;
    }

    /**
     * Game engine will handle logic if card from stock isn't played
     * This function provides basic accepting rules into waste
     * Only cards played into waste are from stock
     */
    @Override
    public boolean canAccept(Card card) {
        return false;
    }

    /**
     * Game engine will handle pushing the cards into the stock.
     * For this function we just determine how the waste would 
     * add the cards. Flip the card face up
     */
    @Override
    public void push(Card card) {
        if (card != null) {
            waste.add(card);
            if(!card.isFaceUp()) {
                card.flip();
            }
        }
    }

    /**
     * Yes you can draw a card from the waste
     * Game engine will handle the the instances when you can
     * This function draws a card from the waste pile. 
     * It does NOT handle when you could, where it would go
     * etc etc. Just if this draw method is called from the waste pile,
     * draw that card from the waste pile
     * 
     * Return top card from the waste pile
     * If no cards in Waste, calling this method from waste returns null
     * Remove card drawn from the waste
     */
    @Override
    public Card draw() {
        Card nextCard = null;
        if (size() > 0) {
            nextCard = waste.get(size()-1);
            waste.remove(size()-1);
            return nextCard;
        }
        return null;
        
    }

    /**
     * String representation of a waste pile
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < waste.size(); i++) {
            sb.append(waste.get(i) + "\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        ////////////////////// TESTING WASTE //////////////////
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


        ////////////////// push //////////////////////////
        System.out.println("*****Testing push******");

        System.out.print(c1); // ace of clubs : face down
        System.out.print(c2); // king of hearts : face down
        System.out.println();

        System.out.println("Waste: ");
        Waste waste = new Waste();
        waste.push(c1); // ace of clubs
        waste.push(c2); // king of hearts
        waste.push(c3); // jack of hearts
        waste.push(c4); // six of spades
        waste.push(c5); // seven of diamonds
        waste.push(c6); // six of diamonds
        waste.push(c7); // four of clubs
        waste.push(c8); // jack of spades
        waste.push(c9); // jack of diamonds
        waste.push(c10); // eight of spades
        waste.push(cNullCard); // null

        System.out.println(waste);
        System.out.println("Size: " + waste.size()); // 10
        System.out.println();


        ////////////// can accept /////////////////
        System.out.println("-----Testing canAccept function------");
        System.out.println(waste.canAccept(c1)); // false
        System.out.println(waste.canAccept(c2)); // fasle
        System.out.println(waste.canAccept(c3)); // false
        System.out.println(waste.canAccept(c4)); // false
        System.out.println(waste.canAccept(c5)); // false
        System.out.println(waste.canAccept(c6)); // false
        System.out.println();


        //////// draw //////////
        System.out.println("*****Testing draw function*****");
        Card nextCard = waste.draw();
        System.out.print(nextCard); // eight of spades
        System.out.println(waste.size()); // 9
        
        nextCard = waste.draw();
        System.out.print(nextCard); // jack of diamonds
        System.out.println(waste.size()); // 8
        System.out.println();

        Waste waste2 = new Waste();
        waste2.push(c1); // ace of clubs 
        waste2.push(c2); // king of hearts

        System.out.println(waste2);
        Card myNextCard = null;
        waste2.push(myNextCard);
        System.out.println(waste2.size()); // 2
        myNextCard = waste2.draw();
        System.out.print(myNextCard); // king of hearts
        myNextCard = waste2.draw();
        System.out.print(myNextCard); // ace of clubs
        myNextCard = waste2.draw(); 
        System.out.println(myNextCard); // null
        System.out.println(waste2.size()); // 0
        
    }
}
