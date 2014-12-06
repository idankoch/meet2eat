package com.wew.daos;

import java.util.HashMap;
import java.util.Map;

import com.wew.entities.Place;

public class PlaceDao {

	Map<String,Place> places = new HashMap<String,Place>();
	
	public Place getPlace(Place place) {
		return places.get(place.getName());
	}

	public void deletePlace(Place place) {
		String name = place.getName();
		if(places.containsKey(name)){
			places.remove(name);
		}
	}

	public void insertPlace(Place place) {		
		places.put(place.getName(), place);
	}
	
	
	
}
