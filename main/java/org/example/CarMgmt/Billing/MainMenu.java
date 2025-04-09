package org.example.CarMgmt.Billing;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Billing.Menus.CustomerMenu;
import org.example.CarMgmt.Billing.Menus.StaffMenu;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;

public class MainMenu {
	public static BasicWindow menuWindow;
	public static Panel panel;
	public static void showUserSelection() {
		MultiWindowTextGUI gui = App.gui;
		User user = UserSelection.user;
		String userType = user.getType();
        menuWindow = new BasicWindow(String.format("%s Menu", userType));
        panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        switch (userType) {
        // TODO: change to modern switch
        case "Customer":
        	new CustomerMenu().populateMenuItems();
        	break;
        case "Staff":
        case "Admin":
        	new StaffMenu().populateMenuItems();
        	break;
        }
        Button changeUserButton = new Button("Change User", () -> {
        	menuWindow.close();
        	UserSelection.showUserSelection();
        });
        panel.addComponent(new EmptySpace());
		panel.addComponent(changeUserButton);
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
    }
}
