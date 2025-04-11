package exceptions;

import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

import app.App;

public class DatabaseErrorHandler {
	public static void showDatabaseErrorDialog() {
		new MessageDialogBuilder()
		.setTitle("Database Error")
		.setText("There's a problem with the database.\nThis app will not work until the issue is resolved.")
		.build()
		.showDialog(App.gui);
	}
}
