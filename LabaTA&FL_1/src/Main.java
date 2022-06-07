import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        int choice = 0;//Выбор операции пользователем
        String choice1 = "";//Выбор пользователем номера для операции
        String alpa = "";//Выбор пользователем алфавита для операций

        Pattern numPat = Pattern.compile("[0-9]+");
        Pattern wordPat1 = Pattern.compile("[a-zA-Z]+");
        Pattern wordPat2 = Pattern.compile("[а-яА-Я]+");

        Scanner in = new Scanner(System.in);

	    System.out.println("Лабораторная работа №1 по Теории автоматов и формальных языков");
        System.out.println("   \"Лексикографический номер\"");
        System.out.println("На выбор вам доступно два действия:");
        System.out.println("1. По номеру получить слово");
        System.out.println("2. По слову получить номер");
        System.out.print("Введите ваш выбор: ");

        choice = in.nextInt();

        if(choice ==1){
            in.nextLine();
            System.out.println("Выбрано \"По номеру слово\"");
            alpa = checkAlpa();
            System.out.print("Введите номер: ");
            choice1 = in.nextLine();
            Matcher matcher3 = numPat.matcher(choice1);

            while(!(matcher3.matches())){
                System.out.println("Вы ошиблись при вводе номера попробуйте еще раз: ");
                choice1 = in.nextLine();
                matcher3 = numPat.matcher(choice1);
            }
            int N = Integer.parseInt(choice1);
            if(N == 0){System.out.println("Пустрое слово");}
            else{
                int n = alpa.length();
                int k = N/n;
                ArrayList<Integer> r0 = new ArrayList<>();
                int r = N%n;
                //Поиск условия где 1<=r<=n
                while(k>n){
                    while (!((1<=r)&&(r<=n))){
                        k--;
                        r = N-k*n;
                    }
                    r0.add(r);
                    N = k;
                    k = N/n;
                    r = N%n;
                }
              //  if(k==3)
                if((0<=k)&&(k<=n)) {
                    r0.add(r);
                    r0.add(k);
                }
                Collections.reverse(r0);
                printSolve(r0,n);
                printWord(alpa,r0);
            }
        }
        if(choice ==2){

            in.nextLine();
            System.out.println("Выбрано \"По слову номер\"");
            alpa = checkAlpa();
            System.out.print("Введите слово: ");
            choice1 = in.nextLine();

            Matcher matcher3;
            Pattern alfaPat1 = Pattern.compile("[a-zA-Z]{2,}");
            Matcher matcher1 = alfaPat1.matcher(alpa);
            if(matcher1.matches()) {matcher3 = wordPat1.matcher(choice1);}
            else matcher3 = wordPat2.matcher(choice1);

            while(!(matcher3.matches())){
                System.out.println("Вы ошиблись при вводе слова попробуйте еще раз: ");
                choice1 = in.nextLine();
                if(matcher1.matches()==true) matcher3 = wordPat1.matcher(choice1);
                else matcher3 = wordPat2.matcher(choice1);
            }
            ArrayList<Integer> r = new ArrayList<>();
            for(int i = 0;i<choice1.length();i++){
                r.add(alpa.indexOf(choice1.charAt(i))+1);
            }
            printSolve(r,alpa.length());
            printNum(r,alpa.length());
        }
    }
    public static void  printSolve(ArrayList<Integer> r,int n){
        int stepen = r.size()-1;
        for (int elem:r) {
            System.out.print(elem+"*"+n+"^"+stepen+" ");
            stepen--;
        }
    }
    public static void printNum(ArrayList<Integer> r,int n){
        int result = 0;
        int counter = 0;
        for(int i = r.size()-1;i>=0;i--){
            result+=(Math.pow(n,i)*r.get(counter));
            counter++;
        }
        System.out.print("= "+result);
    }
    public static void printWord(String alpa,ArrayList<Integer> r){
        System.out.print("Полученное слово: ");
        for (int el:r) {
            System.out.print(alpa.charAt(el-1));
        }
    }
    public static String checkAlpa(){
        Pattern alfaPat1 = Pattern.compile("[a-zA-Z]{2,}");
        Pattern alfaPat2 = Pattern.compile("[а-яА-Я]{2,}");
        Scanner in = new Scanner(System.in);
        System.out.print("Введите алфавит: ");
        String alpa = in.nextLine();
        char[] myArray = alpa.toCharArray();
        int countrep = 0;
        for(int i=0; i<myArray.length-1; i++){
            for (int j=i+1; j<myArray.length; j++){
                if(myArray[i] == myArray[j]){
                    countrep++;
                }
            }
        }
        Matcher matcher1 = alfaPat1.matcher(alpa);
        Matcher matcher2 = alfaPat2.matcher(alpa);
        //  !((A||B)&&C)

        while (!((matcher1.matches()||matcher2.matches())&&(countrep<1))){
            System.out.print("Вы ошиблись при вводе алфавита попробуйте еще раз: ");
            alpa = in.nextLine();

            matcher1 = alfaPat1.matcher(alpa);
            matcher2 = alfaPat2.matcher(alpa);

            myArray = alpa.toCharArray();
            countrep = 0;
            for(int i=0; i<myArray.length-1; i++){
                for (int j=i+1; j<myArray.length; j++){
                    if(myArray[i] == myArray[j]){
                        countrep++;
                    }
                }
            }
        }
        return alpa;
    }

}
