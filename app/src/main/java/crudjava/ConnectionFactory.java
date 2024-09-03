package crudjava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
  private static final String URL = "jdbc:postgresql://localhost:5432/turma";
  private static final String USER = "guia";
  private static final String PASSWORD = "guiaflex";

  public static Connection getConnection() {
    try {
      Connection connection = DriverManager.getConnection(URL,
          USER,
          PASSWORD);

      if (connection != null) {
        System.out.println("Conexão realizada com sucesso!");
        return connection;
      } else {
        System.out.println("Conexão não realizada!");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }
}
