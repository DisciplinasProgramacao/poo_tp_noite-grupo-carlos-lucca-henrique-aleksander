package src;

import java.time.LocalDate;

public class Filme extends Midia {

    // #region variável de instância
    private final int duracao;
    // #endregion

    /**
     * Construtor para criar um Filme com nome, identificador, data e duração.
     * Responsável por atribuir valor ao construtor da classe-mãe (Midia) e
     * instancia-la
     * 
     * @param nome          Nome do filme
     * @param identificador Identificador do filme
     * @param data          Data de lançamento do filme
     * @param duracao       Duração do filme
     */
    public Filme(String nome, String identificador, LocalDate data, int duracao) {
        super(nome, identificador, data);
        this.duracao = duracao;
    }

    /**
     * Retorna a duração do filme.
     * 
     * @return int contendo a duração do filme.
     */
    public int getDuracao() {
        return duracao;
    }

}
