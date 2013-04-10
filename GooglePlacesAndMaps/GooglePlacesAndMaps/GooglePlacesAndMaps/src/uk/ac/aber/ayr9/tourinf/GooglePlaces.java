package uk.ac.aber.ayr9.tourinf;

import org.apache.http.client.HttpResponseException;
import android.util.Log;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson.JacksonFactory;

/**
 * @author Androidhive + Adam Rigby (ayr9)
 * Code modified, JsonHttpParser replaced with JsonObjectParser
 * due to depracation of old method. Basic Details changed e.g.
 * API Key, application name. Rankby distance parameter added
 * to search request to get places ordered by distance.
 */


public class GooglePlaces {

	//Global instance of the HTTP transport
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	// Google API Key for browser apps. 	 					
	private static final String API_KEY = "AIzaSyDx7K8jvVIIK8SlSGcLJZkeqdGlEdR6OWs";

	// Google Places serach URLs
	private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
	private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

	private double _latitude;
	private double _longitude;
	//private double _radius;
	
	/**
	 * Places Search Method, uses URL above and params below to construct a full URL to get list of places 
	 * @param latitude - latitude of place
	 * @param longitude - longitude of place
	 * @param radius - radius of searchable area
	 * @param types - type of place to search
	 * @return list of places
	 * */
	public PlacesList search(double latitude, double longitude, double radius, String types)
			throws Exception {

		this._latitude = latitude;
		this._longitude = longitude;
		//this._radius = radius;

		try {

			HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
			HttpRequest request = httpRequestFactory
					.buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
			request.getUrl().put("key", API_KEY);
			request.getUrl().put("location", _latitude + "," + _longitude);
			//request.getUrl().put("radius", _radius); // Unit in meters
			request.getUrl().put("sensor", "false");
			if(types != null)
				request.getUrl().put("types", types);
				request.getUrl().put("rankby", "distance");
				
			//request.setConnectTimeout(200000); For Testing on TestDroid
			Log.e("URL Value",request.getUrl().toString());
			//Parse data at URL address  
			PlacesList list = request.execute().parseAs(PlacesList.class);
			// Log places response status
			Log.d("Places Status", "" + list.status);
			return list;

		} catch (HttpResponseException e) {
			Log.e("Error:", e.getMessage());
			return null;
		}

	}

	/**
	 * Searches to get more details on a singular place from the original list
	 * @param refrence - reference id of place in question
	 * 				   - This is obtained from parsing the origninal search API request
	 * */
	public PlaceDetails getPlaceDetails(String reference) throws Exception {
		try {

			HttpRequestFactory httpRequestFactory = createRequestFactory(HTTP_TRANSPORT);
			HttpRequest request = httpRequestFactory
					.buildGetRequest(new GenericUrl(PLACES_DETAILS_URL));
			request.getUrl().put("key", API_KEY);
			request.getUrl().put("reference", reference);
			request.getUrl().put("sensor", "false");
			//request.setConnectTimeout(200000); For Testing on TestDroid
			Log.e("URL Detail",request.getUrl().toString());
			PlaceDetails place = request.execute().parseAs(PlaceDetails.class);
			
			return place;

		} catch (HttpResponseException e) {
			Log.e("Error in Perform Details", e.getMessage());
			throw e;
		}
	}

	/**
	 * Create HttpRequestFactory
	 * */
	public static HttpRequestFactory createRequestFactory(
			final HttpTransport transport) {
		return transport.createRequestFactory(new HttpRequestInitializer() {
			public void initialize(HttpRequest request) {
				GoogleHeaders headers = new GoogleHeaders();
				headers.setApplicationName("Tourinf-Places");
				request.setHeaders(headers);
				JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
				request.setParser(parser);
			}
		});
	}

}
