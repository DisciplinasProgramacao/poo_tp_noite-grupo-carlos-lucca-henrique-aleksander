package src;

import src.Exceptions.AuthorizationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Cliente {
    // #region variável de instância
    private String nome;
    private String senha;
    private String nomeUsuario;
    private IComentarista tipoCliente;
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
    private boolean temMaisDeCincoAvaliacoesUltimoMes() {
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

    /**
     * Realiza uma avaliação de uma mídia.
     *
     * @param avaliacao A nota atribuída à mídia.
     * @param midia     A mídia a ser avaliada.
     * @return A avaliação realizada.
     * @throws IllegalArgumentException Se a mídia não estiver na lista de mídias
     *                                  assistidas.
     */
    public Avaliacao avaliar(Avaliacao avaliacaoClient, Midia midia) throws IllegalArgumentException {
        // Verifica se a mídia está na lista de mídias assistidas
        if (!midiasAssistidas.contains(midia)) {
            throw new IllegalArgumentException("Você só pode avaliar uma mídia em sua lista de mídias assistidas");
        }
        atualizarTipoCliente();
        avaliacoes.add(avaliacaoClient);
        midia.addAvaliacaoToAvaliacoesList(avaliacaoClient);
        return avaliacaoClient;
    }

    /**
     * Adiciona um comentário a uma avaliação existente.
     *
     * @param avaliacao  A avaliação à qual o comentário será adicionado.
     * @param comentario O comentário a ser adicionado.
     * @return A avaliação com o comentário adicionado.
     * @throws AuthorizationException Se o tipo de cliente for nulo.
     */
    public Avaliacao comentar(Avaliacao avaliacao, String comentario) throws AuthorizationException {
        if (tipoCliente == null) {
            throw new AuthorizationException();
        }
        // Adiciona o comentário à avaliação
        avaliacao.addComentario(comentario);
        return avaliacao;
    }

    /**
     * Atualiza o tipo do cliente com base no número de avaliações realizadas no
     * último mês.
     * Se o cliente tiver realizado mais de cinco avaliações, o tipo do cliente será
     * atualizado para ClienteEspecialista.
     */
    private void atualizarTipoCliente() {
        if (temMaisDeCincoAvaliacoesUltimoMes()) {
            this.tipoCliente = new ClienteEspecialista();
        }
    }

    /**
     * Descrição do cliente em string, com os seus respectivos dados.
     * 
     * @return String com nome, nome de usuário, mídias assistidas, mídias futuras
     *         e avaliações feitas do cliente.
     */
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
