package com.kyleerhabor.su_cs363_project.Service.Exception;

public class UsernameExistsException extends RuntimeException {
  public final String username;

  public UsernameExistsException(String username) {
    this.username = username;
  }
}
