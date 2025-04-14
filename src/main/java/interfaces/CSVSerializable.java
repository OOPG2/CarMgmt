/**
 * The CSVSerializable interface defines a method for converting objects to CSV row format.
 * Classes implementing this interface should provide their own implementation for CSV serialization.
 */
package interfaces;

/**
 * CSVSerializable provides an abstraction for converting objects into CSV-compatible string arrays.
 */
public interface CSVSerializable {
    /**
     * Converts the object into a CSV row representation.
     *
     * @return an array of strings representing the object in CSV format
     */
    String[] toCsvRow();
}
