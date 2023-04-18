package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Streaming {
    public Cliente clienteLogado;
    public HashMap<String, Cliente> clientes;
    public HashMap<String, Serie> series;

    public String cadastrarCliente(String nomeUsuario, String nome, String senha){
        if(clientes.containsKey(nomeUsuario)){
            return "Já existe uma conta com esse nome de usuário";
        }
        Cliente cliente = new Cliente(nomeUsuario, nome, senha);
        clientes.put(nomeUsuario, cliente);
        return "Usuário cadastrado com sucesso!";
    }

    public String cadastrarSerie(Serie serie){
        if(series.containsKey(serie.getIdentificador())){
            return "Serie já cadastrada no sistema";
        }
        series.put(serie.getIdentificador(), serie);
        return "Série cadastrada";
    }

    public ArrayList<Serie> buscaSerieGenero(String genero){}

    public ArrayList<Serie> buscaSerieNome(String nome){}
    public ArrayList<Serie> buscaSerieIdioma(String idioma){}

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

    public boolean adicionarSerieFutura(Serie serie){
        clienteLogado.adicionarSerieFutura(serie);
    }

    public boolean terminarSerie(String identificador){
        Serie serieTerminada = series.get(identificador);
        clienteLogado.terminarSerie(serieTerminada);
    }

    public void iniciarSerie(String identificador){}



}