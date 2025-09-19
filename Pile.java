/**
 * Lays out the neccesary methods our piles will 
 * need to perform. The pile classes will implement me
 * The 4 piles are Tableau, Foundation, Stock, and Waste
 * Each pile agrees to these functions but implements the 
 * rules differently
 */

public interface Pile {
    
    public int size(); // return size of pile

    public boolean canAccept(); // is card allowed into pile

    public void push(); // place card onto the pile

    public Card draw(); // remove top card from pile

}
