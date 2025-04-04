package org.example.CarMgmt.menus.postLogin.Admin;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import org.example.CarMgmt.manager.MenuManager;
import org.example.CarMgmt.manager.UserManager;
import org.example.CarMgmt.objects.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.CarMgmt.menus.postLogin.LoggedMenu.showLoggedMenu;
import static org.example.CarMgmt.menus.postLogin.ProfileMenu.showProfileMenu;

public class CustomerManagementMenu {
    public static void showCustomerManagementMenu(MultiWindowTextGUI gui) {
        BasicWindow showUsersWindow = new BasicWindow("OOP Rentals - Customer Users");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        String[] labels = new String[] {"ID", "Name", "Email", "Phone", "Membership", "Status"};
        Table<String> table = new Table<>(labels);
        Map<String, User> users = UserManager.getUsers().entrySet()
                .parallelStream()
                .filter(entry -> entry.getValue().getRole().equals("Customer"))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
        for (Map.Entry<String, User> entry : users.entrySet()) {
            table.getTableModel().addRow(
                    entry.getKey(),
                    entry.getValue().getName(),
                    entry.getValue().getEmail(),
                    entry.getValue().getPhone(),
                    "Tier " + entry.getValue().getMembership(),
                    entry.getValue().getIsBanned() ? "BANNED" : "Active"
            );
        }
        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            showUsersWindow.close();
            MenuManager.setCameFrom("CustomerManagement");
            showProfileMenu(gui, UserManager.getUserById(data.get(0)));
        });

        ComboBox<String> filterBox = new ComboBox<>();
        final TextBox searchBox = new TextBox().setTextChangeListener((newText, changedByUserInteraction) -> {
            if (changedByUserInteraction) {
                if (!newText.isEmpty()) {
                    Map<String, User> filteredUsers = users.entrySet().parallelStream()
                            .filter(entry -> {
                                User user = entry.getValue();
                                return switch (filterBox.getSelectedItem().toLowerCase()) {
                                    case "id" -> entry.getKey().toLowerCase().contains(newText);
                                    case "name" -> user.getName() != null && user.getName().toLowerCase().contains(newText);
                                    case "email" -> user.getEmail() != null && user.getEmail().toLowerCase().contains(newText);
                                    case "phone" -> user.getPhone() != null && user.getPhone().toLowerCase().contains(newText);
                                    default -> false;
                                };
                            })
                            .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));

                    table.getTableModel().clear();
                    for (Map.Entry<String, User> entry : filteredUsers.entrySet()) {
                        table.getTableModel().addRow(
                                entry.getKey(),
                                entry.getValue().getName(),
                                entry.getValue().getEmail(),
                                entry.getValue().getPhone(),
                                "Tier " + entry.getValue().getMembership(),
                                entry.getValue().getIsBanned() ? "BANNED" : "Active"
                        );
                    }
                }
                else {
                    table.getTableModel().clear();
                    for (Map.Entry<String, User> entry : users.entrySet()) {
                        table.getTableModel().addRow(
                                entry.getKey(),
                                entry.getValue().getName(),
                                entry.getValue().getEmail(),
                                entry.getValue().getPhone(),
                                "Tier " + entry.getValue().getMembership(),
                                entry.getValue().getIsBanned() ? "BANNED" : "Active"
                        );
                    }
                }
            }
        });
        Panel searchPanel = new Panel();
        searchPanel.setLayoutManager(new GridLayout(2));
        searchPanel.addComponent(new Label("Search:"));
        searchPanel.addComponent(searchBox.setLayoutData(GridLayout.createHorizontallyFilledLayoutData()));
        panel.addComponent(searchPanel.setLayoutData(GridLayout.createHorizontallyFilledLayoutData()));

        for (String label : labels)
            filterBox.addItem(label);
        panel.addComponent(filterBox);

        Button returnButton = new Button("Go Back", () -> {
            showUsersWindow.close();
            showLoggedMenu(gui);
        });

        panel.addComponent(table.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(new EmptySpace());
        panel.addComponent(returnButton.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(1)));

        showUsersWindow.setComponent(panel);
        gui.addWindowAndWait(showUsersWindow);
    }
}
