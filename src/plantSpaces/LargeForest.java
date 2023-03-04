package plantSpaces;

/**
 * @author urlta
 * any object of this class is a PlantSpace with the values of a LargeForest
 *
 */
public class LargeForest extends Forest {
    
    /**
     * sets the values as described for the plantingSpace largeForest
     */
    public LargeForest() {
        setShortName("LFo");
        setLongName("Large Forest");
        setCapacity(8);
        setAllowedVegetables(allowedVegetables);
    }
    
}
