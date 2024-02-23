package Task_5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Note {

    public final List<String> notes = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void addNote(int index, String note) {
        lock.writeLock().lock();
        try {
            System.out.println("Сейчас будет добавлена заметка [" + note + "] На позицию " + index);
            notes.add(index, note);
            System.out.println("Уже добавлена заметка [" + note + "]");
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void removeNote(int index) {
        lock.writeLock().lock();
        try {
            System.out.println("Сейчас будет удалена заметка с позиции " + index);
            String note;
            note = notes.remove(index);
            System.out.println("Уже удалена заметка [" + note + "] с позиции " + index);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void readNote(int index) {
        lock.readLock().lock(); // Блокируем доступ к списку для чтения
        try {
            System.out.println("Сейчас будет прочтена заметка с позиции " + index);
            String note;
            note = notes.get(index);
            System.out.println("Прочтена заметка [" + note + "] с позиции " + index);
        } finally {
            lock.readLock().unlock(); // Разблокируем доступ к списку для чтения
        }
    }
}