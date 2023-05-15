package src;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Streaming {
    private Cliente clienteLogado;
    private HashMap<String, Cliente> clientes;
    private HashMap<String, Midia> midias;

    public Streaming() {
        clienteLogado = null;
        this.midias = new HashMap<>();
        this.clientes = new HashMap<>();
 
    }

    private void lerArquivoClientes() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader("espectadores.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String nome = values[0];
                String nomeUsuario = values[1];
                String senha = values[2];
                cadastrarCliente(nome, senha, nomeUsuario);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void lerArquivoSeries() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader("series.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String identificador = values[0];
                String nome = values[1];
                String anoLancamento = values[2];
                Midia novaSerie = new Serie(nome, identificador, null, null,
                        LocalDate.parse(anoLancamento, DateTimeFormatter.ofPattern("dd/MM/yyyy")), 10);
                cadastrarMidia(novaSerie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void lerArquivoAudiencia() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader("audiencia.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String nomeUsuario = values[0];
                char tipo = values[1].charAt(0);
                String identificadorSerie = values[2];
                Cliente cliente = clientes.get(nomeUsuario);
                Midia midiaLinha = midias.get(identificadorSerie);
                if (midiaLinha != null && cliente != null) {
                    if (tipo == 'A') {
                        cliente.terminarMidia(midiaLinha);
                    } else if (tipo == 'F') {
                        cliente.adicionarMidiaFutura(midiaLinha);
                    }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void lerArquivoFilmes() throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader("filmes.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                String identificador = values[0];
                String nome = values[1];
                String lancamento = values[2];
                int duracao = Integer.parseInt(values[3]);
                Midia novoFilme= new Filme(nome, identificador, null, null,
                         LocalDate.parse(lancamento, DateTimeFormatter.ofPattern("dd/MM/yyyy")),duracao);
              
                cadastrarMidia(novoFilme);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void iniciar() throws FileNotFoundException {
        lerArquivoClientes();
        lerArquivoSeries();
        lerArquivoAudiencia();
        lerArquivoFilmes();
    }

    public String cadastrarCliente(String nome, String senha, String nomeUsuario) {
        if (clientes.containsKey(nomeUsuario)) {
            return "Já existe uma conta com esse nome de usuário";
        }
        Cliente cliente = new Cliente(nome, senha, nomeUsuario);
        clientes.put(nomeUsuario, cliente);
        return "Usuário cadastrado com sucesso!";
    }

    public String cadastrarMidia(Midia midia) {
        if (midias.containsKey(midia.getIdentificador())) {
            return "Midia já cadastrada no sistema";
        }
        midias.put(midia.getIdentificador(), midia);
        return "Midia cadastrada";
    }

    // Temos que fazer esse metodos de busca serem genericos
    public ArrayList<Midia> buscaSerieGeneroSerie(String genero) {
        ArrayList<Midia> listaPorGenero = new ArrayList<>();
        for (Map.Entry<String, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            if (midia.getGenero().contains(genero)) {
                listaPorGenero.add(midia);
            }
        }
        return listaPorGenero;
    }

    // Temos que fazer esse metodos de busca serem genericos
    public ArrayList<Midia> buscaSerieNomeSerie(String nome) {
        ArrayList<Midia> listaPorNome = new ArrayList<>();
        for (Map.Entry<String, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            if (midia.getNome().equals(nome)) {
                listaPorNome.add(midia);
            }
        }
        return listaPorNome;
    }

    // Temos que fazer esse metodos de busca serem genericos
    public ArrayList<Midia> buscaSerieIdiomaSerie(String idioma) {
        ArrayList<Midia> listaPorIdioma = new ArrayList<>();
        for (Map.Entry<String, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            if (midia.getIdioma().contains(idioma)) {
                listaPorIdioma.add(midia);
            }
        }
        return listaPorIdioma;
    }


    public <T> ArrayList<Midia> buscarFilme( T criterio) {
        ArrayList<Midia> resultados = new ArrayList<>();

        for (Map.Entry<String, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            if (criterio instanceof String) {
                if (midia.getNome().equalsIgnoreCase((String) criterio)) {
                    resultados.add(midia);
                }
            } else if (criterio instanceof String[]) {
                String[] arrayTexto = (String[]) criterio;
                if (contemGenerosOuIdiomas(midia, arrayTexto)) {
                    resultados.add(midia);
                }
            } else if (criterio instanceof Midia ) {
                if (midia.equals((Midia ) criterio)) {
                    resultados.add(midia);
                }
            }
        }

        return resultados;
    }
   
        private boolean contemGenerosOuIdiomas(Midia midia, String[] arrayTexto) {
            for (String texto : arrayTexto) {
                if (contemValor(midia.getGenero(), texto) || contemValor(midia.getIdioma(), texto)) {
                    return true;
                }
            }
            return false;
        }
    
        private boolean contemValor(ArrayList<String> array, String valor) {
            for (String elemento : array) {
                if (elemento.equals(valor)) {
                    return true;
                }
            }
            return false;
        }
    }

    public String login(String nomeUsuario, String senha) {
        if (clientes.containsKey(nomeUsuario)) {
            Cliente autenticar = clientes.get(nomeUsuario);
            if (senha == autenticar.getSenha()) {
                clienteLogado = autenticar;
                return "Login feito com sucesso";
            }
            return "Senha incorreta";
        }
        return "Usuário não encontrado";

    }

    public void adicionarMidiaFutura(Midia midia) {
        clienteLogado.adicionarMidiaFutura(midia);
    }

    public void terminarMidia(String identificador) {
        Midia midiaTerminada = midias.get(identificador);
        if (midiaTerminada != null) {
            clienteLogado.terminarMidia(midiaTerminada);
        }
    }

}