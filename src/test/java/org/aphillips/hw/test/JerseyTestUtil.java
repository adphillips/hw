package org.aphillips.hw.test;

import static junit.framework.Assert.assertEquals;
import junit.framework.AssertionFailedError;

import com.sun.jersey.api.client.ClientResponse;

@SuppressWarnings("nls")
public class JerseyTestUtil {

  protected static void assertResponse(ClientResponse response, ClientResponse.Status expectedStatus) {
    assertResponse(response, expectedStatus, null);
  }

  public static void assertResponse(ClientResponse response, ClientResponse.Status expectedStatus,
      String expectedMediaType) {
    try {
      assertEquals(expectedStatus, response.getClientResponseStatus());
    } catch (AssertionFailedError e) {
      throw new AssertionFailedError("Response status incorrect: " + e.getMessage());
    }

    if (expectedMediaType != null) {
      try {
        assertEquals(expectedMediaType, response.getType().toString());
      } catch (AssertionFailedError e) {
        throw new AssertionFailedError("Response media type incorrect: " + e.getMessage());
      }
    }
  }
}
