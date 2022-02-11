package br.com.letscode.java;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Emprestimo {

    private static final int QTD_MAXIMA_LIVROS = 3;
    private static final List<LocalDate> feriados = getListFeriados();

    private Pessoa pessoa;
    private Livro[] livros;
    private final LocalDate dataInicio;
    private LocalDate dataDevolucao;//data efetiva de devolução

    private static List<LocalDate> getListFeriados() {
        return List.of(
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 4, 2),
            LocalDate.of(2021, 4, 21),
            LocalDate.of(2021, 5, 1),
            LocalDate.of(2021, 9, 7),
            LocalDate.of(2021, 10, 12),
            LocalDate.of(2021, 11, 2),
            LocalDate.of(2021, 11, 15),
            LocalDate.of(2021, 12, 25)
        );
    }

    public Emprestimo() {
        this.dataInicio = LocalDate.now();
    }

    public Emprestimo(Pessoa pessoa, Livro[] livros) throws ExcedeuQuantidadeMaximaLivrosException {
        this.pessoa = pessoa;
        this.livros = livros;
        this.dataInicio = LocalDate.now();

        //checar o tamanho do array Livro[] para não ultrapassar o tamanho máximo
        if (livros.length > QTD_MAXIMA_LIVROS) {
           throw new ExcedeuQuantidadeMaximaLivrosException(livros.length, QTD_MAXIMA_LIVROS);
        }
    }

    /**
     * Regra "dataInicio + prazo (Aluno/Professor)"
     * @return a data estimada para devolução
     */
    public LocalDate calcularDataPrevistaDevolucao() {
        var date = this.dataInicio;
        var prazoEmDias = this.pessoa.getPrazoDevolucaoDias();
        int contDiasUteis = 0;

        while (contDiasUteis < prazoEmDias) {
            date = date.plusDays(1);
            boolean fimDeSemana = SATURDAY.equals(date.getDayOfWeek()) || SUNDAY.equals(date.getDayOfWeek());
            if (!fimDeSemana && !feriados.contains(date)) {
                contDiasUteis++;//dia útil
            }
        }
        return date;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Livro[] getLivros() {
        return livros;
    }

    public void setLivros(Livro[] livros) {
        this.livros = livros;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    @Override
    public String toString() {
        return "Emprestimo{" +
            "pessoa=" + pessoa +
            ", livros=" + Arrays.toString(livros) +
            ", dataInicio=" + dataInicio +
            ", dataDevolucao=" + dataDevolucao +
            '}';
    }
}