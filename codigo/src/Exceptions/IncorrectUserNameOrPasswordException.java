package src.Exceptions;

public class IncorrectUserNameOrPasswordException extends RuntimeException {
    public IncorrectUserNameOrPasswordException() {
        super("Falha na autenticação: nome de usuário ou senha incorretos.");
    }
}
