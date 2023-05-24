package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Midia implements Comparable<Midia> {

    private String nome;
    private Idioma idioma;
    private Genero genero;
    private LocalDate data;
    private String identificador;
    private int assistidaPorClientes;
    private ArrayList<Avaliacao> avaliacoes;

    private static Random rd = new Random();

    public Midia(String nome, String identificador, LocalDate data) {
        this.nome = nome;
        this.idioma = sorteiaEnum(Idioma.class);
        this.genero = sorteiaEnum(Genero.class);
        this.data = data;
        this.identificador = identificador;
        this.assistidaPorClientes = 0;
        this.avaliacoes = new ArrayList<>(null);
    }

    private <T extends Enum<T>> T sorteiaEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        int indiceAleatorio = rd.nextInt(values.length);
        return values[indiceAleatorio];
    }

    public String getData() {
        return data.format(app.DATA_FORMATTER);
    }

    public void adicionaAssistido() {
        this.assistidaPorClientes++;
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void addAvaliacaoToAvaliacoesList(Avaliacao avaliacao) {
        avaliacoes.add(avaliacao);
    }

    public String getNome() {
        return nome;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getAssistidaPorClientes() {
        return assistidaPorClientes;
    }

    public double calculaMediaAvaliacoes() {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }

        double totalAvaliacoes = 0;
        for (Avaliacao avaliacao : avaliacoes) {
            totalAvaliacoes += avaliacao.getAvaliacao();
        }
        return (double) totalAvaliacoes / avaliacoes.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Idioma: ").append(idioma).append("\n");
        sb.append("Gênero: ").append(genero).append("\n");
        sb.append("Data: ").append(getData()).append("\n");
        sb.append("Assista por: ").append(assistidaPorClientes).append("pessoas").append("\n");
        sb.append("Avaliação média: ").append(calculaMediaAvaliacoes()).append("estrelas").append("\n");
        return sb.toString();
    
    }
    @Override
    public int compareTo(String parametro) {
        // Comparação pelo nome
        int comparacaoNome = this.nome.compareTo(parametro);
        if (comparacaoNome != 0) {
            return comparacaoNome;
        }

        // Comparação pelo idioma (enum)
        int comparacaoIdioma = this.idioma.getDescricao().compareTo(parametro);
        if (comparacaoIdioma != 0) {
            return comparacaoIdioma;
        }

        // Comparação pelo gênero (enum)
        return this.genero.getDescricao().compareTo(parametro);
    }

}
