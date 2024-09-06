package crudjava;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Nota {
  private double resultado = -1;
  private int idAluno = 0;

  public Nota(String resultado, int idAluno) {
    setResultado(resultado);
    setIdAluno(idAluno);
  }

  public double getResultado() {
    return resultado;
  }

  public int getIdAluno() {
    return idAluno;
  }

  public void setResultado(String resultadoStr) {

    if (!resultadoStr.matches("\\d*\\.?\\d+")) {
      System.err.println("Nota inválida! Deve conter apenas números e no máximo um .!");
      System.out.println();
      return;
    }

    try {
      BigDecimal resultado = new BigDecimal(resultadoStr).setScale(2, RoundingMode.DOWN);
      double resultadoDouble = resultado.doubleValue();

      if (resultadoDouble < 0) {
        System.err.println("Nota não pode ser negativa!");
        System.out.println();
        return;
      }

      if (resultadoDouble > 10) {
        System.err.println("Nota não pode ser maior que 10!");
        System.out.println();
        return;
      }

      this.resultado = resultadoDouble;
    } catch (NumberFormatException e) {
      System.err.println("Nota inválida!");
      System.out.println();
    }
  }

  public void setIdAluno(int idAluno) {

    if (idAluno < 0) {
      System.err.println("ID do aluno não pode ser negativo!");
      System.out.println();
      return;
    }

    if (!DatabaseUtil.executeWithConnectionReturn(connection -> UtilsDAO.isIDPresent(connection, "aluno", idAluno))) {
      System.err.println("ID do aluno não encontrado!");
      System.out.println();
      return;
    }

    this.idAluno = idAluno;
  }

}
