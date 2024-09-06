package crudjava;

class Nota {
  private double resultado;
  private int idAluno;

  public Nota(double resultado, int idAluno) {
    setResultado(resultado);
    setIdAluno(idAluno);
  }

  public double getResultado() {
    return resultado;
  }

  public int getIdAluno() {
    return idAluno;
  }

  public void setResultado(double resultado) {

    if (resultado < 0 || resultado > 10) {
      this.resultado = -1.00;
      return;
    }

    this.resultado = Math.floor(resultado * 100) / 100;
  }

  public void setIdAluno(int idAluno) {
    this.idAluno = idAluno;
  }

}
