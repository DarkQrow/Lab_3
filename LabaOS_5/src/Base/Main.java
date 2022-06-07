package Base;

import java.util.Scanner;

public class Main {
    public static void main(String[]args ){
        Scanner read = new Scanner(System.in);
        Cash history = new Cash();

        int choice = 0;
        System.out.println("Добро пожаловать в лабораторную работу №5");
        System.out.print("Какого размера должны быть блоки: ");
        int x = read.nextInt();
        System.out.print("Сколько блоков необходимо выделить: ");
        int y = read.nextInt();
        if(y >8){
            while(y>8){
                System.out.print("Невозможно выделить такое кол-во блоков,"+'\n'+"Не хватает памяьт"+'\n'+"Пожалуйсто повторите ввод: ");
                y = read.nextInt();
            }
        }
        Memory mem = new Memory(y,x);
        //инициализация памяти
        System.out.println("Теперь пользователь может свободно выбрать одну из нескольких опций: ");
        while(choice!=7) {
            System.out.println("1.Запись данных в указанные блоки");
            System.out.println("2.Чтение данных из заданных блоков");
            System.out.println("3.Получение информации о состоянии бл.пространства");
            System.out.println("4.Начало транзакционной сессии");
            System.out.println("5.Завершение транзакционной сессии");
            System.out.println("6.Откат");
            System.out.println("7.Завершение действий");
            choice = read.nextInt();
            switch (choice){

                case 1:{
                    StringBuilder str1 = new StringBuilder();
                    StringBuilder str2 = new StringBuilder();
                    System.out.println("Вы выбрали запись в блоки");
                    System.out.println("В какие блоки будет произодится запись(вводите через пробел)");
                    System.out.print("Номера блоков: ");
                    read.nextLine();
                    String blchoice[] = read.nextLine().split(" ");

                    int[] blchoicearg  = new int[blchoice.length];
                    for(int i = 0;i<blchoicearg.length;i++){
                        blchoicearg[i] = Integer.parseInt(blchoice[i]);
                        str1.append(blchoice[i]+" ");
                    }
                    System.out.println("Чтобы вы хотели туда записать(Вводите через пробел)");
                    System.out.println("Имейте в виду, что вся лишняя информация не будет записываться");
                    System.out.print("Запись: ");
                    String[] info = read.nextLine().split(" ");
                    Byte[]tmp = new Byte[x];
                    for(int i = 0;i<x;i++){
                        if(i< info.length)
                        tmp[i] = (byte)Integer.parseInt(info[i]);
                        else tmp[i] = 0;
                        str2.append(tmp[i]+" ");
                    }
                    mem.setBlock(blchoicearg,tmp);


                    if(history.OnOff)history.cash.add("Запись в блоки №"+str1+"|"+str2);
                    break;}
                case 2:{
                    StringBuilder str = new StringBuilder();
                    System.out.println("Вы выбрали чтение блоков");
                    System.out.println("Какие блоки вы желаете отследить(если все, то введите 9)");
                    String blocks[] = read.next().split(" ");
                    if(blocks.length == 1) {
                        mem.readBlock();
                        str.append("Всего");
                    }
                    else{
                    int[] blchoicearg  = new int[blocks.length];
                        for (int i = 0; i < blchoicearg.length; i++) {
                            blchoicearg[i] = Integer.getInteger(blocks[i]);
                            str.append(blocks[i]+" ");
                        }
                        mem.readBlock(blchoicearg);
                    }

                    if(history.OnOff)history.cash.add("Чтение "+str);
                    break;}
                case 3:{
                    mem.printBlocksInfo();
                    System.out.println("Кэш занимает: "+history.cash.size()+" байт");
                    if(history.OnOff)history.cash.add("Чтение общей инфы");
                    break;}
                case 4:{
                    history.setOnOff(true);
                    System.out.println("Кэширование запущено...");
                    if(history.OnOff)history.cash.add("Кэширование запущено...");
                    Memory C = mem.CopyMem();
                    history.setMem(C);
                    break;}
                case 5:{
                    if(history.OnOff)history.cash.add("Кэширование приостановлено...");
                    System.out.println("Кэширование приостановлено...");
                    history.setOnOff(false);
                    history.SaveCash();
                    break;}
                case 6:{
                    mem = history.mem;
                    if(history.OnOff)history.cash.add("Откат...");
                    System.out.println("Откат...");
                    break;}
                case 7:{
                    System.out.println("Инициализация завершения...");
                    break;}

            }



        }
    }

}
