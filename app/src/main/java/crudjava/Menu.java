package crudjava;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {

  static void clearTerminal() {
    // ANSI escape code to clear the screen
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  static void alunoMenu(Scanner scanner) {
    // clearTerminal();
    boolean running = true;

    while (running) {
      System.out.println("Escolha uma opção:");
      System.out.println("C - Inserir Aluno");
      System.out.println("R - Consultar Aluno");
      System.out.println("U - Atualizar Aluno");
      System.out.println("D - Deletar Aluno");
      System.out.println("L - Listar tabela Aluno");
      System.out.println("X - Voltar");

      try {
        char option = Character.toUpperCase(scanner.next().charAt(0));
        scanner.nextLine(); // Limpeza de buffer

        switch (option) {
          case 'C':
            System.out.println("Digite o nome do aluno que deve ser inserido: ");
            Aluno aluno = new Aluno(scanner.nextLine());

            if (aluno.getNome() == null) {
              break;
            }

            DatabaseUtil.executeWithConnection(connection -> AlunoDAO.createAluno(connection, aluno));
            break;

          case 'R':
            System.out.println("Digite o id do aluno que deve ser lido: ");
            int idAlunoToRead = scanner.nextInt();

            Aluno alunoToRead = DatabaseUtil
                .executeWithConnectionReturn(connection -> AlunoDAO.readAluno(connection, idAlunoToRead));

            if (alunoToRead != null) {
              System.out.print("ID: " + idAlunoToRead);
              System.out.println(" Nome: " + alunoToRead.getNome() + "\n");
              for (Nota nota : alunoToRead.getNotas()) {
                System.out.println("Nota: " + nota.getResultado());
              }
              System.out.println();
            }
            break;

          case 'U':
            System.out.println("Digite o id do aluno:");
            int idAlunoToUpdate = scanner.nextInt();

            if (DatabaseUtil
                .executeWithConnectionReturn(
                    connection -> !UtilsDAO.isIDPresent(connection, "aluno", idAlunoToUpdate))) {
              System.out.println("ID de aluno não encontrado\n");
              break;
            }

            scanner.nextLine();
            System.out.println("Digite o novo nome do aluno:");
            Aluno alunoToUpdate = new Aluno(scanner.nextLine());

            if (alunoToUpdate.getNome() == null) {
              System.out.println("Nome de aluno invalido. Atualização interrompida. \n");
              break;
            }

            if (DatabaseUtil.executeWithConnectionReturn(
                connection -> AlunoDAO.isNamePresent(connection, alunoToUpdate.getNome()))) {
              System.out.println("Aluno com nome " + alunoToUpdate.getNome() + " já cadastrado.\n");
              break;
            }

            DatabaseUtil
                .executeWithConnection(connection -> AlunoDAO.updateAluno(connection, alunoToUpdate, idAlunoToUpdate));
            break;

          case 'D':
            System.out.println("Digite o indice do aluno:");
            int idAlunoToDelete = scanner.nextInt();
            scanner.nextLine();

            if (DatabaseUtil
                .executeWithConnectionReturn(
                    connection -> !UtilsDAO.isIDPresent(connection, "aluno", idAlunoToDelete))) {
              System.out.println("ID não encontrado\n");
              break;
            }

            DatabaseUtil.executeWithConnection(connection -> AlunoDAO.deleteAluno(connection, idAlunoToDelete));
            break;
          case 'X':
            running = false;
            System.out.println("Saindo de Aluno...\n");
            break;
          case 'L':
            System.out.println("Listando tabela aluno...");
            System.out.println();

            DatabaseUtil.executeWithConnection(connection -> AlunoDAO.listAluno(connection));
            break;

          default:
            System.out.println("Opção inválida");
            break;
        }
      } catch (NoSuchElementException e) {
        System.out.println("Nenhuma entrada disponivel. Por favor, tente novamente.");
        scanner.next();
      } catch (Exception e) {
        System.out.println("Ocorreu um erro. " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  static void notaMenu(Scanner scanner) {
    // clearTerminal();
    boolean running = true;

    while (running) {
      System.out.println("Escolha uma opção:");
      System.out.println("C - Inserir nota");
      System.out.println("R - Consultar nota");
      System.out.println("U - Atualizar nota");
      System.out.println("D - Deletar nota");
      System.out.println("L - Listar tabela Nota");
      System.out.println("X - Voltar");

      try {
        char option = Character.toUpperCase(scanner.next().charAt(0));
        scanner.nextLine();

        switch (option) {
          case 'C':
            System.out.println("Digite o resultado da nota que deve ser inserido:");
            String resultadoToInsert = scanner.next();

            System.out.println("Digite o ID do aluno da nota que deve ser inserido:");
            int idAlunoToInsert = scanner.nextInt();

            Nota notaToInsert = new Nota(resultadoToInsert, idAlunoToInsert);

            if (notaToInsert.getResultado() == -1 || notaToInsert.getIdAluno() == 0)
              break;

            DatabaseUtil
                .executeWithConnection(connection -> NotaDAO.insertNota(connection, notaToInsert));
            break;

          case 'R':
            System.out.println("Digite o ID da nota que deve ser lida: ");
            int idNotaToRead = scanner.nextInt();

            if (DatabaseUtil
                .executeWithConnectionReturn(connection -> !UtilsDAO.isIDPresent(connection, "nota", idNotaToRead))) {
              System.out.println("ID de nota não encontrado\n");
              break;
            }

            Aluno alunoToRead = DatabaseUtil
                .executeWithConnectionReturn(connection -> NotaDAO.readNota(connection, idNotaToRead));

            if (alunoToRead != null) {
              System.out.println("ID da nota: " + idNotaToRead);
              for (Nota nota : alunoToRead.getNotas()) {
                System.out.println("Nota: " + nota.getResultado());
              }
              System.out.println("Aluno: " + alunoToRead.getNome() + "\n");
              System.out.println();
            }

            break;
          case 'U':
            System.out.println("Digite o ID da nota:");
            int idNotaToUpdate = scanner.nextInt();

            if (DatabaseUtil
                .executeWithConnectionReturn(
                    connection -> !UtilsDAO.isIDPresent(connection, "nota", idNotaToUpdate))) {
              System.out.println("ID de nota não encontrado.\n");
              break;
            }

            System.out.println("Digite o novo valor da nota:");
            String resultadoToUpdate = scanner.next();

            System.out.println("Digite o novo ID do aluno da nota:");
            int idAlunoToUpdate = scanner.nextInt();

            Nota notaToUpdate = new Nota(resultadoToUpdate, idAlunoToUpdate);

            if (notaToUpdate.getResultado() == -1.00 || notaToUpdate.getIdAluno() == 0) {
              break;
            }

            DatabaseUtil
                .executeWithConnection(
                    connection -> NotaDAO.updateNota(connection, notaToUpdate, idNotaToUpdate, idAlunoToUpdate));
            break;
          case 'D':
            System.out.println("Digite a nota:");
            int notaToDelete = scanner.nextInt();

            DatabaseUtil.executeWithConnection(connection -> NotaDAO.deleteNota(connection, notaToDelete));
            break;
          case 'X':
            running = false;
            System.out.println("Saindo de Nota...\n");
            break;
          case 'L':
            System.out.println("Listando tabela nota...");
            System.out.println();

            DatabaseUtil.executeWithConnection(connection -> NotaDAO.listNota(connection));
            break;
          default:
            System.out.println("Opção inválida");
            break;
        }
      } catch (NoSuchElementException e) {
        System.out.println("Nenhuma entrada disponivel. Por favor, tente novamente.");
        scanner.next();
      } catch (Exception e) {
        System.out.println("Ocorreu um erro. " + e.getMessage());
        e.printStackTrace();
      }
    }
  }

  static void tableMenu(Scanner scanner) {
    // clearTerminal();
    boolean running = true;

    while (running) {
      System.out.println("Escolha uma opção:");
      System.out.println("A - Aluno");
      System.out.println("N - Nota");
      System.out.println("X - Sair");

      try {
        char option = Character.toUpperCase(scanner.next().charAt(0));
        scanner.nextLine();

        switch (option) {
          case 'A':
            alunoMenu(scanner);
            break;
          case 'N':
            notaMenu(scanner);
            break;
          case 'X':
            running = false;
            System.out.println("Saindo...");
            break;
          default:
            System.out.println("Opção inválida!\n");
            break;
        }
      } catch (NoSuchElementException e) {
        System.out.println("Nenhuma entrada disponivel. Por favor, tente novamente.");
        scanner.next();
      } catch (Exception e) {
        System.out.println("Ocorreu um erro. " + e.getMessage());
        e.printStackTrace();
      }
    }
  }
}