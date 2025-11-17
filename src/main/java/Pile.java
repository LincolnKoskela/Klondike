/**
 * Lays out the neccesary functions our pile classes will 
 * need to perform. The pile classes will implement me.
 * The 4 pile classes are Tableau, Foundation, Stock, and Waste
 * Each pile class agrees to these functions but implements the 
 * rules differently
 * 
 * Override these functions
 * you may need to Overload these functions as well
 */

import java.util.*;

public interface Pile {
    
    public int size(); // return size of pile

    public boolean canAccept(Card card); // is card allowed into pile

    public void push(Card card); // place card onto the pile

    public Card draw(); // remove top card from pile returning type Card

    public boolean isEmpty(); // is pile empty

    public void clear(); // empties piles

    public Card topCard(); // get top card

    public List<Card> getCards(); // get each card in the pile

}
