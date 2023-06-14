package src;

/**
 * Interface para um comentarista.
 * Define um método para adicionar um comentário a uma avaliação.
 */
public interface IComentarista {

    /**
     * Adiciona um comentário a uma avaliação.
     *
     * @param comentario O comentário a ser adicionado.
     * @param avaliacao  A avaliação à qual o comentário será adicionado.
     */
    void addComentario(String comentario, Avaliacao avaliacao);
}
