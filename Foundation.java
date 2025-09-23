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

    @Override
    public int size() {
        return foundation.size();
    }

    /**
     * This function is true when the card is 
     * same suit and 1 rank greater than the previous rank
     * it must start at Ace(1) 
     * So check if empty and handle that edge case
     * Then card must be same suit and +1 rank 
     * 
     * @param -- Card being asked about
     */
    @Override
    public boolean canAccept(Card card) {
        Card currentCard = foundation.get(size()-1);

        if (foundation.isEmpty() && card.getRank().getValue() != 1) {
            return false;
        }
        if (currentCard.getSuit() != card.getSuit()) {
            return false;
        }

        int difference = card.getRank().getValue() - currentCard.getRank().getValue();
        if (difference != 1) {
            return false;
        }
        
        
        
    }



}
