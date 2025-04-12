package csvParser;

import beans.*;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvParser {
	// Reads the CSV from an external file using the provided file path.
	public <T extends CsvBeans> HashMap<String, CsvBeans> csvToHashmap(Class<T> clazz, String filePath) throws Exception {
		try (Reader reader = new FileReader(filePath)) {
			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
					.withIgnoreLeadingWhiteSpace(true)
					.withType(clazz)
					.build();
			List<T> beans = csvToBean.parse();
			HashMap<String, CsvBeans> hashmap = new HashMap<>();
			for (T bean : beans) {
				hashmap.put(bean.getId(), bean);
			}
			return hashmap;
		}
	}
}
