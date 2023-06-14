package src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.stream.Stream;

import src.Exceptions.AuthorizationException;
import src.Exceptions.InvalidMidiaException;
import src.Exceptions.ReadFileError;

public class Streaming {
    private Cliente clienteLogado;
    private HashMap<String, Cliente> clientes;
    private HashMap<String, Midia> midias;

    public Streaming() {
        clienteLogado = null;
        this.midias = new HashMap<>();
        this.clientes = new HashMap<>();

    }

    private void lerArquivoClientes() throws FileNotFoundException, ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("espectadores.csv"))) {
            lines.map(line -> line.split(";"))
                    .forEach(values -> cadastrarCliente(values[0], values[2], values[1]));
        } catch (IOException e) {
            throw new ReadFileError();
        }
    }

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    private void lerArquivoSeries() throws FileNotFoundException, ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("series.csv"))) {
            lines.map(line -> line.split(";"))
                    .forEach(values -> {
                        Midia novaSerie = new Serie(values[1], values[0],
                                LocalDate.parse(values[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")), 10);
                        cadastrarMidia(novaSerie);
                    });
        } catch (IOException e) {
            throw new ReadFileError();
        }
    }

    private void lerArquivoAudiencia() throws FileNotFoundException, ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("audiencia.csv"))) {
            lines.forEach(line -> {
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
            });
        } catch (IOException e) {
            throw new ReadFileError();
        }
    }

    private void lerArquivoFilmes() throws FileNotFoundException, ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("filmes.csv"))) {
            lines.forEach(line -> {
                String[] values = line.split(";");
                String identificador = values[0];
                String nome = values[1];
                String lancamento = values[2];
                int duracao = Integer.parseInt(values[3]);
                Midia novoFilme = new Filme(nome, identificador,
                        LocalDate.parse(lancamento, DateTimeFormatter.ofPattern("dd/MM/yyyy")), duracao);
                cadastrarMidia(novoFilme);
            });
        } catch (IOException e) {
            throw new ReadFileError();
        }
    }

    public void iniciar() throws FileNotFoundException, ReadFileError {
        lerArquivoClientes();
        lerArquivoSeries();
        lerArquivoAudiencia();
        lerArquivoFilmes();
    }

    public Cliente getClienteLogado() {
        return clienteLogado;
    }

    public String cadastrarCliente(String nome, String senha, String nomeUsuario) {
        if (clientes.containsKey(nomeUsuario)) {
            throw new AuthorizationException();
        }
        Cliente cliente = new Cliente(nome, senha, nomeUsuario);
        clientes.put(nomeUsuario, cliente);
        return "Usuário cadastrado com sucesso!";
    }

    public String cadastrarMidia(Midia midia) {
        if (midias.containsKey(midia.getIdentificador())) {
            throw new InvalidMidiaException("Midia já cadastrada no sistema");
        }
        midias.put(midia.getIdentificador(), midia);
        return "Midia cadastrada";
    }

    public void mostraTodasMidias() {
        midias.forEach((identificador, midia) -> System.out.println(midia.toString()));
    }

    // // Temos que fazer esse metodos de busca serem genericos
    // public ArrayList<Midia> buscaSerieGeneroSerie(String genero) {
    // ArrayList<Midia> listaPorGenero = new ArrayList<>();
    // for (Map.Entry<String, Midia> entry : midias.entrySet()) {
    // Midia midia = entry.getValue();
    // if (midia.getGenero().contains(genero)) {
    // listaPorGenero.add(midia);
    // }
    // }
    // return listaPorGenero;
    // }

    // // Temos que fazer esse metodos de busca serem genericos
    // public ArrayList<Midia> buscaSerieNomeSerie(String nome) {
    // ArrayList<Midia> listaPorNome = new ArrayList<>();
    // for (Map.Entry<String, Midia> entry : midias.entrySet()) {
    // Midia midia = entry.getValue();
    // if (midia.getNome().equals(nome)) {
    // listaPorNome.add(midia);
    // }
    // }
    // return listaPorNome;
    // }

    // // Temos que fazer esse metodos de busca serem genericos
    // public ArrayList<Midia> buscaSerieIdiomaSerie(String idioma) {
    // ArrayList<Midia> listaPorIdioma = new ArrayList<>();
    // for (Map.Entry<String, Midia> entry : midias.entrySet()) {
    // Midia midia = entry.getValue();
    // if (midia.getIdioma().contains(idioma)) {
    // listaPorIdioma.add(midia);
    // }
    // }
    // return listaPorIdioma;
    // }

    // public <T> ArrayList<Midia> buscarFilme(T criterio) {
    // ArrayList<Midia> resultados = new ArrayList<>();

    // for (Map.Entry<String, Midia> entry : midias.entrySet()) {
    // Midia midia = entry.getValue();
    // if (criterio instanceof String) {
    // if (midia.getNome().equalsIgnoreCase((String) criterio)) {
    // resultados.add(midia);
    // }
    // } else if (criterio instanceof String[]) {
    // String[] arrayTexto = (String[]) criterio;
    // if (contemGenerosOuIdiomas(midia, arrayTexto)) {
    // resultados.add(midia);
    // }
    // } else if (criterio instanceof Midia ) {
    // if (midia.equals((Midia) criterio)) {
    // resultados.add(midia);
    // }
    // }
    // }

    // return resultados;
    // }

    // private boolean contemGenerosOuIdiomas(Midia midia, String[] arrayTexto) {
    // for (String texto : arrayTexto) {
    // if (contemValor(midia.getGenero(), texto) || contemValor(midia.getIdioma(),
    // texto)) {
    // return true;
    // }
    // }
    // return false;
    // }

    // private boolean contemValor(ArrayList<String> array, String valor) {
    // for (String elemento : array) {
    // if (elemento.equals(valor)) {
    // return true;
    // }
    // }
    // return false;
    // }

    public String login(String nomeUsuario, String senha) {
        if (clientes.containsKey(nomeUsuario)) {
            Cliente autenticar = clientes.get(nomeUsuario);
            System.out.println("Senha: " + autenticar.getSenha());
            if (senha.equals(autenticar.getSenha())) {
                clienteLogado = autenticar;
                return "Login feito com sucesso";
            }
        }
           throw new AuthorizationException();
    }

    public void adicionarMidiaFutura(Midia midia) {
        clienteLogado.adicionarMidiaFutura(midia);
        // illegal argument aqui
    }

    public void terminarMidia(String identificador) {
        Midia midiaTerminada = midias.get(identificador);
        if (midiaTerminada == null) {
            throw new InvalidMidiaException(identificador + "Mídia não existe");
        }
        clienteLogado.terminarMidia(midiaTerminada);
    }
}