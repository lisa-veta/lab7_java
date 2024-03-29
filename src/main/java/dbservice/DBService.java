package dbservice;

import accounts.UserProfile;
import dbservice.UsersDAO;
import java.sql.*;

public class DBService {
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
    public static void addUser(UserProfile user) throws ClassNotFoundException, SQLException {
        checkConnection();
        UsersDAO dao = new UsersDAO(connection);
        dao.insertUser(user);
    }
    public static UserProfile getUserByLogin(String login) throws SQLException, ClassNotFoundException {
        checkConnection();
        UsersDAO dao = new UsersDAO(connection);
        return dao.get(login);
    }
}
