package Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class LZW{
    public static void main(String[]args){
String test = "з е л е н а я   256 258 н ь 263 257 259 е е т ";
System.out.println(test);
ArrayList<String> code = new ArrayList<>();

//System.out.println(code);
       int i = 0;
        while(i<test.length()){
            int index = test.indexOf(" ");
            if(index == 0) index++;
            String tmp = test.substring(0,index);
            code.add(tmp);
            test =test.replaceFirst(tmp+" ","");
        }
        String itog = LZWdecode(code);
        System.out.println(itog);
    }
    public static ArrayList LZWcode(String text) {
        HashMap<Integer, String> codetable = new HashMap<>();

        for (int i = 0; i < 256; i++) {
            codetable.put(i, Character.toString(i));
        }
        int i = 1;

        ArrayList<String> code = new ArrayList<>();
        String buff = "" + text.charAt(0);

        while (i < text.length()) {
            char vpot = text.charAt(i);
            if (codetable.containsValue(buff + vpot)) {
                buff = buff + vpot;
            } else {
                if(buff.length()<2){
                    code.add(""+buff);
                }
                else {
                    code.add("" + getKeyByValue(codetable, buff));
                }
                codetable.put(codetable.size(), buff + vpot);
                buff = "" + vpot;
            }
            i++;
        }

        if((i) == text.length()){
            code.add(text.charAt(i-1)+"");
            codetable.put(codetable.size(),""+text.charAt(i-1));

        }

        return code;
    }
     public static String LZWdecode(ArrayList<String> text) {
         HashMap<Integer, String> codetable = new HashMap<>();

         for (int i = 0; i < 256; i++) {
             codetable.put(i, Character.toString(i));
         }
         int i = 1;
         String res = new String();
         String str;
         String ch;
         String oldc = ""+text.get(0);
         ch = text.get(0);
         res = res + oldc;
         String newc ;

         while (i < text.size()) {
             newc = ""+text.get(i);
             if(!codetable.containsValue(newc)){
                 str = perev(oldc,codetable);
                 str = str+ch;
             }
             else{
                 str = perev(newc,codetable);
             }

             res = res+str;
             ch = ""+str.charAt(0);
            // if(oldc.length()>1){
            //     oldc = perev(oldc,codetable);
           //  }
             codetable.put(codetable.size(),oldc+ch);

             oldc = newc;
             i++;

         }
         return res;
     }
     public static String perev(String beg,HashMap<Integer,String> codetable){
        String res;

        if(beg.length()<2) res = beg;
        else{
            res = codetable.get(Integer.parseInt(beg));
        }
    return res;
     }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}