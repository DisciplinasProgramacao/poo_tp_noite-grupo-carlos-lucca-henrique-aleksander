package src;

public class ClienteEspecialista implements IComentarista {

    @Override
    public void addComentario(String comentario, Avaliacao avaliacao) {
        avaliacao.addComentario(comentario);
    }

}
