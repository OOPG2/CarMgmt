package org.example.CarMgmt;

import org.example.CarMgmt.Beans.CsvBeans;

public abstract class Writer {
    abstract public <T extends CsvBeans> void writeToCsv(T bean) throws Exception;
}