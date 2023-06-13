package src;

import src.Exceptions.InvalidAvaliacaoException;
import src.Exceptions.InvalidMidiaException;

import java.time.LocalDateTime;

public class Avaliacao {
    private LocalDateTime data;
    private int avaliacao;
    private String comentario;
    private Midia midiaAvaliada;
    private Cliente cliente;

    private final static int MIN_AVALIACAO_VALUE = 0;
    private final static int MAX_AVALIACAO_VALUE = 5;

    public Avaliacao() {
    };

    public Avaliacao(int avaliacao, String comentario, Midia midiaAvaliada, Cliente cliente) {
        init(avaliacao, comentario, midiaAvaliada, cliente);
    }

    public Avaliacao(int avaliacao, Midia midiaAvaliada, Cliente cliente) {
        init(avaliacao, null, midiaAvaliada, cliente);
    }

    public void init(int avaliacao, String comentario, Midia midiaAvaliada, Cliente cliente){
        if(avaliacao<MIN_AVALIACAO_VALUE || avaliacao>MAX_AVALIACAO_VALUE){
            throw new InvalidAvaliacaoException();
        }
        this.avaliacao = avaliacao;
        this.comentario = comentario;
        this.data = LocalDateTime.now();
        this.midiaAvaliada = midiaAvaliada;
        this.cliente = cliente;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Midia getMidiaAvaliada() {
        return midiaAvaliada;
    }

    public String getComentario() {
        if (comentario == null) {
            return "Sem comentário";
        }
        return comentario;
    }

    public void addComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(cliente.getNome());
        sb.append("Mídia: ").append(getMidiaAvaliada().getNome()).append("\n");
        sb.append("Avaliação: ").append(avaliacao).append("\n");
        if (comentario != null) {
            sb.append("Comentário: ").append(getComentario()).append("\n");
        }
        sb.append("Data: ").append(data.format(app.DATA_FORMATTER)).append("\n");
        return sb.toString();
    }
}
