package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        Util.openConnection();
        UserDaoJDBCImpl daoJDBC = new UserDaoJDBCImpl();
        daoJDBC.createUsersTable();
        daoJDBC.saveUser("Kirill", "Nazarov", (byte) 99);
        daoJDBC.saveUser("Kolya", "Oleynikov", (byte) 1);
        daoJDBC.saveUser("Anton", "Shentsov", (byte) 33);
        daoJDBC.saveUser("Dima", "Batya", (byte) 17);
        for (User user : daoJDBC.getAllUsers()) {
            System.out.println(user);
        }
        daoJDBC.cleanUsersTable();
        daoJDBC.dropUsersTable();
        Util.closeConnection();
    }
}
