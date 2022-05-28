package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
   private static String url = "jdbc:mysql://localhost:3306/mysql";
   private static String userName = "root";
   private static String password = "Mildway1925russia";
   public static Connection getConnection(){
      Connection connection = null;
      try {
         connection = DriverManager.getConnection(url,userName,password);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
      return connection;
   }// реализуйте настройку соеденения с БД
}
