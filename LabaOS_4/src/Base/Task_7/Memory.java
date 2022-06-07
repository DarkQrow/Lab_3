package Base.Task_7;

class Field{
    char[][] field;

    Field(){
        field = new char[10][10];
       for (int i = 0;i<10;i++){
           for(int j = 0;j<10;j++){
               field[i][j] = '0';
           }
       }
    }
}

public class Memory{
Field first;
Field second;

    Memory(){
        first = new Field();
        second = new Field();
        int[][] zn1 = {{1,1,1,1},{1,3,0,1},{1,3,2,1},{1,3,0,1},{2,5,1,1},{2,5,8,2},{2,8,1,2},{3,3,4,2},{3,1,8,2},{4,1,3,1}};
        int[][] zn2 = {{1,3,7,1},{1,6,9,1},{1,9,9,1},{1,7,3,1},{2,0,0,1},{2,0,3,1},{2,8,5,1},{3,3,0,2},{3,7,1,2},{4,5,4,1}};

        first.field = setShip(first.field,zn1);
        second.field = setShip(second.field,zn2);
    }
    public static char[][] setShip(char[][] A,int [][] zn){
        char[][]B = A;
        System.out.println("Расставляем корабли осознанно: ");
      for(int i = 0;i<10;i++){
          B = PlaceCell(B,zn[i][0],zn[i][1],zn[i][2],zn[i][3]);
      }
        return B;
    }
    public static char[][] setRandomShip(char[][]A) {
        char[][]B = A;
        System.out.println("Расставляем Случайным образом корабли");
        for(int i = 1;i<5;i++){
            for(int j = 5-i;j>0;j--) {
                int x = (int) (0 + Math.random() * (10));
                int y = (int) (0 + Math.random() * (10 - i));
                int HW = (int) (1 + Math.random() * 2);
                int count = 0;
                while (!CheckCell(HW, x, y, i, B)) {
                    x = (int) (0 + Math.random() * (10));
                    y = (int) (0 + Math.random() * (10));
                    HW = (int) (1 + Math.random() * 2);
                    count++;
                    if(count == 60)break;
                }
                B = PlaceCell(B, i, x, y, HW);
            }
        }

     return B;
    }

public static boolean CheckCell(int HW,int x, int y,int i, char[][] A){
        int count = 0;
        switch (HW){
            case 1:{
                for (int k = -1; k < i+1; k++) {
                    if((y+k<10)&&(y+k>0)&&(x-1>-1)&&(x+1<10)) {
                        if ((A[x][y + k] == '0')) {
                            count++;
                        }

                        if ((A[x - 1][y + k] == '0')) {
                            count++;
                        }
                        if ((A[x + 1][y + k] == '0')) {
                            count++;
                        }
                    }
                }
                break;
            }
            case 2:{
                for (int k = -1; k < i+1; k++) {
                    if ((x + k < 10) && (x + k > 0) && (y - 1 > -1) && (y + 1 < 10)) {
                        if ((A[x + k][y] == '0')) {
                            count++;
                        }
                        if ((A[x + k][y - 1] == '0')) {
                            count++;
                        }
                        if ((A[x + k][y + 1] == '0')) {
                            count++;
                        }
                    }
                }
                break;
            }
        }
        if(count == (3*i+6))
            return true;
        else
            return false;
}
  public static char[][] PlaceCell(char[][]A,int i,int x, int y,int HW) {
      switch (HW) {
          case 1: {
              for (int k = 0; k < i; k++) {
                  A[x][y + k] = '1';
              }
              break;
          }
          case 2: {
              for (int k = 0; k < i; k++) {
                  A[x + k][y] = '1';
              }
              break;
          }
          default: {
              System.out.println("Что то не так");
              break;
          }

      }
      return A;
  }
 public static boolean Shot(Field A,int x,int y) {
     boolean check = true;
     switch (A.field[x][y]) {
         case '1': {
             A.field[x][y] = '*';
             break;
         }
         case '*':
         case '!': {
             check = false;
             break;
         }
         case '0': {
             A.field[x][y] = '!';
             break;
         }
     }

     return check;
 }
    public boolean CheckWin(){
        int count1 = 0;
        int count2 = 0;
        boolean Check = false;
        for (char[] el:first.field) {
            for (char cell:el) {
                if((cell == '*')) count1++;
            }
        }
        for (char[] el:second.field) {
            for (char cell:el) {
                if((cell == '*')) count2++;
            }
        }
        if((count1 == 19)&&(count2==20)){
            System.out.println("Draw");
            Check = true;
        }
        else{
            if (count2 == 20) {
                System.out.println("FirstWin");
                Check = true;
            } else {
                if (count1 == 19) {
                    System.out.println("SecondWin");
                    Check = true;
                }
            }
        }
            // System.out.println(count1);
            return Check;

    }
}