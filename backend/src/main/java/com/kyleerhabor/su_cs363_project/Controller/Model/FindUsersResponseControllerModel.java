package com.kyleerhabor.su_cs363_project.Controller.Model;

import com.kyleerhabor.su_cs363_project.Service.Model.UserServiceModel;
import java.util.List;

public record FindUsersResponseControllerModel(List<UserServiceModel> users) {}
