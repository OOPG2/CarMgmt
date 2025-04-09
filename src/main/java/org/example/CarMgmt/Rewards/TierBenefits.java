package org.example.CarMgmt.Rewards;

import java.util.ArrayList;
import java.util.List;

public class TierBenefits {
	private Integer lifetimePoints;
	private String tier = "CLASSIC";
	private List<String> benefits = new ArrayList<>();
	private boolean prioritySupport = false;
	private boolean waivedCancellationFees = false;
	private boolean roadsideAssistance = false;
	private boolean freeInsurance = false;
	private boolean priorityReservation = false;
	
	public TierBenefits(Integer lifetimePoints) {
		this.lifetimePoints = lifetimePoints;
		init();
	}
	
	private void init() {
		if (lifetimePoints >= 0) {
			tier = "CLASSIC";
			roadsideAssistance = true;
			benefits.add("24/7 Roadside Assistance");
		} 
		if (lifetimePoints >= 2000) {
			tier = "SILVER";
			prioritySupport = true;
			benefits.add("Priority Support");
		} 
		if (lifetimePoints >= 4000) {
			tier = "GOLD";
			priorityReservation = true;
			benefits.add("Priority Reservation");
		}
		if (lifetimePoints >= 8000) {
			tier = "PLATINUM";
			waivedCancellationFees = true;
			freeInsurance = true;
			benefits.add("Waived Cancellation Fees");
			benefits.add("Free Insurance");
		}
	}
	
	public String getTier() {
		return tier;
	}
	
	public boolean getPrioritySupport() {
		return prioritySupport;
	}
	
	public boolean waivedCancellationFees() {
		return waivedCancellationFees;
	}
	
	public boolean roadsideAssistance() {
		return roadsideAssistance;
	}
	
	public boolean freeInsurance() {
		return freeInsurance;
	}
	
	public boolean priorityReservation() {
		return priorityReservation;
	}
	
	public List<String> getBenefits() {
		return benefits;
	}
}
