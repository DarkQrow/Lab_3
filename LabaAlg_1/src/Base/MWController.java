package Base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MWController {

    @FXML
    private TextArea CodeList;
    @FXML
    private Button Code;

    @FXML
    private Button Decode;

    @FXML
    private Button Download;

    @FXML
    private ListView ListView;


    @FXML
    public  void initialize(){
        CodeList.setEditable(false);
    }

    FileChooser fx = new FileChooser();
    File selectedfile;
    StringBuilder codedtext = new StringBuilder();
    Huffman.CodeTreeNode tree;

    public void Download(ActionEvent actionEvent) {

        fx.setTitle("Выберите файл для работы");
        fx.setInitialDirectory(new File("C:/Users/Ирочка/IdeaProjects/LabaAlg_1/src/Files"));
        fx.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT","*.txt"));
        selectedfile = fx.showOpenDialog(null);

        if(selectedfile != null){
            ListView.getItems().add(selectedfile.getName());
        }
        else{
            System.out.println("File is not valid");
        }
    }
    public void CodeHuff(ActionEvent actionEvent){

        StringBuilder text = new StringBuilder();

       try( FileReader re = new FileReader(selectedfile)){
           int c;
           while ((c = re.read())!=-1){
              text.append((char)c);
           }
       }
       catch(IOException ex){
           System.out.println("Что то не так при чтении файла");
       }

        TreeMap<Character,Integer> freq = Huffman.countL(text.toString());
        ArrayList<Huffman.CodeTreeNode> codeTreeNodes = new ArrayList<>();
        for(Character c :freq.keySet()){
            codeTreeNodes.add(new Huffman.CodeTreeNode(c,freq.get(c)));
        }

         tree = Huffman.Huff(codeTreeNodes);

        TreeMap<Character,String> codes = new TreeMap<>();
        for(Character c: freq.keySet()){
            codes.put(c, tree.getCodeForCharecter(c,""));
        }

        for(int i = 0;i<text.toString().length();i++){
            codedtext.append(codes.get(text.toString().charAt(i)));
        }
        for(Map.Entry<Character,String> entry:codes.entrySet()){
            CodeList.appendText("Буква: "+ entry.getKey()+" Код: "+entry.getValue()+'\n');
        }
        System.out.println("Коды полученные при кодировании: "+codes.toString());
        try(FileWriter wr = new FileWriter("C:/Users/Ирочка/IdeaProjects/LabaAlg_1/src/Files/codedhuff.txt")){
            wr.write(codedtext.toString());
        }
        catch (IOException ex){
            System.out.println("Что то пошло не так при записи сообщения ");
        }

    }
    public void DecodeHuff(ActionEvent actionEvent){
        StringBuilder codes = new StringBuilder();
        try(FileReader re = new FileReader(selectedfile)) {
            int c;
            while ((c = re.read())!=-1){
                codes.append((char)c);
            }
        }
        catch (IOException ex){
            System.out.println("Что то не так с чтением закодированного файла");
        }
        String decode = Huffman.HuffBack(codes.toString(),tree);
        try(FileWriter wr = new FileWriter("C:/Users/Ирочка/IdeaProjects/LabaAlg_1/src/Files/decodedHuff.txt")){
            wr.write(decode);
        }
        catch (IOException ex){
            System.out.println("Что то пошло не так при записи сообщения ");
        }

    }

    public void CodeLZW(ActionEvent actionEvent) {
        StringBuilder text = new StringBuilder();
        try(FileReader re = new FileReader(selectedfile)){
            int c;
            while ((c = re.read())!=-1){
                text.append((char)c);
            }
        }
        catch (IOException ex){
            System.out.println("Что то пошло не так при чтение фалйа для кодирования LZW");
        }
        ArrayList<String> code = LZW.LZWcode(text.toString());
        StringBuilder codedtext = new StringBuilder();
        for(int i = 0;i<code.size();i++){
            codedtext.append(code.get(i)+" ");
        }

        try (FileWriter wr = new FileWriter("C:/Users/Ирочка/IdeaProjects/LabaAlg_1/src/Files/codedLZW.txt")){
            wr.write(codedtext.toString());
            System.out.println(codedtext);
        }
        catch (IOException ex){
            System.out.println("Что то пошло не так при создании кодированного LZW файла ");
        }

    }

    public void DecodeLZW(ActionEvent actionEvent) {
        StringBuilder text = new StringBuilder();
        try (FileReader re = new FileReader(selectedfile)){
            int c;
            while ((c = re.read())!=-1){
                text.append((char)c);
            }
        }
        catch (IOException ex){
            System.out.println("Что то пошло не так при чтение файла для декодинга LZW");
        }

        String codedtext = text.toString();


        ArrayList<String> codelist = new ArrayList<>();
        int i = 0;
        while(i<codedtext.length()){
            int index = codedtext.indexOf(" ");
            if(index == 0) index++;
            String tmp = codedtext.substring(0,index);
            codelist.add(tmp);
            codedtext =codedtext.replaceFirst(tmp+" ","");
        }
        for(String el:codelist){
            System.out.print(el+" ");
        }
        try(FileWriter wr = new FileWriter("C:/Users/Ирочка/IdeaProjects/LabaAlg_1/src/Files/decodedLZW.txt")){
            wr.write(LZW.LZWdecode(codelist));
        }
        catch (IOException ex){
            System.out.println("Что то пошло не так при записи декодировки LZW");
        }
    }
}
