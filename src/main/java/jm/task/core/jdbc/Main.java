package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Kirill", "Nazarov", (byte) 99);
        service.saveUser("Kolya", "Oleynikov", (byte) 1);
        service.saveUser("Anton", "Shentsov", (byte) 33);
        service.saveUser("Dima", "Batya", (byte) 17);
        for (User user : service.getAllUsers()) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
