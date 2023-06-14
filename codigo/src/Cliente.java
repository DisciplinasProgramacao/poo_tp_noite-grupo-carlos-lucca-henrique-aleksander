package src;

import src.Exceptions.AuthorizationException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Cliente {
    // #region variável de instância
    private String nome;
    private String senha;
    private String nomeUsuario;
    private ICliente tipoCliente;
    private ArrayList<Midia> midiasFuturas;
    private ArrayList<Midia> midiasAssistidas;
    private ArrayList<Avaliacao> avaliacoes;
    // #endregion

    /**
     * Construtor para criar um Cliente com nome, senha e nome de usuário.
     * 
     * @param nome        Nome do cliente
     * @param senha       Senha do cliente
     * @param nomeUsuario Nome de usuário do cliente
     */
    public Cliente(String nome, String senha, String nomeUsuario) {
        this.nome = nome;
        this.senha = senha;
        this.nomeUsuario = nomeUsuario;
        this.midiasFuturas = new ArrayList<>();
        this.midiasAssistidas = new ArrayList<>();
        this.avaliacoes = new ArrayList<>();
    }

    /**
     * Adiciona uma mídia à lista de mídias futuras do cliente.
     * Verifica se a mídia já está na lista antes de adicioná-la.
     * 
     * @param midia A mídia a ser adicionada. Não deve ser um valor repetido.
     */
    public void adicionarMidiaFutura(Midia midia) {
        boolean contemMidia = midiasFuturas.contains(midia);
        if (!contemMidia) {
            this.midiasFuturas.add(midia);
        }
    }

    /**
     * Adiciona uma mídia à lista de mídias assistidas do cliente.
     * Verifica se a mídia já está na lista antes de adicioná-la.
     * Se a mídia já foi assistida, não a adiciona novamente e não incrementa a
     * contagem de assistidos.
     * 
     * @param midia A mídia a ser adicionada.
     */
    public void terminarMidia(Midia midia) {
        if (!midiasAssistidas.contains(midia)) {
            this.midiasAssistidas.add(midia);
            midia.adicionaAssistido();
        }
    }

    /**
     * Busca uma mídia na lista de mídias futuras e na lista de mídias assistidas do
     * cliente.
     * Retorna a mídia se encontrada, caso contrário, retorna null.
     * 
     * @param midia A mídia a ser buscada.
     * @return A mídia encontrada ou null se não encontrada.
     */
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

    /**
     * Verifica se o cliente fez mais de cinco avaliações no último mês.
     * 
     * @return true se o cliente fez mais de cinco avaliações, false caso contrário.
     */
    public boolean temMaisDeCincoAvaliacoesUltimoMes() {
        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime mesPassado = hoje.minusMonths(1);
        AtomicInteger qtdeAvaliacoesNesseMes = new AtomicInteger(0);
        avaliacoes.forEach(av -> {
            LocalDateTime data = av.getData();
            if (data.isAfter(mesPassado) && data.isBefore(hoje)) {
                qtdeAvaliacoesNesseMes.incrementAndGet();
            }
        });
        return qtdeAvaliacoesNesseMes.get() >= 5;
    }

    // public Avaliacao avaliar(int avaliacao, Midia midia) {
    // if (midiasAssistidas.contains(midia)) {
    // Avaliacao avaliacaoClient = tipoCliente.avaliar(avaliacao, midia, this);
    // avaliacoes.add(avaliacaoClient);
    // midia.addAvaliacaoToAvaliacoesList(avaliacaoClient);
    // return avaliacaoClient;
    // } else {
    // throw new IllegalArgumentException("Você só pode avaliar uma mídia em sua
    // lista de mídias assistidas");
    // }
    // }

    /**
     * Retorna o nome do cliente.
     * 
     * @return String contendo o nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o nome de usuário do cliente.
     * 
     * @return String contendo o nome de usuário do cliente.
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * Retorna a senha do cliente.
     * 
     * @return String contendo a senha do cliente.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Retorna o tipo do cliente.
     * 
     * @return String contendo o tipo do cliente.
     */
    public String getTipoCliente() {
        return tipoCliente.tipoCliente();
    }

    /**
     * Retorna a lista de mídias assistidas pelo cliente.
     * 
     * @return um ArrayList contendo mídias assistidas pelo cliente.
     */
    public ArrayList<Midia> getMidiasAssistidas() {
        return midiasAssistidas;
    }

    /**
     * Retorna a lista de mídias futuras do cliente.
     * 
     * @return um ArrayList contendo mídias futuras do cliente.
     */
    public ArrayList<Midia> getMidiasFuturas() {
        return midiasFuturas;
    }

    /**
     * Retorna a lista de avaliações feitas pelo usuário.
     * 
     * @return um ArrayList contendo Avaliações feitas pelo cliente.
     */
    public ArrayList<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public Avaliacao avaliar(int avaliacao, Midia midia) {
        if (!midiasAssistidas.contains(midia)) {
            throw new IllegalArgumentException("Você só pode avaliar uma mídia em sua lista de mídias assistidas");
        }
        updateClientType();
        Avaliacao avaliacaoClient = tipoCliente.avaliar(avaliacao, midia, this);
        avaliacoes.add(avaliacaoClient);
        midia.addAvaliacaoToAvaliacoesList(avaliacaoClient);
        return avaliacaoClient;
    }

    public Avaliacao comentar(Avaliacao avaliacao, String comentario) {
        if (tipoCliente == null) {
            throw new AuthorizationException();
        }
        avaliacao.addComentario(comentario);
        return avaliacao;
    }

    private void updateClientType() {
        if (hasMoreThenFiveAvaliationsLastMonth()) {
            this.tipoCliente = new ClienteEspecialista();
        }
    }

    private boolean hasMoreThenFiveAvaliationsLastMonth() {
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
