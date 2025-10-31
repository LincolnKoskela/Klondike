/**
 * Ask piles whether moves are legal, applies moves, 
 * and enforces the game rules. This class will be passed 
 * info from the Play.java class, and Play.java will talk 
 * to the user/player.
 * 
 * 
 * extra notes : 
 * 
 * COLUMNS ARE 1 BASED ROWS ARE 0 BASED WILL GET THEM ON SAME PAGE IN PLAY.JAVA
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
    public GameEngine (boolean shuffled) {
        this.board = new Board();
        this.deck = new Deck(shuffled);
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
                    board.getTableau(column).head().flip(); 
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
        Tableau s = board.getTableau(source); // source tab
        Tableau d = board.getTableau(dest); 
        
        Card card = s.getCard(sourceRow);
        if (!card.isFaceUp()) {
            System.out.println("Invalid selection.");
            return false;
        }
         

        if (!d.canAccept(card)) {
            System.out.println("Destination can't accept.");
            return false;
        }
        return true;
    }

    /**
     * This function moves the card(s) from one tableau to another tableau if 
     * the canMove() function says so.
     * 
     * @param source is the tab column getting cards from
     * @param sourceRow is the row in the source column. Will give us the card
     * @param dest is the destination column
     * 
     * @var int bottom: bottom of column. The variable is used in for loop for when to stop iterating
     * through the size of the source tableau.

     * @var Card flipCard: is card variable to determine if the card needs to be flipped
     * post tableau move
     */
    public void move(int source, int sourceRow, int dest) {
        int bottom = board.getTableau(source).size();

        // sublist that's being moved (inclusv -> inclusv)
        List<Card> list = board.getTableau(source).sublist(sourceRow, bottom);
        
        if (canMove(source, sourceRow, dest)) {
            board.getTableau(dest).push(list);
            board.getTableau(source).remove(list);

            /*
            * the card before the initial card (sourceRow - 1), does it need to be fliped?
            * if its not face up, after the move, flip the card
            */ 
            Card flipCard = board.getTableau(source).getCard(sourceRow - 1);
            if (!flipCard.isFaceUp()) {
                flipCard.flip();
            }
        } else {
            throw new IllegalArgumentException("Move invalid.");
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

    public void moveTableauToFoundation(int source, int sourceRow, Card.Suit suit) {
        Card card = board.getTableau(source).getCard(sourceRow);
        if (board.getFoundation(suit).canAccept(card)) {
            board.getFoundation(suit).push(card);
            board.getTableau(source).remove(card);
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
     * @return 
     */
    // public boolean isGameOver() {
        
    // }

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

        /////////////////seed the board to manually test/////////////////////
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
        
        //////////// test out some functionalities before canMove() ///////////////////////
        test.draw(); // 1S
        test.moveWastetoFoundation(Card.Suit.SPADES);
        System.out.println(test);
        test.draw(); // 2S
        System.out.println(test);
        test.draw(); // 3S
        System.out.println(test);

        test.moveWasteToTableau(4);
        System.out.println(test); // lets go!~ it works 3S moved under 4H

        // try a failed one // nothing should happen becuase it can't accept
        test.moveWasteToTableau(4);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.draw();
        test.draw();
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(3);
        System.out.println(test); // good
        test.draw();
        test.draw();
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(2);
        System.out.println(test);
        test.draw();
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(1);
        System.out.println(test);
        test.draw();
        test.draw();
        System.out.println(test);
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(4);
        System.out.println(test);
        test.move(2, 1, 1);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWastetoFoundation(Card.Suit.DIAMONDS); // didnt work need to move from tab to foundation first
        System.out.println(test);

        test.moveTableauToFoundation(4, 5, Card.Suit.DIAMONDS);
        System.out.println(test);
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.draw();
        test.moveWasteToTableau(6);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(3);
        System.out.println(test);
        test.draw();
        test.draw();
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(1);
        System.out.println(test);
        test.draw();
        test.draw();
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.recycle();
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWastetoFoundation(Card.Suit.SPADES);
        test.moveTableauToFoundation(4, 4, Card.Suit.SPADES);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(6);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(3);
        test.move(4, 3, 3);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(1);
        System.out.println(test);
        test.draw();
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(2);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.move(2, 1, 7);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWastetoFoundation(Card.Suit.DIAMONDS);
        System.out.println(test);
        test.draw();

        System.out.println(test);
        test.moveWasteToTableau(1);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(7);
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.moveWasteToTableau(5);
        System.out.println(test);
        test.move(6, 5, 1);
        System.out.println(test);
        test.moveTableauToFoundation(1, 9, Card.Suit.SPADES);
        test.moveTableauToFoundation(1, 8, Card.Suit.SPADES);
        System.out.println(test);
        test.moveTableauToFoundation(1, 8, Card.Suit.DIAMONDS);
        System.out.println(test);
        test.move(4, 2, 1);
    
        System.out.println(test);
        test.moveFoundationToTableau(Card.Suit.SPADES, 1);
        System.out.println(test);

        test.moveTableauToFoundation(1, 9, Card.Suit.SPADES);
        System.out.println(test);
        test.move(4, 1, 6);
        System.out.println(test);

        test.recycle();
        System.out.println(test);
        test.draw();
        System.out.println(test);
        test.move(3, 5, 6);
        System.out.println(test);
        test.moveTableauToFoundation(3, 4, Card.Suit.DIAMONDS);
        System.out.println(test);


    }
}
