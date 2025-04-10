package org.example.CarMgmt.menus.postLogin.Customer;

import com.googlecode.lanterna.gui2.*;

public class CustomerVehicleManagementMenu {
    public static void showCustomerVehicleManagementMenu(MultiWindowTextGUI gui){
        BasicWindow window = new BasicWindow("Vehicle Management");
        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Button collectVehicle = new Button("Collect Vehicle", () -> {
            window.close();
            CollectVehicleMenu.showMenu(gui);
        });

        Button returnVehicle = new Button("Return Vehicle", () -> {
            window.close();
            ReturnVehicleMenu.showMenu(gui);
        });

        Button exitButton = new Button("Exit", window::close);
        panel.addComponent(collectVehicle);
        panel.addComponent(returnVehicle);
        panel.addComponent(new EmptySpace());
        panel.addComponent(exitButton);
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}
