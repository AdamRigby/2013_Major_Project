package uk.ac.aber.ayr9.tourinf.guitest;

import android.test.ActivityInstrumentationTestCase2;
import uk.ac.aber.ayr9.tourinf.MainActivity;
import android.widget.AbsListView;
import junit.framework.AssertionFailedError;
import com.bitbar.recorder.extensions.ExtSolo;
import android.widget.EditText;

public class MainActivityTest2 extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private ExtSolo solo; // ExtSolo is an extension of Robotium Solo that helps collecting better test execution data during test runs

	public MainActivityTest2() {
		super(MainActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		solo = new ExtSolo(getInstrumentation(), getActivity(), this.getClass()
				.getCanonicalName(), getName());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
		solo.tearDown();
		super.tearDown();
	}

	public void testRecorded() throws Exception {
		try {
			solo.waitForActivity("MainActivity");
			solo.sleep(1500);
			assertTrue(
					"Wait for edit text (id: uk.ac.aber.ayr9.tourinf.R.id.locationText) failed.",
					solo.waitForView(
							solo.findViewById(uk.ac.aber.ayr9.tourinf.R.id.locationText),
							200000, true));
			solo.clearEditText((EditText) solo
					.findViewById(uk.ac.aber.ayr9.tourinf.R.id.locationText));
			solo.enterText((EditText) solo
					.findViewById(uk.ac.aber.ayr9.tourinf.R.id.locationText),
					"aberystwyth");
			solo.sleep(1500);
			assertTrue(
					"Wait for button (id: uk.ac.aber.ayr9.tourinf.R.id.searchButton) failed.",
					solo.waitForView(
							solo.findViewById(uk.ac.aber.ayr9.tourinf.R.id.searchButton),
							200000, true));
			solo.clickOnView(solo
					.findViewById(uk.ac.aber.ayr9.tourinf.R.id.searchButton));
			solo.waitForActivity("MainMenu");
			solo.sleep(1500);
			assertTrue("Wait for list failed.",
					solo.waitForView(AbsListView.class, 1, 200000, true));
			solo.clickInList(1);
			solo.waitForActivity("MainPlacesActivity");
			solo.sleep(60000);
			assertTrue("Wait for list failed.",
					solo.waitForView(AbsListView.class, 1, 200000, true));
			solo.clickInList(2);
			solo.waitForActivity("SinglePlaceActivity");
			solo.sleep(60000);
			solo.drag(solo.toScreenX(0.141f), solo.toScreenX(0.044f),
					solo.toScreenY(0.819f), solo.toScreenY(0.275f), 7);
			solo.sleep(1500);
			solo.drag(solo.toScreenX(0.038f), solo.toScreenX(0.016f),
					solo.toScreenY(0.835f), solo.toScreenY(0.363f), 7);
			solo.sleep(1500);
			assertTrue(
					"Wait for button (id: uk.ac.aber.ayr9.tourinf.R.id.mapButton) failed.",
					solo.waitForView(
							solo.findViewById(uk.ac.aber.ayr9.tourinf.R.id.mapButton),
							200000, true));
			solo.clickOnView(solo
					.findViewById(uk.ac.aber.ayr9.tourinf.R.id.mapButton));
			solo.waitForActivity("MapActivity");
			solo.sleep(1500);
			solo.goBack();
			solo.sleep(1500);
			solo.goBack();
			solo.waitForActivity("MainPlacesActivity");
			solo.sleep(1500);
			assertTrue("Wait for list failed.",
					solo.waitForView(AbsListView.class, 1, 200000, true));
			solo.clickInList(1);
			solo.waitForActivity("SinglePlaceActivity");
			solo.sleep(60000);
			solo.drag(solo.toScreenX(0.119f), solo.toScreenX(0.016f),
					solo.toScreenY(0.777f), solo.toScreenY(0.402f), 5);
			solo.sleep(1500);
			solo.goBack();
			solo.sleep(1500);
			solo.goBack();
			solo.waitForActivity("MainMenu");
			solo.sleep(1300);
			solo.goBack();
			solo.waitForActivity("MainActivity");
		} catch (AssertionFailedError e) {
			solo.fail(
					"uk.ac.aber.ayr9.tourinf.guitest.MainActivityTest2.testRecorded_scr_fail",
					e);
			throw e;
		} catch (Exception e) {
			solo.fail(
					"uk.ac.aber.ayr9.tourinf.guitest.MainActivityTest2.testRecorded_scr_fail",
					e);
			throw e;
		}
	}

}
