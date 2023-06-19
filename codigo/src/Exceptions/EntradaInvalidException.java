package src.Exceptions;

public class EntradaInvalidException extends RuntimeException {
    public EntradaInvalidException() {
        super("Valor Inválido. Entre com apenas números.");
    }
}
