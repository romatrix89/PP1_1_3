package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String SQL_DELETE = "DELETE FROM users WHERE NAME=?";
    private static final String SQL_INSERT = "INSERT INTO users (name, lastname, age) VALUES( ?, ?, ?)";
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INTEGER primary key auto_increment, " +
                    "name VARCHAR(30), lastname VARCHAR(30), age INTEGER);");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
   }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preStatement = Util.getConnection().prepareStatement(SQL_INSERT)) {
            preStatement.setString(1, name);
            preStatement.setString(2, lastName);
            preStatement.setInt(3, age);
            preStatement.addBatch();
            preStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        try (PreparedStatement preStatement = Util.getConnection().prepareStatement(SQL_DELETE)){
            preStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> usersDB = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()){
               String name = resultSet.getString("name");
               String lastName = resultSet.getString("lastname");
               byte age = resultSet.getByte("age");
               User currentUser = new User(name, lastName, age);
               usersDB.add(currentUser);
               System.out.println(currentUser.toString());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usersDB;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()){
            statement.executeUpdate("TRUNCATE users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
