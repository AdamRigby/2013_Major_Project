package uk.ac.aber.ayr9.tourinf;

import java.io.Serializable;
import java.util.List;
import com.google.api.client.util.Key;

/**
 * @author Androidhive
 * Holds the status of the JSON parsing and its results for one 
 * or more places within an area.
 */

/** Class implemented from "Serializable" so that you can pass this
 *  class Object to another using Intents, otherwise you can't pass 
 *  to another actitivy * */
public class PlacesList implements Serializable {

	private static final long serialVersionUID = -3516915145322471458L;

	@Key
	public String status;

	@Key
	public List<Place> results;

}