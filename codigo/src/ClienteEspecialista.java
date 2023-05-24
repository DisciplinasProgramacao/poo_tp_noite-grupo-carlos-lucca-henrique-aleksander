package src;

import java.util.Scanner;

public class ClienteEspecialista implements ICliente {

    @Override
    public Avaliacao avaliar(int nota, Midia midia, Cliente cliente) {
        return new Avaliacao(nota, midia, cliente);
    }

    public void addComentario(String comentario, Avaliacao avaliacao) {
        avaliacao.addComentario(comentario);
    }

    public String tipoCliente() {
        return "Especialista";
    }

}
