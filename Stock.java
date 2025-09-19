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
     * size of the stock cards hits 0, the pile will then take on the cards
     * in the waste pile and reset the waste pile
     */
    @Override
    public boolean canAccept() {
        
    }

    /*
     * Initial draw is from the start of the game, when the tableau's are dealt
     * This helper method for canAccept that will give an acception to 
     * accepting cards from the tableau that weren't deal at the start of the game
     */
    public boolean initialDraw() {
        return true;
    }

    /**
     * places (pushes) card onto the pile
     * this will only happen in the stock pile when the size = 0
     * then it'll take the cards back from the waste pile
     */
    @Override
    public void push() {
        if (size() == 0) {
            
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
