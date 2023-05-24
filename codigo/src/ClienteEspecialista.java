package src;

import java.util.Scanner;

public class ClienteEspecialista implements ICliente {

    @Override
    public Avaliacao avaliar(int nota, Midia midia, Cliente cliente) {
        Scanner ler = new Scanner(System.in);
        System.out.println("Escolha uma nota de 1 a 5");
        while (nota <= 0 && nota > 5) {
            System.out.println("nota invalida, tente novamente");
            System.out.println("Escolha uma nota de 1 a 5");
            nota = ler.nextInt();
        }
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
                    return new Avaliacao(nota, comentario, midia, cliente);
                }
                break;
            }
        } while (op != "S" && op != "N");

        return new Avaliacao(nota, midia, cliente);

    }

    public void addComentario(String comentario, Avaliacao avaliacao) {
        avaliacao.addComentario(comentario);
    }

    public String tipoCliente() {
        return "Especialista";
    }

}
