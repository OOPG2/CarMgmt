package org.example.CarMgmt.Helper;

public class TotalRentalCalculator {
	public Double calculateRental(String dailyRental) {
		String[] dailyRentalArray = dailyRental.split(",");
		double totalRental = 0;
		for (String rental: dailyRentalArray) {
			totalRental += Double.parseDouble(rental);
		}
		return totalRental;
	}
}
