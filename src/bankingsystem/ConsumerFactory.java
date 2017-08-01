/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and OPEN the template in the editor.
 */
package bankingsystem;

import bankingsystem.entity.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Biplav
 */

public class ConsumerFactory extends Thread {

    private static ConsumerFactory ConsumerFactoryInstance;
    public static final int LIMIT_CONSUMER = 3;
    private static int consumerCount = 0;
    private static boolean limitReached = false;

    public static void startFactory() {
        if (ConsumerFactoryInstance == null) {
            ConsumerFactoryInstance = new ConsumerFactory();
            ConsumerFactoryInstance.start();
        }
    }

    public static ConsumerFactory getInstance() {
        startFactory();
        return ConsumerFactoryInstance;
    }

    private ConsumerFactory() {
    }

    public void produce() {
//        if (consumerCount < LIMIT_CONSUMER) {
//            new Consumer().start();
        new Consumer().start();
//            System.out.println("consumer id = "+ con.getId());

        consumerCount++;
//        }
    }

    @Override
    public void run() {
        try {
            int count = 0;
            double random;
            double random2;

            while (BankingSystem.OPEN && !limitReached) {

                random = Math.random() * 100;
                random2 = Math.random() * 100;
                if (random > 0 && random < 98.4 && random2 > 0 && random2 < 65.4) {
                    //sleep(100);
                    produce();
                    count++;
                }
                if (consumerCount == LIMIT_CONSUMER) {
                    limitReached = true;
                }
            }
            System.out.println("\nlimit reached consumer factory closed = " + consumerCount);
        } catch (Exception ex) {
            Logger.getLogger(ConsumerFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
