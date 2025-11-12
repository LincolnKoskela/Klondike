
/**
 * Ask piles whether moves are legal, applies moves, 
 * and enforces the game rules. This class will be passed 
 * info from the Play.java class, and Play.java will talk 
 * to the user/player.
 * 
 */

import java.util.*;

public class GameEngine {

    private Board board;
    private Deck deck;
    private boolean gameOver;
    private int count; // count the cards

    private boolean success; // if the move was successful
    private String lastMoveDescription; 



    // constructor
    public GameEngine(boolean shuffled) {
        this.board = new Board();
        this.deck = new Deck(shuffled);
        this.gameOver = false;
        this.count = 0;
    }

    // get board
    public Board getBoard () {
        return board;
    }

    public boolean getSuccess() {
        return success;
    }

    // public String lastMoveDescription() {
    //     if (getSuccess() == true) {
    //         // record last move
    //     } else {
            
    //     }
    // }

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
                    board.getTableau(column).head().flip();
                    if (column < 7) {
                        column++;
                    }
                }
                if (index == 28)
                    tabsFilled = true; // end filling tabs
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
            success = false;
            lastMoveDescription = "Stock is not empty. Cannot recycle.";
        } else {
            // while waste is not empty, draw card from waste push stock
            while (!board.getWaste().isEmpty())
                board.getStock().push(board.getWaste().draw());
                success = true;
                lastMoveDescription = "Recycled.";
        }
    }

    /**
     * This function validates the users tab moves.
     * You only need to validate the head of the move since if there is
     * cards below the head, they've already been validated
     * 
     * @param source    is the tableau column user wants to select from
     * @param sourceRow is the row of the source column to get the card(s)
     * @param dest      is the destination column of where the user wants to place
     *                  the card(s)
     * 
     * @return true if destination canAccept the card from the source
     */
    public boolean canMove(int source, int sourceRow, int dest) {
        Tableau s = board.getTableau(source); // source tab
        Tableau d = board.getTableau(dest);
        

        if (sourceRow < 0) {
            sourceRow = 0;
        }

        Card card = s.getCard(sourceRow);
        if (!card.isFaceUp()) {
            lastMoveDescription = "Invalid selection. Can't move face down card.";
            success = false;
            return false;
        }

        if (!d.canAccept(card)) {
            lastMoveDescription = "Destination can't accept.";
            success = false;
            return false;
        }
        success = true;
        return true;
    }

    /**
     * This function moves the card(s) from one tableau to another tableau if
     * the canMove() function says so.
     * 
     * @param source    is the tab column getting cards from
     * @param sourceRow is the row in the source column. Will give us the card
     * @param dest      is the destination column
     * 
     * @var int bottom: bottom of column. The variable is used in for loop for when
     *      to stop iterating
     *      through the size of the source tableau.
     * 
     * @var Card flipCard: is card variable to determine if the card needs to be
     *      flipped
     *      post tableau move
     */
    public void move(int source, int sourceRow, int dest) {
        int bottom = board.getTableau(source).size();

        List<Card> list = board.getTableau(source).sublist(sourceRow, bottom);

        if (canMove(source, sourceRow, dest)) {
            board.getTableau(dest).push(list);
            board.getTableau(source).remove(list);

            if (sourceRow > 0) { // avoids out of bounds exception
                Card flipCard = board.getTableau(source).getCard(sourceRow - 1);
                if (!flipCard.isFaceUp()) {
                    flipCard.flip();
                }
            }
        } else {
            System.out.println("Invalid move. ");
        }
    }

    public void moveWastetoFoundation(Card.Suit suit) {
        Card card = board.getWaste().topCard();
        if (board.getFoundation(suit).canAccept(card)) {
            board.getFoundation(suit).push(card);
            board.getWaste().remove(card);
        }
    }

    public void moveWasteToTableau(int dest) {
        Card card = board.getWaste().topCard();
        if (board.getTableau(dest).canAccept(card)) {
            board.getTableau(dest).push(card);
            board.getWaste().remove(card);
        }
    }

    public void moveFoundationToTableau(Card.Suit suit, int dest) {
        Card card = board.getFoundation(suit).topCard();
        if (board.getTableau(dest).canAccept(card)) {
            board.getTableau(dest).push(card);
            board.getFoundation(suit).remove(card);
        }
    }

    public void moveTableauToFoundation(int source, Card.Suit suit) {
        int sourceRow = board.getTableau(source).size() - 1; // last card in a tableau 
        Card card = board.getTableau(source).getCard(sourceRow);

        if (board.getFoundation(suit).canAccept(card)) {
            board.getFoundation(suit).push(card);
            board.getTableau(source).remove(card);
            if (sourceRow > 0) {
                Card flipMe = board.getTableau(source).getCard(sourceRow - 1);
                if (!flipMe.isFaceUp()) {
                    flipMe.flip();
                }
            }
        }
    }

    /**
     * A players draw from stock to waste
     */
    public void draw() {
        Card card = board.getStock().draw();
        board.getWaste().push(card);
    }

    /**
     * @return true if foundations are full
     */
    public boolean isGameOver() {
        if (board.isGameWon() == true) {
            gameOver = true;
        } else
            gameOver = false;

        return gameOver;
    }

    /**
     * Board
     */
    @Override
    public String toString() {
        return board.toString();
    }

    public static void main(String[] args) {

        ////////// test out the initial deal ///////////////
        GameEngine game = new GameEngine(true);
        game.dealNewGame();
        System.out.println(game);
        System.out.println(game.count); // 52
        System.out.println(game.board.getStock().size());
        int tableauSize = 0;
        for (int i = 1; i <= game.board.getColumns(); i++) {
            tableauSize += game.board.getTableau(i).size();
        }

        System.out.println(tableauSize);
        System.out.println();

        ///////////////// seed the board to manually test/////////////////////
        GameEngine test = new GameEngine(false);
        test.dealNewGame();
        System.out.println(test);

        /*
         * Test recycle:
         * 1) is stock empty?
         * 2) empty stock
         * 3) recycle()
         * 4) is stock full of cards from waste?
         */

        // test.recycle(); this throws IllegalArgumentException [good]
        System.out.println(test.board.getStock().size()); // 24
        Card nextCard = null;
        while (!test.board.getStock().isEmpty()) { // while stock is NOT empty
            nextCard = test.board.getStock().draw();
            test.board.getWaste().push(nextCard);
        }

        System.out.println("Stock size after emptying: " + test.board.getStock().size()); // 0
        System.out.println("Waste size after pushing: " + test.board.getWaste().size()); // 24

        test.recycle();

        System.out.println();
        System.out.println("Stock size: " + test.board.getStock().size()); // 24
        System.out.println("Waste size: " + test.board.getWaste().size()); // 0

        // fill up the SPADES
        test.draw();
        System.out.println(test);
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.draw();
        System.out.println(test);

        // FILL UP THE DIAMONDS
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        System.out.println(test);
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        test.draw();
        System.out.println(test);
        test.moveTableauToFoundation(7, Card.Suit.DIAMONDS);
        System.out.println(test);
        test.move(5, 4, 7);
        System.out.println(test);

        // now its just pure klondike sorting
        test.moveFoundationToTableau(Card.Suit.DIAMONDS, 5);
        System.out.println(test);
        test.move(2, 1, 7);
        System.out.println(test);
        test.moveTableauToFoundation(5, Card.Suit.DIAMONDS);
        test.move(2, 0, 5);
        System.out.println(test); // got an out of bounds error [fixed] -- was trying to flip card at index -1

        test.move(5, 3, 2);
        System.out.println(test);
        test.moveTableauToFoundation(5, Card.Suit.HEARTS);
        System.out.println(test);
        System.out.println(test.isGameOver()); // false

        test.moveTableauToFoundation(5, Card.Suit.HEARTS);
        test.moveTableauToFoundation(5, Card.Suit.HEARTS);
        System.out.println(test);

        test.move(7, 5, 5);
        System.out.println(test);
        test.moveTableauToFoundation(7, Card.Suit.CLUBS);
        System.out.println(test);
        test.moveTableauToFoundation(7, Card.Suit.CLUBS);
        test.moveTableauToFoundation(7, Card.Suit.CLUBS);
        test.moveTableauToFoundation(7, Card.Suit.CLUBS);
        test.moveTableauToFoundation(7, Card.Suit.CLUBS);
        System.out.println(test);
        test.moveTableauToFoundation(6, Card.Suit.CLUBS);
        test.moveTableauToFoundation(6, Card.Suit.CLUBS);
        test.moveTableauToFoundation(6, Card.Suit.CLUBS);
        test.moveTableauToFoundation(6, Card.Suit.CLUBS);
        test.moveTableauToFoundation(6, Card.Suit.CLUBS);
        test.moveTableauToFoundation(6, Card.Suit.CLUBS);
        System.out.println(test);
        System.out.println(test.isGameOver()); // false
        test.moveTableauToFoundation(4, Card.Suit.HEARTS);
        test.moveTableauToFoundation(4, Card.Suit.HEARTS);
        test.moveTableauToFoundation(4, Card.Suit.HEARTS);
        test.moveTableauToFoundation(4, Card.Suit.HEARTS);
        System.out.println(test);
        test.moveTableauToFoundation(3, Card.Suit.HEARTS);
        test.moveTableauToFoundation(3, Card.Suit.HEARTS);
        test.moveTableauToFoundation(3, Card.Suit.HEARTS);
        System.out.println(test);
        test.moveTableauToFoundation(5, Card.Suit.HEARTS);
        System.out.println(test);
        test.moveTableauToFoundation(5, Card.Suit.CLUBS);
        System.out.println(test);
        test.moveTableauToFoundation(2, Card.Suit.HEARTS);
        test.moveTableauToFoundation(1, Card.Suit.HEARTS);
        System.out.println(test);
        test.moveTableauToFoundation(2, Card.Suit.CLUBS);
        System.out.println(test.isGameOver()); // false
        System.out.println();

        test.moveTableauToFoundation(5, Card.Suit.DIAMONDS);
        System.out.println(test);
        System.out.println(test.isGameOver()); // true
    }
}
