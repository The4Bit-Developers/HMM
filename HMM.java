/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HMM {
    
   BufferedReader br;
   Float[] initial;
   Float[][] Trans;
   Float[][] emission;
   double[] obs={1,2,3};         //this is for hot and cold sequence 
  // double[] obs ={11.00,12.099,13.00};  // this is for electricity sequence
   double[] seq;
  
   
   public HMM()
   {
    initial= new Float[3];
   Trans = new Float[3][3];
   emission = new Float[3][3];
   seq= new double[3];  
   
   }
   
    void readFile(String fileName, int N,int LengthOfObs)
   {
       seq= new double[LengthOfObs];
       initial= new Float[N];
       Trans= new Float[N][N];
       emission = new Float[N][LengthOfObs];
       
    try{
        
     br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
   
     //  reading sequence from file. we can change the sequence in file 
        for(int i=0;i<LengthOfObs;i++)
            seq[i]=Double.parseDouble(br.readLine());
        String[] s = new String[3];
        
        //Initial Probabilities
        s[0]=br.readLine();
        for(int i=0;i<N ;i++)
            initial[i]=Float.parseFloat(br.readLine());
        
        //Transition Probabilities
        
        s[1]= br.readLine();
    
        for(int i=0;i<N ;i++)
            for(int j=0;j<N ;j++)
                Trans[i][j]= Float.parseFloat(br.readLine());
        
        //Emission Probabilities
        
        s[2]=br.readLine();
        for(int i=0;i<N;i++)
        {
        for(int j=0;j<obs.length;j++)
            emission [i][j]=Float.parseFloat(br.readLine());
       
        
        }  
          
 
       
        show(initial,Trans,s,emission,LengthOfObs);
           
        } catch (FileNotFoundException ex) {
           // Logger.getLogger(Asignment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
           // Logger.getLogger(Asignment.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   void show(Float[] initial, Float[][] Trans, String[] s, Float[][] e,int len)
   {
       
         // Printing Initial Probabillities
    System.out.println(""+s[0]);
    System.out.print("[");
    for(int i=0;i<initial.length ;i++)
        System.out.print(initial[i]+" ");
     System.out.print("]\n");
     
       // Printing Transition Probabillities
      System.out.println(s[1]);
        for(int i=0;i<Trans.length ;i++){
            for(int j=0;j<Trans.length ;j++){
                System.out.print(Trans[i][j]+"  ");}     
        
           System.out.println("");   
   }
        
   // Printing Emission Probabillities     
    System.out.println(s[2]);

      for(int i=0;i<e.length ;i++){
            for(int j=0;j<obs.length;j++){
                System.out.print(e[i][j]+"  ");}     
        
           System.out.println("");    }
   }
    void forward_algo(int N,int len)
    {
        int k=0;
    float alphas[][]= new float[len][N];
   int ind;
   ind=getInd(seq[0]);
   for(int i=0;i<N;i++)
      alphas[k][i]=initial[i]*emission[i][ind];
   
   k++;
  
   for(int c=1;c<len;c++)
   {   
       ind = getInd(seq[c]);
       float dum=0;
     for (int i=0;i<N;i++){
         dum=0;
         for(int j=0;j<N;j++)
         dum= dum+ (Trans[j][i]*emission[i][ind]*alphas[c-1][j]);
  
         alphas[k][i]=dum;
     }   k++;
     }
  //For printing alphas 
     System.out.println("Alphas");
     for(int i=0;i<len;i++)
        for(int j=0;j<N;j++)
              System.out.println(""+alphas[i][j]);
     //For forward probability
    float sum=0;
     int j=alphas.length-1;
     System.out.println("Forward Probability Of the sequence is:");
     for(int i=0;i<N;i++)
     {
     sum= sum+ alphas[j][i];
     }
     System.out.println(sum);
     
 
    }
    
    void viterbi_Algo(int N,int len)
    {
     Float[][] v= new Float[len][N];
     Float[] chk = new Float[N];
     int ind;
     int k=0;
     ind= getInd(seq[0]);
     for(int i=0;i<N;i++)
         v[k][i]=initial[i]*emission[i][ind];
     k++;
     for(int c=1;c<len;c++)
     {
         ind=getInd(seq[c]);
     for(int i=0;i<N;i++){
         for(int j=0;j<N;j++)
             chk[j]= Trans[j][i]*emission[i][ind]*v[c-1][j];
         v[k][i]=max(chk);
             
     }
     k++;
    }
    // for printing viterbi values
        System.out.println("Viterbi");
        for(int i=0;i<len;i++)
          for(int j=0;j<N;j++)
             System.out.println(""+v[i][j]);
     // for printing and finding sequence
     String[] seq = new String[len];
     
         for(int i=0;i<len;i++){
            for(int j=0;j<N;j++)
            chk[j]=v[i][j];
            ind=getSeq(chk);
            if(initial.length ==2)
            {
            if(ind==0)
                seq[i]="H";
            else
                seq[i]="C"; }
            if(initial.length==3)
            {
            if(ind==0)
                seq[i]="Shortage";
            if(ind==1)
                seq[i]="Surplus";
            if(ind==2)
                seq[i]="Average";
            
            }
            
            }
        System.out.println("Viterbi Sequence is:");    
     for(int i=0;i<seq.length;i++)
         System.out.print(" "+seq[i]);
     System.out.println();
     
    }
    
    // to find maximum  for viterbi values
     Float max(Float arr[])
     {
     float max=arr[0];
     for(int i=0;i<arr.length;i++){
         if(arr[i]>max)
             max=arr[i];}
     return max;
  
     }
     
     //to find seq of viterbi by finding indexes
     int getSeq(Float arr[])
     {
     float max=arr[0];
     int ind=0;
     for(int i=0;i<arr.length;i++){
         if(arr[i]>max){
             max=arr[i];
             ind=i;
         }}
     return ind;
  
     }
     
     int getInd(double seq)
     {
         int ind=0;
     for( int i=0;i<obs.length;i++)
      if(seq==obs[i]){
        ind=i;
          break;
      }
      return ind;    
         }
    
}
