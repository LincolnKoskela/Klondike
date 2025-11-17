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

    @Override
    public int size() {
        return stock.size();
    }

    /**
     * Empties the pile. Removes all cards
     */
    @Override
    public void clear() {
        stock.clear();
    }

    @Override
    public boolean isEmpty() {
        if (size() == 0) return true;
        else return false;
    }

    /**
     * @return Top Card of Stock pile
     */
    @Override
    public Card topCard() {
        if (size() > 0) {
            return stock.get(size()-1);
        } else return null;
    }

    /**
     * Get all the cards in the stock pile. Provides easy access to all cards
     * in the stock.
     * @return list type card. 
     */
    public List<Card> getCards() {
        return stock;
    }

    /**
     * Stock card can't accept cards from: foundation, tableau
     * Stock cards can only accept cards from the waste once the 
     * size of the stock cards hits 0, the pile will 'recycle' cards
     * 
     * for this stock class, since cards won't be moving in and out of stock 
     * (only on special occasions) we'll leave this false and let gameEngine 
     * handle the special occasions
     */
    @Override
    public boolean canAccept(Card card) {
        return false;
    }

    /**
     * @param List of cards being recycled (added to stock pile)
     * This function adds cards back into stock pile in preserve order (non-reverse)
     * and ensures the cards are faced down
     */
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
     * Add to stock pile (push)
     */
    @Override
    public void push(Card card) {
        if (card != null) {
            stock.add(card);
        }
    }

 
    // draw from stock
    @Override
    public Card draw() {
        Card nextCard = null;
        if (size() > 0) {
            nextCard = stock.get(size()-1);
            stock.remove(size()-1);
            nextCard.flip();
            return nextCard;
        }

        return null;
    }

    /**
     * String representation of a stock pile
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stock.size(); i++) {
            sb.append(stock.get(i) + "\n");
        }
        return sb.toString();
    }

    /**
     * @return Players view of the Stock pile
     */
    public String toDisplay() {
        if (topCard() != null) {
            return "XX";
        } else return "";
    }

    public static void main(String[] args) {
        ////////////////////// TESTING /////////////////////////
        /// 
        /// 
        //////////////////////// CARDS ////////////////////////
        Card c1 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        Card c2 = new Card(Card.Rank.KING, Card.Suit.HEARTS);
        Card c3 = new Card(Card.Rank.JACK, Card.Suit.HEARTS);
        Card c4 = new Card(Card.Rank.SIX, Card.Suit.SPADES);
        Card c5 = new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS);
        Card c6 = new Card(Card.Rank.SIX, Card.Suit.DIAMONDS);
        Card c7 = new Card(Card.Rank.FOUR, Card.Suit.CLUBS);
        Card c8 = new Card(Card.Rank.JACK, Card.Suit.SPADES);
        Card c9 = new Card(Card.Rank.JACK, Card.Suit.DIAMONDS);
        Card c10 = new Card(Card.Rank.EIGHT, Card.Suit.SPADES);
        Card cNullCard = null;


        //////////////// push() ///////////////////////////
        System.out.println("----------- Testing push() function ------------");
        Stock stock = new Stock();

        c1.flip();
        c2.flip();
        c3.flip();

        // face up
        System.out.print(c1); // ace of clubs
        System.out.print(c2); // king of hearts
        System.out.print(c3); // jack of hearts

        // all face down 
        stock.push(c1); // Ace of clubs
        stock.push(c2); // King of hearts
        stock.push(c3); // Jack of hearts
        stock.push(c4); // Six of spades
        stock.push(c5); // seven of diamonds
        stock.push(c6); // six of diamonds
        stock.push(c7); // four of clubs
        stock.push(c8); // jack of spades
        stock.push(c9); // jack of diamonds
        stock.push(c10); // eight of spades
        stock.push(cNullCard); 
        System.out.println(stock); // print out the stock
        System.out.println();


        ////////////// push(List<Card> card) /////////////////
        System.out.println("----------- Testing push(List<Card>) function ------------");
        List<Card> list = new ArrayList<>();
        c1.flip();
        c2.flip();
        list.add(c1);
        list.add(c2);
        list.add(c3);

        Stock stock2 = new Stock();
        stock2.push(list);

        System.out.println(stock2); 
        // Ace of clubs : face down
        // king of hearts : face down
        // jack of hearts : face down
        System.out.println();

        ////////////////// canAccept() ///////////////
        System.out.println("-------Testing canAccept----------");
        System.out.println(stock.canAccept(c1)); // false
        System.out.println(stock.canAccept(c2)); // fasle
        System.out.println(stock.canAccept(c3)); // false
        System.out.println(stock.canAccept(c4)); // fasle
        System.out.println(stock.canAccept(c5)); // false
        System.out.println(stock.canAccept(c6)); // fasle
        // every test should be false as we assume stock won't accept any cards 
        // game engine will logically define what the stock can accept
        System.out.println();

        ///////////// draw() and size() ///////////////
        System.out.println("*****Testing draw and size functions*****");
        System.out.println(stock.size()); // 10
        Card nextCard = stock.draw();
        System.out.println(nextCard); // Eight of spades : face up
        System.out.println(stock.size()); // 9
        nextCard = stock.draw();
        System.out.println(nextCard); // Jack of diamonds : face up
        System.out.println(stock.size()); // 8
        nextCard = stock.draw(); 
        System.out.println(nextCard); // jack of spades : face up
        System.out.println(stock.size()); // 7

        System.out.println(stock2.size()); // 3
        Card myNextCard = null;
        System.out.println(myNextCard); // null
        myNextCard = stock2.draw(); 
        System.out.println(myNextCard); // jack of hearts : face up
        System.out.println(stock2.size()); // 2
        myNextCard = stock2.draw();
        myNextCard = stock2.draw();
        System.out.println(myNextCard); // ace of clubs : face up
        System.out.println(stock2.size()); // 0

        myNextCard = stock2.draw(); 
        System.out.println(myNextCard); // null
        System.out.println(stock2.size()); // 0

        System.out.println(stock.isEmpty()); // false
        System.out.println(stock2.isEmpty()); // true

        stock2.push(c1);
        stock2.push(c2);
        stock2.push(c3);
        System.out.println(stock2.topCard());
        System.out.println(stock2.toDisplay());
    }
}
