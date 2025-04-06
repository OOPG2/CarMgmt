package org.example.CarMgmt.Billing.Payments;

import java.util.HashMap;

import org.example.CarMgmt.Beans.CsvBeans;
import org.example.CarMgmt.CsvParser.CsvParser;

public class Retriever<T extends CsvBeans> {
	HashMap<String, CsvBeans> hashmap;
	private Class<? extends CsvBeans> clazz;
	
	public Retriever(Class<? extends CsvBeans> clazz) {
		this.clazz = clazz;
	}
	
	public HashMap<String, ? extends CsvBeans> init(String path) {
		try {
			CsvParser csvParser = new CsvParser();
			hashmap = csvParser.csvToHashmap(clazz, path);
			return hashmap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public T retrieveById(String id) {
		return (T)hashmap.get(id);
	}
}
