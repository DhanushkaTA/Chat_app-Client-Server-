import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ClientInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/client/view/ChatApp.fxml");
        AnchorPane load = FXMLLoader.load(resource);
        primaryStage.setScene(new Scene(load));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
