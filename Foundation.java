/**
 * As stated in the README file, this is where the Klondike magic happens
 * This class represents the structures of the foundation piles. Divided into 
 * 4 piles one for each suit sorted by rank from Ace(1) to King(13)!
 * 
 * Once these cards are sorted, you win the game
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
     * Clean helper method to get topCard
     * @return -- Card obj 
     */
    public Card topCard() {
        return foundation.get(size()-1);
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
        if (card.getSuit() != currentCard.getSuit()) {
            return false;
        }

        // rank must be one higher than top card
        int difference = card.getRank().getValue() - currentCard.getRank().getValue();
        if (difference != 1) {
            return false;
        }

        return true;
    }

    /**
     * Assumed move is legal and performs the action
     */
    @Override
    public void push(Card card) {
        if (card != null) {
            foundation.add(card);
            if (!card.isFaceUp()) {
                card.flip();
            }
        }
    }

    /**
     * Draw a card from foundation
     * This function performs the action
     * @return Card drawn from the foundation
     */
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

    /**
     * String representation of a Foundation pile
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < foundation.size(); i++) {
            sb.append(foundation.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        
    }
}
