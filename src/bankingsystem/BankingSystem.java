/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and OPEN the template in the editor.
 */
package bankingsystem;

import bankingsystem.entity.Counter;

/**
 *
 * @author Biplav
 */
public class BankingSystem { 
    
    public static final int LIMIT = 3;    
    public static boolean OPEN = false;
    public static Counter[] COUNTER;
    private static int COUNTERSIZE = 0;
    private static BankingSystem instance;
    
    private BankingSystem(){
        
    }
    
    public static BankingSystem getInstance(){
        if(instance == null){
            instance = new BankingSystem();
        }
        return instance;
    }
    
    public void execute() {
        // TODO code application logic here          
        OPEN = true;
        if (OPEN) {
            System.out.println("Bank open");
            COUNTER = new Counter[LIMIT];
            counterFactory();
            ConsumerFactory.startFactory();
            
//            while(OPEN){
//                display();
//            }
        }
    }

    private void counterFactory() {
        System.out.println("counter factory started = ");
        while (COUNTERSIZE < LIMIT) {
            COUNTER[COUNTERSIZE] = new Counter();
            System.out.println("counter created = " + COUNTER[COUNTERSIZE].getId());
            COUNTER[COUNTERSIZE].getStarted();
            COUNTERSIZE++;
        }
    }

    public void close() {
        OPEN = false;
        for (int i = 0; i < getActiveCounters(); i++) {
            Counter ctr = COUNTER[i];
            ctr.close();
        }
    }

    public int getActiveCounters() {
        return COUNTERSIZE;
    }
    
    public void display(){
        for (Counter counter : COUNTER) {
            counter.display();
        }
    }
}
