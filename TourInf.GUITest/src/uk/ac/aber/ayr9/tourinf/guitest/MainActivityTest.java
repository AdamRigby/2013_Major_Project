package uk.ac.aber.ayr9.tourinf.guitest;

import android.test.ActivityInstrumentationTestCase2;
import uk.ac.aber.ayr9.tourinf.MainActivity;
import android.widget.AbsListView;
import junit.framework.AssertionFailedError;
import com.bitbar.recorder.extensions.ExtSolo;

public class MainActivityTest
		extends
		ActivityInstrumentationTestCase2<uk.ac.aber.ayr9.tourinf.MainActivity> {

	private ExtSolo solo; // ExtSolo is an extension of Robotium Solo that helps collecting better test execution data during test runs

	public MainActivityTest() {
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
			solo.sleep(2000);
			solo.setGPSMockLocation(52.4173, -4.0788, 0);
			assertTrue(
					"Wait for button (id: uk.ac.aber.ayr9.tourinf.R.id.startButton) failed.",
					solo.waitForView(
							solo.findViewById(uk.ac.aber.ayr9.tourinf.R.id.startButton),
							200000, true));
			solo.clickOnView(solo
					.findViewById(uk.ac.aber.ayr9.tourinf.R.id.startButton));
			solo.setGPSMockLocation(52.4173, -4.0788, 0);
			solo.waitForActivity("MainMenu");
			solo.setGPSMockLocation(52.4173, -4.0788, 0);
			solo.sleep(2000);
			assertTrue("Wait for list failed.",
					solo.waitForView(AbsListView.class, 1, 200000, true));
			solo.clickInList(1);
			solo.setGPSMockLocation(52.4173, -4.0788, 0);
			solo.waitForActivity("MainPlacesActivity");
			solo.setGPSMockLocation(52.4173, -4.0788, 0);
			solo.sleep(60000);
			solo.setGPSMockLocation(52.4173, -4.0788, 0);
			assertTrue("Wait for list failed.",
					solo.waitForView(AbsListView.class, 1, 200000, true));
			solo.clickInList(1);
			solo.waitForActivity("SinglePlaceActivity");
			solo.sleep(60000);
			assertTrue(
					"Wait for button (id: uk.ac.aber.ayr9.tourinf.R.id.mapButton) failed.",
					solo.waitForView(
							solo.findViewById(uk.ac.aber.ayr9.tourinf.R.id.mapButton),
							200000, true));
			solo.clickOnView(solo
					.findViewById(uk.ac.aber.ayr9.tourinf.R.id.mapButton));
			solo.waitForActivity("MapActivity");
			solo.sleep(2000);
			solo.goBack();
			solo.sleep(1800);
			solo.goBack();
			solo.waitForActivity("MainMenu");
			solo.sleep(1400);
			solo.goBack();
			solo.waitForActivity("MainActivity");
		} catch (AssertionFailedError e) {
			solo.fail(
					"uk.ac.aber.ayr9.tourinf.test.MainActivityTest.testRecorded_scr_fail",
					e);
			throw e;
		} catch (Exception e) {
			solo.fail(
					"uk.ac.aber.ayr9.tourinf.test.MainActivityTest.testRecorded_scr_fail",
					e);
			throw e;
		}
	}

}
