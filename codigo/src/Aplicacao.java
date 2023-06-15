package src;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        try {
            exibirMenuPrincipal();
        } catch (Exception error) {
            System.out.println(error);
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

    private static void verMidiaAssistida(){       
        limparTela();
        System.out.println(streaming.getClienteLogado().MostrarListaMidiaAssistida());
    }
     private static void verMidiaFutura(){       
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
        System.out.print("Título da Mídia: ");
        String titulo = scanner.nextLine();
        ArrayList<Midia> resultado = streaming.buscarMidia(titulo, ComparatorMidia.porNome());
        int cont = 0;
        for (Midia midia : resultado) {
            System.out.println(cont + " - " + midia.toString());
            cont++;
        }
        System.out.println("Qual o numero de sua escolha?");
        int op = scanner.nextInt();
        return resultado.get(op - 1);
    }

    private static void adicionarMidiaFutura() {
        System.out.println("== Adicionar Mídia Futura ==");
        Midia retorno = buscaMidiaTituloParaMetodos();
        streaming.getClienteLogado().adicionarMidiaFutura(retorno);
        System.out.println("Mídia adicionada com sucesso.");
    }

    private static void terminarMidia() {
        System.out.println("== Terminar Mídia ==");
        Midia retorno = buscaMidiaTituloParaMetodos();
        streaming.getClienteLogado().adicionarMidiaFutura(retorno);
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
        Midia midia = streaming.getClienteLogado().getMidiasAssistidas().get(op-1);
        System.out.println("Escolha uma nota de 1 a 5");
        int nota = scanner.nextInt();
        System.out.println("Qual seu comentario");
        scanner.nextLine();
        String coment = scanner.nextLine();
        if(streaming.getClienteLogado().getTipoCliente()==null){
        avaliacao = new Avaliacao(nota ,midia, streaming.getClienteLogado(), LocalDate.now());  // Criar a avaliação} 
        }else{
         avaliacao = new Avaliacao(nota, coment ,midia, streaming.getClienteLogado(), LocalDate.now());  // Criar a avaliação
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
                case 6:
                    // TODO: Implementar funcao
                    return;
                case 7:
                    // TODO: Implementar funcao

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

}
