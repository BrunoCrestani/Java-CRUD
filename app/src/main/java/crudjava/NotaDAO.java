package crudjava;

import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class NotaDAO {
  static void insertNota(Connection connection, Nota nota) {
    String query = "INSERT INTO nota (nota, id_aluno) VALUES (?, ?)";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setDouble(1, nota.getResultado());
      pstmt.setInt(2, nota.getIdAluno());
      pstmt.executeUpdate();
      System.out.println("Sucesso ao executar a query de inserção de nota");
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de inserção de nota");
      e.printStackTrace();
    }
  }

  static Aluno readNota(Connection connection, int idNota) {
    String queryNota = "SELECT id_nota, nota, id_aluno FROM nota WHERE id_nota= ?";
    String queryAluno = "SELECT id_aluno, nome FROM aluno WHERE id_aluno = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(queryNota);
        PreparedStatement pstmtAluno = connection.prepareStatement(queryAluno)) {

      pstmt.setInt(1, idNota);
      ResultSet resultNota = pstmt.executeQuery();

      if (resultNota.next()) {
        Nota nota = new Nota(resultNota.getDouble("nota"), resultNota.getInt("id_aluno"));

        pstmtAluno.setInt(1, nota.getIdAluno());
        ResultSet resultAluno = pstmtAluno.executeQuery();

        if (resultAluno.next()) {
          Aluno aluno = new Aluno(resultAluno.getString("nome"));
          aluno.addNota(nota);
          return aluno;
        }
        return null;
      } else {
        System.out.println("Nota não encontrada");
        return null;
      }
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de consulta da tabela nota");
      e.printStackTrace();
      return null;
    }
  }

  static void updateNota(Connection connection, Nota nota, int idNota, int idAluno) {
    String query = "UPDATE nota SET nota = ?, id_aluno = ? WHERE id_nota = ?";

    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setDouble(1, nota.getResultado());
      pstmt.setInt(2, idAluno);
      pstmt.setInt(3, idNota);
      pstmt.executeUpdate();
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
      System.out.println("Sucesso ao executar a query de deleção da nota de ID " + idNota);
    } catch (SQLException e) {
      System.out.println("Erro ao executar a query de deleção da nota de ID " + idNota);
      e.printStackTrace();
    }
  }

  static void listNota(Connection connection) {
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
}