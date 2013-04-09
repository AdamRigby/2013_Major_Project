package uk.ac.aber.ayr9.tourinf;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import uk.ac.aber.ayr9.tourinf.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * @author AndroidHive + Modified by Adam Rigby (ayr9)
 * Class has been modified to incorporate a number of extra features.
 * Functionality added so that a user can blick a button to view the
 * location on a map, get directions, view a website or make a call.
 * The Activity will also get an Image from the place results if 
 * present and display it on screen. Finally a rating bar that uses
 * star indicators has been added.
 */

public class SinglePlaceActivity extends Activity implements OnClickListener{
	
	// Flag for Internet connection status
	Boolean isInternetPresent = false;

	ConnectionDetector cd;
	
	AlertDialogManager alert = new AlertDialogManager();

	GooglePlaces googlePlaces;
	
	PlaceDetails placeDetails;
	
	ProgressDialog pDialog;
	
	GPSTracker gps;

	private String latitude;

	private String longitude;
	
	private String name;

	private String websiteURL;
	
	private String phoneNO;
	
	private String rating;

	private View websiteButton;

	private View callButton;

	private PackageManager pm;
	
	public static String KEY_REFERENCE = "reference"; // id of the Place

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_place);
		
		latitude = latitude == null ? "Not present" : latitude;
		longitude = longitude == null ? "Not present" : longitude;
		name = name == null ? "Not present" : name;
		
		View mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);
		
		View directionsButton = findViewById(R.id.directionsButton);
		directionsButton.setOnClickListener(this);
		
		websiteButton = findViewById(R.id.websiteButton);
		websiteButton.setOnClickListener(this);
        
		callButton = findViewById(R.id.callButton);
		callButton.setOnClickListener(this);
        
		
		pm = this.getPackageManager();
		
        Intent i = getIntent();
		
		// Place referece id
		String reference = i.getStringExtra(KEY_REFERENCE);
		
		// Calling a Async Background thread
		new LoadSinglePlaceDetails().execute(reference);
	}
	
	public PlaceDetails getPlaceDetails() {
		return placeDetails;
	}

	public void onClick(View v) {
    	switch (v.getId()) {
    	
    	case R.id.mapButton:
    		/**Launch Singular MapActivity, forward place name & lat/long
    		to allow a map with marker to be produced **/
    		Intent i = new Intent(this, MapActivity.class);
    		i.putExtra("Lat", latitude);
    		i.putExtra("Long", longitude);
    		i.putExtra("Place_Name", name);
    		startActivity(i);
    		break;
    	case R.id.directionsButton:
    		// If GPS can get location, launch an Intent to start the map application with Directions.
    		gps = new GPSTracker(this);
    		if (gps.canGetLocation()){
    			Intent in = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + gps.getLatitude() + "," + gps.getLongitude() + "&daddr=" + latitude + "," + longitude));
    			startActivity(in);
    			break;
    		} 
    		else {
    			// Can't get user's current location, show dialog warning
    			alert.showAlertDialog(SinglePlaceActivity.this, "GPS Status",
    					"Couldn't get location information. Please enable GPS",
    					false);
    			break;
    		}
    	case R.id.websiteButton:
    		// Get Website URL from Place serializable class and launch intent
    		Intent in = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(websiteURL));
    		startActivity(in);
    		break;
    	case R.id.callButton:
    		// Make phone call with intent using Place class phoneNO key
    		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNO));
    		startActivity(intent);
    		break;
    	}
    }
	
	
	// Background Asynchronous Task to Load Google places
	class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {

		
		// Before starting background thread, show progress dialog until everythings completed 
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SinglePlaceActivity.this);
			pDialog.setMessage("Loading profile ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		 // Get Profile JSON
		protected String doInBackground(String... args) {
			String reference = args[0];
			
			// Creating Places class object
			googlePlaces = new GooglePlaces();

			// Get place details by sending Place reference as a parameter
			try {
				placeDetails = googlePlaces.getPlaceDetails(reference);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		//After completing background task
		protected void onPostExecute(String file_url) {
			// dismiss progress dialog animation after getting all products
			pDialog.dismiss();
			// Update GUI from Background Thread
			runOnUiThread(new Runnable() {
				

				public void run() {

					if(placeDetails != null){
						// Check place details status
						String status = placeDetails.status;
						
						// Get JSON parsed results if everythings OK
						if(status.equals("OK")){
							if (placeDetails.result != null) {
								name = placeDetails.result.name;
								String address = placeDetails.result.formatted_address;
								String phone = placeDetails.result.formatted_phone_number;
								latitude = Double.toString(placeDetails.result.geometry.location.lat);
								longitude = Double.toString(placeDetails.result.geometry.location.lng);
								rating = Double.toString(placeDetails.result.rating);
								
								// Make button visible in layout if website available.
								if (placeDetails.result.website != null) {
									websiteURL = placeDetails.result.website;
									websiteButton.setVisibility(View.VISIBLE);
								}
								
								// Make button visible if device has the ability to place calls e.g. phone, not most tablets
								if (placeDetails.result.formatted_phone_number != null && pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
									phoneNO = placeDetails.result.formatted_phone_number;
									callButton.setVisibility(View.VISIBLE);
								}
								
								Log.d("Place ", name + address + phone + latitude + longitude);
								
								// Declare all the details in the view/layout file single_place.xml
								TextView lbl_name = (TextView) findViewById(R.id.name);
								TextView lbl_address = (TextView) findViewById(R.id.address);
								TextView lbl_phone = (TextView) findViewById(R.id.phone);
								TextView lbl_location = (TextView) findViewById(R.id.location);
								TextView lbl_rating = (TextView) findViewById(R.id.rating);
								RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
								ImageView placeImage = (ImageView) findViewById(R.id.placeImage);
								
								// Check for null data in Places request, sometimes details are missing
								name = name == null ? "Not present" : name; // if name is null display as "Not present"
								address = address == null ? "Not present" : address;
								phone = phone == null ? "Not present" : phone;
								latitude = latitude == null ? "Not present" : latitude;
								longitude = longitude == null ? "Not present" : longitude;
								rating = placeDetails.result.rating == 0 ? "No rating" : rating;
								
								// Set text labels
								lbl_name.setText(name);
								lbl_address.setText(address);
								lbl_phone.setText(Html.fromHtml("<b>Phone:</b> " + phone));
								lbl_location.setText(Html.fromHtml("<b>Latitude:</b> " + latitude + ", <b>Longitude:</b> " + longitude));
								lbl_rating.setText(Html.fromHtml("<b>Rating:</b> "));
								
								// Give rating out of 5 with accuracy to 0.25, get rating from place result.
								ratingBar.setMax(5);
								ratingBar.setStepSize(0.25f);
								ratingBar.setRating((float) placeDetails.result.rating);
								
								// Get a photo from the JSON result parsed, if a reference is present
								if (placeDetails.result.photos != null && placeDetails.result.photos.size() > 0 && placeDetails.result.photos.get(0).photo_reference.length() > 0) {
									
									// URL to get photo from
									String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&key=AIzaSyDx7K8jvVIIK8SlSGcLJZkeqdGlEdR6OWs&photoreference=";
								
									photoUrl = photoUrl + placeDetails.result.photos.get(0).photo_reference + "&sensor=false";
								
									Log.e(this.toString(), placeDetails.result.photos.toString());
									Log.e(this.toString(), photoUrl);
									
									// Get the image from the URL
									new DownloadImageTask().execute(photoUrl, placeImage);
									
									// Show the Image in the view (deafault hidden)
									placeImage.setVisibility(View.VISIBLE);
								}
																
							}
						}
						else if(status.equals("ZERO_RESULTS")){
							alert.showAlertDialog(SinglePlaceActivity.this, "Near Places",
									"Sorry no place found.",
									false);
						}
						else if(status.equals("UNKNOWN_ERROR"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry unknown error occured.",
									false);
						}
						else if(status.equals("OVER_QUERY_LIMIT"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry query limit to google places is reached",
									false);
						}
						else if(status.equals("REQUEST_DENIED"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured. Request is denied",
									false);
						}
						else if(status.equals("INVALID_REQUEST"))
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured. Invalid Request",
									false);
						}
						else
						{
							alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
									"Sorry error occured.",
									false);
						}
					}else{
						alert.showAlertDialog(SinglePlaceActivity.this, "Places Error",
								"Sorry error occured.",
								false);
					}
					
					
				}
			});

		}

	}
	
	// Open connection to URL given and get the bitmap/photo at the address
	public static Bitmap getBitmapFromURL(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Create new seperate thread to run the above method, sets the bitmap for use
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		private String myUrl;
		private ImageView placeImage;

		@Override
		protected Bitmap doInBackground(String... url) {
			myUrl = url[0];
			return getBitmapFromURL(myUrl);
		}

		public void execute(String imageUrl, ImageView placeImage) {
			this.placeImage = placeImage;
			execute(imageUrl);
		}

		protected void onPostExecute(Bitmap result) {
			placeImage.setImageBitmap(result);
		}

	}

}
