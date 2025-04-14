/**
 * The DatabaseErrorHandler class provides a method to display an error dialog
 * when there is a problem with the database. It utilizes Lanterna's MessageDialogBuilder
 * for creating and showing the dialog.
 */
package exceptions;

import app.App;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

/**
 * DatabaseErrorHandler handles the display of database error dialogs in the application.
 */
public class DatabaseErrorHandler {
	/**
	 * Displays an error dialog indicating a problem with the database.
	 * The dialog informs users that the application will not function until the issue is resolved.
	 */
	public static void showDatabaseErrorDialog() {
		new MessageDialogBuilder()
				.setTitle("Database Error")
				.setText("There's a problem with the database.\nThis app will not work until the issue is resolved.")
				.build()
				.showDialog(App.gui);
	}
}
