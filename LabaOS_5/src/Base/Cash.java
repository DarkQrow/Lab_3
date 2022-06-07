package Base;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Cash {
    Boolean OnOff = false;
    ArrayList<String>cash = new ArrayList<>();
    Memory mem;

    public void setMem(Memory mem) {
        this.mem = mem;
    }

    public void setOnOff(Boolean onOff) {
        OnOff = onOff;
    }
    public void SaveCash(){
        File file = new File("C:\\Users\\Павел\\IdeaProjects\\LabaOS_5\\src\\Base\\Cash.txt");
        try(FileWriter wr = new FileWriter(file)){
            for(int i = 0;i<cash.size();i++){
                wr.write(cash.get(i));
                wr.write('\n');
            }
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
}
