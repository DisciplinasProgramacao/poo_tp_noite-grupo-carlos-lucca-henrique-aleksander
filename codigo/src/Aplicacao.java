package src;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
<<<<<<< Updated upstream
=======
import java.util.Comparator;
import java.util.HashMap;
>>>>>>> Stashed changes
import java.util.InputMismatchException;
import java.util.Scanner;

import src.Comparators.ComparatorMidia;
import src.Exceptions.AuthorizationException;
import src.Exceptions.InvalidMidiaException;
import src.Exceptions.ReadFileError;

public class Aplicacao {
    private static Streaming streaming;
    public static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static Scanner scanner = new Scanner(System.in);

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
            while (true) {
                System.out.println("== Menu Principal ==");
                System.out.println("1. Cadastrar Cliente");
                System.out.println("2. Login");
                System.out.println("3. Sair");
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
        } catch (AuthorizationException invalid) {
            System.out.println(invalid.getMessage());
            exibirMenuPrincipal();
        }
    }

    private static void cadastrarCliente() {
        limparTela();

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
        limparTela();

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

        limparTela();
        while (true) {
            System.out.println("== Menu do Cliente ==");
            System.out.println("1. Buscar");
            System.out.println("2. Adicionar Mídia Futura");
            System.out.println("3. Terminar Mídia");
            System.out.println("4. Avaliar Mídia");
            System.out.println("5. Relatórios");
            System.out.println("6. Ver midias vistas");
            System.out.println("7. Ver midias futuras");
            System.out.println("8. Sair");

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
                    adicionarMidiaFutura();
                    break;
                case 3:
                    limparTela();
                    terminarMidia();
                    break;
                case 4:
                    limparTela();
                    avaliarMidia();
                    break;
                case 5:
                    limparTela();
                    exibirMenuRelatorios();
                    break;
                case 6:
                    limparTela();
                    verMidiaAssistida();
                    break;
                case 7:
                    limparTela();
                    verMidiaFutura();
                    break;
                case 8:
                    System.out.println("Saindo do menu do cliente...");
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
        System.out.println(streaming.getClienteLogado().MostrarListaMidiaAssistida());
    }

    private static void verMidiaFutura() {
        limparTela();
        System.out.println(streaming.getClienteLogado().MostrarListaMidiaFutura());
    }

    private static void buscarMidias() {
        limparTela();
        ArrayList<Midia> resultado = null;
        System.out.println("Deseja buscar por \nn - Nome g - Gênero  i - Idioma");
        char op = scanner.next().toLowerCase().charAt(0);
        System.out.println("Informe a sua busca:");
        String valor = scanner.next();
        System.out.println("");
        switch (op) {
            case 'n':
                System.out.println("== Buscar Séries por Nome ==");
                resultado = streaming.buscarMidia(valor, ComparatorMidia.porNome());
                break;
            case 'g':
                System.out.println("== Buscar Séries por Gênero ==");
                resultado = streaming.buscarMidia(valor, ComparatorMidia.porGenero());
                break;
            case 'i':
                System.out.println("== Buscar Séries por Idioma ==");
                resultado = streaming.buscarMidia(valor, ComparatorMidia.porIdioma());
                break;
            default:
                break;
        }
        resultado.stream().forEach(m -> System.out.println(m.toString()));
    }

    private static Midia buscaMidiaTituloParaMetodos() {
        limparTela();
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
        System.out.println("== Adicionar Mídia Futura ==");
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
        System.out.println("Mídia adicionada com sucesso.");
    }

    private static void terminarMidia() {
        limparTela();
        System.out.println("== Terminar Mídia ==");
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
        System.out.println("Mídia adicionada com sucesso.");
    }

    private static void avaliarMidia() {
        limparTela();
        Avaliacao avaliacao;
        System.out.println("== Avaliar Mídia ==");
        ArrayList<Midia> midias = streaming.getClienteLogado().getMidiasAssistidas();
        int contador = 1;
        System.out.println("== Selecione a mídia ==");
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
            avaliacao = new Avaliacao(nota, midia, streaming.getClienteLogado(), LocalDate.now()); // Criar a avaliação}
        } else {
            avaliacao = new Avaliacao(nota, coment, midia, streaming.getClienteLogado(), LocalDate.now()); // Criar a
                                                                                                           // avaliação
        }
        streaming.criarAvaliacao(avaliacao, midia, streaming.getClienteLogado());
        System.out.println("Mídia avaliada com sucesso.");
        System.out.println(avaliacao);
    }

    private static void exibirMenuRelatorios() {
        limparTela();

        while (true) {
            System.out.println("== Relatórios ==");
            System.out.println("1. Cliente que mais assistiu");
            System.out.println("2. Cliente com mais avaliações");
            System.out.println("3. Porcentagem de clientes com 15 avaliações");
            System.out.println("4. Mídias melhores avaliadas");
            System.out.println("5. Mídias com mais avaliações");
            System.out.println("6. Mídias com melhores avaliadas por categoria");
            System.out.println("7. Mídias com mais avaliações por categoria");
            System.out.println("8. Voltar ao menu cliente");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após a leitura do número

            switch (opcao) {
                case 1:
                    // TODO: Implementar funcao
                    break;
                case 2:
                    // TODO: Implementar funcao
                    break;
                case 3:
                    // TODO: Implementar funcao
                    break;
                case 4:
                    // TODO: Implementar funcao
                    break;
                case 5:
                    // TODO: Implementar funcao
                    break;
<<<<<<< Updated upstream
                case 6:
                    // TODO: Implementar funcao
=======

                // Quais são as 10 mídias com mais vizualizações, em ordem descrescente e separadas por genero.
                case 6:
                 limparTela();
                System.out.println("10 mídias mais vistas");
                HashMap<String, ArrayList<Midia>> midiasPorGenero = getTop10MaisVistasPorGenero();
                int indexMidiaVista = 1;
                for (ArrayList<Midia> midiasVistasPorGenero : midiasPorGenero.values()) {
                    for (Midia midia : midiasVistasPorGenero) {
                        System.out.println(indexMidiaVista + "- " + midia);
                        indexMidiaVista++;
                    }
                } 
>>>>>>> Stashed changes
                    return;
                case 7:
<<<<<<< Updated upstream
                    // TODO: Implementar funcao

=======
                    limparTela();
                System.out.println("10 bem avaliadas por genero");
                HashMap<String, ArrayList<Midia>> midiasPorGeneroAvaliacoes = getTop10MaisAvaliadasPorGenero();
                int indexAva = 1;
                for (ArrayList<Midia> midiasVistasPorGenero : midiasPorGeneroAvaliacoes.values()) {
                    for (Midia midia : midiasVistasPorGenero) {
                        System.out.println(indexAva + "- " + midia);
                        indexAva++;
                    }
                } 
>>>>>>> Stashed changes
                    return;
                case 8:
                    System.out.println("Voltando menu do cliente...");
                    exibirMenuCliente();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    exibirMenuRelatorios();
                    break;
            }

            System.out.println();
        }
    }

