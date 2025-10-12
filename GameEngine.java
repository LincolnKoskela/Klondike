/**
 * Ask piles whether moves are legal, applies moves, 
 * and enforces the game rules
 * 
 * 
 * extra notes : 
 * 
 * -- tableau pile moves, pushing a list vs a single card, tab class only handles single pushes
 * -- validate foundation piles stacked correctly by suit and rank when added canAccept then add
 * -- tableau needs to address flips in specific movements
 * -- waste and stock draw/recycle function
 * -- post move flips in the tableaus (if source is new, to flip)
 */

public class GameEngine {

    private Board board;
    private Deck deck;
    private boolean gameOver;
    
    // constructor
    public GameEngine () {
        this.board = new Board();
        this.deck = new Deck();
        this.gameOver = false;
    }

    /**
     * This function deals the deck of cards into the 7 tableau columns. 
     * The rest of the cards go into the stock. Handle the face of the cards. 
     * Only the top card in the tabs should be faced up, the rest face down. 
     * The stock cards are all face down.
     */
    public void dealNewGame() {
        int column = 1;
        int tableausAreFilled = 28; // 28 cards into tabs

        for (int i = 0; i < deck.getSize(); i++) {

            // fill the tabs 
            Card nextCard = deck.draw();
            board.getTableau(column).push(nextCard);

            if (board.getTableau(column).size() == column) {
                board.getTableau(column).topCard().flip(); // flip the top card to face up
                column++;
            }
        }
    }
}
