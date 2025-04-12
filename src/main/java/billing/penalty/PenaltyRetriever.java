package billing.penalty;

import app.Retriever;
import beans.Penalty;

import java.util.Collections;
import java.util.HashMap;


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
