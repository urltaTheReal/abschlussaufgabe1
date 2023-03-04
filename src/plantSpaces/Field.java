package plantSpaces;

import Vegetables.VegetableType;

/**
 * @author urlta
 * any object of this class is a PlantSpace with the values of a Field
 *
 */
public class Field extends PlantSpace {
    
    protected final VegetableType[] allowedVegetables = { VegetableType.CARROT, VegetableType.SALAD, VegetableType.TOMATO };
    
    /**
     * sets the values as described for the plantSpace Field
     */
    public Field() {
        setShortName("Fi");
        setLongName("Field");
        setCapacity(4);
        setAllowedVegetables(allowedVegetables);
    }
}
