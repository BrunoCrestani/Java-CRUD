package crudjava;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.List;

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
            menuUtils.createAluno(scanner);
            break;

          case 'R':
            Aluno alunoToRead = menuUtils.read(scanner, "aluno");

            if (alunoToRead != null) {
              List<Nota> notas = alunoToRead.getNotas();
              for (int i = 0; i < notas.size(); i++) {
                Nota nota = notas.get(i);
                System.out.println("P" + (i + 1) + " - Nota: " + nota.getResultado());
              }
              System.out.println("Aluno: " + alunoToRead.getNome() + "\n");
              System.out.println();
            }
            break;

          case 'U':
            menuUtils.updateAluno(scanner);
            break;

          case 'D':
            menuUtils.delete(scanner, "aluno");
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
            menuUtils.createNota(scanner);
            break;

          case 'R':
            Aluno alunoToRead = menuUtils.read(scanner, "nota");

            if (alunoToRead != null) {
              List<Nota> notas = alunoToRead.getNotas();
              for (int i = 0; i < notas.size(); i++) {
                Nota nota = notas.get(i);
                System.out.println("Índice: " + i + " - Nota: " + nota.getResultado());
              }
              System.out.println("Aluno: " + alunoToRead.getNome() + "\n");
              System.out.println();
            }

            break;
          case 'U':
            menuUtils.updateNota(scanner);
            break;
          case 'D':
            menuUtils.delete(scanner, "nota");
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