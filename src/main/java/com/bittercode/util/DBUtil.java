
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
    private static HikariDataSource dataSource;
            Class.forName(DatabaseConfig.DRIVER_NAME);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:" + DatabaseConfig.CONNECTION_STRING);
            config.setUsername(DatabaseConfig.DB_USER_NAME);
            config.setPassword(DatabaseConfig.DB_PASSWORD);
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);
    public static Connection getConnection() throws StoreException {

        if (dataSource == null) {

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        }
            config.setPassword(DatabaseConfig.DB_PASSWORD);
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);
    public static Connection getConnection() throws StoreException {

        if (dataSource == null) {

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        }
            config.setPassword(DatabaseConfig.DB_PASSWORD);
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);
    public static Connection getConnection() throws StoreException {

        if (dataSource == null) {

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        }
            config.setPassword(DatabaseConfig.DB_PASSWORD);
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);
    public static Connection getConnection() throws StoreException {

        if (dataSource == null) {

        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        }
public class DBUtil {

    private static Connection connection;

    static {

        try {

            Class.forName(DatabaseConfig.DRIVER_NAME);
            
            connection = DriverManager.getConnection(DatabaseConfig.CONNECTION_STRING, DatabaseConfig.DB_USER_NAME,
                    DatabaseConfig.DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {

            e.printStackTrace();

        }

    }// End of static block

    public static Connection getConnection() throws StoreException {

        if (connection == null) {
            throw new StoreException(ResponseCode.DATABASE_CONNECTION_FAILURE);
        }

        return connection;
    }

}
