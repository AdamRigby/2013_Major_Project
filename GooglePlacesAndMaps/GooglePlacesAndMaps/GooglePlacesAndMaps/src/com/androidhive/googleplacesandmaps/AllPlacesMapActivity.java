package com.androidhive.googleplacesandmaps;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
//import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class AllPlacesMapActivity extends android.support.v4.app.FragmentActivity {
	// Nearest places
	PlacesList nearPlaces;

	// Map view
	MapView mapView;

	// Map overlay items
	List<Overlay> mapOverlays;

	AddItemizedOverlay itemizedOverlay;

	GeoPoint geoPoint;
	// Map controllers
	MapController mc;
	
	double latitude;
	double longitude;
	OverlayItem overlayitem;

	private GoogleMap gMap;

	private String user_latitude;

	private String user_longitude;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// Getting intent data
		Intent i = getIntent();
		
		// Users current geo location
		user_latitude = i.getStringExtra("user_latitude");
		user_longitude = i.getStringExtra("user_longitude");
		
		// Nearplaces list
		nearPlaces = (PlacesList) i.getSerializableExtra("near_places");

		setUpMapIfNeeded();  

		

	}
	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (gMap != null) {
                setUpMap();
            }
        } else {
        	setUpMap();
        }
    }
    
    private void setUpMap() {
    	gMap.setMyLocationEnabled(true);
    	
    	int noPlaces = 0;
    	double avgLat = 0;//Double.parseDouble(user_latitude);
    	double avgLong = 0;//Double.parseDouble(user_longitude);
    	// check for null in case it is null
    			if (nearPlaces.results != null) {
    				// loop through all the places
    				for (Place place : nearPlaces.results) {
    					latitude = place.geometry.location.lat; // latitude
    					longitude = place.geometry.location.lng; // longitude
    					
    					gMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(place.name).snippet(place.vicinity));
    					
    					avgLat += latitude;
    					avgLong += longitude;
    					noPlaces ++;
    				}
    				
    			}
    	if (noPlaces > 0) {
    		avgLat = avgLat / (noPlaces * 1.0);
    		avgLong = avgLong / (noPlaces * 1.0);		
    	}
    	
        //gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(longit))).title(name));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(avgLat, avgLong), 16));
        //onGoToPlace();
    }

	
}
