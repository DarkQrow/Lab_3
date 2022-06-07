package Controllers;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
    @FXML
    TextField Alpha;
    @FXML
    TextField NumQ;
    @FXML
    Button returnAlpha;
    @FXML
    Button returnNumQ;
    @FXML
    TextField EndQ;
    @FXML
    TableView TableQ;
    @FXML
    TableView TableS;
    @FXML
    TableView TableP;
    @FXML
    Button StartBtn;
    @FXML
    Button GraphQbtn;
    @FXML
    Button GraphSbtn;
    @FXML
    Button GraphPbtn;
    @FXML
    Button CheckWordbtn;
    @FXML
    Pane DataStart;
    @FXML
    Pane WordStart;
    @FXML
    TextField
    Word;
    @FXML
    Tab Stab;
    @FXML
    Tab Ptab;
    @FXML
    Label NO;
    @FXML
    Label YES;

    String alphabet;//
    String numQ;
    String[] endQ;
    ArrayList<ArrayList<String>> PBig1;
    String startmain;
    ArrayList<ArrayList<String>> Sbig;
    ArrayList<ArrayList<ArrayList<String>>> Qbig;
    String endmain;


    public static void main(String[] args) {launch(args);}


    public void getAlpha(ActionEvent actionEvent) {
        Pattern alfaPat1 = Pattern.compile("[a-zA-Z]{2,}");
        Pattern alfaPat2 = Pattern.compile("[а-яА-Я]{2,}");
        String alpa = Alpha.getText();
        char[] myArray = alpa.toCharArray();
        int countrep = 0;
        for(int i=0; i<myArray.length-1; i++){
            for (int j=i+1; j<myArray.length; j++){
                if(myArray[i] == myArray[j]){
                    countrep++;
                }
            }
        }
        Matcher matcher1 = alfaPat1.matcher(alpa);
        Matcher matcher2 = alfaPat2.matcher(alpa);
        //  !((A||B)&&C)
        if(!((matcher1.matches()||matcher2.matches())&&(countrep<1))){
            Alpha.clear();
            try {
                // Вы должны использовать try-catch, иначе компилятор не пропустит вас, поверьте мне!
                Parent anotherRoot = FXMLLoader.load(getClass().getResource("/FXML/AlphaMistake.fxml"));
                Stage mistakeAlphaStage = new Stage();
                mistakeAlphaStage.setScene(new Scene(anotherRoot));
                mistakeAlphaStage.setTitle("MISTAKE");
                mistakeAlphaStage.initModality(Modality.WINDOW_MODAL);
                mistakeAlphaStage.initOwner(
                        ((Node)actionEvent.getSource()).getScene().getWindow() );
                mistakeAlphaStage.show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            alphabet = Alpha.getText();
            Alpha.setDisable(true);
            NumQ.setDisable(false);
            System.out.println("Alphabet: "+alphabet);
        }

    }
    public void returnAlpha(ActionEvent actionEvent){
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    public void returnNumQ(ActionEvent actionEvent){
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    public void getNumQ(ActionEvent actionEvent){
        Pattern numPat = Pattern.compile("[0-9]+");
        String numq = NumQ.getText();
        Matcher numCheck = numPat.matcher(numq);
        if(!numCheck.matches()){
           NumQ.clear();
            try {
                // Вы должны использовать try-catch, иначе компилятор не пропустит вас, поверьте мне!
                Parent anotherRoot = FXMLLoader.load(getClass().getResource("/FXML/NumQMistake.fxml"));
                Stage mistakeAlphaStage = new Stage();
                mistakeAlphaStage.setScene(new Scene(anotherRoot));
                mistakeAlphaStage.setTitle("MISTAKE");
                mistakeAlphaStage.initModality(Modality.WINDOW_MODAL);
                mistakeAlphaStage.initOwner(
                        ((Node)actionEvent.getSource()).getScene().getWindow() );
                mistakeAlphaStage.show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            numQ = NumQ.getText();
            NumQ.setDisable(true);
            System.out.println("numQ: "+numq);
            EndQ.setDisable(false);
        }
    }
    public void getEndQ(ActionEvent actionEvent){
        String endq = EndQ.getText();
        String[] endqs;
        if(endq.contains(" ")){
          endqs = endq.split(" ");
        }
        else{
            endqs = new String[1];
            endqs[0] = endq;
        }
        int check = 0;
        Pattern numPat = Pattern.compile("[0-9]+");
        for(int i = 0;i<endqs.length;i++){
            Matcher numCheck = numPat.matcher(endqs[i]);
            if(!numCheck.matches()){

                    EndQ.clear();
                    try {
                        // Вы должны использовать try-catch, иначе компилятор не пропустит вас, поверьте мне!
                        Parent anotherRoot = FXMLLoader.load(getClass().getResource("/FXML/EndQMistake.fxml"));
                        Stage mistakeAlphaStage = new Stage();
                        mistakeAlphaStage.setScene(new Scene(anotherRoot));
                        mistakeAlphaStage.setTitle("MISTAKE");
                        mistakeAlphaStage.initModality(Modality.WINDOW_MODAL);
                        mistakeAlphaStage.initOwner(
                                ((Node) actionEvent.getSource()).getScene().getWindow());
                        mistakeAlphaStage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                }
                break;
            }
            else{ check++;System.out.println(check+" "+endqs.length);}
        }
        if(check == endqs.length) {
            startmain = "0";
            endQ = endqs;
            for(int i = 0;i< endQ.length;i++){
                endmain +=endQ[i];
            }
            EndQ.setDisable(true);
            System.out.println("endQ: " + Arrays.deepToString(endqs));
            //создание таблицы
            List<String> columnNames = new ArrayList<>();
            columnNames.add("qn");
            for (int i = 0; i < alphabet.length(); i++) {
                columnNames.add(alphabet.charAt(i) + "");
            }
            columnNames.add("E");
            for (int i = 0; i < columnNames.size(); i++) {
                final int finalIdx = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
                column.setCellValueFactory(param ->
                        new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
                );

                if(i>0) {
                    column.setCellFactory(TextFieldTableCell.forTableColumn());
                    column.setOnEditCommit((TableColumn.CellEditEvent<ObservableList<String>,String>event)->{
                        TablePosition<ObservableList<String>,String> pos = event.getTablePosition();
                        String newData = event.getNewValue();
                        int row = pos.getRow();
                        ObservableList<String> Data = event.getTableView().getItems().get(row);
                        Data.set(pos.getColumn(), newData);
                    });
                }
                column.prefWidthProperty().bind(TableQ.widthProperty().multiply(0.2));
                column.setEditable(true);
                TableQ.getColumns().add(column);
                
            }
           ArrayList<ArrayList<String>> Q = new ArrayList<>();
            for(int i = 0;i<Integer.parseInt(numQ);i++){
                Q.add(new ArrayList<>());
            }
            for(int i = 0;i<Integer.parseInt(numQ);i++){
                Q.get(i).add("q"+i);
                for(int j = 0;j<alphabet.length()+1;j++){
                    Q.get(i).add("-");
                }
            }
                  for (int i = 0; i < Integer.parseInt(numQ); i++) {
                    TableQ.getItems().add(FXCollections.observableArrayList(Q.get(i)));
                }
        }

    }
    public void returnEndQ(ActionEvent actionEvent){
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    public void Start(ActionEvent actionEvent){//бля эта такой пиздец в этой функции будет происходить
        ObservableList<Object> DataCheck = TableQ.getItems();
        for(Object el:DataCheck){
            System.out.println(el);
        }
        System.out.println();
        TableP.getItems().clear();
        TableS.getItems().clear();

        ArrayList<ArrayList<ArrayList<String>>> Q = ObjectToString(DataCheck,alphabet);
        Qbig = ObjectToString(DataCheck,alphabet);
        startmain = findstart(getStemp(Q,alphabet),startmain);//начало и конец для Sки
        String start = startmain;
        endmain = findend(getStemp(Q,alphabet),endmain);
        ArrayList<ArrayList<String>> S = QtoS(Q,alphabet);
        ArrayList<ArrayList<String>> P = StoP(S,startmain,alphabet.length());

        System.out.println("Sstart/end: "+startmain+"/"+endmain);

        PBig1 = StoP(S,startmain,alphabet.length());
        Sbig = QtoS(Q,alphabet);
        endmain = findend(getPtemp(S,startmain),endmain);//начало и конец для Pшки
        startmain ="0";
        System.out.println("Pstart/end: "+startmain+"/"+endmain);


        //заполнение таблиц ФФФФФФФФФФФФФАК
        Stab.setDisable(false);
        Ptab.setDisable(false);
        GraphPbtn.setDisable(false);
        GraphSbtn.setDisable(false);
        GraphQbtn.setDisable(false);
        CheckWordbtn.setDisable(false);

        List<String> columnNames = new ArrayList<>();
        columnNames.add("Sn");
        for (int i = 0; i < alphabet.length(); i++) {
            columnNames.add(alphabet.charAt(i) + "");
        }
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            column.prefWidthProperty().bind(TableS.widthProperty().multiply(0.3));
            column.setEditable(false);
            TableS.getColumns().add(column);
        }
        ArrayList<ArrayList<String>> Stable = new ArrayList<>();
        for(int i = 0;i<S.size();i++){
            Stable.add(new ArrayList<>());
        }
        ArrayList<String> Stemp = getStemp(Q,alphabet);
        for(int i = 0;i<S.size();i++){
            String qs = "";
            for(int j = 0;j<Stemp.get(i).length();j++){
                qs+="q"+Stemp.get(i).charAt(j)+" ";
            }
            Stable.get(i).add("S"+i+"(q"+i+")={"+qs+"}");
            for(int j = 0;j<alphabet.length();j++){
                String build="";
                for(int k = 0;k<S.get(i).get(j).length();k++) {
                build+="S"+S.get(i).get(j).charAt(k)+" ";
                }
                Stable.get(i).add(build);
            }
        }
        for (int i = 0; i < S.size(); i++) {
            TableS.getItems().add(FXCollections.observableArrayList(Stable.get(i)));
        }


        columnNames = new ArrayList<>();
        columnNames.add("Pn");
        for (int i = 0; i < alphabet.length(); i++) {
            columnNames.add(alphabet.charAt(i) + "");
        }
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames.get(i));
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            column.prefWidthProperty().bind(TableP.widthProperty().multiply(0.3));
            column.setEditable(false);
            TableP.getColumns().add(column);

        }
        ArrayList<ArrayList<String>> Ptable = new ArrayList<>();
        for(int i = 0;i<P.size();i++){
            Ptable.add(new ArrayList<>());
        }
        ArrayList<String> Ptemp = getPtemp(S,start);
        for(int i = 0;i<P.size();i++){
            String ss = "";
            for(int j = 0;j<Ptemp.get(i).length();j++){
                ss+="S"+Ptemp.get(i).charAt(j)+" ";
            }
            Ptable.get(i).add("P"+i+"={"+ss+"}");
            for(int j = 0;j<alphabet.length();j++){
                String build="";
                for(int k = 0;k<P.get(i).get(j).length();k++) {
                    if(!(P.get(i).get(j).charAt(k)=='-'))
                    build+="P"+P.get(i).get(j).charAt(k)+" ";
                }
                Ptable.get(i).add(build);
            }
        }
        for (int i = 0; i < P.size(); i++) {
            TableP.getItems().add(FXCollections.observableArrayList(Ptable.get(i)));
        }



    }
    public void CheckWord(ActionEvent actionEvent){

        DataStart.setDisable(true);
        DataStart.setVisible(false);
        WordStart.setDisable(false);
        WordStart.setVisible(true);
    }
    public void getWord(ActionEvent actionEvent){
        YES.setVisible(false);
        NO.setVisible(false);
        Pattern alfaPat1 = Pattern.compile("[a-zA-Z]{2,}");
        Pattern alfaPat2 = Pattern.compile("[а-яА-Я]{2,}");
        String alpa = Alpha.getText();
        char[] myArray = alpa.toCharArray();
        int countrep = 0;
        for(int i=0; i<myArray.length-1; i++){
            for (int j=i+1; j<myArray.length; j++){
                if(myArray[i] == myArray[j]){
                    countrep++;
                }
            }
        }
        ArrayList<ArrayList<String>> PBig = new ArrayList<>();
        for(int k = 0;k< PBig1.size();k++){
            PBig.add(new ArrayList<>());
            for(int l = 0;l<PBig1.get(k).size();l++) {
                PBig.get(k).add("");
                String temp = PBig1.get(k).get(l);
                PBig.get(k).set(l, temp);
            }
        }
        Matcher matcher1 = alfaPat1.matcher(alpa);
        Matcher matcher2 = alfaPat2.matcher(alpa);
        if(!((matcher1.matches()||matcher2.matches())&&(countrep<1))){
            Alpha.clear();
            try {
                Parent anotherRoot = FXMLLoader.load(getClass().getResource("/FXML/WordMistake.fxml"));
                Stage mistakeAlphaStage = new Stage();
                mistakeAlphaStage.setScene(new Scene(anotherRoot));
                mistakeAlphaStage.setTitle("MISTAKE");
                mistakeAlphaStage.initModality(Modality.WINDOW_MODAL);
                mistakeAlphaStage.initOwner(
                        ((Node)actionEvent.getSource()).getScene().getWindow() );
                mistakeAlphaStage.show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            String word = Word.getText();
            System.out.println("Word: "+word);
            boolean check = false;

            for (int i = 0;i<startmain.length();i++){
                if(check) break;
                for(int j = 0;j<endmain.length();j++){

                    for(int k = 0;k< PBig1.size();k++){
                        for(int l = 0;l<PBig.get(k).size();l++) {
                            String temp = PBig1.get(k).get(l);
                            PBig.get(k).set(l, temp);
                        }
                    }
                    if(check)break;
                    int startlet = Integer.parseInt(startmain.charAt(i)+"");
                    int endlet = Integer.parseInt(endmain.charAt(j)+"");
                        for (int k = 0; k < word.length(); k++) {
                            if(!PBig.get(startlet).get(alpa.indexOf(word.charAt(k))).equals("-")) {
                                int temp = startlet;
                                startlet = Integer.parseInt(PBig.get(temp).get(alphabet.indexOf(word.charAt(k))));
                                System.out.println(startlet);
                                if ((k + 1 == word.length()) && (startlet == endlet)) {
                                    YES.setVisible(true);
                                    check = true;
                                    break;
                                }
                               // PBig.get(temp).set(alphabet.indexOf(word.charAt(k)), "-");
                            }
                        }
                }
            }
            if(!check){
                NO.setVisible(true);
            }
        }
    }
    public void PaintGraphQ(ActionEvent actionEvent){
        Digraph<String,String> Q = new DigraphEdgeList<>();
        String alpa = alphabet+"E";
        for(int i = 0;i< Qbig.size();i++){
            Q.insertVertex("q"+i);
        }
        for(int i = 0;i< Qbig.size();i++){
            for (int j = 0;j<Qbig.get(i).size();j++){
                for(int k = 0;k<Qbig.get(i).get(j).size();k++){
                    String temp = alpa.charAt(j)+"";
                    if(!Qbig.get(i).get(j).get(k).equals("-")) {
                        Q.insertEdge("q" + i, "q" + Qbig.get(i).get(j).get(k), temp + i + Qbig.get(i).get(j).get(k));
                    }
                }
            }
        }


        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(Q);
        Scene scene = new Scene(graphView, 800, 600);
        graphView.getStylableVertex("q0").setStyleClass("myStartVertex");
        for(int i = 0;i< endQ.length;i++){
            graphView.getStylableVertex("q"+endQ[i]).setStyleClass("myEndVertex");
        }

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Visualization Q");
        stage.setScene(scene);
        stage.show();

//IMPORTANT - Called after scene is displayed so we can have width and height values
        graphView.init();

    }
    public void PaintGraphS(ActionEvent actionEvent){
        Digraph<String,String> S = new DigraphEdgeList<>();
        for(int i = 0;i< Sbig.size();i++){
            S.insertVertex("S"+i);
        }
        for(int i = 0;i< Sbig.size();i++){
            for (int j = 0;j<Sbig.get(i).size();j++){
                for(int k = 0;k<Sbig.get(i).get(j).length();k++){
                    String temp = alphabet.charAt(j)+"";
                    String tempEl = Sbig.get(i).get(j).charAt(k)+"";
                    if(!tempEl.equals("-")) {
                        S.insertEdge("S" + i, "S" + Sbig.get(i).get(j).charAt(k), temp + i + Sbig.get(i).get(j).charAt(k));
                    }
                }
            }
        }


        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(S);
        Scene scene = new Scene(graphView, 800, 600);
        String start = findstart(getStemp(Qbig,alphabet),"0");
        for(int i = 0;i<start.length();i++) {
            graphView.getStylableVertex("S" + start.charAt(i)).setStyleClass("myStartVertex");
        }
        String endq = "";
        for(int i = 0;i< endQ.length;i++){
            endq+=endQ [i];
        }
        String end = findend(getStemp(Qbig,alphabet),endq);
        for(int i = 0;i< end.length();i++){
            graphView.getStylableVertex("S"+end.charAt(i)).setStyleClass("myEndVertex");
        }
        String startend = "";
        if(end.length()>start.length()){
            for(int i = 0;i<start.length();i++){
                if(end.contains(start.charAt(i)+"")){
                    startend+=start.charAt(i);
                }
            }
        }
        else{
            for(int i = 0;i<end.length();i++){
                if(start.contains(end.charAt(i)+"")){
                    startend+=end.charAt(i);
                }
            }
        }
        for(int i = 0;i< startend.length();i++){
            graphView.getStylableVertex("S"+startend.charAt(i)).setStyleClass("myStartEndVertex");
        }
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Visualization S");
        stage.setScene(scene);
        stage.show();

//IMPORTANT - Called after scene is displayed so we can have width and height values
        graphView.init();
    }
    public void PaintGraphP(ActionEvent actionEvent){
        Digraph<String,String> P = new DigraphEdgeList<>();
        for(int i = 0;i< PBig1.size();i++){
            P.insertVertex("P"+i);
        }
        System.out.println();
        for(int i = 0;i< PBig1.size();i++){
            for (int j = 0;j<PBig1.get(i).size();j++){
                for(int k = 0;k<PBig1.get(i).get(j).length();k++){
                    String temp = alphabet.charAt(j)+"";
                    String tempEl = PBig1.get(i).get(j).charAt(k)+"";
                    if(!tempEl.equals("-")) {
                        P.insertEdge("P" + i, "P" +PBig1.get(i).get(j).charAt(k), temp + i + PBig1.get(i).get(j).charAt(k));
                    }
                }
            }
        }


        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(P);
        Scene scene = new Scene(graphView, 800, 600);
        String start = startmain;
        for(int i = 0;i<start.length();i++) {
            graphView.getStylableVertex("P" +start.charAt(i)).setStyleClass("myStartVertex");
        }
        StringBuffer end = new StringBuffer();
        end.append(endmain);
        System.out.println(end);
        for(int i = 0;i<end.length();i++){
            if(Integer.parseInt(end.charAt(i)+"")>PBig1.size()-1){
                end.deleteCharAt(i);
            }
        }
        System.out.println(end);
        for(int i = 0;i< end.length();i++){
            graphView.getStylableVertex("P"+end.charAt(i)).setStyleClass("myEndVertex");
        }
        //получение вершин начала и конца
        String startend = "";
        if(endmain.length()>startmain.length()){
            for(int i = 0;i<startmain.length();i++){
                if(endmain.contains(startmain.charAt(i)+"")){
                    startend+=startmain.charAt(i);
                }
            }
        }
        else{
            for(int i = 0;i<endmain.length();i++){
                if(startmain.contains(endmain.charAt(i)+"")){
                    startend+=endmain.charAt(i);
                }
            }
        }
        for(int i = 0;i< startend.length();i++){
            graphView.getStylableVertex("P"+startend.charAt(i)).setStyleClass("myStartEndVertex");
        }

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Visualization S");
        stage.setScene(scene);
        stage.show();

//IMPORTANT - Called after scene is displayed so we can have width and height values
        graphView.init();
    }
    public static ArrayList<ArrayList<ArrayList<String>>> ObjectToString(ObservableList<Object> data,String alpha){
        ArrayList<ArrayList<ArrayList<String>>> result = new ArrayList<>();
        //init result
        for(int i = 0;i<data.size();i++){
            result.add(new ArrayList<>());
            for(int j = 0;j<alpha.length()+1;j++){
                result.get(i).add(new ArrayList<>());
            }
        }
        ArrayList<StringBuffer> tempArray = new ArrayList<>();//ObjectTableRow to String
        for(int i = 0;i< data.size();i++){
            tempArray.add(new StringBuffer(data.get(i).toString()));
            tempArray.get(i).delete(0,5);
            tempArray.get(i).deleteCharAt(tempArray.get(i).length()-1);
            System.out.println(tempArray.get(i));
            String[] tempEl = tempArray.get(i).toString().split(", ");
            for(String el:tempEl){
                System.out.println(el+" l:"+el.length());
            }
            for(int j = 0;j<alpha.length()+1;j++){
                if(tempEl[j].length()>1){
                    String[]tempEl2 = tempEl[j].split(",");
                    for(int k = 0;k<tempEl2.length;k++){
                        result.get(i).get(j).add(tempEl2[k]);
                    }
                }
                else{
                        result.get(i).get(j).add(tempEl[j]);
                }
            }
        }
        for(ArrayList<ArrayList<String>>el3:result){
            for(ArrayList<String>el2:el3){
                System.out.print("|");
                for(String el:el2){
                    System.out.print(el+" ");
                }
                System.out.print("|");
            }
            System.out.println();
        }
        return result;
    }
    public static ArrayList<ArrayList<String>> QtoS(ArrayList<ArrayList<ArrayList<String>>> Q, String alpha){
        ArrayList<ArrayList<String>> S = new ArrayList<>();
        ArrayList<String> Stemp = getStemp(Q,alpha);
        //init S
        for(int i = 0;i<Q.size();i++){
            S.add(new ArrayList<>());
            for(int j = 0;j<alpha.length();j++){
                S.get(i).add("");
            }
        }
        //S тяме
        for(int i = 0;i< Stemp.size();i++){
            for(int j = 0;j< Stemp.get(i).length();j++){
                int indexEl = Integer.parseInt(""+Stemp.get(i).charAt(j));
                for(int k = 0;k<alpha.length();k++) {//ток алфавит
                    for (int l = 0; l < Q.get(indexEl).get(k).size(); l++) {
                        if (!Q.get(indexEl).get(k).get(l).equals("-")){
                            String temp = S.get(i).get(k);
                            String value = Q.get(indexEl).get(k).get(l);
                            if(!temp.equals(value))
                            S.get(i).set(k,temp+value);
                        }
                    }
                }
            }
        }
        System.out.println("S1");
        for(ArrayList<String> el1:S){
            for(String el: el1){
                System.out.print(el+" ");
            }
            System.out.println();
        }
        //E time
        System.out.println();
        for(int i = 0;i<S.size();i++){
            for(int j = 0;j<S.get(i).size();j++){
                for(int k = 0;k<S.get(i).get(j).length();k++){
                    int indexEl = Integer.parseInt(""+S.get(i).get(j).charAt(k));
                    for(int l = 0;l<Q.get(indexEl).get(alpha.length()).size();l++){
                        if(!Q.get(indexEl).get(alpha.length()).get(l).equals("-")){
                            String temp = S.get(i).get(j);
                            String value = "";
                            if(ScontainEl(S.get(i),alpha,S.get(i).get(j).charAt(k)+"")) {
                                 value = Q.get(indexEl).get(alpha.length()).get(l);
                            }
                            if (!temp.contains(value)) {
                                S.get(i).set(j, temp + value);
                            }
                        }
                    }
                }
            }
        }

System.out.println("S2");
        for(ArrayList<String> el1:S){
           for(String el: el1){
               System.out.print(el+" ");
           }
           System.out.println();
        }

        return S;
    }
    public static String findstart(ArrayList<String> Stemp,String el){
        String start = "";
        for(int j = 0;j<el.length();j++) {
            String startq = Stemp.get(Integer.parseInt(el.charAt(j)+""));
            for (int i = 0; i < Stemp.size(); i++) {
                if (startq.contains(Integer.toString(i))) {
                    if (!start.contains(Integer.toString(i)))
                        start += i;
                }
            }
        }
        return start;
    }
    public static String findend(ArrayList<String> Stemp,String el){
        String end = "";
        for(int j = 0;j<el.length();j++) {
            for (int i = 0; i < Stemp.size(); i++) {
                if(Stemp.get(i).contains(el.charAt(j)+"")){
                    if(!end.contains(Integer.toString(i))){
                        end+=i;
                    }
                }
            }
        }
        return end;
    }

    public static boolean ScontainEl(ArrayList<String> S,String alphabet,String el){
        boolean result = false;
        for(int i = 0;i<alphabet.length();i++){
            for(int j = 0;j<S.get(i).length();j++){
                if (el.equals(S.get(i).charAt(j)+"")) {
                    result = true;
                }
            }
        }
        return result;
    }
    public static ArrayList<String> getStemp(ArrayList<ArrayList<ArrayList<String>>> Q,String alpa){
        ArrayList<String> Stemp = new ArrayList<>();
        for(int i = 0;i<Q.size();i++) {
            Stemp.add("");
        }
        int indexE = Q.get(0).size()-1;
        System.out.println(indexE);
        for(int i = 0;i<Q.size();i++){
            for(int k = 0;k<Q.get(i).get(indexE).size();k++){
                if(!Q.get(i).get(indexE).get(k).equals("-")){
                    String temp = Stemp.get(i);
                    Stemp.set(i,temp+Q.get(i).get(indexE).get(k));
                }
            }
            String temp = Stemp.get(i);
            Stemp.set(i,i+temp);
        }
        System.out.println("Stemp");
        for(String el1:Stemp){
            System.out.println(el1+" ");
        }
        return Stemp;
    }
    public static ArrayList<ArrayList<String>>StoP(ArrayList<ArrayList<String>> S,String start,int alpa){
        ArrayList<ArrayList<String>> P = new ArrayList<>();

        ArrayList<String> Ptemp = getPtemp(S,start);

        for (int i = 0;i< Ptemp.size();i++){
            P.add(new ArrayList<>());
            for(int j = 0;j<alpa;j++){
                P.get(i).add("");
            }
        }

        for (int i = 0;i< Ptemp.size();i++){
            ArrayList<String> Sunion = new ArrayList<>();
            for(int j = 0;j<alpa;j++){
                Sunion.add("");
            }
            for(int j = 0;j<Ptemp.get(i).length();j++){
                int indexEl = Integer.parseInt(Ptemp.get(i).charAt(j)+"");
                for(int k = 0;k<alpa;k++){
                    String temp = S.get(indexEl).get(k);
                    temp = temp.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
                    String value = Sunion.get(k);
                    if(temp.contains(value)) Sunion.set(k,temp);
                    else
                        if(value.contains(temp)) Sunion.set(k,value);
                        else Sunion.set(k,value + temp);
                }
            }
            for(int j = 0;j<alpa;j++){
                String el = "-";
                for(int k = 0;k< Ptemp.size();k++){

                    if((Ptemp.get(k).contains(Sunion.get(j)))&&(Ptemp.get(k).length()==Sunion.get(j).length())){
                        el = String.valueOf(k);
                    }
                }
                P.get(i).set(j, el);
            }
        }


            int counter = alpa;
            for(int j = 0;j<alpa;j++){
                String temp = Integer.toString(P.size()-1);
                if(P.get(P.size()-1).get(j).equals(temp)||P.get(P.size()-1).get(j).equals("-")) counter--;
            }
            System.out.println(counter);
            if (counter==0) P.remove(P.size()-1);//удаление лишних концовок
        //удаление повторяшек(я ебала это уже 800тые строки)


        System.out.println("P:");
        for(ArrayList<String> el:P){
            System.out.println();
            for(String el1:el)
                System.out.print(el1+" ");
        }
        return P;
    }
    public static boolean PtempcontainsEl(String el,ArrayList<String> Ptemp){
        int counter = Ptemp.size();
        for(int i = 0;i<Ptemp.size();i++){
            if(Ptemp.get(i).equals(el))
               counter--;
        }
        if(counter!=Ptemp.size()) return false;
        else return true;
    }
    public static ArrayList<String> getPtemp(ArrayList<ArrayList<String>> S,String start){
        ArrayList<String> Ptemp = new ArrayList<>();
        Ptemp.add(start);
        for(int i = 0;i<S.size();i++){
            for(int j = 0;j<S.get(i).size();j++){
                System.out.println(S.get(i).get(j));
                S.get(i).set(j,S.get(i).get(j).chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
                if((PtempcontainsEl(S.get(i).get(j),Ptemp))&&(!S.get(i).get(j).equals(""))){

                    Ptemp.add(S.get(i).get(j));
                }
            }
        }
        for (int i = 0;i< Ptemp.size();i++){
            Ptemp.set(i, Ptemp.get(i).chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
        }

        System.out.print("Ptemp: ");
        for(String el:Ptemp) System.out.print(el+" ");
        return Ptemp;
    }
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        stage.setResizable(false);
        stage.setTitle("LabaTA&FL_5");
        URL xmlUrl = getClass().getResource("/FXML/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
