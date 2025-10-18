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
     * 
     * Loop through the deck. Fill each tableau column until the column equals size.
     * Flip that last card and move to the next column. Once all tabs are filled, 
     * move the rest of the deck to the stock pile. 
     */
    public void dealNewGame() {
        int column = 1;
        boolean tabsFilled = false;

        for (int index = 0; index < 52; index++) {
            Card nextCard = deck.draw();
            // fill the tabs 
            if (tabsFilled == false) {
                board.getTableau(column).push(nextCard);

                // flip the top card and move to next column
                if (board.getTableau(column).size() == column) {
                    board.getTableau(column).topCard().flip(); 
                    if (column < 7) {
                        column++;
                    }
                }
                if (index == 28) tabsFilled = true; // end filling tabs
            } else {
                // fill the stock
                board.getStock().push(nextCard);
            }
        }
    }

    @Override
    public String toString() {
        return board.toString();
    }

    public static void main(String[] args) {
        GameEngine game = new GameEngine();
        game.dealNewGame();
        System.out.println(game);
        
        
    }
}
