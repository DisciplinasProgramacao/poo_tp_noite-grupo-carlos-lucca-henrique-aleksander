package src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import src.Comparators.ComparatorMidia;
import src.Exceptions.InvalidMidiaException;

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
        try (Stream<String> lines = Files.lines(Paths.get("espectadores.csv"))) {
            lines.map(line -> line.split(";"))
                    .forEach(values -> cadastrarCliente(values[0], values[2], values[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

    private void lerArquivoSeries() throws FileNotFoundException {
        try (Stream<String> lines = Files.lines(Paths.get("series.csv"))) {
            lines.map(line -> line.split(";"))
                    .forEach(values -> {
                        Midia novaSerie = new Serie(values[1], values[0],
                                LocalDate.parse(values[2], DateTimeFormatter.ofPattern("dd/MM/yyyy")), 10);
                        cadastrarMidia(novaSerie);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void lerArquivoAudiencia() throws FileNotFoundException {
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
            throw new RuntimeException(e);
        }
    }

    private void lerArquivoFilmes() throws FileNotFoundException {
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
            throw new RuntimeException(e);
        }
    }

    public void iniciar() throws FileNotFoundException {
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
            return "Já existe uma conta com esse nome de usuário";
        }
        Cliente cliente = new Cliente(nome, senha, nomeUsuario);
        clientes.put(nomeUsuario, cliente);
        return "Usuário cadastrado com sucesso!";
    }

    public StringBuilder buscarMidia(String valor, ComparatorMidia comp){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            if (comp.compare(midia, valor) == 0) {
                sb.append(midia.toString());
            }
        }
        return sb;
    }

    public void cadastrarMidia(Midia midia) {
        if (!midias.containsKey(midia.getIdentificador())) {
            midias.put(midia.getIdentificador(), midia);
        }
    }

    public void mostraTodasMidias() {
        midias.forEach((identificador, midia) -> System.out.println(midia.toString()));
    }

    // public <T> String buscaIdiomaMidia(String valor, Comparator<T> comparator) {
    //     StringBuilder sb = new StringBuilder();
    //     Comparator<String> idiomaComparator = Comparator.comparing(String::toLowerCase);
    //     List<Midia> listaRetorno = listaMidia.stream()
    //             .filter(midia -> idiomaComparator.compare(midia.retornaIdioma(),
    //                     idioma.toLowerCase()) == 0)
    //             .collect(Collectors.toList());
    //     listaRetorno.forEach(e -> sb.append(e.toString()).append(System.lineSeparator()));

    //     return sb.toString();
    // }

    public String login(String nomeUsuario, String senha) {
        if (clientes.containsKey(nomeUsuario)) {
            Cliente autenticar = clientes.get(nomeUsuario);
            System.out.println("Senha: " + autenticar.getSenha());
            if (senha.equals(autenticar.getSenha())) {
                clienteLogado = autenticar;
                return "Login feito com sucesso";
            }
            return "Senha incorreta";
        }
        return "Usuário não encontrado";

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