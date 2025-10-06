/**
 * Ask piles whether moves are legal, applies moves, 
 * and enforces the game rules
 * 
 * 
 * extra notes : 
 * 
 * -- tableau pile moves 
 * -- validate foundation piles stacked correctly by suit and rank when added canAccept then add
 */

public class GameEngine {

    private Board board;
    private Deck deck;
    
    public GameEngine () {
        this.board = new Board();
        this.deck = new Deck();
    }

    
}
