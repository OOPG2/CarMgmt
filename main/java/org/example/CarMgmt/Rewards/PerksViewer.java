package org.example.CarMgmt.Rewards;

import java.util.List;

import org.example.CarMgmt.App;
import org.example.CarMgmt.Billing.MainMenu;
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
        	MainMenu.showUserSelection();
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
        panel.addComponent(new EmptySpace());
        Integer points = user.getPoints();
        panel.addComponent(new Label(String.format("You currently have %d point%s.", points, points > 1 ? "s" : "")));
        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("Upon accumulating 100 points, your points will be automatically\n"
        		+ "applied as a discount to your next rental"));
        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("* 100 points = $1 off *"));
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
