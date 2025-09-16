import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sisyphus.Sisyphus;

/**
 * A GUI for Sisyphus using FXML.
 */
public class Main extends Application {

    private Sisyphus sisyphus = new Sisyphus();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap, 1200, 800);
            stage.setScene(scene);
            stage.setWidth(1200);
            stage.setHeight(800);
            fxmlLoader.<MainWindow>getController().setSisyphus(sisyphus); // inject the Sisyphus instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
