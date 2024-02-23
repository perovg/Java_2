package Task_8;

import java.util.Random;

public class Generator implements Runnable {

    private int number;

    private Ship generateShip() {
        int loadCapacity;
        Product type;
        Random random = new Random();
        switch (random.nextInt(3)) {
            case 0 -> loadCapacity = 10;
            case 1 -> loadCapacity = 50;
            default -> {
                loadCapacity = 100;
                assert false;
            }
        }

        switch (random.nextInt(3)) {
            case 0 -> type = Product.BANANAS;
            case 1 -> type = Product.BREAD;
            default -> {
                type = Product.CLOTHES;
                assert false;
            }
        }
        number++;
        return new Ship("Ship " + number, type, loadCapacity);
    }

    @Override
    public void run() {
        while (Main.generatingFlag) {
            synchronized (Main.tunnel) {
                if (Main.tunnel.getTunnel().size() < 5) {
                    Ship ship = generateShip();
                    Main.tunnel.addShip(ship);
                    System.out.println(ship.getName() +
                                    " start with " +
                                    ship.getCapacity() +
                                    " " + ship.getType());
                    Main.tunnel.notifyAll();
                } else {
                    try {
                        Main.tunnel.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}