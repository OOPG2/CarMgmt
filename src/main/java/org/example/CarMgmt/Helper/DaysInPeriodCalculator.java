package org.example.CarMgmt.Helper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DaysInPeriodCalculator {
	public long calculateDays(String startDate, String endDate) {
		LocalDate parsedStartDate = LocalDate.parse(startDate, DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate parsedEndDate = LocalDate.parse(endDate, DateTimeFormatter.BASIC_ISO_DATE);
		return Duration.between(parsedStartDate, parsedEndDate).toDays();
	}
}
