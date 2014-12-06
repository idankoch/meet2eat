package com.wew.managers;

import java.util.List;

import org.joda.time.LocalDate;

import com.wew.daos.UserDailyScheduleDao;
import com.wew.entities.Place;
import com.wew.entities.User;
import com.wew.entities.UserMealPreferences;
import com.wew.exceptions.PlaceApplicationException;
import com.wew.exceptions.UserApplicationException;
import com.wew.exceptions.UserDailyPreferencesApplicationException;
import com.wew.validators.PlaceValidator;
import com.wew.validators.UserDailyPreferencesValidator;
import com.wew.validators.UserValidator;

public class UserDailyScheduleManager {

	private UserManager userManager;
	private PlaceManager placesManager;

	private UserDailyScheduleDao userDailyScheduleDao = new UserDailyScheduleDao();

	
	public void upsertUserDailyPreferences(UserMealPreferences dailyPreferences) throws UserApplicationException, UserDailyPreferencesApplicationException, PlaceApplicationException {
		UserDailyPreferencesValidator.validateUserDailyPreferencesParams(dailyPreferences);
		User userFromDB = userManager.getUser(dailyPreferences.getUser());
		UserValidator.validateUserExists(userFromDB);
		dailyPreferences.setUser(userFromDB);
		LocalDate preferenceDate = dailyPreferences.getMealDate();
		validatePlaces(dailyPreferences.getPlaces());
		UserMealPreferences userDailyPreferencesByDate = userDailyScheduleDao.getUserDailyPreferencesByDate(userFromDB,preferenceDate);
		if(userDailyPreferencesByDate == null){
			userDailyScheduleDao.delete(dailyPreferences);
		}
		userDailyScheduleDao.insert(dailyPreferences);

	}

	private void validatePlaces(List<Place> places) throws PlaceApplicationException {
		for (Place place : places) {
			Place placeFromDB = placesManager.getPlace(place);
			PlaceValidator.validatePlaceExists(placeFromDB);
		}
	}

	public UserMealPreferences getUserDailyPreferencesForToday(User user) throws UserApplicationException, UserDailyPreferencesApplicationException {
		User userFromDB = userManager.getUser(user);
		LocalDate nowDate = new LocalDate();
		UserMealPreferences userDailyPreferencesByDate = userDailyScheduleDao.getUserDailyPreferencesByDate(userFromDB,nowDate);
		UserDailyPreferencesValidator.validateUserDailyPreferencesExist(userFromDB, userDailyPreferencesByDate);
		return userDailyPreferencesByDate;	
	}
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setPlacesManager(PlaceManager placesManager) {
		this.placesManager = placesManager;
	}
}
