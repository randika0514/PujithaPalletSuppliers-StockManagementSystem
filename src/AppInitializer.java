import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        /*primaryStage.setScene
                (new Scene(FXMLLoader.load
                        (getClass().getResource("view/LoginForm.fxml"))));
        primaryStage.show();*/

        URL resource = getClass().getResource("view/LoginForm.fxml");
        assert resource != null;
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pujitha Pallet Suppliers (V-1.0.0)");
        primaryStage.show();
    }
}
