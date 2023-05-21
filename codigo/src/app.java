package src;

import java.io.FileNotFoundException;

public class app {
    public static void main(String[] args) throws FileNotFoundException {
        Streaming teste = new Streaming();
        teste.iniciar();

        Avaliacao avaliacao = new Avaliacao(1);
        Avaliacao avaliacao2 = new Avaliacao(1, "top");
        System.out.println(avaliacao);
        System.out.println(avaliacao2);
    }
}
