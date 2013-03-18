package com.androidhive.googleplacesandmaps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        //mContext = c;
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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
           imageView = new ImageView(mContext);
           imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
           imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
           imageView.setPadding(8, 8, 8, 8);
        	convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_row, null);
        } else {
           //imageView = (ImageView) convertView;
        }
        
        
        //imageView.setImageResource(mThumbIds[position]);
        
        RelativeLayout rl=(RelativeLayout) convertView.findViewById(R.id.relativeLayout1);
        imageView=(ImageView)convertView.findViewById(R.id.imageView1);
        imageView.setImageResource(mThumbIds[position]);
        TextView txt= (TextView) convertView.findViewById(R.id.textView1);
        txt.setText(mThumbtext[position]);
        
        return convertView;
       //return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1
    };
    
 // references to icon labels
    private String[] mThumbtext = {
            "Restaurant",
            "Cafe",
            "Bar", 
            "Bank",
            "Museum",
            "Atm's",
            "Theme Park",
            "Bus Station",
            "Petrol Station",
            "Supermarket",
            "Hospital",
            "Hotel",
            "Takeaway",
            "Cinema",
            "Night Club",
            "Park",
            "Parking",
            "Pharmacy",
            "Post Office",
            "Shopping Centre",
            "Stadium",
            "Stores",
            "Taxi",
            "Train Station"
    };
}
