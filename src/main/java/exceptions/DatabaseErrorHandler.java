package exceptions;

import app.App;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;

public class DatabaseErrorHandler {
	public static void showDatabaseErrorDialog() {
		new MessageDialogBuilder()
		.setTitle("Database Error")
		.setText("There's a problem with the database.\nThis app will not work until the issue is resolved.")
		.build()
		.showDialog(App.gui);
	}
}
