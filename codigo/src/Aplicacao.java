package src;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

import src.Comparators.ComparatorMidia;
import src.Exceptions.ReadFileError;

public class Aplicacao {
    private static Streaming streaming;
    public static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ReadFileError {
        streaming = new Streaming();
        try {
            streaming.iniciar();
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler arquivos de inicialização");
            return;
        }
        try {
            exibirMenuPrincipal();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void exibirMenuPrincipal() {
        try {
            while (true) {
                System.out.println("== Menu Principal ==");
                System.out.println("1. Cadastrar Cliente");
                System.out.println("2. Login");
                System.out.println("3. Buscar");
                System.out.println("4. Sair");
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
                        buscarMidias();
                        break;
                    case 4:
                        System.out.println("Saindo do programa...");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }

                System.out.println();
            }
        } catch (InputMismatchException e) {
            System.out.println("Insira um valor válido");
            exibirMenuPrincipal();
        }
    }

    private static void cadastrarCliente() {

        System.out.println("== Cadastro de Cliente ==");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Nome de Usuário: ");
        String nomeUsuario = scanner.nextLine();

        String resultado = streaming.cadastrarCliente(nome, senha, nomeUsuario);
        System.out.println(resultado);
    }

    private static void fazerLogin() {

        System.out.println("== Login ==");
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

        while (true) {
            System.out.println("== Menu do Cliente ==");
            System.out.println("1. Buscar");
            System.out.println("2. Adicionar Mídia Futura");
            System.out.println("3. Terminar Mídia");
            System.out.println("4. Avaliar Mídia");
            System.out.println("5. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após a leitura do número

            switch (opcao) {
                case 1:
                    buscarMidias();
                    break;
                case 2:
                    adicionarMidiaFutura();
                    break;
                case 3:
                    terminarMidia();
                    break;
                case 4:
                    // avaliarMidia();
                    break;
                case 5:
                    System.out.println("Saindo do menu do cliente...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }

            System.out.println();
        }
    }

    private static void buscarMidias() {
        System.out.println("Deseja buscar por \nn - Nome g - Gênero  i - Idioma");
        char op = scanner.next().toLowerCase().charAt(0);
        System.out.println("Informe a sua busca:");
        String valor = scanner.next();
        System.out.println("Valor que entra pelo usuário: " + valor);
        System.out.println("");
        switch (op) {
            case 'n':
                System.out.println("== Buscar Séries por Nome ==");
                System.out.println(streaming.buscarMidia(valor, ComparatorMidia.porNome()).toString());
                break;
            case 'g':
                System.out.println("== Buscar Séries por Gênero ==");
                System.out.println(streaming.buscarMidia(valor, ComparatorMidia.porGenero()).toString());
                break;
            case 'i':
                System.out.println("== Buscar Séries por Idioma ==");
                System.out.println(streaming.buscarMidia(valor, ComparatorMidia.porIdioma()).toString());
                break;
            default:
                break;
        }
    }

    private static void adicionarMidiaFutura() {

        System.out.println("== Adicionar Mídia Futura ==");

        // Solicitar informações sobre a mídia e adicioná-la ao cliente atual
        streaming.getClienteLogado();
        System.out.println("Mídia adicionada com sucesso.");
    }

    private static void terminarMidia() {

        System.out.println("== Terminar Mídia ==");

        // Solicitar informações sobre a mídia terminada e atualizar o status no cliente
        // atual

        System.out.println("Mídia atualizada com sucesso.");
    }

    // private static void avaliarMidia() {
    // Scanner scanner = new Scanner(System.in);

    // System.out.println("== Avaliar Mídia ==");
    // ArrayList<Midia> midias = streaming.getClienteLogado().getMidiasAssistidas();
    // int contador = 1;
    // System.out.println("== Selecione a mídia ==");
    // for (Midia midia : midias) {
    // System.out.println(contador + ": " + midia);
    // }
    // Scanner ler = new Scanner(System.in);
    // System.out.println("Escolha uma nota de 1 a 5");
    // int nota = ler.nextInt();
    // Avaliacao avaliacao = new Avaliacao();
    // while (nota <= 0 && nota > 5) {
    // System.out.println("nota invalida, tente novamente");
    // System.out.println("Escolha uma nota de 1 a 5");
    // nota = ler.nextInt();
    // avaliacao = new Avaliacao(nota, midia, streaming.getClienteLogado());
    // }
    // if (streaming.getClienteLogado().getTipoCliente() == "Especialista") {
    // System.out.println("Deseja adicionar um comentario ? S- sim , N- nao");
    // String op = ler.nextLine();
    // do {
    // if (op != "S" && op != "N") {
    // System.out.println("opcao invalida tente novamente");
    // System.out.println("Deseja adicionar um comentario ? S- sim , N- nao");
    // op = ler.nextLine();
    // } else {
    // if (op == "S") {
    // System.out.println("escreva um comentario");
    // String comentario = ler.nextLine();
    // avaliacao.addComentario(comentario);
    // }
    // break;
    // }
    // } while (op != "S" && op != "N");
    // }
    // System.out.println("Mídia avaliada com sucesso.");
    // }
}
