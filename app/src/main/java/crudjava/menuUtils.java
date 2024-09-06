package crudjava;

import java.util.Scanner;

public class menuUtils {

  public static void createAluno(Scanner scanner) {
    System.out.println("Digite o nome do aluno que deve ser inserido: ");
    Aluno aluno = new Aluno(scanner.nextLine());

    if (aluno.getNome() == null) {
      return;
    }

    boolean isNamePresent = DatabaseUtil
        .executeWithConnectionReturn(connection -> AlunoDAO.isNamePresent(connection, aluno.getNome()));

    if (isNamePresent) {
      System.err.println("Nome já cadastrado!");
      System.out.println();
      return;
    }
    DatabaseUtil.executeWithConnection(connection -> AlunoDAO.createAluno(connection, aluno));
  }

  public static void createNota(Scanner scanner) {
    System.out.println("Digite o resultado da nota que deve ser inserido:");
    String resultadoToInsert = scanner.next();

    System.out.println("Digite o ID do aluno da nota que deve ser inserido:");
    int idAlunoToInsert = scanner.nextInt();

    Nota notaToInsert = new Nota(resultadoToInsert, idAlunoToInsert);

    if (notaToInsert.getResultado() == -1 || notaToInsert.getIdAluno() == 0)
      return;

    DatabaseUtil
        .executeWithConnection(connection -> NotaDAO.insertNota(connection, notaToInsert));

  }

  public static Aluno read(Scanner scanner, String table) {
    System.out.print("Digite o indice do elemento a ser lido da tabela " + table + ": ");
    int id = scanner.nextInt();

    if (id < 0) {
      System.out.println("ID não pode ser negativo!");
      return null;
    }

    Aluno aluno = null;

    if (!DatabaseUtil.executeWithConnectionReturn(connection -> UtilsDAO.isIDPresent(connection, table, id))) {
      System.out.println("ID não encontrado na tabela " + table);
      return aluno;
    }

    if (table.equals("aluno")) {
      aluno = DatabaseUtil.executeWithConnectionReturn(connection -> AlunoDAO.readAluno(connection, id));
    } else if (table.equals("nota")) {
      aluno = DatabaseUtil.executeWithConnectionReturn(connection -> NotaDAO.readNota(connection, id));
    }
    return aluno;
  }

  public static void updateAluno(Scanner scanner) {
    System.out.println("Digite o id do aluno:");
    int idAlunoToUpdate = scanner.nextInt();

    if (DatabaseUtil
        .executeWithConnectionReturn(
            connection -> !UtilsDAO.isIDPresent(connection, "aluno", idAlunoToUpdate))) {
      System.out.println("ID de aluno não encontrado\n");
      return;
    }

    scanner.nextLine();
    System.out.println("Digite o novo nome do aluno:");
    Aluno alunoToUpdate = new Aluno(scanner.nextLine());

    if (alunoToUpdate.getNome() == null) {
      System.err.println("Nome de aluno invalido. Atualização interrompida. \n");
      return;
    }

    if (DatabaseUtil.executeWithConnectionReturn(
        connection -> AlunoDAO.isNamePresent(connection, alunoToUpdate.getNome()))) {
      System.err.println("Aluno com nome " + alunoToUpdate.getNome() + " já cadastrado.\n");
      return;
    }

    DatabaseUtil
        .executeWithConnection(connection -> AlunoDAO.updateAluno(connection, alunoToUpdate, idAlunoToUpdate));

  }

  public static void updateNota(Scanner scanner) {
    System.out.println("Digite o ID da nota:");
    int idNotaToUpdate = scanner.nextInt();

    if (DatabaseUtil
        .executeWithConnectionReturn(
            connection -> !UtilsDAO.isIDPresent(connection, "nota", idNotaToUpdate))) {
      System.out.println("ID de nota não encontrado.\n");
      return;
    }

    System.out.println("Digite o novo valor da nota:");
    String resultadoToUpdate = scanner.next();

    System.out.println("Digite o novo ID do aluno da nota:");
    int idAlunoToUpdate = scanner.nextInt();

    Nota notaToUpdate = new Nota(resultadoToUpdate, idAlunoToUpdate);

    if (notaToUpdate.getResultado() == -1.00 || notaToUpdate.getIdAluno() == 0) {
      return;
    }

    DatabaseUtil
        .executeWithConnection(
            connection -> NotaDAO.updateNota(connection, notaToUpdate, idNotaToUpdate, idAlunoToUpdate));

  }

  public static void delete(Scanner scanner, String table) {
    System.out.print("Digite o indice do elemento a ser deletado da tabela " + table + ": ");
    int id = scanner.nextInt();

    if (!DatabaseUtil.executeWithConnectionReturn(connection -> UtilsDAO.isIDPresent(connection, table, id))) {
      System.err.println("ID não encontrado na tabela " + table);
      System.out.println();
      return;
    }

    if (table.equals("aluno")) {
      DatabaseUtil.executeWithConnection(connection -> AlunoDAO.deleteAluno(connection, id));
    } else if (table.equals("nota")) {
      DatabaseUtil.executeWithConnection(connection -> NotaDAO.deleteNota(connection, id));
    }
  }
}