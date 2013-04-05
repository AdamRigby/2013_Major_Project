package uk.ac.aber.ayr9.tourinf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import uk.ac.aber.ayr9.tourinf.R;

/**
 * @author Adam Rigby + Used Android developer example.
 * A custom adapter written for the MainMenu class to display the images in the gridview correctly
 * and for different screen sizes. Android developer
 * example used as a basis. Modified to use an additional
 * layout file(gridview_row) to allow text to be attached
 * to each icon image in the grid.
 */

public class ImageAdapter extends BaseAdapter {
    
	private Context mContext;

    public ImageAdapter(Context c) {
    	super();
        this.mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // Create new ImageView for each item referenced, using gridview_row
    //as the layout file for icon and text.
    @SuppressWarnings("unused")
	public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // If not recycled, initialise attributes
           imageView = new ImageView(mContext);
           imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
           imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           imageView.setPadding(8, 8, 8, 8);
           convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_row, null);
        } else {
           //imageView = (ImageView) convertView;
        }
        
        RelativeLayout rl=(RelativeLayout) convertView.findViewById(R.id.relativeLayout1);
        imageView=(ImageView)convertView.findViewById(R.id.imageGrid);
        imageView.setImageResource(mThumbIds[position]);
        TextView txt= (TextView) convertView.findViewById(R.id.textGrid);
        txt.setText(mThumbtext[position]);
        
        return convertView;
    }

    // References to icon images in drawable directory
    private Integer[] mThumbIds = {
            R.drawable.restaurant, R.drawable.cafe,
            R.drawable.bar, R.drawable.bank,
            R.drawable.museum, R.drawable.atm,
            R.drawable.theme_park, R.drawable.bus,
            R.drawable.petrol, R.drawable.supermarket,
            R.drawable.hospital, R.drawable.hotel,
            R.drawable.takeaway, R.drawable.cinema,
            R.drawable.nightclub, R.drawable.park,
            R.drawable.parking, R.drawable.pharmacy,
            R.drawable.postoffice, R.drawable.shopping_center,
            R.drawable.stadium, R.drawable.store,
            R.drawable.taxi, R.drawable.railway
    };
    
    // Add labels to icons
    private String[] mThumbtext = {
            "Restaurant", "Cafe",
            "Bar", "Bank",
            "Museum", "ATM's",
            "Theme Park", "Bus Station",
            "Petrol Station", "Supermarket",
            "Hospital", "Hotel",
            "Takeaway", "Cinema",
            "Night Club", "Park",
            "Parking", "Pharmacy",
            "Post Office", "Shopping Centre",
            "Stadium", "Stores",
            "Taxi", "Train Station"
    };
}
