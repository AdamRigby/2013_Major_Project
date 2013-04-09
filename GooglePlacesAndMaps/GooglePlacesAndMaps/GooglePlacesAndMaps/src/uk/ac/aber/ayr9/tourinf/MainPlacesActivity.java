package uk.ac.aber.ayr9.tourinf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import uk.ac.aber.ayr9.tourinf.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * @author Androidhive + Modifications from Adam Rigby (ayr9)
 * This Activity is responsible for calling the GooglePlaces class
 * receiving its results and displaying them. The doInBackground method
 * in LoadPlaces class has been modified to take a type of place as a 
 * parameter. A Geocoder has also been implemented to take in any address'
 * entered manually and convert them into a lat/long value for Places search
 * and mapping.   
 */


public class MainPlacesActivity extends Activity {

	// Flag for Internet connection status
	Boolean isInternetPresent = false;

	ConnectionDetector cd;
	
	AlertDialogManager alert = new AlertDialogManager();

	GooglePlaces googlePlaces;

	// Places List
	PlacesList nearPlaces;

	GPSTracker gps;

	// Button to launch map of all places
	Button btnShowOnMap;

	// Progress dialog animation for when application is loading
	ProgressDialog pDialog;
	
	// Places Listview
	ListView lv;
	
	// ListItems data
	ArrayList<HashMap<String, String>> placesListItems = new ArrayList<HashMap<String,String>>();
	
	
	// KEY Strings
	public static String KEY_REFERENCE = "reference"; // id of the place
	public static String KEY_NAME = "name"; // name of the place
	public static String KEY_VICINITY = "vicinity"; // Place area name

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_places);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			// Internet Connection is not present
			alert.showAlertDialog(MainPlacesActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// creating GPS Class object
		gps = new GPSTracker(this);

		// check if GPS location can get
		if (gps.canGetLocation()) {
			Log.d("Your Location", "latitude:" + gps.getLatitude() + ", longitude: " + gps.getLongitude());
		} else {
			// Can't get user's current location
			alert.showAlertDialog(MainPlacesActivity.this, "GPS Status",
					"Couldn't get location information. Please enable GPS",
					false);
			// Stop executing code by return
			return;
		}

		// Getting listview
		lv = (ListView) findViewById(R.id.list);
		
		// button show on map
		btnShowOnMap = (Button) findViewById(R.id.btn_show_map);
		
		/**Call background Async task to load Google Places
		After getting places from Google all the data is shown in listview**/
		new LoadPlaces().execute();

		// Button click event for shown on map
		btnShowOnMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						AllPlacesMapActivity.class);
				// Send user's current lat/long location info
				i.putExtra("user_latitude", Double.toString(gps.getLatitude()));
				i.putExtra("user_longitude", Double.toString(gps.getLongitude()));
				
				// Pass near places to map activity
				i.putExtra("near_places", nearPlaces);
				// Starying activity
				startActivity(i);
			}
		});
			
		// ListItem click event. On selecting a listitem SinglePlaceActivity is launched
		lv.setOnItemClickListener(new OnItemClickListener() {
 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	// Get value from selected ListItem
                String reference = ((TextView) view.findViewById(R.id.reference)).getText().toString();
                
                // Start new intent
                Intent in = new Intent(getApplicationContext(),
                        SinglePlaceActivity.class);
                
                /** Send place refrence id to SinglePlaceActivity.
                Place refrence used to get the places full details **/
                in.putExtra(KEY_REFERENCE, reference);
                startActivity(in);
            }
        });
	}

	 public PlacesList getNearPlaces() {
		return nearPlaces;
	}

	//Background AsyncTask to load Google Places
	class LoadPlaces extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
	
					pDialog = new ProgressDialog(MainPlacesActivity.this);
					pDialog.setMessage(Html.fromHtml("<b>Search</b><br/>Loading Places..."));
					pDialog.setIndeterminate(false);
					pDialog.setCancelable(false);
					pDialog.show();
				}
			});
			
		}

		/**
		 * Get Places JSON
		 * */
		protected String doInBackground(String... args) {
			// create Places class object
			googlePlaces = new GooglePlaces();
			
			try {
				
				Bundle extras = getIntent().getExtras();
				String types = extras.getString("SearchType"); // get Place type from MainMenu (GridView Layout)
				
				// Radius around location entered in which places will be found
				double radius = 5000; // 5Km 
				
				/**Get nearest places by calling search method of googlePlaces class.
				 Use Geocoder values if location entered manually otherwise use GPS location*/
				if (extras.containsKey("location_entered")) {
					// Get manually entered location if given
                	String location = extras.getString("location_entered");
                	
                	/**Create a Geocoder to convert manually entered location
                	 into a lat/long for Places search and mapping*/
                	Geocoder geoCoder = new Geocoder(getApplicationContext());
					List<Address> addressList = geoCoder.getFromLocationName(location, 1);
                	Address address = addressList.get(0);
                	double selectedLat = 0;
					double selectedLng = 0;
					if(address.hasLatitude() && address.hasLongitude()){
                	    selectedLat = address.getLatitude();
                	    selectedLng = address.getLongitude();
                	}
                	nearPlaces = googlePlaces.search(selectedLat,
    						selectedLng, radius, types);
                } else {
                	nearPlaces = googlePlaces.search(gps.getLatitude(),
						gps.getLongitude(), radius, types);
                }

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * and show the data in UI. Always use runOnUiThread(new Runnable()) 
		 * to update UI from background thread **/
		protected void onPostExecute(String file_url) {
			
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					
					// dismiss the dialog after getting all products
					try {
						pDialog.dismiss();
					} catch (Exception e) {
						
					}
					
					/**
					 * Updating parsed Places into LISTVIEW
					 * */
					// Get json response status
					String status = nearPlaces.status;
					
					// Check for all possible status
					if(status.equals("OK")){
						// Successfully got places details
						if (nearPlaces.results != null) {
							// loop through each place
							for (Place p : nearPlaces.results) {
								HashMap<String, String> map = new HashMap<String, String>();
								
								// Place reference won't display in listview - it will be hidden
								// Place reference is used to get "place full details"
								map.put(KEY_REFERENCE, p.reference);
								
								// Place name
								map.put(KEY_NAME, p.name);
								
								
								// adding HashMap to ArrayList
								placesListItems.add(map);
							}
							// list adapter
							ListAdapter adapter = new SimpleAdapter(MainPlacesActivity.this, placesListItems,
					                R.layout.list_item,
					                new String[] { KEY_REFERENCE, KEY_NAME}, new int[] {
					                        R.id.reference, R.id.name });
							
							// Adding data into listview
							lv.setAdapter(adapter);
						}
					}
					else if(status.equals("ZERO_RESULTS")){
						// Zero results found
						alert.showAlertDialog(MainPlacesActivity.this, "Near Places",
								"Sorry no places found. Try to change the types of places",
								false);
					}
					else if(status.equals("UNKNOWN_ERROR"))
					{
						alert.showAlertDialog(MainPlacesActivity.this, "Places Error",
								"Sorry unknown error occured.",
								false);
					}
					else if(status.equals("OVER_QUERY_LIMIT"))
					{
						alert.showAlertDialog(MainPlacesActivity.this, "Places Error",
								"Sorry query limit to google places is reached",
								false);
					}
					else if(status.equals("REQUEST_DENIED"))
					{
						alert.showAlertDialog(MainPlacesActivity.this, "Places Error",
								"Sorry error occured. Request is denied",
								false);
					}
					else if(status.equals("INVALID_REQUEST"))
					{
						alert.showAlertDialog(MainPlacesActivity.this, "Places Error",
								"Sorry error occured. Invalid Request",
								false);
					}
					else
					{
						alert.showAlertDialog(MainPlacesActivity.this, "Places Error",
								"Sorry error occured.",
								false);
					}
				}
			});

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	

}
