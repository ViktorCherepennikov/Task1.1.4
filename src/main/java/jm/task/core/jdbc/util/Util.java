package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class Util {
   private static SessionFactory sessionFactory;
   private static String url = "jdbc:mysql://localhost:3306/mysql";
   private static String userName = "root";
   private static String password = "Mildway1925russia";
   private static String driver =  "com.mysql.cj.jdbc.Driver";
   private static String dialect = "org.hibernate.dialect.MySQLDialect";
   public static Connection getConnection(){
      Connection connection = null;
      try {
         connection = DriverManager.getConnection(url,userName,password);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
      return connection;
   }
   public static SessionFactory getSessionFactory() {
      Configuration configuration = new Configuration();
      if(sessionFactory == null) {
         try {
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, driver);
            settings.put(Environment.URL, url);
            settings.put(Environment.USER, userName);
            settings.put(Environment.PASS, password);
            settings.put(Environment.DIALECT, dialect);
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      return sessionFactory;
   } // реализуйте настройку соеденения с БД
}
