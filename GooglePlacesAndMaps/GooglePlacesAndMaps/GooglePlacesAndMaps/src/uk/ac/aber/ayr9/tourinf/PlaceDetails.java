package uk.ac.aber.ayr9.tourinf;

import java.io.Serializable;
import com.google.api.client.util.Key;

/**
 * @author Androidhive
 * Holds the status of the JSON parsing and its result (details) 
 * for a single place/location.
 **/

/** Class implemented from "Serializable" so that you can pass this
 *  class Object to another using Intents, otherwise you can't pass 
 *  to another actitivy * */
public class PlaceDetails implements Serializable {

	private static final long serialVersionUID = 2488491452593104368L;

	@Key
	public String status;
	
	@Key
	public Place result;

	@Override
	public String toString() {
		if (result!=null) {
			return result.toString();
		}
		return super.toString();
	}
}
