package org.example.CarMgmt.helper;

public class IDGenerator {
    public static String generateUniqueID(String prefix){
        return prefix + (int)(Math.random() * 10000);
    }
}
