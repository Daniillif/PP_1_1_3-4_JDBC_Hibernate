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
        try {
            String sql = """
                USE user;
                \s
                CREATE TABLE IF NOT Exists Users
                (
                    Id INT AUTO_INCREMENT PRIMARY KEY,
                    Name VARCHAR(20),
                    LastName VARCHAR(20),
                    Age TINYINT
                );""";
            connection.prepareStatement(sql).execute();
        } catch (SQLException e) {
            System.out.println("Ошибка в методе createUsersTable класса UserDaoJDBCImpl");
        }
    }

    public void dropUsersTable() {
        try {
            String sql = "DROP TABLE IF EXISTS Users;";
            connection.prepareStatement(sql).execute();
        } catch (SQLException e) {
            System.out.println("Ошибка в методе dropUsersTable класса UserDaoJDBCImpl");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement preparedStatement = null;
            String sql = "INSERT INTO Users ( Name, LastName, Age) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем — %s добавлен в базу данных",name);

        } catch (SQLException e) {
            System.out.println("Ошибка в методе saveUser класса UserDaoJDBCImpl");
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = null;
            String sql = "DELETE FROM Users WHERE Id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка в методе removeUserById класса UserDaoJDBCImpl");
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        Statement statement =  null;
        try {
            String sql = "SELECT Id,Name,LastName,Age FROM Users";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
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
        try {
            String sql = "TRUNCATE TABLE Users;";
            connection.prepareStatement(sql).execute();
        } catch (SQLException e) {
            System.out.println("Ошибка в методе dropUsersTable класса UserDaoJDBCImpl");
        }
    }
}

