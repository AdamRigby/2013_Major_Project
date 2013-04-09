package uk.ac.aber.ayr9.tourinf.test;

import uk.ac.aber.ayr9.tourinf.MainMenu;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;

public class MainMenuTest extends ActivityInstrumentationTestCase2<MainMenu> {
	
	private MainMenu mMenu;

	public MainMenuTest() {
		super(MainMenu.class);
		
	}
	
	protected void setUp() throws Exception{
		super.setUp();	
		Intent intent = new Intent();
		intent.putExtra("location_entered", "Aberystwyth");
		this.setActivityIntent(intent);
		mMenu = this.getActivity();
		
		
	}
	
	@SmallTest
	public void testIntent() {
		Bundle extras = mMenu.getIntent().getExtras();
		assertEquals("Aberystwyth", extras.get("location_entered"));
	}
	

}
