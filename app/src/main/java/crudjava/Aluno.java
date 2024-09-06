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

    if (nome.length() > 100 || nome.length() < 1) {
      this.nome = null;
      return;
    }

    for (char c : nome.toCharArray()) {
      if (!Character.isLetter(c) && c != ' ') {
        this.nome = null;
        return;
      }
    }

    this.nome = nome;
  }

  public void addNota(Nota nota) {
    this.notas.add(nota);
  }

}