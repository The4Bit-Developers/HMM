/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asignment;

public class Asignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HMM hmm = new HMM();
      
        String add;
        add ="E:\\Zip file\\asignment (1)~\\asignment\\src\\asignment\\Electricity.txt";  // change path for another file
        hmm.readFile(add,3,3); // address,no of states,length of obsevation
        hmm.forward_algo(3, 3); // in this and below function 1st parameter is no of states and 2nd is length of observation 
       hmm.viterbi_Algo(3, 3);
        
       
    }
    
}
