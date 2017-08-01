/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingsystem.entity;

import bankingsystem.ConsumerFactory;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Biplav
 */
public class Counter extends Thread {

    private final int speed;
    private boolean open;
    public LinkedList<Consumer> queue = new LinkedList<>();
    private static int SERVEDAMOUNT;

    public Counter() {
//        System.out.println("Counter opened = " + this.getId());
        speed = (int) (Math.random() * 1000);
//        System.out.println("speed = " + speed);
        open = true;
        SERVEDAMOUNT = 0;
    }

    public void getStarted() {
        start();
    }

    public boolean addConsumer(Consumer a) {
        if (a == null) {
            return false;
        }

        return queue.add(a);
    }

    public boolean serve() throws InterruptedException {
        synchronized (this) {
            try {
                if (!queue.isEmpty()) {
                    Consumer con = queue.removeFirst();
                    System.out.println("\nconsumer served = " + con.getId() + " by Counter = " + this.getId());
                    SERVEDAMOUNT++;
                    con.taskCompleted(true);
                    sleep(speed);
                    return true;
                } else {
//                    System.out.println("Counter" + this.getId() + " is empty");
                }
            } catch (NoSuchElementException e) {
                System.out.println("no such element");
            }
        }

//        System.out.println("counter size = "+size());        
        return false;
    }

    public int size() {
        return queue.size();
    }

    @Override
    public void run() {
        try {
            while (isOpen()) {
                serve();
                display();
                if (SERVEDAMOUNT == ConsumerFactory.LIMIT_CONSUMER) {
                    System.out.println("\ncounter" + this.getId() + " is closed");
                    close();
                }

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Counter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }

    private boolean isOpen() {
        return open;
    }

    public void display() {
        synchronized (this) {
            System.out.print("\n Counter = " + getId() + " -> ");
            queue.forEach((elem) -> {
                System.out.print(elem.getId() + " ");
            });
        }
    }
}
