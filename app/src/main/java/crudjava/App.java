package crudjava;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            if (connection != null) {
                System.out.println("Conexão realizada com sucesso!");

                NotaDAO.insertNota(connection, 1, 95);
                NotaDAO.insertNota(connection, 4, 90);
                NotaDAO.insertNota(connection, 7, 100);

                // NotaDAO.consultaNota(connection);
                // AlunoDAO.consultaAluno(connection);
                NotaDAO.consultaNota(connection);

            } else {
                System.out.println("Conexão não realizada!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao criar a conexão");
            e.printStackTrace();
        }
    }
}