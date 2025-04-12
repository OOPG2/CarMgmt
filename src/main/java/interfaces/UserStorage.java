package interfaces;

import java.util.List;

public interface UserStorage {
    void writeFile(List<String[]> users);
    List<String[]> readFile();
    void appendFile(String[] user);
}