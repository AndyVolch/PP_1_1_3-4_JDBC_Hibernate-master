package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/new_test_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "VmdnikIscv96$";
    static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void openConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Подключение к БД успешно открыто");
        } catch (SQLException e) {
            System.out.println("Не удалось открыть подключение");
        }
    }
    public static void closeConnection(){
        try {
            if (!connection.isClosed()) {
                connection.close();
                System.out.println("Закрываю подключение..");
            }
            if (connection.isClosed()) {
                System.out.println("Подключение успешно закрыто");
            }
        } catch (SQLException e) {
            System.out.println("Проблема с закрытием подключения");
        }
    }
}
