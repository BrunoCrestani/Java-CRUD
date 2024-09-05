package crudjava;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class NotaDAO {
  static void insertNota(Connection connection, Nota nota) {
    String query = "INSERT INTO nota (nota) VALUES (?)";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setDouble(1, nota.getResultado());
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de inserção de nota");
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de inserção de nota");
      e.printStackTrace();
    }
  }

  static void readNota(Connection connection) {
    String query = "SELECT id_nota, nota, id_aluno FROM nota";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      ResultSet result = pstmt.executeQuery();

      while (result.next()) {
        System.out.println("ID: " + result.getInt("id_nota"));
        System.out.println("ID do Aluno: " + result.getInt("id_aluno"));
        System.out.println("Resultado: " + result.getDouble("nota"));
      }

    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de consulta da tabela nota");
      e.printStackTrace();
    }
  }

  static void updateNota(Connection connection, Nota nota, int idNota) {
    String query = "UPDATE nota SET (nota) VALUES (?) WHERE id_nota = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setDouble(1, nota.getResultado());
      pstmt.setInt(2, idNota);
      System.out.println("Sucesso ao executar a query de atualização da nota " + idNota);
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de atualização da nota " + idNota);
    }
  }

  static void deleteNota(Connection connection, int idNota) {
    String query = "DELETE FROM nota WHERE id_nota = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, idNota);
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de deleção da nota do aluno " + idNota);
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de deleção da nota do aluno " + idNota);
      e.printStackTrace();
    }
  }
}