package org.example.CarMgmt.Billing;

import org.example.CarMgmt.App;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import org.example.CarMgmt.Billing.Invoices.UserRetriever;

public class UserSelection {
	public static User user;
	public static void showUserSelection() {
		MultiWindowTextGUI gui = App.gui;
		
        BasicWindow menuWindow = new BasicWindow("User Selection");
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));
        //TODO: REMOVE WHEN INTEGRATING
        new UserRetriever(true);
        Button customerButton = new Button("Customer", () -> {
        	menuWindow.close();
        	user = UserRetriever.users.get("1");
        	MainMenu.showUserSelection();
        });
        Button staffButton = new Button("Staff", () -> {
        	menuWindow.close();
        	user = new User(2, "Staff");
        	MainMenu.showUserSelection();
        });
        Button adminButton = new Button("Admin", () -> {
        	menuWindow.close();
        	user = new User(3, "Admin");
        	MainMenu.showUserSelection();
        });


        panel.addComponent(customerButton);
        panel.addComponent(staffButton);
        panel.addComponent(adminButton);
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
    }
}