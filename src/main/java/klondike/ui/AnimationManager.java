package klondike.ui;

import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.scene.effect.DropShadow;

/**
 * Animation Manager = UI choreography helper.
 * BoardView stays the director (handle clicks + engine calls),
 * while this class handles: lift -> travel -> placement
 */
public class AnimationManager {
    
    private final Pane animationLayer;

    public AnimationManager(Pane animationLayer) {
        this.animationLayer = animationLayer;
        this.animationLayer.setPickOnBounds(false); // not clickable
    }

    /**
     * Animates single CardView from SourceTopNode (ex stockview) 
     * to destNode (ex wasteview).
     * 
     * How this works: 
     * 1) Measure the top card while still attached (the moving card)
     * 2) Measure the starting position from the scene to the transparent overlay
     * 3) Pop the card from the source lifting it into the transparent overlay animation layer
     * 4) Identify Bounds on the destination (measuring destination)
     *  a) dx and dy will be used with tt.setByX(dx) tt.setByY(dy) which is the distance
     * the card will travel. Essentially meausring our distance of travel for the cards
     *  b) Before running the animation, have the model Run engine.draw()
     * 5) Using tt, define the smooth movement
     * 6) Cleanup + run onFinished (redraw()). Meaning restore the animated node back to its state.
     * 
     * Analogy of sequence: 
     * Earth is around for however long you can imagine. The status of you is your original state. 
     * Then something mysterious happens (animation layer) and you are born and live and pass. 
     * You then return back to a new legitmate state.
     * Animation layer does this. Removes Cardview from original state, performs animation and some 
     * runnables (engine and redraw stuff), LIFE/LIVE, then you return to what once was, in a different spot.
     * The animationLayer is the temporary universe where UI nodes exist only long enough to visually explain 
     * a state change that already happened.
     * 
     * @param src PileView source pile of cardviews (start point). We need 
     * the src to be a pileView so we have access to it's functions. While 
     * dest can be just a Node since we just need its position.
     * @param dest Node that'll be used as the destination on the screen. 
     * This provides flexibility with our various pileviews -> destination nodes. 
     * 
     * These Runnables are named describing the timing of execution, not what they do specifically.
     * @param onBeforePlay Run 'this' before the animation starts (engine.draw()).
     * The animation is just a visual representation of a move that already happened.
     * By the time the animation is done, the game state is already valid.
     * @param onFinished Run 'this' after the animation is finished (redraw())
     * 
     * Runnable can be thought of as a box that holds some code that I want to run later.
     * Simply delayed or delegated behavior.
     * 
     * 
     */
    public void animateTopCardToNode(
        PileView src, 
        Node dest,
        Runnable onBeforePlay,
        Runnable onFinished
    ) {
        // 1) Get top card while still attached
        CardView moving = src.getTopCardView();
        if (moving == null) return;

        // 2) Measure start position (scene to transparent overlay coords)
        Bounds startScene = moving.localToScene(moving.getBoundsInLocal());
        Bounds startOverlay = animationLayer.sceneToLocal(startScene);

        // 3) Pop from source (UI-only) and lift into transparent overlay
        src.popTopCardView();
        moving.relocate(startOverlay.getMinX(), startOverlay.getMinY());
        animationLayer.getChildren().add(moving);

        // 4) Destination position: top left of dest node (scene -> overlay)
        Bounds destScene = dest.localToScene(dest.getBoundsInLocal());
        Bounds destOverlay = animationLayer.sceneToLocal(destScene);

        double dx = destOverlay.getMinX() - startOverlay.getMinX(); // distance x
        double dy = destOverlay.getMinY() - startOverlay.getMinY(); // distance y

        // BoardView update the model before animation (engine.draw())
        if (onBeforePlay != null) onBeforePlay.run();

        // 5) Animate
        TranslateTransition tt = new TranslateTransition(Duration.millis(200), moving);
        tt.setByX(dx);
        tt.setByY(dy);

        tt.setOnFinished(evt -> {
            // 6) 
            /* undo everything temporarily did, so the card can re-enter normal layout life.
            Temporary card life = remove card from pile -> put into animation overlay -> moved using transition.
            Return the card back to it's non-animated state  */
            moving.setTranslateX(0); // doesn't move nodes real position
            moving.setTranslateY(0);
            animationLayer.getChildren().remove(moving);

            if (onFinished != null) onFinished.run();
        });

        tt.play();
    }

    /**
     * 
     * @param foundationNode
     */
    public void foundationSplash(Node foundationNode) {

        DropShadow glow = new DropShadow();
        glow.setRadius(25);
        glow.setSpread(0.35);
        foundationNode.setEffect(glow);
        
        // ----- POP then scale back up -----
        ScaleTransition up = new ScaleTransition(Duration.millis(90), foundationNode);
        up.setToX(1.08);
        up.setToY(1.08);

        ScaleTransition down = new ScaleTransition(Duration.millis(120), foundationNode);
        down.setToX(1.0);
        down.setToY(1.0);

        SequentialTransition pop = new SequentialTransition(up, down);

        // ----- FLASH (quick opacity dip and return) -----
        FadeTransition flashDown = new FadeTransition(Duration.millis(70), foundationNode);
        flashDown.setToValue(0.75);

        FadeTransition flashUp = new FadeTransition(Duration.millis(110), foundationNode);
        flashUp.setToValue(1.0);

        SequentialTransition flash = new SequentialTransition(flashDown, flashUp);

        // Play pop + flash together
        ParallelTransition combo = new ParallelTransition(pop, flash);

        // Clean up once finished
        combo.setOnFinished(e -> {
            foundationNode.setEffect(null);

            foundationNode.setScaleX(1.0);
            foundationNode.setScaleY(1.0);
            foundationNode.setOpacity(1.0);
        });

        combo.play();
    }
}
