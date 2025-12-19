package klondike.sandbox;

import klondike.*;

public class GameController {
    private int selectedSourceCol = -1;
    private int selectedSourceRow = -1;

    private final GameEngine engine;
    private final BoardView boardView;

    public GameController(GameEngine engine, BoardView boardView) {
        this.engine = engine;
        this.boardView = boardView;
    }

    public void handleCardSelection(Pile pile, int index, int col) {
        // selection logic 
        // then: boardView.redraw()
    }
}
