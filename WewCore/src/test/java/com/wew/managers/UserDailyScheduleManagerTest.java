package com.wew.managers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wew.entities.Place;
import com.wew.entities.User;
import com.wew.entities.UserMealPreferences;
import com.wew.exceptions.PlaceApplicationException;
import com.wew.exceptions.UserApplicationException;
import com.wew.exceptions.UserDailyPreferencesApplicationException;
import com.wew.exceptions.WewApplicationException;

public class UserDailyScheduleManagerTest {
	
	private static final LocalTime END_HOUR = new LocalTime(13,00);
	private static final LocalTime START_HOUR =  new LocalTime(12,00);
	private static final LocalTime ALTERNATIVE_END_HOUR = new LocalTime(13,30);
	UserDailyScheduleManager userDailyScheduleManager = new UserDailyScheduleManager();
	UserManager userManager = new UserManager();
	PlaceManager placeManager = new PlaceManager();
	private User user;
	private UserMealPreferences dailyPreferences;
	private List<Place> places = new ArrayList<Place>();
	private String[] placesNames = {"Mcdonalds","Burger King","Burger Ranch"};
	
	@Before
	public void setup() throws WewApplicationException{
		createUserForTest("idankoch@gmail.com");
		createPlaces(placesNames);
		userDailyScheduleManager.setUserManager(userManager);
		userDailyScheduleManager.setPlacesManager(placeManager);
	}

	@After
	public void teardown() throws WewApplicationException{
		deleteUser();
		deletePlaces();
	}

	@Test
	public void test_updateUserDailyPreferences_newEntry() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		dailyPreferences = new UserMealPreferences(user,new LocalDate(),places,START_HOUR,END_HOUR);
		
		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);
		
		UserMealPreferences dailyPreferencesFromDB = userDailyScheduleManager.getUserDailyPreferencesForToday(user);
		Assert.assertNotNull(dailyPreferencesFromDB);
		User userFromDB = dailyPreferencesFromDB.getUser();
		Assert.assertNotNull(userFromDB);
		Assert.assertEquals(user, userFromDB);
		Assert.assertEquals(places,dailyPreferencesFromDB.getPlaces());
		Assert.assertEquals(START_HOUR, dailyPreferencesFromDB.getFromHour());
		Assert.assertEquals(END_HOUR, dailyPreferencesFromDB.getToHour());
	}
	
	@Test
	public void test_updateUserDailyPreferences_replaceAnOldEntry() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		dailyPreferences = new UserMealPreferences(user,new LocalDate(),places,START_HOUR,END_HOUR);
		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);		
		dailyPreferences = new UserMealPreferences(user,new LocalDate(),places,START_HOUR,ALTERNATIVE_END_HOUR);
		
		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);
		
		UserMealPreferences dailyPreferencesFromDB = userDailyScheduleManager.getUserDailyPreferencesForToday(user);
		Assert.assertNotNull(dailyPreferencesFromDB);
		User userFromDB = dailyPreferencesFromDB.getUser();
		Assert.assertNotNull(userFromDB);
		Assert.assertEquals(user, userFromDB);
		Assert.assertEquals(places,dailyPreferencesFromDB.getPlaces());
		Assert.assertEquals(START_HOUR, dailyPreferencesFromDB.getFromHour());
		Assert.assertEquals(ALTERNATIVE_END_HOUR, dailyPreferencesFromDB.getToHour());
	}
	
	@Test
	public void testStartHourNotExit() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		dailyPreferences = new UserMealPreferences(user,new LocalDate(),places,null,END_HOUR);
		
		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);
		
		UserMealPreferences dailyPreferencesFromDB = userDailyScheduleManager.getUserDailyPreferencesForToday(user);
		Assert.assertNotNull(dailyPreferencesFromDB);
		User userFromDB = dailyPreferencesFromDB.getUser();
		Assert.assertNotNull(userFromDB);
		Assert.assertEquals(user, userFromDB);
		Assert.assertEquals(places,dailyPreferencesFromDB.getPlaces());
		Assert.assertNull(dailyPreferencesFromDB.getFromHour());
		Assert.assertEquals(END_HOUR, dailyPreferencesFromDB.getToHour());
	}
	
	@Test
	public void testEndHourNotExit() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		dailyPreferences = new UserMealPreferences(user,new LocalDate(),places,START_HOUR,null);

		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);
		
		UserMealPreferences dailyPreferencesFromDB = userDailyScheduleManager.getUserDailyPreferencesForToday(user);
		Assert.assertNotNull(dailyPreferencesFromDB);
		User userFromDB = dailyPreferencesFromDB.getUser();
		Assert.assertNotNull(userFromDB);
		Assert.assertEquals(user, userFromDB);
		Assert.assertEquals(places,dailyPreferencesFromDB.getPlaces());
		Assert.assertEquals(START_HOUR, dailyPreferencesFromDB.getFromHour());
		Assert.assertNull(dailyPreferencesFromDB.getToHour());
	}
	
	@Test(expected=UserDailyPreferencesApplicationException.class)
	public void testUserDailyPreferencesNull() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		userDailyScheduleManager.upsertUserDailyPreferences(null);
	}
	
	@Test(expected=UserDailyPreferencesApplicationException.class)
	public void testUserNull() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		dailyPreferences = new UserMealPreferences(null,new LocalDate(),places,START_HOUR,END_HOUR);

		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);
	}
	
	@Test(expected=UserApplicationException.class)
	public void testUserNotExist() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		dailyPreferences = new UserMealPreferences(new User("not exist"),new LocalDate(),places,START_HOUR,END_HOUR);
		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);

	}
	
	@Test(expected=UserDailyPreferencesApplicationException.class)
	public void testPlacesNull() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		dailyPreferences = new UserMealPreferences(user,new LocalDate(),null,START_HOUR,END_HOUR);

		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);
	}
	
	@Test(expected=UserDailyPreferencesApplicationException.class)
	public void testPlacesEmpty() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		dailyPreferences = new UserMealPreferences(user,new LocalDate(),new ArrayList<Place>(),START_HOUR,END_HOUR);

		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);
	}
	
	@Test(expected=PlaceApplicationException.class)
	public void testPlaceNotExist() throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		ArrayList<Place> places = new ArrayList<Place>();
		places.add(new Place("not exists"));
		dailyPreferences = new UserMealPreferences(user,new LocalDate(),places,START_HOUR,END_HOUR);

		userDailyScheduleManager.upsertUserDailyPreferences(dailyPreferences);
	}
	
	private void createUserForTest(String email) throws UserApplicationException {
		user = new User(email);
		userManager.addUser(user);
		User userFromDB = userManager.getUser(user);
		Assert.assertEquals(user, userFromDB);
	}
	
	private void createPlaces(String[] placesNames) throws PlaceApplicationException {
		for (String placeName : placesNames) {
			places.add(createPlace(placeName));
		}
	}
	
	private Place createPlace(String placeName) throws PlaceApplicationException {
		Place place = new Place(placeName);
		placeManager.addPlace(place);
		Place placeById = placeManager.getPlace(place);
		Assert.assertEquals(place, placeById);
		return place;
	}
	
	private void deletePlaces() throws PlaceApplicationException {
		for (Place place : places) {
			deletePlace(place);
		}
	}
	
	private void deletePlace(Place place) throws PlaceApplicationException {
		placeManager.deletePlace(place);
		Place placeById = placeManager.getPlace(place);
		Assert.assertNull(placeById);
	}

	private void deleteUser() throws UserApplicationException {
		userManager.deleteUser(user);
		User userFromDB = userManager.getUser(user);
		Assert.assertNull(userFromDB);
	}
}
