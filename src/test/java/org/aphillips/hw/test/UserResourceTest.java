package org.aphillips.hw.test;

import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.aphillips.hw.test.JerseyTestUtil.assertResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.aphillips.hw.domain.User;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.grizzly.GrizzlyTestContainerFactory;

public class UserResourceTest extends JerseyTest {

  private static WebAppDescriptor webAppDescriptor = new WebAppDescriptor.Builder()
      .contextPath("api").contextParam("contextConfigLocation", "classpath:**/test-context.xml")
      .requestListenerClass(RequestContextListener.class)
      .servletClass(SpringServlet.class).contextListenerClass(ContextLoaderListener.class).build();

  public UserResourceTest() throws Exception {
    super(webAppDescriptor);
    this.setTestContainerFactory(new GrizzlyTestContainerFactory());
  }

  @Test
  public void testCreateUser() {
    WebResource webResource = resource();

    // First, create the user

    final User testUser = new User();
    testUser.setFirstName("Bill");
    testUser.setLastName("Lumberg");
    testUser.setPhone("555-444-4321");
    testUser.setEmail("blumberg@innitek.com");
    Calendar cal = Calendar.getInstance();
    cal.clear();
    cal.set(1965, 4, 1);
    testUser.setDob(cal.getTime());
    ClientResponse response = webResource.path("users/").type(APPLICATION_XML).post(ClientResponse.class, testUser);
    assertResponse(response, Status.CREATED);
    String path = response.getLocation().getPath();
    assertTrue("Location URI is incorrect", path.matches("/api/users/[0-9]+"));

    // Now retrieve the user

    final String pre = "/api/";
    final String pathLessApp = path.substring(path.indexOf(pre)+pre.length());
    final Long requestedId = Long.parseLong(path.substring(path.lastIndexOf('/')+1));
    
    response = webResource.path(pathLessApp).accept(APPLICATION_XML).get(ClientResponse.class);
    assertResponse(response, Status.OK, APPLICATION_XML);
    final User retrievedUser = response.getEntity(User.class);
    assertEquals("Id doesn't match", requestedId, retrievedUser.getId());
    assertEquals("First name doesn't match", testUser.getFirstName(), retrievedUser.getFirstName());

    //We don't need to check the entire User here since that is covered by the DAO test
  }
  
  @Ignore
  @Test
  public void testInvalidDateOfBirth() {
    WebResource webResource = resource();

    String xml = "<user><dob>1965-02-31</dob><email>xmltest@innitek.com</email><firstName>Bill</firstName><id>2</id><lastName>Lumberg</lastName><phone>555-444-4321</phone></user>";
    ClientResponse response = webResource.path("users/").type(APPLICATION_XML).post(ClientResponse.class, xml);
    assertResponse(response, Status.BAD_REQUEST);
  }
  
  @Test
  public void testInvalidName() {
    WebResource webResource = resource();
    
    final User testUser = new User();
    testUser.setFirstName("50");
    testUser.setLastName("cent");
    
    ClientResponse response = webResource.path("users/").type(APPLICATION_XML).post(ClientResponse.class, testUser);
    assertResponse(response, Status.BAD_REQUEST);
  }
}
