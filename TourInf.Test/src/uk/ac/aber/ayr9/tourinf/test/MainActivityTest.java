package uk.ac.aber.ayr9.tourinf.test;



import uk.ac.aber.ayr9.tourinf.MainActivity;
import android.test.ActivityInstrumentationTestCase2;




public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private MainActivity mActivity;
	
	
	public MainActivityTest() {
		super(MainActivity.class);	
	}
	
	protected void setUp() throws Exception{
		super.setUp();	
		mActivity = this.getActivity();
	}
	
	public void testTitleText() 
	{
		assertEquals("TourInf",(String)mActivity.getText(uk.ac.aber.ayr9.tourinf.R.string.mainTitle));
	}
	
	public void testButtonText() 
	{
		assertEquals("Use Current Location",(String)mActivity.getText(uk.ac.aber.ayr9.tourinf.R.string.startButton));
		assertEquals("Go!",(String)mActivity.getText(uk.ac.aber.ayr9.tourinf.R.string.searchButton));
	}
}
