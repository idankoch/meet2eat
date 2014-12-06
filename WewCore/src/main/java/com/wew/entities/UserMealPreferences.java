package com.wew.entities;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.wew.enums.MealType;

public class UserMealPreferences {

	private User user;
	private LocalDate mealDate;
	private List<Place> places;
	private LocalTime fromHour;
	private LocalTime toHour;//currently not in use
	private MealType mealType = MealType.LUNCH;
	
	public UserMealPreferences(User user, LocalDate mealDate, List<Place> places,
			LocalTime fromHour, LocalTime toHour) {
		super();
		this.user = user;
		this.mealDate = mealDate;
		this.places = places;
		this.fromHour = fromHour;
		this.toHour = toHour;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getMealDate() {
		return mealDate;
	}

	public void setMealDate(LocalDate todaysDate) {
		this.mealDate = todaysDate;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public LocalTime getFromHour() {
		return fromHour;
	}

	public void setFromHour(LocalTime fromHour) {
		this.fromHour = fromHour;
	}

	public LocalTime getToHour() {
		return toHour;
	}

	public void setToHour(LocalTime toHour) {
		this.toHour = toHour;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	
}
