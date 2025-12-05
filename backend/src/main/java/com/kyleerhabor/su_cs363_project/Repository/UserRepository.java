package com.kyleerhabor.su_cs363_project.Repository;

import com.kyleerhabor.su_cs363_project.Controller.Model.CreateUserRequestControllerModel;
import com.kyleerhabor.su_cs363_project.Repository.Model.UserRepositoryModel;
import java.util.List;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
  @Autowired private NamedParameterJdbcTemplate jdbc;

  public Boolean usernameExists(String username) {
    final var sql = "SELECT 1 FROM users WHERE username = :username";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("username", username);

    try {
      return jdbc.queryForObject(sql, parameters, Boolean.class);
    } catch (EmptyResultDataAccessException e) {
      return false;
    }
  }

  public Long create(CreateUserRequestControllerModel user, UUID id) {
    final var sql = "INSERT INTO users (uuid, username, name) VALUES (:uuid, :username, :name)";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("uuid", id.toString());
    parameters.addValue("username", user.username());
    parameters.addValue("name", user.name());

    final var keyHolder = new GeneratedKeyHolder();
    jdbc.update(sql, parameters, keyHolder);

    return keyHolder.getKey().longValue();
  }

  public List<UserRepositoryModel> findUsers() {
    final var sql = "SELECT id, uuid, username, name FROM users";
    final var parameters = new MapSqlParameterSource();
    final var users = jdbc.query(sql, parameters, (rs, _) -> new UserRepositoryModel(
      rs.getLong("id"),
      UUID.fromString(rs.getString("uuid")),
      rs.getString("username"),
      rs.getString("name")
    ));

    return users;
  }

  public @Nullable UserRepositoryModel findUserById(UUID id) {
    final var sql = "SELECT id, username, name FROM users WHERE uuid = :id";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("id", id.toString());

    final var user = jdbc.queryForObject(sql, parameters, (rs, _) -> new UserRepositoryModel(
      rs.getLong("id"),
      id,
      rs.getString("username"),
      rs.getString("name")
    ));

    return user;
  }

  public Boolean userExists(UUID id) {
    final var sql = "SELECT 1 FROM users WHERE uuid = :id";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("id", id.toString());

    try {
      return jdbc.queryForObject(sql, parameters, Boolean.class);
    } catch (EmptyResultDataAccessException e) {
      return false;
    }
  }

  public void deleteUser(UUID id) {
    final var sql = "DELETE FROM users WHERE uuid = :id";
    final var parameters = new MapSqlParameterSource();
    parameters.addValue("id", id.toString());
    jdbc.update(sql, parameters);
  }
}
