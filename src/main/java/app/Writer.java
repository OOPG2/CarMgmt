package app;

import beans.*;

public abstract class Writer {
    abstract public <T extends CsvBeans> void writeToCsv(T bean) throws Exception;
}