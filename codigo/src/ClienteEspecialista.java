package src;

/**
 * Implementação da interface IComentarista para o cliente especialista.
 * Permite adicionar um comentário a uma avaliação.
 */
public class ClienteEspecialista implements IComentarista {

    /**
     * Adiciona um comentário a uma avaliação.
     *
     * @param comentario O comentário a ser adicionado.
     * @param avaliacao  A avaliação à qual o comentário será adicionado.
     */
    @Override
    public void addComentario(String comentario, Avaliacao avaliacao) {
        avaliacao.addComentario(comentario);
    }
}
