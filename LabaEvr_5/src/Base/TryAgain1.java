package Base;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TryAgain1 {
    public static void main(String[] args) {
        // write your code here
        Scanner in = new Scanner(System.in);

        StringBuilder fileBuild = new StringBuilder();
        File file = new File("ParntoChildren.txt");
        if(file.exists())
            file.delete();

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
        fileBuild.append("Гены: \n");
        int temp = 0;
        for(int i = 1;i<=n;i++){System.out.print(temp+"-"+genes.get(i)+"|");fileBuild.append(temp+"-"+genes.get(i)+"|");temp=genes.get(i)+1;}
        fileBuild.append('\n');
        for(int i = 0;i<Z;i++) {//ну шо поехали ебаться с первым поколением, боюсь что буквально
            max = -1;
            fileBuild.append("O" + i + ":\n");
            fileBuild.append(O.get(i) + "\n");
            fileBuild.append("G" + i + ":\n");
            fileBuild.append(G.get(i) + "\n");
            fileBuild.append("Фенотип: \n");
            ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O.get(i), G.get(i), genes);
            fileBuild.append(FenotypeWriter(Fenotype));
            for(int j = 0;j<Fenotype.size();j++){
                if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
            }
            if(max<minmax) minmax = max;
            fileBuild.append("Min: "+minmax +"\n");
        }

        try(FileWriter writer = new FileWriter("ParntoChildren.txt", false)) {
            writer.write(fileBuild.toString());
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        fileBuild.setLength(0);

        int iter = 0;
        int counter = 1;
        while (counter<k) {
            fileBuild.append("\n");
            iter++;
            System.out.println("Иттерация" + iter);
            fileBuild.append("Поколение: " + iter + "\n");
            ArrayList<ArrayList<Integer>> newParents = new ArrayList<>();
            ArrayList<ArrayList<Integer>> ParentsGenes = new ArrayList<>();
            ArrayList<Integer> MAX = new ArrayList<>();

            for (int i = 0; i < Z; i++) {
                newParents.add(new ArrayList<>());
                ParentsGenes.add(G.get(i));
                MAX.add(-1);
            }




            ArrayList<ArrayList<Integer>> pairs = PairGenerator(Z, pk);
            for (int i = 0; i < Z; i++) {
                int min = 9999;
                ArrayList<ArrayList<Integer>> children = new ArrayList<>();
                ArrayList<ArrayList<Integer>> childrenM = new ArrayList<>();
                for(int j = 0;j<2;j++){
                    children.add(new ArrayList<>());
                    childrenM.add(new ArrayList<>());
                }

                int T = (int) (Math.random() * (ParentsGenes.get(0).size() - 1)+1);
                fileBuild.append("T: " + T + "\n");
                fileBuild.append("Родитель 1: " + ParentsGenes.get(pairs.get(i).get(0)) + "\n");
                fileBuild.append("Родитель 2: " + ParentsGenes.get(pairs.get(i).get(1)) + "\n");
                for (int j = 0; j < T; j++) {
                    children.get(0).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                    children.get(1).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                    childrenM.get(0).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                    childrenM.get(1).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                }
                for (int j = T; j < ParentsGenes.get(pairs.get(i).get(0)).size(); j++) {
                    children.get(0).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                    children.get(1).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                    childrenM.get(0).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                    childrenM.get(1).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                }
                fileBuild.append("Дети до мутации:\n");
                fileBuild.append("Ребенок1:" + children.get(0) + "\n");
                fileBuild.append("Ребенок2:" + children.get(1) + "\n");
                int pmrandom = (int) (Math.random() *(100-0+1))+0;
                int randomChild =(int)Math.random();
                int muteEl = (int) (Math.random() * m);
                int mute = (int) (Math.random() * 4);
                while(pmrandom>pm){ mute = (int) (Math.random() * 4); pmrandom = (int) (Math.random() *(100-0+1))+0;}
                fileBuild.append("Кроссовер случился между: " + pairs.get(i).get(0) + " и " + pairs.get(i).get(1) + "\n");
                fileBuild.append("Мутация произошла с ребенком " + randomChild + "эл " + muteEl + " на бин.зн " + mute + "\n");
                int temp1 = children.get(randomChild).get(muteEl);
                String result1 = Integer.toBinaryString(temp1);
                StringBuilder resultWithPadding = new StringBuilder();
                resultWithPadding.append(String.format("%8s", result1).replaceAll(" ", "0"));
                if (resultWithPadding.charAt(mute) == '1') resultWithPadding.setCharAt(mute, '0');
                if (resultWithPadding.charAt(mute) == '0') resultWithPadding.setCharAt(mute, '1');
                children.get(randomChild).set(muteEl, Integer.parseInt(resultWithPadding.toString(), 2));

                ArrayList<Integer> result = new ArrayList<>();
                for(int l = 0;l<2;l++) {
                    fileBuild.append("\nРебенок" + l + " " + children.get(l).toString() + "\n");
                    ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O.get(0), children.get(l), genes);
                    fileBuild.append(FenotypeWriter(Fenotype));
                    max = -1;
                    for(int j = 0;j<Fenotype.size();j++){
                        if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
                    }
                    if(max<min){ min = max;result = children.get(l);}
                }
                for(int l = 0;l<2;l++) {
                    fileBuild.append("\nРебенокНеМ"+l+" "+childrenM.get(l).toString()+"\n");
                    ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O.get(0), childrenM.get(l), genes);
                    fileBuild.append(FenotypeWriter(Fenotype));
                    max = -1;
                    for(int j = 0;j<Fenotype.size();j++){
                        if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
                    }
                    if(max<min){ min = max;result = childrenM.get(l);}
                }
                newParents.set(i,result);
                fileBuild.append("Новая особь"+i+" "+newParents.get(i)+"\n");
                fileBuild.append("Её фенотип:\n");
                ArrayList <ArrayList<Integer>>Fenotype = fenotypeBuild(O.get(0),newParents.get(i),genes);
                fileBuild.append(FenotypeWriter(Fenotype));
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
                for(int j = 0;j< newParents.size();j++) {
                    ParentsGenes.get(i).set(j,newParents.get(i).get(j));
                }
            }
            //вот тут надо будет детей дописать, а так вприииинципе вроде как закончила
            try(FileWriter writer = new FileWriter("ParntoChildren.txt", true)) {
                writer.write(fileBuild.toString());
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

    public static ArrayList<ArrayList<Integer>> PairGenerator(int Z,int pk){
        ArrayList<ArrayList<Integer>> Pairs = new ArrayList<>();
        for(int i = 0;i<Z;i++){
            Pairs.add(new ArrayList<>());
        }
        for(int i = 0;i<Z;i++) {
            int pkrandom = (int) (Math.random() *(100-0+1))+0;
            int randomParent1 = (int) (Math.random() * Z);
            int randomParent2 = (int) (Math.random() * Z);
            while (randomParent1 == randomParent2){ randomParent2 = (int) (Math.random() * Z);}
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(randomParent1);
            temp.add(randomParent2);
            for(int j = 0;j<i;j++){
                while(Pairs.get(j).containsAll(temp)){
                    randomParent1 = (int) (Math.random() * Z);
                    randomParent2 = (int) (Math.random() * Z);
                    temp.set(0,randomParent1);
                    temp.set(1,randomParent2);
                }
            }
            while (pkrandom > pk) {
                pkrandom = (int) (Math.random() *(100-0+1))+0;
                randomParent1 = (int) (Math.random() * Z);
                randomParent2 = (int) (Math.random() * Z);
                while (randomParent1 == randomParent2){ randomParent2 = (int) (Math.random() * Z);}
                for(int j = 0;j<i;j++){
                    while(Pairs.get(j).containsAll(temp)){
                        randomParent1 = (int) (Math.random() * Z);
                        randomParent2 = (int) (Math.random() * Z);
                        temp.set(0,randomParent1);
                        temp.set(1,randomParent2);
                    }
                }
            }
            Pairs.get(i).add(randomParent1);
            Pairs.get(i).add(randomParent2);
        }
        for(ArrayList<Integer> el:Pairs){
            System.out.println(el.toString());
        }
        return Pairs;
    }
    public static String FenotypeWriter(ArrayList<ArrayList<Integer>> Fenotype){
        StringBuilder fileBuild = new StringBuilder();
        for(ArrayList<Integer> el:Fenotype){
            fileBuild.append(el.toString()+"|");
            fileBuild.append(SumStroke(el)+"\n");
        }
        return fileBuild.toString();
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
