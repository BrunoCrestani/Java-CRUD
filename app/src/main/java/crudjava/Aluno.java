package crudjava;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
  private String nome;
  private List<Nota> notas;

  public Aluno(String nome) {
    setNome(nome);
    this.notas = new ArrayList<>();
  }

  public String getNome() {
    return nome;
  }

  public List<Nota> getNotas() {
    return notas;
  }

  public void setNome(String nome) {

    nome = nome.trim();

    if (nome.length() > 100) {
      System.err.println("Nome muito grande!");
      System.out.println();
      return;
    }

    if (nome.isEmpty()) {
      System.err.println("Nome não pode ser vazio ou apenas espaços!");
      System.out.println();
      return;
    }

    if (nome.contains("  ")) {
      System.err.println("Nome não pode conter espaços duplos!");
      System.out.println();
      return;
    }

    for (char c : nome.toCharArray()) {
      if (!Character.isLetter(c) && c != ' ') {
        System.err.println("Nome não pode conter números ou caracteres especiais!");
        System.out.println();
        return;
      }
    }

    this.nome = nome;
  }

  public void addNota(Nota nota) {
    this.notas.add(nota);
  }

}