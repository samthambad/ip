import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sisyphus.Sisyphus;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Sisyphus sisyphus;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image sisyphusImage = new Image(this.getClass().getResourceAsStream("/images/Sisyphus.png"));

    @FXML
    /**
     * Initializes the main window controls and bindings.
     * Ensures the scroll pane follows new messages and the dialog container
     * fills the available width for responsive resizing.
     */
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        // Make the scroll content fit width and dialog container responsive
        scrollPane.setFitToWidth(true);
        dialogContainer.setFillWidth(true);
        dialogContainer.getChildren().add(DialogBox.getSisyphusDialog(Sisyphus.Ui.introMessage(), sisyphusImage));
    }

    /** Injects the Sisyphus instance */
    public void setSisyphus(Sisyphus d) {
        sisyphus = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Sisyphus's reply and then
     * appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = sisyphus.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSisyphusDialog(response, sisyphusImage)
        );
        userInput.clear();
    }
}
