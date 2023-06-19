package src;

/**
 * Implementação da interface IComentarista para o cliente profissional.
 * Permite adicionar um comentário a uma avaliação.
 */
public class ClienteProfissional implements ICliente, IComentarista {

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
    public Avaliacao avaliar(Avaliacao avaliacao, Cliente cliente) {
        Midia midiaAvaliada = avaliacao.getMidiaAvaliada();
        boolean jaAvaliouOFilme = cliente.getAvaliacoes()
                .stream()
                .anyMatch(avaliacao1 -> midiaAvaliada.getNome().equals(avaliacao1.getMidiaAvaliada().getNome()));
        if (jaAvaliouOFilme) {
            throw new IllegalArgumentException("Avaliação já existe na lista.");
        } else {
            return avaliacao;
        }
    }

    @Override
    public boolean terminarMidia(Cliente cliente, Midia midia) {
        boolean jaAssistiu = cliente.getMidiasAssistidas()
                .stream()
                .anyMatch(midiaA -> midiaA.getNome().equals(midia.getNome()));
        if (jaAssistiu) {
            throw new IllegalArgumentException("Essa midia ja foi vista");
        } else {
            return true;
        }
    }
}
