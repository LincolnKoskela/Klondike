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

    private final GameEngine engine;
    private final BoardView boardView;

    public GameController(GameEngine engine, BoardView boardView) {
        this.engine = engine;
        this.boardView = boardView;
    }

    /**
     * This function uses GameControllers private fields to 
     * compare its selectedPile to @param pile and selectedIndex
     * to @param idx to see if its a new selection or a deselection. 
     * 
     * User clicks on a cardView, the pileview detects the click, 
     * then the this function updates the selection state.
     * The Boardview will redraw().
     */
    public void handleCardsClicked(Pile clickedPile, int clickedIndex) {

        // 1) nothing has been selected
        if (!hasSelection()) {
            select(clickedPile, clickedIndex);
            boardView.redraw();
            return;
        }

        // 2) deselection
        if (selectedPile == clickedPile && selectedIndex == clickedIndex) {
            clearSelection();
            boardView.redraw();
            return;
        }

        // 3) otherwise -> perform engine moves


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

    private void select(Pile pile, int idx) {
        selectedPile = pile;
        selectedIndex = idx;
    }
}
