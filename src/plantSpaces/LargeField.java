package plantSpaces;

/**
 * @author urlta
 * any object of this class is a PlantSpace with the values of a LargeField
 *
 */
public class LargeField extends Field{
    
    /**
     * sets the values as described for the plantingSpace largeField
     */
    
    public LargeField() {
        setShortName("LFi");
        setLongName("Large Field");
        setCapacity(8);
        setAllowedVegetables(allowedVegetables);
    }
}
