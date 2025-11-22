/**
 * As stated in the README file, this is where the Klondike magic happens
 * This class represents the structures of the foundation piles. Divided into 
 * 4 piles one for each suit sorted by rank from Ace(1) to King(13)!
 * 
 * Once these cards are sorted, you win the game.
 * 
 * This class represents a basic foundation list and how it would act
 * The board will construct the 4 foundation piles 
 */

import java.util.*;

public class Foundation implements Pile {
    
    private List<Card> foundation;

    public Foundation() {
        this.foundation = new ArrayList<>();
    }

    /**
     * Get size of foundation
     */
    @Override
    public int size() {
        return foundation.size();
    }

    /**
     * Empties the pile. Removes all cards
     */
    @Override
    public void clear() {
        foundation.clear();
    }

    public Foundation copy() {
        Foundation newFoundation = new Foundation();
        for (Card c : this.foundation) {
            newFoundation.push(new Card(c));
        }
        return newFoundation;
    }

    /**
     * @return true if list is empty
     */
    public boolean isEmpty() {
        if (size() == 0) return true;
        return false;
    }

    public boolean isFull() {
        if (topCard() != null 
        && topCard().getRank().getValue() == 13 
        && size() == 13) {
            return true;
        }

        return false;
    }

    @Override
    public Card topCard() {
        if (size() > 0) {
            return foundation.get(size()-1);
        }
        else return null;
    }

    public List<Card> getCards() {
        return foundation;
    }

    /**
     * This function is true when the card is 
     * same suit and 1 rank greater than the previous rank
     * it must start at Ace(1) 
     * So check if empty and handle that edge case
     * Then card must be same suit and +1 rank 
     * 
     * @param -- Card being played into foundation
     */
    @Override
    public boolean canAccept(Card card) {
        Card currentCard = topCard();

        // null check
        if (card == null) {
            return false;
        }

        // if empty only accept an ace
        if (foundation.isEmpty() && card.getRank().getValue() != 1) {
            return false;
        }

        // card must have same suit as top card
        // top card can't pt to null reference
        if (currentCard != null) {
            if (currentCard.getSuit() != card.getSuit()) {
                return false;
            }
        }

        // rank must be one higher than top card
        if (currentCard != null) {
            int difference = 0;
            difference = card.getRank().getValue() - currentCard.getRank().getValue();
            if (difference != 1) return false;
        } 
        
        return true;
    }

    @Override
    public void push(Card card) {
        if (card != null) {
            foundation.add(card);
            if (!card.isFaceUp()) {
                card.flip();
            }
        }
    }

    @Override
    public Card draw() {
        Card nextCard = null;
        if (size() > 0) {
            nextCard = topCard();
            foundation.remove(topCard());
            return nextCard;
        }
        return null;
    }

