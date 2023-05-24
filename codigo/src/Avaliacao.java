package src;

import java.time.LocalDateTime;

public class Avaliacao {
    private LocalDateTime data;
    private int avaliacao;
    private String comentario;
    private Midia midiaAvaliada;
    private Cliente cliente;

    public Avaliacao() {
    };

    public Avaliacao(int avaliacao, Midia midiaAvaliada, Cliente cliente) {
        if (avaliacao < 0 || avaliacao > 5) {
            throw new IllegalArgumentException("Avaliação inválida");
        }
        this.avaliacao = avaliacao;
        this.comentario = null;
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
