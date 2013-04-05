package uk.ac.aber.ayr9.tourinf;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import uk.ac.aber.ayr9.tourinf.R;
import android.os.Bundle;
import android.view.Menu;

/**
 * @author Adam Rigby (ayr9)
 * Shows a Singular place on a map with a marker.
 * Can display users current location.
 */


public class MapActivity extends  android.support.v4.app.FragmentActivity {
	
	private GoogleMap gMap;
	private String lat;
	private String longit;
	private String name;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Bundle extras = getIntent().getExtras();
        
        /**Get extra information needed from SinglePlaceActivity to enable
        the placement of the marker on the map */
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
        // Complete a null check to confirm that the map has not already been instantiated
        if (gMap == null) {
            // Get map from the SupportMapFragment
            gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check success of obtaining the map and launch setup
            if (gMap != null) {
                setUpMap();
            }
        }
    }
    
    private void setUpMap() {
    	gMap.setMyLocationEnabled(true);
        gMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(longit))).title(name));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(longit)), 16));
    }
    
}


