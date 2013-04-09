package uk.ac.aber.ayr9.tourinf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import uk.ac.aber.ayr9.tourinf.R;

/**
 * @author androidhive + Modification from Adam Rigby (ayr9)
 * Class used from androidhive third party code to display Dialog
 * boxes at various stages of the application e.g.
 * when places are not present etc. Modification made to
 * setButton method as old method in code was depracated, new
 * version now used.
 */

public class AlertDialogManager {
	
	private AlertDialog alertDialog;

	/**
	 * Function to display simple Alert Dialog
	 * @param context - application context
	 * @param title - alert dialog title
	 * @param message - alert message
	 * @param status - success/failure (used to set icon)
	 * 				 - pass null if you don't want icon
	 * */
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		if(status != null)
			// Setting alert dialog icon
			alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	public AlertDialog getAlertDialog() {
		return alertDialog;
	}
}
