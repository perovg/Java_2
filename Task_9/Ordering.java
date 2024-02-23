package Task_9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Ordering implements Runnable {

    private final Random random = new Random();
    private static final AtomicInteger counter = new AtomicInteger();

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        while (Main.orderFlag) {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5, 10));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Main.queue.add(order(name));
        }
    }

    private Order order(String name) {
        int sizeOrder = random.nextInt(1, 3);
        List<Menu> orderList = new ArrayList<>(sizeOrder);
        for (int i = 0; i < sizeOrder; ++i) {
            switch (random.nextInt(5)) {
                case 0 -> orderList.add(Menu.FRIED_RABBIT);
                case 1 -> orderList.add(Menu.STEAK);
                case 2 -> orderList.add(Menu.CAESAR_SALAD);
                case 3 -> orderList.add(Menu.CHAMPAGNE);
                case 4 -> orderList.add(Menu.GOURMET_SUP);
                default -> {
                    assert false;
                }
            }
        }
        int deliveryTime = random.nextInt(5, 16);
        return new Order("Order " + counter.incrementAndGet(), orderList, deliveryTime, name);
    }
}