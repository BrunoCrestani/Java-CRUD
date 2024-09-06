package crudjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunoDAO {

  static boolean isNamePresent(Connection connection, String nome) {
    String query = "SELECT nome FROM aluno WHERE nome = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setString(1, nome);
      ResultSet result = pstmt.executeQuery();

      if (result.next()) {
        return true;
      } else {
        return false;
      }
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de verificação de nome\n");
      e.printStackTrace();
      return true;
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

    try (PreparedStatement pstmtAluno = connection.prepareStatement(queryAluno);
        PreparedStatement pstmtNota = connection.prepareStatement(queryNota)) {

      pstmtAluno.setInt(1, idAluno);
      ResultSet resultAluno = pstmtAluno.executeQuery();

      if (resultAluno.next()) {
        Aluno aluno = new Aluno(resultAluno.getString("nome"));

        pstmtNota.setInt(1, idAluno);
        ResultSet resultNota = pstmtNota.executeQuery();

        while (resultNota.next()) {
          Nota nota = new Nota(resultNota.getString("nota"), idAluno);
          aluno.addNota(nota);
        }

        return aluno;
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

  static void deleteAluno(Connection connection, int idAluno) {
    String queryAluno = "DELETE FROM aluno WHERE id_aluno = ?";
    String queryNota = "DELETE FROM nota WHERE id_aluno = ?";

    try (PreparedStatement pstmtNota = connection.prepareStatement(queryNota);
        PreparedStatement pstmtAluno = connection.prepareStatement(queryAluno)) {

      pstmtNota.setInt(1, idAluno);
      pstmtNota.executeUpdate();

      pstmtAluno.setInt(1, idAluno);
      pstmtAluno.executeUpdate();

      System.out.println("Sucesso ao executar a query de deleção do aluno e de suas notas.");
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de deleção de aluno e de suas notas.");
      e.printStackTrace();
    }
  }

  static void listAluno(Connection connection) {
    String query = "SELECT id_aluno, nome FROM aluno";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      ResultSet result = pstmt.executeQuery();

      while (result.next()) {
        System.out.println("ID: " + result.getInt("id_aluno"));
        System.out.println("Nome: " + result.getString("nome"));
      }
      System.out.println();

    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de listagem da tabela aluno.");
      e.printStackTrace();
    }
  }
}