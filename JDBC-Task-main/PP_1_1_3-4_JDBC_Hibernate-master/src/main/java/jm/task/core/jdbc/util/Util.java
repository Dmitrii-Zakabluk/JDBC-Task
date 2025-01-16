package jm.task.core.jdbc.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    public static final class ConnectionManager {
        private static final String PASSWORD_KEY = "db.password";
        private static final String USERNAME_KEY = "db.username";
        private static final String URL_KEY = "db.url";

        static {
            loadDriver();
        }

        private static void loadDriver() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        private ConnectionManager() {
        }

        public static Connection open() {
            try {
                return DriverManager.getConnection(
                        PropertiesUtil.get(URL_KEY),
                        PropertiesUtil.get(USERNAME_KEY),
                        PropertiesUtil.get(PASSWORD_KEY)
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static final class PropertiesUtil {

        private static final Properties PROPERTIES = new Properties();

        static {
            loadProperties();
        }

        private PropertiesUtil() {
        }

        public static String get(String key) {
            return PROPERTIES.getProperty(key);
        }

        private static void loadProperties() {
            try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
                PROPERTIES.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
