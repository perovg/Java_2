package Task_8;

import java.util.concurrent.TimeUnit;

public class Main {

    public static volatile boolean generatingFlag = true;
    public static volatile boolean loadingFlag = true;

    public static final Tunnel tunnel = new Tunnel();

    public static void main(String[] args) throws InterruptedException {
        Generator generator = new Generator();
        Thread generatorThread = new Thread(generator);

        Berth bananaBerth = new Berth(Product.BANANAS);
        Berth breadBerth = new Berth(Product.BREAD);
        Berth clothesBerth = new Berth(Product.CLOTHES);

        generatorThread.start();
        breadBerth.start();
        clothesBerth.start();
        bananaBerth.start();

        TimeUnit.SECONDS.sleep(30);
        generatingFlag = false;

        synchronized (tunnel) {
            tunnel.notifyAll();

            while (loadingFlag) {
                tunnel.wait();
                if (tunnel.getTunnel().isEmpty()) {
                    loadingFlag = false;
                }
            }

            tunnel.notifyAll();
        }
        System.out.println("---------TIME IS UP----------");
    }
}