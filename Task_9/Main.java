package Task_9;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public final static ConcurrentLinkedQueue<Order> queue = new ConcurrentLinkedQueue<>();
    public static volatile boolean orderFlag = true;
    public static volatile boolean cookFlag = true;

    private static final int COUNT_CUSTOMERS = 2;
    private static final int COUNT_COOK = 2;
    private static final int COUNT_WAITERS = 2;

    public static final ExecutorService cooks = Executors.newFixedThreadPool(COUNT_COOK);
    public static final ExecutorService customers = Executors.newFixedThreadPool(COUNT_CUSTOMERS);
    public static final ExecutorService waiters = Executors.newFixedThreadPool(COUNT_WAITERS);
    public static final ExecutorService queuePrinter = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //запускаем посетителей
        List<CompletableFuture<Void>> customersList = new ArrayList<>();
        for (int i = 0; i < COUNT_CUSTOMERS; ++i) {
            customersList.add(CompletableFuture.runAsync(new Ordering(), customers));
        }
        CompletableFuture<Void> FuturesOfCustomers =
                CompletableFuture.allOf(customersList.toArray(new CompletableFuture[0]));

        //запускаем поваров
        List<CompletableFuture<Void>> cooksList =
                new ArrayList<>();
        for (int i = 0; i < COUNT_COOK; ++i) {
            cooksList.add(CompletableFuture.runAsync(new Kitchen(), cooks));
        }
        CompletableFuture<Void> FuturesOfCooks =
                CompletableFuture.allOf(cooksList.toArray(new CompletableFuture[0]));

        //запускаем принтер очереди
        queuePrinter.submit(() -> {
            while (cookFlag || orderFlag) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Queue: " + queue + "\n");
            }
        }
        );

        TimeUnit.SECONDS.sleep(30);
        orderFlag = false;
        FuturesOfCustomers.get();
        customers.shutdown();
        System.out.println("---------Time to order is up---------" + "\n");

        while (!queue.isEmpty()) {
            TimeUnit.SECONDS.sleep(2);
        }
        queuePrinter.shutdown();
        cookFlag = false;
        FuturesOfCooks.get();
        cooks.shutdown();
        waiters.shutdown();
        System.out.println("---------The kitchen has prepared the last order---------" + "\n");
    }
}