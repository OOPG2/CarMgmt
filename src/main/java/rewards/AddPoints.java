package rewards;

import app.App;
import objects.Customer;

public class AddPoints {
	public static void addPoints(Customer customer, Integer pointsEarned) {
		Integer currentPoints = customer.getLoyaltyPoints();
		Integer currentLifetimePoints = customer.getLifetimePoints();
		customer.setLoyaltyPoints(currentPoints + pointsEarned);
		customer.setLifetimePoints(currentLifetimePoints + pointsEarned);
		App.userManager.updateUser(customer);
	}
}
