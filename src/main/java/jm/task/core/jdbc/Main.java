package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        //удаление таблицы
        userService.dropUsersTable();
        //Создание таблицы User(ов)
        userService.createUsersTable();
        //Добавление 4 User(ов) в таблицу с данными на свой выбор.
        //После каждого добавления должен быть вывод в консоль
        //( User с именем – name добавлен в базу данных )
        userService.saveUser("pavel", "mezin", (byte) 35);
        userService.saveUser("max", "madmax", (byte) 30);
        userService.saveUser("rain", "morris", (byte) 31);
        // Получение всех User из базы и вывод в консоль
        List<User> userList = userService.getAllUsers();
        // ( должен быть переопределен toString в классе User)
        userList.forEach(System.out::println);
        //Очистка таблицы User(ов)
        userService.cleanUsersTable();
        // Удаление таблицы
        userService.dropUsersTable();

    }
}
