package src;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Cliente {
    private String nome;
    private String senha;
    private String nomeUsuario;
    private ArrayList<Midia> midiasFuturas;
    private ArrayList<Midia> midiasAssistidas;
    private ArrayList<Avaliacao> avaliacoes;
    private boolean podeComentar;

    public Cliente(String nome, String senha, String nomeUsuario) {
        this.nome = nome;
        this.senha = senha;
        this.nomeUsuario = nomeUsuario;
        this.midiasFuturas = new ArrayList<>();
        this.midiasAssistidas = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
        this.podeComentar = false;
    }

    public void adicionarMidiaFutura(Midia midia) {
        boolean contemMidia = midiasFuturas.contains(midia);
        if (!contemMidia) {
            this.midiasFuturas.add(midia);
        }

    }

    public String getNome() {
        return nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void terminarMidia(Midia midia) {
        if (!midiasAssistidas.contains(midia)) {
            this.midiasAssistidas.add(midia);
            midia.adicionaAssistido();
        }
    }

    public Midia buscarMidia(Midia midia) {

        for (Midia m : this.midiasFuturas) {
            if (m.equals(midia)) {
                return m;
            }
        }
        for (Midia m : this.midiasAssistidas) {
            if (m.equals(midia)) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Midia> getMidiasAssistidas() {
        return midiasAssistidas;
    }

    public ArrayList<Midia> getMidiasFuturas() {
        return midiasFuturas;
    }

    public String getSenha() {
        return senha;
    }

    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public boolean getPodeComentar() {
        return podeComentar;
    }

    public Avaliacao avaliar(int avaliacao, Midia midia) {
        if (midiasAssistidas.contains(midia)) {
            Avaliacao avaliacaoClient = new Avaliacao(avaliacao, midia, this);
            avaliacoes.add(avaliacaoClient);
            return avaliacaoClient;
        } else {
            throw new IllegalArgumentException("Você só pode avaliar uma mídia em sua lista de mídias assistidas");
        }
    }

    public void addComentario(String comentario, Avaliacao avaliacao) {
        avaliacao.addComentario(comentario);
    }

    public boolean hasMoreThenFiveAvaliationsLastMonth() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime lastMonth = today.minusMonths(1);
        AtomicInteger qtdeAvaliacoesNesseMes = new AtomicInteger(0);
        avaliacoes.forEach(av -> {
            LocalDateTime data = av.getData();
            if (data.isAfter(lastMonth) && data.isBefore(today)) {
                qtdeAvaliacoesNesseMes.incrementAndGet();
            }
        });
        return qtdeAvaliacoesNesseMes.get() >= 5;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(getNome()).append("\n");
        sb.append("Nome de usuário: ").append(nomeUsuario).append("\n");
        sb.append("Mídias assistidas: ").append(midiasAssistidas.size()).append("\n");
        sb.append("Mídias para assistir: ").append(midiasFuturas.size()).append("\n");
        sb.append("Avaliações feitas: ").append(avaliacoes.size()).append("\n");
        return sb.toString();
    }
}
