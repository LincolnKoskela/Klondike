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

        // 1) if theres no selection, then select
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

        /**
         * 3) otherwise -> perform engine moves
         * 
         * @param selectedPile (Pile from)
         * @param selectedIndex(int fromIndex)
         * @param clickedPile (Pile to)
         * 
         * boolean moved uses tryMove function to determine if the move
         * succeeded, returning true, if so, clear selection
         */ 
        boolean moved = tryMove(selectedPile, selectedIndex, clickedPile);

        if (moved) {
            clearSelection();
        } else {
            select(clickedPile, clickedIndex);
        }

        boardView.redraw();
    }

    public boolean isSelected(Pile pile, int idx) {
        return selectedPile == pile && selectedIndex == idx;
    }

    public boolean isInSelectedRun(Pile pile, int idx) {
        if (!(pile instanceof Tableau)) return isSelected(pile, idx);

        if (pile != selectedPile) return false;
        if (selectedIndex < 0) return false;

        return idx >= selectedIndex;
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

    /**
     * Function returns boolean value by checking if the move executed. 
     * The move may or may not execute. The GameEngine validates moves.
     * This function checks whether the size of the piles changed and if 
     * they did, that means the move executed. 
     * 
     * This boolean will be used in handleCardClicked function to determine
     * if the move was executed, and if so, clear selection because theres 
     * no more pending moves. If false, that the move didn't execute, treat
     * the move tried as a new selection.
     *  
     * @param from is the pile being selected
     * @param fromIndex index on a specific pile, used in tableaus
     * @param to is the destination to move the pile to
     * @return true if the move executed, false if the move did NOT execute.
     */
    private boolean tryMove(Pile from, int fromIndex, Pile to) {
        PileRegistry registry = boardView.getRegistry();

        // TABLEAU -> TABLEAU
        if (from instanceof Tableau && to instanceof Tableau) {
            Integer src = registry.getTableauCol(from);
            Integer dest = registry.getTableauCol(to);

            if (src == null || dest == null) return false;

            if (!engine.canMove(src, fromIndex, dest)) return false;

            int srcBefore = ((Tableau) from).size();
            int destBefore = ((Tableau) to).size();

            engine.move(src, fromIndex, dest);

            int srcAfter = ((Tableau) from).size();
            int destAfter = ((Tableau) to).size();

            // if move happened, source should shrink and dest should grow
            return (srcAfter < srcBefore) && (destAfter > destBefore);
        }

        // WASTE -> TABLEAU
        if (from instanceof Waste && to instanceof Tableau) {
            Integer dest = registry.getTableauCol(to);
            if (dest == null) return false;

            int wasteBefore = ((Waste) from).size();
            int destBefore = ((Tableau) to).size();

            engine.moveWasteToTableau(dest);

            int wasteAfter = ((Waste) from).size();
            int destAfter = ((Tableau) to).size();

            return (wasteBefore == wasteAfter -1) && (destAfter == destBefore + 1);
        }

        // WASTE -> FOUNDATION
        if (from instanceof Waste && to instanceof Foundation) {
            Card.Suit suit = registry.getFoundationSuit(to);

            if (suit == null) return false;

            int wasteBefore = ((Waste) from).size();
            int foundationBefore = ((Foundation) to).size();

            engine.moveWastetoFoundation(suit);

            int wasteAfter = ((Waste) from).size();
            int foundationAfter = ((Foundation) to).size();

            return (wasteBefore == wasteAfter -1) && (foundationAfter == foundationBefore + 1);
        }

        // TABLEAU -> FOUNDATION
        if (from instanceof Tableau && to instanceof Foundation) {
            Integer src = registry.getTableauCol(from);
            Card.Suit suit = registry.getFoundationSuit(to);

            if (src == null || suit == null) return false;

            int tableauBefore = ((Tableau) from).size();
            int foundationBefore = ((Foundation) to).size();

            engine.moveTableauToFoundation(src, suit);

            int tableauAfter = ((Tableau) from).size();
            int foundationAfter = ((Foundation) to).size();

            return (tableauBefore == tableauAfter - 1) && (foundationAfter == foundationBefore + 1);
        }

        // FOUNDATION -> TABLEAU
        if (from instanceof Foundation && to instanceof Tableau) {
            Card.Suit suit = registry.getFoundationSuit(from);
            Integer dest = registry.getTableauCol(to);

            if (suit == null || dest == null) return false;

            int foundationBefore = ((Foundation) from).size();
            int tableauBefore = ((Tableau) to).size();

            engine.moveFoundationToTableau(suit, dest);

            int foundationAfter = ((Foundation) from).size();
            int tableauAfter = ((Tableau) to).size();

            return (foundationBefore == foundationAfter -1) && (tableauAfter == tableauBefore +1);
        }

        return false;
        
    }

    
    
}
