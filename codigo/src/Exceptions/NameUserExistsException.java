package src.Exceptions;

public class NameUserExistsException extends RuntimeException {
    public NameUserExistsException() {
        super("Já existe um cliente com esse nome de usuário");
    }
}
