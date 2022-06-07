package Controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;


import java.io.IOException;
import java.net.URL;


public class Main extends Application {
    @FXML
    Button LogButton;
    @FXML
    Button RegButton;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        // установка надписи
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/FXML/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void LogWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/LogWindow.fxml"));
        Scene Registration = new Scene(root);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(Registration);
        appStage.show();
    }
    public void RegWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/RegWindow.fxml"));
        Scene Registration = new Scene(root);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(Registration);
        appStage.show();
    }
}