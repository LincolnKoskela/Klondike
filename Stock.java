/**
 * What is the stock pile? 
 * The stock pile starts with cards face down. 
 * Draw from the top card in the stock pile. With player 
 * decision, player can choose to play the card or put into waste pile
 * 
 * Card starts face down, draw makes card face up, choices 
 * are to play card, do different piles on the board, 
 * such as foundation piles, tableau piles, or the waste pile
 */

import java.util.*;

public class Stock implements Pile {
    private List<Card> stock;

    // constuct a stock pile 
    public Stock() {
        this.stock = new ArrayList<>();
    }

    public int size() {
        return stock.size();
    }

    /**
     * Stock card can't accept cards from: foundation, tableau
     * Stock cards can only accept cards from the waste once the 
     * size of the stock cards hits 0, the pile will 'recycle' cards
     * 
     * for this stock class, since cards won't be moving in and out of stock 
     * (only on special occasions) we'll leave this false
     */
    @Override
    public boolean canAccept() {
        return false;
    }

    /**
     * @param -- List of cards being recycled (added to stock pile)
     * This function adds cards back into stock pile in preserve order (non-reverse)
     * and ensures the cards are faced down
     */
    @Override
    public void push(List<Card> cards) {
        Card card = null;
        for (int i = 0; i < cards.size(); i++) {
            card = cards.get(i);
            if (card.isFaceUp()) {
                card.flip();
            }
            stock.add(card);
        }
    }

    /**
     * draw card from the top of the deck
     * the last element in the index
     * pop()
     * then remove that card from this pile
     * return nextCard obj
     */
    @Override
    public Card draw() {
        Card nextCard = null;
        if (size() > 0) {
            nextCard = stock.get(size()-1);
            stock.remove(size()-1);
            return nextCard;
        }

        return null;
    }
}
