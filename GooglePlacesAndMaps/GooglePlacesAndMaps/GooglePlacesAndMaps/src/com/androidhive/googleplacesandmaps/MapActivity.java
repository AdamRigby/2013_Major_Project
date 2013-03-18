package com.androidhive.googleplacesandmaps;




import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

public class MapActivity extends  android.support.v4.app.FragmentActivity {
	
	private GoogleMap gMap;
	private String lat;
	private String longit;
	private String name;
	
	/*CameraPosition Place_Location =
	            new CameraPosition.Builder().target(new LatLng(Double.parseDouble(lat), Double.parseDouble(longit)))
	                    .zoom(15.5f)
	                    .bearing(0)
	                    .tilt(25)
	                    .build();*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
		lat = extras.getString("Lat");
		longit = extras.getString("Long");
		name = extras.getString("Place_Name");
        setUpMapIfNeeded();   
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
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
        }
    }
    
    private void setUpMap() {
    	gMap.setMyLocationEnabled(true);
        gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(longit))).title(name));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(longit)), 16));
        //onGoToPlace();
    }
    
    
    /*public void onGoToPlace(View view) {
        if (!checkReady()) {
            return;
        }

        changeCamera(CameraUpdateFactory.newCameraPosition(Place_Location));
    }
    
    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(this, R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    
    private void changeCamera(CameraUpdate update) {
        changeCamera(update, null);
    }
    
    private void changeCamera(CameraUpdate update, CancelableCallback callback) {
    	//mMap.animateCamera(update, callback);
    	mMap.moveCamera(update);
        }*/
    
}


