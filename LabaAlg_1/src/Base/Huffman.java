package Base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class Huffman {

    //Подсчет вероятности для каждой буквы
    static TreeMap<Character,Integer> countL(String text){
        TreeMap<Character,Integer> tmp = new TreeMap<>();
        for(int i = 0;i<text.length();i++){
            Character c = text.charAt(i);
            Integer count = tmp.get(c);
            tmp.put(c,count != null ? count +1:1);
        }
        return tmp;
    }
    //Прячем непристойности/кодируем все
    static CodeTreeNode Huff(ArrayList<CodeTreeNode> codeTreeNodes){
        while(codeTreeNodes.size()>1){
            Collections.sort(codeTreeNodes);
            CodeTreeNode left = codeTreeNodes.remove(codeTreeNodes.size()-1);
            CodeTreeNode right = codeTreeNodes.remove(codeTreeNodes.size()-1);

            CodeTreeNode tmp = new CodeTreeNode(null, right.weight+ left.weight,left,right);
            codeTreeNodes.add(tmp);
        }
        return codeTreeNodes.get(0);
    }
    //Фак го бэк или декодируем
    static String HuffBack(String encoded,CodeTreeNode tree){
        StringBuilder decode = new StringBuilder();

        CodeTreeNode tmp = tree;
        for(int i = 0;i<encoded.length();i++){
            tmp = encoded.charAt(i) == '0' ? tmp.left : tmp.right;
            if(tmp.content !=null){
                decode.append(tmp.content);
                tmp = tree;
            }
        }
        return  decode.toString();
    }
    //Узел нашего дерева
    static class CodeTreeNode implements Comparable<CodeTreeNode>{

        Character content;
        int weight;
        CodeTreeNode left;
        CodeTreeNode right;

        public CodeTreeNode(Character content,int weight){
            this.content = content;
            this.weight = weight;
        }
        public CodeTreeNode(Character content,int weight,CodeTreeNode left,CodeTreeNode right) {
            this.weight = weight;
            this.content = content;
            this.left = left;
            this.right = right;
        }
        @Override
        public int compareTo(CodeTreeNode o) {
            return o.weight - weight;
        }

        //обход дерева через рекурсию
        public String getCodeForCharecter(Character ch,String parentpath){
            if(content == ch){
                return parentpath;
            }
            else{
                if(left!=null){
                    String path = left.getCodeForCharecter(ch,parentpath+0);
                    if(path!=null){
                        return path;
                    }
                }
                if(right!=null){
                    String path = right.getCodeForCharecter(ch,parentpath+1);
                    if(path!=null){
                        return path;
                    }
                }
            }
            return null;
        }
    }
}
