package jm.task.core.jdbc;

import com.mysql.jdbc.Driver;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Сергей", "Иванов", (byte) 19);
        userService.saveUser("Алексей", "Петров", (byte) 22);
        userService.saveUser("Иван", "Сидоров", (byte) 18);
        userService.saveUser("Николай", "Иванов", (byte) 29);

        userService.removeUserById(4);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
