package codigo;
import java.util.*;
public class Series {
       
    private String nome;
    private String idioma;
    private String genero;
    private String id;
    private int assistidaPorClientes;

    public Series(String nome, String idioma, String genero, String id, int assistidaPorClientes){
        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.id = id;
        this.assistidaPorClientes = assistidaPorClientes;
    }

    public void adicionaAssistido(){
        this.assistidaPorClientes++;
    }

    public int getAssistidaPorClientes() {
        return assistidaPorClientes;
    }
    
}