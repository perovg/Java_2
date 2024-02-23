package Task_8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Berth extends Thread {

    private final Product type;

    public Berth (Product type) {
        this.type = type;
    }

    @Override
    public void run() {
        List<Ship> loadShipList = new ArrayList<>();
        while (Main.loadingFlag) {
            synchronized (Main.tunnel) {
                if (!Main.tunnel.getTunnel().isEmpty()) {
                    for (Ship ship : Main.tunnel.getTunnel()) {
                        if (ship.getType() == type) {
                            loadShipList.add(ship);
                        }
                    }
                } else {
                    System.out.println("The tunnel is empty");
                }

                if (loadShipList.isEmpty()) {
                    try {
                        Main.tunnel.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            for (Ship ship : loadShipList) {
                try {
                    loading(ship);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            loadShipList.clear();
        }
    }

    public void loading(Ship ship) throws InterruptedException {

        System.out.println("Loading of ship number" + ship.getName() + ", type " + ship.getType() + ", has begun");
        TimeUnit.SECONDS.sleep(ship.getCapacity() / 10);

        synchronized (Main.tunnel) {
            Main.tunnel.removeShip(ship);
            Main.tunnel.notifyAll();
        }

        System.out.println(ship.getName() + " is loaded");
    }
}
