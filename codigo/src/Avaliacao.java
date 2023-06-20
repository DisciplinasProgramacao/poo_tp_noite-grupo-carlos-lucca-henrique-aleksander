package src;

import src.Exceptions.InvalidAvaliacaoException;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa uma avaliação de uma mídia feita por um cliente.
 */
public class Avaliacao {
    // #region variável de instância
    private LocalDateTime data;
    private int avaliacao;
    private String comentario;
    private Midia midiaAvaliada;
    private Cliente cliente;
    // #endregion

    // #region constantes
    private static final int MIN_AVALIACAO_VALUE = 1;
    private static final int MAX_AVALIACAO_VALUE = 5;
    // #endregion

    /**
     * Construtor da classe Avaliacao com comentário.
     *
     * @param avaliacao     A nota atribuída à avaliação.
     * @param comentario    O comentário associado à avaliação.
     * @param midiaAvaliada A mídia que foi avaliada.
     * @param cliente       O cliente que fez a avaliação.
     */
    public Avaliacao(int avaliacao, String comentario, Midia midiaAvaliada, Cliente cliente) {
        init(avaliacao, comentario, midiaAvaliada, cliente, LocalDateTime.now());
    }

    /**
     * Construtor da classe Avaliacao sem comentário.
     *
     * @param avaliacao     A nota atribuída à avaliação.
     * @param midiaAvaliada A mídia que foi avaliada.
     * @param cliente       O cliente que fez a avaliação.
     */
    public Avaliacao(int avaliacao, Midia midiaAvaliada, Cliente cliente) {
        init(avaliacao, null, midiaAvaliada, cliente, LocalDateTime.now());
    }

    /**
     * Construtor da classe Avaliacao com data.
     *
     * @param avaliacao     A nota atribuída à avaliação.
     * @param midiaAvaliada A mídia que foi avaliada.
     * @param cliente       O cliente que fez a avaliação.
     * @param data          A data da avaliação.
     */
    public Avaliacao(int avaliacao, Midia midiaAvaliada, Cliente cliente, LocalDate data) {
        init(avaliacao, null, midiaAvaliada, cliente, data.atStartOfDay());
    }

    /**
     * Construtor da classe Avaliacao com data.
     *
     * @param avaliacao     A nota atribuída à avaliação.
     * @param comentario    O comentário associado à avaliação.
     * @param midiaAvaliada A mídia que foi avaliada.
     * @param cliente       O cliente que fez a avaliação.
     * @param data          A data da avaliação.
     */
    public Avaliacao(int avaliacao, String comentario, Midia midiaAvaliada, Cliente cliente, LocalDate data) {
        init(avaliacao, comentario, midiaAvaliada, cliente, data.atStartOfDay());
    }

    /**
     * Inicializa os atributos da avaliação.
     *
     * @param avaliacao     A nota atribuída à avaliação.
     * @param comentario    O comentário associado à avaliação.
     * @param midiaAvaliada A mídia que foi avaliada.
     * @param cliente       O cliente que fez a avaliação.
     * @throws InvalidAvaliacaoException Se a nota da avaliação estiver fora dos
     *                                   limites permitidos.
     */
    private void init(int avaliacao, String comentario, Midia midiaAvaliada, Cliente cliente, LocalDateTime data)
            throws InvalidAvaliacaoException {
        if (avaliacao < MIN_AVALIACAO_VALUE || avaliacao > MAX_AVALIACAO_VALUE) {
            throw new InvalidAvaliacaoException();
        }
        this.avaliacao = avaliacao;
        this.comentario = comentario;
        this.data = data;
        this.midiaAvaliada = midiaAvaliada;
        this.cliente = cliente;
    }

    /**
     * Obtém a nota da avaliação.
     *
     * @return A nota da avaliação.
     */
    public int getAvaliacao() {
        return avaliacao;
    }

    /**
     * Obtém a data em que a avaliação foi realizada.
     *
     * @return A data da avaliação.
     */
    public LocalDateTime getData() {
        return data;
    }

    /**
     * Obtém a mídia que foi avaliada.
     *
     * @return A mídia avaliada.
     */
    public Midia getMidiaAvaliada() {
        return midiaAvaliada;
    }

    /**
     * Obtém o cliente que avaliou a midia.
     *
     * @return O cliente que avaliou.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Obtém o comentário associado à avaliação.
     * Se não houver comentário, retorna a string "Sem comentário".
     *
     * @return O comentário da avaliação.
     */
    public String getComentario() {
        if (comentario == null) {
            return "Sem comentário";
        }
        return comentario;
    }

    public String getDataFormatada(){
       return data.format(Aplicacao.DATA_FORMATTER);
    }
    /**
     * Adiciona um comentário à avaliação.
     *
     * @param comentario O comentário a ser adicionado.
     */
    public void addComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * Retorna uma representação em formato de string da avaliação.
     *
     * @return A representação em formato de string da avaliação.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(cliente.getNome()).append("\n");
        sb.append("Mídia: ").append(getMidiaAvaliada().getNome()).append("\n");
        sb.append("Avaliação: ").append(avaliacao).append("\n");
        if (comentario != null) {
            sb.append("Comentário: ").append(getComentario()).append("\n");
        }
        sb.append("Data: ").append(data.format(Aplicacao.DATA_FORMATTER)).append("\n");
        return sb.toString();
    }
}
