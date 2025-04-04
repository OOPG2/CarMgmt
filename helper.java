package org.example.CarMgmt;

import com.googlecode.lanterna.gui2.*;

import java.io.IOException;

public class helper {
    public static void flash(MultiWindowTextGUI gui, String message, int milliseconds) throws IOException, InterruptedException {
        BasicWindow completeWindow = new BasicWindow();
        Panel completePanel = new Panel();
        completePanel.setLayoutManager(new GridLayout(1));
        completePanel.addComponent(new Label(message));
        completeWindow.setComponent(completePanel);
        gui.addWindow(completeWindow);
        gui.updateScreen();
        Thread.sleep(milliseconds);

        completeWindow.close();
    }

//    public static class CsvParser {
//	    public static HashMap<String, CsvBeans> hashmap = new HashMap<>();
//
//		public <T extends CsvBeans> void csvToHashmap(Class clazz, String path) throws Exception {
//			try {
//				InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
//				InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(inputStream));
//		        CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
//		        		.withIgnoreLeadingWhiteSpace(true)
//		        		.withType(clazz)
//		        		.build();
//		        List<T> parsed = cb.parse();
//		        for(T bean: parsed) {
//					hashmap.put(bean.getId(), bean);
//				}
//		    } catch (NullPointerException e) {
//		    	e.printStackTrace();
//		    }
//		}
}
