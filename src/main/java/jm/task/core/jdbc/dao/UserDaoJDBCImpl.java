package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("CREATE TABLE `user_table` (`ID` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NULL, `last_name` VARCHAR(45) NULL, `age` INT(3) NULL, PRIMARY KEY (`ID`))");
            System.out.println("Таблица успешно создана");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Указанная таблица уже создана");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("DROP TABLE `user_table`");
            System.out.println("Таблица успешно удалена");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Удаляемой таблицы не существует");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO user_table (name, last_name, age) values (?, ?, ?)";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM user_table WHERE ID=?";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
            System.out.println("Запись удалена с базы данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_table");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
             statement.execute("DELETE FROM user_table");
            System.out.println("Все записи удалены");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
