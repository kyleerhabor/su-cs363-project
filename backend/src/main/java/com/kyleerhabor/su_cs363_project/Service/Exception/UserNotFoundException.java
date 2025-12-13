package com.kyleerhabor.su_cs363_project.Service.Exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
  public final UUID id;

  public UserNotFoundException(final UUID id) {
    this.id = id;
  }
}
