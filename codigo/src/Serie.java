package src;

import java.time.LocalDate;

public class Serie extends Midia {

    // #region variável de instância
    private int qtdEpisodios;
    // #endregion

    /**
     * Construtor para criar uma Série com nome, identificador, data e quantidade de
     * episódios.
     * Responsável por atribuir valor ao construtor da classe-mãe (Midia) e
     * instancia-la
     * 
     * @param nome          Nome do filme
     * @param identificador Identificador do filme
     * @param data          Data de lançamento do filme
     * @param qtdEpisodios  Quantidade de episódios
     */
    public Serie(String nome, String identificador, LocalDate data, int qtdEpisodios) {
        super(nome, identificador, data);
        this.qtdEpisodios = qtdEpisodios;
    }

    /**
     * Retorna a quantidade de episódios da série.
     * 
     * @return int contendo a quantidade de episódios da série.
     */
    public int getQtdEpisodios() {
        return qtdEpisodios;
    }
}