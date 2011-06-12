package org.aphillips.hw.impl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.aphillips.hw.api.UserDao;
import org.aphillips.hw.api.UserResource;
import org.aphillips.hw.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("request")
@Path("/users")
public class UserResourceImpl implements UserResource {
  
  private UserDao userDao;

  @Autowired
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

//  public Collection loadProductsByCategory(String category) {
//      return this.sessionFactory.getCurrentSession()
//              .createQuery("from test.Product product where product.category=?")
//              .setParameter(0, category)
//              .list();
//  }

  public Response createUser(User user) {
    userDao.saveUser(user);
    
    URI uri;
    try {
      uri = new URI("http://localhost/api/users/"+user.getId());
    } catch (URISyntaxException e) {
      //unchecked exceptions are the way to go
      throw new WebApplicationException(e);
    }
    return Response.status(Status.CREATED).location(uri).build();
  }

  public User readUser(String id) {
    // TODO Auto-generated method stub
    throw new WebApplicationException(Response.Status.NOT_FOUND);
  }

  public User readUserXml(String id) {
    throw new WebApplicationException(Response.Status.NOT_FOUND);
  }

  public User readUserJson(String id) {
    throw new WebApplicationException(Response.Status.NOT_FOUND);
  }

}
