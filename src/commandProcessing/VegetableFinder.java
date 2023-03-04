package commandProcessing;

import Vegetables.VegetableType;

/**
 * @author urlta
 * 
 *         vegetable finder, finds the index of a Vegetable in
 *         VegetableType.values() by the name of the vegetable
 *
 */
public class VegetableFinder {

    /**
     * returns the index of an vegetable out of VegetableType.values() if the
     * entered vegetable is non existent returns carrot
     * 
     * (works for everything using a vegetable tracker, as the class uses the
     * vegetable.values() command to set the vegetable array)
     * 
     * to ensure the vegetable exists, exist vegetable with name must return true
     * for the same input
     * 
     * @param vegetableName
     * @return
     */
    public static VegetableType findVegetableByName(String vegetableName) {
        for (VegetableType vegetable : VegetableType.values()) {
            if (vegetable.getName().equals(vegetableName)) {
                return vegetable;
            }
        }
        System.out.println("Error: tried to find a nonexsistent vegetable by name");
        return VegetableType.CARROT;
    }

    public static boolean existsVegetableWithName(String vegetableName) {
        for (VegetableType vegetable : VegetableType.values()) {
            if (vegetable.getName().equals(vegetableName)) {
                return true;
            }
        }
        return false;
    }
}
