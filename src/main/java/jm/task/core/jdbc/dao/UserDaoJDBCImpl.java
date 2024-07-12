package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT Exists Users
                (
                    Id INT AUTO_INCREMENT PRIMARY KEY,
                    Name VARCHAR(20),
                    LastName VARCHAR(20),
                    Age TINYINT
                );""";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка в методе createUsersTable класса UserDaoJDBCImpl");
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка в методе dropUsersTable класса UserDaoJDBCImpl");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users ( Name, LastName, Age) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем — %s добавлен в базу данных", name);
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Ошибка в методе saveUser класса UserDaoJDBCImpl");

        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE Id=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка в методе removeUserById класса UserDaoJDBCImpl");
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String sql = "SELECT Id,Name,LastName,Age FROM Users";
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка в методе getAllUsers класса UserDaoJDBCImpl");
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE Users;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Ошибка в методе dropUsersTable класса UserDaoJDBCImpl");
        }
    }
}

