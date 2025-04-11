package constants;

public class Constants {
	private static int daysToDueDate = 7;
	private static double overdueFinePerday = 20;
	private static double gstRate = 0.09;
	private static String UEN = "200604393R";
	private static String bankRecipient = "OOP Rentals";
	private static String bankName = "OOP Bank";
	private static String bankAccountNo = "123-456-7890";
	private static String csvBasePath = "/Users/axel/eclipse-workspace/IntegratedCarMgmt/src/main/resources/";
	private static String defaultPassword = "password";
	
	public static int getDaysToDueDate() {
		return daysToDueDate;
	}
	
	public static double getOverdueFinePerDay() {
		return overdueFinePerday;
	}
	
	public static double getGSTRate() {
		return gstRate;
	}
	
	public static String getUEN() {
		return UEN;
	}
	
	public static String getBankRecipient() {
		return bankRecipient;
	}
	
	public static String getBankName() {
		return bankName;
	}
	
	public static String getBankAccountNo() {
		return bankAccountNo;
	}
	
	public static String getCsvBasePath() {
		return csvBasePath;
	}
	
	public static String getDefaultPassword() {
		return defaultPassword;
	}
}
