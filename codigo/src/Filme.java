package src;

import java.time.LocalDate;
import java.util.ArrayList;

public class Filme extends Midia {

    private int duracao;

    public Filme(String nome, String identificador, ArrayList<String> idioma, ArrayList<String> genero,
            LocalDate data, int duracao) {
        super(nome, identificador, idioma, genero, data);
        this.duracao = duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getDuracao() {
        return duracao;
    }

}
