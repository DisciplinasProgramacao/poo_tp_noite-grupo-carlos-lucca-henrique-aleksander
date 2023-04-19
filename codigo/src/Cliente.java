package codigo.src;
import java.util.*;

public class Cliente
 {
    private String nome;
    private String senha;
    private String email;
    private ArrayList<Serie> seriesFuturas;
    private ArrayList<Serie> seriesAssistidas;



    public Cliente(String nome, String senha, String email){
        this.nome=nome;
        this.senha=senha;
        this.email=email;
        this.seriesFuturas = new ArrayList<>();
        this.seriesAssistidas = new ArrayList<>();
    }

    

    public void adicionarSerieFutura(Serie serie){
        this.seriesFuturas.add(serie);

    }
  
    public void terminarSerie(Serie serie){
        if(!seriesAssistidas.contains(serie)){
            this.seriesAssistidas.add(serie);
            serie.adicionaAssistido();
        }
    }
    public Serie buscarSerie(Serie serie){
        
        for (Serie s : this.seriesFuturas) {
            if (s.equals(serie)) {
                return s;
            }
        }
        for (Serie s : this.seriesAssistidas) {
            if (s.equals(serie)) {
                return s;
            }
        }
        return null; 
    }

    public ArrayList<Serie> getSeriesAssistidas() {
        return seriesAssistidas;
    }

    public ArrayList<Serie> getSeriesFuturas() {
        return seriesFuturas;
    }
    public String getSenha() {
        return senha;
    }
    
    
    
}
