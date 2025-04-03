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
	
	public void init(String path) {
		try {
			System.out.println(clazz);
			System.out.println(path);
			CsvParser csvParser = new CsvParser();
			hashmap = csvParser.csvToHashmap(clazz, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public T retrieveById(String id) {
		return (T)hashmap.get(id);
	}
}
