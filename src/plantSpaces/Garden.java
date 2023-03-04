
package plantSpaces;

import Vegetables.VegetableType;

/**
 * @author urlta
 * any object of this class is a PlantSpace with the values of a Garden
 *
 */
public class Garden extends PlantSpace {

    private final VegetableType[] allowedVegetables = VegetableType.values();

    /**
     * sets the values as described for the plantSpace Garden
     */
    public Garden() {
        setShortName("G");
        setLongName("Garden");
        setCapacity(2);
        setAllowedVegetables(allowedVegetables);
    }
}
