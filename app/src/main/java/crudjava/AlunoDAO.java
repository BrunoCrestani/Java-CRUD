package crudjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

  static boolean isIDPresent(Connection connection, int id) {
    String query = "SELECT id_aluno FROM aluno WHERE id_aluno = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, id);
      ResultSet result = pstmt.executeQuery();

      return result.next();
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de verificação de ID");
      e.printStackTrace();
      return false;
    }
  }

  static void createAluno(Connection connection, Aluno aluno) {
    String query = "INSERT INTO aluno (nome) VALUES (?)";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, aluno.getNome());
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de inserção do aluno " + aluno.getNome());
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de inserção do aluno " + aluno.getNome());
      e.printStackTrace();
    }
  }

  static Aluno readAluno(Connection connection, int idAluno) {
    String queryAluno = "SELECT id_aluno, nome FROM aluno WHERE Id_aluno = ?";

    String queryNota = "SELECT id_nota, nota FROM nota WHERE id_aluno = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(queryAluno)) {
      pstmt.setInt(1, idAluno);
      ResultSet result = pstmt.executeQuery();

      if (result.next()) {
        return new Aluno(result.getString("nome"));
      } else {
        System.out.println("Aluno com ID " + idAluno + " não encontrado.\n");
        return null;
      }
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de consulta de aluno\n");
      e.printStackTrace();
      return null;
    }
  }

  static void deleteAluno(Connection connection, int idAluno) {
    String query = "DELETE FROM aluno WHERE id_aluno = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, idAluno);
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de deleção de aluno");
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de deleção de aluno");
      e.printStackTrace();
    }
  }

  static void updateAluno(Connection connection, Aluno aluno, int idAluno) {
    String query = "UPDATE aluno SET nome = ? WHERE id_aluno = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, aluno.getNome());
      pstmt.setInt(2, idAluno);
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de atualização do aluno " + aluno.getNome());
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de atualização do aluno " + aluno.getNome());
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