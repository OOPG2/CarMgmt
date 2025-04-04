package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvParser {
	public HashMap<String, CsvBeans> hashmap = new HashMap<>();
	
		public <T extends CsvBeans> void csvToHashmap(Class clazz, String path) throws Exception {
			Path filepath = Paths.get(path); // Converts the path string into a Path object
			if (!Files.exists(filepath)){ // Checks if the file already exists
				// If the file doesn't exist, create a copy of the file from resources
				Files.copy(Objects.requireNonNull(getClass().getResourceAsStream("/" + path)),filepath);
			}
			try {
				// Read from the copied file
				InputStreamReader reader = new InputStreamReader(new FileInputStream(path));
		        CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
						.withType(clazz)
						.withIgnoreLeadingWhiteSpace(true)
						.build();
		        List<T> parsed = cb.parse();
		        for(T bean: parsed) {
					hashmap.put(bean.getId(), bean);
				}
		    } catch (NullPointerException e) {
		    	e.printStackTrace();
		    }
		}
}
