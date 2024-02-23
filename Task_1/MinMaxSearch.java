package Task_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MinMaxSearch {
    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader("/home/gregory/IdeaProjects/Java_2/resoursces/data.txt"));
        String[] inp = r.readLine().split(" ");
        int[] arr = new int[inp.length];
        for (int i = 0; i < inp.length; i++) {
            arr[i] = Integer.valueOf(inp[i]);
        }

        //Параллельный поиск
        FindMinMax findMin = new FindMinMax(arr, 0, arr.length - 1, true);
        FindMinMax findMax = new FindMinMax(arr, 0, arr.length - 1, false);

        Thread t1 = new Thread(findMin);
        Thread t2 = new Thread(findMax);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MinMax min = findMin.getResult();
        MinMax max = findMax.getResult();

        System.out.println("Минимум и максимум в массиве: " + min.min + ", " + max.max);

        //Примитивный поиск
        int minim = Integer.MAX_VALUE;
        int maxim = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            minim = Math.min(minim, arr[i]);
            maxim = Math.max(maxim, arr[i]);
        }

        if(minim == min.min && maxim == max.max) {
            System.out.println("Значения совпали");
        } else {
            System.out.println("Правильный минимум и максимум в массиве:" + minim + ", " + maxim);
        }
    }
}

class FindMinMax implements Runnable {
    int[] arr;
    int start;
    int end;
    boolean findMin;
    MinMax result;

    public FindMinMax(int[] arr, int start, int end, boolean findMin) {
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.findMin = findMin;
        this.result = null;
    }

    public MinMax getResult() {
        return result;
    }

    @Override
    public void run() {
        if (arr == null) {
            return;
        }

        int min = arr[start];
        int max = arr[start];

        for (int i = start + 1; i <= end; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        result = new MinMax(min, max);
    }
}

class MinMax {
    int min;
    int max;

    public MinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }
}