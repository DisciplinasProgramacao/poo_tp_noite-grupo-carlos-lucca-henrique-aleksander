package src.Exceptions;

public class ReadFileError extends Exception{
    public ReadFileError() {
        super("Erro ao inicializar os arquivos, verifique as instrucoes.md");
    }
}
