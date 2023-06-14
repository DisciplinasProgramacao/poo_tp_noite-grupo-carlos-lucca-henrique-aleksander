package src;

import src.Exceptions.ReadFileError;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class app {
    private static Streaming streaming;
    public static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void main(String[] args) {
        streaming = new Streaming();
        try {
            streaming.iniciar();
        } catch (FileNotFoundException | ReadFileError e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            exibirMenuPrincipal();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void exibirMenuPrincipal() {
        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("== Menu Principal ==");
                System.out.println("1. Cadastrar Cliente");
                System.out.println("2. Login");
                System.out.println("3. Buscar Séries por Gênero");
                System.out.println("4. Buscar Séries por Nome");
                System.out.println("5. Buscar Séries por Idioma");
                System.out.println("6. Buscar Filmes");
                System.out.println("7. Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> cadastrarCliente();
                    case 2 -> fazerLogin();
                    case 3 -> buscarSeriesPorGenero();
                    case 4 -> buscarSeriesPorNome();
                    case 5 -> buscarSeriesPorIdioma();
                    case 6 -> buscarFilmes();
                    case 7 -> {
                        System.out.println("Saindo do programa...");
                        return;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }

                System.out.println();
            }
        } catch (InputMismatchException e) {
            System.out.println("Insira um valor válido");
            exibirMenuPrincipal();
        }
    }

    private static void cadastrarCliente() {
        Scanner scanner = new Scanner(System.in);

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
        Scanner scanner = new Scanner(System.in);

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
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("== Menu do Cliente ==");
            System.out.println("1. Buscar Séries por Gênero");
            System.out.println("2. Buscar Séries por Nome");
            System.out.println("3. Buscar Séries por Idioma");
            System.out.println("4. Buscar Filmes");
            System.out.println("5. Adicionar Mídia Futura");
            System.out.println("6. Terminar Mídia");
            System.out.println("7. Avaliar Mídia");
            System.out.println("8. Sair");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após a leitura do número

            switch (opcao) {
                case 1 -> buscarSeriesPorGenero();
                case 2 -> buscarSeriesPorNome();
                case 3 -> buscarSeriesPorIdioma();
                case 4 -> buscarFilmes();
                case 5 -> adicionarMidiaFutura();
                case 6 -> terminarMidia();
                case 7 -> avaliarMidia();
                case 8 -> {
                    System.out.println("Saindo do menu do cliente...");
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println();
        }
    }

    private static void buscarSeriesPorGenero() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== Buscar Séries por Gênero ==");
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        // ArrayList<Midia> series = streaming.buscaSerieGeneroSerie(genero);

        // if (series.isEmpty()) {
        // System.out.println("Nenhuma série encontrada com o gênero informado.");
        // } else {
        // System.out.println("Séries encontradas:");
        // for (Midia serie : series) {
        // System.out.println(serie);
        // }
        // }
    }

    private static void buscarSeriesPorNome() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== Buscar Séries por Nome ==");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        // ArrayList<Midia> series = streaming.buscarFilme(nome);

        // if (series.isEmpty()) {
        // System.out.println("Nenhuma série encontrada com o nome informado.");
        // } else {
        // System.out.println("Séries encontradas:");
        // for (Midia serie : series) {
        // System.out.println(serie);
        // }
        // }
    }

    private static void buscarSeriesPorIdioma() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== Buscar Séries por Idioma ==");
        System.out.print("Idioma: ");
        String idioma = scanner.nextLine();

        // ArrayList<Midia> series = streaming.buscaSerieIdiomaSerie(idioma);

        // if (series.isEmpty()) {
        // System.out.println("Nenhuma série encontrada com o idioma informado.");
        // } else {
        // System.out.println("Séries encontradas:");
        // for (Midia serie : series) {
        // System.out.println(serie);
        // }
        // }
    }



    private static void buscarFilmes() {
        // ArrayList<Midia> filmes = streaming.buscarFilme("Nome");

        // if (filmes.isEmpty()) {
        // System.out.println("Nenhum filme encontrado.");
        // } else {
        // System.out.println("Filmes encontrados:");
        // for (Midia filme : filmes) {
        // System.out.println(filme);
        // }
        // }
    }

    private static void adicionarMidiaFutura() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== Adicionar Mídia Futura ==");

        // Solicitar informações sobre a mídia e adicioná-la ao cliente atual
        streaming.getClienteLogado();
        System.out.println("Mídia adicionada com sucesso.");
    }

    private static void terminarMidia() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== Terminar Mídia ==");

        // Solicitar informações sobre a mídia terminada e atualizar o status no cliente
        // atual

        System.out.println("Mídia atualizada com sucesso.");
    }

    private static void avaliarMidia() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("== Avaliar Mídia ==");
        ArrayList<Midia> midias = streaming.getClienteLogado().getMidiasAssistidas();
        int contador = 1;
        System.out.println("== Selecione a mídia ==");
        for (Midia midia : midias) {
            System.out.println(contador + ": " + midia);
        }
        Scanner ler = new Scanner(System.in);
        System.out.println("Escolha uma nota de 1 a 5");
        int nota = ler.nextInt();
        while (nota <= 0 || nota > 5) {
            System.out.println("nota invalida, tente novamente");
            System.out.println("Escolha uma nota de 1 a 5");
            nota = ler.nextInt();
        }
        if (streaming.getClienteLogado().getTipoCliente() == "Especialista") {
            System.out.println("Deseja adicionar um comentario ? S- sim , N- nao");
            String op = ler.nextLine();
            do {
                if (op != "S" && op != "N") {
                    System.out.println("opcao invalida tente novamente");
                    System.out.println("Deseja adicionar um comentario ? S- sim , N- nao");
                    op = ler.nextLine();
                } else {
                    if (op == "S") {
                        System.out.println("escreva um comentario");
                        String comentario = ler.nextLine();
                    }
                    break;
                }
            } while (op != "S" && op != "N");
        }
        System.out.println("Mídia avaliada com sucesso.");
    }
}
