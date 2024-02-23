package Task_4;

public class OneByOne {
    private static final Object lock = new Object();

    private static boolean oddTurn = true;

    public static void main(String[] args) {
        Thread oddThread = new Thread(new OddPrinter());
        Thread evenThread = new Thread(new EvenPrinter());

        oddThread.start();
        evenThread.start();
    }

    static class OddPrinter implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 10; i += 2) {
                synchronized (lock) {
                    while (!oddTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(i);
                    oddTurn = false;
                    lock.notify();
                }
            }
        }
    }

    static class EvenPrinter implements Runnable {
        @Override
        public void run() {
            for (int i = 2; i <= 10; i += 2) {
                synchronized (lock) {
                    while (oddTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(i);
                    oddTurn = true;
                    lock.notify();
                }
            }
        }
    }
}