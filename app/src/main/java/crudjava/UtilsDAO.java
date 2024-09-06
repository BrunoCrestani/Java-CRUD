package crudjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilsDAO {

  static boolean isIDPresent(Connection connection, String table, int id) {
    String queryAluno = "SELECT id_aluno FROM " + table + " WHERE id_aluno = ?";
    String queryNota = "SELECT id_nota FROM " + table + " WHERE id_nota = ?";

    String query = "";

    if (table == "aluno") {
      query = queryAluno;
    } else if (table == "nota") {
      query = queryNota;
    }

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

}
