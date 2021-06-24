package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;
    private Savepoint savePoint;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            connection.setSavepoint("savePoint");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(savePoint);
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("c o n n e c t ...");
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(30) NOT NULL, " +
                    "lastName VARCHAR(30) NOT NULL, " +
                    "age TINYINT NOT NULL)");
            connection.setSavepoint();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(savePoint);
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("create user table");
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            connection.setSavepoint();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(savePoint);
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("drop user table");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pst = connection.prepareStatement
                ("INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)")) {
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setInt(3, age);
            pst.executeUpdate();
            connection.setSavepoint();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(savePoint);
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            System.out.println("save user table");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pst = connection.prepareStatement("DELETE FROM Users WHERE id = ?")) {
            pst.setLong(1, id);
            pst.executeUpdate();
            connection.setSavepoint();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(savePoint);
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("remove user ById");
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                userList.add(user);
            }
            connection.setSavepoint();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(savePoint);
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("get all user");
        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE Users");
            connection.setSavepoint();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback(savePoint);
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println("clean user table");
    }
}
