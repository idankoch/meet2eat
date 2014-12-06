package com.wew.managers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

import com.wew.entities.Place;
import com.wew.entities.RecommendationTime;
import com.wew.entities.UserMealPreferences;

public class RecommendationManagerTest {

	private RecommendationManager recommendationManager = new RecommendationManager();
	private List<UserMealPreferences> usersMealPreferences;

	@Before
	public void setup(){
		usersMealPreferences = new ArrayList<UserMealPreferences>();
	}

	@Test
	public void testSelectedTime_outOfFour() {
		LocalTime first = new LocalTime(12,00);
		usersMealPreferences.add(new UserMealPreferences(null, null, null, first, null));
		usersMealPreferences.add(new UserMealPreferences(null, null, null, new LocalTime(12,15), null));
		LocalTime median = new LocalTime(12,30);
		usersMealPreferences.add(new UserMealPreferences(null, null, null, median, null));
		LocalTime last = new LocalTime(13,00);
		usersMealPreferences.add(new UserMealPreferences(null, null, null, last, null));

		RecommendationTime selectTime = recommendationManager.selectTime(usersMealPreferences);

		assertNotNull(selectTime);
		assertEquals(first, selectTime.getFirst());
		assertEquals(last, selectTime.getLast());
		assertEquals(new LocalTime(12,26), selectTime.getAverageTime());
		assertEquals(median, selectTime.getMedianTime());
	}

	@Test
	public void testSelectedTime_outOfFive() {
		LocalTime first = new LocalTime(12,00);
		usersMealPreferences.add(new UserMealPreferences(null, null, null, first, null));
		usersMealPreferences.add(new UserMealPreferences(null, null, null, new LocalTime(12,15), null));
		LocalTime median = new LocalTime(12,30);
		usersMealPreferences.add(new UserMealPreferences(null, null, null, median, null));
		usersMealPreferences.add(new UserMealPreferences(null, null, null, new LocalTime(12,45), null));
		LocalTime last = new LocalTime(13,00);
		usersMealPreferences.add(new UserMealPreferences(null, null, null, last, null));

		RecommendationTime selectTime = recommendationManager.selectTime(usersMealPreferences);

		assertNotNull(selectTime);
		assertEquals(first, selectTime.getFirst());
		assertEquals(last, selectTime.getLast());
		assertEquals(new LocalTime(12,30), selectTime.getAverageTime());
		assertEquals(median, selectTime.getMedianTime());
	}

	@Test
	public void testSelectedTime_singleSelection() {
		LocalTime first = new LocalTime(12,00);
		usersMealPreferences.add(new UserMealPreferences(null, null, null, first, null));

		RecommendationTime selectTime = recommendationManager.selectTime(usersMealPreferences);

		assertNotNull(selectTime);
		assertEquals(first, selectTime.getFirst());
		assertEquals(first, selectTime.getLast());
		assertEquals(first, selectTime.getAverageTime());
		assertEquals(first, selectTime.getMedianTime());
	}

	@Test
	public void testSelectPlace_onePlaceOnePerson(){
		List<Place> places = new ArrayList();
		addPlace(places, "place 1");
		usersMealPreferences.add(new UserMealPreferences(null, null, places, null, null));

		List<Place> placesResult = recommendationManager.selectPlace(usersMealPreferences);

		assertNotNull(placesResult);
		assertEquals(places,placesResult);
	}

	@Test	
	public void testSelectPlace_threePlacesOnePerson(){
		List<Place> places = new ArrayList<Place>();
		addPlace(places, "place 1");
		addPlace(places, "place 2");
		addPlace(places, "place 3");
		usersMealPreferences.add(new UserMealPreferences(null, null, places, null, null));

		List<Place> placesResult = recommendationManager.selectPlace(usersMealPreferences);
		
		assertNotNull(placesResult);
		assertEquals(places,placesResult);
	}

	@Test
	public void testSelectPlace_threePlacesThreePeople(){
		List<Place> places = new ArrayList<Place>();
		addPlace(places, "place 1");
		addPlace(places, "place 2");
		addPlace(places, "place 3");
		usersMealPreferences.add(new UserMealPreferences(null, null, places, null, null));
		places = new ArrayList<Place>();
		addPlace(places, "place 3");
		addPlace(places, "place 1");
		addPlace(places, "place 2");
		usersMealPreferences.add(new UserMealPreferences(null, null, places, null, null));
		places = new ArrayList<Place>();
		addPlace(places, "place 3");
		addPlace(places, "place 2");
		addPlace(places, "place 1");
		usersMealPreferences.add(new UserMealPreferences(null, null, places, null, null));

		List<Place> placesResult = recommendationManager.selectPlace(usersMealPreferences);
		List<Place> expectedResults = new ArrayList<Place>();
		addPlace(expectedResults, "place 3");
		addPlace(expectedResults, "place 1");
		addPlace(expectedResults, "place 2");
		assertEquals(expectedResults,placesResult);
	}
	
	private void addPlace(List<Place> places, String placeName) {
		Place place;
		place = new Place(placeName);
		places.add(place);
	}
}
