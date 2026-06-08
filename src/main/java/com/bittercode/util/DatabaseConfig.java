
    // In a cloud-native Azure deployment, configuration should be externalized.
    // This class now reads its values from environment variables that can be
    // populated directly or via Azure App Configuration / Key Vault references.

    public static final String DRIVER_NAME = getEnvOrDefault("DB_DRIVER", "org.postgresql.Driver");

    public static final String DB_HOST = getEnvOrDefault("DB_HOST", "localhost");

    // Port is no longer hard coded; it is fully controlled via environment
    // variable so that Azure can assign it dynamically when required.
    public static final String DB_PORT = getEnvOrDefault("DB_PORT", "5432");

    public static final String DB_NAME = getEnvOrDefault("DB_NAME", "bookstore");

    // Username and password are expected to come from environment variables that
    // in turn are backed by Azure Key Vault references.
    public static final String DB_USER_NAME = getEnvOrDefault("DB_USERNAME", "");

    public static final String DB_PASSWORD = getEnvOrDefault("DB_PASSWORD", "");

    public static final String CONNECTION_STRING = DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    private static String getEnvOrDefault(String key, String defaultValue) {
        String value = System.getenv(key);
        return (value == null || value.isEmpty()) ? defaultValue : value;
    }
        }
    }

    public final static String DRIVER_NAME = prop.getProperty("db.driver");
    public final static String DB_HOST = prop.getProperty("db.host");
    public final static String DB_PORT = prop.getProperty("db.port");
    public final static String DB_NAME = prop.getProperty("db.name");
    public final static String DB_USER_NAME = prop.getProperty("db.username");
    public final static String DB_PASSWORD = prop.getProperty("db.password");
    public final static String CONNECTION_STRING = DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

}
