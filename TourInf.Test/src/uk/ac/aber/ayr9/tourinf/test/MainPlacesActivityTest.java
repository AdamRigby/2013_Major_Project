package uk.ac.aber.ayr9.tourinf.test;


import uk.ac.aber.ayr9.tourinf.MainPlacesActivity;
import uk.ac.aber.ayr9.tourinf.Place;
import uk.ac.aber.ayr9.tourinf.PlacesList;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

public class MainPlacesActivityTest extends ActivityInstrumentationTestCase2<MainPlacesActivity> {

	private MainPlacesActivity mPlacesActivity;
	
	public MainPlacesActivityTest() {
		super(MainPlacesActivity.class);
		
	}
	
	protected void setUp() throws Exception{
		super.setUp();	
		Intent intent = new Intent();
		intent.putExtra("location_entered", "Aberystwyth");
		intent.putExtra("SearchType", "restaurant");
		this.setActivityIntent(intent);
		mPlacesActivity = this.getActivity();
		
	}

	
	@SmallTest
	public void testIntent() {
		Bundle extras = mPlacesActivity.getIntent().getExtras();
		assertEquals("Aberystwyth", extras.get("location_entered"));
		assertEquals("restaurant", extras.get("SearchType"));
	}
	
	@MediumTest
	public void testList() {
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		PlacesList pList = mPlacesActivity.getNearPlaces();
		assertTrue(pList.results != null);
		boolean correct = false;
		for (Place p:pList.results) {
			if (p.name.equalsIgnoreCase("Treehouse") ){
				correct = true;
			}
		}
		
		assertTrue(correct);
	}
	
}
