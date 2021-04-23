import java.sql.*;

public class UserDao {
    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public User get(Integer id) throws ClassNotFoundException, SQLException, SQLException {
        Connection connection = connectionMaker.getConnection();
        User user = null;
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from userinfo where id = ?");
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //리턴
        return user;
    }

    public void insert(User user) throws SQLException, ClassNotFoundException {
        Connection connection = connectionMaker.getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into userinfo (name,password) values(?,?)",
                        Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2,user.getPassword());

        preparedStatement.executeUpdate();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        user.setId(resultSet.getInt(1));

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

}