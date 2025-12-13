package com.kyleerhabor.su_cs363_project.Service.Exception;

import java.util.UUID;

public class UserTitleNotFoundException extends RuntimeException {
  public final UUID id;

  public UserTitleNotFoundException(final UUID id) {
    this.id = id;
  }
}
