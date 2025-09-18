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
    public boolean canAccept() {

    }

    /**
     * places card onto the pile
     * this will only happen in the stock pile when the size = 0
     * then it'll take the cards back from the waste pile
     */
    public void push() {

    }

    /**
     * draw card from the top of the deck
     */
    public void draw() {

    }


}
