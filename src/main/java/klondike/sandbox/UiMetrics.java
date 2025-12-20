package klondike.sandbox;
public final class UiMetrics {
    private UiMetrics() {}

    /**
     * This keeps consistent layout of the board. with spacing between the CardViews
     * and the PileViews. Also tracks the dimensions of the cards to keep dimensions 
     * consistent as well. This will help prevent any deadspace on the board!
     */
    public static final double CARD_W = 70; // dimensions of cards width
    public static final double CARD_H = 100; // dimensions of cards height
    public static final double PILE_GAP_X = 20; // space in btween each horizontal pile
    public static final double PILE_GAP_Y = 30; // space that will separate vertically, like stock from tabs
    public static final double TABLEAU_Y_OFFSET = 30; // cards being offset in the tableaus
}
