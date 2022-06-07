package Controllers;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.lang.invoke.VolatileCallSite;
import java.net.URL;
import java.util.*;

public class MainController extends Application{
    @FXML
    private TextField mText;

    @FXML
    private TextField Ntext;

    @FXML
    private TextField StartText;

    @FXML
    private TextField T1Text;

    @FXML
    private TextField T2Text;
    @FXML
    private TextField kText;
    @FXML
    private TextField PmText;
    @FXML
    private TextField PkText;

    int N = 0;
    int m = 0;
    int Start = 0;
    int T1 = 0;
    int T2 = 0;
    int k = 0;
    int PK = 0;
    int PM = 0;
    ArrayList<ArrayList<Integer>>  GraphWeight;
    ArrayList<Integer> GreenGuy ;
    ArrayList<Integer> GreenWeight;
    ArrayList<Integer> YellowGuy ;
    ArrayList<Integer> YellowWeight;
    int iteration;

    @FXML
    void BuildDataToGraph(ActionEvent event) {
        //Получаем данные с полей и лучше чтобы они там были тк защиту прописывать лень
        N = Integer.parseInt(Ntext.getText());
        Start = Integer.parseInt(StartText.getText());
        T1 = Integer.parseInt(T1Text.getText());
        T2 = Integer.parseInt(T2Text.getText());
        m = Integer.parseInt(mText.getText());
        PK = Integer.parseInt(PkText.getText());
        PM = Integer.parseInt(PmText.getText());
        k = Integer.parseInt(kText.getText());
        GraphWeight = new ArrayList<>();
        GreenGuy = new ArrayList<>();
        GreenWeight = new ArrayList<>();
        YellowGuy = new ArrayList<>();
        YellowWeight = new ArrayList<>();

        ArrayList<ArrayList<Integer>> GraphWeightCopy = new ArrayList<>();
        for(int i = 0;i<N;i++) {
            GraphWeight.add(new ArrayList<>());
            GraphWeightCopy.add(new ArrayList<>());
            for (int j = 0; j < N; j++) {
                GraphWeight.get(i).add(-1);
                GraphWeightCopy.get(i).add(999);
            }
        }
        for(int i = 0;i<N;i++){
            for(int j =0;j<N;j++){
                int random = (int)(Math.random()*(T2-T1+1))+T1;
                if((i!=j)&&(GraphWeight.get(i).get(j)==-1)&&(GraphWeight.get(j).get(i)==-1)) {
                    GraphWeight.get(i).set(j, random);
                    GraphWeight.get(j).set(i, random);
                    GraphWeightCopy.get(i).set(j, random);
                    GraphWeightCopy.get(j).set(i, random);
                }
            }
        }

        for(ArrayList<Integer>el1:GraphWeightCopy){
            for(Integer el:el1){
                System.out.print(el+" ");
            }
            System.out.println();
        }

        //cостовляем путь для жадного алгоритма
        GreenGuy.add(Start);//добавляем начало
        int prevEl = Start;

        while (GreenGuy.size()!=N){
            int nextmin = Collections.min(GraphWeightCopy.get(prevEl));

            int nextEl = GraphWeightCopy.get(prevEl).indexOf(nextmin);

            for(int i = 0;i<N;i++) {
                GraphWeightCopy.get(prevEl).set(i, 999);
                GraphWeightCopy.get(i).set(prevEl, 999);
            }

            GreenGuy.add(nextEl);
            prevEl = nextEl;
            GreenWeight.add(nextmin);
        }
        GreenWeight.add(GraphWeight.get(prevEl).get(Start));
        GreenGuy.add(Start);//а теперь конец
        System.out.println(GreenGuy);
        System.out.println(GreenWeight);
        //ну придется все для Голдберга тоже состовлять здесь тк мне нужно 2 переменные создать
        int max ;
        int minmax = 9999;
        ArrayList<ArrayList<Integer>> O = pathGenerator(N,Start,m);
        for(int i = 0;i<O.size();i++){
            max = 0;
            for(int k = 0;k<N;k++){
                max+=GraphWeight.get(O.get(i).get(k)).get(O.get(i).get(k+1));
            }
            if(max<minmax) minmax = max;
        }
        int iter = 0;
        int counter = 1;
         while (counter<k) {
            iter++;
            ArrayList<Integer> ParCh = new ArrayList<>();
            System.out.println("Иттерация" + iter);
            ArrayList<ArrayList<Integer>> newParents = new ArrayList<>();
            ArrayList<ArrayList<Integer>> ParentsGenes = new ArrayList<>();
            ArrayList<Integer> MAX = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                newParents.add(new ArrayList<>());
                ParentsGenes.add(O.get(i));
                MAX.add(-1);
            }
            ArrayList<ArrayList<Integer>> pairs = PairGenerator(m, PK);
            for (int i = 0; i < m; i++) {
                int min = 9999;
                ArrayList<ArrayList<Integer>> children = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    children.add(new ArrayList<>());

                }
                System.out.println("Род1" + ParentsGenes.get(pairs.get(i).get(0)));
                System.out.println("Род2" + ParentsGenes.get(pairs.get(i).get(1)));
                //вот тут кроссоверт делается
                int T = (int) (Math.random() * (ParentsGenes.get(0).size() - 2) + 1);
                System.out.println("TКрос:" + T);
                for (int j = 0; j < T; j++) {
                    children.get(0).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                    children.get(1).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                }
                for (int j = T; j < ParentsGenes.get(0).size(); j++) {
                    if (children.get(0).contains(ParentsGenes.get(pairs.get(i).get(1)).get(j))) {
                        for (int k = 1; k < T; k++) {
                            if (!children.get(0).contains(children.get(1).get(k))) {
                                children.get(0).add(children.get(1).get(k));
                                break;
                            }
                        }
                    } else {
                        children.get(0).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                    }
                    if (children.get(1).contains(ParentsGenes.get(pairs.get(i).get(0)).get(j))) {
                        for (int k = 1; k < T; k++) {
                            if (!children.get(1).contains(children.get(0).get(k))) {
                                children.get(1).add(children.get(0).get(k));
                                break;
                            }
                        }
                    } else {
                        children.get(1).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                    }
                }
                children.get(0).add(children.get(0).get(0));
                children.get(1).add(children.get(1).get(0));
                System.out.println("Реб1" + children.get(0));
                System.out.println("Реб2" + children.get(1));

                //а вот тут мутация
                int randomChild = (int) (Math.random() * 2);
                int pmrandom = (int) (Math.random() * (100 - 0 + 1)) + 0;
                int mute1 = 1 + (int) (Math.random() * (N / 2));
                int mute2 = (N / 2) + (int) (Math.random() * (N / 2));
                while (pmrandom > PM) {
                    mute1 = 1 + (int) (Math.random() * (N / 2));
                    mute2 = (N / 2) + (int) (Math.random() * (N / 2));
                    pmrandom = (int) (Math.random() * (100 - 0 + 1)) + 0;
                }
                int tempEl1 = children.get(randomChild).get(mute1);
                int tempEl2 = children.get(randomChild).get(mute2);
                children.get(randomChild).set(mute1, tempEl2);
                children.get(randomChild).set(mute2, tempEl1);
                System.out.println("Mреб " + randomChild + "эл " + mute1 + " " + mute2 + ": " + children.get(randomChild));
                //выбираем лучщего по суммам
                ArrayList<Integer> result = new ArrayList<>();
                for (int l = 0; l < 2; l++) {
                    max = 0;
                    for (int k = 0; k < N; k++) {
                        max += GraphWeight.get(children.get(l).get(k)).get(children.get(l).get(k + 1));

                    }
                    if (max < min) {
                        min = max;
                        result = children.get(l);
                    }
                    System.out.println("Реб"+l+" "+max);
                }
                for (int l = 0; l < 2; l++) {
                    max = 0;
                    for (int k = 0; k < N; k++) {
                        max += GraphWeight.get(ParentsGenes.get(pairs.get(i).get(l)).get(k)).get(ParentsGenes.get(pairs.get(i).get(l)).get(k + 1));
                    }
                    if (max < min) {
                        min = max;
                        result = ParentsGenes.get(pairs.get(i).get(l));
                    }
                    System.out.println("Род"+l+" "+max);
                }

                newParents.set(i, result);


                max = 0;
                for (int k = 0; k < N; k++) {
                    max += GraphWeight.get(newParents.get(i).get(k)).get(newParents.get(i).get(k + 1));
                }
                MAX.set(i, max);
                //
            }
                System.out.println(MAX);
                max = Collections.min(MAX);
                if (max == minmax) counter++;
                if (max < minmax) {
                    minmax = max;
                    counter = 1;
                }
                if (max > minmax) {
                    minmax = max;
                    counter = 1;
                }
                System.out.println("MIN " + minmax + " k:" + counter + "/" + k);

