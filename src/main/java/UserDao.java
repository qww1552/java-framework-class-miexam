import java.sql.*;

public class UserDao {
    public User get(Integer id) throws ClassNotFoundException, SQLException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost/jeju?serverTimezone=UTC"
                        , "root", "1234");
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from userinfo where id = ?");
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        preparedStatement.close();
        connection.close();
        //리턴
        return user;
    }
}