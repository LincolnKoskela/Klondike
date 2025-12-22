package klondike.sandbox;

import klondike.*;

/**
 * This class interprets sequences of user actions
 */
public class GameController {

    // pile & index
    private Pile selectedPile = null;
    private int selectedIndex = -1;

    public void handleCardsClicked(Pile pile, int idx) {

        if (selectedPile == pile && selectedIndex == idx) {
            selectedPile = null;
            selectedIndex = -1;
        } else {
            selectedPile = pile;
            selectedIndex = idx;
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
