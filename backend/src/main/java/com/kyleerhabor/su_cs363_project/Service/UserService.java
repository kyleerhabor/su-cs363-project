package com.kyleerhabor.su_cs363_project.Service;

import com.kyleerhabor.su_cs363_project.Controller.Exception.UserNotFoundException;
import com.kyleerhabor.su_cs363_project.Controller.Exception.UsernameExistsException;
import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Repository.UserRepository;
import com.kyleerhabor.su_cs363_project.Repository.Model.UserRepositoryModel;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
  @Autowired private UserRepository userRepository;

  public Long create(CreateUserRequestControllerModel user, UUID id) {
    if (userRepository.usernameExists(user.username())) {
      throw new UsernameExistsException(user.username());
    }

    return userRepository.create(user, id);
  }

  public List<UserRepositoryModel> findUsers() {
    return userRepository.findUsers();
  }

  public UserRepositoryModel findById(UUID id) {
    var user = userRepository.findUserById(id);

    if (user == null) {
      throw new UserNotFoundException(id);
    }

    return user;
  }

  public void deleteUser(UUID id) {
    if (!userRepository.userExists(id)) {
      throw new UserNotFoundException(id);
    }

    userRepository.deleteUser(id);
  }
}
