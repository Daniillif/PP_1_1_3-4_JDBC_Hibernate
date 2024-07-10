package jm.task.core.jdbc.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/user";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DB_Driver).getDeclaredConstructor().newInstance();
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("Connection OK");
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException |
                     NoSuchMethodException | SQLException e) {
                e.printStackTrace();
                System.out.println("Connection FAILED");
            }
        }
        return connection;
    }
}
