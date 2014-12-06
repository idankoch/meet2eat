package com.wew.daos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.wew.entities.User;
import com.wew.entities.UserMealPreferences;

public class UserDailyScheduleDao {

	private static final String dailyPreferencesKey = "%s-%s-%s-%s";//year-month-day-user unique identifier
	
	Map<String,UserMealPreferences> userDailyPrefernces = new HashMap<String, UserMealPreferences>();
	
	public UserMealPreferences getUserDailyPreferencesByDate(User userFromDB, LocalDate preferenceDate) {
		String key = getUserDailyPreferenceKey(userFromDB, preferenceDate);
		return userDailyPrefernces.get(key);
	}

	public void delete(UserMealPreferences dailyPreferences) {
		String key = getUserDailyPreferenceKey(dailyPreferences.getUser(), dailyPreferences.getMealDate());
		userDailyPrefernces.remove(key);
	}

	public void insert(UserMealPreferences dailyPreferences) {
		String key = getUserDailyPreferenceKey(dailyPreferences.getUser(), dailyPreferences.getMealDate());
		userDailyPrefernces.put(key, dailyPreferences);
	}
	
	private String getUserDailyPreferenceKey(User userFromDB, LocalDate nowDate) {
		int dayOfMonth = nowDate.getDayOfMonth();
		int monthOfYear = nowDate.getMonthOfYear();
		int year = nowDate.getYear();
		
		String key = String.format(dailyPreferencesKey, year,monthOfYear,dayOfMonth,userFromDB.getEmail());
		return key;
	}
}
