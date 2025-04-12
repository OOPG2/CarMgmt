package rewards;

import app.App;
import com.googlecode.lanterna.gui2.*;
import manager.AuthenticationManager;
import menus.LoggedMenu;
import objects.Customer;

public class PerksViewer {
	public static void showPerks() {
        App app = new App();
		MultiWindowTextGUI gui = App.gui;
		BasicWindow menuWindow = new BasicWindow(String.format("Membership Perks"));
		Panel panel = new Panel();
		panel.setLayoutManager(new GridLayout(1));
        Button back = new Button("Back", () -> {
        	menuWindow.close();
        	LoggedMenu.showLoggedMenu();
        });
        panel.addComponent(back);
        AuthenticationManager authenticationManager = app.getAuthenticationManager();
        Customer user = (Customer) authenticationManager.getLoggedUser();
        TierBenefits tierBenefits = new TierBenefits(user.getLifetimePoints());
        panel.addComponent(new Label(String.format("You are in the %s tier. Enjoy:", tierBenefits.getTier())));
        String benefits = "";
        for (String b: tierBenefits.getBenefits()) {
        	benefits += String.format("- %s\n", b);
        }
        panel.addComponent(new Label(benefits));
        panel.addComponent(new EmptySpace());
        Integer points = user.getLoyaltyPoints();
        panel.addComponent(new Label(String.format("You currently have %d point%s.", points, points > 1 ? "s" : "")));
        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("Upon accumulating 100 points, your points (in increments of 100)\n"
        		+ "will be automatically applied as a discount to your next invoice."));
        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("* 100 points = $1 off *"));
        menuWindow.setComponent(panel);
        gui.addWindowAndWait(menuWindow);
	}
}
