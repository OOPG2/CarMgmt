package org.example.CarMgmt.Billing.Payments;

import java.io.FileWriter;
import java.util.List;

import org.example.CarMgmt.Beans.CsvBeans;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public abstract class Writer {
	abstract public <T extends CsvBeans> void writeToCsv(T bean) throws Exception;
}
