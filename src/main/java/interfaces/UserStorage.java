/**
 * The UserStorage interface defines methods for managing user data storage.
 * It includes functionality for writing, reading, and appending data to a storage system.
 */
package interfaces;

import java.util.List;

/**
 * UserStorage provides an abstraction for interacting with user data storage.
 */
public interface UserStorage {
    /**
     * Writes a list of user records to the storage.
     *
     * @param users a list of user records, where each record is represented as a string array
     */
    void writeFile(List<String[]> users);

    /**
     * Reads user records from the storage.
     *
     * @return a list of user records, where each record is represented as a string array
     */
    List<String[]> readFile();

    /**
     * Appends a single user record to the storage.
     *
     * @param user a user record represented as a string array
     */
    void appendFile(String[] user);
}
