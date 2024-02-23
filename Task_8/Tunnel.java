package Task_8;

import java.util.ArrayList;
import java.util.List;

public class Tunnel {
    private final List<Ship> tunnel = new ArrayList<>(5);

    public List<Ship> getTunnel() {
        return tunnel;
    }

    public void addShip(Ship ship) {
        tunnel.add(ship);
        assert tunnel.size() < 6;
    }

    public void removeShip(Ship ship) {
        tunnel.remove(ship);
    }
}