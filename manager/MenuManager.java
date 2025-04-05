package org.example.CarMgmt.manager;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;

import static org.example.CarMgmt.menus.postLogin.Admin.CustomerManagementMenu.showCustomerManagementMenu;
import static org.example.CarMgmt.menus.postLogin.Admin.StaffManagementMenu.showStaffManagementMenu;
import static org.example.CarMgmt.menus.postLogin.ProfileMenu.showProfileMenu;

public class MenuManager {
    private static String cameFrom = null; // Mainly for pages of depth 1.

    public static void redirect(MultiWindowTextGUI gui) {
        String page = getCameFrom();
        setCameFrom(null);
        switch (page) {
            case "CustomerManagement" -> showCustomerManagementMenu(gui);
            case "StaffManagement" -> showStaffManagementMenu(gui);
            case "Profile" -> showProfileMenu(gui, null);
        }
    }

    public static String getCameFrom() {
        return cameFrom;
    }

    public static void setCameFrom(String cameFrom) {
        MenuManager.cameFrom = cameFrom;
    }
}
