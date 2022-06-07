
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

        StringBuilder fileBuild = new StringBuilder();
        File file = new File("ParntoChildren.txt");
        if(file.exists())
            file.delete();

        System.out.println("Лабораторная работа №6 \"Ген.алгоритмы\"");
        fileBuild.append("Лабораторная работа №6 \"Ген.алгоритмы\"\n");
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
        for(int i = 0;i<Z;i++) {
            for(int j = 0;j<m;j++){
                int random = (int)(Math.random()*(T2-T1+1))+T1;
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
            ArrayList<Pair<Integer, Integer>> MAX = new ArrayList<>();
            ArrayList<Integer> maxnoel = new ArrayList<>();
            for(int i = 0;i<Z*2;i++){
                MAX.add(new Pair<>(-1,i));
            }

            for (int i = 0; i < Z; i++) {
                maxnoel.add(-1);
                newParents.add(new ArrayList<>());
                ParentsGenes.add(G.get(i));
                ArrayList <ArrayList<Integer>>Fenotype = fenotypeBuild(O.get(i),ParentsGenes.get(i),genes);
                fileBuild.append(FenotypeWriter(Fenotype));
                for(int j = 0;j<Fenotype.size();j++){
                    if(SumStroke(Fenotype.get(j))>MAX.get(i).getLeft()) {
                        MAX.get(i).setLeft(SumStroke(Fenotype.get(j)));
                    }
                }

            }

            ArrayList<ArrayList<Integer>> pairs = PairGenerator(Z, pk);
            for (int i = 0; i < Z; i++) {
                int min = 9999;
                ArrayList<ArrayList<Integer>> children = new ArrayList<>();
                // ArrayList<ArrayList<Integer>> childrenM = new ArrayList<>();
                for(int j = 0;j<2;j++){
                    children.add(new ArrayList<>());
                    //  childrenM.add(new ArrayList<>());
                }

                int Cross1 = (int)(Math.random()*ParentsGenes.size()/2)+1;
                int Cross2 = (int)(Math.random()*ParentsGenes.size()/2)+Cross1+1;

                fileBuild.append("Родитель 1: " + ParentsGenes.get(pairs.get(i).get(0)) + "\n");
                fileBuild.append(FenotypeWriter(fenotypeBuild(O.get(i), ParentsGenes.get(pairs.get(i).get(0)), genes)));
                fileBuild.append("Родитель 2: " + ParentsGenes.get(pairs.get(i).get(1)) + "\n");
                fileBuild.append(FenotypeWriter(fenotypeBuild(O.get(i), ParentsGenes.get(pairs.get(i).get(1)), genes)));
                for (int j = 0; j < Cross1; j++) {
                    children.get(0).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                    children.get(1).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                }
                for (int j = Cross1; j < Cross2; j++) {
                    children.get(0).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                    children.get(1).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                }
                for (int j = Cross2; j < ParentsGenes.get(pairs.get(i).get(0)).size(); j++) {
                    children.get(0).add(ParentsGenes.get(pairs.get(i).get(0)).get(j));
                    children.get(1).add(ParentsGenes.get(pairs.get(i).get(1)).get(j));
                }
                fileBuild.append("Дети до мутации:\n");
                fileBuild.append("Ребенок1:" + children.get(0) + "\n");
                fileBuild.append(FenotypeWriter(fenotypeBuild(O.get(i), children.get(0), genes)));
                fileBuild.append("Ребенок2:" + children.get(1) + "\n");
                fileBuild.append(FenotypeWriter(fenotypeBuild(O.get(i), children.get(1), genes)));

                int pmrandom = (int) (Math.random() *(100-0+1))+0;
                int randomChild =(int)Math.random();
                int muteEl = (int) (Math.random() * m);
                int mute1 = (int) (Math.random() * 4);
                int mute2 = (int) (Math.random() * 4)+4;
                while(pmrandom>pm){
                    mute1 = (int) (Math.random() * 4);
                    mute2 = (int) (Math.random() * 4)+4;
                    pmrandom = (int) (Math.random() *(100-0+1))+0;
                }
                fileBuild.append("Кроссовер случился между: " + pairs.get(i).get(0) + " и " + pairs.get(i).get(1) + "\n");
                fileBuild.append("В точках: "+Cross1+","+Cross2+ "\n");
                fileBuild.append("Мутация произошла с ребенком " + randomChild + "эл " + muteEl + " на бин.зн-х " + mute1 +","+mute2+ "\n");
                int temp1 = children.get(randomChild).get(muteEl);
                String result1 = Integer.toBinaryString(temp1);
                fileBuild.append("Зн-е до м: "+result1+"\n");
                StringBuilder resultWithPadding = new StringBuilder();
                resultWithPadding.append(String.format("%8s", result1).replaceAll(" ", "0"));
                if (resultWithPadding.charAt(mute1) == '1') resultWithPadding.setCharAt(mute1, '0');
                if (resultWithPadding.charAt(mute1) == '0') resultWithPadding.setCharAt(mute1, '1');
                if (resultWithPadding.charAt(mute2) == '1') resultWithPadding.setCharAt(mute2, '0');
                if (resultWithPadding.charAt(mute2) == '0') resultWithPadding.setCharAt(mute2, '1');
                children.get(randomChild).set(muteEl, Integer.parseInt(resultWithPadding.toString(), 2));
                fileBuild.append("Зн-е после м: "+resultWithPadding+"\n");
                ArrayList<Integer> result = new ArrayList<>();
                for(int l = 0;l<2;l++) {
                    fileBuild.append("\nРебенок" + l + " " + children.get(l).toString() + "\n");
                    ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O.get(i), children.get(l), genes);
                    fileBuild.append(FenotypeWriter(Fenotype));
                    max = -1;
                    for(int j = 0;j<Fenotype.size();j++){
                        if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
                    }
                    if(max<min){ min = max;result = children.get(l);}
                }
               /* for(int l = 0;l<2;l++) {
                    // fileBuild.append("\nРебенокНеМ"+l+" "+childrenM.get(l).toString()+"\n");
                    ArrayList<ArrayList<Integer>> Fenotype = fenotypeBuild(O.get(i), ParentsGenes.get(pairs.get(i).get(l)), genes);
                    //fileBuild.append(FenotypeWriter(Fenotype));
                    max = -1;
                    for(int j = 0;j<Fenotype.size();j++){
                        if(SumStroke(Fenotype.get(j))>max) max = SumStroke(Fenotype.get(j));
                    }
                    if(max<min){ min = max;result = ParentsGenes.get((pairs.get(i).get(l)));}
                }

                */
                newParents.set(i,result);
                fileBuild.append("Новая особь"+i+" "+newParents.get(i)+"\n");
                fileBuild.append("Её фенотип:\n");
                ArrayList <ArrayList<Integer>>Fenotype = fenotypeBuild(O.get(i),newParents.get(i),genes);
                fileBuild.append(FenotypeWriter(Fenotype));
                for(int j = 0;j<Fenotype.size();j++){
                    if(SumStroke(Fenotype.get(j))>MAX.get(i+Z).getLeft())
                        MAX.get(i+(Z)).setLeft(SumStroke(Fenotype.get(j)));
                }

            }
            ArrayList<ArrayList<Integer>> tempPArents = new ArrayList<>();
            for(int i = 0;i<Z*2;i++){
                tempPArents.add(new ArrayList<>());
                for(int j = 0;j< newParents.size();j++) {
                    tempPArents.get(i).add(-1);
                }
            }
            for(int i = 0;i<Z;i++){
                for(int j = 0;j< newParents.size();j++) {
                    tempPArents.get(i).set(j,ParentsGenes.get(i).get(j));
                }
            }
            for(int i = Z;i<Z*2;i++){
                for(int j = 0;j< newParents.size();j++) {
                    tempPArents.get(i).set(j,newParents.get(i/2).get(j));
                }
            }
            ArrayList<ArrayList<Integer>> sortedParents = SortParents(tempPArents,MAX);


            for(int i = 0;i<Z;i++){
                ArrayList <ArrayList<Integer>>Fenotype = fenotypeBuild(O.get(i),sortedParents.get(i),genes);
                for(int j = 0;j< sortedParents.size();j++) {
                    ParentsGenes.get(i).set(j,sortedParents.get(i).get(j));

                }
                for(int j = 0;j<Fenotype.size();j++) {
                    if(SumStroke(Fenotype.get(j))>maxnoel.get(i)) maxnoel.set(i,SumStroke(Fenotype.get(j)));
                }
            }

            fileBuild.append("max: "+maxnoel);
            // System.out.println(maxnoel);
            max = Collections.min(maxnoel);
            if(max == minmax) counter++;
            if(max<minmax){ minmax = max;counter = 1;}
            if(max>minmax){minmax = max;counter = 1;}
            System.out.println("MIN "+minmax + " k:"+counter+"/"+k);
            fileBuild.append("MIN "+minmax + " k:"+counter+"/"+k+"\n");


            //вот тут надо будет детей дописать, а так вприииинципе вроде как закончила
            try(FileWriter writer = new FileWriter("ParntoChildren.txt", true)) {
                writer.write(fileBuild.toString());
                writer.flush();
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }
            fileBuild.setLength(0);
            //if(iter == 20) break;
        }
        System.out.println("Все это нечто отработало свое");
    }
    public static ArrayList<ArrayList<Integer>> SortParents(ArrayList<ArrayList<Integer>> Parents,ArrayList<Pair<Integer,Integer>> unSorted){
        ArrayList<Integer> MaxSorted = new ArrayList<>();
        for(int i = 0;i<unSorted.size();i++){
            MaxSorted.add(unSorted.get(i).getLeft());
        }
        System.out.println("MAXBIG: "+MaxSorted);
        Collections.sort(MaxSorted);
        System.out.println("MAXBIG: "+MaxSorted);
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<ArrayList<Integer>> tempresult = new ArrayList<>();
        for(int i = 0;i<Parents.size();i++){
            if(i< Parents.size()/2) tempresult.add(new ArrayList<>());
            result.add(new ArrayList<>());
            for(int j = 0;j<Parents.get(i).size();j++){
                result.get(i).add(-1);
                if(i< Parents.size()/2) tempresult.get(i).add(-1);
            }
        }
        System.out.print("MaxCuted:[");
        for(int i = 0;i< MaxSorted.size()/2;i++){
            System.out.print(" "+MaxSorted.get(i));
        }
        System.out.println("]");
        for(int i = 0;i< MaxSorted.size();i++){
            int temp = MaxSorted.get(i);
            int flag = 0;
            for(int j = 0;j<unSorted.size();j++){
                if(flag == 1) continue;
                if(unSorted.get(j).getLeft() == temp){
                    for(int k = 0;k< Parents.get(0).size();k++){
                        result.get(i).set(k,Parents.get(unSorted.get(j).getRight()).get(k));
                    }
                    unSorted.get(j).setLeft(-1);
                    flag = 1;
                }

            }
        }
        for(int i = 0;i<tempresult.size();i++){
            for(int j = 0;j<tempresult.get(0).size();j++){
                tempresult.get(i).set(j,result.get(i).get(j));
            }
        }


        return tempresult;
    }
    public static ArrayList<ArrayList<Integer>> PairGenerator(int Z,int pk){
        ArrayList<ArrayList<Integer>> Pairs = new ArrayList<>();
        for(int i = 0;i<Z;i++){
            Pairs.add(new ArrayList<>());
        }
        for(int i = 0;i<Z;i++) {
            int pkrandom = (int) (Math.random() *(100-0+1))+0;
            int randomParent1 = i;
            int randomParent2 = (int) (Math.random() * Z);
            while (randomParent1 == randomParent2){ randomParent2 = (int) (Math.random() * Z);}
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(randomParent1);
            temp.add(randomParent2);
            for(int j = 0;j<i;j++){
                while(Pairs.get(j).containsAll(temp)){
                    randomParent2 = (int) (Math.random() * Z);
                    temp.set(0,randomParent1);
                    temp.set(1,randomParent2);
                }
            }
            while (pkrandom > pk) {
                pkrandom = (int) (Math.random() *(100-0+1))+0;
                randomParent2 = (int) (Math.random() * Z);
                while (randomParent1 == randomParent2){ randomParent2 = (int) (Math.random() * Z);}
                for(int j = 0;j<i;j++){
                    while(Pairs.get(j).containsAll(temp)){
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
        for(int i = 0;i<G.size();i++){
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
