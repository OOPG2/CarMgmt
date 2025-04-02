package org.example.CarMgmt.Billing.Payments;

import java.util.HashMap;
import java.util.Map;

import org.example.CarMgmt.Beans.CsvBeans;
import org.example.CarMgmt.Beans.Penalty;
import org.example.CarMgmt.CsvParser.CsvParser;

public class PenaltyRetriever {
	HashMap<String, CsvBeans> hashmap;
	
	public PenaltyRetriever() {
		init();
	}
	
	public void init() {
		try {
			CsvParser csvParser = new CsvParser();
			hashmap = csvParser.csvToHashmap(Penalty.class, "databases/penalties.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Penalty retrieveReservationById(String id) {
		return (Penalty)hashmap.get(id);
	}
}
