package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Controllers.JDBC;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;


public class Main extends Application {
    ResourceBundle rb = ResourceBundle.getBundle("Translations");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/login.fxml"));
        primaryStage.setTitle(rb.getString("title"));
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }

    public static void createAlert(Alert.AlertType alertType, String string){
        Alert alert = new Alert(alertType, string);
        alert.showAndWait();
    }

    public static void changeScene(String fxml) throws IOException {
        Stage secondaryStage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        Scene scene = new Scene(root);
        secondaryStage.setScene(scene);
        secondaryStage.sizeToScene();
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }
    public static void closeScene(Button button){
        button.getScene().getWindow().hide();
    }

    public static void main(String[] args) {
        JDBC.makeConnection();
        launch(args);
    }
}
