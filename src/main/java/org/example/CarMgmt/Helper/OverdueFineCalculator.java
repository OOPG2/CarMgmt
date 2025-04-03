package org.example.CarMgmt.Helper;

import org.example.CarMgmt.Constants;

public class OverdueFineCalculator {
	public double calculateOverdueFine(String startDate, String endDate) {
		Double overdueFinePerDay = new Constants().getOverdueFinePerDay();
		// minus 1 as invoice is overdue only after 23:59:59 on due date
		Long days = new DaysInPeriodCalculator().calculateDays(startDate, endDate) - 1;
		return overdueFinePerDay * days;
	}
}
