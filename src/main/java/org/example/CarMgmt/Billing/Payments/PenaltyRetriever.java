package org.example.CarMgmt.Billing.Payments;

import java.util.HashMap;

import org.example.CarMgmt.Beans.Invoice;
import org.example.CarMgmt.Beans.Penalty;

public class PenaltyRetriever extends Retriever<Penalty> {	
	public static HashMap<String, Penalty> penalties;
	public PenaltyRetriever() {
		super(Penalty.class);
		penalties = (HashMap<String, Penalty>) init("databases/penalties.csv");
	}
}
