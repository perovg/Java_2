package Task_8;

public class Ship {
    private final Product type;
    private final String name;
    private final int CAPACITY;

    public Ship(String name, Product type, int cap) {
        this.name = name;
        this.type = type;
        this.CAPACITY = cap;
    }

    public Product getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity() {
        return CAPACITY;
    }
}
