package com.wew.managers;

import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.wew.entities.User;
import com.wew.exceptions.UserApplicationException;
import com.wew.managers.UserManager;

public class UserManagerTest {
	
	UserManager userManager = new UserManager();
	private User user;

	@Before
	public void setup() throws UserApplicationException{
		user = new User("idankoch@gmail.com");
		userManager.addUser(user);
		User userFromDB = userManager.getUser(user);
		Assert.assertEquals(user, userFromDB);
	}
	
	@After
	public void teardown() throws UserApplicationException{
		userManager.deleteUser(user);
		User userFromDB = userManager.getUser(user);
		Assert.assertNull(userFromDB);
	}
	
	@Test(expected=UserApplicationException.class)
	public void testUserNull() throws UserApplicationException {
		userManager.addUser(null);
	}
	
	@Test(expected=UserApplicationException.class)
	public void testUserEmailNull() throws UserApplicationException {
		User userWithNullEmail = new User(null);
		userManager.addUser(userWithNullEmail);
	}
	
	@Test(expected=UserApplicationException.class)
	public void testUserEmailEmpty() throws UserApplicationException {
		User userWithEmptyEmail = new User("    ");
		userManager.addUser(userWithEmptyEmail);
	}
	
	@Test(expected=UserApplicationException.class)
	public void testInsertUserThatAlreadyExists() throws UserApplicationException {
		userManager.addUser(user);
	}

	@Test
	public void testAddBuddy() throws UserApplicationException{
		User buddy = new User("sivan.shoval@gmail.com");
		userManager.addUser(buddy);
		userManager.addBuddy(user, buddy);
		
		User userFromDB = userManager.getUser(user);
		Set<User> userFromDBBuddyList =  userFromDB.getBuddies();
		Assert.assertNotNull(userFromDBBuddyList);
		Assert.assertEquals(1, userFromDBBuddyList.size());
		Assert.assertEquals(buddy,userFromDBBuddyList.iterator().next());
	}
	
	@Test
	public void testRemoveBuddy() throws UserApplicationException{
		User buddy = new User("sivan.shoval@gmail.com");
		userManager.addUser(buddy);
		userManager.addBuddy(user, buddy);
		userManager.removeBuddy(user, buddy);

		User userFromDB = userManager.getUser(user);
		Set<User> userFromDBBuddyList =  userFromDB.getBuddies();
		Assert.assertNotNull(userFromDBBuddyList);
		Assert.assertEquals(0, userFromDBBuddyList.size());
	}
	
	@Test(expected=UserApplicationException.class)
	public void testAddBuddy_buddyDoesNotExist() throws UserApplicationException{
		User buddy = new User("sivan.shoval@gmail.com");
		userManager.addBuddy(user, buddy);
	}
	
	@Test(expected=UserApplicationException.class)
	public void testAddBuddyThatAlreadyExists() throws UserApplicationException{
		User buddy = new User("sivan.shoval@gmail.com");
		userManager.addBuddy(user, buddy);
		userManager.addBuddy(user, buddy);
	}
	
	@Test(expected=UserApplicationException.class)
	public void testAddBuddyThatIsTheSameAsUser() throws UserApplicationException{
		userManager.addBuddy(user, user);
	}
}
