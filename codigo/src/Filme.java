package src;

import java.time.LocalDate;
public class Filme extends Midia {
    private int duracao;

    public Filme(String nome, String identificador, LocalDate data, int duracao) {
        super(nome, identificador, data);
        this.duracao = duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public int getDuracao() {
        return duracao;
    }

}
