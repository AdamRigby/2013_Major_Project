package uk.ac.aber.ayr9.tourinf;

import java.util.HashMap;
import uk.ac.aber.ayr9.tourinf.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.view.*;
import android.content.Intent;

/**
 * @author Adam Rigby (ayr9)
 * Displays the main menu of the application using icons using the
 * gridview layout in main_menu.xml and the custom image adapter.
 */

public class MainMenu extends Activity {
	
	
private HashMap<Integer, String> map = new HashMap<Integer, String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        populateMap();
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        
        // Get location entered from MainActivity if one was entered
        Bundle extrasIntent = getIntent().getExtras();
        
        final Bundle extras = extrasIntent;
        
        
        gridview.setOnItemClickListener(new OnItemClickListener() {
        	
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	
            	/**When an items clicked, use its position
            	to get the corresponding value from the HashMap */
            	String type = TranslatePos(position);
            	
            	Intent i = new Intent(getApplicationContext(), MainPlacesActivity.class);
            	
            	if (extras != null && extras.containsKey("location_entered")) {
                	String location = extras.getString("location_entered");
                	
                	//If location entered in MainActivity forward location on
                	i.putExtra("location_entered", location);
                }
            	/** Forward the type of place wanted to search
            	 from the user click and HashMap value*/
            	i.putExtra("SearchType", type);
            	
            	//Start MainPlacesActivity
        		startActivity(i);
            }
        });
    }
    
    //Enter key value pairs into HashMap
    public void populateMap(){
    	
    map.put(0, "restaurant"); 
	map.put(1, "cafe");
	map.put(2, "bar");
	map.put(3, "bank");
	map.put(4, "museum");
	map.put(5, "atm");
	map.put(6, "amusement_park");
	map.put(7, "bus_station");
	map.put(8, "gas_station");
	map.put(9, "grocery_or_supermarket");
	map.put(10, "hospital");
	map.put(11, "lodging");
	map.put(12, "meal_takeaway");
	map.put(13, "movie_theater");
	map.put(14, "night_club");
	map.put(15, "park");
	map.put(16, "parking");
	map.put(17, "pharmacy");
	map.put(18, "post_office");
	map.put(19, "shopping_mall");
	map.put(20, "stadium");
	map.put(21, "store");
	map.put(22, "taxi_stand");
	map.put(23, "train_station");
	
	}
    
    public String TranslatePos(int pos){
    	return map.get(pos).toString();    
    	
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}