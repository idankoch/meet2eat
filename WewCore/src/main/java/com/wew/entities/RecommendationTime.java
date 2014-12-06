package com.wew.entities;

import org.joda.time.LocalTime;

public class RecommendationTime {

	private LocalTime averageTime;
	private LocalTime medianTime;
	private LocalTime last;
	private LocalTime first;

	public RecommendationTime(LocalTime averageTime, LocalTime medianTime,
			LocalTime last, LocalTime first) {
				this.averageTime = averageTime;
				this.medianTime = medianTime;
				this.last = last;
				this.first = first;
	}

	public LocalTime getAverageTime() {
		return averageTime;
	}

	public LocalTime getMedianTime() {
		return medianTime;
	}

	public LocalTime getLast() {
		return last;
	}

	public LocalTime getFirst() {
		return first;
	}
}
