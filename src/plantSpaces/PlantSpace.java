package plantSpaces;

import Vegetables.VegetableType;

/**
 * @author urlta
 * 
 *         This class is responsible for the planting and growing of vegetables
 *         it should be modified by setting the names, capacity and
 *         allowedVegetables. this can be done manually or by the Constructor
 *         copying a Planting space
 * 
 *
 */
public class PlantSpace {
    private String shortName;
    private String longName;
    private int capacity;
    private VegetableType[] allowedVegetables;
    private VegetableType plantedVegetable;
    private int growCountdown = -1;
    private int currentAmount = 0;
    private int xCoordinate;
    private int yCoordinate;

    /**
     * this constructor allows a plant space to use all the important values of a
     * different plant space, essentially copying it
     * 
     * @param anotherPlantSpace Space to be copied
     */
    public PlantSpace(PlantSpace anotherPlantSpace) {
        this.shortName = anotherPlantSpace.getShortName();
        this.longName = anotherPlantSpace.getLongName();
        this.capacity = anotherPlantSpace.getCapacity();
        this.allowedVegetables = anotherPlantSpace.getAllowedVegetables();
    }

    /**
     * this is the constructor for when nothing needs to be changed
     */
    public PlantSpace() {

    }

    public String getLongName() {
        return longName;
    }

    public VegetableType getPlantedVegetable() {
        return plantedVegetable;
    }

    public void setPlantedVegetable(VegetableType plantedVegetable) {
        this.plantedVegetable = plantedVegetable;
    }

    public int getGrowCountdown() {
        return growCountdown;
    }

    public void setGrowCountdown(int growCountdown) {
        this.growCountdown = growCountdown;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    /**
     * method returns position on entered axis 'x' or 'y'
     * 
     * @param axis axis can be 'x' or 'y'
     * @return position on chosen axis
     */
    public int getPosition(char axis) {
        if (axis == 'x') {
            return xCoordinate;
        }
        if (axis == 'y') {
            return yCoordinate;
        } else {
            System.out.println("Error: the programm tired to find a coordinate from a different dimension");
            return 0;
        }
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public void setXCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public void setYCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getShortName() {
        return shortName;
    }

    public int getCapacity() {
        return capacity;
    }

    public VegetableType[] getAllowedVegetables() {
        return allowedVegetables;
    }

    public void setPosition(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAllowedVegetables(VegetableType[] allowedVegetables) {
        this.allowedVegetables = allowedVegetables;
    }

    /**
     * does everything to end the round: grows vegetables and returns the amount of
     * vegetables grown
     * 
     * @return amount of vegetables that grew that round
     */
    public int endRound() {
        if (growCountdown > 0) {
            growCountdown--;
        }
        if (growCountdown == 0) {
            growCountdown = plantedVegetable.getTimeToGrow();
            return growVegetables();
        }
        return 0;
    }

    private int growVegetables() {
        if (currentAmount * 2 < capacity) {
            currentAmount = currentAmount * 2;
            return currentAmount / 2; // returns the number of Grown Vegetables
        } else if (currentAmount * 2 > capacity) {
            int grownVegetables = capacity - currentAmount;
            currentAmount = capacity;
            growCountdown = -1; // stops the grow counter because Space is full
            return grownVegetables;
        } else {
            currentAmount = currentAmount * 2;
            growCountdown = -1; // stops the grow counter because Space is full
            return currentAmount / 2;
        }
    }

    /**
     * sets the plantedVegetable and starts and sets the corresponding grow
     * countdown
     * 
     * @param plantedVegetable vegetable that will be planted
     */
    public void plantVegetable(VegetableType plantedVegetable) {
        this.plantedVegetable = plantedVegetable;
        this.growCountdown = plantedVegetable.getTimeToGrow();
    }

}
