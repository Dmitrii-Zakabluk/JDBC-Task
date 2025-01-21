package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

import static jm.task.core.jdbc.util.Util.HibernateUtil.factoryClose;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws SQLException {
        userService.createUsersTable();

        userService.saveUser("Сергей", "Иванов", (byte) 19);
        userService.saveUser("Алексей", "Петров", (byte) 22);
        userService.saveUser("Иван", "Сидоров", (byte) 18);
        userService.saveUser("Николай", "Иванов", (byte) 29);

        userService.removeUserById(3);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();

        factoryClose();
    }
}
