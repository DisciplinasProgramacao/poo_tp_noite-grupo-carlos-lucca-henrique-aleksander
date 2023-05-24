package src;

import java.util.Scanner;

public class ClienteRegular implements ICliente {

    @Override
    public Avaliacao avaliar(int nota, Midia midia, Cliente cliente) {
        return new Avaliacao(nota, midia, cliente);
    }

    public String tipoCliente() {
        return "Regular";
    }

}
