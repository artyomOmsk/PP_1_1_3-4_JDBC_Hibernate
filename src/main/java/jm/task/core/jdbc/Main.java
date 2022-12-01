package jm.task.core.jdbc;

import com.mysql.cj.jdbc.NonRegisteringDriver;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Иван", "Иванов", (byte) 20);
        userService.saveUser("Александр", "Петрович", (byte) 30);
        userService.saveUser("Василий", "Семёнов", (byte) 20);
        userService.saveUser("Дмитрий", "Васильев", (byte) 20);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

