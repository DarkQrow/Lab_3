package Controllers;

import java.util.ArrayList;

public class Test {

    public ArrayList<String> getStemp(ArrayList<ArrayList<ArrayList<String>>> Q,String alpa){
        ArrayList<String> Stemp = new ArrayList<>();
        for(int i = 0;i<Q.size();i++) {
            Stemp.add("");
        }
        int indexE = Q.get(0).size()-1;
        System.out.println(indexE);
        for(int i = 0;i<Q.size();i++){
            for(int k = 0;k<Q.get(i).get(indexE).size();k++){
                if(!Q.get(i).get(indexE).get(k).equals("-")){
                    String temp = Stemp.get(i);
                    Stemp.set(i,temp+Q.get(i).get(indexE).get(k));
                }
            }
            String temp = Stemp.get(i);
            Stemp.set(i,i+temp);
        }
        System.out.println("Stemp");
        for(String el1:Stemp){
            System.out.println(el1+" ");
        }
        return Stemp;
    }
    public ArrayList<String> getPtemp(ArrayList<ArrayList<String>> S,String start){
        ArrayList<String> Ptemp = new ArrayList<>();
        Ptemp.add(start);
        for(int i = 0;i<S.size();i++){
            for(int j = 0;j<S.get(i).size();j++){
                System.out.println(S.get(i).get(j));
              // if((PtempcontainsEl(S.get(i).get(j),Ptemp))&&(!S.get(i).get(j).equals(""))){
                    Ptemp.add(S.get(i).get(j));
               // }
            }
        }
        for (int i = 0;i< Ptemp.size();i++){
            Ptemp.set(i, Ptemp.get(i).chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
        }

        System.out.print("Ptemp: ");
        for(String el:Ptemp) System.out.print(el+" ");
        return Ptemp;
    }
}
