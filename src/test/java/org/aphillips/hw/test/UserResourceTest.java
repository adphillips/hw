package org.aphillips.hw.test;

import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static org.aphillips.hw.test.JerseyTestUtil.assertResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.aphillips.hw.domain.User;
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

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "test-context.xml" })
public class UserResourceTest extends JerseyTest {

  //  private static WebAppDescriptor webAppDescriptor = new WebAppDescriptor.Builder(
  //      "org.aphillips.hw.impl").contextPath("api").build();

  private static WebAppDescriptor webAppDescriptor = new WebAppDescriptor.Builder()
      .contextPath("api").contextParam("contextConfigLocation", "classpath:**/test-context.xml")
      .requestListenerClass(RequestContextListener.class)
//      .contextListenerClass(ContextLoaderListener.class)
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

    //TODO make sure users completely match
  }
}
