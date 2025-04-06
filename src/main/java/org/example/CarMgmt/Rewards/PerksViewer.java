package org.example.CarMgmt.Rewards;

import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Billing.User;
import org.example.CarMgmt.Billing.UserSelection;
import org.example.CarMgmt.Billing.Menus.CustomerMenu;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;

public class PerksViewer {
	public void showPerks() {
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Membership Perks"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	new CustomerMenu().populateMenuItems();
        });
        panel.addComponent(back);
        User user = UserSelection.user;
        TierBenefits tierBenefits = new TierBenefits(user.getLifetimePoints());
        panel.addComponent(new Label(String.format("You are in the %s tier. Enjoy:", tierBenefits.getTier())));
        String benefits = "";
        for (String b: tierBenefits.getBenefits()) {
        	benefits += String.format("- %s\n", b);
        }
        panel.addComponent(new Label(benefits));
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
