public class ParallelPrinting {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("Поток 1", 5);
        MyThread t2 = new MyThread("Поток 2", 3);
        MyThread t3 = new MyThread("Поток 3", 7);

        t1.start();
        t2.start();
        t3.start();
    }
}


class MyThread extends Thread {
    private String name;
    private int count;

    public MyThread(String name, int count) {
        this.name = name;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 1; i <= count; i++) {
            System.out.println(name + " " + i);
        }
    }
}

