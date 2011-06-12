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

  @Override
  public Response createUser(User user) {
    userDao.saveUser(user);
    
    URI uri;
    try {
      //FIXME set this path based on servlet context, not hardcoded
      uri = new URI("http://localhost/api/users/"+user.getId());
    } catch (URISyntaxException e) {
      //unchecked exceptions are the way to go
      throw new WebApplicationException(e);
    }
    return Response.status(Status.CREATED).location(uri).build();
  }

  @Override
  public User readUser(Long id) {
    User user = userDao.readUser(id);
    if(user == null) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
    return user;
  }

  public User readUserXml(Long id) {
    throw new WebApplicationException(Response.Status.NOT_FOUND);
  }

  public User readUserJson(Long id) {
    throw new WebApplicationException(Response.Status.NOT_FOUND);
  }

}
