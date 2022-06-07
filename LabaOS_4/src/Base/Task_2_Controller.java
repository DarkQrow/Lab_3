package Base;

import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class Task_2_Controller {
    @FXML
    private Button PressMe;

    public void Press(ActionEvent actionEvent) throws IOException {
        System.out.println(1);
        PressMe.setDisable(true);

        new Thread(() -> Platform.runLater(new Runnable() {
            public void run() {

                Stage stagenew = new Stage();

                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Task_2_2.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stagenew.setTitle("Task_2");
                stagenew.setScene(new Scene(root));
                stagenew.setResizable(false);
                stagenew.show();

            }
        })).start();
        /*
        Stage stagenew = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Task_2_2.fxml"));
        stagenew.setTitle("Task_2");
        stagenew.setScene(new Scene(root));
        stagenew.setResizable(false);
        stagenew.show();

         */

    }

}
