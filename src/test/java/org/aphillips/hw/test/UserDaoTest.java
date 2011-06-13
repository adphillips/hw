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
  public void testCreateAndReadUser() {

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

  //
  // Validation tests: Arguably these could go in a UserValidatorTest but for the sake of 
  // a bit of piece of mind integration tests, i stuck them here
  //

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
  
  @Test
  public void testCreateUserPhoneFormat1() {
    User user = getValidBaseUser();
    user.setPhone("(012)-345-6789");
    userDao.saveUser(user);
    Assert.assertNotNull("Userid not null, a save error must have occurred", user.getId());
    Assert.assertTrue("Id should not greater than zero", user.getId() > 0L);
  }
  
  @Test
  public void testCreateUserPhoneFormat2() {
    User user = getValidBaseUser();
    user.setPhone("555 111 2222");
    userDao.saveUser(user);
    Assert.assertNotNull("Userid not null, a save error must have occurred", user.getId());
    Assert.assertTrue("Id should not greater than zero", user.getId() > 0L);
  }
  
  @Test
  public void testCreateUserPhoneFormat3() {
    User user = getValidBaseUser();
    user.setPhone("555.111.2222");
    userDao.saveUser(user);
    Assert.assertNotNull("Userid not null, a save error must have occurred", user.getId());
    Assert.assertTrue("Id should not greater than zero", user.getId() > 0L);
  }
  
  @Test(expected=ValidationError.class)
  public void testCreateUserBadPhone() {
    User user = getValidBaseUser();
    user.setPhone("[555]-111-2222");
    userDao.saveUser(user);
  }
  
  @Test(expected=ValidationError.class)
  public void testCreateUserDobTooRecent() {
    User user = getValidBaseUser();
    //date must be in the past so this should fail
    user.setDob(new Date());
    userDao.saveUser(user);
  }

}
