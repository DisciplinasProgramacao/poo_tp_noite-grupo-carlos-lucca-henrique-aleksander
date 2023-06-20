package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import src.Comparators.ComparatorMidia;
import src.Exceptions.IncorrectUserNameOrPasswordException;
import src.Exceptions.InvalidMidiaException;
import src.Exceptions.NameUserExistsException;
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
     * Retorna o mapa de midias.
     *
     * @return o HashMap de midias.
     */
    public HashMap<String, Midia> getMidias() {
        return midias;
    }

    /**
     * Lê o arquivo de clientes e cadastra os clientes no sistema.
     *
     * @throws ReadFileError caso ocorra um erro ao ler o arquivo.
     */
    private void lerArquivoClientes() throws ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("espectadores.csv"))) {
            lines.map(line -> line.split(";")).forEach(values -> {
                String nomeUsuario = values[1];
                String nome = values[0];
                String senha = values[2];
                char tipo = values[3].charAt(0);
                cadastrarCliente(nome, senha, nomeUsuario, tipo);
            });
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
                String data = values[2];
                int duracao = Integer.parseInt(values[3]);
                char lancamento = values[4].charAt(0);
                Midia novoFilme;
                if (lancamento == 'L') {
                    novoFilme = new Filme(nome, identificador,
                            LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")), duracao, true);
                } else {
                    novoFilme = new Filme(nome, identificador,
                            LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")), duracao, false);
                }
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
                        cliente.adicionarMidiaAssistida(midiaLinha);
                    } else if (tipo == 'F') {
                        cliente.adicionarMidiaFutura(midiaLinha);
                    }
                }
            });
        } catch (IOException e) {
            throw new ReadFileError();
        } catch (Exception err) {
        }
    }

    /**
     * Lê o arquivo de séries e cadastra as séries no sistema.
     *
     * @throws ReadFileError caso ocorra um erro ao ler o arquivo.
     */
    private void lerArquivoSeries() throws ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("series.csv"))) {
            lines.map(line -> line.split(";"))
                    .forEach(values -> {
                        int qtdEpisodios = Integer.parseInt(values[3]);
                        char lancamento = values[4].charAt(0);
                        Midia novaSerie;
                        if (lancamento == 'L') {
                            novaSerie = new Serie(values[1], values[0],
                                    LocalDate.parse(values[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")), qtdEpisodios,
                                    true);
                        } else {
                            novaSerie = new Serie(values[1], values[0],
                                    LocalDate.parse(values[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                    qtdEpisodios, false);
                        }
                        cadastrarMidia(novaSerie);
                    });
        } catch (IOException e) {
            throw new ReadFileError();
        }
    }

    /**
     * Lê o arquivo de avaliações e cadastra as avaliações no sistema.
     *
     * @throws ReadFileError caso ocorra um erro ao ler o arquivo.
     */
    private void lerArquivoAvaliacao() throws ReadFileError {
        try (Stream<String> lines = Files.lines(Paths.get("avaliacoes.csv"))) {
            lines.map(line -> line.split(";"))
                    .forEach(values -> {
                        int avaliacao = Integer.parseInt(values[0]);
                        Midia midia = midias.get(values[1]);
                        Cliente cliente = clientes.get(values[2]);
                        LocalDate data = LocalDate.parse(values[3], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        Avaliacao av;
                        if (values.length == 5) {
                            String comentario = values[4];
                            av = new Avaliacao(avaliacao, comentario, midia, cliente, data);
                        } else {
                            av = new Avaliacao(avaliacao, midia, cliente, data);
                        }
                        criarAvaliacaoArquivo(av, midia, cliente);
                    });
        } catch (IOException e) {
            throw new ReadFileError();
        } catch (Exception e) {
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
        lerArquivoFilmes();
        lerArquivoAudiencia();
        lerArquivoAvaliacao();
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
     * Desloga o cliente.
     *
     */
    public void logout() {
        clienteLogado = null;
    }

    /**
     * Cria uma avaliacao.
     * Cria uma avaliacao.
     *
     * @param midia a mídia a ser cadastrada.
     */
    private void criarAvaliacaoArquivo(Avaliacao avaliacao, Midia midia, Cliente cliente) {
        try {
            if (midia != null && cliente != null) {
                cliente.adicionarMidiaAssistida(midia);
                cliente.avaliar(avaliacao, midia);
                midia.addAvaliacaoToAvaliacoesList(avaliacao);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void criarAvaliacao(Avaliacao avaliacao, Midia midia) {
        clienteLogado.avaliar(avaliacao, midia);
        midia.addAvaliacaoToAvaliacoesList(avaliacao);
    }

    /**
     * Cadastra um novo cliente no sistema de 'streaming'.
     *
     * @param nome        o nome do cliente.
     * @param senha       a senha do cliente.
     * @param nomeUsuario o nome de usuário do cliente.
     * @param tipo        o tipo do cliente (opcional).
     * @return uma mensagem indicando o resultado do cadastro.
     */
    public String cadastrarCliente(String nome, String senha, String nomeUsuario, char tipo) {
        Cliente cliente = new Cliente(nome, senha, nomeUsuario, tipo);
        verificarEAdicionarCliente(cliente);
        return "Usuario cadastrado com sucesso";
    }

    /**
     * Cadastra um novo cliente no sistema de 'streaming'.
     *
     * @param nome        o nome do cliente.
     * @param senha       a senha do cliente.
     * @param nomeUsuario o nome de usuário do cliente.
     * @return uma mensagem indicando o resultado do cadastro.
     * @throws IOException
     */
    public String cadastrarCliente(String nome, String senha, String nomeUsuario) throws IOException {
        Cliente cliente = new Cliente(nome, senha, nomeUsuario);
        boolean sucesso = verificarEAdicionarCliente(cliente);
        if(sucesso){
        String str = nome+";"+nomeUsuario+";"+senha+";R";
        BufferedWriter writer = new BufferedWriter(new FileWriter("espectadores.csv", true));
        writer.newLine();
        writer.append(str);
        writer.close();
        }
        return "Usuario cadastrado com sucesso";
    }

    /**
     * Verifica se o nome de usuário do cliente já existe no sistema.
     * Se não existir, adiciona o cliente ao mapa de clientes.
     *
     * @param cliente o cliente a ser verificado e adicionado.
     * @return uma mensagem indicando o resultado do cadastro.
     */
    private boolean verificarEAdicionarCliente(Cliente cliente) {
        if (clientes.containsKey(cliente.getNomeUsuario())) {
            throw new NameUserExistsException();
        }
        clientes.put(cliente.getNomeUsuario(), cliente);
        return true;
    }

    /**
     * Busca por mídias no sistema de ‘streaming’.
     *
     * @param valor o valor de busca.
     * @param comp  o comparador de mídia.
     * @return uma ArrayList contendo as informações das mídias encontradas.
     */
    public ArrayList<Midia> buscarMidia(String valor, ComparatorMidia comp) {
        ArrayList<Midia> resultado = new ArrayList<>();
        for (Map.Entry<String, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            if (comp.compare(midia, valor) == 0) {
                resultado.add(midia);
            }
        }
        return resultado;
    }

    /**
     * Cadastra uma nova mídia no sistema de ‘streaming’.
     *
     * @param midia a mídia a ser cadastrada.
     * @return uma mensagem indicando o resultado do cadastro.
     */
    public String cadastrarMidia(Midia midia) {
        if (midias.containsKey(midia.getIdentificador())) {
            // throw new InvalidMidiaException("Midia já cadastrada no sistema");
            return "Não";
        }
        midias.put(midia.getIdentificador(), midia);
        return "Mídia cadastrada com sucesso";
    }

    /**
     * Exibe as informações de todas as mídias no sistema de streaming.
     */
    public String mostraTodasMidias() {
        StringBuilder sb = new StringBuilder();
        midias.forEach((identificador, midia) -> sb.append(midia.toString() + "\n"));
        return sb.toString();
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
            if (senha.equals(autenticar.getSenha())) {
                clienteLogado = autenticar;
                return "Login feito com sucesso";
            }
        }
        throw new IncorrectUserNameOrPasswordException();
    }

        /**
     * Adiciona uma mídia futura para o cliente logado no sistema de streaming.
     *
     * @param midia a mídia futura a ser adicionada.
     * @throws IOException
     */
    public ArrayList<Avaliacao> fetchListaAvaliacao() {
        return clienteLogado.getAvaliacoes();
    }

    /**
     * Adiciona uma mídia futura para o cliente logado no sistema de streaming.
     *
     * @param midia a mídia futura a ser adicionada.
     * @throws IOException
     */
    public void adicionarMidiaFutura(Midia midia) throws IOException {
        clienteLogado.adicionarMidiaFutura(midia);
         salvarAudienciaArquivo('F', midia);

        // Illegal argument aqui
    }

    /**
     * Marca uma mídia como terminada para o cliente logado no sistema de streaming.
     *
     * @param identificador o identificador da mídia a ser terminada.
     * @throws IOException
     */
    public void terminarMidia(Midia midia) throws IOException {
        if (midia == null) {
            throw new InvalidMidiaException( "Mídia não existe");
        }
        clienteLogado.terminarMidia(midia);
         salvarAudienciaArquivo('A', midia);
    }

    private void salvarAudienciaArquivo(char letraAouF, Midia midia) throws IOException{
        String str = clienteLogado.getNomeUsuario()+";"+letraAouF+";"+midia.getIdentificador();
        BufferedWriter writer = new BufferedWriter(new FileWriter("audiencia.csv", true));
        writer.newLine();
        writer.append(str);
        writer.close();
    }
}
