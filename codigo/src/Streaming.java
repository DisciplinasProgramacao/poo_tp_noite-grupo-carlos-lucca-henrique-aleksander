package src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import src.Comparators.ComparatorMidia;
import src.Exceptions.AuthorizationException;
import src.Exceptions.InvalidMidiaException;
import src.Exceptions.ReadFileError;

public class Streaming {
    private Cliente clienteLogado;
    private HashMap<String, Cliente> clientes;
    private HashMap<String, Midia> midias;

    /**
     * Construtor da classe Streaming.
     * Inicializa as variáveis clienteLogado, midias e clientes.
     */
    public Streaming() {
        clienteLogado = null;
        this.midias = new HashMap<>();
        this.clientes = new HashMap<>();
    }

    /**
     * Retorna o mapa de clientes.
     *
     * @return o HashMap de clientes.
     */
    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    /**
     * Lê o arquivo de clientes e cadastra os clientes no sistema.
     *
     * @throws ReadFileError caso ocorra um erro ao ler o arquivo.
     */
    private void lerArquivoClientes() throws ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("espectadores.csv"))) {
            lines.map(line -> line.split(";"))
                    .forEach(values -> cadastrarCliente(values[0], values[2], values[1]));
        } catch (IOException e) {
            throw new ReadFileError();
        }
    }

    /**
     * Lê o arquivo de filmes e cadastra os filmes no sistema.
     *
     * @throws ReadFileError caso ocorra um erro ao ler o arquivo.
     */
    private void lerArquivoFilmes() throws ReadFileError {
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

    /**
     * Lê o arquivo de audiência e atualiza a visualização dos clientes.
     *
     * @throws ReadFileError caso ocorra um erro ao ler o arquivo.
     */
    private void lerArquivoAudiencia() throws ReadFileError {
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

    /**
     * Lê o arquivo de séries e cadastra as séries no sistema.
     *
     * @throws ReadFileError caso ocorra um erro ao ler o arquivo.
     */
    private void lerArquivoSeries() throws IOException, ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("series.csv"))) {
            lines.map(line -> line.split(";"))
                    .forEach(values -> {
                        int qtdEpisodios = Integer.parseInt(values[3]);
                        Midia novaSerie = new Serie(values[1], values[0],
                                LocalDate.parse(values[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                qtdEpisodios);
                        cadastrarMidia(novaSerie);
                    });
        } catch (IOException e) {
            throw new ReadFileError();
        }
    }

    /**
     * Inicia o sistema de streaming, lendo os arquivos e cadastrando os clientes e
     * as mídias.
     *
     * @throws IOException   caso ocorra um erro ao ler os arquivos.
     * @throws ReadFileError caso ocorra um erro ao ler os arquivos.
     */
    public void iniciar() throws IOException, ReadFileError {
        lerArquivoClientes();
        lerArquivoSeries();
        lerArquivoAudiencia();
        lerArquivoFilmes();
    }

    /**
     * Retorna o cliente logado no sistema de streaming.
     *
     * @return o cliente logado.
     */
    public Cliente getClienteLogado() {
        return clienteLogado;
    }

    /**
     * Cadastra um novo cliente no sistema de streaming.
     *
     * @param nome        o nome do cliente.
     * @param senha       a senha do cliente.
     * @param nomeUsuario o nome de usuário do cliente.
     * @return uma mensagem indicando o resultado do cadastro.
     */
    public String cadastrarCliente(String nome, String senha, String nomeUsuario) {
        if (clientes.containsKey(nomeUsuario)) {
            throw new AuthorizationException();
        }
        Cliente cliente = new Cliente(nome, senha, nomeUsuario);
        clientes.put(nomeUsuario, cliente);
        return "Usuário cadastrado com sucesso!";
    }

    /**
     * Busca por mídias no sistema de streaming.
     *
     * @param valor o valor de busca.
     * @param comp  o comparador de mídia.
     * @return uma StringBuilder contendo as informações das mídias encontradas.
     */
    public StringBuilder buscarMidia(String valor, ComparatorMidia comp) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            if (comp.compare(midia, valor) == 0) {
                sb.append(midia.toString());
            }
        }
        return sb;
    }

    /**
     * Cadastra uma nova mídia no sistema de streaming.
     *
     * @param midia a mídia a ser cadastrada.
     * @return uma mensagem indicando o resultado do cadastro.
     */
    public String cadastrarMidia(Midia midia) {
        if (midias.containsKey(midia.getIdentificador())) {
            // throw new InvalidMidiaException("Midia já cadastrada no sistema");
            System.out.println("Midia duplicada: " + midia.getIdentificador());
            return "Midia já cadastrada no sistema.";
        }
        midias.put(midia.getIdentificador(), midia);
        return "Mídia cadastrada com sucesso!";
    }

    /**
     * Exibe as informações de todas as mídias no sistema de streaming.
     */
    public void mostraTodasMidias() {
        midias.forEach((identificador, midia) -> System.out.println(midia.toString()));
    }

    /**
     * Realiza o login de um cliente no sistema de streaming.
     *
     * @param nomeUsuario o nome de usuário do cliente.
     * @param senha       a senha do cliente.
     * @return uma mensagem indicando o resultado do login.
     */
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

    /**
     * Adiciona uma mídia futura para o cliente logado no sistema de streaming.
     *
     * @param midia a mídia futura a ser adicionada.
     */
    public void adicionarMidiaFutura(Midia midia) {
        clienteLogado.adicionarMidiaFutura(midia);
        // Illegal argument aqui
    }

    /**
     * Marca uma mídia como terminada para o cliente logado no sistema de streaming.
     *
     * @param identificador o identificador da mídia a ser terminada.
     */
    public void terminarMidia(String identificador) {
        Midia midiaTerminada = midias.get(identificador);
        if (midiaTerminada == null) {
            throw new InvalidMidiaException(identificador + " e Mídia não existem");
        }
        clienteLogado.terminarMidia(midiaTerminada);
    }
}
