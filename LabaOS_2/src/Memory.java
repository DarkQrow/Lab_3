import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Memory {
    ArrayList<Page> OMem = new ArrayList<>();
    Page[] VMem = new Page[8];
    Memory(){
        for(int i = 0;i<8;i++){
            VMem[i] = new Page();
            OMem.add(new Page());
        }
    }

    /*  public int CheckOMem(int n){
        int check = -1;
        for(int i = 0;i<8;i++){
            if(Mem[i].number == n) {
                check = i;
                break;
            }
        }
        return check;
    }
    public int CheckVMem(int n){
        int check = -1;
        for(int i = 8;i<16;i++){
            if(Mem[i].number == n) {
                check = i;
                break;
            }
        }
        return check;
    }
*/
    public void FIFO(int n){
        Page Temp = Page.CopyPage(OMem.get(0));
        OMem.remove(0);
        OMem.add(VMem[n-8]);
        VMem[n-8] = Temp;
    }
    public void FIFOX2(int n){
        System.out.println(1);
     //  boolean check = false;
        for(int i = 0;i<8;i++){
          if(OMem.get(0).R){
              OMem.get(0).R = false;
              Page Temp = Page.CopyPage(OMem.get(0));
              OMem.remove(0);
              OMem.add(Temp);
            //  if(i == 7) check = true;
          }
          else
              break;
        }
        FIFO(n);
    }

    public void ReadMem(int n){
       if(n>7){
           System.out.println("Произведен обмен");
           FIFOX2(n);
           OMem.get(7).ShowPage();
           OMem.get(7).R = true;
       }
       else {
           OMem.get(n).ShowPage();
           OMem.get(n).R = true;
       }
    }
    public void Add(int n,int num){
        if(n>7){
            System.out.println("Произведен обмен");
            if(OMem.get(0).R) {
                OMem.get(0).R = false;
                Page Temp = Page.CopyPage(OMem.get(0));
                OMem.remove(0);
                OMem.add(Temp);
            }
            OMem.get(0).Inf10to2(num);
            OMem.get(0).R = false;
            OMem.get(0).M = false;
            FIFO(n);
            VMem[n-8].R = true;
            VMem[n-8].M = true;
        }
        else {
            if(OMem.get(0).R) {
                OMem.get(0).R = false;
                Page Temp = Page.CopyPage(OMem.get(0));
                OMem.remove(0);
                OMem.add(Temp);
            }
            OMem.get(n).R = true;
            OMem.get(n).M = true;
            OMem.get(n).Inf10to2(num);
        }
    }
    public void ShowMap(){
        System.out.println("На данный момент карта выглядит так: ");
        System.out.print("Оп.Память :");
        for(int i = 0;i<8;i++){
            System.out.print(i+") ");
            for(int j = 0;j<5;j++) {
                System.out.print(OMem.get(i).inf[j]+" ");
            }
        }
        System.out.println();
        System.out.print("Вн.Память :");
        for(int i = 0;i<8;i++){
            System.out.print(i+") ");
            for(int j = 0;j<5;j++) {
                System.out.print(VMem[i].inf[j]+" ");
            }
        }
        System.out.println();
    }


}
