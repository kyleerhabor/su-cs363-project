package com.kyleerhabor.su_cs363_project.Service.Model;

import java.util.List;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

@Nullable
public record UserServiceModel(UUID id, String username, String name, List<UserTitleServiceModel> titles) {}
