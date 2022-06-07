package Base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Добро пожаловать в лабораторную работу №2");
        System.out.println("по Эвристическим методоам и алгоритмам");
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

        ArrayList<ArrayList<Integer>> Tnm = new ArrayList<>(M);
        ArrayList<ArrayList<Integer>> Tnmlow = new ArrayList<>(M);
        ArrayList<ArrayList<Integer>> Tnmrise = new ArrayList<>(M);
        for(int i = 0;i<M;i++){
            ArrayList<Integer> Tm = new ArrayList<>(N);
            for(int j = 0;j<N;j++){
                int random = (int)(Math.random()*(T2-T1+1)+T1);
                Tm.add(random);
            }
            Tnm.add(Tm);
            Tnmlow.add(Tm);
            Tnmrise.add(Tm);
        }
        System.out.println("Случайный порядок");
        PrintMatrix(Tnm);
        Alg3_3(Tnm);
        System.out.println("По убыванию");
        SortMatrix(Tnmlow);
        PrintMatrix(Tnmlow);
        Alg3_3(Tnmlow);
        System.out.println("По возростанию");
        SortMatrix(Tnmrise);
        Collections.reverse(Tnmrise);
        PrintMatrix(Tnmrise);
        Alg3_3(Tnmrise);
        System.out.println();
        System.out.println("Теперь сравним по эффективности с выборкой минимальныйх: ");
        PrintMatrix(Tnmlow);
        ArrayList<ArrayList<Integer>> resultMatrix = new ArrayList<>(N);
        for(int i = 0;i<N;i++){
            resultMatrix.add(i,new ArrayList<>());
        }
        //Поиск минимального процесса в каждой строке и добавление в итоговую матрицу
        for(ArrayList<Integer> Tm:Tnmlow){
            int temp = Collections.min(Tm);
            resultMatrix.get(Tm.indexOf(temp)).add(temp);
        }
        PrintMatrix(resultMatrix);
        //Составление списка сумм по процесам
        ArrayList<Integer> resultMax = new ArrayList<>();
        for(ArrayList<Integer> el:resultMatrix)
            resultMax.add(SumStroke(el));

        System.out.print("Результат: max"+resultMax+" = "+Collections.max(resultMax));


    }
    public static void Alg3_3 (ArrayList<ArrayList<Integer>> Tnm){
        int N = Tnm.get(0).size();
        int M = Tnm.size();
        ArrayList<Integer> process = new ArrayList<>(N);
        ArrayList<Integer> processtemp = new ArrayList<>(N);
        for(int i = 0;i<N;i++){
            process.add(0);
            processtemp.add(0);
        }

        int temp1 = Collections.min(Tnm.get(0));
        process.set(Tnm.get(0).indexOf(temp1),temp1);
        processtemp.set(Tnm.get(0).indexOf(temp1),temp1);
        System.out.println(process);
        System.out.println(processtemp);
        for(int i = 1;i<M;i++){
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
        ArrayList<Integer> temp = new ArrayList<>();
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
