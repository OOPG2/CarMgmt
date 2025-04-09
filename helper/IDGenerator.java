package org.example.CarMgmt.helper;

import java.util.Map;

public class IDGenerator {
    public static String generateUniqueID(String prefix){
        return prefix + (int)(Math.random() * 10000);
    }
}
