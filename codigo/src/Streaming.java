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
    private HashMap<String, Serie> series;
    private HashMap<String, Midia> midias;

    public Streaming() {
        clienteLogado = null;
        this.midias = new HashMap<>();
        this.clientes = new HashMap<>();
        this.series = new HashMap<>();
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
                Midia novoFilme = new Filme(nome, identificador, null, null,
                        LocalDate.parse(lancamento, DateTimeFormatter.ofPattern("dd/MM/yyyy")), 10);
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
        if (series.containsKey(midia.getIdentificador())) {
            return "Midia já cadastrada no sistema";
        }
        midias.put(midia.getIdentificador(), midia);
        return "Midia cadastrada";
    }

    // Temos que fazer esse metodos de busca serem genericos
    public ArrayList<Serie> buscaSerieGeneroSerie(String genero) {
        ArrayList<Serie> listaPorGenero = new ArrayList<>();
        for (Map.Entry<String, Serie> entry : series.entrySet()) {
            Serie serie = entry.getValue();
            if (serie.getGenero().contains(genero)) {
                listaPorGenero.add(serie);
            }
        }
        return listaPorGenero;
    }

    // Temos que fazer esse metodos de busca serem genericos
    public ArrayList<Serie> buscaSerieNomeSerie(String nome) {
        ArrayList<Serie> listaPorNome = new ArrayList<>();
        for (Map.Entry<String, Serie> entry : series.entrySet()) {
            Serie serie = entry.getValue();
            if (serie.getNome().equals(nome)) {
                listaPorNome.add(serie);
            }
        }
        return listaPorNome;
    }

    // Temos que fazer esse metodos de busca serem genericos
    public ArrayList<Serie> buscaSerieIdiomaSerie(String idioma) {
        ArrayList<Serie> listaPorIdioma = new ArrayList<>();
        for (Map.Entry<String, Serie> entry : series.entrySet()) {
            Serie serie = entry.getValue();
            if (serie.getIdioma().contains(idioma)) {
                listaPorIdioma.add(serie);
            }
        }
        return listaPorIdioma;
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