package plantSpaces;

import Vegetables.VegetableType;

/**
 * @author urlta
 * any object of this class is a PlantSpace with the values of a Forest
 *
 */
public class Forest extends PlantSpace{
    
    protected final VegetableType[] allowedVegetables = { VegetableType.CARROT, VegetableType.MUSHROOM };
    
    /**
     * sets the values as described for the plantSpace Forest
     */
    public Forest() {
        setShortName("Fo");
        setLongName("Forest");
        setCapacity(4);
        setAllowedVegetables(allowedVegetables);
    }
}
