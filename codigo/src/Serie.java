package src;

import java.time.LocalDate;
import java.util.ArrayList;

public class Serie extends Midia {

    private int qtdEpisodios;

    public Serie(String nome, String identificador, ArrayList<String> idioma, ArrayList<String> genero,
            LocalDate data, int qtdEpisodios) {
        super(nome, identificador, idioma, genero, data);
        this.qtdEpisodios = qtdEpisodios;
    }

    public int getQtdEpisodios() {
        return qtdEpisodios;
    }

    public void setQtdEpisodios(int qtdEpisodios) {
        this.qtdEpisodios = qtdEpisodios;
    }

}