    public void remove(Card card) {
        foundation.remove(card);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < foundation.size(); i++) {
            sb.append(foundation.get(i) + "\n");
        }
        return sb.toString();
    }

    public String toDisplay() {
        if (topCard() != null) return topCard().toDisplay();
        else return "";
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


        /*
         * Cards should all be face up in the foundation!
         */

        //////////// push ////////////////////
        
        System.out.print(c1); // ace of clubs : face down
        System.out.print(c2); // king of hearts : face down
        System.out.println();

        System.out.println("Testing push");
        Foundation f1 = new Foundation();
        f1.push(c1); // ace of clubs
        f1.push(c2); // king of hearts
        f1.push(c3); // jack of hearts
        f1.push(c4); // six of spades
        f1.push(c5); // seven of diamonds
        f1.push(c6); // six of diamonds
        f1.push(c7); // four of clubs
        f1.push(c8); // jack of spades
        f1.push(c9); // jack of diamonds
        f1.push(c10); // eight of spades
        f1.push(cNullCard); // null

        System.out.println(f1); 
        System.out.println(f1.size()); // 10
        System.out.println();

        

         ////////// canAccept ///////////////
        System.out.println("Testing canAccept function: ");
        Card s1 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card s2 = new Card(Card.Rank.TWO, Card.Suit.SPADES);
        Card s3 = new Card(Card.Rank.THREE, Card.Suit.SPADES);
        Card s4 = new Card(Card.Rank.FOUR, Card.Suit.SPADES);
        Card s5 = new Card(Card.Rank.FIVE, Card.Suit.SPADES);
        Card sj = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        Card sq = new Card(Card.Rank.QUEEN, Card.Suit.SPADES);
        Card sk = new Card(Card.Rank.KING, Card.Suit.SPADES);

        Card h1 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card h2 = new Card(Card.Rank.TWO, Card.Suit.HEARTS);
        Card h3 = new Card(Card.Rank.THREE, Card.Suit.HEARTS);
        Card h4 = new Card(Card.Rank.FOUR, Card.Suit.HEARTS);
        Card h5 = new Card(Card.Rank.FIVE, Card.Suit.HEARTS);

        /*
         * Testing foundations can accept function that can only 
         * accept the first card in the pile as it being an ace
         * It must be 1 rank higher the following cards and same suit!
         */

        Foundation spades = new Foundation();
        Foundation hearts = new Foundation();

        System.out.println(spades.canAccept(s1)); // true 
        System.out.println(spades.canAccept(s3)); // false 
        System.out.println(spades.canAccept(s2)); // false

        spades.push(s1);
        System.out.println(spades.canAccept(s2)); // true
        System.out.println(spades.canAccept(s3)); // false
        spades.push(s2);
        System.out.println(spades.canAccept(s3)); // true
        System.out.println(spades.canAccept(s4)); // false
        System.out.println(spades.canAccept(h3)); // false -- diff suit'
        System.out.println(spades.canAccept(cNullCard)); // false
        System.out.println(hearts.canAccept(h1)); // true
        System.out.println(hearts.canAccept(h2)); // false
        System.out.println(hearts.canAccept(cNullCard)); // false
        System.out.println(spades.canAccept(sk)); // false
        System.out.println();
        
        // is empty
        System.out.println("isEmpty()");
        System.out.println(spades.isEmpty()); // false
        System.out.print(spades); // s1 and s2
        spades.draw();
        System.out.print(spades); // s1
        spades.draw();
        System.out.println(spades.isEmpty()); // true
        System.out.println();

        ///////// push //////////
        
        System.out.println("Testing push");
        hearts.push(cNullCard);
        hearts.push(h1);
        hearts.push(h2);
        hearts.push(h3);
        hearts.push(h4);
        hearts.push(h5);
        System.out.print(hearts); // h1, h2, h3, h4, h5 all face up
        System.out.println(hearts.size()); // 5
        System.out.println();

        //////// draw ////////
        System.out.println("TESTING DRAW");
        System.out.println(hearts.topCard()); // 5h
        Card nextCard = hearts.draw(); 
        System.out.println(nextCard); // 5h
        System.out.println();
        System.out.print(hearts);
        System.out.println(hearts.size());

        hearts.draw();
        hearts.draw();
        hearts.draw();
        System.out.print(hearts); // acehearts
        System.out.println(hearts.size()); // 1

        hearts.draw();
        System.out.println(hearts.size()); // 0

        Card theNextCard = hearts.draw();
        System.out.println(theNextCard); // null -- hearts is empty
        System.out.println(hearts.isEmpty()); // true

        Foundation fxy = new Foundation();
        System.out.println(fxy.toDisplay());
        fxy.push(c1);
        fxy.push(c2);
        System.out.println(fxy.toDisplay());


        /////////////// remove ////////////////
        System.out.println();
        System.out.println("Remove() test: ");
        System.out.println(fxy.toDisplay());
        System.out.println(fxy.size());
        fxy.remove(c2);
        System.out.println(fxy.toDisplay());
        System.out.println(fxy.size());
        fxy.remove(c1);
        System.out.println(fxy.toDisplay());
        System.out.println(fxy.size());
        fxy.remove(fxy.topCard());
        System.out.println(fxy.toDisplay());
        
    }
}
