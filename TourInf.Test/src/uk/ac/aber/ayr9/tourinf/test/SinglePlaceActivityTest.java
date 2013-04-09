package uk.ac.aber.ayr9.tourinf.test;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import uk.ac.aber.ayr9.tourinf.PlaceDetails;
import uk.ac.aber.ayr9.tourinf.SinglePlaceActivity;

public class SinglePlaceActivityTest extends ActivityInstrumentationTestCase2<SinglePlaceActivity> {

	private SinglePlaceActivity sPlaceActivity;
	private String testReference;
	
	public SinglePlaceActivityTest() {
		super(SinglePlaceActivity.class);
	}
	
	protected void setUp() throws Exception{
		super.setUp();	
		Intent intent = new Intent();
		testReference = "CoQBegAAADca9UMF-Zc1vZl5gKd8FMUAMjgvEB59jpPbKkb66abEjo2W4mkDrANb8poQNN2ZulGZ3sx9avos4uwYDMFltQJyhD_Bcl1gHcoihbOKuyGzbqYnlf5n4O8pkigomskfLFbhs-MqPUJrTtguRdNAeGK_pJE-s7zxqxpOtP1leiHbEhBZkgV7e9-MPm5PU1_m-RZiGhRT-Kmuf7_vZE0_JK65q9pNn4vfmQ";
		intent.putExtra("reference", testReference);
		this.setActivityIntent(intent);
		sPlaceActivity = this.getActivity();
		
	}

	
	@SmallTest
	public void testIntent() {
		Bundle extras = sPlaceActivity.getIntent().getExtras();
		assertEquals(testReference, extras.get(SinglePlaceActivity.KEY_REFERENCE));
	}
	
	@MediumTest
	public void testDetails() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		PlaceDetails placeDetails = sPlaceActivity.getPlaceDetails();
		assertEquals("OK", placeDetails.status);
		assertEquals("W M Morrison Supermarkets Plc", placeDetails.result.name);
		assertEquals("Great Darkgate St, Llanbadarn Fawr, Aberystwyth, Aberystwyth, United Kingdom", placeDetails.result.formatted_address);
		assertEquals("01970 626892", placeDetails.result.formatted_phone_number);
		
		
	}

}
