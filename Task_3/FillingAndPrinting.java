package Task_3;

import java.util.ArrayList;
import java.util.List;

public class FillingAndPrinting {

    private static volatile boolean isFilled = false;
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        WriterThread writer = new WriterThread(10);
        PrinterThread printer = new PrinterThread();

        Thread writerThread = new Thread(writer);
        Thread printerThread = new Thread(printer);
        writerThread.start();
        printerThread.start();

        writerThread.join();
        printerThread.join();
    }

    private static class WriterThread implements Runnable {

        private final int size;

        private WriterThread(int size) {
            this.size = size;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; ++i) {
                synchronized (list) {
                    while (isFilled) {
                        try {
                            list.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    for (int j = 0; j < size; ++j) {
                        list.add((int) (Math.random() * 100));
                    }
                    isFilled = true;
                    list.notifyAll();
                }
            }
        }
    }

    private static class PrinterThread implements Runnable {

        @Override
        public synchronized void run() {
            for (int i = 0; i < 10; ++i) {
                synchronized (list) {
                    while (!isFilled) {
                        try {
                            list.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(list);
                    isFilled = false;
                    list.clear();
                    list.notifyAll();
                }
            }
        }
    }
}