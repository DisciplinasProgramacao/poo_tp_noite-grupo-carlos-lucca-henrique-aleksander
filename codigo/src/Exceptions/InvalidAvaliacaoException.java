package src.Exceptions;

public class InvalidAvaliacaoException extends RuntimeException{
    public InvalidAvaliacaoException() {
        super("Argumento inválido para criar avaliação");
    }

    public InvalidAvaliacaoException(String message) {
        super(message);
    }
}
