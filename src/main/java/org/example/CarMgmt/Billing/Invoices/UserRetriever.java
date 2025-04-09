package org.example.CarMgmt.Billing.Invoices;

import java.util.HashMap;

import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Billing.User;

public class UserRetriever {
	public static HashMap<String, User> users = new HashMap<>();
	public UserRetriever() {
		User cust = new User(1, "Customer");
		cust.setLifetimePoints(10000);
		cust.setPoints(0);
		users.put("1", cust);
	}
	
	public User retrieveById(String id) {
		return users.get(id);
	}
}
