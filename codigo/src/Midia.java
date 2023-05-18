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

}
