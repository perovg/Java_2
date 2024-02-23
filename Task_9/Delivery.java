package Task_9;

import java.util.ArrayList;
import java.util.HashMap;

public class Delivery implements Runnable {

    private final Order order;
    private HashMap<Integer, Waiter> waiters;

    public Delivery(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        try {
            System.out.println("╳╳╳   " + name + " started delivery " + order + "\n");
            order.delivery();
            System.out.println("✔✔✔   " + name + " delivered " + order + "\n");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}