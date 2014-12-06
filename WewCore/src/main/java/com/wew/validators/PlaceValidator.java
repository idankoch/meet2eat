package com.wew.validators;

import com.wew.entities.Place;
import com.wew.exceptions.PlaceApplicationException;

public class PlaceValidator {

	public static void validatePlaceArgument(Place place) throws PlaceApplicationException {
		if(place == null || place.getName() == null || place.getName().trim().isEmpty()){
			throw new PlaceApplicationException(String.format("Place cannot be null or place name cannot be null"),PlaceApplicationException.INVALID_PLACE_ARGUMENT);
		}
	}

	public static void validatePlaceNotAlreadyExist(Place placeFromDB) throws PlaceApplicationException {
		if(placeFromDB != null){
			throw new PlaceApplicationException(String.format("Place %s already exists",placeFromDB.getName()),PlaceApplicationException.INVALID_PLACE_ARGUMENT);
		}
	}

	public static void validatePlaceExists(Place placeFromDB) throws PlaceApplicationException {
		if(placeFromDB == null || placeFromDB.getName() == null || placeFromDB.getName().isEmpty()){
			throw new PlaceApplicationException(String.format("Place does not exists or null"),PlaceApplicationException.INVALID_PLACE_ARGUMENT);
		}
	}
}
