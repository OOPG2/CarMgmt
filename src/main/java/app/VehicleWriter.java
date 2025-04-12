package app;

import beans.*;
import constants.*;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.File;
import java.io.FileWriter;
import java.util.Collections;

public class VehicleWriter extends Writer{
    @Override
    public <T extends CsvBeans> void writeToCsv(T bean) throws Exception {
        Constants constants = new Constants();
        String filePath = constants.getCsvBasePath() + "databases/vehicles.csv";
        File file = new File(filePath);
        boolean fileExists = file.exists();
        try (java.io.Writer writer = new FileWriter(filePath, true)) {
            StatefulBeanToCsv<Vehicle> beanToCsv = new StatefulBeanToCsvBuilder<Vehicle>(writer)
                    .withApplyQuotesToAll(false)
                    .withOrderedResults(true)
                    .withQuotechar('\"')
                    .withSeparator(',')
                    .build();

            beanToCsv.write((Vehicle) bean);
        }
    }
}
