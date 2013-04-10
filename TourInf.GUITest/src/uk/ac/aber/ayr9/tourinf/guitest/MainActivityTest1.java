package uk.ac.aber.ayr9.tourinf.guitest;

import android.test.ActivityInstrumentationTestCase2;
import uk.ac.aber.ayr9.tourinf.MainActivity;
import android.widget.AbsListView;
import junit.framework.AssertionFailedError;
import com.bitbar.recorder.extensions.ExtSolo;
import android.widget.EditText;
import java.util.regex.Pattern;

public class MainActivityTest1 extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private ExtSolo solo; // ExtSolo is an extension of Robotium Solo that helps collecting better test execution data during test runs

	public MainActivityTest1() {
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

	private void assertsGroup_1() throws Exception {
		assertTrue(solo
				.searchText(Pattern
						.quote("40-46 North Parade, Aberystwyth, Ceredigion, United Kingdom")));
		assertTrue(solo
				.searchText(Pattern.quote("Harry's Hotel, Bistro & Bar")));
		assertTrue(solo.searchText(Pattern
				.quote("Latitude: 52.415894, Longitude: -4.081557")));
		assertTrue(solo.searchText(Pattern.quote("Name:")));
		assertTrue(solo.searchText(Pattern.quote("Phone: 01970 639007")));
		assertTrue(solo.searchText(Pattern.quote("Place Details")));
		assertTrue(solo.searchText(Pattern.quote("Rating: ")));
		assertTrue(solo.searchText(Pattern.quote("Address:")));
		assertTrue(solo.searchText(Pattern.quote("Call")));
		assertTrue(solo.searchText(Pattern.quote("Get Directions")));
		assertTrue(solo.searchText(Pattern.quote("Go to Website")));
		assertTrue(solo.searchText(Pattern.quote("Show on Map")));
	}

	private void assertsGroup_2() throws Exception {
		assertTrue(solo.searchText(Pattern.quote("Address:")));
		assertTrue(solo.searchText(Pattern
				.quote("Latitude: 52.416132, Longitude: -4.084001")));
		assertTrue(solo.searchText(Pattern.quote("Name:")));
		assertTrue(solo.searchText(Pattern.quote("The Varsity")));
		assertTrue(solo.searchText(Pattern.quote("Phone: 01970 615234")));
		assertTrue(solo.searchText(Pattern.quote("Place Details")));
		assertTrue(solo.searchText(Pattern
				.quote("Portland Street, Aberystwyth, United Kingdom")));
		assertTrue(solo.searchText(Pattern.quote("Rating: ")));
		assertTrue(solo.searchText(Pattern.quote("Show on Map")));
		assertTrue(solo.searchText(Pattern.quote("Call")));
		assertTrue(solo.searchText(Pattern.quote("Get Directions")));
		assertTrue(solo.searchText(Pattern.quote("Go to Website")));
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
			solo.sleep(1400);
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
			solo.clickInList(3);
			solo.waitForActivity("MainPlacesActivity");
			solo.sleep(60000);
			assertTrue("Wait for list failed.",
					solo.waitForView(AbsListView.class, 1, 200000, true));
			solo.clickInList(2);
			solo.waitForActivity("SinglePlaceActivity");
			solo.sleep(60000);
			assertsGroup_1();
			solo.drag(solo.toScreenX(0.081f), solo.toScreenX(0.231f),
					solo.toScreenY(0.429f), solo.toScreenY(0.031f), 6);
			solo.sleep(1500);
			solo.takeScreenshot("uk.ac.aber.ayr9.tourinf.guitest.MainActivityTest1.testRecorded_scr_1");
			solo.goBack();
			solo.waitForActivity("MainPlacesActivity");
			solo.sleep(1500);
			assertTrue("Wait for list failed.",
					solo.waitForView(AbsListView.class, 1, 200000, true));
			solo.clickInList(3);
			solo.waitForActivity("SinglePlaceActivity");
			solo.sleep(60000);
			assertsGroup_2();
			solo.drag(solo.toScreenX(0.106f), solo.toScreenX(0.166f),
					solo.toScreenY(0.371f), solo.toScreenY(0.165f), 5);
			solo.sleep(1500);
			solo.takeScreenshot("uk.ac.aber.ayr9.tourinf.guitest.MainActivityTest1.testRecorded_scr_2");
			solo.goBack();
			solo.waitForActivity("MainPlacesActivity");
			solo.sleep(900);
			solo.goBack();
			solo.waitForActivity("MainMenu");
			solo.sleep(900);
			solo.goBack();
			solo.waitForActivity("MainActivity");
		} catch (AssertionFailedError e) {
			solo.fail(
					"uk.ac.aber.ayr9.tourinf.guitest.MainActivityTest1.testRecorded_scr_fail",
					e);
			throw e;
		} catch (Exception e) {
			solo.fail(
					"uk.ac.aber.ayr9.tourinf.guitest.MainActivityTest1.testRecorded_scr_fail",
					e);
			throw e;
		}
	}

}
