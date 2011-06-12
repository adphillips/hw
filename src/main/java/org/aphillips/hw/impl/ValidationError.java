package org.aphillips.hw.impl;

public class ValidationError extends RuntimeException {

  private static final long serialVersionUID = 8854788331873900145L;

  public ValidationError(String msg) {
    super(msg);
  }

}
