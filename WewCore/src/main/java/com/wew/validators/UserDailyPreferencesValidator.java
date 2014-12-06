package com.wew.validators;

import java.util.List;

import com.wew.entities.Place;
import com.wew.entities.User;
import com.wew.entities.UserMealPreferences;
import com.wew.exceptions.UserDailyPreferencesApplicationException;

public class UserDailyPreferencesValidator {
	
	public static void validateUserDailyPreferencesExist(User userFromDB,
			UserMealPreferences userDailyPreferencesByDate)
			throws UserDailyPreferencesApplicationException {
		if(userDailyPreferencesByDate == null){
			throw new UserDailyPreferencesApplicationException(String.format("Daily preferences not found for user %s",userFromDB.getEmail()),UserDailyPreferencesApplicationException.DAILY_USER_PREFERENCES_NOT_FOUND);
		}
	}

	public static void validateUserDailyPreferencesParams(
			UserMealPreferences userDailyPreferences) throws UserDailyPreferencesApplicationException {
		if(userDailyPreferences == null){
			throw new UserDailyPreferencesApplicationException("daily preferences cannot be null", UserDailyPreferencesApplicationException.USER_DAILY_PREFERENCES_NULL);
		}
		User user = userDailyPreferences.getUser();
		if(userDailyPreferences.getUser() == null || user.getEmail().trim().isEmpty()){
			throw new UserDailyPreferencesApplicationException("User in daily preferences cannot be null and must contain unique user identifer", UserDailyPreferencesApplicationException.USER_DAILY_PREFERENCES_NULL);
		}
		List<Place> places = userDailyPreferences.getPlaces();
		if(places == null || places.isEmpty()){
			throw new UserDailyPreferencesApplicationException("Places in daily preferences cannot be null or empty", UserDailyPreferencesApplicationException.USER_DAILY_PREFERENCES_PLACES_NULL);
		}
	}

}
