package src;

import java.util.Scanner;

public class ClienteRegular implements ICliente {

    @Override
    public Avaliacao avaliar() {
        Scanner ler = new Scanner(System.in);
        System.out.println("Escolha uma nota de 1 a 5");
        int nota = ler.nextInt();
        while (nota <= 0 && nota > 5) {
            System.out.println("nota invalida, tente novamente");
            System.out.println("Escolha uma nota de 1 a 5");
            nota = ler.nextInt();
        }
        Avaliacao avaliacao = new Avaliacao(nota);
        return avaliacao;
    }

}
