package codigo.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Streaming {
    private Cliente clienteLogado;
    private HashMap<String, Cliente> clientes;
    private HashMap<String, Serie> series;

    public String cadastrarCliente(String nome, String senha, String email){
        if(clientes.containsKey(email)){
            return "Já existe uma conta com esse nome de usuário";
        }
        Cliente cliente = new Cliente(nome, senha, email);
        clientes.put(email, cliente);
        return "Usuário cadastrado com sucesso!";
    }

    public String cadastrarSerie(Serie serie){
        if(series.containsKey(serie.getIdentificador())){
            return "Serie já cadastrada no sistema";
        }
        series.put(serie.getIdentificador(), serie);
        return "Série cadastrada";
    }

    public ArrayList<Serie> buscaSerieGenero(String genero){
        ArrayList<Serie> listaPorGenero = new ArrayList<>();
        for (Map.Entry<String, Serie> entry: series.entrySet()) {
            Serie serie = entry.getValue();
            if (serie.getGenero().contains(genero)) {
                listaPorGenero.add(serie);
            }
        }
        return listaPorGenero;
    }
    
    public ArrayList<Serie> buscaSerieNome(String nome){
        ArrayList<Serie> listaPorNome = new ArrayList<>();
        for (Map.Entry<String, Serie> entry: series.entrySet()) {
           Serie serie = entry.getValue();
           if (serie.getNome().equals(nome)) {
            listaPorNome.add(serie);
           }
        }
        return listaPorNome;
    }
    public ArrayList<Serie> buscaSerieIdioma(String idioma){
        ArrayList<Serie> listaPorIdioma = new ArrayList<>();
        for (Map.Entry<String, Serie> entry : series.entrySet()) {
            Serie serie = entry.getValue();
            if (serie.getIdioma().contains(idioma)) {
                listaPorIdioma.add(serie);
            }
        }
        return listaPorIdioma;
    }


    public String login(String nomeUsuario, String senha){
        if(clientes.containsKey(nomeUsuario)) {
            Cliente autenticar = clientes.get(nomeUsuario);
            if(senha == autenticar.getSenha()){
                clienteLogado = autenticar;
                return "Login feito com sucesso";
            }
                return "Senha incorreta";
        }
        return "Usuário não encontrado";

    }

    // public boolean adicionarSerieFutura(Serie serie){
    //     clienteLogado.adicionarSerieFutura(serie);
    // }

    public void terminarSerie(String identificador){
         Serie serieTerminada = series.get(identificador);
         if(serieTerminada!=null){
            clienteLogado.terminarSerie(serieTerminada);
         }
     }

    // public void iniciarSerie(String identificador){}



}