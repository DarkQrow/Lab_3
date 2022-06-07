package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.invoke.VarHandle;
import java.net.URL;
import java.util.*;

public class LogWindowController implements Initializable {
    @FXML
    private Pane VeryBadPane;
    @FXML
    private Rectangle VeryBad;
    @FXML
    private Label VeryBadText;
    @FXML
    private Pane LinePane;
    @FXML
    private Button Back;
    @FXML
    private Button Ready;
    @FXML
    private ComboBox<String> UserChoice;
    @FXML
    private Label Bad;
    @FXML
    private Label Good;
    @FXML
    AnchorPane BigBro;
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

    String User;
    int Try = 0;

    ArrayList<String> Data = new ArrayList<>();
    int [] Password = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1};
    ArrayList<Double> PointsX = new ArrayList<>();
    ArrayList<Double> PointsY = new ArrayList<>();

    int passwordCount = 0;

    public void initialize(URL location, ResourceBundle resources){
        File Users = new File("./src/Users/");

        ObservableList<String> usersList = FXCollections.observableArrayList(Users.list());
        UserChoice.setItems(usersList);

    }
    public void getUser(ActionEvent actionEvent){
        User = UserChoice.getValue();
        System.out.println(User);
        File p = new File("./src/Users/"+User);
        Scanner reader = null;
        try {
            reader = new Scanner(p);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (reader.hasNext()){
            Data.add(reader.nextLine());
        }
        PasswordField.setDisable(false);
    }
    public void ReadyButton(ActionEvent actionEvent){
        int counter = 0;
        for(int i = 0;i<9;i++){
            System.out.println(Password[i]+" "+Data.get(i));
            if(Password[i]==Integer.parseInt(Data.get(i))) counter++;
        }
        if(counter == 9) Good.setVisible(true);
        else {Bad.setVisible(true);
            Circle1_1.setDisable(false);
            Circle1_2.setDisable(false);
            Circle1_3.setDisable(false);
            Circle2_1.setDisable(false);
            Circle2_2.setDisable(false);
            Circle2_3.setDisable(false);
            Circle3_1.setDisable(false);
            Circle3_2.setDisable(false);
            Circle3_3.setDisable(false);
            Circle1_1.setFill(Color.DODGERBLUE);
            Circle1_2.setFill(Color.DODGERBLUE);
            Circle1_3.setFill(Color.DODGERBLUE);
            Circle2_1.setFill(Color.DODGERBLUE);
            Circle2_2.setFill(Color.DODGERBLUE);
            Circle2_3.setFill(Color.DODGERBLUE);
            Circle3_1.setFill(Color.DODGERBLUE);
            Circle3_2.setFill(Color.DODGERBLUE);
            Circle3_3.setFill(Color.DODGERBLUE);
            LinePane.getChildren().clear();
            Try++;
            PointsX.clear();
            PointsY.clear();
            passwordCount = 0;
            if(Try ==3) {
                System.out.println("LOCH");
                Bad.setText("Вы ввели пароль \n неверно 3 раза \n Попыток не осталось");
                Circle1_1.setDisable(true);
                Circle1_2.setDisable(true);
                Circle1_3.setDisable(true);
                Circle2_1.setDisable(true);
                Circle2_2.setDisable(true);
                Circle2_3.setDisable(true);
                Circle3_1.setDisable(true);
                Circle3_2.setDisable(true);
                Circle3_3.setDisable(true);
                Circle1_1.setFill(Color.WHITE);
                Circle1_2.setFill(Color.WHITE);
                Circle1_3.setFill(Color.WHITE);
                Circle2_1.setFill(Color.WHITE);
                Circle2_2.setFill(Color.WHITE);
                Circle2_3.setFill(Color.WHITE);
                Circle3_1.setFill(Color.WHITE);
                Circle3_2.setFill(Color.WHITE);
                Circle3_3.setFill(Color.WHITE);
            }

        }
    }
    public void BackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/mainScene.fxml"));
        Scene Registration = new Scene(root);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(Registration);
        appStage.show();
    }
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
            LinePane.getChildren().add(tempPane);
            Stage appStage = (Stage) LinePane.getScene().getWindow();
            appStage.setScene(LinePane.getScene());
            appStage.show();
            tempPane.toBack();
        }
        if(passwordCount == 8){ Back.setDisable(false);Ready.setDisable(false);}
        passwordCount++;

        System.out.println(Arrays.toString(Password));
        System.out.println(PointsX);
        System.out.println(PointsY);

    }

}
