package com.wew.compatartors;

import java.util.Comparator;

import org.joda.time.LocalTime;

public class LocalTimeComparator implements Comparator<LocalTime> {
	

	@Override
	public int compare(LocalTime o1, LocalTime o2) {
		return o1.compareTo(o2);
	}

}
