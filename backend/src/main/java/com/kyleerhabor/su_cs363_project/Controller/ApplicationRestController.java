package com.kyleerhabor.su_cs363_project.Controller;

import com.kyleerhabor.su_cs363_project.Controller.Exception.UserNotFoundException;
import com.kyleerhabor.su_cs363_project.Controller.Exception.UsernameExistsException;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserResponseControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.ErrorCodeControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.ErrorControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.UserCollectionControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.UserControllerModel;
import com.kyleerhabor.su_cs363_project.Service.UserService;
import java.text.MessageFormat;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationRestController {
  @Autowired private UserService userService;

  @GetMapping("/users")
  public UserCollectionControllerModel getUsers() {
    return new UserCollectionControllerModel(
      userService
        .findUsers()
        .stream()
        .map((user) -> new UserControllerModel(user))
        .toList()
    );
  }

  @PostMapping("/users")
  @ResponseStatus(code = HttpStatus.CREATED)
  public CreateUserResponseControllerModel createUser(@RequestBody CreateUserRequestControllerModel user) {
    var id = UUID.randomUUID();
    userService.create(user, id);

    return new CreateUserResponseControllerModel(id);
  }

  @GetMapping("/users/{id}")
  public UserControllerModel getUser(@PathVariable UUID id) {
    return new UserControllerModel(userService.findById(id));
  }

  @DeleteMapping("/users/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable UUID id) {
    userService.deleteUser(id);
  }

  @ExceptionHandler(UsernameExistsException.class)
  @ResponseStatus(code = HttpStatus.FORBIDDEN)
  public ErrorControllerModel handleUsernameExistsException(UsernameExistsException e) {
    return new ErrorControllerModel(
      ErrorCodeControllerModel.USERNAME_EXISTS,
      MessageFormat.format("A user with the username ''{0}'' already exists", e.username)
    );
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorControllerModel handleUserNotFoundException(UserNotFoundException e) {
    return new ErrorControllerModel(
      ErrorCodeControllerModel.USER_NOT_FOUND,
      MessageFormat.format("Could not find user with ID ''{0}''", e.id)
    );
  }
}
