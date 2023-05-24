package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Midia {

    private String nome;
    private ArrayList<String> idioma;
    private ArrayList<String> genero;
    private LocalDate data;
    private String identificador;
    private int assistidaPorClientes;
    private ArrayList<Avaliacao> avaliacoes;

    public Midia(String nome, String identificador, ArrayList<String> idioma, ArrayList<String> genero,
            LocalDate data) {
        this.nome = nome;
        this.idioma = idioma;
        this.genero = idioma;
        this.data = data;
        this.identificador = identificador;
        this.assistidaPorClientes = 0;
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

    public ArrayList<String> getIdioma() {
        return idioma;
    }

    public ArrayList<String> getGenero() {
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
