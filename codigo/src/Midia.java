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


    @Override
    public String toString() {
        return "Nome Mídia: " + getNome() + "\nGênero: " + getGenero() + "\nIdioma: " + getIdioma();
    }
}
