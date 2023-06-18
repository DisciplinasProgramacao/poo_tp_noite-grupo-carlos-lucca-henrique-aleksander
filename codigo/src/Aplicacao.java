package src;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
//import java.util.Map;
import java.util.Scanner;
//import java.util.stream.Collectors;

import src.Comparators.ComparatorMidia;
import src.Exceptions.AuthorizationException;
import src.Exceptions.InvalidMidiaException;
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

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void exibirMenuPrincipal() {
        limparTela();
        try {
            while (!sair) {
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

        String resultado = streaming.cadastrarCliente(nome, senha, nomeUsuario);
        System.out.println("\u001B[32m" + resultado + "\u001B[37m");
    }

    private static void fazerLogin() {
        limparTela();

        System.out.println("\u001b[38;5;214m== Login ==\u001B[37m");
        System.out.print("Nome de Usuário: ");
        String nomeUsuario = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        String resultado = streaming.login(nomeUsuario, senha);
        System.out.println(resultado);

        if (resultado.equals("Login feito com sucesso")) {
            exibirMenuCliente();
        }
    }

    private static void exibirMenuCliente() {

        limparTela();
        while (!sair) {
            System.out.println("\u001B[33m== Menu do Cliente ==\u001B[37m");
            System.out.println("1. Buscar");
            System.out.println("2. Ver minhas Mídias Assistidas");
            System.out.println("3. Ver minhas Mídias Futuras");
            System.out.println("4. Adicionar Mídia a lista de futuras / Assistir futuramente");
            System.out.println("5. Terminar Mídia");
            System.out.println("6. Avaliar Mídia");
            System.out.println("7. Relatórios");
            System.out.println("\u001B[31m8. Sair	\u001B[37m");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    limparTela();
                    buscarMidias();
                    break;
                case 2:
                    limparTela();
                    verMidiaAssistida();
                    break;
                case 3:
                    limparTela();
                    verMidiaFutura();
                    break;
                case 4:
                    limparTela();
                    adicionarMidiaFutura();
                    break;
                case 5:
                    limparTela();
                    terminarMidia();
                    break;
                case 6:
                    limparTela();
                    avaliarMidia();
                    break;
                case 7:
                    limparTela();
                    exibirMenuRelatorios();
                    break;
                case 8:
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
        try {
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
            System.out.println();
            avaliacao = new Avaliacao(nota, midia, streaming.getClienteLogado(), LocalDate.now());
            streaming.getClienteLogado().avaliar(avaliacao, midia);
            System.out.println("Deseja comentar ? s - sim / n - nao");
            scanner.nextLine();
            String opComen = scanner.nextLine();
            while (opComen.equals("s") && opComen.equals("n")) {
                System.out.println("opcao invalida");
                System.out.println("Deseja comentar ? s - sim / s - nao");
                opComen = scanner.nextLine();
            }

            if (opComen.equals("s")) {
                System.out.println("Qual seu comentario");
                String coment = scanner.nextLine();
                streaming.getClienteLogado().comentar((IComentarista) streaming.getClienteLogado().getTipoCliente(),
                        avaliacao, coment);
            }

            streaming.criarAvaliacao(avaliacao, midia, streaming.getClienteLogado());
            System.out.println("\u001B[32mMídia avaliada com sucesso. \u001B[37m");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void exibirMenuRelatorios() {
        limparTela();
        while (!sair) {
            System.out.println("\u001B[33m== Menu de Relatórios ==\u001B[37m");
            System.out.println("1. Cliente que mais assistiu");
            System.out.println("2. Cliente com mais avaliações");
            System.out.println("3. Porcentagem de clientes com 15 avaliações");
            System.out.println("4. Mídias melhores avaliadas");
            System.out.println("5. Mídias com mais avaliações");
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
                    System.out.println("Cliente que mais assistiu Mídias: " + "\nNome: "
                            + clienteQueMaisAssistiu().getNome() + "\nNome Usuário: "
                            + clienteQueMaisAssistiu().getNomeUsuario()
                            + "\nTotal Mídias: " + clienteQueMaisAssistiu().getMidiasAssistidas().size());
                    break;

                // Qual cliente tem mais avaliações, e quantas avaliações.
                case 2:
                    limparTela();
                    System.out.println("Cliente que mais avaliou Mídias: " + "\nNome: "
                            + clienteComMaisAvaliacoes().getNome() + "\nNome Usuário: "
                            + clienteComMaisAvaliacoes().getNomeUsuario() + "\nTotal de Avaliações: "
                            + clienteComMaisAvaliacoes().getAvaliacoes().size());
                    break;

                // Qual a porcentagem dos clientes com pelo menos 15 avaliações.
                case 3:
                    limparTela();
                    System.out.println("Porcentagem de clientes com pelo menos 15 Avaliações: "
                            + porcClientesMin15avaliacoes() + "%");
                    break;

                // Quais são as 10 mídias com a melhor médias de avaliações e que tenham sido
                // vistas pelo menos 100 vezes, apresentada em ordem descrescente.
                case 4:
                    limparTela();
                    break;

                // Quais são as 10 mídias com mais vizualizações, em ordem descrescente.
                case 5:
                    limparTela();
                    break;

                //
                case 6:
                    limparTela();
                    return;

                //
                case 7:
                    limparTela();

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
        Long result = streaming.getClientes().entrySet().stream()
                .filter(cliente -> cliente.getValue().getAvaliacoes().size() >= 15).count();
        return (double) (result / streaming.getClientes().size()) * 100.0;
    }

}
