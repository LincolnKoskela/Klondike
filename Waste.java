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
        waste.add(card);
        if(!card.isFaceUp()) {
            card.flip();
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
        if (size() < 0) {
            return null;
        }
        nextCard = waste.get(size()-1);
        waste.remove(size()-1);
        return nextCard;

    }
}
