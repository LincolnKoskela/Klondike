package klondike.sandbox;

import klondike.*;

/**
 * This class interprets sequences of user actions, 
 * owns selection.
 */
public class GameController {

    // pile & index
    private Pile selectedPile = null;
    private int selectedIndex = -1;

    /**
     * This function uses GameControllers private fields to 
     * compare its selectedPile to @param pile and selectedIndex
     * to @param idx to see if its a new selection or a deselection. 
     * 
     * User clicks on a cardView, the pileview detects the click, 
     * then the this function updates the selection state.
     * The Boardview will redraw().
     */
    public void handleCardsClicked(Pile pile, int idx) {

        if (selectedPile == pile && selectedIndex == idx) {
            selectedPile = null;
            selectedIndex = -1;
            System.out.println("Deselected Index: " + idx);
        } else {
            selectedPile = pile;
            selectedIndex = idx;
            System.out.println("Selected Index: " + idx);
        }
    }

    public boolean isSelected(Pile pile, int idx) {
        return selectedPile == pile && selectedIndex == idx;
    }

    public boolean hasSelection() {
        return selectedPile != null && selectedIndex >= 0;
    }

    public Pile getPileSelected() {
        return selectedPile;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void clearSelection() {
        selectedPile = null;
        selectedIndex = -1;
    }
}
