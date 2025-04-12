package billing.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DaysInPeriodCalculator {
	public long calculateDays(String startDate, String endDate) {
		LocalDate parsedStartDate = LocalDate.parse(startDate, DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate parsedEndDate = LocalDate.parse(endDate, DateTimeFormatter.BASIC_ISO_DATE);
		return ChronoUnit.DAYS.between(parsedStartDate, parsedEndDate);
	}
}
