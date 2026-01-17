package klondike.ui;

public class UiMetrics {
    private UiMetrics() {}

    // Card dimensions
    public static final double CARD_H = 120;
    public static final double CARD_W = 80;

    // origin of board
    public static final double X0 = 60;
    public static final double Y0 = 40;

    // Space between x and y 
    public static final double GAP_X = 20;
    public static final double GAP_Y = 30;

    // others
    public static final double Y_OFFSET = 30;
    public static final double STEP_X = CARD_W + GAP_X;
    public static final double F_X = X0 + 3 * STEP_X; // foundations start at 3 xSteps
    public static final double T_Y = Y0 + (CARD_H + GAP_Y);
}
