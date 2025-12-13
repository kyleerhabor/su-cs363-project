package com.kyleerhabor.su_cs363_project.Repository.Model;

public record FindUsersRepositoryModel(
  String userUUID,
  String userUsername,
  String userName,
  String userTitleUUID,
  Boolean userTitleIsFavorite,
  String userTitleTitleUUID,
  String userTitleTitleName,
  String userTitleTitleDescription
) {}
