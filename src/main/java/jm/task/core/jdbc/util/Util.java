package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/new_test_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "VmdnikIscv96$";

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            System.out.println("Подключение с БД установлено");
            return connection;
        } catch (SQLException e) {
            System.out.println("Не удалось открыть соединение с БД");
            throw e;
        }
    }
}
