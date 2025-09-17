import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);

        // Allow the dialog label to take available width and wrap as needed (for normal messages)
        dialog.setWrapText(true);
        dialog.setMaxWidth(Double.MAX_VALUE);
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        HBox.setHgrow(dialog, Priority.ALWAYS);

        // Base style class for dialog container (used by CSS)
        getStyleClass().add("dialog-box");
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.getStyleClass().add("user");
        return db;
    }

    public static DialogBox getSisyphusDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.getStyleClass().add("bot");

        // Normalize tabs, use monospaced font, and disable wrapping for ASCII art
        String mono = text.replace("\t", "    ");
        db.dialog.setText(mono);
        db.dialog.setWrapText(false);
        db.dialog.setStyle(
                "-fx-font-family: 'Menlo','Monaco','Consolas','Courier New',monospace; -fx-font-size: 12px;"
        );

        return db;
    }
}