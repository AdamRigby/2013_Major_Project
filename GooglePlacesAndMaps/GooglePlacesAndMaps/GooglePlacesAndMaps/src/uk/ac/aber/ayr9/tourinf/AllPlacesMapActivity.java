package uk.ac.aber.ayr9.tourinf;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.OverlayItem;
import uk.ac.aber.ayr9.tourinf.R;

/**
 * @author Adam Rigby (ayr9)
 * New class to recreate a similar outcome to map class in androidhive third party
 * code. Instead of using map view, uses the new Android Maps v2 API. Google's mapdemo
 * example was used as a tutorial to find out how the new version works.
 */

public class AllPlacesMapActivity extends android.support.v4.app.FragmentActivity {
	
	// List of nearest places
	PlacesList nearPlaces;
	
	double latitude;
	double longitude;
	OverlayItem overlayitem;

	private GoogleMap gMap;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		Intent i = getIntent();
		
		// Get list of near places to display on map later
		nearPlaces = (PlacesList) i.getSerializableExtra("near_places");

		setUpMapIfNeeded();  

	}
	
	private void setUpMapIfNeeded() {
		//Complete a null check to confirm that the map has not already been instantiated
        if (gMap == null) {
            // Get map from the SupportMapFragment
            gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check success of obtaining the map and launch setup.
            if (gMap != null) {
                setUpMap();
            }
        } else {
        	setUpMap();
        }
    }
    
    private void setUpMap() {
    	//Lets users see their live location on map by pressing an icon.
    	gMap.setMyLocationEnabled(true);
    	
    	int noPlaces = 0;
    	double avgLat = 0;//Double.parseDouble(user_latitude);
    	double avgLong = 0;//Double.parseDouble(user_longitude);
    	
    			if (nearPlaces.results != null) {
    				
    				for (Place place : nearPlaces.results) {
    					latitude = place.geometry.location.lat; // latitude
    					longitude = place.geometry.location.lng; // longitude
    					
    					//Add a place marker at the lat/long of each place in the list/
    					//Allow it to be clickable, displaying their name and vicinity
    					gMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(place.name).snippet(place.vicinity));
    					
    					avgLat += latitude;
    					avgLong += longitude;
    					noPlaces ++;
    				}
    				
    			}
    	//Workout the average position of markers/places for camera to zoom centrally on the map		
    	if (noPlaces > 0) {
    		avgLat = avgLat / (noPlaces * 1.0);
    		avgLong = avgLong / (noPlaces * 1.0);		
    	}
    	
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(avgLat, avgLong), 16));
        
    }

	
}
