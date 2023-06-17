package src;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import src.Comparators.ComparatorMidia;
import src.Exceptions.AuthorizationException;
import src.Exceptions.IncorrectUserNameOrPasswordException;
import src.Exceptions.InvalidMidiaException;
import src.Exceptions.NameUserExistsException;
import src.Exceptions.ReadFileError;

public class Aplicacao {
    private static Streaming streaming;
    public static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static Scanner scanner = new Scanner(System.in);
    private static boolean sair = false;

    public static void main(String[] args) {
        streaming = new Streaming();
        try {
            streaming.iniciar();
        } catch (InvalidMidiaException e) {
            System.out.println(e.getMessage());
        } catch (IOException | ReadFileError e) {
            e.printStackTrace();
        } finally {
            exibirMenuPrincipal();
        }
    }

    /**
     * Limpar mensagens antigas da tela
     * 
     */
    static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pausa para leitura de mensagens em console
     * 
     */
    static void pausa() {
        System.out.println("Pressione Enter para continuar.");
        System.console().readLine(); // Aguarda o usuário pressionar Enter
    }

    private static void exibirMenuPrincipal() {
        try {
            while (!sair) {
                limparTela();
                System.out.println("\u001B[33m== Menu Principal ==\u001B[37m");
                System.out.println("1. Cadastrar Cliente");
                System.out.println("2. Login");
                System.out.println("\u001B[31m3. Sair	\u001B[37m");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarCliente();
                        break;
                    case 2:
                        fazerLogin();
                        break;
                    case 3:
                        System.out.println("Saindo do programa...");
                        sair = true;
                        break;
                    default:
                        limparTela();
                        System.out.println("\u001B[31mOpção inválida. Tente novamente\u001B[37m");
                        break;
                }
                System.out.println();
            }
        } catch (InputMismatchException e) {
            System.out.println("Insira um valor válido");
            exibirMenuPrincipal();
        } catch (AuthorizationException invalid) {
            System.out.println(invalid.getMessage());
            exibirMenuPrincipal();
        }
    }

    private static void cadastrarCliente() {
        limparTela();
        System.out.println("\u001b[38;5;214m== Cadastro de Cliente ==\u001B[37m");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Nome de Usuário: ");
        String nomeUsuario = scanner.nextLine();

        try {
            String resultado = streaming.cadastrarCliente(nome, senha, nomeUsuario);
            System.out.println("\u001B[32m" + resultado + "\u001B[37m");
        } catch (NameUserExistsException e) {
            System.out.println("\u001B[31mErro: " + e.getMessage() + "\u001B[37m");
        }

        pausa();
    }

    private static void fazerLogin() {
        limparTela();

        System.out.println("\u001b[38;5;214m== Login ==\u001B[37m");
        System.out.print("Nome de Usuário: ");
        String nomeUsuario = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        try {
            String resultado = streaming.login(nomeUsuario, senha);
            System.out.println("\u001B[32m" + resultado + "\u001B[37m");

            if (resultado.equals("Login feito com sucesso")) {
                pausa();
                exibirMenuCliente();
            }
        } catch (IncorrectUserNameOrPasswordException e) {
            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[37m");
            pausa();
        }
    }

    private static void exibirMenuCliente() {
        while (!sair) {
            limparTela();
            System.out.println("\u001B[33m== Menu do Cliente ==\u001B[37m");
            System.out.println("1. Buscar");
            System.out.println("2. Ver minhas Mídias Assistidas");
            System.out.println("3. Ver minhas Mídias Futuras");
            System.out.println("4. Listas minhas Avaliações");
            System.out.println("5. Adicionar Mídia a lista de futuras / Assistir futuramente");
            System.out.println("6. Terminar Mídia");
            System.out.println("7. Avaliar Mídia");
            System.out.println("8. Relatórios");
            System.out.println("\u001B[31m9. Sair	\u001B[37m");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Descarta o caractere de nova linha

            switch (opcao) {
                case 1:
                    buscarMidias();
                    pausa();
                    break;
                case 2:
                    verMidiaAssistida();
                    pausa();
                    break;
                case 3:
                    verMidiaFutura();
                    pausa();
                    break;
                case 4:
                    listarAvaliacoes();
                    pausa();
                    break;
                case 5:
                    adicionarMidiaFutura();
                    pausa();
                    break;
                case 6:
                    terminarMidia();
                    pausa();
                    break;
                case 7:
                    avaliarMidia();
                    pausa();
                    break;
                case 8:
                    exibirMenuRelatorios();
                    pausa();
                    break;
                case 9:
                    System.out.println("Saindo do menu do cliente...");
                    exibirMenuPrincipal();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
            System.out.println();
        }
    }

    private static void listarAvaliacoes() {
        streaming.getClienteLogado().getAvaliacoes().stream().forEach(avaliacao -> System.out.println(avaliacao));
    }

    private static void verMidiaAssistida() {
        limparTela();
        System.out.println("\u001B[32m== Mídias Assistidas ==\u001B[37m \n");
        System.out.println(streaming.getClienteLogado()
                .MostrarListaEspecifica(streaming.getClienteLogado().getMidiasAssistidas()));
        System.out.println("\u001B[32m== Fim da lista ==\u001B[37m ");
    }

    private static void verMidiaFutura() {
        limparTela();
        System.out.println("\u001B[32m== Mídias Futuras ==\u001B[37m \n");
        System.out.println(
                streaming.getClienteLogado().MostrarListaEspecifica(streaming.getClienteLogado().getMidiasFuturas()));
        System.out.println("\u001B[32m== Fim da lista ==\u001B[37m ");
    }

    private static void buscarMidias() {
        limparTela();
        ArrayList<Midia> resultado = null;
        System.out.println("\u001B[32m== Buscar Mídia ==\u001B[37m");
        System.out.println(
                "\u001B[32mn - Nome \u001B[37m ¬ \u001B[35m g - Gênero \u001B[37m ¬ \u001B[33m i - Idioma \u001B[37m");
        try {
            char op = scanner.next().toLowerCase().charAt(0);

            System.out.println("Informe a sua busca:");
            String valor = scanner.next();
            limparTela();

            switch (op) {
                case 'n':
                    System.out.println("\u001B[35m== Buscar Séries por Nome ==\u001B[37m");
                    resultado = streaming.buscarMidia(valor, ComparatorMidia.porNome());
                    break;
                case 'g':
                    System.out.println("\u001B[35m== Buscar Séries por Gênero ==\u001B[37m");
                    resultado = streaming.buscarMidia(valor, ComparatorMidia.porGenero());
                    break;
                case 'i':
                    System.out.println("\u001B[35m== Buscar Séries por Idioma ==\u001B[37m");
                    resultado = streaming.buscarMidia(valor, ComparatorMidia.porIdioma());
                    break;
                default:
                    throw new IllegalArgumentException("Opção inválida. Por favor, escolha uma das opções fornecidas.");
            }

            resultado.stream().forEach(m -> System.out.println(m.toString()));
            System.out.println("\u001B[35m== Fim da Busca ==\u001B[37m");
        } catch (IllegalArgumentException e) {
            System.out.println("\u001B[31mErro: \u001B[37m" + e.getMessage());
        }
    }

    private static Midia buscaMidiaTituloParaMetodos() {
        System.out.print("Título da Mídia: ");
        String titulo = scanner.nextLine();
        ArrayList<Midia> resultado = streaming.buscarMidia(titulo, ComparatorMidia.porNome());
        int cont = 1;
        if (resultado.isEmpty()) {
            return null;
        }
        for (Midia midia : resultado) {
            System.out.println(cont + " - " + midia.toString());
            cont++;
        }
        System.out.println("Qual o numero de sua escolha?");
        int op = scanner.nextInt();
        if (op < 0 || op > resultado.size()) {
            System.out.println("Valor inválido, busque novamente");
            exibirMenuCliente();
        }

        return resultado.get(op - 1);
    }

    private static void adicionarMidiaFutura() {
        limparTela();
        System.out.println("\u001B[32m== Adicionar Mídia Futura ==\u001B[37m");
        Midia retorno = buscaMidiaTituloParaMetodos();
        if (retorno == null) {
            System.out.println("Mídia inválida, tente novamente");
            exibirMenuCliente();
        }
        try {
            streaming.getClienteLogado().adicionarMidiaFutura(retorno);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            exibirMenuCliente();
        }
        System.out.println("\u001B[32mMídia adicionada com sucesso. \u001B[37m");
    }

    private static void terminarMidia() {
        limparTela();
        System.out.println("\u001B[32m== Terminar Mídia ==\u001B[37m");
        Midia retorno = buscaMidiaTituloParaMetodos();
        if (retorno == null) {
            System.out.println("Mídia inválida, tente novamente");
            exibirMenuCliente();
        }
        try {
            streaming.getClienteLogado().terminarMidia(retorno);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            exibirMenuCliente();
        }
        System.out.println("\u001B[32mMídia terminada e adiciona a lista de assistidas. \u001B[37m");
    }

    private static void avaliarMidia() {
        limparTela();
        Avaliacao avaliacao;
        System.out.println("\u001B[32m== Avaliar Mídia ==\u001B[37m");
        ArrayList<Midia> midias = streaming.getClienteLogado().getMidiasAssistidas();
        int contador = 1;
        for (Midia midia : midias) {
            System.out.println(contador + ": " + midia);
            contador++;
        }
        System.out.println("Qual a sua escolha? Digite o numero dela");
        int op = scanner.nextInt();
        Midia midia = streaming.getClienteLogado().getMidiasAssistidas().get(op - 1);
        System.out.println("Escolha uma nota de 1 a 5");
        int nota = scanner.nextInt();
        System.out.println("Qual seu comentario");
        scanner.nextLine();
        String coment = scanner.nextLine();
        if (streaming.getClienteLogado().getTipoCliente() == null) {
            avaliacao = new Avaliacao(nota, midia, streaming.getClienteLogado(), LocalDate.now());
        } else {
            avaliacao = new Avaliacao(nota, coment, midia, streaming.getClienteLogado(), LocalDate.now());
        }
        streaming.criarAvaliacao(avaliacao, midia, streaming.getClienteLogado());
        System.out.println("\u001B[32mMídia avaliada com sucesso. \u001B[37m");
    }

    private static void exibirMenuRelatorios() {
        while (!sair) {
            limparTela();
            System.out.println("\u001B[33m== Menu de Relatórios ==\u001B[37m");
            System.out.println("1. Cliente que mais assistiu");
            System.out.println("2. Cliente com mais Avaliações");
            System.out.println("3. Porcentagem de clientes com 15 Avaliações");
            System.out.println("4. Mídias melhores Avaliadas");
            System.out.println("5. Mídias com mais Vizualizações");
            System.out.println("6. Mídias com melhores avaliadas por categoria");
            System.out.println("7. Mídias com mais avaliações por categoria");
            System.out.println("8. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após a leitura do número

            switch (opcao) {
                // Qual cliente assistiu mais mídias, e quantas mídias.
                case 1:
                    limparTela();
                    System.out.println("\u001b[38;5;214mCliente que mais assistiu Mídias: \u001B[37m" + "\nNome: "
                            + clienteQueMaisAssistiu().getNome() + "\nNome Usuário: "
                            + clienteQueMaisAssistiu().getNomeUsuario()
                            + "\nTotal Mídias: " + clienteQueMaisAssistiu().getMidiasAssistidas().size());
                    pausa();
                    break;

                // Qual cliente tem mais avaliações, e quantas avaliações.
                case 2:
                    limparTela();
                    System.out.println("\u001b[38;5;214mCliente que mais avaliou Mídias: \u001B[37m" + "\nNome: "
                            + clienteComMaisAvaliacoes().getNome() + "\nNome Usuário: "
                            + clienteComMaisAvaliacoes().getNomeUsuario() + "\nTotal de Avaliações: "
                            + clienteComMaisAvaliacoes().getAvaliacoes().size());
                    pausa();
                    break;

                // Qual a porcentagem dos clientes com pelo menos 15 avaliações.
                case 3:
                    limparTela();
                    System.out
                            .println("\u001b[38;5;214mPorcentagem de clientes com pelo menos 15 Avaliações: \u001B[37m"
                                    + String.format("%.5f", porcClientesMin15avaliacoes()) + "%");
                    pausa();
                    break;

                // Quais são as 10 mídias com a melhor médias de avaliações e que tenham sido
                // vistas pelo menos 100 vezes, apresentada em ordem descrescente.
                case 4:
                    limparTela();
                    System.out.println(
                            "\u001b[38;5;214mAs 10 Mídias melhores avaliadas e com 100 vizualizações: \u001B[37m");
                    System.out.println(melhoresAvaliadasEAssistidas());
                    pausa();
                    break;

                // Quais são as 10 mídias com mais vizualizações, em ordem descrescente.
                case 5:
                    limparTela();
                    System.out.println(
                            "\u001b[38;5;214mAs 10 Mídias com mais vizualizações: \u001B[37m");
                    System.out.println(midiasComMaisVizualizacoes());
                    pausa();
                    break;

                //
                case 6:
                    limparTela();

                    pausa();
                    return;

                //
                case 7:
                    limparTela();

                    pausa();
                    return;
                case 8:
                    limparTela();
                    System.out.println("Voltando menu do cliente...");
                    exibirMenuCliente();
                    return;
                default:
                    limparTela();
                    System.out.println("Opção inválida. Tente novamente.");
                    exibirMenuRelatorios();
                    break;
            }
            System.out.println();
        }
    }

    private static Cliente clienteQueMaisAssistiu() {
        return streaming.getClientes().values().stream()
                .max(Comparator.comparingInt(cliente -> cliente.getMidiasAssistidas().size()))
                .orElse(null);
    }

    private static Cliente clienteComMaisAvaliacoes() {
        return streaming.getClientes().values().stream()
                .max(Comparator.comparingInt(cliente -> cliente.getAvaliacoes().size()))
                .orElse(null);
    }

    private static double porcClientesMin15avaliacoes() {
        double result = streaming.getClientes().entrySet().stream()
                .filter(cliente -> cliente.getValue().getAvaliacoes().size() >= 15).count();
        double totalClientes = streaming.getClientes().size();
        return (result / totalClientes) * 100.0;
    }

    private static String melhoresAvaliadasEAssistidas() {
        StringBuilder sb = new StringBuilder();
        List<Midia> results = streaming.getMidias().values().stream()
                .filter(midia -> midia.getAssistidaPorClientes() >= 100)
                .filter(midia -> !midia.getAvaliacoes().isEmpty())
                .sorted(Comparator.comparingDouble(Midia::calculaMediaAvaliacoes).reversed())
                .limit(10)
                .collect(Collectors.toList());

        for (int i = 0; i < results.size(); i++) {
            sb.append("\u001B[31m" + (i + 1) + "º " + "\u001B[37m "
                    + "\u001B[32m" + results.get(i).getNome() + "\u001B[37m"
                    + " ¦   Médias Avaliações: "
                    + String.format("%.2f", results.get(i).calculaMediaAvaliacoes()).replace(",", ".") + "\n");
        }
        return sb.toString();
    }

    private static String midiasComMaisVizualizacoes() {
        StringBuilder sb = new StringBuilder();
        List<Midia> results = streaming.getMidias().values().stream()
                .filter(midia -> midia.getAssistidaPorClientes() != 0)
                .sorted(Comparator.comparingDouble(Midia::getAssistidaPorClientes).reversed())
                .limit(10)
                .collect(Collectors.toList());

        for (int i = 0; i < results.size(); i++) {
            sb.append("\u001B[31m" + (i + 1) + "º " + "\u001B[37m " + "\u001B[32m" + results.get(i).getNome()
                    + "\u001B[37m" + " ¦   Vizualizações: " + results.get(i).getAssistidaPorClientes() + "\n");
        }
        return sb.toString();
    }

}
