package dbservice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultHandler<T> {

    ///void accept(PreparedStatement preparedStatement) throws SQLException;
    T handle(ResultSet resultSet) throws SQLException;
}