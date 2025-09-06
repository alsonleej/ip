package kipgui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Kip using FXML.
 */
public class Main extends Application {

    private KipGUI kipGUI = new KipGUI();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Kip");
            MainWindow controller = fxmlLoader.<MainWindow>getController();
            controller.setKipGUI(kipGUI);
            controller.setStage(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
