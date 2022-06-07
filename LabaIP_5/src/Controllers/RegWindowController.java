package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class RegWindowController {

    @FXML
    private Label Frase;

    @FXML
    private Button Back;

    @FXML
    private Button Start;

    @FXML
    private TextField Text;

    @FXML
    private TextField UserNameText;

    long starttime;

    ArrayList<String> fraseCount = new ArrayList<>();

    ArrayList<Double>frasetime = new ArrayList<>();

    public void StartButton(ActionEvent actionEvent) throws IOException {
        ArrayList<String> frases = new ArrayList<>();
        //читаем заготовленные фразы из файла
        File p = new File("src/Phrases.txt");
        Scanner reader = new Scanner(p);
        while (reader.hasNext()){
            frases.add(reader.nextLine());
        }
       int randomFrase = (int)(( Math.random() * (9 - 0 + 1) + 0));
        Frase.setText(frases.get(randomFrase));
        Start.setDisable(true);
        Text.setDisable(false);
        starttime = System.currentTimeMillis();
    }
    public void ReadyButton(ActionEvent actionEvent) throws IOException {
        SaveData(frasetime,UserNameText.getText(),Frase.getText(),fraseCount);
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/mainScene.fxml"));
        Scene Registration = new Scene(root);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(Registration);
        appStage.show();
    }
    public void TextPrint(ActionEvent actionEvent){
      System.out.println(Text.getText());
      fraseCount.add(Text.getText());
      Text.clear();
      frasetime.add((double) (((System.currentTimeMillis() - starttime)/1000)+(System.currentTimeMillis()-starttime)%1000*0.001/1000*0.001));
      System.out.println(((System.currentTimeMillis() - starttime)/1000)+(System.currentTimeMillis()-starttime)%1000/1000*0.001);
      starttime = System.currentTimeMillis();
      if(frasetime.size() == 4) Text.setDisable(true);
    }
    public void TimeCount(KeyEvent keyEvent){
        long m = System.currentTimeMillis();
        System.out.print(keyEvent.getText());
        System.out.println((long) (System.currentTimeMillis() - m));
    }
    public void UserNameText(ActionEvent event){
        String UserName;
            if(UserNameText.getText()=="") System.out.println("Пусто");
            else {
                UserName = UserNameText.getText();
                System.out.println(UserName);
                UserNameText.setDisable(true);
            };

    }
    public static void SaveData(ArrayList<Double> time,String User,String Frase,ArrayList<String> Frasecount){
        //middlespeed counter
        int fraseSize = Frase.length();
        double[] letterSpeed = new double[4];
        double middleSpeed = 0;
        for(int i = 0;i<4;i++){
            letterSpeed[i] = ((time.get(i)/fraseSize)+(time.get(i)%fraseSize));
            middleSpeed+=letterSpeed[i];
        }
        middleSpeed = ((middleSpeed/4)+(middleSpeed%4/1000*0.001));
        //mistakes counter
        int mistakesCounter = 0;
        for(int i = 0;i<4;i++){
            for(int j = 0;j<Frasecount.get(i).length();j++){

                if(!(Frase.charAt(j) == Frasecount.get(i).charAt(j)))
                    mistakesCounter++;
            }
        }
        mistakesCounter=((mistakesCounter/4)+(mistakesCounter%4));

        File saveUsers = new File("./src/Users/"+User);
        try(FileWriter writer = new FileWriter(saveUsers, false))
        {
            writer.write(Frase+"\n");
            writer.write(middleSpeed+"\n");
            writer.write(mistakesCounter+"\n");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }



    }
}
