package org.example.CarMgmt.CsvParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.example.CarMgmt.Beans.CsvBeans;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvParser {
		public <T extends CsvBeans> HashMap<String, CsvBeans> csvToHashmap(Class<T> clazz, String path) throws Exception {
			try {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
				InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
		        CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
		        		.withIgnoreLeadingWhiteSpace(true)
		        		.withType(clazz)
		        		.build();
		        List<T> parsed = cb.parse();
		        HashMap<String, CsvBeans> hashmap = new HashMap<>();
		        for(T bean: parsed) {
					hashmap.put(bean.getId(), bean);
				}
		        return hashmap;
		    } catch (NullPointerException e) {
		    	e.printStackTrace();
		    }
			return null;
		}
}
