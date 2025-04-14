/**
 * The MenuManager class handles navigation between menus in the system.
 * It provides functionality for redirecting users to specific menus based on the current page.
 */
package manager;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;

import static menus.ProfileMenu.showProfileMenu;
import static menus.postLogin.Admin.CustomerManagementMenu.showCustomerManagementMenu;
import static menus.postLogin.Admin.StaffManagementMenu.showStaffManagementMenu;

/**
 * MenuManager facilitates menu transitions and tracks the originating menu.
 */
public class MenuManager {
    /**
     * A string indicating the originating page. Used for navigation purposes.
     */
    private static String cameFrom = null; // Mainly for pages of depth 1.

    /**
     * Redirects to the appropriate menu based on the current page.
     *
     * @param gui the MultiWindowTextGUI object used for displaying the menus
     */
    public static void redirect(MultiWindowTextGUI gui) {
        String page = getCameFrom();
        setCameFrom(null);
        switch (page) {
            case "CustomerManagement":
                showCustomerManagementMenu(gui);
                break;
            case "StaffManagement":
                showStaffManagementMenu(gui);
                break;
            case "Profile":
                showProfileMenu(gui, null);
                break;
            default:
                System.out.println("Unknown page: " + page);
                break;
        }
    }

    /**
     * Returns the originating page.
     *
     * @return a string representing the originating page
     */
    public static String getCameFrom() {
        return cameFrom;
    }

    /**
     * Sets the originating page.
     *
     * @param cameFrom the originating page to set
     */
    public static void setCameFrom(String cameFrom) {
        MenuManager.cameFrom = cameFrom;
    }
}
