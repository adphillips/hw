package org.aphillips.hw.impl;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

  private static Log logger = LogFactory.getLog(UserResourceImpl.class);

  private UserDao userDao;

  @javax.ws.rs.core.Context 
  private HttpServletRequest request;

  @Autowired
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Response createUser(User user) {
    try {
      userDao.saveUser(user);
    } catch (ValidationError e) {
      logger.warn(e);
      return Response.status(Status.BAD_REQUEST).build();
    }

    URI uri;
    try {
      String requestUrl = request.getRequestURL().toString();
      if(!requestUrl.endsWith("/")) {
        requestUrl = requestUrl + "/";
      }
      uri = new URI(requestUrl + user.getId());
    } catch (URISyntaxException e) {
      //unchecked exceptions are the way to go
      throw new WebApplicationException(e);
    }
    return Response.status(Status.CREATED).location(uri).build();
  }

  @Override
  public User readUser(Long id) {
    User user = userDao.readUser(id);
    if (user == null) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
    return user;
  }

  public User readUserXml(Long id) {
    return readUser(id);
  }

  public User readUserJson(Long id) {
    return readUser(id);
  }

}
