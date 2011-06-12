package org.aphillips.hw.test;

import java.util.Calendar;

import org.aphillips.hw.api.UserDao;
import org.aphillips.hw.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"test-context.xml"})
public class UserDaoTest {

  @Autowired
  private UserDao userDao;
  
  @Test
  public void testCreateUser() {
    
    final User testUser = new User();
    testUser.setFirstName("Bill");
    testUser.setLastName("Lumberg");
    testUser.setPhone("555-444-4321");
    testUser.setEmail("blumberg@innitek.com");
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(1965, 4, 1);
    testUser.setDob(cal.getTime());
    
    userDao.saveUser(testUser);
    Assert.assertNotNull("Userid not null, a save error must have occurred", testUser.getId());
    Assert.assertTrue("Id should not greater than zero", testUser.getId() > 0L);
  }
  
  
}