                for(int i = 0;i<m;i++){
                    for(int j = 0;j< newParents.get(0).size();j++) {
                        ParentsGenes.get(i).set(j,newParents.get(i).get(j));
                    }
                }
                if(counter==k) {
                    YellowGuy = ParentsGenes.get(0);
                    for (int k = 0; k < N; k++) {
                        YellowWeight.add(GraphWeight.get(ParentsGenes.get(0).get(k)).get(ParentsGenes.get(0).get(k + 1)));
                    }
                    iteration = iter;
                }
        }

    }

    @FXML
    void showBlueGraph(ActionEvent event) {
        Graph<Integer,String> StartGraph = new GraphEdgeList<>();
        for(int i = 0;i<N;i++){
            StartGraph.insertVertex(i);
        }
        for(int i = 0;i<N;i++){
            for(int j =N-1;j>i;j--){
                int tempWeight = GraphWeight.get(i).get(j);
                if(i!=j) {
                    StartGraph.insertEdge(i,j,tempWeight+" \""+i+"-"+j+"\"");
                }
            }
        }
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<Integer, String> graphView = new SmartGraphPanel<>(StartGraph,strategy);

        BorderPane root = new BorderPane();
        graphView.setMinSize(600,800);

        Pane info = new Pane();
        info.setMinSize(400,800);
        info.setStyle("-fx-background-color: #909090");
        Label methodName = new Label(" Алгоритм Голдберга");
        methodName.setWrapText(true);
        methodName.setMaxSize(200,200);
        methodName.setLayoutX(130);
        methodName.setFont(Font.font("Arial", FontWeight.BOLD,30));
        methodName.setTextFill(Color.web("#fcff5c"));
        methodName.setAlignment(Pos.CENTER);
        Rectangle Line = new Rectangle();
        Line.setWidth(400);
        Line.setHeight(5);
        Line.setLayoutY(92);

        StringBuilder pathText = new StringBuilder();
        pathText.append("Лучший Путь: \n");
        for(int i = 0;i<YellowGuy.size();i++){
            pathText.append(YellowGuy.get(i));
            if(i+1!=YellowGuy.size()) pathText.append("->");
        }
        Label path = new Label(pathText.toString());
        path.setTextFill(Color.web("#fcff5c"));
        path.setFont(Font.font("Arial",FontWeight.BOLD,20));
        path.setLayoutY(100);
        path.setLayoutX(20);
        int summTemp = 0;
        for(int i = 0;i<YellowWeight.size();i++){
            summTemp+=YellowWeight.get(i);
        }
        Label pathLength = new Label("Длина пути: "+summTemp);
        pathLength.setLayoutY(150);
        pathLength.setLayoutX(20);
        pathLength.setTextFill(Color.web("#fcff5c"));
        pathLength.setFont(Font.font("Arial",FontWeight.BOLD,20));
        pathText.setLength(0);

        Label Iter = new Label("Количество поколений: "+iteration);
        Iter.setLayoutY(175);
        Iter.setLayoutX(20);
        Iter.setTextFill(Color.web("#fcff5c"));
        Iter.setFont(Font.font("Arial",FontWeight.BOLD,20));

        Rectangle Line2 = new Rectangle();
        Line2.setWidth(400);
        Line2.setHeight(5);
        Line2.setLayoutY(198);

        pathText.append("\tТаблица смежности\n");
        pathText.append("    ");
        for(int i = 0;i<N;i++){
            pathText.append(i+"   ");
        }
        pathText.append("\n");
        for(int i = 0;i<N;i++){
            pathText.append("-----");
        }
        pathText.append("\n");
        for(int i = 0;i<GraphWeight.size();i++){
            pathText.append(i+" |");
            for(int j = 0;j<GraphWeight.get(i).size();j++){
                pathText.append(GraphWeight.get(i).get(j)+" ");
            }
            pathText.append("\n\n");
        }
        Label tableSm = new Label(pathText.toString());
        tableSm.setTextFill(Color.web("#fcff5c"));
        tableSm.setFont(Font.font("Arial",FontWeight.BOLD,20));
        tableSm.setLayoutY(200);
        tableSm.setLayoutX(20);
        info.getChildren().add(methodName);
        info.getChildren().add(Line);
        info.getChildren().add(path);
        info.getChildren().add(pathLength);
        info.getChildren().add(Line2);
        info.getChildren().add(tableSm);
        info.getChildren().add(Iter);
        root.setRight(info);
        root.setLeft(graphView);
        Scene scene = new Scene(root, 1000, 800);

        //graphView.setAutomaticLayout(true);
        for(int i = 0;i<N;i++){
            String temp = YellowWeight.get(i)+" \""+YellowGuy.get(i)+"-"+YellowGuy.get(i+1)+"\"";
            if(YellowGuy.get(i)>YellowGuy.get(i+1)) {
                temp = YellowWeight.get(i ) + " \"" + YellowGuy.get(i+1) + "-" + YellowGuy.get(i) + "\"";
            }
            graphView.getStylableEdge(temp).setStyleClass("myYellowEdge");
            graphView.getStylableVertex(i).setStyleClass("myYellowVertex");
        }
        graphView.getStylableVertex(Start).setStyleClass("myStartYellowVertex");

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Голдберг Graph");
        stage.setScene(scene);
        stage.show();

        graphView.init();
    }

    @FXML
    void showRedGraph(ActionEvent event) {
        Graph<Integer,String> StartGraph = new GraphEdgeList<>();
        for(int i = 0;i<N;i++){
            StartGraph.insertVertex(i);
        }
        for(int i = 0;i<N;i++){
            for(int j =N-1;j>i;j--){
                int tempWeight = GraphWeight.get(i).get(j);
                if(i!=j) {
                    StartGraph.insertEdge(i,j,tempWeight+" \""+i+"-"+j+"\"");
                }
            }
        }
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<Integer, String> graphView = new SmartGraphPanel<>(StartGraph,strategy);

        BorderPane root = new BorderPane();
        graphView.setMinSize(600,800);

        Pane info = new Pane();
        info.setMinSize(400,800);
        info.setStyle("-fx-background-color: white");
        Label methodName = new Label(" Жадный алгоритм");
        methodName.setWrapText(true);
        methodName.setMaxSize(200,200);
        methodName.setLayoutX(130);
        methodName.setFont(Font.font("Arial", FontWeight.BOLD,30));
        methodName.setTextFill(Color.web("#483D8B"));
        methodName.setAlignment(Pos.CENTER);
        Rectangle Line = new Rectangle();
        Line.setWidth(400);
        Line.setHeight(5);
        Line.setLayoutY(92);

        StringBuilder pathText = new StringBuilder();
        pathText.append("Путь: \n");
        for(int i = 0;i<GreenGuy.size();i++){
            pathText.append(GreenGuy.get(i));
            if(i+1!=GreenGuy.size()) pathText.append("->");
        }
        Label path = new Label(pathText.toString());
        path.setTextFill(Color.web("#483D8B"));
        path.setFont(Font.font("Arial",FontWeight.BOLD,20));
        path.setLayoutY(100);
        path.setLayoutX(20);
        int summTemp = 0;
        for(int i = 0;i<GreenWeight.size();i++){
            summTemp+=GreenWeight.get(i);
        }
        Label pathLength = new Label("Длина пути: "+summTemp);
        pathLength.setLayoutY(150);
        pathLength.setLayoutX(20);
        pathLength.setTextFill(Color.web("#483D8B"));
        pathLength.setFont(Font.font("Arial",FontWeight.BOLD,20));
        pathText.setLength(0);

        Rectangle Line2 = new Rectangle();
        Line2.setWidth(400);
        Line2.setHeight(5);
        Line2.setLayoutY(175);

        pathText.append("\tТаблица смежности\n");
        pathText.append("    ");
        for(int i = 0;i<N;i++){
            pathText.append(i+"   ");
        }
        pathText.append("\n");
        for(int i = 0;i<N;i++){
            pathText.append("-----");
        }
        pathText.append("\n");
        for(int i = 0;i<GraphWeight.size();i++){
            pathText.append(i+" |");
            for(int j = 0;j<GraphWeight.get(i).size();j++){
                pathText.append(GraphWeight.get(i).get(j)+" ");
            }
            pathText.append("\n\n");
        }
        Label tableSm = new Label(pathText.toString());
        tableSm.setTextFill(Color.web("#483D8B"));
        tableSm.setFont(Font.font("Arial",FontWeight.BOLD,20));
        tableSm.setLayoutY(180);
        tableSm.setLayoutX(20);
        info.getChildren().add(methodName);
        info.getChildren().add(Line);
        info.getChildren().add(path);
        info.getChildren().add(pathLength);
        info.getChildren().add(Line2);
        info.getChildren().add(tableSm);
        root.setRight(info);
        root.setLeft(graphView);
        Scene scene = new Scene(root, 1000, 800);

        //graphView.setAutomaticLayout(true);
        for(int i = 0;i<N;i++){
            String temp = GreenWeight.get(i)+" \""+GreenGuy.get(i)+"-"+GreenGuy.get(i+1)+"\"";
            if(GreenGuy.get(i)>GreenGuy.get(i+1)) {
                temp = GreenWeight.get(i ) + " \"" + GreenGuy.get(i+1) + "-" + GreenGuy.get(i) + "\"";
            }
            graphView.getStylableEdge(temp).setStyleClass("myVioletEdge");
            graphView.getStylableVertex(i).setStyleClass("myVioletVertex");
        }
        graphView.getStylableVertex(Start).setStyleClass("myStartVioletVertex");


        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.setTitle("Жадный Graph");
        stage.setScene(scene);
        stage.show();

        graphView.init();

    }

    @FXML
    void showStartGraph(ActionEvent event) {
        Graph<Integer,String> StartGraph = new GraphEdgeList<>();
        for(int i = 0;i<N;i++){
            StartGraph.insertVertex(i);
        }
        for(int i = 0;i<N;i++){
            for(int j =N-1;j>i;j--){
                int tempWeight = GraphWeight.get(i).get(j);
                if(i!=j) {
                    StartGraph.insertEdge(i,j,tempWeight+" \""+i+"-"+j+"\"");
                }
            }
        }
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<Integer, String> graphView = new SmartGraphPanel<>(StartGraph,strategy);
        Scene scene = new Scene(graphView, 800, 600);
        graphView.getStylableVertex(Start).setStyleClass("myStartVertex");
        //graphView.setAutomaticLayout(true);


        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Start Graph");
        stage.setScene(scene);
        stage.show();

        graphView.init();
    }

    public static void main(String[]args){launch(args);}

    public static ArrayList<ArrayList<Integer>> PairGenerator(int Z,int pk){
        ArrayList<ArrayList<Integer>> Pairs = new ArrayList<>();
        for(int i = 0;i<Z;i++){
            Pairs.add(new ArrayList<>());
        }
        for(int i = 0;i<Z;i++) {
            int pkrandom = (int) (Math.random() *(100-0+1))+0;
            int randomParent1 = i;
            int randomParent2 = (int) (Math.random() * Z);
            while (randomParent1 == randomParent2){ randomParent2 = (int) (Math.random() * Z);}
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(randomParent1);
            temp.add(randomParent2);
            for(int j = 0;j<i;j++){
                while(Pairs.get(j).containsAll(temp)){
                    randomParent2 = (int) (Math.random() * Z);
                    temp.set(0,randomParent1);
                    temp.set(1,randomParent2);
                }
            }
            while (pkrandom > pk) {
                pkrandom = (int) (Math.random() *(100-0+1))+0;
                randomParent2 = (int) (Math.random() * Z);
                while (randomParent1 == randomParent2){ randomParent2 = (int) (Math.random() * Z);}
                for(int j = 0;j<i;j++){
                    while(Pairs.get(j).containsAll(temp)){
                        randomParent2 = (int) (Math.random() * Z);
                        temp.set(0,randomParent1);
                        temp.set(1,randomParent2);
                    }
                }
            }
            Pairs.get(i).add(randomParent1);
            Pairs.get(i).add(randomParent2);
        }
        for(ArrayList<Integer> el:Pairs){
            System.out.println(el.toString());
        }
        return Pairs;
    }

    public static ArrayList<ArrayList<Integer>> pathGenerator(int N,int Start,int m){
        ArrayList<ArrayList<Integer>>result = new ArrayList<>();

        for(int i = 0;i<m;i++) {
            ArrayList<Integer> variants = new ArrayList<>();
            for (int j = 0; j < N; j++) {
                if (j != Start)
                    variants.add(j);
            }
            System.out.println("v:" + variants);
            Random random = new Random();
            ArrayList<Integer> combo = new ArrayList<>();
            combo.add(Start);
            while (combo.size() != N) {
                int tempEl = (random.nextInt(variants.size()) + 0);
                if (!combo.contains(variants.get(tempEl))) {
                    combo.add(variants.get(tempEl));
                    variants.remove(tempEl);
                }
            }
            combo.add(Start);
            System.out.println("combo:" + combo);
            result.add(combo);
        }

        return result;
    }
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        stage.setResizable(false);
        stage.setTitle("LabaEvr_7");
        URL xmlUrl = getClass().getResource("/FXML/Main.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
