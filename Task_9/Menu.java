package Task_9;

import java.util.HashMap;

public enum Menu {
    GOURMET_SUP,
    FRIED_RABBIT,
    STEAK,
    CAESAR_SALAD,
    CHAMPAGNE;

    public final static HashMap<Menu, Integer> cookingTimes = new HashMap<>();

    static {
        cookingTimes.put(GOURMET_SUP, 11);
        cookingTimes.put(FRIED_RABBIT, 9);
        cookingTimes.put(STEAK, 13);
        cookingTimes.put(CAESAR_SALAD, 7);
        cookingTimes.put(CHAMPAGNE, 3);
    }
}