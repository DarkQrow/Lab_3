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
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LogWindowController implements Initializable {


    @FXML
    private Label Frase;

    @FXML
    private Button Ready;

    @FXML
    private Button Start;

    @FXML
    private TextField Text;

    @FXML
    private ComboBox<String> UserChoice;

    @FXML
    private Label Error;

    @FXML
    private Label Complete;

    String User;

    ArrayList<String> Data = new ArrayList<>();

    long starttime;

    String userFrase;

    double frasetime;

    public void initialize(URL location, ResourceBundle resources){
        File Users = new File("./src/Users/");

        ObservableList<String> usersList = FXCollections.observableArrayList(Users.list());
        UserChoice.setItems(usersList);
    }
    public void getUser(ActionEvent actionEvent){
        User = UserChoice.getValue();
        System.out.println(User);
    }
    public void BackButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/mainScene.fxml"));
        Scene Registration = new Scene(root);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(Registration);
        appStage.show();
    }
    public void StartButton(ActionEvent actionEvent) throws IOException {

        //читаем информацию о пользователе из файла
        File p = new File("./src/Users/"+User);
        Scanner reader = new Scanner(p);
        while (reader.hasNext()){
            Data.add(reader.nextLine());
        }
        Frase.setText(Data.get(0));
        Start.setDisable(true);
        Text.setDisable(false);
        starttime = System.currentTimeMillis();
    }
    public void TextPrint(ActionEvent actionEvent){
        System.out.println(Text.getText());
        userFrase = Text.getText();
        frasetime=(double) (((System.currentTimeMillis() - starttime)/1000)+(System.currentTimeMillis()-starttime)%1000/1000*0.001);
        System.out.println(((System.currentTimeMillis() - starttime)/1000)+(System.currentTimeMillis()-starttime)%1000/1000*0.001);
        Text.clear();
        Text.setDisable(true);
    }
    public void ReadyButton(ActionEvent actionEvent) {
        int mistakeError = Data.get(0).length()/2;//погрешность по ошибкам
        double speedError = 2.5;//погрешночть по скорости ввода
        boolean[] flags = new boolean[2];

            //просчитываем скорость
            double middleSpeed;
            middleSpeed = (frasetime / Data.get(0).length()) + (frasetime % Data.get(0).length());
            //просчитываем точность ввода
            int mistakesCounter = 0;
            for (int j = 0; j < userFrase.length(); j++) {
                if (!(Data.get(0).charAt(j) == userFrase.charAt(j)))
                    mistakesCounter++;
            }
            //сравнение значений при входе и при регистрации
            //сравнение скорости
            if ((Double.parseDouble(Data.get(1)) - speedError <= middleSpeed) && (middleSpeed <= Double.parseDouble(Data.get(1)) + speedError))
                flags[0] = true;
            //сравнение ошибок
            if ((Integer.parseInt(Data.get(2)) - mistakeError <= mistakesCounter) && (mistakesCounter <= Integer.parseInt(Data.get(2)) + mistakeError))
                flags[1] = true;

            if (flags[0] && flags[1]) Complete.setVisible(true);
            else Error.setVisible(true);

    }
}
