package Task_6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Chest {

    private int gold; // Количество золота в сундуке

    public Chest(int gold) {
        this.gold = gold;
    }

    public synchronized void addGold(int amount) {
        gold += amount;
        System.out.println("Добавлено " + amount + " золота. Теперь в сундуке " + gold + " золота.");
    }

    public int getGold() {
        return gold;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Chest chest = new Chest(0);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(() -> {
                int amount = ThreadLocalRandom.current().nextInt(10, 51);
                chest.addGold(amount);
                return null;
            });
        }

        executor.invokeAll(tasks);

        executor.shutdown();

        System.out.println("В сундуке " + chest.getGold() + " золота.");
    }
}
