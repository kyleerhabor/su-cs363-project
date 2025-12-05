package com.kyleerhabor.su_cs363_project.Controller.Model;

import com.kyleerhabor.su_cs363_project.Repository.Model.UserRepositoryModel;
import java.util.UUID;

public record UserControllerModel(UUID id, String username, String name) {
  public UserControllerModel(final UserRepositoryModel user) {
    this(user.uuid(), user.username(), user.name());
  }
}
