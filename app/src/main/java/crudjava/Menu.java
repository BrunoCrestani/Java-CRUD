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
      System.out.println("X - Voltar");

      try {
        char option = Character.toUpperCase(scanner.next().charAt(0));
        scanner.nextLine(); // Clear the buffer

        switch (option) {
          case 'C':
            System.out.println("Digite o nome do aluno que deve ser inserido: ");
            Aluno aluno = new Aluno(scanner.nextLine());

            if (aluno.getNome() == null) {
              System.out.println("Nome de aluno invalido. Criação interrompida. \n");
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
              System.out.println("ID: " + idAlunoToRead);
              System.out.println("Nome: " + alunoToRead.getNome() + "\n");
            }
            break;

          case 'U':
            System.out.println("Digite o id do aluno:");
            int idAlunoToUpdate = scanner.nextInt();

            if (DatabaseUtil
                .executeWithConnectionReturn(connection -> !AlunoDAO.isIDPresent(connection, idAlunoToUpdate))) {
              System.out.println("ID não encontrado\n");
              break;
            }

            scanner.nextLine();
            System.out.println("Digite o novo nome do aluno:");
            Aluno alunoToUpdate = new Aluno(scanner.nextLine());

            DatabaseUtil
                .executeWithConnection(connection -> AlunoDAO.updateAluno(connection, alunoToUpdate, idAlunoToUpdate));
            break;

          case 'D':
            System.out.println("Digite o indice do aluno:");
            int idAlunoToDelete = scanner.nextInt();
            scanner.nextLine();

            if (DatabaseUtil
                .executeWithConnectionReturn(connection -> !AlunoDAO.isIDPresent(connection, idAlunoToDelete))) {
              System.out.println("ID não encontrado\n");
              break;
            }

            DatabaseUtil.executeWithConnection(connection -> AlunoDAO.deleteAluno(connection, idAlunoToDelete));
            break;
          case 'X':
            running = false;
            System.out.println("Saindo de Aluno...\n");
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
      System.out.println("X - Voltar");

      try {
        char option = Character.toUpperCase(scanner.next().charAt(0));
        scanner.nextLine();

        switch (option) {
          case 'C':
            System.out.println("Digite o resultado da nota que deve ser inserido:");
            Nota notaToInsert = new Nota(scanner.nextInt());

            DatabaseUtil
                .executeWithConnection(connection -> NotaDAO.insertNota(connection, notaToInsert));
            break;
          case 'R':
            DatabaseUtil
                .executeWithConnection(connection -> NotaDAO.readNota(connection));
            break;
          case 'U':
            System.out.println("Digite o indice da nota:");
            int idNotaToUpdate = scanner.nextInt();

            System.out.println("Digite o novo valor da nota");
            double resultadoToUpdate = scanner.nextDouble();
            Nota notaToUpdate = new Nota(resultadoToUpdate);

            DatabaseUtil
                .executeWithConnection(
                    connection -> NotaDAO.updateNota(connection, notaToUpdate, idNotaToUpdate));
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