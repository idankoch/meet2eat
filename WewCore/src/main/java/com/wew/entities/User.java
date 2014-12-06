package com.wew.entities;


import java.util.Set;
import java.util.TreeSet;

public class User implements Comparable<User>{

	private String email = "";
	private Set<User> buddies = new TreeSet<User>();
	
	public User(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public Set<User> getBuddies() {
		return buddies;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addBuddy(User buddy) {
		this.buddies.add(buddy);
	}

	public void removeBuddy(User buddy) {
		if(this.buddies.contains(buddy)){
			this.buddies.remove(buddy);
		}
	}

	@Override
	public int compareTo(User user) {
		return this.email.compareTo(user.getEmail());
	}
	
	
}
