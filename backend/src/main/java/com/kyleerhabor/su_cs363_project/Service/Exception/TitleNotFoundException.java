package com.kyleerhabor.su_cs363_project.Service.Exception;

import java.util.UUID;

public class TitleNotFoundException extends RuntimeException {
  public final UUID id;

  public TitleNotFoundException(final UUID id) {
    this.id = id;
  }
}
