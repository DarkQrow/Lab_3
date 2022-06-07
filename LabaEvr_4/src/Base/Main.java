package Base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Добро пожаловать в лабораторную работу №4");
        System.out.println("по Эвристическим методам и алгоритмам");
        System.out.println("У меня 18ый вариант - \" Метод построения расписания с произвольной загрузкой \"");
        System.out.println("Пользователем задаются параметры: N,M,T1 и T2");
        int N,M,T1,T2;//Инициализация параметров
        Scanner in = new Scanner(System.in);
        System.out.print("N: ");
        N = in.nextInt();
        System.out.print("M: ");
        M = in.nextInt();
        System.out.print("T1: ");
        T1 = in.nextInt();
        System.out.print("T2: ");
        T2 = in.nextInt();
        //Cоздание массива NxM с элементами в разбросе от T1 до T2

        ArrayList<ArrayList<Integer>> Tnmlow = new ArrayList<>(M);
        for(int i = 0;i<M;i++){
            ArrayList<Integer> Tm = new ArrayList<>(N);
            for(int j = 0;j<N;j++){
                int random = (int)(Math.random()*(T2-T1+1)+T1);
                Tm.add(random);
            }
            Tnmlow.add(Tm);
        }
        System.out.println("По убыванию");
        SortMatrix(Tnmlow);
        Collections.reverse(Tnmlow);
        PrintMatrix(Tnmlow);
        Alg_Barier(Tnmlow);

    }
    public static void Alg_Barier(ArrayList<ArrayList<Integer>> Tnm) {
        int N = Tnm.get(0).size();
        int M = Tnm.size();
        int Barier = 0;
        for (int i = 0; i < Tnm.size(); i++) { //Получаем сумму всех элементов
            Barier += SumStroke(Tnm.get(i));
        }
        Barier = Barier / Tnm.size();
        System.out.println("Барьер: " + Barier);

        ArrayList<Integer> resultMatrix = new ArrayList<>();
        for (int i = 0; i < Tnm.get(0).size(); i++) {
            resultMatrix.add(0);
        }
        boolean barstart = true;
        int counter = 0;
        while (barstart) {
            int temp = Collections.min(Tnm.get(counter));
            resultMatrix.set(Tnm.get(counter).indexOf(temp), resultMatrix.get(Tnm.get(counter).indexOf(temp)) + temp);
            counter++;
            System.out.println(counter+")"+resultMatrix);
            System.out.println("------------------------------");

            for (int i = 0; i < resultMatrix.size(); i++) {
                if (resultMatrix.get(i) > Barier) {
                    System.out.println("Барьер достигнут");
                    barstart = false;
                }
            }
        }
        ArrayList<Integer> process = new ArrayList<>(N);
        ArrayList<Integer> processtemp = new ArrayList<>(N);
        for(int i = 0;i<N;i++){
            process.add(resultMatrix.get(i));
            processtemp.add(resultMatrix.get(i));
        }

        for(int i = counter;i<M;i++){
            for(int j = 0;j<N;j++){
                int temp = process.get(j);
                processtemp.set(j,temp+Tnm.get(i).get(j));
            }
            int min = Collections.min(processtemp);
            process.set(processtemp.indexOf(min),process.get(processtemp.indexOf(min))+Tnm.get(i).get(processtemp.indexOf(min)));
            System.out.println("------------------------");
            System.out.println(processtemp);
            System.out.println(process);


            for(int j = 0;j<N;j++){
                processtemp.set(j,process.get(j));
            }
        }

        System.out.println("Результат: max"+process+" = "+Collections.max(process));

    }
    public static ArrayList<ArrayList<Integer>> SortMatrix(ArrayList<ArrayList<Integer>>Arr){
        ArrayList<Integer> temp ;
        for(int i = 0;i<Arr.size();i++){
            temp = Arr.get(i);
            int j = i;
            while((j>0)&&(SumStroke(Arr.get(j-1))>SumStroke(temp))){
                Arr.set(j,Arr.get(j-1));
                --j;
            }
            Arr.set(j,temp);
        }
        Collections.reverse(Arr);
       return Arr;
    }
    public static int SumStroke(ArrayList<Integer> Arr){
        int result = 0;
        for (int el:Arr) {
            result+=el;
        }
        return result;
    }
    public static void PrintMatrix(ArrayList<ArrayList<Integer>> Arr){
        System.out.println("Mатрица с суммами: ");
        for (ArrayList<Integer>Ar:Arr) {
            for (int el:Ar) {
                System.out.printf("%3d",el);
            }
            int x = 3*Arr.get(0).size();
            String format = "%"+x+"s";
            System.out.printf(format,"| "+SumStroke(Ar));
            System.out.println();
        }
    }

}
