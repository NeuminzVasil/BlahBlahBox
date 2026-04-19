import java.sql.*;

public class AuthService {

    private static Connection connection;
    private static Statement statement;

    /**
     * Метод подключения к базе данных.
     */
    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:dbUsers.db");
            statement = connection.createStatement();
            System.out.println("AuthService.connect(). соединение с базой пользователей - ok!");
        } catch (Exception e) {
            //e.getMessage();
            e.printStackTrace();
            System.err.println("AuthService.connect(). соединение с базой пользователей - failed!");
        }
        return connection;
    }

    /**
     * метод отключения от базы данных.
     */
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    /**
     * получить Nick из базы
     *
     * @param login - логин
     * @param pass  - пароль
     * @return
     */
    public static String getNickByLoginAndPass(String login, String pass) {

        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'", login, pass);
        System.out.println("AuthService.getNickByLoginAndPass(): " + sql);
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("AuthService.getNickByLoginAndPass():" + resultSet.getString(1));
                return resultSet.getString(1);
            }

        } catch (SQLException e) {
            System.out.println("AuthService.getNickByLoginAndPass(): ОШИБКА ПОЛУЧЕНИЯ НИКА: " + sql);
            e.getMessage();
        }
        return null;
    }

    public static void main(String[] args) {
        connection  = connect();
        try {
            System.out.println(connection.getClientInfo());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
