package org.aphillips.hw.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.aphillips.hw.api.UserDao;
import org.aphillips.hw.domain.User;
import org.aphillips.hw.domain.UserValidator;
import org.aphillips.hw.impl.ValidationError;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "test-context.xml" })
public class UserDaoTest {

  @Autowired
  private UserDao userDao;

  @Test
  public void testCreateUser() {

    final User testUser = getValidBaseUser();
    userDao.saveUser(testUser);
    Assert.assertNotNull("Userid not null, a save error must have occurred", testUser.getId());
    Assert.assertTrue("Id should not greater than zero", testUser.getId() > 0L);

    User retrievedUser = userDao.readUser(testUser.getId());
    assertNotNull("Retrieved user is null", retrievedUser);
    assertEquals("First name doesn't match", testUser.getFirstName(), retrievedUser.getFirstName());
    assertEquals("Last name doesn't match", testUser.getLastName(), retrievedUser.getLastName());
    assertEquals("Phone doesn't match", testUser.getPhone(), retrievedUser.getPhone());
    assertEquals("Email doesn't match", testUser.getEmail(), retrievedUser.getEmail());
    assertEquals("Date of Birth doesn't match", testUser.getDob(), retrievedUser.getDob());

  }

  /*
  Rules for user creation:
    At least one of the following fields must exist: First Name, Last Name, Email Address, or Phone Number
    First and Last Name must be alpha-characters only
    Email Address must be real email address
    Phone Number can accept any of the following characters: ()-.0-9
    Birthday must be an actual date, and in the past. "Actual date" means no Feb 31, for example.
    */

  private User getValidBaseUser() {
    final User testUser = new User();
    testUser.setFirstName("Bill");
    testUser.setLastName("Lumberg");
    testUser.setPhone("555-444-4321");
    testUser.setEmail("blumberg@innitek.com");

    UserValidator v = new UserValidator(testUser);
    v.validate();

    testUser.setId(null);
    return testUser;
  }
  
  @Test(expected = ValidationError.class)
  public void testCreateUserInsufficientData() {
    final User testUser = new User();
    userDao.saveUser(testUser);
  }
  
  @Test
  public void testValidBaseUser() {
    getValidBaseUser();
    //just check that this does not throw a validation error
  }

  @Test(expected = ValidationError.class)
  public void testCreateUserNonAlphaInFirstName() {
    User user = getValidBaseUser();
    user.setFirstName("50-cent");
    userDao.saveUser(user);
  }

  @Test(expected = ValidationError.class)
  public void testCreateUserNonAlphaInLastName() {
    User user = getValidBaseUser();
    user.setLastName("50-cent");
    userDao.saveUser(user);
  }

  @Test(expected=ValidationError.class)
  public void testCreateUserBadEmail() {
    User user = getValidBaseUser();
    user.setEmail("emailwithnodomain.com");
    userDao.saveUser(user);
  }
  
  @Test(expected=ValidationError.class)
  public void testCreateUserBadDoB() {
    User user = getValidBaseUser();
    //date must be in the past so this should fail
    user.setDob(new Date());
    userDao.saveUser(user);
  }

}