<<<<<<< Updated upstream
=======
   private static ArrayList<Midia> getMostReviewedMidiasDesc() {
    ArrayList<Midia> midias = new ArrayList<>(streaming.getMidias().values());
    
    ArrayList<Midia> filteredMidias = midias.stream()
        .filter(midia -> midia.getAssistidaPorClientes() >= 100)
        .sorted(Comparator.comparingDouble(Midia::calculaMediaAvaliacoes).reversed())
        .collect(Collectors.toCollection(ArrayList::new));
    
    ArrayList<Midia> top10Midias = new ArrayList<>();
    
    if (filteredMidias.size() >= 10) {
        top10Midias.addAll(filteredMidias.subList(0, 10));
    } else {
        top10Midias.addAll(filteredMidias);
    }
    
    return top10Midias;
}
private static HashMap<String, ArrayList<Midia>> getTop10MaisAvaliadasPorGenero() {
        limparTela();
        System.out.println("10 mídias mais bem avaliadas por genero");
        ArrayList<Midia> top10MidiaAvaliadas = getMostReviewedMidiasDesc();
        HashMap<String, ArrayList<Midia>> midiasPorGenero = new HashMap<>();

        for (Midia midia : top10MidiaAvaliadas) {
            String genero = midia.getGenero().toString();
            if (!midiasPorGenero.containsKey(genero)) {
                midiasPorGenero.put(genero, new ArrayList<>());
            }

            midiasPorGenero.get(genero).add(midia);
        }

        for (String genero : midiasPorGenero.keySet()) {
            int indexMidiaVista = 1;
            ArrayList<Midia> midiasAvaliadasPorGenero = midiasPorGenero.get(genero);
            for (Midia midia : midiasAvaliadasPorGenero) {
                System.out.println(indexMidiaVista + "- " + midia);
                indexMidiaVista++;
            }

            System.out.println();
        }

        return midiasPorGenero;
    }

private static ArrayList<Midia> getTop10MaisVistas() {
    ArrayList<Midia> midias = new ArrayList<>(streaming.getMidias().values());
    
    ArrayList<Midia> top10Midias = midias.stream()
        .sorted(Comparator.comparingInt(Midia::getAssistidaPorClientes).reversed())
        .limit(10)
        .collect(Collectors.toCollection(ArrayList::new));
    
    return top10Midias;
}
        private static HashMap<String, ArrayList<Midia>> getTop10MaisVistasPorGenero() {
        limparTela();
        System.out.println("10 mídias mais vistas");
        ArrayList<Midia> top10MidiaVistas = getTop10MaisVistas();
        HashMap<String, ArrayList<Midia>> midiasPorGenero = new HashMap<>();
        

        for (Midia midia : top10MidiaVistas) {
            String genero = midia.getGenero().toString();
            if (!midiasPorGenero.containsKey(genero)) {
                midiasPorGenero.put(genero, new ArrayList<>());
            }

            midiasPorGenero.get(genero).add(midia);
        }

        for (String genero : midiasPorGenero.keySet()) {
            int indexMidiaVista = 1;
            ArrayList<Midia> midiasVistasPorGenero = midiasPorGenero.get(genero);
            for (Midia midia : midiasVistasPorGenero) {
                System.out.println(indexMidiaVista + "- " + midia);
                indexMidiaVista++;
            }

            System.out.println();
        }

        return midiasPorGenero;
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

>>>>>>> Stashed changes
}
