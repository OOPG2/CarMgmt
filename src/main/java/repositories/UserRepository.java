package repositories;

import constants.*;
import interfaces.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserStorage {
    private static final Constants constants = new Constants();
    private static final String userCsv = constants.getCsvBasePath()+"databases/users.csv";
    private static final String[] HEADERS = {"user_id", "password", "role", "name", "email", "phone", "lifetime_points", "loyalty_points", "is_banned"};

    static {
        initializeFile();
    }

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

    public List<String[]> readFile() {
            List<String[]> userInfos = new ArrayList<>();
            String line;

            try (BufferedReader br = new BufferedReader(new FileReader(userCsv))) {
                br.readLine();

                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty())
                        continue;
                    userInfos.add(line.split(","));
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return userInfos;
    }

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
