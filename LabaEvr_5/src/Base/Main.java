package Base;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner in = new Scanner(System.in);
        int shit = 8;
        int shit2 = 0;

        StringBuilder fileBuild = new StringBuilder();
        File file = new File("ParntoChildren.txt");
        if(file.exists())
        file.delete();

        //Ну штош поехали эти ваши гены писать
        System.out.println("Лабораторная работа №4 \"Ген.алгоритмы\"");
        fileBuild.append("Лабораторная работа №4 \"Ген.алгоритмы\"\n");
        System.out.print("m: "); int m = in.nextInt();//количество цифр в особе(я не совсем помню как они называются)
        fileBuild.append("m: "+m+'\n');
        System.out.print("n: "); int n = in.nextInt();//колво столбиков при построение фенотипа, гены
        fileBuild.append("n: "+n+'\n');
        System.out.print("T1: "); int T1 = in.nextInt();//Ну это по классике разброс
        fileBuild.append("T1: "+T1+'\n');
        System.out.print("T2: "); int T2 = in.nextInt();
        fileBuild.append("T2: "+T2+'\n');
        System.out.print("Z: ");int Z = in.nextInt();//колво особей в поколении
        fileBuild.append("Z: "+Z+'\n');
        System.out.print("k: ");int k = in.nextInt();//результат который надо достич
        fileBuild.append("k: "+k+'\n');
        System.out.print("Pk: ");int pk = in.nextInt();//оператор кроссовера
        fileBuild.append("Pk: "+pk+"%"+'\n');
        System.out.print("Pm: ");int pm = in.nextInt();//оператор мутации
        fileBuild.append("Pm: "+pm+"%"+'\n');
        //Это была очень долгая инициализация
        //System.out.println("Сгенерированные элементы для особей:");
        ArrayList<ArrayList<Integer>> O = new ArrayList<>();
        for(int i = 0;i<Z;i++){
            O.add(new ArrayList<>());
        }
        for(int j = 0;j<m;j++){
            int random = (int)(Math.random()*(T2-T1+1))+T1;
            for(int i = 0;i<Z;i++) {
                O.get(i).add(random);
            }
        }
        //System.out.println(O.get(0).toString());
        fileBuild.append("Сгенерированные элементы для особей:\n");
        fileBuild.append(O.get(0)+"\n");
        ArrayList<ArrayList<Integer>> G = new ArrayList<>();
        for(int i = 0;i<Z;i++){
            G.add(new ArrayList<>());
            for(int j = 0;j<m;j++){
                int random = (int)(Math.random()*(255-0+1))+0;
                G.get(i).add(random);
            }
        }
        ArrayList<Integer> genes = new ArrayList<>();
        int max ;
        int minmax = 9999;
        genes.add(0);
        for(int j = 1;j<=n;j++){
            genes.add(255*j/n);
        }
        //System.out.print("Гены: ");
        fileBuild.append("Гены: \n");
        int temp = 0;
        for(int i = 1;i<=n;i++){System.out.print(temp+"-"+genes.get(i)+"|");fileBuild.append(temp+"-"+genes.get(i)+"|");temp=genes.get(i)+1;}
        //System.out.println();
        fileBuild.append('\n');
        for(int i = 0;i<Z;i++){//ну шо поехали ебаться с первым поколением, боюсь что буквально
            max= -1;
           // System.out.print("O"+i+":");OGPrint(O.get(i));System.out.println();
           // System.out.print("G"+i+":");OGPrint(G.get(i));System.out.println();
            //System.out.println("Фенотип"+i);
            fileBuild.append("O"+i+":\n");
            fileBuild.append(O.get(i)+"\n");
            fileBuild.append("G"+i+":\n");
            fileBuild.append(G.get(i)+"\n");
            fileBuild.append("Фенотип: \n");
            ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O.get(i),G.get(i),genes);
            //вот думаю где то тут мне понадобится функция для составления фенотипа, да отличная идея
            for(ArrayList<Integer> el:Fenotype){
                fileBuild.append(el.toString()+"|");
                // System.out.print(el.toString()+"|");
                //System.out.println(SumStroke(el));
                fileBuild.append(SumStroke(el)+"\n");
            }//выводим наш фенотип и затем можем искать минимальный среди максимальных
            for(int j = 0;j<Fenotype.size();j++){
                if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
            }
            if(max<minmax) minmax = max;
            fileBuild.append("Min: "+minmax +"\n");
           // System.out.println("MIN "+minmax);
           // System.out.println();

        }
        try(FileWriter writer = new FileWriter("ParntoChildren.txt", false))
        {
            // запись всей строки
            writer.write(fileBuild.toString());
            // запись по символам
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        fileBuild.setLength(0);

        int iter = 0;
        int counter = 1;//о а теперь начнем веселье с детишками и не подумайте что я тут педофил
        while (counter<k){
            //System.out.println();
            fileBuild.append("\n");
            iter++;
            System.out.println("Иттерация"+iter);
            fileBuild.append("Поколение: "+iter+"\n");

            ArrayList<ArrayList<Integer>> newParents = new ArrayList<>();
            ArrayList<ArrayList<Integer>> ParentsGenes = new ArrayList<>();
            ArrayList<Integer> MAX = new ArrayList<>();

            for(int i = 0;i<Z;i++){
                newParents.add(new ArrayList<>());
                ParentsGenes.add(G.get(i));
                MAX.add(-1);
            }
            for(int i = 0; i<Z;i++) {
                for(int j = 0;j<Z;j++) MAX.set(i,-1);
                int randomParent1 = (int) (Math.random() *Z);
                int randomParent2 = (int) (Math.random() * Z);

                while (randomParent1 == randomParent2){ randomParent2 = (int) (Math.random() * Z);}
                //System.out.println("Родители будущих родителей: Особь" + randomParent1 + " Особь" + randomParent2);
                int min = 9999;
                ArrayList<ArrayList<Integer>> children = new ArrayList<>();
                ArrayList<ArrayList<Integer>> childrenM = new ArrayList<>();
                for(int j = 0;j<2;j++){
                    children.add(new ArrayList<>());
                    childrenM.add(new ArrayList<>());
                }
                //int cross = (int) (Math.random() * ParentsGenes.get(randomParent1).size());
                int pkrandom = (int) (Math.random() *(100-0+1))+0;

                while (pkrandom > pk) {
                    pkrandom = (int) (Math.random() *(100-0+1))+0;
                   // cross = (int) (Math.random() * ParentsGenes.get(randomParent1).size());
                    randomParent2 = (int) (Math.random()*Z);
                }

                    //newParents.set(i,Children(ParentsGenes.get(randomParent1),ParentsGenes.get(randomParent2),O.get(0),genes,pk,pm));
                    int T = (int) (Math.random() * (ParentsGenes.get(randomParent1).size()-1));
                    //System.out.println("T: "+T);
                    //System.out.println("Родитель1"+ParentsGenes.get(randomParent1));
                    //System.out.println("Родитель2"+ParentsGenes.get(randomParent2));
                    fileBuild.append("T: " + T + "\n");
                    fileBuild.append("Родитель 1: " + ParentsGenes.get(randomParent1) + "\n");
                    fileBuild.append("Родитель 2: " + ParentsGenes.get(randomParent2) + "\n");
                    for (int j = 0; j < T; j++) {
                        children.get(0).add(ParentsGenes.get(randomParent1).get(j));
                        children.get(1).add(ParentsGenes.get(randomParent2).get(j));
                        childrenM.get(0).add(ParentsGenes.get(randomParent1).get(j));
                        childrenM.get(1).add(ParentsGenes.get(randomParent2).get(j));
                    }
                    //chance cross if (cross!=0) do Children

                    for (int j = T; j < ParentsGenes.get(randomParent1).size(); j++) {
                        children.get(0).add(ParentsGenes.get(randomParent2).get(j));
                        children.get(1).add(ParentsGenes.get(randomParent1).get(j));
                        childrenM.get(0).add(ParentsGenes.get(randomParent2).get(j));
                        childrenM.get(1).add(ParentsGenes.get(randomParent1).get(j));
                    }
                    //System.out.println("Дети до мутации:");
                    //System.out.println("Ребенок1:"+children.get(0));
                    //System.out.println("Ребенок2:"+children.get(1));
                    fileBuild.append("Дети до мутации:\n");
                    fileBuild.append("Ребенок1:" + children.get(0) + "\n");
                    fileBuild.append("Ребенок2:" + children.get(1) + "\n");

                    //формируем мутацию
                    int pmrandom = (int) (Math.random() *(100-0+1))+0;
                    int randomChild =(int) (Math.random() *(2-1+0))+1;

                    if (pmrandom < pm) {
                        int mute = (int) (shit2 + Math.random() * shit);
                        int muteEl = (int) (Math.random() * m);
                        //System.out.println("Кроссовер случился между: "+randomParent1+" и "+randomParent2);
                        //System.out.println("Мутация произошла с ребенком "+q+ "эл "+ muteEl+" на бин.зн "+mute);
                        fileBuild.append("Кроссовер случился между: " + randomParent1 + " и " + randomParent2 + "\n");
                        fileBuild.append("Мутация произошла с ребенком " + randomChild + "эл " + muteEl + " на бин.зн " + mute + "\n");
                        int temp1 = children.get(randomChild).get(muteEl);
                        String result1 = Integer.toBinaryString(temp1);
                        StringBuilder resultWithPadding = new StringBuilder();
                        resultWithPadding.append(String.format("%8s", result1).replaceAll(" ", "0"));
                        if (resultWithPadding.charAt(mute) == '1') resultWithPadding.setCharAt(mute, '0');
                         if (resultWithPadding.charAt(mute) == '0') resultWithPadding.setCharAt(mute, '1');
                        children.get(randomChild).set(muteEl, Integer.parseInt(resultWithPadding.toString(), 2));
                    }

                ArrayList<Integer> result = new ArrayList<>();
                for(int l = 0;l<2;l++) {
                    //System.out.println("Ребенок"+l+" "+children.get(l).toString());
                    fileBuild.append("\nРебенок"+l+" "+children.get(l).toString()+"\n");
                    ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O.get(0), children.get(l), genes);
                    //вот думаю где то тут мне понадобится функция для составления фенотипа, да отличная идея
                    for (ArrayList<Integer> el : Fenotype) {
                        fileBuild.append(el.toString()+"|");
                        // System.out.print(el.toString()+"|");
                        //System.out.println(SumStroke(el));
                        fileBuild.append(SumStroke(el)+"\n");
                    }//выводим наш фенотип и затем можем искать минимальный среди максимальных
                    max = -1;
                    for(int j = 0;j<Fenotype.size();j++){
                        if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
                    }
                    if(max<min){ min = max;result = children.get(l);}
                    //System.out.println("MIN "+min);
                    //System.out.println("r: "+result);
                }
                //тут я пыатаюсь поправить ситуацию с помощью НЕ МУТИРОВАВШИХСЯ ДЕТЕЙ
                for(int l = 0;l<2;l++) {
                    //System.out.println("Ребенок"+l+" "+children.get(l).toString());
                    fileBuild.append("\nРебенокНеМ"+l+" "+childrenM.get(l).toString()+"\n");
                    ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O.get(0), childrenM.get(l), genes);
                    //вот думаю где то тут мне понадобится функция для составления фенотипа, да отличная идея
                    for (ArrayList<Integer> el : Fenotype) {
                        fileBuild.append(el.toString()+"|");
                        // System.out.print(el.toString()+"|");
                        //System.out.println(SumStroke(el));
                        fileBuild.append(SumStroke(el)+"\n");
                    }//выводим наш фенотип и затем можем искать минимальный среди максимальных
                    max = -1;
                    for(int j = 0;j<Fenotype.size();j++){
                        if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
                    }
                    if(max<min){ min = max;result = childrenM.get(l);}
                    //System.out.println("MIN "+min);
                    //System.out.println("r: "+result);
                }
                //System.out.println("MIN "+min);
                //System.out.println("r: "+result);
                //просто перекинуть все в функцию
                newParents.set(i,result);

               // System.out.println("Новая особь"+i+" "+newParents.get(i));
               // System.out.println("Её фенотип:");
                fileBuild.append("Новая особь"+i+" "+newParents.get(i)+"\n");
                fileBuild.append("Её фенотип:\n");
                ArrayList <ArrayList<Integer>>Fenotype = fenotypeBuild(O.get(0),newParents.get(i),genes);
                for(ArrayList<Integer> el:Fenotype){
                    fileBuild.append(el.toString()+"|");
                    // System.out.print(el.toString()+"|");
                    //System.out.println(SumStroke(el));
                    fileBuild.append(SumStroke(el)+"\n");
                }
                for(int j = 0;j<Fenotype.size();j++){
                    if(SumStroke(Fenotype.get(j))>MAX.get(i)) MAX.set(i,SumStroke(Fenotype.get(j)));
                }
            }
            fileBuild.append(MAX.toString());
            System.out.println(MAX);
            max = Collections.min(MAX);
            if(max == minmax) counter++;
            if(max<minmax){ minmax = max;counter = 1;}
            if(max>minmax){minmax = max;counter = 1;}
            System.out.println("MIN "+minmax + " k:"+counter+"/"+k);
            fileBuild.append("MIN "+minmax + " k:"+counter+"/"+k+"\n");
            for(int i = 0;i<Z;i++){
                ParentsGenes.set(i,newParents.get(i));
            }
            //вот тут надо будет детей дописать, а так вприииинципе вроде как закончила
            try(FileWriter writer = new FileWriter("ParntoChildren.txt", true))
            {
                // запись всей строки
                writer.write(fileBuild.toString());
                // запись по символам
                writer.flush();
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
            fileBuild.setLength(0);
            //а if(iter == k+1) break;
        }
        System.out.println("Все это нечто отработало свое");
    }
    public static ArrayList<Integer>Children(ArrayList<Integer>par1, ArrayList<Integer>par2,ArrayList<Integer>O,ArrayList<Integer> genes,int pk,int pm){
        int min = 9999;
        ArrayList<ArrayList<Integer>> children = new ArrayList<>();
        for(int i = 0;i<2;i++){
            children.add(new ArrayList<>());
        }
        int T = (int)(Math.random()*par1.size()+1);
        System.out.println("T: "+T);
        for(int i = 0;i<T;i++){
            children.get(0).add(par1.get(i));
            children.get(1).add(par2.get(i));
        }
        for(int i = T;i<par1.size();i++){
            children.get(0).add(par2.get(i));
            children.get(1).add(par1.get(i));
        }
        //формируем кроссовер и мутацию
        for(int i = 0;i<2;i++) {
            int cross = (int) (Math.random() * par1.size());
            int mute = (int) (Math.random() * 8);
            int pkrandom = (int) (Math.random() * 100);
            int pmrandom = (int) (Math.random() * 100);
            while (pkrandom > pk) {
                pkrandom = (int) (Math.random() * 100);
                cross = (int) (Math.random() * par1.size());
            }
            while (pmrandom > pm) {
                pmrandom = (int) (Math.random() * 100);
                mute = (int) (Math.random() *8 );
            }
            System.out.println("Кроссовер: " + cross + " Мутация: " + mute);
            int temp = children.get(i).get(cross);
            String result = Integer.toBinaryString(temp);
            StringBuilder resultWithPadding = new StringBuilder();
            resultWithPadding.append(String.format("%8s", result).replaceAll(" ", "0"));
            while (resultWithPadding.charAt(mute) == '1') mute = (int) (Math.random() * 8);
            resultWithPadding.setCharAt(mute, '1');
            children.get(i).set(cross, Integer.parseInt(resultWithPadding.toString(), 2));
        }
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0;i<2;i++) {
            System.out.println("Children"+i+" "+children.get(i).toString()+i);
            ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O, children.get(i), genes);
            //вот думаю где то тут мне понадобится функция для составления фенотипа, да отличная идея
            for (ArrayList<Integer> el : Fenotype) {
                System.out.print(el.toString() + "|");
                System.out.println(SumStroke(el));
            }//выводим наш фенотип и затем можем искать минимальный среди максимальных
            int max = -1;
            for(int j = 0;j<Fenotype.size();j++){
                if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
            }
            if(max<min){ min = max;result = children.get(i);}
            //System.out.println("MIN "+min);
            //System.out.println("r: "+result);
        }
        //просто перекинуть все в функцию
        return result;
    }
    public static void OGPrint(ArrayList<Integer> OG){
        for(Integer el:OG){
            System.out.printf("%4d",el);System.out.print("|");
        }
    }
    public static ArrayList<ArrayList<Integer>>fenotypeBuild(ArrayList<Integer>Oi,ArrayList<Integer>G,ArrayList<Integer>genes){
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for(int i = 1;i<genes.size();i++){
            result.add(new ArrayList<>());
        }//инициализация
        for(int i = 0;i<Oi.size();i++){
            for(int j = 1;j<genes.size();j++){
                if((genes.get(j-1)< G.get(i))&&(G.get(i)<=genes.get(j))){
                    result.get(j-1).add(Oi.get(i));
                }
            }
        }
        return result;
    }
    public static int SumStroke(ArrayList<Integer> Arr){
        int result = 0;
        for (int el:Arr) {
            result+=el;
        }
        return result;
    }
}
