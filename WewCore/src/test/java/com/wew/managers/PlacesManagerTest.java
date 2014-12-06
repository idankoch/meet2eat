package com.wew.managers;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wew.entities.Place;
import com.wew.exceptions.PlaceApplicationException;
import com.wew.managers.PlaceManager;

public class PlacesManagerTest  {
	
	private static final String TEST_RESTURANT_NAME = "testResturantName";

	PlaceManager placeManager = new PlaceManager();
	private Place place;
	
	@Before
	public void setup() throws PlaceApplicationException{
		place = new Place(TEST_RESTURANT_NAME);
		placeManager.addPlace(place);
		Place placeById = placeManager.getPlace(place);
		Assert.assertEquals(place, placeById);
	}
	
	@After
	public void teardown() throws PlaceApplicationException{
		placeManager.deletePlace(place);
		Place placeById = placeManager.getPlace(place);
		Assert.assertNull(placeById);
	}
	
	@Test(expected=PlaceApplicationException.class)
	public void testInsertResurantThatAlreadyExists() throws PlaceApplicationException{
		placeManager.addPlace(place);
	}
	
	@Test(expected=PlaceApplicationException.class)
	public void testCreatePlaceNull() throws PlaceApplicationException{
		placeManager.addPlace(null);
	}
	
	@Test(expected=PlaceApplicationException.class)
	public void testCreatePlaceNameNull() throws PlaceApplicationException{
		Place placeWithNullName = new Place(null);
		
		placeManager.addPlace(placeWithNullName);
	}
	
	@Test(expected=PlaceApplicationException.class)
	public void testCreatePlaceNameEmpty() throws PlaceApplicationException{
		Place placeWithEmptyName = new Place("    ");

		placeManager.addPlace(placeWithEmptyName);
	}
}
