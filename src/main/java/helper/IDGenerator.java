/**
 * The IDGenerator class provides utility methods for generating unique identifiers.
 * It includes functionality to generate IDs with a specified prefix.
 */
package helper;

/**
 * IDGenerator offers a method to create unique IDs by appending a random numeric value to a prefix.
 */
public class IDGenerator {
    /**
     * Generates a unique identifier using the provided prefix.
     * The generated ID consists of the prefix followed by a random integer between 0 and 9999.
     *
     * @param prefix the string to prepend to the generated ID
     * @return a unique identifier as a string
     */
    public static String generateUniqueID(String prefix) {
        return prefix + (int)(Math.random() * 10000);
    }
}
