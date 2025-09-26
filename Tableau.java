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
     * Top card on the pile
     * @return -- Card obj
     */
    public Card topCard() {
        return tableau.get(size()-1);
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
        if (currentCard.getSuit().getColor() == card.getSuit().getColor()) {
            return false;
        }

        // top card should be one rank higher than card being played, so a difference of 1
        int difference = currentCard.getRank().getValue() - card.getRank().getValue();
        if (difference != 1) { // if not differnce of 1, return false
            return false;
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
            if (!topCard().isFaceUp()) { // if the card is face down flip it
                topCard().flip();
            }
            return nextCard;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tableau.size(); i++) {
            sb.append(tableau.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        
    }
}
