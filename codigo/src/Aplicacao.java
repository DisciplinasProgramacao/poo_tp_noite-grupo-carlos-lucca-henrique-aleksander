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
import src.Exceptions.IncorrectUserNameOrPasswordException;
import src.Exceptions.InvalidAvaliacaoException;
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
        } catch (Exception e) {
            System.out.println("Erro inesperado! contate os desenvolvedores e informe o erro seguinte " + e);
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
        while (!sair) {
            limparTela();
            System.out.println("\u001B[33m== Menu Principal ==\u001B[37m");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Login");
            System.out.println("\u001B[31m3. Sair	\u001B[37m");
            System.out.print("Escolha uma opção: ");
            if (scanner.hasNextInt()) {
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
            } else {
                limparTela();
                System.out.println(
                        "\u001B[31mValor Inválido. Entre com apenas números.\u001B[37m");
                scanner.nextLine();
            }
            pausa();
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
        } catch (IOException e) {
            System.out.println("\u001B[31mErro: " + e.getMessage() + "\u001B[37m");
        }
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
        }
    }

    private static void exibirMenuCliente() {
        try {
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
                if (scanner.hasNextInt()) {
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        case 1:
                            buscarMidias();
                            break;
                        case 2:
                            verMidiaAssistida();
                            break;
                        case 3:
                            verMidiaFutura();
                            break;
                        case 4:
                            listarAvaliacoes();
                            break;
                        case 5:
                            adicionarMidiaFutura();
                            break;
                        case 6:
                            terminarMidia();
                            break;
                        case 7:
                            avaliarMidia();
                            break;
                        case 8:
                            exibirMenuRelatorios();
                            break;
                        case 9:
                            System.out.println("Saindo do menu do cliente...");
                            streaming.logout();
                            exibirMenuPrincipal();
                            return;
                        default:
                            limparTela();
                            System.out.println("\u001B[31mOpção inválida. Tente novamente\u001B[37m");
                            break;
                    }
                } else {
                    limparTela();
                    System.out.println(
                            "\u001B[31mOpção inválida, não coloque valor diferente de números. Tente novamente\u001B[37m");
                    scanner.nextLine(); // Limpa o buffer do scanner
                }
                pausa();
            }
        } catch (InputMismatchException e) {
            System.out.println("Insira um valor válido");
            exibirMenuPrincipal();
        } catch (InvalidMidiaException e){
            System.out.println(e.getMessage());
            pausa();
            exibirMenuCliente();
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
           throw new InvalidMidiaException("Mídia inválida, tente novamente");
        }
        try {
            streaming.adicionarMidiaFutura(retorno);
        }catch(IOException e){
            System.out.println("Não foi possível salvar sua alteração no arquivo, mas fique tranquilo, você poderá utilizar essa informação enquanto o aplicativo não fechar!");
            pausa();
            exibirMenuCliente();
        } 
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Insira um valor mostrado na lista de mídias!");
            pausa();
            exibirMenuCliente();
        }catch (RuntimeException e) {
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
            throw new InvalidMidiaException("Mídia inválida, tente novamente");
        }
        try {
            streaming.terminarMidia(retorno);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Insira um valor mostrado na lista de mídias!");
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
            pausa();
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
            if(midias.isEmpty()){
                System.out.println("Poxa, você ainda não assistiu nenhuma mídia!");
                pausa();
                exibirMenuCliente();
            }
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
            System.out.println("Deseja comentar ? s - sim / n - nao");
            scanner.nextLine();
            String opComen = scanner.nextLine();
            while (!opComen.equals("s") && !opComen.equals("n")) {
                System.out.println("opcao invalida");
                System.out.println("Deseja comentar ? s - sim / s - nao");
                opComen = scanner.nextLine();
            }

            if (opComen.equalsIgnoreCase("s")) {
                System.out.println("Qual seu comentario");
                String coment = scanner.nextLine();
                streaming.getClienteLogado().comentar((IComentarista) streaming.getClienteLogado().getTipoCliente(),
                        avaliacao, coment);
            }

            streaming.criarAvaliacao(avaliacao, midia);
            System.out.println("\u001B[32mMídia avaliada com sucesso. \u001B[37m");
        }catch(InvalidAvaliacaoException e){
            System.out.println(e.getMessage());
        }    catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Informe um dos números indicados na lista");
         }catch(ClassCastException e){
            System.out.println("Você não pode comentar!");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void exibirMenuRelatorios() {
        try {
            while (!sair) {
                limparTela();
                System.out.println("\u001B[33m== Menu de Relatórios ==\u001B[37m");
                System.out.println("1. Cliente que mais assistiu");
                System.out.println("2. Cliente com mais Avaliações");
                System.out.println("3. Porcentagem de clientes com 15 Avaliações");
                System.out.println("4. Mídias melhores Avaliadas");
                System.out.println("5. Mídias com mais Vizualizações");
                System.out.println("6. Mídias com melhores avaliadas por Categoria");
                System.out.println("7. Mídias com mais Vizualizações por Categoria");
                System.out.println("\u001B[31m8. Voltar \u001B[37m");
                System.out.print("Escolha uma opção: ");
                if (scanner.hasNextInt()) {
                    int opcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcao) {
                        // Qual cliente assistiu mais mídias, e quantas mídias.
                        case 1:
                            limparTela();
                            System.out.println("\u001b[38;5;214mCliente que mais assistiu Mídias: \u001B[37m"
                                    + "\nNome: "
                                    + clienteQueMaisAssistiu().getNome() + "\nNome Usuário: "
                                    + clienteQueMaisAssistiu().getNomeUsuario()
                                    + "\nTotal Mídias: " + clienteQueMaisAssistiu().getMidiasAssistidas().size());
                            break;

                        // Qual cliente tem mais avaliações, e quantas avaliações.
                        case 2:
                            limparTela();
                            System.out
                                    .println("\u001b[38;5;214mCliente que mais avaliou Mídias: \u001B[37m" + "\nNome: "
                                            + clienteComMaisAvaliacoes().getNome() + "\nNome Usuário: "
                                            + clienteComMaisAvaliacoes().getNomeUsuario() + "\nTotal de Avaliações: "
                                            + clienteComMaisAvaliacoes().getAvaliacoes().size());
                            break;

                        // Qual a porcentagem dos clientes com pelo menos 15 avaliações.
                        case 3:
                            limparTela();
                            System.out
                                    .println(
                                            "\u001b[38;5;214mPorcentagem de clientes com pelo menos 15 Avaliações: \u001B[37m"
                                                    + String.format("%.5f", porcClientesMin15avaliacoes()) + "%");
                            break;

                        // Quais são as 10 mídias com a melhor médias de avaliações e que tenham sido
                        // vistas pelo menos 100 vezes, apresentada em ordem descrescente.
                        case 4:
                            limparTela();
                            System.out.println(
                                    "\u001b[38;5;214mAs 10 Mídias melhores avaliadas e com 100 vizualizações: \u001B[37m");
                            System.out.println(melhoresAvaliadasEAssistidas());
                            break;

                        // Quais são as 10 mídias com mais vizualizações, em ordem descrescente.
                        case 5:
                            limparTela();
                            System.out.println(
                                    "\u001b[38;5;214mAs 10 Mídias com mais vizualizações: \u001B[37m");
                            System.out.println(midiasComMaisVizualizacoes());
                            break;

                        // Quais são as 10 mídias com a melhor médias de avaliações e que tenham sido
                        // vistas pelo menos 100 vezes, apresentada em ordem descrescente e por gênero.
                        case 6:
                            limparTela();
                            System.out.println(
                                    "\u001b[38;5;214mAs 10 Mídias melhores avaliadas e com 100 vizualizações por Gênero: \u001B[37m");
                            System.out.print("Qual o gênero que deseja fazer a busca? ");
                            System.out.println(melhoresAvaliadasEAssistidasGenero(scanner.nextLine()));
                            return;

                        // Quais são as 10 mídias com mais vizualizações, em ordem descrescente e por
                        // gênero.
                        case 7:
                            limparTela();
                            System.out.println(
                                    "\u001b[38;5;214mAs 10 Mídias com mais vizualizações por Gênero: \u001B[37m");
                            System.out.print("Qual o gênero que deseja fazer a busca? ");
                            System.out.println(midiasComMaisVizualizacoesGenero(scanner.nextLine()));
                            return;
                        case 8:
                            limparTela();
                            System.out.println("Voltando menu de relatórios...");
                            exibirMenuCliente();
                            return;
                        default:
                            limparTela();
                            System.out.println("\u001B[31mOpção inválida. Tente novamente\u001B[37m");
                            break;
                    }
                } else {
                    limparTela();
                    System.out.println(
                            "\u001B[31mOpção inválida, não coloque valor diferente de números. Tente novamente\u001B[37m");
                    scanner.nextLine();
                }
                pausa();
            }
        } catch (InputMismatchException e) {
            System.out.println("Insira um valor válido");
            exibirMenuPrincipal();
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

        if (results.isEmpty()) {
            sb.append("Não há nenhuma mídia");
        } else {
            for (int i = 0; i < results.size(); i++) {
                sb.append("\u001B[31m" + (i + 1) + "º " + "\u001B[37m "
                        + "\u001B[32m" + results.get(i).getNome() + "\u001B[37m"
                        + " ¦   Médias Avaliações: "
                        + String.format("%.2f", results.get(i).calculaMediaAvaliacoes()).replace(",", ".")
                        + " ¦   Vizualizações: " + results.get(i).getAssistidaPorClientes() + "\n");
            }
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
        if (results.isEmpty()) {
            sb.append("Não há nenhuma mídia");
        } else {
            for (int i = 0; i < results.size(); i++) {
                sb.append("\u001B[31m" + (i + 1) + "º " + "\u001B[37m " + "\u001B[32m" + results.get(i).getNome()
                        + "\u001B[37m" + " ¦   Vizualizações: " + results.get(i).getAssistidaPorClientes() + "\n");
            }
        }
        return sb.toString();
    }

    private static String melhoresAvaliadasEAssistidasGenero(String valor) {
        StringBuilder sb = new StringBuilder();
        List<Midia> resultsGenero = streaming.buscarMidia(valor, ComparatorMidia.porGenero());
        List<Midia> results = resultsGenero.stream()
                .filter(midia -> midia.getAssistidaPorClientes() >= 100)
                .filter(midia -> !midia.getAvaliacoes().isEmpty())
                .sorted(Comparator.comparingDouble(Midia::calculaMediaAvaliacoes).reversed())
                .limit(10)
                .collect(Collectors.toList());
        if (results.isEmpty()) {
            sb.append("Não há nenhuma mídia");
        } else {
            sb.append("Gênero: " + valor + "\n");
            for (int i = 0; i < results.size(); i++) {
                sb.append("\u001B[31m" + (i + 1) + "º " + "\u001B[37m "
                        + "\u001B[32m" + results.get(i).getNome() + "\u001B[37m"
                        + " ¦   Médias Avaliações: "
                        + String.format("%.2f", results.get(i).calculaMediaAvaliacoes()).replace(",", ".")
                        + "\n");
            }
        }
        return sb.toString();
    }

    private static String midiasComMaisVizualizacoesGenero(String valor) {
        StringBuilder sb = new StringBuilder();
        List<Midia> resultsGenero = streaming.buscarMidia(valor, ComparatorMidia.porGenero());
        List<Midia> results = resultsGenero.stream()
                .filter(midia -> midia.getAssistidaPorClientes() != 0)
                .sorted(Comparator.comparingDouble(Midia::getAssistidaPorClientes).reversed())
                .limit(10)
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            sb.append("Não há nenhuma mídia");
        } else {
            sb.append("Gênero: " + valor + "\n");
            for (int i = 0; i < results.size(); i++) {
                sb.append("\u001B[31m" + (i + 1) + "º " + "\u001B[37m " + "\u001B[32m" + results.get(i).getNome()
                        + "\u001B[37m" + " ¦   Vizualizações: " + results.get(i).getAssistidaPorClientes() + "\n");
            }
        }
        return sb.toString();
    }
}
