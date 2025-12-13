package com.kyleerhabor.su_cs363_project.Service.Model;

import java.util.UUID;
import org.jspecify.annotations.Nullable;

@Nullable
public record TitleServiceModel(UUID id, String name, String description) {}
