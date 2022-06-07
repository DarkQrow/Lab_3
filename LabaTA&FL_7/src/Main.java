import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //Пользователем задаются
        //Терминальные символы - Т
        //Нетерминальные символы - N
        //Кол-во правил
        //И сами правила
        //Как итог программы выводит какой это тип грамматики по Хомскому
        Scanner in = new Scanner(System.in);
        System.out.println("Лабораторная работа по ТАиФЯ №7");
        System.out.println("Определение типа грамматики по Хомскому");
        System.out.println("Введите терминальные символы через проблел");
        System.out.print("T: ");
        String[]T = in.nextLine().split(" ");
        System.out.println(Arrays.toString(T));
        System.out.println("Введите нетерминальные символы через пробел");
        System.out.print("N: ");
        String[]N = in.nextLine().split(" ");
        System.out.print("Введите кол-во правил: ");
        int Pn = in.nextInt();
        in.nextLine();
        ArrayList<String> P = new ArrayList<>();
        for(int i = 0;i<Pn;i++){
            System.out.println("Введите правило: ");
            P.add(in.nextLine());
        }


        ArrayList<Integer> GrammRule = new ArrayList<>();
        for(int i = 0;i<Pn;i++){
            GrammRule.add(Homski(T,N,P.get(i)));
        }
        System.out.println("Подробный разбор типов правил: ");
        System.out.println("Терминальные символы: "+Arrays.toString(T));
        System.out.println("Нетерминальные символы: "+Arrays.toString(N));
        System.out.println("Правила: ");
        for(int i = 0;i<P.size();i++){
            System.out.print(P.get(i)+"\t");
            System.out.println(printType(GrammRule.get(i)));
        }
        int min = Collections.min(GrammRule);
        switch(min){
            case 0:{ System.out.println("Это грамматика языка типа 0 или Языка с неограниченной фазовой структурой"); break;}
            case 1:
            case 2: { System.out.println("Это грамматика контекстно-зависимого языка");break;}
            case 3:
            case 4: {System.out.println("Это грамматика контекстно-свободного языка");break;}
            case 5:
            case 6:{System.out.println("Это грамматика регулярного языка");break;}
            default:{System.out.println("Вы кажется умудрились не попасть ни в один тип по Хомскому, поздравляю");break;}
        }
    }
    public static String printType(int type){
        String result = "";
        switch(type){
            case 0:{ System.out.println("Тип 0"); break;}
            case 1:{ System.out.println("НУ");break;}
            case 2: { System.out.println("КЗ");break;}
            case 3:{System.out.println("УКС");break;}
            case 4: {System.out.println("КС");break;}
            case 5:{System.out.println("ЛРег");break;}
            case 6:{System.out.println("ПРег");break;}
            default:{System.out.println("КАК?!?!?!");break;}
        }
        return result;
    }
    public static int Homski(String[]T,String[]N,String P){
        int result = -1;
        int index1 = P.indexOf("-");
        int index2 = P.indexOf(">")+1;
        String alpa = P.substring(0,index1);
        String beta = P.substring(index2);
        if((alpa.length()>0)&&(containsN(N,alpa))) {
            result = 0;//проверка на тип 0
            if ((alpa.length() + beta.length() > 1) && (alpa.length() <= beta.length())) {
                result = 1; //проверка на НУ
                if (KZ(N, alpa, beta))
                    result = 2; //проверка на КЗ
                    if (((alpa.length() == 1) && (containsN(N, alpa))) && (beta.length() > -1)) {
                        result = 3;//проверка на УКС
                        if (((alpa.length() == 1) && (containsN(N, alpa))) && (beta.length() > 0)) {
                            result = 4;//проверка на КС
                            if ((alpa.length() == 1) && (containsN(N, alpa)) && (regLeft(N, T, beta)))
                                result = 5;//проверка на ЛРег
                            if ((alpa.length() == 1) && (containsN(N, alpa)) && (regRight(N, T, beta)))
                                result = 6;//проверка на ПРег

                        }

                }
            }
        }
        return result;
    };
    public static boolean containsN(String[]N,String P){
        for(int i = 0;i<N.length;i++){
            if(P.contains(N[i])) return true;
        }
        return false;
    }
    public static boolean KZ(String[]N,String alpa,String beta){
        String a1;
        String a2;
        for(int i = 0;i<N.length;i++){
            ArrayList<Integer> pos = reapetsN(N[i],alpa);
            if(pos.size()==0) return false;
            for(int j = 0;j< pos.size();j++){
                a1 = alpa.substring(0,pos.get(j));
                a2 = alpa.substring(pos.get(j)+1);
                Pattern pattern = Pattern.compile(a1+".++"+a2);
                Matcher matcher = pattern.matcher(beta);
                if(matcher.find()) return true;
            }
        }
        return false;
    }
    public static ArrayList<Integer> reapetsN(String N,String P){
        Matcher m = Pattern.compile("(?=("+N+"))").matcher(P);
        List<Integer> pos = new ArrayList<Integer>();
        while (m.find()) {
            pos.add(m.start());
        }
        return (ArrayList<Integer>) pos;
    }
    public static boolean regLeft(String[]N,String[]T,String beta){
        if(beta.length() == 0) return false;
        String B = beta.charAt(0)+"";
        String gamma;
        int counter = 0;
        if((B.length()==1)&&(containsN(N,B))) {
            gamma = beta.substring(1);
            for (int i = 0; i < gamma.length(); i++) {
                String temp = gamma.charAt(i) + "";
                boolean check = false;
                for (int j = 0; j < T.length; j++) {
                    if (temp.equals(T[j])) check = true;
                }
                if (check) counter++;
            }
        }
        else{
            gamma = beta;
            for (int i = 0; i < gamma.length(); i++) {
                String temp = gamma.charAt(i) + "";
                boolean check = false;
                for (int j = 0; j < T.length; j++) {
                    if (temp.equals(T[j])) check = true;
                }
                if (check) counter++;
            }
        }
        if(counter == gamma.length()) return true;
        return false;
    }
    public static boolean regRight(String[]N,String[]T,String beta){
        if(beta.length() == 0) return false;
        String B = beta.charAt(beta.length()-1)+"";
        String gamma;
        int counter = 0;
        if((B.length()==1)&&(containsN(N,B))) {
            gamma = beta.substring(0, beta.length() - 1);
            for (int i = 0; i < gamma.length(); i++) {
                String temp = gamma.charAt(i) + "";
                boolean check = false;
                for (int j = 0; j < T.length; j++) {
                    if (temp.equals(T[j])) check = true;
                }
                if (check) counter++;
            }
        }
        else{
            gamma = beta;
            for (int i = 0; i < gamma.length(); i++) {
                String temp = gamma.charAt(i) + "";
                boolean check = false;
                for (int j = 0; j < T.length; j++) {
                    if (temp.equals(T[j])) check = true;
                }
                if (check) counter++;
            }
        }
        if(counter == gamma.length()) return true;
        return false;
    }
}
