package src;

/**
 * Implementação da interface IComentarista para o cliente especialista.
 * Permite adicionar um comentário a uma avaliação.
 */
public class ClienteEspecialista implements ICliente, IComentarista {

    /**
     * Adiciona um comentário a uma avaliação.
     *
     * @param comentario O comentário a ser adicionado.
     */
    @Override
    public String addComentario(String comentario) {
        return comentario;
    }

    @Override
    public boolean terminarMidia(Cliente cliente, Midia midia) {
        boolean jaAssistiu = cliente.getMidiasAssistidas()
                .stream()
                .anyMatch(midiaA -> midiaA.getNome().equals(midia.getNome()));
        if (jaAssistiu) {
            throw new IllegalArgumentException("Essa midia ja foi vista");
        } else {
            if (midia.getIsLancamentoFuturo()) {
                throw new IllegalArgumentException("Essa midia so pode ser vista por um cliente profissional");
            } else {
                return true;
            }
        }
    }
}
