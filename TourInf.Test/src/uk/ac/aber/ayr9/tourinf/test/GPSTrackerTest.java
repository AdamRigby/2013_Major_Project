package uk.ac.aber.ayr9.tourinf.test;


import uk.ac.aber.ayr9.tourinf.GPSTracker;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
//import android.os.IBinder;
import android.test.ServiceTestCase;
//import android.test.mock.MockContext;
//import android.test.suitebuilder.annotation.MediumTest;
//import android.test.suitebuilder.annotation.SmallTest;


public class GPSTrackerTest extends ServiceTestCase<GPSTracker> {

	GPSTracker gTracker;
	//private Context mContext;
	private Context mSystemContext;

	public GPSTrackerTest() {
		super(GPSTracker.class);
		//setContext(mContext);
	}
	
	//public GPSTrackerTest(Class<GPSTracker> serviceClass) {
		//super(serviceClass);
		//setContext(mContext);
	//}
	
	public void testPreconditions() {
	      
	    }
	
	protected void setUp() throws Exception {
		super.setUp();	
		//setContext(mContext);
		mSystemContext = getContext();
		
		//Intent bindIntent = new Intent("com.androidhive.googleplacesandmaps.GPSTracker");
        //IBinder binder = bindService(bindIntent);
        //assertNotNull(binder);
		
	}
	
	@Override
	public void testServiceTestCaseSetUpProperly() {
		
	}

	
	public void testGPS() 
	{
		gTracker = this.getService();
		gTracker = new GPSTracker(mSystemContext);
		final Location loc = gTracker.getLocation();
		final Location expected =  ((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE)).getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		assertEquals(loc.getLatitude(), expected.getLatitude());
		assertEquals(loc.getLongitude(), expected.getLongitude());
		}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
}

