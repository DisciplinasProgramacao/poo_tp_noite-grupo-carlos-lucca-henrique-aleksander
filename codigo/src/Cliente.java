package src;

import java.util.*;

public class Cliente
 {
    private String nome;
    private String senha;
    private String nomeUsuario;
    private ArrayList<Serie> seriesFuturas;
    private ArrayList<Serie> seriesAssistidas;



    public Cliente(String nome, String senha, String nomeUsuario){
        this.nome=nome;
        this.senha=senha;
        this.nomeUsuario = nomeUsuario;
        this.seriesFuturas = new ArrayList<>();
        this.seriesAssistidas = new ArrayList<>();
    }

    

    public void adicionarSerieFutura(Serie serie){
        boolean contemSerie = seriesFuturas.contains(serie);
        if(!contemSerie) {
            this.seriesFuturas.add(serie);
        }

    }

     public String getNome() {
         return nome;
     }

     public String getNomeUsuario() {
         return nomeUsuario;
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
