package src;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Filme {
    private String nome;
    private String identificador;
    private LocalDate lancamento;
    private int duracao;
    private ArrayList<String> idioma;
    private ArrayList<String> genero;
    private int assistidaPorClientes;

    public Filme(String nome, String identificador, LocalDate lancamento, int duracao){
        this.nome = nome;
        this.identificador = identificador;
        this.lancamento = lancamento;
        this.duracao = duracao;
        this.idioma = new ArrayList<>();
        this.genero = new ArrayList<>();
    }
    public Filme(String nome, ArrayList<String> idioma, ArrayList<String> genero, String identificador, int assistidaPorClientes, LocalDate lancamento) {
        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.identificador = identificador;
        this.assistidaPorClientes = assistidaPorClientes;
        this.lancamento = lancamento;
    }
    public String getLancamento(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return  lancamento.format(formatter);
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
