package app;

import beans.CsvBeans;
import constants.Constants;
import csvParser.CsvParser;
import exceptions.RowNotFoundException;

import java.util.HashMap;


public class Retriever<T extends CsvBeans> {
	HashMap<String, CsvBeans> hashmap;
	private Class<? extends CsvBeans> clazz;
	
	public Retriever(Class<? extends CsvBeans> clazz) {
		this.clazz = clazz;
	}
	
	public HashMap<String, ? extends CsvBeans> init(String path) {
		Constants constants = new Constants();
		try {
			CsvParser csvParser = new CsvParser();
			hashmap = csvParser.csvToHashmap(clazz, constants.getCsvBasePath() + path);
			return hashmap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public T retrieveById(String id) throws RowNotFoundException {
		if (hashmap.containsKey(id)) {
			return (T)hashmap.get(id);
		} else {
			throw new RowNotFoundException("Row not found");
		}
	}
}
