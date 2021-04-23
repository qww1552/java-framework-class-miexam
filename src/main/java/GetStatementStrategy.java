import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetStatementStrategy implements StatementStrategy {
    @Override
    public PreparedStatement makeStatement(Object object, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
        Integer id = (Integer) object;
        preparedStatement.setInt(1, id);

        return preparedStatement;
    }
}
