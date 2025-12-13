package com.kyleerhabor.su_cs363_project.Controller.Model;

import com.kyleerhabor.su_cs363_project.Service.Model.TitleServiceModel;
import java.util.List;

public record FindTitlesResponseControllerModel(List<TitleServiceModel> titles) {}
