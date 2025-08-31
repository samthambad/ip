import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    /**
     * Launches the JavaFX GUI application.
     *
     * @param args CLI arguments passed to the application
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
