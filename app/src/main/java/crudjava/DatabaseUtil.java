package crudjava;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseUtil {
  public static void executeWithConnection(Consumer<Connection> consumer) {
    try (Connection connection = ConnectionFactory.getConnection()) {
      if (connection != null) {
        System.out.println("Conexão realizada com sucesso!");
        consumer.accept(connection);
      } else {
        System.out.println("Conexão não realizada! Erro no DatabaseUtil.");
      }
    } catch (SQLException e) {
      System.out.println("Erro ao criar a conexão");
      e.printStackTrace();
    }
  }

  public static <T> T executeWithConnectionReturn(Function<Connection, T> function) {
    try (Connection connection = ConnectionFactory.getConnection()) {
      if (connection != null) {
        System.out.println("Conexão realizada com sucesso!");
        return function.apply(connection);
      } else {
        System.out.println("Conexão não realizada! Erro no DatabaseUtil.");
        return null;
      }
    } catch (SQLException e) {
      System.out.println("Erro ao criar a conexão");
      e.printStackTrace();
      return null;
    }
  }
}