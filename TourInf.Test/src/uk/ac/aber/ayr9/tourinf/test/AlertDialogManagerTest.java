package uk.ac.aber.ayr9.tourinf.test;

import uk.ac.aber.ayr9.tourinf.AlertDialogManager;
import uk.ac.aber.ayr9.tourinf.MainActivity;
import android.app.AlertDialog;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

public class AlertDialogManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {

	
	private AlertDialogManager aDialogManager;
	private MainActivity mActivity;
	
	
	public AlertDialogManagerTest() {
		super(MainActivity.class);	
	}
	
	protected void setUp() throws Exception{
		super.setUp();
		mActivity = this.getActivity();
		aDialogManager = new AlertDialogManager();
		
		
	}
	
	public void testDialog() 
	{
		aDialogManager.showAlertDialog(mActivity, "Test Title", "Test Message", true);
		AlertDialog aDialog = aDialogManager.getAlertDialog();
		assertEquals("Test Title", aDialog.getWindow().getAttributes().getTitle());
		assertEquals("Test Message", ((TextView) aDialog.getWindow().findViewById(android.R.id.message)).getText());
		assertTrue(aDialog.isShowing());
	}
	
	
	
	
}
