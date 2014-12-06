package com.wew.managers;

import com.wew.daos.PlaceDao;
import com.wew.entities.Place;
import com.wew.exceptions.PlaceApplicationException;
import com.wew.validators.PlaceValidator;

public class PlaceManager {

	private PlaceDao placeDao = new PlaceDao();

	public void addPlace(Place place) throws PlaceApplicationException{
		PlaceValidator.validatePlaceArgument(place);
		Place placeFromDB = placeDao.getPlace(place);
		PlaceValidator.validatePlaceNotAlreadyExist(placeFromDB);
		placeDao.insertPlace(place);
	}

	public void deletePlace(Place place) throws PlaceApplicationException {
		PlaceValidator.validatePlaceArgument(place);

		placeDao.deletePlace(place);
	}

	public Place getPlace(Place place) throws PlaceApplicationException {
		PlaceValidator.validatePlaceArgument(place);

		return placeDao.getPlace(place);
	}
}