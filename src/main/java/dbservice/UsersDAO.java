package dbservice;

import accounts.UserProfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersDAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/usersJava";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Xfq8ybR*";
    private static Connection connection;

    public static void getConnection() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try{
            connection =  DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkConnection() throws ClassNotFoundException{
        if(connection == null){
            getConnection();
        }
    }
    public static void addUser(UserProfile user) throws ClassNotFoundException {
        checkConnection();
        String query = "INSERT INTO users (login, pass, email) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static UserProfile getUserByLogin(String login) throws SQLException, ClassNotFoundException {
        checkConnection();
        Executor executor = new Executor(connection);
        String query = "SELECT * FROM users WHERE login = '" + login + "'";
        return executor.execQuery(query, resultSet -> {
            if (resultSet.next()) {
                String retrievedLogin = resultSet.getString("login");
                String retrievedPass = resultSet.getString("pass");
                String email = resultSet.getString("email");
                return new UserProfile(retrievedLogin, retrievedPass, email);
            }
            return null;
        });
    }
}
