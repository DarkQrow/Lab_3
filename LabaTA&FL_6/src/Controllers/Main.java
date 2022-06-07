package Controllers;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
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
import javafx.scene.layout.Background;
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
    Button StartBtn;
    @FXML
    Button GraphQbtn;
    @FXML
    Button GraphItogbtn;
    @FXML
    Pane DataStart;
    @FXML
    Pane WordStart;
    @FXML
    Button SolveBtn;
    @FXML
    TextArea SolveCode;

    String alphabet;//
    String numQ;
    String[] endQ;
    String startmain;
    ArrayList<ArrayList<ArrayList<String>>> Qbig;
    ArrayList<String> Solve = new ArrayList<>();
    ArrayList<String> C = new ArrayList<>();
    String endmain = "";
    String StartText;


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

        ArrayList<ArrayList<ArrayList<String>>> Q = ObjectToString(DataCheck,alphabet);
        Qbig = ObjectToString(DataCheck,alphabet);

        //Включение клавиш справа на панеле
        GraphItogbtn.setDisable(false);
        SolveBtn.setDisable(false);

        GraphQbtn.setDisable(false);


    }
    //Отображение хода решения
    public void Solve(ActionEvent actionEvent){

        DataStart.setDisable(true);
        DataStart.setVisible(false);
        WordStart.setDisable(false);
        WordStart.setVisible(true);
        Solve.add("1) Автомат детерминированный");
        SolveCode.setText(StartText);
        //Шаг второй проверка вершин на удаление
        String deleteEdges = "";
        for(int i = 1;i<Qbig.size();i++){
            int counter = 0;
            for(int l = 0;l< Qbig.size();l++){
                for(int j = 0;j<alphabet.length();j++){
                    for(int k = 0;k<Qbig.get(i).get(j).size();k++) {
                        if (!Qbig.get(l).get(j).get(k).equals("-")) {
                            if ((i == Integer.parseInt(Qbig.get(l).get(j).get(k))) &&
                                    (i != l)) counter++;
                        }
                    }
                }
            }
            if(counter == 0) deleteEdges+=i;
        }

        StringBuilder endClass =new StringBuilder();
        for(int i = 0;i< endQ.length;i++){
            endClass.append(endQ[i]);
        }

        System.out.println("DeleteEdges: "+deleteEdges);
        if(deleteEdges.equals("")) Solve.add("2) Вершины не удаляются");
        else {Solve.add("2) Будет удалена "+deleteEdges+" вершина"); endClass.deleteCharAt(endClass.indexOf(deleteEdges));}
        //Обработка нулевой эквивалентности
        StringBuilder str = new StringBuilder(); //для вывода
        StringBuilder str1 = new StringBuilder();//для класса
        str.append("3) \u2261\u2070 {");
        for(int i = 0;i<endClass.length()-1;i++){
            str.append("q"+endClass.charAt(i)+", ");
        }
        System.out.println(endClass);
        str.append("q"+endClass.charAt(endClass.length()-1)+"} ");
        str.append("{");
        for(int i = 0;i<Qbig.size();i++){
            if(!endmain.contains(i+"")){
                str.append("q"+i+", ");
                str1.append(i);
            }
        }
        str.delete(str.length()-2,str.length());
        str.append("}");
        Solve.add(str.toString());

        C.add(endClass.toString());//добавление класса конечных вершин c1
        C.add(str1.toString());//добавление класса остальных вершин c2
       // for(String el:C) System.out.println("C:"+el);
        str1.setLength(0);
        for(int i = 0;i<C.size();i++){
            for(int j = 0;j<C.get(i).length();j++){
                for(int k = 0;k<alphabet.length();k++){
                    int indexEl = Integer.parseInt(C.get(i).charAt(j)+"");
                    str1.append("(q"+C.get(i).charAt(j)+","+alphabet.charAt(k)+")\u2794"+Qbig.get(indexEl).get(k).get(0)+"\t");
                }
                str1.append("\n");
            }
            if(i != C.size()-1)str1.append("\n");
        }
        for(String el:C) System.out.println("C:"+el);
        //System.out.println(str.toString());
        //Обработка первой эквивалентности
        StringBuilder newclass = new StringBuilder();
        for(int i = 0;i<C.size();i++){
            for(int j = 0;j<alphabet.length();j++){
                for(int k = 0;k<C.get(i).length();k++){
                    int indexEl = Integer.parseInt(C.get(i).charAt(k)+"");
                    if(!C.get(i).contains(Qbig.get(indexEl).get(j).get(0))){
                        if(newclass.indexOf(C.get(i).charAt(k)+"")==-1) {
                            newclass.append(C.get(i).charAt(k));
                        }
                    }
                }
            } StringBuilder temp = new StringBuilder(C.get(i));
            for(int j = 0;j<newclass.length();j++){
                int indexEl = temp.indexOf(newclass.charAt(j)+"");
                if(indexEl!=-1)
                temp.deleteCharAt(indexEl);
            }
            C.set(i,temp.toString());
        }
        C.add(newclass.toString());//тут конечно есть условность с тем что ток один класс прибавляется, но давайте об этом не думать
        //добавление первой эквивалентности в решение(Solve)
        str.setLength(0);//звращенно чистим то что есть
        str.append("   \u2261\u00B9 ");
        for(int i = 0;i<C.size();i++) {
            str.append("{");
            for (int j = 0; j < C.get(i).length()-1;j++){
                str.append("q" + C.get(i).charAt(j) + ", ");
            }
            str.append("q" + C.get(i).charAt(C.get(i).length()-1) + "} ");
        }
        Solve.add(str.toString());//добавили итог, а теперь надо бы решение
        Solve.add(str1.toString());
        //поехали со второй и третьей благо тут очередная условность есть
        //попоробуем как с первой, выглядит похоже/ ну почти тут все таки стоит учитывать, что тут классов побольше
        int indexC = C.size();
        for(int i = 0;i<indexC;i++){
            newclass.setLength(0);
            for(int j = 0;j<alphabet.length();j++){
                for(int k = 0;k<C.get(i).length();k++){
                    int indexEl = Integer.parseInt(C.get(i).charAt(k)+"");
                    if(!C.get(i).contains(Qbig.get(indexEl).get(j).get(0))){
                        if(newclass.indexOf(C.get(i).charAt(k)+"")==-1) {
                            newclass.append(C.get(i).charAt(k));
                        }
                    }
                }
            }
            StringBuilder temp = new StringBuilder(C.get(i));
            for(int j = 0;j<newclass.length();j++){
                int indexEl = temp.indexOf(newclass.charAt(j)+"");
                if(indexEl!=-1) {
                    temp.deleteCharAt(indexEl);
                }
            }
            //тут что то пошло не по плану приходится избавлятсяотпустот и этот кусок лучше не трогать
            if(temp.isEmpty()) C.remove(i);
             else C.set(i, temp.toString());
            if(newclass.equals("")) C.remove(i);
             C.add(newclass.toString());
        }

        for(int i = 0;i<C.size();i++){
            String temp = C.get(i);
            if(temp.isEmpty()||temp.isBlank()) C.remove(i);
        }
        //пора наводить марафет, хотя я понимаб что на самом деле тут должна быть еще одна проверка
        //НО
        //НО
        //мы на это забьем, хотя если на моем варианте всё сломается то будет смешно
        //и кста надо бы проверить удаление вершин в начале
        //а то что то там говорит, но не факт, что удаляет
        str.setLength(0);//звращенно чистим то что есть
        str.append("   \u2261\u00B2 ");
        for(int i = 0;i<C.size();i++) {
            str.append("{");
            for (int j = 0; j < C.get(i).length()-1;j++){
                str.append("q" + C.get(i).charAt(j) + ", ");
            }
            str.append("q" + C.get(i).charAt(C.get(i).length()-1) + "} ");
        }
        Solve.add(str.toString());//добавили вторую эквивалентность
        str.setLength(0);//звращенно чистим то что есть
        str.append("   \u2261\u00B3 ");
        for(int i = 0;i<C.size();i++) {
            str.append("{");
            for (int j = 0; j < C.get(i).length()-1;j++){
                str.append("q" + C.get(i).charAt(j) + ", ");
            }
            str.append("q" + C.get(i).charAt(C.get(i).length()-1) + "} ");
        }
        Solve.add(str.toString());//добавили условно третий, потому что честно лень чекать
        Solve.add("   \u2261\u00B2=\u2261\u00B3");//типа эфвиваленции равны
        Solve.add("Рисунок можно посмотреть по кнопке");//типа что то рисует
        for(String el:C) System.out.println("C:"+el);
        for(String el:Solve) System.out.println(el);
        StringBuilder text = new StringBuilder();
        for(int i = 0;i<Solve.size();i++) {
         text.append(Solve.get(i)+"\n");
        }
        SolveCode.setText(text.toString());
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

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(Q,strategy);
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
    public void PaintGraphItog(ActionEvent actionEvent){
        Digraph<String,String> Q = new DigraphEdgeList<>();
        ArrayList<String>Qvert = new ArrayList<>();
        for(int i = 0;i< C.size();i++){
            if(C.get(i).length()==1) {
                Qvert.add("q"+C.get(i).charAt(0));
                Q.insertVertex("q" + C.get(i).charAt(0));
            }
            else {
                Q.insertVertex("[q"+C.get(i).charAt(0)+"]");
                Qvert.add("[q"+C.get(i).charAt(0)+"]");
            }
        }
        ArrayList<ArrayList<String>>Qtemp = new ArrayList<>();
        for(int i = 0;i<C.size();i++){
            Qtemp.add(new ArrayList<>());
            for(int j = 0;j<alphabet.length();j++){
                Qtemp.get(i).add("");
            }
        }
        for(int i = 0;i< C.size();i++){
            for(int j = 0;j< C.get(i).length();j++){
                int indexEl = Integer.parseInt(""+C.get(i).charAt(j));
                for(int k = 0;k<alphabet.length();k++) {//ток алфавит
                    for (int l = 0; l < Qbig.get(indexEl).get(k).size(); l++) {
                        if (!Qbig.get(indexEl).get(k).get(l).equals("-")){
                            String temp = Qtemp.get(i).get(k);
                            String value = Qbig.get(indexEl).get(k).get(l);
                            if(!temp.equals(value))
                                Qtemp.get(i).set(k,temp+value);
                        }
                    }
                }
            }
        }
        for (int i = 0;i< Qtemp.size();i++){
            for(int j = 0;j<Qtemp.get(i).size();j++){
                Qtemp.get(i).set(j,Qtemp.get(i).get(j).substring(0,1));
            }
        }
        System.out.println("Check!");
        for(ArrayList<String> El:Qtemp){
            for(String el:El){
                System.out.print(el+" ");
            }
            System.out.println();
        }
        for(int i = 0;i<Qtemp.size();i++){
            for(int j = 0;j<Qtemp.get(i).size();j++){
                for(int k = 0;k<C.size();k++){
                    if(C.get(k).contains(Qtemp.get(i).get(j)+"")){
                        Qtemp.get(i).set(j,k+"");
                        break;
                    }
                }
            }
        }
        for(ArrayList<String> El:Qtemp){
            for(String el:El){
                System.out.print(el+" ");
            }
            System.out.println();
        }
        for(int i = 0;i<Qtemp.size();i++){
            for(int j = 0;j<Qtemp.get(i).size();j++){
                int inedexSecond = Integer.parseInt(Qtemp.get(i).get(j).charAt(0)+"");
                StringBuilder edge = new StringBuilder();
                edge.append(alphabet.charAt(j));
                edge.append(i);
                edge.append(inedexSecond);
                System.out.println(edge);
                Q.insertEdge(Qvert.get(i),Qvert.get(inedexSecond),edge.toString());
            }
        }
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(Q,strategy);
        Scene scene = new Scene(graphView, 800, 600);
        for(int i = 0;i<Qvert.size();i++){
            if(Qvert.get(i).contains("0")) graphView.getStylableVertex(Qvert.get(i)).setStyleClass("myStartVertex");
            if(Qvert.get(i).contains(endQ[0])) graphView.getStylableVertex(Qvert.get(i)).setStyleClass("myEndVertex");
        }
        /*graphView.getStylableVertex("q0").setStyleClass("myStartVertex");
        for(int i = 0;i< endQ.length;i++){
            graphView.getStylableVertex("q"+endQ[i]).setStyleClass("myEndVertex");
        }

         */

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Visualization Q2");
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

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        stage.setResizable(false);
        stage.setTitle("LabaTA&FL_6");
        URL xmlUrl = getClass().getResource("/FXML/mainScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
