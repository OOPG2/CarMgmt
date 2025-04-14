/**
 * The CsvParser class provides functionality for reading CSV files and converting them
 * into HashMap representations, where each entry is keyed by a unique ID.
 * It leverages OpenCSV for parsing CSV data into Java objects.
 */
package csvParser;

import beans.CsvBeans;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

/**
 * CsvParser reads CSV files and maps their content into Java objects of specified types.
 */
public class CsvParser {
	/**
	 * Reads the CSV from an external file and converts it into a HashMap.
	 * Each entry in the HashMap is keyed by the unique ID of the corresponding bean object.
	 *
	 * @param <T>      the type of the beans, which must extend CsvBeans
	 * @param clazz    the class of the beans to create from the CSV data
	 * @param filePath the path to the CSV file to read
	 * @return a HashMap where keys are bean IDs and values are bean objects
	 * @throws Exception if an error occurs while reading the file or parsing the CSV data
	 */
	public <T extends CsvBeans> HashMap<String, CsvBeans> csvToHashmap(Class<T> clazz, String filePath) throws Exception {
		try (Reader reader = new FileReader(filePath)) {
			// Configure the CsvToBeanBuilder to parse the CSV data into Java objects
			CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
					.withIgnoreLeadingWhiteSpace(true)
					.withType(clazz)
					.build();

			// Parse the CSV data into a list of bean objects
			List<T> beans = csvToBean.parse();

			// Convert the list of beans into a HashMap
			HashMap<String, CsvBeans> hashmap = new HashMap<>();
			for (T bean : beans) {
				hashmap.put(bean.getId(), bean);
			}

			return hashmap;
		}
	}
}
