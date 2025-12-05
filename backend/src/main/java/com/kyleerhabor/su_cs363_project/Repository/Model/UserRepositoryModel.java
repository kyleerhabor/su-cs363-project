package com.kyleerhabor.su_cs363_project.Repository.Model;

import java.util.UUID;

public record UserRepositoryModel(Long id, UUID uuid, String username, String name) {}
