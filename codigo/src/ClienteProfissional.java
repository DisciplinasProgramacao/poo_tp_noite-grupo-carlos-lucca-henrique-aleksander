package src;

public class ClienteProfissional implements IComentarista{
    @Override
    public void addComentario(String comentario, Avaliacao avaliacao) {
        avaliacao.addComentario(comentario);
    }
}
