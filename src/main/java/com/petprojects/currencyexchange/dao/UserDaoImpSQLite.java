package com.petprojects.currencyexchange.dao;

import com.petprojects.currencyexchange.entity.Gender;
import com.petprojects.currencyexchange.entity.Role;
import com.petprojects.currencyexchange.entity.User;
import com.petprojects.currencyexchange.utils.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDaoImpSQLite implements UserDao {
    private final static UserDao INSTANCE = new UserDaoImpSQLite();

    private static final String SAVE_SQL =
            "INSERT INTO user (email, password, role, gender) VALUES (?, ?, ?, ?)";
    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL =
            "SELECT * FROM user WHERE email = ? AND password = ?";

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    @SneakyThrows
    public User save(User user) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setString(4, user.getGender().name());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try(Connection connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if(resultSet.next()) {
                user = User.builder()
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"))
                        .gender(Gender.valueOf(resultSet.getString("gender")))
                        .role(Role.valueOf(resultSet.getString("role")))
                        .build();
            }
            return Optional.ofNullable(user);
        }
    }

    @Override
    public boolean update(User user) {
        return false;
    }
}
