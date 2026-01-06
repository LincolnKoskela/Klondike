package klondike.ui;
import klondike.*;

public class StockView extends PileView {
    
    public StockView(Pile pile) {
        super(pile);
    }


    /**
     * layout specific to pile. Only top card visually matters
     */
    @Override
    protected void layoutCards() {
        if (!cardViews.isEmpty()) {
            cardViews.get(cardViews.size() - 1).relocate(0, 0);
        }
    }
}
