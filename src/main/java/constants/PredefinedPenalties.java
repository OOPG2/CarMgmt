package constants;

import java.util.HashMap;

public class PredefinedPenalties {
	public static HashMap<String, Double> penalties = new HashMap<>();
	public HashMap<String, Double> getPredefinedPenalties() {
		penalties.put("Admin Fee for Traffic/Parking Violations", 100.00);
		penalties.put("Crossed border without authorisation", 300.00);
		penalties.put("Late Return", 100.00);
		penalties.put("Lost Key/Documents", 250.00);
		penalties.put("Smoking in the Vehicle", 200.00);
		penalties.put("Unauthorised Driver", 150.00);
		penalties.put("Damage, Minor", 300.00);
		penalties.put("Damage, Moderate", 600.00);
		penalties.put("Damage, Significant", 1200.00);
		return penalties;
	}
}
