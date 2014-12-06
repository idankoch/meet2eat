package com.wew.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.joda.time.LocalTime;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.wew.compatartors.LocalTimeComparator;
import com.wew.entities.Place;
import com.wew.entities.RecommendationTime;
import com.wew.entities.UserMealPreferences;

public class RecommendationManager {

	private int minTime = 1;

	public void getGroupRecommendation(List<UserMealPreferences> usersMealPreferences){
		selectTime(usersMealPreferences);
		selectPlace(usersMealPreferences);
	}

	protected List<Place> selectPlace(List<UserMealPreferences> usersMealPreferences) {
		Map<Place,Integer> placesAndScores = new HashMap<Place,Integer>();
		for (UserMealPreferences userMealPreferences : usersMealPreferences) {
			List<Place> places = userMealPreferences.getPlaces();
			int score = 10;
			for (Place place : places) {
				if(placesAndScores.containsKey(place)){
					//update old entry
					placesAndScores.put(place, placesAndScores.get(place)+score);
				} else{
					//new entry
					placesAndScores.put(place,score);
				}
				score--;
			}
		}

		List<Entry<Place,Integer>> entries = new ArrayList<>(placesAndScores.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Place, Integer>>() {

            @Override
            public int compare(Entry<Place, Integer> o1,
                    Entry<Place, Integer> o2) {
                if (o1.getValue() == null && o2.getValue() == null) return 0;
                if (o1.getValue() == null) return -1; //Nulls last
                return - o1.getValue().compareTo(o2.getValue());
            }

        });

        ArrayList<Place> results = new ArrayList<Place>();
        for (Entry<Place, Integer> entry : entries) {
			results.add(entry.getKey());
			System.out.println(entry.toString());
		}
        
        return results;
	}

	protected RecommendationTime selectTime(List<UserMealPreferences> usersMealPreferences) {
		List<LocalTime> fromHours = new ArrayList<LocalTime>();
		List<LocalTime> toHours = new ArrayList<LocalTime>();

		for (UserMealPreferences userDailyPreferences : usersMealPreferences) {
			LocalTime fromHour = userDailyPreferences.getFromHour();
			fromHours.add(fromHour);
			LocalTime toHour = userDailyPreferences.getToHour();
			toHours.add(toHour);
		}	

		LocalTime[] fromTimeArray = fromHours.toArray(new LocalTime[0]);
		LocalTime averageTime = averageTime(fromTimeArray);
		LocalTime medianTime = getMedianTime(fromTimeArray);
		LocalTime last = fromTimeArray[fromTimeArray.length-1];
		LocalTime first = fromTimeArray[0];
		return new RecommendationTime(averageTime,medianTime,last,first);
	}

	private LocalTime getMedianTime(LocalTime[] fromHours) {
		Arrays.sort(fromHours,new LocalTimeComparator());
		return fromHours[fromHours.length/2];
	}

	private LocalTime averageTime(LocalTime[] fromTimeArray) {
		int sumHours = 0;
		int sumMinutes = 0;
		LocalTime first = fromTimeArray[0];
		for (LocalTime fromHour : fromTimeArray) {
			sumHours += fromHour.getHourOfDay();
			sumMinutes += ((fromHour.getHourOfDay()-first.getHourOfDay())*60)+ fromHour.getMinuteOfHour();
		}
		int averageHour = sumHours/fromTimeArray.length;
		int averageMinute = sumMinutes/fromTimeArray.length;

		return new LocalTime(averageHour,averageMinute);
	}
}
