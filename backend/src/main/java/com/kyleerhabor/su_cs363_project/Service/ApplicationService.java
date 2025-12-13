package com.kyleerhabor.su_cs363_project.Service;

import com.kyleerhabor.su_cs363_project.Controller.Model.CreateTitleRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserTitleRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Repository.ApplicationRepository;
import com.kyleerhabor.su_cs363_project.Repository.Model.FindUserByUUIDRepositoryModel;
import com.kyleerhabor.su_cs363_project.Service.Exception.TitleNotFoundException;
import com.kyleerhabor.su_cs363_project.Service.Exception.UserNotFoundException;
import com.kyleerhabor.su_cs363_project.Service.Exception.UserTitleNotFoundException;
import com.kyleerhabor.su_cs363_project.Service.Exception.UsernameExistsException;
import com.kyleerhabor.su_cs363_project.Service.Model.TitleServiceModel;
import com.kyleerhabor.su_cs363_project.Service.Model.UserServiceModel;
import com.kyleerhabor.su_cs363_project.Service.Model.UserTitleServiceModel;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationService {
  @Autowired private ApplicationRepository repository;

  public void createTitle(UUID id, CreateTitleRequestControllerModel title) {
    repository.createTitle(id, title);
  }

  public List<TitleServiceModel> findTitles() {
    final var items = repository.findTitles();
    final var titles = items
      .stream()
      .map((items2) -> new TitleServiceModel(
        UUID.fromString(items2.titleUUID()),
        items2.titleName(),
        items2.titleDescription()
      ))
      .toList();

    return titles;
  }

  public TitleServiceModel findTitle(UUID id) {
    final var title = repository.findTitleByUUID(id);

    if (title == null) {
      throw new TitleNotFoundException(id);
    }

    return new TitleServiceModel(null, title.titleName(), title.titleDescription());
  }

  public void deleteTitle(UUID id) {
    final var titleID = repository.findTitleIDByUUID(id);

    if (titleID == null) {
      throw new TitleNotFoundException(id);
    }

    repository.deleteTitle(titleID);
  }

  public void createUser(UUID id, CreateUserRequestControllerModel user) {
    if (repository.findUserIDByUsername(user.username()) != null) {
      throw new UsernameExistsException(user.username());
    }

    repository.createUser(id, user);
  }

  public List<UserServiceModel> findUsers() {
    final var items = repository.findUsers();
    final var users = items
      .stream()
      .map((item2) -> new UserServiceModel(
        UUID.fromString(item2.userUUID()),
        item2.userUsername(),
        item2.userName(),
        items
          .stream()
          .filter((item3) -> item3.userTitleUUID() != null && item3.userUUID() == item2.userUUID())
          .map((item3) -> new UserTitleServiceModel(
            UUID.fromString(item3.userTitleUUID()),
            new TitleServiceModel(
              UUID.fromString(item3.userTitleTitleUUID()),
              item3.userTitleTitleName(),
              item3.userTitleTitleDescription()
            ),
            item3.userTitleIsFavorite()
          ))
          .toList()
      ))
      .toList();

    return users;
  }

  public UserServiceModel findUser(UUID id) {
    final var items = repository.findUserByUUID(id);
    final FindUserByUUIDRepositoryModel item;

    try {
      item = items.getFirst();
    } catch (NoSuchElementException _) {
      throw new UserNotFoundException(id);
    }

    final var user = new UserServiceModel(
      null,
      item.userUsername(),
      item.userName(),
      items
        .stream()
        .filter((item2) -> item2.userTitleUUID() != null)
        .map((item2) -> new UserTitleServiceModel(
          UUID.fromString(item2.userTitleUUID()),
          new TitleServiceModel(
            UUID.fromString(item2.userTitleTitleUUID()),
            item2.userTitleTitleName(),
            item2.userTitleTitleDescription()),
          item2.userTitleIsFavorite()
        ))
        .toList()
      );

    return user;
  }

  public void deleteUser(UUID id) {
    final var userID = repository.findUserIDByUUID(id);

    if (userID == null) {
      throw new UserNotFoundException(id);
    }

    repository.deleteUser(userID);
  }

  public void createUserTitle(UUID id, UUID user, CreateUserTitleRequestControllerModel userTitle) {
    final var userID = repository.findUserIDByUUID(user);

    if (userID == null) {
      throw new UserNotFoundException(user);
    }

    final var titleID = repository.findTitleIDByUUID(userTitle.title());

    if (titleID == null) {
      throw new TitleNotFoundException(userTitle.title());
    }

    final var userTitleID = repository.createUserTitle(id, titleID, userTitle);
    repository.createTitleUser(userID, userTitleID);
  }

  public void deleteUserTitle(UUID id) {
    final var userTitleID = repository.findUserTitleIDByUUID(id);

    if (userTitleID == null) {
      throw new UserTitleNotFoundException(id);
    }

    repository.deleteUserTitle(userTitleID);
  }
}
