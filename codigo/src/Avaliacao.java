package src;

public class Avaliacao {
    // talvez vamos ter que fazer um id cliente
    int nota = 0;
    String comentario = null;

    public Avaliacao(int nota) {
        this.nota = nota;
    }

    public Avaliacao(int nota, String comentario) {
        this.nota = nota;
        this.comentario = comentario;
    }
}
