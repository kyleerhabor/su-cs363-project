package com.kyleerhabor.su_cs363_project.Controller.Exception;

public class UsernameExistsException extends RuntimeException {
  public final String username;

  public UsernameExistsException(String username) {
    this.username = username;
  }
}
