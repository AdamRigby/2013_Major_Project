package uk.ac.aber.ayr9.tourinf;

import java.io.Serializable;
import java.util.ArrayList;
import com.google.api.client.util.Key;

/**
 * @author Androidhive + Modifications from Adam Rigby (ayr9)
 * JSON request is parsed into this serialisable class for storage.
 * Modification were made to include a static class for photo's with
 * a key to a photo reference. The place class has also been modified
 * to hold keys to an ArrayList of photos a website and a rating.
 */

/** Class implemented from "Serializable" so that you can pass this
 *  class Object to another using Intents, otherwise you can't pass 
 *  to another actitivy * */
public class Place implements Serializable {

	private static final long serialVersionUID = -3457056988206296457L;

	@Key
	public String id;
	
	@Key
	public String name;
	
	@Key
	public String reference;
	
	@Key
	public String icon;
	
	@Key
	public String vicinity;
	
	@Key
	public Geometry geometry;
	
	@Key
	public ArrayList<Photos> photos;
	
	@Key
	public String formatted_address;
	
	@Key
	public String formatted_phone_number;
	
	@Key
	public String website;
	
	@Key
	public double rating;

	@Override
	public String toString() {
		return name + " - " + id + " - " + reference;
	}
	
	public static class Geometry implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4769781230778261861L;
		@Key
		public Location location;
	}
	
	public static class Location implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 7738914709823938708L;

		@Key
		public double lat;
		
		@Key
		public double lng;
	}
	
	public static class Photos implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1102577548624702326L;

		@Key
		public int height;
		
		@Key
		public int width;
		
		@Key
		public String photo_reference;
		
		
		public String toString() {
			return height + " " + width + " " + photo_reference;
		}
	}
	
	
	
}
