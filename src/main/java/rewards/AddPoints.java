package rewards;

import app.App;
import manager.UserManager;
import objects.Customer;

public class AddPoints {
	public static void addPoints(Customer customer, Integer pointsEarned) {
		App app = new App();
		UserManager userManager = app.getUserManager();
		Integer currentPoints = customer.getLoyaltyPoints();
		Integer currentLifetimePoints = customer.getLifetimePoints();
		customer.setLoyaltyPoints(currentPoints + pointsEarned);
		customer.setLifetimePoints(currentLifetimePoints + pointsEarned);
		userManager.updateUser(customer);
	}
}
