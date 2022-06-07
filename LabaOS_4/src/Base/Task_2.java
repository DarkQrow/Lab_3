package Base;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Task_2 extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stagemain) throws Exception{
        stage = stagemain;
        Parent root = FXMLLoader.load(getClass().getResource("Task_2.fxml"));
        stagemain.setTitle("Task_2");
        stagemain.setScene(new Scene(root));
        stagemain.setResizable(false);
        stagemain.show();
    }

    public static void main(String[] args) {launch(args);}
}