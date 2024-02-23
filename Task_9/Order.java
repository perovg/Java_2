package Task_9;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Order {

    private final String id;
    private final List<Menu> orderList;
    private final int deliveryTime;
    private final String customer;

    private Status status = Status.WAITING_FOR_COOKING;

    private final ReentrantLock locker = new ReentrantLock();

    public Order(String id, List<Menu> orderList, int deliveryTime, String customer) {
        this.id = id;
        this.orderList = orderList;
        this.deliveryTime = deliveryTime;
        this.customer = customer;
        System.out.println("□□□   " + customer + " ordered: " + id + " | " + orderList + "\n");
    }

    public boolean cook() throws InterruptedException {
        if (status == Status.WAITING_FOR_COOKING) {
            if (locker.tryLock()) {
                if (status == Status.WAITING_FOR_COOKING) {
                    try {
                        status = Status.IS_COOKING;
                        for (Menu dish : orderList) {
                            TimeUnit.SECONDS.sleep(Menu.cookingTimes.get(dish));
                        }
                        status = Status.AWAITING_DELIVERY;
                        return true;
                    } finally {
                        locker.unlock();
                    }
                }
            }
        }
        return false;
    }


    public void delivery() throws InterruptedException {
        if (status == Status.AWAITING_DELIVERY) {
            if (locker.tryLock()) {
                if (status == Status.AWAITING_DELIVERY) {
                    try {
                        status = Status.DELIVERY;
                        TimeUnit.SECONDS.sleep(deliveryTime);
                        status = Status.COMPLETE;
                    } finally {
                        locker.unlock();
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return id;
    }
}