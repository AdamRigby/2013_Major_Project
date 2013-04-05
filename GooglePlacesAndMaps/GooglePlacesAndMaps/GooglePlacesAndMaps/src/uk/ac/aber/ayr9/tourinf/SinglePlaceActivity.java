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
 * 
 * @author AndroidHive + Heavily Modified by Adam Rigby (ayr9)
 *
 */

public class SinglePlaceActivity extends Activity implements OnClickListener{
	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	// Google Places
	GooglePlaces googlePlaces;
	
	// Place Details
	PlaceDetails placeDetails;
	
	// Progress dialog
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
	
	// KEY Strings
	public static String KEY_REFERENCE = "reference"; // id of the place

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
	
	public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.mapButton:
    		//System.out.println("MAP!");
    		Intent i = new Intent(this, MapActivity.class);
    		i.putExtra("Lat", latitude);
    		i.putExtra("Long", longitude);
    		i.putExtra("Place_Name", name);
    		startActivity(i);
    		break;
    	case R.id.directionsButton:
    		/*String uri = String.format("geo:%f,%f", latitude, longitude);
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    		startActivity(intent);*/
    		gps = new GPSTracker(this);
    		if (gps.canGetLocation()){
    			Intent in = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + gps.getLatitude() + "," + gps.getLongitude() + "&daddr=" + latitude + "," + longitude));
    			startActivity(in);
    			break;
    		} 
    		else {
    			// Can't get user's current location
    			alert.showAlertDialog(SinglePlaceActivity.this, "GPS Status",
    					"Couldn't get location information. Please enable GPS",
    					false);
    			// stop executing code by return
    			break;
    		}
    	case R.id.websiteButton:
    		/*String uri = String.format("geo:%f,%f", latitude, longitude);
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    		startActivity(intent);*/
    		
    		Intent in = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(websiteURL));
    		startActivity(in);
    		break;
    	case R.id.callButton:
    		/*String uri = String.format("geo:%f,%f", latitude, longitude);
    		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
    		startActivity(intent);*/
    		Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNO));
    		
    		startActivity(intent);
    		break;
    	}
    }
	
	/**
	 * Background Async Task to Load Google places
	 * */
	class LoadSinglePlaceDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(SinglePlaceActivity.this);
			pDialog.setMessage("Loading profile ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Profile JSON
		 * */
		protected String doInBackground(String... args) {
			String reference = args[0];
			
			// creating Places class object
			googlePlaces = new GooglePlaces();

			// Check if used is connected to Internet
			try {
				placeDetails = googlePlaces.getPlaceDetails(reference);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				

				public void run() {
					/**
					 * Updating parsed Places into LISTVIEW
					 * */
					if(placeDetails != null){
						String status = placeDetails.status;
						
						// check place deatils status
						// Check for all possible status
						if(status.equals("OK")){
							if (placeDetails.result != null) {
								name = placeDetails.result.name;
								String address = placeDetails.result.formatted_address;
								String phone = placeDetails.result.formatted_phone_number;
								latitude = Double.toString(placeDetails.result.geometry.location.lat);
								longitude = Double.toString(placeDetails.result.geometry.location.lng);
								rating = Double.toString(placeDetails.result.rating);
								
								if (placeDetails.result.website != null) {
									websiteURL = placeDetails.result.website;
									websiteButton.setVisibility(View.VISIBLE);
								}
								

								if (placeDetails.result.formatted_phone_number != null && pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
									phoneNO = placeDetails.result.formatted_phone_number;
									callButton.setVisibility(View.VISIBLE);
								}
								
								Log.d("Place ", name + address + phone + latitude + longitude);
								
								// Displaying all the details in the view
								// single_place.xml
								TextView lbl_name = (TextView) findViewById(R.id.name);
								TextView lbl_address = (TextView) findViewById(R.id.address);
								TextView lbl_phone = (TextView) findViewById(R.id.phone);
								TextView lbl_location = (TextView) findViewById(R.id.location);
								TextView lbl_rating = (TextView) findViewById(R.id.rating);
								RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
								ImageView placeImage = (ImageView) findViewById(R.id.placeImage);
								
								// Check for null data from google
								// Sometimes place details might missing
								name = name == null ? "Not present" : name; // if name is null display as "Not present"
								address = address == null ? "Not present" : address;
								phone = phone == null ? "Not present" : phone;
								latitude = latitude == null ? "Not present" : latitude;
								longitude = longitude == null ? "Not present" : longitude;
								rating = placeDetails.result.rating == 0 ? "No rating" : rating;
								
								lbl_name.setText(name);
								lbl_address.setText(address);
								lbl_phone.setText(Html.fromHtml("<b>Phone:</b> " + phone));
								lbl_location.setText(Html.fromHtml("<b>Latitude:</b> " + latitude + ", <b>Longitude:</b> " + longitude));
								lbl_rating.setText(Html.fromHtml("<b>Rating:</b> "));
								
								ratingBar.setMax(5);
								ratingBar.setStepSize(0.25f);
								ratingBar.setRating((float) placeDetails.result.rating);
								
								if (placeDetails.result.photos != null && placeDetails.result.photos.size() > 0 && placeDetails.result.photos.get(0).photo_reference.length() > 0) {
									String photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&key=AIzaSyDx7K8jvVIIK8SlSGcLJZkeqdGlEdR6OWs&photoreference=";
								
									photoUrl = photoUrl + placeDetails.result.photos.get(0).photo_reference + "&sensor=false";
								
									Log.e(this.toString(), placeDetails.result.photos.toString());
									Log.e(this.toString(), photoUrl);
									
									new DownloadImageTask().execute(photoUrl, placeImage);
									
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
