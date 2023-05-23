package src;

import java.time.LocalDate;

public class Serie extends Midia {

    private int qtdEpisodios;

    public Serie(String nome, String identificador, LocalDate data, int qtdEpisodios) {
        super(nome, identificador, data);
        this.qtdEpisodios = qtdEpisodios;
    }

    public int getQtdEpisodios() {
        return qtdEpisodios;
    }

    public void setQtdEpisodios(int qtdEpisodios) {
        this.qtdEpisodios = qtdEpisodios;
    }

}