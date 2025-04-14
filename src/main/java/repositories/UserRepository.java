/**
 * The UserRepository class implements the UserStorage interface to manage user data storage.
 * It provides methods for writing, reading, and appending user data to a CSV file.
 * This class automatically initializes the file if it does not exist or is empty.
 */
package repositories;

import constants.Constants;
import interfaces.UserStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserRepository is responsible for interacting with the user data stored in a CSV file.
 */
public class UserRepository implements UserStorage {
    /**
     * Constants instance used to fetch the base path for the CSV file.
     */
    private static final Constants constants = new Constants();

    /**
     * The file path of the user CSV file.
     */
    private static final String userCsv = constants.getCsvBasePath() + "databases/users.csv";

    /**
     * Headers for the user CSV file.
     */
    private static final String[] HEADERS = {"user_id", "password", "role", "name", "email", "phone", "lifetime_points", "loyalty_points", "is_banned"};

    /**
     * Static block to initialize the user CSV file.
     */
    static {
        initializeFile();
    }

    /**
     * Initializes the user CSV file by creating directories and writing headers if the file does not exist or is empty.
     */
    private static void initializeFile() {
        File file = new File(userCsv);
        try {
            file.getParentFile().mkdirs();

            if (!file.exists() || file.length() == 0) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(String.join(",", HEADERS));
                }
            }
        } catch (IOException e) {
            System.err.println("Error initializing users.csv (initializeFile): " + e.getMessage());
        }
    }

    /**
     * Writes a list of user records to the user CSV file.
     * Each record is represented as a string array.
     *
     * @param userInfos a list of user records to write to the file
     */
    @Override
    public void writeFile(List<String[]> userInfos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userCsv))) {
            writer.write(String.join(",", HEADERS));
            writer.newLine();

            for (int i = 0; i < userInfos.size(); i++) {
                writer.write(String.join(",", userInfos.get(i)));

                if (i < userInfos.size() - 1) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing users to file (writeFile): " + e.getMessage());
        }
    }

    /**
     * Reads user records from the user CSV file.
     * Each record is parsed as a string array.
     *
     * @return a list of user records read from the file
     */
    @Override
    public List<String[]> readFile() {
        List<String[]> userInfos = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(userCsv))) {
            br.readLine(); // Skip headers

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                userInfos.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfos;
    }

    /**
     * Appends a single user record to the user CSV file.
     *
     * @param userInfo a user record represented as a string array
     */
    @Override
    public void appendFile(String[] userInfo) {
        if (userInfo != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(userCsv, true))) {
                if (new File(userCsv).length() > 0) {
                    writer.newLine();
                }
                writer.write(String.join(",", userInfo));
                System.out.println("Data appended to " + userCsv);
            } catch (IOException e) {
                System.err.println("Error appending users to file (createUser): " + e.getMessage());
            }
        }
    }
}
