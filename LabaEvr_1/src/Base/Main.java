package Base;

import java.util.*;

public class Main {

    public static void main(String[] args) {
	//
        int n ;
        System.out.println("Добро подаловать в Лабораторную работу №1 по Эвристическим методам и алгоритмы");
        System.out.println("У меня по списку 18ый вариант");
        System.out.println("Метод: HDMT - Метод половинного деления");
        System.out.println("Количество процессоров (n):");
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        int a;
        int b;
        System.out.println("Количество заданий процессоров (m) находится в пределах: [a]");
        System.out.println("a:=");
        a = in.nextInt();
        int m = a;

       // System.out.println("Случайно сгенерированное значение m = "+m);
        System.out.println("Множество весов заданий (Т)[a,b]:");
        System.out.println("b:=");
        b = in.nextInt();
        int c = in.nextInt();
        //Создание матрицы - двумерного массива

        Integer [][] NM = new Integer[n][m];
        int T = 35;

            for(int j = 0;j<m;j++){
                T = b +(int)(Math.random()*(c-b));
                NM[0][j] = T;
            }
            for(int i = 1;i<n;i++){
                NM[i] = NM[i-1];
            }

        printMatrix(NM);
        //Сортируем в порядке убывания
        Arrays.sort(NM[0], Comparator.reverseOrder());

        printMatrix(NM);
        System.out.println("Множество заданий принимает вид: Т = "+Arrays.toString(NM[0]));
        //Начало метода половинного деления, переход на первый уровень
        ArrayList<Integer> FirstR = new ArrayList<>();
        ArrayList<Integer> FirstL = new ArrayList<>();
        ArrayList<Integer> SecondL1 = new ArrayList<>();
        ArrayList<Integer> SecondL2 = new ArrayList<>();
        ArrayList<Integer> SecondR1 = new ArrayList<>();
        ArrayList<Integer> SecondR2 = new ArrayList<>();
        FirstL.add(NM[0][0]);
        FirstR.add(NM[0][1]);

        for(int i = 2;i<m;i++){
            if(SumMat(FirstL)<=SumMat(FirstR))
                FirstL.add(NM[0][i]);
            else
                FirstR.add(NM[0][i]);
        }
        System.out.println("Процессоры с значениями загрузок после перехода на первый уровень:");
        System.out.println("Первый:"+FirstL);
        System.out.println("Нагрузка на первый процессор: "+SumMat(FirstL));
        System.out.println("Второй:"+FirstR);
        System.out.println("Нагрузка на второй процессор: "+SumMat(FirstR));

        //Переход на второй уровень(повтор действий как для перехода на первый, но отдельно для каждого массива)
        //Переход для Pa
        SecondL1.add(FirstL.get(0));
        SecondL2.add(FirstL.get(1));

        for(int i = 2;i<FirstL.size();i++){
            if(SumMat(SecondL1)<=SumMat(SecondL2))
                SecondL1.add(FirstL.get(i));
            else
                SecondL2.add(FirstL.get(i));
        }
        //Переход для Pb
        SecondR1.add(FirstR.get(0));
        SecondR2.add(FirstR.get(1));

        for(int i = 2;i<FirstR.size();i++){
            if(SumMat(SecondR1)<=SumMat(SecondR2))
                SecondR1.add(FirstR.get(i));
            else
                SecondR2.add(FirstR.get(i));
        }
        System.out.println();
        System.out.println("Процессоры с значениями нагрузок после перехода к второму уровню");
        System.out.println("Первая подгрупа");
        System.out.println("Первый: "+SecondL1);
        System.out.println("Нагрузка на первый процессор первой подгруппы: "+ SumMat(SecondL1));
        System.out.println("Второй: "+SecondL2);
        System.out.println("Нагрузка на второй процессор первой подгруппы: "+ SumMat(SecondL2));
        System.out.println("Вторая подгрупа");
        System.out.println("Первый: "+SecondR1);
        System.out.println("Нагрузка на первый процессор второй подгруппы: "+ SumMat(SecondR1));
        System.out.println("Второй: "+SecondR2);
        System.out.println("Нагрузка на второй процессор второй подгруппы: "+ SumMat(SecondR2));
        //Итоговая нагрузка
        System.out.println("Итоговая нагрузка: ");
        System.out.println("Результат: max ("+SumMat(SecondL1)+", "+SumMat(SecondL2)+", "+
                SumMat(SecondR1)+", "+SumMat(SecondR2)+") = "+getMax(new int[]{SumMat(SecondL1), SumMat(SecondL2), SumMat(SecondR1), SumMat(SecondR2)}));

    }

    public static int getMax(int[] inputArray){
        int maxValue = inputArray[0];
        for(int i=1;i < inputArray.length;i++){ if(inputArray[i] > maxValue){
            maxValue = inputArray[i];
        }
        }
        return maxValue;
    }

    public static void printMatrix(Integer [][] Mat){
        System.out.println("Наша матрица сейчас выглядит вот так:");
        for (Integer[] el:Mat) {
            System.out.print("| ");
            for (int num:el) {
                System.out.print(num+" ");
            }
        System.out.println(" |");
        }
    }
    public static int SumMat(ArrayList<Integer>Mat){
        int sum = 0;
        for(int el:Mat){
            sum+=el;
        }
        return sum;
    }
}
