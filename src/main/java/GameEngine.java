/**
 * Ask piles whether moves are legal, applies moves, 
 * and enforces the game rules. This class will be passed 
 * info from the Play.java class, and Play.java will talk 
 * to the user/player.
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

 import java.util.*;

public class GameEngine {

    private Board board;
    private Deck deck;
    private boolean gameOver;
    private int count; // count the cards

    
    // constructor
    public GameEngine () {
        this.board = new Board();
        this.deck = new Deck();
        this.gameOver = false;
        this.count = 0;
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

        for (int index = 1; index <= 52; index++) {
            Card nextCard = deck.draw();
            // fill the tabs 
            if (tabsFilled == false) {
                board.getTableau(column).push(nextCard);
                count++;

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
                count++;
            }
        }
    }

    /**
     * You can recycle the waste pile of cards back into the stock
     * when the stock is empty. This function checks if the stock is empty
     * and if so, push all the cards from the waste into the stock. This
     * makes cards playable from the stock again. 
     * 
     * Waste Pile (face up) -> Stock pile (face down) 
     * Stock push function will flip the cards no need to do that here
     */
    public void recycle() {
        if (!board.getStock().isEmpty()) {
            throw new IllegalArgumentException("Cannot recycle: the stock is not empty.");
        } else {
            // while waste is not empty, draw card from waste push stock
            while(!board.getWaste().isEmpty())
            board.getStock().push(board.getWaste().draw());
        }
    }

    /**
     * This function validates the users tab moves. 
     * You only need to validate the head of the move since if there is 
     * cards below the head, they've already been validated
     * @param source is the tableau column user wants to select from
     * @param sourceRow is the row of the source column to get the card(s)
     * @param dest is the destination column of where the user wants to place the card(s)
     * 
     * @return true if destination canAccept the card from the source
     */
    public boolean canMove(int source, int sourceRow, int dest) {
        Tableau s = board.getTableau(source);
        Tableau d = board.getTableau(dest);
        
        Card card = s.getCard(sourceRow);
        if (!card.isFaceUp()) return false;

        if (d.canAccept(card)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function moves the card(s) from one tableau to another tableau if 
     * the canMove() function says so.
     * 
     * @param source is the tab column getting cards from
     * @param sourceRow is the row in the source column. Will give us the card
     * @param dest is the destination column
     * 
     * int max: variable is used in for loop for when to stop iterating
     * through the size of the source tableau
     * Card card: getsCard at row, gets pushed to dest tableau, then removed from source tab
     * for each iteration
     * Card flipCard: is card variable to determine if the card needs to be flipped
     * post tableau move
     */
    public void move(int source, int sourceRow, int dest) {
        int max = board.getTableau(source).size();
        Card card;
        
        if (canMove(source, sourceRow, dest)) {
            for (int row = sourceRow; row <= max; row++) {
                card = board.getTableau(source).getCard(row);
                board.getTableau(dest).push(card);
                board.getTableau(source).remove(card);
            }

            /*
            * the card before the initial card (sourceRow - 1), does it need to be fliped?
            * if its not face up, after the move, flip the card
            */ 
            Card flipCard = board.getTableau(source).getCard(sourceRow - 1);
            if (!flipCard.isFaceUp()) {
                flipCard.flip();
            }
        } 
    }

    /**
     * @return true if all foundations are full
     */
    public boolean isGameWon() {
        return board.isGameWon();
    }

    @Override
    public String toString() {
        return board.toString();
    }

    public static void main(String[] args) {

        ////////// test out the initial deal ///////////////
        GameEngine game = new GameEngine();
        game.dealNewGame();
        System.out.println(game);
        System.out.println(game.count); // 52
        System.out.println(game.board.getStock().size());
        int tableauSize = 0; 
        for (int i = 1; i <= game.board.getColumns(); i++) { 
            tableauSize += game.board.getTableau(i).size();
        }

        System.out.println(tableauSize);

        /////////////////////////////////////////////////////////
        
        
        
    }
}
