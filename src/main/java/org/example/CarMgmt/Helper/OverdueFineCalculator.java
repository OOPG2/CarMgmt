package org.example.CarMgmt.Helper;

import org.example.CarMgmt.Constants;

public class OverdueFineCalculator {
	private Long overdueDays;
	public double calculateOverdueFine(String startDate, String endDate) {
		Double overdueFinePerDay = new Constants().getOverdueFinePerDay();
		Long days = new DaysInPeriodCalculator().calculateDays(startDate, endDate);
		this.overdueDays = days;
		return overdueFinePerDay * days;
	}
	
	public Long getOverdueDays() {
		return overdueDays;
	}
}
