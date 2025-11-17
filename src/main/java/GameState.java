/**
 * This class represents a frozen picture of the board at a given moment.
 * All data / no logic.
 * GameState is just a container.
 */
import java.util.*;

public class GameState {
    
    private List<List<Card>> tableauPiles;
    private List<Card> stock;
    private List<Card> waste;
    private EnumMap<Card.Suit, List<Card>> foundations;

    // construct a current gameState
    public GameState(List<List<Card>> tableauPiles, List<Card> stock, 
    List<Card> waste, EnumMap<Card.Suit, List<Card>> foundations) {

        this.tableauPiles = tableauPiles;
        this.stock = stock;
        this.waste = waste;
        this.foundations = foundations;
    }

    // getters //
    public List<List<Card>> getTabs() {return tableauPiles;}
    public List<Card> getStock() {return stock;}
    public List<Card> getWaste() {return waste;}
    public EnumMap<Card.Suit, List<Card>> getFoundations() {return foundations;}
}
