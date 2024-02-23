package Task_9;

public class Kitchen implements Runnable {

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        while (Main.cookFlag) {
            Order order = Main.queue.poll();
            if (order != null) {
                try {
                    System.out.println("┊┊┊   " + name + " starts cooking " + order + "\n");
                    if (order.cook()) {
                        System.out.println("┋┋┋   " + name + " has cooked " + order + "\n");
                        Main.waiters.submit(new Delivery(order));
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}