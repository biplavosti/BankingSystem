/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and OPEN the template in the editor.
 */
package bankingsystem.entity;

import bankingsystem.BankingSystem;

/**
 *
 * @author Biplav
 */
public class Consumer extends Thread {

    public int priority;
//    private Counter myCounter;
    private volatile boolean completed;
    private boolean served;

    public Consumer() {
        priority = 0;
        completed = false;
        served = false;
    }

    public Consumer(int priority) {
        this.priority = priority;
        completed = false;
//        myCounter = selectCounter();
        served = false;
    }

    @Override
    public void run() {

        selectCounter();
//        System.out.println("\nConsumer " + this.getId() + " goes to COUNTER = " + myCounter.getId());
//        myCounter.queue.add(this);
//        Counter newCounter = selectCounter();
//        if (myCounter != newCounter) {
//            synchronized (newCounter) {
//                System.out.println("Consumer " + this.getId() + " goes to COUNTER = " + newCounter.getId());
//                if (newCounter.queue.add(this)) {
//                    if (myCounter != null) {
//                        myCounter.queue.remove(this);
//                    }
//                }
//                myCounter = newCounter;
//            }
//        }
        while (!completed);
        
        synchronized (this) {
            System.out.println("\nConsumer closed = " + this.getId());
        }

    }

    public Consumer taskCompleted(boolean completed) {
        this.completed = completed;
//        System.out.println("\nConsumer task completed = " + this.getId());
        return this;
    }

    private Counter selectCounter() {
        Counter counter;
//        for (int i = 0; i < BankingSystem.getActiveCounters(); i++) {
//            Counter ctr = BankingSystem.COUNTER[i];
//            if (counter == null) {
//                counter = ctr;
//            }
//            if (counter.size() > ctr.size()) {
//                counter = ctr;
//            }
//        }

        int index = ((int) (Math.random() * 100)) % BankingSystem.LIMIT;
//        System.out.println("index = "+index);
        counter = BankingSystem.COUNTER[index];
        if (counter == null) {
            counter = BankingSystem.COUNTER[0];
        }
        counter.addConsumer(this);

        synchronized (counter) {
            System.out.println("consumer = " + this.getId() + " added in COUNTER = " + counter.getId());
            counter.display();
        }

//        counter.display();
        return counter;
    }

    public void isServed(boolean served) {
        this.served = served;
//        myCounter.queue.remove();
//        this.completed = served;
    }
}
