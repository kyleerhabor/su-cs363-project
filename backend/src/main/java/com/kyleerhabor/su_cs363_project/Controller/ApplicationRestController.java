package com.kyleerhabor.su_cs363_project.Controller;

import com.kyleerhabor.su_cs363_project.Controller.Model.CreateTitleRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateTitleResponseControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserResponseControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserTitleRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserTitleResponseControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.ErrorCodeControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.ErrorControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.FindTitleResponseControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.FindTitlesResponseControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.FindUserResponseControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.FindUsersResponseControllerModel;
import com.kyleerhabor.su_cs363_project.Service.ApplicationService;
import com.kyleerhabor.su_cs363_project.Service.Exception.TitleNotFoundException;
import com.kyleerhabor.su_cs363_project.Service.Exception.UserNotFoundException;
import com.kyleerhabor.su_cs363_project.Service.Exception.UserTitleNotFoundException;
import com.kyleerhabor.su_cs363_project.Service.Exception.UsernameExistsException;
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
  @Autowired
  private ApplicationService service;

  @PostMapping("/titles")
  @ResponseStatus(code = HttpStatus.CREATED)
  public CreateTitleResponseControllerModel createTitle(@RequestBody CreateTitleRequestControllerModel title) {
    final var id = UUID.randomUUID();
    service.createTitle(id, title);

    return new CreateTitleResponseControllerModel(id);
  }

  @GetMapping("/titles")
  public FindTitlesResponseControllerModel findTitles() {
    return new FindTitlesResponseControllerModel(service.findTitles());
  }


  @GetMapping("/titles/{id}")
  public FindTitleResponseControllerModel findTitle(@PathVariable UUID id) {
    return new FindTitleResponseControllerModel(service.findTitle(id));
  }

  @DeleteMapping("/titles/{id}")
  public void deleteTitle(@PathVariable UUID id) {
    service.deleteTitle(id);
  }

  @GetMapping("/users")
  public FindUsersResponseControllerModel findUsers() {
    return new FindUsersResponseControllerModel(service.findUsers());
  }

  @PostMapping("/users")
  @ResponseStatus(code = HttpStatus.CREATED)
  public CreateUserResponseControllerModel createUser(@RequestBody CreateUserRequestControllerModel user) {
    var id = UUID.randomUUID();
    service.createUser(id, user);

    return new CreateUserResponseControllerModel(id);
  }

  @GetMapping("/users/{id}")
  public FindUserResponseControllerModel findUser(@PathVariable UUID id) {
    return new FindUserResponseControllerModel(service.findUser(id));
  }

  @DeleteMapping("/users/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable UUID id) {
    service.deleteUser(id);
  }

  @PostMapping("/users/{user}/titles")
  @ResponseStatus(code = HttpStatus.CREATED)
  public CreateUserTitleResponseControllerModel createUserTitle(
    @PathVariable UUID user,
    @RequestBody CreateUserTitleRequestControllerModel userTitle
  ) {
    final var id = UUID.randomUUID();
    service.createUserTitle(id, user, userTitle);

    return new CreateUserTitleResponseControllerModel(id);
  }

  @DeleteMapping("/users/titles/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  public void deleteUserTitle(@PathVariable UUID id) {
    service.deleteUserTitle(id);
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

  @ExceptionHandler(TitleNotFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorControllerModel handleTitleNotFoundException(TitleNotFoundException e) {
    return new ErrorControllerModel(
      ErrorCodeControllerModel.TITLE_NOT_FOUND,
      MessageFormat.format("Could not find title with ID ''{0}''", e.id)
    );
  }

  @ExceptionHandler(UserTitleNotFoundException.class)
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  public ErrorControllerModel handleTitleNotFoundException(UserTitleNotFoundException e) {
    return new ErrorControllerModel(
      ErrorCodeControllerModel.USER_TITLE_NOT_FOUND,
      MessageFormat.format("Could not find user title with ID ''{0}''", e.id)
    );
  }
}
