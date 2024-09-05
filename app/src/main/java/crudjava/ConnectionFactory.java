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
      return DriverManager.getConnection(URL,
          USER,
          PASSWORD);
    } catch (SQLException e) {
      System.out.println("Erro ao criar a conexão");
      e.printStackTrace();
    }

    return null;
  }
}
