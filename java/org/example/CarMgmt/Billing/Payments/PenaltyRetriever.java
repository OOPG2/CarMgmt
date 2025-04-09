package org.example.CarMgmt.Billing.Payments;

import java.util.Collections;
import java.util.HashMap;
import org.example.CarMgmt.Beans.Penalty;

public class PenaltyRetriever extends Retriever<Penalty> {	
	public static HashMap<String, Penalty> penalties;
	public static String currentLastRowId = "0";
	public PenaltyRetriever() {
		super(Penalty.class);
		penalties = (HashMap<String, Penalty>) init("databases/penalties.csv");
		if (penalties.size() > 0) {
			currentLastRowId = Collections.max(penalties.keySet());
		}
	}
}
