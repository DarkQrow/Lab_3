package Base;

import java.util.ArrayList;

public class Memory {
    public Byte[] bitMap;
    ArrayList<Byte[]> blocks = new ArrayList<>();
    int Chblocks ;
    int blockSize;
    Memory(int x,int y){
        blockSize = (y);
        Chblocks = (x);
        bitMap = new Byte[Chblocks];
        for(int i = 0;i<Chblocks;i++){
            bitMap[i] = 0;
            Byte[] block = new Byte[blockSize];
            for (int j = 0;j<blockSize;j++){
                block[j] = 0;
            }
            blocks.add(block);
        }
    }
    public Memory CopyMem(){
        Memory B = new Memory(this.Chblocks,this.blockSize);
        for(int i = 0;i<Chblocks;i++){
            B.bitMap[i] = this.bitMap[i];
            Byte[] block = new Byte[blockSize];
            for (int j = 0;j<blockSize;j++){
                 block = this.blocks.get(i);
            }
            blocks.add(block);
        }
        return B;
    }
    public void setChblocks(int x){
        Chblocks = x;
    }
    public void freeChblocks(){
        Chblocks = 0;
    }
    public void setBlock(int[] x,Byte[]arr){
        for(int i = 0;i<x.length;i++){
            bitMap[x[i]] = 1;
            blocks.set((x[i]),arr);
        }
    }
    public void readBlock(int[] x ){
        if(x.length>Chblocks)return;
        for(int i = 0;i<x.length;i++){
            System.out.println("Block "+x[i]+":");
            Byte[] tmp = blocks.get(i);
            for(int j = 0;j<tmp.length;j++)
            System.out.print(tmp[j]+" ");
            System.out.println();
        }
    }
    public void printBitMap(){
        System.out.println(bitMap);
    }
    public void readBlock( ){

        for(int i = 0;i<Chblocks;i++){
            System.out.println("Block "+(i)+":");
            Byte[] tmp = blocks.get(i);
            for(int j = 0;j<tmp.length;j++)
                System.out.print(tmp[j]+" ");
            System.out.println();
        }
    }
    public int checkEmptyBlocks(){
        int count = 0;
        for(int i = 0;i<bitMap.length;i++)
            if(bitMap[i] == 0)
                count++;
            return count;
    }
    public int MemSize(){
        return bitMap.length;
    }
    public void printBlocksInfo(){
        System.out.println("Информация о состоянии блочного пространства");
        System.out.println("Размер блока: "+blockSize);
        System.out.println("Кол-во блоков: "+blocks.size());
        System.out.println("Кол-во пустых блоков: "+checkEmptyBlocks());
        System.out.println("Размер памяти под сл.информацию: "+MemSize()+" байт");

    }

    public static int countBlocksMem(int x){
        int result = 0;
        int checknumber = x;
        int count = 0;
        while(checknumber%2==0){
            checknumber =checknumber/2;
            count++;
        }
        System.out.println(Math.pow(2,count));
       // result =x- (int)Math.pow(2,count);

        return count;
    }
public static void main(String []args){
        //System.out.println(countBlocksMem(320));
    Memory mem = new Memory(8,7);
    int[] blocks = {0,1,2,3,4,5,7};
    mem.readBlock(blocks);
}
}
