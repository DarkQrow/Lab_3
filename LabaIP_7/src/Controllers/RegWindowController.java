package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class RegWindowController {
    @FXML
    AnchorPane BigBro;
    @FXML
    private TextField UserNameText;
    @FXML
    private Button Back;
    @FXML
    private Circle Circle1_1;
    @FXML
    private Circle Circle1_2;
    @FXML
    private Circle Circle1_3;
    @FXML
    private Circle Circle2_1;
    @FXML
    private Circle Circle2_2;
    @FXML
    private Circle Circle2_3;
    @FXML
    private Circle Circle3_1;
    @FXML
    private Circle Circle3_2;
    @FXML
    private Circle Circle3_3;
    @FXML
    private Pane PasswordField;

    int [] Password = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1};
    ArrayList<Double> PointsX = new ArrayList<>();
    ArrayList<Double> PointsY = new ArrayList<>();

    int passwordCount = 0;

    public void PasswordEnter(MouseEvent mouseEvent){
        Line polyline = new Line();
        polyline.setStroke(Color.WHITE);
        polyline.setStrokeWidth(10);

        Object source = mouseEvent.getSource();
        if (Circle1_1.equals(source)) {
            Password[0] = passwordCount;
            Circle1_1.setFill(Color.WHITE);
            PointsX.add(Circle1_1.getLayoutX());
            PointsY.add(Circle1_1.getLayoutY());
            Circle1_1.setDisable(true);
        } else if (Circle1_2.equals(source)) {
            Password[1] = passwordCount;
            Circle1_2.setDisable(true);
            Circle1_2.setFill(Color.WHITE);
            PointsX.add(Circle1_2.getLayoutX());
            PointsY.add(Circle1_2.getLayoutY());
        } else if (Circle1_3.equals(source)) {
            Password[2] = passwordCount;
            Circle1_3.setDisable(true);
            Circle1_3.setFill(Color.WHITE);
            PointsX.add(Circle1_3.getLayoutX());
            PointsY.add(Circle1_3.getLayoutY());
        } else if (Circle2_1.equals(source)) {
            Password[3] = passwordCount;
            Circle2_1.setDisable(true);
            Circle2_1.setFill(Color.WHITE);
            PointsX.add(Circle2_1.getLayoutX());
            PointsY.add(Circle2_1.getLayoutY());
        } else if (Circle2_2.equals(source)) {
            Password[4] = passwordCount;
            Circle2_2.setDisable(true);
            Circle2_2.setFill(Color.WHITE);
            PointsX.add(Circle2_2.getLayoutX());
            PointsY.add(Circle2_2.getLayoutY());
        } else if (Circle2_3.equals(source)) {
            Password[5] = passwordCount;
            Circle2_3.setDisable(true);
            Circle2_3.setFill(Color.WHITE);
            PointsX.add(Circle2_3.getLayoutX());
            PointsY.add(Circle2_3.getLayoutY());
        } else if (Circle3_1.equals(source)) {
            Password[6] = passwordCount;
            Circle3_1.setDisable(true);
            Circle3_1.setFill(Color.WHITE);
            PointsX.add(Circle3_1.getLayoutX());
            PointsY.add(Circle3_1.getLayoutY());
        } else if (Circle3_2.equals(source)) {
            Password[7] = passwordCount;
            Circle3_2.setDisable(true);
            Circle3_2.setFill(Color.WHITE);
            PointsX.add(Circle3_2.getLayoutX());
            PointsY.add(Circle3_2.getLayoutY());
        } else if (Circle3_3.equals(source)) {
            Password[8] = passwordCount;
            Circle3_3.setDisable(true);
            Circle3_3.setFill(Color.WHITE);
            PointsX.add(Circle3_3.getLayoutX());
            PointsY.add(Circle3_3.getLayoutY());
        }
        if(passwordCount>0) {
            polyline.setStartX(PointsX.get(passwordCount-1));
            polyline.setStartY(PointsY.get(passwordCount-1));
            polyline.setEndX(PointsX.get(passwordCount));
            polyline.setEndY(PointsY.get(passwordCount));
            Pane tempPane = new Pane();
            tempPane.resize(363,363);
            tempPane.setLayoutX(0);
            tempPane.setLayoutY(0);
            tempPane.getChildren().add(polyline);
            PasswordField.getChildren().add(tempPane);
            Stage appStage = (Stage) PasswordField.getScene().getWindow();
            appStage.setScene(PasswordField.getScene());
            appStage.show();
            tempPane.toBack();
        }
        if(passwordCount == 8) Back.setDisable(false);
        passwordCount++;

        System.out.println(Arrays.toString(Password));
        System.out.println(PointsX);
        System.out.println(PointsY);

    }
    public void ReadyButton(ActionEvent actionEvent) throws IOException {
        SaveData(UserNameText.getText(),Password);
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/mainScene.fxml"));
        Scene Registration = new Scene(root);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(Registration);
        appStage.show();
    }
    public void UserNameText(ActionEvent event){
        String UserName;
        if(UserNameText.getText()=="") System.out.println("Пусто");
        else {
            UserName = UserNameText.getText();
            System.out.println(UserName);
            UserNameText.setDisable(true);
        };
        PasswordField.setDisable(false);

    }
    public static void SaveData(String User,int[] password){

        File saveUsers = new File("./src/Users/"+User);
        try(FileWriter writer = new FileWriter(saveUsers, false))
        {
            for(int i = 0;i<9;i++)
            writer.write(password[i]+"\n");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }



    }
}
