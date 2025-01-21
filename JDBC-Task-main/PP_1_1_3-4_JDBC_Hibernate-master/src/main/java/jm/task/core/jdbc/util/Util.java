package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static class HibernateUtil {
        private static SessionFactory sessionFactory;

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    Configuration configuration = new Configuration();

                    Properties settings = new Properties();
                    settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                    settings.put(Environment.URL, "jdbc:mysql://localhost:3305/mysql");
                    settings.put(Environment.USER, "root");
                    settings.put(Environment.PASS, "root");
                    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

                    settings.put(Environment.SHOW_SQL, "true");

                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                    settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                    configuration.setProperties(settings);

                    configuration.addAnnotatedClass(User.class);

                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();

                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sessionFactory;
        }
    }

    public static final class ConnectionManager {
        private static final String PASSWORD_KEY = "db.password";
        private static final String USERNAME_KEY = "db.username";
        private static final String URL_KEY = "db.url";

        static {
            loadDriver();
        }

        private ConnectionManager() {
        }

        private static void loadDriver() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
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

