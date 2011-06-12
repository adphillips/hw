package org.aphillips.hw.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.aphillips.hw.domain.User;

public interface UserResource {
  
  /**
   * Creates a record in a users database table, and respond to the 
   * submission by returning all of the data that was submitted in 
   * XML format, along with the ID of the newly created user
   * @param user the attributes of the user to be created
   * @returns a HTTP 201 (Created) with the URI to the newly created user
   */
  @POST
  @Consumes({APPLICATION_XML})
  Response createUser(User user);
  
  /**
   * Returns a representation of the user specified by <code>id</code>
   * If the user can't be found, a HTTP 404 response code is returned.
   * @return a representation of the user or 404 if not found
   */
  @Path("{id}")
  @GET
  User readUser(@PathParam("id") String id);
  
  //
  // The following are compatibility endpoints for clients who cannot 
  // set the HTTP Accept header to specify the desired content type 
  //
  
  @Path("{id}.xml")
  @GET
  @Produces({APPLICATION_XML})
  User readUserXml(@PathParam("id") String id);
  
  @Path("{id}.json")
  @GET
  @Produces({APPLICATION_JSON})
  User readUserJson(@PathParam("id") String id);

}
