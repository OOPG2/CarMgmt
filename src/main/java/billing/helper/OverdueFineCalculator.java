package billing.helper;

import constants.*;

public class OverdueFineCalculator {
	private Long overdueDays;
	public double calculateOverdueFine(String startDate, String endDate) {
		Double overdueFinePerDay = Constants.getOverdueFinePerDay();
		Long days = new DaysInPeriodCalculator().calculateDays(startDate, endDate);
		this.overdueDays = days;
		return overdueFinePerDay * days;
	}
	
	public Long getOverdueDays() {
		return overdueDays;
	}
}
