package crudjava;

class Nota {
  private int id_nota;
  private int resultado;
  private int id_aluno;

  public Nota(int id_nota, int resultado, int id_aluno) {
    this.id_nota = id_nota;
    this.resultado = resultado;
    this.id_aluno = id_aluno;
  }

  public int getIdNota() {
    return id_nota;
  }

  public int getResultado() {
    return resultado;
  }

  public int getIdAluno() {
    return id_aluno;
  }

  public void setIdNota(int id_nota) {
    this.id_nota = id_nota;
  }

  public void setResultado(int resultado) {
    this.resultado = resultado;
  }

  public void setIdAluno(int id_aluno) {
    this.id_aluno = id_aluno;
  }

}
