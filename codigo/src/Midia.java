package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Midia {

    private String nome;
    private Idioma idioma;
    private Genero genero;
    private LocalDate data;
    private String identificador;
    private int assistidaPorClientes;
    private static Random rd = new Random();

    public Midia(String nome, String identificador, LocalDate data) {
        this.nome = nome;
        this.idioma = sorteiaEnum(Idioma.class);
        this.genero = sorteiaEnum(Genero.class);
        this.data = data;
        this.identificador = identificador;
        this.assistidaPorClientes = 0;
    }

    private <T extends Enum<T>> T sorteiaEnum(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();
        int indiceAleatorio = rd.nextInt(values.length);
        T sorteado = values[indiceAleatorio];
        return sorteado;
    }

    public String getData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    public void adicionaAssistido() {
        this.assistidaPorClientes++;
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
        sb.append("Nome: ").append(getNome()).append("\n");
        sb.append("Idioma: ").append(idioma.get(0)).append("\n");
        sb.append("Gênero: ").append(genero.get(0)).append("\n");
        sb.append("Data: ").append(data.format(app.DATA_FORMATTER)).append("\n");
        sb.append("Assista por: ").append(assistidaPorClientes).append("pessoas").append("\n");
        sb.append("Avaliação média: ").append(calculaMediaAvaliacoes()).append("estrelas").append("\n");
        return sb.toString();
    }

}
