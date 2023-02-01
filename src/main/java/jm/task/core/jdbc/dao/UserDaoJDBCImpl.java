package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.execute("CREATE TABLE `user_table` (`ID` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NULL, `last_name` VARCHAR(45) NULL, `age` INT(3) NULL, PRIMARY KEY (`ID`))");
            System.out.println("Таблица успешно создана");
            connection.commit();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Указанная таблица уже создана");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Не удалось произвести rollback транзакции");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось произвести rollback транзакции");
                }
            } else {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть подключение");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть statement");
                }
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.execute("DROP TABLE `user_table`");
            System.out.println("Таблица успешно удалена");
            connection.commit();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Удаляемой таблицы не существует");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("Не удалось произвести rollback транзакции");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось произвести rollback транзакции");
                }
            } else {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть подключение");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть statement");
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO user_table (name, last_name, age) values (?, ?, ?)";

        try {
            connection = Util.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось произвести rollback транзакции");
                }
            } else {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть подключение");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть statement");
                }
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = "DELETE FROM user_table WHERE ID=?";
        try {
            connection = Util.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.execute();
            connection.commit();
            System.out.println("Запись удалена с базы данных");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось произвести rollback транзакции");
                }
            } else {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть подключение");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть statement");
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()){
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
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            statement.execute("DELETE FROM user_table");
            System.out.println("Все записи удалены");
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось произвести rollback транзакции");
                }
            } else {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть подключение");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    throw new RuntimeException("Не удалось закрыть statement");
                }
            }
        }
    }
}
