import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[]args){

        Scanner in = new Scanner(System.in);

        System.out.println("Добро пожаловать в лабораторную работу №2 по");
        System.out.println("Теория алгроритмов и формальных языков");
        System.out.println("Варинат № 18");
        System.out.println("Задание: все цепочки из a,b,c в которых первый символ или a или b");
        System.out.println("Мной было построенно выражение: (a+b)^+  (a+b+c)^*");
        System.out.println("Для этого регулярного выражения пользователь может выбрать");
        System.out.println("сам сколько элементов вывести из множества");
        System.out.print("Введите количество:");

        int n = in.nextInt();
        int checkcombo = 0;
        ArrayList<String> combo = new ArrayList<>();
        combo.add("a");
        combo.add("b");
        combo.add("c");

        Pattern check = Pattern.compile("^[a?|b?][abc?]*");
        Matcher matcher;
        String result = "";
        combo = generateString(combo);
        combo = generateString(combo);
        combo = generateString(combo);
        combo = generateString(combo);
        combo = generateString(combo);
        //System.out.println(combo.toString());

        System.out.println("Полученные элементы из множества: ");

        for(int i = 0;i<combo.size();i++){
            matcher = check.matcher(combo.get(i));
            if(matcher.matches()){
                System.out.println(checkcombo+") "+combo.get(i));
                checkcombo ++;
            }
            if(n == checkcombo)break;
        }
    }
    public static ArrayList<String> generateString(ArrayList<String> arr){
        int size = arr.size();
        String alpa = "abc";
        String cur = "";
        for(int j = 0;j<3;j++) {
          for(int i = 0;i<size;i++) {
              cur = alpa.charAt(j) + arr.get(i);
              if(!arr.contains(cur))
              arr.add(cur);
          }
        }
        return arr;
    }
}
