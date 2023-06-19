package src.Exceptions;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("Erro de autenticação");
    }
}
