package crudjava;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class NotaDAO {
  static void insertNota(Connection connection, int id_aluno, int nota) {
    String query = "INSERT INTO nota (, id_aluno) VALUES (?, ?)";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id_aluno);
      pstmt.setInt(2, nota);
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de inserção do aluno " + id_aluno);
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de inserção de nota do aluno " + id_aluno);
      e.printStackTrace();
    }
  }

  static void deleteNota(Connection connection, int id_nota) {
    String query = "DELETE FROM nota WHERE id_nota = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id_nota);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de deleção da nota do aluno " + id_nota);
      e.printStackTrace();
    }

  }

  static void consultaNota(Connection connection) {
    String query = "SELECT id_nota, nota, id_aluno FROM nota";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      System.out.println("Executando a query: " + query);
      ResultSet result = pstmt.executeQuery();

      while (result.next()) {
        System.out.println("ID: " + result.getInt("id_nota"));
        System.out.println("Resultado: " + result.getInt("nota"));
        System.out.println("ID: " + result.getInt("id_aluno"));
      }

    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de consulta");
      e.printStackTrace();
    }
  }
}
