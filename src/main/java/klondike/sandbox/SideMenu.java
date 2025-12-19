package klondike.sandbox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class SideMenu extends VBox {
    private Button home;
    private Button logout;
    private Button settings;
    private Button profile;

    // What is this object? Its a vbox w/ these four buttons -> SideMenu
    public SideMenu() {
        this.home = new Button("Home");
        this.logout = new Button("Logout");
        this.settings = new Button("Settings");
        this.profile = new Button("Profile");

        configureLayout();
        applyStyle();
        
    }

    /**
     * Style the buttons
     * https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/doc-files/cssref.html
     */
    private void applyStyle() {
        setStyle(
            "-fx-background-color: #262626;" + 
            "-fx-border-color: #555;" + 
            "-fx-border-width: 1;"
        );

        for (Button b : new Button[] {home, profile, settings, logout}) {
            b.setMaxWidth(Double.MAX_VALUE);
            b.setStyle(
                "-fx-background-color: #262626;" + 
                "-fx-font-size: 12px;" + 
                "-fx-font-weight: bold;" + 
                "-fx-text-fill: white;"
            );

            b.setOnMouseEntered(e -> b.setStyle(
                "-fx-background-color: #333333;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;"
            ));

            b.setOnMouseExited(e -> b.setStyle(
                "-fx-background-color: #262626;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 12px;"
            ));
        }
    }

    private void configureLayout() {
        setSpacing(6);
        setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        setPrefWidth(160);
        getChildren().addAll(home, profile, settings, logout);
    }

}
