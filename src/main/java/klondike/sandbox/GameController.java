package klondike.sandbox;

import klondike.*;

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

    public void handleCardSelection(Pile pile, int index, int col) {
        Card card = pile.getCards().get(index);
    }
}
