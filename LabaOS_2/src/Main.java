import java.util.Scanner;
public class Main {
public static void main(String[]args){
    Scanner in = new Scanner(System.in);
    Memory start = new Memory();
    System.out.println("Изначально предоставляется оп.память и вн. память на 256 байт");
    System.out.println("В каждой из них есть по 8 ячеек(отсчет ведется с 0,начальная 0 конечная 15)");
    System.out.println("В каждую ячейку можно записать число");
    System.out.println("По умолчанию карта выглядит так: ");
    start.ShowMap();
    int ch = 0;

    while(ch!=4){

        System.out.println("Вы можете произвести с ней 4 действия: ");
        System.out.println("1. Произвести чтение");
        System.out.println("2. Записать число в ячейку");
        System.out.println("3. Вывести карту");
        System.out.println("4. Закончить работу");
        System.out.print("Выбор: ");
        ch = in.nextInt();
        switch (ch){
            case 1:
                int ch1 ;
                System.out.print("Число из какой ячейки нужно считать: ");
                ch1 = in.nextInt();
                start.ReadMem(ch1);
                break;
            case 2:
                int ch2 ;
                int num ;
                System.out.print("Какое число будет записываться: ");
                num = in.nextInt();
                System.out.print("В какую ячейку: ");
                ch2 = in.nextInt();
                start.Add(ch2,num);
                break;
            case 3:
                start.ShowMap();
                break;
        }
    }
}


}
