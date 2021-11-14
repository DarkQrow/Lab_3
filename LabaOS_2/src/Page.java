public class Page {
   byte[] inf = new byte[27];

   boolean exist;
   boolean M;
   boolean R;
   Page(){

      exist = false;
      M = false;
      R = false;
      for(int i = 0;i<27;i++){
         inf[i] = 0;
      }

   }
  /* Page(int x){
      exist = false;
      M = false;
      R = false;
      for(int i = 0;i<27;i++){
         inf[i] = 1;
      }
   }
*/
   public int Inf2to10(){
      int num10 = 0;
      for(int i = 0;i<27;i++){
         if(inf[i] == 1){
            num10+=Math.pow(2,(i+1));
         }
      }
      return num10;
   }
   public void Inf10to2(int x){
      int i = 26;
      while((x != 0)||(i!=0)){
         this.inf[i] = (byte)(x%2);
         x = x/2;
         i--;
      }
   }

   public static Page CopyPage(Page x){
      Page Temp = new Page();
      Temp.inf = x.inf;
      Temp.exist = x.exist;
      Temp.M = x.M;
      Temp.R = x.R;
      return Temp;
   }

   public void ShowPage(){
      System.out.print("По данному запросу хранится : ");
      for(int i = 0;i<27;i++){
         System.out.print(inf [i]+" ");
      }
      System.out.println();
   }
}
