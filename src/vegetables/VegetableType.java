package vegetables;

/**
 * @author urlta
 * 
 *         This class sets and saves all important information for each
 *         vegetable
 *
 */
public enum VegetableType {
    CARROT(1, "carrot", "carrots"), SALAD(2, "salad", "salads"), TOMATO(3, "tomato", "tomatoes"),
    MUSHROOM(4, "mushroom", "mushrooms");

    final private int timeToGrow;
    final private String name;
    final private String namePlural;

    VegetableType(int timeToGrow, String name, String namePlural) {
        this.timeToGrow = timeToGrow;
        this.name = name;
        this.namePlural = namePlural;
    }

    public int getTimeToGrow() {
        return timeToGrow;
    }

    public String getName() {
        return name;
    }

    public String getNamePlural() {
        return namePlural;
    }

}
