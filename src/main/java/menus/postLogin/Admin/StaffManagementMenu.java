package menus.postLogin.Admin;

import app.App;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import manager.MenuManager;
import manager.UserManager;
import objects.Staff;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static menus.LoggedMenu.showLoggedMenu;
import static menus.ProfileMenu.showProfileMenu;
import static menus.createAccountMenu.showCreateAccountMenu;


public class StaffManagementMenu {
    public static void showStaffManagementMenu(MultiWindowTextGUI gui) {
        App app = new App();

        UserManager userManager = app.getUserManager();

        BasicWindow showUsersWindow = new BasicWindow("OOP Rentals - Staff Users");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        String[] labels = new String[] {"ID", "Name", "Email", "Phone"};
        Table<String> table = new Table<>(labels);
        Map<String, Staff> users = userManager.getUsers().entrySet()
                .parallelStream()
                .filter(entry -> entry.getValue() instanceof Staff)
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, entry -> (Staff) entry.getValue()));
        for (Map.Entry<String, Staff> entry : users.entrySet()) {
            table.getTableModel().addRow(
                    entry.getKey(),
                    entry.getValue().getName(),
                    entry.getValue().getEmail(),
                    entry.getValue().getPhone()
            );
        }
        table.setSelectAction(() -> {
            List<String> data = table.getTableModel().getRow(table.getSelectedRow());
            showUsersWindow.close();
            MenuManager.setCameFrom("StaffManagement");
            showProfileMenu(gui, userManager.getUserByID(data.get(0)));
        });

        ComboBox<String> filterBox = new ComboBox<>();
        final TextBox searchBox = new TextBox().setTextChangeListener((newText, changedByUserInteraction) -> {
            if (changedByUserInteraction) {
                if (!newText.isEmpty()) {
                    Map<String, Staff> filteredUsers = users.entrySet().parallelStream()
                            .filter(entry -> {
                                Staff user = entry.getValue();
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
                    for (Map.Entry<String, Staff> entry : filteredUsers.entrySet()) {
                        table.getTableModel().addRow(
                                entry.getKey(),
                                entry.getValue().getName(),
                                entry.getValue().getEmail(),
                                entry.getValue().getPhone()
                        );
                    }
                }
                else {
                    table.getTableModel().clear();
                    for (Map.Entry<String, Staff> entry : users.entrySet()) {
                        table.getTableModel().addRow(
                                entry.getKey(),
                                entry.getValue().getName(),
                                entry.getValue().getEmail(),
                                entry.getValue().getPhone()
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

        Button addStaffButton = new Button("Add Staff", () -> {
            showUsersWindow.close();
            MenuManager.setCameFrom("StaffManagement");
            showCreateAccountMenu(gui);
        });

        Button returnButton = new Button("Go Back", () -> {
            showUsersWindow.close();
            showLoggedMenu();
        });

        panel.addComponent(table.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());
        panel.addComponent(new EmptySpace());

        panel.addComponent(addStaffButton);
        panel.addComponent(returnButton.setLayoutData(GridLayout.createHorizontallyEndAlignedLayoutData(1)));

        showUsersWindow.setComponent(panel);
        gui.addWindowAndWait(showUsersWindow);
    }
}
