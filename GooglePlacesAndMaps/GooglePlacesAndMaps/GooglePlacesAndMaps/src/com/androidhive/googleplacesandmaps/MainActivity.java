package com.androidhive.googleplacesandmaps;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        View startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        
        View searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.startButton:
    		Intent i = new Intent(this, MainMenu.class);
    		startActivity(i);
    		break;
    	case R.id.searchButton:
    		
    		try {
    			EditText locationText = (EditText) findViewById(R.id.locationText);
    			String placeName = locationText.getText().toString();
    			Intent in = new Intent(this, MainMenu.class);
    			in.putExtra("location_entered", placeName);
        		startActivity(in);
    			
    		} catch (Exception e) {
    			// failed to get location value
    			Toast.makeText(getApplicationContext(),
    					"No location entered", Toast.LENGTH_SHORT).show();
    		}
    		break;
    	}
    }
}
