package src;

import java.util.ArrayList;

public class Serie {
       
    private String nome;
    private ArrayList<String> idioma;
    private ArrayList<String> genero;
    private String identificador;
    private int assistidaPorClientes;

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

    public Serie(String nome, ArrayList<String> idioma, ArrayList<String> genero, String identificador, int assistidaPorClientes){
        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.identificador = identificador;
        this.assistidaPorClientes = assistidaPorClientes;
    }

    public void adicionaAssistido(){
        this.assistidaPorClientes++;
    }

    public int getAssistidaPorClientes() {
        return assistidaPorClientes;
    }
    
}