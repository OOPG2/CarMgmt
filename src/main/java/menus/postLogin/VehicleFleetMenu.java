package menus.postLogin;

import app.VehicleRetriever;
import beans.Vehicle;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;

import java.util.HashMap;

import static menus.postLogin.UpdateVehicleInfoMenu.showUpdateVehicleInfoMenu;
import static menus.postLogin.VehicleManagementMenu.showVehicleManagementMenu;

public class VehicleFleetMenu {
    static String selectedID = null;
    public static void showVehicleFleetMenu(MultiWindowTextGUI gui) {
        BasicWindow window = new BasicWindow("Vehicle Fleet");
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Label label = new Label("Press \"Enter\" To Edit The Selected Vehicle's Information");
        Table<String> table = new Table<>("Vehicle ID","Brand","Model","Type","Car Plate","Daily Rental","Status","Condition","Age","Seats","Mileage");
        try {
            HashMap<String, Vehicle> hashmap = new VehicleRetriever().getVehicles();
            hashmap.keySet().stream()
                    .mapToInt(Integer::valueOf)
                    .sorted()
                    .forEach(k-> table.getTableModel().addRow(hashmap.get(String.valueOf(k)).getAll()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        table.setVisibleRows(10);
        table.setSelectAction(() -> {
            selectedID = table.getTableModel().getRow(table.getSelectedRow()).get(0);
            window.close();
            showUpdateVehicleInfoMenu(gui);
        });
        Button backButton = new Button("Back", () -> {
            window.close();
            showVehicleManagementMenu(gui);
        });
        panel.addComponent(label);
        panel.addComponent(table);
        panel.addComponent(backButton);
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}
