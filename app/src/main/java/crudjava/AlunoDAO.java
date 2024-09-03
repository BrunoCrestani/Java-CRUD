package crudjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

  static void insertAluno(Connection connection, String name) {
    String query = "INSERT INTO aluno (nome) VALUES (?)";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, name);
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de inserção do aluno " + name);
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de inserção do aluno " + name);
      e.printStackTrace();
    }
  }

  static void deleteAluno(Connection connection, String name) {
    String query = "DELETE FROM aluno WHERE nome = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, name);
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de deleção do aluno " + name);
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de deleção do aluno " + name);
      e.printStackTrace();
    }
  }

  static void consultaAluno(Connection connection) {
    String query = "SELECT id_aluno, nome FROM aluno";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      ResultSet result = pstmt.executeQuery();

      while (result.next()) {
        System.out.println("ID: " + result.getInt("id_aluno"));
        System.out.println("Nome: " + result.getString("nome"));
      }
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de consulta");
      e.printStackTrace();
    }
  }
}