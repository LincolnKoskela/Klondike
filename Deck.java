import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class Deck {
    
    private List<Card> deck = new ArrayList<>();

    /**
     * Construct a deck of playing cards
     * shuffle the cards
     */
    public Deck() {
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                deck.add(new Card
                (Card.Rank.values()[j], 
                Card.Suit.values()[i]));
            }
        }
        shuffle();
    }

    /**
     * shuffle the deck of cards
     * use a temp card variable to swap cards
     * loop 'backwards' through the deck 
     * swap with int j and i while j takes random 
     * card position within deck
     */
    public void shuffle() {
        Random r = new Random();
        Card temp;

        for (int i = deck.size()-1; i >= 1; i--) {
            int j = r.nextInt(i + 1); // inclusive
            temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    /**
     * A riffle shuffle splits the deck in half and 
     * everyother card exchages postions back into the deck
     * 
     * just for fun
     * 
     * loop through the deck iterating every two cards
     * i and i+1 are swapped within the deck
     * deck.get(i/2) to avoid out of bounds
     */
    public void riffle() {

        // each iteration set 2 cards into deck from each sublist
        for (int i = 0; i < deck.size()-1; i+=2) {
            deck.set(i, deck.get(i/2));
            deck.set(i + 1, deck.get(i/2));
        }
    }

    /**
     * @return false if there is no duplicates
     */
    public boolean hasDuplicates() {
        Set<Card> set = new HashSet<>();
        for (int i = 0; i < deck.size(); i++) {
            set.add(deck.get(i));
        }
        return set.size() < deck.size();
    }

    public int size() {
        return deck.size();
    }

    public Card topCard() {
        if (!isEmpty()) {
            return deck.get(size()-1);
        } else {
            return null;
        }
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public Card draw() {
        Card nextCard = null;
        if (!isEmpty()) {
            nextCard = topCard();
            deck.remove(topCard());
            return nextCard;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < deck.size(); i++) {
            sb.append(deck.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck);
        System.out.println(deck.hasDuplicates());
        

    }
}
